package com.alipay.android.phone.inside.log.util;

public enum NetConnectionType {
    WIFI(0, "WIFI"),
    NETWORK_TYPE_1(1, "unicom2G"),
    NETWORK_TYPE_2(2, "mobile2G"),
    NETWORK_TYPE_4(4, "telecom2G"),
    NETWORK_TYPE_5(5, "telecom3G"),
    NETWORK_TYPE_6(6, "telecom3G"),
    NETWORK_TYPE_12(12, "telecom3G"),
    NETWORK_TYPE_8(8, "unicom3G"),
    NETWORK_TYPE_3(3, "unicom3G"),
    NETWORK_TYPE_13(13, "LTE"),
    NETWORK_TYPE_11(11, "IDEN"),
    NETWORK_TYPE_9(9, "HSUPA"),
    NETWORK_TYPE_10(10, "HSPA"),
    NETWORK_TYPE_15(15, "HSPAP"),
    NETWORK_TYPE_16(16, "MOBILE_UNKNOWN");
    
    private int code;
    private String name;

    private NetConnectionType(int i, String str) {
        this.code = i;
        this.name = str;
    }

    public final int getCode() {
        return this.code;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public static NetConnectionType getTypeByCode(int i) {
        NetConnectionType[] values;
        for (NetConnectionType netConnectionType : values()) {
            if (netConnectionType.getCode() == i) {
                return netConnectionType;
            }
        }
        return NETWORK_TYPE_16;
    }
}
