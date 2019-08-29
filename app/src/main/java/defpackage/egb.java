package defpackage;

import android.os.Handler;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.sharebike.model.EndBill;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.order.OrderInfo;
import com.autonavi.minimap.route.sharebike.order.OrderState;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.minimap.statusbar.StatusBarManager.FeatureType;

/* renamed from: egb reason: default package */
/* compiled from: BaseNotificationAdapter */
public class egb implements egw {
    public static volatile egb c;
    public ehz a = new ehz();
    public ehx b = new ehx();
    private ehv d = ehv.a();

    private egb() {
        eao.d("wbsww", "create notifcation tunnel");
        eao.d("wbsww", "notification end");
    }

    public static egb a() {
        if (c == null) {
            synchronized (egb.class) {
                try {
                    c = new egb();
                }
            }
        }
        return c;
    }

    private void a(RideState rideState) {
        eao.d("wbsww", "requestSuccess");
        this.a.a(rideState);
        ehx ehx = this.b;
        StringBuilder sb = new StringBuilder("network stat");
        sb.append(ehx.e);
        eao.d("wbsww", sb.toString());
        if (ehx.e) {
            drp.b().a((c) ehx.h);
            ehx.e = false;
        }
        Class<?> topPageClass = AMapPageUtil.getTopPageClass();
        if (topPageClass != null) {
            StringBuilder sb2 = new StringBuilder("apply mCurrentTopPage=");
            sb2.append(ehx.a);
            sb2.append("dest page =");
            sb2.append(topPageClass.getSimpleName());
            eao.d("wbsww", sb2.toString());
            ehx.a = topPageClass.getSimpleName();
        }
        if (rideState != null) {
            StringBuilder sb3 = new StringBuilder("state11=");
            sb3.append(rideState);
            sb3.append("integer=");
            sb3.append(rideState.status);
            eao.d("wbsww", sb3.toString());
            ehx.b = rideState;
            ehx.a(rideState.status);
        }
        ehv ehv = this.d;
        String str = null;
        bui mVPActivityContext = AMapPageUtil.getMVPActivityContext();
        if (!(mVPActivityContext == null || mVPActivityContext.c() == null)) {
            str = mVPActivityContext.c().getSimpleName();
        }
        ehv.a(rideState, str);
    }

    public final boolean a(OrderState orderState, OrderInfo orderInfo, int i) {
        if (orderInfo == null) {
            return false;
        }
        if (orderInfo.hasNetFailed) {
            boolean z = orderInfo.hasNetFailed;
            eao.d("wbsww", "requestFailed");
            ehx ehx = this.b;
            StringBuilder sb = new StringBuilder("netFailed=");
            sb.append(z);
            sb.append("upTo30Times=");
            sb.append(i);
            eao.d("wbsww", sb.toString());
            Handler handler = new Handler(AMapPageFramework.getAppContext().getMainLooper());
            drp.b().b((c) ehx.h);
            ehx.e = true;
            handler.post(new Runnable() {
                public final void run() {
                    eao.d("wbsww", "netFailed invisiable view");
                    StatusBarManager.d().d(FeatureType.TYPE_BICYCLE);
                }
            });
        } else if (orderInfo.extraData instanceof RideState) {
            a((RideState) orderInfo.extraData);
        } else if ((orderInfo.extraData instanceof EndBill) && ((EndBill) orderInfo.extraData).errorCode == 1) {
            RideState rideState = new RideState();
            rideState.status = 0;
            rideState.bikeId = null;
            a(rideState);
        }
        return false;
    }
}
