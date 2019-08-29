package com.autonavi.minimap.ajx3.debug;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.util.FileUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventLogRecorder {
    private static final String FILE;
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    /* access modifiers changed from: private */
    public static final ConcurrentLinkedQueue<String> MESSAGE_QUEUE = new ConcurrentLinkedQueue<>();
    private static final int MSG_RUN = 1;
    private static EventLogRecorder sInstance;
    /* access modifiers changed from: private */
    public RandomAccessFile accessFile = null;
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == 1 && !EventLogRecorder.this.mIsRuning) {
                EventLogRecorder.this.mIsRuning = true;
                try {
                    EventLogRecorder.this.mTask = new LogRecorderTask();
                    EventLogRecorder.this.mTask.execute(new String[]{""});
                } catch (Exception unused) {
                    EventLogRecorder.this.mIsRuning = false;
                }
            }
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
            EventLogRecorder.this.init();
            try {
                EventLogRecorder.this.accessFile.seek(EventLogRecorder.this.accessFile.length());
            } catch (Exception unused) {
            }
            for (String str = (String) EventLogRecorder.MESSAGE_QUEUE.poll(); str != null; str = (String) EventLogRecorder.MESSAGE_QUEUE.poll()) {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        EventLogRecorder.this.accessFile.write(str.getBytes());
                    } catch (Exception unused2) {
                    }
                }
            }
            EventLogRecorder.this.close();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean bool) {
            EventLogRecorder.this.mTask = null;
            EventLogRecorder.this.mIsRuning = false;
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getExternalStorageDirectory());
        sb.append("/autonavi/ajx_log/event_log.log");
        FILE = sb.toString();
    }

    private static EventLogRecorder getInstance() {
        if (sInstance == null) {
            sInstance = new EventLogRecorder();
        }
        return sInstance;
    }

    private static String getFormat(Date date) {
        return new SimpleDateFormat(FORMAT, Locale.CHINA).format(date);
    }

    public static void log(String str, String str2) {
        TextUtils.isEmpty(str);
        String format = getFormat(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(" ---------- >>>>>>>>> \n");
        sb.append(format);
        sb.append(" : ");
        sb.append(str);
        sb.append(" : \n           ");
        sb.append(str2);
        sb.append("\n");
        sb.append(" <<<<<<<<< ---------- \n");
        MESSAGE_QUEUE.offer(sb.toString());
        getInstance().run();
    }

    private void run() {
        if (!this.mIsRuning) {
            this.mHandler.sendEmptyMessage(1);
        }
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
