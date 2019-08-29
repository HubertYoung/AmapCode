package com.jiuyan.inimage.a;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: BaseRecyclerViewAdapter */
public abstract class b extends Adapter<ViewHolder> {
    protected Context a;
    protected LayoutInflater b = LayoutInflater.from(this.a);

    public b(Context context) {
        this.a = context;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
