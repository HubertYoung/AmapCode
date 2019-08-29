package com.alipay.android.phone.bluetoothsdk.beacon;

public class BeaconResult {
    public String[] error;
    public Object obj;
    public boolean success;

    public BeaconResult(boolean success2) {
        this.success = success2;
    }

    public BeaconResult(boolean success2, String[] error2) {
        this.success = success2;
        this.error = error2;
    }

    public String getErrorCode() {
        return (this.error == null || this.error.length <= 1) ? "" : this.error[0];
    }

    public String getErrorMessage() {
        return (this.error == null || this.error.length <= 1) ? "" : this.error[1];
    }
}
