package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.OnLoadImageListener;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.iconfont.model.IconInfo;

public class AURightIconContainerView extends AURelativeLayout {
    private static final String TAG = AURightIconContainerView.class.getSimpleName();
    private AUIconView leftIconImageView;
    private OnLoadImageListener mOnLoadImageListener;
    private AUIconView rightIconImageView;

    public AURightIconContainerView(Context context) {
        super(context);
        init(context);
    }

    public AURightIconContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AURightIconContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_right_icon_container, this, true);
        this.leftIconImageView = (AUIconView) findViewById(R.id.container_left_icon);
        this.rightIconImageView = (AUIconView) findViewById(R.id.container_right_icon);
    }

    public void setOnLoadImageListener(OnLoadImageListener onLoadImageListener) {
        this.mOnLoadImageListener = onLoadImageListener;
    }

    public void setLeftIconImage(IconInfo iconInfo) {
        setIconView(this.leftIconImageView, iconInfo);
    }

    public void setRightIconImage(IconInfo iconInfo) {
        setIconView(this.rightIconImageView, iconInfo);
    }

    private void setIconView(AUIconView imageView, IconInfo iconInfo) {
        if (iconInfo == null) {
            imageView.setVisibility(4);
        } else if (iconInfo.type == 1) {
            imageView.setVisibility(0);
            if (this.mOnLoadImageListener != null) {
                this.mOnLoadImageListener.loadImage(iconInfo.icon, imageView.getImageView(), null);
            } else {
                Log.e(TAG, "The AURightIconContainerView's iconType is 'IconInfo.TYPE_URL',AURightIconContainerView must be set OnLoadImageListener");
            }
        } else if (iconInfo.type == 3) {
            imageView.setVisibility(0);
            imageView.setImageDrawable(iconInfo.drawable);
        } else if (iconInfo.type == 2) {
            imageView.setVisibility(0);
            imageView.setIconfontUnicode(iconInfo.icon);
        } else {
            imageView.setVisibility(4);
        }
    }
}
