package defpackage;

import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.autonavi.minimap.R;

/* renamed from: arb reason: default package */
/* compiled from: BaseGuideComponent */
public abstract class arb implements asf {
    /* access modifiers changed from: 0000 */
    @StringRes
    public abstract int a();

    public final int b() {
        return 2;
    }

    public final View a(LayoutInflater layoutInflater) {
        View inflate = layoutInflater.inflate(R.layout.common_guide, null);
        ((TextView) inflate.findViewById(R.id.content)).setText(a());
        return inflate;
    }
}
