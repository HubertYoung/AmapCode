package com.jiuyan.inimage;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: InPhotoEditActivity */
class b implements OnClickListener {
    final /* synthetic */ InPhotoEditActivity a;

    b(InPhotoEditActivity inPhotoEditActivity) {
        this.a = inPhotoEditActivity;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onClick(View view) {
        view.setVisibility(8);
        if (this.a.a(this.a.o, this.a.n)) {
            this.a.j.postDelayed(new c(this), 1000);
        }
    }
}
