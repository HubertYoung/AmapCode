package com.alipay.mobile.common.transport.iprank.mng.resolve;

import android.content.Context;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.iprank.biz.IpRankStorageBiz;
import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.dao.models.RealTimeLocation;
import com.alipay.mobile.common.transport.iprank.utils.IpRankUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LocalDNSResolve {
    public static final String TAG = "IPR_DNSResolve";
    private static LocalDNSResolve a = null;
    private RealTimeLocation b = null;
    public Context mContext;
    public IpRankStorageBiz storageBiz;

    class InetAddrGetAllByNameTask implements Callable<InetAddress[]> {
        private String a;

        InetAddrGetAllByNameTask(String host) {
            this.a = host;
        }

        public InetAddress[] call() {
            return DnsUtil.getAllByName(this.a);
        }
    }

    public static LocalDNSResolve getInstance(Context context) {
        if (a != null) {
            return a;
        }
        synchronized (LocalDNSResolve.class) {
            try {
                if (a == null) {
                    a = new LocalDNSResolve(context);
                }
            }
        }
        return a;
    }

    private LocalDNSResolve(Context context) {
        this.mContext = context;
        this.storageBiz = IpRankStorageBiz.getInstance(context);
        this.b = RealTimeLocation.getInstance(this.mContext);
    }

    public InetAddress[] getAllByName(final String host) {
        Future future = null;
        try {
            int timeout = TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.GET_ALL_BY_NAME_TIME_OUT);
            future = NetworkAsyncTaskExecutor.submit((Callable<T>) new InetAddrGetAllByNameTask<T>(host));
            final InetAddress[] inetAddresses = (InetAddress[]) future.get((long) timeout, TimeUnit.SECONDS);
            NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                public void run() {
                    if (!MiscUtils.isOtherProcess(LocalDNSResolve.this.mContext)) {
                        int ttlConfig = TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.IPRANK_TTL);
                        LocalDNSResolve.this.a(host, LocalDNSResolve.this.a(host, inetAddresses, ttlConfig), ttlConfig, inetAddresses);
                    }
                }
            });
            LogCatUtil.info(TAG, "LocalDNSResolve success,host=" + host + ",ips=" + Arrays.toString(inetAddresses));
            a(future);
            return inetAddresses;
        } catch (Exception e) {
            if (e instanceof UnknownHostException) {
                throw a(host, e);
            }
            Throwable rootCause = MiscUtils.getRootCause(e);
            if (rootCause == null || !(rootCause instanceof UnknownHostException)) {
                throw a(host, e);
            }
            throw a(host, rootCause);
        } catch (Throwable th) {
            a(future);
            throw th;
        }
    }

    private static void a(Future<InetAddress[]> future) {
        if (future != null) {
            try {
                if (!future.isDone()) {
                    future.cancel(true);
                }
            } catch (Throwable th) {
                LogCatUtil.info(TAG, "getAllByName，exception");
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(String host, ArrayList<IpRankModel> list, int ttl_config, InetAddress[] inetAddresses) {
        if (inetAddresses != null && inetAddresses.length != 0) {
            removeNotInLocaldns(host, inetAddresses);
            for (int i = 0; i < list.size(); i++) {
                IpRankModel ipRankModel = list.get(i);
                if (this.storageBiz.getIpRankManager().isIpRankModelInDB(ipRankModel)) {
                    IpRankModel tmpModel = this.storageBiz.getIpRankModel(host, ipRankModel.ip);
                    if (tmpModel != null) {
                        tmpModel.time = ipRankModel.time;
                        tmpModel.ttl = tmpModel.time + ((long) (ttl_config * 60 * 1000));
                        this.storageBiz.updateIp2DB(tmpModel);
                    }
                } else {
                    this.storageBiz.putIp2DB(ipRankModel);
                }
            }
            this.storageBiz.getCache().remove(host);
            this.storageBiz.reloadIpInfoToCache(host);
        }
    }

    public void removeNotInLocaldns(String host, InetAddress[] inetAddresses) {
        String ipParam = "(";
        int i = 0;
        while (i < inetAddresses.length) {
            try {
                ipParam = ipParam + "'" + inetAddresses[i].getHostAddress() + "',";
                i++;
            } catch (Throwable ex) {
                LogCatUtil.error((String) TAG, "removeNotInLocaldns ex:" + ex.toString());
                return;
            }
        }
        this.storageBiz.removeipsNotinLocaldns(host, ipParam.substring(0, ipParam.length() - 1) + ")");
    }

    private static UnknownHostException a(String host, Throwable e) {
        UnknownHostException newException = new UnknownHostException("original hostname: " + host);
        try {
            newException.initCause(e);
            return newException;
        } catch (Exception e2) {
            if (e instanceof UnknownHostException) {
                throw ((UnknownHostException) e);
            }
            throw new UnknownHostException(" host:" + host + "  message: " + e.toString());
        }
    }

    /* access modifiers changed from: private */
    public ArrayList<IpRankModel> a(String host, InetAddress[] inetAddresses, int ttlConfig) {
        if (inetAddresses == null || inetAddresses.length == 0) {
            return null;
        }
        long time = System.currentTimeMillis();
        ArrayList list = new ArrayList(len);
        long tmpLbsId = this.b.getLbsIdGently();
        int curNetType = IpRankUtil.getNetType(this.mContext);
        for (InetAddress hostAddress : inetAddresses) {
            IpRankModel ipRankModel = new IpRankModel();
            ipRankModel.lbs_id = tmpLbsId;
            ipRankModel.domain = host;
            ipRankModel.ip = hostAddress.getHostAddress();
            ipRankModel.time = time;
            ipRankModel.ttl = ((long) (ttlConfig * 60 * 1000)) + time;
            ipRankModel.netType = curNetType;
            ipRankModel.rtt = 0;
            ipRankModel.successCount = 0;
            ipRankModel.failCount = 0;
            ipRankModel.feedbackSuccCount = 0;
            ipRankModel.feedbackSuccTime = -1;
            ipRankModel.lastSuccTime = -1;
            ipRankModel.grade = -1.0f;
            list.add(ipRankModel);
        }
        return list;
    }
}
