package com.alibaba.baichuan.android.jsbridge.plugin;

import android.content.Context;
import android.content.Intent;
import com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext;
import com.alibaba.baichuan.android.trade.c.b.d;

public abstract class AlibcApiPlugin {
    protected Context b;
    protected d c;
    protected boolean d = true;
    public Object paramObj;

    public abstract boolean execute(String str, String str2, AlibcJsCallbackContext alibcJsCallbackContext);

    public void initialize(Context context, d dVar) {
        initialize(context, dVar, null);
    }

    public void initialize(Context context, d dVar, Object obj) {
        this.b = context;
        this.c = dVar;
        this.paramObj = obj;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onDestroy() {
        this.d = false;
    }

    public void onPause() {
        this.d = false;
    }

    public void onResume() {
        this.d = true;
    }

    public void onScrollChanged(int i, int i2, int i3, int i4) {
    }
}
