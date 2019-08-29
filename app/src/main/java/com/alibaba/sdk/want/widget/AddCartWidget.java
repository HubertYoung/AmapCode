package com.alibaba.sdk.want.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.alibaba.sdk.trade.component.cart.AlibcCartParams;
import com.alibaba.sdk.trade.component.cart.AlibcTkParams;
import com.alibaba.sdk.trade.container.AlibcBaseComponent;
import com.alibaba.sdk.trade.container.AlibcComponentCallback;
import com.alibaba.sdk.trade.container.AlibcContainer;
import com.alibaba.sdk.trade.container.utils.AlibcComponentLog;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;
import com.alibaba.sdk.want.AlibcWantConstant;
import com.alibaba.sdk.want.AlibcWantEventDispatch;
import com.alibaba.sdk.want.ui.AlibcAddCartWidgetUI;
import com.alibaba.sdk.want.ui.AlibcIWidgetUI;
import java.util.HashMap;

public class AddCartWidget implements IWantWidget {
    private final String TAG = "AddCartWidget";
    private AlibcCartParams mAddData;
    private AlibcCartParams mShowData;
    private AlibcBaseComponent mWantComponent;
    private AlibcIWidgetUI mWidgetUI;

    public final String getType() {
        return WantWidgetFactory.ADDWANTWIDGET_TYPE;
    }

    public AddCartWidget(Activity activity, ViewGroup viewGroup) {
        this.mWidgetUI = new AlibcAddCartWidgetUI(activity, viewGroup);
        this.mWidgetUI.setWidget(this);
    }

    public void updateData(WantBaseData wantBaseData, HashMap<String, String> hashMap, AlibcTkParams alibcTkParams) {
        if (this.mWantComponent == null) {
            this.mWantComponent = AlibcContainer.getComponentByType(1);
        }
        if (this.mWantComponent == null) {
            componentErr();
            return;
        }
        AlibcComponentTrack.sendUseabilitySuccess(AlibcComponentTrack.MODEL_NAME_WANT, "addCart");
        if (updateShowData(wantBaseData, hashMap, alibcTkParams)) {
            updateUI(this.mShowData);
        }
    }

    public void removeData() {
        resetData();
        if (this.mWidgetUI != null) {
            this.mWidgetUI.removeData();
        }
    }

    public void removeDataEnd() {
        if (this.mShowData != null) {
            updateUI(this.mShowData);
        }
    }

    public boolean addData() {
        updateAddData();
        addDataWithComponent();
        return true;
    }

    public void destroy() {
        AlibcComponentLog.d("AddCartWidget", "destroy");
        if (this.mWidgetUI != null) {
            this.mWidgetUI.destroy();
        }
        this.mWidgetUI = null;
    }

    private synchronized boolean updateShowData(WantBaseData wantBaseData, HashMap<String, String> hashMap, AlibcTkParams alibcTkParams) {
        if (this.mShowData == null || !TextUtils.equals(this.mShowData.mItemID, wantBaseData.getKey()) || !TextUtils.equals(this.mShowData.mTips, wantBaseData.getValue()) || this.mWidgetUI == null || this.mWidgetUI.getWidgetUIVisible() != 0) {
            AlibcComponentTrack.preprocessYbhpss(hashMap, AlibcComponentTrack.LABEL_VALUE_FOR_YBHPSS_CART_WANT, true);
            this.mShowData = new AlibcCartParams();
            this.mShowData.mItemID = wantBaseData.getKey();
            this.mShowData.mTips = wantBaseData.getValue();
            this.mShowData.mTaokeParams = alibcTkParams;
            this.mShowData.mYbhpssParams = hashMap;
            return true;
        }
        AlibcComponentLog.e("AddCartWidget", "same data, ignore");
        return false;
    }

    private synchronized void updateAddData() {
        this.mAddData = this.mShowData;
        this.mShowData = null;
    }

    private synchronized void restorePendingData() {
        if (this.mShowData == null) {
            this.mShowData = this.mAddData;
        }
    }

    private synchronized void resetData() {
        this.mShowData = null;
        this.mAddData = null;
    }

    private void componentErr() {
        String str;
        String str2;
        if (!AlibcContainer.checkLicense("BC_WantAddCart")) {
            str2 = AlibcComponentTrack.ERRNO_WANT_CART_DISABLE;
            str = "组件未授权";
        } else {
            str2 = AlibcComponentTrack.ERRNO_WANT_CART_NO_PACKEGE;
            str = "组件找不到";
        }
        AlibcWantEventDispatch.onEvent(AlibcWantConstant.WANT_ADDCART_FAIL, str);
        AlibcComponentTrack.sendUseabilityFailure(AlibcComponentTrack.MODEL_NAME_WANT, "addCart", str2, str);
    }

    private void addDataWithComponent() {
        if (this.mAddData == null) {
            AlibcComponentLog.e("AddCartWidget", "add data is null");
        } else {
            this.mWantComponent.execute(this.mAddData, new AlibcComponentCallback() {
                public void onSuccess(Object obj) {
                    AlibcComponentLog.d("AddCartWidget", "add cart with want component success");
                    AddCartWidget.this.updateResult(AlibcWantConstant.WANT_ADDCART_SUCCESS, null);
                }

                public void onError(String str, String str2) {
                    AlibcComponentLog.d("AddCartWidget", "add cart with want component fail");
                    AddCartWidget.this.updateFailResult(str, str2);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void updateFailResult(String str, String str2) {
        String str3;
        String str4 = null;
        if (TextUtils.equals(str, AlibcComponentTrack.MTOP_ERRNO_LOGIN_CANCEL)) {
            str3 = AlibcComponentTrack.ERRNO_COMPONENT_CART_CANCEL_LOGIN;
        } else if (TextUtils.equals(str, AlibcComponentTrack.MTOP_ERRNO_AUTH_CANCEL)) {
            str3 = AlibcComponentTrack.ERRNO_COMPONENT_CART_CANCEL_AUTH;
        } else {
            if (TextUtils.equals(str, "ADD_CART_FAILURE")) {
                str4 = str2;
            }
            str3 = AlibcComponentTrack.ERRNO_COMPONENT_CART_MTOP_FAIL;
        }
        updateResult(str3, str4);
    }

    /* access modifiers changed from: private */
    public void updateResult(String str, String str2) {
        this.mWidgetUI.updateResult(str, str2);
        if (!TextUtils.equals(str, AlibcWantConstant.WANT_ADDCART_SUCCESS)) {
            restorePendingData();
        }
        updateUI(this.mShowData);
    }

    private void updateUI(AlibcCartParams alibcCartParams) {
        this.mWidgetUI.updateUI(alibcCartParams);
    }
}
