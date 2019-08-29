package com.autonavi.minimap.ajx3.upgrade;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.authjs.a;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Ajx3BundleFileInfo {
    private static final long ONE_WEEK = 604800000;
    private static final String TAG = "BundleFileInfo";
    static final int TYPE_BASE = 0;
    static final int TYPE_UPDATE = 1;
    String baseAjxFileName;
    String bundleName;
    private int lastPatchMode = 0;
    String mBaseEnv = "all";
    private Context mContext;
    private String mDiffFolderPath;
    boolean mIsRollback = false;
    long mLastCheckTime = -1;
    private List<String> oldPatchFileList = new ArrayList();
    private List<String> patchFileList = new ArrayList();

    private Ajx3BundleFileInfo(Context context) {
        this.mContext = context;
        this.mDiffFolderPath = new File(context.getFilesDir(), "ajx_diff").getAbsolutePath();
    }

    Ajx3BundleFileInfo(Context context, String str, String str2, String str3) {
        this.mContext = context;
        this.baseAjxFileName = str2;
        this.bundleName = str;
        if (!TextUtils.isEmpty(str3)) {
            this.mBaseEnv = str3;
        }
        this.mDiffFolderPath = new File(context.getFilesDir(), "ajx_diff").getAbsolutePath();
    }

    /* access modifiers changed from: 0000 */
    public boolean hasBase() {
        return !TextUtils.isEmpty(this.baseAjxFileName);
    }

    /* access modifiers changed from: 0000 */
    public void setBase(String str) {
        this.baseAjxFileName = str;
    }

    /* access modifiers changed from: 0000 */
    public boolean isPatchExit() {
        if (this.patchFileList == null || this.patchFileList.size() <= 0) {
            return false;
        }
        File file = new File(this.mDiffFolderPath, this.bundleName);
        for (String file2 : this.patchFileList) {
            if (!new File(file.getAbsolutePath(), file2).exists()) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean handleMerge(Ajx3BundleFileInfo ajx3BundleFileInfo) {
        if (ajx3BundleFileInfo == null || ajx3BundleFileInfo.patchFileList == null || ajx3BundleFileInfo.patchFileList.size() <= 0) {
            return false;
        }
        String patchVersion = ajx3BundleFileInfo.getPatchVersion();
        if (TextUtils.isEmpty(patchVersion)) {
            return false;
        }
        int compareAjxVersion = compareAjxVersion(patchVersion, getBaseAjxVersionVarFileName());
        if (compareAjxVersion > 0) {
            this.lastPatchMode = ajx3BundleFileInfo.lastPatchMode;
            this.patchFileList.add(ajx3BundleFileInfo.patchFileList.get(ajx3BundleFileInfo.patchFileList.size() - 1));
            clearBundleCaches(true);
        } else {
            clearAllPatch();
        }
        if (compareAjxVersion > 0) {
            return true;
        }
        return false;
    }

    private static int compareAjxVersion(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (TextUtils.isEmpty(str2)) {
            return 1;
        }
        if (str.equals(str2)) {
            return 0;
        }
        int[] parseVersionToIntArray = parseVersionToIntArray(str);
        int[] parseVersionToIntArray2 = parseVersionToIntArray(str2);
        if (parseVersionToIntArray == null || parseVersionToIntArray.length != 3) {
            return -1;
        }
        if (parseVersionToIntArray2 == null || parseVersionToIntArray2.length != 3) {
            return 1;
        }
        for (int i = 0; i < 3; i++) {
            if (parseVersionToIntArray[i] > parseVersionToIntArray2[i]) {
                return 1;
            }
            if (parseVersionToIntArray[i] < parseVersionToIntArray2[i]) {
                return -1;
            }
        }
        return 0;
    }

    private static int[] parseVersionToIntArray(String str) {
        String[] split = str.split("\\.");
        if (split.length != 5) {
            return null;
        }
        int[] iArr = new int[3];
        try {
            iArr[0] = Integer.parseInt(split[0]);
            iArr[1] = Integer.parseInt(split[2]);
            iArr[2] = Integer.parseInt(split[3]);
            return iArr;
        } catch (Exception unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public String getPatchFileNameVarVersion(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (this.oldPatchFileList.size() > 0) {
            File file = new File(this.mDiffFolderPath, this.bundleName);
            for (String next : this.oldPatchFileList) {
                File file2 = new File(file, next);
                if (file2.exists() && file2.isFile() && str.equals(AjxFileInfo.getAjxVersionByFilePath(file2.getAbsolutePath(), hasBase() ? 1 : 0))) {
                    return next;
                }
            }
        }
        String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(this.bundleName);
        if (str.equals(baseAjxFileVersion)) {
            return baseAjxFileVersion;
        }
        String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(this.bundleName);
        return str.equals(loadedDiffAjxFileVersion) ? loadedDiffAjxFileVersion : "";
    }

    private String getPatchVersion() {
        if (this.patchFileList == null || this.patchFileList.size() <= 0) {
            return "";
        }
        String str = this.patchFileList.get(this.patchFileList.size() - 1);
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        File file = new File(new File(this.mDiffFolderPath, this.bundleName).getAbsolutePath(), str);
        if (!file.exists()) {
            return "";
        }
        String ajxVersionByFilePath = AjxFileInfo.getAjxVersionByFilePath(file.getAbsolutePath(), hasBase() ? 1 : 0);
        if (!TextUtils.isEmpty(ajxVersionByFilePath)) {
            return ajxVersionByFilePath.startsWith("v") ? ajxVersionByFilePath.substring(1) : ajxVersionByFilePath;
        }
        return "";
    }

    private String getBaseAjxVersionVarFileName() {
        String[] strArr;
        if (TextUtils.isEmpty(this.baseAjxFileName)) {
            return "";
        }
        if (!this.baseAjxFileName.endsWith(".ajx")) {
            return "";
        }
        String substring = this.baseAjxFileName.substring(0, this.baseAjxFileName.length() - 4);
        if (!substring.contains("_v")) {
            strArr = substring.split("_");
        } else {
            strArr = substring.split("_v");
        }
        if (strArr.length < 2) {
            return "";
        }
        return strArr[strArr.length - 1];
    }

    /* access modifiers changed from: 0000 */
    public void clearAllPatch() {
        File file = new File(this.mDiffFolderPath, this.bundleName);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles.length > 0) {
                for (File file2 : listFiles) {
                    Ajx3SpUtil.remoevPatchInvalidTime(this.mContext, file2.getName());
                    file2.delete();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void clearBundleCaches(boolean z) {
        if (this.lastPatchMode == 2 && this.patchFileList.size() == 1) {
            File file = new File(this.mDiffFolderPath, this.bundleName);
            if (file.exists() && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                String str = this.patchFileList.get(0);
                if (listFiles.length > 0) {
                    long currentTimeMillis = System.currentTimeMillis();
                    for (File file2 : listFiles) {
                        try {
                            if (file2.exists() && !file2.getName().equals(str)) {
                                if (z) {
                                    this.oldPatchFileList.remove(file2.getName());
                                    Ajx3SpUtil.remoevPatchInvalidTime(this.mContext, file2.getName());
                                    file2.delete();
                                } else {
                                    long patchInvalidTime = currentTimeMillis - Ajx3SpUtil.getPatchInvalidTime(this.mContext, file2.getName());
                                    if (patchInvalidTime < 0 || patchInvalidTime > 604800000) {
                                        this.oldPatchFileList.remove(file2.getName());
                                        Ajx3SpUtil.remoevPatchInvalidTime(this.mContext, file2.getName());
                                        file2.delete();
                                    }
                                }
                            }
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean rollback(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        for (String next : this.patchFileList) {
            if (!TextUtils.isEmpty(next)) {
                Ajx3SpUtil.setPatchInvalidTime(this.mContext, next, currentTimeMillis);
                if (!this.oldPatchFileList.contains(next)) {
                    this.oldPatchFileList.add(next);
                }
            }
        }
        this.oldPatchFileList.remove(str);
        Ajx3SpUtil.remoevPatchInvalidTime(this.mContext, str);
        this.patchFileList.clear();
        if (!"base_version".equals(str)) {
            this.patchFileList.add(str);
        }
        this.mLastCheckTime = currentTimeMillis;
        this.mIsRollback = true;
        if (this.patchFileList.size() > 0 || hasBase()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void addPatch(String str, int i, String str2) {
        if (!TextUtils.isEmpty(str)) {
            this.lastPatchMode = i;
            this.oldPatchFileList.remove(str);
            Ajx3SpUtil.remoevPatchInvalidTime(this.mContext, str);
            if (!this.patchFileList.contains(str)) {
                if (i == 2) {
                    long currentTimeMillis = System.currentTimeMillis();
                    for (String next : this.patchFileList) {
                        if (!TextUtils.isEmpty(next)) {
                            Ajx3SpUtil.setPatchInvalidTime(this.mContext, next, currentTimeMillis);
                            if (!this.oldPatchFileList.contains(next)) {
                                this.oldPatchFileList.add(next);
                            }
                        }
                    }
                    this.patchFileList.clear();
                }
                if (!this.patchFileList.contains(str)) {
                    this.patchFileList.add(str);
                }
            }
        }
    }

    private void insertPatch(String str) {
        if (!TextUtils.isEmpty(str) && !this.patchFileList.contains(str)) {
            this.patchFileList.add(0, str);
        }
    }

    /* access modifiers changed from: 0000 */
    public int patchSize() {
        return this.patchFileList.size();
    }

    /* access modifiers changed from: 0000 */
    public String getBaseAjxVersion() {
        return AjxFileInfo.getBaseAjxFileVersion(this.bundleName);
    }

    /* access modifiers changed from: 0000 */
    public String getLatestPatchVersion() {
        return AjxFileInfo.getLoadedDiffAjxFileVersion(this.bundleName);
    }

    static void stringToMap(Context context, String str, HashMap<String, Ajx3BundleFileInfo> hashMap) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        Ajx3BundleFileInfo generateFromString = generateFromString(context, jSONArray.getString(i));
                        if (generateFromString != null) {
                            hashMap.put(generateFromString.bundleName, generateFromString);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    static String mapToString(List<String> list, HashMap<String, Ajx3BundleFileInfo> hashMap) {
        if (list == null || list.size() <= 0 || hashMap == null) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        for (String str : list) {
            Ajx3BundleFileInfo ajx3BundleFileInfo = hashMap.get(str);
            if (ajx3BundleFileInfo != null) {
                jSONArray.put(ajx3BundleFileInfo.toJSONObject());
            }
        }
        return jSONArray.toString();
    }

    private static Ajx3BundleFileInfo generateFromString(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has(a.d)) {
                return null;
            }
            String string = jSONObject.getString(a.d);
            Ajx3BundleFileInfo ajx3BundleFileInfo = new Ajx3BundleFileInfo(context);
            ajx3BundleFileInfo.bundleName = string;
            if (jSONObject.has("env")) {
                ajx3BundleFileInfo.mBaseEnv = jSONObject.getString("env");
            } else {
                ajx3BundleFileInfo.mBaseEnv = "all";
            }
            if (jSONObject.has("lastPatchMode")) {
                ajx3BundleFileInfo.lastPatchMode = jSONObject.getInt("lastPatchMode");
            }
            if (jSONObject.has("lastCheckTime")) {
                ajx3BundleFileInfo.mLastCheckTime = jSONObject.getLong("lastCheckTime");
            }
            if (!jSONObject.has("ajxFiles")) {
                return null;
            }
            String string2 = jSONObject.getString("ajxFiles");
            if (TextUtils.isEmpty(string2)) {
                return null;
            }
            JSONArray jSONArray = new JSONArray(string2);
            if (jSONArray.length() <= 0) {
                return null;
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                String string3 = jSONArray.getString(i);
                if (!TextUtils.isEmpty(string3)) {
                    JSONObject jSONObject2 = new JSONObject(string3);
                    if (jSONObject2.has("fileName")) {
                        String string4 = jSONObject2.getString("fileName");
                        if (!TextUtils.isEmpty(string4)) {
                            if (!jSONObject2.has("isBaseAJX") || jSONObject2.getInt("isBaseAJX") != 1) {
                                ajx3BundleFileInfo.insertPatch(string4);
                            } else {
                                ajx3BundleFileInfo.setBase(string4);
                            }
                        }
                    }
                }
            }
            String optString = jSONObject.optString("oldAjxFiles", "");
            if (!TextUtils.isEmpty(optString)) {
                JSONArray jSONArray2 = new JSONArray(optString);
                if (jSONArray2.length() > 0) {
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        String string5 = jSONArray2.getString(i2);
                        if (!TextUtils.isEmpty(string5)) {
                            String optString2 = new JSONObject(string5).optString("fileName", "");
                            if (!TextUtils.isEmpty(optString2) && !ajx3BundleFileInfo.oldPatchFileList.contains(optString2)) {
                                ajx3BundleFileInfo.oldPatchFileList.add(optString2);
                            }
                        }
                    }
                }
            }
            return ajx3BundleFileInfo;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject toJSONObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(a.d, this.bundleName);
            jSONObject.put("env", this.mBaseEnv);
            JSONArray jSONArray = new JSONArray();
            int size = this.patchFileList.size();
            if (this.patchFileList.size() > 0) {
                for (int i = size - 1; i >= 0; i--) {
                    String str = this.patchFileList.get(i);
                    if (!TextUtils.isEmpty(str)) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("isBaseAJX", 0);
                        jSONObject2.put("fileName", str);
                        jSONArray.put(jSONObject2);
                    }
                }
            }
            int size2 = this.oldPatchFileList.size();
            JSONArray jSONArray2 = new JSONArray();
            if (this.oldPatchFileList.size() > 0) {
                for (int i2 = 0; i2 < size2; i2++) {
                    String str2 = this.oldPatchFileList.get(i2);
                    if (!TextUtils.isEmpty(str2)) {
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("fileName", str2);
                        jSONArray2.put(jSONObject3);
                    }
                }
            }
            if (!TextUtils.isEmpty(this.baseAjxFileName)) {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("isBaseAJX", 1);
                jSONObject4.put("fileName", this.baseAjxFileName);
                jSONArray.put(jSONObject4);
            }
            jSONObject.put("ajxFiles", jSONArray);
            jSONObject.put("lastPatchMode", this.lastPatchMode);
            jSONObject.put("lastCheckTime", this.mLastCheckTime);
            jSONObject.put("oldAjxFiles", jSONArray2);
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    public String toString() {
        return toJSONObject().toString();
    }
}
