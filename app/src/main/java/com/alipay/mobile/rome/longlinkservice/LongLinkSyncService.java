package com.alipay.mobile.rome.longlinkservice;

import android.content.Context;
import com.alipay.mobile.rome.longlinkservice.syncmodel.SyncInitInfo;
import com.alipay.mobile.rome.longlinkservice.syncmodel.SyncUpMessage;

public abstract class LongLinkSyncService {
    private static final String LONG_LINK_SERVICE_IMPL = "com.alipay.mobile.rome.syncservice.service.LongLinkSyncServiceImpl";
    private static LongLinkSyncService sInstance;

    public abstract void appToBackground();

    public abstract void appToForeground();

    public abstract void cancelSendSyncMsg(String str, String str2);

    public abstract String getBizSyncKey(String str, String str2);

    public abstract String getCdid();

    public abstract boolean getLinkState();

    public abstract boolean isConnected();

    public abstract boolean isSyncAvailable();

    public abstract void log(String str, String str2, String str3);

    public abstract SyncState querSyncNetState(String str);

    public abstract void refreshBiz(String str);

    public abstract void registerBiz(String str);

    public abstract void registerBizCallback(String str, ISyncCallback iSyncCallback);

    public abstract void registerSendCallback(String str, ISyncUpCallback iSyncUpCallback);

    public abstract void registerSyncStateCallback(ISyncStateCallback iSyncStateCallback);

    public abstract void reportCmdReceived(String str, String str2, String str3);

    public abstract void reportCommandHandled(String str, String str2, String str3);

    public abstract void reportMsgHandled(String str);

    public abstract void reportMsgReceived(String str, String str2, String str3);

    public abstract String sendSyncMsg(SyncUpMessage syncUpMessage);

    public abstract boolean sendSyncMsg(String str, String str2);

    public abstract String sendSyncMsgNeedCallback(SyncUpMessage syncUpMessage);

    public abstract <K> String sendSyncMsgWithResponse(SyncUpMessage syncUpMessage, ISyncUpResp<K> iSyncUpResp);

    public abstract void setInitInfo(SyncInitInfo syncInitInfo);

    public abstract void setLogPrinter(ILogPrint iLogPrint);

    public abstract void setMonitorCallback(ISyncMonitor iSyncMonitor);

    public abstract void unregisterBiz(String str);

    public abstract void unregisterBizCallback(String str);

    public abstract void unregisterSyncStateCallback(ISyncStateCallback iSyncStateCallback);

    public abstract void updateBizSyncKey(String str, String str2, String str3);

    public abstract void updateUserInfo(String str, String str2);

    public static LongLinkSyncService getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LongLinkSyncService.class) {
                try {
                    sInstance = (LongLinkSyncService) Class.forName(LONG_LINK_SERVICE_IMPL).getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
                } catch (Throwable th) {
                    StringBuilder sb = new StringBuilder("init LongLinkSyncServiceImpl exception found[");
                    sb.append(th);
                    sb.append("]");
                }
            }
        }
        return sInstance;
    }
}
