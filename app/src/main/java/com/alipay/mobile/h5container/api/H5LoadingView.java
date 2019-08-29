package com.alipay.mobile.h5container.api;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.process.H5EventHandler;
import com.alipay.mobile.nebula.provider.H5LoadingViewProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5StateListUtils;
import com.alipay.mobile.nebula.util.H5TypefaceCache;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.File;

public class H5LoadingView implements H5LoadingViewProvider {
    public static final String TAG = "H5LoadingView";
    /* access modifiers changed from: private */
    public TextView backLoadingView;
    private View contentView;
    /* access modifiers changed from: private */
    public ProgressBar progressBar;

    public View getContentView(final Activity activity, Bundle startParam) {
        if (this.contentView != null) {
            return this.contentView;
        }
        if (activity != null) {
            H5Log.d(TAG, "getContentView: activity.getResouces()=" + activity.getResources());
        }
        this.contentView = H5ViewCache.getCachedViewById(R.layout.h5_loading_fragment);
        if (this.contentView == null) {
            this.contentView = LayoutInflater.from(activity).inflate(R.layout.h5_loading_fragment, null);
        }
        this.backLoadingView = (TextView) this.contentView.findViewById(R.id.h5_lv_nav_back_loading);
        H5TypefaceCache.getInstance();
        this.backLoadingView.setTypeface(H5TypefaceCache.getTypeface(activity, "h5iconfont", "h5iconfont" + File.separator + "h5titlebar.ttf"));
        this.backLoadingView.setTextColor(H5StateListUtils.getStateColor(-15692055));
        this.progressBar = (ProgressBar) this.contentView.findViewById(R.id.h5_nav_loading_loading);
        this.backLoadingView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!activity.isFinishing()) {
                    if (H5Utils.isInTinyProcess()) {
                        H5EventHandler h5EventHandlerService = (H5EventHandler) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                        if (h5EventHandlerService != null) {
                            h5EventHandlerService.moveTaskToBackAndStop(activity, false);
                        }
                    } else {
                        activity.finish();
                    }
                    H5Log.d(H5LoadingView.TAG, "isFinishing " + activity.isFinishing());
                }
            }
        });
        this.progressBar.postDelayed(new Runnable() {
            public void run() {
                H5LoadingView.this.progressBar.setVisibility(0);
            }
        }, 300);
        return this.contentView;
    }

    public void stopLoading(final Activity activity) {
        if (this.progressBar != null) {
            try {
                this.progressBar.setIndeterminateDrawable(null);
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
        if (this.backLoadingView != null) {
            this.backLoadingView.postDelayed(new Runnable() {
                public void run() {
                    try {
                        if (!activity.isFinishing() && H5LoadingView.this.backLoadingView != null) {
                            H5LoadingView.this.backLoadingView.setVisibility(8);
                        }
                    } catch (Exception e) {
                        H5Log.e((String) H5LoadingView.TAG, (Throwable) e);
                    }
                }
            }, 5000);
        }
    }

    public void setTitle(String title) {
        if (this.contentView != null) {
            TextView titleView = (TextView) this.contentView.findViewById(R.id.h5_ll_lv_nav_title);
            if (titleView != null) {
                titleView.setText(title);
            }
        }
    }

    public void setIcon(String url) {
    }
}
