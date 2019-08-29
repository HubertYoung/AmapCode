package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.Callback;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.model.OrderRequest;
import com.autonavi.minimap.life.order.base.net.OrderNetManager$1;
import com.autonavi.minimap.life.order.viewpoint.net.ViewPointOrderNetWorkListener;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.ScenicListRequest;
import com.autonavi.minimap.ordercenter.param.ScenicOrdersListRequest;

/* renamed from: dqi reason: default package */
/* compiled from: ViewPointUIController */
public final class dqi extends dpj {
    public dpq b;
    private OrderRequest c;

    public dqi(Context context) {
        super(context);
    }

    public dqi(Context context, dpq dpq) {
        super(context);
        this.b = dpq;
    }

    public final AosRequest a() {
        return a(1, this.a.getString(R.string.life_order_viewpoint_list_loading));
    }

    public final AosRequest a(int i, String str) {
        try {
            return a(new ViewPointOrderNetWorkListener(this.b), 20, i, str);
        } catch (Exception e) {
            kf.a((Throwable) e);
            return null;
        }
    }

    public final void b(int i, String str) {
        try {
            ViewPointOrderNetWorkListener viewPointOrderNetWorkListener = new ViewPointOrderNetWorkListener(this.b);
            dps a = dnm.a();
            dqn dqn = new dqn(i);
            ScenicListRequest scenicListRequest = new ScenicListRequest();
            scenicListRequest.b = dqn.j;
            scenicListRequest.c = 10;
            CompatDialog a2 = !TextUtils.isEmpty(str) ? aav.a(scenicListRequest, str) : null;
            if (a2 != null) {
                a2.show();
            }
            OrderRequestHolder.getInstance().sendScenicList(scenicListRequest, new OrderNetManager$1(a, viewPointOrderNetWorkListener, dqn, a2));
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    public static AosRequest a(Callback<dpp> callback, int i, int i2, String str) {
        return dnm.a().a((dpm) new dqm(i2), i, callback, str);
    }

    public final AosRequest a(OrderRequest orderRequest) {
        this.c = orderRequest;
        return a(1);
    }

    public final AosRequest a(int i) {
        try {
            ViewPointOrderNetWorkListener viewPointOrderNetWorkListener = new ViewPointOrderNetWorkListener(this.b);
            dps a = dnm.a();
            dql dql = new dql(i);
            OrderRequest orderRequest = this.c;
            String string = this.a.getString(R.string.life_order_viewpoint_list_loading);
            ScenicOrdersListRequest scenicOrdersListRequest = new ScenicOrdersListRequest();
            scenicOrdersListRequest.c = dql.j;
            scenicOrdersListRequest.d = orderRequest.phone;
            scenicOrdersListRequest.e = orderRequest.code;
            a.a((dpm) dql, (Callback<dpp>) viewPointOrderNetWorkListener, scenicOrdersListRequest, string);
            return scenicOrdersListRequest;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return null;
        }
    }
}
