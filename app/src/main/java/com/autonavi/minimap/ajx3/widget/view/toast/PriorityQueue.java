package com.autonavi.minimap.ajx3.widget.view.toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

class PriorityQueue<E> extends AbstractQueue<E> implements Serializable {
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    private static final long serialVersionUID = 156525540690621702L;
    private final Comparator<? super E> comparator;
    transient int modCount;
    transient Object[] queue;
    int size;

    final class Itr implements Iterator<E> {
        private int cursor;
        private int expectedModCount;
        private ArrayDeque<E> forgetMeNot;
        private int lastRet;
        private E lastRetElt;

        private Itr() {
            this.lastRet = -1;
            this.expectedModCount = PriorityQueue.this.modCount;
        }

        public final boolean hasNext() {
            return this.cursor < PriorityQueue.this.size || (this.forgetMeNot != null && !this.forgetMeNot.isEmpty());
        }

        public final E next() {
            if (this.expectedModCount != PriorityQueue.this.modCount) {
                throw new ConcurrentModificationException();
            } else if (this.cursor < PriorityQueue.this.size) {
                E[] eArr = PriorityQueue.this.queue;
                int i = this.cursor;
                this.cursor = i + 1;
                this.lastRet = i;
                return eArr[i];
            } else {
                if (this.forgetMeNot != null) {
                    this.lastRet = -1;
                    this.lastRetElt = this.forgetMeNot.poll();
                    if (this.lastRetElt != null) {
                        return this.lastRetElt;
                    }
                }
                throw new NoSuchElementException();
            }
        }

        public final void remove() {
            if (this.expectedModCount != PriorityQueue.this.modCount) {
                throw new ConcurrentModificationException();
            }
            if (this.lastRet != -1) {
                Object removeAt = PriorityQueue.this.removeAt(this.lastRet);
                this.lastRet = -1;
                if (removeAt == null) {
                    this.cursor--;
                } else {
                    if (this.forgetMeNot == null) {
                        this.forgetMeNot = new ArrayDeque<>();
                    }
                    this.forgetMeNot.add(removeAt);
                }
            } else if (this.lastRetElt != null) {
                PriorityQueue.this.removeEq(this.lastRetElt);
                this.lastRetElt = null;
            } else {
                throw new IllegalStateException();
            }
            this.expectedModCount = PriorityQueue.this.modCount;
        }
    }

    public E get(int i) {
        if (i < 0 || i >= this.queue.length) {
            return null;
        }
        return this.queue[i];
    }

    public PriorityQueue() {
        this(11, null);
    }

    public PriorityQueue(int i) {
        this(i, null);
    }

    public PriorityQueue(Comparator<? super E> comparator2) {
        this(11, comparator2);
    }

    public PriorityQueue(int i, Comparator<? super E> comparator2) {
        if (i <= 0) {
            throw new IllegalArgumentException();
        }
        this.queue = new Object[i];
        this.comparator = comparator2;
    }

