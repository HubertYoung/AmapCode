package com.alipay.mobile.nebulacore.manager;

import com.alipay.mobile.nebula.appcenter.listen.NebulaAppCallback;
import com.alipay.mobile.nebula.appcenter.listen.NebulaAppManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class H5NebulaAppManager implements NebulaAppManager {
    private List<NebulaAppCallback> a = Collections.synchronizedList(new ArrayList());

    public synchronized void registerNebulaAppCallback(NebulaAppCallback nebulaAppCallback) {
        if (Nebula.DEBUG) {
            H5Log.d("H5NebulaAppManager", "registerNebulaAppCallback " + H5Utils.getClassName(nebulaAppCallback));
        }
        this.a.add(nebulaAppCallback);
    }

    public synchronized void unregisterNebulaAppCallback(NebulaAppCallback nebulaAppCallback) {
        if (Nebula.DEBUG) {
            H5Log.d("H5NebulaAppManager", "unregisterNebulaAppCallback " + H5Utils.getClassName(nebulaAppCallback));
        }
        this.a.remove(nebulaAppCallback);
    }

    public synchronized List<NebulaAppCallback> getNebulaAppCallbackList() {
        if (Nebula.DEBUG) {
            String log = "";
            for (NebulaAppCallback nebulaAppCallback : this.a) {
                log = log + Token.SEPARATOR + H5Utils.getClassName(nebulaAppCallback);
            }
            H5Log.d("H5NebulaAppManager", "getNebulaAppCallbackList " + log);
        }
        return this.a;
    }
}
