package com.alipay.mobile.common.transport.ext;

import android.content.Context;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.ext.diagnose.NetworkDiagnoseService;
import com.alipay.mobile.common.transport.ext.diagnose.eastereggs.DiagnoseResult;
import com.alipay.mobile.common.transport.iprank.mng.speedtest.BaseSpeedTest;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetBeanFactory;
import java.lang.reflect.InvocationTargetException;

public class ExtTransportOffice {
    public static final String AMNET_HELPER = "com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper";
    public static final String DIAGNOSE_BY_SYSTEMCALL = "com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseBySystemCall";
    public static final String DIAGNOSE_BY_USERCALL = "com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseByUserCall";
    public static final String DIAGNOSE_LAUNCH = "launch";
    public static final String DIAGNOSE_NOTIFY = "diagnoseNotify";
    public static final String EXT_TRANSPORT_CONN_CHECKER = "com.alipay.mobile.common.transportext.biz.shared.ExtTransportConnChecker";
    public static final String EXT_TRANSPORT_MANAGER_IMPL = "com.alipay.mobile.common.transportext.biz.shared.ExtTransportManagerImpl";
    public static final String IS_CONNECTION_AVAILABLE = "isConnectionAvailable";
    public static final String METHOD_SETSCENE = "setScene";
    public static final String MMTPSCENEMANAGER = "com.alipay.mobile.common.transportext.biz.mmtp.MMTPSceneManager";
    public static final String NETWORK_CHECK_CLASS_NAME = "com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkCheck";
    public static final String NETWORK_DIAGONAL_SERVICE = "com.alipay.mobile.common.transportext.biz.diagnose.NetworkDiagnoseServiceImpl";
    public static final String SPEED_TEST_IMPL = "com.alipay.mobile.common.transportext.biz.iprank.SpeeTestImpl";
    private static boolean a = false;
    private static ExtTransportOffice b;
    private Context c;
    private Object d;

    private ExtTransportOffice() {
    }

    public static final ExtTransportOffice getInstance() {
        if (b != null) {
            return b;
        }
        synchronized (ExtTransportOffice.class) {
            try {
                if (b == null) {
                    b = new ExtTransportOffice();
                }
            }
        }
        return b;
    }

    public void init(Context context) {
        this.c = context;
        if (!a) {
            a = true;
            try {
                MiscUtils.invokeMethod(getExtTransportManager(), "init", new Class[]{Context.class}, new Object[]{this.c});
            } catch (Throwable e) {
                if (e instanceof InvocationTargetException) {
                    LogCatUtil.info("ExtTransportOffice", "[init] " + ((InvocationTargetException) e).getTargetException().toString());
                } else {
                    LogCatUtil.info("ExtTransportOffice", "[init] " + e.toString());
                }
                if (MiscUtils.isDebugger(this.c)) {
                    return;
                }
                if (e.getClass().getName().contains("MMTPException")) {
                    TransportConfigureManager.getInstance().setValue(TransportConfigureItem.AMNET_SWITCH, "0,0,0");
                } else if (e.getCause() != null && e.getCause().getClass().getName().contains("MMTPException")) {
                    TransportConfigureManager.getInstance().setValue(TransportConfigureItem.AMNET_SWITCH, "0,0,0");
                }
            }
        }
    }

    public boolean isEnableExtTransport(Context context) {
        try {
            if (MiscUtils.isEnableExtTransport(context) && getExtTransportManager() != null) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            LogCatUtil.info("ExtTransportOffice", "isEnableExtTransport: " + e.toString());
            return false;
        }
    }

    public ExtTransportClient getExtTransportClient(Context context, TransportContext transportContext) {
        if (!isEnableExtTransport(context)) {
            return null;
        }
        try {
            return (ExtTransportClient) MiscUtils.invokeMethod(getExtTransportManager(), "getExtTransportClient", new Class[]{Context.class, TransportContext.class}, new Object[]{this.c, transportContext});
        } catch (Throwable e) {
            if (MiscUtils.isDebugger(this.c)) {
                LogCatUtil.error("ExtTransportOffice", "No enable extTransport. ", e);
            }
            return null;
        }
    }

    public Object getExtTransportManager() {
        if (this.d != null) {
            return this.d;
        }
        synchronized (this) {
            if (this.d != null) {
                Object obj = this.d;
                return obj;
            }
            try {
                this.d = Class.forName(EXT_TRANSPORT_MANAGER_IMPL).newInstance();
            } catch (Throwable e) {
                if (MiscUtils.isDebugger(this.c)) {
                    LogCatUtil.info("ExtTransportOffice", "No enable extTransport." + e.toString());
                }
            }
            return this.d;
        }
    }

    @Deprecated
    public ProtobufCodec getProtobufCodec() {
        try {
            return (ProtobufCodec) Class.forName("com.alipay.mobile.common.transportext.biz.shared.ProtobufCodecImpl").newInstance();
        } catch (Throwable e) {
            LogCatUtil.debug("ExtTransportOffice", "com.alipay.mobile.common.transportext.biz.shared.ProtobufCodecImpl not found." + e.toString());
            return null;
        }
    }

    public NetworkDiagnoseService getNetworkDiagnoseService() {
        try {
            return (NetworkDiagnoseService) Class.forName(NETWORK_DIAGONAL_SERVICE).newInstance();
        } catch (Throwable e) {
            LogCatUtil.debug("ExtTransportOffice", "com.alipay.mobile.common.transportext.biz.diagnose.NetworkDiagnoseServiceImpl not found." + e.toString());
            return null;
        }
    }

    public boolean isInvokedMMTPInit() {
        return a;
    }

