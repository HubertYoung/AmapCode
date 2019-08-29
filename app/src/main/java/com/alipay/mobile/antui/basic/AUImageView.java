package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.alipay.mobile.antui.screenadpt.AUAttrsUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenUtils;

public class AUImageView extends ImageView implements AUViewInterface {
    private Boolean isAP;

    public AUImageView(Context context) {
        super(context);
    }

    public AUImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (AUScreenUtils.checkApFlag(context, attrs, this)) {
            AUAttrsUtils.adptApPadding(this, context);
        }
    }

    public AUImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (AUScreenUtils.checkApFlag(context, attrs, this)) {
            AUAttrsUtils.adptApPadding(this, context);
        }
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(AUViewEventHelper.wrapClickListener(l));
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }
}
