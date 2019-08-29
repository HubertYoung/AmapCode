package com.alipay.mobile.nebula.newembedview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import java.lang.ref.WeakReference;

public abstract class H5NewBaseEmbedView implements IH5NewEmbedView {
    private static final String TAG = "H5NewBaseEmbedView";
    public WeakReference<Context> mContext;
    public WeakReference<H5Page> mH5Page;

    public void onCreate(Context context, H5Page h5Page) {
        this.mContext = new WeakReference<>(context);
        this.mH5Page = new WeakReference<>(h5Page);
    }

    public void execJavaScript(String code, IH5EmbedViewJSCallback jsCallback) {
        H5Page h5Page = (H5Page) this.mH5Page.get();
        if (h5Page != null) {
            h5Page.execJavaScript4EmbedView("javascript:" + code, jsCallback);
        }
    }

    public void getComponentResourceDataWithUrl(String url, ResponseListen listener, H5Page h5Page) {
        H5Log.d(TAG, "getComponentResourceDataWithUrl url " + url);
        if (h5Page != null) {
            String abUrl = getAbsoluteUrl(url, h5Page.getParams());
            H5Log.d(TAG, "getComponentResourceDataWithUrl abUrl " + abUrl);
            H5Session h5Session = h5Page.getSession();
            if (h5Session != null) {
                H5ContentProvider h5ContentProvider = h5Session.getWebProvider();
                if (h5ContentProvider != null) {
                    h5ContentProvider.getContentOnUi(abUrl, listener);
                    return;
                }
                H5Log.d(TAG, "getComponentResourceDataWithUrl h5ContentProvider == null");
                listener.onGetResponse(null);
                return;
            }
            H5Log.d(TAG, "getComponentResourceDataWithUrl h5Session == null");
            listener.onGetResponse(null);
            return;
        }
        H5Log.d(TAG, "getComponentResourceDataWithUrl h5Page == null");
        listener.onGetResponse(null);
    }

    private String getAbsoluteUrl(String oriUrl, Bundle startParams) {
        String entryUrl = H5Utils.getString(startParams, (String) "url");
        if (!TextUtils.isEmpty(entryUrl)) {
            return H5Utils.getAbsoluteUrlV2(entryUrl, oriUrl, null);
        }
        return null;
    }
}
