package com.alipay.mobile.tinyappcommon.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.h5container.api.H5ViewCache;
import com.alipay.mobile.nebula.provider.H5LoadingViewProvider;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyapp.R;

public class TinyAppActivityLoadingView implements H5LoadingViewProvider {
    public static final String TAG = TinyAppActivityLoadingView.class.getSimpleName();
    /* access modifiers changed from: private */
    public ViewGroup contentView;
    /* access modifiers changed from: private */
    public TitleBarRightButtonView mRightButtonViewContainer;
    /* access modifiers changed from: private */
    public ProgressBar progressBar;
    /* access modifiers changed from: private */
    public String title;
    /* access modifiers changed from: private */
    public TextView titleView;

    public View getContentView(final Activity activity, Bundle startParam) {
        if (this.contentView != null) {
            return this.contentView;
        }
        if (!useUpDownAnimator(startParam)) {
            H5Log.d(TAG, "getContentView...do not use custom view");
            return null;
        }
        this.contentView = new FrameLayout(activity);
        this.contentView.setLayoutParams(new LayoutParams(-1, -1));
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                if (activity != null) {
                    H5Log.d(TinyAppActivityLoadingView.TAG, "getContentView: activity.getResouces()=" + activity.getResources());
                }
                View realContentView = H5ViewCache.getCachedViewById(R.layout.tiny_app_activity_loading_view);
                if (realContentView == null) {
                    realContentView = LayoutInflater.from(activity).inflate(R.layout.tiny_app_activity_loading_view, null);
                }
                TinyAppActivityLoadingView.this.contentView.addView(realContentView, new LayoutParams(-1, -1));
                TinyAppActivityLoadingView.this.configBackButton();
                TinyAppActivityLoadingView.this.progressBar = (ProgressBar) TinyAppActivityLoadingView.this.contentView.findViewById(R.id.h5_nav_loading_loading);
                TinyAppActivityLoadingView.this.progressBar.postDelayed(new Runnable() {
                    public final void run() {
                        TinyAppActivityLoadingView.this.progressBar.setVisibility(0);
                    }
                }, 300);
                TinyAppActivityLoadingView.this.mRightButtonViewContainer = (TitleBarRightButtonView) TinyAppActivityLoadingView.this.contentView.findViewById(R.id.right_btn_container);
                TinyAppActivityLoadingView.this.mRightButtonViewContainer.setCloseButtonOnClickListener(new OnClickListener() {
                    public final void onClick(View v) {
                        activity.finish();
                    }
                });
                TinyAppActivityLoadingView.this.titleView = (TextView) TinyAppActivityLoadingView.this.contentView.findViewById(R.id.h5_ll_lv_nav_title);
                if (!TextUtils.isEmpty(TinyAppActivityLoadingView.this.title)) {
                    TinyAppActivityLoadingView.this.setTitle(TinyAppActivityLoadingView.this.title);
                }
            }
        });
        return this.contentView;
    }

    private boolean useUpDownAnimator(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return bundle.containsKey("needAnimInTiny");
    }

    /* access modifiers changed from: private */
    public void configBackButton() {
        if (this.contentView == null) {
        }
    }

    public void stopLoading(Activity activity) {
        if (this.progressBar != null) {
            try {
                this.progressBar.setIndeterminateDrawable(null);
            } catch (Exception e) {
                H5Log.e(TAG, (Throwable) e);
            }
        }
    }

    public void setTitle(String title2) {
        this.title = title2;
        if (this.contentView != null && this.titleView != null) {
            this.titleView.setText(title2);
            ((RelativeLayout.LayoutParams) this.titleView.getLayoutParams()).setMargins(H5DimensionUtil.dip2px(this.contentView.getContext(), 16.0f), 0, 0, 0);
        }
    }

    public void setIcon(String url) {
    }
}
