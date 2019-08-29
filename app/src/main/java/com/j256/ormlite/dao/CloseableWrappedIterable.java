package com.j256.ormlite.dao;

public interface CloseableWrappedIterable<T> extends CloseableIterable<T> {
    void close();
}
