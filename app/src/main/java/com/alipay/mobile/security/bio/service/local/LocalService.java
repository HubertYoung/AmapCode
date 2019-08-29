package com.alipay.mobile.security.bio.service.local;

import android.content.Context;
import com.alipay.mobile.security.bio.service.BioService;
import com.alipay.mobile.security.bio.service.BioServiceManager;

public abstract class LocalService extends BioService {
    public Context mContext;

    public void onCreate(BioServiceManager bioServiceManager) {
        super.onCreate(bioServiceManager);
        if (bioServiceManager != null) {
            this.mContext = bioServiceManager.getBioApplicationContext();
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }
}
