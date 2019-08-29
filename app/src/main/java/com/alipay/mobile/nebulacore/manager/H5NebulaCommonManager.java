package com.alipay.mobile.nebulacore.manager;

import com.alipay.mobile.nebula.appcenter.common.NebulaCommonInfo;
import com.alipay.mobile.nebula.appcenter.common.NebulaCommonManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class H5NebulaCommonManager implements NebulaCommonManager {
    private List<NebulaCommonInfo> a = Collections.synchronizedList(new ArrayList());

    public synchronized void registerNebulaCommonInfo(NebulaCommonInfo nebulaCommonInfo) {
        if (Nebula.DEBUG && nebulaCommonInfo != null) {
            String log = "";
            for (String appId : nebulaCommonInfo.getAppIdList()) {
                log = appId + Token.SEPARATOR + log;
            }
            H5Log.d("H5NebulaCommonManager", "registerNebulaCommonInfo " + log);
        }
        this.a.add(nebulaCommonInfo);
    }

    public synchronized void unregisterNebulaCommonInfo(NebulaCommonInfo nebulaCommonInfo) {
        this.a.remove(nebulaCommonInfo);
    }

    public synchronized List<NebulaCommonInfo> getNebulaAppCallbackList() {
        if (Nebula.DEBUG) {
            String log = "";
            for (NebulaCommonInfo appIdList : this.a) {
                for (String appId : appIdList.getAppIdList()) {
                    log = appId + Token.SEPARATOR + log;
                }
            }
            H5Log.d("H5NebulaCommonManager", "getNebulaAppCallbackList " + log);
        }
        return this.a;
    }
}
