package com.autonavi.minimap.basemap.traffic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

public class TrafficEditText extends EditText {
    /* access modifiers changed from: private */
    public b inputListener = null;
    private float mDensity;
    private float mScaledDensity;

    class a extends InputConnectionWrapper {
        public a(InputConnection inputConnection) {
            super(inputConnection, false);
        }

        public final boolean sendKeyEvent(KeyEvent keyEvent) {
            if (TrafficEditText.this.inputListener != null) {
                TrafficEditText.this.inputListener;
            }
            return super.sendKeyEvent(keyEvent);
        }

        public final boolean commitText(CharSequence charSequence, int i) {
            if (TrafficEditText.this.inputListener != null) {
                TrafficEditText.this.inputListener;
            }
            return super.commitText(charSequence, i);
        }

        public final void setTarget(InputConnection inputConnection) {
            super.setTarget(inputConnection);
        }
    }

    public interface b {
    }

    public TrafficEditText(Context context) {
        super(context);
        init();
    }

    public TrafficEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TrafficEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return new a(super.onCreateInputConnection(editorInfo));
    }

    private void init() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.mScaledDensity = displayMetrics.scaledDensity;
        this.mDensity = displayMetrics.density;
        setTextSize(getTextSize());
    }

    public void setTextSize(float f) {
        super.setTextSize(getScaleSpSize(f));
    }

    private float getScaleSpSize(float f) {
        if (this.mScaledDensity <= 0.0f) {
            return f;
        }
        float f2 = f / this.mScaledDensity;
        return this.mScaledDensity != this.mDensity ? f2 / (this.mScaledDensity / this.mDensity) : f2;
    }

    public void setInputListener(b bVar) {
        this.inputListener = bVar;
    }
}
