package com.autonavi.minimap.offline.JsRequest;

import android.text.TextUtils;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.helper.JsAllCityInfoHelper;
import com.autonavi.minimap.offline.model.Province;
import com.autonavi.minimap.offline.util.JsConvertUtils;
import java.util.List;
import org.json.JSONException;

public class OfflineAllCityRequest extends UseCase<CityInfoParam, CityInfoResponse, Integer> {

    public static final class CityInfoParam implements RequestValues {
    }

    public static final class CityInfoResponse implements ResponseValue {
        private String allCityInfoStr;

        public final String getAllCityInfoStr() {
            return this.allCityInfoStr;
        }

        public final void setAllCityInfoStr(String str) {
            this.allCityInfoStr = str;
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(CityInfoParam cityInfoParam) {
        List<Province> convertProvince = JsConvertUtils.convertProvince();
        if (convertProvince.isEmpty()) {
            getUseCaseCallback().onError(Integer.valueOf(0));
            return;
        }
        try {
            String convertAllCityInfoJOStr = new JsAllCityInfoHelper().convertAllCityInfoJOStr(convertProvince);
            if (TextUtils.isEmpty(convertAllCityInfoJOStr)) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            CityInfoResponse cityInfoResponse = new CityInfoResponse();
            cityInfoResponse.setAllCityInfoStr(convertAllCityInfoJOStr);
            getUseCaseCallback().onSuccess(cityInfoResponse);
        } catch (JSONException unused) {
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }
}
