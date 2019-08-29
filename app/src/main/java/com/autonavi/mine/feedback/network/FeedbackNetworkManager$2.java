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

public class FeedbackNetworkManager$2 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ String a;

    public FeedbackNetworkManager$2(String str) {
        this.a = str;
    }

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse == null || aosByteResponse.getResult() == null) {
            a();
            return;
        }
        try {
            if (AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse).optInt("code", 0) == 1) {
                cgi.a(this.a);
            } else {
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.feedback_fail_check_network));
            }
        } catch (JSONException unused) {
            a();
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        a();
    }

    private static void a() {
        ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.feedback_fail_check_network));
    }
}
