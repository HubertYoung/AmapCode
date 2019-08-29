package com.autonavi.map.search.js.action;

import android.text.TextUtils;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenPoiAction$1 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ int a;
    final /* synthetic */ int b;
    final /* synthetic */ int c;
    final /* synthetic */ boolean d;
    final /* synthetic */ byp e;

    public OpenPoiAction$1(byp byp, int i, int i2, int i3, boolean z) {
        this.e = byp;
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = z;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        aho.a(new Runnable() {
            public final void run() {
                bct.a();
            }
        });
        final JsAdapter a2 = this.e.a();
        if (a2 != null && aosByteResponse != null && aosByteResponse.getResult() != null) {
            String str = new String((byte[]) aosByteResponse.getResult());
            if (TextUtils.isEmpty(str)) {
                ToastHelper.showLongToast(this.e.a().mPageContext.getActivity().getString(R.string.ic_net_error_tipinfo));
                return;
            }
            try {
                final List<POI> a3 = ccb.a(new JSONObject(str));
                if (a3.size() <= 0) {
                    ToastHelper.showLongToast("未查找到结果");
                } else {
                    aho.a(new Runnable() {
                        public final void run() {
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putObject("POI", (POI) a3.get(0));
                            pageBundle.putBoolean("isFromWeb", true);
                            pageBundle.putInt("showTab", 0);
                            pageBundle.putInt("status", OpenPoiAction$1.this.a);
                            pageBundle.putInt(IndoorLocationProvider.NAME, OpenPoiAction$1.this.b);
                            pageBundle.putInt("floor", OpenPoiAction$1.this.c);
                            pageBundle.putBoolean("showIndoorMap", OpenPoiAction$1.this.d);
                            byp.b(a2, OpenPoiAction$1.this.a, pageBundle);
                        }
                    });
                }
            } catch (JSONException unused) {
                ToastHelper.showLongToast(this.e.a().mPageContext.getActivity().getString(R.string.ic_net_error_tipinfo));
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        aho.a(new Runnable() {
            public final void run() {
                bct.a();
            }
        });
    }
}
