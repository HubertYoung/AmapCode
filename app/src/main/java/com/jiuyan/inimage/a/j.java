package com.jiuyan.inimage.a;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.ViewGroup;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.bean.BeanRecRelation.DataEntity;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PasterGalleryRecommendTitleAdapter */
public class j extends a {
    private List<DataEntity> c = new ArrayList();
    /* access modifiers changed from: private */
    public m d;
    private int e = 0;

    public j(Context context) {
        super(context);
        a(false);
        b(false);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(m mVar) {
        this.d = mVar;
    }

    public void a(List<DataEntity> list) {
        this.c.addAll(list);
        notifyDataSetChanged();
    }

    public void b(List<DataEntity> list) {
        this.e = 0;
        this.c.clear();
        a(list);
    }

    public void a(int i) {
        this.e = i;
        notifyDataSetChanged();
    }

    public List<DataEntity> b() {
        return this.c;
    }

    public ViewHolder a(ViewGroup viewGroup) {
        return null;
    }

    public void a(ViewHolder viewHolder, int i) {
    }

    public ViewHolder b(ViewGroup viewGroup) {
        return null;
    }

    public void b(ViewHolder viewHolder, int i) {
    }

    public ViewHolder c(ViewGroup viewGroup) {
        return new l(this.b.inflate(R.layout.in_alipay_paster_item_of_recycler_paster_gallery_recommend_title, viewGroup, false));
    }

    public void c(ViewHolder viewHolder, int i) {
        l lVar = (l) viewHolder;
        DataEntity dataEntity = this.c.get(i);
        lVar.a.setText(dataEntity.cate_name);
        lVar.itemView.setOnClickListener(new k(this, i, dataEntity));
        if (i == this.e) {
            lVar.a.setTextColor(Color.parseColor("#222222"));
            lVar.a.setBackgroundResource(R.drawable.in_alipay_publish_relation_title_bg);
            return;
        }
        lVar.a.setBackgroundColor(0);
        lVar.a.setTextColor(-1);
    }

    public int a() {
        return this.c.size();
    }
}
