package com.j256.ormlite.dao;

import java.util.List;

public interface GenericRawResults<T> extends CloseableWrappedIterable<T> {
    void close();

    String[] getColumnNames();

    T getFirstResult();

    int getNumberColumns();

    List<T> getResults();
}
