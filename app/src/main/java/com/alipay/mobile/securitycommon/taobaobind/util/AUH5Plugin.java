package com.alipay.mobile.securitycommon.taobaobind.util;

import android.content.Intent;
import android.net.Uri;
import com.alipay.mobile.account.adapter.LogAdapter;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;

public class AUH5Plugin implements H5Plugin {
    private boolean a = false;
    private H5Page b;
    private Intent c;

    /* access modifiers changed from: protected */
    public void onCancel() {
    }

    /* access modifiers changed from: protected */
    public void onFail() {
    }

    public void onInitialize(H5CoreNode h5CoreNode) {
    }

    /* access modifiers changed from: protected */
    public void onSuccess() {
    }

    public AUH5Plugin() {
    }

    public AUH5Plugin(H5Page h5Page) {
        this.b = h5Page;
    }

    public void setPage(H5Page h5Page) {
        this.b = h5Page;
    }

    public H5Page getPage() {
        return this.b;
    }

    public void setBackable(boolean z) {
        this.a = z;
    }

    public boolean isBackable() {
        return this.a;
    }

    public Intent getIntentExtra() {
        if (this.c == null) {
            this.c = new Intent();
        }
        return this.c;
    }

    public boolean handleEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        LogAdapter.a((String) "AliuserH5Plugin", (String) "handleEvent");
        return false;
    }

    public boolean interceptEvent(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        StringBuilder sb = new StringBuilder("interceptEvent:");
        sb.append(h5Event.getAction());
        LogAdapter.a((String) "AliuserH5Plugin", sb.toString());
        if (CommonEvents.H5_PAGE_SHOULD_LOAD_URL.equals(h5Event.getAction())) {
            String string = h5Event.getParam().getString("url");
            LogAdapter.a((String) "AliuserH5Plugin", "invoke url:".concat(String.valueOf(string)));
            if (checkWebviewBridge(string)) {
                LogAdapter.a((String) "AliuserH5Plugin", "onOverrideUrlLoading:".concat(String.valueOf(string)));
                getIntentExtra().putExtras(TaobaoBindUtil.serialBundle(Uri.parse(string).getQuery()));
                return onOverrideUrlLoading(string);
            }
            LogAdapter.a((String) "AliuserH5Plugin", (String) "url unprocess");
        } else if (CommonEvents.H5_PAGE_BACK.equals(h5Event.getAction())) {
            LogAdapter.a((String) "AliuserH5Plugin", String.format("user back, mIsBackable:%s", new Object[]{Boolean.valueOf(this.a)}));
            if (!this.a) {
                this.b.exitPage();
                onCancel();
            }
        } else if (CommonEvents.H5_TOOLBAR_CLOSE.equals(h5Event.getAction())) {
            LogAdapter.a((String) "AliuserH5Plugin", (String) "user cancel");
            this.b.exitPage();
            onCancel();
        }
        return false;
    }

    public void onPrepare(H5EventFilter h5EventFilter) {
        h5EventFilter.addAction(CommonEvents.H5_PAGE_SHOULD_LOAD_URL);
        h5EventFilter.addAction(CommonEvents.H5_TOOLBAR_CLOSE);
        if (!this.a) {
            h5EventFilter.addAction(CommonEvents.H5_PAGE_BACK);
        }
    }

    public void onRelease() {
        LogAdapter.a((String) "AliuserH5Plugin", (String) "onRelease");
    }

    public boolean checkWebviewBridge(String str) {
        Uri parse = Uri.parse(str);
        StringBuilder sb = new StringBuilder();
        sb.append(parse.getAuthority());
        sb.append(parse.getPath());
        return "https://www.alipay.com/webviewbridge".contains(sb.toString());
    }

    /* access modifiers changed from: protected */
    public boolean onOverrideUrlLoading(String str) {
        if ("quit".equals(getIntentExtra().getStringExtra("action"))) {
            onFail();
            return true;
        } else if (!"true".equals(getIntentExtra().getStringExtra("isSuc"))) {
            return false;
        } else {
            getPage().exitPage();
            onSuccess();
            return true;
        }
    }
}
