package com.jiuyan.inimage.widget;

import android.graphics.PointF;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.paster.d;
import com.jiuyan.inimage.paster.j;
import com.jiuyan.inimage.util.q;

/* compiled from: TextWaterMarkView */
class af implements j {
    final /* synthetic */ TextWaterMarkView a;

    af(TextWaterMarkView textWaterMarkView) {
        this.a = textWaterMarkView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean a(PointF pointF) {
        q.a(this.a.c, "onBlankAreaClick");
        this.a.k();
        return false;
    }

    public boolean a(d dVar) {
        q.a(this.a.c, "onSelectedObjectByFinger");
        return false;
    }

    public boolean a(boolean z) {
        return false;
    }

    public boolean a() {
        q.a(this.a.c, "onCancelSelectedByFinger");
        this.a.k();
        return false;
    }

    public boolean b(d dVar) {
        q.a(this.a.c, "onSelectedObjectByClickObjectRect");
        this.a.j();
        return false;
    }
}
