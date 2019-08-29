package com.alipay.mobile.nebula.appcenter.apphandler;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5Log;

public class H5AppPrepareData implements Cloneable {
    private static final String OFF_ASYNC = "0";
    private static final String OFF_SYNCFORCE = "1";
    private static final String OFF_SYNCTRY = "2";
    public static final int PAGE_OPEN = 1;
    public static final int PAGE_UNOPEN = 0;
    public static final String PREPARE_DOWNLOAD_FAIL = "10009";
    public static final String PREPARE_FAIL = "10000";
    public static final String PREPARE_FALLBACK_FAIL = "10011";
    public static final String PREPARE_RPC_FAIL = "10004";
    public static final String PREPARE_SUCCESS = "1";
    public static final String PREPARE_TIMEOUT = "10010";
    public static final String PREPARE_UNZIP_FAIL = "10003";
    private static final String REQ_ASYNC = "0";
    private static final String REQ_SYNCFORCE = "2";
    private static final String REQ_SYNCTRY = "1";
    public static final String TAG = "H5AppPrepareData";
    private String appId;
    private long beginTime;
    private long downloadEndTime;
    private long downloadTime;
    private long endTime;
    private String errorDetail;
    private long getDBVersionTime;
    private long installEndTime;
    private long installTime;
    private String nbUrl;
    private String offlineMode;
    private int pageStatus;
    private long requestBeginTime;
    private long requestEndTime;
    private String requestMode;
    private String version;

    public H5AppPrepareData() {
        clear();
    }

    /* access modifiers changed from: protected */
    public H5AppPrepareData clone() {
        return (H5AppPrepareData) super.clone();
    }

    public void clear() {
        H5Log.d(TAG, "clear");
        this.downloadTime = 0;
        this.requestEndTime = 0;
        this.requestBeginTime = 0;
        this.beginTime = 0;
        this.pageStatus = 0;
        this.endTime = 0;
        this.installTime = 0;
        this.nbUrl = "";
        this.errorDetail = "";
        this.version = "";
        this.appId = "";
        this.offlineMode = "";
        this.requestMode = "";
        this.getDBVersionTime = 0;
    }

    public long getGetDBVersionTime() {
        return this.getDBVersionTime;
    }

    public void setGetDBVersionTime(long getDBVersionTime2) {
        this.getDBVersionTime = getDBVersionTime2;
    }

    public long getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(long beginTime2) {
        this.beginTime = beginTime2;
    }

    public long getRequestBeginTime() {
        return this.requestBeginTime;
    }

    public void setRequestBeginTime(long requestBeginTime2) {
        this.requestBeginTime = requestBeginTime2;
    }

    public long getRequestEndTime() {
        return this.requestEndTime;
    }

    public void setRequestEndTime(long requestEndTime2) {
        this.requestEndTime = requestEndTime2;
    }

    public long getDownloadTime() {
        return this.downloadTime;
    }

    public void setDownloadTime(long downloadTime2) {
        this.downloadTime = downloadTime2;
    }

    public long getInstallTime() {
        return this.installTime;
    }

    public void setInstallTime(long installTime2) {
        this.installTime = installTime2;
    }

    public long getDownloadEndTime() {
        return this.downloadEndTime;
    }

    public void setDownloadEndTime(long downloadEndTime2) {
        this.downloadEndTime = downloadEndTime2;
    }

    public long getInstallEndTime() {
        return this.installEndTime;
    }

    public void setInstallEndTime(long installEndTime2) {
        this.installEndTime = installEndTime2;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime2) {
        this.endTime = endTime2;
    }

    public String getRequestMode() {
        return this.requestMode;
    }

    public void setRequestMode(String requestMode2) {
        if (TextUtils.equals(requestMode2, "synctry")) {
            this.requestMode = "1";
        } else if (TextUtils.equals(requestMode2, "syncforce")) {
            this.requestMode = "2";
        } else {
            this.requestMode = "0";
        }
    }

    public String getOfflineMode() {
        return this.offlineMode;
    }

    public void setOfflineMode(String syncOffline, String nbOffMode) {
        if (!TextUtils.equals(syncOffline, "sync")) {
            this.offlineMode = "0";
        } else if (TextUtils.equals(nbOffMode, "try")) {
            this.offlineMode = "2";
        } else {
            this.offlineMode = "1";
        }
    }

    public int getPageStatus() {
        return this.pageStatus;
    }

    public void setPageStatus(int pageStatus2) {
        this.pageStatus = pageStatus2;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }

    public void setErrorDetail(String errorDetail2) {
        this.errorDetail = errorDetail2;
    }

    public String getNbUrl() {
        return this.nbUrl;
    }

    public void setNbUrl(String nbUrl2) {
        if (TextUtils.isEmpty(nbUrl2)) {
            this.nbUrl = "";
        } else {
            this.nbUrl = nbUrl2;
        }
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId2) {
        this.appId = appId2;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }

    public String toString() {
        return "H5AppPrepareData{beginTime=" + this.beginTime + ", requestBeginTime=" + this.requestBeginTime + ", requestEndTime=" + this.requestEndTime + ", downloadTime=" + this.downloadTime + ", installTime=" + this.installTime + ", endTime=" + this.endTime + ", requestMode=" + this.requestMode + ", offlineMode=" + this.offlineMode + ", pageStatus=" + this.pageStatus + ", errorDetail=" + this.errorDetail + ", nbUrl='" + this.nbUrl + '\'' + '}';
    }

    public void uploadPrepareLog(String step, String errorCode) {
        setEndTime(System.currentTimeMillis());
        try {
            H5AppPrepareData logData = clone();
            H5LogUtil.logNebulaTech(H5LogData.seedId("H5_APP_PREPARE").param1().add("monitor", null).param2().add("getDBVersionTime", Long.valueOf(this.getDBVersionTime)).param3().add("step", step).add("appId", logData.getAppId()).add("version", logData.getVersion()).add("proc", getProcessTimeStr(logData)).add("req", logData.getRequestMode()).add(CameraParams.FLASH_MODE_OFF, logData.getOfflineMode()).add("page", Integer.valueOf(logData.getPageStatus())).add("err", logData.getErrorDetail()).add("errc", errorCode).add(H5Param.NB_URL, logData.getNbUrl()));
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
        }
    }

    private String getProcessTimeStr(H5AppPrepareData logData) {
        StringBuilder timeStr = new StringBuilder();
        timeStr.append("all_").append(logData.getEndTime() - logData.getBeginTime());
        if (logData.getRequestBeginTime() > 0 && logData.getRequestEndTime() > 0) {
            timeStr.append("|req_").append(logData.getRequestEndTime() - logData.getRequestBeginTime());
        }
        if (logData.getDownloadTime() > 0 && logData.getDownloadEndTime() > 0) {
            timeStr.append("|down_").append(logData.getDownloadEndTime() - logData.getDownloadTime());
        }
        if (logData.getInstallTime() > 0 && logData.getInstallEndTime() > 0) {
            timeStr.append("|zip_").append(logData.getInstallEndTime() - logData.getInstallTime());
        }
        return timeStr.toString();
    }
}
