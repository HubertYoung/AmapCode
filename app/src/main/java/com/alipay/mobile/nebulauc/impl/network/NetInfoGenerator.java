package com.alipay.mobile.nebulauc.impl.network;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.SuperId;
import java.util.Map;

public class NetInfoGenerator {
    private static final String ALL_TIME = "at";
    private static final String CHANNEL_TYPE = "ct";
    private static final String DNS_TIME = "dt";
    private static final String ERROR_STACK = "es";
    private static final String KEY_JOINER = "_";
    private static final String KEY_VALUE_JOINER = "::";
    private static final String NET_QUALITY = "qos";
    private static final String NET_TYPE = "nt";
    private static final String PROTOCOL = "ptl";
    private static final String RADICAL = "rd";
    private static final String READ_TIME = "rt";
    private static final String REQUEST_ALL_TIME = "rat";
    private static final String SSL_TIME = "sst";
    private static final String STALLED_TIME = "st";
    private static final String STATUS = "status";
    private static final String TARGET_IP = "tip";
    private static final String TCP_TIME = "tt";
    private static final String THREAD_POOL_WAIT_NUM = "tw";
    private static final String WAIT_TIME = "wt";

    static String generateNetInfo(String tag, Map<String, String> netPerfData, String protocol, boolean isRadical, int statusCode, long requestAllTime) {
        long time = System.currentTimeMillis();
        StringBuilder netInfo = new StringBuilder();
        if (netPerfData != null) {
            try {
                String channelType = netPerfData.get("NETTUNNEL");
                String netType = netPerfData.get("NETTYPE");
                String targetIp = netPerfData.get("TARGET_HOST");
                String stalledTime = netPerfData.get(RPCDataItems.STALLED_TIME);
                String dnsTime = netPerfData.get("DNS_TIME");
                String tcpTime = netPerfData.get("TCP_TIME");
                String sslTime = netPerfData.get("SSL_TIME");
                String waitTime = netPerfData.get(RPCDataItems.WAIT_TIME);
                String readTime = netPerfData.get(RPCDataItems.READ_TIME);
                String allTime = netPerfData.get("ALL_TIME");
                String qos = netPerfData.get(RPCDataItems.QOS);
                int totalTasks = H5Utils.parseInt(netPerfData.get("TH_PO_ATC"));
                int doneTasks = H5Utils.parseInt(netPerfData.get("TH_PO_CTC"));
                netInfo.append(PROTOCOL).append(KEY_VALUE_JOINER).append(handleNetInfoItem(protocol)).append("_").append("status").append(KEY_VALUE_JOINER).append(handleNetInfoItem(String.valueOf(statusCode))).append("_").append("rd").append(KEY_VALUE_JOINER).append(isRadical ? DictionaryKeys.CTRLXY_Y : SuperId.BIT_1_MAIN_BUSSTATION).append("_").append(REQUEST_ALL_TIME).append(KEY_VALUE_JOINER).append(String.valueOf(requestAllTime)).append("_").append("ct").append(KEY_VALUE_JOINER).append(handleNetInfoItem(channelType)).append("_").append(NET_TYPE).append(KEY_VALUE_JOINER).append(handleNetInfoItem(netType)).append("_").append(TARGET_IP).append(KEY_VALUE_JOINER).append(handleNetInfoItem(targetIp)).append("_").append(THREAD_POOL_WAIT_NUM).append(KEY_VALUE_JOINER).append(totalTasks - doneTasks).append("_").append("st").append(KEY_VALUE_JOINER).append(handleNetInfoItem(stalledTime)).append("_").append("dt").append(KEY_VALUE_JOINER).append(handleNetInfoItem(dnsTime)).append("_").append("tt").append(KEY_VALUE_JOINER).append(handleNetInfoItem(tcpTime)).append("_").append(SSL_TIME).append(KEY_VALUE_JOINER).append(handleNetInfoItem(sslTime)).append("_").append("wt").append(KEY_VALUE_JOINER).append(handleNetInfoItem(waitTime)).append("_").append("rt").append(KEY_VALUE_JOINER).append(handleNetInfoItem(readTime)).append("_").append(ALL_TIME).append(KEY_VALUE_JOINER).append(handleNetInfoItem(allTime)).append("_").append(NET_QUALITY).append(KEY_VALUE_JOINER).append(handleNetInfoItem(qos)).append("_").append("es").append(KEY_VALUE_JOINER).append("-1");
            } catch (Throwable e) {
                H5Log.e(tag, "generateNetInfo error, ", e);
            }
        }
        String netInfoStr = netInfo.toString();
        H5Log.d(tag, "generateNetInfo: " + netInfoStr);
        H5Log.d(tag, "generateNetInfo cost : " + (System.currentTimeMillis() - time));
        return netInfoStr;
    }

    private static String handleNetInfoItem(String item) {
        return TextUtils.isEmpty(item) ? "-1" : item;
    }
}
