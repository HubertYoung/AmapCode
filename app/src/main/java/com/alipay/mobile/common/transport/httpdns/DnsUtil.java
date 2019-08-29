package com.alipay.mobile.common.transport.httpdns;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.netsdkextdependapi.appinfo.AppInfoUtil;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.netsdkextdependapi.security.SecurityUtil;
import com.alipay.mobile.common.netsdkextdependapi.security.SignRequest;
import com.alipay.mobile.common.netsdkextdependapi.security.SignResult;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.MpaasPropertiesUtil;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DnsUtil {
    public static final String AMDC_DOMAIN = "amdc.alipay.com";
    public static final String DOMAIN_GROUP = "a4";
    public static final String EGG_PAIN_IP = "0.0.0.0";
    public static final String TAG = "HTTP_DNS_DnsUtil";

    public static String getDomainGroup() {
        try {
            Context context = TransportEnvUtil.getContext();
            String domainGrouup = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("amdc.dg");
            if (!TextUtils.isEmpty(domainGrouup)) {
                return domainGrouup;
            }
            return DOMAIN_GROUP;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "getDomainGroup ex:" + ex.toString());
            return DOMAIN_GROUP;
        }
    }

    public static String getAmdcHost() {
        try {
            Context context = TransportEnvUtil.getContext();
            String amdcHost = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("amdc.host");
            if (!TextUtils.isEmpty(amdcHost)) {
                return amdcHost;
            }
            return AMDC_DOMAIN;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "getAmdcHost ex: " + ex.toString());
            return AMDC_DOMAIN;
        }
    }

    public static boolean isLogicIP(String ip) {
        if (ip == null) {
            return false;
        }
        if ("0.0.0.0".equals(ip.trim())) {
            return true;
        }
        return Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$").matcher(ip).matches();
    }

    public static boolean isNumeric(String sttl) {
        if (!Pattern.compile("[0-9]*").matcher(sttl).matches()) {
            return false;
        }
        return true;
    }

    public static boolean isLogicHost(String host) {
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        char[] bytes = host.toCharArray();
        if (bytes.length <= 0) {
            return false;
        }
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] < 'A' || bytes[i] > 'Z') && ((bytes[i] < 'a' || bytes[i] > 'z') && ((bytes[i] < '0' || bytes[i] > '9') && bytes[i] != '.' && bytes[i] != '-'))) {
                return false;
            }
        }
        return true;
    }

    public static byte[] ipToBytesByReg(String ipAddr) {
        byte[] ret = new byte[4];
        try {
            String[] ipArr = ipAddr.split("\\.");
            ret[0] = (byte) (Integer.parseInt(ipArr[0]) & 255);
            ret[1] = (byte) (Integer.parseInt(ipArr[1]) & 255);
            ret[2] = (byte) (Integer.parseInt(ipArr[2]) & 255);
            ret[3] = (byte) (Integer.parseInt(ipArr[3]) & 255);
            return ret;
        } catch (Exception e) {
            throw new IllegalArgumentException(ipAddr + " is invalid IP");
        }
    }

    public static String bytesToIp(byte[] bytes) {
        return new StringBuffer().append(bytes[0] & 255).append(DjangoUtils.EXTENSION_SEPARATOR).append(bytes[1] & 255).append(DjangoUtils.EXTENSION_SEPARATOR).append(bytes[2] & 255).append(DjangoUtils.EXTENSION_SEPARATOR).append(bytes[3] & 255).toString();
    }

    public static int getFlag(Context mContext) {
        if (!MiscUtils.isInAlipayClient(mContext) || MiscUtils.isRealPushProcess(mContext)) {
            return 0;
        }
        return 1;
    }

    public static String getHttpdnsServerUrl(Context context) {
        try {
            if (MiscUtils.isDebugger(context)) {
                String serverUrl = ReadSettingServerUrl.getInstance().getHttpdnsServerUrl(context);
                if (TextUtils.isEmpty(serverUrl)) {
                    return "";
                }
                LogCatUtil.debug(TAG, "get httpdns url from setting,dnsUrl=[" + serverUrl + "]");
                return serverUrl;
            }
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "getHttpdnsServerUrl exception", ex);
        }
        return null;
    }

    public static HttpdnsIP mergerHttpdnsIp(HttpdnsIP httpdnsIP1, HttpdnsIP httpdnsIP2) {
        if (httpdnsIP1 == null || httpdnsIP2 == null) {
            LogCatUtil.warn((String) TAG, (String) "HttpdnsIp is null");
            return null;
        }
        try {
            List list1 = Arrays.asList(httpdnsIP1.getIpEntries());
            List list2 = Arrays.asList(httpdnsIP2.getIpEntries());
            List resultList = new ArrayList();
            resultList.addAll(list1);
            resultList.addAll(list2);
            httpdnsIP1.setIpEntries((HttpdnsIPEntry[]) resultList.toArray(new HttpdnsIPEntry[resultList.size()]));
            return httpdnsIP1;
        } catch (Exception e) {
            LogCatUtil.debug(TAG, "mergerHttpdnsIp exception");
            return null;
        }
    }

    public static final InetAddress[] getAllByName(String hostName) {
        TransportContextThreadLocalUtils.addDnsType(RPCDataItems.VALUE_DT_LOCALDNS);
        InetAddress[] address = InetAddress.getAllByName(hostName);
        if (Arrays.asList(address).contains(InetAddress.getByName("0.0.0.0"))) {
            a(hostName, address);
        }
        return address;
    }

    private static void a(String hostName, InetAddress[] address) {
        try {
            Performance pf = new TransportPerformance();
            pf.setSubType("ISP_DNS");
            pf.setParam2("FATAL");
            pf.getExtPramas().put("host", hostName);
            pf.getExtPramas().put("ips", Arrays.toString(address));
            MonitorLoggerUtils.uploadPerfLog(pf);
            LogCatUtil.debug(TAG, "PERF:" + pf.toString());
            LoggerFactory.getMonitorLogger().mtBizReport("BIZ_NETWORK", hostName, Arrays.toString(address), null);
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "perfLog ex:" + ex.toString());
        }
    }

    public static boolean isUseSign() {
        if (MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DNS_USE_SIGN))) {
            return true;
        }
        return false;
    }

    public static String getSignData(String requestUrl, String requsetBody, String ts, String appid) {
        StringBuffer signPlain = new StringBuffer();
        signPlain.append(requsetBody).append(appid).append(ts);
        return a(requestUrl, signPlain.toString());
    }

    private static String a(String requestUrl, String content) {
        try {
            SignRequest signRequest = new SignRequest();
            signRequest.appkey = a(requestUrl);
            signRequest.signType = SignRequest.SIGN_TYPE_HMAC_SHA1;
            signRequest.content = content;
            SignResult signature = SecurityUtil.signature(signRequest);
            if (!signature.isSuccess()) {
                LogCatUtil.debug(TAG, "[signature] Signed fail, requestType: HMAC_SHA1, appKey:" + signRequest.appkey);
                return "";
            }
            LogCatUtil.debug(TAG, "[signature] Get security signed string: " + signature.sign + ", requestType: HMAC_SHA1, appKey:" + signRequest.appkey);
            return signature.sign;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, e);
            return "";
        }
    }

    private static String a(String requestUrl) {
        return MpaasPropertiesUtil.getAppkey(MpaasPropertiesUtil.getAppKeyFromMetaData(TransportEnvUtil.getContext()), b(requestUrl), TransportEnvUtil.getContext());
    }

    private static boolean b(String reqUrl) {
        if (!MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
            return true;
        }
        if (TextUtils.isEmpty(reqUrl) || !reqUrl.contains(".com")) {
            return false;
        }
        return true;
    }

    public static String getAppId() {
        try {
            return MpaasPropertiesUtil.getAppId(TransportEnvUtil.getContext());
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "getAppId ex:" + ex.toString());
            return AppInfoUtil.getProductId();
        }
    }

    public static String getWsid() {
        try {
            String wsid = MpaasPropertiesUtil.getWorkspaceId(TransportEnvUtil.getContext());
            LogCatUtil.info(TAG, "Mpaas properties loaded, wsid:" + wsid);
            return wsid;
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "getWsid ex:" + ex.toString());
            return "";
        }
    }

    public static String getConfigVersion() {
        return SharedPreUtils.getStringData(TransportEnvUtil.getContext(), "httpdns_configversion");
    }

    public static void putConfigVersion(String configVersion) {
        SharedPreUtils.putData(TransportEnvUtil.getContext(), (String) "httpdns_configversion", configVersion);
    }
}
