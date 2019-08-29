package defpackage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.autonavi.map.suspend.SuspendViewCommonTemplate;
import com.autonavi.map.suspend.SuspendViewCommonTemplate.a;

/* renamed from: ccv reason: default package */
/* compiled from: SuspendViewHelper */
public class ccv implements a {
    public static final int POSITION_BOTTOM_MIDDLE = 7;
    public static final int POSITION_LEFT_BOTTOM = 3;
    public static final int POSITION_LEFT_MIDDLE = 2;
    public static final int POSITION_LEFT_TOP = 1;
    public static final int POSITION_RIGHT_BOTTOM = 6;
    public static final int POSITION_RIGHT_MIDDLE = 5;
    public static final int POSITION_RIGHT_TOP = 4;
    private SuspendViewCommonTemplate mSuspendViewTemplate;

    public boolean adjustWidget(int i, ViewGroup viewGroup, int i2) {
        return false;
    }

    public ccv(Context context) {
        this.mSuspendViewTemplate = new SuspendViewCommonTemplate(context);
    }

    public void addWidget(View view, LayoutParams layoutParams, int i) {
        this.mSuspendViewTemplate.addView(view, layoutParams, i);
    }

    public void addWidget(View view, LayoutParams layoutParams, int i, int i2) {
        this.mSuspendViewTemplate.addView(view, layoutParams, i, i2);
    }

    public void enabledAdjustWidgetCallback() {
        this.mSuspendViewTemplate.setViewAdjustCallback(this);
    }

    public View getSuspendView() {
        return this.mSuspendViewTemplate;
    }
}
