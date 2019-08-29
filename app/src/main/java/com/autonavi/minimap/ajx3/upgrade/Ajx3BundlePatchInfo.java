package com.autonavi.minimap.ajx3.upgrade;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.authjs.a;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.Type;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class Ajx3BundlePatchInfo {
    static final long NO_CHECK_INTERVAL_DEFINED = -10000;
    static final int PATCH_MODE_BASE = 0;
    private static final int PATCH_MODE_PATCH = 1;
    static final int PATCH_MODE_PATCH_FULL = 2;
    private static final int STATE_DOWNLOADING = 1;
    private static final int STATE_DOWNLOAD_FAILED = 2;
    private static final int STATE_DOWNLOAD_SUCCESSED = 3;
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDEL = 0;
    static final int STATE_VERFIY = 4;
    private static final String TAG = "DownloadTask";
    String ajxFileName;
    String bundleName;
    boolean isUserSelect;
    /* access modifiers changed from: private */
    public PatchRunCallBack mCallBack;
    private long mCheckInterval;
    private Context mContext;
    private File mDiffDir;
    private File mDiffTmpDir;
    private Type mDownloadType;
    String mEnv;
    private RollbackInfo mRollbackInfo;
    private String md5;
    int packageMode;
    int state;
    /* access modifiers changed from: private */
    public String url;
    String version;

    class AjxBundleFileDownloadCallback implements bjf {
        public void onProgressUpdate(long j, long j2) {
        }

        public void onStart(long j, Map<String, List<String>> map, int i) {
        }

        private AjxBundleFileDownloadCallback() {
        }

        public void onFinish(bpk bpk) {
            Ajx3BundlePatchInfo.this.state = 3;
            Ajx3BundlePatchInfo.this.onDownloadFinish();
        }

        public void onError(int i, int i2) {
            if (-3 != i) {
                StringBuilder sb = new StringBuilder("errorCode: ");
                sb.append(i);
                sb.append(" ,statusCode: ");
                sb.append(i2);
                sb.append(" ,url: ");
                sb.append(Ajx3BundlePatchInfo.this.url);
                sb.append(" ,ajxFileName: ");
                sb.append(Ajx3BundlePatchInfo.this.ajxFileName);
                Ajx3ActionLogUtil.actionLogAjxWeb(4, 4, sb.toString(), Ajx3BundlePatchInfo.this.isUserSelect, Ajx3UpgradeManager.getInstance().mStatId);
            }
            Ajx3BundlePatchInfo.this.state = 2;
            if (Ajx3BundlePatchInfo.this.isUserSelect) {
                Ajx3BundlePatchInfo.this.clear();
            }
            Ajx3BundlePatchInfo.this.mCallBack.onFailed(Ajx3BundlePatchInfo.this);
        }
    }

    interface PatchRunCallBack {
        void onCancelled(Ajx3BundlePatchInfo ajx3BundlePatchInfo);

        void onFailed(Ajx3BundlePatchInfo ajx3BundlePatchInfo);

        void onSucceeded(Ajx3BundlePatchInfo ajx3BundlePatchInfo);
    }

    /* access modifiers changed from: 0000 */
    public JSONObject toJSONObject() {
        if (!isValid()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(a.d, this.bundleName);
            jSONObject.put("ajxFileName", this.ajxFileName);
            jSONObject.put("md5", this.md5);
            jSONObject.put("version", this.version);
            jSONObject.put("url", this.url);
            jSONObject.put("packageMode", this.packageMode);
            jSONObject.put("state", this.state);
            jSONObject.put("env", this.mEnv);
            jSONObject.put("checkInterval", this.mCheckInterval);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    static Ajx3BundlePatchInfo generateFromString(Context context, String str) {
        String str2 = "";
        String str3 = "";
        String str4 = "";
        String str5 = "";
        String str6 = "";
        String str7 = "";
        int i = 1;
        int i2 = 0;
        long j = NO_CHECK_INTERVAL_DEFINED;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(a.d)) {
                str2 = jSONObject.getString(a.d);
            }
            if (jSONObject.has("ajxFileName")) {
                str3 = jSONObject.getString("ajxFileName");
            }
            if (jSONObject.has("md5")) {
                str4 = jSONObject.getString("md5");
            }
            if (jSONObject.has("version")) {
                str5 = jSONObject.getString("version");
            }
            if (jSONObject.has("url")) {
                str6 = jSONObject.getString("url");
            }
            if (jSONObject.has("packageMode")) {
                i = jSONObject.getInt("packageMode");
            }
            if (jSONObject.has("state")) {
                i2 = jSONObject.getInt("state");
            }
            if (jSONObject.has("env")) {
                str7 = jSONObject.getString("env");
            }
            if (jSONObject.has("checkInterval")) {
                j = jSONObject.getLong("checkInterval");
            }
        } catch (JSONException unused) {
        }
        String str8 = str2;
        String str9 = str3;
        String str10 = str4;
        String str11 = str5;
        int i3 = i;
        if (TextUtils.isEmpty(str8) || TextUtils.isEmpty(str9) || TextUtils.isEmpty(str10) || TextUtils.isEmpty(str11) || TextUtils.isEmpty(str6)) {
            return null;
        }
        Ajx3BundlePatchInfo ajx3BundlePatchInfo = new Ajx3BundlePatchInfo(context, str8, str9, str10, str11, str6, i3);
        ajx3BundlePatchInfo.state = i2;
        ajx3BundlePatchInfo.mEnv = str7;
        ajx3BundlePatchInfo.mCheckInterval = j;
        return ajx3BundlePatchInfo;
    }

    private Ajx3BundlePatchInfo(Context context, String str, String str2, String str3, String str4, String str5, int i) {
        this.mCallBack = null;
        this.bundleName = "";
        this.ajxFileName = "";
        this.mEnv = "";
        this.md5 = "";
        this.version = "";
        this.url = "";
        this.packageMode = 1;
        this.state = 0;
        this.isUserSelect = false;
        this.mDownloadType = Type.init;
        this.mCheckInterval = NO_CHECK_INTERVAL_DEFINED;
        this.mRollbackInfo = null;
        this.bundleName = str;
        this.ajxFileName = str2;
        this.md5 = str3;
        this.version = str4;
        this.url = str5;
        this.packageMode = i;
        this.state = 0;
        this.mContext = context;
        this.mDiffTmpDir = new File(this.mContext.getCacheDir(), "ajx_diff_tmp");
        this.mDiffDir = new File(this.mContext.getFilesDir(), "ajx_diff/".concat(String.valueOf(str)));
    }

    Ajx3BundlePatchInfo(Context context, String str, Type type, List<RollbackInfo> list) {
        this.mCallBack = null;
        this.bundleName = "";
        this.ajxFileName = "";
        this.mEnv = "";
        this.md5 = "";
        this.version = "";
        this.url = "";
        boolean z = true;
        this.packageMode = 1;
        this.state = 0;
        this.isUserSelect = false;
        this.mDownloadType = Type.init;
        this.mCheckInterval = NO_CHECK_INTERVAL_DEFINED;
        this.mRollbackInfo = null;
        this.isUserSelect = type == Type.init ? false : z;
        this.mDownloadType = type;
        this.mContext = context;
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("name")) {
                this.bundleName = jSONObject.getString("name");
            }
            if (jSONObject.has("fileName")) {
                this.ajxFileName = jSONObject.getString("fileName");
            }
            if (jSONObject.has("md5")) {
                this.md5 = jSONObject.getString("md5");
            }
            if (jSONObject.has("version")) {
                this.version = jSONObject.getString("version");
            }
            if (jSONObject.has("url")) {
                this.url = jSONObject.getString("url");
            }
            if (jSONObject.has("packageMode")) {
                this.packageMode = jSONObject.getInt("packageMode");
            }
            if (jSONObject.has("env")) {
                this.mEnv = jSONObject.getString("env");
            }
            if (jSONObject.has("checkInterval") && jSONObject.get("checkInterval") != null) {
                this.mCheckInterval = jSONObject.getLong("checkInterval");
            }
            this.state = 0;
            if (!TextUtils.isEmpty(this.bundleName) && list != null && type == Type.rollback) {
                Iterator<RollbackInfo> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RollbackInfo next = it.next();
                    if (this.bundleName.equals(next.bundleName)) {
                        this.mRollbackInfo = next;
                        break;
                    }
                }
            }
            if (!isValid()) {
                this.state = -1;
            }
        } catch (JSONException unused) {
            this.state = -1;
        }
        this.mDiffTmpDir = new File(this.mContext.getCacheDir(), "ajx_diff_tmp");
        File filesDir = this.mContext.getFilesDir();
        StringBuilder sb = new StringBuilder("ajx_diff/");
        sb.append(this.bundleName);
        this.mDiffDir = new File(filesDir, sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public boolean isValid() {
        boolean z = !TextUtils.isEmpty(this.bundleName) && !TextUtils.isEmpty(this.ajxFileName) && !TextUtils.isEmpty(this.url);
        if (z && this.mDownloadType == Type.rollback) {
            if (this.mRollbackInfo == null || TextUtils.isEmpty(this.mRollbackInfo.target)) {
                return false;
            }
            if (TextUtils.isEmpty(this.version) || this.version.equals(this.mRollbackInfo.target)) {
                return z;
            }
            return false;
        }
        return z;
    }

    public void run(PatchRunCallBack patchRunCallBack) {
        this.mCallBack = patchRunCallBack;
        int i = this.state;
        if (i != 0) {
            switch (i) {
                case 2:
                    break;
                case 3:
                case 4:
                    if (checkDownloadFileValid(new File(this.mDiffTmpDir, this.ajxFileName))) {
                        onDownloadFinish();
                        return;
                    }
                    File file = new File(this.mDiffDir, this.ajxFileName);
                    if (!file.exists() || !file.isFile() || !isDiffValid(file.getAbsolutePath())) {
                        clear();
                        this.state = 0;
                        download();
                        return;
                    }
                    this.state = 4;
                    this.mCallBack.onSucceeded(this);
                    return;
                default:
                    if (!isValid()) {
                        this.mCallBack.onFailed(this);
                        return;
                    }
                    clear();
                    this.state = 0;
                    download();
                    return;
            }
        }
        download();
    }

    private boolean checkFile() {
        File file = new File(this.mDiffDir, this.ajxFileName);
        return file.exists() && file.isFile() && isDiffValid(file.getAbsolutePath());
    }

    private void download() {
        if (checkFile()) {
            this.state = 4;
            this.mCallBack.onSucceeded(this);
            return;
        }
        this.state = 1;
        File file = new File(this.mDiffTmpDir, this.ajxFileName);
        bjg bjg = new bjg(file.getAbsolutePath());
        bjg.setUrl(this.url);
        bjg.b = true;
        bjg.addHeader(OfflineUtil.CDN_HEADER_MAC, aat.a());
        bjh.a().a(file.getAbsolutePath());
        bjh.a().a(bjg, (bjf) new AjxBundleFileDownloadCallback());
    }

    /* access modifiers changed from: 0000 */
    public void onDestroy() {
        if (this.state == 1) {
            bjh.a().a(new File(this.mDiffTmpDir, this.ajxFileName).getAbsolutePath());
        }
    }

    /* access modifiers changed from: 0000 */
    public void clear() {
        this.state = -1;
        File file = new File(this.mDiffTmpDir, this.ajxFileName);
        File file2 = new File(this.mDiffDir, this.ajxFileName);
        bjh.a().a(file2.getAbsolutePath());
        if (file.exists()) {
            file.delete();
        }
        if (file2.exists()) {
            file2.delete();
        }
    }

    /* access modifiers changed from: private */
    public void onDownloadFinish() {
        File file = new File(this.mDiffTmpDir, this.ajxFileName);
        File file2 = new File(this.mDiffDir, this.ajxFileName);
        if (checkDownloadFileValid(file)) {
            if (this.mDownloadType == Type.rollback) {
                String ajxVersionByFilePath = AjxFileInfo.getAjxVersionByFilePath(file.getAbsolutePath(), 1);
                if (TextUtils.isEmpty(ajxVersionByFilePath)) {
                    ajxVersionByFilePath = AjxFileInfo.getAjxVersionByFilePath(file.getAbsolutePath(), 0);
                }
                if (!this.mRollbackInfo.target.equals(ajxVersionByFilePath)) {
                    clear();
                    this.state = -1;
                    this.mCallBack.onFailed(this);
                    return;
                }
            }
            if (!mvCache2File(this.ajxFileName, this.ajxFileName)) {
                StringBuilder sb = new StringBuilder(" move error: ");
                sb.append(this.ajxFileName);
                Ajx3ActionLogUtil.actionLogAjxWeb(8, 8, sb.toString(), this.isUserSelect, Ajx3UpgradeManager.getInstance().mStatId);
            } else if (isDiffValid(file2.getAbsolutePath())) {
                this.state = 4;
                this.mCallBack.onSucceeded(this);
                return;
            }
        }
        clear();
        this.state = -1;
        this.mCallBack.onFailed(this);
    }

    private boolean isDiffValid(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Parcel checkAjxFileSign = AjxFileInfo.checkAjxFileSign(str);
        checkAjxFileSign.reset();
        if (!checkAjxFileSign.readBoolean()) {
            String readString = checkAjxFileSign.readString();
            StringBuilder sb = new StringBuilder("Bad patch with checkSign error: ");
            sb.append(this.ajxFileName);
            sb.append(" ;error: ");
            sb.append(readString);
            Ajx3ActionLogUtil.actionLogAjxWeb(5, -1, sb.toString(), this.isUserSelect, Ajx3UpgradeManager.getInstance().mStatId);
            return false;
        }
        String baseAjxFileName = Ajx3UpgradeManager.getInstance().getBaseAjxFileName(this.bundleName);
        if (!TextUtils.isEmpty(baseAjxFileName)) {
            Parcel checkDiffAjxFileValid = AjxFileInfo.checkDiffAjxFileValid(baseAjxFileName, str);
            checkDiffAjxFileValid.reset();
            if (!checkDiffAjxFileValid.readBoolean()) {
                String readString2 = checkDiffAjxFileValid.readString();
                StringBuilder sb2 = new StringBuilder(" Bad patch with checkUpdate error: ");
                sb2.append(this.ajxFileName);
                sb2.append(" ;error: ");
                sb2.append(readString2);
                Ajx3ActionLogUtil.actionLogAjxWeb(5, -1, sb2.toString(), this.isUserSelect, Ajx3UpgradeManager.getInstance().mStatId);
                return false;
            }
        }
        return true;
    }

    private boolean checkDownloadFileValid(File file) {
        return file.exists() && file.isFile();
    }

    private boolean mvCache2File(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        File file = new File(this.mDiffTmpDir, str);
        File file2 = new File(this.mDiffDir, str2);
        File file3 = null;
        if (file2.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(file2.getAbsolutePath());
            sb.append(FilePathHelper.SUFFIX_DOT_TMP);
            file3 = new File(sb.toString());
            if ((file3.exists() && !file3.delete()) || !file2.renameTo(file3)) {
                return false;
            }
        } else {
            File parentFile = file2.getParentFile();
            if (!parentFile.exists() && !parentFile.mkdirs()) {
                return false;
            }
        }
        boolean renameTo = file.renameTo(file2);
        if (file3 != null) {
            if (renameTo) {
                boolean delete = file3.delete();
            } else {
                file3.renameTo(file);
            }
        }
        return renameTo;
    }
}
