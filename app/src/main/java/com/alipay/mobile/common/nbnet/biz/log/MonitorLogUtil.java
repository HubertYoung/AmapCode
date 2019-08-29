package com.alipay.mobile.common.nbnet.biz.log;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadResponse;
import com.alipay.mobile.common.nbnet.biz.qoe.NetworkQoeManagerFactory;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.utils.ConnectionUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MonitorLogUtil {
    public static final void a(NBNetContext nbNetContext) {
        FrameworkMonitorFactory.a().a(nbNetContext);
    }

    private static void a(int errCode, String errMsg, Map<String, String> extMap) {
        FrameworkMonitorFactory.a().a(errCode, errMsg, extMap);
    }

    public static final void a(NBNetUploadResponse uploadResponse) {
        if (!uploadResponse.isSuccess() && uploadResponse.getErrorCode() != -8) {
            a(uploadResponse.getErrorCode(), uploadResponse.getErrorMsg(), new HashMap(1));
        }
    }

    public static final void a(NBNetContext nbNetContext, long stalledTime) {
        k(nbNetContext).add(RPCDataItems.STALLED_TIME);
        nbNetContext.setAttribute(RPCDataItems.STALLED_TIME, String.valueOf(stalledTime));
    }

    private static void n(NBNetContext nbNetContext) {
        k(nbNetContext).add(RPCDataItems.GROUND);
        nbNetContext.setAttribute(RPCDataItems.GROUND, "FG");
    }

    private static void o(NBNetContext nbNetContext) {
        k(nbNetContext).add(RPCDataItems.GROUND);
        nbNetContext.setAttribute(RPCDataItems.GROUND, "BG");
    }

    public static final void a(NBNetContext nbNetContext, Context applicationContext) {
        if (NBNetEnvUtils.a(applicationContext)) {
            n(nbNetContext);
        } else {
            o(nbNetContext);
        }
    }

    public static final void b(NBNetContext nbNetContext) {
        k(nbNetContext).add(RPCDataItems.IGN_ERR);
        nbNetContext.setAttribute(RPCDataItems.IGN_ERR, "T");
    }

    public static final void b(NBNetContext nbNetContext, long costTime) {
        k(nbNetContext).add("CP_TIME");
        nbNetContext.setAttribute("CP_TIME", String.valueOf(costTime));
    }

    public static final void c(NBNetContext nbNetContext, long dnsTime) {
        k(nbNetContext).add("DNS_TIME");
        nbNetContext.setAttribute("DNS_TIME", String.valueOf(dnsTime));
    }

    public static final void d(NBNetContext nbNetContext, long tcpTime) {
        k(nbNetContext).add("TCP_TIME");
        nbNetContext.setAttribute("TCP_TIME", String.valueOf(tcpTime));
        NetworkQoeManagerFactory.a().b((int) tcpTime);
    }

    public static final void e(NBNetContext nbNetContext, long sslTime) {
        k(nbNetContext).add("SSL_TIME");
        nbNetContext.setAttribute("SSL_TIME", String.valueOf(sslTime));
        NetworkQoeManagerFactory.a().b((int) sslTime);
    }

    public static final void f(NBNetContext nbNetContext, long sentTime) {
        k(nbNetContext).add(RPCDataItems.SENT_TIME);
        nbNetContext.setAttribute(RPCDataItems.SENT_TIME, String.valueOf(sentTime));
    }

    public static final void g(NBNetContext nbNetContext, long waitTime) {
        k(nbNetContext).add(RPCDataItems.WAIT_TIME);
        nbNetContext.setAttribute(RPCDataItems.WAIT_TIME, String.valueOf(waitTime));
        NetworkQoeManagerFactory.a().a((int) waitTime);
    }

    public static final void h(NBNetContext nbNetContext, long dataLen) {
        k(nbNetContext).add("datalen");
        nbNetContext.setAttribute("datalen", String.valueOf(dataLen));
    }

    public static final void i(NBNetContext nbNetContext, long upMassTime) {
        k(nbNetContext).add("U_MASS_TIME");
        nbNetContext.setAttribute("U_MASS_TIME", String.valueOf(upMassTime));
    }

    public static final void j(NBNetContext nbNetContext, long downMassTime) {
        k(nbNetContext).add("D_MASS_TIME");
        nbNetContext.setAttribute("D_MASS_TIME", String.valueOf(downMassTime));
    }

    public static final void c(NBNetContext nbNetContext) {
        k(nbNetContext).add("MASS_TYPE");
        nbNetContext.setAttribute("MASS_TYPE", "MASS_UP");
    }

    public static final void d(NBNetContext nbNetContext) {
        k(nbNetContext).add("MASS_TYPE");
        nbNetContext.setAttribute("MASS_TYPE", "MASS_DOWN");
    }

    public static final void a(NBNetContext nbNetContext, String fileId) {
        k(nbNetContext).add("fileId");
        nbNetContext.setAttribute("fileId", fileId);
    }

    public static final void e(NBNetContext nbNetContext) {
        k(nbNetContext).add("RESULT");
        nbNetContext.setAttribute("RESULT", "succ");
    }

    public static final void f(NBNetContext nbNetContext) {
        k(nbNetContext).add("RESULT");
        nbNetContext.setAttribute("RESULT", UploadDataResult.FAIL_MSG);
    }

    public static final void b(NBNetContext nbNetContext, String uuid) {
        k(nbNetContext).add("uuid");
        nbNetContext.setAttribute("uuid", uuid);
    }

    public static final void a(NBNetContext nbNetContext, boolean isQuic) {
        k(nbNetContext).add("quic");
        nbNetContext.setAttribute("quic", isQuic ? "Y" : "N");
    }

    public static final void c(NBNetContext nbNetContext, String fileName) {
        k(nbNetContext).add("fileName");
        nbNetContext.setAttribute("fileName", fileName);
    }

    public static final void d(NBNetContext nbNetContext, String traceId) {
        k(nbNetContext).add("traceid");
        nbNetContext.setAttribute("traceid", traceId);
    }

    public static final void a(NBNetContext nbNetContext, int errorCode) {
        k(nbNetContext).add("ErrorCode");
        nbNetContext.setAttribute("ErrorCode", String.valueOf(errorCode));
    }

    public static final void e(NBNetContext nbNetContext, String errorMsg) {
        k(nbNetContext).add("ErrorMsg");
        nbNetContext.setAttribute("ErrorMsg", errorMsg);
    }

    public static final void b(NBNetContext nbNetContext, int retryCount) {
        k(nbNetContext).add("TOCount");
        nbNetContext.setAttribute("TOCount", String.valueOf(retryCount));
    }

    public static final void f(NBNetContext nbNetContext, String targetHost) {
        k(nbNetContext).add("TARGET_HOST");
        nbNetContext.setAttribute("TARGET_HOST", targetHost);
    }

    public static final void g(NBNetContext nbNetContext) {
        k(nbNetContext).add("crypto");
        nbNetContext.setAttribute("crypto", "0");
    }

    public static final void k(NBNetContext nbNetContext, long fileLen) {
        k(nbNetContext).add("flen");
        nbNetContext.setAttribute("flen", String.valueOf(fileLen));
    }

    public static final void c(NBNetContext nbNetContext, int requestId) {
        k(nbNetContext).add("key");
        nbNetContext.setAttribute("key", String.valueOf(requestId));
    }

    public static final void g(NBNetContext nbNetContext, String connMethod) {
        k(nbNetContext).add("CONN_METHOD");
        nbNetContext.setAttribute("CONN_METHOD", connMethod);
    }

    public static final void l(NBNetContext nbNetContext, long waitConnTime) {
        k(nbNetContext).add("WAIT_CONN_TIME");
        nbNetContext.setAttribute("WAIT_CONN_TIME", String.valueOf(waitConnTime));
    }

    public static final void b(NBNetContext nbNetContext, boolean isTriedComp) {
        k(nbNetContext).add("TRIED_COMP");
        nbNetContext.setAttribute("TRIED_COMP", isTriedComp ? "1" : "0");
    }

    public static final void h(NBNetContext nbNetContext) {
        k(nbNetContext).add("resType");
        nbNetContext.setAttribute("resType", "1");
    }

    public static final void h(NBNetContext nbNetContext, String bizType) {
        k(nbNetContext).add("BIZ_TYPE");
        nbNetContext.setAttribute("BIZ_TYPE", bizType);
    }

    public static final void c(NBNetContext nbNetContext, boolean proxy) {
        k(nbNetContext).add(RPCDataItems.PROXY);
        nbNetContext.setAttribute(RPCDataItems.PROXY, proxy ? "T" : "F");
    }

    public static final void i(NBNetContext nbNetContext, String via) {
        if (!TextUtils.isEmpty(via)) {
            k(nbNetContext).add(RPCDataItems.CDN_VIA);
            nbNetContext.setAttribute(RPCDataItems.CDN_VIA, via);
        }
    }

    public static final void m(NBNetContext nbNetContext, long readTime) {
        k(nbNetContext).add(RPCDataItems.READ_TIME);
        Object readTimeObj = nbNetContext.getAttribute(RPCDataItems.READ_TIME);
        if (readTimeObj != null) {
            try {
                readTime += Long.parseLong((String) readTimeObj);
            } catch (Throwable e) {
                NBNetLogCat.d("MonitorLogUtil", "parseLong exception: " + e.toString());
            }
        }
        nbNetContext.setAttribute(RPCDataItems.READ_TIME, String.valueOf(readTime));
    }

    public static final void b(NBNetContext nbNetContext, Context context) {
        try {
            int net0 = ConnectionUtil.getConnType(context);
            int net1 = ConnectionUtil.getNetworkType(context);
            k(nbNetContext).add("networkType");
            nbNetContext.setAttribute("networkType", net0 + "-" + net1);
        } catch (Throwable e) {
            NBNetLogCat.d("MonitorLogUtil", "appendNetworkType exception: " + e.toString());
        }
    }

    public static final void i(NBNetContext nbNetContext) {
        nbNetContext.setAttribute("nbnet.MONITOR_LOG_TYPE", "mmup");
    }

    public static final void j(NBNetContext nbNetContext) {
        nbNetContext.setAttribute("nbnet.MONITOR_LOG_TYPE", TransportConstants.VALUE_TARGET_SPI);
    }

    public static Set<String> k(NBNetContext nbNetContext) {
        Set monitorKeySet = (Set) nbNetContext.getAttribute("nbnet.MONITOR_KEYS");
        if (monitorKeySet != null) {
            return monitorKeySet;
        }
        Set monitorKeySet2 = new HashSet();
        nbNetContext.setAttribute("nbnet.MONITOR_KEYS", monitorKeySet2);
        return monitorKeySet2;
    }

    public static final boolean l(NBNetContext nbNetContext) {
        Object obj = nbNetContext.getAttribute("RESULT");
        if (obj != null && (obj instanceof String) && TextUtils.equals((String) obj, UploadDataResult.FAIL_MSG)) {
            return false;
        }
        return true;
    }

    public static final boolean m(NBNetContext nbNetContext) {
        Object obj = nbNetContext.getAttribute(RPCDataItems.IGN_ERR);
        if (obj != null && (obj instanceof String)) {
            return TextUtils.equals((String) obj, "T");
        }
        return false;
    }
}
