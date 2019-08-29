package com.alipay.mobile.common.transportext.biz.quic;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.transport.concurrent.ActThreadPoolExecutor;
import com.alipay.mobile.common.transport.concurrent.TaskExecutorManager;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.download.DownloadManager;
import com.alipay.mobile.common.transport.download.DownloadRequest;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transportext.biz.appevent.AppEventManager;
import com.alipay.mobile.common.transportext.biz.appevent.EventInterfaceAdapter;
import com.alipay.mobile.common.transportext.biz.util.NetInfoHelper;
import com.alipay.mobile.common.utils.ConnectionUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class QuicTestController {
    private static final String APP_SUB_DIR_STR = "quic_libs";
    private static final String EXTERNAL_FILE = "quic_fils";
    private static final String LIB_QUIC_SO_FILE_NAME = "libquic.so";
    private static final String QUIC_ZIP = "quic.zip";
    private static final String TAG = "QuicTestController";
    private File appSubDir;
    /* access modifiers changed from: private */
    public Context context;
    private File externalFilesDir;
    /* access modifiers changed from: private */
    public FutureTask<Void> futureTask;
    private boolean loadedSo = false;
    private QuicAppEventListener quicAppEventListener;
    private File quicSoFile;
    private File quicZipFile;
    private Timer taskCheckTimer;
    private File tmpQuicSoFile;
    /* access modifiers changed from: private */
    public boolean working = false;

    class MyLibQuicCallback extends LibQuicCallback {
        MyLibQuicCallback() {
        }

        public void offerLog(String res) {
            try {
                int net0 = ConnectionUtil.getConnType(QuicTestController.this.context);
                int net1 = ConnectionUtil.getNetworkType(QuicTestController.this.context);
                Performance pf = new TransportPerformance();
                pf.setSubType("QUIC");
                pf.setParam1("1.0");
                pf.setParam2(net0 + "_" + net1);
                pf.setParam3("quic");
                pf.getExtPramas().put("qlog", res);
                LoggerFactory.getMonitorLogger().performance(PerformanceID.MONITORPOINT_NETWORK, pf);
                LogCatUtil.debug("QUIC_PERF", pf.toString());
            } catch (Exception e) {
                LogCatUtil.warn((String) QuicTestController.TAG, (Throwable) e);
            }
        }
    }

    class QuicAppEventListener extends EventInterfaceAdapter {
        private LibQuicNative quicProxy;
        private boolean screenOff = false;

        QuicAppEventListener() {
        }

        public void onSeceenOffEvent() {
            screenOff();
        }

        public void onNetWorkEvent(boolean netStatus, int mainType, int subType) {
            if (this.quicProxy == null) {
                LogCatUtil.info(QuicTestController.TAG, "quicProxy is null");
            }
        }

        public void onAppLeaveEvent() {
            screenOff();
        }

        public void screenOff() {
            if (!this.screenOff) {
                this.screenOff = true;
                QuicTestController.this.innerTriggerTest();
            }
        }

        public void setQuicProxy(LibQuicNative quicProxy2) {
            this.quicProxy = quicProxy2;
        }
    }

    public QuicTestController(Context context2) {
        this.context = context2;
        init();
    }

    private void init() {
        this.externalFilesDir = this.context.getExternalFilesDir(EXTERNAL_FILE);
        this.quicZipFile = new File(this.externalFilesDir, QUIC_ZIP);
        this.tmpQuicSoFile = new File(this.externalFilesDir, LIB_QUIC_SO_FILE_NAME);
        this.appSubDir = new File(this.context.getApplicationContext().getFilesDir(), APP_SUB_DIR_STR);
        this.quicSoFile = new File(this.appSubDir, LIB_QUIC_SO_FILE_NAME);
    }

    public void tryTriggerTest() {
        if (MiscUtils.grayscale(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.QUIC_SWITCH)) && !isNoTimeTo() && !this.working) {
            this.quicAppEventListener = new QuicAppEventListener();
            AppEventManager.register(this.quicAppEventListener);
        }
    }

    /* access modifiers changed from: private */
    public void innerTriggerTest() {
        if (!isNoTimeTo()) {
            synchronized (this) {
                if (!this.working) {
                    this.working = true;
                    ActThreadPoolExecutor bgExecutor = TaskExecutorManager.getInstance(this.context).getFgExecutor();
                    this.futureTask = new FutureTask<>(new Callable<Void>() {
                        /* JADX INFO: finally extract failed */
                        public Void call() {
                            try {
                                QuicTestController.this.goTest();
                                QuicTestController.this.working = false;
                                return null;
                            } catch (Throwable th) {
                                QuicTestController.this.working = false;
                                throw th;
                            }
                        }
                    });
                    try {
                        bgExecutor.execute(this.futureTask);
                        startTaskCheckTimer();
                    } catch (Exception e) {
                        this.working = false;
                        if (this.quicAppEventListener != null) {
                            AppEventManager.unregister(this.quicAppEventListener);
                            LogCatUtil.debug(TAG, "unregister quicAppEventListener.");
                        }
                        if (this.taskCheckTimer != null) {
                            this.taskCheckTimer.cancel();
                            LogCatUtil.debug(TAG, "taskCheckTimer cancel.");
                        }
                        LogCatUtil.warn((String) TAG, (Throwable) e);
                    }
                }
            }
        }
    }

    private void startTaskCheckTimer() {
        LogCatUtil.debug(TAG, "startTaskCheckTimer");
        this.taskCheckTimer = new Timer("QuicTaskCheck");
        this.taskCheckTimer.schedule(new TimerTask() {
            public void run() {
                if (QuicTestController.this.futureTask != null && !QuicTestController.this.futureTask.isDone()) {
                    QuicTestController.this.futureTask.cancel(true);
                    LogCatUtil.debug(QuicTestController.TAG, "futureTask cancel.");
                }
            }
        }, 60000);
    }

    private boolean isNoTimeTo() {
        try {
            if (!TextUtils.isEmpty("")) {
                long nextExeTime = Long.valueOf("").longValue();
                if (System.currentTimeMillis() < nextExeTime) {
                    LogCatUtil.info(TAG, "The on time to. nextTime=" + new Date(nextExeTime).toLocaleString());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, (Throwable) e);
            return true;
        }
    }

    public synchronized void goTest() {
        donwloadOrUpdateFile();
        unzip();
        copyToAppDir();
        loadSo();
        executeTest();
    }

    /* access modifiers changed from: protected */
    public void donwloadOrUpdateFile() {
        if (!NetworkUtils.isNetworkAvailable(this.context)) {
            throw new IllegalStateException("Currently no network！ ");
        } else if (!NetworkUtils.isWiFiMobileNetwork(this.context)) {
            LogCatUtil.info(TAG, "Not wifi network, don't download task. return.");
        } else {
            TransportConfigureManager mng = TransportConfigureManager.getInstance();
            if (!mng.isLoadedConfig()) {
                LogCatUtil.info(TAG, "no config! return.");
                return;
            }
            int quicZipSize = mng.getIntValue(TransportConfigureItem.QUIC_ZIP_SIZE);
            if (quicZipSize <= 0) {
                LogCatUtil.info(TAG, "Illegal zip file size. return.");
                return;
            }
            String quicDownUrl = mng.getStringValue((ConfigureItem) TransportConfigureItem.QUIC_DOWN_URL);
            if (TextUtils.isEmpty(quicDownUrl)) {
                LogCatUtil.info(TAG, "download url is empty. return.");
                return;
            }
            if (!MiscUtils.isDebugger(this.context)) {
                try {
                    String host = new URL(quicDownUrl).getHost();
                    if (!host.endsWith("alipayobjects.com") && !host.endsWith("alipay.com")) {
                        LogCatUtil.info(TAG, "download host only support 'alipayobjects.com' or 'tfs.alipay.com', return.");
                        return;
                    }
                } catch (Exception e) {
                    LogCatUtil.warn((String) TAG, (Throwable) e);
                }
            }
            if (this.externalFilesDir == null) {
                LogCatUtil.info(TAG, "externalFilesDir is null, return.");
                return;
            }
            if (this.quicZipFile.exists()) {
                this.quicZipFile.delete();
            }
            DownloadRequest downloadRequest = new DownloadRequest(quicDownUrl);
            downloadRequest.setPath(this.quicZipFile.getPath());
            downloadRequest.setRedownload(true);
            downloadRequest.setUrl(quicDownUrl);
            try {
                new DownloadManager(this.context).addDownload(downloadRequest).get(30, TimeUnit.SECONDS);
            } catch (Exception e2) {
                LogCatUtil.warn((String) TAG, (Throwable) e2);
            }
            if (!this.quicZipFile.exists()) {
                LogCatUtil.info(TAG, "file download fail!");
            } else if (this.quicZipFile.length() != ((long) quicZipSize)) {
                this.quicZipFile.delete();
                LogCatUtil.info(TAG, "file download fail!");
            } else {
                LogCatUtil.info(TAG, "download success!");
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ba A[SYNTHETIC, Splitter:B:37:0x00ba] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00f5 A[SYNTHETIC, Splitter:B:57:0x00f5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void unzip() {
        /*
            r14 = this;
            java.lang.String r10 = "QuicTestController"
            java.lang.String r11 = "================ unzip ============="
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r10, r11)
            java.io.File r10 = r14.externalFilesDir
            if (r10 != 0) goto L_0x0013
            java.lang.String r10 = "QuicTestController"
            java.lang.String r11 = "externalFilesDir is null."
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r10, r11)
        L_0x0012:
            return
        L_0x0013:
            java.io.File r10 = r14.quicZipFile
            if (r10 == 0) goto L_0x0027
            java.io.File r10 = r14.quicZipFile
            boolean r10 = r10.exists()
            if (r10 == 0) goto L_0x0027
            java.io.File r10 = r14.quicZipFile
            boolean r10 = r10.canRead()
            if (r10 != 0) goto L_0x0046
        L_0x0027:
            java.lang.String r10 = "QuicTestController"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.io.File r12 = r14.quicZipFile
            java.lang.String r12 = r12.getPath()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = " not exists or not permissions!"
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r10, r11)
            goto L_0x0012
        L_0x0046:
            java.io.File r10 = r14.tmpQuicSoFile
            boolean r10 = r10.exists()
            if (r10 == 0) goto L_0x0071
            java.io.File r10 = r14.tmpQuicSoFile
            r10.delete()
            java.lang.String r10 = "QuicTestController"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.io.File r12 = r14.tmpQuicSoFile
            java.lang.String r12 = r12.getPath()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = " deleted !"
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r10, r11)
        L_0x0071:
            r8 = 0
            java.util.zip.ZipInputStream r9 = new java.util.zip.ZipInputStream     // Catch:{ Exception -> 0x0141 }
            java.io.FileInputStream r10 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0141 }
            java.io.File r11 = r14.quicZipFile     // Catch:{ Exception -> 0x0141 }
            r10.<init>(r11)     // Catch:{ Exception -> 0x0141 }
            r9.<init>(r10)     // Catch:{ Exception -> 0x0141 }
        L_0x007e:
            java.util.zip.ZipEntry r7 = r9.getNextEntry()     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
            if (r7 == 0) goto L_0x00f9
            boolean r10 = r7.isDirectory()     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
            if (r10 != 0) goto L_0x007e
            r6 = 0
            java.lang.String r5 = r7.getName()     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
            java.lang.String r10 = "libquic.so"
            boolean r10 = r10.equals(r5)     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
            if (r10 == 0) goto L_0x0099
            java.io.File r6 = r14.tmpQuicSoFile     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
        L_0x0099:
            if (r6 == 0) goto L_0x007e
            r1 = 0
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0144 }
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ all -> 0x0144 }
            r10.<init>(r6)     // Catch:{ all -> 0x0144 }
            r2.<init>(r10)     // Catch:{ all -> 0x0144 }
            r10 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r10]     // Catch:{ all -> 0x00b6 }
        L_0x00aa:
            int r4 = r9.read(r0)     // Catch:{ all -> 0x00b6 }
            r10 = -1
            if (r4 == r10) goto L_0x00e7
            r10 = 0
            r2.write(r0, r10, r4)     // Catch:{ all -> 0x00b6 }
            goto L_0x00aa
        L_0x00b6:
            r10 = move-exception
            r1 = r2
        L_0x00b8:
            if (r1 == 0) goto L_0x00bd
            r1.close()     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
        L_0x00bd:
            throw r10     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
        L_0x00be:
            r3 = move-exception
            r8 = r9
        L_0x00c0:
            java.lang.String r10 = "QuicTestController"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r10, r3)     // Catch:{ all -> 0x013f }
            if (r8 == 0) goto L_0x0012
            r8.close()     // Catch:{ Throwable -> 0x00cc }
            goto L_0x0012
        L_0x00cc:
            r3 = move-exception
            java.lang.String r10 = "QuicTestController"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "zipInputStream.close exception: "
            r11.<init>(r12)
            java.lang.String r12 = r3.toString()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r10, r11)
            goto L_0x0012
        L_0x00e7:
            r2.flush()     // Catch:{ all -> 0x00b6 }
            r9.closeEntry()     // Catch:{ all -> 0x00b6 }
            r2.close()     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
            goto L_0x007e
        L_0x00f1:
            r10 = move-exception
            r8 = r9
        L_0x00f3:
            if (r8 == 0) goto L_0x00f8
            r8.close()     // Catch:{ Throwable -> 0x0125 }
        L_0x00f8:
            throw r10
        L_0x00f9:
            java.io.File r10 = r14.quicZipFile     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
            r10.delete()     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
            java.lang.String r10 = "QuicTestController"
            java.lang.String r11 = "unzip success!"
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r10, r11)     // Catch:{ Exception -> 0x00be, all -> 0x00f1 }
            r9.close()     // Catch:{ Throwable -> 0x010a }
            goto L_0x0012
        L_0x010a:
            r3 = move-exception
            java.lang.String r10 = "QuicTestController"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "zipInputStream.close exception: "
            r11.<init>(r12)
            java.lang.String r12 = r3.toString()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r10, r11)
            goto L_0x0012
        L_0x0125:
            r3 = move-exception
            java.lang.String r11 = "QuicTestController"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "zipInputStream.close exception: "
            r12.<init>(r13)
            java.lang.String r13 = r3.toString()
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r11, r12)
            goto L_0x00f8
        L_0x013f:
            r10 = move-exception
            goto L_0x00f3
        L_0x0141:
            r3 = move-exception
            goto L_0x00c0
        L_0x0144:
            r10 = move-exception
            goto L_0x00b8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.quic.QuicTestController.unzip():void");
    }

    /* access modifiers changed from: protected */
    public void copyToAppDir() {
        LogCatUtil.info(TAG, "================ copyToAppDir =============");
        if (!this.tmpQuicSoFile.exists()) {
            LogCatUtil.info(TAG, "No need uncompress file!");
            return;
        }
        File targetQuicSoFile = new File(this.quicSoFile.getParentFile(), this.quicSoFile.getName() + FilePathHelper.SUFFIX_DOT_TMP);
        if (!cpyFile(this.tmpQuicSoFile, targetQuicSoFile)) {
            targetQuicSoFile.delete();
            return;
        }
        this.tmpQuicSoFile.delete();
        if (this.quicSoFile.exists()) {
            this.quicSoFile.delete();
        }
        targetQuicSoFile.renameTo(this.quicSoFile);
        LogCatUtil.info(TAG, "Copy success!");
    }

    /* access modifiers changed from: protected */
    public void loadSo() {
        LogCatUtil.info(TAG, "================ loadSo =============");
        if (!this.quicSoFile.exists()) {
            LogCatUtil.debug(TAG, "loadSo:   quic so is not exists!");
        } else if (!this.quicSoFile.canRead()) {
            LogCatUtil.debug(TAG, "loadSo:  quic so not permissions");
        } else {
            try {
                System.load(this.quicSoFile.getAbsolutePath());
                this.loadedSo = true;
                LogCatUtil.info(TAG, "load success!!");
            } catch (Exception e) {
                LogCatUtil.error((String) TAG, (Throwable) e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void executeTest() {
        LogCatUtil.info(TAG, "================ executeTest =============");
        try {
            if (!this.loadedSo) {
                LogCatUtil.debug(TAG, "loadedSo=false");
                if (this.quicAppEventListener != null) {
                    AppEventManager.unregister(this.quicAppEventListener);
                    LogCatUtil.debug(TAG, "unregister quicAppEventListener.");
                }
                if (this.taskCheckTimer != null) {
                    this.taskCheckTimer.cancel();
                    LogCatUtil.debug(TAG, "taskCheckTimer cancel.");
                    return;
                }
                return;
            }
            TransportConfigureManager mng = TransportConfigureManager.getInstance();
            LibQuicNative quicProxy = new LibQuicNative();
            this.quicAppEventListener.setQuicProxy(quicProxy);
            quicProxy.Init(new MyLibQuicCallback());
            this.quicAppEventListener.onNetWorkEvent(NetInfoHelper.isNetAvailable(this.context), NetworkUtils.getNetworkType(this.context), ConnectionUtil.getConnType(this.context));
            String dstIp = mng.getStringValue((ConfigureItem) TransportConfigureItem.QUIC_HOST);
            int port = mng.getIntValue(TransportConfigureItem.QUIC_PORT);
            String tmpDataDir = this.externalFilesDir.getAbsolutePath() + "/work";
            quicProxy.ConnectAndSendRequest(dstIp.getBytes(), dstIp.length(), port, tmpDataDir.getBytes(), tmpDataDir.length());
            if (this.quicAppEventListener != null) {
                AppEventManager.unregister(this.quicAppEventListener);
                LogCatUtil.debug(TAG, "unregister quicAppEventListener.");
            }
            if (this.taskCheckTimer != null) {
                this.taskCheckTimer.cancel();
                LogCatUtil.debug(TAG, "taskCheckTimer cancel.");
            }
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, (Throwable) e);
            if (this.quicAppEventListener != null) {
                AppEventManager.unregister(this.quicAppEventListener);
                LogCatUtil.debug(TAG, "unregister quicAppEventListener.");
            }
            if (this.taskCheckTimer != null) {
                this.taskCheckTimer.cancel();
                LogCatUtil.debug(TAG, "taskCheckTimer cancel.");
            }
        } catch (Throwable th) {
            if (this.quicAppEventListener != null) {
                AppEventManager.unregister(this.quicAppEventListener);
                LogCatUtil.debug(TAG, "unregister quicAppEventListener.");
            }
            if (this.taskCheckTimer != null) {
                this.taskCheckTimer.cancel();
                LogCatUtil.debug(TAG, "taskCheckTimer cancel.");
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00a1 A[SYNTHETIC, Splitter:B:29:0x00a1] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a6 A[SYNTHETIC, Splitter:B:32:0x00a6] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00e2 A[SYNTHETIC, Splitter:B:41:0x00e2] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00e7 A[SYNTHETIC, Splitter:B:44:0x00e7] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean cpyFile(java.io.File r14, java.io.File r15) {
        /*
            r13 = this;
            r12 = 0
            r9 = 0
            r7 = 0
            boolean r1 = r15.exists()     // Catch:{ Exception -> 0x0099 }
            if (r1 != 0) goto L_0x003a
            java.io.File r11 = r15.getParentFile()     // Catch:{ Exception -> 0x0099 }
            boolean r1 = r11.exists()     // Catch:{ Exception -> 0x0099 }
            if (r1 != 0) goto L_0x0037
            boolean r1 = r11.mkdirs()     // Catch:{ Exception -> 0x0099 }
            if (r1 != 0) goto L_0x0037
            java.lang.String r1 = "QuicTestController"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0099 }
            r2.<init>()     // Catch:{ Exception -> 0x0099 }
            java.lang.String r3 = r11.getPath()     // Catch:{ Exception -> 0x0099 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x0099 }
            java.lang.String r3 = " create fail !"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x0099 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0099 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r1, r2)     // Catch:{ Exception -> 0x0099 }
            r1 = r12
        L_0x0036:
            return r1
        L_0x0037:
            r15.createNewFile()     // Catch:{ Exception -> 0x0099 }
        L_0x003a:
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0099 }
            r10.<init>(r15)     // Catch:{ Exception -> 0x0099 }
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0126, all -> 0x011f }
            r8.<init>(r14)     // Catch:{ Exception -> 0x0126, all -> 0x011f }
            java.nio.channels.FileChannel r5 = r10.getChannel()     // Catch:{ Exception -> 0x012a, all -> 0x0122 }
            java.nio.channels.FileChannel r0 = r8.getChannel()     // Catch:{ Exception -> 0x012a, all -> 0x0122 }
            r1 = 0
            long r3 = r0.size()     // Catch:{ Exception -> 0x012a, all -> 0x0122 }
            r0.transferTo(r1, r3, r5)     // Catch:{ Exception -> 0x012a, all -> 0x0122 }
            r5.close()     // Catch:{ Exception -> 0x012a, all -> 0x0122 }
            r0.close()     // Catch:{ Exception -> 0x012a, all -> 0x0122 }
            r10.close()     // Catch:{ IOException -> 0x0065 }
        L_0x005e:
            r8.close()     // Catch:{ IOException -> 0x007f }
        L_0x0061:
            r1 = 1
            r7 = r8
            r9 = r10
            goto L_0x0036
        L_0x0065:
            r6 = move-exception
            java.lang.String r1 = "QuicTestController"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "outputStream "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x005e
        L_0x007f:
            r6 = move-exception
            java.lang.String r1 = "QuicTestController"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "inputStream "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x0061
        L_0x0099:
            r6 = move-exception
        L_0x009a:
            java.lang.String r1 = "QuicTestController"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r6)     // Catch:{ all -> 0x00df }
            if (r9 == 0) goto L_0x00a4
            r9.close()     // Catch:{ IOException -> 0x00ab }
        L_0x00a4:
            if (r7 == 0) goto L_0x00a9
            r7.close()     // Catch:{ IOException -> 0x00c5 }
        L_0x00a9:
            r1 = r12
            goto L_0x0036
        L_0x00ab:
            r6 = move-exception
            java.lang.String r1 = "QuicTestController"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "outputStream "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x00a4
        L_0x00c5:
            r6 = move-exception
            java.lang.String r1 = "QuicTestController"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "inputStream "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x00a9
        L_0x00df:
            r1 = move-exception
        L_0x00e0:
            if (r9 == 0) goto L_0x00e5
            r9.close()     // Catch:{ IOException -> 0x00eb }
        L_0x00e5:
            if (r7 == 0) goto L_0x00ea
            r7.close()     // Catch:{ IOException -> 0x0105 }
        L_0x00ea:
            throw r1
        L_0x00eb:
            r6 = move-exception
            java.lang.String r2 = "QuicTestController"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "outputStream "
            r3.<init>(r4)
            java.lang.String r4 = r6.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)
            goto L_0x00e5
        L_0x0105:
            r6 = move-exception
            java.lang.String r2 = "QuicTestController"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "inputStream "
            r3.<init>(r4)
            java.lang.String r4 = r6.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)
            goto L_0x00ea
        L_0x011f:
            r1 = move-exception
            r9 = r10
            goto L_0x00e0
        L_0x0122:
            r1 = move-exception
            r7 = r8
            r9 = r10
            goto L_0x00e0
        L_0x0126:
            r6 = move-exception
            r9 = r10
            goto L_0x009a
        L_0x012a:
            r6 = move-exception
            r7 = r8
            r9 = r10
            goto L_0x009a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.quic.QuicTestController.cpyFile(java.io.File, java.io.File):boolean");
    }
}
