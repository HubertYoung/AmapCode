package com.autonavi.minimap.ajx3.layout;

import android.text.Layout;
import java.util.concurrent.ConcurrentHashMap;

public class TextLayoutPools {
    private static ConcurrentHashMap<Long, Layout> sLayouts = new ConcurrentHashMap<>();

    public static void produceLayout(long j, Layout layout) {
        sLayouts.put(Long.valueOf(j), layout);
    }

    public static Layout comsumeLayout(long j) {
        return sLayouts.remove(Long.valueOf(j));
    }
}
