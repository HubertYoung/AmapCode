package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.amap.location.sdk.fusion.LocationManagerProxy;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: cla reason: default package */
/* compiled from: Location */
public final class cla extends cky {

    /* renamed from: cla$a */
    /* compiled from: Location */
    class a implements wo {
        private a() {
        }

        /* synthetic */ a(cla cla, byte b) {
            this();
        }

        public final Boolean a() {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null || !(pageContext.getActivity() instanceof brr)) {
                return null;
            }
            return Boolean.valueOf(!((brr) pageContext.getActivity()).i());
        }
    }

    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "Location";
    }

    public final void a(Application application) {
        LocationInstrument.getInstance().setUtils(new a(this, 0));
        LocationManagerProxy.getInstance().init(application);
    }
}
