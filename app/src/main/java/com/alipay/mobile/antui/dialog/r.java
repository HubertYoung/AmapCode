package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.badge.AUBadgeView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;

/* compiled from: AUFloatMenu */
final class r extends BaseMessagePopItemView {
    final /* synthetic */ AUFloatMenu a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public r(AUFloatMenu aUFloatMenu, Context context) {
        // this.a = aUFloatMenu;
        super(context);
    }

    /* access modifiers changed from: protected */
    public final void initView(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.float_menu_item, this, true);
        this.mTitleView = (AUTextView) findViewById(R.id.item_name);
        this.mIconView = (AUIconView) findViewById(R.id.item_icon);
        this.mBadgeView = (AUBadgeView) findViewById(R.id.red_point);
        setGravity(19);
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.au_float_list_item_height));
    }

    /* access modifiers changed from: protected */
    public final int getHorizonPadding(Context context) {
        return getResources().getDimensionPixelOffset(R.dimen.AU_SPACE3);
    }

    public final void setPopItem(MessagePopItem item) {
        super.setPopItem(item);
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        Integer textColor = theme.getColor(getContext(), AUThemeKey.FLOATMENU_TEXTCOLOR);
        if (textColor != null) {
            setTitleTextColor(textColor.intValue());
        }
        Float textSize = theme.getDimension(getContext(), AUThemeKey.FLOATMENU_TEXTSIZE, null);
        if (textSize != null) {
            setTitleTextSize((int) textSize.floatValue());
        }
    }
}
