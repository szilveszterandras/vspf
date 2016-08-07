package szilveszterandras.vspf.handler;

import szilveszterandras.vspf.dal.DAOFactory;
import szilveszterandras.vspf.dal.Tag;

public class InsertTagHandler extends AuthorizedHandler<Tag> {
	public InsertTagHandler() {
		super(Tag.class);
	}
	@Override
	public SocketHandler run() {
		DAOFactory.getInstance().getTagDAO().insertTag(this.payload);
		return this;
	}
}
