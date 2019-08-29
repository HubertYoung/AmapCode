package android.support.v7.util;

import android.support.v7.util.TileList.Tile;

interface ThreadUtil<T> {

    public interface BackgroundCallback<T> {
        void a(int i);

        void a(int i, int i2);

        void a(int i, int i2, int i3, int i4, int i5);

        void a(Tile<T> tile);
    }

    public interface MainThreadCallback<T> {
        void a(int i, int i2);

        void a(int i, Tile<T> tile);

        void b(int i, int i2);
    }
}
