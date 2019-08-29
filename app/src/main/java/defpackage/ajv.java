package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import java.util.ArrayList;

/* renamed from: ajv reason: default package */
/* compiled from: PageBackUtil */
final class ajv {
    public static boolean a(bid bid, String str, ResultType resultType, PageBundle pageBundle, ajr ajr) {
        boolean z = false;
        if (TextUtils.isEmpty(str) || bid == null) {
            return false;
        }
        ArrayList<akc> a = a(bid);
        if (a == null) {
            return false;
        }
        int size = a.size() - 1;
        Class<?> cls = null;
        bid bid2 = null;
        int i = 0;
        while (true) {
            if (size < 0) {
                break;
            }
            i++;
            AbstractBasePage abstractBasePage = ((bun) a.get(size)).a;
            if (ajr.a(str, abstractBasePage)) {
                cls = abstractBasePage.getClass();
                break;
            }
            size--;
            bid2 = abstractBasePage;
        }
        if (cls != null) {
            if (!(resultType == null || bid2 == null)) {
                bid2.setResult(resultType, pageBundle);
            }
            PageBundle pageBundle2 = new PageBundle();
            pageBundle2.setFlags(4, i);
            bid.startPage((Class) cls, pageBundle2);
            z = true;
        }
        return z;
    }

    public static boolean b(bid bid, String str, ResultType resultType, PageBundle pageBundle, ajr ajr) {
        boolean z = false;
        if (TextUtils.isEmpty(str) || bid == null) {
            return false;
        }
        ArrayList<akc> a = a(bid);
        if (a == null) {
            return false;
        }
        int size = a.size() - 1;
        Class<?> cls = null;
        AbstractBasePage abstractBasePage = null;
        int i = 0;
        while (true) {
            if (size < 0) {
                break;
            }
            i++;
            abstractBasePage = ((bun) a.get(size)).a;
            if (ajr.a(str, abstractBasePage) && size > 0) {
                i++;
                cls = ((bun) a.get(size - 1)).a.getClass();
                break;
            }
            size--;
        }
        if (cls != null) {
            if (!(abstractBasePage == null || resultType == null)) {
                abstractBasePage.setResult(resultType, pageBundle);
            }
            PageBundle pageBundle2 = new PageBundle();
            pageBundle2.setFlags(4, i);
            AMapLog.d("AmapPage", "startPage index=".concat(String.valueOf(i)));
            bid.startPage((Class) cls, pageBundle2);
            z = true;
        }
        return z;
    }

    private static ArrayList<akc> a(bid bid) {
        if (bid == null || !(bid instanceof bup)) {
            return null;
        }
        bul mvpActivityContext = ((bup) bid).getMvpActivityContext();
        if (mvpActivityContext == null) {
            return null;
        }
        return mvpActivityContext.d();
    }
}
