package com.alipay.mobile.nebulauc.util;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.fgbg.ProcessFgBgWatcher;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommonUtil {
    public static final String TAG = "CommonUtil";
    private static String sUserId = "";
    private static final ThreadLocal<SimpleDateFormat> simpleDateFormat = new ThreadLocal<SimpleDateFormat>() {
        /* access modifiers changed from: protected */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        }
    };

    public static boolean isM40(String version) {
        return !TextUtils.isEmpty(version) && version.startsWith("2.11");
    }

    public static boolean isForeground() {
        Context context = H5Utils.getContext();
        return context != null && ProcessFgBgWatcher.getInstance().isProcessForeground(context);
    }

    public static void dumpDir(File dir) {
        if (dir == null) {
            H5Log.d(TAG, "cannot dump null directory!");
            return;
        }
        dumpFile(dir);
        if (dir.isDirectory()) {
            File[] flist = dir.listFiles();
            if (flist != null) {
                for (File file : flist) {
                    if (file.isDirectory()) {
                        dumpDir(file);
                    } else {
                        dumpFile(file);
                    }
                }
            }
        }
    }

    public static void dumpFile(File file) {
        if (file == null || !file.exists()) {
            H5Log.d(TAG, file + " not exist!");
            return;
        }
        String read = file.canRead() ? UploadQueueMgr.MSGTYPE_REALTIME : "-";
        String write = file.canWrite() ? "w" : "-";
        String meta = read + write + (file.canExecute() ? DictionaryKeys.CTRLXY_X : "-") + (file.isDirectory() ? "d" : "f");
        String lastModified = "";
        SimpleDateFormat formatter = simpleDateFormat.get();
        if (formatter != null) {
            lastModified = formatter.format(new Date(file.lastModified()));
        }
        H5Log.d(TAG, meta + Token.SEPARATOR + file + Token.SEPARATOR + (file.length() / 1024) + "kb " + lastModified);
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if ("js".equalsIgnoreCase(extension)) {
            return "application/javascript";
        }
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static String stringify(Throwable t) {
        if (t == null) {
            return null;
        }
        if (isUploadAllStack()) {
            return Log.getStackTraceString(t);
        }
        return t.toString();
    }

    private static boolean isUploadAllStack() {
        return !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5ConfigUtil.getConfig("h5_upload_allStack"));
    }

    public static boolean isUrlMatch(String regex, String url) {
        return H5PatternHelper.matchRegex(regex, url);
    }

    public static boolean isUrlMatch(String url, JSONArray whiteList) {
        if (whiteList == null || whiteList.isEmpty()) {
            return false;
        }
        for (int i = 0; i < whiteList.size(); i++) {
            String whiteItem = whiteList.getString(i);
            if (!TextUtils.isEmpty(whiteItem) && isUrlMatch(whiteItem, url)) {
                return true;
            }
        }
        return false;
    }

    public static boolean tinyProcessUseSpdy(String appId) {
        String value = H5ConfigUtil.getConfig("h5_use_spdy_initTinyProcess");
        if (!TextUtils.isEmpty(value)) {
            JSONArray jsonArray = H5Utils.parseArray(value);
            if (jsonArray != null && !jsonArray.isEmpty() && !TextUtils.isEmpty(appId) && (jsonArray.contains("all") || jsonArray.contains(appId))) {
                H5Log.d(TAG, " contain appId " + appId + " not use spdy");
                return false;
            }
        }
        return true;
    }

    public static String getUserId() {
        if (H5Utils.isMainProcess()) {
            H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
            if (h5LoginProvider != null) {
                return h5LoginProvider.getUserId();
            }
        } else if (!TextUtils.isEmpty(sUserId)) {
            return sUserId;
        } else {
            H5LoginProvider h5LoginProvider2 = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
            if (h5LoginProvider2 != null) {
                sUserId = h5LoginProvider2.getUserId();
                return sUserId;
            }
        }
        return "";
    }

    public static boolean isMainDoc(int requestType) {
        return requestType == 0 || requestType == 1;
    }

    public static String matchMultimediaImageType(String appId, String url) {
        String value = H5ConfigUtil.getConfig("h5_multiMediaAppList");
        JSONArray multiMediaAppList = null;
        if (!TextUtils.isEmpty(value)) {
            multiMediaAppList = H5Utils.parseArray(value);
        }
        if (multiMediaAppList != null && multiMediaAppList.contains(appId)) {
            String type = H5FileUtil.getMimeType(url);
            if (!TextUtils.isEmpty(type) && (type.contains("jpeg") || type.contains("png") || type.contains("webp"))) {
                return type;
            }
        }
        return null;
    }

    public static boolean isApk(String url) {
        String urlWithoutQuery = getUrlWithOutQuery(url);
        return !TextUtils.isEmpty(urlWithoutQuery) && urlWithoutQuery.endsWith(".apk");
    }

    public static String getUrlWithOutQuery(String url) {
        Uri uri = H5UrlHelper.parseUrl(url);
        if (uri == null) {
            return null;
        }
        String query = uri.getEncodedQuery();
        if (TextUtils.isEmpty(query)) {
            return url;
        }
        return url.replace("?" + query, "");
    }

    public static void logFolderTree(File file, String info) {
        if (file != null) {
            try {
                if (enableLogFolderTree()) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(info);
                    builder.append(Token.SEPARATOR + file.getAbsolutePath());
                    builder.append("\n");
                    if (file.isDirectory()) {
                        appendFolderInfo(file, builder, 1);
                    } else {
                        builder.append(file.getName());
                        builder.append(Token.SEPARATOR);
                        builder.append("file");
                    }
                    H5Log.d(TAG, builder.toString());
                }
            } catch (Exception e) {
                H5Log.e(TAG, "logFolderTree exception : ", e);
            }
        }
    }

    private static void appendFolderInfo(File file, StringBuilder sb, int depth) {
        try {
            File[] fileList = file.listFiles();
            if (fileList.length == 0) {
                for (int d = 1; d <= depth; d++) {
                    sb.append(Token.SEPARATOR);
                }
                sb.append(file.getName());
                sb.append(" is empty");
                return;
            }
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    StringBuilder childBuilder = new StringBuilder();
                    appendFolderInfo(fileList[i], childBuilder, depth + 1);
                    for (int d2 = 1; d2 <= depth; d2++) {
                        sb.append(Token.SEPARATOR);
                    }
                    sb.append("/");
                    sb.append(fileList[i].getName());
                    sb.append(Token.SEPARATOR);
                    sb.append("dir");
                    sb.append("\n");
                    sb.append(childBuilder);
                } else if (fileList[i].isFile()) {
                    for (int d3 = 1; d3 <= depth; d3++) {
                        sb.append(Token.SEPARATOR);
                    }
                    sb.append(fileList[i].getName());
                    sb.append(Token.SEPARATOR);
                    sb.append("file");
                    sb.append("\n");
                } else {
                    for (int d4 = 1; d4 <= depth; d4++) {
                        sb.append("  ");
                    }
                    sb.append(fileList[i].getName());
                    sb.append(Token.SEPARATOR);
                    sb.append("ghost");
                    sb.append("\n");
                }
            }
        } catch (Exception e) {
            H5Log.e(TAG, "appendFolderInfo error", e);
        }
    }

    private static boolean enableLogFolderTree() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableLogFolderTree"))) {
            return true;
        }
        return false;
    }

    public static void sendError(H5BridgeContext bridgeContext, int error, String msg) {
        H5Log.d(TAG, "send error: " + msg);
        if (bridgeContext != null) {
            bridgeContext.sendError(error, msg);
        }
    }
}
