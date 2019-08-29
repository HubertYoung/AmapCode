package com.autonavi.map.search.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.autonavi.minimap.R;

public class IdqLoadingView extends RelativeLayout {
    private View mLoadingAnimView;
    private LayoutParams mLoadingAnimViewParams;

    public IdqLoadingView(Context context) {
        this(context, null);
    }

    public IdqLoadingView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public IdqLoadingView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        setClickable(true);
        this.mLoadingAnimView = LayoutInflater.from(context).inflate(R.layout.idq_plus_loading_layout, this).findViewById(R.id.idq_plus_loading_view);
        this.mLoadingAnimViewParams = this.mLoadingAnimView.getLayoutParams();
    }

    @UiThread
    public void setLoadingAnimViewH(int i) {
        this.mLoadingAnimViewParams.height = (int) (((double) i) * 0.6d);
        this.mLoadingAnimView.setLayoutParams(this.mLoadingAnimViewParams);
    }

    public int getLoadingAnimViewH() {
        return this.mLoadingAnimViewParams.height;
    }
}
