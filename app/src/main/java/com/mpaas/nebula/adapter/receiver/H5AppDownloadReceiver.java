package com.mpaas.nebula.adapter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.mpaas.nebula.adapter.api.MPaaSNebula;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

public class H5AppDownloadReceiver extends BroadcastReceiver {
    public void onReceive(Context context, final Intent intent) {
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                try {
                    Bundle bundle = intent.getExtras();
                    String app_id = bundle.getString("app_id");
                    String version = bundle.getString("version");
                    MPaaSNebula.getInstance().getH5AppProvider().installApp(app_id, version);
                    LoggerFactory.getTraceLogger().debug("H5AppDownloadReceiver", "app_id is " + app_id + "  version is " + version);
                } catch (Throwable hh) {
                    LoggerFactory.getTraceLogger().error((String) "H5AppDownloadReceiver", hh);
                }
            }
        });
    }

    public static String getStackTrace(Throwable throwable) {
        PrintWriter pw;
        try {
            StringWriter sw = new StringWriter();
            pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            String stringWriter = sw.toString();
            pw.close();
            return stringWriter;
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) "ExceptionUtil", e2);
            return null;
        }
    }

    public static void event(int level, String bizType, String seedId, String ucId, String param1, String param2, String param3, Map<String, String> map) {
        try {
            Behavor ticketBehavor = new Behavor();
            if (level >= 0) {
                ticketBehavor.setLoggerLevel(level);
            }
            ticketBehavor.setBehaviourPro(bizType);
            ticketBehavor.setSeedID(seedId);
            ticketBehavor.setUserCaseID(ucId);
            ticketBehavor.setParam1(param1);
            ticketBehavor.setParam2(param2);
            ticketBehavor.setParam3(param3);
            if (map != null) {
                for (Entry entry : map.entrySet()) {
                    if (entry != null) {
                        ticketBehavor.addExtParam((String) entry.getKey(), (String) entry.getValue());
                    }
                }
            }
            LoggerFactory.getBehavorLogger().event("event", ticketBehavor);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "H5AppDownloadReceiver", e);
        }
    }
}
