package com.autonavi.minimap.offline.auto.protocol;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkPackage;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkPackageResponse;
import com.autonavi.minimap.offline.auto.protocol.request.AutoApkInfoRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoApkInfoRequest.AutoApkInfoResponse;
import com.autonavi.minimap.offline.dataaccess.UseCase.UseCaseCallback;
import com.autonavi.minimap.offline.dataaccess.UseCaseHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class ApkUpdateHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    /* access modifiers changed from: private */
    public ATApkPackage mAosInfo;
    /* access modifiers changed from: private */
    public ATApkPackageResponse mApkInfo;

    @Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"dic", "div", "ver_info"}, url = "ws/app/conf/app_update/auto_telecontroller/?")
    public static class AutoApkUpdateConfig implements ParamEntity {
        public String ver_info = "";
    }

    public interface UpdateCallback {
        void onUpdate(ATApkPackageResponse aTApkPackageResponse, ATApkPackage aTApkPackage);
    }

    static class a {
        /* access modifiers changed from: private */
        public static final ApkUpdateHandler a = new ApkUpdateHandler();
    }

    public static ApkUpdateHandler get() {
        return a.a;
    }

    private ApkUpdateHandler() {
        this.mApkInfo = loadAutoApkInfo();
    }

    public void checkUpdate(final UpdateCallback updateCallback) {
        UseCaseHandler.getInstance().execute(new AutoApkInfoRequest(), new UseCaseCallback<AutoApkInfoResponse, Integer>() {
            public final /* synthetic */ void onSuccess(Object obj) {
                ATApkPackageResponse aTApkPackageResponse = ((AutoApkInfoResponse) obj).rsp;
                if (aTApkPackageResponse == null) {
                    aTApkPackageResponse = new ATApkPackageResponse();
                }
                ApkUpdateHandler.this.checkAosInfo(aTApkPackageResponse, updateCallback);
            }

            public final void onCancel() {
                ApkUpdateHandler.this.checkAosInfo(ApkUpdateHandler.this.mApkInfo, updateCallback);
            }

            public final /* synthetic */ void onError(Object obj) {
                ApkUpdateHandler.this.checkAosInfo(ApkUpdateHandler.this.mApkInfo, updateCallback);
            }
        });
    }

    private boolean apkInfoEquals(ATApkPackageResponse aTApkPackageResponse, ATApkPackageResponse aTApkPackageResponse2) {
        if (aTApkPackageResponse == null && aTApkPackageResponse2 == null) {
            return true;
        }
        return aTApkPackageResponse != null && aTApkPackageResponse2 != null && TextUtils.equals(aTApkPackageResponse.getAppver(), aTApkPackageResponse2.getAppver()) && TextUtils.equals(aTApkPackageResponse.getDiv(), aTApkPackageResponse2.getDiv()) && TextUtils.equals(aTApkPackageResponse.getBuild(), aTApkPackageResponse2.getBuild()) && TextUtils.equals(aTApkPackageResponse.getDic(), aTApkPackageResponse2.getDic()) && TextUtils.equals(aTApkPackageResponse.getDip(), aTApkPackageResponse2.getDip()) && TextUtils.equals(aTApkPackageResponse.getDiu(), aTApkPackageResponse2.getDiu());
    }

    /* access modifiers changed from: private */
    public void checkAosInfo(ATApkPackageResponse aTApkPackageResponse, UpdateCallback updateCallback) {
        if (!apkInfoEquals(aTApkPackageResponse, this.mApkInfo) || this.mAosInfo == null || updateCallback == null) {
            this.mApkInfo = aTApkPackageResponse;
            saveAutoApkInfo(this.mApkInfo);
            requestAosInfo(aTApkPackageResponse, updateCallback);
            return;
        }
        updateCallback.onUpdate(aTApkPackageResponse, this.mAosInfo);
    }

    private void requestAosInfo(final ATApkPackageResponse aTApkPackageResponse, final UpdateCallback updateCallback) {
        AutoApkUpdateConfig autoApkUpdateConfig = new AutoApkUpdateConfig();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appver", aTApkPackageResponse.getAppver());
            jSONObject.put(LocationParams.PARA_COMMON_AUTODIV, aTApkPackageResponse.getDiv());
            jSONObject.put("build", aTApkPackageResponse.getBuild());
            jSONObject.put(LocationParams.PARA_COMMON_DIC, aTApkPackageResponse.getDic());
            jSONObject.put(LocationParams.PARA_COMMON_DIP, aTApkPackageResponse.getDip());
            jSONObject.put(LocationParams.PARA_COMMON_DIU, aTApkPackageResponse.getDiu());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        autoApkUpdateConfig.ver_info = jSONObject.toString();
        AosPostRequest b = aax.b(autoApkUpdateConfig);
        yq.a();
        yq.a((AosRequest) b, (AosResponseCallback<T>) new FalconAosPrepareResponseCallback<ATApkPackage>() {
            public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
                return b(aosByteResponse);
            }

            public final /* synthetic */ void a(Object obj) {
                ApkUpdateHandler.this.mAosInfo = (ATApkPackage) obj;
                if (updateCallback != null) {
                    updateCallback.onUpdate(aTApkPackageResponse, ApkUpdateHandler.this.mAosInfo);
                }
            }

            private static ATApkPackage b(AosByteResponse aosByteResponse) {
                try {
                    return (ATApkPackage) JsonUtil.fromString(new String((byte[]) aosByteResponse.getResult(), "UTF-8"), ATApkPackage.class);
                } catch (Exception unused) {
                    return null;
                }
            }

            public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
                ApkUpdateHandler.this.mAosInfo = null;
                if (updateCallback != null) {
                    updateCallback.onUpdate(aTApkPackageResponse, null);
                }
            }
        });
    }

    private void saveAutoApkInfo(ATApkPackageResponse aTApkPackageResponse) {
        if (aTApkPackageResponse != null) {
            try {
                new MapSharePreference(SharePreferenceName.SharedPreferences).putStringValue("amap_auto_20_apk_info", JsonUtil.toString(aTApkPackageResponse));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ATApkPackageResponse loadAutoApkInfo() {
        String stringValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue("amap_auto_20_apk_info", null);
        if (TextUtils.isEmpty(stringValue)) {
            return new ATApkPackageResponse();
        }
        try {
            return (ATApkPackageResponse) JsonUtil.fromString(stringValue, ATApkPackageResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ATApkPackageResponse();
        }
    }
}
