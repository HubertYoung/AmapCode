package defpackage;

import android.view.View;
import android.view.View.OnTouchListener;

/* renamed from: ced reason: default package */
/* compiled from: IGPSButton */
public interface ced {
    String getLogVersionState();

    int getState();

    View getView();

    int getVisibility();

    void setOnTouchListener(OnTouchListener onTouchListener);

    void setState(int i);

    void setVisibility(int i);
}
