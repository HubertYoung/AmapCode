package com.autonavi.minimap.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    public void onReq(BaseReq baseReq) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AMapLog.info("paas.pay", "MicroMsg.SDKSample.WXPayEntryActivity", "WXPayEntryActivity onCreate ");
        setContentView(new FrameLayout(this));
        aoj.d().a(getIntent(), this);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        aoj.d().a(intent, this);
    }

    public void onResp(BaseResp baseResp) {
        AMapLog.info("paas.pay", "MicroMsg.SDKSample.WXPayEntryActivity", "WXPayEntryActivity onResp, resp = ".concat(String.valueOf(baseResp)));
        aoj d = aoj.d();
        if (d.a != null) {
            d.a.triggerWxShare(baseResp);
        }
        finish();
    }

    public void finish() {
        KeyboardUtil.hideInputMethod(this);
        super.finish();
    }
}
