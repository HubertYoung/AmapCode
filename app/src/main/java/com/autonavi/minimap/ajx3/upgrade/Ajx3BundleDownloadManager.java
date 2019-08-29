package com.autonavi.minimap.ajx3.upgrade;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.Type;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Ajx3BundleDownloadManager implements AppendCallback, PatchRunCallBack {
    static final String BUNDLES = "bundles";
    static final String BUNDLE_ENV = "env";
    static final String BUNDLE_NAME = "bundleName";
    static final String FILE_NAME = "fileName";
    private static final String GROUPS = "groups";
    private static final int MAX_TASK = 3;
    private static final int MSG_CHECK_AND_RUN = 2;
    private static final int MSG_RESTORE = 1;
    private static final int MSG_RUN = 0;
    private static final String TAG = "Ajx3BundleDM";
    private static AsyncTask<String, Integer, Boolean> mTask;
    private boolean isRunning = false;
    private Context mContext;
    private File mDiffTmpDir;
    String mDownloadInfo = "";
    private Type mDownloadType;
    private List<BundleGroup> mGroups = new ArrayList();
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    return;
                case 1:
                    return;
                case 2:
                    if (Ajx3BundleDownloadManager.this.mPendingList.size() > 0 || Ajx3BundleDownloadManager.this.mRunningList.size() > 0) {
                        if (Ajx3BundleDownloadManager.this.mPendingList.size() > 0 && Ajx3BundleDownloadManager.this.mRunningList.size() < 3) {
                            Ajx3BundlePatchInfo ajx3BundlePatchInfo = (Ajx3BundlePatchInfo) Ajx3BundleDownloadManager.this.mPendingList.remove(0);
                            Ajx3BundleDownloadManager.this.mRunningList.add(ajx3BundlePatchInfo);
                            ajx3BundlePatchInfo.run(Ajx3BundleDownloadManager.this);
                            break;
                        }
                    } else {
                        Ajx3BundleDownloadManager.this.appendAjxPatch();
                        return;
                    }
                    break;
            }
        }
    };
    /* access modifiers changed from: private */
    public List<Ajx3BundlePatchInfo> mPendingList = new ArrayList();
    /* access modifiers changed from: private */
    public List<Ajx3BundlePatchInfo> mRunningList = new ArrayList();

    public void onCancelled(Ajx3BundlePatchInfo ajx3BundlePatchInfo) {
    }

    Ajx3BundleDownloadManager(Context context) {
        this.mContext = context.getApplicationContext();
        this.mDiffTmpDir = new File(context.getCacheDir(), "ajx_diff_tmp");
    }

    /* access modifiers changed from: 0000 */
    public boolean isRunning() {
        return this.isRunning;
    }

    /* access modifiers changed from: 0000 */
    public Type getDownloadType() {
        return this.mDownloadType;
    }

    /* access modifiers changed from: 0000 */
    public boolean restore() {
        this.mDownloadType = Type.init;
        if (isRunning()) {
            return true;
        }
        this.mGroups.clear();
        this.mRunningList.clear();
        this.mPendingList.clear();
        String lastCheckTask = Ajx3SpUtil.getLastCheckTask(this.mContext);
        if (TextUtils.isEmpty(lastCheckTask)) {
            return false;
        }
        try {
            JSONArray jSONArray = new JSONArray(lastCheckTask);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                BundleGroup generateFromString = generateFromString(jSONArray.getString(i));
                if (generateFromString != null) {
                    this.mGroups.add(generateFromString);
                }
            }
        } catch (JSONException unused) {
        }
        for (BundleGroup bundleGroup : this.mGroups) {
            this.mPendingList.addAll(bundleGroup.mBundleList);
        }
        this.isRunning = true;
        Ajx3SpUtil.setLastCheckTask(this.mContext, "");
        askToRun();
        return true;
    }

    public int run(String str, Type type) {
        return run(str, type, null);
    }

    public int run(String str, Type type, List<RollbackInfo> list) {
        this.mDownloadInfo = "";
        this.mDownloadType = type;
        if (TextUtils.isEmpty(str) || "null".equals(str)) {
            Ajx3SpUtil.setLastCheckResponse(this.mContext, "");
            Ajx3UpgradeManager.getInstance().onDownloadPatchReady(null, "bad_server", this.mDownloadType, false);
            return 0;
        }
        if (this.mDownloadType == Type.init) {
            if (!str.equals(Ajx3SpUtil.getLastCheckResponse(this.mContext))) {
                Ajx3SpUtil.setLastCheckTask(this.mContext, "");
            } else if (restore()) {
                return 0;
            }
        }
        this.isRunning = true;
        if (mTask != null) {
            mTask.cancel(true);
            mTask = null;
        }
        for (BundleGroup clearGroup : this.mGroups) {
            clearGroup.clearGroup();
        }
        this.mRunningList.clear();
        this.mPendingList.clear();
        this.mGroups.clear();
        clearTempPatchFile();
        Ajx3SpUtil.setLastCheckTask(this.mContext, "");
        try {
            String optString = new JSONObject(str).optString(GROUPS, "");
            if (TextUtils.isEmpty(optString)) {
                this.isRunning = false;
                Ajx3UpgradeManager.getInstance().onDownloadPatchReady(null, "bad_server", this.mDownloadType, false);
                return 0;
            }
            JSONArray jSONArray = new JSONArray(optString);
            int length = jSONArray.length();
            if (length <= 0) {
                this.isRunning = false;
                Ajx3UpgradeManager.getInstance().onDownloadPatchReady(null, "no_bizdata", this.mDownloadType, false);
                return 0;
            }
            for (int i = 0; i < length; i++) {
                String string = jSONArray.getString(i);
                if (!TextUtils.isEmpty(string)) {
                    String optString2 = new JSONObject(string).optString(BUNDLES, "");
                    if (!TextUtils.isEmpty(optString2)) {
                        handleBundleGroup(optString2, list);
                    }
                }
            }
            if (this.mDownloadType == Type.init) {
                Ajx3SpUtil.setLastCheckResponse(this.mContext, str);
            }
            int size = this.mPendingList.size();
            if (size > 0) {
                generateDownloadInfo();
                askToRun();
            } else {
                this.isRunning = false;
            }
            return size;
        } catch (JSONException unused) {
            this.isRunning = false;
            return 0;
        }
    }

    private void generateDownloadInfo() {
        StringBuilder sb = new StringBuilder();
        for (Ajx3BundlePatchInfo next : this.mPendingList) {
            if (next != null) {
                sb.append(next.bundleName);
                sb.append(":");
                sb.append(next.version);
                sb.append(";");
            }
        }
        this.mDownloadInfo = sb.toString();
    }

    private void clearTempPatchFile() {
        if (this.mDiffTmpDir.exists() && this.mDiffTmpDir.isDirectory()) {
            File[] listFiles = this.mDiffTmpDir.listFiles();
            if (listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.exists() && file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }

    public void cancel() {
        this.mDownloadType = Type.init;
        if (mTask != null) {
            mTask.cancel(true);
            mTask = null;
        }
        for (BundleGroup clearGroup : this.mGroups) {
            clearGroup.clearGroup();
        }
        this.mRunningList.clear();
        this.mPendingList.clear();
        this.mGroups.clear();
        this.isRunning = false;
    }

    public void onDestroy() {
        this.mDownloadType = Type.init;
        try {
            if (isRunning()) {
                if (mTask != null) {
                    mTask.cancel(true);
                    mTask = null;
                }
                for (BundleGroup onDestroy : this.mGroups) {
                    onDestroy.onDestroy();
                }
                saveState();
            }
        } catch (Exception unused) {
        }
        this.mGroups.clear();
        this.mRunningList.clear();
        this.mPendingList.clear();
        this.isRunning = false;
    }

    private void saveState() {
        if (this.mDownloadType != Type.init) {
            Ajx3SpUtil.setLastCheckTask(this.mContext, "");
        } else if (this.mGroups.size() <= 0) {
            Ajx3SpUtil.setLastCheckTask(this.mContext, "");
        } else {
            try {
                JSONArray jSONArray = new JSONArray();
                for (BundleGroup bundleGroup : this.mGroups) {
                    String bundleGroup2 = bundleGroup.toString();
                    if (!TextUtils.isEmpty(bundleGroup2)) {
                        jSONArray.put(bundleGroup2);
                    }
                }
                Ajx3SpUtil.setLastCheckTask(this.mContext, jSONArray.length() > 0 ? jSONArray.toString() : "");
            } catch (Exception unused) {
            }
        }
    }

    private void handleBundleGroup(String str, List<RollbackInfo> list) {
        try {
            BundleGroup bundleGroup = new BundleGroup();
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            if (length > 0) {
                boolean z = false;
                for (int i = 0; i < length; i++) {
                    String string = jSONArray.getString(i);
                    if (!TextUtils.isEmpty(string)) {
                        Ajx3BundlePatchInfo ajx3BundlePatchInfo = new Ajx3BundlePatchInfo(this.mContext, string, this.mDownloadType, list);
                        if (ajx3BundlePatchInfo.isValid()) {
                            if (needDownload(ajx3BundlePatchInfo)) {
                                z = true;
                            }
                            bundleGroup.add(ajx3BundlePatchInfo);
                        }
                    }
                }
                if (bundleGroup.mBundleList.size() > 0 && z) {
                    this.mGroups.add(bundleGroup);
                    this.mPendingList.addAll(bundleGroup.mBundleList);
                }
            }
        } catch (JSONException unused) {
        }
    }

    private boolean needDownload(Ajx3BundlePatchInfo ajx3BundlePatchInfo) {
        String str = "";
        if (!TextUtils.isEmpty(ajx3BundlePatchInfo.mEnv)) {
            str = ajx3BundlePatchInfo.mEnv;
        } else {
            Ajx3BundleFileInfo bundleFileInfo = Ajx3UpgradeManager.getInstance().getBundleFileInfo(ajx3BundlePatchInfo.bundleName);
            if (bundleFileInfo != null) {
                str = bundleFileInfo.mBaseEnv;
            }
        }
        if (!TextUtils.isEmpty(str) && !"all".equalsIgnoreCase(str) && this.mDownloadType == Type.init) {
            return false;
        }
        return true;
    }

    private void askToRun() {
        this.mHandler.sendEmptyMessage(2);
    }

    /* access modifiers changed from: private */
    public void appendAjxPatch() {
        if (this.isRunning) {
            Ajx3BundleAppendPatchTask ajx3BundleAppendPatchTask = new Ajx3BundleAppendPatchTask(this.mGroups, this, this.mDownloadType);
            mTask = ajx3BundleAppendPatchTask;
            ajx3BundleAppendPatchTask.execute(new String[]{""});
        }
    }

    public void onAppendFinished(boolean z) {
        mTask = null;
        if (z) {
            Ajx3ActionLogUtil.actionLogAjx("B003", "");
            Ajx3SpUtil.setLastCheckResponse(this.mContext, "");
            Ajx3SpUtil.setLastCheckTask(this.mContext, "");
        } else {
            saveState();
        }
        this.mGroups.clear();
        this.mRunningList.clear();
        this.mPendingList.clear();
        this.isRunning = false;
    }

    public void onAppendCancelled() {
        mTask = null;
        this.mGroups.clear();
        this.mRunningList.clear();
        this.mPendingList.clear();
        this.isRunning = false;
    }

    public void onSucceeded(Ajx3BundlePatchInfo ajx3BundlePatchInfo) {
        if (this.mRunningList.contains(ajx3BundlePatchInfo)) {
            this.mRunningList.remove(ajx3BundlePatchInfo);
        }
        askToRun();
    }

    public void onFailed(Ajx3BundlePatchInfo ajx3BundlePatchInfo) {
        if (this.mRunningList.contains(ajx3BundlePatchInfo)) {
            this.mRunningList.remove(ajx3BundlePatchInfo);
        }
        askToRun();
    }

    private BundleGroup generateFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                Ajx3BundlePatchInfo generateFromString = Ajx3BundlePatchInfo.generateFromString(this.mContext, jSONArray.getString(i));
                if (generateFromString != null && generateFromString.isValid()) {
                    arrayList.add(generateFromString);
                }
            }
            if (arrayList.size() > 0) {
                BundleGroup bundleGroup = new BundleGroup();
                bundleGroup.mBundleList = arrayList;
                return bundleGroup;
            }
        } catch (JSONException unused) {
        }
        return null;
    }
}
