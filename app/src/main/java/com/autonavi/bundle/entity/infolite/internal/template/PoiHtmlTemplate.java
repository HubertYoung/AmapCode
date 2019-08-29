package com.autonavi.bundle.entity.infolite.internal.template;

import android.text.Html;
import android.text.Spanned;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class PoiHtmlTemplate extends PoiLayoutTemplate {
    private static final long serialVersionUID = 5465668749441292971L;
    private Spanned spanned;
    private String value;

    public int isShown() {
        return (this.value == null || "".equals(this.value)) ? 8 : 0;
    }

    public Spanned getSpanned() {
        return this.spanned;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
        this.spanned = Html.fromHtml(str);
    }
}
