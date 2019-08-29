package defpackage;

import android.graphics.drawable.Drawable;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import java.util.HashSet;
import java.util.Iterator;

/* renamed from: erh reason: default package */
/* compiled from: LoadingLayoutProxy */
public final class erh implements ere {
    private final HashSet<LoadingLayout> a = new HashSet<>();

    public final void a(LoadingLayout loadingLayout) {
        if (loadingLayout != null) {
            this.a.add(loadingLayout);
        }
    }

    public final void setLastUpdatedLabel(CharSequence charSequence) {
        Iterator<LoadingLayout> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().setLastUpdatedLabel(charSequence);
        }
    }

    public final void setLoadingDrawable(Drawable drawable) {
        Iterator<LoadingLayout> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().setLoadingDrawable(drawable);
        }
    }

    public final void setRefreshingLabel(CharSequence charSequence) {
        Iterator<LoadingLayout> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().setRefreshingLabel(charSequence);
        }
    }

    public final void setPullLabel(CharSequence charSequence) {
        Iterator<LoadingLayout> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().setPullLabel(charSequence);
        }
    }

    public final void setReleaseLabel(CharSequence charSequence) {
        Iterator<LoadingLayout> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().setReleaseLabel(charSequence);
        }
    }
}
