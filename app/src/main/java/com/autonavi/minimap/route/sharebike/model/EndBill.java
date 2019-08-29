package com.autonavi.minimap.route.sharebike.model;

public class EndBill extends BaseNetResult {
    public static final int END_ORDER_FAILED = 163;
    public static final int ORDER_PAYMENT_FAILED = 164;
    public static final int UNKNOWN_END_ORDER_FAILED = 162;
    public double distance;
    public int price;
    public int time;
    public String toast = "";
}
