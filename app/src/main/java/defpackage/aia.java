package defpackage;

import android.util.Pair;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import java.util.List;
import org.json.JSONObject;

/* renamed from: aia reason: default package */
/* compiled from: IVoiceService */
public interface aia extends esc {
    JSONObject a(String str, int i, JSONObject jSONObject);

    void a();

    void a(int i, int i2, Pair<String, Object> pair);

    void a(int i, int i2, List<Pair<String, Object>> list);

    void a(aih aih);

    boolean a(IAjxContext iAjxContext, String str, int i);

    boolean a(IAjxContext iAjxContext, String str, int i, Pair<String, Object> pair);

    boolean a(IAjxContext iAjxContext, String str, int i, List<Pair<String, Object>> list);

    boolean a(String str, int i);

    void b();
}
