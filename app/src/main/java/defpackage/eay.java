package defpackage;

import android.content.Context;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: eay reason: default package */
/* compiled from: ResourceUtil */
public final class eay {
    public static String a(int i) {
        Context appContext = AMapPageUtil.getAppContext();
        return appContext != null ? appContext.getString(i) : "";
    }
}
