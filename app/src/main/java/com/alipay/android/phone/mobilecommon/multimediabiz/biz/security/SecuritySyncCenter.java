package com.alipay.android.phone.mobilecommon.multimediabiz.biz.security;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.rome.longlinkservice.ISyncCallback;
import com.alipay.mobile.rome.longlinkservice.LongLinkSyncService;
import com.alipay.mobile.rome.longlinkservice.syncmodel.SyncCommand;
import com.alipay.mobile.rome.longlinkservice.syncmodel.SyncMessage;

public class SecuritySyncCenter implements ISyncCallback {
    private static final String BIZ_NAME = "AFTS-DS";
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger((String) "SecuritySyncCenter");
    private static SecuritySyncCenter syncCenter = new SecuritySyncCenter();
    /* access modifiers changed from: private */
    public LongLinkSyncService syncService;

    public static final SecuritySyncCenter get() {
        return syncCenter;
    }

    private SecuritySyncCenter() {
    }

    public void init() {
        logger.d("init Sync center", new Object[0]);
        this.syncService = (LongLinkSyncService) AppUtils.getService(LongLinkSyncService.class);
        if (this.syncService != null) {
            this.syncService.registerBiz(BIZ_NAME);
            this.syncService.registerBizCallback(BIZ_NAME, this);
        }
    }

    public void uninit() {
        this.syncService.unregisterBiz(BIZ_NAME);
        this.syncService.unregisterBizCallback(BIZ_NAME);
    }

    public void onReceiveMessage(final SyncMessage syncMessage) {
        logger.d("onReceiveMessage msg: " + syncMessage, new Object[0]);
        if (!ConfigManager.getInstance().getCommonConfigItem().securityConf.isEnableSyncHandle()) {
            this.syncService.reportMsgReceived(syncMessage);
        } else {
            TaskScheduleManager.get().orderedExecutor().submit("mm-secure", new Runnable() {
                public void run() {
                    try {
                        if (!TextUtils.isEmpty(syncMessage.msgData)) {
                            JSONArray array = JSON.parseArray(syncMessage.msgData);
                            for (int i = 0; i < array.size(); i++) {
                                String pl2 = array.getJSONObject(i).getString(H5Param.PREFETCH_LOCATION);
                                if (!TextUtils.isEmpty(pl2)) {
                                    AftsDsSyncModel model = (AftsDsSyncModel) JSON.parseObject(pl2, AftsDsSyncModel.class);
                                    if ("secure".equals(model.biz) && !TextUtils.isEmpty(model.data)) {
                                        SecuritySyncCenter.this.dispatch((AftsSecurityMsg) JSON.parseObject(model.data, AftsSecurityMsg.class));
                                    }
                                }
                            }
                            SecuritySyncCenter.this.syncService.reportMsgReceived(syncMessage);
                        }
                    } catch (Throwable e) {
                        SecuritySyncCenter.logger.e(e, "onReceiveMessage error", new Object[0]);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void dispatch(AftsSecurityMsg msg) {
        String[] strArr;
        logger.d("dispatch msg: " + msg, new Object[0]);
        for (String fileId : msg.fileIds) {
            if (fileId.contains(MergeUtil.SEPARATOR_KV)) {
                String[] ids = fileId.split("\\|");
                ResSecurityManager.handleSyncIllegalRes(ids[0]);
                ResSecurityManager.handleSyncIllegalRes(ids[1]);
            } else {
                ResSecurityManager.handleSyncIllegalRes(fileId);
            }
        }
    }

    public void onReceiveCommand(SyncCommand syncCommand) {
        logger.d("onReceiveCommand msg: " + syncCommand, new Object[0]);
    }
}
