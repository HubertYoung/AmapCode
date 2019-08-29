package defpackage;

import com.amap.bundle.blutils.app.ConfigerHelper;

/* renamed from: edi reason: default package */
/* compiled from: RouteCombineURL */
public final class edi {
    public static final String a;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY));
        sb.append("ws/shield/dsp/app/navigation/walk/v2");
        a = sb.toString();
    }
}
