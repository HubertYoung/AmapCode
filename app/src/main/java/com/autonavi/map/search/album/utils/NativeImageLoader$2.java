package com.autonavi.map.search.album.utils;

import android.graphics.Bitmap;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class NativeImageLoader$2 extends LinkedHashMap<String, SoftReference<Bitmap>> {
    private static final long serialVersionUID = 1;
    final /* synthetic */ bvl a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public NativeImageLoader$2(bvl bvl) {
        // this.a = bvl;
        super(20, 0.75f, true);
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> entry) {
        return size() > 20;
    }
}
