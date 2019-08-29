package com.alipay.mobile.nebula.appcenter.model;

import android.support.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AppRes extends BaseRes implements Serializable {
    public Map<String, Object> config;
    public List<AppInfo> data;
    @Nullable
    public Map<String, List<String>> discardData;
    public boolean fromNewConfig;
    public List<String> removeAppIdList;
    public String rpcFailDes;
}
