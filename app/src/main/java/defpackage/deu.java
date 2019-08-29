package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import org.json.JSONException;

/* renamed from: deu reason: default package */
/* compiled from: AosSnsBatchParser */
public final class deu extends AbstractAOSParser {
    public int a = 0;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) {
        parseHeader(bArr);
        if (this.mDataObject != null && this.mDataObject.has("failed_num")) {
            try {
                this.a = this.mDataObject.getInt("failed_num");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
