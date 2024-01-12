package com.example.TsonkovResortManager.repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface CRUDRepositoryInterface<T,ID>{
    T save(T model);
    Optional<T> findById(ID id);
    List<T> findAll();
    void delete(T entity);
    void deleteById(ID id);
    void update(T entity);
}
