package com.amap.location.offline.b.a;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.location.common.model.AmapLoc;
import java.util.HashMap;

/* compiled from: OffWifiInfo */
public class c {
    public int a = 0;
    public HashMap<Long, b> b = new HashMap<>();
    public int c = 0;
    public StringBuilder d = new StringBuilder();
    public StringBuilder e = new StringBuilder();
    public AmapLoc f;

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append(this.a);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.c);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        double d2 = 0.0d;
        sb.append(this.f != null ? this.f.getLat() : 0.0d);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        if (this.f != null) {
            d2 = this.f.getLon();
        }
        sb.append(d2);
        sb.append('}');
        return sb.toString();
    }
}
