package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.badge.AUBadgeView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;

/* compiled from: AUActionSheet */
final class b extends BaseMessagePopItemView {
    final /* synthetic */ AUActionSheet a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public b(AUActionSheet aUActionSheet, Context context) {
        // this.a = aUActionSheet;
        super(context);
    }

    /* access modifiers changed from: protected */
    public final void initView(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.float_menu_item, this, true);
        this.mTitleView = (AUTextView) findViewById(R.id.item_name);
        this.mIconView = (AUIconView) findViewById(R.id.item_icon);
        this.mBadgeView = (AUBadgeView) findViewById(R.id.red_point);
        setGravity(17);
        setMinimumHeight(context.getResources().getDimensionPixelSize(R.dimen.au_action_sheet_height));
        setBackgroundResource(R.drawable.pop_list_corner_shape);
        this.mTitleView.setTextSize(0, (float) context.getResources().getDimensionPixelOffset(R.dimen.AU_TEXTSIZE5));
        setIconfontSize(context.getResources().getDimensionPixelSize(R.dimen.au_action_sheet_icon_size));
    }

    /* access modifiers changed from: protected */
    public final int getHorizonPadding(Context context) {
        return 0;
    }

    public final void setPopItem(MessagePopItem item) {
        super.setPopItem(item);
        if (item == null || item.externParam == null || !(item.externParam.get(AUActionSheet.TEXT_WARNING_TYPE) instanceof Boolean)) {
            setTitleTextColor(this.a.normalTitleColor);
        } else {
            setTitleTextColor(((Boolean) item.externParam.get(AUActionSheet.TEXT_WARNING_TYPE)).booleanValue() ? this.a.warningTitleColor : this.a.normalTitleColor);
        }
    }
}
