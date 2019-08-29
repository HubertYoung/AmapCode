package com.alipay.android.phone.mobilecommon.multimedia.api.data;

import java.util.Arrays;

public class PermissionResult {
    public boolean granted;
    public int[] grantedResults;
    public String[] requirePermissions;
    public boolean shouldRequestPermissionRationale;
    public boolean showedSystemDialog;

    public String toString() {
        return "PermissionResult{showedSystemDialog=" + this.showedSystemDialog + ", shouldRequestPermissionRationale=" + this.shouldRequestPermissionRationale + ", granted=" + this.granted + ", requirePermissions=" + Arrays.toString(this.requirePermissions) + ", grantedResults=" + Arrays.toString(this.grantedResults) + '}';
    }
}
