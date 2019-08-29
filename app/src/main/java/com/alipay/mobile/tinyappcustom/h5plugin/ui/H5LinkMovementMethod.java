package com.alipay.mobile.tinyappcustom.h5plugin.ui;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class H5LinkMovementMethod extends LinkMovementMethod {
    private OnLinkClickListener a;

    public interface OnLinkClickListener {
        void onLinkClick(String str);
    }

    public void setOnLinkClickListener(OnLinkClickListener onLinkClickListener) {
        this.a = onLinkClickListener;
    }

    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();
        if (action == 1 || action == 0) {
            Layout layout = widget.getLayout();
            int off = layout.getOffsetForHorizontal(layout.getLineForVertical((((int) event.getY()) - widget.getTotalPaddingTop()) + widget.getScrollY()), (float) ((((int) event.getX()) - widget.getTotalPaddingLeft()) + widget.getScrollX()));
            ClickableSpan[] link = (ClickableSpan[]) buffer.getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == 1) {
                    this.a.onLinkClick(((URLSpan) link[0]).getURL());
                }
                return true;
            }
            Selection.removeSelection(buffer);
        }
        return super.onTouchEvent(widget, buffer, event);
    }
}
