package com.alipay.mobile.security.bio.service;

import com.alipay.mobile.security.bio.api.BioCallback;
import com.alipay.mobile.security.bio.utils.SignHelper;
import java.util.HashMap;
import java.util.UUID;

public class BioAppManager extends BioService {
    private HashMap<String, BioAppDescription> a;
    private HashMap<String, BioCallback> b;

    public void onCreate(BioServiceManager bioServiceManager) {
        super.onCreate(bioServiceManager);
        this.a = new HashMap<>();
        this.b = new HashMap<>();
    }

    public BioAppDescription getBioAppDescription(String str) {
        return this.a.get(str);
    }

    public BioCallback getBioCallback(String str) {
        return this.b.get(str);
    }

    public void onDestroy() {
        this.a.clear();
        this.b.clear();
        super.onDestroy();
    }

    public String put(BioAppDescription bioAppDescription, BioCallback bioCallback) {
        String MD5 = SignHelper.MD5(System.currentTimeMillis() + "_" + (Math.random() * 10000.0d) + UUID.randomUUID().toString());
        bioAppDescription.setTag(MD5);
        this.a.put(MD5, bioAppDescription);
        this.b.put(MD5, bioCallback);
        return MD5;
    }
}
