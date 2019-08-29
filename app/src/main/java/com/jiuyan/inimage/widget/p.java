package com.jiuyan.inimage.widget;

import android.view.View;
import com.jiuyan.inimage.util.d;
import com.jiuyan.inimage.util.m;
import com.jiuyan.inimage.util.q;

/* compiled from: PhotoCropViewFreedom */
class p implements Runnable {
    final /* synthetic */ PhotoCropViewFreedom a;
    private final m b;
    private int c;
    private int d;

    public void a() {
        q.a(this.a.e, "Cancel Fling");
        this.b.a(true);
    }

    public void run() {
        if (this.a.f && this.b.a()) {
            int b2 = this.b.b();
            int c2 = this.b.c();
            q.a(this.a.e, "fling run(). CurrentX:" + this.c + " CurrentY:" + this.d + " NewX:" + b2 + " NewY:" + c2);
            this.a.x.postTranslate((float) (this.c - b2), (float) (this.d - c2));
            this.a.setImageViewMatrix(this.a.getDisplayMatrix());
            this.c = b2;
            this.d = c2;
            d.a((View) this.a, (Runnable) this);
        }
    }
}
