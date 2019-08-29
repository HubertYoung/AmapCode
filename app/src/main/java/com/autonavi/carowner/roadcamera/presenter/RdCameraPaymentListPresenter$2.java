package com.autonavi.carowner.roadcamera.presenter;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.carowner.roadcamera.model.RdCameraPaymentListModel;
import com.autonavi.carowner.roadcamera.page.RdCameraPaymentListPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class RdCameraPaymentListPresenter$2 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ int a;
    final /* synthetic */ bhv b;

    public RdCameraPaymentListPresenter$2(bhv bhv, int i) {
        this.b = bhv;
        this.a = i;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        this.b.d();
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
            ((RdCameraPaymentListPage) this.b.mPage).h.onRefreshComplete();
            try {
                if (jSONObject.getString("code").equals("1")) {
                    if (jSONObject.has("data")) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        this.b.e = jSONObject2.optInt(NewHtcHomeBadger.COUNT);
                        ((RdCameraPaymentListPage) this.b.mPage).a(2, this.b.e);
                        this.b.d = this.a;
                        List<RdCameraPaymentItem> parsePaymentItem = RdCameraPaymentItem.parsePaymentItem(jSONObject2);
                        if (parsePaymentItem != null && parsePaymentItem.size() > 0) {
                            if (this.b.d == 1) {
                                ((RdCameraPaymentListModel) this.b.b).c().clearData();
                            }
                            ((RdCameraPaymentListModel) this.b.b).c().addData(parsePaymentItem);
                        }
                        ((RdCameraPaymentListPage) this.b.mPage).a();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        this.b.d();
        ToastHelper.showLongToast(this.b.b().getString(R.string.rd_camera_apply_net_error));
    }
}
