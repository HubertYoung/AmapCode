package defpackage;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.route.sharebike.order.OrderInfo;
import com.autonavi.minimap.route.sharebike.order.OrderState;
import java.util.HashSet;
import java.util.Set;

/* renamed from: egz reason: default package */
/* compiled from: OrderStatusDispatcher */
public class egz {
    private Set<egw> a = new HashSet();
    private OrderState b;
    private OrderInfo c;
    private OrderInfo d;
    private int e;

    public final synchronized boolean a(OrderState orderState, OrderInfo orderInfo, OrderInfo orderInfo2, int i) {
        this.c = orderInfo;
        this.d = orderInfo2;
        this.e = i;
        this.b = orderState;
        return b(this.b, this.c, this.d, this.e);
    }

    public final synchronized void a(egw egw) {
        if (egw != null) {
            this.a.add(egw);
        }
    }

    public final synchronized void b(egw egw) {
        if (egw != null) {
            this.a.remove(egw);
        }
    }

    private synchronized boolean b(OrderState orderState, OrderInfo orderInfo, OrderInfo orderInfo2, int i) {
        Object obj;
        String str;
        String str2;
        String str3;
        StringBuilder sb = new StringBuilder();
        sb.append(egx.class.getName());
        sb.append(Token.SEPARATOR);
        sb.append(egz.class.getName());
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder("updateOrderStatus orderState:");
        sb3.append(orderState);
        sb3.append("notiOrderInfo: ");
        sb3.append(orderInfo == null ? "null" : Integer.valueOf(orderInfo.status));
        sb3.append("pendingOrderInfo: ");
        if (orderInfo2 == null) {
            obj = "null";
        } else {
            obj = Integer.valueOf(orderInfo2.status);
        }
        sb3.append(obj);
        sb3.append(" failedTime ");
        sb3.append(i);
        eao.f(sb2, sb3.toString());
        if (this.a.size() > 0) {
            HashSet hashSet = new HashSet();
            hashSet.addAll(this.a);
            StringBuilder sb4 = new StringBuilder(" orderstate");
            if (orderState == null) {
                str = "null";
            } else {
                str = orderState.name();
            }
            sb4.append(str);
            sb4.append(" orderInfo:");
            if (orderInfo == null) {
                str2 = "null";
            } else {
                StringBuilder sb5 = new StringBuilder("net ");
                sb5.append(orderInfo.hasNetFailed);
                sb5.append(" state ");
                sb5.append(orderInfo.status);
                sb5.append(" orderId ");
                sb5.append(orderInfo.orderId);
                str2 = sb5.toString();
            }
            sb4.append(str2);
            sb4.append(" pendingOrderInfo:");
            if (orderInfo2 == null) {
                str3 = "null";
            } else {
                StringBuilder sb6 = new StringBuilder("net ");
                sb6.append(orderInfo2.hasNetFailed);
                sb6.append(" state");
                sb6.append(orderInfo2.status);
                sb6.append(" orderId ");
                sb6.append(orderInfo2.orderId);
                str3 = sb6.toString();
            }
            sb4.append(str3);
            eao.e("OrderStatusDispatcher", sb4.toString());
            if (hashSet.size() > 0) {
                for (Object next : hashSet) {
                    try {
                        if (next instanceof egw) {
                            ((egw) next).a(orderState, orderInfo, i);
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        }
        return false;
    }
}
