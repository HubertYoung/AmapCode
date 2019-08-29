package defpackage;

import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.bundle.activities.page.ActivitiesPage;

/* renamed from: cub reason: default package */
/* compiled from: ActivitiesPresenter */
public final class cub extends AbstractBasePresenter<ActivitiesPage> {
    public String a;
    public String b;

    public cub(ActivitiesPage activitiesPage) {
        super(activitiesPage);
    }

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            ToastHelper.showToast(str);
        }
        ((ActivitiesPage) this.mPage).a();
    }
}
