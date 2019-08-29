package com.autonavi.minimap.offline.auto.protocol.request;

import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientHelper;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkPackage;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATExecuteResponse;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadApkPackageRequest;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import java.nio.charset.Charset;

public class AutoPrepareSendApkRequest extends UseCase<AutoPrepareSendApkParam, AutoPrepareSendApkResponse, Integer> {

    public static final class AutoPrepareSendApkParam implements RequestValues {
        ATApkPackage mApkPackage;

        public AutoPrepareSendApkParam(ATApkPackage aTApkPackage) {
            this.mApkPackage = aTApkPackage;
        }
    }

    public static final class AutoPrepareSendApkResponse implements ResponseValue {
        public ATExecuteResponse rsp;

        public final void setRsp(ATExecuteResponse aTExecuteResponse) {
            this.rsp = aTExecuteResponse;
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(AutoPrepareSendApkParam autoPrepareSendApkParam) {
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
        String sb2 = sb.toString();
        try {
            ATUploadApkPackageRequest aTUploadApkPackageRequest = new ATUploadApkPackageRequest();
            aTUploadApkPackageRequest.setApkInfo(autoPrepareSendApkParam.mApkPackage);
            byte[] postBytes = HttpClientHelper.getInstance().postBytes(sb2, "/dataservice/prepareuploadapk", null, JsonUtil.toString(aTUploadApkPackageRequest).getBytes());
            if (postBytes == null || postBytes.length <= 0) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            AutoPrepareSendApkResponse autoPrepareSendApkResponse = new AutoPrepareSendApkResponse();
            autoPrepareSendApkResponse.setRsp((ATExecuteResponse) JsonUtil.fromString(new String(postBytes, Charset.forName("utf-8")), ATExecuteResponse.class));
            getUseCaseCallback().onSuccess(autoPrepareSendApkResponse);
        } catch (Exception e) {
            e.printStackTrace();
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }
}
