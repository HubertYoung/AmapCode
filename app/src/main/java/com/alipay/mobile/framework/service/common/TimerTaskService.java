package com.alipay.mobile.framework.service.common;

import android.os.Handler;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.service.CommonService;

public abstract class TimerTaskService extends CommonService {

    public interface OnTickListener {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        void onException(Exception exc);

        void onTick();
    }

    public abstract boolean registerListener(OnTickListener onTickListener, int i);

    public abstract boolean registerListener(OnTickListener onTickListener, int i, Handler handler);

    public abstract boolean unregisterListener(OnTickListener onTickListener);

    public TimerTaskService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
