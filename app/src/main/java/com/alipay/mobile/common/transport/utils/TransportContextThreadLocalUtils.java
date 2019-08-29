package com.alipay.mobile.common.transport.utils;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;

public class TransportContextThreadLocalUtils {
    private static ThreadLocal<TransportContext> a = new ThreadLocal<>();

    public static TransportContext getValue() {
        return a.get();
    }

    public static void setValue(TransportContext transportContext) {
        a.set(transportContext);
    }

    public static void addDnsType(String dnsType) {
        TransportContext transportContext = getValue();
        if (transportContext != null) {
            transportContext.getCurrentDataContainer().putDataItem(RPCDataItems.DT, dnsType);
        }
    }

    public static void setTargetHost(String targetHost) {
        TransportContext transportContext = getValue();
        if (transportContext != null) {
            transportContext.getCurrentDataContainer().putDataItem("TARGET_HOST", targetHost);
        }
    }

    public static boolean isFromLocalDns() {
        TransportContext transportContext = getValue();
        if (transportContext == null) {
            return false;
        }
        return TextUtils.equals(transportContext.getCurrentDataContainer().getDataItem(RPCDataItems.DT), RPCDataItems.VALUE_DT_LOCALDNS);
    }

    public static boolean isFromLocalCacheDns() {
        TransportContext transportContext = getValue();
        if (transportContext == null) {
            return false;
        }
        return TextUtils.equals(transportContext.getCurrentDataContainer().getDataItem(RPCDataItems.DT), RPCDataItems.VALUE_DT_LOCAL_CACHE_DNS);
    }

    public static boolean isFromIpRank() {
        TransportContext transportContext = getValue();
        if (transportContext == null) {
            return false;
        }
        return TextUtils.equals(transportContext.getCurrentDataContainer().getDataItem(RPCDataItems.DT), RPCDataItems.VALUE_DT_IPRANK);
    }

    public static boolean isFromHttpDns() {
        TransportContext transportContext = getValue();
        if (transportContext == null) {
            return false;
        }
        return TextUtils.equals(transportContext.getCurrentDataContainer().getDataItem(RPCDataItems.DT), RPCDataItems.VALUE_DT_HTTPDNS);
    }
}
