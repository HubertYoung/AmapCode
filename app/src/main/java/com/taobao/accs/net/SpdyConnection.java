package com.taobao.accs.net;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.statist.StatObject;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.ut.monitor.SessionMonitor;
import com.taobao.accs.ut.statistics.MonitorStatistic;
import com.taobao.accs.ut.statistics.ReceiveMsgStat;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.LoadSoFailUtil;
import com.taobao.accs.utl.OrangeAdapter;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.android.spdy.AccsSSLCallback;
import org.android.spdy.RequestPriority;
import org.android.spdy.SessionCb;
import org.android.spdy.SessionInfo;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyByteArray;
import org.android.spdy.SpdyDataProvider;
import org.android.spdy.SpdyRequest;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.android.spdy.Spdycb;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class SpdyConnection extends BaseConnection implements SessionCb, Spdycb {
    private static final int ACCS_CONN_TIMEOUT = 120000;
    private static final int CONN_TIMEOUT = 40000;
    private static final int DEFAULT_RETRY_TIME = 5000;
    private static final String HTTP_STATUS = ":status";
    private static final int MAX_RETRY_TIMES = 4;
    protected static final int MAX_TIMEOUT_DATA = 3;
    private static final int REQ_TIMEOUT = 80000;
    private static final String TAG = "SilenceConn_";
    private static final long nanoToMs = 1000000;
    /* access modifiers changed from: private */
    public long lastPingTime;
    /* access modifiers changed from: private */
    public long lastPingTimeNano;
    private SpdyAgent mAgent = null;
    /* access modifiers changed from: private */
    public boolean mCanUserProxy = false;
    private Object mConnLock = new Object();
    private long mConnStartTime;
    private long mConnStartTimeNano;
    protected ScheduledFuture<?> mConnTimoutFuture;
    private String mFinalUrl;
    /* access modifiers changed from: private */
    public HttpDnsProvider mHttpDnsProvider = new HttpDnsProvider(getChannelHost());
    protected String mIp;
    /* access modifiers changed from: private */
    public boolean mLastConnectFail = false;
    /* access modifiers changed from: private */
    public LinkedList<Message> mMessageList = new LinkedList<>();
    private MonitorStatistic mMonitorInfo;
    protected int mPort;
    /* access modifiers changed from: private */
    public String mProxy = "";
    protected String mProxyIp;
    protected int mProxyPort;
    /* access modifiers changed from: private */
    public boolean mRunning = true;
    /* access modifiers changed from: private */
    public SpdySession mSession = null;
    /* access modifiers changed from: private */
    public String mSessionId;
    /* access modifiers changed from: private */
    public SessionMonitor mStatistic;
    /* access modifiers changed from: private */
    public int mStatus = 3;
    private NetworkThread mThread;
    private String mUrl;
    private int sessionConnectInterval = -1;
    private String testUrl = null;

    class NetworkThread extends Thread {
        private final String TAG = getName();
        public int failTimes = 0;
        long lastConnectTime;

        public NetworkThread(String str) {
            super(str);
        }

        private void tryConnect(boolean z) {
            while (true) {
                if (SpdyConnection.this.mStatus != 1) {
                    ALog.d(SpdyConnection.this.getTag(), "tryConnect", "force", Boolean.valueOf(z));
                    if (!UtilityImpl.isNetworkConnected(SpdyConnection.this.mContext)) {
                        ALog.e(this.TAG, "Network not available", new Object[0]);
                        return;
                    }
                    if (z) {
                        this.failTimes = 0;
                    }
                    ALog.i(this.TAG, "tryConnect", "force", Boolean.valueOf(z), "failTimes", Integer.valueOf(this.failTimes));
                    if (SpdyConnection.this.mStatus == 1 || this.failTimes < 4) {
                        if (SpdyConnection.this.mStatus == 1) {
                            break;
                        }
                        if (SpdyConnection.this.mConnectionType == 1 && this.failTimes == 0) {
                            ALog.i(this.TAG, "tryConnect in app, no sleep", new Object[0]);
                        } else {
                            ALog.i(this.TAG, "tryConnect, need sleep", new Object[0]);
                            try {
                                sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        SpdyConnection.this.mProxy = "";
                        if (this.failTimes == 3) {
                            SpdyConnection.this.mHttpDnsProvider.forceUpdateStrategy(SpdyConnection.this.getChannelHost());
                        }
                        SpdyConnection.this.connect(null);
                        SpdyConnection.this.mStatistic.setRetryTimes(this.failTimes);
                        if (SpdyConnection.this.mStatus != 1) {
                            this.failTimes++;
                            ALog.e(this.TAG, "try connect fail, ready for reconnect", new Object[0]);
                            z = false;
                        } else {
                            this.lastConnectTime = System.currentTimeMillis();
                            return;
                        }
                    } else {
                        SpdyConnection.this.mCanUserProxy = true;
                        ALog.e(this.TAG, "tryConnect fail", "maxTimes", Integer.valueOf(4));
                        return;
                    }
                } else if (SpdyConnection.this.mStatus == 1 && System.currentTimeMillis() - this.lastConnectTime > 5000) {
                    this.failTimes = 0;
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:207:0x04d1, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:211:?, code lost:
            throw r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:212:0x04d5, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:220:0x04f3, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:224:?, code lost:
            throw r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:225:0x04f7, code lost:
            com.taobao.accs.utl.ALog.e(r1.TAG, " run finally error", r0, new java.lang.Object[0]);
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [B:183:0x0455, B:189:0x0474, B:216:0x04e8] */
        /* JADX WARNING: Removed duplicated region for block: B:116:0x0346 A[Catch:{ all -> 0x0442, all -> 0x041f, all -> 0x0362, all -> 0x0342, all -> 0x00ed, Throwable -> 0x0423 }] */
        /* JADX WARNING: Removed duplicated region for block: B:139:0x03a6 A[SYNTHETIC, Splitter:B:139:0x03a6] */
        /* JADX WARNING: Removed duplicated region for block: B:168:0x0426 A[Catch:{ all -> 0x0442, all -> 0x041f, all -> 0x0362, all -> 0x0342, all -> 0x00ed, Throwable -> 0x0423 }] */
        /* JADX WARNING: Removed duplicated region for block: B:183:0x0455 A[SYNTHETIC, Splitter:B:183:0x0455] */
        /* JADX WARNING: Removed duplicated region for block: B:214:0x04d8 A[Catch:{ all -> 0x04f3, all -> 0x04d1, Throwable -> 0x04d5 }] */
        /* JADX WARNING: Removed duplicated region for block: B:89:0x02c9 A[SYNTHETIC, Splitter:B:89:0x02c9] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r23 = this;
                r1 = r23
                java.lang.String r2 = r1.TAG
                java.lang.String r3 = "NetworkThread run"
                r4 = 0
                java.lang.Object[] r5 = new java.lang.Object[r4]
                com.taobao.accs.utl.ALog.i(r2, r3, r5)
                r1.failTimes = r4
                r2 = 0
            L_0x000f:
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this
                boolean r3 = r3.mRunning
                if (r3 == 0) goto L_0x0505
                java.lang.String r3 = r1.TAG
                java.lang.String r5 = "ready to get message"
                java.lang.Object[] r6 = new java.lang.Object[r4]
                com.taobao.accs.utl.ALog.d(r3, r5, r6)
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this
                java.util.LinkedList r3 = r3.mMessageList
                monitor-enter(r3)
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0501 }
                java.util.LinkedList r5 = r5.mMessageList     // Catch:{ all -> 0x0501 }
                int r5 = r5.size()     // Catch:{ all -> 0x0501 }
                if (r5 != 0) goto L_0x004e
                java.lang.String r5 = r1.TAG     // Catch:{ InterruptedException -> 0x0046 }
                java.lang.String r6 = "no message, wait"
                java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ InterruptedException -> 0x0046 }
                com.taobao.accs.utl.ALog.d(r5, r6, r7)     // Catch:{ InterruptedException -> 0x0046 }
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ InterruptedException -> 0x0046 }
                java.util.LinkedList r5 = r5.mMessageList     // Catch:{ InterruptedException -> 0x0046 }
                r5.wait()     // Catch:{ InterruptedException -> 0x0046 }
                goto L_0x004e
            L_0x0046:
                r0 = move-exception
                r2 = r0
                r2.printStackTrace()     // Catch:{ all -> 0x0501 }
                monitor-exit(r3)     // Catch:{ all -> 0x0501 }
                goto L_0x0505
            L_0x004e:
                java.lang.String r5 = r1.TAG     // Catch:{ all -> 0x0501 }
                java.lang.String r6 = "try get message"
                java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x0501 }
                com.taobao.accs.utl.ALog.d(r5, r6, r7)     // Catch:{ all -> 0x0501 }
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0501 }
                java.util.LinkedList r5 = r5.mMessageList     // Catch:{ all -> 0x0501 }
                int r5 = r5.size()     // Catch:{ all -> 0x0501 }
                if (r5 == 0) goto L_0x007d
                com.taobao.accs.net.SpdyConnection r2 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0501 }
                java.util.LinkedList r2 = r2.mMessageList     // Catch:{ all -> 0x0501 }
                java.lang.Object r2 = r2.getFirst()     // Catch:{ all -> 0x0501 }
                com.taobao.accs.data.Message r2 = (com.taobao.accs.data.Message) r2     // Catch:{ all -> 0x0501 }
                com.taobao.accs.ut.monitor.NetPerformanceMonitor r5 = r2.getNetPermanceMonitor()     // Catch:{ all -> 0x0501 }
                if (r5 == 0) goto L_0x007d
                com.taobao.accs.ut.monitor.NetPerformanceMonitor r5 = r2.getNetPermanceMonitor()     // Catch:{ all -> 0x0501 }
                r5.onTakeFromQueue()     // Catch:{ all -> 0x0501 }
            L_0x007d:
                monitor-exit(r3)     // Catch:{ all -> 0x0501 }
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this
                boolean r3 = r3.mRunning
                if (r3 == 0) goto L_0x0505
                if (r2 == 0) goto L_0x000f
                java.lang.String r3 = r1.TAG
                java.lang.String r5 = "sendMessage not null"
                java.lang.Object[] r6 = new java.lang.Object[r4]
                com.taobao.accs.utl.ALog.d(r3, r5, r6)
                r3 = 201(0xc9, float:2.82E-43)
                r7 = 1
                int r8 = r2.getType()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r9 = r1.TAG     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r10 = "sendMessage"
                r11 = 4
                java.lang.Object[] r12 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r13 = "type"
                r12[r4] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r13 = com.taobao.accs.data.Message.MsgType.name(r8)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r12[r7] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r13 = "status"
                r14 = 2
                r12[r14] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r13 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r13 = r13.mStatus     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r15 = 3
                r12[r15] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.utl.ALog.i(r9, r10, r12)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r8 != r14) goto L_0x01b5
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r8 = r8.mConnectionType     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r8 != r7) goto L_0x00f1
                java.lang.String r8 = r1.TAG     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r9 = "sendMessage INAPP ping, skip"
                java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.utl.ALog.d(r8, r9, r10)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r3 = r1.TAG     // Catch:{ Throwable -> 0x0423 }
                java.lang.String r5 = "send succ, remove it"
                java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.utl.ALog.d(r3, r5, r6)     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                java.util.LinkedList r3 = r3.mMessageList     // Catch:{ Throwable -> 0x0423 }
                monitor-enter(r3)     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x00ed }
                java.util.LinkedList r5 = r5.mMessageList     // Catch:{ all -> 0x00ed }
                r5.remove(r2)     // Catch:{ all -> 0x00ed }
                monitor-exit(r3)     // Catch:{ all -> 0x00ed }
                goto L_0x000f
            L_0x00ed:
                r0 = move-exception
                r5 = r0
                monitor-exit(r3)     // Catch:{ all -> 0x00ed }
                throw r5     // Catch:{ Throwable -> 0x0423 }
            L_0x00f1:
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r10 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                long r12 = r10.lastPingTime     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r10 = 0
                long r8 = r8 - r12
                com.taobao.accs.net.SpdyConnection r10 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                android.content.Context r10 = r10.mContext     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.HeartbeatManager r10 = com.taobao.accs.net.HeartbeatManager.getInstance(r10)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r10 = r10.getInterval()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r10 = r10 - r7
                int r10 = r10 * 1000
                long r12 = (long) r10     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r8 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
                if (r8 >= 0) goto L_0x011b
                boolean r8 = r2.force     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r8 == 0) goto L_0x0116
                goto L_0x011b
            L_0x0116:
                r1.tryConnect(r4)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                goto L_0x02c1
            L_0x011b:
                java.lang.String r8 = r1.TAG     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r9 = "sendMessage"
                java.lang.Object[] r10 = new java.lang.Object[r11]     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r11 = "force"
                r10[r4] = r11     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                boolean r11 = r2.force     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.Boolean r11 = java.lang.Boolean.valueOf(r11)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r10[r7] = r11     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r11 = "last ping"
                r10[r14] = r11     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                long r11 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r13 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                long r13 = r13.lastPingTime     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r16 = 0
                long r11 = r11 - r13
                java.lang.Long r11 = java.lang.Long.valueOf(r11)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r10[r15] = r11     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.utl.ALog.d(r8, r9, r10)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r1.tryConnect(r7)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                org.android.spdy.SpdySession r8 = r8.mSession     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r8 == 0) goto L_0x02a8
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r8 = r8.mStatus     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r8 != r7) goto L_0x02a8
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r10 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                long r10 = r10.lastPingTime     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r12 = 0
                long r8 = r8 - r10
                com.taobao.accs.net.SpdyConnection r10 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                android.content.Context r10 = r10.mContext     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.HeartbeatManager r10 = com.taobao.accs.net.HeartbeatManager.getInstance(r10)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r10 = r10.getInterval()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r10 = r10 - r7
                int r10 = r10 * 1000
                long r10 = (long) r10     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
                if (r8 < 0) goto L_0x02c1
                java.lang.String r8 = r1.TAG     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r9 = "sendMessage onSendPing"
                java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.utl.ALog.i(r8, r9, r10)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.data.MessageHandler r8 = r8.mMessageHandler     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r8.onSendPing()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                org.android.spdy.SpdySession r8 = r8.mSession     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r8.submitPing()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.ut.monitor.SessionMonitor r8 = r8.mStatistic     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r8.onSendPing()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                long r9 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r8.lastPingTime = r9     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                long r9 = java.lang.System.nanoTime()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r8.lastPingTimeNano = r9     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r8.setPingTimeOut()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                goto L_0x02c1
            L_0x01b5:
                if (r8 != r7) goto L_0x02aa
                r1.tryConnect(r7)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r8 = r8.mStatus     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r8 != r7) goto L_0x02a8
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                org.android.spdy.SpdySession r8 = r8.mSession     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r8 == 0) goto L_0x02a8
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                android.content.Context r8 = r8.mContext     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r9 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r9 = r9.mConnectionType     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                byte[] r8 = r2.build(r8, r9)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                long r9 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r2.setSendTime(r9)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r9 = r8.length     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r10 = 16384(0x4000, float:2.2959E-41)
                if (r9 <= r10) goto L_0x01f6
                java.lang.Integer r9 = r2.command     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r9 = r9.intValue()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r10 = 102(0x66, float:1.43E-43)
                if (r9 == r10) goto L_0x01f6
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.data.MessageHandler r8 = r8.mMessageHandler     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r9 = -4
                r8.onResult(r2, r9)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                goto L_0x02c1
            L_0x01f6:
                com.taobao.accs.net.SpdyConnection r9 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                org.android.spdy.SpdySession r16 = r9.mSession     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r17 = r2.getIntDataId()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r18 = 200(0xc8, float:2.8E-43)
                r19 = 0
                if (r8 != 0) goto L_0x0209
                r20 = 0
                goto L_0x020c
            L_0x0209:
                int r9 = r8.length     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r20 = r9
            L_0x020c:
                r21 = r8
                r16.sendCustomControlFrame(r17, r18, r19, r20, r21)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r9 = r1.TAG     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r10 = "send data"
                r12 = 6
                java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r13 = "length"
                r12[r4] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r8 != 0) goto L_0x0220
                r13 = 0
                goto L_0x0221
            L_0x0220:
                int r13 = r8.length     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
            L_0x0221:
                java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r12[r7] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r13 = "dataId"
                r12[r14] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r13 = r2.getDataId()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r12[r15] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r13 = "utdid"
                r12[r11] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r11 = 5
                com.taobao.accs.net.SpdyConnection r13 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r13 = r13.mUtdid     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r12[r11] = r13     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.utl.ALog.e(r9, r10, r12)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r9 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.data.MessageHandler r9 = r9.mMessageHandler     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r9.onSend(r2)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                boolean r9 = r2.isAck     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r9 == 0) goto L_0x0271
                java.lang.String r9 = r1.TAG     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r10 = "sendCFrame end ack"
                java.lang.Object[] r11 = new java.lang.Object[r14]     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r12 = "dataId"
                r11[r4] = r12     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r12 = r2.getIntDataId()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.Integer r12 = java.lang.Integer.valueOf(r12)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r11[r7] = r12     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.utl.ALog.e(r9, r10, r11)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r9 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.util.LinkedHashMap r9 = r9.mAckMessage     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r10 = r2.getIntDataId()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r9.put(r10, r2)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
            L_0x0271:
                com.taobao.accs.ut.monitor.NetPerformanceMonitor r9 = r2.getNetPermanceMonitor()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                if (r9 == 0) goto L_0x027e
                com.taobao.accs.ut.monitor.NetPerformanceMonitor r9 = r2.getNetPermanceMonitor()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r9.onSendData()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
            L_0x027e:
                com.taobao.accs.net.SpdyConnection r9 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r10 = r2.getDataId()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r11 = r2.timeout     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                long r11 = (long) r11     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r9.setTimeOut(r10, r11)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r9 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.data.MessageHandler r9 = r9.mMessageHandler     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo r14 = new com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r11 = r2.serviceId     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                boolean r12 = defpackage.m.h()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.net.SpdyConnection r10 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r13 = r10.getChannelHost()     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                int r8 = r8.length     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                long r5 = (long) r8     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r10 = r14
                r8 = r14
                r14 = r5
                r10.<init>(r11, r12, r13, r14)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r9.addTrafficsInfo(r8)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                goto L_0x02c1
            L_0x02a8:
                r5 = 0
                goto L_0x02c2
            L_0x02aa:
                r1.tryConnect(r4)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r5 = r1.TAG     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r6 = "skip msg"
                java.lang.Object[] r9 = new java.lang.Object[r14]     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.String r10 = "type"
                r9[r4] = r10     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                r9[r7] = r8     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
                com.taobao.accs.utl.ALog.e(r5, r6, r9)     // Catch:{ Throwable -> 0x0373, all -> 0x036e }
            L_0x02c1:
                r5 = 1
            L_0x02c2:
                com.taobao.accs.net.SpdyConnection r6 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x036a, all -> 0x0366 }
                r6.setHeartbeat(r7)     // Catch:{ Throwable -> 0x036a, all -> 0x0366 }
                if (r5 != 0) goto L_0x0346
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                r5.close()     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.ut.monitor.SessionMonitor r5 = r5.mStatistic     // Catch:{ Throwable -> 0x0423 }
                if (r5 == 0) goto L_0x02e1
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.ut.monitor.SessionMonitor r5 = r5.mStatistic     // Catch:{ Throwable -> 0x0423 }
                java.lang.String r6 = "send fail"
                r5.setCloseReason(r6)     // Catch:{ Throwable -> 0x0423 }
            L_0x02e1:
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                java.util.LinkedList r5 = r5.mMessageList     // Catch:{ Throwable -> 0x0423 }
                monitor-enter(r5)     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r6 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0342 }
                java.util.LinkedList r6 = r6.mMessageList     // Catch:{ all -> 0x0342 }
                int r6 = r6.size()     // Catch:{ all -> 0x0342 }
                int r6 = r6 - r7
            L_0x02f3:
                if (r6 < 0) goto L_0x032d
                com.taobao.accs.net.SpdyConnection r7 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0342 }
                java.util.LinkedList r7 = r7.mMessageList     // Catch:{ all -> 0x0342 }
                java.lang.Object r7 = r7.get(r6)     // Catch:{ all -> 0x0342 }
                com.taobao.accs.data.Message r7 = (com.taobao.accs.data.Message) r7     // Catch:{ all -> 0x0342 }
                if (r7 == 0) goto L_0x032a
                java.lang.Integer r8 = r7.command     // Catch:{ all -> 0x0342 }
                if (r8 == 0) goto L_0x032a
                java.lang.Integer r8 = r7.command     // Catch:{ all -> 0x0342 }
                int r8 = r8.intValue()     // Catch:{ all -> 0x0342 }
                r9 = 100
                if (r8 == r9) goto L_0x0319
                java.lang.Integer r8 = r7.command     // Catch:{ all -> 0x0342 }
                int r8 = r8.intValue()     // Catch:{ all -> 0x0342 }
                if (r8 != r3) goto L_0x032a
            L_0x0319:
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0342 }
                com.taobao.accs.data.MessageHandler r8 = r8.mMessageHandler     // Catch:{ all -> 0x0342 }
                r9 = -1
                r8.onResult(r7, r9)     // Catch:{ all -> 0x0342 }
                com.taobao.accs.net.SpdyConnection r7 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0342 }
                java.util.LinkedList r7 = r7.mMessageList     // Catch:{ all -> 0x0342 }
                r7.remove(r6)     // Catch:{ all -> 0x0342 }
            L_0x032a:
                int r6 = r6 + -1
                goto L_0x02f3
            L_0x032d:
                java.lang.String r3 = r1.TAG     // Catch:{ all -> 0x0342 }
                java.lang.String r6 = "network disconnected, wait"
                java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x0342 }
                com.taobao.accs.utl.ALog.e(r3, r6, r7)     // Catch:{ all -> 0x0342 }
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0342 }
                java.util.LinkedList r3 = r3.mMessageList     // Catch:{ all -> 0x0342 }
                r3.wait()     // Catch:{ all -> 0x0342 }
                monitor-exit(r5)     // Catch:{ all -> 0x0342 }
                goto L_0x000f
            L_0x0342:
                r0 = move-exception
                r3 = r0
                monitor-exit(r5)     // Catch:{ all -> 0x0342 }
                throw r3     // Catch:{ Throwable -> 0x0423 }
            L_0x0346:
                java.lang.String r3 = r1.TAG     // Catch:{ Throwable -> 0x0423 }
                java.lang.String r5 = "send succ, remove it"
                java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.utl.ALog.d(r3, r5, r6)     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                java.util.LinkedList r3 = r3.mMessageList     // Catch:{ Throwable -> 0x0423 }
                monitor-enter(r3)     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0362 }
                java.util.LinkedList r5 = r5.mMessageList     // Catch:{ all -> 0x0362 }
                r5.remove(r2)     // Catch:{ all -> 0x0362 }
                monitor-exit(r3)     // Catch:{ all -> 0x0362 }
                goto L_0x000f
            L_0x0362:
                r0 = move-exception
                r5 = r0
                monitor-exit(r3)     // Catch:{ all -> 0x0362 }
                throw r5     // Catch:{ Throwable -> 0x0423 }
            L_0x0366:
                r0 = move-exception
                r6 = r5
                goto L_0x0452
            L_0x036a:
                r0 = move-exception
                r6 = r5
                r5 = r0
                goto L_0x0376
            L_0x036e:
                r0 = move-exception
                r5 = r0
                r6 = 1
                goto L_0x0453
            L_0x0373:
                r0 = move-exception
                r5 = r0
                r6 = 1
            L_0x0376:
                java.lang.String r8 = "accs"
                java.lang.String r9 = "send_fail"
                java.lang.String r10 = r2.serviceId     // Catch:{ all -> 0x0451 }
                java.lang.String r11 = "1"
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0451 }
                r12.<init>()     // Catch:{ all -> 0x0451 }
                com.taobao.accs.net.SpdyConnection r13 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0451 }
                int r13 = r13.mConnectionType     // Catch:{ all -> 0x0451 }
                r12.append(r13)     // Catch:{ all -> 0x0451 }
                java.lang.String r13 = r5.toString()     // Catch:{ all -> 0x0451 }
                r12.append(r13)     // Catch:{ all -> 0x0451 }
                java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x0451 }
                com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0451 }
                r5.printStackTrace()     // Catch:{ all -> 0x0451 }
                java.lang.String r8 = r1.TAG     // Catch:{ all -> 0x0451 }
                java.lang.String r9 = "service connection run"
                java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ all -> 0x0451 }
                com.taobao.accs.utl.ALog.e(r8, r9, r5, r10)     // Catch:{ all -> 0x0451 }
                if (r6 != 0) goto L_0x0426
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                r5.close()     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.ut.monitor.SessionMonitor r5 = r5.mStatistic     // Catch:{ Throwable -> 0x0423 }
                if (r5 == 0) goto L_0x03be
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.ut.monitor.SessionMonitor r5 = r5.mStatistic     // Catch:{ Throwable -> 0x0423 }
                java.lang.String r6 = "send fail"
                r5.setCloseReason(r6)     // Catch:{ Throwable -> 0x0423 }
            L_0x03be:
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                java.util.LinkedList r5 = r5.mMessageList     // Catch:{ Throwable -> 0x0423 }
                monitor-enter(r5)     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r6 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x041f }
                java.util.LinkedList r6 = r6.mMessageList     // Catch:{ all -> 0x041f }
                int r6 = r6.size()     // Catch:{ all -> 0x041f }
                int r6 = r6 - r7
            L_0x03d0:
                if (r6 < 0) goto L_0x040a
                com.taobao.accs.net.SpdyConnection r7 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x041f }
                java.util.LinkedList r7 = r7.mMessageList     // Catch:{ all -> 0x041f }
                java.lang.Object r7 = r7.get(r6)     // Catch:{ all -> 0x041f }
                com.taobao.accs.data.Message r7 = (com.taobao.accs.data.Message) r7     // Catch:{ all -> 0x041f }
                if (r7 == 0) goto L_0x0407
                java.lang.Integer r8 = r7.command     // Catch:{ all -> 0x041f }
                if (r8 == 0) goto L_0x0407
                java.lang.Integer r8 = r7.command     // Catch:{ all -> 0x041f }
                int r8 = r8.intValue()     // Catch:{ all -> 0x041f }
                r9 = 100
                if (r8 == r9) goto L_0x03f6
                java.lang.Integer r8 = r7.command     // Catch:{ all -> 0x041f }
                int r8 = r8.intValue()     // Catch:{ all -> 0x041f }
                if (r8 != r3) goto L_0x0407
            L_0x03f6:
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x041f }
                com.taobao.accs.data.MessageHandler r8 = r8.mMessageHandler     // Catch:{ all -> 0x041f }
                r9 = -1
                r8.onResult(r7, r9)     // Catch:{ all -> 0x041f }
                com.taobao.accs.net.SpdyConnection r7 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x041f }
                java.util.LinkedList r7 = r7.mMessageList     // Catch:{ all -> 0x041f }
                r7.remove(r6)     // Catch:{ all -> 0x041f }
            L_0x0407:
                int r6 = r6 + -1
                goto L_0x03d0
            L_0x040a:
                java.lang.String r3 = r1.TAG     // Catch:{ all -> 0x041f }
                java.lang.String r6 = "network disconnected, wait"
                java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x041f }
                com.taobao.accs.utl.ALog.e(r3, r6, r7)     // Catch:{ all -> 0x041f }
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x041f }
                java.util.LinkedList r3 = r3.mMessageList     // Catch:{ all -> 0x041f }
                r3.wait()     // Catch:{ all -> 0x041f }
                monitor-exit(r5)     // Catch:{ all -> 0x041f }
                goto L_0x000f
            L_0x041f:
                r0 = move-exception
                r3 = r0
                monitor-exit(r5)     // Catch:{ all -> 0x041f }
                throw r3     // Catch:{ Throwable -> 0x0423 }
            L_0x0423:
                r0 = move-exception
                r3 = r0
                goto L_0x0446
            L_0x0426:
                java.lang.String r3 = r1.TAG     // Catch:{ Throwable -> 0x0423 }
                java.lang.String r5 = "send succ, remove it"
                java.lang.Object[] r6 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.utl.ALog.d(r3, r5, r6)     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x0423 }
                java.util.LinkedList r3 = r3.mMessageList     // Catch:{ Throwable -> 0x0423 }
                monitor-enter(r3)     // Catch:{ Throwable -> 0x0423 }
                com.taobao.accs.net.SpdyConnection r5 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x0442 }
                java.util.LinkedList r5 = r5.mMessageList     // Catch:{ all -> 0x0442 }
                r5.remove(r2)     // Catch:{ all -> 0x0442 }
                monitor-exit(r3)     // Catch:{ all -> 0x0442 }
                goto L_0x000f
            L_0x0442:
                r0 = move-exception
                r5 = r0
                monitor-exit(r3)     // Catch:{ all -> 0x0442 }
                throw r5     // Catch:{ Throwable -> 0x0423 }
            L_0x0446:
                java.lang.String r5 = r1.TAG
                java.lang.String r6 = " run finally error"
                java.lang.Object[] r7 = new java.lang.Object[r4]
                com.taobao.accs.utl.ALog.e(r5, r6, r3, r7)
                goto L_0x000f
            L_0x0451:
                r0 = move-exception
            L_0x0452:
                r5 = r0
            L_0x0453:
                if (r6 != 0) goto L_0x04d8
                com.taobao.accs.net.SpdyConnection r2 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x04d5 }
                r2.close()     // Catch:{ Throwable -> 0x04d5 }
                com.taobao.accs.net.SpdyConnection r2 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x04d5 }
                com.taobao.accs.ut.monitor.SessionMonitor r2 = r2.mStatistic     // Catch:{ Throwable -> 0x04d5 }
                if (r2 == 0) goto L_0x046d
                com.taobao.accs.net.SpdyConnection r2 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x04d5 }
                com.taobao.accs.ut.monitor.SessionMonitor r2 = r2.mStatistic     // Catch:{ Throwable -> 0x04d5 }
                java.lang.String r6 = "send fail"
                r2.setCloseReason(r6)     // Catch:{ Throwable -> 0x04d5 }
            L_0x046d:
                com.taobao.accs.net.SpdyConnection r2 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x04d5 }
                java.util.LinkedList r2 = r2.mMessageList     // Catch:{ Throwable -> 0x04d5 }
                monitor-enter(r2)     // Catch:{ Throwable -> 0x04d5 }
                com.taobao.accs.net.SpdyConnection r6 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x04d1 }
                java.util.LinkedList r6 = r6.mMessageList     // Catch:{ all -> 0x04d1 }
                int r6 = r6.size()     // Catch:{ all -> 0x04d1 }
                int r6 = r6 - r7
            L_0x047f:
                if (r6 < 0) goto L_0x04bd
                com.taobao.accs.net.SpdyConnection r7 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x04d1 }
                java.util.LinkedList r7 = r7.mMessageList     // Catch:{ all -> 0x04d1 }
                java.lang.Object r7 = r7.get(r6)     // Catch:{ all -> 0x04d1 }
                com.taobao.accs.data.Message r7 = (com.taobao.accs.data.Message) r7     // Catch:{ all -> 0x04d1 }
                if (r7 == 0) goto L_0x04b7
                java.lang.Integer r8 = r7.command     // Catch:{ all -> 0x04d1 }
                if (r8 == 0) goto L_0x04b7
                java.lang.Integer r8 = r7.command     // Catch:{ all -> 0x04d1 }
                int r8 = r8.intValue()     // Catch:{ all -> 0x04d1 }
                r9 = 100
                if (r8 == r9) goto L_0x04a5
                java.lang.Integer r8 = r7.command     // Catch:{ all -> 0x04d1 }
                int r8 = r8.intValue()     // Catch:{ all -> 0x04d1 }
                if (r8 != r3) goto L_0x04b9
            L_0x04a5:
                com.taobao.accs.net.SpdyConnection r8 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x04d1 }
                com.taobao.accs.data.MessageHandler r8 = r8.mMessageHandler     // Catch:{ all -> 0x04d1 }
                r10 = -1
                r8.onResult(r7, r10)     // Catch:{ all -> 0x04d1 }
                com.taobao.accs.net.SpdyConnection r7 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x04d1 }
                java.util.LinkedList r7 = r7.mMessageList     // Catch:{ all -> 0x04d1 }
                r7.remove(r6)     // Catch:{ all -> 0x04d1 }
                goto L_0x04ba
            L_0x04b7:
                r9 = 100
            L_0x04b9:
                r10 = -1
            L_0x04ba:
                int r6 = r6 + -1
                goto L_0x047f
            L_0x04bd:
                java.lang.String r3 = r1.TAG     // Catch:{ all -> 0x04d1 }
                java.lang.String r6 = "network disconnected, wait"
                java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ all -> 0x04d1 }
                com.taobao.accs.utl.ALog.e(r3, r6, r7)     // Catch:{ all -> 0x04d1 }
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x04d1 }
                java.util.LinkedList r3 = r3.mMessageList     // Catch:{ all -> 0x04d1 }
                r3.wait()     // Catch:{ all -> 0x04d1 }
                monitor-exit(r2)     // Catch:{ all -> 0x04d1 }
                goto L_0x0500
            L_0x04d1:
                r0 = move-exception
                r3 = r0
                monitor-exit(r2)     // Catch:{ all -> 0x04d1 }
                throw r3     // Catch:{ Throwable -> 0x04d5 }
            L_0x04d5:
                r0 = move-exception
                r2 = r0
                goto L_0x04f7
            L_0x04d8:
                java.lang.String r3 = r1.TAG     // Catch:{ Throwable -> 0x04d5 }
                java.lang.String r6 = "send succ, remove it"
                java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x04d5 }
                com.taobao.accs.utl.ALog.d(r3, r6, r7)     // Catch:{ Throwable -> 0x04d5 }
                com.taobao.accs.net.SpdyConnection r3 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Throwable -> 0x04d5 }
                java.util.LinkedList r3 = r3.mMessageList     // Catch:{ Throwable -> 0x04d5 }
                monitor-enter(r3)     // Catch:{ Throwable -> 0x04d5 }
                com.taobao.accs.net.SpdyConnection r6 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ all -> 0x04f3 }
                java.util.LinkedList r6 = r6.mMessageList     // Catch:{ all -> 0x04f3 }
                r6.remove(r2)     // Catch:{ all -> 0x04f3 }
                monitor-exit(r3)     // Catch:{ all -> 0x04f3 }
                goto L_0x0500
            L_0x04f3:
                r0 = move-exception
                r2 = r0
                monitor-exit(r3)     // Catch:{ all -> 0x04f3 }
                throw r2     // Catch:{ Throwable -> 0x04d5 }
            L_0x04f7:
                java.lang.String r3 = r1.TAG
                java.lang.String r6 = " run finally error"
                java.lang.Object[] r4 = new java.lang.Object[r4]
                com.taobao.accs.utl.ALog.e(r3, r6, r2, r4)
            L_0x0500:
                throw r5
            L_0x0501:
                r0 = move-exception
                r2 = r0
                monitor-exit(r3)     // Catch:{ all -> 0x0501 }
                throw r2
            L_0x0505:
                com.taobao.accs.net.SpdyConnection r2 = com.taobao.accs.net.SpdyConnection.this
                r2.close()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.SpdyConnection.NetworkThread.run():void");
        }
    }

    /* access modifiers changed from: protected */
    public int getMaxTimeOutData() {
        return 3;
    }

    /* access modifiers changed from: protected */
    public boolean isKeepAlive() {
        return false;
    }

    public SpdyConnection(Context context, int i, String str) {
        super(context, i, str);
        initClient();
    }

    public void start() {
        this.mRunning = true;
        ALog.d(getTag(), H5PageData.KEY_UC_START, new Object[0]);
        initAwcn(this.mContext);
        if (this.mThread == null) {
            ALog.i(getTag(), "start thread", new Object[0]);
            StringBuilder sb = new StringBuilder("NetworkThread_");
            sb.append(this.mConfigTag);
            this.mThread = new NetworkThread(sb.toString());
            this.mThread.setPriority(2);
            this.mThread.start();
        }
        ping(false, false);
    }

    /* access modifiers changed from: protected */
    public void sendMessage(final Message message, final boolean z) {
        if (!this.mRunning || message == null) {
            String tag = getTag();
            StringBuilder sb = new StringBuilder("not running or msg null! ");
            sb.append(this.mRunning);
            ALog.e(tag, sb.toString(), new Object[0]);
            return;
        }
        try {
            if (ThreadPoolExecutorFactory.getScheduledExecutor().getQueue().size() > 1000) {
                throw new RejectedExecutionException("accs");
            }
            ScheduledFuture<?> schedule = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new Runnable() {
                public void run() {
                    synchronized (SpdyConnection.this.mMessageList) {
                        SpdyConnection.this.clearRepeatControlCommand(message);
                        if (SpdyConnection.this.mMessageList.size() == 0) {
                            SpdyConnection.this.mMessageList.add(message);
                        } else {
                            Message message = (Message) SpdyConnection.this.mMessageList.getFirst();
                            if (message.getType() != 1) {
                                if (message.getType() != 0) {
                                    if (message.getType() != 2 || message.getType() != 2) {
                                        SpdyConnection.this.mMessageList.addLast(message);
                                    } else if (!message.force && message.force) {
                                        SpdyConnection.this.mMessageList.removeFirst();
                                        SpdyConnection.this.mMessageList.addFirst(message);
                                    }
                                }
                            }
                            SpdyConnection.this.mMessageList.addLast(message);
                            if (message.getType() == 2) {
                                SpdyConnection.this.mMessageList.removeFirst();
                            }
                        }
                        if (z || SpdyConnection.this.mStatus == 3) {
                            try {
                                SpdyConnection.this.mMessageList.notifyAll();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }, message.delyTime, TimeUnit.MILLISECONDS);
            if (message.getType() == 1 && message.cunstomDataId != null) {
                if (message.isControlFrame()) {
                    cancel(message.cunstomDataId);
                }
                this.mMessageHandler.reqTasks.put(message.cunstomDataId, schedule);
            }
            if (message.getNetPermanceMonitor() != null) {
                message.getNetPermanceMonitor().setDeviceId(UtilityImpl.getDeviceId(this.mContext));
                message.getNetPermanceMonitor().setConnType(this.mConnectionType);
                message.getNetPermanceMonitor().onEnterQueueData();
            }
        } catch (RejectedExecutionException unused) {
            this.mMessageHandler.onResult(message, ErrorCode.MESSAGE_QUEUE_FULL);
            String tag2 = getTag();
            StringBuilder sb2 = new StringBuilder("send queue full count:");
            sb2.append(ThreadPoolExecutorFactory.getScheduledExecutor().getQueue().size());
            ALog.e(tag2, sb2.toString(), new Object[0]);
        } catch (Throwable th) {
            this.mMessageHandler.onResult(message, -8);
            ALog.e(getTag(), "send error", th, new Object[0]);
        }
    }

    public void shutdown() {
        super.shutdown();
        this.mRunning = false;
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new Runnable() {
            /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
            /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x002b */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r2 = this;
                    com.taobao.accs.net.SpdyConnection r0 = com.taobao.accs.net.SpdyConnection.this
                    r0.close()
                    com.taobao.accs.net.SpdyConnection r0 = com.taobao.accs.net.SpdyConnection.this
                    com.taobao.accs.ut.monitor.SessionMonitor r0 = r0.mStatistic
                    if (r0 == 0) goto L_0x0018
                    com.taobao.accs.net.SpdyConnection r0 = com.taobao.accs.net.SpdyConnection.this
                    com.taobao.accs.ut.monitor.SessionMonitor r0 = r0.mStatistic
                    java.lang.String r1 = "shut down"
                    r0.setCloseReason(r1)
                L_0x0018:
                    com.taobao.accs.net.SpdyConnection r0 = com.taobao.accs.net.SpdyConnection.this
                    java.util.LinkedList r0 = r0.mMessageList
                    monitor-enter(r0)
                    com.taobao.accs.net.SpdyConnection r1 = com.taobao.accs.net.SpdyConnection.this     // Catch:{ Exception -> 0x002b }
                    java.util.LinkedList r1 = r1.mMessageList     // Catch:{ Exception -> 0x002b }
                    r1.notifyAll()     // Catch:{ Exception -> 0x002b }
                    goto L_0x002b
                L_0x0029:
                    r1 = move-exception
                    goto L_0x002d
                L_0x002b:
                    monitor-exit(r0)     // Catch:{ all -> 0x0029 }
                    return
                L_0x002d:
                    monitor-exit(r0)     // Catch:{ all -> 0x0029 }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.SpdyConnection.AnonymousClass2.run():void");
            }
        });
        ALog.e(getTag(), "shut down", new Object[0]);
    }

    public void ping(boolean z, boolean z2) {
        ALog.d(getTag(), "try ping, force:".concat(String.valueOf(z)), new Object[0]);
        if (this.mConnectionType == 1) {
            ALog.d(getTag(), "INAPP, skip", new Object[0]);
        } else {
            send(Message.BuildPing(z, (int) (z2 ? Math.random() * 10.0d * 1000.0d : 0.0d)), z);
        }
    }

    public int getChannelState() {
        return this.mStatus;
    }

    public void close() {
        ALog.e(getTag(), " force close!", new Object[0]);
        try {
            this.mSession.closeSession();
            this.mStatistic.setCloseType(1);
        } catch (Exception unused) {
        }
        notifyStatus(3);
    }

    public MonitorStatistic updateMonitorInfo() {
        if (this.mMonitorInfo == null) {
            this.mMonitorInfo = new MonitorStatistic();
        }
        this.mMonitorInfo.connType = this.mConnectionType;
        this.mMonitorInfo.messageNum = this.mMessageList.size();
        this.mMonitorInfo.networkAvailable = UtilityImpl.isNetworkConnected(this.mContext);
        this.mMonitorInfo.proxy = this.mProxy;
        this.mMonitorInfo.status = this.mStatus;
        int i = 0;
        this.mMonitorInfo.tcpConnected = this.mStatistic == null ? false : this.mStatistic.getRet();
        this.mMonitorInfo.threadIsalive = isAlive();
        MonitorStatistic monitorStatistic = this.mMonitorInfo;
        if (this.mMessageHandler != null) {
            i = this.mMessageHandler.getUnhandledCount();
        }
        monitorStatistic.unHandleMessageNum = i;
        this.mMonitorInfo.url = this.mFinalUrl;
        return this.mMonitorInfo;
    }

    /* access modifiers changed from: private */
    public void clearRepeatControlCommand(Message message) {
        if (message.command != null && this.mMessageList.size() != 0) {
            for (int size = this.mMessageList.size() - 1; size >= 0; size--) {
                Message message2 = this.mMessageList.get(size);
                if (!(message2 == null || message2.command == null || !message2.getPackageName().equals(message.getPackageName()))) {
                    switch (message.command.intValue()) {
                        case 1:
                        case 2:
                            if (message2.command.intValue() == 1 || message2.command.intValue() == 2) {
                                this.mMessageList.remove(size);
                                break;
                            }
                        case 3:
                        case 4:
                            if (message2.command.intValue() == 3 || message2.command.intValue() == 4) {
                                this.mMessageList.remove(size);
                                break;
                            }
                        case 5:
                        case 6:
                            if (message2.command.intValue() == 5 || message2.command.intValue() == 6) {
                                this.mMessageList.remove(size);
                                break;
                            }
                    }
                    String tag = getTag();
                    StringBuilder sb = new StringBuilder("clearRepeatControlCommand message:");
                    sb.append(message2.command);
                    sb.append("/");
                    sb.append(message2.getPackageName());
                    ALog.d(tag, sb.toString(), new Object[0]);
                }
            }
            if (this.mMessageHandler != null) {
                this.mMessageHandler.cancelControlMessage(message);
            }
        }
    }

    /* access modifiers changed from: private */
    public void connect(String str) {
        SessionInfo sessionInfo;
        int i;
        String str2 = str;
        if (this.mStatus != 2 && this.mStatus != 1) {
            if (this.mHttpDnsProvider == null) {
                this.mHttpDnsProvider = new HttpDnsProvider(getChannelHost());
            }
            List<bo> availableStrategy = this.mHttpDnsProvider.getAvailableStrategy(getChannelHost());
            if (availableStrategy == null || availableStrategy.size() <= 0) {
                if (str2 != null) {
                    this.mIp = str2;
                } else {
                    this.mIp = getChannelHost();
                }
                this.mPort = System.currentTimeMillis() % 2 == 0 ? 80 : 443;
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_DNS, RPCDataItems.VALUE_DT_LOCALDNS, 0.0d);
                ALog.i(getTag(), "connect get ip from amdc fail!!", new Object[0]);
            } else {
                for (bo next : availableStrategy) {
                    if (next != null) {
                        ALog.e(getTag(), "connect", OnNativeInvokeListener.ARG_IP, next.a(), "port", Integer.valueOf(next.d()));
                    }
                }
                if (this.mLastConnectFail) {
                    this.mHttpDnsProvider.updateStrategyPos();
                    this.mLastConnectFail = false;
                }
                bo strategy = this.mHttpDnsProvider.getStrategy();
                this.mIp = strategy == null ? getChannelHost() : strategy.a();
                if (strategy == null) {
                    i = 443;
                } else {
                    i = strategy.d();
                }
                this.mPort = i;
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_POINT_DNS, RPCDataItems.VALUE_DT_HTTPDNS, 0.0d);
                ALog.e(getTag(), "connect from amdc succ", OnNativeInvokeListener.ARG_IP, this.mIp, "port", Integer.valueOf(this.mPort), "originPos", Integer.valueOf(this.mHttpDnsProvider.getStrategyPos()));
            }
            StringBuilder sb = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS);
            sb.append(this.mIp);
            sb.append(":");
            sb.append(this.mPort);
            sb.append("/accs/");
            this.mUrl = sb.toString();
            ALog.e(getTag(), "connect", MonitorItemConstants.KEY_URL, this.mUrl);
            this.mSessionId = String.valueOf(System.currentTimeMillis());
            if (this.mStatistic != null) {
                x.a().a((StatObject) this.mStatistic);
            }
            this.mStatistic = new SessionMonitor();
            this.mStatistic.setConnectType(this.mConnectionType == 0 ? "service" : "inapp");
            if (this.mAgent != null) {
                try {
                    this.mConnStartTime = System.currentTimeMillis();
                    this.mConnStartTimeNano = System.nanoTime();
                    this.mProxyIp = UtilityImpl.getProxyHost(this.mContext);
                    this.mProxyPort = UtilityImpl.getProxyPort(this.mContext);
                    this.lastPingTime = System.currentTimeMillis();
                    this.mStatistic.onStartConnect();
                    notifyStatus(2);
                    synchronized (this.mConnLock) {
                        try {
                            if (TextUtils.isEmpty(this.mProxyIp) || this.mProxyPort < 0 || !this.mCanUserProxy) {
                                ALog.e(getTag(), "connect normal", new Object[0]);
                                String str3 = this.mIp;
                                int i2 = this.mPort;
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(getChannelHost());
                                sb2.append("_");
                                sb2.append(this.mAppkey);
                                sessionInfo = new SessionInfo(str3, i2, sb2.toString(), null, 0, this.mSessionId, this, 4226);
                                this.mProxy = "";
                            } else {
                                ALog.e(getTag(), "connect", CommonUtils.APN_PROP_PROXY, this.mProxyIp, "port", Integer.valueOf(this.mProxyPort));
                                String str4 = this.mIp;
                                int i3 = this.mPort;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(getChannelHost());
                                sb3.append("_");
                                sb3.append(this.mAppkey);
                                sessionInfo = new SessionInfo(str4, i3, sb3.toString(), this.mProxyIp, this.mProxyPort, this.mSessionId, this, 4226);
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(this.mProxyIp);
                                sb4.append(":");
                                sb4.append(this.mProxyPort);
                                this.mProxy = sb4.toString();
                            }
                            sessionInfo.setPubKeySeqNum(getPublicKeyType());
                            sessionInfo.setConnectionTimeoutMs(40000);
                            this.mSession = this.mAgent.createSession(sessionInfo);
                            this.mStatistic.connection_stop_date = 0;
                            this.mConnLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            this.mCanUserProxy = false;
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private int getPublicKeyType() {
        boolean isSecurityOff = isSecurityOff();
        int i = 0;
        if (AccsClientConfig.mEnv != 2) {
            int channelPubKey = this.mConfig.getChannelPubKey();
            if (channelPubKey > 0) {
                ALog.i(getTag(), "getPublicKeyType use custom pub key", "pubKey", Integer.valueOf(channelPubKey));
                return channelPubKey;
            }
            i = isSecurityOff ? 4 : 3;
        }
        return i;
    }

    private void auth() {
        if (this.mSession == null) {
            notifyStatus(3);
            return;
        }
        try {
            String encode = URLEncoder.encode(UtilityImpl.getDeviceId(this.mContext));
            String appsign = UtilityImpl.getAppsign(this.mContext, getAppkey(), this.mConfig.getAppSecret(), UtilityImpl.getDeviceId(this.mContext), this.mConfigTag);
            String buildAuthUrl = buildAuthUrl(this.mUrl);
            ALog.e(getTag(), "auth", "url", buildAuthUrl);
            this.mFinalUrl = buildAuthUrl;
            if (!checkParam(encode, getAppkey(), appsign)) {
                ALog.e(getTag(), "auth param error!", new Object[0]);
                onAuthFail(-6);
                return;
            }
            new URL(buildAuthUrl);
            SpdyRequest spdyRequest = new SpdyRequest(new URL(buildAuthUrl), (String) "GET", RequestPriority.DEFAULT_PRIORITY, (int) REQ_TIMEOUT, 40000);
            spdyRequest.setDomain(getChannelHost());
            this.mSession.submitRequest(spdyRequest, new SpdyDataProvider((byte[]) null), getChannelHost(), this);
        } catch (Throwable th) {
            ALog.e(getTag(), "auth exception ", th, new Object[0]);
            onAuthFail(-7);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0036, code lost:
        if (android.text.TextUtils.isEmpty(r14) != false) goto L_0x0038;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0054  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean checkParam(java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
            r11 = this;
            android.content.Context r0 = r11.mContext
            int r0 = com.taobao.accs.utl.Utils.getMode(r0)
            r1 = 2
            r2 = 1
            if (r0 != r1) goto L_0x000b
            return r2
        L_0x000b:
            boolean r0 = android.text.TextUtils.isEmpty(r12)
            r3 = 0
            if (r0 != 0) goto L_0x001e
            boolean r0 = android.text.TextUtils.isEmpty(r13)
            if (r0 != 0) goto L_0x001e
            boolean r0 = android.text.TextUtils.isEmpty(r14)
            if (r0 == 0) goto L_0x0099
        L_0x001e:
            r0 = 3
            r11.notifyStatus(r0)
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 == 0) goto L_0x002a
        L_0x0028:
            r0 = 1
            goto L_0x0038
        L_0x002a:
            boolean r12 = android.text.TextUtils.isEmpty(r13)
            if (r12 == 0) goto L_0x0032
            r0 = 2
            goto L_0x0038
        L_0x0032:
            boolean r12 = android.text.TextUtils.isEmpty(r14)
            if (r12 == 0) goto L_0x0028
        L_0x0038:
            com.taobao.accs.ut.monitor.SessionMonitor r12 = r11.mStatistic
            r12.setFailReason(r0)
            com.taobao.accs.ut.monitor.SessionMonitor r12 = r11.mStatistic
            r12.onConnectStop()
            int r12 = r11.mConnectionType
            if (r12 != 0) goto L_0x0049
            java.lang.String r12 = "service"
            goto L_0x004b
        L_0x0049:
            java.lang.String r12 = "inapp"
        L_0x004b:
            com.taobao.accs.net.SpdyConnection$NetworkThread r13 = r11.mThread
            if (r13 == 0) goto L_0x0054
            com.taobao.accs.net.SpdyConnection$NetworkThread r13 = r11.mThread
            int r13 = r13.failTimes
            goto L_0x0055
        L_0x0054:
            r13 = 0
        L_0x0055:
            com.taobao.accs.utl.UTMini r4 = com.taobao.accs.utl.UTMini.getInstance()
            r5 = 66001(0x101d1, float:9.2487E-41)
            java.lang.String r14 = "DISCONNECT "
            java.lang.String r12 = java.lang.String.valueOf(r12)
            java.lang.String r6 = r14.concat(r12)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r0)
            java.lang.Integer r8 = java.lang.Integer.valueOf(r13)
            r12 = 221(0xdd, float:3.1E-43)
            java.lang.Integer r9 = java.lang.Integer.valueOf(r12)
            java.lang.String[] r10 = new java.lang.String[r1]
            java.lang.String r12 = r11.mFinalUrl
            r10[r3] = r12
            java.lang.String r12 = r11.mProxy
            r10[r2] = r12
            r4.commitEvent(r5, r6, r7, r8, r9, r10)
            java.lang.String r12 = "accs"
            java.lang.String r14 = "connect"
            java.lang.String r1 = "retrytimes:"
            java.lang.String r13 = java.lang.String.valueOf(r13)
            java.lang.String r13 = r1.concat(r13)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r1 = ""
            com.taobao.accs.utl.AppMonitorAdapter.commitAlarmFail(r12, r14, r13, r0, r1)
            r2 = 0
        L_0x0099:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.SpdyConnection.checkParam(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    /* access modifiers changed from: private */
    public synchronized void setHeartbeat(boolean z) {
        if (this.mConnectionType != 1) {
            this.lastPingTime = System.currentTimeMillis();
            this.lastPingTimeNano = System.nanoTime();
            HeartbeatManager.getInstance(this.mContext).set();
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0048 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:36:0x0093 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x009f */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0097 A[SYNTHETIC, Splitter:B:41:0x0097] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void notifyStatus(int r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            java.lang.String r0 = r9.getTag()     // Catch:{ all -> 0x00bc }
            java.lang.String r1 = "notifyStatus start"
            r2 = 2
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x00bc }
            java.lang.String r4 = "status"
            r5 = 0
            r3[r5] = r4     // Catch:{ all -> 0x00bc }
            java.lang.String r4 = r9.getStatus(r10)     // Catch:{ all -> 0x00bc }
            r6 = 1
            r3[r6] = r4     // Catch:{ all -> 0x00bc }
            com.taobao.accs.utl.ALog.e(r0, r1, r3)     // Catch:{ all -> 0x00bc }
            int r0 = r9.mStatus     // Catch:{ all -> 0x00bc }
            if (r10 != r0) goto L_0x002a
            java.lang.String r10 = r9.getTag()     // Catch:{ all -> 0x00bc }
            java.lang.String r0 = "ignore notifyStatus"
            java.lang.Object[] r1 = new java.lang.Object[r5]     // Catch:{ all -> 0x00bc }
            com.taobao.accs.utl.ALog.i(r10, r0, r1)     // Catch:{ all -> 0x00bc }
            monitor-exit(r9)
            return
        L_0x002a:
            r9.mStatus = r10     // Catch:{ all -> 0x00bc }
            switch(r10) {
                case 1: goto L_0x0073;
                case 2: goto L_0x0056;
                case 3: goto L_0x0031;
                case 4: goto L_0x00a5;
                default: goto L_0x002f;
            }     // Catch:{ all -> 0x00bc }
        L_0x002f:
            goto L_0x00a5
        L_0x0031:
            r9.setHeartbeat(r6)     // Catch:{ all -> 0x00bc }
            android.content.Context r0 = r9.mContext     // Catch:{ all -> 0x00bc }
            com.taobao.accs.net.HeartbeatManager r0 = com.taobao.accs.net.HeartbeatManager.getInstance(r0)     // Catch:{ all -> 0x00bc }
            r0.onNetworkFail()     // Catch:{ all -> 0x00bc }
            java.lang.Object r0 = r9.mConnLock     // Catch:{ all -> 0x00bc }
            monitor-enter(r0)     // Catch:{ all -> 0x00bc }
            java.lang.Object r1 = r9.mConnLock     // Catch:{ Exception -> 0x0048 }
            r1.notifyAll()     // Catch:{ Exception -> 0x0048 }
            goto L_0x0048
        L_0x0046:
            r10 = move-exception
            goto L_0x0054
        L_0x0048:
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            com.taobao.accs.data.MessageHandler r0 = r9.mMessageHandler     // Catch:{ all -> 0x00bc }
            r1 = -10
            r0.onNetworkFail(r1)     // Catch:{ all -> 0x00bc }
            r9.ping(r5, r6)     // Catch:{ all -> 0x00bc }
            goto L_0x00a5
        L_0x0054:
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            throw r10     // Catch:{ all -> 0x00bc }
        L_0x0056:
            java.util.concurrent.ScheduledFuture<?> r0 = r9.mConnTimoutFuture     // Catch:{ all -> 0x00bc }
            if (r0 == 0) goto L_0x005f
            java.util.concurrent.ScheduledFuture<?> r0 = r9.mConnTimoutFuture     // Catch:{ all -> 0x00bc }
            r0.cancel(r6)     // Catch:{ all -> 0x00bc }
        L_0x005f:
            java.lang.String r0 = r9.mSessionId     // Catch:{ all -> 0x00bc }
            java.util.concurrent.ScheduledThreadPoolExecutor r1 = com.taobao.accs.common.ThreadPoolExecutorFactory.getScheduledExecutor()     // Catch:{ all -> 0x00bc }
            com.taobao.accs.net.SpdyConnection$3 r3 = new com.taobao.accs.net.SpdyConnection$3     // Catch:{ all -> 0x00bc }
            r3.<init>(r0)     // Catch:{ all -> 0x00bc }
            r7 = 120000(0x1d4c0, double:5.9288E-319)
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x00bc }
            r1.schedule(r3, r7, r0)     // Catch:{ all -> 0x00bc }
            goto L_0x00a5
        L_0x0073:
            android.content.Context r0 = r9.mContext     // Catch:{ all -> 0x00bc }
            com.taobao.accs.net.HeartbeatManager r0 = com.taobao.accs.net.HeartbeatManager.getInstance(r0)     // Catch:{ all -> 0x00bc }
            r0.resetLevel()     // Catch:{ all -> 0x00bc }
            r9.setHeartbeat(r6)     // Catch:{ all -> 0x00bc }
            java.util.concurrent.ScheduledFuture<?> r0 = r9.mConnTimoutFuture     // Catch:{ all -> 0x00bc }
            if (r0 == 0) goto L_0x0088
            java.util.concurrent.ScheduledFuture<?> r0 = r9.mConnTimoutFuture     // Catch:{ all -> 0x00bc }
            r0.cancel(r6)     // Catch:{ all -> 0x00bc }
        L_0x0088:
            java.lang.Object r0 = r9.mConnLock     // Catch:{ all -> 0x00bc }
            monitor-enter(r0)     // Catch:{ all -> 0x00bc }
            java.lang.Object r1 = r9.mConnLock     // Catch:{ Exception -> 0x0093 }
            r1.notifyAll()     // Catch:{ Exception -> 0x0093 }
            goto L_0x0093
        L_0x0091:
            r10 = move-exception
            goto L_0x00a3
        L_0x0093:
            monitor-exit(r0)     // Catch:{ all -> 0x0091 }
            java.util.LinkedList<com.taobao.accs.data.Message> r0 = r9.mMessageList     // Catch:{ all -> 0x00bc }
            monitor-enter(r0)     // Catch:{ all -> 0x00bc }
            java.util.LinkedList<com.taobao.accs.data.Message> r1 = r9.mMessageList     // Catch:{ Exception -> 0x009f }
            r1.notifyAll()     // Catch:{ Exception -> 0x009f }
            goto L_0x009f
        L_0x009d:
            r10 = move-exception
            goto L_0x00a1
        L_0x009f:
            monitor-exit(r0)     // Catch:{ all -> 0x009d }
            goto L_0x00a5
        L_0x00a1:
            monitor-exit(r0)     // Catch:{ all -> 0x009d }
            throw r10     // Catch:{ all -> 0x00bc }
        L_0x00a3:
            monitor-exit(r0)     // Catch:{ all -> 0x0091 }
            throw r10     // Catch:{ all -> 0x00bc }
        L_0x00a5:
            java.lang.String r0 = r9.getTag()     // Catch:{ all -> 0x00bc }
            java.lang.String r1 = "notifyStatus end"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00bc }
            java.lang.String r3 = "status"
            r2[r5] = r3     // Catch:{ all -> 0x00bc }
            java.lang.String r10 = r9.getStatus(r10)     // Catch:{ all -> 0x00bc }
            r2[r6] = r10     // Catch:{ all -> 0x00bc }
            com.taobao.accs.utl.ALog.i(r0, r1, r2)     // Catch:{ all -> 0x00bc }
            monitor-exit(r9)
            return
        L_0x00bc:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.SpdyConnection.notifyStatus(int):void");
    }

    public String getChannelHost() {
        String channelHost = this.mConfig.getChannelHost();
        ALog.i(getTag(), "getChannelHost", "host", channelHost);
        return channelHost == null ? "" : channelHost;
    }

    private void initClient() {
        try {
            SpdyAgent.enableDebug = true;
            this.mAgent = SpdyAgent.getInstance(this.mContext, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
            if (SpdyAgent.checkLoadSucc()) {
                LoadSoFailUtil.loadSoSuccess();
                if (!isSecurityOff()) {
                    this.mAgent.setAccsSslCallback(new AccsSSLCallback() {
                        public byte[] getSSLPublicKey(int i, byte[] bArr) {
                            return UtilityImpl.staticBinarySafeDecryptNoB64(SpdyConnection.this.mContext, SpdyConnection.this.mConfigTag, SpdyConnection.this.mAppkey, bArr);
                        }
                    });
                }
                if (!OrangeAdapter.isTnetLogOff(false)) {
                    String str = this.mConnectionType == 0 ? "service" : "inapp";
                    ALog.d(getTag(), "into--[setTnetLogPath]", new Object[0]);
                    String tnetLogFilePath = UtilityImpl.getTnetLogFilePath(this.mContext, str);
                    ALog.d(getTag(), "config tnet log path:".concat(String.valueOf(tnetLogFilePath)), new Object[0]);
                    if (!TextUtils.isEmpty(tnetLogFilePath)) {
                        this.mAgent.configLogFile(tnetLogFilePath, 5242880, 5);
                    }
                }
                return;
            }
            ALog.e(getTag(), "initClient", new Object[0]);
            LoadSoFailUtil.loadSoFail();
        } catch (Throwable th) {
            ALog.e(getTag(), "initClient", th, new Object[0]);
        }
    }

    public boolean isAlive() {
        return this.mRunning;
    }

    public void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e) {
                ALog.e(getTag(), "session cleanUp has exception: ".concat(String.valueOf(e)), new Object[0]);
            }
        }
        int i2 = this.mThread != null ? this.mThread.failTimes : 0;
        ALog.e(getTag(), "spdySessionFailedError", "retryTimes", Integer.valueOf(i2), "errorId", Integer.valueOf(i));
        this.mCanUserProxy = false;
        this.mLastConnectFail = true;
        notifyStatus(3);
        this.mStatistic.setFailReason(i);
        this.mStatistic.onConnectStop();
        UTMini.getInstance().commitEvent(66001, "DISCONNECT ".concat(String.valueOf(this.mConnectionType == 0 ? "service" : "inapp")), (Object) Integer.valueOf(i), (Object) Integer.valueOf(i2), (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), this.mFinalUrl, this.mProxy);
        AppMonitorAdapter.commitAlarmFail("accs", "connect", "retrytimes:".concat(String.valueOf(i2)), String.valueOf(i), "");
    }

    public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        SuperviseConnectInfo superviseConnectInfo2 = superviseConnectInfo;
        this.sessionConnectInterval = superviseConnectInfo2.connectTime;
        int i = superviseConnectInfo2.handshakeTime;
        ALog.e(getTag(), "spdySessionConnectCB", "sessionConnectInterval", Integer.valueOf(this.sessionConnectInterval), "sslTime", Integer.valueOf(i), "reuse", Integer.valueOf(superviseConnectInfo2.sessionTicketReused));
        auth();
        this.mStatistic.setRet(true);
        this.mStatistic.onConnectStop();
        this.mStatistic.tcp_time = (long) this.sessionConnectInterval;
        this.mStatistic.ssl_time = (long) i;
        String str = this.mConnectionType == 0 ? "service" : "inapp";
        UTMini instance = UTMini.getInstance();
        StringBuilder sb = new StringBuilder("CONNECTED ");
        sb.append(str);
        sb.append(Token.SEPARATOR);
        sb.append(superviseConnectInfo2.sessionTicketReused);
        instance.commitEvent(66001, sb.toString(), (Object) String.valueOf(this.sessionConnectInterval), (Object) String.valueOf(i), (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), String.valueOf(superviseConnectInfo2.sessionTicketReused), this.mFinalUrl, this.mProxy);
        AppMonitorAdapter.commitAlarmSuccess("accs", "connect", "");
    }

    public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
        SuperviseConnectInfo superviseConnectInfo2 = superviseConnectInfo;
        ALog.e(getTag(), "spdySessionCloseCallback", "errorCode", Integer.valueOf(i));
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e) {
                ALog.e(getTag(), "session cleanUp has exception: ".concat(String.valueOf(e)), new Object[0]);
            }
        }
        notifyStatus(3);
        this.mStatistic.onCloseConnect();
        if (this.mStatistic.getConCloseDate() > 0 && this.mStatistic.getConStopDate() > 0) {
            this.mStatistic.getConCloseDate();
            this.mStatistic.getConStopDate();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mStatistic.getCloseReason());
        sb.append("tnet error:");
        sb.append(i);
        this.mStatistic.setCloseReason(sb.toString());
        if (superviseConnectInfo2 != null) {
            this.mStatistic.live_time = (long) superviseConnectInfo2.keepalive_period_second;
        }
        x.a().a((StatObject) this.mStatistic);
        for (Message next : this.mMessageHandler.getUnhandledMessages()) {
            if (next.getNetPermanceMonitor() != null) {
                next.getNetPermanceMonitor().setFailReason((String) "session close");
                x.a().a((StatObject) next.getNetPermanceMonitor());
            }
        }
        String str = this.mConnectionType == 0 ? "service" : "inapp";
        String tag = getTag();
        StringBuilder sb2 = new StringBuilder("spdySessionCloseCallback, conKeepTime:");
        sb2.append(this.mStatistic.live_time);
        sb2.append(" connectType:");
        sb2.append(str);
        ALog.d(tag, sb2.toString(), new Object[0]);
        UTMini.getInstance().commitEvent(66001, "DISCONNECT CLOSE ".concat(String.valueOf(str)), (Object) Integer.valueOf(i), (Object) Long.valueOf(this.mStatistic.live_time), (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), this.mFinalUrl, this.mProxy);
    }

    public void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj) {
        ALog.d(getTag(), "spdyPingRecvCallback uniId:".concat(String.valueOf(j)), new Object[0]);
        if (j >= 0) {
            this.mMessageHandler.onRcvPing();
            HeartbeatManager.getInstance(this.mContext).onHeartbeatSucc();
            HeartbeatManager.getInstance(this.mContext).set();
            this.mStatistic.onPingCBReceive();
            if (this.mStatistic.ping_rec_times % 2 == 0) {
                UtilityImpl.setServiceTime(this.mContext, Constants.SP_KEY_SERVICE_END, System.currentTimeMillis());
            }
        }
    }

    public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
        setHeartbeat(true);
        ALog.e(getTag(), "onFrame", "type", Integer.valueOf(i2), "len", Integer.valueOf(bArr.length));
        if (ALog.isPrintLog(Level.D) && bArr.length < 512) {
            long currentTimeMillis = System.currentTimeMillis();
            String str = "";
            for (byte b : bArr) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(Integer.toHexString(b & 255));
                sb.append(Token.SEPARATOR);
                str = sb.toString();
            }
            String tag = getTag();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" log time:");
            sb2.append(System.currentTimeMillis() - currentTimeMillis);
            ALog.d(tag, sb2.toString(), new Object[0]);
        }
        if (i2 == 200) {
            try {
                long currentTimeMillis2 = System.currentTimeMillis();
                this.mMessageHandler.onMessage(bArr);
                ReceiveMsgStat receiveMsgStat = this.mMessageHandler.getReceiveMsgStat();
                if (receiveMsgStat != null) {
                    receiveMsgStat.receiveDate = String.valueOf(currentTimeMillis2);
                    receiveMsgStat.messageType = this.mConnectionType == 0 ? "service" : "inapp";
                    receiveMsgStat.commitUT();
                }
            } catch (Throwable th) {
                ALog.e(getTag(), "onDataReceive ", th, new Object[0]);
                UTMini.getInstance().commitEvent(66001, "SERVICE_DATA_RECEIVE", UtilityImpl.getStackMsg(th));
            }
            ALog.d(getTag(), "try handle msg", new Object[0]);
            cancelPingTimeOut();
        } else {
            ALog.e(getTag(), "drop frame", "len", Integer.valueOf(bArr.length));
        }
        ALog.d(getTag(), "spdyCustomControlFrameRecvCallback", new Object[0]);
    }

    public void spdyStreamCloseCallback(SpdySession spdySession, long j, int i, Object obj, SuperviseData superviseData) {
        ALog.d(getTag(), "spdyStreamCloseCallback", new Object[0]);
        if (i != 0) {
            ALog.e(getTag(), "spdyStreamCloseCallback", "statusCode", Integer.valueOf(i));
            onAuthFail(i);
        }
    }

    public void spdyRequestRecvCallback(SpdySession spdySession, long j, Object obj) {
        ALog.d(getTag(), "spdyRequestRecvCallback", new Object[0]);
    }

    public void spdyOnStreamResponse(SpdySession spdySession, long j, Map<String, List<String>> map, Object obj) {
        this.lastPingTime = System.currentTimeMillis();
        this.lastPingTimeNano = System.nanoTime();
        try {
            Map<String, String> header = UtilityImpl.getHeader(map);
            ALog.d(TAG, "spdyOnStreamResponse", Performance.KEY_LOG_HEADER, map);
            int parseInt = Integer.parseInt(header.get(HTTP_STATUS));
            ALog.e(getTag(), "spdyOnStreamResponse", "httpStatusCode", Integer.valueOf(parseInt));
            if (parseInt == 200) {
                notifyStatus(1);
                String str = header.get("x-at");
                if (!TextUtils.isEmpty(str)) {
                    this.mConnToken = str;
                }
                SessionMonitor sessionMonitor = this.mStatistic;
                long j2 = 0;
                if (this.mStatistic.connection_stop_date > 0) {
                    j2 = System.currentTimeMillis() - this.mStatistic.connection_stop_date;
                }
                sessionMonitor.auth_time = j2;
                UTMini.getInstance().commitEvent(66001, "CONNECTED 200 ".concat(String.valueOf(this.mConnectionType == 0 ? "service" : "inapp")), (Object) this.mFinalUrl, (Object) this.mProxy, (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), "0");
                AppMonitorAdapter.commitAlarmSuccess("accs", "auth", "");
            } else {
                onAuthFail(parseInt);
            }
        } catch (Exception e) {
            ALog.e(getTag(), e.toString(), new Object[0]);
            close();
            this.mStatistic.setCloseReason(LogCategory.CATEGORY_EXCEPTION);
        }
        ALog.d(getTag(), "spdyOnStreamResponse", new Object[0]);
    }

    private void onAuthFail(int i) {
        this.mConnToken = null;
        close();
        int i2 = this.mThread != null ? this.mThread.failTimes : 0;
        this.mStatistic.setCloseReason("code not 200 is".concat(String.valueOf(i)));
        this.mLastConnectFail = true;
        UTMini.getInstance().commitEvent(66001, "CONNECTED NO 200 ".concat(String.valueOf(this.mConnectionType == 0 ? "service" : "inapp")), (Object) Integer.valueOf(i), (Object) Integer.valueOf(i2), (Object) Integer.valueOf(Constants.SDK_VERSION_CODE), this.mFinalUrl, this.mProxy);
        AppMonitorAdapter.commitAlarmFail("accs", "auth", "", String.valueOf(i), "");
    }

    public void spdyDataSendCallback(SpdySession spdySession, boolean z, long j, int i, Object obj) {
        ALog.d(getTag(), "spdyDataSendCallback", new Object[0]);
    }

    public void spdyDataRecvCallback(SpdySession spdySession, boolean z, long j, int i, Object obj) {
        ALog.d(getTag(), "spdyDataRecvCallback", new Object[0]);
    }

    public void notifyNetWorkChange(String str) {
        this.mCanUserProxy = false;
        this.mTimeoutMsgNum = 0;
    }

    public void bioPingRecvCallback(SpdySession spdySession, int i) {
        ALog.w(getTag(), "bioPingRecvCallback uniId:".concat(String.valueOf(i)), new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void onTimeOut(String str, String str2) {
        try {
            notifyStatus(4);
            close();
            this.mStatistic.setCloseReason(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean cancel(String str) {
        boolean z;
        synchronized (this.mMessageList) {
            z = true;
            int size = this.mMessageList.size() - 1;
            while (true) {
                if (size < 0) {
                    z = false;
                    break;
                }
                Message message = this.mMessageList.get(size);
                if (message != null && message.getType() == 1 && message.cunstomDataId != null && message.cunstomDataId.equals(str)) {
                    this.mMessageList.remove(size);
                    break;
                }
                size--;
            }
        }
        return z;
    }

    public byte[] getSSLMeta(SpdySession spdySession) {
        return UtilityImpl.SecurityGuardGetSslTicket2(this.mContext, this.mConfigTag, this.mAppkey, spdySession.getDomain());
    }

    public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        return UtilityImpl.SecurityGuardPutSslTicket2(this.mContext, this.mConfigTag, this.mAppkey, spdySession.getDomain(), bArr);
    }

    public void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, long j, SpdyByteArray spdyByteArray, Object obj) {
        ALog.d(getTag(), "spdyDataChunkRecvCB", new Object[0]);
    }

    /* access modifiers changed from: protected */
    public String getTag() {
        StringBuilder sb = new StringBuilder(TAG);
        sb.append(this.mConfigTag);
        return sb.toString();
    }

    public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        reSendAck(i);
    }

    /* access modifiers changed from: protected */
    public void initAwcn(Context context) {
        if (!this.mAwcnInited) {
            super.initAwcn(context);
            m.a(false);
            this.mAwcnInited = true;
            ALog.i(getTag(), "init awcn success!", new Object[0]);
        }
    }
}
