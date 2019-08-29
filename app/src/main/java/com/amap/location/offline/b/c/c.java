package com.amap.location.offline.b.c;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: OfflineRequest */
public class c {
    public byte a;
    public int b;
    public List<Long> c;
    public List<String> d;
    public HashMap<String, String> e;
    public byte[] f;

    public c(byte b2, List<Long> list, List<String> list2) {
        this.a = b2;
        this.c = list;
        this.d = list2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("OfflineRequest{mType=");
        sb.append(this.a);
        sb.append(", mWifiList=");
        sb.append(this.c);
        sb.append(", mCellList=");
        sb.append(this.d);
        sb.append(", mHeaders=");
        sb.append(this.e);
        sb.append(", mBody=");
        sb.append(Arrays.toString(this.f));
        sb.append('}');
        return sb.toString();
    }
}
