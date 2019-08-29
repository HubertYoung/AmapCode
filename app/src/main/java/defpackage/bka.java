package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.common.imageloader.ImageLoader.Priority;
import com.autonavi.common.imageloader.MemoryPolicy;
import com.autonavi.common.imageloader.NetworkPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: bka reason: default package */
/* compiled from: RequestCreator */
public final class bka {
    private static final AtomicInteger k = new AtomicInteger();
    private List<bjo> A;
    private Config B;
    private Priority C;
    private Map<String, String> D;
    public final ImageLoader a;
    public boolean b;
    public boolean c;
    public boolean d = true;
    public int e;
    public int f;
    public int g;
    public Drawable h;
    public Object i;
    public boolean j = false;
    private int l;
    private Drawable m;
    private Uri n;
    private int o;
    private String p;
    private int q;
    private int r;
    private boolean s;
    private boolean t;
    private boolean u;
    private float v;
    private float w;
    private float x;
    private boolean y;
    private boolean z;

    public bka(ImageLoader imageLoader, Uri uri) {
        if (imageLoader.o) {
            throw new IllegalStateException("ImageLoader instance already shut down. Cannot submit new requests.");
        }
        this.a = imageLoader;
        this.n = uri;
        this.o = 0;
        this.B = imageLoader.k;
        this.D = null;
    }

