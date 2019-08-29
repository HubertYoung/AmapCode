package com.amap.location.common.a;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.amap.location.common.a.a.C0014a;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: SaveController */
public class a<Item extends C0014a> {
    private Callback a = new Callback() {
        public boolean handleMessage(Message message) {
            try {
                return a.this.a(message);
            } catch (Exception unused) {
                return true;
            }
        }
    };
    private int b = 0;
    private ReentrantReadWriteLock c = new ReentrantReadWriteLock();
    private b<Item> d;
    private Handler e;
    private ArrayList<Item> f = new ArrayList<>();
    private long g;
    private boolean h;

    /* renamed from: com.amap.location.common.a.a$a reason: collision with other inner class name */
    /* compiled from: SaveController */
    public interface C0014a {
        long a();
    }

    /* compiled from: SaveController */
    public interface b<Item extends C0014a> {
        void a();

        void a(ArrayList<Item> arrayList);

        boolean a(long j);

        void b();

        long c();

        long d();
    }

    public void a(b<Item> bVar, Looper looper) {
        if (bVar == null || looper == null) {
            throw new RuntimeException("business 和 looper 都不能为 null");
        }
        try {
            this.c.writeLock().lock();
            if (this.b == 0) {
                this.d = bVar;
                this.e = new Handler(looper, this.a);
                if (Looper.myLooper() == looper) {
                    this.d.a();
                } else {
                    this.e.sendEmptyMessage(4);
                }
                this.b = 1;
            }
        } finally {
            this.c.writeLock().unlock();
        }
    }

    public void a() {
        this.c.writeLock().lock();
        try {
            if (this.b == 1) {
                this.b = 2;
                this.e.removeCallbacksAndMessages(null);
                if (this.e.getLooper() == Looper.myLooper()) {
                    c();
                } else {
                    this.e.sendEmptyMessage(3);
                }
                this.e = null;
            }
        } finally {
            this.c.writeLock().unlock();
        }
    }

    public void a(Item item) {
        try {
            this.c.readLock().lock();
            if (this.e != null) {
                if (this.e.getLooper() == Looper.myLooper()) {
                    b(item);
                } else {
                    this.e.obtainMessage(1, item).sendToTarget();
                }
            }
        } finally {
            this.c.readLock().unlock();
        }
    }

    public void b() {
        try {
            this.c.readLock().lock();
            if (this.e != null) {
                if (this.e.getLooper() == Looper.myLooper()) {
                    this.e.removeMessages(2);
                    d();
                } else {
                    this.e.removeMessages(2);
                    this.e.obtainMessage(2).sendToTarget();
                }
            }
        } finally {
            this.c.readLock().unlock();
        }
    }

    /* access modifiers changed from: private */
    public boolean a(Message message) {
        switch (message.what) {
            case 1:
                b((C0014a) message.obj);
                break;
            case 2:
                d();
                break;
            case 3:
                c();
                break;
            case 4:
                this.d.a();
                break;
        }
        return true;
    }

    private void c() {
        d();
        this.d.b();
        this.d = null;
    }

    /* JADX INFO: finally extract failed */
    private void b(Item item) {
        this.f.add(item);
        this.g += item.a();
        if (this.g >= this.d.c()) {
            try {
                this.c.readLock().lock();
                if (this.e != null) {
                    this.e.removeMessages(2);
                }
                this.c.readLock().unlock();
                d();
            } catch (Throwable th) {
                this.c.readLock().unlock();
                throw th;
            }
        } else {
            e();
        }
    }

    private void d() {
        this.h = false;
        if (this.d.a(this.g)) {
            this.d.a(this.f);
        }
        this.f.clear();
        this.g = 0;
    }

    /* JADX INFO: finally extract failed */
    private void e() {
        if (!this.h) {
            try {
                this.c.readLock().lock();
                if (this.e != null) {
                    this.e.sendEmptyMessageDelayed(2, this.d.d());
                }
                this.c.readLock().unlock();
                this.h = true;
            } catch (Throwable th) {
                this.c.readLock().unlock();
                throw th;
            }
        }
    }
}
