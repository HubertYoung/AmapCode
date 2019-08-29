package com.ant.phone.slam.stat;

public class SlamTrackStat {
    public int a;
    public int b;
    public int c;
    public long d;
    public long e;
    public long f;
    public long g;
    public long h;
    public long i;
    public long j;
    StringBuilder k = new StringBuilder(256);

    public final void a() {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.j = 0;
        this.i = 0;
    }

    public String toString() {
        this.k.delete(0, this.k.length());
        this.k.append("SlamTrackStat.result=").append(this.a).append(",viewWidth=").append(this.b).append(",viewHeight=").append(this.c).append(",startORBTime=").append(this.e).append(",renderTime=").append(this.f).append(",fastDectedPoints=").append(this.g).append(",fastTrackPoints=").append(this.h).append(",totalMapPoints=").append(this.i).append(",totalKeyFrames=").append(this.j).append(",");
        return this.k.toString();
    }
}
