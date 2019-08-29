package com.alipay.android.phone.inside.cashier.service;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.app.helper.PayHelperServcie;
import com.alipay.android.app.helper.Tid;
import com.alipay.android.app.helper.TidHelper;
import com.alipay.android.phone.inside.cashier.utils.CashierOperation;
import com.alipay.android.phone.inside.cashier.utils.TidDgree;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class InsideServiceGetTid extends AbstractInsideService<Bundle, Bundle> {
    public static final String CASHIER_TID = "Tid";
    public static final String CASHIER_TID_IMEI = "IMEI";
    public static final String CASHIER_TID_IMSI = "IMSI";
    public static final String CASHIER_TID_SEED = "TidSeed";
    public static final String CASHIER_TID_VIRTUALIMSI = "VirtualImsi";
    public static final String CASHIER_TID_VIRTUALTMEI = "VirtualImei";
    static final String PARAMS_IS_LOAD_LOCAL = "IsLoadLocal";

    public Bundle startForResult(Bundle bundle) throws Exception {
        Bundle bundle2;
        LoggerFactory.f().b((String) "inside", (String) "InsideServiceGetTid::startForResult(_)");
        boolean z = (bundle == null || !bundle.containsKey(PARAMS_IS_LOAD_LOCAL)) ? false : bundle.getBoolean(PARAMS_IS_LOAD_LOCAL);
        if (!TextUtils.equals(StaticConfig.a((String) "tidDegradeSwitch"), "true")) {
            try {
                if (StaticConfig.j()) {
                    bundle2 = getTidForAlipay(getContext());
                } else {
                    bundle2 = getTid(getContext(), z);
                }
                return bundle2;
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "cashier", (String) "GetTidEx", th, "isLoadLocalTid:".concat(String.valueOf(z)));
                throw th;
            }
        } else {
            String str = z ? CashierOperation.BIZ_GET_LOCAL_TID : CashierOperation.BIZ_GET_TID;
            Bundle bundle3 = new Bundle();
            bundle3.putString("action", str);
            bundle3.putString("identify", Long.toString(System.currentTimeMillis()));
            Bundle requestOperationResult = new CashierOperation().requestOperationResult(getContext(), str, bundle3);
            if (isLoadTidFailed(requestOperationResult)) {
                LoggerFactory.e().a((String) "cashier", (String) "TidOperationFailed", "result:".concat(String.valueOf(requestOperationResult)));
                requestOperationResult = loadTidDgrade();
            }
            return requestOperationResult;
        }
    }

    private Bundle loadTidDgrade() {
        Bundle bundle;
        Throwable th;
        Bundle bundle2 = new Bundle();
        try {
            bundle = TidDgree.loadTid(getContext());
            try {
                if (isLoadTidFailed(bundle)) {
                    LoggerFactory.e().a((String) "cashier", (String) "TidReOperationFailed", "result:".concat(String.valueOf(bundle)));
                } else {
                    LoggerFactory.d().a("cashier", BehaviorType.EVENT, "TidReOperationSuccess", "result:".concat(String.valueOf(bundle)));
                }
            } catch (Throwable th2) {
                th = th2;
                LoggerFactory.e().a((String) "cashier", (String) "TidReOperationFailed", th);
                return bundle;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            bundle = bundle2;
            th = th4;
            LoggerFactory.e().a((String) "cashier", (String) "TidReOperationFailed", th);
            return bundle;
        }
        return bundle;
    }

    private boolean isLoadTidFailed(Bundle bundle) {
        return bundle == null || !bundle.containsKey(CASHIER_TID) || TextUtils.isEmpty(bundle.getString(CASHIER_TID));
    }

    private Bundle getTidForAlipay(Context context) {
        LoggerFactory.f().b((String) "inside", "InsideServiceGetTid::getTidForAlipay > start :".concat(String.valueOf(context)));
        LoggerFactory.d().a("tidContext", BehaviorType.EVENT, "TidContext", "context:".concat(String.valueOf(context)));
        Tid loadLocalTid = getPayHelperService().loadLocalTid();
        String tid = loadLocalTid.getTid();
        String tidSeed = loadLocalTid.getTidSeed();
        String imei = getPayHelperService().getIMEI();
        String imsi = getPayHelperService().getIMSI();
        String virtualImei = getPayHelperService().getVirtualImei();
        String virtualImsi = getPayHelperService().getVirtualImsi();
        Bundle bundle = new Bundle();
        bundle.putString(CASHIER_TID, tid);
        bundle.putString(CASHIER_TID_SEED, tidSeed);
        bundle.putString("IMEI", imei);
        bundle.putString("IMSI", imsi);
        bundle.putString(CASHIER_TID_VIRTUALTMEI, virtualImei);
        bundle.putString(CASHIER_TID_VIRTUALIMSI, virtualImsi);
        LoggerFactory.f().b((String) "inside", "InsideServiceGetTid::getTidForAlipay >> result=".concat(String.valueOf(bundle)));
        return bundle;
    }

    private Bundle getTid(Context context, boolean z) throws Exception {
        Tid tid;
        LoggerFactory.f().b((String) "inside", "InsideServiceGetTid::getTid > start :".concat(String.valueOf(context)));
        LoggerFactory.d().a("tidContext", BehaviorType.EVENT, "TidContext", "context:".concat(String.valueOf(context)));
        if (z) {
            tid = TidHelper.loadTID(context);
            if (tid == null || TextUtils.isEmpty(tid.getTid())) {
                LoggerFactory.d().a("tidDgree", BehaviorType.EVENT, "CallTidDgree", "tid:".concat(String.valueOf(tid)));
                return TidDgree.loadTid(context);
            }
        } else {
            tid = TidHelper.loadOrCreateTID(context);
        }
        String tid2 = tid.getTid();
        String tidSeed = tid.getTidSeed();
        String imei = TidHelper.getIMEI(context);
        String imsi = TidHelper.getIMSI(context);
        String virtualImei = TidHelper.getVirtualImei(context);
        String virtualImsi = TidHelper.getVirtualImsi(context);
        Bundle bundle = new Bundle();
        bundle.putString(CASHIER_TID, tid2);
        bundle.putString(CASHIER_TID_SEED, tidSeed);
        bundle.putString("IMEI", imei);
        bundle.putString("IMSI", imsi);
        bundle.putString(CASHIER_TID_VIRTUALTMEI, virtualImei);
        bundle.putString(CASHIER_TID_VIRTUALIMSI, virtualImsi);
        LoggerFactory.f().b((String) "inside", "InsideServiceGetTid::getTid >> result=".concat(String.valueOf(bundle)));
        return bundle;
    }

    private PayHelperServcie getPayHelperService() {
        return (PayHelperServcie) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PayHelperServcie.class.getName());
    }
}
