package com.alipay.mobile.antui.adapter;

import android.graphics.Bitmap;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: ImagePickerAdapter */
final class d implements Runnable {
    final /* synthetic */ Bitmap a;
    final /* synthetic */ c b;

    d(c this$1, Bitmap bitmap) {
        this.b = this$1;
        this.a = bitmap;
    }

    public final void run() {
        if (this.a != null) {
            this.b.a.mIvDelete.setVisibility(8);
            this.b.a.mIvDisPlayItemPhoto.setImageBitmap(this.a);
            return;
        }
        AuiLogger.info("ImagePickerAdapter", "pickInfo Adapter show:" + this.b.b + "图片:" + this.a);
        this.b.a.mIvDisPlayItemPhoto.setVisibility(8);
        this.b.a.mIvDelete.setVisibility(0);
    }
}
