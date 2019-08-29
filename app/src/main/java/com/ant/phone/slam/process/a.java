package com.ant.phone.slam.process;

/* compiled from: SlamProcessor */
final class a {
    double a;
    byte[] b;
    final /* synthetic */ SlamProcessor c;

    a(SlamProcessor slamProcessor, double timeStamp, byte[] data) {
        this.c = slamProcessor;
        this.a = timeStamp;
        this.b = data;
    }

    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (Double.compare(((a) o).a, this.a) != 0) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.a);
        return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
    }
}
