package defpackage;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* renamed from: bji reason: default package */
/* compiled from: Action */
public abstract class bji<T> {
    public final ImageLoader a;
    public final bjz b;
    final WeakReference<T> c;
    final boolean d;
    public final int e;
    final int f;
    final int g;
    final Drawable h;
    public final String i;
    public final Object j;
    public final boolean k;
    public boolean l;
    public boolean m;

    /* renamed from: bji$a */
    /* compiled from: Action */
    public static class a<M> extends WeakReference<M> {
        public final bji a;

        public a(bji bji, M m, ReferenceQueue<? super M> referenceQueue) {
            super(m, referenceQueue);
            this.a = bji;
        }
    }

    public abstract void a();

    public abstract void a(Bitmap bitmap, LoadedFrom loadedFrom);

    bji(ImageLoader imageLoader, T t, bjz bjz, int i2, int i3, int i4, Drawable drawable, String str, Object obj, boolean z, boolean z2) {
        WeakReference<T> weakReference;
        this.a = imageLoader;
        this.b = bjz;
        if (t == null) {
            weakReference = null;
        } else {
            weakReference = new a<>(this, t, imageLoader.j);
        }
        this.c = weakReference;
        this.e = i2;
        this.f = i3;
        this.d = z;
        this.g = i4;
        this.h = drawable;
        this.i = str;
        this.j = obj == 0 ? this : obj;
        this.k = z2;
    }

    public void b() {
        this.m = true;
    }

    public final T c() {
        if (this.c == null) {
            return null;
        }
        return this.c.get();
    }
}
