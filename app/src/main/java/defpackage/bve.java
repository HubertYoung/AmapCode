package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.search.album.page.AlbumMainPage.b;
import com.autonavi.map.search.photoupload.entity.ImageInfo;
import com.autonavi.minimap.R;
import java.util.List;

/* renamed from: bve reason: default package */
/* compiled from: AlbumManager */
public final class bve {
    public static void a(AbstractBasePage abstractBasePage, List<ImageInfo> list, String str, List<ImageInfo> list2, int i, int i2, cah cah) {
        if (abstractBasePage != null && list != null && !list.isEmpty()) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("ALBUM_IMAGE_LIST", list);
            b bVar = new b();
            bVar.d = false;
            bVar.b = abstractBasePage.getString(R.string.sure);
            bVar.g = i2;
            bVar.a = str;
            pageBundle.putObject("ALBUM_FRAGMENT_STYLE", bVar);
            if (list2 != null) {
                pageBundle.putObject("SELECT_DATA_LIST", list2);
            }
            pageBundle.putObject("album_bundle_builder", cah);
            abstractBasePage.startPageForResult((String) "amap.album.action.AlbumMainPage", pageBundle, i);
        }
    }
}
