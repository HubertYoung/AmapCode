package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.search.album.page.AlbumFolderPage;

/* renamed from: bvf reason: default package */
/* compiled from: AlbumFolderPresenter */
public final class bvf extends AbstractBasePresenter<AlbumFolderPage> {
    private ResultType a = ResultType.NONE;

    public bvf(AlbumFolderPage albumFolderPage) {
        super(albumFolderPage);
    }

    public final void onDestroy() {
        AlbumFolderPage albumFolderPage = (AlbumFolderPage) this.mPage;
        if (albumFolderPage.a != null) {
            albumFolderPage.a.setOnClickListener(null);
        }
        if (albumFolderPage.b != null) {
            albumFolderPage.b.setOnItemClickListener(null);
        }
        super.onDestroy();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (pageBundle != null && pageBundle.getBoolean("PHOTO_FRAGMENT_CLOSE")) {
            ((AlbumFolderPage) this.mPage).setResult(ResultType.OK, pageBundle);
            ((AlbumFolderPage) this.mPage).finish();
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (ResultType.OK == this.a) {
            ((AlbumFolderPage) this.mPage).setResult(ResultType.OK, ((AlbumFolderPage) this.mPage).a());
        }
        return super.onBackPressed();
    }
}
