package defpackage;

import android.os.Bundle;

/* renamed from: dxy reason: default package */
/* compiled from: IListItemView */
public interface dxy<DT> {
    void bindData(int i, DT dt, Bundle bundle);

    boolean isDataChanged(DT dt);

    void setOnViewClickListener(dxz dxz);
}
