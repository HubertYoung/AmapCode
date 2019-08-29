package defpackage;

import android.support.annotation.Nullable;
import android.view.View;
import com.autonavi.bundle.uitemplate.container.SlideContainer.b;
import com.autonavi.map.core.view.MapLayerView;
import java.lang.ref.WeakReference;

/* renamed from: aqe reason: default package */
/* compiled from: MainMapContainerService */
public final class aqe implements czh {
    public WeakReference<arn> a;
    private MapLayerView b;

    public aqe(arn arn) {
        this.a = new WeakReference<>(arn);
    }

    public final void a() {
        this.b = null;
    }

    @Nullable
    public final View b() {
        arn arn = (arn) this.a.get();
        if (arn == null) {
            return null;
        }
        return arn.getContentView();
    }

    public final boolean a(b bVar) {
        arn arn = (arn) this.a.get();
        if (arn != null) {
            return arn.b(bVar);
        }
        return false;
    }
}
