package defpackage;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.autonavi.minimap.route.sharebike.order.OrderInfo;
import com.autonavi.minimap.route.sharebike.order.OrderState;

/* renamed from: egy reason: default package */
/* compiled from: OrderStateMachine */
public final class egy {
    OrderInfo a;
    private OrderState b;

    /* renamed from: egy$1 reason: invalid class name */
    /* compiled from: OrderStateMachine */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[OrderState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.autonavi.minimap.route.sharebike.order.OrderState[] r0 = com.autonavi.minimap.route.sharebike.order.OrderState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.minimap.route.sharebike.order.OrderState r1 = com.autonavi.minimap.route.sharebike.order.OrderState.SCAN     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.minimap.route.sharebike.order.OrderState r1 = com.autonavi.minimap.route.sharebike.order.OrderState.UNLOCK     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.autonavi.minimap.route.sharebike.order.OrderState r1 = com.autonavi.minimap.route.sharebike.order.OrderState.RIDE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.autonavi.minimap.route.sharebike.order.OrderState r1 = com.autonavi.minimap.route.sharebike.order.OrderState.ORDER_CHANGE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.autonavi.minimap.route.sharebike.order.OrderState r1 = com.autonavi.minimap.route.sharebike.order.OrderState.IDLE     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                com.autonavi.minimap.route.sharebike.order.OrderState r1 = com.autonavi.minimap.route.sharebike.order.OrderState.ORDER_ERROR_REPORT     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.autonavi.minimap.route.sharebike.order.OrderState r1 = com.autonavi.minimap.route.sharebike.order.OrderState.ORDER_PENDING     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.egy.AnonymousClass1.<clinit>():void");
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:4|5|6|(2:8|9)|10|11) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0017 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized com.autonavi.minimap.route.sharebike.order.OrderState a() {
        /*
            r3 = this;
            monitor-enter(r3)
            com.autonavi.minimap.route.sharebike.order.OrderState r0 = r3.b     // Catch:{ all -> 0x0021 }
            if (r0 != 0) goto L_0x001d
            r0 = -1
            java.lang.String r1 = "share_bike_order_status_key"
            java.lang.String r1 = defpackage.ehs.b(r1)     // Catch:{ all -> 0x0021 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ NumberFormatException -> 0x0017 }
            if (r2 != 0) goto L_0x0017
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x0017 }
            r0 = r1
        L_0x0017:
            com.autonavi.minimap.route.sharebike.order.OrderState r0 = com.autonavi.minimap.route.sharebike.order.OrderState.getOrderState(r0)     // Catch:{ all -> 0x0021 }
            r3.b = r0     // Catch:{ all -> 0x0021 }
        L_0x001d:
            com.autonavi.minimap.route.sharebike.order.OrderState r0 = r3.b     // Catch:{ all -> 0x0021 }
            monitor-exit(r3)
            return r0
        L_0x0021:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.egy.a():com.autonavi.minimap.route.sharebike.order.OrderState");
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a(OrderInfo orderInfo) {
        ehs.a((String) "share_bike_pend_order_info_key", JSON.toJSONString(orderInfo));
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void b(OrderInfo orderInfo) {
        ehs.a((String) "share_bike_current_order_info_key", JSON.toJSONString(orderInfo));
        this.a = orderInfo;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void a(OrderState orderState) {
        if (orderState != null) {
            this.b = orderState;
            StringBuilder sb = new StringBuilder();
            sb.append(orderState.getCode());
            ehs.a((String) "share_bike_order_status_key", sb.toString());
        }
    }

    public final synchronized OrderInfo b() {
        try {
            String b2 = ehs.b("share_bike_pend_order_info_key");
            if (TextUtils.isEmpty(b2)) {
                return null;
            }
            return (OrderInfo) JSON.parseObject(b2, OrderInfo.class);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:9|(2:11|(2:13|(2:15|(3:17|18|19)))(3:20|21|(2:23|(2:25|(3:27|28|29)))(3:30|31|(2:35|(3:37|38|39)))))|40|41|42|43) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x0080 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized com.autonavi.minimap.route.sharebike.order.OrderInfo c() {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "share_bike_riding_status_id"
            java.lang.String r0 = defpackage.ehs.b(r0)     // Catch:{ all -> 0x0087 }
            boolean r0 = java.lang.Boolean.parseBoolean(r0)     // Catch:{ all -> 0x0087 }
            java.lang.String r1 = "share_bike_unlocking_status_id"
            java.lang.String r1 = defpackage.ehs.b(r1)     // Catch:{ all -> 0x0087 }
            boolean r1 = java.lang.Boolean.parseBoolean(r1)     // Catch:{ all -> 0x0087 }
            com.autonavi.minimap.route.sharebike.order.OrderInfo r2 = r3.a     // Catch:{ all -> 0x0087 }
            if (r2 != 0) goto L_0x0084
            if (r0 != 0) goto L_0x001d
            if (r1 == 0) goto L_0x0084
        L_0x001d:
            java.lang.String r0 = "share_bike_current_order_info_key"
            java.lang.String r0 = defpackage.ehs.b(r0)     // Catch:{ all -> 0x0087 }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0087 }
            if (r1 != 0) goto L_0x0084
            java.lang.Class<com.autonavi.minimap.route.sharebike.order.OrderInfo> r1 = com.autonavi.minimap.route.sharebike.order.OrderInfo.class
            java.lang.Object r0 = com.alibaba.fastjson.JSON.parseObject(r0, r1)     // Catch:{ all -> 0x0087 }
            com.autonavi.minimap.route.sharebike.order.OrderInfo r0 = (com.autonavi.minimap.route.sharebike.order.OrderInfo) r0     // Catch:{ all -> 0x0087 }
            if (r0 == 0) goto L_0x0080
            int r1 = r0.dataSource     // Catch:{ all -> 0x0087 }
            r2 = 2
            if (r1 != r2) goto L_0x004d
            java.lang.Object r1 = r0.extraData     // Catch:{ all -> 0x0087 }
            boolean r1 = r1 instanceof com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0087 }
            if (r1 == 0) goto L_0x0080
            java.lang.Object r1 = r0.extraData     // Catch:{ all -> 0x0087 }
            com.alibaba.fastjson.JSONObject r1 = (com.alibaba.fastjson.JSONObject) r1     // Catch:{ all -> 0x0087 }
            if (r1 == 0) goto L_0x0080
            java.lang.Class<com.autonavi.minimap.route.sharebike.model.RideState> r2 = com.autonavi.minimap.route.sharebike.model.RideState.class
            java.lang.Object r1 = r1.toJavaObject(r2)     // Catch:{ Throwable -> 0x0080 }
            r0.extraData = r1     // Catch:{ Throwable -> 0x0080 }
            goto L_0x0080
        L_0x004d:
            int r1 = r0.dataSource     // Catch:{ all -> 0x0087 }
            r2 = 1
            if (r1 != r2) goto L_0x0067
            java.lang.Object r1 = r0.extraData     // Catch:{ all -> 0x0087 }
            boolean r1 = r1 instanceof com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0087 }
            if (r1 == 0) goto L_0x0080
            java.lang.Object r1 = r0.extraData     // Catch:{ all -> 0x0087 }
            com.alibaba.fastjson.JSONObject r1 = (com.alibaba.fastjson.JSONObject) r1     // Catch:{ all -> 0x0087 }
            if (r1 == 0) goto L_0x0080
            java.lang.Class<com.autonavi.minimap.route.sharebike.model.ScanQrcode> r2 = com.autonavi.minimap.route.sharebike.model.ScanQrcode.class
            java.lang.Object r1 = r1.toJavaObject(r2)     // Catch:{ Throwable -> 0x0080 }
            r0.extraData = r1     // Catch:{ Throwable -> 0x0080 }
            goto L_0x0080
        L_0x0067:
            int r1 = r0.dataSource     // Catch:{ all -> 0x0087 }
            r2 = 4
            if (r1 != r2) goto L_0x0080
            java.lang.Object r1 = r0.extraData     // Catch:{ all -> 0x0087 }
            boolean r1 = r1 instanceof com.alibaba.fastjson.JSONObject     // Catch:{ all -> 0x0087 }
            if (r1 == 0) goto L_0x0080
            java.lang.Object r1 = r0.extraData     // Catch:{ all -> 0x0087 }
            com.alibaba.fastjson.JSONObject r1 = (com.alibaba.fastjson.JSONObject) r1     // Catch:{ all -> 0x0087 }
            if (r1 == 0) goto L_0x0080
            java.lang.Class<com.autonavi.minimap.route.sharebike.model.EndBill> r2 = com.autonavi.minimap.route.sharebike.model.EndBill.class
            java.lang.Object r1 = r1.toJavaObject(r2)     // Catch:{ Throwable -> 0x0080 }
            r0.extraData = r1     // Catch:{ Throwable -> 0x0080 }
        L_0x0080:
            r3.a = r0     // Catch:{ all -> 0x0087 }
            monitor-exit(r3)
            return r0
        L_0x0084:
            r0 = 0
            monitor-exit(r3)
            return r0
        L_0x0087:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.egy.c():com.autonavi.minimap.route.sharebike.order.OrderInfo");
    }
}
