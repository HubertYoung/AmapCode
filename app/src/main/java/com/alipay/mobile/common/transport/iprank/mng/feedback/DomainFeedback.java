package com.alipay.mobile.common.transport.iprank.mng.feedback;

import android.content.Context;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.iprank.AlipayDNSHelper;
import com.alipay.mobile.common.transport.iprank.biz.IpRankStorageBiz;
import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.dao.models.RealTimeLocation;
import com.alipay.mobile.common.transport.iprank.utils.IpRankUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;

public class DomainFeedback implements IFeedback {
    private static DomainFeedback a = null;
    private RealTimeLocation b = null;
    public Context mContext;
    public IpRankStorageBiz storageBiz = null;

    public static DomainFeedback getInstance(Context context) {
        if (a != null) {
            return a;
        }
        synchronized (AlipayDNSHelper.class) {
            try {
                if (a == null) {
                    a = new DomainFeedback(context);
                }
            }
        }
        return a;
    }

    private DomainFeedback(Context context) {
        this.mContext = context;
        this.storageBiz = IpRankStorageBiz.getInstance(this.mContext);
        this.b = RealTimeLocation.getInstance(this.mContext);
    }

    public void feedback(String domain, String ip, boolean isSuccess, int rtt) {
        try {
            if (a(domain)) {
                LogCatUtil.info("IPR_DomainFeedback", "feedback domain=[" + domain + "] ip=[" + ip + "] isSuccess=[" + isSuccess + "] rtt=[" + rtt + "]");
                if (!isSuccess) {
                    AlipayDNSHelper.getInstance().removeSingleIp(domain, ip);
                    return;
                }
                int netType = IpRankUtil.getNetType(this.mContext);
                long tmpLbsId = this.b.getLbsIdGently();
                if (this.storageBiz.getIpRankManager().isIpRankModelInDB(domain, ip, netType, tmpLbsId)) {
                    IpRankModel ipRankModel = this.storageBiz.getIpRankModel(domain, ip, netType, tmpLbsId);
                    if (ipRankModel == null) {
                        LogCatUtil.debug("IPR_DomainFeedback", "ipRankModel is null");
                        return;
                    }
                    if (isSuccess) {
                        long time = System.currentTimeMillis();
                        ipRankModel.rtt = rtt;
                        ipRankModel.successCount++;
                        ipRankModel.lastSuccTime = time;
                        ipRankModel.feedbackSuccTime = time;
                        ipRankModel.feedbackSuccCount++;
                    } else {
                        ipRankModel.rtt = 9999;
                        ipRankModel.failCount++;
                    }
                    this.storageBiz.updateIp2DB(ipRankModel);
                    this.storageBiz.getCache().remove(domain);
                    this.storageBiz.reloadIpInfoToCache(domain);
                    return;
                }
                LogCatUtil.debug("IPR_DomainFeedback", "domain:" + domain + " ip: " + ip + " isn't from iprank,need't feedback");
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "IPR_DomainFeedback", ex);
        }
    }

    private boolean a(String domain) {
        if ((MiscUtils.isInAlipayClient(this.mContext) && MiscUtils.isOtherProcess(this.mContext)) || DnsUtil.isLogicIP(domain)) {
            return false;
        }
        if (NetworkUtils.isNetworkAvailable(this.mContext)) {
            return true;
        }
        LogCatUtil.debug("IPR_DomainFeedback", "network is unavailable,need't update db");
        return false;
    }
}
