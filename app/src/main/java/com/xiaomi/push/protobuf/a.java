package com.xiaomi.push.protobuf;

import com.google.protobuf.micro.b;
import com.google.protobuf.micro.c;
import com.google.protobuf.micro.e;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class a {

    /* renamed from: com.xiaomi.push.protobuf.a$a reason: collision with other inner class name */
    public static final class C0081a extends e {
        private boolean a;
        private int b = 0;
        private boolean c;
        private boolean d = false;
        private boolean e;
        private int f = 0;
        private boolean g;
        private boolean h = false;
        private List<String> i = Collections.emptyList();
        private int j = -1;

        public static C0081a b(byte[] bArr) {
            return (C0081a) new C0081a().a(bArr);
        }

        public static C0081a c(b bVar) {
            return new C0081a().a(bVar);
        }

        public final int a() {
            if (this.j < 0) {
                b();
            }
            return this.j;
        }

        public final C0081a a(int i2) {
            this.a = true;
            this.b = i2;
            return this;
        }

        public final C0081a a(String str) {
            if (str == null) {
                throw new NullPointerException();
            }
            if (this.i.isEmpty()) {
                this.i = new ArrayList();
            }
            this.i.add(str);
            return this;
        }

        public final C0081a a(boolean z) {
            this.c = true;
            this.d = z;
            return this;
        }

        public final void a(c cVar) {
            if (e()) {
                cVar.b(1, d());
            }
            if (g()) {
                cVar.a(2, f());
            }
            if (i()) {
                cVar.a(3, h());
            }
            if (k()) {
                cVar.a(4, j());
            }
            for (String a2 : l()) {
                cVar.a(5, a2);
            }
        }

        public final int b() {
            int i2 = 0;
            int d2 = e() ? c.d(1, d()) + 0 : 0;
            if (g()) {
                d2 += c.b(2, f());
            }
            if (i()) {
                d2 += c.c(3, h());
            }
            if (k()) {
                d2 += c.b(4, j());
            }
            for (String b2 : l()) {
                i2 += c.b(b2);
            }
            int size = d2 + i2 + (l().size() * 1);
            this.j = size;
            return size;
        }

        public final C0081a b(int i2) {
            this.e = true;
            this.f = i2;
            return this;
        }

        /* renamed from: b */
        public final C0081a a(b bVar) {
            while (true) {
                int a2 = bVar.a();
                if (a2 == 0) {
                    return this;
                }
                if (a2 == 8) {
                    a(bVar.i());
                } else if (a2 == 16) {
                    a(bVar.f());
                } else if (a2 == 24) {
                    b(bVar.e());
                } else if (a2 == 32) {
                    b(bVar.f());
                } else if (a2 == 42) {
                    a(bVar.g());
                } else if (!a(bVar, a2)) {
                    return this;
                }
            }
        }

        public final C0081a b(boolean z) {
            this.g = true;
            this.h = z;
            return this;
        }

        public final int d() {
            return this.b;
        }

        public final boolean e() {
            return this.a;
        }

        public final boolean f() {
            return this.d;
        }

        public final boolean g() {
            return this.c;
        }

        public final int h() {
            return this.f;
        }

        public final boolean i() {
            return this.e;
        }

        public final boolean j() {
            return this.h;
        }

        public final boolean k() {
            return this.g;
        }

        public final List<String> l() {
            return this.i;
        }

        public final int m() {
            return this.i.size();
        }
    }
}
