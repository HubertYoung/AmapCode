package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: lr reason: default package */
/* compiled from: AppInitConfigResponserBase */
public abstract class lr extends AbstractAOSParser {
    /* access modifiers changed from: protected */
    public abstract void a(JSONObject jSONObject, boolean z);

    public final boolean a(String str, AtomicReference<JSONObject> atomicReference, Boolean bool) {
        if (str != null) {
            return a(str.getBytes(), atomicReference, bool.booleanValue());
        }
        new Object[1][0] = getClass().getSimpleName();
        lt.b();
        return false;
    }

    private boolean a(byte[] bArr, AtomicReference<JSONObject> atomicReference, boolean z) {
        if (bArr.length == 0) {
            new Object[1][0] = getClass().getSimpleName();
            lt.b();
            return false;
        }
        JSONObject parseHeader = parseHeader(bArr);
        Object[] objArr = {getClass().getSimpleName(), parseHeader};
        lt.b();
        JSONObject optJSONObject = parseHeader.optJSONObject("datas");
        if (atomicReference != null) {
            atomicReference.set(optJSONObject);
        }
        Object[] objArr2 = {getClass().getSimpleName(), optJSONObject};
        lt.b();
        if (optJSONObject == null || !this.result) {
            Object[] objArr3 = {getClass().getSimpleName(), optJSONObject, Boolean.valueOf(this.result)};
            lt.b();
            return false;
        }
        try {
            a(optJSONObject, z);
            return true;
        } catch (Throwable th) {
            Object[] objArr4 = {getClass().getSimpleName(), th};
            lt.b();
            return false;
        }
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        a(bArr, (AtomicReference<JSONObject>) null, false);
    }

    public String getErrorDesc(int i) {
        switch (i) {
            case 0:
                return BASE_ERROR;
            case 1:
                return "";
            case 3:
                return "参数有误";
            case 4:
                return "访问失败，请重试";
            case 5:
                return "授权过期，请重试";
            default:
                return BASE_ERROR;
        }
    }
}
