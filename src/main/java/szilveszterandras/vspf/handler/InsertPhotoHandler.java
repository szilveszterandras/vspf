package szilveszterandras.vspf.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import com.google.gson.Gson;

import szilveszterandras.vspf.App;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.fileserver.UploadBuffer;
import szilveszterandras.vspf.payload.PhotoFilter;
import szilveszterandras.vspf.payload.StatusResponse;

public class InsertPhotoHandler extends AuthorizedHandler<PhotoFilter> {

	public InsertPhotoHandler() {
		super(PhotoFilter.class);
	}

	@Override
	public SocketHandler run() {
		// Check for hash in buffer, if it exists, persist
		UUID hash = UUID.fromString(payload.getHash());
		if (UploadBuffer.getInstance().contains(hash)) {
			try {
				String username = DAOFactory.getInstance().getUserDAO()
						.getUser(this.connection.getSession().getUserId()).getUsername();
				// TODO properly set extension
				String path = username + "/" + hash + ".jpg";
				
				Photo p = new Photo();
				p.setUserId(this.connection.getSession().getUserId());
				p.setTitle(payload.getTitle());
				p.setDescription(payload.getDescription());
				p.setHash(payload.getHash());
				p.setUploadedAt(new Date());
				p.setPath(path);

				this.savePhotoToDisk(hash, username);
				DAOFactory.getInstance().getPhotoDAO().insertPhoto(p);
				for (String s : payload.getTags()) {
					DAOFactory.getInstance().getTagDAO().insertTag(new Tag(s, p.getId()));
				}
				sendEvent((new Gson()).toJson(true));
			} catch (IOException e) {
				reportFailure();
				logger.warn("Unable to write file to disk", e);
			}
		} else {
			reportFailure();
		}
		return this;
	}

	private void savePhotoToDisk(UUID hash, String username) throws IOException {
		byte[] contents = UploadBuffer.getInstance().pull(hash);
		String fullPath = App.config.getProperty("imageCache") + "/" + this.payload.getPath();
		logger.debug("Saving file to path: " + fullPath);

		// Create folder if it doesn't exist
		File f = new File(App.config.getProperty("imageCache") + "/" + username);
		f.mkdirs();
		FileOutputStream out = new FileOutputStream(fullPath);
		out.write(contents);
		out.close();
	}

	private void reportFailure() {
		sendEvent((new Gson()).toJson(new StatusResponse("Unable to save photo")));
	}
}
