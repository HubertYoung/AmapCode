package defpackage;

import com.autonavi.annotation.VirtualApp;
import java.util.Comparator;

/* renamed from: bqo reason: default package */
/* compiled from: BundlePriorityComparator */
public final class bqo implements Comparator<Class> {
    public final /* synthetic */ int compare(Object obj, Object obj2) {
        Class cls = (Class) obj;
        Class cls2 = (Class) obj2;
        if (cls == null || cls2 == null) {
            return 0;
        }
        VirtualApp virtualApp = (VirtualApp) cls.getAnnotation(VirtualApp.class);
        VirtualApp virtualApp2 = (VirtualApp) cls2.getAnnotation(VirtualApp.class);
        if (!(virtualApp == null || virtualApp2 == null)) {
            if (virtualApp.priority() > virtualApp2.priority()) {
                return 1;
            }
            if (virtualApp.priority() < virtualApp2.priority()) {
                return -1;
            }
        }
        return 0;
    }
}
