package defpackage;

import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;

/* renamed from: cow reason: default package */
/* compiled from: IFavoritesAction */
public interface cow {
    void loadDataAsync(boolean z);

    void onEditFragmentResult(int i, ResultType resultType, PageBundle pageBundle);

    void onParentDeleteClick();

    void onParentManageClick();

    void setEditModeEnabled(boolean z, boolean z2);
}
