package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.basemap.save.page.SaveEditPointPage;

/* renamed from: crg reason: default package */
/* compiled from: SaveEditPointPresenter */
public final class crg extends AbstractBasePresenter<SaveEditPointPage> {
    public crg(SaveEditPointPage saveEditPointPage) {
        super(saveEditPointPage);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        SaveEditPointPage saveEditPointPage = (SaveEditPointPage) this.mPage;
        if (i == 1 && resultType == ResultType.OK && pageBundle != null) {
            String string = pageBundle.getString("key_tag");
            FavoritePOI favoritePOI = saveEditPointPage.a;
            if (TextUtils.isEmpty(string)) {
                string = null;
            }
            favoritePOI.setTag(string);
        }
    }
}
