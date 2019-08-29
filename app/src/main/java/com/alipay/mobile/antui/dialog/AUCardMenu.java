package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.api.OnLoadImageListener;

public class AUCardMenu extends AUAbsMenu {
    /* access modifiers changed from: private */
    public static final String TAG = AUCardMenu.class.getSimpleName();
    /* access modifiers changed from: private */
    public OnMessageItemClickListener mListener;
    /* access modifiers changed from: private */
    public OnLoadImageListener mOnLoadImageListener;

    public interface OnMessageItemClickListener {
        void onItemClick(int i);

        void onItemOptionsClick(int i, int i2);
    }

    public AUCardMenu(Context context) {
        super(context);
    }

    public BaseAdapter initAdapter(Context context) {
        return new l(this, 0);
    }

    public void setOnLoadImageListener(OnLoadImageListener onLoadImageListener) {
        this.mOnLoadImageListener = onLoadImageListener;
    }

    public void setOnClickListener(OnMessageItemClickListener listener) {
        this.mListener = listener;
        this.mItemClickListener = new i(this);
    }
}
