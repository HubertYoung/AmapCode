package android.support.v7.util;

import android.util.SparseArray;
import java.lang.reflect.Array;

class TileList<T> {
    final SparseArray<Tile<T>> a;
    Tile<T> b;

    public static class Tile<T> {
        public final T[] a;
        public int b;
        public int c;
        Tile<T> d;

        public Tile(Class<T> cls, int i) {
            this.a = (Object[]) Array.newInstance(cls, i);
        }
    }
}
