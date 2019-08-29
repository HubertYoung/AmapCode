package com.alipay.android.phone.inside.wallet.plugin.service;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class InstallGuideService extends AbstractInsideService<Bundle, Bundle> {
    static final String KEY_INATALL_TIPS = "installTips";

    public Bundle startForResult(Bundle bundle) throws Exception {
        runOnUiThread(getToastRunnable(bundle.getString(KEY_INATALL_TIPS)));
        return new Bundle();
    }

    private void runOnUiThread(final Runnable runnable) {
        new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                if (message.what == 4097) {
                    try {
                        LoggerFactory.f().e("inside", "InstallGuideService::runOnUiThread > reach 0x1001");
                        runnable.run();
                    } catch (Throwable th) {
                        LoggerFactory.f().c((String) "inside", th);
                    }
                }
                super.handleMessage(message);
            }
        }.sendEmptyMessage(4097);
    }

    private Runnable getToastRunnable(final String str) {
        final Context context = getContext();
        return new Runnable() {
            public void run() {
                Toast.makeText(context, str, 0).show();
            }
        };
    }
}
