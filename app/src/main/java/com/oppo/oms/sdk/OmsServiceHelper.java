package com.oppo.oms.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.autonavi.widget.ui.BalloonLayout;
import com.oppo.oms.IOmsService;
import com.oppo.oms.IOmsService.Stub;
import com.oppo.oms.sdk.Util.Utils;
import com.oppo.oms.sdk.entity.ErrorEntity;
import com.oppo.oms.sdk.entity.FeatureEntity;
import com.oppo.oms.sdk.entity.FeatureRequest;
import com.oppo.oms.sdk.entity.Result;
import java.lang.reflect.Field;
import org.json.JSONObject;

class OmsServiceHelper {
    private static final String ACTION_OMS_SERVICE = "action.com.oppo.oms.OMS_SERVICE";
    private static final String ERROR_NOT_EXIST = "4001";
    private static final String PKG_NAME_OMS_SERVICE = "com.oppo.oms";
    private Context mContext;
    /* access modifiers changed from: private */
    public Object mLock = new Object();
    /* access modifiers changed from: private */
    public IOmsService mOmsService;
    private ServiceConnection mServiceConnection;

    public OmsServiceHelper(Context context) {
        this.mContext = context.getApplicationContext();
        this.mServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                OmsServiceHelper.this.mOmsService = Stub.asInterface(iBinder);
                synchronized (OmsServiceHelper.this.mLock) {
                    OmsServiceHelper.this.mLock.notify();
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                OmsServiceHelper.this.mOmsService = null;
            }
        };
    }

    public Result<ErrorEntity, FeatureEntity> requestFeature(FeatureRequest featureRequest) {
        Field[] declaredFields;
        Result<ErrorEntity, FeatureEntity> result = new Result<>();
        if (!Utils.isAppInstalled(this.mContext, "com.oppo.oms")) {
            ErrorEntity errorEntity = new ErrorEntity();
            errorEntity.code = ERROR_NOT_EXIST;
            errorEntity.msg = "OmsService does not exist";
            result.setError(errorEntity);
            return result;
        }
        if (this.mOmsService == null) {
            synchronized (this.mLock) {
                try {
                    bindService();
                    this.mLock.wait(BalloonLayout.DEFAULT_DISPLAY_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ErrorEntity errorEntity2 = new ErrorEntity();
        FeatureEntity featureEntity = new FeatureEntity();
        if (this.mOmsService == null) {
            errorEntity2.code = "-1";
            errorEntity2.msg = "Service is busy,please try again later";
            result.setError(errorEntity2);
        } else {
            JSONObject jSONObject = new JSONObject();
            for (Field field : FeatureRequest.class.getDeclaredFields()) {
                try {
                    jSONObject.put(field.getName(), field.get(featureRequest));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                JSONObject jSONObject2 = new JSONObject(this.mOmsService.requestCapacityAuth(jSONObject.toString()));
                result.setSuccess(jSONObject2.getBoolean("success"));
                if (result.isSuccess()) {
                    JSONObject jSONObject3 = jSONObject2.getJSONObject("data");
                    featureEntity.appPackage = jSONObject3.getString("appPackage");
                    featureEntity.certVersion = jSONObject3.getString("certVersion");
                    featureEntity.statusWord = jSONObject3.getString("authorityStatusWord");
                    result.setData(featureEntity);
                } else {
                    JSONObject jSONObject4 = jSONObject2.getJSONObject("error");
                    errorEntity2.code = jSONObject4.optString("code");
                    errorEntity2.msg = jSONObject4.optString("msg");
                    result.setError(errorEntity2);
                }
                try {
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } catch (Exception e4) {
                e4.printStackTrace();
                errorEntity2.code = "-1";
                errorEntity2.msg = "Service is busy,please try again later";
                result.setError(errorEntity2);
            } finally {
                try {
                    this.mContext.unbindService(this.mServiceConnection);
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
            }
        }
        return result;
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction(ACTION_OMS_SERVICE);
        intent.setPackage("com.oppo.oms");
        this.mContext.bindService(intent, this.mServiceConnection, 1);
    }
}
