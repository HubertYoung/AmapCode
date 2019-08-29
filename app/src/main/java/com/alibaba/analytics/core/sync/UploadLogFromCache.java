package com.alibaba.analytics.core.sync;

import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.config.UTRealtimeConfBiz;
import com.alibaba.analytics.core.model.Log;
import com.alibaba.analytics.core.network.NetworkUtil;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEvent;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather;
import com.alibaba.analytics.core.sync.UploadLog.NetworkStatus;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.fastjson.JSON;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadLogFromCache extends UploadLog {
    private static final int MAX_NUM = 300;
    private static UploadLogFromCache s_instance = new UploadLogFromCache();
    private volatile boolean bRunning = false;
    private boolean hasSuccess = false;
    private List<Log> mCacheLogs = new ArrayList();
    public final SelfMonitorEventDispather mMonitor = new SelfMonitorEventDispather();
    private int mTNetFailTimes = 0;
    private int mUploadByteSize = 0;
    private List<Log> mUploadingLogs = new ArrayList();

    public static UploadLogFromCache getInstance() {
        return s_instance;
    }

    public void addLog(Log log) {
        synchronized (this) {
            if (this.mCacheLogs.size() >= 300) {
                for (int i = 99; i >= 0; i--) {
                    this.mCacheLogs.remove(i);
                }
            }
            this.mCacheLogs.add(log);
        }
        UploadQueueMgr.getInstance().add(UploadQueueMgr.MSGTYPE_REALTIME);
    }

    private void removeUploadingLogs() {
        synchronized (this) {
            this.mCacheLogs.removeAll(this.mUploadingLogs);
            this.mUploadingLogs.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public void upload() {
        Logger.d();
        try {
            if (!UTRealtimeConfBiz.getInstance().isRealtimeClosed()) {
                uploadEventLog();
            }
        } catch (Throwable th) {
            Logger.e(null, th, new Object[0]);
        }
    }

    private void uploadEventLog() {
        Logger.d();
        if (NetworkUtil.isConnectInternet(Variables.getInstance().getContext())) {
            if (NetworkStatus.ALL != this.mAllowedNetworkStatus && this.mAllowedNetworkStatus != getNetworkStatus()) {
                Logger.w((String) "network not match,return", "current networkstatus", getNetworkStatus(), "mAllowedNetworkStatus", this.mAllowedNetworkStatus);
            } else if (!this.bRunning) {
                this.bRunning = true;
                int i = 0;
                while (true) {
                    try {
                        if (i < this.mMaxUploadTimes) {
                            if (this.mCacheLogs.size() != 0) {
                                if (uploadByTnet()) {
                                    break;
                                }
                                i++;
                            } else {
                                this.bRunning = false;
                                break;
                            }
                        } else {
                            break;
                        }
                    } catch (Throwable th) {
                        Logger.e(null, th, new Object[0]);
                        return;
                    } finally {
                        this.bRunning = false;
                    }
                }
            }
        }
    }

    private boolean uploadByTnet() throws Exception {
        byte[] bArr;
        Logger.d();
        Map<String, String> buildEventRequestMap = buildEventRequestMap();
        if (buildEventRequestMap == null || buildEventRequestMap.size() == 0) {
            this.bRunning = false;
            return true;
        }
        try {
            bArr = BizRequest.getPackRequestByRealtime(buildEventRequestMap);
        } catch (Exception e) {
            Logger.e(null, e, new Object[0]);
            bArr = null;
        }
        if (bArr == null) {
            Logger.d((String) "", "packRequest is null");
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        BizResponse sendRequest = TnetUtil.sendRequest(bArr);
        boolean isSuccess = sendRequest.isSuccess();
        if (isSuccess) {
            Variables.getInstance().turnOnSelfMonitor();
            this.hasSuccess = true;
            this.mTNetFailTimes = 0;
            removeUploadingLogs();
            try {
                parserConfig(sendRequest.data);
            } catch (Exception e2) {
                Logger.d((String) null, e2);
            }
        } else {
            this.mTNetFailTimes++;
            if (Variables.getInstance().isHttpService()) {
                return true;
            }
            if (Variables.getInstance().isSelfMonitorTurnOn() && this.hasSuccess && this.mTNetFailTimes <= 10) {
                HashMap hashMap = new HashMap();
                hashMap.put("rt", String.valueOf(sendRequest.rt));
                hashMap.put("pSize", String.valueOf(this.mUploadByteSize));
                hashMap.put("errCode", String.valueOf(sendRequest.errCode));
                hashMap.put("type", "2");
                this.mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.UPLOAD_FAILED, JSON.toJSONString(hashMap), Double.valueOf(1.0d)));
            }
        }
        if (Logger.isDebug()) {
            Logger.d((String) "", "isSendSuccess", Boolean.valueOf(isSuccess), "cost time", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
        }
        try {
            Thread.sleep(100);
        } catch (Throwable th) {
            Logger.w((String) null, "thread sleep interrupted", th);
        }
        return false;
    }

    private Map<String, String> buildEventRequestMap() {
        int i;
        if (this.mCacheLogs.size() == 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            this.mUploadingLogs.clear();
            int effectiveTime = UTRealtimeConfBiz.getInstance().getEffectiveTime() * 1000;
            long currentTimeMillis = System.currentTimeMillis();
            i = 0;
            for (int i2 = 0; i2 < this.mCacheLogs.size(); i2++) {
                Log log = this.mCacheLogs.get(i2);
                if (currentTimeMillis - Long.parseLong(log.time) > ((long) effectiveTime)) {
                    arrayList.add(log);
                } else {
                    this.mUploadingLogs.add(log);
                    StringBuilder sb = new StringBuilder();
                    sb.append(log.getTopicId());
                    StringBuilder sb2 = (StringBuilder) hashMap.get(sb.toString());
                    if (sb2 == null) {
                        sb2 = new StringBuilder();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(log.getTopicId());
                        hashMap.put(sb3.toString(), sb2);
                    } else {
                        sb2.append(1);
                        i++;
                    }
                    String content = this.mCacheLogs.get(i2).getContent();
                    sb2.append(content);
                    i += content.length();
                }
            }
            if (!arrayList.isEmpty()) {
                if (Variables.getInstance().isSelfMonitorTurnOn()) {
                    this.mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.LOGS_TIMEOUT, null, Double.valueOf((double) arrayList.size())));
                }
                this.mCacheLogs.removeAll(arrayList);
            }
        }
        HashMap hashMap2 = new HashMap();
        this.mUploadByteSize = i;
        for (String str : hashMap.keySet()) {
            hashMap2.put(str, ((StringBuilder) hashMap.get(str)).toString());
        }
        if (Logger.isDebug()) {
            Logger.d((String) "", "mUploadByteSize", Integer.valueOf(this.mUploadByteSize), NewHtcHomeBadger.COUNT, Integer.valueOf(this.mUploadingLogs.size()), "timeoutLogs count", Integer.valueOf(arrayList.size()));
        }
        return hashMap2;
    }
}
