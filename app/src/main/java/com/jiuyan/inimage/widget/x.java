package com.jiuyan.inimage.widget;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.bean.BeanDataPaster;
import com.jiuyan.inimage.bean.BeanRecRelation;
import com.jiuyan.inimage.http.interfaces.OnCompleteListener;
import com.jiuyan.inimage.util.q;
import java.util.List;

/* compiled from: RelationFrameLayout */
class x implements OnCompleteListener {
    final /* synthetic */ RelationFrameLayout a;

    x(RelationFrameLayout relationFrameLayout) {
        this.a = relationFrameLayout;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onSuccess(Object obj) {
        q.a("Paster", " relation succ");
        q.a("Paster", "relation succ");
        this.a.b.setVisibility(0);
        this.a.d.setVisibility(0);
        BeanRecRelation beanRecRelation = (BeanRecRelation) obj;
        if (beanRecRelation.succ && beanRecRelation.data != null) {
            q.a("Paster", " relation succ " + beanRecRelation.data.size());
            q.a("Paster", "relation succ" + beanRecRelation.data.size());
            if (beanRecRelation.data.size() > 0) {
                this.a.e.b(beanRecRelation.data);
                List<BeanDataPaster> list = beanRecRelation.data.get(0).list;
                this.a.g = beanRecRelation.data.get(0).cate_id;
                this.a.a(list);
                this.a.f.setVisibility(0);
                return;
            }
            this.a.f.setVisibility(8);
        }
    }

    public void onFailed(int i) {
        q.a("Paster", " relation fail ");
        q.a("Paster", "relation fail");
        this.a.b.setVisibility(0);
    }
}
