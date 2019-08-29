package com.autonavi.minimap.offline.auto.protocol.request;

import android.text.TextUtils;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientHelper;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATDeleteCityDataItemRequest;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;

public class AutoDelCitysRequest extends UseCase<AutoDelCityParam, AutoDelCityResponse, Integer> {
    String mUrl;

    public static final class AutoDelCityParam implements RequestValues {
        String mParams = "";

        public AutoDelCityParam(ATDeleteCityDataItemRequest aTDeleteCityDataItemRequest) {
            try {
                this.mParams = JsonUtil.toString(aTDeleteCityDataItemRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class AutoDelCityResponse implements ResponseValue {
        private int code;
        private String msg;

        public int getCode() {
            return this.code;
        }

        public void setCode(int i) {
            this.code = i;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String str) {
            this.msg = str;
        }
    }

    public AutoDelCitysRequest() {
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
    public void executeUseCase(AutoDelCityParam autoDelCityParam) {
        if (TextUtils.isEmpty(this.mUrl)) {
            getUseCaseCallback().onError(Integer.valueOf(201));
            return;
        }
        try {
            byte[] postBytes = HttpClientHelper.getInstance().postBytes(this.mUrl, "/dataservice/deletecity", null, autoDelCityParam.mParams.getBytes());
            if (postBytes == null || postBytes.length <= 0) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            getUseCaseCallback().onSuccess((AutoDelCityResponse) JsonUtil.fromString(new String(postBytes), AutoDelCityResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }
}
