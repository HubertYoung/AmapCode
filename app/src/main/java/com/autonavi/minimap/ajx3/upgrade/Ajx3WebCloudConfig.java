package com.autonavi.minimap.ajx3.upgrade;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.aocs.AocsRequestHolder;
import com.autonavi.minimap.aocs.param.Updatable11Request;
import org.json.JSONException;
import org.json.JSONObject;

class Ajx3WebCloudConfig {
    private static final String TAG = "Ajx3WebCloudConfig";
    /* access modifiers changed from: private */
    public boolean isRunning = false;
    /* access modifiers changed from: private */
    public WebCloudCheckCallBack mCallBack;
    private Context mContext;
    private JSONObject mData = null;
    /* access modifiers changed from: private */
    public boolean mIsColdStart = false;
    public String mStatId = "";

    Ajx3WebCloudConfig(Context context) {
        this.mContext = context;
        String webConfigInfo = Ajx3SpUtil.getWebConfigInfo(this.mContext);
        if (!TextUtils.isEmpty(webConfigInfo)) {
            try {
                this.mData = new JSONObject(webConfigInfo);
            } catch (JSONException unused) {
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void checkUpdate(boolean z, WebCloudCheckCallBack webCloudCheckCallBack) {
        if (!this.isRunning) {
            this.mCallBack = webCloudCheckCallBack;
            this.isRunning = true;
            this.mIsColdStart = z;
            this.mStatId = Ajx3ActionLogUtil.generateStatId();
            Updatable11Request updatable11Request = new Updatable11Request();
            updatable11Request.d = "";
            updatable11Request.b = "1";
            updatable11Request.c = bny.c;
            final Ajx3WebCloudConfigParser ajx3WebCloudConfigParser = new Ajx3WebCloudConfigParser(this.mIsColdStart ? "cold_start" : "hot_start", this.mStatId);
            Ajx3ActionLogUtil.actionLogAjxWebCloud("B001", this.mIsColdStart ? "cold_start" : "hot_start", this.mStatId, "");
            AocsRequestHolder.getInstance().sendUpdatable11(updatable11Request, new AosResponseCallbackOnUi<AosByteResponse>() {
                public void onSuccess(AosByteResponse aosByteResponse) {
                    if (aosByteResponse == null || aosByteResponse.getResult() == null) {
                        if (Ajx3WebCloudConfig.this.mCallBack != null) {
                            Ajx3WebCloudConfig.this.mCallBack.onFinished();
                        }
                        return;
                    }
                    ajx3WebCloudConfigParser.parser((byte[]) aosByteResponse.getResult());
                    Ajx3WebCloudConfig.this.handleData(ajx3WebCloudConfigParser.mData);
                    Ajx3WebCloudConfig.this.isRunning = false;
                    if (Ajx3WebCloudConfig.this.mCallBack != null) {
                        Ajx3WebCloudConfig.this.mCallBack.onFinished();
                    }
                }

                public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                    Ajx3WebCloudConfig.this.isRunning = false;
                    Ajx3ActionLogUtil.actionLogAjxWebCloudOnLine(Ajx3WebCloudConfig.this.mIsColdStart ? "cold_start" : "hot_start", Ajx3WebCloudConfig.this.mStatId, "", -1);
                    if (Ajx3WebCloudConfig.this.mCallBack != null) {
                        Ajx3WebCloudConfig.this.mCallBack.onFinished();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void handleData(String str) {
        Ajx3SpUtil.setWebConfigInfo(this.mContext, str);
        this.mData = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                this.mData = new JSONObject(str);
            } catch (JSONException unused) {
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public long getCheckInterval(String str) {
        if (!TextUtils.isEmpty(str) && this.mData != null && this.mData.has(str)) {
            try {
                JSONObject jSONObject = this.mData.getJSONObject(str);
                if (jSONObject != null && jSONObject.has("value")) {
                    JSONObject jSONObject2 = new JSONObject(jSONObject.getString("value"));
                    if (jSONObject2.has("checkInterval")) {
                        return jSONObject2.getLong("checkInterval");
                    }
                }
            } catch (JSONException unused) {
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public String getWebConfig(String str) {
        try {
            if (!TextUtils.isEmpty(str) && this.mData != null && this.mData.has(str)) {
                return this.mData.getString(str);
            }
        } catch (Exception unused) {
        }
        return "";
    }
}
