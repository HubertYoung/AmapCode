package com.alipay.mobile.tinyappcommon.utils.pkg;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.InputStream;

public final class TinyAppPkgUtils {
    private static final String TAG = "TinyAppPkgUtils";

    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean exists(com.alipay.mobile.h5container.api.H5Page r16, java.lang.String r17) {
        /*
            if (r16 == 0) goto L_0x0008
            android.os.Bundle r14 = r16.getParams()
            if (r14 != 0) goto L_0x000a
        L_0x0008:
            r14 = 0
        L_0x0009:
            return r14
        L_0x000a:
            if (r17 == 0) goto L_0x0012
            boolean r14 = android.text.TextUtils.isEmpty(r17)
            if (r14 == 0) goto L_0x0014
        L_0x0012:
            r14 = 0
            goto L_0x0009
        L_0x0014:
            android.os.Bundle r9 = r16.getParams()
            java.lang.Class<com.alipay.mobile.nebula.provider.H5AppProvider> r14 = com.alipay.mobile.nebula.provider.H5AppProvider.class
            java.lang.String r14 = r14.getName()
            java.lang.Object r8 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r14)
            com.alipay.mobile.nebula.provider.H5AppProvider r8 = (com.alipay.mobile.nebula.provider.H5AppProvider) r8
            if (r8 != 0) goto L_0x0028
            r14 = 0
            goto L_0x0009
        L_0x0028:
            java.lang.String r1 = com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils.getAppId(r9)
            java.lang.String r14 = "appVersion"
            java.lang.String r14 = com.alipay.mobile.nebula.util.H5Utils.getString(r9, r14)
            java.lang.String r6 = r8.getInstallPath(r1, r14)
            boolean r14 = android.text.TextUtils.isEmpty(r6)
            if (r14 == 0) goto L_0x003e
            r14 = 0
            goto L_0x0009
        L_0x003e:
            java.io.File r10 = new java.io.File
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.StringBuilder r14 = r14.append(r6)
            java.lang.String r15 = "/"
            java.lang.StringBuilder r14 = r14.append(r15)
            java.lang.StringBuilder r14 = r14.append(r1)
            java.lang.String r15 = ".tar"
            java.lang.StringBuilder r14 = r14.append(r15)
            java.lang.String r14 = r14.toString()
            r10.<init>(r14)
            boolean r14 = r10.exists()
            if (r14 != 0) goto L_0x0068
            r14 = 0
            goto L_0x0009
        L_0x0068:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r0 = r17
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r15 = "/"
            java.lang.StringBuilder r14 = r14.append(r15)
            java.lang.String r7 = r14.toString()
            r12 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00ee }
            r5.<init>(r10)     // Catch:{ Throwable -> 0x00ee }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x00ee }
            r2.<init>(r5)     // Catch:{ Throwable -> 0x00ee }
            com.alipay.mobile.nebula.util.tar.TarInputStream r13 = new com.alipay.mobile.nebula.util.tar.TarInputStream     // Catch:{ Throwable -> 0x00ee }
            r13.<init>(r2)     // Catch:{ Throwable -> 0x00ee }
        L_0x008d:
            com.alipay.mobile.nebula.util.tar.TarEntry r11 = r13.getNextEntry()     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            if (r11 == 0) goto L_0x00e7
            java.lang.String r4 = r11.getName()     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            r0 = r17
            boolean r14 = android.text.TextUtils.equals(r4, r0)     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            if (r14 != 0) goto L_0x00b6
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            java.lang.String r15 = "/"
            r14.<init>(r15)     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            java.lang.StringBuilder r14 = r14.append(r4)     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            java.lang.String r14 = r14.toString()     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            r0 = r17
            boolean r14 = android.text.TextUtils.equals(r14, r0)     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            if (r14 == 0) goto L_0x00bc
        L_0x00b6:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r13)
            r14 = 1
            goto L_0x0009
        L_0x00bc:
            if (r4 == 0) goto L_0x008d
            java.lang.String r14 = "/"
            boolean r14 = r4.endsWith(r14)     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            if (r14 == 0) goto L_0x008d
            boolean r14 = android.text.TextUtils.equals(r4, r7)     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            if (r14 != 0) goto L_0x00e1
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            java.lang.String r15 = "/"
            r14.<init>(r15)     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            java.lang.StringBuilder r14 = r14.append(r4)     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            java.lang.String r14 = r14.toString()     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            boolean r14 = android.text.TextUtils.equals(r14, r7)     // Catch:{ Throwable -> 0x0102, all -> 0x00ff }
            if (r14 == 0) goto L_0x008d
        L_0x00e1:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r13)
            r14 = 1
            goto L_0x0009
        L_0x00e7:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r13)
            r12 = r13
        L_0x00eb:
            r14 = 0
            goto L_0x0009
        L_0x00ee:
            r3 = move-exception
        L_0x00ef:
            java.lang.String r14 = "TinyAppPkgUtils"
            java.lang.String r15 = "parse package exception"
            com.alipay.mobile.nebula.util.H5Log.e(r14, r15, r3)     // Catch:{ all -> 0x00fa }
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r12)
            goto L_0x00eb
        L_0x00fa:
            r14 = move-exception
        L_0x00fb:
            com.alipay.mobile.nebula.util.H5IOUtils.closeQuietly(r12)
            throw r14
        L_0x00ff:
            r14 = move-exception
            r12 = r13
            goto L_0x00fb
        L_0x0102:
            r3 = move-exception
            r12 = r13
            goto L_0x00ef
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.tinyappcommon.utils.pkg.TinyAppPkgUtils.exists(com.alipay.mobile.h5container.api.H5Page, java.lang.String):boolean");
    }

    public static byte[] getData(H5Page h5Page, String path) {
        byte[] bArr = null;
        if (!(h5Page == null || h5Page.getParams() == null)) {
            String host = H5Utils.getString(h5Page.getParams(), (String) H5Param.ONLINE_HOST);
            if (!path.startsWith("http") || path.contains(host)) {
                String realPath = getAbsoluteUrl(path, h5Page.getParams());
                H5Log.d(TAG, "getFromPkg realPath " + realPath);
                H5Session h5Session = h5Page.getSession();
                if (h5Session != null) {
                    H5ContentProvider h5ContentProvider = h5Session.getWebProvider();
                    if (h5ContentProvider != null) {
                        WebResourceResponse webResourceResponse = h5ContentProvider.getContent(realPath, true);
                        if (webResourceResponse != null) {
                            InputStream inputStream = null;
                            try {
                                inputStream = webResourceResponse.getData();
                                if (inputStream != null) {
                                    bArr = H5IOUtils.inputToByte(inputStream);
                                } else {
                                    H5IOUtils.closeQuietly(inputStream);
                                }
                            } catch (Throwable throwable) {
                                H5Log.e((String) TAG, throwable);
                            } finally {
                                H5IOUtils.closeQuietly(inputStream);
                            }
                        }
                    }
                }
            }
        }
        return bArr;
    }

    private static String getAbsoluteUrl(String oriUrl, Bundle startParams) {
        String entryUrl = H5Utils.getString(startParams, (String) "url");
        if (!TextUtils.isEmpty(entryUrl)) {
            return H5Utils.getAbsoluteUrlV2(entryUrl, oriUrl, null);
        }
        return oriUrl;
    }
}
