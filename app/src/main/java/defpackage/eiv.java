package defpackage;

import android.text.TextUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

/* renamed from: eiv reason: default package */
/* compiled from: TrainPlanFilterHelper */
public final class eiv {
    /* access modifiers changed from: private */
    public static final String[] a = {"00:00-06:00", "06:00-12:00", "12:00-18:00", "18:00-24:00"};
    private final String[] b = {"GC", "D", "ZTK", "_"};
    private HashSet<String> c = new HashSet<>();
    private ArrayList<a> d = new ArrayList<>();
    private ArrayList<a> e = new ArrayList<>();

    /* renamed from: eiv$a */
    /* compiled from: TrainPlanFilterHelper */
    public static class a {
        Date a;
        Date b;
        SimpleDateFormat c;

        public a(int i) {
            if (i < 4 && i >= 0) {
                String[] split = eiv.a[i].split("-");
                this.c = new SimpleDateFormat("HH:mm");
                try {
                    this.a = this.c.parse(split[0]);
                    this.b = this.c.parse(split[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        public final boolean a(String str) {
            if (this.c == null) {
                this.c = new SimpleDateFormat("HH:mm");
            }
            if (this.a == null || this.b == null) {
                return false;
            }
            try {
                Date parse = this.c.parse(str);
                if (parse.after(this.b) || parse.before(this.a)) {
                    return false;
                }
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public eiv() {
    }

    public eiv(com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter.a aVar) {
        a(aVar);
    }

    public final boolean a(com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter.a aVar) {
        if (aVar == null || aVar.e.length != 5 || aVar.f.length != 5) {
            return false;
        }
        b();
        if (aVar.d[0]) {
            for (int i = 0; i < 4; i++) {
                this.c.add(this.b[i]);
            }
        } else {
            for (int i2 = 1; i2 < aVar.d.length; i2++) {
                if (aVar.d[i2]) {
                    this.c.add(this.b[i2 - 1]);
                }
            }
        }
        if (aVar.e[0]) {
            for (int i3 = 0; i3 < 4; i3++) {
                this.d.add(new a(i3));
            }
        } else {
            for (int i4 = 1; i4 < aVar.e.length; i4++) {
                if (aVar.e[i4]) {
                    this.d.add(new a(i4 - 1));
                }
            }
        }
        if (aVar.f[0]) {
            for (int i5 = 0; i5 < 4; i5++) {
                this.e.add(new a(i5));
            }
        } else {
            for (int i6 = 1; i6 < aVar.f.length; i6++) {
                if (aVar.f[i6]) {
                    this.e.add(new a(i6 - 1));
                }
            }
        }
        return true;
    }

    private void b() {
        this.c = new HashSet<>();
        this.d = new ArrayList<>();
        this.e = new ArrayList<>();
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Iterator<String> it = this.c.iterator();
        while (it.hasNext()) {
            if (it.next().contains(str)) {
                return true;
            }
        }
        if (!this.c.contains(this.b[3]) || this.b[0].contains(str) || this.b[1].contains(str) || this.b[2].contains(str)) {
            return false;
        }
        return true;
    }

    public final boolean b(String str) {
        Iterator<a> it = this.d.iterator();
        while (it.hasNext()) {
            if (it.next().a(str)) {
                return true;
            }
        }
        return false;
    }

    public final boolean c(String str) {
        Iterator<a> it = this.e.iterator();
        while (it.hasNext()) {
            if (it.next().a(str)) {
                return true;
            }
        }
        return false;
    }
}
