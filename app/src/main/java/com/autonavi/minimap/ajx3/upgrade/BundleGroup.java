package com.autonavi.minimap.ajx3.upgrade;

import android.text.TextUtils;
import com.alipay.sdk.authjs.a;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class BundleGroup {
    private boolean isReady = true;
    List<Ajx3BundlePatchInfo> mBundleList = new ArrayList();

    BundleGroup() {
    }

    public String toString() {
        if (this.mBundleList.size() <= 0) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        for (Ajx3BundlePatchInfo next : this.mBundleList) {
            if (next != null) {
                JSONObject jSONObject = next.toJSONObject();
                if (jSONObject != null) {
                    jSONArray.put(jSONObject);
                }
            }
        }
        return jSONArray.length() > 0 ? jSONArray.toString() : "";
    }

    /* access modifiers changed from: 0000 */
    public void add(Ajx3BundlePatchInfo ajx3BundlePatchInfo) {
        if (ajx3BundlePatchInfo != null && !this.mBundleList.contains(ajx3BundlePatchInfo)) {
            this.mBundleList.add(ajx3BundlePatchInfo);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isReady() {
        this.isReady = true;
        if (this.mBundleList.size() <= 0) {
            this.isReady = false;
            return false;
        }
        Iterator<Ajx3BundlePatchInfo> it = this.mBundleList.iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().state != 4) {
                    this.isReady = false;
                    break;
                }
            } else {
                break;
            }
        }
        return this.isReady;
    }

    /* access modifiers changed from: 0000 */
    public void onDestroy() {
        if (this.mBundleList.size() > 0) {
            for (Ajx3BundlePatchInfo onDestroy : this.mBundleList) {
                onDestroy.onDestroy();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean clearGroup() {
        if (this.mBundleList.size() <= 0) {
            return true;
        }
        for (Ajx3BundlePatchInfo clear : this.mBundleList) {
            clear.clear();
        }
        this.mBundleList.clear();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean contains(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (Ajx3BundlePatchInfo ajx3BundlePatchInfo : this.mBundleList) {
            if (str.equals(ajx3BundlePatchInfo.bundleName)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean couldRetryAppend(List<String> list) {
        boolean z;
        Iterator<Ajx3BundlePatchInfo> it = this.mBundleList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            Ajx3BundlePatchInfo next = it.next();
            if (!TextUtils.isEmpty(next.bundleName) && list.contains(next.bundleName)) {
                z = false;
                break;
            }
        }
        if (!z) {
            for (Ajx3BundlePatchInfo next2 : this.mBundleList) {
                if (!list.contains(next2.bundleName)) {
                    list.add(next2.bundleName);
                }
            }
        }
        return z;
    }

    private boolean needSuspend(String str) {
        return Ajx3UpgradeManager.getInstance().needSuspend(str);
    }

    private boolean needCheckSuspend(boolean z, Type type) {
        if (z) {
            return false;
        }
        if (type != Type.web && type != Type.scheme) {
            return true;
        }
        for (Ajx3BundlePatchInfo next : this.mBundleList) {
            if (next != null) {
                String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(next.bundleName);
                if (TextUtils.isEmpty(baseAjxFileVersion)) {
                    baseAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(next.bundleName);
                }
                if (TextUtils.isEmpty(baseAjxFileVersion)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean addPatch(boolean z, Type type) {
        if (this.mBundleList.size() <= 0) {
            return true;
        }
        if (!this.isReady) {
            return false;
        }
        JSONArray jSONArray = new JSONArray();
        boolean needCheckSuspend = needCheckSuspend(z, type);
        boolean z2 = false;
        for (Ajx3BundlePatchInfo next : this.mBundleList) {
            if (next != null) {
                z2 = next.isUserSelect;
                if (z || !needCheckSuspend || !needSuspend(next.bundleName)) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put(a.d, next.bundleName);
                        jSONObject.put("fileName", next.ajxFileName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jSONArray.put(jSONObject);
                } else {
                    if (Ajx3UpgradeManager.getInstance().addToSuspend(this)) {
                        if (type == Type.web || type == Type.scheme) {
                            Ajx3UpgradeManager.getInstance().showToast("更新成功，重启后可打开页面");
                        }
                        Ajx3ActionLogUtil.actionLogRollback("jsservice_dependence", generateSuspendLog(this));
                    }
                    return true;
                }
            }
        }
        if (jSONArray.length() <= 0) {
            return false;
        }
        Parcel addPatchList = AjxFileInfo.addPatchList(jSONArray.toString());
        addPatchList.reset();
        boolean readBoolean = addPatchList.readBoolean();
        if (!readBoolean) {
            String readString = addPatchList.readString();
            for (Ajx3BundlePatchInfo clear : this.mBundleList) {
                clear.clear();
            }
            Ajx3ActionLogUtil.actionLogAjxWeb(z ? 21 : 9, -1, " Append fail for inner error: ".concat(String.valueOf(readString)), z2, Ajx3UpgradeManager.getInstance().mStatId);
        }
        return readBoolean;
    }

    private JSONObject generateSuspendLog(BundleGroup bundleGroup) {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            for (Ajx3BundlePatchInfo next : bundleGroup.mBundleList) {
                if (next != null) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("name", next.bundleName);
                    jSONObject2.put("version", next.version);
                    jSONArray.put(jSONObject2);
                }
            }
            jSONObject.put("bundles", jSONArray);
        } catch (JSONException unused) {
        }
        return jSONObject;
    }
}
