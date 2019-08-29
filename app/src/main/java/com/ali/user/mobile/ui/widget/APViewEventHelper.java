package com.ali.user.mobile.ui.widget;

import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

public class APViewEventHelper {
    private static ClickListenerWrapper a;

    public interface ClickListenerWrapper {
        OnClickListener a();

        OnItemClickListener b();
    }

    public static OnClickListener a(OnClickListener onClickListener) {
        if (a == null) {
            return onClickListener;
        }
        return a.a();
    }

    public static OnItemClickListener a(OnItemClickListener onItemClickListener) {
        if (a == null) {
            return onItemClickListener;
        }
        return a.b();
    }
}
