package com.autonavi.minimap.ajx3.upgrade;

import android.os.AsyncTask;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.Type;
import java.util.ArrayList;
import java.util.List;

public class Ajx3BundleAppendPatchTask extends AsyncTask<String, Integer, Boolean> {
    private boolean isCanceled = true;
    private List<Ajx3BundlePatchInfo> list = new ArrayList();
    private AppendCallback mCallBack;
    private List<BundleGroup> mGroups;
    private List<BundleGroup> mTempGroups;
    private Type mType;

    interface AppendCallback {
        void onAppendCancelled();

        void onAppendFinished(boolean z);
    }

    Ajx3BundleAppendPatchTask(List<BundleGroup> list2, AppendCallback appendCallback, Type type) {
        this.mTempGroups = new ArrayList(list2);
        this.mGroups = list2;
        this.mCallBack = appendCallback;
        this.mType = type;
    }

    /* access modifiers changed from: protected */
    public Boolean doInBackground(String... strArr) {
        boolean z = true;
        for (BundleGroup isReady : this.mTempGroups) {
            if (!isReady.isReady()) {
                z = false;
            }
        }
        if (z) {
            Ajx3ActionLogUtil.actionLogAjx("B002", "");
        }
        ArrayList arrayList = new ArrayList();
        for (BundleGroup next : this.mTempGroups) {
            if (isCancelled()) {
                return Boolean.FALSE;
            }
            if (next.mBundleList.size() <= 0) {
                arrayList.add(next);
            } else if (this.mType == Type.rollback) {
                this.list.addAll(next.mBundleList);
            } else if (next.addPatch(false, this.mType)) {
                this.list.addAll(next.mBundleList);
                arrayList.add(next);
            } else {
                z = false;
            }
        }
        if (isCancelled()) {
            return Boolean.FALSE;
        }
        this.isCanceled = false;
        this.mTempGroups = arrayList;
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: protected */
    public void onCancelled() {
        if (this.mCallBack != null) {
            this.mCallBack.onAppendCancelled();
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Boolean bool) {
        if (!this.isCanceled) {
            this.mGroups.removeAll(this.mTempGroups);
        }
        if (this.mCallBack != null) {
            this.mCallBack.onAppendFinished(bool.booleanValue());
        }
        Ajx3UpgradeManager.getInstance().onDownloadPatchReady(this.list, (!bool.booleanValue() || this.list == null || this.list.size() <= 0) ? "download_fail" : "success", this.mType, bool.booleanValue());
    }
}
