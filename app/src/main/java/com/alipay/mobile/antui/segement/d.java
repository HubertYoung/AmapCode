package com.alipay.mobile.antui.segement;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.model.ItemCategory;

/* compiled from: AUSegment */
final class d implements OnClickListener {
    final /* synthetic */ ItemCategory a;
    final /* synthetic */ AUSegment b;

    d(AUSegment this$0, ItemCategory itemCategory) {
        this.b = this$0;
        this.a = itemCategory;
    }

    public final void onClick(View v) {
        if (this.b.repeatClick || !TextUtils.equals(this.b.currentCategoryTag, (String) v.getTag())) {
            this.b.currentCategoryTag = (String) v.getTag();
            this.b.setCurMenuState(v, this.a);
        }
    }
}
