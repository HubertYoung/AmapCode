package com.alipay.mobile.framework.service.common.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TimerTaskService;
import com.alipay.mobile.framework.service.common.TimerTaskService.OnTickListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimerTaskServiceImpl extends TimerTaskService {
    long baseTime = 10;
    Object lock = new Object();
    Map<onTickListenerWrapper, Integer> mCountDownListeners = new HashMap();
    Map<onTickListenerWrapper, Integer> mListeners = new HashMap();
    TaskScheduleService mScheduleService = ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName()));
    Timer mTimer = null;
    boolean scheduleStarted = false;
    ScheduledFuture<?> scheduleTask = null;

    /* renamed from: com.alipay.mobile.framework.service.common.impl.TimerTaskServiceImpl$1 reason: invalid class name */
    /* synthetic */ class AnonymousClass1 {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);
    }

    class TickTimerTask implements Runnable {
        private TickTimerTask() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        /* synthetic */ TickTimerTask(TimerTaskServiceImpl x0, AnonymousClass1 x1) {
            this();
        }

        public void run() {
            a();
        }

        private void a() {
            synchronized (TimerTaskServiceImpl.this.lock) {
                for (Entry entry : TimerTaskServiceImpl.this.mCountDownListeners.entrySet()) {
                    if (((Integer) entry.getValue()).equals(Integer.valueOf(1))) {
                        TimerTaskServiceImpl.this.mCountDownListeners.put(entry.getKey(), TimerTaskServiceImpl.this.mListeners.get(entry.getKey()));
                        a((onTickListenerWrapper) entry.getKey());
                    } else {
                        TimerTaskServiceImpl.this.mCountDownListeners.put(entry.getKey(), Integer.valueOf(((Integer) entry.getValue()).intValue() - 1));
                    }
                }
            }
        }

        private void a(onTickListenerWrapper wrapper) {
            if (wrapper.getListener() != null) {
                if (wrapper.getHandler() == null) {
                    a(wrapper.getListener());
                } else {
                    a(wrapper.getListener(), wrapper.getHandler());
                }
            }
        }

        private void a(final OnTickListener listener) {
            TimerTaskServiceImpl.this.mScheduleService.parallelExecute(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    try {
                        listener.onTick();
                    } catch (Exception e) {
                        listener.onException(e);
                    }
                }
            }, "TimerTaskServiceImpl");
        }

        private void a(final OnTickListener listener, Handler handler) {
            handler.post(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    try {
                        listener.onTick();
                    } catch (Exception e) {
                        listener.onException(e);
                    }
                }
            });
        }
    }

    class onTickListenerWrapper {
        Handler mHandler;
        OnTickListener mListener;

        public onTickListenerWrapper(OnTickListener mListener2, Handler mHandler2) {
            this.mListener = mListener2;
            this.mHandler = mHandler2;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public OnTickListener getListener() {
            return this.mListener;
        }

        public void setListener(OnTickListener mListener2) {
            this.mListener = mListener2;
        }

        public Handler getHandler() {
            return this.mHandler;
        }

        public void setHandler(Handler mHandler2) {
            this.mHandler = mHandler2;
        }
    }

    public TimerTaskServiceImpl() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean registerListener(OnTickListener listener, int timeUnit, Handler handler) {
        onTickListenerWrapper wrapper;
        if (listener == null) {
            throw new IllegalArgumentException();
        } else if (timeUnit <= 0) {
            throw new IllegalArgumentException();
        } else if (a(listener)) {
            return false;
        } else {
            if (handler == null) {
                wrapper = new onTickListenerWrapper(listener, new Handler(Looper.getMainLooper()));
            } else {
                wrapper = new onTickListenerWrapper(listener, handler);
            }
            synchronized (this.lock) {
                this.mListeners.put(wrapper, Integer.valueOf(timeUnit));
                this.mCountDownListeners.put(wrapper, Integer.valueOf(timeUnit));
                if (!this.scheduleStarted) {
                    this.scheduleTask = this.mScheduleService.scheduleWithFixedDelay(new TickTimerTask(this, null), "TimerTaskService", 0, this.baseTime, TimeUnit.MINUTES);
                    this.scheduleStarted = true;
                }
            }
            return true;
        }
    }

    private boolean a(OnTickListener listener) {
        for (onTickListenerWrapper listener2 : this.mListeners.keySet()) {
            if (listener2.getListener() == listener) {
                return true;
            }
        }
        return false;
    }

    public boolean registerListener(OnTickListener listener, int timeUnit) {
        if (listener == null) {
            throw new IllegalArgumentException();
        } else if (timeUnit <= 0) {
            throw new IllegalArgumentException();
        } else if (a(listener)) {
            return false;
        } else {
            onTickListenerWrapper wrapper = new onTickListenerWrapper(listener, null);
            synchronized (this.lock) {
                this.mListeners.put(wrapper, Integer.valueOf(timeUnit));
                this.mCountDownListeners.put(wrapper, Integer.valueOf(timeUnit));
                if (!this.scheduleStarted) {
                    this.scheduleTask = this.mScheduleService.scheduleWithFixedDelay(new TickTimerTask(this, null), "TimerTaskService", 0, this.baseTime, TimeUnit.MINUTES);
                    this.scheduleStarted = true;
                }
            }
            return true;
        }
    }

    public boolean unregisterListener(OnTickListener listener) {
        if (listener == null) {
            return false;
        }
        for (onTickListenerWrapper tickListenerWrapper : this.mListeners.keySet()) {
            if (tickListenerWrapper.getListener() == listener) {
                synchronized (this.lock) {
                    this.mListeners.remove(tickListenerWrapper);
                    this.mCountDownListeners.remove(tickListenerWrapper);
                }
                a();
                return true;
            }
        }
        return false;
    }

    private void a() {
        if (this.mListeners.isEmpty()) {
            b();
        }
    }

    private void b() {
        if (this.scheduleTask != null) {
            this.scheduleTask.cancel(false);
            this.scheduleStarted = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle paramBundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle paramBundle) {
        this.mListeners = null;
        this.mCountDownListeners = null;
        b();
    }
}
