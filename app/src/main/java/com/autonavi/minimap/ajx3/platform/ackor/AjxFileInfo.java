package com.autonavi.minimap.ajx3.platform.ackor;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.sdk.authjs.a;
import com.autonavi.minimap.ajx3.loader.AjxPathLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class AjxFileInfo {
    public static int ASSET_INIT_MODE = 0;
    public static int FILE_INIT_MODE = 1;
    public static final int[] LIMIT_VERSION = {9, 0, 0};
    private static final int TYPE_BASE = 0;
    private static final int TYPE_UPDATE = 1;
    public static int URI_TYPE_BROADCAST = 203;
    public static int URI_TYPE_PAGE = 200;
    public static int URI_TYPE_PROVIDER = 202;
    public static int URI_TYPE_SERVICE = 201;
    public static int initMode = ASSET_INIT_MODE;
    public static boolean isDebug = false;
    public static boolean isReadFromAjxFile = true;
    private final String baseAjxFileInAsset;
    private final String defaultBaseJsPathInAssets;
    private String mAjxFilesInfoMap;
    private final String updateAjxFilePath;

    private static native Parcel nativeAddDiffList(String str);

    private static native Parcel nativeCheckAjxFileSign(String str);

    private static native boolean nativeCheckIfPathConfiguredInRouterTable(int i, String str);

    private static native Parcel nativeCheckUpdateAjxFileValid(String str, String str2);

    private static native void nativeDebugSetReadSource(boolean z);

    private static native String nativeGetAjxVersionByFilePath(String str, int i);

    private static native String nativeGetAllAJXVersion();

    private static native String nativeGetAllAJXVersionByType(int i);

    private static native String nativeGetAllBundlesIndexSnapshot();

    private static native String nativeGetBundleConfigInfo(String str, int i, String str2);

    private static native String nativeGetBundleDependencies(String str, String str2);

    private static native byte[] nativeGetBytesByPath(String str, String str2, int i);

    private static native String nativeGetFullPathByUri(String str);

    private static native int[] nativeGetImgDimonsions(String str, String str2, int i);

    private static native int nativeGetLatestDiffIndex(String str);

    private static native String nativeGetLoadedAjxFileVersion(String str, int i);

    private static native Bitmap nativeGetSVGBytesByData(String str, int i, int i2, int i3, Config config);

    private static native Bitmap nativeGetSVGBytesByPath(String str, String str2, int i, int i2, int i3, int i4, Config config);

    private static native boolean nativeIsFileExists(String str, String str2, int i);

    public static String getExtraInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nLimitVersion: ");
        for (int append : LIMIT_VERSION) {
            sb.append(append);
        }
        return sb.toString();
    }

    public AjxFileInfo(String str, @Nullable String str2, String str3, String str4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("baseAjxFileInAsset or defaultBaseJsPathInAssets should not be null.");
        }
        this.baseAjxFileInAsset = str;
        this.updateAjxFilePath = str2;
        this.defaultBaseJsPathInAssets = str3;
        this.mAjxFilesInfoMap = str4;
    }

    public String getAjxFileInfo() {
        return this.mAjxFilesInfoMap;
    }

    public void updateAjxFileInfo(String str) {
        this.mAjxFilesInfoMap = str;
    }

    public String getBaseAjxFileInAsset() {
        return this.baseAjxFileInAsset;
    }

    public String getUpdateAjxFilePath() {
        return this.updateAjxFilePath;
    }

    public String getDefaultBaseJsPath() {
        return this.defaultBaseJsPathInAssets;
    }

    public static byte[] getFileDataByPath(@NonNull String str) {
        String bundleName = getBundleName(str);
        if (TextUtils.isEmpty(bundleName)) {
            return null;
        }
        return nativeGetBytesByPath(bundleName, str, getLatestPatchIndex(bundleName));
    }

    public static String getBundleName(String str) {
        if (TextUtils.isEmpty(str) || !str.contains("/")) {
            return "";
        }
        if (str.startsWith(AjxPathLoader.DOMAIN)) {
            str = str.replace(AjxPathLoader.DOMAIN, "");
        }
        String[] split = str.split("/");
        return split.length > 0 ? split[0] : "";
    }

    public static byte[] getFileDataByPath(@NonNull String str, int i) {
        String bundleName = getBundleName(str);
        if (TextUtils.isEmpty(bundleName)) {
            return null;
        }
        return nativeGetBytesByPath(bundleName, str, i);
    }

    public static Bitmap getSVGByteByPath(@NonNull String str, int i, int i2, int i3, int i4) {
        String bundleName = getBundleName(str);
        if (TextUtils.isEmpty(bundleName)) {
            return null;
        }
        return nativeGetSVGBytesByPath(bundleName, str, i, i2, i3, i4, Config.ARGB_8888);
    }

    public static Bitmap getSVGByteByData(@NonNull String str, int i, int i2, int i3) {
        return nativeGetSVGBytesByData(str, i, i2, i3, Config.ARGB_8888);
    }

    public static boolean isFileExists(@NonNull String str) {
        String bundleName = getBundleName(str);
        if (TextUtils.isEmpty(bundleName)) {
            return false;
        }
        return isFileExists(str, getLatestPatchIndex(bundleName));
    }

    public static boolean isFileExists(@NonNull String str, int i) {
        String bundleName = getBundleName(str);
        if (TextUtils.isEmpty(bundleName)) {
            return false;
        }
        return nativeIsFileExists(bundleName, str, i);
    }

    public static String getAllAjxFileVersion() {
        return nativeGetAllAJXVersion();
    }

    public static String getAllAjxFileBaseVersion() {
        return nativeGetAllAJXVersionByType(0);
    }

    public static String getAllAjxLatestPatchVersion() {
        return nativeGetAllAJXVersionByType(1);
    }

    public static String getBaseAjxFileVersion() {
        return getAllAjxFileBaseVersion();
    }

    public static String getBaseAjxFileVersion(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String nativeGetLoadedAjxFileVersion = nativeGetLoadedAjxFileVersion(str, 0);
        return nativeGetLoadedAjxFileVersion == null ? "" : nativeGetLoadedAjxFileVersion;
    }

    public static String getLoadedDiffAjxFileVersion(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String nativeGetLoadedAjxFileVersion = nativeGetLoadedAjxFileVersion(str, 1);
        return nativeGetLoadedAjxFileVersion == null ? "" : nativeGetLoadedAjxFileVersion;
    }

    public static Parcel checkAjxFileSign(String str) {
        return nativeCheckAjxFileSign(str);
    }

    public static Parcel checkDiffAjxFileValid(String str, String str2) {
        return nativeCheckUpdateAjxFileValid(str, str2);
    }

    public static int[] getImgDimonsions(String str, int i) {
        String bundleName = getBundleName(str);
        if (TextUtils.isEmpty(bundleName)) {
            return null;
        }
        return nativeGetImgDimonsions(bundleName, str, i);
    }

    public static int getLatestPatchIndex(String str) {
        return nativeGetLatestDiffIndex(str);
    }

    public static Parcel addPatch(String str, String str2) {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(a.d, str);
            jSONObject.put("fileName", str2);
        } catch (JSONException unused) {
        }
        jSONArray.put(jSONObject);
        return addPatchList(jSONArray.toString());
    }

    public static Parcel addPatchList(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return nativeAddDiffList(str);
    }

    public static void debugSetReadSource(boolean z) {
        isReadFromAjxFile = z;
        nativeDebugSetReadSource(z);
    }

    public static String getFullPathByUri(String str) {
        return nativeGetFullPathByUri(str);
    }

    public static boolean checkIfPathConfiguredInRouterTable(int i, String str) {
        return nativeCheckIfPathConfiguredInRouterTable(i, str);
    }

    public static String getAjxVersionByFilePath(String str, int i) {
        return nativeGetAjxVersionByFilePath(str, i);
    }

    public static String getBundleDependencies(String str, String str2) {
        return (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) ? "" : nativeGetBundleDependencies(str, str2);
    }

    public static String getBundleConfigInfo(String str, String str2) {
        return (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) ? "" : nativeGetBundleConfigInfo(str, getLatestPatchIndex(str), str2);
    }

    public static String getBundleConfigInfo(String str, int i, String str2) {
        return (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || i < 0) ? "" : nativeGetBundleConfigInfo(str, i, str2);
    }

    public static String getAllBundlesIndexSnapshot() {
        return nativeGetAllBundlesIndexSnapshot();
    }
}
