package com.amap.location.e.d;

import com.amap.location.common.model.AmapLoc;
import com.amap.location.g.d.a;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/* compiled from: MobileHotspotDetector */
public class d {
    private a a;
    private long b = -1;
    private boolean c = false;
    private HashSet<Long> d = new HashSet<>();
    private LinkedHashMap<Long, AmapLoc> e = new LinkedHashMap<Long, AmapLoc>(10) {
        /* access modifiers changed from: protected */
        public boolean removeEldestEntry(Entry<Long, AmapLoc> entry) {
            return size() > 10;
        }
    };

    public d(a aVar) {
        this.a = aVar;
    }
}
