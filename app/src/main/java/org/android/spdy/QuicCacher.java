package org.android.spdy;

import android.content.Context;

public interface QuicCacher {
    void init(Context context);

    byte[] load(String str);

    boolean store(String str, String str2);
}
