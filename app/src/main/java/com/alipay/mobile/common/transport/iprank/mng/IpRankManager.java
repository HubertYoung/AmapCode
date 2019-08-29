package com.alipay.mobile.common.transport.iprank.mng;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.iprank.dao.IpRankDao;
import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.util.ArrayList;

public class IpRankManager {
    public static final String TAG = "IPR_IpRankManager";
    public IpRankDao ipRankDao = new IpRankDao(this.mContext);
    public Context mContext;

    public IpRankManager(Context context) {
        this.mContext = context;
    }

    public void putIpRankModel2DB(IpRankModel ipRankModel) {
        this.ipRankDao.putIpRankModel2DB(ipRankModel);
    }

    public void deleteFromIpRank() {
        int size = this.ipRankDao.getTableSize();
        LogCatUtil.warn((String) TAG, "before deleteFromIpRank,the size is: " + size);
        TransportConfigureManager configMgr = TransportConfigureManager.getInstance();
        int maxSize = configMgr.getIntValue(TransportConfigureItem.IPRANK_MAX_SIZE);
        String threshold = configMgr.getStringValue((ConfigureItem) TransportConfigureItem.IPRANK_DELETE_THRESHOLD);
        if (size > maxSize) {
            LogCatUtil.debug(TAG, "deleteFromIpRank,the table is too large,will delete some");
            this.ipRankDao.deleteFromIpRank(Double.parseDouble(threshold));
            int size2 = this.ipRankDao.getTableSize();
            LogCatUtil.warn((String) TAG, "after deleteFromIpRank,the size is: " + size2);
            if (size2 > maxSize) {
                LogCatUtil.debug(TAG, "deleteFromIpRank,the table is still very large,will delete strongly");
                this.ipRankDao.deleteStrongly();
                int size3 = this.ipRankDao.getTableSize();
                LogCatUtil.warn((String) TAG, "after delete strongly,the size is: " + size3);
                if (size3 > maxSize) {
                    LogCatUtil.debug(TAG, "deleteFromIpRank,have to delete finally... ");
                    this.ipRankDao.deleteFinally(size3 - maxSize);
                    LogCatUtil.warn((String) TAG, "after delete finally,the size is: " + this.ipRankDao.getTableSize());
                }
            }
        }
    }

    public void updateIpRankModel2DB(IpRankModel ipRankModel) {
        this.ipRankDao.updateIpRankModel(ipRankModel);
    }

    public ArrayList<IpRankModel> getIpRankModels(String host, long lbs_id, int netType) {
        if (TextUtils.isEmpty(host)) {
            return null;
        }
        return this.ipRankDao.getIpRankModels(host, lbs_id, netType);
    }

    public IpRankModel getIpRankModel(String host, String ip, long lbs_id, int netType) {
        if (TextUtils.isEmpty(host)) {
            return null;
        }
        return this.ipRankDao.getIpRankModel(host, ip, lbs_id, netType);
    }

    public boolean isIpRankModelInDB(IpRankModel ipRankModel) {
        return this.ipRankDao.isIpRankModelInDB(ipRankModel);
    }

    public boolean isIpRankModelInDB(String host, String ip, int netType, long lbs_id) {
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        return this.ipRankDao.isIpRankModelInDB(host, ip, netType, lbs_id);
    }

    public ArrayList<IpRankModel> getAllIpRankModels(long lbs_id, int netType) {
        return this.ipRankDao.getAllIpRankModels(lbs_id, netType);
    }

    public ArrayList<IpRankModel> getGivenNumIpRankModels(int num, long lbs_id, int netType) {
        return this.ipRankDao.getGivenNumIpRankModels(num, lbs_id, netType);
    }

    public int getIpNum(long lbs_id, int netType) {
        ArrayList ips = getAllIpRankModels(lbs_id, netType);
        if (ips != null) {
            return ips.size();
        }
        return -1;
    }

    public int getTableSize() {
        return this.ipRankDao.getTableSize();
    }

    public void removeIpsByHost(String host) {
        this.ipRankDao.removeIpsByHost(host);
    }

    public void removeSingleIp(String host, String ip) {
        this.ipRankDao.removeSingleIp(host, ip);
    }

    public void clearIprank() {
        this.ipRankDao.clearIprank();
    }

    public void removeipsNotinLocaldns(String host, long lbsId, int netType, String ipParams) {
        this.ipRankDao.removeipsNotinLocaldns(host, lbsId, netType, ipParams);
    }
}
