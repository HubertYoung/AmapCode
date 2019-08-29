package com.ut.mini.internal;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.analytics.AnalyticsMgr;
import com.alibaba.analytics.core.ClientVariables;
import com.alibaba.analytics.core.Constants.UT;
import com.alibaba.analytics.core.logbuilder.TimeStampAdjustMgr;
import com.alibaba.analytics.core.sync.HttpsHostPortMgr;
import com.alibaba.analytics.core.sync.TnetHostPortMgr;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.SpSetting;
import com.alibaba.analytics.utils.StringUtils;
import com.ta.utdid2.device.UTDevice;
import com.ut.mini.UTAnalytics;
import com.ut.mini.exposure.ExposureUtils;
import com.ut.mini.exposure.TrackerManager;
import java.util.Map;

public class UTTeamWork {
    private static final String TAG = "UTTeamWork";
    private static UTTeamWork s_instance;

    public void initialized() {
    }

    public static synchronized UTTeamWork getInstance() {
        UTTeamWork uTTeamWork;
        synchronized (UTTeamWork.class) {
            try {
                if (s_instance == null) {
                    s_instance = new UTTeamWork();
                }
                uTTeamWork = s_instance;
            }
        }
        return uTTeamWork;
    }

    public void turnOnRealTimeDebug(Map<String, String> map) {
        Logger.d((String) TAG, "", map.entrySet().toArray());
        UTAnalytics.getInstance().turnOnRealTimeDebug(map);
    }

    public void turnOffRealTimeDebug() {
        Logger.e();
        UTAnalytics.getInstance().turnOffRealTimeDebug();
    }

    public void dispatchLocalHits() {
        UTAnalytics.getInstance().dispatchLocalHits();
    }

    public void saveCacheDataToLocal() {
        UTAnalytics.getInstance().saveCacheDataToLocal();
    }

    public void setToAliyunOsPlatform() {
        UTAnalytics.getInstance().setToAliyunOsPlatform();
    }

    public void closeAuto1010Track() {
        ClientVariables.getInstance().set1010AutoTrackClose();
    }

    public String getUtsid() {
        try {
            String appKey = ClientVariables.getInstance().getAppKey();
            String utdid = UTDevice.getUtdid(ClientVariables.getInstance().getContext());
            long parseLong = Long.parseLong(AnalyticsMgr.getValue("session_timestamp"));
            if (!StringUtils.isEmpty(appKey) && !StringUtils.isEmpty(utdid)) {
                StringBuilder sb = new StringBuilder();
                sb.append(utdid);
                sb.append("_");
                sb.append(appKey);
                sb.append("_");
                sb.append(parseLong);
                return sb.toString();
            }
        } catch (Exception e) {
            Logger.w("", e, new Object[0]);
        }
        return null;
    }

    public void setHostPort4Tnet(Context context, String str, int i) {
        if (context != null && !TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(":");
            sb.append(i);
            SpSetting.put(context, TnetHostPortMgr.TAG_TNET_HOST_PORT, sb.toString());
        }
    }

    public void clearHostPort4Tnet(Context context) {
        if (context != null) {
            SpSetting.put(context, TnetHostPortMgr.TAG_TNET_HOST_PORT, null);
        }
    }

    public void setHost4Https(Context context, String str) {
        if (context != null && !TextUtils.isEmpty(str)) {
            SpSetting.put(context, HttpsHostPortMgr.TAG_HTTPS_HOST_PORT, str);
        }
    }

    public void clearHost4Https(Context context) {
        if (context != null) {
            SpSetting.put(context, HttpsHostPortMgr.TAG_HTTPS_HOST_PORT, null);
        }
    }

    public void setHostPort4Http(Context context, String str) {
        if (context != null && !TextUtils.isEmpty(str)) {
            SpSetting.put(context, UT.TAG_SP_HTTP_TRANSFER_HOST, str);
        }
    }

    public void clearHostPort4Http(Context context) {
        if (context != null) {
            SpSetting.put(context, UT.TAG_SP_HTTP_TRANSFER_HOST, null);
        }
    }

    public void setHost4TimeAdjustService(Context context, String str) {
        if (context != null && !TextUtils.isEmpty(str)) {
            SpSetting.put(context, TimeStampAdjustMgr.TAG_TIME_ADJUST_HOST_PORT, str);
        }
    }

    public void clearHost4TimeAdjustService(Context context) {
        if (context != null) {
            SpSetting.put(context, TimeStampAdjustMgr.TAG_TIME_ADJUST_HOST_PORT, null);
        }
    }

    public void registerExposureViewHandler(ExposureViewHandle exposureViewHandle) {
        TrackerManager.getInstance().registerExposureViewHandler(exposureViewHandle);
    }

    public void unRegisterExposureViewHandler(ExposureViewHandle exposureViewHandle) {
        TrackerManager.getInstance().unRegisterExposureViewHandler(exposureViewHandle);
    }

    public ExposureViewHandle getExposureViewHandler(Activity activity) {
        return TrackerManager.getInstance().getExposureViewHandle();
    }

    public void setExposureTagForWeex(View view) {
        ExposureUtils.setExposureForWeex(view);
    }

    public boolean startExpoTrack(Activity activity) {
        return TrackerManager.getInstance().addToTrack(activity);
    }

    public boolean stopExpoTrack(Activity activity) {
        return TrackerManager.getInstance().removeToTrack(activity);
    }

    public void setIgnoreTagForExposureView(View view) {
        ExposureUtils.setIgnoreTagForExposureView(view);
    }

    public void clearIgnoreTagForExposureView(View view) {
        ExposureUtils.clearIgnoreTagForExposureView(view);
    }
}
