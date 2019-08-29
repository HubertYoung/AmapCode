package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;

/* renamed from: bod reason: default package */
/* compiled from: VersionIndependent */
public final class bod {
    public static String a(String str) {
        return !TextUtils.isEmpty(str) ? AjxFileInfo.getBundleConfigInfo(str, "bizVersion") : "";
    }
}
