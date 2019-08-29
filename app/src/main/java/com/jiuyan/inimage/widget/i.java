package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.b.f;
import com.jiuyan.inimage.bean.BeanAKeyUse;
import com.jiuyan.inimage.util.q;

/* compiled from: MagicWandView */
class i extends f {
    final /* synthetic */ MagicWandView b;

    i(MagicWandView magicWandView) {
        this.b = magicWandView;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean a(BeanAKeyUse beanAKeyUse) {
        this.b.a(beanAKeyUse);
        this.b.a(false);
        return true;
    }

    public void b(int i) {
        super.b(i);
        this.b.a(false);
        q.a((String) "download paster fail");
    }
}
