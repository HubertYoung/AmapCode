package defpackage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/* renamed from: yz reason: default package */
/* compiled from: NetworkFilter */
public final class yz implements bpd {
    private List<bpd> a = new LinkedList();

    public yz() {
        zb zbVar = new zb();
        zm zmVar = new zm();
        aan aan = new aan();
        yy yyVar = new yy();
        za zaVar = new za();
        yw ywVar = new yw();
        this.a.add(zbVar);
        this.a.add(zmVar);
        this.a.add(aan);
        this.a.add(yyVar);
        this.a.add(zaVar);
        this.a.add(ywVar);
    }

    public final bph a(bph bph) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.a);
        for (int i = 0; i < arrayList.size(); i++) {
            bph = ((bpd) arrayList.get(i)).a(bph);
        }
        return bph;
    }

    public final bpk a(bpk bpk) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.a);
        for (int i = 0; i < arrayList.size(); i++) {
            bpk = ((bpd) arrayList.get(i)).a(bpk);
        }
        return bpk;
    }
}
