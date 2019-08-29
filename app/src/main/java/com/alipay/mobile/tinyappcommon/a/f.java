package com.alipay.mobile.tinyappcommon.a;

import android.os.Bundle;
import android.os.Messenger;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.tinyappcommon.api.TinyAppLiteProcessService;

/* compiled from: TinyAppLiteProcessServiceImpl */
public final class f implements TinyAppLiteProcessService {
    public static final f a = new f();

    private f() {
    }

    public final boolean isLiteProcess() {
        return LiteProcessApi.isLiteProcess();
    }

    public final void sendDataToMainProcess(int what, Bundle data) {
        if (LiteProcessApi.isLiteProcess()) {
            c.a(what, data);
        }
    }

    public final void replyDataToLiteProcess(Messenger messenger, int what, Bundle data) {
        if (!LiteProcessApi.isLiteProcess()) {
            c.a(messenger, what, data);
        }
    }
}
