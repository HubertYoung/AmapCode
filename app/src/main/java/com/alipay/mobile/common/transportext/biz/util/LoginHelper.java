package com.alipay.mobile.common.transportext.biz.util;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginHelper {
    private static final String LOGTAG = "net_LoginHelper";
    private static long expirTime = -1;
    private static int inCycleLoginCount = 0;
    /* access modifiers changed from: private */
    public static AtomicBoolean loging = new AtomicBoolean(false);

    public static final boolean tryDoLogin() {
        ExternalService authService = LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface("com.alipay.mobile.framework.service.ext.security.AuthService");
        try {
            return ((Boolean) authService.getClass().getDeclaredMethod("rpcAuth", new Class[0]).invoke(authService, new Object[0])).booleanValue();
        } catch (Exception e) {
            LogCatUtil.error((String) "LoginUtils", (Throwable) e);
            return false;
        }
    }

    public static final void tryLogin() {
        LogCatUtil.info(LOGTAG, "session invalid, go to login !");
        if (ActivityHelper.isBackgroundRunning()) {
            LogCatUtil.info(LOGTAG, "Wallet not at front desk. return.");
        } else if (!MiscUtils.isAtFrontDesk(ExtTransportEnv.getAppContext())) {
            LogCatUtil.info(LOGTAG, "Wallet not at front desk. return.");
        } else {
            LogCatUtil.info(LOGTAG, "Wallet at front desk.");
            if (loging.get()) {
                LogCatUtil.info(LOGTAG, "doing loging.");
                return;
            }
            loging.set(true);
            long nowTime = System.currentTimeMillis();
            if (nowTime > expirTime) {
                expirTime = 60000 + nowTime;
                inCycleLoginCount = 1;
                LogCatUtil.debug(LOGTAG, "reset inCycleLoginCount");
            } else if (inCycleLoginCount > 3) {
                LogCatUtil.debug(LOGTAG, "login count more than 3");
                return;
            }
            LogCatUtil.debug(LOGTAG, " inCycleLoginCount=【“" + inCycleLoginCount + "”】");
            inCycleLoginCount++;
            try {
                NetworkAsyncTaskExecutor.execute(new Runnable() {
                    public final void run() {
                        boolean suc = LoginHelper.tryDoLogin();
                        LoginHelper.loging.set(false);
                        LogCatUtil.debug(LoginHelper.LOGTAG, "tryDoLogin result=[" + suc + "]");
                    }
                });
            } catch (Exception e) {
                LogCatUtil.error((String) LOGTAG, (Throwable) e);
                loging.set(false);
            }
        }
    }
}
