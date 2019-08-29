package com.alipay.mobile.common.transportext.biz.appevent;

import com.alipay.mobile.common.amnet.api.AmnetGeneralEventManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import com.alipay.mobile.common.transportext.biz.util.LogUtilAmnet;
import java.util.Map;

public class AmnetEventNotify {
    private static final String LOGTAG = new StringBuilder(LogUtilAmnet.PRETAG).append(AmnetEventNotify.class.getSimpleName()).toString();

    public static void onAppLeave() {
        LogUtilAmnet.i(LOGTAG, "frameworkActivityUserleavehint: [AmnetEventNotify] ");
        AppEventManager.getListener().onAppLeaveEvent();
    }

    public static void onAppResume() {
        LogUtilAmnet.i(LOGTAG, "frameworkActivityResume:  [AmnetEventNotify]  ");
        AppEventManager.getListener().onAppResumeEvent();
    }

    public static void setUserInfo(String userId, String sessionId, byte[] syncExtParam) {
        LogUtilAmnet.i(LOGTAG, "setUserInfo:  [AmnetEventNotify] ");
        AmnetUserInfo.setUserInfo(userId, sessionId, syncExtParam);
    }

    public static void onSyncConnState() {
        LogCatUtil.info(LOGTAG, "onSyncConnState");
        AmnetGeneralEventManager amnetGeneralEventManager = AmnetHelper.getAmnetManager().getAmnetGeneralEventManager();
        if (amnetGeneralEventManager.getLatestConnState() != -1) {
            LogCatUtil.info(LOGTAG, "onSyncConnState state have synchronous");
            return;
        }
        int connState = AmnetHelper.getConnState();
        if (connState != -1) {
            amnetGeneralEventManager.notifyConnStateChange(connState);
            LogCatUtil.info(LOGTAG, "onSyncConnState notifyConnStateChange");
        }
    }

    public static int onSyncGetConnState() {
        LogCatUtil.info(LOGTAG, "onSyncGetConnState");
        try {
            return AmnetHelper.getConnState();
        } catch (Throwable e) {
            LogCatUtil.info(LOGTAG, "onSyncGetConnState exception:" + e.toString());
            return -1;
        }
    }

    public static void onSyncInitChanged(Map<String, String> initMap) {
        LogCatUtil.info(LOGTAG, "onSyncInitChanged onSyncInitChanged.");
        AppEventManager.getListener().onSyncInitChanged(initMap);
    }
}
