package com.autonavi.minimap.route.sharebike.order;

public enum OrderState {
    IDLE(0),
    SCAN(1),
    UNLOCK(2),
    RIDE(3),
    ORDER_CHANGE(4),
    ORDER_ERROR_REPORT(5),
    ORDER_PENDING(6);
    
    int a;

    private OrderState(int i) {
        this.a = -1;
        this.a = i;
    }

    public static OrderState getOrderState(int i) {
        if (i < 0) {
            return null;
        }
        OrderState[] values = values();
        for (OrderState orderState : values) {
            if (orderState != null && i == orderState.a) {
                return orderState;
            }
        }
        return null;
    }

    public static int getOrderStatusCode(OrderState orderState) {
        if (orderState != null) {
            return orderState.a;
        }
        return -1;
    }

    public final int getCode() {
        return this.a;
    }
}
