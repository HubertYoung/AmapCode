package com.autonavi.minimap.search.view;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

public class EllipsizedHiddenTextView extends TextView {
    private a mGlobalLayoutListener;
    /* access modifiers changed from: private */
    public int mMinShowCounts = -1;

    class a implements OnGlobalLayoutListener {
        private a() {
        }

        /* synthetic */ a(EllipsizedHiddenTextView ellipsizedHiddenTextView, byte b) {
            this();
        }

        public final void onGlobalLayout() {
            if (EllipsizedHiddenTextView.this.getText().length() > 0) {
                Layout layout = EllipsizedHiddenTextView.this.getLayout();
                if (layout != null) {
                    int lineCount = layout.getLineCount();
                    if (lineCount > 0) {
                        int ellipsisCount = layout.getEllipsisCount(lineCount - 1);
                        if (ellipsisCount <= 0 || EllipsizedHiddenTextView.this.getText().length() - ellipsisCount <= EllipsizedHiddenTextView.this.mMinShowCounts) {
                            EllipsizedHiddenTextView.this.setVisibility(0);
                        }
                    }
                }
                return;
            }
            EllipsizedHiddenTextView.this.setVisibility(8);
        }
    }

    public EllipsizedHiddenTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setShowCounts(attributeSet);
    }

    public EllipsizedHiddenTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setShowCounts(attributeSet);
    }

    public EllipsizedHiddenTextView(Context context) {
        super(context);
    }

    private void setShowCounts(AttributeSet attributeSet) {
        String attributeValue = attributeSet.getAttributeValue(null, "minShowCounts");
        if (attributeValue != null) {
            try {
                this.mMinShowCounts = Integer.parseInt(attributeValue);
            } catch (NumberFormatException e) {
                this.mMinShowCounts = -1;
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        if (this.mGlobalLayoutListener == null) {
            this.mGlobalLayoutListener = new a(this, 0);
            getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
        super.onAttachedToWindow();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mGlobalLayoutListener != null) {
            getViewTreeObserver().removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
            this.mGlobalLayoutListener = null;
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        if (getText().length() > 0) {
            setVisibility(0);
        }
        super.onConfigurationChanged(configuration);
    }
}
