package defpackage;

import com.autonavi.minimap.agroup.entity.MemberInfo;
import java.util.Comparator;

/* renamed from: cjv reason: default package */
/* compiled from: MemInfoComparator */
public final class cjv implements Comparator<MemberInfo> {
    public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
        int i = (((MemberInfo) obj).joinTime > ((MemberInfo) obj2).joinTime ? 1 : (((MemberInfo) obj).joinTime == ((MemberInfo) obj2).joinTime ? 0 : -1));
        if (i < 0) {
            return -1;
        }
        return i == 0 ? 0 : 1;
    }
}
