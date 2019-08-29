package com.jiuyan.inimage;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.b.s;
import com.jiuyan.inimage.bean.BeanPaster;
import com.jiuyan.inimage.d.b;
import com.jiuyan.inimage.util.q;

/* compiled from: InPhotoEditActivity */
class g implements s {
    final /* synthetic */ BeanPaster a;
    final /* synthetic */ InPhotoEditActivity b;

    g(InPhotoEditActivity inPhotoEditActivity, BeanPaster beanPaster) {
        this.b = inPhotoEditActivity;
        this.a = beanPaster;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(String str) {
        b bVar = new b();
        bVar.a = this.a;
        bVar.b = "0";
        q.a("H5Receiver", "download " + str + " succ");
        q.a("H5Receiver", "download " + str + " succ");
        this.b.f.a(bVar);
    }

    public void b(String str) {
        this.b.toast(this.b.getString(R.string.in_sdk_download_failed), 0);
        q.a("H5Receiver", "download " + str + " fail");
    }
}
