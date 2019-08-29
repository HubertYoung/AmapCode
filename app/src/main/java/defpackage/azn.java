package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.routecommute.common.bean.CommuteControlBean;

/* renamed from: azn reason: default package */
/* compiled from: CommuteConfigHolder */
public final class azn {
    public CommuteControlBean a;

    /* renamed from: azn$a */
    /* compiled from: CommuteConfigHolder */
    public static class a {
        public static azn a = new azn(0);
    }

    /* synthetic */ azn(byte b) {
        this();
    }

    private azn() {
    }

    public final boolean a(String str) {
        this.a = CommuteControlBean.create(str);
        return this.a != null;
    }

    public final double a() {
        if (this.a == null) {
            return 1500.0d;
        }
        return this.a.getBusBubbleRuleInfo().a;
    }

    public final azp b() {
        if (this.a == null) {
            return null;
        }
        return this.a.getNewUserBubbleRule();
    }

    public final boolean c() {
        if (azl.e()) {
            return true;
        }
        if (this.a != null) {
            azp newUserBubbleRule = this.a.getNewUserBubbleRule();
            if (!(newUserBubbleRule == null || newUserBubbleRule.c == null)) {
                String n = azi.n();
                if (TextUtils.isEmpty(newUserBubbleRule.c.a) || !TextUtils.equals(newUserBubbleRule.c.a, n) || !azl.d()) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
