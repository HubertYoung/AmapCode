package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.alipay.mobile.common.patch.PatchUtils;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.taobao.wireless.security.sdk.SecurityGuardManager;
import com.taobao.wireless.security.sdk.SecurityGuardParamContext;
import com.taobao.wireless.security.sdk.securesignature.ISecureSignatureComponent;
import java.io.File;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

public class DjangoUtils {
    public static final int DJANGO_NETWORK_TYPE_2G = 3;
    public static final int DJANGO_NETWORK_TYPE_3G = 4;
    public static final int DJANGO_NETWORK_TYPE_4G = 5;
    public static final int DJANGO_NETWORK_TYPE_ETHERNET = 1;
    public static final int DJANGO_NETWORK_TYPE_NONE = 127;
    public static final int DJANGO_NETWORK_TYPE_UNKNOWN = 0;
    public static final int DJANGO_NETWORK_TYPE_WIFI = 2;
    public static final char EXTENSION_SEPARATOR = '.';
    public static final String EXTENSION_SEPARATOR_STR = Character.toString(EXTENSION_SEPARATOR);
    private static Pattern a = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    public static String genSignature(String appKey, Long timestamp) {
        StringBuilder builder = new StringBuilder(appKey);
        builder.append(timestamp);
        return genSignature(appKey, builder.toString());
    }

    public static String genSignature(String appKey, String content) {
        String sign = "";
        try {
            ISecureSignatureComponent signComp = SecurityGuardManager.getInstance(AppUtils.getApplicationContext()).getSecureSignatureComp();
            HashMap paramMap = new HashMap();
            paramMap.put("INPUT", content);
            SecurityGuardParamContext paramContext = new SecurityGuardParamContext();
            paramContext.appKey = appKey;
            paramContext.paramMap = paramMap;
            paramContext.requestType = 16;
            return signComp.signRequest(paramContext);
        } catch (Exception e) {
            Logger.E((String) "DjangoUtils", (Throwable) e, (String) "genSignature exp ", new Object[0]);
            return sign;
        }
    }

    public static void releaseDownloadResponse(BaseDownResp baseDownResp) {
        if (baseDownResp != null) {
            try {
                releaseConnection(baseDownResp.getMethod(), baseDownResp.getResp());
            } catch (Exception e) {
                Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            }
        }
    }

    public static void releaseConnection(HttpRequestBase method, HttpResponse resp) {
        if (resp != null) {
            HttpClientUtils.consumeQuietly(resp.getEntity());
        }
    }

    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        }
        int extensionPos = filename.lastIndexOf(46);
        if (indexOfLastSeparator(filename) > extensionPos) {
            return -1;
        }
        return extensionPos;
    }

    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        }
        return Math.max(filename.lastIndexOf(47), filename.lastIndexOf(92));
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfExtension(filename);
        if (index == -1) {
            return "";
        }
        return filename.substring(index + 1);
    }

    public static String getMediaDir(Context mContext, String subPath) {
        String mBaseDir;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            File root = mContext.getExternalFilesDir(null);
            if (root != null) {
                mBaseDir = root.getAbsolutePath();
            } else {
                String sdcard = FileUtils.getSDPath();
                if (!TextUtils.isEmpty(sdcard)) {
                    mBaseDir = sdcard + File.separator + PatchUtils.ExtDataTunnel + File.separator + AutoJsonUtils.JSON_FILES;
                } else {
                    mBaseDir = mContext.getCacheDir().getAbsolutePath();
                }
            }
        } else {
            mBaseDir = mContext.getCacheDir().getAbsolutePath();
        }
        String mBaseDir2 = mBaseDir + File.separator + DjangoConstant.STORE_PATH + subPath;
        File dir = new File(mBaseDir2);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        Logger.D(DjangoClient.LOG_TAG, "getBaseDir mBaseDir: " + mBaseDir2, new Object[0]);
        return dir.getAbsolutePath();
    }

    public static int convertNetworkType(int type) {
        switch (type) {
            case 0:
                return 127;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 2;
            case 4:
                return 5;
            default:
                return 0;
        }
    }

    public static boolean isValidIp(String ipAddress) {
        if (TextUtils.isEmpty(ipAddress)) {
            return false;
        }
        return a.matcher(ipAddress).matches();
    }
}
