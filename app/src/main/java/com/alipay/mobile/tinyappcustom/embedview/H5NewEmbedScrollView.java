package com.alipay.mobile.tinyappcustom.embedview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5NewEmbedScrollView extends H5NewEmbedFrameLayout {
    private ViewGroup a;

    public static class DelegateScrollView extends ScrollView {
        public DelegateScrollView(Context context) {
            super(context);
        }

        public DelegateScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public DelegateScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public H5EmbedBaseFrameLayout getContainer() {
            return (H5EmbedBaseFrameLayout) getChildAt(0);
        }

        public void addView(View child) {
            if (getChildCount() > 0) {
                getContainer().addView(child);
            } else {
                super.addView(child);
            }
        }

        public void addView(View child, int index) {
            if (getChildCount() > 0) {
                getContainer().addView(child, index);
            } else {
                super.addView(child, index);
            }
        }

        public void addView(View child, LayoutParams params) {
            if (getChildCount() > 0) {
                getContainer().addView(child, params);
            } else {
                super.addView(child, params);
            }
        }

        public void addView(View child, int index, LayoutParams params) {
            if (getChildCount() > 0) {
                getContainer().addView(child, index, params);
            } else {
                super.addView(child, index, params);
            }
        }

        public void updateViewLayout(View view, LayoutParams params) {
            if (getChildCount() > 0) {
                getContainer().updateViewLayout(view, params);
            } else {
                super.updateViewLayout(view, params);
            }
        }

        public void setBackgroundColor(int color) {
            if (getChildCount() > 0) {
                getContainer().setBackgroundColor(color);
            } else {
                super.setBackgroundColor(color);
            }
        }

        public int indexOfChild(View child) {
            if (getChildCount() > 0) {
                return getContainer().indexOfChild(child);
            }
            return super.indexOfChild(child);
        }

        public void setBackgroundDrawable(Drawable background) {
            if (getChildCount() > 0) {
                getContainer().setBackgroundDrawable(background);
            } else {
                super.setBackgroundDrawable(background);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final View a() {
        if (this.a == null) {
            this.a = new DelegateScrollView((Context) this.mContext.get());
            FrameLayout frameLayout = new H5EmbedBaseFrameLayout((Context) this.mContext.get());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            this.a.addView(frameLayout);
        }
        return this.a;
    }

    public void onReceivedRender(JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        super.onReceivedRender(jsonObject, h5BridgeContext);
        this.a.setScrollY(H5DimensionUtil.dip2px((Context) this.mContext.get(), H5Utils.parseFloat(jsonObject.getString("scrollTop"))));
    }
}
