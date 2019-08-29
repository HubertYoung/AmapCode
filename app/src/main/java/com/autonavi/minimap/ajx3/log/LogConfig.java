package com.autonavi.minimap.ajx3.log;

public class LogConfig {
    /* access modifiers changed from: private */
    public String basejsVersion;
    /* access modifiers changed from: private */
    public String device;
    /* access modifiers changed from: private */
    public String dibv;
    /* access modifiers changed from: private */
    public String diu;
    /* access modifiers changed from: private */
    public String div;
    /* access modifiers changed from: private */
    public String id;
    /* access modifiers changed from: private */
    public String platform;

    public static class Builder {
        private LogConfig logConfig = new LogConfig();

        public Builder setId(String str) {
            this.logConfig.id = str;
            return this;
        }

        public Builder setBasejsVersion(String str) {
            this.logConfig.basejsVersion = str;
            return this;
        }

        public Builder setDiv(String str) {
            this.logConfig.div = str;
            return this;
        }

        public Builder setDibv(String str) {
            this.logConfig.dibv = str;
            return this;
        }

        public Builder setDevice(String str) {
            this.logConfig.device = str;
            return this;
        }

        public Builder setPlatform(String str) {
            this.logConfig.platform = str;
            return this;
        }

        public Builder setDiu(String str) {
            this.logConfig.diu = str;
            return this;
        }

        public LogConfig build() {
            return this.logConfig;
        }
    }

    private LogConfig() {
    }

    public String getId() {
        return this.id;
    }

    public String getBasejsVersion() {
        return this.basejsVersion;
    }

    public String getDiv() {
        return this.div;
    }

    public String getDibv() {
        return this.dibv;
    }

    public String getDevice() {
        return this.device;
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getDiu() {
        return this.diu;
    }
}
