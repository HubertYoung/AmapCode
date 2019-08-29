package com.alibaba.analytics.core.config;

import com.alibaba.analytics.core.Variables;
import java.util.Map;

public class UTBussinessConfBiz extends UTOrangeConfBiz {
    public void onOrangeConfigurationArrive(String str, Map<String, String> map) {
        if (map.containsKey("tpk")) {
            String str2 = map.get("tpk");
            if (str2 != null) {
                Variables.getInstance().setTPKString(str2);
                UTConfigMgr.postServerConfig("tpk_md5", Variables.getInstance().getTpkMD5());
            }
        }
    }

    public void onNonOrangeConfigurationArrive(String str) {
        super.onNonOrangeConfigurationArrive(str);
    }

    public String[] getOrangeGroupnames() {
        return new String[]{"ut_bussiness"};
    }
}
