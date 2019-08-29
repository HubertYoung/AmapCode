package com.autonavi.mine.feedback.network;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import org.json.JSONException;

public class FeedbackNetworkManager$1 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ String a;

    public FeedbackNetworkManager$1(String str) {
        this.a = str;
    }

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse == null || aosByteResponse.getResult() == null) {
            ToastHelper.showToast(AbstractAOSParser.ERROR_NETWORK);
            return;
        }
        try {
            int optInt = AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse).optInt("code", 0);
            if (optInt == 1) {
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.feedback_satisfy_response));
                cgi.a(this.a);
                return;
            }
            if (optInt != 14) {
                ToastHelper.showToast(AbstractAOSParser.DEFAULT_ERROR_MSG);
            }
        } catch (JSONException unused) {
            ToastHelper.showToast(AbstractAOSParser.PARSE_ERROR);
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        ToastHelper.showToast(aosResponseException.isCallbackError ? AbstractAOSParser.DEFAULT_ERROR_MSG : AbstractAOSParser.ERROR_NETWORK);
    }
}
