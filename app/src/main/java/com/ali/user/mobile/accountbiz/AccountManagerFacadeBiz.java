package com.ali.user.mobile.accountbiz;

import android.content.Context;
import com.ali.user.mobile.account.bean.DeviceInfoBean;
import com.ali.user.mobile.account.bean.Tid;
import com.ali.user.mobile.account.domain.MspDeviceInfoBean;
import com.ali.user.mobile.account.facade.AccountManagerFacade;
import com.ali.user.mobile.account.model.MobileSecurityResult;
import com.ali.user.mobile.accountbiz.extservice.DeviceService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import java.util.List;

public class AccountManagerFacadeBiz {
    protected DeviceService a;
    protected AccountManagerFacade b;
    AccountManagerFacadeBizCallBack c;
    String d = "";
    String e = "";
    String f = "";
    String g = "";
    String h = "";
    String i = "";
    protected Tid j = null;
    private Tid k = null;

    public AccountManagerFacadeBiz(Context context, AccountManagerFacadeBizCallBack accountManagerFacadeBizCallBack) {
        this.a = AntExtServiceManager.getDeviceService(context);
        this.c = accountManagerFacadeBizCallBack;
        this.b = (AccountManagerFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(AccountManagerFacade.class);
        if (this.a != null) {
            MspDeviceInfoBean queryCertification = this.a.queryCertification();
            if (!(queryCertification == null || queryCertification.getTid() == null)) {
                this.j = new Tid();
                this.j.setClientKey(queryCertification.getMspkey());
                this.j.setImei(queryCertification.getImei());
                this.j.setImsi(queryCertification.getImsi());
                this.j.setTid(queryCertification.getTid());
                this.j.setVimei(queryCertification.getVimei());
                this.j.setVimsi(queryCertification.getVimsi());
            }
        }
        DeviceInfoBean queryDeviceInfo = this.a.queryDeviceInfo();
        if (!(queryDeviceInfo == null || queryDeviceInfo.getWalletTid() == null)) {
            this.k = new Tid();
            this.k.setClientKey(DeviceInfo.a().t());
            this.k.setImei(DeviceInfo.a().p());
            this.k.setImsi(DeviceInfo.a().q());
            this.k.setTid(queryDeviceInfo.getWalletTid());
        }
        AliUserLog.c("AccountManagerFacadeBiz", "ActivityApplication: ");
    }

    public final MobileSecurityResult a(MspDeviceInfoBean mspDeviceInfoBean, List<String> list) {
        Tid tid = new Tid();
        tid.setClientKey(mspDeviceInfoBean.getMspkey());
        tid.setImei(mspDeviceInfoBean.getImei());
        tid.setImsi(mspDeviceInfoBean.getImsi());
        tid.setTid(mspDeviceInfoBean.getTid());
        try {
            return this.b.updateWalletLoginAuth(tid, list);
        } catch (RpcException unused) {
            AliUserLog.c("AccountManagerFacadeBiz", "更新免登关系异常");
            return null;
        }
    }
}
