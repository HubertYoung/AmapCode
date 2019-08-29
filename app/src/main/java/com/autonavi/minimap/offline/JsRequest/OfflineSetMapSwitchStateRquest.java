package com.autonavi.minimap.offline.JsRequest;

import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;

public class OfflineSetMapSwitchStateRquest extends UseCase<Request, Response, Integer> {

    public static final class Request implements RequestValues {
    }

    public static final class Response implements ResponseValue {
        private boolean isDownloadMap = true;
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(Request request) {
    }
}
