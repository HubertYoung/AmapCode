package com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.foreign;

import android.os.Build.VERSION;
import com.alipay.euler.andfix.Compat;
import com.alipay.mobile.common.amnet.api.AmnetGeneralEventManager;
import com.alipay.mobile.common.amnet.ipcapi.mainproc.MainProcGeneralListenService;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.strategy.ExtTransportTunnelWatchdog;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.amnet.Initialization.RspInit;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;

public class GeneralEventListenServiceImpl implements MainProcGeneralListenService {
    private static GeneralEventListenServiceImpl GENERAL_EVENT_LISTEN_SERVICE = null;
    private static final String TAG = "amnet_MainProcGeneralListenService";
    private AmnetGeneralEventManager amnetGeneralEventManager;
    private byte amnetLifeState;

    public static final MainProcGeneralListenService getInstance() {
        if (GENERAL_EVENT_LISTEN_SERVICE == null) {
            synchronized (MainProcGeneralListenService.class) {
                try {
                    if (GENERAL_EVENT_LISTEN_SERVICE == null) {
                        GENERAL_EVENT_LISTEN_SERVICE = new GeneralEventListenServiceImpl();
                    }
                }
            }
        }
        return GENERAL_EVENT_LISTEN_SERVICE;
    }

    private GeneralEventListenServiceImpl() {
    }

    public void change(final int state) {
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyConnStateChange(state);
            }
        });
    }

    public void panic(final int err, final String inf) {
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyError(err, inf);
            }
        });
    }

    public Map<Byte, Map<String, String>> collect(Map<Byte, Map<String, String>> param) {
        getAmnetGeneralEventManager().notifyCollectInitInfo(param);
        DeviceInfo deviceInfo = DeviceInfo.createInstance(ExtTransportEnv.getAppContext());
        Map oldInit = param.get(Byte.valueOf(0));
        if (oldInit == null) {
            oldInit = new HashMap();
            param.put(Byte.valueOf(0), oldInit);
        }
        oldInit.put(Constants.KEY_IMEI, deviceInfo.getImei());
        oldInit.put(Constants.KEY_IMSI, deviceInfo.getImsi());
        oldInit.put("utdid", deviceInfo.getmDid());
        try {
            oldInit.put("vmType", Compat.getAndFixVM().name());
            oldInit.put("apiLevel", String.valueOf(VERSION.SDK_INT));
            oldInit.put("releaseVersion", LoggerFactory.getLogContext().getReleaseCode());
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
        LogCatUtil.info(TAG, "main process init info = [" + param.toString() + "]");
        return param;
    }

    public void report(final String key, final double val) {
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyReport(key, val);
            }
        });
    }

    public void notifyInitOk() {
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyInitOk();
            }
        });
    }

    public void touch(String ipLocal, String ipRemote, String portLocal, String portRemote) {
        final String str = ipLocal;
        final String str2 = ipRemote;
        final String str3 = portLocal;
        final String str4 = portRemote;
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyReportIpPort(str, str2, str3, str4);
            }
        });
    }

    public void restrict(final int delay, final String inf) {
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyRestrict(delay, inf);
            }
        });
    }

    public void listenSessionInvalid() {
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifySessionInvalid();
            }
        });
    }

    public void resendSessionid() {
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyResendSessionid();
            }
        });
    }

    public void notifyAmnetLifeChanged(byte state) {
        this.amnetLifeState = state;
        if (state == 2) {
            ExtTransportTunnelWatchdog.getInstance().mrpcFailureRest();
        }
        LogCatUtil.info(TAG, "====notifyAmnetLifeChanged ,Amnet change to==== " + state);
    }

    public void onFinalErrorEvent(long receiptId, int errorCode, String errorMsg, Map<String, String> params) {
        final long j = receiptId;
        final int i = errorCode;
        final String str = errorMsg;
        final Map<String, String> map = params;
        NetworkAsyncTaskExecutor.execute(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyFinalError(j, i, str, map);
            }
        });
    }

    public void notifyGift(final String key, final String val) {
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyGift(key, val);
            }
        });
    }

    public void notifyInitResponse(final RspInit rspInit) {
        NetworkAsyncTaskExecutor.executeDispatch(new Runnable() {
            public void run() {
                GeneralEventListenServiceImpl.this.getAmnetGeneralEventManager().notifyInitResponse(rspInit);
            }
        });
    }

    public AmnetGeneralEventManager getAmnetGeneralEventManager() {
        if (this.amnetGeneralEventManager == null) {
            this.amnetGeneralEventManager = AmnetHelper.getAmnetManager().getAmnetGeneralEventManager();
        }
        return this.amnetGeneralEventManager;
    }

    public void setAmnetLifeState(byte amnetLifeState2) {
        this.amnetLifeState = amnetLifeState2;
        LogCatUtil.info(TAG, "====setAmnetLifeState ,Amnet change to===== " + amnetLifeState2);
    }

    public boolean isAmnetActived() {
        return this.amnetLifeState == 2;
    }
}
