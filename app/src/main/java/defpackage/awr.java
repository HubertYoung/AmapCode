package defpackage;

import android.support.annotation.IntRange;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.photoUpload.impl.PhotoUploadExporter$1;
import com.autonavi.common.PageBundle;
import com.autonavi.map.search.photo.page.PoiPhotoSuccessPage;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import java.util.List;

@BundleInterface(caf.class)
/* renamed from: awr reason: default package */
/* compiled from: PhotoUploadExporter */
public class awr implements caf {
    private List<ImageInfo> a = new PhotoUploadExporter$1(this);

    public final void a(bid bid, boolean z, @IntRange(from = 1, to = 6) int i, a aVar) {
        if (bid == null) {
            StringBuilder sb = new StringBuilder("invalid param,pageContext:");
            sb.append(bid);
            sb.append(",callback:");
            sb.append(aVar);
            AMapLog.error("paas.photoupload", "PhotoUploadExporter", sb.toString());
            return;
        }
        cah cah = new cah();
        cah.g = 7;
        cah.e = z;
        cah.f = i;
        cah.b().a().d = "amap.search.action.photo";
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("album_bundle_builder", cah);
        pageBundle.putObject("callback", aVar);
        bid.startPageForResult((String) "amap.album.action.AlbumSelectPhotoPage", pageBundle, 20486);
    }

    public final void a(bid bid, String str) {
        if (!TextUtils.isEmpty(str)) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", str);
            bid.startPage(PoiPhotoSuccessPage.class, pageBundle);
        }
    }

    public final void a(bid bid) {
        if (bid != null) {
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
            cah.b().a().d = "amap.search.action.photo";
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("album_bundle_builder", cah);
            bid.startPageForResult((String) "amap.album.action.AlbumSelectPhotoPage", pageBundle, 20485);
        }
    }
}
