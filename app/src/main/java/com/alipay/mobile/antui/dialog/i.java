package com.alipay.mobile.antui.dialog;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/* compiled from: AUCardMenu */
final class i implements OnItemClickListener {
    final /* synthetic */ AUCardMenu a;

    i(AUCardMenu this$0) {
        this.a = this$0;
    }

    public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(AUCardMenu.TAG, String.format("OnItemClick: position = %d", new Object[]{Integer.valueOf(position)}));
        if (this.a.mListener != null) {
            this.a.mListener.onItemClick(position);
        } else {
            Log.d(AUCardMenu.TAG, "OnItemClick: mListener is null");
        }
    }
}
