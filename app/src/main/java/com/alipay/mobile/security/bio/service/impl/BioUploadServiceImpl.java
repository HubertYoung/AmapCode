package com.alipay.mobile.security.bio.service.impl;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioUploadCallBack;
import com.alipay.mobile.security.bio.service.BioUploadItem;
import com.alipay.mobile.security.bio.service.BioUploadService;

public class BioUploadServiceImpl extends BioUploadService {
    private BioUploadWatchThread a;

    public void setZimId(String str) {
        if (this.a != null && !TextUtils.isEmpty(str)) {
            this.a.setZimId(str);
        }
    }

    public void addCallBack(BioUploadCallBack bioUploadCallBack) {
        if (this.a != null && bioUploadCallBack != null) {
            this.a.addBioUploadCallBack(bioUploadCallBack);
        }
    }

    public int upload(BioUploadItem bioUploadItem) {
        if (this.a != null) {
            this.a.addBioUploadItem(bioUploadItem);
        }
        return 0;
    }

    public boolean isFulled() {
        if (this.a != null) {
            return this.a.isFulled();
        }
        return false;
    }

    public void onCreate(BioServiceManager bioServiceManager) {
        this.a = new BioUploadWatchThread("BioUploadService", bioServiceManager);
        this.a.start();
    }

    public void clearUp() {
        this.a.clearUploadItems();
        this.a.clearBioUploadCallBacks();
    }

    public void onDestroy() {
        if (this.a != null && this.a.isEmpty()) {
            this.a.release();
            this.a = null;
        }
    }
}
