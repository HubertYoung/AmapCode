package com.alipay.android.phone.bluetoothsdk.better.ble;

public class BleResult {
    public String[] error;
    public Object obj;
    public boolean success;
    public boolean syncReturn;

    public BleResult() {
    }

    public BleResult(boolean success2, boolean syncReturn2) {
        this.success = success2;
        this.syncReturn = syncReturn2;
    }

    public BleResult(boolean success2, boolean syncReturn2, String[] error2) {
        this.success = success2;
        this.syncReturn = syncReturn2;
        this.error = error2;
    }

    public String getErrorCode() {
        if (this.error == null || this.error.length <= 0) {
            return null;
        }
        return this.error[0];
    }

    public String getErrorMessage() {
        if (this.error == null || this.error.length <= 1) {
            return null;
        }
        return this.error[1];
    }
}
