package com.alipay.mobile.antui.picker;

import android.view.MotionEvent;
import android.view.View;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.MyViewHolder;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.OnItemClickListener;

/* compiled from: AUImagePickerSkeleton */
final class n implements OnItemClickListener {
    final /* synthetic */ AUImagePickerSkeleton a;

    n(AUImagePickerSkeleton this$0) {
        this.a = this$0;
    }

    public final void onItemAddClick(View v, int position) {
        if (this.a.pickerClickListener != null) {
            this.a.pickerClickListener.onPickerClick(position);
        }
    }

    public final void onItemClick(View v, int position) {
        if (this.a.pickerClickListener != null) {
            this.a.pickerClickListener.onImageClick(position);
        }
    }

    public final void onItemLongClick(MyViewHolder vh, View v, int position) {
        v.post(new o(this, vh));
    }

    public final void onTouch(View v, MotionEvent event) {
        this.a.touchX = event.getX();
        this.a.touchY = event.getY();
    }
}
