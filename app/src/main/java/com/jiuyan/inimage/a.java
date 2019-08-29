package com.jiuyan.inimage;

import android.graphics.PointF;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.paster.d;
import com.jiuyan.inimage.paster.j;

/* compiled from: InPhotoEditActivity */
class a implements j {
    final /* synthetic */ InPhotoEditActivity a;

    a(InPhotoEditActivity inPhotoEditActivity) {
        this.a = inPhotoEditActivity;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean a(PointF pointF) {
        if (this.a.l.getVisibility() == 0) {
            this.a.l.setVisibility(8);
        }
        this.a.b = false;
        this.a.b();
        return true;
    }

    public boolean a(boolean z) {
        if (!this.a.b) {
            this.a.a(z);
        }
        return true;
    }

    public boolean a(d dVar) {
        if (!this.a.b) {
            if (this.a.l.getVisibility() == 8) {
                this.a.l.setVisibility(0);
            }
            this.a.l.a(dVar.b());
        } else {
            this.a.l.a(dVar.b());
        }
        return true;
    }

    public boolean a() {
        if (this.a.l.getVisibility() == 0) {
            this.a.l.setVisibility(8);
        }
        this.a.f();
        return false;
    }

    public boolean b(d dVar) {
        return false;
    }
}
