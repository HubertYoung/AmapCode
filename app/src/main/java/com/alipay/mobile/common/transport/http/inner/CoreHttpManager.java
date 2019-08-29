package com.alipay.mobile.common.transport.http.inner;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.concurrent.TaskExecutorManager;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.common.transport.ext.ExtTransportOffice;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.http.AndroidHttpClient;
import com.alipay.mobile.common.transport.http.HttpContextExtend;
import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpTask;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsInitRunnable;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.logtunnel.LogHttpUrlRequest;
import com.alipay.mobile.common.transport.multimedia.DjgHttpUrlRequest;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.utils.AppStartNetWorkingHelper;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NwSharedSwitchUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.alipay.mobile.quinox.bundle.IBundleOperator;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Security;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class CoreHttpManager {
    public static CoreHttpManager DEFAULT_HTTP_MANAGER = null;
    public static final String TAG = "HttpManager";
    private AndroidHttpClient a;
    private AndroidHttpClient b;
    private AndroidHttpClient c;
    private AndroidHttpClient d;
    private TaskExecutorManager e;
    /* access modifiers changed from: private */
    public CountDownLatch f = new CountDownLatch(1);
    private long g;
    private long h;
    private long i;
    private int j;
    protected Context mContext;

    public static final CoreHttpManager getInstance(Context context) {
        if (DEFAULT_HTTP_MANAGER != null) {
            return DEFAULT_HTTP_MANAGER;
        }
        synchronized (CoreHttpManager.class) {
            try {
                if (DEFAULT_HTTP_MANAGER != null) {
                    CoreHttpManager coreHttpManager = DEFAULT_HTTP_MANAGER;
                    return coreHttpManager;
                }
                DEFAULT_HTTP_MANAGER = new CoreHttpManager(context);
                return DEFAULT_HTTP_MANAGER;
            }
        }
    }

    private CoreHttpManager(Context context) {
        this.mContext = context;
        a();
    }

    private void a() {
        LogCatUtil.info("HttpManager", "Transport start init ..");
        TransportEnvUtil.setContext(this.mContext);
        e();
        f();
        getHttpClient();
        this.e = TaskExecutorManager.getInstance(this.mContext);
        NetworkAsyncTaskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    HttpContextExtend.createInstance(CoreHttpManager.this.mContext);
                    NwSharedSwitchUtil.init();
                    CoreHttpManager.this.c();
                    CoreHttpManager.this.b();
                    CoreHttpManager.this.d();
                    CoreHttpManager.this.notifyFirstTunnelChanged();
                    LogCatUtil.info("HttpManager", "Transport async init finish.");
                } catch (Throwable e) {
                    LogCatUtil.error("HttpManager", "Network init very serious error. ", e);
                }
                try {
                    CoreHttpManager.this.f.countDown();
                } catch (Throwable e2) {
                    LogCatUtil.warn((String) "HttpManager", "countDown exception. " + e2.toString());
                }
            }
        });
        LogCatUtil.info("HttpManager", "Transport init finish.");
    }

    /* access modifiers changed from: protected */
    public void notifyFirstTunnelChanged() {
        if (!MiscUtils.isInAlipayClient(this.mContext) || !MiscUtils.isPushProcess(this.mContext)) {
            NetworkTunnelStrategy.getInstance().notifyFirstTunnelChanged();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        try {
            AppStartNetWorkingHelper.runOnAppStart(new AlipayHttpDnsInitRunnable(this.mContext, DnsUtil.getFlag(this.mContext)), this.mContext);
        } catch (Exception e2) {
            LogCatUtil.warn((String) "HttpManager", "runOnAppStart exception : " + e2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.mContext == null) {
            LogCatUtil.error((String) "HttpManager", (String) "initConfigWithStrategy. mContext is null.");
            return;
        }
        TransportConfigureManager.getInstance().firstUpdateConfig(this.mContext);
        NetworkTunnelStrategy.getInstance().init(this.mContext, HttpContextExtend.getInstance().getDid());
    }

    /* access modifiers changed from: private */
    public void d() {
        ExtTransportOffice extTransOffice = ExtTransportOffice.getInstance();
        extTransOffice.setContext(this.mContext);
        if (extTransOffice.isEnableExtTransport(this.mContext) && !MiscUtils.isPushProcess(this.mContext)) {
            extTransOffice.init(this.mContext);
        }
    }

    private static void e() {
        Security.setProperty("networkaddress.cache.ttl", "-1");
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("java.net.preferIPv6Addresses", "false");
    }

    public Future<Response> execute(HttpManager httpManager, Request request) {
        if (!(request instanceof HttpUrlRequest)) {
            throw new IllegalArgumentException("request not instanceof HttpUrlRequest. request=[" + (request != null ? request.getClass().getName() : " is null. ") + "]");
        }
        try {
            if (this.f.getCount() == 1) {
                LogCatUtil.info("HttpManager", "waiting for transport init complete!");
            }
            this.f.await();
        } catch (InterruptedException e2) {
            LogCatUtil.warn((String) "HttpManager", "countDownLatch await exception. " + e2.toString());
        }
        if (MiscUtils.isDebugger(this.mContext)) {
            LogCatUtil.info("HttpManager", dumpPerf(httpManager.getClass().getSimpleName()));
        }
        HttpUrlRequest urlHttpRequest = (HttpUrlRequest) request;
        return this.e.execute(createTask(httpManager.generateWorker(urlHttpRequest), a(urlHttpRequest)));
    }

    private static int a(HttpUrlRequest urlHttpRequest) {
        if (c(urlHttpRequest)) {
            return 4;
        }
        String operationType = urlHttpRequest.getTag(TransportConstants.KEY_OPERATION_TYPE);
        if (TextUtils.equals(operationType, DownloadRequest.OPERATION_TYPE)) {
            try {
                URL url = new URL(urlHttpRequest.getUrl());
                if (!url.getPath().endsWith(".amr") && !url.getPath().endsWith(IBundleOperator.EXTENSION)) {
                    return 2;
                }
                if (((DownloadRequest) urlHttpRequest).isUrgentResource()) {
                    return 8;
                }
                return 3;
            } catch (MalformedURLException e2) {
                throw new RuntimeException(e2);
            }
        } else if (TextUtils.equals(operationType, H5HttpUrlRequest.OPERATION_TYPE)) {
            return 6;
        } else {
            if (TextUtils.equals(operationType, DjgHttpUrlRequest.OPERATION_TYPE)) {
                return 5;
            }
            if (TextUtils.equals(operationType, LogHttpUrlRequest.OPERATION_TYPE)) {
                return 7;
            }
            if (b(urlHttpRequest)) {
                return 1;
            }
            return 0;
        }
    }

    private static boolean b(HttpUrlRequest urlHttpRequest) {
        String operationType = urlHttpRequest.getTag(TransportConstants.KEY_OPERATION_TYPE);
        if (TextUtils.isEmpty(operationType)) {
            return true;
        }
        if (urlHttpRequest.isBgRpc()) {
            LogCatUtil.debug("BgRpc", "Background RPC： " + operationType);
            return true;
        } else if (!MiscUtils.isBgRpc(operationType)) {
            return false;
        } else {
            urlHttpRequest.setBgRpc(true);
            LogCatUtil.warn((String) "BgRpc", "Warning: Force bg RPC :" + operationType);
            return true;
        }
    }

    private static boolean c(HttpUrlRequest urlHttpRequest) {
        boolean isUrgent = urlHttpRequest.getUrgentFlag();
        LogCatUtil.debug("UrgentRPC", "Request is Urgent RPC: " + isUrgent);
        String openSwitch = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.USE_URGENT_RPC_POOL);
        if (TextUtils.isEmpty(openSwitch) || !openSwitch.startsWith("T")) {
            return false;
        }
        return isUrgent;
    }

    private static void f() {
        HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return r1.a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.common.transport.http.AndroidHttpClient getHttpClient() {
        /*
            r1 = this;
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.a
            if (r0 == 0) goto L_0x0007
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.a
        L_0x0006:
            return r0
        L_0x0007:
            monitor-enter(r1)
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.a     // Catch:{ all -> 0x0010 }
            if (r0 == 0) goto L_0x0013
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.a     // Catch:{ all -> 0x0010 }
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x0010:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            throw r0
        L_0x0013:
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.a     // Catch:{ all -> 0x0010 }
            if (r0 != 0) goto L_0x001d
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = com.alipay.mobile.common.transport.http.AndroidHttpClient.newDefaultInstance()     // Catch:{ all -> 0x0010 }
            r1.a = r0     // Catch:{ all -> 0x0010 }
        L_0x001d:
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.a
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.http.inner.CoreHttpManager.getHttpClient():com.alipay.mobile.common.transport.http.AndroidHttpClient");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return r1.b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.common.transport.http.AndroidHttpClient getH5HttpClient() {
        /*
            r1 = this;
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.b
            if (r0 == 0) goto L_0x0007
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.b
        L_0x0006:
            return r0
        L_0x0007:
            monitor-enter(r1)
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.b     // Catch:{ all -> 0x0010 }
            if (r0 == 0) goto L_0x0013
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.b     // Catch:{ all -> 0x0010 }
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x0010:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            throw r0
        L_0x0013:
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.b     // Catch:{ all -> 0x0010 }
            if (r0 != 0) goto L_0x001d
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = com.alipay.mobile.common.transport.http.AndroidHttpClient.newDefaultInstance()     // Catch:{ all -> 0x0010 }
            r1.b = r0     // Catch:{ all -> 0x0010 }
        L_0x001d:
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.b
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.http.inner.CoreHttpManager.getH5HttpClient():com.alipay.mobile.common.transport.http.AndroidHttpClient");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return r1.c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.common.transport.http.AndroidHttpClient getDjgHttpClient() {
        /*
            r1 = this;
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.c
            if (r0 == 0) goto L_0x0007
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.c
        L_0x0006:
            return r0
        L_0x0007:
            monitor-enter(r1)
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.c     // Catch:{ all -> 0x0010 }
            if (r0 == 0) goto L_0x0013
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.c     // Catch:{ all -> 0x0010 }
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x0010:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            throw r0
        L_0x0013:
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.c     // Catch:{ all -> 0x0010 }
            if (r0 != 0) goto L_0x001f
            java.lang.String r0 = "Android_MWallet_DJango"
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = com.alipay.mobile.common.transport.http.AndroidHttpClient.newInstanceOfBigConn(r0)     // Catch:{ all -> 0x0010 }
            r1.c = r0     // Catch:{ all -> 0x0010 }
        L_0x001f:
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.c
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.http.inner.CoreHttpManager.getDjgHttpClient():com.alipay.mobile.common.transport.http.AndroidHttpClient");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return r1.d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.common.transport.http.AndroidHttpClient getLogHttpClient() {
        /*
            r1 = this;
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.d
            if (r0 == 0) goto L_0x0007
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.d
        L_0x0006:
            return r0
        L_0x0007:
            monitor-enter(r1)
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.d     // Catch:{ all -> 0x0010 }
            if (r0 == 0) goto L_0x0013
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.d     // Catch:{ all -> 0x0010 }
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x0010:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            throw r0
        L_0x0013:
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.d     // Catch:{ all -> 0x0010 }
            if (r0 != 0) goto L_0x001d
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = com.alipay.mobile.common.transport.http.AndroidHttpClient.newDefaultInstance()     // Catch:{ all -> 0x0010 }
            r1.d = r0     // Catch:{ all -> 0x0010 }
        L_0x001d:
            monitor-exit(r1)     // Catch:{ all -> 0x0010 }
            com.alipay.mobile.common.transport.http.AndroidHttpClient r0 = r1.d
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.http.inner.CoreHttpManager.getLogHttpClient():com.alipay.mobile.common.transport.http.AndroidHttpClient");
    }

    public void addDataSize(long size) {
        this.g += size;
    }

    public void addConnectTime(long time) {
        this.h += time;
        this.j++;
    }

    public void addSocketTime(long time) {
        this.i += time;
    }

    public long getAverageSpeed() {
        if (this.i == 0) {
            return 0;
        }
        return ((this.g * 1000) / this.i) >> 10;
    }

    public long getAverageConnectTime() {
        if (this.j == 0) {
            return 0;
        }
        return this.h / ((long) this.j);
    }

    public String dumpPerf(String tag) {
        try {
            return String.format(tag + MetaRecord.LOG_SEPARATOR + hashCode() + ": Avarage Speed = %d KB/S, Connetct Time = %d ms, All data size = %d bytes, All enqueueConnect time = %d ms, All socket time = %d ms, All request times = %d times", new Object[]{Long.valueOf(getAverageSpeed()), Long.valueOf(getAverageConnectTime()), Long.valueOf(this.g), Long.valueOf(this.h), Long.valueOf(this.i), Integer.valueOf(this.j)});
        } catch (Exception e2) {
            LogCatUtil.warn((String) "HttpManager", (String) "dumpPerf exception");
            return "";
        }
    }

    public void close() {
        this.e.closeAllSingleThreadPool();
        if (this.a != null) {
            this.a.close();
            this.a = null;
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setH5HttpClient(AndroidHttpClient mH5HttpClient) {
        synchronized (this) {
            this.b = mH5HttpClient;
        }
    }

    public void setHttpClient(AndroidHttpClient httpClient) {
        this.a = httpClient;
    }

    public void setDjgHttpClient(AndroidHttpClient djgHttpClient) {
        this.c = djgHttpClient;
    }

    /* access modifiers changed from: protected */
    public HttpTask createTask(HttpWorker httpWorke, int taskType) {
        return new HttpTask(httpWorke, taskType);
    }
}
