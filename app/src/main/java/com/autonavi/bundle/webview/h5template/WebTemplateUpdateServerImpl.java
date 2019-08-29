package com.autonavi.bundle.webview.h5template;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMemberNames;

@KeepPublicClassMemberNames
@KeepName
public class WebTemplateUpdateServerImpl implements bgx {
    private static final String a;
    private static String b;
    private static String c;
    private static String d;
    private static String e;
    private static String f;
    private static String g;
    private static String h;
    private static String i;
    private static AtomicBoolean j = new AtomicBoolean(false);
    private Context k;

    static {
        if (bno.c) {
            a = "internal_h5.zip";
        } else {
            a = "publish_h5.zip";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getCacheDir().getAbsolutePath());
        sb.append("/");
        b = sb.toString();
    }

    public WebTemplateUpdateServerImpl(Context context) {
        this.k = context.getApplicationContext();
        setBasePath(b);
    }

    public synchronized void setBasePath(String str) {
        b = str;
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append("updates/");
        c = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(b);
        sb2.append("websets/");
        d = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(d);
        sb3.append("m");
        e = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(c);
        sb4.append(".UpdateReady");
        f = sb4.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(c);
        sb5.append("last.zip");
        g = sb5.toString();
        StringBuilder sb6 = new StringBuilder();
        sb6.append(c);
        sb6.append("temp.zip");
        h = sb6.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(b);
        sb7.append("Databases.db");
        i = sb7.toString();
    }

    public void getUrl(final String str, final a aVar) {
        ahm.a(new Runnable() {
            public final void run() {
                String url = WebTemplateUpdateServerImpl.this.getUrl(str);
                if (!TextUtils.isEmpty(url)) {
                    aVar.a(url);
                }
            }
        });
    }

    @Deprecated
    public String getUrl(String str) {
        String str2;
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            int indexOf = str.indexOf("?");
            if (indexOf > 0) {
                str2 = str.substring(0, indexOf);
            } else {
                str2 = null;
            }
            StringBuilder sb = new StringBuilder("file://");
            sb.append(d);
            sb.append(str);
            String sb2 = sb.toString();
            if (!TextUtils.isEmpty(str2)) {
                str = str2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(d);
            sb3.append(str);
            if (!new File(sb3.toString()).getCanonicalPath().startsWith(new File(d).getCanonicalPath())) {
                sb2 = "";
            }
            return sb2;
        } catch (Exception unused) {
            return "";
        }
    }

    public String getTemplateFilePath(String str) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(e);
            sb.append(str);
            String sb2 = sb.toString();
            File file = new File(sb2);
            if (file.exists()) {
                if (file.isFile()) {
                    if (!new File(sb2).getCanonicalPath().startsWith(new File(e).getCanonicalPath())) {
                        sb2 = "";
                    }
                    return sb2;
                }
            }
            return "";
        } catch (Exception e2) {
            kf.a((Throwable) e2);
            return "";
        }
    }

    public void update() {
        Application application = AMapAppGlobal.getApplication();
        File dir = application.getDir("webtemplate", 0);
        if (dir == null) {
            StringBuilder sb = new StringBuilder("/data/data/");
            sb.append(application.getPackageName());
            sb.append("/app_webtemplate");
            dir = new File(sb.toString());
        }
        if (!dir.exists() && !dir.mkdirs()) {
            dir = null;
        }
        if (dir != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(dir.getAbsolutePath());
            sb2.append("/");
            setBasePath(sb2.toString());
        }
    }
}
