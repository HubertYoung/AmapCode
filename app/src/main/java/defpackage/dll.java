package defpackage;

import android.app.Activity;
import android.content.Intent;
import com.autonavi.map.manger.IIntentUtil;
import com.autonavi.map.manger.MapInterfaceFactory;

/* renamed from: dll reason: default package */
/* compiled from: MapInterfaceFactoryImpl */
public final class dll extends MapInterfaceFactory {
    private dll() {
    }

    public static void a() {
        if (instance == null) {
            instance = new dll();
        }
    }

    public final IIntentUtil getIntentUtil(Activity activity, Intent intent) {
        return new dlj(activity, intent);
    }
}
