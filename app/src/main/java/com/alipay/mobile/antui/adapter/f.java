package com.alipay.mobile.antui.adapter;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.MyViewHolder;

/* compiled from: ImagePickerAdapter */
final class f implements OnTouchListener {
    final /* synthetic */ MyViewHolder a;

    f(MyViewHolder this$1) {
        this.a = this$1;
    }

    public final boolean onTouch(View v, MotionEvent event) {
        ImagePickerAdapter.this.mOnItemClickListener.onTouch(v, event);
        return false;
    }
}
