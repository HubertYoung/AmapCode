package com.alipay.zoloz.toyger.extservice;

import android.graphics.Rect;
import com.alipay.zoloz.toyger.algorithm.TGDepthFrame;
import com.alipay.zoloz.toyger.algorithm.TGFrame;

/* compiled from: ToygerIspService */
final class a implements Runnable {
    final /* synthetic */ Rect a;
    final /* synthetic */ TGFrame b;
    final /* synthetic */ TGDepthFrame c;
    final /* synthetic */ ToygerIspService d;

    a(ToygerIspService toygerIspService, Rect rect, TGFrame tGFrame, TGDepthFrame tGDepthFrame) {
        this.d = toygerIspService;
        this.a = rect;
        this.b = tGFrame;
        this.c = tGDepthFrame;
    }

    public final void run() {
        this.d.adjustIsp(this.a, this.b.data, this.c.data);
    }
}
