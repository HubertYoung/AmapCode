package com.alipay.mobile.antui.iconfont;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import org.json.JSONArray;

public interface IconfontInterface {
    void destroy();

    String getIconfontBundle();

    Context getIconfontContext();

    String getIconfontFileName();

    IconfontInterface setIconfontColor(String str);

    void setIconfontFileName(String str);

    IconfontInterface setIconfontFonts(JSONArray jSONArray);

    IconfontInterface setIconfontSize(String str);

    IconfontInterface setIconfontTypeface(Typeface typeface);

    IconfontInterface setIconfontUnicode(String str);

    IconfontInterface setImageDrawable(Drawable drawable);

    IconfontInterface setImageResource(int i);
}
