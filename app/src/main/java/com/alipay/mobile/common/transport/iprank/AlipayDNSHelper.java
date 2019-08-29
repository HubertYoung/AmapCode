package com.alipay.mobile.common.transport.iprank;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.iprank.biz.IpRankStorageBiz;
import com.alipay.mobile.common.transport.iprank.biz.SpeedTestBiz;
import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.mng.feedback.DomainFeedback;
import com.alipay.mobile.common.transport.iprank.mng.resolve.LocalDNSResolve;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AlipayDNSHelper {
    public static final int SPEEDTEST_STATUS_BUSY = 1;
    public static final int SPEEDTEST_STATUS_IDLE = 0;
    public static final String TAG = "IPR_ADNSHelper";
    private static AlipayDNSHelper a = null;
    private long b;
    private final int c = 3600000;
    /* access modifiers changed from: private */
    public int d = 0;
    public DomainFeedback domainFeedback = null;
    /* access modifiers changed from: private */
    public int e = -1;
    /* access modifiers changed from: private */
    public int f = -1;
    private List<String> g;
    public LocalDNSResolve localDNSResolve = null;
    public Context mContext = TransportEnvUtil.getContext();
    public SpeedTestBiz speedTestBiz = null;
    public IpRankStorageBiz storageBiz = null;

    class SpeedTestTask implements Runnable {
        SpeedTestTask() {
        }

        public void run() {
            if (!MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.IPRANK_AB_SWITCH)) {
                LogCatUtil.info(AlipayDNSHelper.TAG, "iprank is off,need't speedtest");
                return;
            }
            synchronized (this) {
                if (AlipayDNSHelper.this.d == 1) {
                    LogCatUtil.info(AlipayDNSHelper.TAG, "SPEEDTEST_STATUS_BUSY, return.");
                    return;
                }
                AlipayDNSHelper.this.d = 1;
                try {
                    LogCatUtil.info(AlipayDNSHelper.TAG, "开始执行测速任务...");
                    AlipayDNSHelper.this.c();
                } catch (Throwable th) {
                    LogCatUtil.error(AlipayDNSHelper.TAG, "SpeedTestTask exception", th);
                } finally {
                    AlipayDNSHelper.this.d = 0;
                }
            }
        }
    }

    public static AlipayDNSHelper getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (AlipayDNSHelper.class) {
            try {
                if (a == null) {
                    a = new AlipayDNSHelper();
                }
            }
        }
        return a;
    }

    private AlipayDNSHelper() {
        if (this.mContext == null) {
            throw new IllegalArgumentException("context shouldn't be null");
        }
        this.storageBiz = IpRankStorageBiz.getInstance(this.mContext);
        this.localDNSResolve = LocalDNSResolve.getInstance(this.mContext);
        this.speedTestBiz = SpeedTestBiz.getInstance(this.mContext);
        this.domainFeedback = DomainFeedback.getInstance(this.mContext);
        a();
        f();
    }

    private void a() {
        if (!MiscUtils.isPushProcess(this.mContext)) {
            NetworkAsyncTaskExecutor.scheduleAtFixedRate(new SpeedTestTask(), TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL, 3600000, TimeUnit.MILLISECONDS);
        }
    }

    public InetAddress[] getAllByName(String host) {
        if (TextUtils.isEmpty(host)) {
            LogCatUtil.debug(TAG, "getAllByName host is null");
            return null;
        } else if (!MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.IPRANK_AB_SWITCH)) {
            LogCatUtil.info(TAG, "iprank is off,use local dns");
            return getAllByNameByLocalDNS(host);
        } else {
            try {
                this.e++;
                InetAddress[] addresses = this.storageBiz.getAllByName(host);
                if (addresses != null) {
                    LogCatUtil.info(TAG, "getAllByName,host: " + host + " ,ips: " + Arrays.toString(addresses));
                    this.f++;
                    g();
                    TransportContextThreadLocalUtils.addDnsType(RPCDataItems.VALUE_DT_IPRANK);
                    return addresses;
                }
            } catch (Throwable ex) {
                LogCatUtil.error((String) TAG, "getAllByName,ex:" + ex.toString());
            }
            LogCatUtil.debug(TAG, "getAllByName return null,use local dns");
            g();
            return getAllByNameByLocalDNS(host);
        }
    }

    public InetAddress[] getAllByName(String host, TransportContext transportContext) {
        if (TextUtils.isEmpty(host)) {
            LogCatUtil.debug(TAG, "getAllByName host is null");
            return null;
        }
        String ipH5Switch = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.IPRANK_H5_SWITCH);
        if (transportContext != null && transportContext.bizType == 2 && !TextUtils.equals("T", ipH5Switch)) {
            LogCatUtil.debug(TAG, "H5 don't use ip rank");
            return getAllByNameByLocalDNS(host);
        } else if (!MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.IPRANK_AB_SWITCH)) {
            LogCatUtil.info(TAG, "iprank is off,use local dns");
            return getAllByNameByLocalDNS(host);
        } else {
            try {
                this.e++;
                InetAddress[] addresses = this.storageBiz.getAllByName(host);
                if (addresses != null) {
                    LogCatUtil.info(TAG, "getAllByName,host: " + host + " ,ips: " + Arrays.toString(addresses));
                    this.f++;
                    g();
                    TransportContextThreadLocalUtils.addDnsType(RPCDataItems.VALUE_DT_IPRANK);
                    return addresses;
                }
            } catch (Throwable ex) {
                LogCatUtil.error((String) TAG, "getAllByName,ex:" + ex.toString());
            }
            LogCatUtil.debug(TAG, "getAllByName return null,use local dns");
            g();
            return getAllByNameByLocalDNS(host);
        }
    }

    public InetAddress[] getAllByNameByLocalDNS(String host) {
        TransportContextThreadLocalUtils.addDnsType(RPCDataItems.VALUE_DT_LOCALDNS);
        return this.localDNSResolve.getAllByName(host);
    }

    public void removeIpsInIpRank(final String host) {
        if (!TextUtils.isEmpty(host)) {
            if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                LogCatUtil.debug(TAG, "network isn't available,no remove");
                return;
            }
            try {
                LogCatUtil.debug(TAG, "removeIps,host=[" + host + "]");
                this.storageBiz.getCache().remove(host);
                this.storageBiz.removeIpsByHost(host);
            } catch (Throwable ex) {
                LogCatUtil.error((String) TAG, ex);
            }
            NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                public void run() {
                    try {
                        AlipayDNSHelper.getInstance().getAllByNameByLocalDNS(host);
                    } catch (Throwable ex) {
                        LogCatUtil.error((String) AlipayDNSHelper.TAG, ex);
                    }
                }
            });
        }
    }

    public void removeSingleIp(final String host, String ip) {
        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(host)) {
            try {
                LogCatUtil.debug(TAG, "removeSingleIp,host=[" + host + "] ip=[" + ip + "]");
                this.storageBiz.getCache().remove(host);
                this.storageBiz.removeSingleIp(host, ip);
                NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                    public void run() {
                        try {
                            AlipayDNSHelper.getInstance().getAllByNameByLocalDNS(host);
                        } catch (Throwable ex) {
                            LogCatUtil.error((String) AlipayDNSHelper.TAG, ex);
                        }
                    }
                });
            } catch (Throwable ex) {
                LogCatUtil.error((String) TAG, ex);
            }
        }
    }

    public void feedback(String domain, String ip, boolean isSuccess, int rtt) {
        if (!MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.IPRANK_AB_SWITCH)) {
            LogCatUtil.printInfo(TAG, "feedback,iprank is off");
            return;
        }
        LogCatUtil.info(TAG, "feedback,domain=" + domain + ",ip=" + ip + ",success=" + isSuccess + ",rtt=" + rtt);
        final String str = domain;
        final String str2 = ip;
        final boolean z = isSuccess;
        final int i = rtt;
        NetworkAsyncTaskExecutor.executeSerial(new Runnable() {
            public void run() {
                AlipayDNSHelper.this.domainFeedback.feedback(str, str2, z, i);
            }
        });
    }

    /* access modifiers changed from: private */
    public List<String> b() {
        if (this.g != null && !this.g.isEmpty()) {
            return this.g;
        }
        this.g = new ArrayList(2);
        this.g.add("mygw.alipay.com");
        this.g.add("mobilegw.alipay.com");
        return this.g;
    }

    /* access modifiers changed from: private */
    public void c() {
        this.b = e();
        if (System.currentTimeMillis() - this.b > TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL) {
            d();
            NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                public void run() {
                    AlipayDNSHelper.this.speedTestBiz.speedTest();
                    LogCatUtil.info(AlipayDNSHelper.TAG, "测速任务结束...");
                }
            });
        }
    }

    private void d() {
        SharedPreUtils.putData(this.mContext, (String) "iprank_last_test_time", System.currentTimeMillis());
    }

    private long e() {
        return SharedPreUtils.getLonggData(this.mContext, "iprank_last_test_time");
    }

    private void f() {
        this.e = SharedPreUtils.getIntData(this.mContext, "iprank_queryNum");
        this.f = SharedPreUtils.getIntData(this.mContext, "iprank_hitNum");
        if (this.e == -1) {
            this.e = 0;
        }
        if (this.f == -1) {
            this.f = 0;
        }
    }

    private void g() {
        NetworkAsyncTaskExecutor.executeIO(new Runnable() {
            public void run() {
                if (AlipayDNSHelper.this.e >= 50) {
                    AlipayDNSHelper.this.h();
                    AlipayDNSHelper.this.e = 0;
                    AlipayDNSHelper.this.f = 0;
                }
                SharedPreUtils.putData(AlipayDNSHelper.this.mContext, (String) "iprank_queryNum", AlipayDNSHelper.this.e);
                SharedPreUtils.putData(AlipayDNSHelper.this.mContext, (String) "iprank_hitNum", AlipayDNSHelper.this.f);
            }
        });
    }

    /* access modifiers changed from: private */
    public void h() {
        try {
            Performance pf = new TransportPerformance();
            pf.setSubType("IpRank");
            pf.setParam1("ratio");
            pf.getExtPramas().put("queryNum", String.valueOf(this.e));
            pf.getExtPramas().put("hitNum", String.valueOf(this.f));
            pf.getExtPramas().put("Lrucache", this.storageBiz.getCache().toString());
            MonitorLoggerUtils.uploadPerfLog(pf);
            LogCatUtil.debug(TAG, "ip rank perf:" + pf.toString());
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }

    public void seeYouAgain() {
        try {
            this.storageBiz.getCache().evictAll();
            this.storageBiz.clearIprank();
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "seeYouAgain ex:" + ex.toString());
        }
    }

    public void putIpRankMode(IpRankModel ipRankModel) {
        try {
            this.storageBiz.getCache().remove(ipRankModel.domain);
            this.storageBiz.putIp2DB(ipRankModel);
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "putIpRankMode ex:" + ex.toString());
        }
    }
}
