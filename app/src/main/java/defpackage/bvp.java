package defpackage;

import android.content.Intent;
import android.os.Handler;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.widget.ListDialog;
import java.util.ArrayList;

/* renamed from: bvp reason: default package */
/* compiled from: IPoiSearcherCallback */
public interface bvp {
    ListDialog a(ArrayList<String> arrayList, String str);

    void a();

    void a(int i, int i2);

    void a(Intent intent);

    void a(Class<? extends AbstractBasePage> cls, PageBundle pageBundle);

    void a(String str);

    void a(ArrayList<POI> arrayList, String str, Handler handler, int i, int i2, InfoliteResult infoliteResult);

    void b(Class<? extends AbstractBasePage> cls, PageBundle pageBundle);

    void b(String str);
}
