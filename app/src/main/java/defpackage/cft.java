package defpackage;

import com.amap.bundle.network.response.exception.ServerException;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import org.json.JSONObject;

/* renamed from: cft reason: default package */
/* compiled from: AosWalletGetAccountParser */
public final class cft extends cfs {
    public cfo b;

    public final void parser(byte[] bArr) {
        JSONObject parseHeader = super.parseHeader(bArr);
        if (this.errorCode == 1) {
            this.b = new cfo();
            this.b.c = parseHeader.optString("alipay_account");
            this.b.a = parseHeader.optString("alipay_name");
            this.b.b = Boolean.valueOf(parseHeader.optBoolean("result"));
            this.b.d = Boolean.valueOf(parseHeader.optBoolean("certified"));
            this.errorMessage = parseHeader.optString("show_msg");
            return;
        }
        a(new ServerException(this.errorCode, this.errorMessage));
    }

    public final String getErrorDesc(int i) {
        if (i == 0) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_repuest_fail);
        } else if (i == 14) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_relogin);
        } else if (i == 24) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.authorization_expires_desc2);
        } else if (i == 59) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_taobao_unbind2);
        } else if (i == 61) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_repuest_fail);
        } else if (i != 130) {
            switch (i) {
                case 2:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_repuest_fail);
                    break;
                case 3:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_repuest_fail);
                    break;
                case 4:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_repuest_fail);
                    break;
            }
        } else {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_repuest_fail);
        }
        return this.errorMessage;
    }
}
