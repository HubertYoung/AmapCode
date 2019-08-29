package com.alipay.mobile.common.transportext.biz.diagnose.network;

import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.Address;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration.DetectInf;
import com.alipay.mobile.common.transportext.biz.diagnose.network.NetworkDiagnoseManager.ResultCount;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.ArrayList;
import java.util.List;

public class SpeedTestManager {
    private static final String TAG = "DIAGNOSE-SPEEDTESTMANAGER";
    static boolean firstTime = true;
    static int netErrCode = -2;
    private static SpeedTestManager singleton;
    private DiagnoseStateManager callback = null;
    private ResultCount resultCount = null;

    public static final SpeedTestManager instance() {
        if (singleton == null) {
            singleton = new SpeedTestManager();
        }
        return singleton;
    }

    static List<SpeedTestLinkData> convertLinkData(String data) {
        if (data == null) {
            return null;
        }
        List list = new ArrayList();
        String[] contents = data.split("\\|");
        for (String split : contents) {
            String[] objects = split.split(";");
            if (objects.length >= 9) {
                SpeedTestLinkData speedTestLinkData = new SpeedTestLinkData();
                try {
                    String ip = objects[0];
                    int port = -1;
                    if (NetworkDiagnoseUtil.isSafety(objects[1])) {
                        port = Integer.parseInt(objects[1]);
                    }
                    String result = objects[2];
                    String describe = objects[3];
                    String channel = objects[4];
                    float connTime = 0.0f;
                    if (!objects[5].equals("-") || NetworkDiagnoseUtil.isSafety(objects[5])) {
                        connTime = Float.parseFloat(objects[5]);
                    }
                    float sslTime = 0.0f;
                    if (!objects[6].equals("-") || NetworkDiagnoseUtil.isSafety(objects[6])) {
                        sslTime = Float.parseFloat(objects[6]);
                    }
                    float rtt = 0.0f;
                    if (!objects[7].equals("-") || NetworkDiagnoseUtil.isSafety(objects[7])) {
                        rtt = Float.parseFloat(objects[7]);
                    }
                    int errCode = -1;
                    if (NetworkDiagnoseUtil.isSafety(objects[8])) {
                        errCode = Integer.parseInt(objects[8]);
                    }
                    speedTestLinkData.ip = ip;
                    speedTestLinkData.port = port;
                    speedTestLinkData.result = result;
                    speedTestLinkData.describe = describe;
                    speedTestLinkData.channel = channel;
                    speedTestLinkData.connTime = connTime;
                    speedTestLinkData.sslTime = sslTime;
                    speedTestLinkData.rtt = rtt;
                    speedTestLinkData.errCode = errCode;
                    speedTestLinkData.data = data;
                } catch (Throwable th) {
                    speedTestLinkData.errCode = 9;
                    speedTestLinkData.data = data;
                }
                list.add(speedTestLinkData);
            }
        }
        return list;
    }

    public void register(ResultCount resultCount2) {
        if (resultCount2 != null) {
            this.resultCount = resultCount2;
        }
    }

    public void register(DiagnoseStateManager callback2) {
        if (callback2 != null) {
            this.callback = callback2;
        }
    }

