package defpackage;

import android.graphics.Bitmap;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam.SendType;

/* renamed from: dco reason: default package */
/* compiled from: ShareData */
public final class dco {
    public dci a;
    public boolean b = false;
    public g c;
    public b d;
    public k e;
    public i f;
    public h g;
    public a h;
    public f i;
    public d j;
    public e k;

    /* renamed from: dco$a */
    /* compiled from: ShareData */
    public static class a extends c {
        public SendType a = SendType.Unknown;
        public String b;
        public Bitmap c;
        public String d;
        public String e;
    }

    /* renamed from: dco$b */
    /* compiled from: ShareData */
    public static class b extends c {
        public String a;
    }

    /* renamed from: dco$c */
    /* compiled from: ShareData */
    public static class c {
        public String f;
        public String g;
        public boolean h = true;
        public String i = null;
    }

    /* renamed from: dco$d */
    /* compiled from: ShareData */
    public static class d extends c {
        public String a;
        public String b;
        public Bitmap c;
    }

    /* renamed from: dco$e */
    /* compiled from: ShareData */
    public static class e extends c {
        public String a;
        public String b;
        public Bitmap c;
    }

    /* renamed from: dco$f */
    /* compiled from: ShareData */
    public static class f extends c {
        public String a;
    }

    /* renamed from: dco$g */
    /* compiled from: ShareData */
    public static class g extends c {
    }

    /* renamed from: dco$h */
    /* compiled from: ShareData */
    public static class h extends j {
        public h() {
            this.e = 1;
        }
    }

    /* renamed from: dco$i */
    /* compiled from: ShareData */
    public static class i extends j {
        public i() {
            this.e = 0;
        }
    }

    /* renamed from: dco$j */
    /* compiled from: ShareData */
    public static abstract class j extends c {
        public int a;
        public String b;
        public Bitmap c;
        public String d;
        protected int e;
        public dcs j;
        public int k;

        public final int a() {
            return this.e;
        }
    }

    /* renamed from: dco$k */
    /* compiled from: ShareData */
    public static class k extends c {
        public int a;
        public int b;
        public String c;
        public String d;
        public boolean e = false;
        public boolean j = false;
    }
}
