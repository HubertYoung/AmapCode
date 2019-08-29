package com.alibaba.analytics.core.config;

import java.util.Map;

public abstract class UTOrangeConfBiz {
    public abstract String[] getOrangeGroupnames();

    public void onNonOrangeConfigurationArrive(String str) {
    }

    public abstract void onOrangeConfigurationArrive(String str, Map<String, String> map);
}
