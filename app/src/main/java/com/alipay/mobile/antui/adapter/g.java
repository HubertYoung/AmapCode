package com.alipay.mobile.antui.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.MyViewHolder;

/* compiled from: ImagePickerAdapter */
final class g implements OnClickListener {
    final /* synthetic */ MyViewHolder a;

    g(MyViewHolder this$1) {
        this.a = this$1;
    }

    public final void onClick(View v) {
        ImagePickerAdapter.this.mOnItemClickListener.onItemClick(v, this.a.getAdapterPosition());
    }
}
