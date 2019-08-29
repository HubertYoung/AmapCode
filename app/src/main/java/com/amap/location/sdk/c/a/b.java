package com.amap.location.sdk.c.a;

/* compiled from: PhoneStateHolder */
class b {
    private int a = -1;
    private int b = -1;
    private int c = -1;
    private int d = -1;
    private int e = -1;
    private int f = -1;
    private int g = -1;
    private int h = -1;
    private int i = -1;
    private int j = -1;
    private long k = -1;
    private long l = -1;
    private long m = -1;

    b() {
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        this.a = i2;
    }

    /* access modifiers changed from: 0000 */
    public void b(int i2) {
        this.b = i2;
    }

    /* access modifiers changed from: 0000 */
    public void c(int i2) {
        this.c = i2;
    }

    /* access modifiers changed from: 0000 */
    public short a() {
        return (short) this.d;
    }

    /* access modifiers changed from: 0000 */
    public void d(int i2) {
        this.d = i2;
    }

    /* access modifiers changed from: 0000 */
    public void e(int i2) {
        this.e = i2;
    }

    /* access modifiers changed from: 0000 */
    public void f(int i2) {
        this.f = i2;
    }

    /* access modifiers changed from: 0000 */
    public void g(int i2) {
        this.g = i2;
    }

    /* access modifiers changed from: 0000 */
    public void h(int i2) {
        this.h = i2;
    }

    /* access modifiers changed from: 0000 */
    public void i(int i2) {
        this.i = i2;
    }

    /* access modifiers changed from: 0000 */
    public void j(int i2) {
        this.j = i2;
    }

    /* access modifiers changed from: 0000 */
    public void a(long j2) {
        this.k = j2;
    }

    /* access modifiers changed from: 0000 */
    public void b(long j2) {
        this.l = j2;
    }

    /* access modifiers changed from: 0000 */
    public void c(long j2) {
        this.m = j2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Phone State:,airMode:");
        sb.append(this.a);
        sb.append(",lowMemory:");
        sb.append(this.b);
        sb.append(",sdcardMounted:");
        sb.append(this.c);
        sb.append(",wifiEnable:");
        sb.append(this.d);
        sb.append(",internetConnected:");
        sb.append(this.e);
        sb.append(",mobileDataType:");
        sb.append(this.f);
        sb.append(",mobileDataChannelType:");
        sb.append(this.g);
        sb.append(",locatePermissionState:");
        sb.append(this.h);
        sb.append(",gpsEnable:");
        sb.append(this.i);
        sb.append(",gpsMockEnabled:");
        sb.append(this.j);
        sb.append(",dns1:");
        sb.append(this.k);
        sb.append(",dns2:");
        sb.append(this.l);
        sb.append(",timestamp:");
        sb.append(this.m);
        return sb.toString();
    }
}
