package com.spotify.service;

public interface BaseService<T, K> {
    T save(T t) throws Exception;

    T update(T t);

    T findById(K id);

    boolean delete(K id);
}
