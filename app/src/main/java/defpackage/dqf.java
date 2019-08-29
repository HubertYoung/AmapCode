package defpackage;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.hotel.page.OrderHotelDetailPage;

/* renamed from: dqf reason: default package */
/* compiled from: OrderHotelDetailPresenter */
public final class dqf extends AbstractBasePresenter<OrderHotelDetailPage> implements OnClickListener {
    public dqf(OrderHotelDetailPage orderHotelDetailPage) {
        super(orderHotelDetailPage);
    }

    public final void onStart() {
        ((OrderHotelDetailPage) this.mPage).setSoftInputMode(18);
        super.onStart();
        OrderHotelDetailPage orderHotelDetailPage = (OrderHotelDetailPage) this.mPage;
        if (orderHotelDetailPage.b != null && orderHotelDetailPage.b.getWebView() != null) {
            orderHotelDetailPage.b.setOnLaunchTaobaoLogin(orderHotelDetailPage);
            orderHotelDetailPage.b.getWebView().onResume();
        }
    }

    public final void onStop() {
        OrderHotelDetailPage orderHotelDetailPage = (OrderHotelDetailPage) this.mPage;
        if (!(orderHotelDetailPage.b == null || orderHotelDetailPage.b.getWebView() == null)) {
            orderHotelDetailPage.b.getWebView().onPause();
        }
        super.onStop();
    }

    public final void onDestroy() {
        ((OrderHotelDetailPage) this.mPage).b.setOnLaunchTaobaoLogin(null);
        super.onDestroy();
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        ((OrderHotelDetailPage) this.mPage).a();
        return true;
    }

    public final void onClick(View view) {
        if (view.getId() == R.id.title_btn_left) {
            ((OrderHotelDetailPage) this.mPage).a();
        }
    }
}
