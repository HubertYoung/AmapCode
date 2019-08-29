package defpackage;

import android.content.res.Resources;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import com.alipay.sdk.util.h;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.util.HashMap;
import java.util.Map;

/* renamed from: bmb reason: default package */
/* compiled from: AlipayResult */
public final class bmb {
    private static final Map<String, String> e;
    String a = null;
    public String b = null;
    String c = null;
    public String d;

    public bmb(String str) {
        this.c = str;
        String replace = this.c.replace("{", "").replace(h.d, "");
        this.d = a(replace, "resultStatus=", ";memo");
        this.b = a(replace, "memo=", ";result").replace("。", "");
        if (TextUtils.isEmpty(this.b)) {
            if (!e.containsKey(this.d) || !TextUtils.isEmpty(this.b)) {
                this.a = AMapAppGlobal.getApplication().getString(R.string.error_other);
            } else {
                this.a = e.get(this.d);
            }
            this.b = this.a;
        }
    }

    static {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        HashMap hashMap = new HashMap();
        e = hashMap;
        hashMap.put(AlibcAlipay.PAY_SUCCESS_CODE, resources.getString(R.string.result_successful_payment));
        e.put("4000", resources.getString(R.string.result_system_error));
        e.put("4001", resources.getString(R.string.result_invalid_data_format));
        e.put("4003", resources.getString(R.string.result_bound_alipay_account_error));
        e.put(Result.PUNISH_ACCOUNT, resources.getString(R.string.result_unbound_alipay_account));
        e.put(Result.RUBBISH_ACCOUNT, resources.getString(R.string.result_fail_to_bind_account));
        e.put("4006", resources.getString(R.string.result_failed_payment));
        e.put("4010", resources.getString(R.string.result_rebind_account));
        e.put("6000", resources.getString(R.string.result_service_is_updating));
        e.put("6001", resources.getString(R.string.result_payment_cancel));
        e.put("7001", resources.getString(R.string.result_webpage_payment_failed));
    }

    private static String a(String str, String str2, String str3) {
        try {
            return str.substring(str.indexOf(str2) + str2.length(), str.indexOf(str3));
        } catch (Exception e2) {
            kf.a((Throwable) e2);
            return str;
        }
    }
}
