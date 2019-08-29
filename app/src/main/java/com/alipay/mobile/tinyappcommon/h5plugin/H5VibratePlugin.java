package com.alipay.mobile.tinyappcommon.h5plugin;

import android.annotation.SuppressLint;
import android.os.Vibrator;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5VibratePlugin extends H5SimplePlugin {
    static final String TAG = H5VibratePlugin.class.getSimpleName();
    static final String VIBRATE_LONG = "vibrateLong";
    static final String VIBRATE_SHORT = "vibrateShort";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(VIBRATE_LONG);
        filter.addAction(VIBRATE_SHORT);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (TextUtils.isEmpty(action)) {
            return false;
        }
        char c = 65535;
        switch (action.hashCode()) {
            case -1360764789:
                if (action.equals(VIBRATE_LONG)) {
                    c = 0;
                    break;
                }
                break;
            case 772222029:
                if (action.equals(VIBRATE_SHORT)) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                vibrate(true, context, event);
                return true;
            case 1:
                vibrate(false, context, event);
                return true;
            default:
                return false;
        }
    }

    @SuppressLint({"MissingPermission"})
    static void vibrate(boolean pLong, H5BridgeContext bridgeContext, H5Event event) {
        try {
            Vibrator v = (Vibrator) H5Utils.getContext().getSystemService("vibrator");
            if (v != null) {
                v.vibrate(pLong ? 400 : 40);
            }
            bridgeContext.sendSuccess();
        } catch (Throwable e) {
            H5Log.e(TAG, e);
            bridgeContext.sendError(event, Error.UNKNOWN_ERROR);
        }
    }
}
