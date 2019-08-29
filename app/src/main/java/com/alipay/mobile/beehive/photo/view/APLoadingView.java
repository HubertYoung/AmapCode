package com.alipay.mobile.beehive.photo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.beehive.R;

public class APLoadingView extends LinearLayout {
    private TextView mLoadingText;

    public APLoadingView(Context context) {
        super(context);
        initView(context);
    }

    public APLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(0);
        LayoutInflater.from(context).inflate(R.layout.loading_view, this, true);
        this.mLoadingText = (TextView) findViewById(R.id.loading_text);
    }

    public void showLoadingText(boolean show) {
        this.mLoadingText.setVisibility(show ? 0 : 8);
    }

    public void setLoadingText(String text) {
        this.mLoadingText.setText(text);
    }

    public void setLoadingColor(int color) {
        this.mLoadingText.setTextColor(color);
    }

    public void setLoadingSize(float size) {
        this.mLoadingText.setTextSize(size);
    }
}
