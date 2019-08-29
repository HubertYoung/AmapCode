package com.ali.user.mobile.accountbiz.extservice.impl;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.ali.user.mobile.account.DeviceCallBack;
import com.ali.user.mobile.account.bean.DeviceInfoBean;
import com.ali.user.mobile.account.domain.MspDeviceInfoBean;
import com.ali.user.mobile.accountbiz.extservice.DeviceService;
import com.ali.user.mobile.accountbiz.extservice.base.BaseExtService;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.log.LoggerUtils;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.cashier.service.InsideServiceGetTid;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;

public class DeviceServiceImpl extends BaseExtService implements DeviceService {
    private static DeviceService mDeviceService;
    final String TAG = "DeviceServiceImpl";
    DeviceCallBack deviceCallBack;

    private DeviceServiceImpl(Context context) {
        super(context);
    }

    public static DeviceService getInstance(Context context) {
        if (mDeviceService == null) {
            synchronized (DeviceServiceImpl.class) {
                try {
                    if (mDeviceService == null) {
                        mDeviceService = new DeviceServiceImpl(context);
                    }
                }
            }
        }
        return mDeviceService;
    }

    public MspDeviceInfoBean queryCertification() {
        AliUserLog.c("DeviceServiceImpl", "queryCertification");
        return queryMspTid();
    }

    public DeviceInfoBean queryDeviceInfo() {
        AliUserLog.c("DeviceServiceImpl", "queryDeviceInfo-直接从快捷获取mspTid");
        DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
        MspDeviceInfoBean queryCertification = queryCertification();
        if (queryCertification != null) {
            deviceInfoBean.setWalletTid(queryCertification.getTid());
        }
        return deviceInfoBean;
    }

    private MspDeviceInfoBean queryMspTid() {
        AliUserLog.c("DeviceServiceImpl", "调用移动快捷获取tid 开始");
        MspDeviceInfoBean mspDeviceInfoBean = new MspDeviceInfoBean();
        try {
            Bundle bundle = (Bundle) PluginManager.b(PhoneCashierPlugin.KEY_SERVICE_GET_TID).startForResult(null);
            if (bundle == null) {
                AliUserLog.d("DeviceServiceImpl", "调用移动快捷获取tid=null");
            } else {
                String string = bundle.getString(InsideServiceGetTid.CASHIER_TID);
                String string2 = bundle.getString(InsideServiceGetTid.CASHIER_TID_SEED);
                String string3 = bundle.getString("IMEI");
                String string4 = bundle.getString("IMSI");
                String string5 = bundle.getString(InsideServiceGetTid.CASHIER_TID_VIRTUALTMEI);
                String string6 = bundle.getString(InsideServiceGetTid.CASHIER_TID_VIRTUALIMSI);
                String format = String.format("调用移动快捷获取tid=%s, key=%s, imei=%s, imsi=%s, vimei=%s, vimsi=%s", new Object[]{string, string2, string3, string4, string5, string6});
                AliUserLog.c("DeviceServiceImpl", format);
                if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string5) || TextUtils.isEmpty(string6)) {
                    LoggerUtils.a("loadOrCreateTID:".concat(String.valueOf(format)));
                }
                mspDeviceInfoBean.setImei(string3);
                mspDeviceInfoBean.setImsi(string4);
                mspDeviceInfoBean.setMspkey(string2);
                mspDeviceInfoBean.setTid(string);
                mspDeviceInfoBean.setVimei(string5);
                mspDeviceInfoBean.setVimsi(string6);
            }
        } catch (Throwable th) {
            AliUserLog.a("DeviceServiceImpl", "调用移动快捷获取tid 失败，可能未安装最新移动快捷", th);
            LogAgent.a(th);
        }
        return mspDeviceInfoBean;
    }
}
