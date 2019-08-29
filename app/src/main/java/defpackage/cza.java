package defpackage;

import android.app.Application;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;

/* renamed from: cza reason: default package */
/* compiled from: PastePwdUtil */
public final class cza {
    public static String a() {
        boolean z;
        Application application = AMapAppGlobal.getApplication();
        if (application == null) {
            return "";
        }
        ClipboardManager clipboardManager = (ClipboardManager) application.getSystemService("clipboard");
        try {
            z = clipboardManager.hasPrimaryClip();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            AMapLog.d("PastePwdUtil", sb.toString());
            z = false;
        }
        if (!z || clipboardManager.getPrimaryClip() == null || clipboardManager.getPrimaryClipDescription() == null) {
            return "";
        }
        if (clipboardManager.getPrimaryClipDescription().hasMimeType("text/plain")) {
            Item itemAt = clipboardManager.getPrimaryClip().getItemAt(0);
            if (itemAt == null || itemAt.getText() == null || !(itemAt.getText() instanceof String)) {
                return "";
            }
            return (String) itemAt.getText();
        } else if (!clipboardManager.getPrimaryClipDescription().hasMimeType("text/html") || VERSION.SDK_INT < 16) {
            return "";
        } else {
            Item itemAt2 = clipboardManager.getPrimaryClip().getItemAt(0);
            if (itemAt2 == null || TextUtils.isEmpty(itemAt2.getHtmlText())) {
                return "";
            }
            return itemAt2.getHtmlText();
        }
    }
}
