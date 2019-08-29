package com.alipay.android.phone.mobilecommon.multimedia.api.data;

import java.util.Arrays;

public class SecurityRequireException extends SecurityException {
    private final String[] permissions;

    public SecurityRequireException(String... permissions2) {
        if (permissions2 == null || permissions2.length <= 0) {
            this.permissions = null;
            return;
        }
        this.permissions = new String[permissions2.length];
        System.arraycopy(permissions2, 0, this.permissions, 0, permissions2.length);
    }

    public String[] getRequirePermissions() {
        return this.permissions;
    }

    public String toString() {
        return "[SecurityRequireException]: " + (this.permissions != null ? Arrays.toString(this.permissions) : "NONE.");
    }
}
