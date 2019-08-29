package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import org.json.JSONObject;

/* renamed from: aqs reason: default package */
/* compiled from: LuBanHotWordParser */
public final class aqs extends AbstractAOSParser {
    public aqt a = new aqt();

    public final void parser(byte[] bArr) {
        try {
            this.mDataObject = new JSONObject(new String(bArr, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.mDataObject != null) {
            this.a.a(this.mDataObject);
        }
    }

    public final String getErrorDesc(int i) {
        return this.errorMessage;
    }
}
