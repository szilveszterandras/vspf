package szilveszterandras.vspf.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import szilveszterandras.vspf.Notifiable;
import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.dal.TagCount;
import szilveszterandras.vspf.payload.SearchTerm;

public class SearchTagsHandler extends AuthorizedHandler<SearchTerm> {
	public SearchTagsHandler() {
		super(SearchTerm.class);
		
		Notifiable<Tag> n = new Notifiable<Tag>() {
			@Override
			public void onEvent(Tag data) {
				returnFitting();
			}
		};
		this.subscribe("tag/persist", n);
		this.subscribe("tag/remove", n);
	}
	
	@Override
	public SocketHandler run() {
		returnFitting();
		return this;
	}
	private void returnFitting() {
		List<TagCount> tags = DAOFactory.getInstance().getTagDAO().getAllTagCounts().stream()
				.filter(t -> t.getTag().contains(payload.getTerm()))
				.sorted((t1, t2) -> t1.getTag().compareTo(t2.getTag()))
				.collect(Collectors.toList());

		sendEvent((new Gson()).toJson(tags));
	}
}
