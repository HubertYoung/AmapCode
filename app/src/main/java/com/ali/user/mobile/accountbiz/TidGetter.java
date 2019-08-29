package com.ali.user.mobile.accountbiz;

import android.content.Context;
import com.ali.user.mobile.account.bean.Tid;
import com.ali.user.mobile.account.domain.MspDeviceInfoBean;
import com.ali.user.mobile.accountbiz.extservice.DeviceService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.log.AliUserLog;

public class TidGetter {
    private DeviceService a;

    public TidGetter(Context context) {
        this.a = AntExtServiceManager.getDeviceService(context);
    }

    public final Tid a() {
        AliUserLog.c("TidGetter", "getClientTid");
        Tid tid = new Tid();
        MspDeviceInfoBean queryCertification = this.a.queryCertification();
        tid.setClientKey(queryCertification.getMspkey());
        tid.setImei(queryCertification.getImei());
        tid.setImsi(queryCertification.getImsi());
        tid.setTid(queryCertification.getTid());
        tid.setVimei(queryCertification.getVimei());
        tid.setVimsi(queryCertification.getVimsi());
        return tid;
    }
}
