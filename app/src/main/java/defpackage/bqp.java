package defpackage;

import com.autonavi.annotation.MultipleImpl;
import java.util.Comparator;

/* renamed from: bqp reason: default package */
/* compiled from: ServicePriorityComparator */
public final class bqp implements Comparator<Class> {
    public final /* synthetic */ int compare(Object obj, Object obj2) {
        Class cls = (Class) obj;
        Class cls2 = (Class) obj2;
        if (cls == null || cls2 == null) {
            return 0;
        }
        MultipleImpl multipleImpl = (MultipleImpl) cls.getAnnotation(MultipleImpl.class);
        MultipleImpl multipleImpl2 = (MultipleImpl) cls2.getAnnotation(MultipleImpl.class);
        if (!(multipleImpl == null || multipleImpl2 == null)) {
            if (multipleImpl.priority() > multipleImpl2.priority()) {
                return 1;
            }
            if (multipleImpl.priority() < multipleImpl2.priority()) {
                return -1;
            }
        }
        return 0;
    }
}
