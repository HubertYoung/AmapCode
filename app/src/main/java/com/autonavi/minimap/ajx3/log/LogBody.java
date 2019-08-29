package com.autonavi.minimap.ajx3.log;

public class LogBody {
    /* access modifiers changed from: private */
    public String action;
    /* access modifiers changed from: private */
    public String ajxFileVersion;
    /* access modifiers changed from: private */
    public String androidExt;
    /* access modifiers changed from: private */
    public long contextId;
    /* access modifiers changed from: private */
    public String frontExt;
    /* access modifiers changed from: private */
    public int logLevel;
    /* access modifiers changed from: private */
    public int logType;
    /* access modifiers changed from: private */
    public String msg;
    /* access modifiers changed from: private */
    public String pagePath;
    /* access modifiers changed from: private */
    public String stack;
    /* access modifiers changed from: private */
    public String tag;
    /* access modifiers changed from: private */
    public long time;

    public static class Builder {
        private LogBody logBody = new LogBody();

        public Builder setContextId(long j) {
            this.logBody.contextId = j;
            return this;
        }

        public Builder setLogLevel(int i) {
            this.logBody.logLevel = i;
            return this;
        }

        public Builder setLogType(int i) {
            this.logBody.logType = i;
            return this;
        }

        public Builder setTime(long j) {
            this.logBody.time = j;
            return this;
        }

        public Builder setStateType(String str) {
            this.logBody.action = str;
            return this;
        }

        public Builder setTag(String str) {
            this.logBody.tag = str;
            return this;
        }

        public Builder setAjxFileVersion(String str) {
            this.logBody.ajxFileVersion = str;
            return this;
        }

        public Builder setPagePath(String str) {
            this.logBody.pagePath = str;
            return this;
        }

        public Builder setMsg(String str) {
            this.logBody.msg = str;
            return this;
        }

        public Builder setStack(String str) {
            this.logBody.stack = str;
            return this;
        }

        public Builder setFrontExt(String str) {
            this.logBody.frontExt = str;
            return this;
        }

        public Builder setAndroidExt(String str) {
            this.logBody.androidExt = str;
            return this;
        }

        public LogBody build() {
            return this.logBody;
        }
    }

    public long getContextId() {
        return this.contextId;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public int getLogType() {
        return this.logType;
    }

    public long getTime() {
        return this.time;
    }

    public String getAction() {
        return this.action;
    }

    public String getTag() {
        return this.tag;
    }

    public String getAjxFileVersion() {
        return this.ajxFileVersion;
    }

    public String getPagePath() {
        return this.pagePath;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getStack() {
        return this.stack;
    }

    public String getFrontExt() {
        return this.frontExt;
    }

    public String getAndroidExt() {
        return this.androidExt;
    }
}
