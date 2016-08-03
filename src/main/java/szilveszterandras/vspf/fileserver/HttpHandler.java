package szilveszterandras.vspf.fileserver;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_MODIFIED;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import szilveszterandras.vspf.App;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	public static final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
	public static final int HTTP_CACHE_SECONDS = 60;
	public static final SimpleDateFormat dateFormatter = getDateFormatter("EEE, dd MMM yyyy HH:mm:ss zzz",
			TimeZone.getTimeZone("GMT"));

	public static SimpleDateFormat getDateFormatter(String format, TimeZone timezone) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format, Locale.US);
		dateFormatter.setTimeZone(timezone);
		return dateFormatter;
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (!request.decoderResult().isSuccess()) {
			sendError(ctx, BAD_REQUEST);
			return;
		}

		if (request.method() == GET) {
			handleGet(ctx, request);
		} else if (request.method() == POST) {
			handleUpload(ctx, request);
		} else {
			sendError(ctx, METHOD_NOT_ALLOWED);
		}
	}

	private void handleGet(ChannelHandlerContext ctx, FullHttpRequest request) throws IOException, ParseException {
		final String path = sanitizeUri(request.uri());

		File file = new File(path);
		if (file.isHidden() || !file.exists() || !file.isFile()) {
			sendError(ctx, NOT_FOUND);
			return;
		}
		logger.debug(String.format("Requested file at path: %s", file.getAbsolutePath()));

		// Cache Validation
		if (!checkIfModified(request.headers().get(HttpHeaderNames.IF_MODIFIED_SINCE), file)) {
			sendNotModified(ctx);
			return;
		}

		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException ignore) {
			sendError(ctx, NOT_FOUND);
			return;
		}
		long fileLength = raf.length();

		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE,
				(new MimetypesFileTypeMap()).getContentType(file.getPath()));
		HttpUtil.setContentLength(response, fileLength);
		setDateAndCacheHeaders(response, file);
		if (HttpUtil.isKeepAlive(request)) {
			response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		}

		// Write the initial line and the header.
		ctx.write(response);

		// Write the content.
		ctx.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength));
		ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture cf) throws Exception {
				raf.close();
				logger.debug(String.format("Finished transfer of file: %s, connection closed", file.getAbsolutePath()));
				ChannelFutureListener.CLOSE.operationComplete(cf);
			}
		});
	}

	private void handleUpload(ChannelHandlerContext ctx, FullHttpRequest request) {
		if (!request.uri().equals("/upload")) {
			sendError(ctx, FORBIDDEN);
			return;
		}

		InterfaceHttpData data = (new HttpPostRequestDecoder(request)).next();
		if (data == null || data.getHttpDataType() != HttpDataType.FileUpload) {
			sendError(ctx, FORBIDDEN);
		}
		UUID imageHash = UUID.randomUUID();
		try {
			byte[] contents = ((FileUpload) data).get();
			UploadBuffer.getInstance().add(imageHash, contents);
		} catch (IOException e) {
			if (ctx.channel().isActive()) {
				sendError(ctx, INTERNAL_SERVER_ERROR);
				logger.warn("Unable to upload file: " + data.getName(), e);
				return;
			}
		}

		// Send generated UUID in response
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain")
				.set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		HttpUtil.setContentLength(response, imageHash.toString().length());

		ctx.write(response);
		ctx.write(imageHash.toString());
		ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
		e.printStackTrace();
		if (ctx.channel().isActive()) {
			sendError(ctx, INTERNAL_SERVER_ERROR);
		}
	}

	private boolean checkIfModified(String ifModifiedSince, File file) {
		Date ifModifiedSinceDate;
		try {
			ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);
		} catch (ParseException | NullPointerException e) {
			// If modified since date can't be parsed, flag as potentially
			// modified
			return true;
		}
		// Only compare up to the second because the datetime format we send
		// to the client does not have milliseconds
		long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
		long fileLastModifiedSeconds = file.lastModified() / 1000;
		return ifModifiedSinceDateSeconds < fileLastModifiedSeconds;
	}

	private static String sanitizeUri(String uri) {
		// Convert file separators.
		uri = uri.replace('/', File.separatorChar);
		// TODO security check uri
		// Convert to absolute path.
		return App.config.getProperty("imageCache") + File.separator + uri;
	}

	private static void sendResponse(ChannelHandlerContext ctx, FullHttpResponse response) {
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8")
				.set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

		// Close the connection as soon as the error message is sent.
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		sendResponse(ctx, new DefaultFullHttpResponse(HTTP_1_1, status));
	}

	private static void sendNotModified(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, NOT_MODIFIED);
		response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(new Date()));
		sendResponse(ctx, response);
	}

	private static void setDateAndCacheHeaders(HttpResponse response, File fileToCache) {
		// Date header
		Calendar time = new GregorianCalendar();
		response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));

		// Add cache headers
		time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
		response.headers().set(HttpHeaderNames.EXPIRES, dateFormatter.format(time.getTime()));
		response.headers().set(HttpHeaderNames.CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
		response.headers().set(HttpHeaderNames.LAST_MODIFIED,
				dateFormatter.format(new Date(fileToCache.lastModified())));
	}
}