package com.autonavi.minimap.offline.JsRequest;

import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.inter.inner.ICheckOfflineResponse;
import com.autonavi.minimap.offline.map.inter.impl.OfflineManagerImpl;

public class OfflineSetNaviSwitchStateRequest extends UseCase<Request, Response, Integer> {

    public static final class Request implements RequestValues {
    }

    public static final class Response implements ResponseValue {
        private boolean isCanUse;

        public Response(boolean z) {
            this.isCanUse = z;
        }

        public final boolean isCanUse() {
            return this.isCanUse;
        }

        public final void setCanUse(boolean z) {
            this.isCanUse = z;
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(Request request) {
        new OfflineManagerImpl().checkOfflineNavi(AMapPageUtil.getPageContext(), new ICheckOfflineResponse() {
            public void callback(boolean z) {
                OfflineSetNaviSwitchStateRequest.this.getUseCaseCallback().onSuccess(new Response(z));
            }
        });
    }
}
