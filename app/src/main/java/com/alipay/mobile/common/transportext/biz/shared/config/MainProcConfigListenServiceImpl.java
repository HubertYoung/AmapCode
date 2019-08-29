package com.alipay.mobile.common.transportext.biz.shared.config;

import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.ext.MainProcConfigListenService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NwSharedSwitchUtil;
import com.alipay.mobile.common.transport.utils.SwitchMonitorLogUtil;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;

public class MainProcConfigListenServiceImpl implements MainProcConfigListenService {
    private static final String TAG = "MainProcConfigListenService";
    private static MainProcConfigListenServiceImpl mainProcConfigListenService;

    public static final MainProcConfigListenService getInstance() {
        if (mainProcConfigListenService != null) {
            return mainProcConfigListenService;
        }
        synchronized (MainProcConfigListenServiceImpl.class) {
            try {
                if (mainProcConfigListenService != null) {
                    MainProcConfigListenServiceImpl mainProcConfigListenServiceImpl = mainProcConfigListenService;
                    return mainProcConfigListenServiceImpl;
                }
                mainProcConfigListenService = new MainProcConfigListenServiceImpl();
                return mainProcConfigListenService;
            }
        }
    }

    private MainProcConfigListenServiceImpl() {
    }

    public void notifyConfigureChangedEvent() {
        NetworkAsyncTaskExecutor.executeIO(new Runnable() {
            public void run() {
                LogCatUtil.info(MainProcConfigListenServiceImpl.TAG, "Accepted push condfigure changed");
                TransportConfigureManager.getInstance().updateConfig(ExtTransportEnv.getAppContext());
                SwitchMonitorLogUtil.monitorSwitchUpdatedLog(ExtTransportEnv.getAppContext(), NwSharedSwitchUtil.getSwitchSrc());
            }
        });
    }
}
