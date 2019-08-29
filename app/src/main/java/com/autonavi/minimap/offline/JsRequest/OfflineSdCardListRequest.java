package com.autonavi.minimap.offline.JsRequest;

import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.blutils.SdCardInfo;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import com.autonavi.minimap.offline.helper.JsSdCardInfoHelper;
import java.util.ArrayList;

public class OfflineSdCardListRequest extends UseCase<EmptyParam, Response, Integer> {
    private static final int ERROR_CODE_GET_SD_CARD_INFO = 1;
    private static final int ERROR_CODE_GET_SD_CARD_JSON_DATA_INFO = 3;
    private static final int ERROR_CODE_GET_STORAGE_INFO = 2;

    public static final class EmptyParam implements RequestValues {
    }

    public static final class Response implements ResponseValue {
        private String sdCardListJoStr;

        public final String getSdCardListJoStr() {
            return this.sdCardListJoStr;
        }

        public final void setSdCardListJoStr(String str) {
            this.sdCardListJoStr = str;
        }
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(EmptyParam emptyParam) {
        ArrayList<SdCardInfo> enumExternalSDcardInfo = FileUtil.enumExternalSDcardInfo(PathManager.a().a);
        if (enumExternalSDcardInfo == null || enumExternalSDcardInfo.isEmpty()) {
            getUseCaseCallback().onError(Integer.valueOf(1));
            return;
        }
        String a = PathManager.a().a(DirType.OFFLINE);
        if (TextUtils.isEmpty(a)) {
            getUseCaseCallback().onError(Integer.valueOf(2));
            return;
        }
        String convertSdCardList = JsSdCardInfoHelper.convertSdCardList(enumExternalSDcardInfo, a);
        if (TextUtils.isEmpty(convertSdCardList)) {
            getUseCaseCallback().onError(Integer.valueOf(3));
            return;
        }
        Response response = new Response();
        response.setSdCardListJoStr(convertSdCardList);
        getUseCaseCallback().onSuccess(response);
    }
}
