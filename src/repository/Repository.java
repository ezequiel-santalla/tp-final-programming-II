package repository;

import java.util.List;

public interface Repository <T, V>{

    public V create(T model);

    public T find(V id);

    public void update(T model);

    public void delete(V id);

    public List<T> getAll();
}
