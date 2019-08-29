package com.autonavi.minimap.ajx3.upgrade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.minimap.ajx3.AjxInit;
import com.autonavi.minimap.ajx3.TarGzipUtil;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Ajx3BundleDataManager {
    private static final int APP_STATUS_DOWNGRADE = 1;
    private static final int APP_STATUS_NONE = 0;
    private static final int APP_STATUS_UPGRADE = -1;
    private static final int APP_STATUS_UPGRADE_WITH_BASE_CHANGED = -2;
    private static final int[] BASE_VERSION = {9, 0, 0, 1};
    private static final String ENGINE_VERSION = "engineVersion";
    private static final String NAME = "name";
    private static final String SEPARATOR = ";";
    private static final String TAG = "Ajx3UpgradeManager";
    private String engineVersion;
    private HashMap<String, Ajx3BundleFileInfo> mBundleFileInfoMap = new HashMap<>();
    private BundleNameListUpdateListener mBundleListUpdateListener;
    private List<String> mBundleNameList = new ArrayList();
    private Context mContext;
    File mDiffDir;
    private File mDiffTmpDir;
    private List<String> mJsBundleNameList = new ArrayList();

    interface BundleNameListUpdateListener {
        void onBundleNameUpdated(List<String> list);

        void onJsBundleNameUpdate(List<String> list);
    }

    Ajx3BundleDataManager(Context context) {
        this.mContext = context;
        this.mDiffDir = new File(context.getFilesDir(), "ajx_diff");
        this.mDiffTmpDir = new File(context.getCacheDir(), "ajx_diff_tmp");
        init();
    }

    /* access modifiers changed from: 0000 */
    public List<String> getBundleNameList() {
        return this.mBundleNameList;
    }

    /* access modifiers changed from: 0000 */
    public List<String> getJsBundleNameList() {
        return this.mJsBundleNameList;
    }

    /* access modifiers changed from: 0000 */
    public HashMap<String, Ajx3BundleFileInfo> getBundleFileInfoMap() {
        return this.mBundleFileInfoMap;
    }

    private String getEngineVersion() {
        if (TextUtils.isEmpty(this.engineVersion)) {
            this.engineVersion = Ajx3SpUtil.getAjxEngineVersion(this.mContext);
        }
        return this.engineVersion;
    }

    /* access modifiers changed from: 0000 */
    public Ajx3BundleFileInfo getBundleFileInfo(String str) {
        return this.mBundleFileInfoMap.get(str);
    }

    /* access modifiers changed from: 0000 */
    public String getFileInfoMap() {
        return Ajx3SpUtil.getBundleInfo(this.mContext);
    }

    /* access modifiers changed from: 0000 */
    public String reInitBundleInfo() {
        deleteAllDownloadBundle();
        readBaseAjxInfo(this.mContext);
        Ajx3SpUtil.commit(this.mContext);
        String bundleInfo = Ajx3SpUtil.getBundleInfo(this.mContext);
        AjxInit.updateAjxInfo(bundleInfo);
        return bundleInfo;
    }

    public void deleteAllDownloadBundle() {
        clearAllCaches();
        Ajx3SpUtil.setBundleNames(this.mContext, "");
        Ajx3SpUtil.setBundleInfo(this.mContext, "");
        Ajx3SpUtil.setLastCheckTask(this.mContext, "");
        Ajx3SpUtil.setLastCheckResponse(this.mContext, "");
        Ajx3SpUtil.setWebConfigInfo(this.mContext, "");
    }

    private int getAppOverwriteInstallStatus(String str) {
        String appVersion = Ajx3SpUtil.getAppVersion(this.mContext);
        if (TextUtils.isEmpty(appVersion) || TextUtils.isEmpty(str)) {
            return 1;
        }
        if (appVersion.equals(str)) {
            return 0;
        }
        return compareAppVersionName(appVersion, str);
    }

    private int compareAppVersionName(@NonNull String str, @NonNull String str2) {
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int min = Math.min(split.length, split2.length);
        int i = 1;
        if (min <= 0) {
            return 1;
        }
        int i2 = 0;
        while (true) {
            if (i2 >= min) {
                break;
            }
            String removeZero = removeZero(split2[i2].trim());
            String removeZero2 = removeZero(split[i2].trim());
            if (!TextUtils.isEmpty(removeZero) && !TextUtils.isEmpty(removeZero2)) {
                try {
                    int parseInt = Integer.parseInt(removeZero);
                    int parseInt2 = Integer.parseInt(removeZero2);
                    if (parseInt > parseInt2) {
                        i = -1;
                        break;
                    } else if (parseInt < parseInt2) {
                        break;
                    }
                } catch (NumberFormatException unused) {
                    continue;
                }
            }
            i2++;
        }
        if (i == -1) {
            if (min > BASE_VERSION.length) {
                min = BASE_VERSION.length;
            }
            for (int i3 = 0; i3 < min; i3++) {
                String removeZero3 = removeZero(split[i3].trim());
                if (!TextUtils.isEmpty(removeZero3)) {
                    try {
                        if (Integer.parseInt(removeZero3) < BASE_VERSION[i3]) {
                            return -2;
                        }
                    } catch (NumberFormatException unused2) {
                        return -2;
                    }
                }
            }
        }
        return i;
    }

    private String removeZero(String str) {
        while (str.length() > 1 && str.startsWith("0")) {
            str = str.substring(1);
        }
        return str;
    }

    /* access modifiers changed from: 0000 */
    public void onCancel() {
        saveToSP();
    }

    private void saveToSP() {
        try {
            StringBuilder sb = new StringBuilder();
            for (String next : this.mBundleNameList) {
                if (this.mBundleFileInfoMap.get(next) != null) {
                    if (sb.length() <= 0) {
                        sb.append(next);
                    } else {
                        sb.append(";");
                        sb.append(next);
                    }
                }
            }
            Ajx3SpUtil.setBundleNames(this.mContext, sb.toString().trim());
            Ajx3SpUtil.setBundleInfo(this.mContext, Ajx3BundleFileInfo.mapToString(this.mBundleNameList, this.mBundleFileInfoMap));
        } catch (Exception unused) {
        }
        Ajx3SpUtil.commit(this.mContext);
    }

    private void saveJsBundleToSP() {
        if (this.mJsBundleNameList.size() != 0) {
            try {
                JSONArray jSONArray = new JSONArray();
                for (String put : this.mJsBundleNameList) {
                    jSONArray.put(put);
                }
                Ajx3SpUtil.setJsBundleNames(this.mContext, jSONArray.toString());
            } catch (Exception unused) {
            }
        }
    }

    private void clearBundleCaches() {
        for (String str : this.mBundleNameList) {
            Ajx3BundleFileInfo ajx3BundleFileInfo = this.mBundleFileInfoMap.get(str);
            if (ajx3BundleFileInfo != null) {
                ajx3BundleFileInfo.clearBundleCaches(false);
            }
        }
    }

    private boolean readJsBundleNames() {
        String jsBundleNames = Ajx3SpUtil.getJsBundleNames(this.mContext);
        if (TextUtils.isEmpty(jsBundleNames)) {
            return false;
        }
        try {
            JSONArray jSONArray = new JSONArray(jsBundleNames);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                String optString = jSONArray.optString(i, null);
                if (optString != null) {
                    this.mJsBundleNameList.add(optString);
                }
            }
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    private boolean readAjxFileInfoFromSP() {
        String bundleNames = Ajx3SpUtil.getBundleNames(this.mContext);
        if (TextUtils.isEmpty(bundleNames)) {
            return false;
        }
        String[] split = bundleNames.split(";");
        if (split.length <= 0) {
            return false;
        }
        for (String str : split) {
            if (!TextUtils.isEmpty(str)) {
                this.mBundleNameList.add(str);
            }
        }
        if (this.mBundleNameList.size() <= 0) {
            return false;
        }
        String bundleInfo = Ajx3SpUtil.getBundleInfo(this.mContext);
        if (TextUtils.isEmpty(bundleInfo)) {
            return false;
        }
        Ajx3BundleFileInfo.stringToMap(this.mContext, bundleInfo, this.mBundleFileInfoMap);
        if (this.mBundleFileInfoMap.size() > 0) {
            return true;
        }
        return false;
    }

    private static byte[] decodeAssetResData(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = open.read(bArr);
                if (read >= 0) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    open.close();
                    return byteArray;
                }
            }
        } catch (IOException unused) {
            return null;
        } catch (OutOfMemoryError unused2) {
            return null;
        }
    }

    private boolean mergeBundleName(Context context) {
        byte[] decodeAssetResData = decodeAssetResData(context, "ajx_file_base/bundle_info.json");
        if (decodeAssetResData == null) {
            return false;
        }
        String str = new String(decodeAssetResData);
        HashMap<String, Ajx3BundleFileInfo> hashMap = new HashMap<>();
        ArrayList arrayList = new ArrayList();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(ENGINE_VERSION)) {
                this.engineVersion = jSONObject.getString(ENGINE_VERSION);
            }
            if (!jSONObject.has("bundles")) {
                return false;
            }
            String string = jSONObject.getString("bundles");
            if (TextUtils.isEmpty(string)) {
                return false;
            }
            JSONArray jSONArray = new JSONArray(string);
            int length = jSONArray.length();
            if (length <= 0) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                String string2 = jSONArray.getString(i);
                if (!TextUtils.isEmpty(string2)) {
                    JSONObject jSONObject2 = new JSONObject(string2);
                    if (jSONObject2.has("name") && jSONObject2.has("fileName")) {
                        String string3 = jSONObject2.getString("name");
                        String string4 = jSONObject2.getString("fileName");
                        String string5 = jSONObject2.has("env") ? jSONObject2.getString("env") : "all";
                        if (!TextUtils.isEmpty(string4) && !TextUtils.isEmpty(string3)) {
                            arrayList.add(string3);
                            Ajx3BundleFileInfo ajx3BundleFileInfo = new Ajx3BundleFileInfo(this.mContext, string3, string4, string5);
                            ajx3BundleFileInfo.mLastCheckTime = 0;
                            hashMap.put(ajx3BundleFileInfo.bundleName, ajx3BundleFileInfo);
                        }
                    }
                }
            }
            for (String next : this.mBundleNameList) {
                Ajx3BundleFileInfo ajx3BundleFileInfo2 = this.mBundleFileInfoMap.get(next);
                if (ajx3BundleFileInfo2 != null) {
                    if (arrayList.contains(next)) {
                        Ajx3BundleFileInfo ajx3BundleFileInfo3 = hashMap.get(next);
                        if (ajx3BundleFileInfo3 != null) {
                            ajx3BundleFileInfo3.mLastCheckTime = ajx3BundleFileInfo2.mLastCheckTime;
                            ajx3BundleFileInfo3.handleMerge(ajx3BundleFileInfo2);
                        }
                    } else if (ajx3BundleFileInfo2.hasBase()) {
                        ajx3BundleFileInfo2.clearAllPatch();
                    } else if (ajx3BundleFileInfo2.isPatchExit()) {
                        arrayList.add(next);
                        hashMap.put(next, ajx3BundleFileInfo2);
                    } else {
                        StringBuilder sb = new StringBuilder(" it is web , but patch is not exit: ");
                        sb.append(ajx3BundleFileInfo2.bundleName);
                        sb.append(" ; ");
                        sb.append(ajx3BundleFileInfo2.toString());
                    }
                }
            }
            this.mBundleNameList.clear();
            this.mBundleNameList = arrayList;
            this.mBundleFileInfoMap = hashMap;
            Ajx3SpUtil.setAjxEngineVersion(context, this.engineVersion);
            saveToSP();
            return true;
        } catch (JSONException unused) {
        }
    }

    private void readBaseAjxInfo(Context context) {
        byte[] decodeAssetResData = decodeAssetResData(context, "ajx_file_base/bundle_info.json");
        if (decodeAssetResData != null) {
            String str = new String(decodeAssetResData);
            StringBuilder sb = new StringBuilder();
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has(ENGINE_VERSION)) {
                    this.engineVersion = jSONObject.getString(ENGINE_VERSION);
                }
                if (jSONObject.has("bundles")) {
                    String string = jSONObject.getString("bundles");
                    if (!TextUtils.isEmpty(string)) {
                        JSONArray jSONArray = new JSONArray(string);
                        int length = jSONArray.length();
                        if (length > 0) {
                            for (int i = 0; i < length; i++) {
                                String string2 = jSONArray.getString(i);
                                if (!TextUtils.isEmpty(string2)) {
                                    JSONObject jSONObject2 = new JSONObject(string2);
                                    if (jSONObject2.has("name") && jSONObject2.has("fileName")) {
                                        String string3 = jSONObject2.getString("name");
                                        String string4 = jSONObject2.getString("fileName");
                                        String string5 = jSONObject2.has("env") ? jSONObject2.getString("env") : "all";
                                        if (!TextUtils.isEmpty(string4) && !TextUtils.isEmpty(string3)) {
                                            if (sb.length() <= 0) {
                                                sb.append(string3);
                                            } else {
                                                sb.append(";");
                                                sb.append(string3);
                                            }
                                            this.mBundleNameList.add(string3);
                                            Ajx3BundleFileInfo ajx3BundleFileInfo = new Ajx3BundleFileInfo(this.mContext, string3, string4, string5);
                                            ajx3BundleFileInfo.mLastCheckTime = 0;
                                            this.mBundleFileInfoMap.put(ajx3BundleFileInfo.bundleName, ajx3BundleFileInfo);
                                        }
                                    }
                                }
                            }
                            if (sb.length() > 0) {
                                Ajx3SpUtil.setBundleNames(context, sb.toString().trim());
                            }
                            Ajx3SpUtil.setBundleInfo(context, Ajx3BundleFileInfo.mapToString(this.mBundleNameList, this.mBundleFileInfoMap));
                            Ajx3SpUtil.setAjxEngineVersion(context, this.engineVersion);
                        }
                    }
                }
            } catch (JSONException unused) {
            }
        }
    }

    private void clearTemp() {
        File[] listFiles = this.mDiffTmpDir.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    ahd.a(file);
                }
            }
        }
    }

    private void clearAllCaches() {
        File[] listFiles = this.mDiffDir.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile()) {
                    Ajx3SpUtil.remoevPatchInvalidTime(this.mContext, file.getName());
                    file.delete();
                } else if (file.isDirectory()) {
                    ahd.a(file);
                }
            }
        }
        clearTemp();
    }

    /* access modifiers changed from: 0000 */
    public boolean onDownloadPatchReady(List<Ajx3BundlePatchInfo> list, boolean z, String str) {
        if (list == null || list.size() <= 0) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        long currentTimeMillis = System.currentTimeMillis();
        boolean z2 = false;
        for (Ajx3BundlePatchInfo next : list) {
            if (!TextUtils.isEmpty(next.bundleName)) {
                sb.append(next.bundleName);
                sb.append(":");
                sb.append(AjxFileInfo.getLoadedDiffAjxFileVersion(next.bundleName));
                sb.append(";");
                if (this.mBundleNameList.contains(next.bundleName)) {
                    Ajx3BundleFileInfo ajx3BundleFileInfo = this.mBundleFileInfoMap.get(next.bundleName);
                    if (ajx3BundleFileInfo != null) {
                        ajx3BundleFileInfo.addPatch(next.ajxFileName, next.packageMode, next.version);
                        ajx3BundleFileInfo.mLastCheckTime = currentTimeMillis;
                    } else {
                        Ajx3BundleFileInfo ajx3BundleFileInfo2 = new Ajx3BundleFileInfo(this.mContext, next.bundleName, "", next.mEnv);
                        ajx3BundleFileInfo2.addPatch(next.ajxFileName, next.packageMode, next.version);
                        ajx3BundleFileInfo2.mLastCheckTime = currentTimeMillis;
                        this.mBundleFileInfoMap.put(next.bundleName, ajx3BundleFileInfo2);
                    }
                } else {
                    this.mBundleNameList.add(next.bundleName);
                    Ajx3BundleFileInfo ajx3BundleFileInfo3 = new Ajx3BundleFileInfo(this.mContext, next.bundleName, "", next.mEnv);
                    ajx3BundleFileInfo3.addPatch(next.ajxFileName, next.packageMode, next.version);
                    ajx3BundleFileInfo3.mLastCheckTime = currentTimeMillis;
                    this.mBundleFileInfoMap.put(next.bundleName, ajx3BundleFileInfo3);
                }
                z2 = true;
            }
        }
        if (sb.length() > 0) {
            Ajx3ActionLogUtil.actionLogAjxWeb(10, 0, sb.toString(), z, str);
        }
        saveToSP();
        if (z2) {
            notifyBundleListUpdate(this.mBundleNameList);
        }
        return z2;
    }

    /* access modifiers changed from: 0000 */
    public String generateOnlineLogRequestInfo() {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            for (String next : this.mBundleNameList) {
                if (!TextUtils.isEmpty(next)) {
                    Ajx3BundleFileInfo ajx3BundleFileInfo = this.mBundleFileInfoMap.get(next);
                    if (ajx3BundleFileInfo != null && next.equals(ajx3BundleFileInfo.bundleName)) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("name", next);
                        jSONObject2.put("currentVersion", ajx3BundleFileInfo.getLatestPatchVersion());
                        jSONObject2.put("baseVersion", ajx3BundleFileInfo.getBaseAjxVersion());
                        jSONObject2.put("historySize", ajx3BundleFileInfo.patchSize());
                        if (jSONObject2.length() > 0) {
                            jSONArray.put(jSONObject2);
                        }
                    }
                }
            }
            jSONObject.put(ENGINE_VERSION, getEngineVersion());
            jSONObject.put("packages", jSONArray);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    /* access modifiers changed from: 0000 */
    public String generateRequestInfo(String str) {
        String str2;
        String str3;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            int i = 0;
            if (TextUtils.isEmpty(str)) {
                boolean z = true;
                for (String next : this.mBundleNameList) {
                    if (!TextUtils.isEmpty(next)) {
                        Ajx3BundleFileInfo ajx3BundleFileInfo = this.mBundleFileInfoMap.get(next);
                        if (ajx3BundleFileInfo != null && next.equals(ajx3BundleFileInfo.bundleName)) {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("name", next);
                            if (z && (!TextUtils.isEmpty(ajx3BundleFileInfo.getLatestPatchVersion()) || !TextUtils.isEmpty(ajx3BundleFileInfo.getBaseAjxVersion()))) {
                                z = false;
                            }
                            jSONObject2.put("currentVersion", ajx3BundleFileInfo.getLatestPatchVersion());
                            jSONObject2.put("baseVersion", ajx3BundleFileInfo.getBaseAjxVersion());
                            jSONObject2.put("historySize", ajx3BundleFileInfo.patchSize());
                            if (jSONObject2.length() > 0) {
                                jSONArray.put(jSONObject2);
                            }
                        }
                    }
                }
                if (z) {
                    return "";
                }
            } else {
                Ajx3BundleFileInfo ajx3BundleFileInfo2 = this.mBundleFileInfoMap.get(str);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("name", str);
                if (ajx3BundleFileInfo2 == null) {
                    str2 = "";
                } else {
                    str2 = ajx3BundleFileInfo2.getLatestPatchVersion();
                }
                if (TextUtils.isEmpty(str2)) {
                    str2 = "";
                }
                jSONObject3.put("currentVersion", str2);
                if (ajx3BundleFileInfo2 == null) {
                    str3 = "";
                } else {
                    str3 = ajx3BundleFileInfo2.getBaseAjxVersion();
                }
                jSONObject3.put("baseVersion", str3);
                if (ajx3BundleFileInfo2 != null) {
                    i = ajx3BundleFileInfo2.patchSize();
                }
                jSONObject3.put("historySize", i);
                if (jSONObject3.length() > 0) {
                    jSONArray.put(jSONObject3);
                }
            }
            jSONObject.put(ENGINE_VERSION, getEngineVersion());
            jSONObject.put("packages", jSONArray);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    /* access modifiers changed from: 0000 */
    public String generateRequestInfo(List<RollbackInfo> list) {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            for (RollbackInfo rollbackInfo : list) {
                String str = rollbackInfo.bundleName;
                if (!TextUtils.isEmpty(str)) {
                    Ajx3BundleFileInfo ajx3BundleFileInfo = this.mBundleFileInfoMap.get(str);
                    if (ajx3BundleFileInfo != null && str.equals(ajx3BundleFileInfo.bundleName)) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("name", str);
                        jSONObject2.put("baseVersion", ajx3BundleFileInfo.getBaseAjxVersion());
                        jSONObject2.put("historySize", ajx3BundleFileInfo.patchSize());
                        if (jSONObject2.length() > 0) {
                            jSONArray.put(jSONObject2);
                        }
                    }
                }
            }
            jSONObject.put(ENGINE_VERSION, getEngineVersion());
            jSONObject.put("packages", jSONArray);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    /* access modifiers changed from: 0000 */
    public void handleScanAjx(String str) {
        LogHelper.d("Ajx3BundleDataManager #处理扫码ajx生效 jsPath = ".concat(String.valueOf(str)));
        File file = new File(str);
        if (file.exists()) {
            try {
                String parent = file.getParent();
                if (str.endsWith("tar.gz")) {
                    TarGzipUtil.unTarGzipFile(file, parent);
                } else {
                    parent = unZipFiles(file, parent);
                }
                if (!TextUtils.isEmpty(parent)) {
                    File file2 = new File(parent);
                    File file3 = new File(parent, "bundle_info.json");
                    if (file3.exists()) {
                        if (file3.isFile()) {
                            JSONObject jSONObject = new JSONObject(FileUtil.readData(file3.getAbsolutePath()));
                            if (jSONObject.has("bundles")) {
                                String string = jSONObject.getString("bundles");
                                if (!TextUtils.isEmpty(string)) {
                                    JSONArray jSONArray = new JSONArray(string);
                                    int length = jSONArray.length();
                                    if (length > 0) {
                                        for (int i = 0; i < length; i++) {
                                            String string2 = jSONArray.getString(i);
                                            if (!TextUtils.isEmpty(string2)) {
                                                JSONObject jSONObject2 = new JSONObject(string2);
                                                if (jSONObject2.has("name") && jSONObject2.has("fileName")) {
                                                    String string3 = jSONObject2.getString("name");
                                                    String string4 = jSONObject2.getString("fileName");
                                                    if (!TextUtils.isEmpty(string4) && !TextUtils.isEmpty(string3)) {
                                                        File file4 = new File(file3.getParent(), string4);
                                                        if (file4.exists()) {
                                                            if (!this.mDiffDir.exists()) {
                                                                this.mDiffDir.mkdir();
                                                            }
                                                            File file5 = new File(this.mDiffDir, string3);
                                                            if (!file5.exists()) {
                                                                file5.mkdir();
                                                            }
                                                            if (copyFile(new File(file5, string4).getAbsolutePath(), file4.getAbsolutePath())) {
                                                                Parcel addPatch = AjxFileInfo.addPatch(string3, string4);
                                                                addPatch.reset();
                                                                addPatch.readBoolean();
                                                            }
                                                        }
                                                        if (this.mBundleNameList.contains(string3)) {
                                                            Ajx3BundleFileInfo ajx3BundleFileInfo = this.mBundleFileInfoMap.get(string3);
                                                            if (ajx3BundleFileInfo != null) {
                                                                ajx3BundleFileInfo.addPatch(string4, 2, "");
                                                            } else {
                                                                Ajx3BundleFileInfo ajx3BundleFileInfo2 = new Ajx3BundleFileInfo(this.mContext, string3, "", "all");
                                                                ajx3BundleFileInfo2.addPatch(string4, 2, "");
                                                                this.mBundleFileInfoMap.put(string3, ajx3BundleFileInfo2);
                                                            }
                                                        } else {
                                                            this.mBundleNameList.add(string3);
                                                            Ajx3BundleFileInfo ajx3BundleFileInfo3 = new Ajx3BundleFileInfo(this.mContext, string3, "", "all");
                                                            ajx3BundleFileInfo3.addPatch(string4, 2, "");
                                                            this.mBundleFileInfoMap.put(string3, ajx3BundleFileInfo3);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        ahd.c(file2);
                                        file.delete();
                                        saveToSP();
                                        notifyBundleListUpdate(this.mBundleNameList);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void handleScanJs(String str) {
        LogHelper.d("Ajx3BundleDataManager #处理扫码js生效 jsPath = ".concat(String.valueOf(str)));
        String bundleName = AjxFileInfo.getBundleName(str);
        if (!TextUtils.isEmpty(bundleName)) {
            if (!this.mJsBundleNameList.contains(bundleName)) {
                this.mJsBundleNameList.add(bundleName);
                saveJsBundleToSP();
            }
            notifyJsBundleListUpdate(this.mJsBundleNameList);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:9|10|(2:11|(1:13)(1:50))|14|15|16|17|18|19|21) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0034 */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0045 A[SYNTHETIC, Splitter:B:30:0x0045] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x004a A[SYNTHETIC, Splitter:B:34:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0051 A[SYNTHETIC, Splitter:B:42:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0056 A[SYNTHETIC, Splitter:B:46:0x0056] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean copyFile(java.lang.String r4, java.lang.String r5) {
        /*
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x004e, all -> 0x0041 }
            r2.<init>(r4)     // Catch:{ Exception -> 0x004e, all -> 0x0041 }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x004e, all -> 0x0041 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x004e, all -> 0x0041 }
            boolean r5 = r2.exists()     // Catch:{ Exception -> 0x004e, all -> 0x0041 }
            if (r5 == 0) goto L_0x0015
            r2.delete()     // Catch:{ Exception -> 0x004e, all -> 0x0041 }
        L_0x0015:
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x004e, all -> 0x0041 }
            r5.<init>(r4)     // Catch:{ Exception -> 0x004e, all -> 0x0041 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x004f, all -> 0x003f }
            r4.<init>(r2)     // Catch:{ Exception -> 0x004f, all -> 0x003f }
            r1 = 2048(0x800, float:2.87E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x003d, all -> 0x0039 }
        L_0x0023:
            int r2 = r5.read(r1)     // Catch:{ Exception -> 0x003d, all -> 0x0039 }
            r3 = -1
            if (r2 == r3) goto L_0x002e
            r4.write(r1, r0, r2)     // Catch:{ Exception -> 0x003d, all -> 0x0039 }
            goto L_0x0023
        L_0x002e:
            r4.flush()     // Catch:{ Exception -> 0x003d, all -> 0x0039 }
            r4.close()     // Catch:{ Exception -> 0x0034 }
        L_0x0034:
            r5.close()     // Catch:{ Exception -> 0x0037 }
        L_0x0037:
            r4 = 1
            return r4
        L_0x0039:
            r0 = move-exception
            r1 = r4
            r4 = r0
            goto L_0x0043
        L_0x003d:
            r1 = r4
            goto L_0x004f
        L_0x003f:
            r4 = move-exception
            goto L_0x0043
        L_0x0041:
            r4 = move-exception
            r5 = r1
        L_0x0043:
            if (r1 == 0) goto L_0x0048
            r1.close()     // Catch:{ Exception -> 0x0048 }
        L_0x0048:
            if (r5 == 0) goto L_0x004d
            r5.close()     // Catch:{ Exception -> 0x004d }
        L_0x004d:
            throw r4
        L_0x004e:
            r5 = r1
        L_0x004f:
            if (r1 == 0) goto L_0x0054
            r1.close()     // Catch:{ Exception -> 0x0054 }
        L_0x0054:
            if (r5 == 0) goto L_0x0059
            r5.close()     // Catch:{ Exception -> 0x0059 }
        L_0x0059:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.upgrade.Ajx3BundleDataManager.copyFile(java.lang.String, java.lang.String):boolean");
    }

    private static String unZipFiles(File file, String str) throws IOException {
        ZipFile zipFile = new ZipFile(file);
        StringBuilder sb = new StringBuilder();
        sb.append(file.getName());
        sb.append("_dir");
        File file2 = new File(str, sb.toString());
        if (!file2.exists()) {
            file2.mkdirs();
        }
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            String name = zipEntry.getName();
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            if (name.contains("/")) {
                name = name.substring(name.lastIndexOf("/") + 1);
            }
            File file3 = new File(file2.getAbsolutePath(), name);
            if (!file3.isDirectory()) {
                FileOutputStream fileOutputStream = new FileOutputStream(file3);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                inputStream.close();
                fileOutputStream.close();
            }
        }
        return file2.getAbsolutePath();
    }

    public void setBundleListUpdateListener(BundleNameListUpdateListener bundleNameListUpdateListener) {
        this.mBundleListUpdateListener = bundleNameListUpdateListener;
    }

    private void notifyBundleListUpdate(List<String> list) {
        if (this.mBundleListUpdateListener != null && list != null) {
            this.mBundleListUpdateListener.onBundleNameUpdated(new ArrayList(list));
        }
    }

    private void notifyJsBundleListUpdate(List<String> list) {
        if (this.mBundleListUpdateListener != null && list != null) {
            this.mBundleListUpdateListener.onJsBundleNameUpdate(new ArrayList(list));
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateDataByRollback(List<RollbackInfo> list) {
        for (RollbackInfo next : list) {
            if (next != null && !TextUtils.isEmpty(next.targetFileName)) {
                Ajx3BundleFileInfo ajx3BundleFileInfo = this.mBundleFileInfoMap.get(next.bundleName);
                if (ajx3BundleFileInfo != null && ajx3BundleFileInfo.rollback(next.targetFileName)) {
                    this.mBundleNameList.remove(next.bundleName);
                    this.mBundleFileInfoMap.remove(next.bundleName);
                }
            }
        }
        saveToSP();
    }

    /* access modifiers changed from: 0000 */
    public void rollbackAll() {
        deleteAllDownloadBundle();
        this.mBundleFileInfoMap.clear();
        this.mBundleNameList.clear();
        saveToSP();
    }

    /* access modifiers changed from: 0000 */
    public boolean hasPatch() {
        for (String str : this.mBundleNameList) {
            Ajx3BundleFileInfo ajx3BundleFileInfo = this.mBundleFileInfoMap.get(str);
            if (ajx3BundleFileInfo != null && ajx3BundleFileInfo.isPatchExit()) {
                return true;
            }
        }
        return false;
    }

    private void init() {
        String str = a.a().a;
        int appOverwriteInstallStatus = getAppOverwriteInstallStatus(str);
        if (appOverwriteInstallStatus == 1 || appOverwriteInstallStatus == -2) {
            deleteAllDownloadBundle();
        } else if (appOverwriteInstallStatus == -1) {
            clearTemp();
            Ajx3SpUtil.setLastCheckTask(this.mContext, "");
            Ajx3SpUtil.setLastCheckResponse(this.mContext, "");
            if (!readAjxFileInfoFromSP() || !mergeBundleName(this.mContext)) {
                clearAllCaches();
                Ajx3SpUtil.setBundleNames(this.mContext, "");
                Ajx3SpUtil.setBundleInfo(this.mContext, "");
                readBaseAjxInfo(this.mContext);
            }
            if (this.mBundleNameList.size() <= 0 || this.mBundleFileInfoMap.size() <= 0) {
                Ajx3ActionLogUtil.actionLogCommon("", "open_bundle_json_error", "B002", "p1", "There is no ajx file exist 1");
            }
            Ajx3SpUtil.setAppVersion(this.mContext, str);
            Ajx3SpUtil.commit(this.mContext);
            return;
        }
        if (!readAjxFileInfoFromSP()) {
            deleteAllDownloadBundle();
            readBaseAjxInfo(this.mContext);
        }
        if (this.mBundleNameList.size() <= 0 || this.mBundleFileInfoMap.size() <= 0) {
            Ajx3ActionLogUtil.actionLogCommon("", "open_bundle_json_error", "B002", "p1", "There is no ajx file exist 2");
        }
        clearBundleCaches();
        Ajx3SpUtil.setAppVersion(this.mContext, str);
        Ajx3SpUtil.commit(this.mContext);
    }
}
