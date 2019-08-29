package com.alipay.mobile.antui.api;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.alipay.mobile.antui.theme.AUAbsTheme;

public interface AntUI {
    void init(Context context, AttributeSet attributeSet, TypedArray typedArray);

    void initAttrStyle(Context context, AttributeSet attributeSet, TypedArray typedArray);

    void initContent(Context context, AttributeSet attributeSet, TypedArray typedArray);

    void initStyleByTheme(Context context);

    void upDateTheme(Context context, AUAbsTheme aUAbsTheme);
}
