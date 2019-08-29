package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.theme.AUThemeKey;
import com.alipay.mobile.antui.theme.AUThemeManager;

/* compiled from: AUPopMenu */
final class aj extends AULinearLayout {
    final /* synthetic */ AUPopMenu a;
    private AUTextView b;
    private View c;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public aj(AUPopMenu aUPopMenu, Context context) {
        // this.a = aUPopMenu;
        super(context);
        b();
    }

    private void b() {
        LayoutInflater.from(getContext()).inflate(R.layout.au_pop_menu_item, this, true);
        setOrientation(1);
        setPadding(this.a.itemMargin, 0, this.a.itemMargin, 0);
        this.b = (AUTextView) findViewById(R.id.pop_title);
        this.c = findViewById(R.id.pop_divider);
    }

    public final void a(MessagePopItem item) {
        if (item != null && !TextUtils.isEmpty(item.title)) {
            b(item);
        }
    }

    private void b(MessagePopItem item) {
        AUAbsTheme theme = AUThemeManager.getCurrentTheme();
        this.b.setText(item.title);
        Integer textColor = theme.getColor(getContext(), AUThemeKey.FLOATMENU_TEXTCOLOR);
        if (textColor != null) {
            this.b.setTextColor(textColor.intValue());
        }
        Float textSize = theme.getDimension(getContext(), AUThemeKey.FLOATMENU_TEXTSIZE);
        if (textSize != null) {
            this.b.setTextSize(0, textSize.floatValue());
        }
    }

    public final void a(boolean isVisible) {
        this.c.setVisibility(isVisible ? 0 : 8);
    }

    public final AUTextView a() {
        return this.b;
    }
}