    public SpeedTestLinkData diagnoseByLink(String domain, boolean isSsl) {
        SpeedTestLinkData sLinkData = new SpeedTestLinkData();
        if (firstTime) {
            firstTime = false;
            new NetworkCheck().checkNetwork();
        }
        if (netErrCode < 0) {
            sLinkData.errCode = netErrCode;
            return sLinkData;
        } else if (domain == null) {
            LogCatUtil.warn((String) TAG, (String) "[diagnoseByLink] domain is null.");
            sLinkData.errCode = 1;
            return sLinkData;
        } else {
            try {
                List list = convertLinkData(Link.diagnoseByLink(domain, isSsl, null));
                boolean ok = false;
                if (list == null || list.size() == 0) {
                    sLinkData.errCode = 9;
                } else {
                    SpeedTestLinkData speedTestLinkData = list.get(0);
                    if (speedTestLinkData != null) {
                        if (speedTestLinkData.result != null && speedTestLinkData.result.equals(DictionaryKeys.CTRLXY_Y)) {
                            ok = true;
                        }
                        sLinkData = speedTestLinkData;
                    } else {
                        sLinkData.errCode = 9;
                    }
                }
                Address proxy = NetworkDiagnoseUtil.sysProxy(null, isSsl);
                if (proxy != null) {
                    LogCatUtil.info(TAG, "[diagnoseByLink]proxy:" + proxy.ip + ":" + proxy.port);
                    String resultByProxy = Link.diagnoseByLink(domain, isSsl, proxy);
                    List listProxy = convertLinkData(resultByProxy);
                    LogCatUtil.info(TAG, "[diagnoseByLink] by proxy result:" + resultByProxy);
                    if (!(listProxy == null || listProxy.get(0) == null)) {
                        SpeedTestLinkData speedTestLinkData2 = listProxy.get(0);
                        if (speedTestLinkData2 != null && ((speedTestLinkData2.result != null && speedTestLinkData2.result.equals(DictionaryKeys.CTRLXY_Y)) || !ok)) {
                            sLinkData = speedTestLinkData2;
                        }
                    }
                }
            } catch (Throwable e) {
                LogCatUtil.warn((String) TAG, "[diagnoseByLink]" + e);
            }
            return sLinkData;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean diagnose(DetectInf detectInf) {
        boolean isSsl = true;
        long start = System.currentTimeMillis();
        LogCatUtil.info(TAG, "[diagnose]detectInf begin.");
        if (detectInf == null) {
            LogCatUtil.warn((String) TAG, (String) "[diagnose] dectectInf is null.");
            report(true, false, false, "[diagnose] dectectInf is null.");
            return false;
        }
        if (1 != detectInf.protocol) {
            isSsl = false;
        }
        Address proxy = NetworkDiagnoseUtil.sysProxy(null, isSsl);
        boolean link_ok = new Link(detectInf, this.callback, null).start();
        if (proxy != null) {
            Link link = new Link(detectInf, this.callback, proxy);
            if (this.resultCount != null) {
                this.resultCount.addTotal();
            }
            link_ok |= link.start();
        }
        AlipayQosService.getInstance().estimate(link_ok ? (double) (System.currentTimeMillis() - start) : 5000.0d, 3);
        return link_ok;
    }

    private List<SpeedTestPingData> convertPingData(String data) {
        if (data == null) {
            LogCatUtil.warn((String) TAG, (String) "[convertPingData] data is null.");
            return null;
        }
        LogCatUtil.info(TAG, "[convertPingData] data:" + data);
        List list = new ArrayList();
        String[] contents = data.split("\\|");
        for (String split : contents) {
            String[] objects = split.split(";");
            if (objects.length >= 4) {
                SpeedTestPingData speedTestPingData = new SpeedTestPingData();
                int seq = -1;
                try {
                    if (NetworkDiagnoseUtil.isSafety(objects[0])) {
                        seq = Integer.parseInt(objects[0]);
                    }
                    String ip = objects[1];
                    int ttl = 0;
                    if (NetworkDiagnoseUtil.isSafety(objects[2])) {
                        ttl = Integer.parseInt(objects[2]);
                    }
                    float time = 0.0f;
                    if (NetworkDiagnoseUtil.isSafety(objects[3])) {
                        time = Float.parseFloat(objects[3]);
                    }
                    speedTestPingData.seq = seq;
                    speedTestPingData.ip = ip;
                    speedTestPingData.ttl = ttl;
                    speedTestPingData.time = time;
                    speedTestPingData.data = data;
                } catch (Throwable e) {
                    LogCatUtil.warn((String) TAG, "[convertPingData]" + e);
                    speedTestPingData.data = data;
                }
                list.add(speedTestPingData);
            }
        }
        return list;
    }

    private void report(boolean fin, boolean ok, boolean done, String summary) {
        if (this.callback != null) {
            if (summary != null) {
                summary = "out_diago:" + summary;
            }
            this.callback.report(fin, ok, done, summary);
        }
    }
}
