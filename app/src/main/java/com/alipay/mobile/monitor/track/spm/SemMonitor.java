package com.alipay.mobile.monitor.track.spm;

import android.text.TextUtils;
import android.view.View;
import com.alipay.android.phone.wallet.spmtracker.ISemMonitor;
import com.alipay.android.phone.wallet.spmtracker.SpmTracker;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.alipay.mobile.monitor.track.TrackIntegrator;
import com.alipay.mobile.monitor.track.TrackIntegrator.OnAutoClickListener;
import com.alipay.mobile.monitor.track.TrackIntegrator.PageInfo;
import com.alipay.mobile.monitor.track.spm.merge.MergeCenter;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.monitor.track.spm.monitor.TrackerExecutor;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.MergeTracker;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.SemClickTracker;
import com.alipay.mobile.monitor.track.spm.sem.SemInfo;
import java.util.HashMap;
import java.util.Map;

enum SemMonitor implements ISemMonitor {
    INSTANCE;
    
    public static String KEY_POS;
    public static String KEY_RPCID;
    public static String KEY_SEMID;
    /* access modifiers changed from: private */
    public final String TAG;
    private OnAutoClickListener mAutoClickListener;
    private TrackerExecutor mTrackerExecutor;

    static {
        KEY_SEMID = "semId";
        KEY_RPCID = MergeUtil.KEY_RID;
        KEY_POS = "pos";
    }

    /* access modifiers changed from: private */
    public void a(String pageId, SemInfo semInfo) {
        if (semInfo != null && !TextUtils.isEmpty(semInfo.getSemId())) {
            Map param4 = new HashMap();
            param4.put(KEY_RPCID, semInfo.getRpcId());
            param4.put(KEY_POS, semInfo.getPos());
            if (semInfo.getEntityInfo() != null && !semInfo.getEntityInfo().isEmpty()) {
                param4.putAll(semInfo.getEntityInfo());
            }
            this.mTrackerExecutor.commitTracker(new SemClickTracker(a(pageId, semInfo.getSemId(), semInfo.getBizCode(), 1, param4, new String[0])));
        }
    }

    public void semClick(String pageId, String semId, Map<String, String> param4, String bizCode) {
        if (!TextUtils.isEmpty(semId)) {
            this.mTrackerExecutor.commitTracker(new SemClickTracker(a(pageId, semId, bizCode, 1, param4, new String[0])));
        }
    }

    private void b(String pageId, SemInfo semInfo) {
        if (semInfo != null && !TextUtils.isEmpty(semInfo.getSemId())) {
            Map param4 = new HashMap();
            param4.put(KEY_RPCID, semInfo.getRpcId());
            param4.put(KEY_POS, semInfo.getPos());
            if (semInfo.getEntityInfo() != null && !semInfo.getEntityInfo().isEmpty()) {
                param4.putAll(semInfo.getEntityInfo());
            }
            MergeCenter.INSTANCE.merge(new MergeTracker(BehavorID.SEMEXPO, a(pageId, semInfo.getSemId(), semInfo.getBizCode(), 1, param4, new String[0])));
        }
    }

    public void semExpo(String pageId, String semId, Map<String, String> param4, String bizCode) {
        if (!TextUtils.isEmpty(semId)) {
            MergeCenter.INSTANCE.merge(new MergeTracker(BehavorID.SEMEXPO, a(pageId, semId, bizCode, 1, param4, new String[0])));
        }
    }

    public void setSemTag(View view, String bizCode, String semId, String rpcId, int pos, Map<String, String> entityInfo) {
        SemInfo semInfo = new SemInfo();
        semInfo.setSemId(semId);
        semInfo.setRpcId(rpcId);
        semInfo.setBizCode(bizCode);
        semInfo.setPos(pos + "");
        semInfo.setEntityInfo(entityInfo);
        a(view, semInfo);
        b(SpmTracker.getPageId(view.getContext()), semInfo);
    }

    public void clearSemTag(View view) {
        a(view, (SemInfo) null);
    }

    public String getSemInfo(View view) {
        SemInfo semInfo = a(view);
        if (semInfo != null) {
            return createSemInfo(semInfo.getSemId(), semInfo.getRpcId());
        }
        return "-";
    }

    public String createSemInfo(String semId, String rpcId) {
        StringBuilder sb = new StringBuilder("");
        if (!TextUtils.isEmpty(semId)) {
            sb.append(KEY_SEMID).append("_").append(semId);
            sb.append("__");
        }
        if (!TextUtils.isEmpty(rpcId)) {
            sb.append(KEY_RPCID).append("_").append(rpcId);
        }
        return sb.toString();
    }

    public String getLastClickSemInfo(Object page) {
        SpmLogCator.debug(this.TAG, "getLastClickSemInfo page:" + page);
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        if (pageInfo == null) {
            return "";
        }
        SpmLogCator.debug(this.TAG, "getLastClickSemInfo pageInfo.lastClickSem:" + pageInfo.lastClickSem);
        return pageInfo.lastClickSem == null ? "" : pageInfo.lastClickSem;
    }

    private void a(View view, SemInfo semInfo) {
        if (view != null && semInfo != null) {
            int semTagId = TrackIntegrator.getSemTagId();
            if ((semTagId >>> 24) < 2) {
                SpmLogCator.error(this.TAG, "The semTagId must be an application-specific resource id. semTagId:" + semTagId);
            } else {
                view.setTag(TrackIntegrator.getSemTagId(), semInfo);
            }
        }
    }

    private SemInfo a(View view) {
        if (view == null) {
            return null;
        }
        Object semInfo = view.getTag(TrackIntegrator.getSemTagId());
        if (semInfo instanceof SemInfo) {
            return (SemInfo) semInfo;
        }
        return null;
    }

    private Builder a(String pageId, String spmId, String bizCode, int loggerLevel, Map<String, String> map, String... params) {
        Builder builder = new Builder("UC-SEM").setSeedID(spmId);
        builder.setBehaviourPro(bizCode).setPageId(pageId).setLoggerLevel(loggerLevel);
        if (params != null && params.length > 0) {
            if (1 <= params.length) {
                builder.setParam1(params[0]);
            }
            if (2 <= params.length) {
                builder.setParam2(params[1]);
            }
            if (3 <= params.length) {
                builder.setParam3(params[2]);
            }
        }
        if (map != null) {
            for (String key : map.keySet()) {
                builder.addExtParam(key, map.get(key));
            }
        }
        return builder;
    }
}
