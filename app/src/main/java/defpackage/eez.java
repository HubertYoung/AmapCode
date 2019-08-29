package defpackage;

import android.view.View;
import com.autonavi.bundle.routecommon.inter.IRouteUI.ContainerType;
import java.util.List;

/* renamed from: eez reason: default package */
/* compiled from: RouteFlowViewUtil */
public final class eez {
    private static eez e;
    public List<View> a;
    public List<View> b;
    public List<View> c;
    public ContainerType[] d;

    public static synchronized eez a() {
        eez eez;
        synchronized (eez.class) {
            try {
                if (e == null) {
                    e = new eez();
                }
                eez = e;
            }
        }
        return eez;
    }

    public final void a(ContainerType[] containerTypeArr) {
        if (containerTypeArr != null && this.d != null && containerTypeArr.length == this.d.length && this.d.length != 0) {
            for (int length = containerTypeArr.length - 1; length >= 0; length--) {
                a(a(containerTypeArr[length]));
            }
            this.d = containerTypeArr;
        }
    }

    private List<View> a(ContainerType containerType) {
        switch (containerType) {
            case HEAD_VIEW:
                return this.a;
            case FLOW_VIEW:
                return this.b;
            case CONTAINER_VIEW:
                return this.c;
            default:
                return null;
        }
    }

    private static void a(List<View> list) {
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                View view = list.get(i);
                if (view != null) {
                    view.bringToFront();
                }
            }
        }
    }
}
