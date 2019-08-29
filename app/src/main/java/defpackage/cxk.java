package defpackage;

import android.app.Activity;
import android.content.Context;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageContainer;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.bundle.evaluate.api.IEvaluateOperationManager.Channel;

/* renamed from: cxk reason: default package */
/* compiled from: EvaluateVariable */
public class cxk {
    private static cxk d;
    public Context a = null;
    public volatile Activity b = null;
    public volatile boolean c = false;

    public static String g() {
        return "1.b";
    }

    private cxk() {
    }

    public static cxk a() {
        if (d == null) {
            synchronized (cxk.class) {
                try {
                    if (d == null) {
                        d = new cxk();
                    }
                }
            }
        }
        return d;
    }

    public final boolean b() {
        if (!bno.a) {
            return false;
        }
        eog.a(this.a);
        return eog.b();
    }

    public static String c() {
        String str = "";
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof AbstractBasePage) {
            PageContainer pageContainer = ((AbstractBasePage) pageContext).getPageContainer();
            if (pageContainer != null) {
                AbstractBasePage cureentRecordPage = pageContainer.getCureentRecordPage();
                str = cureentRecordPage != null ? cureentRecordPage.getClass().getSimpleName() : "";
            }
        }
        Class<?> topPageClass = AMapPageUtil.getTopPageClass();
        if (topPageClass == null) {
            return "$".concat(String.valueOf(str));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(topPageClass.getSimpleName());
        sb.append("$");
        sb.append(str);
        return sb.toString();
    }

    public static long d() {
        return NetworkParam.getSession();
    }

    public static String e() {
        return NetworkParam.getAdiu();
    }

    public final Channel h() {
        if (!bno.a) {
            return Channel.UT_CHANNEL;
        }
        eog.a(this.a);
        return eog.a();
    }

    public static String f() {
        String str = a.a().a;
        return str == null ? "<n>" : str;
    }
}