    public void setInvokedMMTPInit(boolean inited) {
        a = inited;
    }

    public void setContext(Context context) {
        this.c = context;
    }

    public void notifyPush2UpdateInfo(Context context, boolean firstUpdate) {
        if (context != null) {
            try {
                if (!MiscUtils.isInAlipayClient(context)) {
                    return;
                }
            } catch (Throwable e) {
                LogCatUtil.info("ExtTransportOffice", "requestHttpDnsFromPush: " + e.toString());
                return;
            }
        }
        ((ExtHttpDnsManager) NetBeanFactory.getBean(Class.forName("com.alipay.mobile.common.transportext.biz.httpdns.ExtHttpDnsManagerImpl"))).ayncNotifyUpdateDnsInfo(firstUpdate);
    }

    public boolean isConnectionAvailable() {
        boolean z = false;
        try {
            Class connCheckerClass = Class.forName(EXT_TRANSPORT_CONN_CHECKER);
            Boolean ret = (Boolean) connCheckerClass.getMethod(IS_CONNECTION_AVAILABLE, new Class[0]).invoke(connCheckerClass.getClass(), new Object[0]);
            LogCatUtil.info("ExtTransportOffice", "ExtTPOffice , isConnectionAvailable " + ret.booleanValue());
            return ret.booleanValue();
        } catch (Throwable e) {
            LogCatUtil.info("ExtTransportOffice", "isConnectionAvailable: " + e.toString());
            return z;
        }
    }

    public void networkStateNotify(boolean available) {
        try {
            Class networkCheckClass = Class.forName(NETWORK_CHECK_CLASS_NAME);
            networkCheckClass.getMethod("networkStateNotify", new Class[]{Boolean.TYPE}).invoke(networkCheckClass.getClass(), new Object[]{Boolean.valueOf(available)});
            LogCatUtil.info("ExtTransportOffice", "ExtTPOffice , networkStateNotify " + available);
        } catch (Throwable e) {
            LogCatUtil.warn((String) "ExtTransportOffice", "networkStateNotify: " + e.toString());
        }
    }

    public static boolean isFakeWifi() {
        try {
            Class networkCheckClass = Class.forName(NETWORK_CHECK_CLASS_NAME);
            Object result = networkCheckClass.getMethod("isFakeWifi", new Class[0]).invoke(networkCheckClass.getClass(), new Object[0]);
            if (result == null) {
                return false;
            }
            Boolean isFakeWifi = (Boolean) result;
            LogCatUtil.info("ExtTransportOffice", "ExtTPOffice , isFakeWifi " + isFakeWifi);
            return isFakeWifi.booleanValue();
        } catch (Throwable e) {
            LogCatUtil.warn((String) "ExtTransportOffice", "isFakeWifi: " + e.toString());
            return false;
        }
    }

    public BaseSpeedTest getSpeeTestImpl() {
        try {
            return (BaseSpeedTest) NetBeanFactory.getBean(Class.forName(SPEED_TEST_IMPL));
        } catch (Exception ex) {
            LogCatUtil.error("ExtTransportOffice", "getSpeeTestImpl exception", ex);
            return null;
        }
    }

    public void diagnoseNotify() {
        try {
            Class diagnoseClass = Class.forName(DIAGNOSE_BY_SYSTEMCALL);
            diagnoseClass.getMethod(DIAGNOSE_NOTIFY, new Class[0]).invoke(diagnoseClass.getClass(), new Object[0]);
            LogCatUtil.info("ExtTransportOffice", "ExtTPOffice , diagnoseNotify");
        } catch (Throwable e) {
            LogCatUtil.warn((String) "ExtTransportOffice", "diagnoseNotify: " + e.toString());
        }
    }

    public void diagnoseForEasterEggs(DiagnoseResult result) {
        try {
            LogCatUtil.info("ExtTransportOffice", "ExtTPOffice, diagnoseForEasterEggs");
            Class.forName(DIAGNOSE_BY_USERCALL).getMethod(DIAGNOSE_LAUNCH, new Class[]{DiagnoseResult.class}).invoke(null, new Object[]{result});
        } catch (Throwable ex) {
            LogCatUtil.error((String) "ExtTransportOffice", ex);
        }
    }

    public void setScene(String scene, boolean pass) {
        try {
            Class sceneManager = Class.forName(MMTPSCENEMANAGER);
            sceneManager.getMethod(METHOD_SETSCENE, new Class[]{String.class, Boolean.TYPE}).invoke(sceneManager.getClass(), new Object[]{scene, Boolean.valueOf(pass)});
        } catch (Throwable ex) {
            LogCatUtil.error("ExtTransportOffice", "setScene exception", ex);
        }
    }

    public int getConnState() {
        try {
            Class amnetHelper = Class.forName(AMNET_HELPER);
            int state = ((Integer) amnetHelper.getMethod("getConnState", new Class[0]).invoke(amnetHelper.getClass(), new Object[0])).intValue();
            LogCatUtil.info("ExtTransportOffice", "getConnState,state=[" + state + "]");
            return state;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "ExtTransportOffice", ex);
            return -1;
        }
    }

    public String getSwitchFromOriginal(String key) {
        try {
            Class switchBridge = Class.forName("com.alipay.mobile.common.transportext.biz.util.SwitchBridge");
            String result = (String) switchBridge.getMethod("getSwitchFromOriginal", new Class[]{String.class}).invoke(switchBridge, new Object[]{key});
            LogCatUtil.debug("ExtTransportOffice", "key=[" + key + "] value=[" + result + "]");
            return result;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "ExtTransportOffice", ex);
            return null;
        }
    }
}
