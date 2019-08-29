package com.alibaba.appmonitor.delegate;

import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import com.alibaba.appmonitor.event.EventRepo;
import com.alibaba.appmonitor.event.EventType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

class CommitTask implements Runnable {
    private static final String TAG = "CommitTask";
    private static boolean init = false;
    private static HashMap<Integer, ScheduledFuture> mFutureMap = new HashMap<>();
    private static Map<Integer, CommitTask> uploadTasks;
    private int eventId;
    private int interval = 300000;
    private long startTime;

    static void init() {
        EventType[] values;
        if (!init) {
            Logger.d((String) TAG, "init StatisticsAlarmEvent");
            uploadTasks = new ConcurrentHashMap();
            for (EventType eventType : EventType.values()) {
                if (eventType.isOpen()) {
                    int eventId2 = eventType.getEventId();
                    CommitTask commitTask = new CommitTask(eventId2, eventType.getForegroundStatisticsInterval() * 1000);
                    uploadTasks.put(Integer.valueOf(eventId2), commitTask);
                    mFutureMap.put(Integer.valueOf(eventId2), TaskExecutor.getInstance().schedule(mFutureMap.get(Integer.valueOf(eventId2)), commitTask, (long) commitTask.interval));
                }
            }
            init = true;
        }
    }

    static void destroy() {
        for (Integer num : mFutureMap.keySet()) {
            ScheduledFuture scheduledFuture = mFutureMap.get(num);
            if (scheduledFuture != null && !scheduledFuture.isDone()) {
                scheduledFuture.cancel(true);
            }
        }
        init = false;
        uploadTasks = null;
        mFutureMap.clear();
    }

    static void setStatisticsInterval(int i, int i2) {
        synchronized (uploadTasks) {
            CommitTask commitTask = uploadTasks.get(Integer.valueOf(i));
            if (commitTask == null) {
                if (i2 > 0) {
                    CommitTask commitTask2 = new CommitTask(i, i2 * 1000);
                    uploadTasks.put(Integer.valueOf(i), commitTask2);
                    mFutureMap.put(Integer.valueOf(i), TaskExecutor.getInstance().schedule(mFutureMap.get(Integer.valueOf(i)), commitTask2, (long) commitTask2.interval));
                }
            } else if (i2 > 0) {
                int i3 = i2 * 1000;
                if (commitTask.interval != i3) {
                    commitTask.interval = i3;
                    long currentTimeMillis = System.currentTimeMillis();
                    long j = ((long) commitTask.interval) - (currentTimeMillis - commitTask.startTime);
                    if (j < 0) {
                        j = 0;
                    }
                    ScheduledFuture scheduledFuture = mFutureMap.get(Integer.valueOf(i));
                    TaskExecutor.getInstance().schedule(scheduledFuture, commitTask, j);
                    mFutureMap.put(Integer.valueOf(i), scheduledFuture);
                    commitTask.startTime = currentTimeMillis;
                }
            } else {
                uploadTasks.remove(Integer.valueOf(i));
            }
        }
    }

    private CommitTask(int i, int i2) {
        this.eventId = i;
        this.interval = i2;
        this.startTime = System.currentTimeMillis();
    }

    public void run() {
        Logger.d((String) TAG, "check&commit event", Integer.valueOf(this.eventId));
        EventRepo.getRepo().uploadEvent(this.eventId);
        if (uploadTasks.containsValue(this)) {
            this.startTime = System.currentTimeMillis();
            mFutureMap.put(Integer.valueOf(this.eventId), TaskExecutor.getInstance().schedule(mFutureMap.get(Integer.valueOf(this.eventId)), this, (long) this.interval));
        }
    }

    static void uploadAllEvent() {
        for (EventType eventId2 : EventType.values()) {
            EventRepo.getRepo().uploadEvent(eventId2.getEventId());
        }
    }
}
