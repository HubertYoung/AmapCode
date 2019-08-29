package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.framework.service.common.SchemeService;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.uc.webview.export.WebView;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/* renamed from: ajq reason: default package */
/* compiled from: WebviewSchemeCheckServiceImpl */
public class ajq implements bnt {
    private SharedPreferences a = AMapPageUtil.getAppContext().getSharedPreferences("WebViewSchemeConfig", 0);
    private Set<String> b;

    public final String a() {
        return this.a.getString("version", "");
    }

    public final void a(Set<String> set, String str) {
        Editor edit = this.a.edit();
        edit.putStringSet("data", set);
        edit.putString("version", str);
        edit.apply();
    }

    @Nullable
    private Set<String> b() {
        return this.a.getStringSet("data", null);
    }

    public final boolean a(final WebView webView, String str) {
        Intent intent;
        if (!a(str)) {
            return false;
        }
        if (str.startsWith("intent")) {
            try {
                intent = Intent.parseUri(str, 1);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                if (VERSION.SDK_INT >= 15) {
                    intent.setSelector(null);
                }
            } catch (URISyntaxException unused) {
                return false;
            }
        } else {
            intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        }
        try {
            Activity topActivity = AMapAppGlobal.getTopActivity();
            if (topActivity != null) {
                topActivity.startActivity(intent);
            }
        } catch (Exception unused2) {
            if (webView.getContentHeight() <= 0) {
                webView.postDelayed(new Runnable() {
                    public final void run() {
                        webView.clearHistory();
                    }
                }, 1000);
            }
        }
        return true;
    }

    public final boolean a(final android.webkit.WebView webView, String str) {
        Intent intent;
        if (!a(str)) {
            return false;
        }
        if (str.startsWith("intent")) {
            try {
                intent = Intent.parseUri(str, 1);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                if (VERSION.SDK_INT >= 15) {
                    intent.setSelector(null);
                }
            } catch (URISyntaxException unused) {
                return false;
            }
        } else {
            intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        }
        try {
            Activity topActivity = AMapAppGlobal.getTopActivity();
            if (topActivity != null) {
                topActivity.startActivity(intent);
            }
        } catch (Exception unused2) {
            if (webView.getContentHeight() <= 0) {
                webView.postDelayed(new Runnable() {
                    public final void run() {
                        webView.clearHistory();
                    }
                }, 1000);
            }
        }
        return true;
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (this.b == null) {
            this.b = b();
        }
        if (this.b == null) {
            for (String startsWith : c()) {
                if (str.startsWith(startsWith)) {
                    return true;
                }
            }
            return false;
        }
        for (String startsWith2 : this.b) {
            if (str.startsWith(startsWith2)) {
                return true;
            }
        }
        return false;
    }

    private static Set<String> c() {
        HashSet hashSet = new HashSet();
        hashSet.add("amapuri");
        hashSet.add("androidamap");
        hashSet.add(SchemeService.SCHEME_REVEAL);
        return hashSet;
    }

    public final boolean b(String str) {
        if (!TextUtils.isEmpty(str) && !str.startsWith("http") && !str.startsWith("file") && !str.startsWith("ftp")) {
            return true;
        }
        return false;
    }
}
