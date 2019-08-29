package com.alipay.mobile.antui.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.MyViewHolder;

/* compiled from: ImagePickerAdapter */
final class h implements OnClickListener {
    final /* synthetic */ MyViewHolder a;

    h(MyViewHolder this$1) {
        this.a = this$1;
    }

    public final void onClick(View v) {
        if (ImagePickerAdapter.this.mOnItemClickListener != null) {
            ImagePickerAdapter.this.mOnItemClickListener.onItemAddClick(v, this.a.getAdapterPosition());
        }
    }
}
