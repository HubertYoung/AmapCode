package com.alipay.mobile.antui.basic;

import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.filter.AUFilterTabContainerView;
import com.alipay.mobile.antui.model.FilterCategoryData;

/* compiled from: AUFilterMenuView */
final class i implements OnClickListener {
    final /* synthetic */ FilterCategoryData a;
    final /* synthetic */ int b;
    final /* synthetic */ AUFilterTabContainerView c;
    final /* synthetic */ AUFilterMenuView d;

    i(AUFilterMenuView this$0, FilterCategoryData filterCategoryData, int i, AUFilterTabContainerView aUFilterTabContainerView) {
        this.d = this$0;
        this.a = filterCategoryData;
        this.b = i;
        this.c = aUFilterTabContainerView;
    }

    public final void onClick(View v) {
        if (!this.a.itemDatas.get(this.b).allowMultipleSelect) {
            this.c.clearSelect();
        }
        if (this.d.isOneGroup) {
            this.d.clearSelect();
        }
        v.setSelected(!v.isSelected());
        if (!this.d.isConfirmVisible) {
            this.d.updateData();
        }
    }
}
