package com.autonavi.widget.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import com.amap.bundle.commonui.loading.LoadingView;
import com.autonavi.map.fragmentcontainer.IViewLayer;

public class LoadingViewBL extends LoadingView implements IViewLayer {
    private boolean mCancelable = false;

    public View getView() {
        return this;
    }

    public void showBackground(boolean z) {
    }

    public LoadingViewBL(Context context, int i) {
        super(context, i);
    }

    public LoadingViewBL(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public LoadingViewBL(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public boolean onBackPressed() {
        return this.mCancelable;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void setCancelable(boolean z) {
        this.mCancelable = z;
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }
}
