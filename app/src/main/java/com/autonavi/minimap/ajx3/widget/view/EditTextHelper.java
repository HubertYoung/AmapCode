package com.autonavi.minimap.ajx3.widget.view;

import android.text.Layout;
import android.widget.EditText;

public class EditTextHelper {
    private boolean isOffsetValid;
    private int mOffset;
    private EditText mView;

    public EditTextHelper(EditText editText) {
        this.mView = editText;
    }

    public int getScrollY(int i) {
        return this.isOffsetValid ? bringPointIntoViewAdditional(i) : i;
    }

    public void bringPointIntoView(int i) {
        this.isOffsetValid = true;
        this.mOffset = i;
    }

    private int bringPointIntoViewAdditional(int i) {
        this.isOffsetValid = false;
        Layout layout = this.mView.getLayout();
        int lineForOffset = layout.getLineForOffset(this.mOffset);
        int lineTop = layout.getLineTop(lineForOffset);
        int lineTop2 = layout.getLineTop(lineForOffset + 1);
        int height = layout.getHeight();
        int bottom = ((this.mView.getBottom() - this.mView.getTop()) - this.mView.getExtendedPaddingTop()) - this.mView.getExtendedPaddingBottom();
        int i2 = (lineTop2 - lineTop) / 2;
        int i3 = bottom / 4;
        if (i2 > i3) {
            i2 = i3;
        }
        if (lineTop - i < i2) {
            i = lineTop - i2;
        }
        if (lineTop2 - i > bottom) {
            i = lineTop2 - bottom;
        }
        if (height - i < bottom) {
            i = height - bottom;
        }
        if (0 - i > 0) {
            return 0;
        }
        return i;
    }
}
