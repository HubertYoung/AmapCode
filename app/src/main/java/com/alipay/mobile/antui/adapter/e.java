package com.alipay.mobile.antui.adapter;

import android.view.View;
import android.view.View.OnLongClickListener;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.MyViewHolder;
import com.alipay.mobile.antui.utils.AuiLogger;

/* compiled from: ImagePickerAdapter */
final class e implements OnLongClickListener {
    final /* synthetic */ MyViewHolder a;

    e(MyViewHolder this$1) {
        this.a = this$1;
    }

    public final boolean onLongClick(View v) {
        if (ImagePickerAdapter.this.mOnItemClickListener != null) {
            AuiLogger.info("ItemDragCallback", "ItemDragCallback  onLongClick");
            ImagePickerAdapter.this.mOnItemClickListener.onItemLongClick(this.a, v, this.a.getAdapterPosition());
        }
        return false;
    }
}
