package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.KeyValueStorage;
import com.autonavi.common.KeyValueStorage.WebStorage;

/* renamed from: bic reason: default package */
/* compiled from: AMapStorageUtil */
public final class bic {
    public static <T extends KeyValueStorage<T>> T a(Class<T> cls) {
        return bkl.a.b(cls, AMapAppGlobal.getApplication());
    }

    public static WebStorage a(String str) {
        bkl bkl = bkl.a;
        AMapAppGlobal.getApplication();
        return bkl.a(str);
    }
}
