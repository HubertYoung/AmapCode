package defpackage;

import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.hotel.net.OrderHotelAosCallback;
import com.autonavi.minimap.life.order.hotel.net.OrderHotelParamByCategory;
import com.autonavi.minimap.life.order.hotel.net.OrderHotelParamNew;
import com.autonavi.minimap.life.order.hotel.page.OrderHotelListPage;
import com.autonavi.minimap.life.order.hotel.presenter.OrderHotelListPresenter$1;
import com.autonavi.minimap.life.order.hotel.presenter.OrderHotelListPresenter$2;
import java.util.List;

/* renamed from: dqg reason: default package */
/* compiled from: OrderHotelListPresenter */
public final class dqg extends AbstractBasePresenter<OrderHotelListPage> implements dqd {
    private doe a = new dof();

    public dqg(OrderHotelListPage orderHotelListPage) {
        super(orderHotelListPage);
    }

    public final void a(int i, int i2, String str) {
        a(i, i2, str, new OrderHotelAosCallback(this));
    }

    private void a(int i, int i2, String str, OrderHotelAosCallback orderHotelAosCallback) {
        OrderHotelParamByCategory orderHotelParamByCategory = new OrderHotelParamByCategory();
        orderHotelParamByCategory.hotel_category = i;
        orderHotelParamByCategory.pagenum = i2;
        orderHotelParamByCategory.pagesize = 10;
        orderHotelParamByCategory.limit = 1;
        AosGetRequest a2 = aax.a(orderHotelParamByCategory);
        CompatDialog a3 = aav.a(a2, str);
        OrderHotelListPresenter$1 orderHotelListPresenter$1 = new OrderHotelListPresenter$1(this, orderHotelAosCallback, i2, a3);
        a3.show();
        in.a().a((AosRequest) a2, (AosResponseCallback<T>) orderHotelListPresenter$1);
    }

    public final void a(int i, String str) {
        OrderHotelParamNew orderHotelParamNew = new OrderHotelParamNew();
        orderHotelParamNew.pagenum = i;
        orderHotelParamNew.pagesize = 10;
        AosGetRequest a2 = aax.a(orderHotelParamNew);
        CompatDialog a3 = aav.a(a2, str);
        OrderHotelListPresenter$2 orderHotelListPresenter$2 = new OrderHotelListPresenter$2(this, new OrderHotelAosCallback(this), i, a3);
        a3.show();
        in.a().a((AosRequest) a2, (AosResponseCallback<T>) orderHotelListPresenter$2);
    }

    public final void a(int i, int i2) {
        ((OrderHotelListPage) this.mPage).onDelete(i, i2);
    }

    public final void a(int i, List<dpl> list, int i2, int i3) {
        ((OrderHotelListPage) this.mPage).onListUpdate(list, i2, i, i3);
    }

    public final void a() {
        ((OrderHotelListPage) this.mPage).onNetError();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((OrderHotelListPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public final void a(int i) {
        int i2 = R.string.result_success2;
        if (i == 0) {
            i2 = R.string.error_unknown;
        } else if (i == 14) {
            i2 = R.string.error_require_login;
        } else if (i != 17) {
            switch (i) {
                case 2:
                    i2 = R.string.error_request_failure;
                    break;
                case 3:
                    i2 = R.string.error_incorrect_parameter;
                    break;
                case 4:
                    i2 = R.string.error_incorrect_signature;
                    break;
                case 5:
                    i2 = R.string.app_download_fail_verify_error;
                    break;
                case 6:
                    i2 = R.string.error_no_support;
                    break;
            }
        } else {
            i2 = R.string.verify_error;
        }
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(i2));
    }
}
