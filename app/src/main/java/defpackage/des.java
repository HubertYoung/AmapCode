package defpackage;

import android.content.res.Configuration;
import android.view.KeyEvent;
import com.autonavi.minimap.drive.fragment.CarPlateInputFragment;

/* renamed from: des reason: default package */
/* compiled from: CarPlateInputPresener */
public final class des extends sw<CarPlateInputFragment, der> {
    public final void onStart() {
    }

    public des(CarPlateInputFragment carPlateInputFragment) {
        super(carPlateInputFragment);
    }

    public final void onPageCreated() {
        CarPlateInputFragment carPlateInputFragment = (CarPlateInputFragment) this.mPage;
        carPlateInputFragment.c = carPlateInputFragment.getContentView();
        carPlateInputFragment.c.setOnTouchListener(carPlateInputFragment);
        carPlateInputFragment.p = false;
        carPlateInputFragment.c();
        carPlateInputFragment.h();
        carPlateInputFragment.i();
        carPlateInputFragment.o = new djt(carPlateInputFragment.getActivity());
    }

    public final void onResume() {
        super.onResume();
        CarPlateInputFragment carPlateInputFragment = (CarPlateInputFragment) this.mPage;
        if (!carPlateInputFragment.r) {
            carPlateInputFragment.a();
        }
        carPlateInputFragment.r = false;
        carPlateInputFragment.k = false;
    }

    public final void onPause() {
        super.onPause();
        CarPlateInputFragment carPlateInputFragment = (CarPlateInputFragment) this.mPage;
        StringBuilder sb = new StringBuilder();
        sb.append(carPlateInputFragment.d.getText().toString().trim());
        sb.append(carPlateInputFragment.e.getBoxResults().trim());
        carPlateInputFragment.q = sb.toString();
        carPlateInputFragment.k = carPlateInputFragment.h.isShowing();
        carPlateInputFragment.i = carPlateInputFragment.h.isShowing();
        carPlateInputFragment.f();
    }

    public final void onStop() {
        ((CarPlateInputFragment) this.mPage).j();
    }

    public final void onDestroy() {
        ((CarPlateInputFragment) this.mPage).b();
    }

    public final void onConfigurationChanged(Configuration configuration) {
        CarPlateInputFragment carPlateInputFragment = (CarPlateInputFragment) this.mPage;
        boolean z = true;
        carPlateInputFragment.r = true;
        boolean z2 = carPlateInputFragment.j;
        carPlateInputFragment.l = false;
        carPlateInputFragment.e.setOnCompleteListener(null);
        carPlateInputFragment.m = carPlateInputFragment.d.getText().toString().trim();
        if (carPlateInputFragment.n != null && carPlateInputFragment.n.length() >= 6) {
            carPlateInputFragment.e.setBoxesText(carPlateInputFragment.n);
        }
        carPlateInputFragment.n = carPlateInputFragment.e.getBoxResults().trim();
        boolean isShowing = carPlateInputFragment.h.isShowing();
        if (isShowing) {
            carPlateInputFragment.h.dismiss();
        }
        if (!carPlateInputFragment.k) {
            carPlateInputFragment.i = isShowing;
        }
        carPlateInputFragment.f();
        carPlateInputFragment.k();
        carPlateInputFragment.d();
        carPlateInputFragment.f = null;
        carPlateInputFragment.e();
        carPlateInputFragment.g = null;
        if (configuration.orientation != 2) {
            z = false;
        }
        carPlateInputFragment.a(z);
        carPlateInputFragment.s.removeCallbacksAndMessages(null);
        carPlateInputFragment.b = null;
        carPlateInputFragment.a = null;
        carPlateInputFragment.c();
        carPlateInputFragment.h();
        carPlateInputFragment.i();
        carPlateInputFragment.o = new djt(carPlateInputFragment.getActivity());
        carPlateInputFragment.a();
        if (!z2) {
            carPlateInputFragment.k();
        }
        carPlateInputFragment.r = false;
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getRepeatCount() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        ((CarPlateInputFragment) this.mPage).a(((CarPlateInputFragment) this.mPage).g());
        return true;
    }

    public final /* synthetic */ su a() {
        return new der(this);
    }
}
