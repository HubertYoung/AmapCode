package com.alipay.android.phone.mobilesdk.permission.guide.info;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import java.util.concurrent.TimeUnit;

public class GetInfoValve implements Runnable {
    public void run() {
        LoggerFactory.getTraceLogger().debug("Permissions", "GetInfoValve start.");
        if (!LoggerFactory.getProcessInfo().isMainProcess()) {
            LoggerFactory.getTraceLogger().info("Permissions", "cancel rpc task because it's not in process.");
        } else {
            ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).schedule(new a(), "PGInfoRpcTask", 2, TimeUnit.SECONDS);
        }
    }
}
