package com.autonavi.minimap.ajx3.util;

import android.text.InputFilter;
import android.text.Spanned;
import java.util.regex.Pattern;

public class EmojiFilter implements InputFilter {
    private Pattern mEmojiPattern = Pattern.compile("[🀀-🏿]|[🐀-🟿]|[☀-⟿]", 66);

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return this.mEmojiPattern.matcher(charSequence).find() ? "" : charSequence;
    }
}
