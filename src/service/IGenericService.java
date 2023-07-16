package service;

import java.util.List;

public interface IGenericService<E,T> {
    List<E> findAll();

    void save(E e);

    E findById(T t);

    void deleteById(T t);
}
