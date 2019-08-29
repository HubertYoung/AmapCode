package com.j256.ormlite.dao;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.DatabaseResults;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EagerForeignCollection<T, ID> extends BaseForeignCollection<T, ID> implements CloseableWrappedIterable<T>, ForeignCollection<T>, Serializable {
    private static final long serialVersionUID = -2523335606983317721L;
    /* access modifiers changed from: private */
    public List<T> results;

    public EagerForeignCollection(Dao<T, ID> dao, Object parent, Object parentId, FieldType foreignFieldType, String orderColumn, boolean orderAscending) {
        super(dao, parent, parentId, foreignFieldType, orderColumn, orderAscending);
        if (parentId == null) {
            this.results = new ArrayList();
        } else {
            this.results = dao.query(getPreparedQuery());
        }
    }

    public CloseableIterator<T> iterator() {
        return iteratorThrow(-1);
    }

    public CloseableIterator<T> iterator(int flags) {
        return iteratorThrow(flags);
    }

    public CloseableIterator<T> closeableIterator() {
        return iteratorThrow(-1);
    }

    public CloseableIterator<T> closeableIterator(int flags) {
        return iteratorThrow(-1);
    }

    public CloseableIterator<T> iteratorThrow() {
        return iteratorThrow(-1);
    }

    public CloseableIterator<T> iteratorThrow(int flags) {
        return new CloseableIterator<T>() {
            private int offset = -1;

            public boolean hasNext() {
                return this.offset + 1 < EagerForeignCollection.this.results.size();
            }

            public T first() {
                this.offset = 0;
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return EagerForeignCollection.this.results.get(0);
            }

            public T next() {
                this.offset++;
                return EagerForeignCollection.this.results.get(this.offset);
            }

            public T nextThrow() {
                this.offset++;
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return EagerForeignCollection.this.results.get(this.offset);
            }

            public T current() {
                if (this.offset < 0) {
                    this.offset = 0;
                }
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return EagerForeignCollection.this.results.get(this.offset);
            }

            public T previous() {
                this.offset--;
                if (this.offset < 0 || this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return EagerForeignCollection.this.results.get(this.offset);
            }

            public T moveRelative(int relativeOffset) {
                this.offset += relativeOffset;
                if (this.offset < 0 || this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return EagerForeignCollection.this.results.get(this.offset);
            }

            public void remove() {
                if (this.offset < 0) {
                    throw new IllegalStateException("next() must be called before remove()");
                } else if (this.offset >= EagerForeignCollection.this.results.size()) {
                    throw new IllegalStateException("current results position (" + this.offset + ") is out of bounds");
                } else {
                    Object removed = EagerForeignCollection.this.results.remove(this.offset);
                    this.offset--;
                    if (EagerForeignCollection.this.dao != null) {
                        try {
                            EagerForeignCollection.this.dao.delete(removed);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            public void close() {
            }

            public void closeQuietly() {
            }

            public DatabaseResults getRawResults() {
                return null;
            }

            public void moveToNext() {
                this.offset++;
            }
        };
    }

    public CloseableWrappedIterable<T> getWrappedIterable() {
        return this;
    }

    public CloseableWrappedIterable<T> getWrappedIterable(int flags) {
        return this;
    }

    public void close() {
    }

    public void closeLastIterator() {
    }

    public boolean isEager() {
        return true;
    }

    public int size() {
        return this.results.size();
    }

    public boolean isEmpty() {
        return this.results.isEmpty();
    }

    public boolean contains(Object o) {
        return this.results.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return this.results.containsAll(c);
    }

    public Object[] toArray() {
        return this.results.toArray();
    }

    public <E> E[] toArray(E[] array) {
        return this.results.toArray(array);
    }

    public boolean add(T data) {
        if (this.results.add(data)) {
            return super.add(data);
        }
        return false;
    }

    public boolean addAll(Collection<? extends T> collection) {
        if (this.results.addAll(collection)) {
            return super.addAll(collection);
        }
        return false;
    }

    public boolean remove(Object data) {
        if (!this.results.remove(data) || this.dao == null) {
            return false;
        }
        try {
            if (this.dao.delete(data) != 1) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not delete data element from dao", e);
        }
    }

    public boolean removeAll(Collection<?> collection) {
        boolean changed = false;
        for (Object data : collection) {
            if (remove(data)) {
                changed = true;
            }
        }
        return changed;
    }

    public boolean retainAll(Collection<?> collection) {
        return super.retainAll(collection);
    }

    public int updateAll() {
        int updatedC = 0;
        for (T data : this.results) {
            updatedC += this.dao.update(data);
        }
        return updatedC;
    }

    public int refreshAll() {
        int updatedC = 0;
        for (T data : this.results) {
            updatedC += this.dao.refresh(data);
        }
        return updatedC;
    }

    public int refreshCollection() {
        this.results = this.dao.query(getPreparedQuery());
        return this.results.size();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof EagerForeignCollection)) {
            return false;
        }
        return this.results.equals(((EagerForeignCollection) obj).results);
    }

    public int hashCode() {
        return this.results.hashCode();
    }
}
