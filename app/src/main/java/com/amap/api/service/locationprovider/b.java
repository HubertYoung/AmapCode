package com.amap.api.service.locationprovider;

/* compiled from: Version */
public class b {
    int a = 1;
    int b = 0;

    public void a(int i) {
        this.a = i;
    }

    public void b(int i) {
        this.b = i;
    }

    public int hashCode() {
        return ((this.a + 31) * 31) + this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        b bVar = (b) obj;
        return this.a == bVar.a && this.b == bVar.b;
    }
}
