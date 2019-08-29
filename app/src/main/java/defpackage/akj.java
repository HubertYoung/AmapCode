package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: akj reason: default package */
/* compiled from: PageStack */
public final class akj {
    final Context a;
    final LayoutInflater b;
    final akf c;
    public final ArrayList<a> d = new ArrayList<>();
    public final ArrayList<b> e = new ArrayList<>();
    public final Handler f = new Handler() {
        public final void handleMessage(Message message) {
            if (message.what != 0) {
                new StringBuilder("error: never reach here: what=").append(message.what);
                return;
            }
            akj akj = akj.this;
            while (akj.e.size() > 0 && akj.e.get(0).a()) {
                akj.e.remove(0);
            }
        }
    };
    public akb g;
    public Object h;
    public int i = 1;
    boolean j;
    private final akb k = new ajy();

    /* renamed from: akj$a */
    /* compiled from: PageStack */
    public class a implements ajz {
        public final akm a;
        public akc b;
        View c;
        int d = 0;
        akh e;
        int f;
        akh g;

        a(akm akm) {
            this.a = akm;
        }

        /* access modifiers changed from: 0000 */
        public final String g() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.b);
            sb.append(" ident=");
            sb.append(this.a.i);
            return sb.toString();
        }

        /* access modifiers changed from: 0000 */
        public final boolean h() {
            return this.d == 6;
        }

        /* access modifiers changed from: 0000 */
        public final boolean i() {
            return this.d == 2 || this.d == 5 || this.d == 3;
        }

        private boolean k() {
            return this.d == 2 || this.d == 3;
        }

        /* access modifiers changed from: 0000 */
        public final boolean j() {
            return this.d == 4;
        }

        public final boolean a() {
            return this.a.a(16);
        }

        public final View b() {
            return this.c;
        }

        public final boolean a(akm akm) {
            try {
                this.b = (akc) akm.f.newInstance();
                new StringBuilder("handle create: controller created: c=").append(g());
                this.d = 1;
                akj.this.d.add(this);
                try {
                    this.b.a(akj.this.a, akj.this.b, akj.this.h);
                    if (this.a.k != null) {
                        this.a.j.a = this.a.k.a;
                    }
                    this.b.a(this.a.j);
                    return true;
                } catch (Throwable th) {
                    new StringBuilder("handle create: ").append(th.getMessage());
                    throw th;
                }
            } catch (Throwable th2) {
                new StringBuilder("handle create: ").append(th2.getMessage());
                throw new RuntimeException(th2);
            }
        }

        public final View d() {
            try {
                View a2 = this.b.a();
                new StringBuilder("handle create view: view created: v=").append(a2);
                this.c = a2;
                return this.c;
            } catch (Throwable th) {
                new StringBuilder("handle create view: ").append(th.getMessage());
                throw th;
            }
        }

        public final void c() {
            if (this.d == 1) {
                this.d = 6;
                new StringBuilder("handle destroy: c=").append(g());
                akj.this.c.a(this.c);
                try {
                    this.b.b();
                    akj.this.d.remove(this);
                    if (this.a.k != null) {
                        akj.this.a(this.a.k.b, this.a.k.a, this.f, this.g);
                    }
                    akj.a(akj.this);
                } catch (Throwable th) {
                    new StringBuilder("handle destroy: ").append(th.getMessage());
                    throw th;
                }
            }
        }

        public final void a(boolean z) {
            StringBuilder sb = new StringBuilder("handle start: as=");
            sb.append(akj.this.i);
            sb.append(", s=");
            sb.append(this.d);
            sb.append(" c=");
            sb.append(g());
            if (akj.this.i >= 2) {
                boolean z2 = true;
                if (this.d != 1) {
                    z2 = false;
                }
                if (z2) {
                    this.d = 2;
                    akf akf = akj.this.c;
                    View view = this.a.m;
                    new StringBuilder("add: v=").append(view);
                    view.clearAnimation();
                    if (akf.a.indexOfChild(view) < 0) {
                        akf.a.addView(view, view.getLayoutParams());
                    } else {
                        view.setVisibility(0);
                    }
                    if (this.e != null) {
                        new StringBuilder("handle new params: c=").append(g());
                        try {
                            this.b.b(this.e);
                            this.e = null;
                        } catch (Throwable th) {
                            new StringBuilder("handle new params: ").append(th.getMessage());
                            throw th;
                        }
                    }
                    try {
                        this.b.c();
                    } catch (Throwable th2) {
                        new StringBuilder("handle start: ").append(th2.getMessage());
                        throw th2;
                    }
                }
                if (k() && z) {
                    StringBuilder sb2 = new StringBuilder("handle resume: as=");
                    sb2.append(akj.this.i);
                    sb2.append(", s=");
                    sb2.append(this.d);
                    sb2.append(" c=");
                    sb2.append(g());
                    if (akj.this.i >= 4 && k()) {
                        this.d = 4;
                        if (this.e != null) {
                            new StringBuilder("handle new params: c=").append(g());
                            try {
                                this.b.b(this.e);
                                this.e = null;
                            } catch (Throwable th3) {
                                new StringBuilder("handle new params: ").append(th3.getMessage());
                                throw th3;
                            }
                        }
                        new StringBuilder("handle resume: c=").append(g());
                        try {
                            this.b.e();
                            akj.a(akj.this);
                        } catch (Throwable th4) {
                            new StringBuilder("handle resume: ").append(th4.getMessage());
                            throw th4;
                        }
                    }
                }
            }
        }

        public final void e() {
            StringBuilder sb = new StringBuilder("handle stop: pause, as=");
            sb.append(akj.this.i);
            sb.append(", s=");
            sb.append(this.d);
            sb.append(" c=");
            sb.append(g());
            f();
            StringBuilder sb2 = new StringBuilder("handle stop: stop, as=");
            sb2.append(akj.this.i);
            sb2.append(", s=");
            sb2.append(this.d);
            sb2.append(" c=");
            sb2.append(g());
            if (i()) {
                this.d = 1;
                if (akj.this.i > 1) {
                    akf.b(this.c);
                }
                new StringBuilder("handle stop: c=").append(g());
                try {
                    this.b.d();
                } catch (Throwable th) {
                    new StringBuilder("handle stop: ").append(th.getMessage());
                    throw th;
                }
            }
        }

        public final void f() {
            StringBuilder sb = new StringBuilder("handle pause: as=");
            sb.append(akj.this.i);
            sb.append(", s=");
            sb.append(this.d);
            sb.append(" c=");
            sb.append(g());
            if (j()) {
                this.d = 2;
                new StringBuilder("handle pause: c=").append(g());
                try {
                    this.b.f();
                } catch (Throwable th) {
                    new StringBuilder("handle pause: ").append(th.getMessage());
                    throw th;
                }
            }
        }
    }

    /* renamed from: akj$b */
    /* compiled from: PageStack */
    interface b {
        boolean a();
    }

    public akj(Context context, LayoutInflater layoutInflater, akf akf) {
        this.a = context;
        this.b = layoutInflater;
        this.c = akf;
        this.g = this.k;
    }

    public final boolean a(akg akg, int i2, akh akh) {
        StringBuilder sb = new StringBuilder("do set page result: rc=");
        sb.append(i2);
        sb.append(" id=");
        sb.append(akg);
        a a2 = a(akg);
        if (a2 == null) {
            return true;
        }
        a2.f = i2;
        a2.g = akh;
        return true;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006c, code lost:
        if (r6 != false) goto L_0x006e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.ArrayList<defpackage.ajz> a(defpackage.ajz r6, boolean r7) {
        /*
            r5 = this;
            java.util.ArrayList<akj$a> r0 = r5.d
            int r0 = r0.indexOf(r6)
            r1 = 0
            r2 = 1
            if (r7 == 0) goto L_0x0044
            if (r0 >= 0) goto L_0x003c
            boolean r6 = r6.a()
            if (r6 == 0) goto L_0x0013
            return r1
        L_0x0013:
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            java.util.ArrayList<akj$a> r7 = r5.d
            int r7 = r7.size()
            int r7 = r7 - r2
        L_0x001f:
            if (r7 < 0) goto L_0x003b
            java.util.ArrayList<akj$a> r0 = r5.d
            java.lang.Object r0 = r0.get(r7)
            akj$a r0 = (defpackage.akj.a) r0
            boolean r1 = r0.i()
            if (r1 != 0) goto L_0x0035
            boolean r1 = r0.j()
            if (r1 == 0) goto L_0x003b
        L_0x0035:
            r6.add(r0)
            int r7 = r7 + -1
            goto L_0x001f
        L_0x003b:
            return r6
        L_0x003c:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.String r7 = "待实现"
            r6.<init>(r7)
            throw r6
        L_0x0044:
            java.util.ArrayList<akj$a> r6 = r5.d
            int r6 = r6.size()
            int r6 = r6 - r2
            int r7 = r0 + 1
            akj$a r6 = r5.b(r6, r7)
            if (r6 == 0) goto L_0x0054
            return r1
        L_0x0054:
            int r0 = r0 - r2
            r6 = 0
            akj$a r7 = r5.b(r0, r6)
            if (r7 == 0) goto L_0x006f
            int r3 = r7.d
            r4 = 2
            if (r3 != r4) goto L_0x0063
            r3 = 1
            goto L_0x0064
        L_0x0063:
            r3 = 0
        L_0x0064:
            if (r3 != 0) goto L_0x006e
            int r7 = r7.d
            r3 = 4
            if (r7 != r3) goto L_0x006c
            r6 = 1
        L_0x006c:
            if (r6 == 0) goto L_0x006f
        L_0x006e:
            return r1
        L_0x006f:
            java.util.ArrayList r6 = r5.b(r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.akj.a(ajz, boolean):java.util.ArrayList");
    }

    /* access modifiers changed from: private */
    public boolean a(akg akg, int i2, int i3, akh akh) {
        StringBuilder sb = new StringBuilder("do set result: req=");
        sb.append(i2);
        sb.append(" rc=");
        sb.append(i3);
        sb.append(" id=");
        sb.append(akg);
        a a2 = a(akg);
        if (a2 == null) {
            return true;
        }
        if (a2.h()) {
            new StringBuilder("do set result: page is finished, do nothing: c=").append(a2.a.f);
            return true;
        }
        if (!(a2.d == 3)) {
            if (!(a2.d == 5)) {
                try {
                    a2.b.a(i2, i3, akh);
                    new StringBuilder("handle result done: c=").append(a2.g());
                    return true;
                } catch (Throwable th) {
                    new StringBuilder("handle result done: ").append(th.getMessage());
                    throw th;
                }
            }
        }
        new StringBuilder("do set result: pending for target animation: c=").append(a2.a.f);
        return false;
    }

    public final ArrayList<ajz> a() {
        return b(this.d.size() - 1);
    }

    static final ArrayList<a> a(a aVar) {
        ArrayList<a> arrayList = new ArrayList<>();
        arrayList.add(aVar);
        return arrayList;
    }

    static ArrayList<ajz> a(ArrayList<a> arrayList) {
        ArrayList<ajz> arrayList2 = new ArrayList<>();
        Iterator<a> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(it.next());
        }
        return arrayList2;
    }

    static final ArrayList<View> b(ArrayList<ajz> arrayList) {
        if (arrayList == null) {
            return null;
        }
        ArrayList<View> arrayList2 = new ArrayList<>();
        Iterator<ajz> it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(it.next().b());
        }
        return arrayList2;
    }

    public static final void a(ArrayList<ajz> arrayList, boolean z) {
        if (arrayList != null) {
            Iterator<ajz> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().a(z);
            }
        }
    }

    public static final void c(ArrayList<ajz> arrayList) {
        if (arrayList != null) {
            Iterator<ajz> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().f();
            }
        }
        if (arrayList != null) {
            Iterator<ajz> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                it2.next().e();
            }
        }
    }

    public static final void d(ArrayList<ajz> arrayList) {
        if (arrayList != null) {
            Iterator<ajz> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().f();
            }
        }
    }

    public static final void e(ArrayList<ajz> arrayList) {
        if (arrayList != null) {
            Iterator<ajz> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().f();
            }
        }
        if (arrayList != null) {
            Iterator<ajz> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                it2.next().e();
            }
        }
        if (arrayList != null) {
            Iterator<ajz> it3 = arrayList.iterator();
            while (it3.hasNext()) {
                it3.next().c();
            }
        }
    }

    private final ArrayList<ajz> b(int i2) {
        ArrayList<ajz> arrayList = new ArrayList<>();
        while (i2 >= 0) {
            a aVar = this.d.get(i2);
            arrayList.add(aVar);
            if (!aVar.a()) {
                break;
            }
            i2--;
        }
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public final void a(ArrayList<a> arrayList, int i2, int i3) {
        while (i2 < i3) {
            a aVar = this.d.get(i2);
            if (aVar.a()) {
                arrayList.add(aVar);
                i2++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final a a(Class<? extends akc> cls, int i2) {
        while (i2 >= 0) {
            a aVar = this.d.get(i2);
            if (aVar.a.f != cls || !TextUtils.isEmpty(aVar.a.l)) {
                if (!aVar.a()) {
                    break;
                }
                i2--;
            } else {
                return aVar;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final a a(String str, int i2) {
        while (i2 >= 0) {
            a aVar = this.d.get(i2);
            String str2 = aVar.a.l;
            if (TextUtils.isEmpty(str2) || !str2.equals(str)) {
                if (!aVar.a()) {
                    break;
                }
                i2--;
            } else {
                return aVar;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final a b(Class<?> cls, int i2) {
        while (i2 >= 0) {
            a aVar = this.d.get(i2);
            if (aVar.a.i != cls || !TextUtils.isEmpty(aVar.a.l)) {
                if (!aVar.a()) {
                    break;
                }
                i2--;
            } else {
                return aVar;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final int a(int i2) {
        while (i2 < this.d.size()) {
            if (!this.d.get(i2).a()) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private final a b(int i2, int i3) {
        while (i2 >= i3) {
            a aVar = this.d.get(i2);
            if (!aVar.a()) {
                return aVar;
            }
            i2--;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final int a(int i2, int i3) {
        while (i2 >= i3) {
            if (!this.d.get(i2).a()) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public final int a(Class<? extends akc> cls) {
        for (int size = this.d.size() - 1; size >= 0; size--) {
            if (this.d.get(size).a.f == cls && TextUtils.isEmpty(this.d.get(size).a.l)) {
                return size;
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public final int b(String str, int i2) {
        int size = this.d.size();
        while (true) {
            size--;
            if (size < 0) {
                return -1;
            }
            i2--;
            String str2 = this.d.get(size).a.l;
            if (!TextUtils.isEmpty(str2) && str2.equals(str) && i2 <= 0) {
                return size;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final int c(Class<?> cls, int i2) {
        int size = this.d.size();
        while (true) {
            size--;
            if (size < 0) {
                return -1;
            }
            i2--;
            if (this.d.get(size).a.i == cls && i2 <= 0 && TextUtils.isEmpty(this.d.get(size).a.l)) {
                return size;
            }
        }
    }

    static boolean f(ArrayList<ajz> arrayList) {
        return arrayList == null || arrayList.isEmpty();
    }

    /* access modifiers changed from: 0000 */
    public final a a(akg akg) {
        akc a2 = akg.a();
        if (a2 != null) {
            a a3 = a(a2);
            if (a3 == null) {
                new StringBuilder("lookup: not found page record: c=").append(a2);
                return null;
            } else if (a3.c != null) {
                return a3;
            } else {
                new StringBuilder("lookup: page record's view is null: c=").append(a2);
                return null;
            }
        } else {
            View b2 = akg.b();
            if (b2 == null) {
                return null;
            }
            a a4 = a(b2);
            if (a4 != null) {
                return a4;
            }
            new StringBuilder("lookup: not found page record: v=").append(b2);
            return null;
        }
    }

    private final a a(View view) {
        Iterator<a> it = this.d.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.c == view) {
                return next;
            }
        }
        return null;
    }

    private final a a(akc akc) {
        Iterator<a> it = this.d.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.b == akc) {
                return next;
            }
        }
        return null;
    }

    static /* synthetic */ void a(akj akj) {
        if (akj.e.size() > 0) {
            akj.f.obtainMessage(0).sendToTarget();
        }
    }
}
