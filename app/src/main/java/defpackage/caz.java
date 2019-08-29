package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.search.page.PoiDetailWebPage;

/* renamed from: caz reason: default package */
/* compiled from: PoiDetailWebPresenter */
public final class caz extends cau<PoiDetailWebPage> {
    public caz(PoiDetailWebPage poiDetailWebPage) {
        super(poiDetailWebPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
        ((PoiDetailWebPage) this.mPage).setSoftInputMode(48);
        PoiDetailWebPage poiDetailWebPage = (PoiDetailWebPage) this.mPage;
        if (poiDetailWebPage.a != null) {
            poiDetailWebPage.a.resumeTimers();
        }
    }

    public final void onStop() {
        super.onStop();
        PoiDetailWebPage poiDetailWebPage = (PoiDetailWebPage) this.mPage;
        if (poiDetailWebPage.a != null) {
            poiDetailWebPage.a.pauseTimers();
        }
        PoiDetailWebPage poiDetailWebPage2 = (PoiDetailWebPage) this.mPage;
        if (poiDetailWebPage2.a != null) {
            poiDetailWebPage2.a.stopLoading();
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        boolean z;
        PoiDetailWebPage poiDetailWebPage = (PoiDetailWebPage) this.mPage;
        if (poiDetailWebPage.a.canGoBack()) {
            poiDetailWebPage.a.goBack();
            poiDetailWebPage.a();
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }
}
