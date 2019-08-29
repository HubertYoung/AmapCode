package com.alipay.mobile.antui.dialog;

import android.content.Context;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import java.util.List;

/* compiled from: AUActionSheet */
final class c extends BaseMessagePopAdapter<b> {
    final /* synthetic */ AUActionSheet a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public c(AUActionSheet aUActionSheet, Context context, List<MessagePopItem> list) {
        // this.a = aUActionSheet;
        super(context, list);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public b createView(Context context) {
        return new b(this.a, context);
    }
}
