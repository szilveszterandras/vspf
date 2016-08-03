package szilveszterandras.vspf.fileserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

public class HttpServer {
	static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

	public HttpServer() {
	}

	public void run() {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) {
							ChannelPipeline p = ch.pipeline();

							p.addLast(new HttpServerCodec());
							p.addLast(new StringEncoder(CharsetUtil.UTF_8));
							p.addLast(new HttpObjectAggregator(10000000));
							p.addLast(new HttpHandler());
						}
					});

			Channel ch = b.bind(PORT).sync().channel();

			System.err.println("Open your web browser and navigate to " + "http://127.0.0.1:" + PORT + '/');

			ch.closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}