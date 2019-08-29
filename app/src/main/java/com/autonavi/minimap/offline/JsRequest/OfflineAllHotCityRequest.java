package com.autonavi.minimap.offline.JsRequest;

import android.text.TextUtils;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.helper.JsAllCityInfoHelper;
import com.autonavi.minimap.offline.model.City;
import com.autonavi.minimap.offline.util.JsConvertUtils;
import java.util.List;
import org.json.JSONException;

public class OfflineAllHotCityRequest extends UseCase<Request, AllHotCityResponse, Integer> {

    public static final class AllHotCityResponse implements ResponseValue {
        String allHotCityInfo;

        public final String getAllHotCityInfo() {
            return this.allHotCityInfo;
        }

        public final void setAllHotCityInfo(String str) {
            this.allHotCityInfo = str;
        }
    }

    public static final class Request implements RequestValues {
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(Request request) {
        List<City> convertHotCity = JsConvertUtils.convertHotCity();
        if (convertHotCity.isEmpty()) {
            getUseCaseCallback().onError(Integer.valueOf(0));
            return;
        }
        try {
            String convertAllHotCityInfoJOStr = new JsAllCityInfoHelper().convertAllHotCityInfoJOStr(convertHotCity);
            if (TextUtils.isEmpty(convertAllHotCityInfoJOStr)) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            AllHotCityResponse allHotCityResponse = new AllHotCityResponse();
            allHotCityResponse.setAllHotCityInfo(convertAllHotCityInfoJOStr);
            getUseCaseCallback().onSuccess(allHotCityResponse);
        } catch (JSONException unused) {
            getUseCaseCallback().onError(Integer.valueOf(0));
        }
    }
}
