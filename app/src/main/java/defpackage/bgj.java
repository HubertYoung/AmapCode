package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.autonavi.bundle.vui.model.SchemeOpenPageModel$1;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.uc.webview.export.internal.SDKFactory;

/* renamed from: bgj reason: default package */
/* compiled from: SchemeOpenPageModel */
public final class bgj extends bgd {
    public Handler a = new Handler(Looper.getMainLooper());
    private final String b = "androidamap://openFeature?featureName=openTrafficEdog";

    public final String a() {
        return "openPageViaSchema";
    }

    public final boolean a(bgb bgb, bfb bfb) {
        if (bgb != null) {
            String str = bgb.k;
            bfh.a("VUI_JAVA", "SchemeOpenPageModel scheme:".concat(String.valueOf(str)));
            if (!TextUtils.isEmpty(str)) {
                if (!TextUtils.isEmpty(str) && str.startsWith("androidamap://openFeature?featureName=openTrafficEdog")) {
                    d.a.d();
                }
                Activity activity = AMapPageUtil.getPageContext().getActivity();
                if (activity == null || !(activity instanceof brr)) {
                    a(bgb, (String) "SchemeOpenPageModel context not MapHostActivity");
                    d.a.a(bgb.a, (int) SDKFactory.getCoreType, (String) null);
                } else {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(str));
                    ((brr) activity).a(intent, new SchemeOpenPageModel$1(this, bgb));
                }
            } else {
                a(bgb, (String) "SchemeOpenPageModel scheme is null");
                d.a.a(bgb.a, (int) SDKFactory.getCoreType, (String) null);
            }
            return true;
        }
        a((bgb) null, (String) "SchemeOpenPageModel cmd is null");
        return false;
    }

    public static void a(bgb bgb, String str) {
        String str2 = bgb == null ? "" : bgb.f;
        bfq bfq = b.a;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" taskId=");
        sb.append(str2);
        bfp.a(bfq, 4, sb.toString());
    }
}
