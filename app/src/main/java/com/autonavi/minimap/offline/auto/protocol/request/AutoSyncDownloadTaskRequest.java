package com.autonavi.minimap.offline.auto.protocol.request;

import android.text.TextUtils;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientHelper;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadCityDataListRequest;
import com.autonavi.minimap.offline.auto.model.protocolModel.AutoBaseResponse;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.utils.log.Logger;

public class AutoSyncDownloadTaskRequest extends UseCase<AutoSyncDownloadTaskParam, AutoBaseResponse, Integer> {
    Logger logger = Logger.getLogger("AutoSyncDownloadTaskRequest");
    String mUrl;

    public static final class AutoSyncDownloadTaskParam implements RequestValues {
        String mParams;

        public AutoSyncDownloadTaskParam(ATUploadCityDataListRequest aTUploadCityDataListRequest) {
            try {
                this.mParams = JsonUtil.toString(aTUploadCityDataListRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public AutoSyncDownloadTaskRequest() {
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
    public void executeUseCase(AutoSyncDownloadTaskParam autoSyncDownloadTaskParam) {
        if (TextUtils.isEmpty(this.mUrl)) {
            getUseCaseCallback().onError(Integer.valueOf(201));
            return;
        }
        String str = autoSyncDownloadTaskParam.mParams;
        this.logger.e("AutoSyncDownloadTaskRequest ".concat(String.valueOf(str)));
        try {
            byte[] postBytes = HttpClientHelper.getInstance().postBytes(this.mUrl, "/dataservice/senddownloadtask", null, str.getBytes());
            if (postBytes == null || postBytes.length <= 0) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            getUseCaseCallback().onSuccess((AutoBaseResponse) JsonUtil.fromString(new String(postBytes), AutoBaseResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
