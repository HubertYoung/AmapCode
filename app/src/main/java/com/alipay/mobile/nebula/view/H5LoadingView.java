package com.alipay.mobile.nebula.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.h5container.api.H5ViewCache;
import com.alipay.mobile.nebula.R;

public class H5LoadingView extends RelativeLayout {
    private TextView textView;

    public H5LoadingView(Context context) {
        this(context, null);
    }

    public H5LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public H5LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View cachedView = H5ViewCache.getCachedViewById(R.layout.h5_loading_view);
        if (cachedView != null) {
            addView(cachedView, new LayoutParams(-1, -1));
        } else {
            LayoutInflater.from(context).inflate(R.layout.h5_loading_view, this);
        }
        this.textView = (TextView) findViewById(R.id.h5_loading_message);
    }

    public void setText(String text) {
        this.textView.setText(text);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
