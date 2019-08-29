package com.amap.location.b.c;

/* compiled from: CellPart */
public class c<T> {
    public byte a;
    public byte b;
    public byte c;
    public short d = 0;
    public long e;
    public T f;

    public String toString() {
        StringBuilder sb = new StringBuilder("CellPart{type=");
        sb.append(this.a);
        sb.append(", isMain=");
        sb.append(this.b);
        sb.append(", interfaceType=");
        sb.append(this.c);
        sb.append(", freshness=");
        sb.append(this.d);
        sb.append(", firstBuildTime=");
        sb.append(this.e);
        sb.append(", cellData=");
        sb.append(this.f);
        sb.append('}');
        return sb.toString();
    }
}
