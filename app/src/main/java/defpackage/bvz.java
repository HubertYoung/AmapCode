package defpackage;

import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.json.JsonUtil;
import org.json.JSONObject;

/* renamed from: bvz reason: default package */
/* compiled from: JsonResultParser */
public final class bvz<T> extends AbstractAOSParser {
    public T a;
    private Class<T> b;

    public bvz(Class<T> cls) {
        this.b = cls;
    }

    public final void parser(byte[] bArr) {
        JSONObject parseHeader = super.parseHeader(bArr);
        if (this.errorCode == 1) {
            try {
                DebugLog.debug("网络请求返回:".concat(String.valueOf(parseHeader)));
                this.a = JsonUtil.fromJson((Object) parseHeader, this.b);
            } catch (Exception e) {
                e.printStackTrace();
                this.result = false;
                this.errorCode = -2;
                StringBuilder sb = new StringBuilder();
                sb.append(PARSE_ERROR);
                sb.append(":");
                sb.append(e.getMessage());
                this.errorMessage = sb.toString();
            }
        }
    }

    public final String getErrorDesc(int i) {
        return this.errorMessage;
    }
}
