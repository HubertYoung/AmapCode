package com.amap.location.f.a;

import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.ArrayList;

/* compiled from: Key */
class b {
    ArrayList<a> a = new ArrayList<>();
    byte b;

    /* compiled from: Key */
    static class a {
        int a;
        int b;
        int c;
        int d;

        a() {
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof a)) {
                a aVar = (a) obj;
                if (this.a == aVar.a && this.b == aVar.b && this.c == aVar.c && this.d == aVar.d) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (((((this.a * 31) + this.b) * 31) + this.c) * 31) + this.d;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(",");
            sb.append(this.b);
            sb.append(",");
            sb.append(this.c);
            sb.append(",");
            sb.append(this.d);
            return sb.toString();
        }
    }

    b() {
    }

    /* access modifiers changed from: 0000 */
    public boolean a() {
        return this.b > 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof b)) {
            b bVar = (b) obj;
            if (this.b == bVar.b && (this.a != null ? this.a.equals(bVar.a) : bVar.a == null)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        byte b2 = this.b;
        return this.a != null ? (b2 * 31) + this.a.hashCode() : b2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a.toString());
        sb.append(MetaRecord.LOG_SEPARATOR);
        sb.append(this.b);
        return sb.toString();
    }
}