    public final bka a(int i2) {
        if (!this.d) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } else if (i2 == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        } else if (this.m != null) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.l = i2;
            return this;
        }
    }

    public final bka b(int i2) {
        if (i2 == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        } else if (this.h != null) {
            throw new IllegalStateException("Error image already set.");
        } else {
            this.e = i2;
            return this;
        }
    }

    public final bka a(int i2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Width must be positive number or 0.");
        } else if (i3 < 0) {
            throw new IllegalArgumentException("Height must be positive number or 0.");
        } else if (i3 == 0 && i2 == 0) {
            throw new IllegalArgumentException("At least one dimension has to be positive number.");
        } else {
            this.q = i2;
            this.r = i3;
            return this;
        }
    }

    public final bka a() {
        if (this.t) {
            throw new IllegalStateException("Center crop can not be used after calling centerInside");
        }
        this.s = true;
        return this;
    }

    public final bka a(bjo bjo) {
        if (bjo == null) {
            throw new IllegalArgumentException("DrawableFactory must not be null.");
        }
        bjo.a();
        if (this.A == null) {
            this.A = new ArrayList(2);
        }
        this.A.add(bjo);
        return this;
    }

    public final bka a(MemoryPolicy memoryPolicy, MemoryPolicy... memoryPolicyArr) {
        if (memoryPolicy == null) {
            throw new IllegalArgumentException("Memory policy cannot be null.");
        }
        this.f = memoryPolicy.a | this.f;
        for (int i2 = 0; i2 <= 0; i2++) {
            MemoryPolicy memoryPolicy2 = memoryPolicyArr[0];
            if (memoryPolicy2 == null) {
                throw new IllegalArgumentException("Memory policy cannot be null.");
            }
            this.f = memoryPolicy2.a | this.f;
        }
        return this;
    }

    public final bka a(NetworkPolicy networkPolicy, NetworkPolicy... networkPolicyArr) {
        if (networkPolicy == null) {
            throw new IllegalArgumentException("Network policy cannot be null.");
        }
        this.g = networkPolicy.a | this.g;
        for (int i2 = 0; i2 <= 0; i2++) {
            NetworkPolicy networkPolicy2 = networkPolicyArr[0];
            if (networkPolicy2 == null) {
                throw new IllegalArgumentException("Network policy cannot be null.");
            }
            this.g = networkPolicy2.a | this.g;
        }
        return this;
    }

    public final void a(bkf bkf) {
        long nanoTime = System.nanoTime();
        bkh.a();
        if (bkf == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (this.c) {
            throw new IllegalStateException("Fit cannot be used with a Target.");
        } else {
            Drawable drawable = null;
            if (!c()) {
                this.a.a((Object) bkf);
                if (this.d) {
                    drawable = b();
                }
                bkf.onPrepareLoad(drawable);
                return;
            }
            bjz a2 = a(nanoTime);
            String a3 = bkh.a(a2);
            if (MemoryPolicy.shouldReadFromMemoryCache(this.f)) {
                Bitmap b2 = this.a.b(a3);
                if (b2 != null) {
                    this.a.a((Object) bkf);
                    bkf.onBitmapLoaded(b2, LoadedFrom.MEMORY);
                    return;
                }
            }
            if (this.d) {
                drawable = b();
            }
            bkf.onPrepareLoad(drawable);
            bkg bkg = new bkg(this.a, bkf, a2, this.f, this.g, this.h, a3, this.i, this.e, this.j);
            this.a.a((bji) bkg);
        }
    }

    public final void a(ImageView imageView, bjl bjl) {
        ImageView imageView2 = imageView;
        long nanoTime = System.nanoTime();
        bkh.a();
        if (imageView2 == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (!c()) {
            this.a.a((Object) imageView2);
            if (this.d) {
                bjx.a(imageView2, b());
            }
        } else {
            if (this.c) {
                if (d()) {
                    throw new IllegalStateException("Fit cannot be used with resize.");
                }
                int width = imageView.getWidth();
                int height = imageView.getHeight();
                if (width == 0 || height == 0) {
                    if (this.d) {
                        bjx.a(imageView2, b());
                    }
                    this.a.a(imageView2, new bjn(this, imageView2, bjl));
                    return;
                }
                a(width, height);
            }
            bjl bjl2 = bjl;
            bjz a2 = a(nanoTime);
            String a3 = bkh.a(a2);
            if (MemoryPolicy.shouldReadFromMemoryCache(this.f)) {
                Bitmap b2 = this.a.b(a3);
                if (b2 != null) {
                    this.a.a((Object) imageView2);
                    bjx.a(imageView2, this.a.d, b2, LoadedFrom.MEMORY, this.b, this.a.m);
                    if (this.a.n) {
                        String b3 = a2.b();
                        StringBuilder sb = new StringBuilder("from ");
                        sb.append(LoadedFrom.MEMORY);
                        bkh.a("Main", "completed", b3, sb.toString());
                    }
                    return;
                }
            }
            if (this.d) {
                bjx.a(imageView2, b());
            }
            bju bju = new bju(this.a, imageView2, a2, this.f, this.g, this.e, this.h, a3, this.i, bjl2, this.b, this.j);
            this.a.a((bji) bju);
        }
    }

    public final Drawable b() {
        if (this.l != 0) {
            return this.a.d.getResources().getDrawable(this.l);
        }
        return this.m;
    }

    public final bjz a(long j2) {
        int andIncrement = k.getAndIncrement();
        bjz e2 = e();
        e2.a = andIncrement;
        e2.b = j2;
        boolean z2 = this.a.n;
        if (z2) {
            bkh.a("Main", "created", e2.b(), e2.toString());
        }
        bjz a2 = this.a.a(e2);
        if (a2 != e2) {
            a2.a = andIncrement;
            a2.b = j2;
            if (z2) {
                bkh.a("Main", "changed", a2.a(), "into ".concat(String.valueOf(a2)));
            }
        }
        return a2;
    }

    public final boolean c() {
        return (this.n == null && this.o == 0) ? false : true;
    }

    public final boolean d() {
        return (this.q == 0 && this.r == 0) ? false : true;
    }

    private bjz e() {
        if (this.t && this.s) {
            throw new IllegalStateException("Center crop and center inside can not be used together.");
        } else if (this.s && this.q == 0 && this.r == 0) {
            throw new IllegalStateException("Center crop requires calling resize with positive width and height.");
        } else if (this.t && this.q == 0 && this.r == 0) {
            throw new IllegalStateException("Center inside requires calling resize with positive width and height.");
        } else {
            if (this.C == null) {
                this.C = Priority.NORMAL;
            }
            Uri uri = this.n;
            int i2 = this.o;
            String str = this.p;
            List<bjo> list = this.A;
            int i3 = this.q;
            int i4 = this.r;
            boolean z2 = this.s;
            boolean z3 = this.t;
            boolean z4 = this.u;
            float f2 = this.v;
            float f3 = this.w;
            float f4 = this.x;
            boolean z5 = this.y;
            boolean z6 = z5;
            bjz bjz = new bjz(uri, i2, str, list, i3, i4, z2, z3, z4, f2, f3, f4, z6, this.z, this.B, this.C, this.D);
            return bjz;
        }
    }
}
