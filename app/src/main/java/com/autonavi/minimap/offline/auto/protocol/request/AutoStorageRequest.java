package com.autonavi.minimap.offline.auto.protocol.request;

import android.text.TextUtils;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientHelper;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATServerStateRequest;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATServerStateResponse;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;

public class AutoStorageRequest extends UseCase<AutoStorageParam, ATServerStateResponse, Integer> {
    String mUrl;

    public static final class AutoStorageParam implements RequestValues {
        String mParams = "";

        public AutoStorageParam(ATServerStateRequest aTServerStateRequest) {
            try {
                this.mParams = JsonUtil.toString(aTServerStateRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public AutoStorageRequest() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        DiscoverInfo wifiDiscoverInfo = iAutoRemoteController != null ? iAutoRemoteController.getWifiDiscoverInfo() : null;
        if (wifiDiscoverInfo != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(wifiDiscoverInfo.IP);
            sb.append(":");
            sb.append(wifiDiscoverInfo.httpPort);
            this.mUrl = sb.toString();
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(AutoStorageParam autoStorageParam) {
        try {
            if (TextUtils.isEmpty(this.mUrl)) {
                getUseCaseCallback().onError(Integer.valueOf(201));
                return;
            }
            byte[] postBytes = HttpClientHelper.getInstance().postBytes(this.mUrl, "/dataservice/serverstate", null, autoStorageParam.mParams.getBytes());
            if (postBytes == null || postBytes.length <= 0) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            getUseCaseCallback().onSuccess((ATServerStateResponse) JsonUtil.fromString(new String(postBytes), ATServerStateResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }
}
