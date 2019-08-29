package defpackage;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;
import java.util.ArrayList;

/* renamed from: ms reason: default package */
/* compiled from: IDriveNaviService */
public interface ms extends esc {

    /* renamed from: ms$a */
    /* compiled from: IDriveNaviService */
    public static class a implements b {
        public void a() {
        }

        public void a(boolean z) {
        }
    }

    /* renamed from: ms$b */
    /* compiled from: IDriveNaviService */
    public interface b {
        void a(boolean z);
    }

    AosRequest a(Callback<tc> callback, ta... taVarArr);

    CharSequence a(@NonNull Context context, int i, int i2);

    void a();

    void a(Activity activity, POI poi, ArrayList<POI> arrayList, POI poi2, int i, int i2, int i3, String str);

    void a(Activity activity, String str, b bVar);

    void a(boolean z);

    void b();
}
