package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemAnimator.ItemHolderInfo;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

public abstract class SimpleItemAnimator extends ItemAnimator {
    boolean f = true;

    public abstract boolean a(ViewHolder viewHolder);

    public abstract boolean a(ViewHolder viewHolder, int i, int i2, int i3, int i4);

    public abstract boolean a(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4);

    public abstract boolean b(ViewHolder viewHolder);

    public final boolean g(@NonNull ViewHolder viewHolder) {
        return !this.f || viewHolder.isInvalid();
    }

    public final boolean a(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2) {
        int i = itemHolderInfo.a;
        int i2 = itemHolderInfo.b;
        View view = viewHolder.itemView;
        int left = itemHolderInfo2 == null ? view.getLeft() : itemHolderInfo2.a;
        int top = itemHolderInfo2 == null ? view.getTop() : itemHolderInfo2.b;
        if (viewHolder.isRemoved() || (i == left && i2 == top)) {
            return a(viewHolder);
        }
        view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
        return a(viewHolder, i, i2, left, top);
    }

    public final boolean b(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
        if (itemHolderInfo == null || (itemHolderInfo.a == itemHolderInfo2.a && itemHolderInfo.b == itemHolderInfo2.b)) {
            return b(viewHolder);
        }
        return a(viewHolder, itemHolderInfo.a, itemHolderInfo.b, itemHolderInfo2.a, itemHolderInfo2.b);
    }

    public final boolean c(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
        if (itemHolderInfo.a == itemHolderInfo2.a && itemHolderInfo.b == itemHolderInfo2.b) {
            f(viewHolder);
            return false;
        }
        return a(viewHolder, itemHolderInfo.a, itemHolderInfo.b, itemHolderInfo2.a, itemHolderInfo2.b);
    }

    public final boolean a(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2) {
        int i;
        int i2;
        int i3 = itemHolderInfo.a;
        int i4 = itemHolderInfo.b;
        if (viewHolder2.shouldIgnore()) {
            int i5 = itemHolderInfo.a;
            i = itemHolderInfo.b;
            i2 = i5;
        } else {
            i2 = itemHolderInfo2.a;
            i = itemHolderInfo2.b;
        }
        return a(viewHolder, viewHolder2, i3, i4, i2, i);
    }
}
