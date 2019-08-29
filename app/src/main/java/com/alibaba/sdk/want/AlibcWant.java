package com.alibaba.sdk.want;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.alibaba.sdk.trade.component.cart.AlibcTkParams;
import com.alibaba.sdk.trade.container.utils.AlibcComponentLog;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;
import com.alibaba.sdk.want.widget.WantBaseData;
import com.alibaba.sdk.want.widget.WantWidgetFactory;
import com.alipay.mobile.common.share.widget.ResUtils;
import java.util.HashMap;

public class AlibcWant {
    private static final String TAG = "AlibcWant";
    private Activity mContext;
    private WantWidgetFactory mFactory;
    private ViewGroup mRootView;

    public View getView(Activity activity) {
        if (activity == null) {
            AlibcComponentLog.e(TAG, "context is null");
            return null;
        }
        if (this.mRootView == null) {
            this.mContext = activity;
            this.mRootView = new LinearLayout(this.mContext);
            LayoutInflater.from(this.mContext).inflate(activity.getResources().getIdentifier("alibc_want_addcart_layout", ResUtils.LAYOUT, activity.getPackageName()), this.mRootView);
            this.mRootView.setVisibility(8);
        }
        return this.mRootView;
    }

    public void updateData(WantBaseData wantBaseData, HashMap<String, String> hashMap, AlibcTkParams alibcTkParams) {
        if (checkParams(wantBaseData)) {
            if (this.mFactory == null) {
                this.mFactory = new WantWidgetFactory(this.mContext, this.mRootView);
            }
            this.mFactory.updateData(wantBaseData, hashMap, alibcTkParams);
        }
    }

    public void removeAllData() {
        if (this.mFactory != null) {
            this.mFactory.removeAllData();
        }
    }

    public void destroy() {
        AlibcComponentLog.d(TAG, "destroy");
        if (this.mFactory != null) {
            this.mFactory.destroy();
        }
        this.mFactory = null;
        this.mRootView = null;
        this.mContext = null;
    }

    private boolean checkParams(WantBaseData wantBaseData) {
        String str;
        boolean z = false;
        if (this.mContext == null) {
            str = "context为空,没有getview?";
        } else if (this.mRootView == null) {
            str = "rootview为空,没有getview?";
        } else if (wantBaseData == null || TextUtils.isEmpty(wantBaseData.getKey()) || TextUtils.isEmpty(wantBaseData.getType())) {
            str = "base data为空";
        } else {
            z = true;
            str = null;
        }
        if (!z) {
            AlibcComponentLog.e(TAG, "params is null");
            AlibcComponentTrack.sendUseabilityFailure(AlibcComponentTrack.MODEL_NAME_WANT, "addCart", AlibcComponentTrack.ERRNO_WANT_CART_PARM, str);
        }
        return z;
    }
}
