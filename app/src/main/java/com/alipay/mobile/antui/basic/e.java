package com.alipay.mobile.antui.basic;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.basic.AUCardOptionView.CardOptionItem;

/* compiled from: AUCardOptionView */
final class e implements OnClickListener {
    final /* synthetic */ CardOptionItem a;
    final /* synthetic */ f b;
    final /* synthetic */ AUCardOptionView c;

    e(AUCardOptionView this$0, CardOptionItem cardOptionItem, f fVar) {
        this.c = this$0;
        this.a = cardOptionItem;
        this.b = fVar;
    }

    public final void onClick(View v) {
        if (this.c.mListner != null) {
            this.c.mListner.onCardOptionClick(v, this.a, this.b.d);
        } else {
            Log.d(AUCardOptionView.TAG, "mListner is null");
        }
    }
}
