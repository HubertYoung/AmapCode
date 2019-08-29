package com.autonavi.minimap.offline.auto.protocol.request;

import android.text.TextUtils;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientHelper;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATCityDataItemRequest;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATCityDataItemResponse;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;

public class AutoCityInfoRequest extends UseCase<CityInfoParam, CityInfoResponse, Integer> {
    private String mUrl;

    public static final class CityInfoParam implements RequestValues {
        String mParams = "";

        public CityInfoParam(ATCityDataItemRequest aTCityDataItemRequest) {
            try {
                this.mParams = JsonUtil.toString(aTCityDataItemRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static final class CityInfoResponse implements ResponseValue {
        ATCityDataItemResponse cityInfo;

        public final void setCityInfo(ATCityDataItemResponse aTCityDataItemResponse) {
            this.cityInfo = aTCityDataItemResponse;
        }

        public final ATCityDataItemResponse getCityInfo() {
            return this.cityInfo;
        }
    }

    public AutoCityInfoRequest() {
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
    public void executeUseCase(CityInfoParam cityInfoParam) {
        try {
            if (TextUtils.isEmpty(this.mUrl)) {
                getUseCaseCallback().onError(Integer.valueOf(201));
                return;
            }
            byte[] postBytes = HttpClientHelper.getInstance().postBytes(this.mUrl, "/dataservice/requestcityinfo", null, cityInfoParam.mParams.getBytes());
            if (postBytes == null || postBytes.length <= 0) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            CityInfoResponse cityInfoResponse = new CityInfoResponse();
            cityInfoResponse.cityInfo = (ATCityDataItemResponse) JsonUtil.fromString(new String(postBytes), ATCityDataItemResponse.class);
            getUseCaseCallback().onSuccess(cityInfoResponse);
        } catch (Exception e) {
            e.printStackTrace();
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }
}
