package com.alipay.mobile.antui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.MyViewHolder;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton.ImageCallBack;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: ImagePickerAdapter */
final class c implements ImageCallBack {
    final /* synthetic */ MyViewHolder a;
    final /* synthetic */ int b;
    final /* synthetic */ ImagePickerAdapter c;

    c(ImagePickerAdapter this$0, MyViewHolder myViewHolder, int i) {
        this.c = this$0;
        this.a = myViewHolder;
        this.b = i;
    }

    public final void setImage(Bitmap bitmap) {
        AuiLogger.info("ImagePickerAdapter", "pickInfo Adapter0:" + bitmap);
        ((Activity) this.c.mContext).runOnUiThread(new d(this, bitmap));
    }
}
