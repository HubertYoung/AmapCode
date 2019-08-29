package com.jiuyan.inimage.a;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.ViewGroup;
import com.alipay.android.hackbyte.ClassVerifier;

/* compiled from: BaseRecyclerAdapterWithHeaderFooter */
public abstract class a extends b {
    private boolean c = false;
    private boolean d = false;

    public abstract int a();

    public abstract ViewHolder a(ViewGroup viewGroup);

    public abstract void a(ViewHolder viewHolder, int i);

    public abstract ViewHolder b(ViewGroup viewGroup);

    public abstract void b(ViewHolder viewHolder, int i);

    public abstract ViewHolder c(ViewGroup viewGroup);

    public abstract void c(ViewHolder viewHolder, int i);

    public a(Context context) {
        super(context);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(boolean z) {
        this.c = z;
    }

    public void b(boolean z) {
        this.d = z;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == Integer.MIN_VALUE) {
            return a(viewGroup);
        }
        if (i == -2147483647) {
            return b(viewGroup);
        }
        return c(viewGroup);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (i == 0 && viewHolder.getItemViewType() == Integer.MIN_VALUE) {
            a(viewHolder, i);
        } else if (i == a() && viewHolder.getItemViewType() == -2147483647) {
            b(viewHolder, i);
        } else {
            c(viewHolder, i - (this.c ? 1 : 0));
        }
    }

    public int getItemCount() {
        int a = a();
        if (this.c) {
            a++;
        }
        if (this.d) {
            return a + 1;
        }
        return a;
    }

    public int getItemViewType(int i) {
        if (i == 0 && this.c) {
            return Integer.MIN_VALUE;
        }
        if (i != a() || !this.d) {
            return 0;
        }
        return -2147483647;
    }
}
