package com.alipay.android.phone.inside.cashier;

import com.alipay.android.phone.inside.cashier.service.CashierTidService;
import com.alipay.android.phone.inside.cashier.service.ExtendParamsService;
import com.alipay.android.phone.inside.cashier.service.InsideAuthV2Service;
import com.alipay.android.phone.inside.cashier.service.InsideEnvService;
import com.alipay.android.phone.inside.cashier.service.InsideServiceCashierReport;
import com.alipay.android.phone.inside.cashier.service.InsideServiceFactory;
import com.alipay.android.phone.inside.cashier.service.InsideServiceGetTid;
import com.alipay.android.phone.inside.cashier.service.InsideServiceTidReset;
import com.alipay.android.phone.inside.framework.plugin.IInsidePlugin;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class PhoneCashierPlugin implements IInsidePlugin {
    public static final String KEY_SERVICE_AUTH_V2 = "com.alipay.android.phone.inside.PHONE_CASHIER_AUTH_V2";
    public static final String KEY_SERVICE_EXTEND_PARAMS = "com.alipay.android.phone.inside.EXTEND_PARAMS";
    public static final String KEY_SERVICE_GET_TID = "com.alipay.android.phone.inside.PHONE_CASHIER_GET_TID";
    public static final String KEY_SERVICE_INSIDE_ENV = "com.alipay.android.phone.inside.INSIDE_ENV";
    public static final String KEY_SERVICE_PAY = "com.alipay.android.phone.inside.PHONE_CASHIER_PAY";
    public static final String KEY_SERVICE_REPORT = "com.alipay.android.phone.inside.PHONE_CASHIER_REPORT";
    public static final String KEY_SERVICE_RESET_TID = "com.alipay.android.phone.inside.PHONE_CASHIER_RESET_TID";
    public static final String KEY_SERVICE_TID_ACTION = "CASHEIR_PLUGIN_TID_ACTION";
    public static final String KEY_SERVICE_UP_CODE_CONFIG = "com.alipay.android.phone.inside.PHONE_CASHIER_UP_CODE_CONFIG";
    static final String TAG = "inside";
    private Map<String, IInsideService> mServiceMap;

    public void onRegisted(Object obj) {
        LoggerFactory.f().b((String) TAG, (String) "PhoneCashierPlugin::onRegisted");
    }

    public void onUnRegisted(Object obj) {
        LoggerFactory.f().b((String) TAG, (String) "PhoneCashierPlugin::onUnRegisted");
    }

    public Map<String, IInsideService> getServiceMap() {
        LoggerFactory.f().b((String) TAG, (String) "PhoneCashierPlugin::getServiceMap");
        initializeService();
        return this.mServiceMap;
    }

    public IInsideService getService(String str) {
        LoggerFactory.f().b((String) TAG, "PhoneCashierPlugin::getService key=".concat(String.valueOf(str)));
        initializeService();
        IInsideService iInsideService = this.mServiceMap.containsKey(str) ? this.mServiceMap.get(str) : null;
        LoggerFactory.f().b((String) TAG, "PhoneCashierPlugin::getService service=".concat(String.valueOf(iInsideService)));
        return iInsideService;
    }

    private void initializeService() {
        LoggerFactory.f().b((String) TAG, (String) "PhoneCashierPlugin::initializeService");
        if (this.mServiceMap == null) {
            this.mServiceMap = new HashMap();
            this.mServiceMap.put(KEY_SERVICE_PAY, InsideServiceFactory.getInsideServicePay());
            this.mServiceMap.put(KEY_SERVICE_GET_TID, new InsideServiceGetTid());
            this.mServiceMap.put(KEY_SERVICE_RESET_TID, new InsideServiceTidReset());
            this.mServiceMap.put(KEY_SERVICE_REPORT, new InsideServiceCashierReport());
            this.mServiceMap.put(KEY_SERVICE_INSIDE_ENV, new InsideEnvService());
            this.mServiceMap.put(KEY_SERVICE_UP_CODE_CONFIG, InsideServiceFactory.getInsideServiceUpCodeConfig());
            this.mServiceMap.put(KEY_SERVICE_EXTEND_PARAMS, new ExtendParamsService());
            this.mServiceMap.put(KEY_SERVICE_TID_ACTION, new CashierTidService());
            this.mServiceMap.put(KEY_SERVICE_AUTH_V2, new InsideAuthV2Service());
        }
    }
}
