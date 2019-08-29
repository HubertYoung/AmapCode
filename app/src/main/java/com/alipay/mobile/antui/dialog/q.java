package com.alipay.mobile.antui.dialog;

import android.content.Context;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import java.util.List;

/* compiled from: AUFloatMenu */
final class q extends BaseMessagePopAdapter<r> {
    final /* synthetic */ AUFloatMenu a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public q(AUFloatMenu aUFloatMenu, Context context, List<MessagePopItem> list) {
        // this.a = aUFloatMenu;
        super(context, list);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public r createView(Context context) {
        return new r(this.a, context);
    }
}
