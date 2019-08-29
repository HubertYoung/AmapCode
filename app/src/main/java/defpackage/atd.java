package defpackage;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import org.json.JSONObject;

/* renamed from: atd reason: default package */
/* compiled from: IBusSaveUtil */
public interface atd {
    IRouteResultData a(byte[] bArr, POI poi, POI poi2);

    Object a(JSONObject jSONObject);

    Object a(JSONObject jSONObject, POI poi, POI poi2);

    String a(Object obj);

    void a(bid bid, cor cor, int i);

    void a(Object obj, JSONObject jSONObject);

    Object b(JSONObject jSONObject);

    boolean b(Object obj);

    boolean c(Object obj);
}
