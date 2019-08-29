package com.alipay.mobile.common.transportext.biz.sync;

import com.alipay.mobile.common.amnet.api.model.AcceptedData;
import com.alipay.mobile.common.amnet.api.model.AmnetPost;
import com.alipay.mobile.common.transport.ext.MMTPException;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import com.alipay.mobile.common.transportext.biz.util.LogUtilAmnet;
import com.alipay.mobile.framework.locale.LocaleHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SyncManager {
    private static final String LOGTAG = "amnet_SyncManager";
    private static final String SYNC_LINK_CALLBACK = "com.alipay.mobile.rome.syncsdk.zlink2.adaptor.AmnetCallbackImpl";
    private static final String SYNC_SERVICE_INIT = "com.alipay.mobile.rome.syncservice.control.ReflectInvoke";
    private static final Map<String, Method> linkCallbackMethod = new ConcurrentHashMap();

    public static void onInitialize() {
        try {
            Class.forName(SYNC_SERVICE_INIT).getMethod("init", new Class[0]).invoke(null, new Object[0]);
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "onInitialize: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.d(LOGTAG, "onInitialize: [ TException=" + e + " ]");
        }
    }

    public static void onAcceptedDataEvent(AcceptedData acceptedData) {
        try {
            Method m = linkCallbackMethod.get("onAcceptedDataEvent");
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod("onAcceptedDataEvent", new Class[]{Byte.TYPE, Map.class, byte[].class, Double.TYPE});
                linkCallbackMethod.put("onAcceptedDataEvent", m);
            }
            m.invoke(null, new Object[]{Byte.valueOf(acceptedData.channel), acceptedData.headers, acceptedData.data, Double.valueOf(acceptedData.readTiming)});
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "onAcceptedDataEvent: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "onAcceptedDataEvent: [ TException=" + e + " ]");
        }
    }

    public static void change(int state) {
        try {
            Method m = linkCallbackMethod.get(LocaleHelper.SPKEY_CHANGE_FLAG);
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod(LocaleHelper.SPKEY_CHANGE_FLAG, new Class[]{Integer.TYPE});
                linkCallbackMethod.put(LocaleHelper.SPKEY_CHANGE_FLAG, m);
            }
            m.invoke(null, new Object[]{Integer.valueOf(state)});
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "change: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "change: [ TException=" + e + " ]");
        }
    }

    public static void panic(int err, String inf) {
        try {
            Method m = linkCallbackMethod.get("panic");
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod("panic", new Class[]{Integer.TYPE, String.class});
                linkCallbackMethod.put("panic", m);
            }
            m.invoke(null, new Object[]{Integer.valueOf(err), inf});
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "panic: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "panic: [ TException=" + e + " ]");
        }
    }

    public static void collectSyncChannel(Map<String, String> param) {
        try {
            Method m = linkCallbackMethod.get("collectSyncChannel");
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod("collectSyncChannel", new Class[]{Map.class});
                linkCallbackMethod.put("collectSyncChannel", m);
            }
            m.invoke(null, new Object[]{param});
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "collectSyncChannel: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "collectSyncChannel: [ TException=" + e + " ]");
        }
    }

    public static void collectCommonChannel(Map<String, String> param) {
        try {
            Method m = linkCallbackMethod.get("collectCommonChannel");
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod("collectCommonChannel", new Class[]{Map.class});
                linkCallbackMethod.put("collectCommonChannel", m);
            }
            m.invoke(null, new Object[]{param});
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "collectCommonChannel: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "collectCommonChannel: [ TException=" + e + " ]");
        }
    }

    public static void report(String key, double val) {
        try {
            Method m = linkCallbackMethod.get("report");
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod("report", new Class[]{String.class, Double.TYPE});
                linkCallbackMethod.put("report", m);
            }
            m.invoke(null, new Object[]{key, Double.valueOf(val)});
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "report: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "report: [ TException=" + e + " ]");
        }
    }

    public static void notifyInitOk() {
        try {
            Method m = linkCallbackMethod.get("notifyInitOk");
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod("notifyInitOk", new Class[0]);
                linkCallbackMethod.put("notifyInitOk", m);
            }
            m.invoke(null, new Object[0]);
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "report: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "notifyInitOk: [ TException=" + e + " ]");
        }
    }

    public static void checkLinkState(int state) {
        try {
            Method m = linkCallbackMethod.get("checkLinkState");
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod("checkLinkState", new Class[]{Integer.TYPE});
                linkCallbackMethod.put("checkLinkState", m);
            }
            m.invoke(null, new Object[]{Integer.valueOf(state)});
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "checkLinkState: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "checkLinkState: [ TException=" + e + " ]");
        }
    }

    public static void sendData(byte[] syncData, Map<String, String> header) {
        LogUtilAmnet.d(LOGTAG, "sendData: [ syncData len=" + syncData.length + " ]");
        AmnetPost amnetPost = new AmnetPost();
        amnetPost.body = syncData;
        amnetPost.channel = 2;
        amnetPost.header = header;
        amnetPost.toBizSys = true;
        try {
            AmnetHelper.post(amnetPost);
        } catch (MMTPException ex) {
            LogUtilAmnet.d(LOGTAG, "Exception occur" + ex.getErrorInfo());
        }
    }

    public static void sendData(AmnetPost amnetPost) {
        try {
            LogUtilAmnet.d(LOGTAG, "sendData amnetPost: [ syncData len=" + amnetPost.body.length + " ]");
        } catch (Exception e) {
        }
        try {
            AmnetHelper.post(amnetPost);
        } catch (MMTPException ex) {
            LogUtilAmnet.d(LOGTAG, "sendData amnetPost.  Exception occur" + ex.getErrorInfo());
        }
    }

    public static boolean isMmtpSwitchOn() {
        boolean ret = NetworkTunnelStrategy.getInstance().isCanUseAmnet();
        LogUtilAmnet.d(LOGTAG, "isMmtpSwitchOn: " + ret);
        return ret;
    }

    public static void notifyShortLinkStart() {
        try {
            Method m = linkCallbackMethod.get("openShortLinkMode");
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod("openShortLinkMode", new Class[0]);
                linkCallbackMethod.put("openShortLinkMode", m);
            }
            m.invoke(null, new Object[0]);
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "notifyShortLinkStart: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "closeShortLinkMode: [ TException=" + e + " ]");
        }
    }

    public static void notifyShortLinkStop() {
        try {
            Method m = linkCallbackMethod.get("closeShortLinkMode");
            if (m == null) {
                m = Class.forName(SYNC_LINK_CALLBACK).getMethod("closeShortLinkMode", new Class[0]);
                linkCallbackMethod.put("closeShortLinkMode", m);
            }
            m.invoke(null, new Object[0]);
        } catch (InvocationTargetException exception) {
            LogUtilAmnet.e(LOGTAG, "notifyShortLinkStop: InvocationTargetException", exception.getTargetException());
        } catch (Throwable e) {
            LogUtilAmnet.e(LOGTAG, "closeShortLinkMode: [ TException=" + e + " ]");
        }
    }
}
