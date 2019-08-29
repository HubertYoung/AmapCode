package defpackage;

import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.model.IOrderSearchResult;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: eiy reason: default package */
/* compiled from: AosBaseOrderListResponser */
public abstract class eiy extends ejc {
    protected IOrderSearchResult a;

    /* access modifiers changed from: protected */
    public abstract IOrderSearchResult a();

    public eiy(int i) {
        super(i);
        this.a = null;
        this.a = a();
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        if (this.a != null) {
            JSONObject parseHeader = super.parseHeader(bArr);
            if (this.errorCode == 1) {
                if (1 == this.b) {
                    this.a.resetAll();
                }
                this.a.setPage(this.b);
                this.a.parse(parseHeader);
            }
        }
    }

    public String getErrorDesc(int i) {
        if (i == 7) {
            this.errorMessage = "";
        } else if (i == 14) {
            this.errorMessage = eay.a(R.string.login_alert);
        } else if (i == 17) {
            this.errorMessage = eay.a(R.string.verify_error);
        } else if (i != 92) {
            switch (i) {
                case 1:
                    this.errorMessage = "成功";
                    break;
                case 2:
                    this.errorMessage = "访问失败";
                    break;
                case 3:
                    this.errorMessage = "参数有误";
                    break;
                case 4:
                    this.errorMessage = "签名错误";
                    break;
                case 5:
                    this.errorMessage = eay.a(R.string.login_again);
                    break;
                default:
                    this.errorMessage = "未知错误";
                    break;
            }
        } else {
            this.errorMessage = eay.a(R.string.login_again);
        }
        return this.errorMessage;
    }

    public final IOrderSearchResult c() {
        return this.a;
    }
}
