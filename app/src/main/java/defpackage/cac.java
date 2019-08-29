package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.search.photo.page.PhotoPage;
import com.autonavi.map.search.photo.presenter.PhotoPresenter$1;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import java.util.ArrayList;
import java.util.List;

/* renamed from: cac reason: default package */
/* compiled from: PhotoPresenter */
public final class cac extends AbstractBasePresenter<PhotoPage> {
    public List<ImageInfo> a = new PhotoPresenter$1(this);

    public cac(PhotoPage photoPage) {
        super(photoPage);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((PhotoPage) this.mPage).a(pageBundle);
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((PhotoPage) this.mPage).a();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (resultType == ResultType.OK && pageBundle != null) {
            if (i == 120) {
                pageBundle.getBoolean("PHOTO_UPLOAD_SUC");
                ((PhotoPage) this.mPage).a(can.a().a, pageBundle.getInt("PHOTO_UPLOAD_COUNT"), can.a().b, 1);
            } else if (i == 12290 || i == 12291) {
                if (pageBundle.containsKey("BUNDLE_KEY_SELECTED_IMAGE_LIST")) {
                    b((List) pageBundle.getObject("BUNDLE_KEY_SELECTED_IMAGE_LIST"));
                }
            } else {
                if (i == 150 && pageBundle.getBoolean("photo_cancel", false)) {
                    ((PhotoPage) this.mPage).a(can.a().a, 0, can.a().b, -1);
                }
            }
        }
    }

    public final void a(ImageInfo imageInfo) {
        if (!this.a.contains(imageInfo)) {
            this.a.add(imageInfo);
            ((PhotoPage) this.mPage).a(this.a);
        }
    }

    public final void a(List<ImageInfo> list) {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            ImageInfo imageInfo = this.a.get(size);
            if (!imageInfo.p) {
                this.a.remove(imageInfo);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            ImageInfo imageInfo2 = list.get(i);
            if (!this.a.contains(imageInfo2)) {
                this.a.add(imageInfo2);
            }
        }
        ((PhotoPage) this.mPage).a(this.a);
    }

    public final void b(List<ImageInfo> list) {
        this.a.clear();
        this.a.addAll(list);
        ((PhotoPage) this.mPage).a(this.a);
    }

    public final void a() {
        int i = 0;
        for (ImageInfo imageInfo : this.a) {
            if (imageInfo.p) {
                i++;
            }
        }
        cah cah = new cah();
        cah.g = 6;
        cah.e = true;
        cah.f = 6 - i;
        List<ImageInfo> list = this.a;
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (!list.get(i2).p) {
                    ImageInfo imageInfo2 = new ImageInfo();
                    imageInfo2.g = list.get(i2).g;
                    imageInfo2.b = list.get(i2).b;
                    imageInfo2.f = list.get(i2).f;
                    imageInfo2.m = list.get(i2).m;
                    imageInfo2.e = list.get(i2).e;
                    imageInfo2.a = list.get(i2).a;
                    imageInfo2.h = list.get(i2).h;
                    imageInfo2.i = list.get(i2).i;
                    imageInfo2.j = list.get(i2).j;
                    imageInfo2.d = list.get(i2).d;
                    imageInfo2.c = list.get(i2).c;
                    imageInfo2.k = list.get(i2).k;
                    imageInfo2.l = list.get(i2).l;
                    arrayList.add(imageInfo2);
                }
            }
        }
        cah.h = arrayList;
        cah.b().a().d = "amap.search.action.photo";
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("album_bundle_builder", cah);
        ((PhotoPage) this.mPage).startPageForResult((String) "amap.album.action.AlbumSelectPhotoPage", pageBundle, 20484);
    }
}
