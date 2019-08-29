package com.alipay.mobile.bqcscanservice.impl;

import android.os.Bundle;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.ServiceConfig;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MPaasScanServiceImpl extends BQCScanServiceImpl {
    public static final String TAG = "MPaasScanServiceImpl";

    public void serviceInit() {
        Bundle bundle = new Bundle();
        bundle.putString(ServiceConfig.KEY_NOT_USE_SCAN_CODE_STATE, "yes");
        super.onCreate(bundle);
    }

    public void serviceOut() {
        super.onDestroy(null);
    }

    public void setTraceLogger(TraceLogger traceLogger) {
        try {
            Method method = Class.forName(LoggerFactory.class.getName()).getDeclaredMethod("setCurTraceLogger", new Class[]{TraceLogger.class});
            if (method != null) {
                method.invoke(null, new Object[]{traceLogger});
            }
        } catch (ClassNotFoundException e) {
            Logger.e(TAG, "setLoggerError(ClassNotFoundException)");
        } catch (NoSuchMethodException e2) {
            Logger.e(TAG, "setLoggerError(NoSuchMethodException)");
        } catch (IllegalAccessException e3) {
            Logger.e(TAG, "setLoggerError(IllegalAccessException)");
        } catch (InvocationTargetException e4) {
            Logger.e(TAG, "setLoggerError(InvocationTargetException)");
        }
    }

    public void cleanup(long postcode) {
        super.cleanup(postcode);
        setTraceLogger(null);
    }
}
