package com.j256.ormlite.dao;

import java.util.Collection;

public interface ForeignCollection<T> extends CloseableIterable<T>, Collection<T> {
    boolean add(T t);

    void closeLastIterator();

    CloseableIterator<T> closeableIterator(int i);

    CloseableWrappedIterable<T> getWrappedIterable();

    CloseableWrappedIterable<T> getWrappedIterable(int i);

    boolean isEager();

    CloseableIterator<T> iterator(int i);

    CloseableIterator<T> iteratorThrow();

    CloseableIterator<T> iteratorThrow(int i);

    int refresh(T t);

    int refreshAll();

    int refreshCollection();

    int update(T t);

    int updateAll();
}
