package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.drawable;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class GifDrawableOwner extends GifDrawableImpl {
    public GifDrawableOwner(Context context, String path, int width, int height, Bundle options) {
        super(context, path, width, height, options, false);
        this.startIgnoreVisible = true;
    }

    public boolean setVisible(boolean visible, boolean restart) {
        return super.setVisibleInnner(visible, restart);
    }

    public void bindView(View view) {
        super.bindViewInner(view);
    }
}
