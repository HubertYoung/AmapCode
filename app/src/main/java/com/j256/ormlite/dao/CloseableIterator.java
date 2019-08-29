package com.j256.ormlite.dao;

import com.j256.ormlite.support.DatabaseResults;
import java.util.Iterator;

public interface CloseableIterator<T> extends Iterator<T> {
    void close();

    void closeQuietly();

    T current();

    T first();

    DatabaseResults getRawResults();

    T moveRelative(int i);

    void moveToNext();

    T nextThrow();

    T previous();
}
