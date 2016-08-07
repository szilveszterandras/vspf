package szilveszterandras.vspf;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.handler.AuthorizedHandler;
import szilveszterandras.vspf.handler.SocketHandler;
import szilveszterandras.vspf.payload.PhotoFilter;
import szilveszterandras.vspf.payload.StatusResponse;

public class UpdatePhotoHandler extends AuthorizedHandler<PhotoFilter> {
	public static final Logger logger = LoggerFactory.getLogger(UpdatePhotoHandler.class);
	public UpdatePhotoHandler() {
		super(PhotoFilter.class);
	}
	@Override
	public SocketHandler run() {
		logger.info("Got update request");
		// Update tags
		this.updateTags(payload.getId(), payload.getTags());
		Photo p = DAOFactory.getInstance().getPhotoDAO().getPhoto(payload.getId());
		if (payload.getTitle() != null) {
			p.setTitle(payload.getTitle());
		}
		if (payload.getDescription() != null) {
			p.setDescription(payload.getDescription());
		}
		DAOFactory.getInstance().getPhotoDAO().updatePhoto(p);
		sendEvent((new Gson()).toJson(new StatusResponse(true)));
		return this;
	}
	private void updateTags(Long id, List<String> newTags) {
		List<Tag> currentTags = DAOFactory.getInstance().getTagDAO().filterByPhotoId(id); 
		List<String> currentTagNames = new ArrayList<String>();
		for (Tag t : currentTags) {
			currentTagNames.add(t.getName());
		}
		// Delete removed tags
		for (Tag t : currentTags) {
			if (!newTags.contains(t.getName())) {
				DAOFactory.getInstance().getTagDAO().deleteTag(t.getId());
			}
		}
		// Add new tags
		for (String t : newTags) {
			if (!currentTagNames.contains(t)) {
				DAOFactory.getInstance().getTagDAO().insertTag(new Tag(t, id));
			}
		}
	}
}
