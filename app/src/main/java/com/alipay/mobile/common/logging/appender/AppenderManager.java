package com.alipay.mobile.common.logging.appender;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AppenderManager {
    private Map<String, Appender> a = new HashMap();
    private LogContext b;
    private boolean c;

    public AppenderManager(LogContext logContext) {
        this.b = logContext;
        this.a.put("applog", new ExternalFileAppender(logContext, "applog", TimeUnit.HOURS.toMillis(1), TimeUnit.DAYS.toMillis(7), 15728640, 65536));
        this.a.put("trafficLog", new ExternalFileAppender(logContext, "trafficLog", TimeUnit.DAYS.toMillis(1), TimeUnit.DAYS.toMillis(30), 8388608, 8192));
        this.a.put("logcat", new ExternalFileAppender(logContext, "logcat", TimeUnit.HOURS.toMillis(1), TimeUnit.DAYS.toMillis(1), 4194304, 8192));
        this.a.put(LogCategory.CATEGORY_USERBEHAVOR, new MdapFileAppender(logContext, LogCategory.CATEGORY_USERBEHAVOR));
        this.a.put(LogCategory.CATEGORY_AUTOUSERBEHAVOR, new MdapFileAppender(logContext, LogCategory.CATEGORY_AUTOUSERBEHAVOR));
        this.a.put(LogCategory.CATEGORY_EXCEPTION, new MdapFileAppender(logContext, LogCategory.CATEGORY_EXCEPTION));
        this.a.put(LogCategory.CATEGORY_SDKMONITOR, new MdapFileAppender(logContext, LogCategory.CATEGORY_SDKMONITOR));
        this.a.put(LogCategory.CATEGORY_PERFORMANCE, new MdapFileAppender(logContext, LogCategory.CATEGORY_PERFORMANCE));
        this.a.put(LogCategory.CATEGORY_ROMESYNC, new MdapFileAppender(logContext, LogCategory.CATEGORY_ROMESYNC));
        this.a.put(LogCategory.CATEGORY_NETWORK, new MdapFileAppender(logContext, LogCategory.CATEGORY_NETWORK));
        this.a.put(LogCategory.CATEGORY_WEBAPP, new MdapFileAppender(logContext, LogCategory.CATEGORY_WEBAPP));
        this.a.put(LogCategory.CATEGORY_FOOTPRINT, new MdapFileAppender(logContext, LogCategory.CATEGORY_FOOTPRINT));
        this.a.put(LogCategory.CATEGORY_KEYBIZTRACE, new MdapFileAppender(logContext, LogCategory.CATEGORY_KEYBIZTRACE));
        this.a.put("crash", new MdapFileAppender(logContext, "crash"));
        this.a.put(LogCategory.CATEGORY_APM, new MdapFileAppender(logContext, LogCategory.CATEGORY_APM));
        this.a.put(LogCategory.CATEGORY_DATAFLOW, new MdapFileAppender(logContext, LogCategory.CATEGORY_DATAFLOW));
        this.a.put(LogCategory.CATEGORY_BATTERY, new MdapFileAppender(logContext, LogCategory.CATEGORY_BATTERY));
        this.a.put(LogCategory.CATEGORY_ALIVEREPORT, new MdapFileAppender(logContext, LogCategory.CATEGORY_ALIVEREPORT));
    }

    public final void a() {
        if (!this.c) {
            this.c = true;
            if (LoggingUtil.isOfflineForExternalFile()) {
                this.a.put("applog", new ExternalFileAppender(this.b, "applog", TimeUnit.HOURS.toMillis(1), TimeUnit.DAYS.toMillis(7), 1073741824, 32768));
            }
        }
    }

    public final void a(String category, boolean isBackupOthers) {
        if (TextUtils.isEmpty(category)) {
            LoggerFactory.getTraceLogger().error((String) "AppenderManager", (String) "backupCurrent: no category");
            return;
        }
        Appender appender = this.a.get(category);
        if (appender == null) {
            LoggerFactory.getTraceLogger().error((String) "AppenderManager", (String) "backupCurrent: no appender");
            return;
        }
        try {
            appender.a(isBackupOthers);
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("AppenderManager", "backupCurrent", t);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0049, code lost:
        if (r12.isLogWrite(r10) != false) goto L_0x004b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(com.alipay.mobile.common.logging.api.LogEvent r24) {
        /*
            r23 = this;
            monitor-enter(r23)
            if (r24 == 0) goto L_0x0009
            boolean r18 = r24.isIllegal()     // Catch:{ all -> 0x0063 }
            if (r18 == 0) goto L_0x0012
        L_0x0009:
            java.lang.String r18 = "AppenderManager"
            java.lang.String r19 = "appendLogEvent: illegal logEvent"
            android.util.Log.e(r18, r19)     // Catch:{ all -> 0x0063 }
        L_0x0010:
            monitor-exit(r23)
            return
        L_0x0012:
            com.alipay.mobile.common.logging.strategy.LogStrategyManager r18 = com.alipay.mobile.common.logging.strategy.LogStrategyManager.getInstance()     // Catch:{ all -> 0x0063 }
            java.lang.String r19 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.logging.api.LogEvent$Level r20 = r24.getLevel()     // Catch:{ all -> 0x0063 }
            boolean r18 = r18.isLogWrite(r19, r20)     // Catch:{ all -> 0x0063 }
            if (r18 == 0) goto L_0x0010
            r0 = r23
            com.alipay.mobile.common.logging.api.LogContext r0 = r0.b     // Catch:{ all -> 0x0063 }
            r18 = r0
            com.alipay.mobile.common.logging.api.LogCustomerControl r12 = r18.getLogCustomerControl()     // Catch:{ all -> 0x0063 }
            if (r12 == 0) goto L_0x004b
            com.alipay.mobile.common.logging.api.customer.LogWriteInfo r10 = new com.alipay.mobile.common.logging.api.customer.LogWriteInfo     // Catch:{ all -> 0x0063 }
            r10.<init>()     // Catch:{ all -> 0x0063 }
            java.lang.String r18 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            r0 = r18
            r10.logCategory = r0     // Catch:{ all -> 0x0063 }
            java.lang.String r18 = r24.getMessage()     // Catch:{ all -> 0x0063 }
            r0 = r18
            r10.logContent = r0     // Catch:{ all -> 0x0063 }
            boolean r18 = r12.isLogWrite(r10)     // Catch:{ all -> 0x0063 }
            if (r18 == 0) goto L_0x0010
        L_0x004b:
            r0 = r23
            java.util.Map<java.lang.String, com.alipay.mobile.common.logging.appender.Appender> r0 = r0.a     // Catch:{ all -> 0x0063 }
            r18 = r0
            java.lang.String r19 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            java.lang.Object r15 = r18.get(r19)     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.logging.appender.Appender r15 = (com.alipay.mobile.common.logging.appender.Appender) r15     // Catch:{ all -> 0x0063 }
            if (r15 == 0) goto L_0x0066
            r0 = r24
            r15.a(r0)     // Catch:{ all -> 0x0063 }
            goto L_0x0010
        L_0x0063:
            r18 = move-exception
            monitor-exit(r23)
            throw r18
        L_0x0066:
            java.lang.String r18 = "flush"
            java.lang.String r19 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            boolean r18 = r18.equals(r19)     // Catch:{ all -> 0x0063 }
            if (r18 == 0) goto L_0x00b8
            java.lang.String r5 = r24.getMessage()     // Catch:{ all -> 0x0063 }
            r0 = r23
            java.util.Map<java.lang.String, com.alipay.mobile.common.logging.appender.Appender> r0 = r0.a     // Catch:{ all -> 0x0063 }
            r18 = r0
            java.util.Collection r18 = r18.values()     // Catch:{ all -> 0x0063 }
            java.util.Iterator r18 = r18.iterator()     // Catch:{ all -> 0x0063 }
        L_0x0084:
            boolean r19 = r18.hasNext()     // Catch:{ all -> 0x0063 }
            if (r19 == 0) goto L_0x0010
            java.lang.Object r3 = r18.next()     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.logging.appender.Appender r3 = (com.alipay.mobile.common.logging.appender.Appender) r3     // Catch:{ all -> 0x0063 }
            if (r5 != 0) goto L_0x009c
            boolean r0 = r3 instanceof com.alipay.mobile.common.logging.appender.MdapFileAppender     // Catch:{ all -> 0x0063 }
            r19 = r0
            if (r19 == 0) goto L_0x0084
        L_0x0098:
            r3.a()     // Catch:{ all -> 0x0063 }
            goto L_0x0084
        L_0x009c:
            java.lang.String r11 = r3.b()     // Catch:{ all -> 0x0063 }
            boolean r19 = r5.equals(r11)     // Catch:{ all -> 0x0063 }
            if (r19 == 0) goto L_0x0084
            boolean r0 = r3 instanceof com.alipay.mobile.common.logging.appender.MdapFileAppender     // Catch:{ all -> 0x0063 }
            r19 = r0
            if (r19 == 0) goto L_0x0098
            com.alipay.mobile.common.logging.appender.MdapFileAppender r3 = (com.alipay.mobile.common.logging.appender.MdapFileAppender) r3     // Catch:{ all -> 0x0063 }
            android.os.Bundle r19 = r24.getBundle()     // Catch:{ all -> 0x0063 }
            r0 = r19
            r3.a(r0)     // Catch:{ all -> 0x0063 }
            goto L_0x0084
        L_0x00b8:
            java.lang.String r18 = "uploadByEvent"
            java.lang.String r19 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            boolean r18 = r18.equals(r19)     // Catch:{ all -> 0x0063 }
            if (r18 == 0) goto L_0x0161
            java.lang.String r7 = r24.getMessage()     // Catch:{ all -> 0x0063 }
            if (r7 != 0) goto L_0x00dd
            r0 = r23
            com.alipay.mobile.common.logging.api.LogContext r0 = r0.b     // Catch:{ all -> 0x0063 }
            r18 = r0
            r19 = 0
            r20 = 0
            android.os.Bundle r21 = r24.getBundle()     // Catch:{ all -> 0x0063 }
            r18.upload(r19, r20, r21)     // Catch:{ all -> 0x0063 }
            goto L_0x0010
        L_0x00dd:
            r0 = r23
            java.util.Map<java.lang.String, com.alipay.mobile.common.logging.appender.Appender> r0 = r0.a     // Catch:{ all -> 0x0063 }
            r18 = r0
            java.util.Collection r18 = r18.values()     // Catch:{ all -> 0x0063 }
            java.util.Iterator r18 = r18.iterator()     // Catch:{ all -> 0x0063 }
        L_0x00eb:
            boolean r19 = r18.hasNext()     // Catch:{ all -> 0x0063 }
            if (r19 == 0) goto L_0x0010
            java.lang.Object r3 = r18.next()     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.logging.appender.Appender r3 = (com.alipay.mobile.common.logging.appender.Appender) r3     // Catch:{ all -> 0x0063 }
            boolean r0 = r3 instanceof com.alipay.mobile.common.logging.appender.MdapFileAppender     // Catch:{ all -> 0x0063 }
            r19 = r0
            if (r19 == 0) goto L_0x00eb
            r0 = r3
            com.alipay.mobile.common.logging.appender.MdapFileAppender r0 = (com.alipay.mobile.common.logging.appender.MdapFileAppender) r0     // Catch:{ all -> 0x0063 }
            r13 = r0
            java.lang.String r11 = r3.b()     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.logging.strategy.LogStrategyManager r19 = com.alipay.mobile.common.logging.strategy.LogStrategyManager.getInstance()     // Catch:{ all -> 0x0063 }
            r0 = r19
            boolean r19 = r0.isLogUpload(r11, r7)     // Catch:{ all -> 0x0063 }
            if (r19 == 0) goto L_0x00eb
            com.alipay.mobile.common.logging.strategy.LogStrategyManager r19 = com.alipay.mobile.common.logging.strategy.LogStrategyManager.getInstance()     // Catch:{ all -> 0x0063 }
            r0 = r19
            boolean r19 = r0.isLogUploadByInterval(r11, r7)     // Catch:{ all -> 0x0063 }
            if (r19 == 0) goto L_0x00eb
            r17 = 0
            if (r12 == 0) goto L_0x0146
            com.alipay.mobile.common.logging.api.customer.LogUploadInfo r16 = new com.alipay.mobile.common.logging.api.customer.LogUploadInfo     // Catch:{ all -> 0x0063 }
            r16.<init>()     // Catch:{ all -> 0x0063 }
            r0 = r16
            r0.logCategory = r11     // Catch:{ all -> 0x0063 }
            java.io.File r19 = r13.c()     // Catch:{ all -> 0x0063 }
            r0 = r19
            r1 = r16
            r1.logFile = r0     // Catch:{ all -> 0x0063 }
            r0 = r16
            com.alipay.mobile.common.logging.api.customer.UploadResultInfo r14 = r12.isLogUpload(r0)     // Catch:{ all -> 0x0063 }
            if (r14 == 0) goto L_0x0146
            boolean r0 = r14.isUpload     // Catch:{ all -> 0x0063 }
            r19 = r0
            if (r19 == 0) goto L_0x00eb
            java.lang.String r0 = r14.uploadUrl     // Catch:{ all -> 0x0063 }
            r17 = r0
        L_0x0146:
            r13.a()     // Catch:{ all -> 0x0063 }
            android.os.Bundle r4 = r24.getBundle()     // Catch:{ all -> 0x0063 }
            if (r4 != 0) goto L_0x0154
            android.os.Bundle r4 = new android.os.Bundle     // Catch:{ all -> 0x0063 }
            r4.<init>()     // Catch:{ all -> 0x0063 }
        L_0x0154:
            java.lang.String r19 = "event"
            r0 = r19
            r4.putString(r0, r7)     // Catch:{ all -> 0x0063 }
            r0 = r17
            r13.a(r0, r4)     // Catch:{ all -> 0x0063 }
            goto L_0x00eb
        L_0x0161:
            java.lang.String r18 = "uploadByType"
            java.lang.String r19 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            boolean r18 = r18.equals(r19)     // Catch:{ all -> 0x0063 }
            if (r18 == 0) goto L_0x01d8
            java.lang.String r11 = r24.getMessage()     // Catch:{ all -> 0x0063 }
            android.os.Bundle r4 = r24.getBundle()     // Catch:{ all -> 0x0063 }
            if (r11 != 0) goto L_0x018a
            r0 = r23
            com.alipay.mobile.common.logging.api.LogContext r0 = r0.b     // Catch:{ all -> 0x0063 }
            r18 = r0
            r19 = 0
            r20 = 0
            android.os.Bundle r21 = r24.getBundle()     // Catch:{ all -> 0x0063 }
            r18.upload(r19, r20, r21)     // Catch:{ all -> 0x0063 }
            goto L_0x0010
        L_0x018a:
            if (r4 == 0) goto L_0x01b1
            java.lang.String r18 = "isMergeUpload"
            r19 = 0
            r0 = r18
            r1 = r19
            boolean r18 = r4.getBoolean(r0, r1)     // Catch:{ all -> 0x0063 }
            if (r18 == 0) goto L_0x01b1
            r0 = r23
            com.alipay.mobile.common.logging.api.LogContext r0 = r0.b     // Catch:{ all -> 0x0063 }
            r18 = r0
            r19 = 0
            android.os.Bundle r20 = r24.getBundle()     // Catch:{ all -> 0x0063 }
            r0 = r18
            r1 = r19
            r2 = r20
            r0.upload(r11, r1, r2)     // Catch:{ all -> 0x0063 }
            goto L_0x0010
        L_0x01b1:
            r0 = r23
            java.util.Map<java.lang.String, com.alipay.mobile.common.logging.appender.Appender> r0 = r0.a     // Catch:{ all -> 0x0063 }
            r18 = r0
            r0 = r18
            java.lang.Object r3 = r0.get(r11)     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.logging.appender.Appender r3 = (com.alipay.mobile.common.logging.appender.Appender) r3     // Catch:{ all -> 0x0063 }
            boolean r0 = r3 instanceof com.alipay.mobile.common.logging.appender.MdapFileAppender     // Catch:{ all -> 0x0063 }
            r18 = r0
            if (r18 == 0) goto L_0x0010
            com.alipay.mobile.common.logging.appender.MdapFileAppender r3 = (com.alipay.mobile.common.logging.appender.MdapFileAppender) r3     // Catch:{ all -> 0x0063 }
            java.lang.String r18 = r24.getUploadUrl()     // Catch:{ all -> 0x0063 }
            android.os.Bundle r19 = r24.getBundle()     // Catch:{ all -> 0x0063 }
            r0 = r18
            r1 = r19
            r3.a(r0, r1)     // Catch:{ all -> 0x0063 }
            goto L_0x0010
        L_0x01d8:
            java.lang.String r18 = "refreshSession"
            java.lang.String r19 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            boolean r18 = r18.equals(r19)     // Catch:{ all -> 0x0063 }
            if (r18 == 0) goto L_0x01ff
            r0 = r23
            com.alipay.mobile.common.logging.api.LogContext r0 = r0.b     // Catch:{ Throwable -> 0x01ef }
            r18 = r0
            r18.refreshSessionId()     // Catch:{ Throwable -> 0x01ef }
            goto L_0x0010
        L_0x01ef:
            r6 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r18 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0063 }
            java.lang.String r19 = "AppenderManager"
            r0 = r18
            r1 = r19
            r0.error(r1, r6)     // Catch:{ all -> 0x0063 }
            goto L_0x0010
        L_0x01ff:
            java.lang.String r18 = "gotoBackground"
            java.lang.String r19 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            boolean r18 = r18.equals(r19)     // Catch:{ all -> 0x0063 }
            if (r18 == 0) goto L_0x022e
            java.lang.String r18 = r24.getMessage()     // Catch:{ Throwable -> 0x021e }
            long r8 = java.lang.Long.parseLong(r18)     // Catch:{ Throwable -> 0x021e }
            com.alipay.mobile.common.logging.strategy.LogStrategyManager r18 = com.alipay.mobile.common.logging.strategy.LogStrategyManager.getInstance()     // Catch:{ Throwable -> 0x021e }
            r0 = r18
            r0.updateBackgroundTime(r8)     // Catch:{ Throwable -> 0x021e }
            goto L_0x0010
        L_0x021e:
            r6 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r18 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0063 }
            java.lang.String r19 = "AppenderManager"
            r0 = r18
            r1 = r19
            r0.error(r1, r6)     // Catch:{ all -> 0x0063 }
            goto L_0x0010
        L_0x022e:
            if (r15 != 0) goto L_0x0010
            r0 = r23
            java.util.Map<java.lang.String, com.alipay.mobile.common.logging.appender.Appender> r0 = r0.a     // Catch:{ all -> 0x0063 }
            r18 = r0
            java.lang.String r19 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.logging.appender.MdapFileAppender r20 = new com.alipay.mobile.common.logging.appender.MdapFileAppender     // Catch:{ all -> 0x0063 }
            r0 = r23
            com.alipay.mobile.common.logging.api.LogContext r0 = r0.b     // Catch:{ all -> 0x0063 }
            r21 = r0
            java.lang.String r22 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            r20.<init>(r21, r22)     // Catch:{ all -> 0x0063 }
            r18.put(r19, r20)     // Catch:{ all -> 0x0063 }
            r0 = r23
            java.util.Map<java.lang.String, com.alipay.mobile.common.logging.appender.Appender> r0 = r0.a     // Catch:{ all -> 0x0063 }
            r18 = r0
            java.lang.String r19 = r24.getCategory()     // Catch:{ all -> 0x0063 }
            java.lang.Object r18 = r18.get(r19)     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.logging.appender.Appender r18 = (com.alipay.mobile.common.logging.appender.Appender) r18     // Catch:{ all -> 0x0063 }
            r0 = r18
            r1 = r24
            r0.a(r1)     // Catch:{ all -> 0x0063 }
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.appender.AppenderManager.a(com.alipay.mobile.common.logging.api.LogEvent):void");
    }
}
