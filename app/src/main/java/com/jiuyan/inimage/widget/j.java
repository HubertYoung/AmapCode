package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.b.p;
import com.jiuyan.inimage.b.r;
import com.jiuyan.inimage.bean.BeanPaster;
import com.jiuyan.inimage.d.a;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: MagicWandView */
class j implements r {
    final /* synthetic */ List a;
    final /* synthetic */ Map b;
    final /* synthetic */ MagicWandView c;

    j(MagicWandView magicWandView, List list, Map map) {
        this.c = magicWandView;
        this.a = list;
        this.b = map;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(int i) {
    }

    public void a(List<p> list) {
        this.c.a(this.c.getContext().getString(R.string.in_sdk_download_failed));
        this.c.a(false);
    }

    public void a() {
        this.c.a(false);
        List<List<BeanPaster>> a2 = this.c.a(this.c.b, this.a, this.b);
        if (a2 != null) {
            ArrayList arrayList = new ArrayList();
            for (List<BeanPaster> next : a2) {
                for (BeanPaster beanPaster : next) {
                    beanPaster.fromWhere = "1";
                }
                arrayList.addAll(next);
            }
            a aVar = new a();
            aVar.a = arrayList;
            this.c.c.onMagicWandClick(aVar);
        }
    }
}
