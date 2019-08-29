package defpackage;

import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.photograph.api.IOpenPage;
import com.autonavi.minimap.photograph.api.IOpenPage.PhotoSelectOptions;
import com.autonavi.minimap.photograph.page.PickPhotoPage;

/* renamed from: dtj reason: default package */
/* compiled from: OpenPageImpl */
public final class dtj implements IOpenPage {

    /* renamed from: dtj$a */
    /* compiled from: OpenPageImpl */
    static class a {
        static dtj a = new dtj(0);
    }

    /* synthetic */ dtj(byte b) {
        this();
    }

    private dtj() {
    }

    public final void a(bid bid, String str, PhotoSelectOptions photoSelectOptions) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean(ImageEditService.IN_EDIT_TYPE_CROP, true);
        pageBundle.putString("title", str);
        pageBundle.putObject("option", photoSelectOptions);
        bid.startPageForResult(PickPhotoPage.class, pageBundle, 2000);
    }

    public final void a(bid bid, com.autonavi.minimap.photograph.api.IOpenPage.a aVar) {
        PageBundle pageBundle = new PageBundle();
        if (aVar.b != null) {
            pageBundle.putBoolean(ImageEditService.IN_EDIT_TYPE_CROP, aVar.b.booleanValue());
        }
        if (aVar.c != null) {
            pageBundle.putInt("cropSize", aVar.c.intValue());
        }
        if (aVar.a != null) {
            pageBundle.putString("title", aVar.a);
        }
        pageBundle.putObject("option", aVar.d);
        bid.startPageForResult(PickPhotoPage.class, pageBundle, 100);
    }
}
