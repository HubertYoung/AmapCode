package defpackage;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;

/* renamed from: cyk reason: default package */
/* compiled from: FrequentAnimHelper */
final class cyk {
    View a;
    View b;

    cyk(@NonNull View view, @NonNull View view2) {
        this.a = view;
        this.b = view2;
    }

    /* access modifiers changed from: 0000 */
    public final boolean a() {
        Animation animation = this.a.getAnimation();
        Animation animation2 = this.b.getAnimation();
        return (animation != null && animation.hasStarted() && !animation.hasEnded()) || (animation2 != null && animation2.hasStarted() && !animation2.hasEnded());
    }
}
