package com.taobao.accs.net;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.statist.RequestStatistic;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.location.common.model.AmapLoc;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.ut.statistics.MonitorStatistic;
import com.taobao.accs.ut.statistics.ReceiveMsgStat;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.OrangeAdapter;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class InAppConnection extends BaseConnection implements l {
    private static final int CONN_TIMEOUT = 60000;
    private static final int RESEND_DELAY = 2000;
    private static final String TAG = "InAppConn_";
    private boolean mRunning = true;
    private Set<String> mSessionRegistered = Collections.synchronizedSet(new HashSet());
    private Runnable mTryStartServiceRunable = new Runnable() {
        public void run() {
            try {
                if (InAppConnection.this.mContext != null && !TextUtils.isEmpty(InAppConnection.this.getAppkey())) {
                    ALog.i(InAppConnection.this.getTag(), "mTryStartServiceRunable bindapp", new Object[0]);
                    InAppConnection.this.startChannelService(InAppConnection.this.mContext);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private ScheduledFuture<?> startServiceTaskFuture = null;

    public static class Auth implements n {
        /* access modifiers changed from: private */
        public String TAG;
        private String authUrl;
        /* access modifiers changed from: private */
        public BaseConnection connection;
        private int connectionType;

        public Auth(BaseConnection baseConnection, String str) {
            this.TAG = baseConnection.getTag();
            StringBuilder sb = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS);
            sb.append(str);
            sb.append("/accs/");
            this.authUrl = baseConnection.buildAuthUrl(sb.toString());
            this.connectionType = baseConnection.mConnectionType;
            this.connection = baseConnection;
        }

        public void auth(p pVar, final a aVar) {
            ALog.e(this.TAG, "auth", MonitorItemConstants.KEY_URL, this.authUrl);
            pVar.a(new a().a(this.authUrl).a(), (o) new o() {
                public void onDataReceive(aa aaVar, boolean z) {
                }

                public void onResponseCode(int i, Map<String, List<String>> map) {
                    ALog.e(Auth.this.TAG, "auth", "httpStatusCode", Integer.valueOf(i));
                    if (i == 200) {
                        aVar.a();
                    } else {
                        aVar.a(i);
                    }
                    Map<String, String> header = UtilityImpl.getHeader(map);
                    ALog.d(Auth.this.TAG, "auth", Performance.KEY_LOG_HEADER, header);
                    String str = header.get("x-at");
                    if (!TextUtils.isEmpty(str)) {
                        Auth.this.connection.mConnToken = str;
                    }
                }

                public void onFinish(int i, String str, RequestStatistic requestStatistic) {
                    if (i < 0) {
                        ALog.e(Auth.this.TAG, "auth onFinish", "statusCode", Integer.valueOf(i));
                        aVar.a(i);
                    }
                }
            });
        }
    }

    public void close() {
    }

    public int getChannelState() {
        return 1;
    }

    public void ping(boolean z, boolean z2) {
    }

    public MonitorStatistic updateMonitorInfo() {
        return null;
    }

    public InAppConnection(Context context, int i, String str) {
        super(context, i, str);
        if (!OrangeAdapter.isTnetLogOff(true)) {
            String tnetLogFilePath = UtilityImpl.getTnetLogFilePath(this.mContext, "inapp");
            ALog.d(getTag(), "config tnet log path:".concat(String.valueOf(tnetLogFilePath)), new Object[0]);
            if (!TextUtils.isEmpty(tnetLogFilePath)) {
                p.a(context, tnetLogFilePath);
            }
        }
        this.startServiceTaskFuture = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(this.mTryStartServiceRunable, 120000, TimeUnit.MILLISECONDS);
    }

    public synchronized void start() {
        ALog.d(getTag(), H5PageData.KEY_UC_START, new Object[0]);
        this.mRunning = true;
        initAwcn(this.mContext);
    }

    /* access modifiers changed from: protected */
    public void sendMessage(final Message message, boolean z) {
        if (!this.mRunning || message == null) {
            String tag = getTag();
            StringBuilder sb = new StringBuilder("not running or msg null! ");
            sb.append(this.mRunning);
            ALog.e(tag, sb.toString(), new Object[0]);
            return;
        }
        try {
            if (ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size() > 1000) {
                throw new RejectedExecutionException("accs");
            }
            ScheduledFuture<?> schedule = ThreadPoolExecutorFactory.getSendScheduledExecutor().schedule(new Runnable() {
                /* JADX WARNING: Removed duplicated region for block: B:46:0x01f8  */
                /* JADX WARNING: Removed duplicated region for block: B:61:0x0248  */
                /* JADX WARNING: Removed duplicated region for block: B:63:0x026d  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r18 = this;
                        r1 = r18
                        com.taobao.accs.data.Message r2 = r6
                        if (r2 == 0) goto L_0x038e
                        com.taobao.accs.data.Message r2 = r6
                        com.taobao.accs.ut.monitor.NetPerformanceMonitor r2 = r2.getNetPermanceMonitor()
                        if (r2 == 0) goto L_0x0017
                        com.taobao.accs.data.Message r2 = r6
                        com.taobao.accs.ut.monitor.NetPerformanceMonitor r2 = r2.getNetPermanceMonitor()
                        r2.onTakeFromQueue()
                    L_0x0017:
                        com.taobao.accs.data.Message r2 = r6
                        int r2 = r2.getType()
                        r3 = 3
                        r4 = 4
                        r5 = 2
                        r6 = 0
                        r7 = 1
                        com.taobao.accs.utl.ALog$Level r8 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ Throwable -> 0x029e }
                        boolean r8 = com.taobao.accs.utl.ALog.isPrintLog(r8)     // Catch:{ Throwable -> 0x029e }
                        if (r8 == 0) goto L_0x004b
                        com.taobao.accs.net.InAppConnection r8 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r8 = r8.getTag()     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r9 = "sendMessage start"
                        java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r11 = "dataId"
                        r10[r6] = r11     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r11 = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r11 = r11.dataId     // Catch:{ Throwable -> 0x029e }
                        r10[r7] = r11     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r11 = "type"
                        r10[r5] = r11     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r11 = com.taobao.accs.data.Message.MsgType.name(r2)     // Catch:{ Throwable -> 0x029e }
                        r10[r3] = r11     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.utl.ALog.d(r8, r9, r10)     // Catch:{ Throwable -> 0x029e }
                    L_0x004b:
                        if (r2 != r7) goto L_0x01dd
                        com.taobao.accs.data.Message r8 = r6     // Catch:{ Throwable -> 0x029e }
                        java.net.URL r8 = r8.host     // Catch:{ Throwable -> 0x029e }
                        if (r8 != 0) goto L_0x005f
                        com.taobao.accs.net.InAppConnection r8 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.MessageHandler r8 = r8.mMessageHandler     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r9 = r6     // Catch:{ Throwable -> 0x029e }
                        r10 = -5
                        r8.onResult(r9, r10)     // Catch:{ Throwable -> 0x029e }
                        goto L_0x01d9
                    L_0x005f:
                        com.taobao.accs.net.InAppConnection r8 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.AccsClientConfig r8 = r8.mConfig     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r8 = r8.getAppKey()     // Catch:{ Throwable -> 0x029e }
                        r r8 = defpackage.r.a(r8)     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.net.InAppConnection r9 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r10 = r6     // Catch:{ Throwable -> 0x029e }
                        java.net.URL r10 = r10.host     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r10 = r10.getHost()     // Catch:{ Throwable -> 0x029e }
                        r9.registerSessionInfo(r8, r10, r6)     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r9 = r6     // Catch:{ Throwable -> 0x029e }
                        java.net.URL r9 = r9.host     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x029e }
                        anet.channel.entity.ConnType$TypeLevel r10 = anet.channel.entity.ConnType.TypeLevel.SPDY     // Catch:{ Throwable -> 0x029e }
                        r11 = 60000(0xea60, double:2.9644E-319)
                        p r8 = r8.a(r9, r10, r11)     // Catch:{ Throwable -> 0x029e }
                        if (r8 == 0) goto L_0x01db
                        com.taobao.accs.data.Message r9 = r6     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.net.InAppConnection r10 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        android.content.Context r10 = r10.mContext     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.net.InAppConnection r11 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        int r11 = r11.mConnectionType     // Catch:{ Throwable -> 0x029e }
                        byte[] r9 = r9.build(r10, r11)     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r10 = "accs"
                        com.taobao.accs.data.Message r11 = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r11 = r11.serviceId     // Catch:{ Throwable -> 0x029e }
                        boolean r10 = r10.equals(r11)     // Catch:{ Throwable -> 0x029e }
                        r13 = 7
                        r14 = 6
                        r15 = 5
                        r11 = 10
                        if (r10 == 0) goto L_0x00f5
                        com.taobao.accs.net.InAppConnection r10 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r10 = r10.getTag()     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r12 = "sendMessage"
                        java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r16 = "dataId"
                        r11[r6] = r16     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r6 = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r6 = r6.getDataId()     // Catch:{ Throwable -> 0x029e }
                        r11[r7] = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r6 = "command"
                        r11[r5] = r6     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r6 = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.Integer r6 = r6.command     // Catch:{ Throwable -> 0x029e }
                        r11[r3] = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r6 = "host"
                        r11[r4] = r6     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r6 = r6     // Catch:{ Throwable -> 0x029e }
                        java.net.URL r6 = r6.host     // Catch:{ Throwable -> 0x029e }
                        r11[r15] = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r6 = "len"
                        r11[r14] = r6     // Catch:{ Throwable -> 0x029e }
                        if (r9 != 0) goto L_0x00dc
                        r6 = 0
                        goto L_0x00dd
                    L_0x00dc:
                        int r6 = r9.length     // Catch:{ Throwable -> 0x029e }
                    L_0x00dd:
                        java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x029e }
                        r11[r13] = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r6 = "utdid"
                        r13 = 8
                        r11[r13] = r6     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r6 = r6.mUtdid     // Catch:{ Throwable -> 0x029e }
                        r13 = 9
                        r11[r13] = r6     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.utl.ALog.e(r10, r12, r11)     // Catch:{ Throwable -> 0x029e }
                        goto L_0x0149
                    L_0x00f5:
                        com.taobao.accs.utl.ALog$Level r6 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x029e }
                        boolean r6 = com.taobao.accs.utl.ALog.isPrintLog(r6)     // Catch:{ Throwable -> 0x029e }
                        if (r6 == 0) goto L_0x0149
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r6 = r6.getTag()     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r10 = "sendMessage"
                        java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r12 = "dataId"
                        r16 = 0
                        r11[r16] = r12     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r12 = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r12 = r12.getDataId()     // Catch:{ Throwable -> 0x029e }
                        r11[r7] = r12     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r12 = "command"
                        r11[r5] = r12     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r12 = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.Integer r12 = r12.command     // Catch:{ Throwable -> 0x029e }
                        r11[r3] = r12     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r12 = "host"
                        r11[r4] = r12     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r12 = r6     // Catch:{ Throwable -> 0x029e }
                        java.net.URL r12 = r12.host     // Catch:{ Throwable -> 0x029e }
                        r11[r15] = r12     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r12 = "len"
                        r11[r14] = r12     // Catch:{ Throwable -> 0x029e }
                        if (r9 != 0) goto L_0x0131
                        r12 = 0
                        goto L_0x0132
                    L_0x0131:
                        int r12 = r9.length     // Catch:{ Throwable -> 0x029e }
                    L_0x0132:
                        java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Throwable -> 0x029e }
                        r11[r13] = r12     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r12 = "utdid"
                        r13 = 8
                        r11[r13] = r12     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.net.InAppConnection r12 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r12 = r12.mUtdid     // Catch:{ Throwable -> 0x029e }
                        r13 = 9
                        r11[r13] = r12     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.utl.ALog.d(r6, r10, r11)     // Catch:{ Throwable -> 0x029e }
                    L_0x0149:
                        com.taobao.accs.data.Message r6 = r6     // Catch:{ Throwable -> 0x029e }
                        long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x029e }
                        r6.setSendTime(r10)     // Catch:{ Throwable -> 0x029e }
                        int r6 = r9.length     // Catch:{ Throwable -> 0x029e }
                        r10 = 16384(0x4000, float:2.2959E-41)
                        if (r6 <= r10) goto L_0x016e
                        com.taobao.accs.data.Message r6 = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.Integer r6 = r6.command     // Catch:{ Throwable -> 0x029e }
                        int r6 = r6.intValue()     // Catch:{ Throwable -> 0x029e }
                        r10 = 102(0x66, float:1.43E-43)
                        if (r6 == r10) goto L_0x016e
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.MessageHandler r6 = r6.mMessageHandler     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r8 = r6     // Catch:{ Throwable -> 0x029e }
                        r9 = -4
                        r6.onResult(r8, r9)     // Catch:{ Throwable -> 0x029e }
                        goto L_0x01d9
                    L_0x016e:
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.MessageHandler r6 = r6.mMessageHandler     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r10 = r6     // Catch:{ Throwable -> 0x029e }
                        r6.onSend(r10)     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r6 = r6     // Catch:{ Throwable -> 0x029e }
                        boolean r6 = r6.isAck     // Catch:{ Throwable -> 0x029e }
                        if (r6 == 0) goto L_0x0190
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        java.util.LinkedHashMap r6 = r6.mAckMessage     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r10 = r6     // Catch:{ Throwable -> 0x029e }
                        int r10 = r10.getIntDataId()     // Catch:{ Throwable -> 0x029e }
                        java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r11 = r6     // Catch:{ Throwable -> 0x029e }
                        r6.put(r10, r11)     // Catch:{ Throwable -> 0x029e }
                    L_0x0190:
                        com.taobao.accs.data.Message r6 = r6     // Catch:{ Throwable -> 0x029e }
                        int r6 = r6.getIntDataId()     // Catch:{ Throwable -> 0x029e }
                        r8.a(r6, r9)     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r6 = r6     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.ut.monitor.NetPerformanceMonitor r6 = r6.getNetPermanceMonitor()     // Catch:{ Throwable -> 0x029e }
                        if (r6 == 0) goto L_0x01aa
                        com.taobao.accs.data.Message r6 = r6     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.ut.monitor.NetPerformanceMonitor r6 = r6.getNetPermanceMonitor()     // Catch:{ Throwable -> 0x029e }
                        r6.onSendData()     // Catch:{ Throwable -> 0x029e }
                    L_0x01aa:
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r8 = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r8 = r8.getDataId()     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r10 = r6     // Catch:{ Throwable -> 0x029e }
                        int r10 = r10.timeout     // Catch:{ Throwable -> 0x029e }
                        long r10 = (long) r10     // Catch:{ Throwable -> 0x029e }
                        r6.setTimeOut(r8, r10)     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.MessageHandler r6 = r6.mMessageHandler     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo r8 = new com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r10 = r6     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r11 = r10.serviceId     // Catch:{ Throwable -> 0x029e }
                        boolean r12 = defpackage.m.h()     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.data.Message r10 = r6     // Catch:{ Throwable -> 0x029e }
                        java.net.URL r10 = r10.host     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r13 = r10.toString()     // Catch:{ Throwable -> 0x029e }
                        int r9 = r9.length     // Catch:{ Throwable -> 0x029e }
                        long r14 = (long) r9     // Catch:{ Throwable -> 0x029e }
                        r10 = r8
                        r10.<init>(r11, r12, r13, r14)     // Catch:{ Throwable -> 0x029e }
                        r6.addTrafficsInfo(r8)     // Catch:{ Throwable -> 0x029e }
                    L_0x01d9:
                        r6 = 1
                        goto L_0x01f6
                    L_0x01db:
                        r6 = 0
                        goto L_0x01f6
                    L_0x01dd:
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r6 = r6.getTag()     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r8 = "sendMessage skip"
                        java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r10 = "type"
                        r11 = 0
                        r9[r11] = r10     // Catch:{ Throwable -> 0x029e }
                        java.lang.String r10 = com.taobao.accs.data.Message.MsgType.name(r2)     // Catch:{ Throwable -> 0x029e }
                        r9[r7] = r10     // Catch:{ Throwable -> 0x029e }
                        com.taobao.accs.utl.ALog.e(r6, r8, r9)     // Catch:{ Throwable -> 0x029e }
                        goto L_0x01d9
                    L_0x01f6:
                        if (r6 != 0) goto L_0x023c
                        r8 = -11
                        if (r2 != r7) goto L_0x0233
                        com.taobao.accs.data.Message r2 = r6
                        boolean r2 = r2.isTimeOut()
                        if (r2 != 0) goto L_0x0210
                        com.taobao.accs.net.InAppConnection r2 = com.taobao.accs.net.InAppConnection.this
                        com.taobao.accs.data.Message r9 = r6
                        r10 = 2000(0x7d0, float:2.803E-42)
                        boolean r2 = r2.reSend(r9, r10)
                        if (r2 != 0) goto L_0x0219
                    L_0x0210:
                        com.taobao.accs.net.InAppConnection r2 = com.taobao.accs.net.InAppConnection.this
                        com.taobao.accs.data.MessageHandler r2 = r2.mMessageHandler
                        com.taobao.accs.data.Message r9 = r6
                        r2.onResult(r9, r8)
                    L_0x0219:
                        com.taobao.accs.data.Message r2 = r6
                        int r2 = r2.retryTimes
                        if (r2 != r7) goto L_0x023c
                        com.taobao.accs.data.Message r2 = r6
                        com.taobao.accs.ut.monitor.NetPerformanceMonitor r2 = r2.getNetPermanceMonitor()
                        if (r2 == 0) goto L_0x023c
                        java.lang.String r2 = "accs"
                        java.lang.String r8 = "resend"
                        java.lang.String r9 = "total_accs"
                        r10 = 0
                        com.taobao.accs.utl.AppMonitorAdapter.commitCount(r2, r8, r9, r10)
                        goto L_0x023c
                    L_0x0233:
                        com.taobao.accs.net.InAppConnection r2 = com.taobao.accs.net.InAppConnection.this
                        com.taobao.accs.data.MessageHandler r2 = r2.mMessageHandler
                        com.taobao.accs.data.Message r9 = r6
                        r2.onResult(r9, r8)
                    L_0x023c:
                        java.lang.String r2 = "accs"
                        com.taobao.accs.data.Message r8 = r6
                        java.lang.String r8 = r8.serviceId
                        boolean r2 = r2.equals(r8)
                        if (r2 == 0) goto L_0x026d
                        com.taobao.accs.net.InAppConnection r2 = com.taobao.accs.net.InAppConnection.this
                        java.lang.String r2 = r2.getTag()
                        java.lang.String r8 = "sendMessage end"
                        java.lang.Object[] r4 = new java.lang.Object[r4]
                        java.lang.String r9 = "dataId"
                        r10 = 0
                        r4[r10] = r9
                        com.taobao.accs.data.Message r9 = r6
                        java.lang.String r9 = r9.getDataId()
                        r4[r7] = r9
                        java.lang.String r7 = "status"
                        r4[r5] = r7
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r6)
                        r4[r3] = r5
                        com.taobao.accs.utl.ALog.e(r2, r8, r4)
                        return
                    L_0x026d:
                        com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.D
                        boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)
                        if (r2 == 0) goto L_0x038e
                        com.taobao.accs.net.InAppConnection r2 = com.taobao.accs.net.InAppConnection.this
                        java.lang.String r2 = r2.getTag()
                        java.lang.String r8 = "sendMessage end"
                        java.lang.Object[] r4 = new java.lang.Object[r4]
                        java.lang.String r9 = "dataId"
                        r10 = 0
                        r4[r10] = r9
                        com.taobao.accs.data.Message r9 = r6
                        java.lang.String r9 = r9.getDataId()
                        r4[r7] = r9
                        java.lang.String r7 = "status"
                        r4[r5] = r7
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r6)
                        r4[r3] = r5
                        com.taobao.accs.utl.ALog.d(r2, r8, r4)
                        return
                    L_0x029a:
                        r0 = move-exception
                        r2 = r0
                        goto L_0x0330
                    L_0x029e:
                        r0 = move-exception
                        r2 = r0
                        java.lang.String r6 = "accs"
                        java.lang.String r8 = "send_fail"
                        com.taobao.accs.data.Message r9 = r6     // Catch:{ all -> 0x029a }
                        java.lang.String r9 = r9.serviceId     // Catch:{ all -> 0x029a }
                        java.lang.String r10 = ""
                        java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x029a }
                        r11.<init>()     // Catch:{ all -> 0x029a }
                        com.taobao.accs.net.InAppConnection r12 = com.taobao.accs.net.InAppConnection.this     // Catch:{ all -> 0x029a }
                        int r12 = r12.mConnectionType     // Catch:{ all -> 0x029a }
                        r11.append(r12)     // Catch:{ all -> 0x029a }
                        java.lang.String r12 = r2.toString()     // Catch:{ all -> 0x029a }
                        r11.append(r12)     // Catch:{ all -> 0x029a }
                        java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x029a }
                        com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r6, r8, r9, r10, r11)     // Catch:{ all -> 0x029a }
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this     // Catch:{ all -> 0x029a }
                        java.lang.String r6 = r6.getTag()     // Catch:{ all -> 0x029a }
                        java.lang.String r8 = "sendMessage"
                        r9 = 0
                        java.lang.Object[] r10 = new java.lang.Object[r9]     // Catch:{ all -> 0x029a }
                        com.taobao.accs.utl.ALog.e(r6, r8, r2, r10)     // Catch:{ all -> 0x029a }
                        java.lang.String r2 = "accs"
                        com.taobao.accs.data.Message r6 = r6
                        java.lang.String r6 = r6.serviceId
                        boolean r2 = r2.equals(r6)
                        if (r2 == 0) goto L_0x0303
                        com.taobao.accs.net.InAppConnection r2 = com.taobao.accs.net.InAppConnection.this
                        java.lang.String r2 = r2.getTag()
                        java.lang.String r6 = "sendMessage end"
                        java.lang.Object[] r4 = new java.lang.Object[r4]
                        java.lang.String r8 = "dataId"
                        r9 = 0
                        r4[r9] = r8
                        com.taobao.accs.data.Message r8 = r6
                        java.lang.String r8 = r8.getDataId()
                        r4[r7] = r8
                        java.lang.String r8 = "status"
                        r4[r5] = r8
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r7)
                        r4[r3] = r5
                        com.taobao.accs.utl.ALog.e(r2, r6, r4)
                        return
                    L_0x0303:
                        com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.D
                        boolean r2 = com.taobao.accs.utl.ALog.isPrintLog(r2)
                        if (r2 == 0) goto L_0x038e
                        com.taobao.accs.net.InAppConnection r2 = com.taobao.accs.net.InAppConnection.this
                        java.lang.String r2 = r2.getTag()
                        java.lang.String r6 = "sendMessage end"
                        java.lang.Object[] r4 = new java.lang.Object[r4]
                        java.lang.String r8 = "dataId"
                        r9 = 0
                        r4[r9] = r8
                        com.taobao.accs.data.Message r8 = r6
                        java.lang.String r8 = r8.getDataId()
                        r4[r7] = r8
                        java.lang.String r8 = "status"
                        r4[r5] = r8
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r7)
                        r4[r3] = r5
                        com.taobao.accs.utl.ALog.d(r2, r6, r4)
                        return
                    L_0x0330:
                        java.lang.String r6 = "accs"
                        com.taobao.accs.data.Message r8 = r6
                        java.lang.String r8 = r8.serviceId
                        boolean r6 = r6.equals(r8)
                        if (r6 == 0) goto L_0x0361
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this
                        java.lang.String r6 = r6.getTag()
                        java.lang.String r8 = "sendMessage end"
                        java.lang.Object[] r4 = new java.lang.Object[r4]
                        java.lang.String r9 = "dataId"
                        r10 = 0
                        r4[r10] = r9
                        com.taobao.accs.data.Message r9 = r6
                        java.lang.String r9 = r9.getDataId()
                        r4[r7] = r9
                        java.lang.String r9 = "status"
                        r4[r5] = r9
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r7)
                        r4[r3] = r5
                        com.taobao.accs.utl.ALog.e(r6, r8, r4)
                        goto L_0x038d
                    L_0x0361:
                        com.taobao.accs.utl.ALog$Level r6 = com.taobao.accs.utl.ALog.Level.D
                        boolean r6 = com.taobao.accs.utl.ALog.isPrintLog(r6)
                        if (r6 == 0) goto L_0x038d
                        com.taobao.accs.net.InAppConnection r6 = com.taobao.accs.net.InAppConnection.this
                        java.lang.String r6 = r6.getTag()
                        java.lang.String r8 = "sendMessage end"
                        java.lang.Object[] r4 = new java.lang.Object[r4]
                        java.lang.String r9 = "dataId"
                        r10 = 0
                        r4[r10] = r9
                        com.taobao.accs.data.Message r9 = r6
                        java.lang.String r9 = r9.getDataId()
                        r4[r7] = r9
                        java.lang.String r9 = "status"
                        r4[r5] = r9
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r7)
                        r4[r3] = r5
                        com.taobao.accs.utl.ALog.d(r6, r8, r4)
                    L_0x038d:
                        throw r2
                    L_0x038e:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.InAppConnection.AnonymousClass1.run():void");
                }
            }, message.delyTime, TimeUnit.MILLISECONDS);
            if (message.getType() == 1 && message.cunstomDataId != null) {
                if (message.isControlFrame() && cancel(message.cunstomDataId)) {
                    this.mMessageHandler.cancelControlMessage(message);
                }
                this.mMessageHandler.reqTasks.put(message.cunstomDataId, schedule);
            }
            NetPerformanceMonitor netPermanceMonitor = message.getNetPermanceMonitor();
            if (netPermanceMonitor != null) {
                netPermanceMonitor.setDeviceId(UtilityImpl.getDeviceId(this.mContext));
                netPermanceMonitor.setConnType(this.mConnectionType);
                netPermanceMonitor.onEnterQueueData();
            }
        } catch (RejectedExecutionException unused) {
            this.mMessageHandler.onResult(message, ErrorCode.MESSAGE_QUEUE_FULL);
            String tag2 = getTag();
            StringBuilder sb2 = new StringBuilder("send queue full count:");
            sb2.append(ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size());
            ALog.e(tag2, sb2.toString(), new Object[0]);
        } catch (Throwable th) {
            this.mMessageHandler.onResult(message, -8);
            ALog.e(getTag(), "send error", th, new Object[0]);
        }
    }

    public void shutdown() {
        ALog.e(getTag(), "shut down", new Object[0]);
        this.mRunning = false;
    }

    public boolean isAlive() {
        return this.mRunning;
    }

    public void notifyNetWorkChange(String str) {
        this.mTimeoutMsgNum = 0;
    }

    /* access modifiers changed from: protected */
    public void onTimeOut(String str, String str2) {
        try {
            Message unhandledMessage = this.mMessageHandler.getUnhandledMessage(str);
            if (!(unhandledMessage == null || unhandledMessage.host == null)) {
                p a = r.a(this.mConfig.getAppKey()).a(cs.a(unhandledMessage.host.toString()), ai.c, 0);
                if (a != null) {
                    a.d();
                }
            }
        } catch (Exception e) {
            ALog.e(getTag(), "onTimeOut", e, new Object[0]);
        }
    }

    public void onDataReceive(final bi biVar, final byte[] bArr, int i, final int i2) {
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new Runnable() {
            public void run() {
                if (ALog.isPrintLog(Level.I)) {
                    ALog.i(InAppConnection.this.getTag(), "onDataReceive", "type", Integer.valueOf(i2));
                }
                if (i2 == 200) {
                    try {
                        long currentTimeMillis = System.currentTimeMillis();
                        InAppConnection.this.mMessageHandler.onMessage(bArr, biVar.i());
                        ReceiveMsgStat receiveMsgStat = InAppConnection.this.mMessageHandler.getReceiveMsgStat();
                        if (receiveMsgStat != null) {
                            receiveMsgStat.receiveDate = String.valueOf(currentTimeMillis);
                            receiveMsgStat.messageType = InAppConnection.this.mConnectionType == 0 ? "service" : "inapp";
                            receiveMsgStat.commitUT();
                        }
                    } catch (Throwable th) {
                        ALog.e(InAppConnection.this.getTag(), "onDataReceive ", th, new Object[0]);
                        UTMini.getInstance().commitEvent(66001, "DATA_RECEIVE", UtilityImpl.getStackMsg(th));
                    }
                } else {
                    String tag = InAppConnection.this.getTag();
                    StringBuilder sb = new StringBuilder("drop frame len:");
                    sb.append(bArr.length);
                    sb.append(" frameType");
                    sb.append(i2);
                    ALog.e(tag, sb.toString(), new Object[0]);
                }
            }
        });
    }

    public void onException(final int i, final int i2, final boolean z, String str) {
        String tag = getTag();
        StringBuilder sb = new StringBuilder("errorId:");
        sb.append(i2);
        sb.append("detail:");
        sb.append(str);
        sb.append(" dataId:");
        sb.append(i);
        sb.append(" needRetry:");
        sb.append(z);
        ALog.e(tag, sb.toString(), new Object[0]);
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new Runnable() {
            public void run() {
                if (i > 0) {
                    Message removeUnhandledMessage = InAppConnection.this.mMessageHandler.removeUnhandledMessage(UtilityImpl.int2String(i));
                    if (removeUnhandledMessage != null) {
                        if (z) {
                            if (!InAppConnection.this.reSend(removeUnhandledMessage, 2000)) {
                                InAppConnection.this.mMessageHandler.onResult(removeUnhandledMessage, i2);
                            }
                            if (removeUnhandledMessage.getNetPermanceMonitor() != null) {
                                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_RESEND, "total_tnet", 0.0d);
                            }
                        } else {
                            InAppConnection.this.mMessageHandler.onResult(removeUnhandledMessage, i2);
                        }
                    }
                }
                if (i < 0 && z) {
                    InAppConnection.this.reSendAck(i);
                }
            }
        });
    }

    public boolean cancel(String str) {
        if (str == null) {
            return false;
        }
        ScheduledFuture scheduledFuture = (ScheduledFuture) this.mMessageHandler.reqTasks.get(str);
        boolean cancel = scheduledFuture != null ? scheduledFuture.cancel(false) : false;
        if (cancel) {
            ALog.e(getTag(), "cancel", "customDataId", str);
        }
        return cancel;
    }

    /* access modifiers changed from: protected */
    public String getTag() {
        StringBuilder sb = new StringBuilder(TAG);
        sb.append(this.mConfigTag);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void initAwcn(Context context) {
        boolean z;
        try {
            if (!this.mAwcnInited) {
                super.initAwcn(context);
                String inappHost = this.mConfig.getInappHost();
                if (!isKeepAlive() || !this.mConfig.isKeepalive()) {
                    ALog.d(getTag(), "initAwcn close keepalive", new Object[0]);
                    z = false;
                } else {
                    z = true;
                }
                registerSessionInfo(r.a(this.mConfig.getAppKey()), inappHost, z);
                this.mAwcnInited = true;
                ALog.i(getTag(), "initAwcn success!", new Object[0]);
            }
        } catch (Throwable th) {
            ALog.e(getTag(), "initAwcn", th, new Object[0]);
        }
    }

    public void registerSessionInfo(r rVar, String str, boolean z) {
        if (!this.mSessionRegistered.contains(str)) {
            rVar.a(t.a(str, z, true, new Auth(this, str), this));
            int inappPubKey = this.mConfig.getInappPubKey();
            q qVar = rVar.g;
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("host cannot be null or empty");
            }
            synchronized (qVar.a) {
                qVar.a.put(str, Integer.valueOf(inappPubKey));
            }
            this.mSessionRegistered.add(str);
            ALog.i(getTag(), "registerSessionInfo", "host", str);
        }
    }

    public void updateConfig(AccsClientConfig accsClientConfig) {
        if (accsClientConfig == null) {
            ALog.i(getTag(), "updateConfig null", new Object[0]);
        } else if (this.mConfig == null || !accsClientConfig.equals(this.mConfig)) {
            try {
                boolean z = true;
                ALog.w(getTag(), "updateConfig", "old", this.mConfig, AmapLoc.TYPE_NEW, accsClientConfig);
                String inappHost = this.mConfig.getInappHost();
                String inappHost2 = accsClientConfig.getInappHost();
                r a = r.a(this.mConfig.getAppKey());
                if (a == null) {
                    ALog.w(getTag(), "updateConfig not need update", new Object[0]);
                    return;
                }
                t remove = a.g.b.remove(inappHost);
                if (remove != null && remove.b) {
                    a.h.a();
                }
                ALog.w(getTag(), "updateConfig unregisterSessionInfo", "host", inappHost);
                if (this.mSessionRegistered.contains(inappHost)) {
                    this.mSessionRegistered.remove(inappHost);
                    ALog.w(getTag(), "updateConfig removeSessionRegistered", "oldHost", inappHost);
                }
                this.mConfig = accsClientConfig;
                this.mAppkey = this.mConfig.getAppKey();
                this.mConfigTag = this.mConfig.getTag();
                if (!isKeepAlive() || !this.mConfig.isKeepalive()) {
                    ALog.i(getTag(), "updateConfig close keepalive", new Object[0]);
                    z = false;
                }
                registerSessionInfo(a, inappHost2, z);
            } catch (Throwable th) {
                ALog.e(getTag(), "updateConfig", th, new Object[0]);
            }
        } else {
            ALog.w(getTag(), "updateConfig not any changed", new Object[0]);
        }
    }
}
