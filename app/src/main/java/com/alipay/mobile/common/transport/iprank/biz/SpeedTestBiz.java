package com.alipay.mobile.common.transport.iprank.biz;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.iprank.dao.IpRankSql;
import com.alipay.mobile.common.transport.iprank.dao.models.IpRankModel;
import com.alipay.mobile.common.transport.iprank.mng.IpLbsManager;
import com.alipay.mobile.common.transport.iprank.mng.score.IScore;
import com.alipay.mobile.common.transport.iprank.mng.score.ScoreManager;
import com.alipay.mobile.common.transport.iprank.mng.speedtest.SpeedTestManager;
import com.alipay.mobile.common.transport.iprank.utils.IpRankUtil;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.monitor.TransportPerformance;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

public class SpeedTestBiz {
    public static final String TAG = "IPR_SpeedTestBiz";
    private static SpeedTestBiz a = null;
    private IpLbsManager b = null;
    private boolean c = false;
    private boolean d = true;
    public Context mContext;
    public IScore scoreManager;
    public SpeedTestManager speedTestManager;
    public IpRankStorageBiz storageBiz;

    public static SpeedTestBiz getInstance(Context context) {
        if (a != null) {
            return a;
        }
        synchronized (SpeedTestBiz.class) {
            try {
                if (a == null) {
                    a = new SpeedTestBiz(context);
                }
            }
        }
        return a;
    }

    private SpeedTestBiz(Context context) {
        this.mContext = context;
        this.storageBiz = IpRankStorageBiz.getInstance(this.mContext);
        this.speedTestManager = SpeedTestManager.getInstance(this.mContext);
        this.scoreManager = ScoreManager.getInstance(this.mContext);
        this.b = new IpLbsManager(this.mContext);
    }

    public boolean isShouldStop() {
        return this.c;
    }

    public void setShouldStop(boolean shouldStop) {
        this.c = shouldStop;
    }

    public void speedTest() {
        try {
            long startTime = System.currentTimeMillis();
            this.storageBiz.deleteFromTable();
            if (b()) {
                Iterator<Entry<String, ArrayList<IpRankModel>>> it = this.storageBiz.getHostAndIpRankModels().entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Entry entry = it.next();
                    if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                        LogCatUtil.debug(TAG, "network unavailable,break");
                        this.d = false;
                        break;
                    } else if (this.c) {
                        LogCatUtil.info(TAG, "shouldStop is true,will break");
                        this.d = false;
                        break;
                    } else {
                        ArrayList tmpList = (ArrayList) entry.getValue();
                        for (int i = 0; i < tmpList.size(); i++) {
                            IpRankModel ipModel = (IpRankModel) tmpList.get(i);
                            int rtt = this.speedTestManager.speedTest(ipModel.ip, 80);
                            if (!(rtt >= 0)) {
                                if (rtt == -1000) {
                                    break;
                                }
                                ipModel.rtt = 9999;
                                ipModel.failCount++;
                            } else {
                                ipModel.rtt = rtt;
                                ipModel.successCount++;
                                ipModel.lastSuccTime = System.currentTimeMillis();
                            }
                        }
                        for (int k = 0; k < tmpList.size(); k++) {
                            this.storageBiz.updateIp2DB((IpRankModel) tmpList.get(k));
                        }
                    }
                }
                this.storageBiz.clearCache();
                a(System.currentTimeMillis() - startTime);
                this.c = false;
                this.d = true;
            }
        } catch (Throwable th) {
            LogCatUtil.debug(TAG, "speedTest exception");
        } finally {
            this.c = false;
            this.d = true;
        }
    }

    private static boolean a() {
        if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.IPRANK_SPEEDTEST_SWITCH), "T")) {
            return true;
        }
        LogCatUtil.debug(TAG, "speedTest switch is not on...");
        return false;
    }

    private boolean b() {
        if (MiscUtils.isAtFrontDesk(this.mContext)) {
            LogCatUtil.warn((String) TAG, (String) "wallet is at front desk,ignore speedtest task");
            return false;
        } else if (MiscUtils.isPushProcess(this.mContext)) {
            LogCatUtil.debug(TAG, "push process don't do speedtest");
            return false;
        } else if (!NetworkUtils.isNetworkAvailable(this.mContext)) {
            LogCatUtil.info(TAG, "speedtest Task,network is not available...");
            return false;
        } else if (a()) {
            return true;
        } else {
            return false;
        }
    }

    private void a(long stalledTime) {
        try {
            Performance pf = new TransportPerformance();
            pf.setSubType("IpRank");
            pf.setParam1("speedTest");
            int ipNum = this.storageBiz.getIpNum();
            int tableSize = this.storageBiz.getTableSize();
            pf.getExtPramas().put("ipNum", String.valueOf(ipNum));
            pf.getExtPramas().put("iprankSize", String.valueOf(tableSize));
            pf.getExtPramas().put("complete", this.d ? "T" : "F");
            pf.getExtPramas().put(IpRankSql.LBS_TABLE, IpRankUtil.getLatLng(this.mContext));
            pf.getExtPramas().put("lbsSize", String.valueOf(this.b.getTableSize()));
            pf.getExtPramas().put("stalled", String.valueOf(stalledTime / 1000));
            MonitorLoggerUtils.uploadPerfLog(pf);
            LogCatUtil.debug(TAG, "speedTest perf:" + pf.toString());
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
    }
}
