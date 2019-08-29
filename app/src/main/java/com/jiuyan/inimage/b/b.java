package com.jiuyan.inimage.b;

import android.content.Context;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.bean.BeanAKeyUse;
import com.jiuyan.inimage.http.interfaces.OnCompleteListener;
import com.jiuyan.inimage.util.q;

/* compiled from: AKeyUseUtil */
final class b implements OnCompleteListener {
    final /* synthetic */ d a;
    final /* synthetic */ Context b;
    final /* synthetic */ e c;

    b(d dVar, Context context, e eVar) {
        this.a = dVar;
        this.b = context;
        this.c = eVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onSuccess(Object obj) {
        if (obj == null) {
            q.a((String) "AKeyUseUtilget data error");
            if (this.a != null) {
                this.a.b(1);
                return;
            }
            return;
        }
        BeanAKeyUse beanAKeyUse = (BeanAKeyUse) obj;
        if (!beanAKeyUse.succ || beanAKeyUse.data == null) {
            q.a((String) "AKeyUseUtilget data error");
            if (this.a != null) {
                this.a.b(1);
                return;
            }
            return;
        }
        q.a((String) "AKeyUseUtilget data succ");
        a.b(this.b, beanAKeyUse, this.c, this.a);
    }

    public void onFailed(int i) {
        q.a((String) "AKeyUseUtilget data error");
        if (this.a != null) {
            this.a.b(1);
        }
    }
}
