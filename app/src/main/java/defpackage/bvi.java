package defpackage;

import android.content.Intent;
import android.location.Location;
import android.text.TextUtils;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.gdtaojin.camera.CameraInterface;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.search.album.page.AlbumSelectPhotoPage;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bvi reason: default package */
/* compiled from: AlbumSelectPhotoPresenter */
public final class bvi extends AbstractBasePresenter<AlbumSelectPhotoPage> {
    public cah a;

    public bvi(AlbumSelectPhotoPage albumSelectPhotoPage) {
        super(albumSelectPhotoPage);
    }

    public final void onStart() {
        super.onStart();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((AlbumSelectPhotoPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        ((AlbumSelectPhotoPage) this.mPage).a.doCloseAnim();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (-1 == i2) {
            switch (i) {
                case 4:
                case 5:
                case 6:
                    ((AlbumSelectPhotoPage) this.mPage).finish();
                    bvj.a((AbstractBasePage) this.mPage, ((AlbumSelectPhotoPage) this.mPage).b, intent, i, this.a);
                    return;
                case 7:
                    a(intent);
                    return;
                default:
                    return;
            }
        } else if (((AlbumSelectPhotoPage) this.mPage).hasViewLayer()) {
            ((AlbumSelectPhotoPage) this.mPage).dismissAllViewLayers();
        } else {
            ((AlbumSelectPhotoPage) this.mPage).finish();
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (ResultType.OK == resultType) {
            switch (i) {
                case 20482:
                    ((AlbumSelectPhotoPage) this.mPage).setResult(ResultType.OK, pageBundle);
                    ((AlbumSelectPhotoPage) this.mPage).finish();
                    return;
                case 20484:
                case 20485:
                    ((AlbumSelectPhotoPage) this.mPage).setResult(ResultType.OK, pageBundle);
                    if (pageBundle != null && pageBundle.getBoolean("PHOTO_FRAGMENT_CLOSE")) {
                        ((AlbumSelectPhotoPage) this.mPage).finish();
                        return;
                    }
                case 20486:
                    ((AlbumSelectPhotoPage) this.mPage).finish();
                    List arrayList = new ArrayList();
                    if (pageBundle != null) {
                        Object obj = pageBundle.get("photo_select_list");
                        if (obj != null) {
                            if (obj instanceof Intent) {
                                a((Intent) pageBundle.get("photo_select_list"));
                                return;
                            }
                            arrayList = (List) pageBundle.get("photo_select_list");
                        }
                    }
                    ((AlbumSelectPhotoPage) this.mPage).a(arrayList);
                    break;
            }
        }
    }

    private void a(Intent intent) {
        ((AlbumSelectPhotoPage) this.mPage).finish();
        ArrayList arrayList = new ArrayList(1);
        String picturePathByURI = CameraInterface.getPicturePathByURI(intent.getData());
        if (!TextUtils.isEmpty(picturePathByURI)) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.b = picturePathByURI;
            imageInfo.n = intent.getIntExtra(CameraControllerManager.RESULT_KEY_COMPRESSED_PICTURE_WIDTH, -1);
            imageInfo.o = intent.getIntExtra(CameraControllerManager.RESULT_KEY_COMPRESSED_PICTURE_HEIGHT, -1);
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            imageInfo.i = String.valueOf(latestLocation.getLongitude());
            imageInfo.j = String.valueOf(latestLocation.getLatitude());
            arrayList.add(imageInfo);
        }
        ((AlbumSelectPhotoPage) this.mPage).a(arrayList);
    }
}
