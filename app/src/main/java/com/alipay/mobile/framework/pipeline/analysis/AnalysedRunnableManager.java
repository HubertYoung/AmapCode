package com.alipay.mobile.framework.pipeline.analysis;

import android.os.Process;
import android.os.SystemClock;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AnalysedRunnableManager {
    public static final int TID_SIZE = 8192;
    private static boolean a;
    private static AnalysedRunnableInfo[] b;
    private static WeakReference<RecordListener>[] c;

    public interface RecordListener {
        boolean isTargetRecord(int i, String str);

        void onEndRecord(AnalysedRunnableInfo analysedRunnableInfo);

        void onStartRecord(AnalysedRunnableInfo analysedRunnableInfo);
    }

    public static void startWorking() {
        if (b == null) {
            b = new AnalysedRunnableInfo[8192];
        }
        if (c == null) {
            c = new WeakReference[2];
        }
    }

    public static void stopWorking() {
        b = null;
        c = null;
    }

    public static boolean addRecordListener(RecordListener recordListener) {
        WeakReference[] recordListeners = c;
        if (recordListeners == null) {
            return false;
        }
        for (int i = 0; i < 2; i++) {
            WeakReference existListenerRefer = recordListeners[i];
            if (existListenerRefer == null || existListenerRefer.get() == null) {
                recordListeners[i] = new WeakReference(recordListener);
                return true;
            }
        }
        return false;
    }

    public static boolean removeRecordListener(RecordListener recordListener) {
        WeakReference[] recordListeners = c;
        if (recordListeners == null) {
            return false;
        }
        int i = 0;
        while (i < 2) {
            WeakReference existListenerRefer = recordListeners[i];
            if (existListenerRefer == null || existListenerRefer.get() != recordListener) {
                i++;
            } else {
                recordListeners[i] = null;
                return true;
            }
        }
        return false;
    }

    public static void startRecord(String runnableName) {
        AnalysedRunnableInfo[] runnableInfos = b;
        if (runnableInfos != null) {
            int tid = Process.myTid();
            int index = tid - Process.myPid();
            if (index >= 0 && index < 8192) {
                AnalysedRunnableInfo runnableInfo = runnableInfos[index];
                if (runnableInfo == null) {
                    runnableInfo = new AnalysedRunnableInfo();
                    runnableInfos[index] = runnableInfo;
                } else {
                    runnableInfo.reset();
                }
                runnableInfo.isRunning = true;
                runnableInfo.tid = tid;
                runnableInfo.name = runnableName;
                runnableInfo.startThreadName = Thread.currentThread().getName();
                runnableInfo.startRunningUptime = SystemClock.uptimeMillis();
                WeakReference[] recordListeners = c;
                if (recordListeners != null) {
                    for (int i = 0; i < 2; i++) {
                        try {
                            WeakReference recordListenerRefer = recordListeners[i];
                            if (recordListenerRefer != null) {
                                RecordListener recordListener = (RecordListener) recordListenerRefer.get();
                                if (recordListener != null && recordListener.isTargetRecord(tid, runnableName)) {
                                    recordListener.onStartRecord(runnableInfo.fullClone());
                                }
                            }
                        } catch (Throwable t) {
                            if (!a) {
                                a = true;
                                TraceLogger.e((String) "AnalysedRunnableManager", t);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void endRecord(String runnableName) {
        AnalysedRunnableInfo[] runnableInfos = b;
        if (runnableInfos != null) {
            int tid = Process.myTid();
            int index = tid - Process.myPid();
            if (index >= 0 && index < 8192) {
                AnalysedRunnableInfo runnableInfo = runnableInfos[index];
                if (runnableInfo != null && runnableInfo.isRunning) {
                    WeakReference[] recordListeners = c;
                    if (recordListeners != null) {
                        for (int i = 0; i < 2; i++) {
                            try {
                                WeakReference recordListenerRefer = recordListeners[i];
                                if (recordListenerRefer != null) {
                                    RecordListener recordListener = (RecordListener) recordListenerRefer.get();
                                    if (recordListener != null && recordListener.isTargetRecord(tid, runnableName)) {
                                        if (runnableInfo.endThreadName == null) {
                                            runnableInfo.endThreadName = Thread.currentThread().getName();
                                        }
                                        if (runnableInfo.endRunningUptime < 0) {
                                            runnableInfo.endRunningUptime = SystemClock.uptimeMillis();
                                        }
                                        recordListener.onEndRecord(runnableInfo.fullClone());
                                    }
                                }
                            } catch (Throwable t) {
                                if (!a) {
                                    a = true;
                                    TraceLogger.e((String) "AnalysedRunnableManager", t);
                                }
                            }
                        }
                    }
                }
                if (runnableInfo != null) {
                    runnableInfo.reset();
                }
            }
        }
    }

    public static List<AnalysedRunnableInfo> getRunnableInfos() {
        AnalysedRunnableInfo[] runnableInfos = b;
        if (runnableInfos == null) {
            return null;
        }
        List runnableList = null;
        for (int i = 0; i < 8192; i++) {
            AnalysedRunnableInfo runnableInfo = runnableInfos[i];
            if (runnableInfo != null && runnableInfo.isRunning) {
                if (runnableList == null) {
                    runnableList = new ArrayList();
                }
                runnableList.add(runnableInfo.fullClone());
            }
        }
        return runnableList;
    }
}
