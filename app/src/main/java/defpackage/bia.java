package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.jni.bedstone.model.FrequentLocationDBInfo;
import java.util.List;

/* renamed from: bia reason: default package */
/* compiled from: IFrequentLocationsService */
public interface bia extends bie, esc {
    int a();

    int a(FrequentLocationDBInfo frequentLocationDBInfo);

    int a(String str);

    int a(String[] strArr);

    void a(boolean z);

    int b();

    @NonNull
    List<FrequentLocationDBInfo> b(String[] strArr);

    boolean c();

    boolean d();
}
