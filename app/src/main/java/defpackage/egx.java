package defpackage;

import android.text.TextUtils;
import com.autonavi.minimap.route.sharebike.order.OrderInfo;
import com.autonavi.minimap.route.sharebike.order.OrderState;

/* renamed from: egx reason: default package */
/* compiled from: OrderManager */
public class egx implements egv {
    private static egx a;
    private egy b = new egy();
    private OrderInfo c = this.b.c();
    private OrderInfo d = this.b.b();
    private int e;
    private egz f = new egz();

    private egx() {
    }

    public static synchronized egv a() {
        egx egx;
        synchronized (egx.class) {
            try {
                if (a == null) {
                    a = new egx();
                }
                egx = a;
            }
        }
        return egx;
    }

    public final void a(OrderInfo orderInfo) {
        OrderState orderState;
        String name = egx.class.getName();
        StringBuilder sb = new StringBuilder("updateOrderStatus status:");
        sb.append(Integer.valueOf(orderInfo.status));
        eao.f(name, sb.toString());
        this.c = orderInfo;
        boolean z = false;
        if (orderInfo.hasNetFailed) {
            this.e++;
        } else {
            this.e = 0;
        }
        egy egy = this.b;
        if (!TextUtils.isEmpty(orderInfo.orderId) && egy.a != null && !TextUtils.isEmpty(egy.a.orderId) && !TextUtils.equals(orderInfo.orderId, egy.a.orderId)) {
            egy.a(orderInfo);
        }
        if (!orderInfo.hasNetFailed) {
            switch (orderInfo.status) {
                case 0:
                    orderState = OrderState.IDLE;
                    break;
                case 1:
                    orderState = OrderState.RIDE;
                    break;
                case 2:
                    orderState = OrderState.UNLOCK;
                    break;
                case 3:
                    orderState = OrderState.ORDER_ERROR_REPORT;
                    break;
            }
        }
        orderState = null;
        if (orderState != null) {
            egy.a(orderState);
            if (orderState != null) {
                switch (AnonymousClass1.a[orderState.ordinal()]) {
                    case 5:
                    case 6:
                    case 7:
                        z = true;
                        break;
                }
            }
            if (!z) {
                egy.b(orderInfo);
            } else {
                egy.b(null);
            }
        }
        this.d = this.b.b();
        this.f.a(this.b.a(), this.c, this.d, this.e);
    }

    public final void a(String str) {
        egy egy = this.b;
        OrderInfo b2 = egy.b();
        if (b2 != null && !TextUtils.isEmpty(str) && TextUtils.equals(str, b2.orderId)) {
            egy.a(OrderState.IDLE);
        }
    }

    public final void a(egw egw) {
        this.f.a(egw);
    }

    public final void b(egw egw) {
        this.f.b(egw);
    }
}
