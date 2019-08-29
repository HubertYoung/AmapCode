package com.amap.bundle.drive.ajx.module;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class ModuleDriveCommonUtilsImpl {
    private static final String TAG = "ModuleDriveCommonUtilsImpl";
    private JsFunctionCallback mJsPhoneStateCallback = null;
    private PhoneStateListener mPhoneStateListener = null;
    private TelephonyManager mTelephonyManager;

    public void registerPhoneStateChange(JsFunctionCallback jsFunctionCallback) {
        this.mJsPhoneStateCallback = jsFunctionCallback;
        this.mTelephonyManager = (TelephonyManager) AMapAppGlobal.getApplication().getSystemService("phone");
        this.mPhoneStateListener = new PhoneStateListener() {
            public void onCallStateChanged(int i, String str) {
                switch (i) {
                    case 0:
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("status", "0");
                            ModuleDriveCommonUtilsImpl.this.notifyPhoneStateChange(jSONObject.toString());
                            return;
                        } catch (JSONException unused) {
                            return;
                        }
                    case 1:
                        try {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("status", "1");
                            ModuleDriveCommonUtilsImpl.this.notifyPhoneStateChange(jSONObject2.toString());
                            return;
                        } catch (JSONException unused2) {
                            return;
                        }
                    case 2:
                        try {
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put("status", "2");
                            ModuleDriveCommonUtilsImpl.this.notifyPhoneStateChange(jSONObject3.toString());
                            return;
                        } catch (JSONException unused3) {
                            break;
                        }
                }
            }
        };
        this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
    }

    public void notifyPhoneStateChange(String str) {
        AMapLog.i(TAG, "notifyPhoneStateChange json:".concat(String.valueOf(str)));
        ku.a().c("NaviMonitor", "ModuleDriveCommonUtilsImpl notifyPhoneStateChange json:".concat(String.valueOf(str)));
        if (this.mJsPhoneStateCallback != null) {
            this.mJsPhoneStateCallback.callback(str);
        }
    }
}
