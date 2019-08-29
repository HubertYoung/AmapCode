package com.jiuyan.inimage.a;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.InPhotoEditActivity;
import com.jiuyan.inimage.b.s;
import com.jiuyan.inimage.bean.BeanDataPaster;
import com.jiuyan.inimage.widget.RoundProgressBar;

/* compiled from: PasterGalleryRecommendAdapter */
class f implements s {
    final /* synthetic */ RoundProgressBar a;
    final /* synthetic */ int b;
    final /* synthetic */ BeanDataPaster c;
    final /* synthetic */ c d;

    f(c cVar, RoundProgressBar roundProgressBar, int i, BeanDataPaster beanDataPaster) {
        this.d = cVar;
        this.a = roundProgressBar;
        this.b = i;
        this.c = beanDataPaster;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(String str) {
        this.a.setProgress(100.0f);
        this.d.notifyDataSetChanged();
        if (this.d.d != null) {
            this.d.d.a(this.b, this.c);
        }
    }

    public void b(String str) {
        this.a.setProgress(0.0f);
        if (this.d.a instanceof InPhotoEditActivity) {
            ((InPhotoEditActivity) this.d.a).toast(this.d.a.getString(R.string.in_sdk_download_failed), 0);
        }
    }
}
