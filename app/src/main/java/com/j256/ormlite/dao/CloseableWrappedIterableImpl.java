package com.j256.ormlite.dao;

import java.sql.SQLException;

public class CloseableWrappedIterableImpl<T> implements CloseableWrappedIterable<T> {
    private final CloseableIterable<T> iterable;
    private CloseableIterator<T> iterator;

    public CloseableWrappedIterableImpl(CloseableIterable<T> iterable2) {
        this.iterable = iterable2;
    }

    public CloseableIterator<T> iterator() {
        return closeableIterator();
    }

    public CloseableIterator<T> closeableIterator() {
        try {
            close();
        } catch (SQLException e) {
        }
        this.iterator = this.iterable.closeableIterator();
        return this.iterator;
    }

    public void close() {
        if (this.iterator != null) {
            this.iterator.close();
            this.iterator = null;
        }
    }
}
