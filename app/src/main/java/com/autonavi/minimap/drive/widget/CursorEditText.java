package com.autonavi.minimap.drive.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.autonavi.minimap.R;

public class CursorEditText extends FrameLayout {
    private EditText mEditText;

    public CursorEditText(Context context) {
        this(context, null);
    }

    public CursorEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CursorEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        inflate(context, R.layout.cursor_box_edittext, this);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mEditText = (EditText) findViewById(R.id.origin_edittext);
    }

    public EditText getOriginEditText() {
        return this.mEditText;
    }

    public void setCursorBoxBg(int i) {
        if (i != -1) {
            this.mEditText.setBackgroundResource(i);
        }
    }
}
