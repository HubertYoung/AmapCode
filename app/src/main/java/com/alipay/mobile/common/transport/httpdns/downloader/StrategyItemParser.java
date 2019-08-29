package com.alipay.mobile.common.transport.httpdns.downloader;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.httpdns.HttpDns;
import com.alipay.mobile.common.transport.httpdns.HttpDns.HttpdnsIP;
import com.alipay.mobile.common.transport.httpdns.HttpdnsIPEntry;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.taobao.accs.utl.BaseMonitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class StrategyItemParser {
    public static final String GROUP_VALUE = "rd";
    public static final String TAG = "HTTP_DNS_StrategyItemParser";
    /* access modifiers changed from: private */
    public static boolean a = false;

    public static boolean isOversea() {
        return a;
    }

    public static String generateStrategyReq(StrategyRequest strategyReq) {
        if (strategyReq == null) {
            LogCatUtil.info(TAG, "request 为null");
            return null;
        }
        try {
            String request = JSON.toJSONString(strategyReq);
            LogCatUtil.info(TAG, "请求体: " + request);
            return request;
        } catch (JSONException e) {
            LogCatUtil.error(TAG, "JSONException", e);
            return null;
        }
    }

    public static StrategyResponse parseStrategyContent(String result) {
        return parseStrategyContent(result, false);
    }

    public static StrategyResponse parseStrategyContent(String result, boolean ipListOnly) {
        LogCatUtil.info(TAG, "result: " + result);
        Map ipListResult = new HashMap();
        long tim = System.currentTimeMillis();
        try {
            JSONObject jsonResult = JSONObject.parseObject(result);
            if (jsonResult == null) {
                LogCatUtil.info(TAG, "jsonResult is null");
                return null;
            }
            long code = jsonResult.getLong("code").longValue();
            String clientIp = jsonResult.getString("clientIp");
            a(jsonResult);
            int ttd = jsonResult.getIntValue("ttd");
            if (ttd <= 0) {
                ttd = 3;
            }
            JSONArray dnsJSONArr = jsonResult.getJSONArray(BaseMonitor.COUNT_POINT_DNS);
            if (dnsJSONArr == null || dnsJSONArr.isEmpty()) {
                return null;
            }
            a(ipListResult, tim, dnsJSONArr, ttd);
            return new StrategyResponse(code, ipListResult, a(ipListOnly, jsonResult), clientIp, a, ttd);
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "parseStrategyContent", ex);
            return null;
        }
    }

    private static void a(JSONObject jsonResult) {
        try {
            Boolean oversea = jsonResult.getBoolean("oversea");
            if (oversea != null) {
                a = oversea.booleanValue();
                LogCatUtil.debug(TAG, "mOversea=[" + a + "]");
                MiscUtils.setOversea(a);
                NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                    public final void run() {
                        SharedPreUtils.putData(TransportEnvUtil.getContext(), (String) "oversea", StrategyItemParser.a);
                    }
                });
                return;
            }
            a = false;
            MiscUtils.setOversea(false);
            NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                public final void run() {
                    SharedPreUtils.removeData(TransportEnvUtil.getContext(), "oversea");
                }
            });
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    private static String a(boolean ipListOnly, JSONObject jsonResult) {
        String confStr = null;
        String configVersion = null;
        if (!ipListOnly) {
            JSONObject confObj = jsonResult.getJSONObject("conf");
            if (confObj != null) {
                confStr = confObj.getString("android_network_core");
                configVersion = confObj.getString("configVersion");
                if (!TextUtils.isEmpty(configVersion)) {
                    DnsUtil.putConfigVersion(configVersion);
                }
            }
            LogCatUtil.info(TAG, "ANC Config Str: " + confStr + " ,configVersion: " + configVersion);
        }
        return confStr;
    }

    private static void a(Map<String, HttpdnsIP> ipListResult, long tim, JSONArray dnsJSONArr, int ttd) {
        for (int i = 0; i < dnsJSONArr.size(); i++) {
            HttpdnsIP tempdnsIP = new HttpdnsIP();
            tempdnsIP.setNetType(AlipayHttpDnsClient.getDnsClient().getNetworkType());
            JSONObject dns = dnsJSONArr.getJSONObject(i);
            String domain = dns.getString("domain");
            tempdnsIP.setTtl((dns.getLong("ttl").longValue() * 1000) + tim);
            tempdnsIP.setTime(tim);
            tempdnsIP.setTtd(ttd);
            String cname = dns.getString("cname");
            if (!TextUtils.isEmpty(cname)) {
                tempdnsIP.setCname(cname);
                ipListResult.put(domain, tempdnsIP);
            } else {
                JSONArray ipsJSONArr = dns.getJSONArray("ips");
                if (ipsJSONArr == null || ipsJSONArr.isEmpty()) {
                    HttpDns.getInstance().getHosts().remove(domain);
                } else if (a(tempdnsIP, ipsJSONArr)) {
                    ipListResult.put(domain, tempdnsIP);
                }
            }
        }
    }

    private static boolean a(HttpdnsIP tempdnsIP, JSONArray ipsJSONArr) {
        String ip = "";
        List ipEntryList = new ArrayList(ipsJSONArr.size());
        for (int j = 0; j < ipsJSONArr.size(); j++) {
            JSONObject tmpJsonIp = ipsJSONArr.getJSONObject(j);
            String tmpGrValue = "";
            String tmpGr = tmpJsonIp.getString("gr");
            if (TextUtils.equals(tmpGr, GROUP_VALUE)) {
                tmpGrValue = tmpGr;
            }
            String tmpIp = tmpJsonIp.getString(OnNativeInvokeListener.ARG_IP);
            HttpdnsIPEntry ipEntry = new HttpdnsIPEntry(tmpGrValue, tmpIp, tmpJsonIp.getIntValue("port"));
            if (DnsUtil.isLogicIP(tmpIp)) {
                ipEntryList.add(ipEntry);
                if (TextUtils.isEmpty(ip)) {
                    ip = tmpIp;
                }
            }
        }
        if (ipEntryList.isEmpty()) {
            return false;
        }
        tempdnsIP.setIp(ip);
        tempdnsIP.setIpEntries((HttpdnsIPEntry[]) ipEntryList.toArray(new HttpdnsIPEntry[ipEntryList.size()]));
        return true;
    }
}
