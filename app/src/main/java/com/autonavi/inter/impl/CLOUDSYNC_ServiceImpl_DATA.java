package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.cloudsync.NetWorkImpl;
import com.autonavi.sync.ICallback;
import com.autonavi.sync.INetwork;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.cloudsync.NetWorkImpl", "com.autonavi.cloudsync.CallbackImpl", "com.autonavi.common.cloudsync.CacheService", "com.autonavi.cloudsync.SyncManagerImpl"}, inters = {"com.autonavi.sync.INetwork", "com.autonavi.sync.ICallback", "com.autonavi.common.cloudsync.inter.ICacheService", "com.autonavi.common.cloudsync.ISyncManager"}, module = "cloudsync")
@KeepName
public final class CLOUDSYNC_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public CLOUDSYNC_ServiceImpl_DATA() {
        put(INetwork.class, NetWorkImpl.class);
        put(ICallback.class, bhx.class);
        put(bip.class, bih.class);
        put(bik.class, bhy.class);
    }
}
