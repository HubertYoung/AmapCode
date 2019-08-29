package com.autonavi.minimap.ajx3.util;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogRecorder {
    private static final String FILE;
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    /* access modifiers changed from: private */
    public static final ConcurrentLinkedQueue<String> MESSAGE_QUEUE = new ConcurrentLinkedQueue<>();
    private static final int MSG_RUN = 1;
    private static LogRecorder sInstance;
    /* access modifiers changed from: private */
    public RandomAccessFile accessFile = null;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
        }
    };
    /* access modifiers changed from: private */
    public boolean mIsRuning = false;
    /* access modifiers changed from: private */
    public AsyncTask<String, Integer, Boolean> mTask = null;

    class LogRecorderTask extends AsyncTask<String, Integer, Boolean> {
        private LogRecorderTask() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(String... strArr) {
            LogRecorder.this.init();
            try {
                LogRecorder.this.accessFile.seek(LogRecorder.this.accessFile.length());
            } catch (Exception unused) {
            }
            for (String str = (String) LogRecorder.MESSAGE_QUEUE.poll(); str != null; str = (String) LogRecorder.MESSAGE_QUEUE.poll()) {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        LogRecorder.this.accessFile.write(str.getBytes());
                    } catch (Exception unused2) {
                    }
                }
            }
            LogRecorder.this.close();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            LogRecorder.this.mTask = null;
            LogRecorder.this.mIsRuning = false;
        }
    }

    public static void log(String str, String str2) {
    }

    private void run() {
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getExternalStorageDirectory());
        sb.append("/autonavi/ajx_log/ajx_log.log");
        FILE = sb.toString();
    }

    private static LogRecorder getInstance() {
        if (sInstance == null) {
            sInstance = new LogRecorder();
        }
        return sInstance;
    }

    private static String getFormat(Date date) {
        return new SimpleDateFormat(FORMAT, Locale.CHINA).format(date);
    }

    /* access modifiers changed from: private */
    public void init() {
        File file = new File(FILE);
        if (!file.exists()) {
            File file2 = new File(file.getParent());
            if (!file2.exists()) {
                file2.mkdirs();
            }
        }
        if (this.accessFile == null) {
            try {
                this.accessFile = new RandomAccessFile(FILE, "rw");
            } catch (FileNotFoundException unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void close() {
        if (this.accessFile != null) {
            try {
                this.accessFile.close();
            } catch (IOException unused) {
            }
            this.accessFile = null;
        }
    }
}
