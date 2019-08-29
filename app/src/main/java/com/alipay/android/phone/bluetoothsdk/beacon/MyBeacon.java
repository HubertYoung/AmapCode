package com.alipay.android.phone.bluetoothsdk.beacon;

public class MyBeacon {
    public static final int PROXIMITY_FAR = 3;
    public static final int PROXIMITY_IMMEDIATE = 1;
    public static final int PROXIMITY_NEAR = 2;
    public static final int PROXIMITY_UNKNOWN = 0;
    public double accuracy;
    public int major;
    public int minor;
    public int proximity;
    public int rssi;
    public int txPower;
    public String uuid;

    public MyBeacon(String uuid2, int major2, int minor2, double accuracy2, int rssi2, int txPower2) {
        this.uuid = uuid2;
        this.major = major2;
        this.minor = minor2;
        this.accuracy = accuracy2;
        this.rssi = rssi2;
        this.txPower = txPower2;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MyBeacon)) {
            return false;
        }
        return this.uuid != null && this.uuid.equals(((MyBeacon) obj).uuid) && this.major == ((MyBeacon) obj).major && this.minor == ((MyBeacon) obj).minor;
    }
}
