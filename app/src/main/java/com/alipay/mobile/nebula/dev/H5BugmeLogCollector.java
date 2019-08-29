package com.alipay.mobile.nebula.dev;

import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.filecache.DiskUtil;
import com.alipay.mobile.nebula.process.ProcessLock;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class H5BugmeLogCollector {
    private static final String TAG = "H5BugmeLogCollector";
    private static final String UPLOAD_TAG = "NebulaDebug:dumpDebugData";
    private static int dumpLimit = 300;
    private static boolean hasReadFile = false;
    private static boolean needDump = false;
    private static String sFilePath = null;
    private static long sLastFlushAppLogTime = 0;
    private static final Queue<String> sQueue = new LinkedBlockingQueue();

    static {
        initConfig();
    }

    private static void initConfig() {
        if (!H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_FORCE_NO_DUMP, false)) {
            H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (provider != null) {
                String config = provider.getConfig("h5_bugmeConfig");
                if (TextUtils.isEmpty(config)) {
                    H5Log.d(TAG, "not get config");
                    return;
                }
                try {
                    JSONObject jo = JSON.parseObject(config);
                    needDump = jo.getBoolean("dump").booleanValue();
                    dumpLimit = jo.getIntValue("dumpLimit");
                } catch (Throwable th) {
                    H5Log.d(TAG, "config init error");
                }
            }
        }
    }

    public static boolean enabled() {
        return needDump;
    }

    @WorkerThread
    public static void enqueueLog(JSONObject logObject) {
        if (logObject != null && enabled()) {
            String log = logObject.toString();
            if (System.currentTimeMillis() - sLastFlushAppLogTime < 5000) {
                H5Log.d(UPLOAD_TAG, log);
            }
            synchronized (sQueue) {
                if (!hasReadFile) {
                    prepare();
                }
                if (sQueue.size() >= dumpLimit) {
                    sQueue.poll();
                }
                sQueue.offer(log);
            }
        }
    }

    public static void flushFile() {
        if (enabled()) {
            H5Log.d(TAG, "flush " + sQueue.size() + " bugme logs");
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public final void run() {
                    long start = System.currentTimeMillis();
                    List<String> logs = H5BugmeLogCollector.readLog();
                    if (logs != null && !logs.isEmpty()) {
                        BufferedWriter writer = null;
                        ProcessLock lock = new ProcessLock(H5BugmeLogCollector.getLockFilePath());
                        try {
                            lock.lock();
                            BufferedWriter writer2 = new BufferedWriter(new FileWriter(H5BugmeLogCollector.getFilePath(), false));
                            try {
                                for (String log : logs) {
                                    writer2.write(log);
                                    writer2.newLine();
                                }
                                H5IOUtils.closeQuietly(writer2);
                                lock.unlock();
                                H5Log.d(H5BugmeLogCollector.TAG, "flush log cost: " + (System.currentTimeMillis() - start));
                            } catch (Throwable th) {
                                th = th;
                                writer = writer2;
                                H5IOUtils.closeQuietly(writer);
                                lock.unlock();
                                H5Log.d(H5BugmeLogCollector.TAG, "flush log cost: " + (System.currentTimeMillis() - start));
                                throw th;
                            }
                        } catch (Throwable th2) {
                            t = th2;
                            H5Log.e((String) H5BugmeLogCollector.TAG, "write bugme log file error " + t);
                            H5IOUtils.closeQuietly(writer);
                            lock.unlock();
                            H5Log.d(H5BugmeLogCollector.TAG, "flush log cost: " + (System.currentTimeMillis() - start));
                        }
                    }
                }
            });
        }
    }

    @WorkerThread
    public static void flushAppLog() {
        if (enabled()) {
            long start = System.currentTimeMillis();
            sLastFlushAppLogTime = start;
            List<String> logs = readLog();
            int size = logs == null ? 0 : logs.size();
            if (size > 0) {
                for (String log : logs) {
                    H5Log.d(UPLOAD_TAG, log);
                }
            }
            H5Log.d(TAG, "flush applog " + size + " cost : " + (System.currentTimeMillis() - start));
        }
    }

    public static List<String> readLog() {
        ArrayList arrayList;
        if (!enabled()) {
            return null;
        }
        synchronized (sQueue) {
            if (!hasReadFile) {
                prepare();
            }
            arrayList = new ArrayList(sQueue);
        }
        return arrayList;
    }

    private static void prepare() {
        long start = System.currentTimeMillis();
        BufferedReader br = null;
        ProcessLock lock = new ProcessLock(getLockFilePath());
        try {
            lock.lock();
            BufferedReader br2 = new BufferedReader(new FileReader(getFilePath()));
            while (true) {
                try {
                    String line = br2.readLine();
                    if (line != null) {
                        sQueue.offer(line);
                    } else {
                        H5IOUtils.closeQuietly(br2);
                        hasReadFile = true;
                        lock.unlock();
                        H5Log.d(TAG, "read log file cost: " + (System.currentTimeMillis() - start));
                        BufferedReader bufferedReader = br2;
                        return;
                    }
                } catch (Throwable th) {
                    th = th;
                    br = br2;
                    H5IOUtils.closeQuietly(br);
                    hasReadFile = true;
                    lock.unlock();
                    H5Log.d(TAG, "read log file cost: " + (System.currentTimeMillis() - start));
                    throw th;
                }
            }
        } catch (Throwable th2) {
            t = th2;
            H5Log.e((String) TAG, "read bugme local log file error" + t);
            H5IOUtils.closeQuietly(br);
            hasReadFile = true;
            lock.unlock();
            H5Log.d(TAG, "read log file cost: " + (System.currentTimeMillis() - start));
        }
    }

    /* access modifiers changed from: private */
    public static String getFilePath() {
        if (sFilePath != null) {
            return sFilePath;
        }
        String fpath = DiskUtil.getSubDir(H5Utils.getContext(), "/h5/bugme/dump-" + H5Utils.getProcessName() + ".log");
        if (!H5FileUtil.exists(fpath)) {
            H5FileUtil.create(fpath);
        }
        sFilePath = fpath;
        return fpath;
    }

    /* access modifiers changed from: private */
    public static String getLockFilePath() {
        return getFilePath() + ".lock";
    }
}
