package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.ride.page.RideHistoryPage;

/* renamed from: een reason: default package */
/* compiled from: RideHistoryPresenter */
public final class een extends eaf<RideHistoryPage> {
    public een(RideHistoryPage rideHistoryPage) {
        super(rideHistoryPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
        ((RideHistoryPage) this.mPage).a();
    }

    public final void onResume() {
        super.onResume();
        RideHistoryPage rideHistoryPage = (RideHistoryPage) this.mPage;
        if (euk.a()) {
            euk.a(AMapAppGlobal.getTopActivity(), rideHistoryPage.getResources().getColor(R.color.c_12));
        }
    }

    public final void onStop() {
        super.onStop();
    }
}
