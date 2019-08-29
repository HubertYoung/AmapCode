package com.alipay.mobile.nebula.appcenter.rpcblacklist;

import java.util.HashMap;
import java.util.Map;

public class H5RpcBlackList {
    private static H5RpcBlackList instance;
    private Map<String, H5RpcUpdateResponse> blackList = new HashMap();

    private H5RpcBlackList() {
    }

    public static synchronized H5RpcBlackList getInstance() {
        H5RpcBlackList h5RpcBlackList;
        synchronized (H5RpcBlackList.class) {
            try {
                if (instance == null) {
                    instance = new H5RpcBlackList();
                }
                h5RpcBlackList = instance;
            }
        }
        return h5RpcBlackList;
    }

    public synchronized void add(String appId, H5RpcUpdateResponse h5RpcUpdateResponse) {
        if (appId != null) {
            this.blackList.put(appId, h5RpcUpdateResponse);
        }
    }

    public synchronized void remove(String appId) {
        if (appId != null) {
            this.blackList.remove(appId);
        }
    }

    public synchronized boolean contains(String appId) {
        boolean containsKey;
        if (appId == null) {
            containsKey = false;
        } else {
            try {
                containsKey = this.blackList.containsKey(appId);
            }
        }
        return containsKey;
    }
}
