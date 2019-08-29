package com.alipay.zoloz.toyger.workspace;

import android.os.Handler;
import android.os.Message;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;
import java.util.HashMap;

/* compiled from: ToygerNavigationFragment */
final class c extends Handler {
    final /* synthetic */ ToygerNavigationFragment a;

    c(ToygerNavigationFragment toygerNavigationFragment) {
        this.a = toygerNavigationFragment;
    }

    public final void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case 0:
                this.a.mToygerRecordService.write(ToygerRecordService.CLICK_START_CAPTURE);
                this.a.forward(new ToygerCaptureFragment());
                return;
            case 1:
                this.a.mWebView.loadUrl("file:///android_asset/html/nav/facewelcome.html");
                return;
            case 2:
                this.a.mWebView.loadUrl("file:///android_asset/html/nav/facewelcome.html");
                return;
            case 3:
                this.a.mToygerRecordService.write(ToygerRecordService.EXIT_GUIDE_PAGE);
                this.a.mToygerCallback.sendResponse(202);
                this.a.mToygerCallback.finishActivity(false);
                return;
            case 4:
                String str = (String) message.obj;
                if (str != null) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("h5_guide_log", str);
                    this.a.mToygerRecordService.write(ToygerRecordService.DEV_TECH_SEED, hashMap);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
