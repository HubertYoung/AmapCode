package com.alipay.mobile.nebula.appcenter.api;

import android.support.annotation.Nullable;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import java.util.List;
import java.util.Map;

public class H5UpdateAppParam {
    /* access modifiers changed from: private */
    @Nullable
    public Map<String, String> appMap = null;
    /* access modifiers changed from: private */
    public AppReq appReq;
    /* access modifiers changed from: private */
    public boolean downLoadAmr = false;
    /* access modifiers changed from: private */
    public boolean downloadRandom = false;
    /* access modifiers changed from: private */
    public boolean forceRpc = false;
    /* access modifiers changed from: private */
    public boolean fromWholeNetwork = false;
    /* access modifiers changed from: private */
    public List<String> resPackageList;
    /* access modifiers changed from: private */
    public long startTime;
    /* access modifiers changed from: private */
    @Nullable
    public H5UpdateAppCallback updateCallback = null;

    public static class Builder {
        private H5UpdateAppParam target = new H5UpdateAppParam();

        public Builder setAppMap(Map<String, String> appMap) {
            this.target.appMap = appMap;
            return this;
        }

        public Builder setUpdateCallback(H5UpdateAppCallback installProcess) {
            this.target.updateCallback = installProcess;
            return this;
        }

        public Builder setDownLoadAmr(boolean downLoadAmr) {
            this.target.downLoadAmr = downLoadAmr;
            return this;
        }

        public Builder setForceRpc(boolean forceRpc) {
            this.target.forceRpc = forceRpc;
            return this;
        }

        public Builder setAppReq(AppReq appReq) {
            this.target.appReq = appReq;
            return this;
        }

        public Builder setDownloadRandom(boolean downloadRandom) {
            this.target.downloadRandom = downloadRandom;
            return this;
        }

        public Builder setResPackageList(List<String> resPackageList) {
            this.target.resPackageList = resPackageList;
            return this;
        }

        public Builder setStartTime(long startTime) {
            this.target.startTime = startTime;
            return this;
        }

        public Builder setFromWholeNetwork(boolean fromWholeNetwork) {
            this.target.fromWholeNetwork = fromWholeNetwork;
            return this;
        }

        public H5UpdateAppParam build() {
            return this.target;
        }
    }

    @Nullable
    public Map<String, String> getAppMap() {
        return this.appMap;
    }

    @Nullable
    public H5UpdateAppCallback getUpdateCallback() {
        return this.updateCallback;
    }

    public boolean isDownLoadAmr() {
        return this.downLoadAmr;
    }

    public boolean isForceRpc() {
        return this.forceRpc;
    }

    @Nullable
    public AppReq getAppReq() {
        return this.appReq;
    }

    public boolean isDownloadRandom() {
        return this.downloadRandom;
    }

    @Nullable
    public List<String> getResPackageList() {
        return this.resPackageList;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public boolean isFromWholeNetwork() {
        return this.fromWholeNetwork;
    }

    public String toString() {
        return "H5UpdateAppParam{appMap=" + this.appMap + ", updateCallback=" + this.updateCallback + ", fromWholeNetwork=" + this.fromWholeNetwork + ", downLoadAmr=" + this.downLoadAmr + ", forceRpc=" + this.forceRpc + ", downloadRandom=" + this.downloadRandom + ", startTime=" + this.startTime + ", resPackageList=" + this.resPackageList + ", appReq=" + this.appReq + '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
