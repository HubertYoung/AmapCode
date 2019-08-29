package com.autonavi.jni.eyrie.amap.tbt.bus.response;

public class TaxiComparatorResponse {
    public String code;
    public TaxiComparatorInfo data;
    public boolean result;

    public static class Simple {
        public int max_price;
        public int min_price;
    }

    public static class TaxiComparatorInfo {
        public String eta_distance;
        public String eta_time;
        public Simple simple;
    }
}
