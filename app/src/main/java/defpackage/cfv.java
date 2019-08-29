package defpackage;

import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.amap.bundle.network.response.exception.ServerException;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.taobao.wireless.security.sdk.indiekit.IndieKitDefine;
import org.json.JSONObject;

/* renamed from: cfv reason: default package */
/* compiled from: AosWalletWithdrawParser */
public final class cfv extends cfs {
    protected cfq b;

    public final void parser(byte[] bArr) {
        JSONObject parseHeader = super.parseHeader(bArr);
        if (this.errorCode == 1) {
            this.b = new cfq();
            JSONObject optJSONObject = parseHeader.optJSONObject("cashoutrequest");
            if (optJSONObject != null) {
                this.b.a = optJSONObject.optString(BioDetector.EXT_KEY_AMOUNT);
                this.b.b = optJSONObject.optString(IndieKitDefine.SG_KEY_INDIE_KIT_USERNAME);
                this.b.c = optJSONObject.optString("alipay_account");
                this.b.d = optJSONObject.optInt("status");
            }
            return;
        }
        a(new ServerException(this.errorCode, this.errorMessage));
    }

    public final String getErrorDesc(int i) {
        if (i == 0) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.withdraw_fail_retry);
        } else if (i == 14) {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.wallet_relogin);
        } else if (i != 144) {
            switch (i) {
                case 2:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.withdraw_fail_retry);
                    break;
                case 3:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.withdraw_fail_retry);
                    break;
                case 4:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.withdraw_fail_retry);
                    break;
                case 5:
                    this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.authorization_expires_desc2);
                    break;
                default:
                    switch (i) {
                        case 122:
                            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.exception_organizing_taobao_data);
                            break;
                        case 123:
                            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.exception_alipay_certification);
                            break;
                        case SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA /*124*/:
                            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.withdraw_fail_retry);
                            break;
                    }
            }
        } else {
            this.errorMessage = AMapAppGlobal.getApplication().getString(R.string.exception_withdraw_reach_limit);
        }
        return this.errorMessage;
    }
}
