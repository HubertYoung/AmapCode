package com.autonavi.minimap.offline.JsRequest;

import android.text.TextUtils;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.helper.JsAlongWayAllCityHelper;

public class AllCityInProvinceRequest extends UseCase<EmptyParam, Response, String> {
    private String provinces;

    public static final class EmptyParam implements RequestValues {
    }

    public static final class Response implements ResponseValue {
        private String alongWayCityJoStr;

        public final String getAlongWayCityJoStr() {
            return this.alongWayCityJoStr;
        }

        public final void setAlongWayCityJoStr(String str) {
            this.alongWayCityJoStr = str;
        }
    }

    public AllCityInProvinceRequest(String str) {
        this.provinces = str;
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(EmptyParam emptyParam) {
        String convertNativeCityInfoInProvince = JsAlongWayAllCityHelper.convertNativeCityInfoInProvince(this.provinces);
        if (!TextUtils.isEmpty(convertNativeCityInfoInProvince)) {
            Response response = new Response();
            response.setAlongWayCityJoStr(convertNativeCityInfoInProvince);
            getUseCaseCallback().onSuccess(response);
            return;
        }
        getUseCaseCallback().onError("convert all city data to json string error!!!");
    }
}
