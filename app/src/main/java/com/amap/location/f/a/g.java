package com.amap.location.f.a;

import java.util.HashSet;

/* compiled from: Nearby */
class g {
    long a;
    HashSet<Long> b = new HashSet<>();

    g() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("main: ");
        sb.append(this.a);
        sb.append(", all: ");
        sb.append(this.b.toString());
        return sb.toString();
    }
}
