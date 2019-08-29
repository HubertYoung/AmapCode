package defpackage;

import com.amap.bundle.network.response.exception.ServerException;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import org.json.JSONObject;

/* renamed from: cfu reason: default package */
/* compiled from: AosWalletParser */
public final class cfu extends cfs {
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;

    public final void parser(byte[] bArr) {
        JSONObject parseHeader = super.parseHeader(bArr);
        if (this.errorCode == 1) {
            this.i = parseHeader.optString("words");
            this.j = parseHeader.optString("note");
            JSONObject optJSONObject = parseHeader.optJSONObject("wallet");
            if (optJSONObject != null) {
                this.b = optJSONObject.optString("available");
                this.c = optJSONObject.optString("checking");
                this.d = optJSONObject.optString("cashouting");
                this.e = optJSONObject.optString("success");
                this.f = optJSONObject.optString("freeze");
                this.g = optJSONObject.optString("failure");
                this.h = optJSONObject.optString("total");
            }
            return;
        }
        a(new ServerException(this.errorCode, this.errorMessage));
    }

    public final String getErrorDesc(int i2) {
        if (i2 == 0) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.query_fail);
        } else if (i2 == 14) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_relogin);
        } else if (i2 == 24) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.taobao_relogin);
        } else if (i2 != 59) {
            switch (i2) {
                case 3:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.query_fail);
                    break;
                case 4:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.query_fail);
                    break;
                case 5:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.query_fail);
                    break;
            }
        } else {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.exception_wallet_taobao_unbind);
        }
        return this.errorMessage;
    }
}
