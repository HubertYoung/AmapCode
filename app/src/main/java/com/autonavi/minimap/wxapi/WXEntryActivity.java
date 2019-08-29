package com.autonavi.minimap.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.autonavi.amap.app.AMapBaseActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends AMapBaseActivity implements IWXAPIEventHandler {
    public boolean onCreatePanelMenu(int i, Menu menu) {
        return false;
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AMapLog.info("paas.pay", "WXEntryActivity", "WXEntryActivity onCreate ");
        try {
            aoj.d().a(getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    public void onResp(BaseResp baseResp) {
        AMapLog.info("paas.pay", "WXEntryActivity", "WXEntryActivity onResp: ".concat(String.valueOf(baseResp)));
        aoj d = aoj.d();
        if (baseResp instanceof Resp) {
            d.a((Resp) baseResp);
            finish();
            return;
        }
        d.a((Resp) null);
        if (d.a != null) {
            d.a.triggerWxShare(baseResp);
        }
        finish();
    }

    public void finish() {
        KeyboardUtil.hideInputMethod(this);
        super.finish();
    }

    public void onReq(BaseReq baseReq) {
        AMapLog.info("paas.pay", "WXEntryActivity", "WXEntryActivity onReq: ".concat(String.valueOf(baseReq)));
    }

    public void onDestroy() {
        aoj.d().e();
        super.onDestroy();
    }
}
