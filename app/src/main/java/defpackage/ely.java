package defpackage;

import android.view.View;
import com.autonavi.common.model.POI;

/* renamed from: ely reason: default package */
/* compiled from: IPoiTipView */
public interface ely<T> {

    /* renamed from: ely$a */
    /* compiled from: IPoiTipView */
    public interface a {
        boolean a(View view);

        boolean a(POI poi);

        boolean b(POI poi);

        boolean c(POI poi);

        boolean d(POI poi);

        boolean e(POI poi);
    }

    void adjustMargin();

    POI getPoi();

    View getView();

    void initData(T t, POI poi, int i);

    void refreshByScreenState(boolean z);

    void setFromSource(String str);

    void setSingle(boolean z);

    void setTipItemEvent(a aVar);
}
