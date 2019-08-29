package com.autonavi.minimap.onekeycheck.action;

import com.alibaba.fastjson.JSONObject;
import com.autonavi.minimap.onekeycheck.exception.OneKeyCheckException;
import com.autonavi.minimap.onekeycheck.module.ResultData;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public abstract class BaseAction {
    public volatile State a;
    protected ActionListener b;
    protected ScheduledExecutorService c;

    public BaseAction(State state, ActionListener actionListener) {
        this.a = state == null ? new State(0) : state;
        setActionListener(actionListener);
    }

    public BaseAction(ActionListener actionListener) {
        this(null, actionListener);
    }

    public void setActionListener(ActionListener actionListener) {
        this.b = actionListener;
    }

    public void callbackException(OneKeyCheckException oneKeyCheckException) {
        if (this.b != null) {
            synchronized (this.b) {
                if (oneKeyCheckException != null) {
                    try {
                        this.b.onException(this, oneKeyCheckException);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public void callbackOnResponse(ResultData resultData) {
        if (this.b != null) {
            synchronized (this.b) {
                this.b.onResponse(this, resultData);
            }
        }
    }

    public State getState() {
        return this.a;
    }

    public void start() {
        this.a.update(1);
    }

    public void stop() {
        this.a.update(4);
    }

    public void finish() {
        this.a.update(4);
    }

    public boolean isStart() {
        return this.a != null && this.a.getState() > 0;
    }

    public <T> T parse(String str, Class<T> cls) {
        return JSONObject.parseObject(str, cls);
    }

    public void startWaitTimer() {
        startWaitTimer(new Runnable() {
            public final void run() {
                OneKeyCheckException oneKeyCheckException = new OneKeyCheckException("检测失败，请稍后重试。", -1);
                BaseAction.this.finish();
                BaseAction.this.cancelWaitTimer();
                BaseAction.this.callbackException(oneKeyCheckException);
            }
        });
    }

    public void startWaitTimer(Runnable runnable) {
        if (this.c == null || this.c.isShutdown()) {
            this.c = Executors.newSingleThreadScheduledExecutor();
        }
        if (runnable != null) {
            this.c.scheduleAtFixedRate(runnable, 0, 60, TimeUnit.SECONDS);
        }
    }

    public void cancelWaitTimer() {
        if (this.c != null) {
            this.c.shutdown();
        }
    }
}
