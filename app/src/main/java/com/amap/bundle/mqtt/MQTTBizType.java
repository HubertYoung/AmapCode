package com.amap.bundle.mqtt;

public enum MQTTBizType {
    UNKNOWN(0),
    AIMPOI(1),
    PUSH(2),
    PARK(3),
    TEAM(4),
    RESERVE_CAR(5),
    CAR_SENSOR(6);
    
    private int value;

    private MQTTBizType(int i) {
        this.value = 0;
        this.value = i;
    }

    public final int value() {
        return this.value;
    }

    public static MQTTBizType valueOf(int i) {
        MQTTBizType mQTTBizType = UNKNOWN;
        switch (i) {
            case 0:
                return UNKNOWN;
            case 1:
                return AIMPOI;
            case 2:
                return PUSH;
            case 3:
                return PARK;
            case 4:
                return TEAM;
            case 5:
                return RESERVE_CAR;
            case 6:
                return CAR_SENSOR;
            default:
                return mQTTBizType;
        }
    }
}
