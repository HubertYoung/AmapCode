package com.autonavi.minimap.ajx3.upgrade;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.alipay.android.phone.a.a.a;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxUploadCoverageInfoHelper {
    private static final String TAG = "coverage";
    private static AjxUploadCoverageInfoHelper sInstance;
    /* access modifiers changed from: private */
    public boolean isRunning = false;
    private TaskCallBack mCallBack = new TaskCallBack() {
        public boolean onFinished() {
            AjxUploadCoverageInfoHelper.this.isRunning = false;
            AjxUploadCoverageInfoHelper.this.mTask = null;
            AjxUploadCoverageInfoHelper.this.askToRun();
            return false;
        }
    };
    private List<JSONObject> mPendingData = new LinkedList();
    /* access modifiers changed from: private */
    public AsyncTask<JSONObject, Integer, Integer> mTask = null;

    interface TaskCallBack {
        boolean onFinished();
    }

    private AjxUploadCoverageInfoHelper() {
    }

    public static AjxUploadCoverageInfoHelper getInstance() {
        if (sInstance == null) {
            sInstance = new AjxUploadCoverageInfoHelper();
        }
        return sInstance;
    }

    public void onMessage(String str) {
        if (!TextUtils.isEmpty(str)) {
            String str2 = "";
            String str3 = "";
            Object obj = null;
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("uri")) {
                    str2 = jSONObject.getString("uri");
                }
                if (jSONObject.has("data")) {
                    str3 = jSONObject.getString("data");
                }
                if (jSONObject.has("params")) {
                    obj = jSONObject.get("params");
                }
            } catch (JSONException unused) {
            }
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                JSONObject buildUploadData = buildUploadData(str2, obj, str3);
                if (buildUploadData != null && buildUploadData.length() > 0) {
                    this.mPendingData.add(buildUploadData);
                    askToRun();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void askToRun() {
        if (!this.isRunning && this.mPendingData.size() > 0) {
            this.isRunning = true;
            sendInfo(this.mPendingData.remove(0));
        }
    }

    private void sendInfo(JSONObject jSONObject) {
        this.mTask = new UploadCoverageTask(this.mCallBack);
        this.mTask.execute(new JSONObject[]{jSONObject});
    }

    private JSONObject buildUploadData(String str, Object obj, String str2) {
        JSONObject jSONObject;
        String allAjxFileVersion = AjxFileInfo.getAllAjxFileVersion();
        String taobaoID = NetworkParam.getTaobaoID();
        String str3 = "8.75.0";
        String str4 = "8.75.0.1001";
        try {
            JSONObject jSONObject2 = new JSONObject(emu.a((String) "VERSION_CURVERINFO").toString());
            if (jSONObject2.has("versionName")) {
                str3 = jSONObject2.getString("versionName");
                str4 = str3;
            }
            jSONObject = new JSONObject(str2);
        } catch (JSONException unused) {
            jSONObject = new JSONObject();
        }
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put("covInfo", jSONObject);
            jSONObject3.put("ajxVersion", allAjxFileVersion);
            jSONObject3.put("version", str3);
            jSONObject3.put("buildVersion", str4);
            jSONObject3.put("platform", a.a);
            jSONObject3.put(LocationParams.PARA_COMMON_DIU, taobaoID);
            jSONObject3.put("from", "AJX");
            jSONObject3.put("page", str);
            jSONObject3.put("pageParms", obj);
            jSONObject3.put("appId", "1");
            jSONObject3.put("time", System.currentTimeMillis() / 1000);
        } catch (JSONException unused2) {
        }
        return jSONObject3;
    }
}
