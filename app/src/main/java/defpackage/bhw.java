package defpackage;

import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.autonavi.carowner.roadcamera.page.RdCameraPaymentTypeFragment;
import com.autonavi.common.PageBundle;
import java.util.List;

/* renamed from: bhw reason: default package */
/* compiled from: RdCameraPaymentTypePresenter */
public final class bhw extends sw<RdCameraPaymentTypeFragment, bhr> {
    public bhw(RdCameraPaymentTypeFragment rdCameraPaymentTypeFragment) {
        super(rdCameraPaymentTypeFragment);
    }

    public final void onPageCreated() {
        RdCameraPaymentTypeFragment rdCameraPaymentTypeFragment = (RdCameraPaymentTypeFragment) this.mPage;
        PageBundle arguments = rdCameraPaymentTypeFragment.getArguments();
        if (arguments == null || (arguments.containsKey("rd_camera_payment_type_list_key") && arguments.containsKey("rd_camera_payment_data_key"))) {
            if (arguments != null && arguments.containsKey("bundle_key_h5_page_param")) {
                rdCameraPaymentTypeFragment.d = (bhs) arguments.getObject("bundle_key_h5_page_param");
            }
            rdCameraPaymentTypeFragment.b = (List) arguments.getObject("rd_camera_payment_type_list_key");
            rdCameraPaymentTypeFragment.a = (RdCameraPaymentItem) arguments.getObject("rd_camera_payment_data_key");
            rdCameraPaymentTypeFragment.c = rdCameraPaymentTypeFragment.a.getDateDesc();
            ((RdCameraPaymentTypeFragment) this.mPage).a();
            return;
        }
        throw new IllegalArgumentException("The arguments is incomplete.");
    }

    public final /* synthetic */ su a() {
        return new bhr(this);
    }
}
