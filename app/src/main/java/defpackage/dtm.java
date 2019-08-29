package defpackage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.photograph.page.PickPhotoBasePage;
import java.io.File;

/* renamed from: dtm reason: default package */
/* compiled from: PickPhotoBasePresenter */
public class dtm extends AbstractBasePresenter<PickPhotoBasePage> {
    public dtm(PickPhotoBasePage pickPhotoBasePage) {
        super(pickPhotoBasePage);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        PickPhotoBasePage pickPhotoBasePage = (PickPhotoBasePage) this.mPage;
        if (i2 != -1) {
            pickPhotoBasePage.d();
            return;
        }
        switch (i) {
            case 12321:
                try {
                    Uri data = intent.getData();
                    if (data != null) {
                        String a = dto.a(AMapPageUtil.getAppContext(), data);
                        if (TextUtils.isEmpty(a)) {
                            AMapLog.w("PickPhotoBasePage", "getImagePath returns NULL. Stop camera action.");
                            if (VERSION.SDK_INT >= 19) {
                                Bitmap a2 = PickPhotoBasePage.a(pickPhotoBasePage.getContext(), data);
                                if (a2 != null && !a2.isRecycled()) {
                                    a = PickPhotoBasePage.a(a2);
                                }
                            }
                        }
                        if (pickPhotoBasePage.g) {
                            pickPhotoBasePage.b(new File(a));
                            return;
                        }
                        pickPhotoBasePage.h = a;
                        pickPhotoBasePage.a(pickPhotoBasePage.h);
                        return;
                    }
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = (Bitmap) extras.getParcelable("data");
                        if (bitmap != null) {
                            String a3 = PickPhotoBasePage.a(bitmap);
                            if (pickPhotoBasePage.g) {
                                pickPhotoBasePage.b(new File(a3));
                                return;
                            }
                            pickPhotoBasePage.h = a3;
                            pickPhotoBasePage.a(pickPhotoBasePage.h);
                            return;
                        }
                        pickPhotoBasePage.h = null;
                        pickPhotoBasePage.a((String) null);
                        return;
                    }
                    return;
                } catch (Exception unused) {
                    ToastHelper.showToast(pickPhotoBasePage.getResources().getString(R.string.gallay_error));
                    return;
                }
            case 12322:
                Bitmap bitmap2 = (Bitmap) intent.getParcelableExtra("data");
                if (bitmap2 != null) {
                    pickPhotoBasePage.h = PickPhotoBasePage.a(bitmap2);
                } else {
                    pickPhotoBasePage.h = PickPhotoBasePage.d;
                }
                pickPhotoBasePage.a(pickPhotoBasePage.h);
                break;
            case 12323:
                if (pickPhotoBasePage.g) {
                    pickPhotoBasePage.b(pickPhotoBasePage.e);
                    return;
                }
                if (pickPhotoBasePage.e != null) {
                    pickPhotoBasePage.h = pickPhotoBasePage.e.getAbsolutePath();
                }
                pickPhotoBasePage.a(pickPhotoBasePage.h);
                return;
        }
    }

    public ON_BACK_TYPE onBackPressed() {
        ((PickPhotoBasePage) this.mPage).d();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }
}
