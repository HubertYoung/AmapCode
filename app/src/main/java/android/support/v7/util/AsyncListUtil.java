package android.support.v7.util;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.v7.util.ThreadUtil.BackgroundCallback;
import android.support.v7.util.ThreadUtil.MainThreadCallback;
import android.support.v7.util.TileList.Tile;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

public class AsyncListUtil<T> {
    final Class<T> a;
    final int b;
    final DataCallback<T> c;
    final TileList<T> d;
    final MainThreadCallback<T> e;
    final BackgroundCallback<T> f;
    final int[] g;
    final int[] h;
    final int[] i;
    int j;
    int k;
    /* access modifiers changed from: private */
    public boolean l;
    private int m;
    /* access modifiers changed from: private */
    public int n;
    /* access modifiers changed from: private */
    public final SparseIntArray o;

    /* renamed from: android.support.v7.util.AsyncListUtil$1 reason: invalid class name */
    class AnonymousClass1 implements MainThreadCallback<T> {
        final /* synthetic */ AsyncListUtil a;

        public final void a(int i, int i2) {
            if (a(i)) {
                this.a.n = i2;
                this.a.j = this.a.k;
                for (int i3 = 0; i3 < this.a.d.a.size(); i3++) {
                    this.a.f.a(this.a.d.a.valueAt(i3));
                }
                this.a.d.a.clear();
                this.a.l = false;
                AsyncListUtil.b(this.a);
            }
        }

        public final void a(int i, Tile<T> tile) {
            Tile tile2;
            if (!a(i)) {
                this.a.f.a(tile);
                return;
            }
            TileList<T> tileList = this.a.d;
            int indexOfKey = tileList.a.indexOfKey(tile.b);
            if (indexOfKey < 0) {
                tileList.a.put(tile.b, tile);
                tile2 = null;
            } else {
                Tile<T> valueAt = tileList.a.valueAt(indexOfKey);
                tileList.a.setValueAt(indexOfKey, tile);
                if (tileList.b == valueAt) {
                    tileList.b = tile;
                }
                tile2 = valueAt;
            }
            if (tile2 != null) {
                new StringBuilder("duplicate tile @").append(tile2.b);
                this.a.f.a(tile2);
            }
            int i2 = tile.b + tile.c;
            int i3 = 0;
            while (i3 < this.a.o.size()) {
                int keyAt = this.a.o.keyAt(i3);
                if (tile.b > keyAt || keyAt >= i2) {
                    i3++;
                } else {
                    this.a.o.removeAt(i3);
                }
            }
        }

        public final void b(int i, int i2) {
            if (a(i)) {
                TileList<T> tileList = this.a.d;
                Tile<T> tile = tileList.a.get(i2);
                if (tileList.b == tile) {
                    tileList.b = null;
                }
                tileList.a.delete(i2);
                if (tile != null) {
                    this.a.f.a(tile);
                }
            }
        }

        private boolean a(int i) {
            return i == this.a.k;
        }
    }

    /* renamed from: android.support.v7.util.AsyncListUtil$2 reason: invalid class name */
    class AnonymousClass2 implements BackgroundCallback<T> {
        final SparseBooleanArray a;
        final /* synthetic */ AsyncListUtil b;
        private Tile<T> c;
        private int d;
        private int e;
        private int f;
        private int g;

        public final void a(int i) {
            this.d = i;
            this.a.clear();
            this.e = this.b.c.a();
            this.b.e.a(this.d, this.e);
        }

        public final void a(int i, int i2, int i3, int i4, int i5) {
            if (i <= i2) {
                int b2 = b(i);
                int b3 = b(i2);
                this.f = b(i3);
                this.g = b(i4);
                if (i5 == 1) {
                    a(this.f, b3, i5, true);
                    a(b3 + this.b.b, this.g, i5, false);
                    return;
                }
                a(b2, this.g, i5, false);
                a(this.f, b2 - this.b.b, i5, true);
            }
        }

        private int b(int i) {
            return i - (i % this.b.b);
        }

        private void a(int i, int i2, int i3, boolean z) {
            int i4 = i;
            while (i4 <= i2) {
                this.b.f.a(z ? (i2 + i) - i4 : i4, i3);
                i4 += this.b.b;
            }
        }

        public final void a(Tile<T> tile) {
            tile.d = this.c;
            this.c = tile;
        }

        private void c(int i) {
            this.a.delete(i);
            this.b.e.b(this.d, i);
        }

        public final void a(int i, int i2) {
            Tile<T> tile;
            if (!this.a.get(i)) {
                if (this.c != null) {
                    tile = this.c;
                    this.c = this.c.d;
                } else {
                    tile = new Tile<>(this.b.a, this.b.b);
                }
                tile.b = i;
                tile.c = Math.min(this.b.b, this.e - tile.b);
                while (this.a.size() >= 10) {
                    int keyAt = this.a.keyAt(0);
                    int keyAt2 = this.a.keyAt(this.a.size() - 1);
                    int i3 = this.f - keyAt;
                    int i4 = keyAt2 - this.g;
                    if (i3 <= 0 || (i3 < i4 && i2 != 2)) {
                        if (i4 <= 0 || (i3 >= i4 && i2 != 1)) {
                            break;
                        }
                        c(keyAt2);
                    } else {
                        c(keyAt);
                    }
                }
                this.a.put(tile.b, true);
                this.b.e.a(this.d, tile);
            }
        }
    }

    public static abstract class DataCallback<T> {
        @WorkerThread
        public abstract int a();
    }

    public static abstract class ViewCallback {
        @UiThread
        public static void a(int[] iArr, int[] iArr2, int i) {
            int i2 = (iArr[1] - iArr[0]) + 1;
            int i3 = i2 / 2;
            iArr2[0] = iArr[0] - (i == 1 ? i2 : i3);
            int i4 = iArr[1];
            if (i != 2) {
                i2 = i3;
            }
            iArr2[1] = i4 + i2;
        }
    }

    static /* synthetic */ void b(AsyncListUtil asyncListUtil) {
        if (asyncListUtil.g[0] <= asyncListUtil.g[1] && asyncListUtil.g[0] >= 0 && asyncListUtil.g[1] < asyncListUtil.n) {
            if (!asyncListUtil.l) {
                asyncListUtil.m = 0;
            } else if (asyncListUtil.g[0] > asyncListUtil.h[1] || asyncListUtil.h[0] > asyncListUtil.g[1]) {
                asyncListUtil.m = 0;
            } else if (asyncListUtil.g[0] < asyncListUtil.h[0]) {
                asyncListUtil.m = 1;
            } else if (asyncListUtil.g[0] > asyncListUtil.h[0]) {
                asyncListUtil.m = 2;
            }
            asyncListUtil.h[0] = asyncListUtil.g[0];
            asyncListUtil.h[1] = asyncListUtil.g[1];
            ViewCallback.a(asyncListUtil.g, asyncListUtil.i, asyncListUtil.m);
            asyncListUtil.i[0] = Math.min(asyncListUtil.g[0], Math.max(asyncListUtil.i[0], 0));
            asyncListUtil.i[1] = Math.max(asyncListUtil.g[1], Math.min(asyncListUtil.i[1], asyncListUtil.n - 1));
            asyncListUtil.f.a(asyncListUtil.g[0], asyncListUtil.g[1], asyncListUtil.i[0], asyncListUtil.i[1], asyncListUtil.m);
        }
    }
}
