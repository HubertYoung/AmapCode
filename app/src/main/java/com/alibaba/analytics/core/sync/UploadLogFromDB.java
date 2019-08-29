package com.alibaba.analytics.core.sync;

import android.os.SystemClock;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.SystemConfigMgr;
import com.alibaba.analytics.core.logbuilder.LogAssemble;
import com.alibaba.analytics.core.model.Log;
import com.alibaba.analytics.core.network.NetworkUtil;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather;
import com.alibaba.analytics.core.store.LogStoreMgr;
import com.alibaba.analytics.core.sync.UploadLog.NetworkStatus;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.MutiProcessLock;
import com.alibaba.fastjson.JSON;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadLogFromDB extends UploadLog {
    private static final int Default_WIN_SIZE = 4;
    private static final int MAX_LOG_COUNT = 350;
    private static final String TAG = "UploadLogFromDB";
    private static final int TOTAL_MAX_POST_SIZE = 5242880;
    private static UploadLogFromDB s_instance = new UploadLogFromDB();
    private volatile boolean bRunning = false;
    private boolean hasSuccess = false;
    private float mAveragePackageSize = 200.0f;
    private int mFactor = 0;
    public final SelfMonitorEventDispather mMonitor = new SelfMonitorEventDispather();
    private int mTNetFailTimes = 0;
    private int mUploadByteSize = 0;
    private long mUploadCount = 0;
    private int mUploadIndex = this.mMaxUploadTimes;
    private int mWinSize = -1;

    public static UploadLogFromDB getInstance() {
        return s_instance;
    }

    public boolean hasSuccess() {
        return this.hasSuccess;
    }

    public void upload() {
        Logger.d();
        try {
            if (!Variables.getInstance().isAllServiceClosed()) {
                uploadEventLog();
            } else {
                Logger.w((String) null, "isAllServiceClosed");
            }
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
        try {
            if (this.mIUploadExcuted != null) {
                this.mIUploadExcuted.onUploadExcuted(this.mUploadCount);
            }
        } catch (Throwable th2) {
            Logger.e(null, th2, new Object[0]);
        }
    }

    private void uploadEventLog() {
        Logger.d();
        if (NetworkUtil.isConnectInternet(Variables.getInstance().getContext())) {
            if (NetworkStatus.ALL != this.mAllowedNetworkStatus && this.mAllowedNetworkStatus != getNetworkStatus()) {
                Logger.w((String) "network not match,return", "current networkstatus", getNetworkStatus(), "mAllowedNetworkStatus", this.mAllowedNetworkStatus);
            } else if (!this.bRunning) {
                this.bRunning = true;
                try {
                    this.mUploadCount = 0;
                    if (!MutiProcessLock.lock(Variables.getInstance().getContext())) {
                        Logger.d((String) TAG, "Other Process is Uploading, break");
                        this.bRunning = false;
                        MutiProcessLock.release();
                        return;
                    }
                    List<Log> list = LogStoreMgr.getInstance().get(getLogCount());
                    if (list != null) {
                        if (list.size() != 0) {
                            if (uploadLogs(list)) {
                                this.mUploadIndex = this.mMaxUploadTimes;
                            } else {
                                this.mUploadIndex--;
                                if (this.mUploadIndex > 0) {
                                    UploadQueueMgr.getInstance().add("i");
                                } else {
                                    this.mUploadIndex = this.mMaxUploadTimes;
                                }
                            }
                            this.bRunning = false;
                            MutiProcessLock.release();
                            return;
                        }
                    }
                    Logger.d((String) "", "logs is null");
                    this.bRunning = false;
                    this.bRunning = false;
                    MutiProcessLock.release();
                } catch (Throwable th) {
                    this.bRunning = false;
                    MutiProcessLock.release();
                    throw th;
                }
            }
        }
    }

    private boolean uploadLogs(List<Log> list) throws Exception {
        byte[] bArr;
        BizResponse bizResponse;
        Logger.d();
        Map<String, String> buildEventRequestMap = buildEventRequestMap(list);
        if (buildEventRequestMap == null || buildEventRequestMap.size() == 0) {
            Logger.d((String) "", "postDataMap is null");
            this.bRunning = false;
            return true;
        }
        try {
            bArr = BizRequest.getPackRequest(buildEventRequestMap);
        } catch (Exception e) {
            Logger.e((String) null, e.toString());
            bArr = null;
        }
        if (bArr == null) {
            reduceWindowSize();
            return false;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (Variables.getInstance().isHttpService()) {
            bizResponse = UrlWrapper.sendRequest(bArr);
        } else {
            bizResponse = TnetUtil.sendRequest(bArr);
        }
        boolean isSuccess = bizResponse.isSuccess();
        long elapsedRealtime2 = SystemClock.elapsedRealtime();
        long j = elapsedRealtime2 - elapsedRealtime;
        calPackPackageWindowSize(Boolean.valueOf(isSuccess), j);
        if (isSuccess) {
            Variables.getInstance().turnOnSelfMonitor();
            this.hasSuccess = true;
            this.mTNetFailTimes = 0;
            this.mUploadCount += (long) LogStoreMgr.getInstance().delete(list);
            this.mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.UPLOAD_TRAFFIC, null, Double.valueOf((double) this.mUploadByteSize)));
            try {
                parserConfig(bizResponse.data);
            } catch (Exception unused) {
            }
        } else {
            this.mTNetFailTimes++;
            if (this.mTNetFailTimes > 10) {
                Variables.getInstance().setHttpService(true);
                Logger.d((String) "", "setHttpService");
                return true;
            } else if (Variables.getInstance().isSelfMonitorTurnOn()) {
                if (!this.hasSuccess || this.mTNetFailTimes > 10) {
                    Variables.getInstance().turnOffSelfMonitor();
                } else {
                    HashMap hashMap = new HashMap();
                    hashMap.put("rt", String.valueOf(bizResponse.rt));
                    hashMap.put("pSize", String.valueOf(this.mUploadByteSize));
                    hashMap.put("errCode", String.valueOf(bizResponse.errCode));
                    hashMap.put("type", "1");
                    this.mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.UPLOAD_FAILED, JSON.toJSONString(hashMap), Double.valueOf(1.0d)));
                }
            }
        }
        Logger.i((String) "", "isSendSuccess", Boolean.valueOf(isSuccess), "upload log count", Integer.valueOf(list.size()), "upload consume", Long.valueOf(j), "delete consume", Long.valueOf(SystemClock.elapsedRealtime() - elapsedRealtime2));
        try {
            Thread.sleep(100);
        } catch (Throwable th) {
            Logger.w(null, th, new Object[0]);
        }
        return false;
    }

    public Map<String, String> buildEventRequestMap(List<Log> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        List list2 = null;
        ArrayList arrayList = null;
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Log log = list.get(i2);
            if (i > 5242880) {
                list2 = addToDelayList(list2, log);
                Logger.d((String) "log delay to upload because totalUploadSize Exceed", ReportManager.LOG_PATH, log, "totalUploadSize", Integer.valueOf(i));
            } else if (SystemConfigMgr.getInstance().checkDelayLog(LogAssemble.disassemble(log.getContent()))) {
                list2 = addToDelayList(list2, log);
                if (list.get(i2).priority.compareToIgnoreCase("3") >= 0) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(list.get(i2));
                }
                Logger.d((String) "log delay to upload because delay config", ReportManager.LOG_PATH, log);
            } else {
                StringBuilder sb = (StringBuilder) hashMap.get(log.eventId);
                if (sb == null) {
                    sb = new StringBuilder();
                    hashMap.put(log.eventId, sb);
                } else {
                    sb.append(1);
                    i++;
                }
                String content = list.get(i2).getContent();
                sb.append(content);
                i += content.length();
            }
        }
        if (list2 != null) {
            list.removeAll(list2);
        }
        if (arrayList != null) {
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                ((Log) arrayList.get(i3)).priority = "2";
            }
            LogStoreMgr.getInstance().updateLogPriority(arrayList);
        }
        HashMap hashMap2 = new HashMap();
        this.mUploadByteSize = i;
        for (String str : hashMap.keySet()) {
            hashMap2.put(str, ((StringBuilder) hashMap.get(str)).toString());
        }
        if (list.size() > 0) {
            this.mAveragePackageSize = ((float) this.mUploadByteSize) / ((float) list.size());
        }
        Logger.d((String) TAG, "averagePackageSize", Float.valueOf(this.mAveragePackageSize), "mUploadByteSize", Integer.valueOf(this.mUploadByteSize), NewHtcHomeBadger.COUNT, Integer.valueOf(list.size()));
        return hashMap2;
    }

    private List<Log> addToDelayList(List<Log> list, Log log) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(log);
        return list;
    }

    private void reduceWindowSize() {
        this.mWinSize /= 2;
        if (this.mWinSize <= 0) {
            this.mWinSize = 1;
            this.mFactor = 0;
        } else if (this.mWinSize > 350) {
            this.mWinSize = 350;
        }
        Logger.d((String) TAG, null, "winsize", Integer.valueOf(this.mWinSize));
    }

    private int calPackPackageWindowSize(Boolean bool, long j) {
        if (j < 0) {
            return this.mWinSize;
        }
        float f = ((float) this.mUploadByteSize) / ((float) j);
        if (!bool.booleanValue()) {
            this.mWinSize /= 2;
            this.mFactor++;
        } else if (j > 45000) {
            return this.mWinSize;
        } else {
            this.mWinSize = (int) ((((double) (f * 45000.0f)) / ((double) this.mAveragePackageSize)) - ((double) this.mFactor));
        }
        if (this.mWinSize <= 0) {
            this.mWinSize = 1;
            this.mFactor = 0;
        } else if (this.mWinSize > 350) {
            this.mWinSize = 350;
        }
        Logger.d((String) TAG, "winsize", Integer.valueOf(this.mWinSize));
        return this.mWinSize;
    }

    private int getLogCount() {
        if (this.mWinSize == -1) {
            String networkType = NetworkUtil.getNetworkType();
            if ("Wi-Fi".equalsIgnoreCase(networkType)) {
                this.mWinSize = 20;
            } else if ("4G".equalsIgnoreCase(networkType)) {
                this.mWinSize = 16;
            } else if ("3G".equalsIgnoreCase(networkType)) {
                this.mWinSize = 12;
            } else {
                this.mWinSize = 8;
            }
        }
        return this.mWinSize;
    }
}
