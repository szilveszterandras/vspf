package szilveszterandras.vspf.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.App;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.User;
import szilveszterandras.vspf.fileserver.UploadBuffer;
import szilveszterandras.vspf.payload.StatusResponse;

public class InsertUserHandler extends SimpleHandler<User> {
	public static final Logger logger = LoggerFactory.getLogger(InsertUserHandler.class);

	public InsertUserHandler() {
		super(User.class);
	}

	@Override
	public SocketHandler run() {
		UUID hash = UUID.fromString(payload.getAvatar());
		if (UploadBuffer.getInstance().contains(hash)) {
			try {
				String path = this.saveAvatarToDisk(hash);
				this.payload.setAvatar(path);
				
				DAOFactory.getInstance().getUserDAO().insertUser(this.payload);
				this.sendEvent((new Gson()).toJson(new StatusResponse(true)));
			} catch (IOException e) {
				sendEvent((new Gson()).toJson(new StatusResponse("Unable to save user")));
				e.printStackTrace();
			}
		} else {
			sendEvent((new Gson()).toJson(new StatusResponse("Unable to save avatar")));
		}
		return this;
	}

	private String saveAvatarToDisk(UUID hash) throws IOException {
		byte[] contents = UploadBuffer.getInstance().pull(hash);
		String path = payload.getUsername() + "/avatar_" + payload.getAvatar() + ".jpg";
		String fullPath = App.config.getProperty("imageCache") + "/" + path; 
		logger.debug("Saving file to path: " + fullPath);

		// Create folder if it doesn't exist
		File f = new File(App.config.getProperty("imageCache") + "/" + payload.getUsername());
		f.mkdirs();
		FileOutputStream out = new FileOutputStream(fullPath);
		out.write(contents);
		out.close();
		return path;
	}
}
