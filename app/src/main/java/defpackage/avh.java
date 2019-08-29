package defpackage;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import org.json.JSONObject;

/* renamed from: avh reason: default package */
/* compiled from: IFootNaviDataUtil */
public interface avh {
    IRouteResultData a(byte[] bArr, POI poi, POI poi2);

    @Deprecated
    Object a(String str);

    @Deprecated
    Object a(JSONObject jSONObject, POI poi, POI poi2, int i);

    @Deprecated
    String a(Object obj);
}
