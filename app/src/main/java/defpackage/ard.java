package defpackage;

import android.view.LayoutInflater;
import android.view.View;
import com.airbnb.lottie.LottieAnimationView;
import com.autonavi.minimap.R;

/* renamed from: ard reason: default package */
/* compiled from: SlideMoreComponent */
public final class ard implements asf {
    LottieAnimationView a;

    public final int b() {
        return -12;
    }

    public final View a(LayoutInflater layoutInflater) {
        View inflate = layoutInflater.inflate(R.layout.slide_more_guide, null);
        this.a = (LottieAnimationView) inflate.findViewById(R.id.guide_lottie);
        return inflate;
    }
}
