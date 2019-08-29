package com.taobao.accs.eudemon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.Utils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;

public class EudemonManager implements Callback {
    public static final int ACT_START = 0;
    public static final int ACT_STOP = -1;
    public static final String AGOO_PID = "agoo.pid";
    public static String AMPARAMS = "startservice -n {packname}/com.taobao.accs.ChannelService";
    private static final int BUF_SIZE = 100;
    private static final String DAEMON_STAT_FILE = "eudemon";
    public static final String EX_FILE_NAME = "DaemonServer";
    private static final String PID = "daemonserver.pid";
    private static final String PKG_INSTALL_DIR = "/data/data/";
    public static final String PROCESS_NAME = "runServer";
    private static final String TAG = "com.taobao.accs.eudemon.EudemonManager";
    private static final ReentrantLock lock = new ReentrantLock();
    private static EudemonManager soManager = null;
    private static int timeoutAlarmDay = 2500;
    private static int timeoutAlarmNight = 7200;
    private String abi;
    private String appKey = "21646297";
    private String checkPackagePath = "";
    public boolean debugMode = false;
    private HandlerThread handerThread = null;
    private Handler hanlder = null;
    private boolean isTransparentProxy = true;
    private Context mContext = null;
    private String reportKey = "100001";
    private String reportLoc = "http://100.69.165.28/agoo/report";
    private int sdkVersion = 0;
    private String serverIp = "100.69.165.28";
    private int serverPort = 80;
    private int timeout = 1800;
    private String ua = "tb_accs_eudemon_1.1.3";

    public EudemonManager(Context context, int i, boolean z) {
        initHandler();
        AMPARAMS = "startservice -n {packname}/com.taobao.accs.ChannelService";
        this.mContext = context;
        this.timeout = i;
        this.debugMode = z;
        this.abi = getFieldReflectively(new Build(), "CPU_ABI");
        StringBuilder sb = new StringBuilder(PKG_INSTALL_DIR);
        sb.append(context.getPackageName());
        this.checkPackagePath = sb.toString();
        this.sdkVersion = Constants.SDK_VERSION_CODE;
        String[] appkey = UtilityImpl.getAppkey(this.mContext);
        this.appKey = (appkey == null || appkey.length == 0) ? "" : appkey[0];
        if (Utils.getMode(context) == 0) {
            this.serverIp = "agoodm.m.taobao.com";
            this.serverPort = 80;
            this.reportLoc = "http://agoodm.m.taobao.com/agoo/report";
            this.reportKey = "1009527";
        } else if (Utils.getMode(context) == 1) {
            this.serverIp = "110.75.98.154";
            this.serverPort = 80;
            this.reportLoc = "http://agoodm.wapa.taobao.com/agoo/report";
            this.reportKey = "1009527";
        } else {
            this.serverIp = "100.69.168.33";
            this.serverPort = 80;
            this.reportLoc = "http://100.69.168.33/agoo/report";
            this.timeout = 60;
            this.reportKey = "9527";
        }
    }

    private void initHandler() {
        this.handerThread = new HandlerThread("soManager-threads");
        this.handerThread.setPriority(5);
        this.handerThread.start();
        this.hanlder = new Handler(this.handerThread.getLooper(), this);
    }

    private String getAbiPath() {
        if (this.abi.startsWith("arm")) {
            return "armeabi/";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.abi);
        sb.append("/");
        return sb.toString();
    }

    private static String getFieldReflectively(Build build, String str) {
        try {
            return Build.class.getField(str).get(build).toString();
        } catch (Throwable unused) {
            return "Unknown";
        }
    }

