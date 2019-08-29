package com.alipay.mobile.antui.adapter;

import android.graphics.Bitmap;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: ImagePickerAdapter */
final class b implements Runnable {
    final /* synthetic */ Bitmap a;
    final /* synthetic */ a b;

    b(a this$1, Bitmap bitmap) {
        this.b = this$1;
        this.a = bitmap;
    }

    public final void run() {
        if (this.a != null) {
            this.b.b.mIvDelete.setVisibility(8);
            this.b.b.mIvDisPlayItemPhoto.setImageBitmap(this.a);
            return;
        }
        AuiLogger.info("ImagePickerAdapter", "pickInfo Adapter show:" + this.b.a + "图片:" + this.a);
        this.b.b.mIvDisPlayItemPhoto.setVisibility(8);
        this.b.b.mIvDelete.setVisibility(0);
    }
}
