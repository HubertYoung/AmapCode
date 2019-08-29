package defpackage;

import com.autonavi.bundle.entity.infolite.internal.Condition;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: djv reason: default package */
/* compiled from: TrafficTopBoardResultData */
public final class djv {
    public String a;
    public String b;
    public String c;
    public String d;
    public a e;
    public ArrayList<a> f;
    public ArrayList<d> g;
    public ArrayList<b> h;

    /* renamed from: djv$a */
    /* compiled from: TrafficTopBoardResultData */
    public static class a {
        public String a;
        public String b;
        public String c;
        public String d;
    }

    /* renamed from: djv$b */
    /* compiled from: TrafficTopBoardResultData */
    public static class b {
        public String a;
        public ArrayList<c> b;
    }

    /* renamed from: djv$c */
    /* compiled from: TrafficTopBoardResultData */
    public static class c {
        public String a;
        public String b;
        public String c;
        public double d;
        public String e;
        public String f;
        public String g;
        public String h;
        public String i;
        public String j;
        public String k;
        public String l;
    }

    /* renamed from: djv$d */
    /* compiled from: TrafficTopBoardResultData */
    public static class d {
        public String a;
        public String b;
    }

    public final Condition a() {
        Condition condition = new Condition();
        condition.displayName = this.e.b;
        condition.value = this.e.a;
        condition.dValue = this.e.a;
        condition.checkedValue = this.e.a;
        ArrayList<Condition> arrayList = condition.subConditions;
        ArrayList arrayList2 = new ArrayList();
        Iterator<a> it = this.f.iterator();
        while (it.hasNext()) {
            a next = it.next();
            Condition condition2 = new Condition();
            condition2.displayName = next.b;
            condition2.name = next.b;
            condition2.value = next.a;
            arrayList2.add(condition2);
        }
        arrayList.addAll(arrayList2);
        return condition;
    }

    public final d b() {
        if (!(this.h == null || this.h.get(0) == null)) {
            String str = this.h.get(0).a;
            Iterator<d> it = this.g.iterator();
            while (it.hasNext()) {
                d next = it.next();
                if (next.a.equals(str)) {
                    return next;
                }
            }
        }
        return null;
    }

    public final Condition c() {
        d b2 = b();
        Condition condition = new Condition();
        if (b2 != null) {
            condition.displayName = b2.b;
            condition.name = b2.b;
            condition.value = b2.a;
            condition.dValue = b2.a;
            condition.checkedValue = b2.a;
            ArrayList<Condition> arrayList = condition.subConditions;
            ArrayList arrayList2 = new ArrayList();
            Iterator<d> it = this.g.iterator();
            while (it.hasNext()) {
                d next = it.next();
                Condition condition2 = new Condition();
                condition2.displayName = next.b;
                condition2.name = next.b;
                condition2.value = next.a;
                arrayList2.add(condition2);
            }
            arrayList.addAll(arrayList2);
        }
        return condition;
    }

    public final String d() {
        return this.e != null ? this.e.a : "";
    }
}
