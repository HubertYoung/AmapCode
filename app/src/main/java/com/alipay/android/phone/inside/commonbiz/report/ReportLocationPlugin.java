package com.alipay.android.phone.inside.commonbiz.report;

import com.alipay.android.phone.inside.commonbiz.service.AccountUniformityService;
import com.alipay.android.phone.inside.commonbiz.service.CheckAccountUniformityService;
import com.alipay.android.phone.inside.commonbiz.service.CommonGetRunningStatusService;
import com.alipay.android.phone.inside.commonbiz.service.CommonSetRunningStatusService;
import com.alipay.android.phone.inside.commonbiz.service.CommonVerifyService;
import com.alipay.android.phone.inside.commonbiz.service.ListBizService;
import com.alipay.android.phone.inside.commonbiz.service.LoginExpireCheckService;
import com.alipay.android.phone.inside.commonbiz.service.LoginExpireService;
import com.alipay.android.phone.inside.commonbiz.service.OpenAuthUniformityService;
import com.alipay.android.phone.inside.framework.plugin.IInsidePlugin;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class ReportLocationPlugin implements IInsidePlugin {
    private static final String REPORT_SERVICE_NAME = "REPORT_DEVICE_LOCATION_SERVICE";
    private static final String TAG = "ReportLocationPlugin";
    private Map<String, IInsideService> mServices = new HashMap();

    public IInsideService getService(String str) {
        return null;
    }

    public void onRegisted(Object obj) {
    }

    public void onUnRegisted(Object obj) {
    }

    public ReportLocationPlugin() {
        this.mServices.put(REPORT_SERVICE_NAME, new ReportLocationPluginService());
        this.mServices.put("COMMONBIZ_SERVICE_LIST_BIZ", new ListBizService());
        this.mServices.put("COMMONBIZ_SERVICE_ACCOUNTUNIFORMITY", new AccountUniformityService());
        this.mServices.put("COMMONBIZ_SERVICE_CHECKACCOUNTUNIFORMITY", new CheckAccountUniformityService());
        this.mServices.put("COMMONBIZ_SERVICE_LOGIN_EXPIRE", new LoginExpireService());
        this.mServices.put("COMMONBIZ_SERVICE_OPEN_AUTH_ACCOUNTUNIFORMITY", new OpenAuthUniformityService());
        this.mServices.put("COMMONBIZ_SERVICE_LOGIN_EXPIRE_CHECK", new LoginExpireCheckService());
        this.mServices.put("COMMON_SERVICE_VERIFY", new CommonVerifyService());
        this.mServices.put("COMMON_SERVICE_GET_RUNNING_STATUS", new CommonGetRunningStatusService());
        this.mServices.put("COMMON_SERVICE_SET_RUNNING_STATUS", new CommonSetRunningStatusService());
        LoggerFactory.f().a((String) TAG, (String) "ReportLocationPlugin plugin constructor");
    }

    public Map<String, IInsideService> getServiceMap() {
        return this.mServices;
    }
}
