package com.autonavi.minimap.ajx3.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import java.util.regex.Pattern;

public class CustomRegularFilter implements InputFilter {
    private EditText mEditText;
    private Pattern mRegularPattern = null;

    public CustomRegularFilter(String str, EditText editText) {
        this.mRegularPattern = Pattern.compile(str, 2);
        this.mEditText = editText;
    }

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mEditText.getText().toString());
        sb.append(charSequence);
        return !this.mRegularPattern.matcher(sb.toString()).matches() ? "" : charSequence;
    }
}
