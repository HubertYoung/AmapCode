package com.autonavi.bundle.offline.ajx;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.offline.service.IAutoOfflineJsCallback3;
import com.autonavi.minimap.offline.service.INMJSAutoCarTransmission3;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("autoCarTransmissionService")
@KeepPublicClassMembers
@KeepName
public class ModuleJsOfflineAutoService extends AbstractModule {
    private INMJSAutoCarTransmission3 mOfflineService = ((INMJSAutoCarTransmission3) ank.a(INMJSAutoCarTransmission3.class));

    @Keep
    public static class DownloadApkParam implements Serializable {
        public String build;
        public String display_ver;
        public String div;
        public int id;
        public String send_params;
        public long total_bytes;
        public String url;
        public String version;
    }

    static class a extends IAutoOfflineJsCallback3 {
        protected String a;
        protected JsFunctionCallback b;

        public final boolean isForMock() {
            return false;
        }

        public a(JsFunctionCallback jsFunctionCallback, String str) {
            this.b = jsFunctionCallback;
            this.a = str;
        }

        @SuppressFBWarnings({"RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT"})
        public final void call(String str) {
            if (this.b != null) {
                this.b.callback(str);
            }
        }

        public final Object callback(Object... objArr) {
            return this.b.callback(objArr);
        }
    }

    public ModuleJsOfflineAutoService(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("requestCityListInfo")
    public void requestCityListInfo(JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.requestCityListInfo(new a(jsFunctionCallback, "requestCityListInfo"));
    }

    @AjxMethod("getAutoCarState")
    public void getAutoCarState(JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.getAutoCarState(new a(jsFunctionCallback, "getAutoCarState"));
    }

    @AjxMethod("startSendAllCities")
    public void startSendAllCities(String str, JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.startSendAllCities(str, new a(jsFunctionCallback, "startSendAllCities"));
    }

    @AjxMethod("prepareSendApk")
    public void prepareSendApk(String str, JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.prepareSendApk(str, new a(jsFunctionCallback, "prepareSendApk"));
    }

    @AjxMethod("initApkInfo")
    public void initApkInfo(JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.initApkInfo(new a(jsFunctionCallback, "initApkInfo"));
    }

    @AjxMethod("startSendApk")
    public void startSendApk(String str, JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.startSendApk(str, new a(jsFunctionCallback, "startSendApk"));
    }

    @AjxMethod("stopSendApk")
    public void stopSendApk() {
        this.mOfflineService.stopSendApk();
    }

    @AjxMethod("stopSend")
    public void stopSendCity(JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.stopSendCity(new a(jsFunctionCallback, "stopSend"));
    }

    @AjxMethod("stopSendCities")
    public void stopSendCities(String str, JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.stopSendCities(str, new a(jsFunctionCallback, "stopSendCities"));
    }

    @AjxMethod("registerAutoLinkListener")
    public void registerAutoLinkListener(JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.registerAutoLinkListener(new a(jsFunctionCallback, "registerAutoLinkListener"));
    }

    @AjxMethod("unRegisterAutoListener")
    public void unRegisterAutoListener() {
        this.mOfflineService.unRegisterAutoListener();
    }

    @AjxMethod("autoConnectStatus")
    public void autoConnectStatus(JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(Boolean.valueOf(this.mOfflineService.autoConnectStatus()));
    }

    @AjxMethod("screenKeepScreenLit")
    public void screenKeepScreenLit(boolean z) {
        this.mOfflineService.screenKeepScreenLit(z);
    }

    @AjxMethod("downloadApkBind")
    public void downloadApkBind(JsFunctionCallback jsFunctionCallback) {
        this.mOfflineService.bindDownloadApk(new a(jsFunctionCallback, "downloadApkOp"));
    }

    @AjxMethod("downloadApkOp")
    public void downloadApkOp(int i, String str) {
        DownloadApkParam downloadApkParam = getDownloadApkParam(str);
        switch (i) {
            case 1:
                this.mOfflineService.startDownloadApk(downloadApkParam.url, downloadApkParam.version, downloadApkParam.display_ver, downloadApkParam.build, downloadApkParam.div, downloadApkParam.total_bytes, downloadApkParam.send_params);
                return;
            case 2:
                this.mOfflineService.pauseDownloadApk(downloadApkParam.id);
                return;
            case 3:
                this.mOfflineService.resumeDownloadApk(downloadApkParam.id);
                return;
            case 4:
                this.mOfflineService.stopDownloadApk(downloadApkParam.id);
                return;
            case 5:
                this.mOfflineService.removeApk(downloadApkParam.id);
                return;
            case 6:
                this.mOfflineService.unbindDownloadApk();
                break;
        }
    }

    private DownloadApkParam getDownloadApkParam(String str) {
        DownloadApkParam downloadApkParam = new DownloadApkParam();
        if (!TextUtils.isEmpty(str)) {
            try {
                return (DownloadApkParam) JsonUtil.fromString(str, DownloadApkParam.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return downloadApkParam;
    }
}
