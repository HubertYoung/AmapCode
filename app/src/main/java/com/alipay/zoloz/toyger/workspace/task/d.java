package com.alipay.zoloz.toyger.workspace.task;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/* compiled from: ToygerBaseTask */
final class d implements Runnable {
    final /* synthetic */ Bitmap a;
    final /* synthetic */ ToygerBaseTask b;

    d(ToygerBaseTask toygerBaseTask, Bitmap bitmap) {
        this.b = toygerBaseTask;
        this.a = bitmap;
    }

    public final void run() {
        if (this.b.mToygerCirclePattern != null) {
            this.b.mToygerCirclePattern.getGuassianBackground().setVisibility(0);
            this.b.mToygerCirclePattern.getGuassianBackground().setBackgroundDrawable(new BitmapDrawable(this.b.mContext.getResources(), this.a));
        }
    }
}
