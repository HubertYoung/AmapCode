package com.amap.location.b;

import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.storage.database.tinyapp.EncryptOrmliteSqliteOpenHelper;

/* compiled from: CollectionConfig */
public class a {
    public static boolean a = false;
    private String b;
    private boolean c = false;
    private final C0010a d = new C0010a();
    private final b e = new b();
    private final c f = new c();

    /* renamed from: com.amap.location.b.a$a reason: collision with other inner class name */
    /* compiled from: CollectionConfig */
    public static class C0010a {
        private boolean a = true;
        private boolean b = true;
        private boolean c = true;
        private boolean d = true;
        private int e = 20000;

        public boolean a() {
            return this.a;
        }

        public void a(boolean z) {
            this.a = z;
        }

        public boolean b() {
            return this.b;
        }

        public boolean c() {
            return this.c;
        }

        public boolean d() {
            return this.d;
        }

        public void b(boolean z) {
            this.d = z;
        }

        public int e() {
            return this.e;
        }
    }

    /* compiled from: CollectionConfig */
    public static class b {
        public volatile byte a = -1;
        private boolean b = true;
        private boolean c = false;

        public byte a() {
            return this.a;
        }

        public void a(String str) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    this.a = Byte.parseByte(str);
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                }
            }
        }

        public boolean b() {
            return this.b;
        }

        public void a(boolean z) {
            this.b = z;
        }

        public boolean c() {
            return this.c;
        }
    }

    /* compiled from: CollectionConfig */
    public static class c {
        private boolean a = true;
        private boolean b = false;
        private int c = EncryptOrmliteSqliteOpenHelper.MAX_DB_SIZE;
        private int d = 307200;
        private int e = 5;

        public void a(boolean z) {
            this.a = z;
        }

        public boolean a() {
            return this.b;
        }

        public void b(boolean z) {
            this.b = z;
        }

        public int b() {
            return this.c;
        }

        public int c() {
            return this.d;
        }

        public int d() {
            return this.e;
        }
    }

    public byte a() {
        return com.amap.location.common.b.a();
    }

    public void a(byte b2) {
        com.amap.location.common.b.a(b2);
    }

    public String b() {
        return com.amap.location.common.b.b() == null ? "" : com.amap.location.common.b.b();
    }

    public void a(String str) {
        com.amap.location.common.b.a(str);
    }

    public String c() {
        return com.amap.location.common.b.c() == null ? "" : com.amap.location.common.b.c();
    }

    public void b(String str) {
        com.amap.location.common.b.b(str);
    }

    public String d() {
        return com.amap.location.common.b.d() == null ? "" : com.amap.location.common.b.d();
    }

    public String e() {
        return this.b == null ? "" : this.b;
    }

    public void c(String str) {
        this.b = str;
    }

    public boolean f() {
        return this.c;
    }

    public void a(boolean z) {
        this.c = z;
    }

    public C0010a g() {
        return this.d;
    }

    public b h() {
        return this.e;
    }

    public c i() {
        return this.f;
    }
}
