package com.alipay.mobile.nebulaappproxy.api;

import android.text.TextUtils;
import com.alipay.mobile.framework.service.common.OrderedExecutor;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class H5SyncUtil {
    public static final String APP_CREDIT_G_NOTIFY = "APP-CREDIT-G";
    public static final String NEBULA_GLOBAL_NOTIFY = "NEBULA-G";
    public static final String NEBULA_USERS_NOTIFY = "NEBULA-U";
    private static String a = "h5_syncdownload";
    public static final List<String> listSync = Collections.synchronizedList(new ArrayList());

    public static synchronized void doSync(String syncMessage) {
        synchronized (H5SyncUtil.class) {
            H5Log.d("H5SyncUtil", "doSync:" + syncMessage);
            if (TextUtils.isEmpty(syncMessage)) {
                H5Log.e((String) "H5SyncUtil", (String) "syncMessage == null,return");
            } else {
                H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
                if (h5LoginProvider == null || h5LoginProvider.isLogin()) {
                    execute(syncMessage);
                } else {
                    H5Log.d("H5SyncUtil", "!h5LoginProvider.isLogin() add handle sync data ");
                    listSync.add(syncMessage);
                }
            }
        }
    }

    public static void execute(String syncMessage) {
        TaskScheduleService taskScheduleService = (TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName());
        if (taskScheduleService != null) {
            try {
                OrderedExecutor mTaskExecutor = taskScheduleService.acquireOrderedExecutor();
                if (mTaskExecutor != null) {
                    mTaskExecutor.submit("h5_do_sync", new H5SyncRunnable(syncMessage));
                }
            } catch (Throwable e) {
                H5Log.e((String) "H5SyncUtil", e);
            }
        }
    }

    public static boolean isDownload() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            String result = h5ConfigProvider.getConfig(a);
            if (!TextUtils.isEmpty(result) && result.equalsIgnoreCase("NO")) {
                return false;
            }
        }
        return true;
    }
}
