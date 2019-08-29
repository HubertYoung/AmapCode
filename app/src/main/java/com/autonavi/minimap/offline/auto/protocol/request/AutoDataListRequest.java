package com.autonavi.minimap.offline.auto.protocol.request;

import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientHelper;
import com.autonavi.minimap.offline.auto.model.nativeModel.AutoJsCity;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATCityDataListResponse;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.utils.log.Logger;

public class AutoDataListRequest extends UseCase<AutoDataListParam, AutoJsCity, Integer> {
    private static final String TAG = "AutoDataListRequest";
    private Logger logger = Logger.getLogger(TAG);
    private DiscoverInfo mDevice;

    public static final class AutoDataListParam implements RequestValues {
    }

    public AutoDataListRequest() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            this.mDevice = iAutoRemoteController.getWifiDiscoverInfo();
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(AutoDataListParam autoDataListParam) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mDevice == null) {
            AutoJsCity buildJsAllCitys = AutoUtils.buildJsAllCitys(null, AutoJsonUtils.parseJsonNativceCity(AutoUtils.getNativeDownloadCity()));
            buildJsAllCitys.setCode("301");
            getUseCaseCallback().onSuccess(buildJsAllCitys);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mDevice.IP);
        sb.append(":");
        sb.append(this.mDevice.httpPort);
        try {
            byte[] bytes = HttpClientHelper.getInstance().getBytes(sb.toString(), "/dataservice/requestdatalistinfo", null);
            if (bytes != null) {
                if (bytes.length > 0) {
                    long currentTimeMillis2 = System.currentTimeMillis();
                    Logger logger2 = this.logger;
                    StringBuilder sb2 = new StringBuilder("request allcity time:");
                    sb2.append(currentTimeMillis2 - currentTimeMillis);
                    logger2.e(sb2.toString());
                    ATCityDataListResponse aTCityDataListResponse = (ATCityDataListResponse) JsonUtil.fromString(new String(bytes), ATCityDataListResponse.class);
                    Logger logger3 = this.logger;
                    StringBuilder sb3 = new StringBuilder("auto data List:");
                    sb3.append(JsonUtil.toString(aTCityDataListResponse));
                    logger3.e(sb3.toString());
                    AutoJsCity buildJsAllCitys2 = AutoUtils.buildJsAllCitys(aTCityDataListResponse, AutoJsonUtils.parseJsonNativceCity(AutoUtils.getNativeDownloadCity()));
                    buildJsAllCitys2.setCode(aTCityDataListResponse.getCode());
                    long currentTimeMillis3 = System.currentTimeMillis();
                    Logger logger4 = this.logger;
                    StringBuilder sb4 = new StringBuilder("build js city time:");
                    sb4.append(currentTimeMillis3 - currentTimeMillis2);
                    logger4.e(sb4.toString());
                    getUseCaseCallback().onSuccess(buildJsAllCitys2);
                    return;
                }
            }
            getUseCaseCallback().onError(Integer.valueOf(0));
        } catch (Exception unused) {
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }
}
