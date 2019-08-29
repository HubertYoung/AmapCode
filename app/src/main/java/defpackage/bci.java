package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.common.model.POI;
import java.util.List;

/* renamed from: bci reason: default package */
/* compiled from: ISearchCQDetailService */
public interface bci extends esc {
    void a(@NonNull bid bid, @NonNull cdy cdy);

    void a(@NonNull bid bid, @NonNull POI poi);

    void a(@NonNull bid bid, @NonNull POI poi, List<Long> list, boolean z);

    boolean a(@NonNull bid bid);

    boolean a(String str);
}