    public static EudemonManager getInstance(Context context, int i, boolean z) {
        try {
            lock.lock();
            if (soManager == null) {
                soManager = new EudemonManager(context, i, z);
            }
        } catch (Exception e) {
            ALog.e(TAG, "getInstance", e, new Object[0]);
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
        lock.unlock();
        return soManager;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a6 A[SYNTHETIC, Splitter:B:35:0x00a6] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00be A[SYNTHETIC, Splitter:B:44:0x00be] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:39:0x00b4=Splitter:B:39:0x00b4, B:26:0x0089=Splitter:B:26:0x0089} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String copyBinToFiles() throws java.io.IOException {
        /*
            r9 = this;
            java.io.File r0 = new java.io.File
            android.content.Context r1 = r9.mContext
            java.io.File r1 = r1.getFilesDir()
            java.lang.String r2 = "DaemonServer"
            r0.<init>(r1, r2)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x0016
            r0.delete()
        L_0x0016:
            java.lang.String r1 = TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "open assets from = "
            r2.<init>(r3)
            java.lang.String r3 = r9.getAbiPath()
            r2.append(r3)
            java.lang.String r3 = "DaemonServer"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.w(r1, r2, r4)
            java.io.FileOutputStream r1 = new java.io.FileOutputStream
            r1.<init>(r0)
            r2 = 0
            boolean r4 = r9.debugMode     // Catch:{ Exception -> 0x009a }
            if (r4 == 0) goto L_0x0076
            android.content.Context r4 = r9.mContext     // Catch:{ Exception -> 0x009a }
            android.content.res.AssetManager r4 = r4.getAssets()     // Catch:{ Exception -> 0x009a }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009a }
            r5.<init>()     // Catch:{ Exception -> 0x009a }
            java.lang.String r6 = r9.getAbiPath()     // Catch:{ Exception -> 0x009a }
            r5.append(r6)     // Catch:{ Exception -> 0x009a }
            java.lang.String r6 = "DaemonServer"
            r5.append(r6)     // Catch:{ Exception -> 0x009a }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x009a }
            java.io.InputStream r4 = r4.open(r5)     // Catch:{ Exception -> 0x009a }
            r2 = 100
            byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x0071, all -> 0x006e }
        L_0x0062:
            int r5 = r4.read(r2)     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            if (r5 <= 0) goto L_0x006c
            r1.write(r2, r3, r5)     // Catch:{ Exception -> 0x0071, all -> 0x006e }
            goto L_0x0062
        L_0x006c:
            r2 = r4
            goto L_0x0079
        L_0x006e:
            r0 = move-exception
            r2 = r4
            goto L_0x00bc
        L_0x0071:
            r2 = move-exception
            r8 = r4
            r4 = r2
            r2 = r8
            goto L_0x009b
        L_0x0076:
            r9.writeFileInRelease(r1, r0)     // Catch:{ Exception -> 0x009a }
        L_0x0079:
            if (r2 == 0) goto L_0x0089
            r2.close()     // Catch:{ IOException -> 0x007f }
            goto L_0x0089
        L_0x007f:
            r2 = move-exception
            java.lang.String r4 = TAG
            java.lang.String r5 = "error in close input file"
            java.lang.Object[] r6 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.e(r4, r5, r2, r6)
        L_0x0089:
            r1.close()     // Catch:{ IOException -> 0x008d }
            goto L_0x00b7
        L_0x008d:
            r1 = move-exception
            java.lang.String r2 = TAG
            java.lang.String r4 = "error in close io"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.e(r2, r4, r1, r3)
            goto L_0x00b7
        L_0x0098:
            r0 = move-exception
            goto L_0x00bc
        L_0x009a:
            r4 = move-exception
        L_0x009b:
            java.lang.String r5 = TAG     // Catch:{ all -> 0x0098 }
            java.lang.String r6 = "error in copy daemon files"
            java.lang.Object[] r7 = new java.lang.Object[r3]     // Catch:{ all -> 0x0098 }
            com.taobao.accs.utl.ALog.e(r5, r6, r4, r7)     // Catch:{ all -> 0x0098 }
            if (r2 == 0) goto L_0x00b4
            r2.close()     // Catch:{ IOException -> 0x00aa }
            goto L_0x00b4
        L_0x00aa:
            r2 = move-exception
            java.lang.String r4 = TAG
            java.lang.String r5 = "error in close input file"
            java.lang.Object[] r6 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.e(r4, r5, r2, r6)
        L_0x00b4:
            r1.close()     // Catch:{ IOException -> 0x008d }
        L_0x00b7:
            java.lang.String r0 = r0.getCanonicalPath()
            return r0
        L_0x00bc:
            if (r2 == 0) goto L_0x00cc
            r2.close()     // Catch:{ IOException -> 0x00c2 }
            goto L_0x00cc
        L_0x00c2:
            r2 = move-exception
            java.lang.String r4 = TAG
            java.lang.String r5 = "error in close input file"
            java.lang.Object[] r6 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.e(r4, r5, r2, r6)
        L_0x00cc:
            r1.close()     // Catch:{ IOException -> 0x00d0 }
            goto L_0x00da
        L_0x00d0:
            r1 = move-exception
            java.lang.String r2 = TAG
            java.lang.String r4 = "error in close io"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.e(r2, r4, r1, r3)
        L_0x00da:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.eudemon.EudemonManager.copyBinToFiles():java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a0 A[SYNTHETIC, Splitter:B:33:0x00a0] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b3 A[SYNTHETIC, Splitter:B:41:0x00b3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void writeFileInRelease(java.io.FileOutputStream r9, java.io.File r10) throws java.io.IOException {
        /*
            r8 = this;
            java.lang.String r0 = r8.abi
            java.lang.String r0 = com.taobao.accs.eudemon.SoData.getData(r0)
            java.lang.String r1 = TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = ">>>soDataSize:datasize:"
            r2.<init>(r3)
            int r3 = r0.length()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r3 = 0
            java.lang.Object[] r4 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.d(r1, r2, r4)
            byte[] r0 = r0.getBytes()
            byte[] r0 = android.util.Base64.decode(r0, r3)
            java.lang.String r1 = TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = ">>>soDataSize:"
            r2.<init>(r4)
            int r4 = r0.length
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r4 = new java.lang.Object[r3]
            com.taobao.accs.utl.ALog.d(r1, r2, r4)
            int r1 = r0.length
            if (r1 > 0) goto L_0x0042
            return
        L_0x0042:
            if (r9 != 0) goto L_0x0045
            return
        L_0x0045:
            r1 = 0
            android.os.StatFs r2 = new android.os.StatFs
            java.lang.String r10 = r10.getCanonicalPath()
            r2.<init>(r10)
            int r10 = r2.getBlockSize()
            int r2 = r2.getAvailableBlocks()
            long r4 = (long) r2
            long r6 = (long) r10
            long r6 = r6 * r4
            int r10 = r0.length
            long r4 = (long) r10
            int r10 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r10 >= 0) goto L_0x0062
            return
        L_0x0062:
            java.io.ByteArrayInputStream r10 = new java.io.ByteArrayInputStream     // Catch:{ IOException -> 0x008d }
            r10.<init>(r0)     // Catch:{ IOException -> 0x008d }
            r0 = 100
            byte[] r1 = new byte[r0]     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
        L_0x006b:
            int r2 = r10.read(r1, r3, r0)     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            if (r2 < 0) goto L_0x0075
            r9.write(r1, r3, r2)     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            goto L_0x006b
        L_0x0075:
            java.io.FileDescriptor r9 = r9.getFD()
            r9.sync()
            r10.close()     // Catch:{ IOException -> 0x0080 }
            return
        L_0x0080:
            r9 = move-exception
            r9.printStackTrace()
            return
        L_0x0085:
            r0 = move-exception
            r1 = r10
            goto L_0x00aa
        L_0x0088:
            r0 = move-exception
            r1 = r10
            goto L_0x008e
        L_0x008b:
            r0 = move-exception
            goto L_0x00aa
        L_0x008d:
            r0 = move-exception
        L_0x008e:
            java.lang.String r10 = TAG     // Catch:{ all -> 0x008b }
            java.lang.String r2 = "error in write files"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x008b }
            com.taobao.accs.utl.ALog.e(r10, r2, r0, r3)     // Catch:{ all -> 0x008b }
            java.io.FileDescriptor r9 = r9.getFD()
            r9.sync()
            if (r1 == 0) goto L_0x00a9
            r1.close()     // Catch:{ IOException -> 0x00a4 }
            goto L_0x00a9
        L_0x00a4:
            r9 = move-exception
            r9.printStackTrace()
            return
        L_0x00a9:
            return
        L_0x00aa:
            java.io.FileDescriptor r9 = r9.getFD()
            r9.sync()
            if (r1 == 0) goto L_0x00bb
            r1.close()     // Catch:{ IOException -> 0x00b7 }
            goto L_0x00bb
        L_0x00b7:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00bb:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.eudemon.EudemonManager.writeFileInRelease(java.io.FileOutputStream, java.io.File):void");
    }

    private void lauchIt(String str) {
        StringBuilder sb = new StringBuilder();
        execShell("", "chmod 500 ".concat(String.valueOf(str)), sb);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(Token.SEPARATOR);
        sb2.append(getBinParam());
        execShell("", sb2.toString(), sb);
        String str2 = TAG;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(Token.SEPARATOR);
        sb3.append(getBinParam());
        ALog.w(str2, sb3.toString(), new Object[0]);
    }

    private String getBinParam() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder(PKG_INSTALL_DIR);
        sb2.append(this.mContext.getPackageName());
        String sb3 = sb2.toString();
        StringBuilder sb4 = new StringBuilder("-s \"");
        sb4.append(sb3);
        sb4.append("/lib/\" ");
        sb.append(sb4.toString());
        sb.append("-n \"runServer\" ");
        StringBuilder sb5 = new StringBuilder("-p \"");
        sb5.append(getAmParams());
        sb5.append("\" ");
        sb.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder("-f \"");
        sb6.append(sb3);
        sb6.append("\" ");
        sb.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder("-t \"");
        sb7.append(this.timeout);
        sb7.append("\" ");
        sb.append(sb7.toString());
        sb.append("-c \"agoo.pid\" ");
        if (this.checkPackagePath != null) {
            StringBuilder sb8 = new StringBuilder("-P ");
            sb8.append(this.checkPackagePath);
            sb8.append(Token.SEPARATOR);
            sb.append(sb8.toString());
        }
        if (this.reportKey != null) {
            StringBuilder sb9 = new StringBuilder("-K ");
            sb9.append(this.reportKey);
            sb9.append(Token.SEPARATOR);
            sb.append(sb9.toString());
        }
        if (this.ua != null) {
            StringBuilder sb10 = new StringBuilder("-U ");
            sb10.append(this.ua);
            sb10.append(Token.SEPARATOR);
            sb.append(sb10.toString());
        }
        if (this.reportLoc != null) {
            StringBuilder sb11 = new StringBuilder("-L ");
            sb11.append(this.reportLoc);
            sb11.append(Token.SEPARATOR);
            sb.append(sb11.toString());
        }
        StringBuilder sb12 = new StringBuilder("-D ");
        sb12.append(getReportData());
        sb12.append(Token.SEPARATOR);
        sb.append(sb12.toString());
        if (this.serverIp != null) {
            StringBuilder sb13 = new StringBuilder("-I ");
            sb13.append(this.serverIp);
            sb13.append(Token.SEPARATOR);
            sb.append(sb13.toString());
        }
        if (this.serverPort > 0) {
            StringBuilder sb14 = new StringBuilder("-O ");
            sb14.append(this.serverPort);
            sb14.append(Token.SEPARATOR);
            sb.append(sb14.toString());
        }
        String proxyHost = UtilityImpl.getProxyHost(this.mContext);
        int proxyPort = UtilityImpl.getProxyPort(this.mContext);
        if (proxyHost != null && proxyPort > 0) {
            StringBuilder sb15 = new StringBuilder("-X ");
            sb15.append(proxyHost);
            sb15.append(Token.SEPARATOR);
            sb.append(sb15.toString());
            StringBuilder sb16 = new StringBuilder("-Y ");
            sb16.append(proxyPort);
            sb16.append(Token.SEPARATOR);
            sb.append(sb16.toString());
        }
        if (this.isTransparentProxy) {
            sb.append("-T ");
        }
        sb.append("-Z ");
        return sb.toString();
    }

    private String getReportData() {
        String deviceId = UtilityImpl.getDeviceId(this.mContext);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = "null";
        }
        StringBuilder sb = new StringBuilder("{\"package\":\"");
        sb.append(this.mContext.getPackageName());
        sb.append("\",\"appKey\":\"");
        sb.append(this.appKey);
        sb.append("\",\"utdid\":\"");
        sb.append(deviceId);
        sb.append("\",\"sdkVersion\":\"");
        sb.append(this.sdkVersion);
        sb.append("\"}");
        String sb2 = sb.toString();
        try {
            return URLEncoder.encode(sb2, "UTF-8");
        } catch (Throwable unused) {
            ALog.e(TAG, "getReportData failed for url encode, data:".concat(String.valueOf(sb2)), new Object[0]);
            return sb2;
        }
    }

    private String getAmParams() {
        StringBuilder sb = new StringBuilder();
        sb.append(AMPARAMS.replaceAll("\\{packname\\}", this.mContext.getApplicationContext().getPackageName()));
        if (VERSION.SDK_INT > 15) {
            sb.append(" --user 0");
        }
        return sb.toString();
    }

    private void doReportDaemonStat(String str, int i, int i2, String str2, String str3, int i3) {
        StringBuilder sb = new StringBuilder("AndroidVer=");
        sb.append(VERSION.RELEASE);
        sb.append("&Model=");
        sb.append(Build.MODEL);
        sb.append("&AndroidSdk=");
        sb.append(VERSION.SDK_INT);
        sb.append("&AccsVer=221&Appkey=");
        sb.append(this.appKey);
        sb.append("&PullCount=");
        sb.append(str2);
        sb.append("&Pid=");
        sb.append(str);
        sb.append("&StartTime=");
        sb.append(i);
        sb.append("&EndTime=");
        sb.append(i2);
        sb.append("&ExitCode=");
        sb.append(str3);
        sb.append("&AliveTime=");
        sb.append(i3);
        UTMini.getInstance().commitEvent(66001, "EUDEMON_ENDSTAT", sb.toString());
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(20:3|4|5|6|7|8|9|(3:10|11|(3:13|(4:15|(2:17|(2:19|97)(1:20))(1:21)|22|98)(1:96)|94)(1:95))|23|24|25|26|27|28|29|30|31|32|33|34) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x00e1 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:30:0x00e4 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x00e7 */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0108 A[SYNTHETIC, Splitter:B:55:0x0108] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x010d A[SYNTHETIC, Splitter:B:59:0x010d] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0112 A[SYNTHETIC, Splitter:B:63:0x0112] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0117 A[SYNTHETIC, Splitter:B:67:0x0117] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0121 A[SYNTHETIC, Splitter:B:77:0x0121] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0126 A[SYNTHETIC, Splitter:B:81:0x0126] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x012b A[SYNTHETIC, Splitter:B:85:0x012b] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0130 A[SYNTHETIC, Splitter:B:89:0x0130] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0134 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void reportDaemonStat() {
        /*
            r18 = this;
            r8 = r18
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "/data/data/"
            r1.<init>(r2)
            android.content.Context r2 = r8.mContext
            java.lang.String r2 = r2.getPackageName()
            r1.append(r2)
            java.lang.String r2 = "/eudemon"
            r1.append(r2)
            java.lang.String r9 = r1.toString()
            java.io.File r1 = new java.io.File
            r1.<init>(r9)
            boolean r2 = r1.exists()
            if (r2 != 0) goto L_0x0027
            return
        L_0x0027:
            java.io.FileInputStream r11 = new java.io.FileInputStream     // Catch:{ Exception -> 0x011b, all -> 0x0100 }
            r11.<init>(r1)     // Catch:{ Exception -> 0x011b, all -> 0x0100 }
            java.io.InputStreamReader r12 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00fe, all -> 0x00fa }
            r12.<init>(r11)     // Catch:{ Exception -> 0x00fe, all -> 0x00fa }
            java.io.BufferedReader r13 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            r13.<init>(r12)     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            java.lang.String r1 = ""
            r14 = r1
        L_0x0039:
            java.lang.String r1 = r13.readLine()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            if (r1 == 0) goto L_0x00ca
            java.lang.String r2 = "\\|"
            java.lang.String[] r2 = r1.split(r2)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            int r3 = r2.length     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r4 = 5
            if (r3 != r4) goto L_0x0039
            r3 = 0
            r3 = r2[r3]     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.String r3 = r3.trim()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r4 = 1
            r4 = r2[r4]     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.String r4 = r4.trim()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            int r4 = r4.intValue()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r5 = 2
            r6 = r2[r5]     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.String r6 = r6.trim()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            int r6 = r6.intValue()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            int r7 = r6 - r4
            r15 = 3
            r15 = r2[r15]     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.String r15 = r15.trim()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r16 = 4
            r2 = r2[r16]     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.String r2 = r2.trim()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.String r10 = "0"
            boolean r10 = r2.equals(r10)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            if (r10 == 0) goto L_0x00bb
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.String r5 = "/proc"
            r10.<init>(r5, r3)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r17 = r2
            java.lang.String r2 = "pidfile:"
            r5.<init>(r2)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r5.append(r10)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            boolean r2 = r10.exists()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            if (r2 == 0) goto L_0x00b5
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r2.<init>()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r2.append(r14)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r2.append(r1)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.String r1 = "\n"
            r2.append(r1)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.lang.String r14 = r2.toString()     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            goto L_0x0039
        L_0x00b5:
            int r1 = r8.timeout     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r2 = 2
            int r1 = r1 / r2
            int r7 = r7 + r1
            goto L_0x00bd
        L_0x00bb:
            r17 = r2
        L_0x00bd:
            r1 = r8
            r10 = r17
            r2 = r3
            r3 = r4
            r4 = r6
            r5 = r15
            r6 = r10
            r1.doReportDaemonStat(r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            goto L_0x0039
        L_0x00ca:
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r1.<init>(r9)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            r10.<init>(r1)     // Catch:{ Exception -> 0x00f2, all -> 0x00ee }
            byte[] r1 = r14.getBytes()     // Catch:{ Exception -> 0x011f, all -> 0x00eb }
            r10.write(r1)     // Catch:{ Exception -> 0x011f, all -> 0x00eb }
            r13.close()     // Catch:{ Exception -> 0x011f, all -> 0x00eb }
            r13.close()     // Catch:{ Throwable -> 0x00e1 }
        L_0x00e1:
            r12.close()     // Catch:{ Throwable -> 0x00e4 }
        L_0x00e4:
            r11.close()     // Catch:{ IOException -> 0x00e7 }
        L_0x00e7:
            r10.close()     // Catch:{ IOException -> 0x00ea }
        L_0x00ea:
            return
        L_0x00eb:
            r0 = move-exception
            r1 = r0
            goto L_0x0106
        L_0x00ee:
            r0 = move-exception
            r1 = r0
            r10 = 0
            goto L_0x0106
        L_0x00f2:
            r10 = 0
            goto L_0x011f
        L_0x00f4:
            r0 = move-exception
            r1 = r0
            r10 = 0
            goto L_0x0105
        L_0x00f8:
            r10 = 0
            goto L_0x011e
        L_0x00fa:
            r0 = move-exception
            r1 = r0
            r10 = 0
            goto L_0x0104
        L_0x00fe:
            r10 = 0
            goto L_0x011d
        L_0x0100:
            r0 = move-exception
            r1 = r0
            r10 = 0
            r11 = 0
        L_0x0104:
            r12 = 0
        L_0x0105:
            r13 = 0
        L_0x0106:
            if (r13 == 0) goto L_0x010b
            r13.close()     // Catch:{ Throwable -> 0x010b }
        L_0x010b:
            if (r12 == 0) goto L_0x0110
            r12.close()     // Catch:{ Throwable -> 0x0110 }
        L_0x0110:
            if (r11 == 0) goto L_0x0115
            r11.close()     // Catch:{ IOException -> 0x0115 }
        L_0x0115:
            if (r10 == 0) goto L_0x011a
            r10.close()     // Catch:{ IOException -> 0x011a }
        L_0x011a:
            throw r1
        L_0x011b:
            r10 = 0
            r11 = 0
        L_0x011d:
            r12 = 0
        L_0x011e:
            r13 = 0
        L_0x011f:
            if (r13 == 0) goto L_0x0124
            r13.close()     // Catch:{ Throwable -> 0x0124 }
        L_0x0124:
            if (r12 == 0) goto L_0x0129
            r12.close()     // Catch:{ Throwable -> 0x0129 }
        L_0x0129:
            if (r11 == 0) goto L_0x012e
            r11.close()     // Catch:{ IOException -> 0x012e }
        L_0x012e:
            if (r10 == 0) goto L_0x0134
            r10.close()     // Catch:{ IOException -> 0x0133 }
        L_0x0133:
            return
        L_0x0134:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.eudemon.EudemonManager.reportDaemonStat():void");
    }

    public void start() {
        Message obtain = Message.obtain();
        obtain.what = 0;
        this.hanlder.sendMessage(obtain);
    }

    private void startInternal() {
        String str = TAG;
        StringBuilder sb = new StringBuilder("api level is:");
        sb.append(VERSION.SDK_INT);
        ALog.d(str, sb.toString(), new Object[0]);
        createAlarm(this.mContext);
        if (VERSION.SDK_INT < 20) {
            try {
                String copyBinToFiles = copyBinToFiles();
                reportDaemonStat();
                lauchIt(copyBinToFiles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        UTMini.getInstance().commitEvent(66001, "EUDEMON_START", "");
    }

    public void stop() {
        Message obtain = Message.obtain();
        obtain.what = -1;
        this.hanlder.sendMessage(obtain);
    }

    private void stopInternal() {
        StringBuilder sb = new StringBuilder(PKG_INSTALL_DIR);
        sb.append(this.mContext.getPackageName());
        File file = new File(sb.toString(), PID);
        if (file.exists()) {
            file.delete();
        }
    }

    public static final PendingIntent getIntentForWakeup(Context context) {
        Intent intent = new Intent();
        StringBuilder sb = new StringBuilder();
        sb.append(context.getApplicationContext().getPackageName());
        sb.append(".intent.action.COCKROACH");
        intent.setAction(sb.toString());
        intent.putExtra("cockroach", "cockroach-PPreotect");
        intent.putExtra("pack", context.getApplicationContext().getPackageName());
        return PendingIntent.getService(context, 0, intent, 134217728);
    }

    public static void createAlarm(Context context) {
        int i = Calendar.getInstance().get(11);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager != null) {
            PendingIntent intentForWakeup = getIntentForWakeup(context);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (i > 23 || i < 6) {
                ALog.w(TAG, "time is night, do not wakeup cpu", new Object[0]);
                createNightAlarm(context, alarmManager, intentForWakeup, elapsedRealtime);
                return;
            }
            ALog.w(TAG, "time is daytime, wakeup cpu for keeping connecntion", new Object[0]);
            createDayAlarm(context, alarmManager, intentForWakeup, elapsedRealtime);
        }
    }

    private static void createDayAlarm(Context context, AlarmManager alarmManager, PendingIntent pendingIntent, long j) {
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            alarmManager.setRepeating(2, ((long) (timeoutAlarmDay * 1000)) + j, (long) (timeoutAlarmDay * 1000), pendingIntent);
        }
    }

    private static void createNightAlarm(Context context, AlarmManager alarmManager, PendingIntent pendingIntent, long j) {
        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(3, j + ((long) (timeoutAlarmNight * 1000)), (long) (timeoutAlarmNight * 1000), pendingIntent);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0069 A[SYNTHETIC, Splitter:B:25:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0070 A[SYNTHETIC, Splitter:B:30:0x0070] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void checkAndRenewPidFile(android.content.Context r5) {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x007f }
            java.io.File r5 = r5.getFilesDir()     // Catch:{ Throwable -> 0x007f }
            java.lang.String r2 = "agoo.pid"
            r1.<init>(r5, r2)     // Catch:{ Throwable -> 0x007f }
            java.lang.String r5 = TAG     // Catch:{ Throwable -> 0x007f }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x007f }
            java.lang.String r3 = "pid path:"
            r2.<init>(r3)     // Catch:{ Throwable -> 0x007f }
            java.lang.String r3 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x007f }
            r2.append(r3)     // Catch:{ Throwable -> 0x007f }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x007f }
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x007f }
            com.taobao.accs.utl.ALog.d(r5, r2, r3)     // Catch:{ Throwable -> 0x007f }
            boolean r5 = r1.exists()     // Catch:{ Throwable -> 0x007f }
            if (r5 == 0) goto L_0x002e
            r1.delete()     // Catch:{ Throwable -> 0x007f }
        L_0x002e:
            r1.createNewFile()     // Catch:{ Throwable -> 0x007f }
            r5 = 0
            int r2 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x005b, all -> 0x0057 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Throwable -> 0x005b, all -> 0x0057 }
            r3.<init>(r1)     // Catch:{ Throwable -> 0x005b, all -> 0x0057 }
            java.lang.String r5 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0055 }
            char[] r5 = r5.toCharArray()     // Catch:{ Throwable -> 0x0055 }
            r3.write(r5)     // Catch:{ Throwable -> 0x0055 }
            r3.close()     // Catch:{ Throwable -> 0x004a }
            return
        L_0x004a:
            r5 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r2 = "error"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.taobao.accs.utl.ALog.e(r1, r2, r5, r0)
            return
        L_0x0055:
            r5 = move-exception
            goto L_0x005e
        L_0x0057:
            r1 = move-exception
            r3 = r5
            r5 = r1
            goto L_0x006e
        L_0x005b:
            r1 = move-exception
            r3 = r5
            r5 = r1
        L_0x005e:
            java.lang.String r1 = TAG     // Catch:{ all -> 0x006d }
            java.lang.String r2 = "save pid error"
            java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ all -> 0x006d }
            com.taobao.accs.utl.ALog.e(r1, r2, r5, r4)     // Catch:{ all -> 0x006d }
            if (r3 == 0) goto L_0x006c
            r3.close()     // Catch:{ Throwable -> 0x004a }
        L_0x006c:
            return
        L_0x006d:
            r5 = move-exception
        L_0x006e:
            if (r3 == 0) goto L_0x007e
            r3.close()     // Catch:{ Throwable -> 0x0074 }
            goto L_0x007e
        L_0x0074:
            r1 = move-exception
            java.lang.String r2 = TAG
            java.lang.String r3 = "error"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.taobao.accs.utl.ALog.e(r2, r3, r1, r0)
        L_0x007e:
            throw r5
        L_0x007f:
            r5 = move-exception
            java.lang.String r1 = TAG
            java.lang.String r2 = "error in create file"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.taobao.accs.utl.ALog.e(r1, r2, r5, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.eudemon.EudemonManager.checkAndRenewPidFile(android.content.Context):void");
    }

    public boolean handleMessage(Message message) {
        try {
            if (message.what == 0) {
                startInternal();
            } else if (message.what == -1) {
                stopInternal();
            }
        } catch (Throwable th) {
            ALog.e(TAG, "handleMessage error", th, new Object[0]);
        }
        return true;
    }

    public static boolean execShell(String str, String str2, StringBuilder sb) {
        try {
            Process exec = Runtime.getRuntime().exec("sh");
            DataInputStream dataInputStream = new DataInputStream(exec.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(exec.getOutputStream());
            if (str != null && !"".equals(str.trim())) {
                StringBuilder sb2 = new StringBuilder("cd ");
                sb2.append(str);
                sb2.append("\n");
                dataOutputStream.writeBytes(sb2.toString());
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str2);
            sb3.append(" &\n");
            dataOutputStream.writeBytes(sb3.toString());
            dataOutputStream.writeBytes("exit \n");
            dataOutputStream.flush();
            exec.waitFor();
            byte[] bArr = new byte[dataInputStream.available()];
            dataInputStream.read(bArr);
            String str3 = new String(bArr);
            if (str3.length() != 0) {
                sb.append(str3);
            }
            return true;
        } catch (Exception e) {
            StringBuilder sb4 = new StringBuilder("Exception:");
            sb4.append(e.getMessage());
            sb.append(sb4.toString());
            return false;
        }
    }
}
