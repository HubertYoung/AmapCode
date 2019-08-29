package com.squareup.picasso;

import android.graphics.Bitmap;

public interface Cache {
    public static final Cache NONE = new Cache() {
        public final void clear() {
        }

        public final void clearKeyUri(String str) {
        }

        public final Bitmap get(String str) {
            return null;
        }

        public final int maxSize() {
            return 0;
        }

        public final void set(String str, Bitmap bitmap) {
        }

        public final int size() {
            return 0;
        }
    };

    void clear();

    void clearKeyUri(String str);

    Bitmap get(String str);

    int maxSize();

    void set(String str, Bitmap bitmap);

    int size();
}
