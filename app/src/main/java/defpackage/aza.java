package defpackage;

import com.autonavi.bundle.routecommute.common.CommuteHelper;

/* renamed from: aza reason: default package */
/* compiled from: CommuteFactory */
public final class aza {
    private CommuteHelper a = new CommuteHelper();
    private azd b = new azd(this.a);

    public final axv a(int i) {
        azb.a("CommuteFactory", "CommuteFactory creator >>>  from:".concat(String.valueOf(i)));
        switch (i) {
            case 0:
                return this.a;
            case 1:
                return this.b;
            default:
                return this.a;
        }
    }
}
