package szilveszterandras.vspf.dal;

import java.util.List;

public interface GenericDAO<T> {

	List<T> getAllDataRows();

	void insertObjects(List<T> objects);

	void updateObject(T obj);

	void deleteObject(int id);
}