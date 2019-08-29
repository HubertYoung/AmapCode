package com.autonavi.minimap.ajx3.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Window;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.debug.AjxDebugConstant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogHelper implements Runnable {
    public static final String DEFAULT_LEVEL = "I";
    public static final String DEFAULT_TAG = "AJX3-JS-LOG";
    private static final ConcurrentLinkedQueue<String> ENGINE_MESSAGE_QUEUE = new ConcurrentLinkedQueue<>();
    public static boolean IS_DEBUG = true;
    private static boolean IS_LOG = false;
    public static final String LOG_TAG = "ajxlog";
    private static final int MESSAGE_COUNT_LIMITS = 200;
    private static final ConcurrentLinkedQueue<String> MESSAGE_QUEUE = new ConcurrentLinkedQueue<>();
    public static final String STOP_MESSAGE = "LOG-HELPER-STOP-THREAD:";
    /* access modifiers changed from: private */
    public static Context mContext;
    private static Map<String, Long> mTimeMap = new HashMap();
    private static LogHelper sLogHelper;
    private boolean isStop = false;
    private Thread mThread;

    public static void d(String str) {
    }

    public static void e(String str) {
    }

    public static void i(String str) {
    }

    public static void loge(String str) {
    }

    public static void loge(String str, String str2) {
    }

    public static void showErrorMsg(String str, Context context) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void init(boolean r1, android.content.Context r2) {
        /*
            java.lang.Class<com.autonavi.minimap.ajx3.util.LogHelper> r0 = com.autonavi.minimap.ajx3.util.LogHelper.class
            monitor-enter(r0)
            IS_LOG = r1     // Catch:{ all -> 0x001f }
            if (r1 != 0) goto L_0x0009
            monitor-exit(r0)
            return
        L_0x0009:
            android.content.Context r1 = r2.getApplicationContext()     // Catch:{ all -> 0x001f }
            mContext = r1     // Catch:{ all -> 0x001f }
            com.autonavi.minimap.ajx3.util.LogHelper r1 = sLogHelper     // Catch:{ all -> 0x001f }
            if (r1 != 0) goto L_0x001d
            com.autonavi.minimap.ajx3.util.LogHelper r1 = new com.autonavi.minimap.ajx3.util.LogHelper     // Catch:{ all -> 0x001f }
            r1.<init>()     // Catch:{ all -> 0x001f }
            sLogHelper = r1     // Catch:{ all -> 0x001f }
            r1.start()     // Catch:{ all -> 0x001f }
        L_0x001d:
            monitor-exit(r0)
            return
        L_0x001f:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.LogHelper.init(boolean, android.content.Context):void");
    }

    public static void throwRunTimeException(String str) {
        if (IS_LOG) {
            throw new RuntimeException(str);
        }
    }

    public static void addEngineLog(String str) {
        if (IS_LOG) {
        }
    }

    public static void addEngineLoge(String str) {
        if (IS_LOG) {
            StringBuilder sb = new StringBuilder("<font color='#ff0000'>");
            sb.append(str.replace(Token.SEPARATOR, "&nbsp;"));
            sb.append("</font>");
            addEngineLog(sb.toString());
        }
    }

    public static void log(String str) {
        if (IS_LOG) {
            addAllLogQueue(str.replace(Token.SEPARATOR, "&nbsp;"));
        }
    }

    public static synchronized void destroy() {
        synchronized (LogHelper.class) {
            if (sLogHelper != null) {
                sLogHelper.stop();
                sLogHelper = null;
            }
        }
    }

    private LogHelper() {
    }

    private void start() {
        if (this.mThread == null) {
            this.mThread = new Thread(this);
            this.mThread.start();
        }
    }

    private void stop() {
        this.isStop = true;
        if (this.mThread != null) {
            new StringBuilder(STOP_MESSAGE).append(this.mThread.getId());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0124 A[SYNTHETIC, Splitter:B:52:0x0124] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0131  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0136 A[SYNTHETIC, Splitter:B:62:0x0136] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r12 = this;
            r0 = 0
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x0116, all -> 0x0111 }
            java.lang.String r2 = "logcat"
            java.lang.String r3 = "AJX3-JS-LOG:I *:S"
            java.lang.String[] r2 = new java.lang.String[]{r2, r3}     // Catch:{ Exception -> 0x0116, all -> 0x0111 }
            java.lang.Process r1 = r1.exec(r2)     // Catch:{ Exception -> 0x0116, all -> 0x0111 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x010c, all -> 0x0107 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x010c, all -> 0x0107 }
            java.io.InputStream r4 = r1.getInputStream()     // Catch:{ Exception -> 0x010c, all -> 0x0107 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x010c, all -> 0x0107 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x010c, all -> 0x0107 }
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x0105 }
            java.lang.String r3 = "logcat"
            java.lang.String r4 = "-c"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4}     // Catch:{ Exception -> 0x0105 }
            r0.exec(r3)     // Catch:{ Exception -> 0x0105 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0105 }
            r0.<init>()     // Catch:{ Exception -> 0x0105 }
            r3 = 0
            r4 = 0
        L_0x0035:
            boolean r5 = r12.isStop     // Catch:{ Exception -> 0x0105 }
            if (r5 != 0) goto L_0x00f7
            java.lang.String r5 = r2.readLine()     // Catch:{ Exception -> 0x0105 }
            if (r5 == 0) goto L_0x00f7
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = "LOG-HELPER-STOP-THREAD:"
            r6.<init>(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.Thread r7 = r12.mThread     // Catch:{ Exception -> 0x0105 }
            long r7 = r7.getId()     // Catch:{ Exception -> 0x0105 }
            r6.append(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0105 }
            boolean r6 = r5.contains(r6)     // Catch:{ Exception -> 0x0105 }
            if (r6 != 0) goto L_0x00f7
            java.lang.String r6 = "AJX3-JS-LOG"
            int r6 = r5.indexOf(r6)     // Catch:{ Exception -> 0x0105 }
            if (r6 <= 0) goto L_0x00f2
            java.lang.String r7 = "W"
            int r7 = r5.indexOf(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r8 = "E"
            int r8 = r5.indexOf(r8)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r9 = "AJX3-JS-LOG"
            int r9 = r5.indexOf(r9)     // Catch:{ Exception -> 0x0105 }
            int r9 = r9 + 11
            java.lang.String r5 = r5.substring(r9)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r9 = ":"
            int r9 = r5.indexOf(r9)     // Catch:{ Exception -> 0x0105 }
            if (r9 <= 0) goto L_0x0099
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0105 }
            java.lang.String r10 = "I"
            r9.<init>(r10)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r10 = ":"
            int r10 = r5.indexOf(r10)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r5 = r5.substring(r10)     // Catch:{ Exception -> 0x0105 }
            r9.append(r5)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r5 = r9.toString()     // Catch:{ Exception -> 0x0105 }
        L_0x0099:
            if (r8 < 0) goto L_0x00d3
            if (r8 >= r6) goto L_0x00d3
            r0.append(r5)     // Catch:{ Exception -> 0x0105 }
            int r4 = r4 + 1
            r6 = 5
            if (r4 < r6) goto L_0x00b7
            java.lang.String r6 = r0.toString()     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = "^"
            boolean r6 = r6.contains(r7)     // Catch:{ Exception -> 0x0105 }
            if (r6 == 0) goto L_0x00b7
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0105 }
            r0.<init>()     // Catch:{ Exception -> 0x0105 }
            r4 = 0
        L_0x00b7:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = "<font color='#ff0000'>"
            r6.<init>(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = " "
            java.lang.String r8 = "&nbsp;"
            java.lang.String r5 = r5.replace(r7, r8)     // Catch:{ Exception -> 0x0105 }
            r6.append(r5)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r5 = "</font>"
            r6.append(r5)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x0105 }
            goto L_0x00f2
        L_0x00d3:
            if (r7 < 0) goto L_0x00f2
            if (r7 >= r6) goto L_0x00f2
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = "<font color='#ff8000'>"
            r6.<init>(r7)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r7 = " "
            java.lang.String r8 = "&nbsp;"
            java.lang.String r5 = r5.replace(r7, r8)     // Catch:{ Exception -> 0x0105 }
            r6.append(r5)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r5 = "</font>"
            r6.append(r5)     // Catch:{ Exception -> 0x0105 }
            java.lang.String r5 = r6.toString()     // Catch:{ Exception -> 0x0105 }
        L_0x00f2:
            r12.addEngineLogQueue(r5)     // Catch:{ Exception -> 0x0105 }
            goto L_0x0035
        L_0x00f7:
            if (r1 == 0) goto L_0x00fc
            r1.destroy()
        L_0x00fc:
            r2.close()     // Catch:{ IOException -> 0x0100 }
            return
        L_0x0100:
            r0 = move-exception
            r0.printStackTrace()
            return
        L_0x0105:
            r0 = move-exception
            goto L_0x011a
        L_0x0107:
            r2 = move-exception
            r11 = r2
            r2 = r0
            r0 = r11
            goto L_0x012f
        L_0x010c:
            r2 = move-exception
            r11 = r2
            r2 = r0
            r0 = r11
            goto L_0x011a
        L_0x0111:
            r1 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
            goto L_0x012f
        L_0x0116:
            r1 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
        L_0x011a:
            r0.printStackTrace()     // Catch:{ all -> 0x012e }
            if (r1 == 0) goto L_0x0122
            r1.destroy()
        L_0x0122:
            if (r2 == 0) goto L_0x012d
            r2.close()     // Catch:{ IOException -> 0x0128 }
            goto L_0x012d
        L_0x0128:
            r0 = move-exception
            r0.printStackTrace()
            return
        L_0x012d:
            return
        L_0x012e:
            r0 = move-exception
        L_0x012f:
            if (r1 == 0) goto L_0x0134
            r1.destroy()
        L_0x0134:
            if (r2 == 0) goto L_0x013e
            r2.close()     // Catch:{ IOException -> 0x013a }
            goto L_0x013e
        L_0x013a:
            r1 = move-exception
            r1.printStackTrace()
        L_0x013e:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.LogHelper.run():void");
    }

    public static void showErrorMsg2(String str) {
        if (AjxDebugConstant.DEBUGGER_OPEN) {
            debugShowErrorMsg(str);
        }
    }

    private static void debugShowErrorMsg(final String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                if (LogHelper.mContext != null) {
                    String str = str;
                    if (str.contains("Caused by:")) {
                        str = str.substring(str.indexOf("Caused by:"), str.length());
                    }
                    Builder builder = new Builder(LogHelper.mContext);
                    builder.setTitle("Error!");
                    if (!TextUtils.isEmpty(str)) {
                        builder.setMessage(str);
                    }
                    builder.setNegativeButton(17039360, new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (dialogInterface != null) {
                                dialogInterface.dismiss();
                            }
                        }
                    });
                    builder.setPositiveButton(17039370, new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (dialogInterface != null) {
                                dialogInterface.dismiss();
                            }
                        }
                    });
                    AlertDialog create = builder.create();
                    Window window = create.getWindow();
                    if (window != null) {
                        window.setType(2003);
                        create.setCancelable(false);
                        try {
                            create.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private static void addAllLogQueue(@NonNull String str) {
        if (IS_LOG) {
            if (MESSAGE_QUEUE.size() > 200) {
                MESSAGE_QUEUE.poll();
            }
            MESSAGE_QUEUE.add(str);
        }
    }

    public void addEngineLogQueue(@NonNull String str) {
        if (IS_LOG) {
            if (ENGINE_MESSAGE_QUEUE.size() > 200) {
                ENGINE_MESSAGE_QUEUE.poll();
            }
            ENGINE_MESSAGE_QUEUE.add(str);
            addAllLogQueue(str);
        }
    }

    public static void clearLog() {
        if (IS_LOG) {
            MESSAGE_QUEUE.clear();
            ENGINE_MESSAGE_QUEUE.clear();
        }
    }

    public static String getEngineLogString() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = ENGINE_MESSAGE_QUEUE.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("<br/>");
        }
        return sb.toString();
    }

    public static String getLogString() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = MESSAGE_QUEUE.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("<br/>");
        }
        return sb.toString();
    }

    public static void jniLog(String str) {
        if (IS_LOG) {
            Ajx.getInstance().jniLog(str);
        }
    }

    public static void jniLogForce(String str) {
        Ajx.getInstance().jniLog(str);
    }

    public static void start(String str) {
        mTimeMap.put(str, Long.valueOf(System.currentTimeMillis()));
    }

    public static synchronized int costTime(String str) {
        synchronized (LogHelper.class) {
            if (mTimeMap != null && mTimeMap.containsKey(str)) {
                long longValue = mTimeMap.get(str).longValue();
                if (longValue > 0) {
                    int currentTimeMillis = (int) (System.currentTimeMillis() - longValue);
                    return currentTimeMillis;
                }
            }
            return -1;
        }
    }
}
