package de.greenrobot.dao.query;

import java.io.Closeable;
import java.util.ListIterator;

public interface CloseableListIterator<T> extends Closeable, ListIterator<T> {
}
