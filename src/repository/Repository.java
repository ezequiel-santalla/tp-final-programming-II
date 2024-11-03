package repository;

import exceptions.EntityNotFoundException;

import java.util.List;

public interface Repository<T, V> {

    V create(T model) ;

    T find(V id) throws EntityNotFoundException;

    void update(T model) throws EntityNotFoundException;

    void delete(V id) throws EntityNotFoundException;

    List<T> getAll() throws EntityNotFoundException;
}
