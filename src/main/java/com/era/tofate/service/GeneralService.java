package com.era.tofate.service;

import java.util.List;
import java.util.Optional;

public interface GeneralService<T> {
    T getOne(Long id);

    Optional<T> findById(Long id);

    T save(T object);

    List<T> getAll();
}
