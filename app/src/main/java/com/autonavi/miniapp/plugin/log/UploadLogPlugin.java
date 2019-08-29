package com.autonavi.miniapp.plugin.log;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.autonavi.miniapp.plugin.BasePlugin;
import java.util.ArrayList;
import java.util.List;

public class UploadLogPlugin extends BasePlugin {
    private static final String ACTION_UPLOADLOG = "uploadLog";
    private static final String TAG = "UploadLogPlugin";
    private List<String> mActions = new ArrayList();

    public UploadLogPlugin() {
        this.mActions.add(ACTION_UPLOADLOG);
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        h5EventFilter.addAction(ACTION_UPLOADLOG);
        super.onPrepare(h5EventFilter);
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        String action = h5Event.getAction();
        JSONObject param = h5Event.getParam();
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("handleEvent, action: ");
        sb.append(action);
        sb.append(", params: ");
        sb.append(param);
        traceLogger.info(TAG, sb.toString());
        if (!ACTION_UPLOADLOG.equals(action)) {
            return super.handleEvent(h5Event, h5BridgeContext);
        }
        handleAction_uploadLog(h5Event, h5BridgeContext);
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0121  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0131  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleAction_uploadLog(com.alipay.mobile.h5container.api.H5Event r19, com.alipay.mobile.h5container.api.H5BridgeContext r20) {
        /*
            r18 = this;
            com.alibaba.fastjson.JSONObject r1 = r19.getParam()
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.String r2 = "bizType"
            java.lang.String r2 = r1.getString(r2)
            java.lang.String r3 = "taskType"
            java.lang.String r3 = r1.getString(r3)
            java.lang.String r4 = "networkCondition"
            java.lang.String r4 = r1.getString(r4)
            java.lang.String r5 = "startDate"
            java.lang.String r5 = r1.getString(r5)
            java.lang.String r6 = "endDate"
            java.lang.String r6 = r1.getString(r6)
            java.lang.String r7 = "account"
            java.lang.String r7 = r1.getString(r7)
            java.lang.String r8 = "zippedLenLimit"
            long r8 = r1.getLongValue(r8)
            boolean r1 = android.text.TextUtils.isEmpty(r5)
            if (r1 != 0) goto L_0x0171
            boolean r1 = android.text.TextUtils.isEmpty(r6)
            if (r1 == 0) goto L_0x0042
            goto L_0x0171
        L_0x0042:
            java.lang.String r1 = "yyyy-MM-dd"
            int r10 = r5.length()
            int r11 = r1.length()
            r12 = 0
            if (r10 != r11) goto L_0x0078
            java.text.SimpleDateFormat r10 = new java.text.SimpleDateFormat
            r10.<init>(r1)
            java.util.Date r1 = r10.parse(r5)     // Catch:{ Throwable -> 0x006b }
            long r14 = r1.getTime()     // Catch:{ Throwable -> 0x006b }
            java.util.Date r1 = r10.parse(r6)     // Catch:{ Throwable -> 0x0068 }
            long r10 = r1.getTime()     // Catch:{ Throwable -> 0x0068 }
            r12 = r10
            goto L_0x0079
        L_0x0068:
            r0 = move-exception
            r1 = r0
            goto L_0x006e
        L_0x006b:
            r0 = move-exception
            r1 = r0
            r14 = r12
        L_0x006e:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r10 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r11 = "UploadLogPlugin"
            r10.error(r11, r1)
            goto L_0x0079
        L_0x0078:
            r14 = r12
        L_0x0079:
            java.lang.String r1 = "yyyy-MM-dd-HH"
            int r10 = r5.length()
            int r11 = r1.length()
            if (r10 != r11) goto L_0x00af
            java.text.SimpleDateFormat r10 = new java.text.SimpleDateFormat
            r10.<init>(r1)
            java.util.Date r1 = r10.parse(r5)     // Catch:{ Throwable -> 0x00a4 }
            long r16 = r1.getTime()     // Catch:{ Throwable -> 0x00a4 }
            java.util.Date r1 = r10.parse(r6)     // Catch:{ Throwable -> 0x009f }
            long r10 = r1.getTime()     // Catch:{ Throwable -> 0x009f }
            r12 = r10
            r14 = r16
            goto L_0x00af
        L_0x009f:
            r0 = move-exception
            r1 = r0
            r14 = r16
            goto L_0x00a6
        L_0x00a4:
            r0 = move-exception
            r1 = r0
        L_0x00a6:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r10 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r11 = "UploadLogPlugin"
            r10.error(r11, r1)
        L_0x00af:
            java.lang.String r1 = "yyyy-MM-dd HH:mm:ss"
            int r10 = r5.length()
            int r11 = r1.length()
            if (r10 != r11) goto L_0x00e5
            java.text.SimpleDateFormat r10 = new java.text.SimpleDateFormat
            r10.<init>(r1)
            java.util.Date r1 = r10.parse(r5)     // Catch:{ Throwable -> 0x00da }
            long r16 = r1.getTime()     // Catch:{ Throwable -> 0x00da }
            java.util.Date r1 = r10.parse(r6)     // Catch:{ Throwable -> 0x00d5 }
            long r10 = r1.getTime()     // Catch:{ Throwable -> 0x00d5 }
            r12 = r10
            r14 = r16
            goto L_0x00e5
        L_0x00d5:
            r0 = move-exception
            r1 = r0
            r14 = r16
            goto L_0x00dc
        L_0x00da:
            r0 = move-exception
            r1 = r0
        L_0x00dc:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r10 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r11 = "UploadLogPlugin"
            r10.error(r11, r1)
        L_0x00e5:
            java.lang.String r1 = "yyyy-MM-dd HH:mm:ss:SSS"
            int r10 = r5.length()
            int r11 = r1.length()
            if (r10 != r11) goto L_0x011b
            java.text.SimpleDateFormat r10 = new java.text.SimpleDateFormat
            r10.<init>(r1)
            java.util.Date r1 = r10.parse(r5)     // Catch:{ Throwable -> 0x0110 }
            long r16 = r1.getTime()     // Catch:{ Throwable -> 0x0110 }
            java.util.Date r1 = r10.parse(r6)     // Catch:{ Throwable -> 0x010b }
            long r5 = r1.getTime()     // Catch:{ Throwable -> 0x010b }
            r12 = r5
            r14 = r16
            goto L_0x011b
        L_0x010b:
            r0 = move-exception
            r1 = r0
            r14 = r16
            goto L_0x0112
        L_0x0110:
            r0 = move-exception
            r1 = r0
        L_0x0112:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r6 = "UploadLogPlugin"
            r5.error(r6, r1)
        L_0x011b:
            boolean r1 = android.text.TextUtils.isEmpty(r2)
            if (r1 == 0) goto L_0x0123
            java.lang.String r2 = "h5plugin.uploadLog"
        L_0x0123:
            boolean r1 = android.text.TextUtils.isEmpty(r3)
            if (r1 == 0) goto L_0x012b
            java.lang.String r3 = "applog"
        L_0x012b:
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 == 0) goto L_0x0133
            java.lang.String r4 = "any"
        L_0x0133:
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            java.lang.String r5 = "bizType"
            r1.putString(r5, r2)
            java.lang.String r2 = "taskType"
            r1.putString(r2, r3)
            java.lang.String r2 = "networkCondition"
            r1.putString(r2, r4)
            java.lang.String r2 = "fromTime"
            r1.putLong(r2, r14)
            java.lang.String r2 = "toTime"
            r1.putLong(r2, r12)
            java.lang.String r2 = "accountName"
            r1.putString(r2, r7)
            java.lang.String r2 = "zippedLenLimit"
            r1.putLong(r2, r8)
            com.autonavi.miniapp.monitor.api.AmapMonitorContext r2 = com.autonavi.miniapp.monitor.api.AmapMonitorFactory.getMonitorContext()
            r3 = 0
            r2.uploadLogByManualTrigger(r1, r3)
            com.alibaba.fastjson.JSONObject r1 = new com.alibaba.fastjson.JSONObject
            r1.<init>()
            r2 = r20
            r2.sendBridgeResult(r1)
            return
        L_0x0171:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r1 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r2 = "UploadLogPlugin"
            java.lang.String r3 = "uploadLog, invalid params"
            r1.error(r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.plugin.log.UploadLogPlugin.handleAction_uploadLog(com.alipay.mobile.h5container.api.H5Event, com.alipay.mobile.h5container.api.H5BridgeContext):void");
    }

    public String getScope() {
        return SCOPE_PAGE;
    }

    public String getEvents() {
        return getEvents(this.mActions);
    }
}
