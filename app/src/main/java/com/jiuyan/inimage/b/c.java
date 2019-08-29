package com.jiuyan.inimage.b;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.bean.BeanAKeyUse;
import com.jiuyan.inimage.util.q;
import java.util.List;

/* compiled from: AKeyUseUtil */
final class c implements r {
    final /* synthetic */ d a;
    final /* synthetic */ List b;
    final /* synthetic */ BeanAKeyUse c;

    c(d dVar, List list, BeanAKeyUse beanAKeyUse) {
        this.a = dVar;
        this.b = list;
        this.c = beanAKeyUse;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(int i) {
        if (this.a != null) {
            this.a.a(i);
        }
    }

    public void a(List<p> list) {
        q.a((String) "AKeyUseUtildownload paster fail");
        a.b(list);
        if (this.a != null) {
            this.a.b(2);
        }
    }

    public void a() {
        q.a((String) "AKeyUseUtildownload paster succ");
        if (this.a == null) {
            return;
        }
        if (a.b(this.b) > 0) {
            this.a.b(2);
        } else {
            this.a.a(this.c);
        }
    }
}
