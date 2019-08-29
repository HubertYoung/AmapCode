package com.autonavi.minimap.offline.JsRequest;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONException;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.helper.JsAllDownloadCityHelper;
import com.autonavi.minimap.offline.model.DownloadProvince;
import com.autonavi.minimap.offline.util.JsConvertUtils;
import java.util.List;

public class OfflineAllDownloadCityRequest extends UseCase<AllDownloadCityParam, AllDownloadCityResponse, Integer> {

    public static final class AllDownloadCityParam implements RequestValues {
        public String code = "";

        public AllDownloadCityParam(String str) {
            this.code = str;
        }
    }

    public static final class AllDownloadCityResponse implements ResponseValue {
        private String mDownloadCityInfoJOStr;

        public final String getDownloadCityInfoJOStr() {
            return this.mDownloadCityInfoJOStr;
        }

        public final void setDownloadAllCityJOStr(String str) {
            this.mDownloadCityInfoJOStr = str;
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(AllDownloadCityParam allDownloadCityParam) {
        List<DownloadProvince> list;
        if ("0".equals(allDownloadCityParam.code)) {
            list = JsConvertUtils.getDownloadProvince();
        } else {
            list = JsConvertUtils.getRecommendDownloadProvinces();
        }
        if (list.isEmpty()) {
            getUseCaseCallback().onError(Integer.valueOf(0));
            return;
        }
        try {
            String convertDownloadAllCityJOStr = JsAllDownloadCityHelper.convertDownloadAllCityJOStr(list);
            if (TextUtils.isEmpty(convertDownloadAllCityJOStr)) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            AllDownloadCityResponse allDownloadCityResponse = new AllDownloadCityResponse();
            allDownloadCityResponse.setDownloadAllCityJOStr(convertDownloadAllCityJOStr);
            getUseCaseCallback().onSuccess(allDownloadCityResponse);
        } catch (JSONException unused) {
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }
}
