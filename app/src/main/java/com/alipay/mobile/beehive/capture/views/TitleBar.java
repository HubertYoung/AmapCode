package com.alipay.mobile.beehive.capture.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.beehive.R;

public class TitleBar extends FrameLayout {
    public AUIconView ivBack;
    public ImageView ivBeauty;
    public ImageView ivCamera;
    public ImageView ivFlash;
    public LinearLayout llOptions;
    public TextView tvTitle;

    public TitleBar(Context context) {
        this(context, null, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_title_bar, this, true);
        this.ivBack = (AUIconView) findViewById(R.id.ivBack);
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.llOptions = (LinearLayout) findViewById(R.id.llOptions);
        this.ivFlash = (ImageView) findViewById(R.id.ivFlash);
        this.ivBeauty = (ImageView) findViewById(R.id.ivBeauty);
        this.ivCamera = (ImageView) findViewById(R.id.ivChangeCamera);
    }

    public void setTitleText(String title) {
        this.tvTitle.setText(title);
    }
}
