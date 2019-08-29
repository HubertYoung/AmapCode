package defpackage;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.bundle.activities.page.LookOverPicturePage;
import com.autonavi.minimap.bundle.featureguide.widget.SplashyFragment;

/* renamed from: cud reason: default package */
/* compiled from: LookOverPicturePresenter */
public final class cud extends AbstractBasePresenter<LookOverPicturePage> {
    public cud(LookOverPicturePage lookOverPicturePage) {
        super(lookOverPicturePage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        String string = ((LookOverPicturePage) this.mPage).getArguments().getString(SplashyFragment.INTENT_photoPath);
        if (!TextUtils.isEmpty(string)) {
            try {
                try {
                    Bitmap a = kp.a(string, ahc.b(string), ags.a(((LookOverPicturePage) this.mPage).getContext()).width() * 2, ags.a(((LookOverPicturePage) this.mPage).getContext()).height() * 2);
                    if (a != null) {
                        LookOverPicturePage lookOverPicturePage = (LookOverPicturePage) this.mPage;
                        if (a != null) {
                            lookOverPicturePage.a.setImageBitmap(a);
                        }
                    }
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
    }
}