    public PriorityQueue(Collection<? extends E> collection) {
        if (collection instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet) collection;
            this.comparator = sortedSet.comparator();
            initElementsFromCollection(sortedSet);
        } else if (collection instanceof PriorityQueue) {
            PriorityQueue priorityQueue = (PriorityQueue) collection;
            this.comparator = priorityQueue.comparator();
            initFromPriorityQueue(priorityQueue);
        } else {
            this.comparator = null;
            initFromCollection(collection);
        }
    }

    public PriorityQueue(PriorityQueue<? extends E> priorityQueue) {
        this.comparator = priorityQueue.comparator();
        initFromPriorityQueue(priorityQueue);
    }

    public PriorityQueue(SortedSet<? extends E> sortedSet) {
        this.comparator = sortedSet.comparator();
        initElementsFromCollection(sortedSet);
    }

    private void initFromPriorityQueue(PriorityQueue<? extends E> priorityQueue) {
        if (priorityQueue.getClass() == PriorityQueue.class) {
            this.queue = priorityQueue.toArray();
            this.size = priorityQueue.size();
            return;
        }
        initFromCollection(priorityQueue);
    }

    private void initElementsFromCollection(Collection<? extends E> collection) {
        Object[] array = collection.toArray();
        if (array.getClass() != Object[].class) {
            array = Arrays.copyOf(array, array.length, Object[].class);
        }
        if (array.length == 1 || this.comparator != null) {
            for (Object obj : array) {
                if (obj == null) {
                    throw new NullPointerException();
                }
            }
        }
        this.queue = array;
        this.size = array.length;
    }

    private void initFromCollection(Collection<? extends E> collection) {
        initElementsFromCollection(collection);
        heapify();
    }

    private void grow(int i) {
        int length = this.queue.length;
        int i2 = length + (length < 64 ? length + 2 : length >> 1);
        if (i2 - MAX_ARRAY_SIZE > 0) {
            i2 = hugeCapacity(i);
        }
        this.queue = Arrays.copyOf(this.queue, i2);
    }

    private static int hugeCapacity(int i) {
        if (i < 0) {
            throw new OutOfMemoryError();
        } else if (i > MAX_ARRAY_SIZE) {
            return Integer.MAX_VALUE;
        } else {
            return MAX_ARRAY_SIZE;
        }
    }

    public boolean add(E e) {
        return offer(e);
    }

    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        this.modCount++;
        int i = this.size;
        if (i >= this.queue.length) {
            grow(i + 1);
        }
        this.size = i + 1;
        if (i == 0) {
            this.queue[0] = e;
        } else {
            siftUp(i, e);
        }
        return true;
    }

    public E peek() {
        if (this.size == 0) {
            return null;
        }
        return this.queue[0];
    }

    private int indexOf(Object obj) {
        if (obj != null) {
            for (int i = 0; i < this.size; i++) {
                if (obj.equals(this.queue[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean remove(Object obj) {
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return false;
        }
        removeAt(indexOf);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean removeEq(Object obj) {
        for (int i = 0; i < this.size; i++) {
            if (obj == this.queue[i]) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    public Object[] toArray() {
        return Arrays.copyOf(this.queue, this.size);
    }

    public <T> T[] toArray(T[] tArr) {
        int i = this.size;
        if (tArr.length < i) {
            return Arrays.copyOf(this.queue, i, tArr.getClass());
        }
        System.arraycopy(this.queue, 0, tArr, 0, i);
        if (tArr.length > i) {
            tArr[i] = null;
        }
        return tArr;
    }

    public Iterator<E> iterator() {
        return new Itr();
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        this.modCount++;
        for (int i = 0; i < this.size; i++) {
            this.queue[i] = null;
        }
        this.size = 0;
    }

    public E poll() {
        if (this.size == 0) {
            return null;
        }
        int i = this.size - 1;
        this.size = i;
        this.modCount++;
        E e = this.queue[0];
        Object obj = this.queue[i];
        this.queue[i] = null;
        if (i != 0) {
            siftDown(0, obj);
        }
        return e;
    }

    /* access modifiers changed from: 0000 */
    public E removeAt(int i) {
        this.modCount++;
        int i2 = this.size - 1;
        this.size = i2;
        if (i2 == i) {
            this.queue[i] = null;
        } else {
            E e = this.queue[i2];
            this.queue[i2] = null;
            siftDown(i, e);
            if (this.queue[i] == e) {
                siftUp(i, e);
                if (this.queue[i] != e) {
                    return e;
                }
            }
        }
        return null;
    }

    private void siftUp(int i, E e) {
        if (this.comparator != null) {
            siftUpUsingComparator(i, e);
        } else {
            siftUpComparable(i, e);
        }
    }

    private void siftUpComparable(int i, E e) {
        Comparable comparable = (Comparable) e;
        while (i > 0) {
            int i2 = (i - 1) >>> 1;
            Object obj = this.queue[i2];
            if (comparable.compareTo(obj) >= 0) {
                break;
            }
            this.queue[i] = obj;
            i = i2;
        }
        this.queue[i] = comparable;
    }

    private void siftUpUsingComparator(int i, E e) {
        while (i > 0) {
            int i2 = (i - 1) >>> 1;
            Object obj = this.queue[i2];
            if (this.comparator.compare(e, obj) >= 0) {
                break;
            }
            this.queue[i] = obj;
            i = i2;
        }
        this.queue[i] = e;
    }

    private void siftDown(int i, E e) {
        if (this.comparator != null) {
            siftDownUsingComparator(i, e);
        } else {
            siftDownComparable(i, e);
        }
    }

    private void siftDownComparable(int i, E e) {
        Comparable comparable = (Comparable) e;
        int i2 = this.size >>> 1;
        while (i < i2) {
            int i3 = (i << 1) + 1;
            Object obj = this.queue[i3];
            int i4 = i3 + 1;
            if (i4 < this.size && ((Comparable) obj).compareTo(this.queue[i4]) > 0) {
                obj = this.queue[i4];
                i3 = i4;
            }
            if (comparable.compareTo(obj) <= 0) {
                break;
            }
            this.queue[i] = obj;
            i = i3;
        }
        this.queue[i] = comparable;
    }

    private void siftDownUsingComparator(int i, E e) {
        int i2 = this.size >>> 1;
        while (i < i2) {
            int i3 = (i << 1) + 1;
            Object obj = this.queue[i3];
            int i4 = i3 + 1;
            if (i4 < this.size && this.comparator.compare(obj, this.queue[i4]) > 0) {
                obj = this.queue[i4];
                i3 = i4;
            }
            if (this.comparator.compare(e, obj) <= 0) {
                break;
            }
            this.queue[i] = obj;
            i = i3;
        }
        this.queue[i] = e;
    }

    private void heapify() {
        for (int i = (this.size >>> 1) - 1; i >= 0; i--) {
            siftDown(i, this.queue[i]);
        }
    }

    public Comparator comparator() {
        return this.comparator;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(Math.max(2, this.size + 1));
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeObject(this.queue[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        objectInputStream.readInt();
        this.queue = new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            this.queue[i] = objectInputStream.readObject();
        }
        heapify();
    }
}
