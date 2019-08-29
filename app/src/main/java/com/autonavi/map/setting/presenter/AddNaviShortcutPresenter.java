package com.autonavi.map.setting.presenter;

import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.setting.page.AddNaviShortcutPage;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"UWF_NULL_FIELD"})
public final class AddNaviShortcutPresenter extends AbstractBasePresenter<AddNaviShortcutPage> {
    public POI a;
    public String b = null;
    public String c;

    class SearchTaskCallback implements Callback<POI> {
        public void error(Throwable th, boolean z) {
        }

        private SearchTaskCallback() {
        }

        public void callback(POI poi) {
            AddNaviShortcutPresenter.this.a(poi);
        }
    }

    public AddNaviShortcutPresenter(AddNaviShortcutPage addNaviShortcutPage) {
        super(addNaviShortcutPage);
    }

    /* access modifiers changed from: private */
    public void a(POI poi) {
        this.a = poi;
        if (this.a != null) {
            AddNaviShortcutPage addNaviShortcutPage = (AddNaviShortcutPage) this.mPage;
            addNaviShortcutPage.a.setText(this.a.getName());
            addNaviShortcutPage.a();
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (i == 1001 && resultType == ResultType.OK) {
            try {
                AddNaviShortcutPage addNaviShortcutPage = (AddNaviShortcutPage) this.mPage;
                addNaviShortcutPage.c.setText(pageBundle.getString("name"));
                addNaviShortcutPage.a();
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        if (pageBundle != null) {
            if (pageBundle.containsKey("MapClickResult")) {
                a((POI) pageBundle.getObject("MapClickResult"));
            } else if (pageBundle.containsKey("searchResult")) {
                a((POI) pageBundle.getObject("searchResult"));
            }
        }
        super.onResult(i, resultType, pageBundle);
    }
}
