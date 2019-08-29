package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

/* renamed from: bvy reason: default package */
/* compiled from: ViewHolder */
public final class bvy {
    public View a;
    private SparseArray<View> b = new SparseArray<>();

    public bvy(View view) {
        this.a = view;
    }

    public bvy(Context context, int i) {
        this.a = LayoutInflater.from(context).inflate(i, null);
    }

    public final <T extends View> T a(int i) {
        T t = (View) this.b.get(i);
        if (t == null) {
            t = this.a.findViewById(i);
            if (t == null) {
                throw new IllegalArgumentException(String.format("can not find the view which id is %d", new Object[]{Integer.valueOf(i)}));
            }
            this.b.put(i, t);
        }
        return t;
    }

    public final bvy a(int i, CharSequence charSequence) {
        ((TextView) a(i)).setText(charSequence);
        return this;
    }

    private bvy d(int i, int i2) {
        ((TextView) a(i)).setTextColor(i2);
        return this;
    }

    public final bvy a(int i, int i2) {
        return d(i, this.a.getResources().getColor(i2));
    }

    public final bvy b(int i, int i2) {
        a(i).setBackgroundResource(i2);
        return this;
    }

    public final bvy a(int i, String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            return this;
        }
        ko.a((ImageView) a(i), str, null, i2);
        return this;
    }

    public final bvy a(int i, boolean z) {
        a(i).setVisibility(z ? 0 : 8);
        return this;
    }

    public final bvy a(int i, int i2, int i3) {
        View a2 = a(i);
        LayoutParams layoutParams = a2.getLayoutParams();
        layoutParams.width = i2;
        layoutParams.height = i3;
        a2.setLayoutParams(layoutParams);
        return this;
    }

    public final bvy c(int i, int i2) {
        View a2 = a(i);
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) a2.getLayoutParams();
        marginLayoutParams.topMargin = i2;
        a2.setLayoutParams(marginLayoutParams);
        return this;
    }

    public final bvy a(int i, OnClickListener onClickListener) {
        a(i).setOnClickListener(onClickListener);
        return this;
    }
}
