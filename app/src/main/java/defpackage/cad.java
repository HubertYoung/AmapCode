package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.search.photo.page.PoiPhotoSuccessPage;

/* renamed from: cad reason: default package */
/* compiled from: PoiPhotoSuccessPresenter */
public final class cad extends cau<PoiPhotoSuccessPage> {
    public cad(PoiPhotoSuccessPage poiPhotoSuccessPage) {
        super(poiPhotoSuccessPage);
    }

    public final ON_BACK_TYPE onBackPressed() {
        PoiPhotoSuccessPage poiPhotoSuccessPage = (PoiPhotoSuccessPage) this.mPage;
        if (poiPhotoSuccessPage.a == null || !poiPhotoSuccessPage.a.canGoBack()) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        }
        poiPhotoSuccessPage.a.stopLoading();
        poiPhotoSuccessPage.a.goBack();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (i == 1) {
            ((PoiPhotoSuccessPage) this.mPage).finish();
        }
    }

    public final void onStart() {
        super.onStart();
        ((PoiPhotoSuccessPage) this.mPage).setSoftInputMode(18);
    }
}
