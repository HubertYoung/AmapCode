package defpackage;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

/* renamed from: cus reason: default package */
/* compiled from: PageEvent */
public final class cus extends cur {
    public int a;
    public AbstractBasePage c;

    private cus(int i, AbstractBasePage abstractBasePage) {
        this.a = i;
        this.c = abstractBasePage;
        this.b = 4;
    }

    public static cus a(int i, AbstractBasePage abstractBasePage) {
        return new cus(i, abstractBasePage);
    }
}
