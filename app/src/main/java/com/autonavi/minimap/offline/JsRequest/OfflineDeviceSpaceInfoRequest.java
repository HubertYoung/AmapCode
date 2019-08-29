package com.autonavi.minimap.offline.JsRequest;

import android.text.TextUtils;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.helper.JsDeviceSpaceInfoHelper;

public class OfflineDeviceSpaceInfoRequest extends UseCase<Request, Response, Integer> {

    public static final class Request implements RequestValues {
    }

    public static final class Response implements ResponseValue {
        String deviceSpaceInfo = "";

        public final String getDeviceSpaceInfo() {
            return this.deviceSpaceInfo;
        }

        public final void setDeviceSpaceInfo(String str) {
            this.deviceSpaceInfo = str;
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(Request request) {
        String infoJOStr = JsDeviceSpaceInfoHelper.getInfoJOStr();
        if (!TextUtils.isEmpty(infoJOStr)) {
            Response response = new Response();
            response.setDeviceSpaceInfo(infoJOStr);
            getUseCaseCallback().onSuccess(response);
            return;
        }
        getUseCaseCallback().onError(Integer.valueOf(0));
    }
}
