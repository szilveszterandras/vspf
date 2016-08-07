package szilveszterandras.vspf.handler;

import java.util.List;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.dal.TagCount;

public class StreamTagsHandler extends AuthorizedHandler<Object> {
	public StreamTagsHandler() {
		super(Object.class);
		// No need to separate tag deletion, count will return with 0,
		// UI will remove the item
		Notifiable<Tag> onTagUpdate = new Notifiable<Tag>() {
			@Override
			public void onEvent(Tag data) {
				TagCount t = DAOFactory.getInstance().getTagDAO().getTagCount(data.getName());
				sendEvent((new Gson()).toJson(wrap(t)));
			}
		};
		this.subscribe("tag/persist", onTagUpdate);		
		this.subscribe("tag/remove", onTagUpdate);
	}

	@Override
	public SocketHandler run() {
		List<TagCount> l = DAOFactory.getInstance().getTagDAO().getAllTagCounts();
		sendEvent((new Gson()).toJson(l));
		return this;
	}
}
