package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.amap.bundle.blutils.app.ConfigerHelper;
import java.io.File;
import java.util.Set;

/* renamed from: ajo reason: default package */
/* compiled from: WebTemplateUrlRewriteDelegateImpl */
public class ajo implements emz {
    private String a;
    private String b;
    private boolean c;

    public final boolean a() {
        return this.c;
    }

    public final String b() {
        return this.a;
    }

    public final String c() {
        return this.b;
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Uri parse = Uri.parse(str);
        if (parse != null) {
            try {
                Set<String> queryParameterNames = parse.getQueryParameterNames();
                if (queryParameterNames != null && queryParameterNames.contains(LogItem.MM_C15_K4_TIME) && "god".equalsIgnoreCase(parse.getQueryParameter(LogItem.MM_C15_K4_TIME)) && queryParameterNames.contains("md5") && !TextUtils.isEmpty(parse.getQueryParameter("md5"))) {
                    return true;
                }
            } catch (UnsupportedOperationException unused) {
                return false;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0028 A[SYNTHETIC, Splitter:B:20:0x0028] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0032 A[SYNTHETIC, Splitter:B:26:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x003c A[SYNTHETIC, Splitter:B:31:0x003c] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:17:0x0023=Splitter:B:17:0x0023, B:23:0x002d=Splitter:B:23:0x002d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.io.File r3, java.lang.String r4) {
        /*
            java.util.Properties r0 = new java.util.Properties
            r0.<init>()
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x0022 }
            r2.<init>(r3)     // Catch:{ FileNotFoundException -> 0x002c, IOException -> 0x0022 }
            r0.load(r2)     // Catch:{ FileNotFoundException -> 0x001d, IOException -> 0x001a, all -> 0x0017 }
            r2.close()     // Catch:{ IOException -> 0x0012 }
            goto L_0x0035
        L_0x0012:
            r3 = move-exception
            r3.printStackTrace()
            goto L_0x0035
        L_0x0017:
            r3 = move-exception
            r1 = r2
            goto L_0x003a
        L_0x001a:
            r3 = move-exception
            r1 = r2
            goto L_0x0023
        L_0x001d:
            r3 = move-exception
            r1 = r2
            goto L_0x002d
        L_0x0020:
            r3 = move-exception
            goto L_0x003a
        L_0x0022:
            r3 = move-exception
        L_0x0023:
            r3.printStackTrace()     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x0035
            r1.close()     // Catch:{ IOException -> 0x0012 }
            goto L_0x0035
        L_0x002c:
            r3 = move-exception
        L_0x002d:
            r3.printStackTrace()     // Catch:{ all -> 0x0020 }
            if (r1 == 0) goto L_0x0035
            r1.close()     // Catch:{ IOException -> 0x0012 }
        L_0x0035:
            java.lang.String r3 = r0.getProperty(r4)
            return r3
        L_0x003a:
            if (r1 == 0) goto L_0x0044
            r1.close()     // Catch:{ IOException -> 0x0040 }
            goto L_0x0044
        L_0x0040:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0044:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ajo.a(java.io.File, java.lang.String):java.lang.String");
    }

    public final String b(String str) {
        this.a = str;
        this.b = this.a;
        this.c = false;
        if (TextUtils.isEmpty(this.a)) {
            return this.b;
        }
        Uri parse = Uri.parse(this.a);
        String queryParameter = parse.getQueryParameter("md5");
        String path = parse.getPath();
        String webTemplateDomain = ConfigerHelper.getInstance().getWebTemplateDomain();
        if (!TextUtils.isEmpty(webTemplateDomain) && !TextUtils.isEmpty(path)) {
            try {
                String path2 = Uri.parse(webTemplateDomain).getPath();
                if (!TextUtils.isEmpty(path2)) {
                    path = path.replace(path2, "");
                }
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(queryParameter) || TextUtils.isEmpty(path)) {
            return this.b;
        }
        String str2 = null;
        bgx bgx = (bgx) a.a.a(bgx.class);
        if (bgx != null) {
            str2 = bgx.getTemplateFilePath(path);
        }
        if (!TextUtils.isEmpty(str2)) {
            File file = new File(str2);
            if (file.exists() && file.getParentFile().exists()) {
                File file2 = new File(file.getParentFile().getPath(), "config.mf");
                if (file2.exists() && file2.isFile()) {
                    String a2 = a(file2, "md5");
                    if (!TextUtils.isEmpty(a2) && !TextUtils.isEmpty(a2.trim()) && a2.trim().equals(queryParameter.trim())) {
                        this.b = "file://".concat(String.valueOf(str2));
                        this.c = true;
                    }
                }
            }
        }
        return this.b;
    }
}
