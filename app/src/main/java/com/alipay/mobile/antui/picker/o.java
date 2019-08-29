package com.alipay.mobile.antui.picker;

import android.content.ClipData;
import android.support.v4.view.ViewCompat;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.MyViewHolder;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: AUImagePickerSkeleton */
final class o implements Runnable {
    final /* synthetic */ MyViewHolder a;
    final /* synthetic */ n b;

    o(n this$1, MyViewHolder myViewHolder) {
        this.b = this$1;
        this.a = myViewHolder;
    }

    public final void run() {
        if (this.b.a.allowDrag) {
            this.b.a.gridSize = (float) (this.a.itemView.getRight() - this.a.itemView.getLeft());
            ViewCompat.setAlpha(this.a.itemView, 0.8f);
            ViewCompat.setScaleX(this.a.itemView, 1.1f);
            ViewCompat.setScaleY(this.a.itemView, 1.1f);
            AUImagePickerSkeleton.partionX = this.b.a.touchX / this.b.a.gridSize;
            AUImagePickerSkeleton.partionY = this.b.a.touchY / this.b.a.gridSize;
            AuiLogger.info(AUImagePickerSkeleton.TAG, "touchX:" + this.b.a.touchX + "  touchY:" + this.b.a.touchY + "   partionX  " + AUImagePickerSkeleton.partionX + "  partionY " + AUImagePickerSkeleton.partionY + "   " + this.b.a.gridSize);
            try {
                this.a.itemView.startDrag(ClipData.newPlainText("", ""), new EnlargeDragShadowBuilder(this.a.itemView), this.a, 0);
                this.a.itemView.setVisibility(4);
            } catch (Exception e) {
                ViewCompat.setAlpha(this.a.itemView, 1.0f);
                ViewCompat.setScaleX(this.a.itemView, 1.0f);
                ViewCompat.setScaleY(this.a.itemView, 1.0f);
                AuiLogger.error("ItemDragCallback", String.valueOf(e));
            }
        } else {
            AuiLogger.info(AUImagePickerSkeleton.TAG, "do not allow drag");
        }
    }
}
