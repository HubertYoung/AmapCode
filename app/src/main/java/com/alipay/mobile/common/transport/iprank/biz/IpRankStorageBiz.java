package com.alipay.mobile.common.transport.iprank.biz;

import android.content.Context;
import android.text.TextUtils;
import android.util.LruCache;
import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.dao.models.RealTimeLocation;
import com.alipay.mobile.common.transport.iprank.mng.IpRankManager;
import com.alipay.mobile.common.transport.iprank.mng.resolve.LocalDNSResolve;
import com.alipay.mobile.common.transport.iprank.mng.score.ScoreManager;
import com.alipay.mobile.common.transport.iprank.utils.IpRankUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class IpRankStorageBiz {
    public static final int CACHE_CHANGE = 1;
    public static final int CACHE_NO_CHANGE = 2;
    public static final String TAG = "IPR_StorageBiz";
    private static IpRankStorageBiz c = null;
    /* access modifiers changed from: private */
    public Context a;
    private IpRankManager b = null;
    /* access modifiers changed from: private */
    public LruCache<String, ArrayList<IpRankModel>> d = null;
    private ScoreManager e;
    private RealTimeLocation f = null;
    private int g = 2;

    public static IpRankStorageBiz getInstance(Context context) {
        if (c != null) {
            return c;
        }
        synchronized (IpRankStorageBiz.class) {
            try {
                if (c == null) {
                    c = new IpRankStorageBiz(context);
                }
            }
        }
        return c;
    }

    private IpRankStorageBiz(Context context) {
        this.a = context;
        this.d = new LruCache<>(48);
        this.b = new IpRankManager(this.a);
        this.e = ScoreManager.getInstance(this.a);
        this.f = RealTimeLocation.getInstance(this.a);
    }

    public LruCache<String, ArrayList<IpRankModel>> getCache() {
        return this.d;
    }

    public synchronized void setCacheStatus(int status) {
        this.g = status;
    }

    public synchronized int getCacheStatus() {
        return this.g;
    }

    public void clearCache() {
        this.d.evictAll();
    }

    public IpRankManager getIpRankManager() {
        return this.b;
    }

    public InetAddress[] getAllByName(String host) {
        InetAddress[] addresses = getIpFromCache(host);
        return addresses != null ? addresses : getIpFromDB(host);
    }

    public InetAddress[] getIpFromCache(final String host) {
        InetAddress[] inetAddressArr = null;
        if (TextUtils.isEmpty(host)) {
            LogCatUtil.debug(TAG, "getIpFromCache,host is null");
        } else {
            ArrayList ipRankModels = this.d.get(host);
            if (ipRankModels == null || ipRankModels.size() <= 0) {
                LogCatUtil.info(TAG, "getIpFromCache,not hit return null,host=" + host);
            } else {
                int netType = IpRankUtil.getNetType(this.a);
                long tmpLbsId = this.f.getLbsIdGently();
                if (((IpRankModel) ipRankModels.get(0)).netType == netType && ((IpRankModel) ipRankModels.get(0)).lbs_id == tmpLbsId) {
                    this.e.computeIpScore(ipRankModels);
                    inetAddressArr = getIpList(ipRankModels);
                    LogCatUtil.info(TAG, "getIpFromCache,hit it,host=" + host);
                    if (c(ipRankModels)) {
                        NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                            public void run() {
                                try {
                                    IpRankStorageBiz.this.d.remove(host);
                                    LogCatUtil.debug(IpRankStorageBiz.TAG, "getIpFromCache,dns time out,request again,host:" + host);
                                    LocalDNSResolve.getInstance(IpRankStorageBiz.this.a).getAllByName(host);
                                } catch (Throwable ex) {
                                    LogCatUtil.error((String) IpRankStorageBiz.TAG, "getIpFromCache exception:" + ex.toString());
                                }
                            }
                        });
                    }
                } else {
                    LogCatUtil.info(TAG, "getIpFromCache,netType、lbs not hit,return null");
                }
            }
        }
        return inetAddressArr;
    }

    public InetAddress[] getIpFromDB(final String host) {
        InetAddress[] inetAddressArr = null;
        if (TextUtils.isEmpty(host)) {
            LogCatUtil.debug(TAG, "getIpFromDB,host is null");
        } else {
            int netType = IpRankUtil.getNetType(this.a);
            ArrayList list = this.b.getIpRankModels(host, this.f.getLbsIdGently(), netType);
            if (list == null || list.size() <= 0) {
                LogCatUtil.debug(TAG, "getIpFromDB,host: " + host + " ,has no ipinfo in DB");
            } else {
                this.e.computeIpScore(list);
                this.d.remove(host);
                this.d.put(host, list);
                LogCatUtil.info(TAG, "getIpFromDB,host: " + host + ",success");
                inetAddressArr = getIpList(list);
                if (c(list)) {
                    NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                        public void run() {
                            try {
                                LogCatUtil.debug(IpRankStorageBiz.TAG, "getIpFromDB,dns time out,request again,host:" + host);
                                LocalDNSResolve.getInstance(IpRankStorageBiz.this.a).getAllByName(host);
                            } catch (Throwable ex) {
                                LogCatUtil.error((String) IpRankStorageBiz.TAG, "getIpFromDB exception:" + ex.toString());
                            }
                        }
                    });
                }
            }
        }
        return inetAddressArr;
    }

    public void reloadIpInfoToCache(String host) {
        if (!TextUtils.isEmpty(host)) {
            int netType = IpRankUtil.getNetType(this.a);
            ArrayList list = this.b.getIpRankModels(host, this.f.getLbsIdGently(), netType);
            if (list != null && list.size() > 0) {
                this.d.remove(host);
                this.d.put(host, list);
                LogCatUtil.debug(TAG, "reloadIpInfoToCache complete,host:" + host);
            }
        }
    }

    public InetAddress[] getIpList(ArrayList<IpRankModel> list) {
        String[] ips = a(list);
        if (ips == null) {
            LogCatUtil.debug(TAG, "getIpList,return null");
            return null;
        }
        try {
            int len = ips.length;
            InetAddress[] addresses = new InetAddress[len];
            for (int i = 0; i < len; i++) {
                addresses[i] = InetAddress.getByName(ips[i]);
            }
            return addresses;
        } catch (UnknownHostException e2) {
            LogCatUtil.error(TAG, "getIpList,UnknownHostException", e2);
            return null;
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "getIpList,Exception", ex);
            return null;
        }
    }

    private String[] a(ArrayList<IpRankModel> list) {
        int len = list.size();
        if (len <= 0) {
            LogCatUtil.info(TAG, "getIps, ips is null");
            return null;
        }
        String[] ips = new String[len];
        try {
            b(list);
            for (int i = 0; i < len; i++) {
                ips[i] = list.get(i).ip;
            }
            return ips;
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "getIps exception", ex);
            return null;
        }
    }

    private void b(ArrayList<IpRankModel> list) {
        Collections.sort(list, new Comparator<IpRankModel>() {
            public int compare(IpRankModel lhs, IpRankModel rhs) {
                return (int) (rhs.grade - lhs.grade);
            }
        });
    }

    private static boolean c(ArrayList<IpRankModel> list) {
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isTimeOut()) {
                return false;
            }
        }
        return true;
    }

    public void putIp2DB(IpRankModel ipRankModel) {
        if (ipRankModel != null) {
            this.b.putIpRankModel2DB(ipRankModel);
        }
    }

    public void updateIp2DB(IpRankModel ipRankModel) {
        if (ipRankModel != null) {
            this.b.updateIpRankModel2DB(ipRankModel);
        }
    }

    public ArrayList<IpRankModel> getAllIpRankModels() {
        int netType = IpRankUtil.getNetType(this.a);
        return this.b.getAllIpRankModels(this.f.getLbsIdGently(), netType);
    }

    public ArrayList<IpRankModel> getGivenNumIpRankModels(int num) {
        int netType = IpRankUtil.getNetType(this.a);
        return this.b.getGivenNumIpRankModels(num, this.f.getLbsIdGently(), netType);
    }

    public IpRankModel getIpRankModel(String host, String ip, int netType, long lbsId) {
        return this.b.getIpRankModel(host, ip, lbsId, netType);
    }

    public IpRankModel getIpRankModel(String host, String ip) {
        int netType = IpRankUtil.getNetType(this.a);
        return this.b.getIpRankModel(host, ip, this.f.getLbsIdGently(), netType);
    }

    public HashMap<String, ArrayList<IpRankModel>> getHostAndIpRankModels() {
        HashMap map = new HashMap();
        ArrayList allIpRankModels = getAllIpRankModels();
        for (int i = 0; i < allIpRankModels.size(); i++) {
            ArrayList tmpModelList = new ArrayList();
            IpRankModel tmpModel = allIpRankModels.get(i);
            tmpModelList.add(tmpModel);
            String tmpDomain = tmpModel.domain;
            if (map.get(tmpDomain) != null) {
                tmpModelList.addAll(map.get(tmpDomain));
            }
            map.put(tmpDomain, tmpModelList);
        }
        return map;
    }

    public HashMap<String, ArrayList<IpRankModel>> getHostAndIpRankModels(int num) {
        HashMap map = new HashMap();
        ArrayList allIpRankModels = getGivenNumIpRankModels(num);
        for (int i = 0; i < allIpRankModels.size(); i++) {
            ArrayList tmpModelList = new ArrayList();
            IpRankModel tmpModel = allIpRankModels.get(i);
            tmpModelList.add(tmpModel);
            String tmpDomain = tmpModel.domain;
            if (map.get(tmpDomain) != null) {
                tmpModelList.addAll(map.get(tmpDomain));
            }
            map.put(tmpDomain, tmpModelList);
        }
        return map;
    }

    public void deleteFromTable() {
        this.b.deleteFromIpRank();
    }

    public int getIpNum() {
        int netType = IpRankUtil.getNetType(this.a);
        return this.b.getIpNum(this.f.getLbsIdGently(), netType);
    }

    public int getTableSize() {
        return this.b.getTableSize();
    }

    public void removeIpsByHost(String host) {
        this.b.removeIpsByHost(host);
    }

    public void removeSingleIp(String host, String ip) {
        this.b.removeSingleIp(host, ip);
    }

    public void clearIprank() {
        this.b.clearIprank();
    }

    public void removeipsNotinLocaldns(String host, String ipParams) {
        this.b.removeipsNotinLocaldns(host, this.f.getLbsIdGently(), IpRankUtil.getNetType(this.a), ipParams);
    }
}
