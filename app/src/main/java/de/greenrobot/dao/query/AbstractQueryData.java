package de.greenrobot.dao.query;

import android.os.Process;
import android.util.SparseArray;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.AbstractQuery;
import java.lang.ref.WeakReference;

abstract class AbstractQueryData<T, Q extends AbstractQuery<T>> {
    final AbstractDao<T, ?> dao;
    final String[] initialValues;
    final SparseArray<WeakReference<Q>> queriesForThreads = new SparseArray<>();
    final String sql;

    /* access modifiers changed from: protected */
    public abstract Q createQuery();

    AbstractQueryData(AbstractDao<T, ?> abstractDao, String str, String[] strArr) {
        this.dao = abstractDao;
        this.sql = str;
        this.initialValues = strArr;
    }

    /* access modifiers changed from: 0000 */
    public Q forCurrentThread(Q q) {
        if (Thread.currentThread() != q.ownerThread) {
            return forCurrentThread();
        }
        System.arraycopy(this.initialValues, 0, q.parameters, 0, this.initialValues.length);
        return q;
    }

    /* access modifiers changed from: 0000 */
    public Q forCurrentThread() {
        Q q;
        int myTid = Process.myTid();
        synchronized (this.queriesForThreads) {
            try {
                WeakReference weakReference = this.queriesForThreads.get(myTid);
                q = weakReference != null ? (AbstractQuery) weakReference.get() : null;
                if (q == null) {
                    gc();
                    q = createQuery();
                    this.queriesForThreads.put(myTid, new WeakReference(q));
                } else {
                    System.arraycopy(this.initialValues, 0, q.parameters, 0, this.initialValues.length);
                }
            }
        }
        return q;
    }

    /* access modifiers changed from: 0000 */
    public void gc() {
        synchronized (this.queriesForThreads) {
            for (int size = this.queriesForThreads.size() - 1; size >= 0; size--) {
                if (this.queriesForThreads.valueAt(size).get() == null) {
                    this.queriesForThreads.remove(this.queriesForThreads.keyAt(size));
                }
            }
        }
    }
}
