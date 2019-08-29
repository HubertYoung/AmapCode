package com.autonavi.minimap.drive.sticker.presenter;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.sticker.page.StickersPage;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;

public class StickersPresenter$MyHandler$2 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ StickersPage a;
    final /* synthetic */ GeoPoint b;
    final /* synthetic */ a c;

    public StickersPresenter$MyHandler$2(a aVar, StickersPage stickersPage, GeoPoint geoPoint) {
        this.c = aVar;
        this.a = stickersPage;
        this.b = geoPoint;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        djf djf = new djf();
        try {
            djf.parser(aosByteResponse.getResponseBodyData());
        } catch (UnsupportedEncodingException e) {
            kf.a((Throwable) e);
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
        }
        if (this.a.isAlive()) {
            this.a.a();
            if (djf.a != null) {
                djg djg = djf.a;
                djg.j = this.b;
                this.c.a.a = djg;
                this.a.a(djg, true);
            } else if (djf.errorCode == 7) {
                this.a.b();
                this.c.a.a = null;
                this.a.a(this.a.getString(R.string.tip_no_stickers_result_nearby));
            } else {
                ToastHelper.showLongToast(this.a.getString(R.string.tip_no_data_result));
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a.isAlive()) {
            this.a.a();
            ToastHelper.showLongToast(this.a.getString(R.string.tip_no_data_result));
        }
    }
}
