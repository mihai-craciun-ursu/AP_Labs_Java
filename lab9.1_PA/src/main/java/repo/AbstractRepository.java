package repo;

import java.util.List;

public interface AbstractRepository<T> {
    public void create(T object);
    public T findById(int id);
    public List<T> findByName(String name);

}
