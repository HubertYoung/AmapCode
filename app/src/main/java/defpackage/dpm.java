package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;

/* renamed from: dpm reason: default package */
/* compiled from: AosBaseOrderListResponser */
public abstract class dpm extends dpp {
    protected IOrderSearchResult i;

    /* access modifiers changed from: protected */
    public abstract IOrderSearchResult b();

    public dpm(int i2) {
        super(i2);
        this.i = null;
        this.i = b();
    }

    public final void a(byte[] bArr) {
        if (this.i != null) {
            super.a(bArr);
            if (this.c == 1) {
                if (1 == this.j) {
                    this.i.resetAll();
                }
                this.i.setPage(this.j);
                this.i.parse(this.h);
            }
        }
    }

    public final String a() {
        int i2 = this.c;
        if (i2 == 7) {
            this.d = AMapAppGlobal.getApplication().getString(R.string.no_more_voucher);
        } else if (i2 == 14) {
            this.d = AMapAppGlobal.getApplication().getString(R.string.login_alert);
        } else if (i2 == 17) {
            this.d = AMapAppGlobal.getApplication().getString(R.string.verify_error);
        } else if (i2 != 92) {
            switch (i2) {
                case 1:
                    this.d = "成功";
                    break;
                case 2:
                    this.d = "访问失败";
                    break;
                case 3:
                    this.d = "参数有误";
                    break;
                case 4:
                    this.d = "签名错误";
                    break;
                case 5:
                    this.d = AMapAppGlobal.getApplication().getString(R.string.login_again);
                    break;
                default:
                    this.d = "未知错误";
                    break;
            }
        } else {
            this.d = AMapAppGlobal.getApplication().getString(R.string.login_again);
        }
        return this.d;
    }

    public final IOrderSearchResult c() {
        return this.i;
    }
}
