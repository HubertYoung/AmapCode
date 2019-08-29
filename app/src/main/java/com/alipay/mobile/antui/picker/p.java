package com.alipay.mobile.antui.picker;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import com.alipay.mobile.antui.utils.DensityUtil;

/* compiled from: AUImagePickerSkeleton */
final class p extends ItemDecoration {
    final /* synthetic */ AUImagePickerSkeleton a;
    private float b;
    private float c;

    public p(AUImagePickerSkeleton this$0, float lrSpace, float tbSpace) {
        this.a = this$0;
        this.b = lrSpace;
        this.c = tbSpace;
    }

    public final void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        outRect.left = DensityUtil.dip2px(this.a.getContext(), this.b);
        outRect.right = DensityUtil.dip2px(this.a.getContext(), this.b);
        outRect.bottom = DensityUtil.dip2px(this.a.getContext(), this.c);
        outRect.top = DensityUtil.dip2px(this.a.getContext(), this.c);
    }
}
