package com.alipay.mobile.antui.basic;

import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

public class AUViewEventHelper {
    private static ClickListenerWrapper clwrapper;

    public interface ClickListenerWrapper {
        OnClickListener wrap(OnClickListener onClickListener);

        OnItemClickListener wrap(OnItemClickListener onItemClickListener);
    }

    public static void setWrapper(ClickListenerWrapper wrapper) {
        clwrapper = wrapper;
    }

    public static OnClickListener wrapClickListener(OnClickListener l) {
        return clwrapper == null ? l : clwrapper.wrap(l);
    }

    public static OnItemClickListener wrapItemClickListener(OnItemClickListener l) {
        return clwrapper == null ? l : clwrapper.wrap(l);
    }
}
