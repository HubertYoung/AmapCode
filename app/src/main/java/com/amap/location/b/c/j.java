package com.amap.location.b.c;

/* compiled from: Satellite */
public class j {
    public byte a;
    public byte b;
    public byte c;
    public short d;
    public byte e;
    public byte f;

    public String toString() {
        StringBuilder sb = new StringBuilder("Satellite{prn=");
        sb.append(this.a);
        sb.append(", snr=");
        sb.append(this.b);
        sb.append(", elevation=");
        sb.append(this.c);
        sb.append(", azimuth=");
        sb.append(this.d);
        sb.append(", usedInFix=");
        sb.append(this.e);
        sb.append(", constellationType=");
        sb.append(this.f);
        sb.append('}');
        return sb.toString();
    }
}
