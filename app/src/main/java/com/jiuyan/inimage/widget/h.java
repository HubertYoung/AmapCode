package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.bean.BeanBaseOneKeyFacePaster;
import com.jiuyan.inimage.http.interfaces.OnCompleteListener;
import com.jiuyan.inimage.util.q;

/* compiled from: MagicWandView */
class h implements OnCompleteListener {
    final /* synthetic */ boolean a;
    final /* synthetic */ MagicWandView b;

    h(MagicWandView magicWandView, boolean z) {
        this.b = magicWandView;
        this.a = z;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onSuccess(Object obj) {
        this.b.f = false;
        BeanBaseOneKeyFacePaster beanBaseOneKeyFacePaster = (BeanBaseOneKeyFacePaster) obj;
        if (beanBaseOneKeyFacePaster.succ && beanBaseOneKeyFacePaster.data != null && beanBaseOneKeyFacePaster.data.is_show) {
            this.b.a = beanBaseOneKeyFacePaster.data;
        }
        if (this.a) {
            this.b.onClick(null);
        }
    }

    public void onFailed(int i) {
        q.a((String) "get paster info fail");
        this.b.f = false;
        this.b.a(false);
        this.b.a(this.b.getContext().getString(R.string.in_sdk_download_failed));
    }
}
