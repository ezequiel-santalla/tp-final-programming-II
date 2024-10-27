package repository;

import java.util.List;

public interface Repository<T, V> {

    V create(T model);

    T find(V id);

    void update(T model);

    void delete(V id);

    List<T> getAll();
}
