package com.autonavi.io.monitor;

import com.autonavi.io.monitor.annotation.AccessMode;

public class AccessError extends Error {
    private String mMessage;
    private AccessMode mMode;
    private String mPath;

    public AccessError() {
    }

    public AccessError(String str, AccessMode accessMode, String str2) {
        this.mPath = str;
        this.mMode = accessMode;
        this.mMessage = str2;
    }

    public String getPath() {
        return this.mPath;
    }

    public void setPath(String str) {
        this.mPath = str;
    }

    public AccessMode getAccessMode() {
        return this.mMode;
    }

    public void setMode(AccessMode accessMode) {
        this.mMode = accessMode;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public void setMessage(String str) {
        this.mMessage = str;
    }

    public String toString() {
        String str;
        String name = getClass().getName();
        if (this.mMessage == null && this.mPath == null) {
            return name;
        }
        if (this.mPath == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(":\n");
            sb.append(this.mMessage);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(name);
        sb2.append(": Access Denied -> [");
        sb2.append(this.mMode);
        sb2.append("] ");
        sb2.append(this.mPath);
        if (this.mMessage != null) {
            StringBuilder sb3 = new StringBuilder("\n");
            sb3.append(this.mMessage);
            str = sb3.toString();
        } else {
            str = "";
        }
        sb2.append(str);
        return sb2.toString();
    }
}
