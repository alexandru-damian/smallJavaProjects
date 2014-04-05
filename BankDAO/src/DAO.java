import java.util.*;



public interface DAO<T extends Object> {
	public void add(T obj);
	public boolean remove(int id);
	public boolean update(T obj);
	public T findById(int id);
	public Map<Integer,T> getList();
}
