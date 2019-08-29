package defpackage;

import android.support.annotation.NonNull;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailDialogFactory.OnDialogClickListener;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dxf reason: default package */
/* compiled from: FootRideNaviAccessManager */
public final class dxf extends dxc<dxa> {
    /* access modifiers changed from: protected */
    public final /* synthetic */ void b(final a aVar, Object obj) {
        final dxa dxa = (dxa) obj;
        if (dxa.c) {
            if (!dwu.d()) {
                aVar.a(true, (String) "dialog_type_declare_dest", (OnDialogClickListener) new OnDialogClickListener() {
                    public final void a(int i) {
                        if (i == 0) {
                            dwu.f();
                            dxf.b(aVar, dxa);
                        }
                    }
                });
            } else {
                b(aVar, dxa);
            }
        } else if (!dwu.c()) {
            aVar.a(true, (String) "dialog_type_declare_foot", (OnDialogClickListener) new OnDialogClickListener() {
                public final void a(int i) {
                    if (i == 0) {
                        dwu.e();
                        dxf.a(aVar, dxa);
                    }
                }
            });
        } else {
            a(aVar, dxa);
        }
    }

    static void a(@NonNull a aVar, @NonNull dxa dxa) {
        if (dxa.a == null) {
            ToastHelper.showToast(aVar.getContext().getString(R.string.bus_navi_jump_to_foot_tips));
        } else if (ebi.a(AMapPageUtil.getPageContext(), LocationInstrument.getInstance().getLatestPosition(), dxa.a.getPoint(), 1)) {
            PageBundle pageBundle = new PageBundle();
            avi avi = (avi) a.a.a(avi.class);
            if (avi != null) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("endPoi", bnx.b(dxa.a));
                    jSONObject.put("fromWhere", dxa.d ? "gjxqtm" : "gjxqlb");
                    if (dxa.b == 1) {
                        jSONObject.put(H5Param.FROM_TYPE, "nfbus");
                    } else if (dxa.b == 4) {
                        jSONObject.put(H5Param.FROM_TYPE, "nfsubway");
                    }
                    pageBundle.putString("bundle_key_obj_result", jSONObject.toString());
                    aVar.a(avi.c().a(1), pageBundle, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void b(@NonNull a aVar, @NonNull dxa dxa) {
        if (dxa.a == null) {
            ToastHelper.showToast(aVar.getContext().getString(R.string.bus_navi_jump_to_ride_tips));
        } else if (edq.a(AMapPageUtil.getPageContext(), LocationInstrument.getInstance().getLatestPosition(), dxa.a.getPoint(), "当前位置距离终点太远\n建议选择其他出行方式")) {
            aww aww = (aww) a.a.a(aww.class);
            if (aww != null) {
                try {
                    PageBundle pageBundle = new PageBundle();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("endPoi", bnx.b(dxa.a));
                    jSONObject.put("fromWhere", dxa.d ? "gjxqtm" : "gjxqlb");
                    pageBundle.putString("bundle_key_obj_result", jSONObject.toString());
                    edr.a(0);
                    aVar.a(aww.a().a(1), pageBundle, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
