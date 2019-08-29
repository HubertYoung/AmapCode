package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class MpaasNetConfigUtil {
    private static Map<String, String> a = null;
    private static List<String> b = null;

    public static final Map<String, String> getMpaasNetConfigProperties(Context context) {
        if (a != null) {
            return a;
        }
        synchronized (MpaasNetConfigUtil.class) {
            if (a == null) {
                a = b(context);
            }
        }
        return a;
    }

    public static boolean isCrypt(Context context) {
        try {
            String crypt = getMpaasNetConfigProperties(context).get("Crypt");
            if (TextUtils.equals(crypt, "TRUE") || TextUtils.equals(crypt, "true")) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            LogCatUtil.error((String) "MpaasNetConfigUtil", "isCrypt. ex: " + e.toString());
            return false;
        }
    }

    public static boolean isDefaultGlobalCrypt(Context context) {
        try {
            if (TextUtils.equals(getMpaasNetConfigProperties(context).get("DefaultGlobalCrypt"), "false")) {
                return false;
            }
            return true;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MpaasNetConfigUtil", "isDefaultGlobalCrypt ex: " + ex.toString());
            return true;
        }
    }

    public static byte getAsymmetricEncryptMethod(Context context) {
        try {
            String method = getMpaasNetConfigProperties(context).get("RSA/ECC/SM2");
            if (TextUtils.equals(method, "RSA")) {
                return 1;
            }
            if (TextUtils.equals(method, "ECC")) {
                return 2;
            }
            if (TextUtils.equals(method, "SM2")) {
                return 3;
            }
            return 1;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MpaasNetConfigUtil", "getAsymmetricEncryptMethod. ex: " + ex.toString());
            return 1;
        }
    }

    public static String getPublicKey(Context context) {
        try {
            return getMpaasNetConfigProperties(context).get("PubKey");
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MpaasNetConfigUtil", "getPublicKey ex: " + ex.toString());
            return "";
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<java.lang.String> getGWWhiteList(android.content.Context r4) {
        /*
            java.util.List<java.lang.String> r1 = b     // Catch:{ Throwable -> 0x001b }
            if (r1 == 0) goto L_0x0007
            java.util.List<java.lang.String> r1 = b     // Catch:{ Throwable -> 0x001b }
        L_0x0006:
            return r1
        L_0x0007:
            java.lang.Class<com.alipay.mobile.common.transport.utils.MpaasNetConfigUtil> r2 = com.alipay.mobile.common.transport.utils.MpaasNetConfigUtil.class
            monitor-enter(r2)     // Catch:{ Throwable -> 0x001b }
            java.util.List<java.lang.String> r1 = b     // Catch:{ all -> 0x0018 }
            if (r1 != 0) goto L_0x0014
            java.util.List r1 = a(r4)     // Catch:{ all -> 0x0018 }
            b = r1     // Catch:{ all -> 0x0018 }
        L_0x0014:
            monitor-exit(r2)     // Catch:{ all -> 0x0018 }
            java.util.List<java.lang.String> r1 = b     // Catch:{ Throwable -> 0x001b }
            goto L_0x0006
        L_0x0018:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0018 }
            throw r1     // Catch:{ Throwable -> 0x001b }
        L_0x001b:
            r0 = move-exception
            java.lang.String r1 = "MpaasNetConfigUtil"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "getGWWhiteList ex:"
            r2.<init>(r3)
            java.lang.String r3 = r0.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r1, r2, r0)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.MpaasNetConfigUtil.getGWWhiteList(android.content.Context):java.util.List");
    }

    private static List<String> a(Context context) {
        List mGWWhiteList = new ArrayList();
        try {
            String whiteList = getMpaasNetConfigProperties(context).get("GWWhiteList");
            LogCatUtil.printInfo("MpaasNetConfigUtil", "GWWhiteList: " + whiteList);
            if (!TextUtils.isEmpty(whiteList)) {
                for (String gw : whiteList.split(",")) {
                    mGWWhiteList.add(gw);
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MpaasNetConfigUtil", "doGetGWWhiteList ex:" + ex.toString());
        }
        return mGWWhiteList;
    }

    private static final Map<String, String> b(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("mpaas_netconfig.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            if (properties.size() <= 0) {
                return Collections.emptyMap();
            }
            Map map = new HashMap(properties.size());
            for (Entry entry : properties.entrySet()) {
                map.put((String) entry.getKey(), (String) entry.getValue());
            }
            LogCatUtil.info("MpaasNetConfigUtil", "mpaas_netconfig properties loaded， size: " + map.size());
            return map;
        } catch (IOException e) {
            LogCatUtil.warn((String) "MpaasNetConfigUtil", "mpaas_netconfig properties load fail. " + e.toString());
        } catch (Throwable e2) {
            LogCatUtil.error("MpaasNetConfigUtil", "mpaas_netconfig properties load fail. ", e2);
        }
        return Collections.emptyMap();
    }
}
