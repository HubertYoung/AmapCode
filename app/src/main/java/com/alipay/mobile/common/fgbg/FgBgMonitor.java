package com.alipay.mobile.common.fgbg;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public abstract class FgBgMonitor {
    private static volatile FgBgMonitor instance;

    public interface FgBgListener {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        void onMoveToBackground(ProcessInfo processInfo);

        void onMoveToForeground(ProcessInfo processInfo);
    }

    public interface ProcessInfo {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        @NonNull
        String getProcessName();

        @NonNull
        String getTopActivity();

        @NonNull
        ProcessType getType();
    }

    public enum ProcessType {
        MAIN,
        PUSH,
        TOOLS,
        EXT,
        SSS,
        LITE,
        UNKNOWN
    }

    public interface ScreenListener {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        void onScreenInteractive();

        void onScreenUninteractive();
    }

    @Nullable
    public abstract ProcessInfo getForegroundProcess();

    /* access modifiers changed from: 0000 */
    public abstract void onProcessFgBgWatcherInited();

    public abstract void registerFgBgListener(@NonNull FgBgListener fgBgListener);

    public abstract void registerScreenListener(@NonNull ScreenListener screenListener);

    public abstract void unregisterFgBgListener(@NonNull FgBgListener fgBgListener);

    public abstract void unregisterScreenListener(@NonNull ScreenListener screenListener);

    public FgBgMonitor() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    @NonNull
    public static FgBgMonitor getInstance(Context context) {
        if (instance == null) {
            synchronized (FgBgMonitor.class) {
                if (instance == null) {
                    instance = new FgBgMonitorImpl(context);
                }
            }
        }
        return instance;
    }
}
