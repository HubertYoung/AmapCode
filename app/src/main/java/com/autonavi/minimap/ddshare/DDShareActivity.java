package com.autonavi.minimap.ddshare;

import android.os.Bundle;
import android.view.Menu;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.android.dingtalk.share.ddsharemodule.IDDAPIEventHandler;
import com.android.dingtalk.share.ddsharemodule.IDDShareApi;
import com.android.dingtalk.share.ddsharemodule.message.BaseReq;
import com.android.dingtalk.share.ddsharemodule.message.BaseResp;
import com.autonavi.amap.app.AMapBaseActivity;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

public class DDShareActivity extends AMapBaseActivity implements IDDAPIEventHandler {
    private IDDShareApi a;

    public boolean onCreatePanelMenu(int i, Menu menu) {
        return false;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        dcf.a("DDShareActivity. onCreate.", new Object[0]);
        try {
            this.a = dcf.b();
            if (this.a != null) {
                this.a.handleIntent(getIntent(), this);
            } else {
                a();
            }
        } catch (Exception e) {
            e.printStackTrace();
            dcf.a("DDShareActivity. onCreate. error: %s", e.toString(), e);
            a();
        }
    }

    public void onReq(BaseReq baseReq) {
        dcf.a("DDShareActivity. onReq.", new Object[0]);
    }

    public void onResp(BaseResp baseResp) {
        int i = baseResp.mErrCode;
        dcf.a("DDShareActivity. onResp. errCode: %s", Integer.valueOf(i));
        if (i != -2) {
            if (i != 0) {
                ddi.a().a(11, -1);
            } else {
                ddi.a().a(11, 0);
                ToastHelper.showLongToast(AMapPageUtil.getAppContext().getString(R.string.pubok));
            }
        }
        a();
    }

    private void a() {
        ddi.a().b();
        finish();
    }

    public void finish() {
        dcf.a("DDShareActivity. finish().", new Object[0]);
        KeyboardUtil.hideInputMethod(this);
        super.finish();
    }
}
