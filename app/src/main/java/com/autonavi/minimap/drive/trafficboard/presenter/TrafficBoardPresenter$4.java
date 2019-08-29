package com.autonavi.minimap.drive.trafficboard.presenter;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.trafficboard.page.TrafficBoardPage;
import org.json.JSONObject;

public class TrafficBoardPresenter$4 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ CompatDialog a;
    final /* synthetic */ djw b;

    public TrafficBoardPresenter$4(djw djw, CompatDialog compatDialog) {
        this.b = djw;
        this.a = compatDialog;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (this.a != null) {
            this.a.dismiss();
        }
        JSONObject jSONObject = null;
        if (aosByteResponse != null) {
            try {
                byte[] bArr = (byte[]) aosByteResponse.getResult();
                if (bArr != null) {
                    String str = new String(bArr, "UTF-8");
                    if (!TextUtils.isEmpty(str)) {
                        jSONObject = new JSONObject(str);
                    }
                }
            } catch (Exception unused) {
            }
        }
        if (jSONObject != null) {
            this.b.a = new dju().a(jSONObject);
            if ("120000".equals(this.b.a.a)) {
                djw.b(this.b, this.b.a);
            } else if (!TextUtils.isEmpty(this.b.a.d)) {
                ToastHelper.showToast(this.b.a.d);
                if (this.b.a.h != null && this.b.a.h.size() > 0) {
                    djw.b(this.b, this.b.a);
                }
                ((TrafficBoardPage) this.b.mPage).b();
                ((TrafficBoardPage) this.b.mPage).a();
            } else {
                ToastHelper.showToast(((TrafficBoardPage) this.b.mPage).getString(R.string.traffic_board_network_err));
                ((TrafficBoardPage) this.b.mPage).b();
                ((TrafficBoardPage) this.b.mPage).a();
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.dismiss();
        }
        ToastHelper.showToast(((TrafficBoardPage) this.b.mPage).getString(R.string.traffic_board_network_err));
        ((TrafficBoardPage) this.b.mPage).b();
        ((TrafficBoardPage) this.b.mPage).a();
    }
}
