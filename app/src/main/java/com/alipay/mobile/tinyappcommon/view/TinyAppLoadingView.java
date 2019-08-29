package com.alipay.mobile.tinyappcommon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.dynamicpanel.H5TinyPopMenu.TitleBarTheme;
import com.uc.webview.export.extension.UCCore;

public class TinyAppLoadingView extends DefaultLoadingView {
    private static final String a = TinyAppLoadingView.class.getSimpleName();
    private TitleBarRightButtonView b;
    private int c;
    private float d;
    /* access modifiers changed from: private */
    public a e;

    public interface a {
    }

    public TinyAppLoadingView(Context context) {
        super(context);
    }

    public TinyAppLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TinyAppLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView() {
        super.initView();
        H5Log.d(a, "TinyAppLoadingView...initView");
        this.mBackButton.setVisibility(8);
        this.c = getContext().getResources().getDisplayMetrics().widthPixels;
        this.b = new TitleBarRightButtonView(getContext(), (TitleBarTheme) null);
        this.b.setCloseButtonOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                TinyAppLoadingView.this.cancel();
                if (TinyAppLoadingView.this.e != null) {
                    TinyAppLoadingView.this.e;
                }
            }
        });
        this.d = TinyappUtils.getDensity(getContext());
        addView(this.b);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.b.measure(MeasureSpec.makeMeasureSpec(200, 0), MeasureSpec.makeMeasureSpec(((int) getContext().getResources().getDimension(R.dimen.h5_title_height)) - (((int) (8.0f * this.d)) * 2), UCCore.VERIFY_POLICY_QUICK));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int offsetX = (this.c - this.b.getMeasuredWidth()) - ((int) (6.0f * this.d));
        int offsetY = (int) (8.0f * this.d);
        this.b.layout(offsetX, offsetY, this.b.getMeasuredWidth() + offsetX, this.b.getMeasuredHeight() + offsetY);
    }

    public void setOnCloseBtnClickedListener(a listener) {
        this.e = listener;
    }

    /* access modifiers changed from: protected */
    public boolean isBackButtonVisible() {
        return false;
    }

    /* access modifiers changed from: protected */
    public float getTitleLeftMargin() {
        return (float) H5DimensionUtil.dip2px(getContext(), 16.0f);
    }
}
