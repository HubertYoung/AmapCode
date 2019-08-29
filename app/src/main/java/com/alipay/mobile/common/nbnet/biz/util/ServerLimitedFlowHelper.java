package com.alipay.mobile.common.nbnet.biz.util;

import android.content.Context;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPResp;
import com.alipay.mobile.common.nbnet.biz.constants.NBNetConfigureItem;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetServerLimitFlowException;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import java.util.Map;

public class ServerLimitedFlowHelper {
    private static long a = 0;
    private static NBNetServerLimitFlowException b;

    public static void a(Map<String, String> headers, Context context) {
        if (MiscUtils.isDebugger(context) && TransportConfigureManager.getInstance().equalsString(NBNetConfigureItem.MOCK_UPLOAD_SERVER_LIMITED_SWITCH, "T")) {
            headers.put("x-arup-error-code", Result.TAOBAO_ACTIVE);
            headers.put("x-arup-error-msg", "Mock upload server limited");
        }
    }

    public static void a(MMDPResp downloadResp, Context context) {
        if (MiscUtils.isDebugger(context) && TransportConfigureManager.getInstance().equalsString(NBNetConfigureItem.MOCK_DOWNLOAD_SERVER_LIMITED_SWITCH, "T")) {
            downloadResp.errcode = Integer.valueOf(1007);
        }
    }

    public static void a(MMDPResp downloadResp) {
        if (downloadResp.errcode != null) {
            switch (downloadResp.errcode.intValue()) {
                case 1006:
                    throw new NBNetServerLimitFlowException("Download server limited operation.");
                case 1007:
                    throw new NBNetServerLimitFlowException("Download server limited flow.");
                default:
                    return;
            }
        }
    }

    public static void a(NBNetServerLimitFlowException serverLimitFlowException) {
        if (serverLimitFlowException == null) {
            NBNetLogCat.d("ServerLimitedFlowHelper", "[enableServerUploadLimiting] serverLimitFlowException is null.");
            return;
        }
        NBNetLogCat.d("ServerLimitedFlowHelper", "[enableServerUploadLimiting] limitingCycleTime: " + serverLimitFlowException.getSleep());
        if (serverLimitFlowException.getSleep() <= 0) {
            return;
        }
        if (a()) {
            NBNetLogCat.d("ServerLimitedFlowHelper", "[enableServerUploadLimiting] Currently in the current limit state, return.");
            return;
        }
        synchronized (ServerLimitedFlowHelper.class) {
            if (a()) {
                NBNetLogCat.d("ServerLimitedFlowHelper", "[enableServerUploadLimiting] Currently in the current limit state of synchronized, return.");
                return;
            }
            a = System.currentTimeMillis() + ((long) (serverLimitFlowException.getSleep() * 1000));
            b = serverLimitFlowException;
            NBNetLogCat.a((String) "ServerLimitedFlowHelper", "[enableServerUploadLimiting] Enabled limit, uploadLimitEndTime: " + a);
        }
    }

    public static boolean a() {
        if (a < 1) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis <= a) {
            NBNetLogCat.a((String) "ServerLimitedFlowHelper", "[isServerUploadLimiting] Currently in the current limit state. currentTimeMillis:" + currentTimeMillis + ", uploadLimitEndTime:" + a + ", diff: " + (a - currentTimeMillis));
            return true;
        }
        synchronized (ServerLimitedFlowHelper.class) {
            a = 0;
            b = null;
        }
        NBNetLogCat.a((String) "ServerLimitedFlowHelper", (String) "[isServerUploadLimiting] Current limit over.");
        return false;
    }

    public static NBNetServerLimitFlowException b() {
        return b;
    }
}
