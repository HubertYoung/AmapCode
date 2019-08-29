package android.support.v4.widget;

import android.widget.TextView;

class TextViewCompatJb {
    TextViewCompatJb() {
    }

    static int a(TextView textView) {
        return textView.getMaxLines();
    }

    static int b(TextView textView) {
        return textView.getMinLines();
    }
}
