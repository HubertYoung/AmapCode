package com.amap.bundle.pay.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.pay.payment.PayInfo;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import java.util.HashMap;

@PageAction("com.amap.bundle.pay.TestWechatPayPage")
public class TestWechatPayPage extends AbstractBasePage<abq> {
    /* access modifiers changed from: private */
    public String a = getClass().getSimpleName();
    private final OnClickListener b = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.pay_test_wxpay_btn) {
                TestWechatPayPage.a(TestWechatPayPage.this);
            } else if (view.getId() == R.id.pay_test_wxpay_signscore_btn) {
                TestWechatPayPage testWechatPayPage = TestWechatPayPage.this;
                StringBuilder sb = new StringBuilder();
                sb.append(System.currentTimeMillis() / 1000);
                String sb2 = sb.toString();
                HashMap hashMap = new HashMap();
                hashMap.put("mch_id", abx.d);
                hashMap.put("nonce_str", abz.a());
                hashMap.put("service_id", abx.c);
                hashMap.put("sign_type", "HMAC-SHA256");
                hashMap.put("timestamp", sb2);
                String a2 = abz.a(hashMap, abx.b);
                StringBuilder sb3 = new StringBuilder("appid=");
                sb3.append(abx.a);
                sb3.append("&");
                sb3.append(a2);
                PayInfo payInfo = new PayInfo("{\"status\":0,\"sign_params\":{\"query\":\"appid=wx9b913299215a38f2&mch_id=1525281191&nonce_str=TeuT1GwlCEiLmBXC8ykNHLglsmlTeE4n&service_id=00003000000000574055351538343872&sign_type=HMAC-SHA256&timestamp=1557115317&sign=F15BA660C5350453D453FB944691FADFF3298595F97EAFFD00B2188261CEEC27\",\"extInfo\":\"{\\\"miniProgramType\\\":0}\",\"businessType\":\"wxpayScoreEnable\"},\"auth_id\":\"o0DJ_josMa32_R7J8jq-4_H7IDTE\"}");
                abv b = aby.b(10, testWechatPayPage.getContext());
                b.setDebug(false);
                b.sign(payInfo, new abw() {
                    public final void a(PayInfo payInfo) {
                        String d = TestWechatPayPage.this.a;
                        StringBuilder sb = new StringBuilder("call back: ");
                        sb.append(payInfo.toJson());
                        AMapLog.info("paas.pay", d, sb.toString());
                    }
                });
            } else if (view.getId() == R.id.pay_test_wxpay_login_btn) {
                aby.c(10, TestWechatPayPage.this.getContext()).a(new abw() {
                    public final void a(PayInfo payInfo) {
                        String d = TestWechatPayPage.this.a;
                        StringBuilder sb = new StringBuilder("call back: ");
                        sb.append(payInfo.toJson());
                        AMapLog.info("paas.pay", d, sb.toString());
                    }
                });
            } else {
                if (view.getId() == R.id.pay_test_wxpay_isinstalled_btn) {
                    TestWechatPayPage.c(TestWechatPayPage.this);
                }
            }
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.wechatpay_test_layout);
        findViewById(R.id.pay_test_wxpay_btn).setOnClickListener(this.b);
        findViewById(R.id.pay_test_wxpay_signscore_btn).setOnClickListener(this.b);
        findViewById(R.id.pay_test_wxpay_login_btn).setOnClickListener(this.b);
        findViewById(R.id.pay_test_wxpay_isinstalled_btn).setOnClickListener(this.b);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new abq(this);
    }

    static /* synthetic */ void a(TestWechatPayPage testWechatPayPage) {
        PayInfo payInfo = new PayInfo("{\"package\":\"Sign=WXPay\",\"appid\":\"wx9b913299215a38f2\",\"sign\":\"783B9E8EC7C9537A22E94032695861A2\",\"partnerid\":\"1525281191\",\"prepayid\":\"wx26152715747696e0a1027a4a0721511924\",\"noncestr\":\"1556263645594\",\"timestamp\":\"1556263645\"}");
        abt a2 = aby.a(10, testWechatPayPage.getContext());
        a2.setDebug(true);
        a2.a(payInfo, new abw() {
            public final void a(PayInfo payInfo) {
                String d = TestWechatPayPage.this.a;
                StringBuilder sb = new StringBuilder("call back: ");
                sb.append(payInfo.toJson());
                AMapLog.info("paas.pay", d, sb.toString());
            }
        });
    }

    static /* synthetic */ void c(TestWechatPayPage testWechatPayPage) {
        abr d = aby.d(10, testWechatPayPage.getContext());
        String str = testWechatPayPage.a;
        StringBuilder sb = new StringBuilder("call back: ");
        sb.append(d.a());
        AMapLog.info("paas.pay", str, sb.toString());
    }
}
