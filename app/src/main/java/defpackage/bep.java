package defpackage;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

/* renamed from: bep reason: default package */
/* compiled from: Tab */
public final class bep {
    @DrawableRes
    public final int a;
    @ColorRes
    public final int b;
    public final CharSequence c;
    public final String d;
    public boolean e;
    public View f;
    public Class<? extends beo> g;

    public bep(int i, CharSequence charSequence, int i2, Class<? extends beo> cls) {
        this.a = i;
        this.c = charSequence;
        this.b = i2;
        this.g = cls;
        StringBuilder sb = new StringBuilder();
        sb.append(this.c);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.g.getSimpleName());
        this.d = sb.toString();
    }

    public static String a(String str, Class<? extends AbstractBasePage> cls) {
        if (TextUtils.isEmpty(str)) {
            return "ERROR_TAB";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(cls.getSimpleName());
        return sb.toString();
    }

    public final String toString() {
        return this.d;
    }

    public static boolean a(bep bep, bep bep2) {
        if (bep == null || bep2 == null || !bep.d.equals(bep2.d)) {
            return false;
        }
        return true;
    }
}
