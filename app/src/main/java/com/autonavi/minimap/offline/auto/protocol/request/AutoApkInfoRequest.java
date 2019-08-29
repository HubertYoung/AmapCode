package com.autonavi.minimap.offline.auto.protocol.request;

import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientHelper;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkPackageResponse;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import java.nio.charset.Charset;

public class AutoApkInfoRequest extends UseCase<AutoApkInfoParam, AutoApkInfoResponse, Integer> {

    public static final class AutoApkInfoParam implements RequestValues {
    }

    public static final class AutoApkInfoResponse implements ResponseValue {
        public ATApkPackageResponse rsp;

        public final void setRsp(ATApkPackageResponse aTApkPackageResponse) {
            this.rsp = aTApkPackageResponse;
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(AutoApkInfoParam autoApkInfoParam) {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        DiscoverInfo wifiDiscoverInfo = iAutoRemoteController != null ? iAutoRemoteController.getWifiDiscoverInfo() : null;
        if (wifiDiscoverInfo == null || !iAutoRemoteController.IsWifiConnected()) {
            getUseCaseCallback().onError(Integer.valueOf(301));
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(wifiDiscoverInfo.IP);
        sb.append(":");
        sb.append(wifiDiscoverInfo.httpPort);
        try {
            byte[] bytes = HttpClientHelper.getInstance().getBytes(sb.toString(), "/dataservice/requestapkinfo", null);
            if (bytes == null || bytes.length <= 0) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            ATApkPackageResponse aTApkPackageResponse = (ATApkPackageResponse) JsonUtil.fromString(new String(bytes, Charset.forName("utf-8")), ATApkPackageResponse.class);
            if (aTApkPackageResponse.getCode() == 1) {
                AutoApkInfoResponse autoApkInfoResponse = new AutoApkInfoResponse();
                autoApkInfoResponse.setRsp(aTApkPackageResponse);
                getUseCaseCallback().onSuccess(autoApkInfoResponse);
                return;
            }
            getUseCaseCallback().onError(Integer.valueOf(0));
        } catch (Exception e) {
            e.printStackTrace();
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }
}
