package defpackage;

import com.autonavi.io.monitor.annotation.AccessMode;
import java.util.Comparator;

/* renamed from: bqv reason: default package */
/* compiled from: AccessModeComparator */
public final class bqv implements Comparator<AccessMode> {
    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((AccessMode) obj).ordinal() - ((AccessMode) obj2).ordinal();
    }
}
