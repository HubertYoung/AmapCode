package com.j256.ormlite.dao;

import com.j256.ormlite.field.FieldType;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LazyForeignCollection<T, ID> extends BaseForeignCollection<T, ID> implements ForeignCollection<T>, Serializable {
    private static final long serialVersionUID = -5460708106909626233L;
    private transient CloseableIterator<T> lastIterator;

    public LazyForeignCollection(Dao<T, ID> dao, Object parent, Object parentId, FieldType foreignFieldType, String orderColumn, boolean orderAscending) {
        super(dao, parent, parentId, foreignFieldType, orderColumn, orderAscending);
    }

    public CloseableIterator<T> iterator() {
        return closeableIterator(-1);
    }

    public CloseableIterator<T> iterator(int flags) {
        return closeableIterator(flags);
    }

    public CloseableIterator<T> closeableIterator() {
        return closeableIterator(-1);
    }

    public CloseableIterator<T> closeableIterator(int flags) {
        try {
            return iteratorThrow(flags);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not build lazy iterator for " + this.dao.getDataClass(), e);
        }
    }

    public CloseableIterator<T> iteratorThrow() {
        return iteratorThrow(-1);
    }

    public CloseableIterator<T> iteratorThrow(int flags) {
        this.lastIterator = seperateIteratorThrow(flags);
        return this.lastIterator;
    }

    public CloseableWrappedIterable<T> getWrappedIterable() {
        return getWrappedIterable(-1);
    }

    public CloseableWrappedIterable<T> getWrappedIterable(final int flags) {
        return new CloseableWrappedIterableImpl(new CloseableIterable<T>() {
            public CloseableIterator<T> iterator() {
                return closeableIterator();
            }

            public CloseableIterator<T> closeableIterator() {
                try {
                    return LazyForeignCollection.this.seperateIteratorThrow(flags);
                } catch (Exception e) {
                    throw new IllegalStateException("Could not build lazy iterator for " + LazyForeignCollection.this.dao.getDataClass(), e);
                }
            }
        });
    }

    public void closeLastIterator() {
        if (this.lastIterator != null) {
            this.lastIterator.close();
            this.lastIterator = null;
        }
    }

    public boolean isEager() {
        return false;
    }

    public int size() {
        CloseableIterator iterator = iterator();
        int sizeC = 0;
        while (iterator.hasNext()) {
            try {
                iterator.moveToNext();
                sizeC++;
            } finally {
                try {
                    iterator.close();
                } catch (SQLException e) {
                }
            }
        }
        return sizeC;
    }

    public boolean isEmpty() {
        CloseableIterator iterator = iterator();
        try {
            return !iterator.hasNext();
        } finally {
            try {
                iterator.close();
            } catch (SQLException e) {
            }
        }
    }

    public boolean contains(Object obj) {
        CloseableIterator iterator = iterator();
        do {
            try {
                if (!iterator.hasNext()) {
                    try {
                        iterator.close();
                    } catch (SQLException e) {
                    }
                    return false;
                }
            } finally {
                try {
                    iterator.close();
                } catch (SQLException e2) {
                }
            }
        } while (!iterator.next().equals(obj));
        return true;
    }

    public boolean containsAll(Collection<?> collection) {
        Set leftOvers = new HashSet(collection);
        CloseableIterator iterator = iterator();
        while (iterator.hasNext()) {
            try {
                leftOvers.remove(iterator.next());
            } finally {
                try {
                    iterator.close();
                } catch (SQLException e) {
                }
            }
        }
        return leftOvers.isEmpty();
    }

    public boolean remove(Object data) {
        CloseableIterator iterator = iterator();
        do {
            try {
                if (!iterator.hasNext()) {
                    try {
                        iterator.close();
                    } catch (SQLException e) {
                    }
                    return false;
                }
            } finally {
                try {
                    iterator.close();
                } catch (SQLException e2) {
                }
            }
        } while (!iterator.next().equals(data));
        iterator.remove();
        return true;
    }

    public boolean removeAll(Collection<?> collection) {
        boolean changed = false;
        CloseableIterator iterator = iterator();
        while (iterator.hasNext()) {
            try {
                if (collection.contains(iterator.next())) {
                    iterator.remove();
                    changed = true;
                }
            } finally {
                try {
                    iterator.close();
                } catch (SQLException e) {
                }
            }
        }
        return changed;
    }

    public Object[] toArray() {
        List items = new ArrayList();
        CloseableIterator iterator = iterator();
        while (iterator.hasNext()) {
            try {
                items.add(iterator.next());
            } finally {
                try {
                    iterator.close();
                } catch (SQLException e) {
                }
            }
        }
        return items.toArray();
    }

    public <E> E[] toArray(E[] array) {
        List items;
        List items2 = null;
        int itemC = 0;
        CloseableIterator iterator = iterator();
        while (true) {
            try {
                items = items2;
                if (iterator.hasNext()) {
                    Object castData = iterator.next();
                    if (itemC >= array.length) {
                        if (items == null) {
                            items2 = new ArrayList();
                            try {
                                for (Object arrayData : array) {
                                    items2.add(arrayData);
                                }
                            } catch (Throwable th) {
                                th = th;
                                try {
                                    iterator.close();
                                } catch (SQLException e) {
                                }
                                throw th;
                            }
                        } else {
                            items2 = items;
                        }
                        items2.add(castData);
                    } else {
                        array[itemC] = castData;
                        items2 = items;
                    }
                    itemC++;
                } else {
                    try {
                        break;
                    } catch (SQLException e2) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                List list = items;
                iterator.close();
                throw th;
            }
        }
        iterator.close();
        if (items != null) {
            return items.toArray(array);
        }
        if (itemC >= array.length - 1) {
            return array;
        }
        array[itemC] = null;
        return array;
    }

    public int updateAll() {
        throw new UnsupportedOperationException("Cannot call updateAll() on a lazy collection.");
    }

    public int refreshAll() {
        throw new UnsupportedOperationException("Cannot call updateAll() on a lazy collection.");
    }

    public int refreshCollection() {
        return 0;
    }

    public boolean equals(Object other) {
        return super.equals(other);
    }

    public int hashCode() {
        return super.hashCode();
    }

    /* access modifiers changed from: private */
    public CloseableIterator<T> seperateIteratorThrow(int flags) {
        if (this.dao != null) {
            return this.dao.iterator(getPreparedQuery(), flags);
        }
        throw new IllegalStateException("Internal DAO object is null.  Lazy collections cannot be used if they have been deserialized.");
    }
}
