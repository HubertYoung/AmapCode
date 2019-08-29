package com.autonavi.miniapp.plugin.map.markerstyle;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.miniapp.plugin.map.markerstyle.BaseMarkerStyle.Callback;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils.ImgCallback;
import com.autonavi.minimap.bundle.featureguide.widget.SplashyFragment;

public class MarkerStyle3 extends BaseMarkerStyle {
    /* access modifiers changed from: private */
    public String mBgColor;
    /* access modifiers changed from: private */
    public String mColor;
    /* access modifiers changed from: private */
    public int mGravity;
    private String mIcon;
    /* access modifiers changed from: private */
    public String mText;
    /* access modifiers changed from: private */
    public float mTextSize;

    /* access modifiers changed from: 0000 */
    public boolean measure() {
        return true;
    }

    MarkerStyle3(H5Page h5Page, Context context) {
        super(h5Page, context);
    }

    /* access modifiers changed from: 0000 */
    public BaseMarkerStyle bindData(JSONObject jSONObject) {
        if (((Context) this.mContext.get()) == null) {
            return this;
        }
        this.mIcon = jSONObject.getString(H5Param.MENU_ICON);
        this.mText = jSONObject.getString("text");
        this.mColor = jSONObject.containsKey("color") ? jSONObject.getString("color") : "#33B276";
        this.mBgColor = jSONObject.containsKey(SplashyFragment.INTENT_bgColor) ? jSONObject.getString(SplashyFragment.INTENT_bgColor) : "#FFFFFF";
        String string = jSONObject.getString("gravity");
        if (TextUtils.equals(string, "left")) {
            this.mGravity = 3;
        } else if (TextUtils.equals(string, "right")) {
            this.mGravity = 5;
        } else {
            this.mGravity = 17;
        }
        String string2 = jSONObject.getString("fontType");
        if (TextUtils.equals(string2, "small")) {
            this.mTextSize = 10.0f;
        } else if (TextUtils.equals(string2, "large")) {
            this.mTextSize = 14.0f;
        } else {
            this.mTextSize = 12.0f;
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void getBitmapImpl(final Callback callback) {
        if (TextUtils.isEmpty(this.mIcon)) {
            callback.call(H5MapUtils.getIconWithString2((Context) this.mContext.get(), this.mTextSize, this.mGravity, this.mText, this.mColor, this.mBgColor, null), 0);
        } else {
            H5MapUtils.getImgFromPkg((H5Page) this.mPage.get(), this.mIcon, new ImgCallback() {
                public void onLoadImage(Bitmap bitmap) {
                    callback.call(bitmap != null ? H5MapUtils.getIconWithString2((Context) MarkerStyle3.this.mContext.get(), MarkerStyle3.this.mTextSize, MarkerStyle3.this.mGravity, MarkerStyle3.this.mText, MarkerStyle3.this.mColor, MarkerStyle3.this.mBgColor, bitmap) : null, 0);
                }
            });
        }
    }
}
