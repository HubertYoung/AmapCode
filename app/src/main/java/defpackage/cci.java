package defpackage;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

/* renamed from: cci reason: default package */
/* compiled from: GridDivider */
public final class cci extends ItemDecoration {
    private int a = 0;

    public cci(int i) {
        this.a = i;
    }

    public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        rect.set(this.a, this.a, this.a, 0);
    }
}
