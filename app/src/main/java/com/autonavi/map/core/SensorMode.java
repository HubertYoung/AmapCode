package com.autonavi.map.core;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class SensorMode {
    public static final int SCENE_ALL = 3;
    public static final int SCENE_CANCEL = 4;
    public static final int SCENE_IGNORE = 0;
    public static final int SCENE_ORI_ONLY = 1;
    public static final int SENE_CRUISE = 2;

    public interface a {
    }

    public interface b {
    }

    public interface c {
    }

    public interface d {
    }

    public static void design(Object obj) {
        Class cls;
        if (obj != null && bid.class.isInstance(obj) && !Transparent.class.isInstance(obj) && !c.class.isInstance(obj)) {
            if (!c.class.isInstance(obj)) {
                if (d.class.isInstance(obj)) {
                    cls = d.class;
                    epp.a().a(1);
                } else if (b.class.isInstance(obj)) {
                    cls = b.class;
                    epp.a().a(2);
                } else if (a.class.isInstance(obj)) {
                    cls = a.class;
                    epp.a().a(3);
                }
                StringBuilder sb = new StringBuilder("page: ");
                sb.append(obj.getClass().getSimpleName());
                sb.append(", locationMode: ");
                sb.append(cls.getSimpleName());
                AMapLog.d("LocationMode", sb.toString());
            }
            cls = c.class;
            epp.a().a(0);
            StringBuilder sb2 = new StringBuilder("page: ");
            sb2.append(obj.getClass().getSimpleName());
            sb2.append(", locationMode: ");
            sb2.append(cls.getSimpleName());
            AMapLog.d("LocationMode", sb2.toString());
        }
    }
}
