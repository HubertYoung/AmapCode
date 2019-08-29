package defpackage;

import android.text.TextUtils;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.basemap.multipoint.page.MultiPointListPage;
import com.autonavi.minimap.basemap.multipoint.page.MultiPointListPage.PointsAdapter;
import java.util.List;

/* renamed from: cqg reason: default package */
/* compiled from: MultiPointListPresenter */
public final class cqg extends AbstractBasePresenter<MultiPointListPage> {
    public cqg(MultiPointListPage multiPointListPage) {
        super(multiPointListPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        MultiPointListPage multiPointListPage = (MultiPointListPage) this.mPage;
        PageBundle arguments = ((MultiPointListPage) this.mPage).getArguments();
        if (arguments.containsKey("key_title")) {
            String string = arguments.getString("key_title");
            if (!TextUtils.isEmpty(string)) {
                multiPointListPage.b.setText(string);
            }
        }
        List list = (List) arguments.getObject("key_multi_points");
        if (list != null && list.size() > 0) {
            multiPointListPage.d = arguments.getInt("key_focus_index");
            multiPointListPage.c.clear();
            multiPointListPage.c.addAll(list);
            multiPointListPage.a.setAdapter(new PointsAdapter());
        }
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final ON_BACK_TYPE onBackPressed() {
        return super.onBackPressed();
    }
}
