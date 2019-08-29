package com.alipay.mobile.nebula.callback;

import android.content.res.Resources;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;

public class H5ShareCallback implements H5CallBack, Runnable {
    private static final String TAG = "H5ShareCallback";
    public static int TIMEOUT = 1000;
    private H5Page h5Page;
    private boolean shareCallBack = false;
    private ShareResult shareResult;

    public interface ShareResult {
        void shareResult(JSONObject jSONObject);

        @Deprecated
        void shareResult(String str, String str2, String str3, String str4);
    }

    public H5ShareCallback(H5Page h5Page2, ShareResult shareResult2) {
        this.h5Page = h5Page2;
        this.shareResult = shareResult2;
        H5Utils.runOnMain(this, (long) TIMEOUT);
    }

    public void onCallBack(JSONObject result) {
        if (!this.shareCallBack) {
            this.shareCallBack = true;
            H5Log.d(TAG, "getShare or getCollect from share.js");
            if (this.h5Page == null) {
                return;
            }
            if (result == null) {
                H5Log.d(TAG, "getShare or getCollect from share.js but get nothing");
                String defaultDesc = this.h5Page.getShareUrl();
                String defaultTitle = TextUtils.isEmpty(this.h5Page.getTitle()) ? getResources().getString(R.string.h5_shareurl) : this.h5Page.getTitle();
                JSONObject defaultResult = new JSONObject();
                defaultResult.put((String) ActionConstant.IMG_URL, (Object) "");
                defaultResult.put((String) "desc", (Object) defaultDesc);
                defaultResult.put((String) "title", (Object) defaultTitle);
                defaultResult.put((String) "link", (Object) null);
                defaultResult.put((String) "fromMeta", (Object) Boolean.valueOf(false));
                this.shareResult.shareResult(defaultResult);
                return;
            }
            this.shareResult.shareResult(result);
        }
    }

    public void run() {
        if (!this.shareCallBack) {
            this.shareCallBack = true;
            H5Log.d(TAG, "getShare or getCollect timeout return default");
            if (this.h5Page != null) {
                String defaultDesc = this.h5Page.getShareUrl();
                String defaultTitle = TextUtils.isEmpty(this.h5Page.getTitle()) ? getResources().getString(R.string.h5_shareurl) : this.h5Page.getTitle();
                JSONObject defaultResult = new JSONObject();
                defaultResult.put((String) ActionConstant.IMG_URL, (Object) "");
                defaultResult.put((String) "desc", (Object) defaultDesc);
                defaultResult.put((String) "title", (Object) defaultTitle);
                defaultResult.put((String) "link", (Object) null);
                defaultResult.put((String) "fromMeta", (Object) Boolean.valueOf(false));
                this.shareResult.shareResult(defaultResult);
            }
        }
    }

    private Resources getResources() {
        return H5WalletWrapper.getNebulaResources();
    }
}
