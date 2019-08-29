package com.amap.location.offline.b.a;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.location.common.model.AmapLoc;

/* compiled from: OffCellInfo */
public class a {
    public boolean a;
    public int b;
    public int c;
    public int d;
    public int e;
    public String f;
    public long g;
    public boolean h = false;
    public AmapLoc i;

    public a(boolean z, String str, long j, int i2, int i3, int i4, int i5) {
        this.a = z;
        this.f = str;
        this.g = j;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append(this.a);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.f);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.b);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.c);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.e);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.h);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        double d2 = 0.0d;
        sb.append(this.i != null ? this.i.getLat() : 0.0d);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        if (this.i != null) {
            d2 = this.i.getLon();
        }
        sb.append(d2);
        sb.append('}');
        return sb.toString();
    }
}
