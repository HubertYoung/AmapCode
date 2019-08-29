package com.alipay.mobile.framework.service;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.MicroContent;

public abstract class MicroService implements MicroContent {
    private MicroApplicationContext a;

    public abstract void create(Bundle bundle);

    public abstract void destroy(Bundle bundle);

    public abstract boolean isActivated();

    /* access modifiers changed from: protected */
    public abstract void onCreate(Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void onDestroy(Bundle bundle);

    public void attachContext(MicroApplicationContext applicationContext) {
        this.a = applicationContext;
    }

    public MicroApplicationContext getMicroApplicationContext() {
        return this.a;
    }

    public void saveState(Editor editor) {
    }

    public void restoreState(SharedPreferences preferences) {
    }
}
