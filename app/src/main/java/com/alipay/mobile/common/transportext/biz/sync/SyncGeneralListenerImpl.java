package com.alipay.mobile.common.transportext.biz.sync;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.common.amnet.api.AmnetListenerAdpter;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.util.LogUtilAmnet;
import com.alipay.mobile.h5container.api.H5PageData;
import java.util.HashMap;
import java.util.Map;

public class SyncGeneralListenerImpl extends AmnetListenerAdpter {
    private static final String LOGTAG = "amnet_SyncGeneralListenerImpl";
    private static volatile SyncGeneralListenerImpl instance;

    private SyncGeneralListenerImpl() {
    }

    public static synchronized SyncGeneralListenerImpl getInstance() {
        SyncGeneralListenerImpl syncGeneralListenerImpl;
        synchronized (SyncGeneralListenerImpl.class) {
            try {
                LogUtilAmnet.d(LOGTAG, "SyncGeneralListenerImpl: getInstance ");
                if (instance == null) {
                    instance = new SyncGeneralListenerImpl();
                }
                syncGeneralListenerImpl = instance;
            }
        }
        return syncGeneralListenerImpl;
    }

    public void change(int state) {
        LogUtilAmnet.d(LOGTAG, "change: [ state=" + state + " ]");
        SyncManager.change(state);
    }

    public void panic(int err, String inf) {
        LogUtilAmnet.d(LOGTAG, "panic: ");
        SyncManager.panic(err, inf);
    }

    public Map<Byte, Map<String, String>> collect(Map<Byte, Map<String, String>> param) {
        Map syncInit = new HashMap();
        SyncManager.collectSyncChannel(syncInit);
        param.put(Byte.valueOf(2), syncInit);
        Map commonInitFromSync = new HashMap();
        SyncManager.collectCommonChannel(commonInitFromSync);
        Map oldCommonInit = param.get(Byte.valueOf(0));
        if (oldCommonInit == null) {
            param.put(Byte.valueOf(0), commonInitFromSync);
        } else {
            oldCommonInit.putAll(commonInitFromSync);
        }
        LogUtilAmnet.d(LOGTAG, "collect [ " + JSON.toJSONString(param) + " ]");
        return param;
    }

    public void report(String key, double val) {
        LogUtilAmnet.d(LOGTAG, "report: ");
        SyncManager.report(key, val);
    }

    public void notifyInitOk() {
        LogUtilAmnet.d(LOGTAG, "notifyInitOk: ");
        SyncManager.notifyInitOk();
    }

    public void notifyGift(String key, String val) {
        LogCatUtil.debug(LOGTAG, "notifyGift,key:" + key + ",val:" + val);
        if (!TextUtils.equals(key, "shortcut_mode")) {
            return;
        }
        if (TextUtils.equals(val, H5PageData.KEY_UC_START)) {
            SyncManager.notifyShortLinkStart();
        } else if (TextUtils.equals(val, AudioUtils.CMDSTOP)) {
            SyncManager.notifyShortLinkStop();
        }
    }
}
