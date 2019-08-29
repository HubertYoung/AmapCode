package defpackage;

import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;

@Router({"pay"})
/* renamed from: abp reason: default package */
/* compiled from: PayRouter */
public class abp extends esk {
    public boolean start(ese ese) {
        if (!bno.a) {
            return false;
        }
        startPage((String) "com.amap.bundle.pay.TestWechatPayPage", new PageBundle());
        return true;
    }
}
