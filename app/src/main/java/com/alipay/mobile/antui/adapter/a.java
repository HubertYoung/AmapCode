package com.alipay.mobile.antui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.MyViewHolder;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton.ImageCallBack;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: ImagePickerAdapter */
final class a implements ImageCallBack {
    final /* synthetic */ int a;
    final /* synthetic */ MyViewHolder b;
    final /* synthetic */ ImagePickerAdapter c;

    a(ImagePickerAdapter this$0, int i, MyViewHolder myViewHolder) {
        this.c = this$0;
        this.a = i;
        this.b = myViewHolder;
    }

    public final void setImage(Bitmap bitmap) {
        AuiLogger.info("ImagePickerAdapter", "pickInfo Adapter第" + this.a + "张图:" + bitmap);
        ((Activity) this.c.mContext).runOnUiThread(new b(this, bitmap));
    }
}
