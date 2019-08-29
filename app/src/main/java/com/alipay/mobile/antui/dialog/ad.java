package com.alipay.mobile.antui.dialog;

import android.content.Context;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.badge.AUBadgeView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;

/* compiled from: AUListDialog */
final class ad extends BaseMessagePopItemView {
    final /* synthetic */ AUListDialog a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public ad(AUListDialog aUListDialog, Context context) {
        // this.a = aUListDialog;
        super(context);
    }

    /* access modifiers changed from: protected */
    public final void initView(Context context) {
        this.a.inflater.inflate(R.layout.list_item_dialog, this, true);
        this.mIconView = (AUIconView) findViewById(R.id.item_icon);
        this.mTitleView = (AUTextView) findViewById(R.id.item_name);
        this.mBadgeView = (AUBadgeView) findViewById(R.id.item_badge);
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.AU_SPACE12));
        setGravity(19);
        setMakeTitleMax(true);
    }

    /* access modifiers changed from: protected */
    public final int getHorizonPadding(Context context) {
        return getResources().getDimensionPixelOffset(R.dimen.AU_SPACE6);
    }
}
