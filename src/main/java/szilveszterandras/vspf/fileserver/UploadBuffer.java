package szilveszterandras.vspf.fileserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadBuffer {
	private static UploadBuffer instance = null;
	private Map<UUID, byte[]> pendingUploads = new HashMap<UUID, byte[]>();
	
	public static UploadBuffer getInstance() {
		if (instance == null) {
			instance = new UploadBuffer();
		}
		return instance;
	}
	
	public void add(UUID imageHash, byte[] file) {
		pendingUploads.put(imageHash, file);
	}
	public Boolean contains(UUID imageHash) {
		return pendingUploads.containsKey(imageHash);
	}
	public byte[] pull(UUID imageHash) {
		byte[] file = pendingUploads.get(imageHash);
		pendingUploads.remove(imageHash);
		return file;
	}
}
