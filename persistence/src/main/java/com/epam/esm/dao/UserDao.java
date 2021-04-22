package com.epam.esm.dao;

import com.epam.esm.model.Pageable;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

public interface UserDao<T extends Persistable<? extends Serializable>> {

    T findById(Long id);

    List<T> findAll(Pageable pageable);
}
