package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.minimap.account.insurance.InsuranceRequestHolder;
import com.autonavi.minimap.account.insurance.model.InsuranceTokenResponse;
import com.autonavi.minimap.account.insurance.param.InsuranceTokenParam;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bme reason: default package */
/* compiled from: GetAlipayLoginToken */
public class bme extends vz {
    public static final String a = "bme";
    private bml b;

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a2 = a();
        if (a2 != null) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                if (!iAccountService.a()) {
                    b(waVar, a2);
                } else if (iAccountService.a(AccountType.Alipay)) {
                    c(waVar, a2);
                } else {
                    a(waVar, a2);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void c(final wa waVar, final JsAdapter jsAdapter) {
        b(jsAdapter);
        InsuranceTokenParam insuranceTokenParam = new InsuranceTokenParam();
        if ("test".equals(ConfigerHelper.getInstance().getNetCondition()) && ConfigerHelper.getInstance().getAlipayTest()) {
            insuranceTokenParam.env = "dev";
        }
        InsuranceRequestHolder.getInstance().sendToken(insuranceTokenParam, new dko<InsuranceTokenResponse>() {
            public final /* synthetic */ void a(dkm dkm) {
                InsuranceTokenResponse insuranceTokenResponse = (InsuranceTokenResponse) dkm;
                bme.a(bme.this, jsAdapter);
                if (insuranceTokenResponse != null) {
                    int i = insuranceTokenResponse.code;
                    String str = insuranceTokenResponse.message;
                    if (i == 1) {
                        bme.a(waVar, jsAdapter, insuranceTokenResponse.autoToken);
                    } else if (i == 10030) {
                        bme.this.a(waVar, jsAdapter);
                    } else if (i != 10050) {
                        String str2 = bme.a;
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append("  error code: ");
                        sb.append(i);
                        AMapLog.i(str2, sb.toString());
                        bme.b(waVar, jsAdapter, "", "0");
                    } else {
                        bme.this.b(waVar, jsAdapter);
                    }
                } else {
                    AMapLog.i(bme.a, "result is empty.");
                    bme.b(waVar, jsAdapter, "", "0");
                }
            }

            public final void a(Exception exc) {
                bme.a(bme.this, jsAdapter);
                String str = bme.a;
                StringBuilder sb = new StringBuilder("error: ");
                sb.append(exc.getMessage());
                AMapLog.i(str, sb.toString());
                bme.b(waVar, jsAdapter, "", "0");
            }
        });
    }

    /* access modifiers changed from: private */
    public static void b(wa waVar, JsAdapter jsAdapter, String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("alipayLoginToken", str);
            jSONObject.put("status", str2);
            jSONObject.put("_action", waVar.b);
            jsAdapter.callJs(waVar.a, jSONObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void b(JsAdapter jsAdapter) {
        if (jsAdapter != null && jsAdapter.mPageContext != null) {
            if (this.b == null) {
                this.b = new bml(jsAdapter.mPageContext.getActivity());
            }
            this.b.a();
        }
    }

    /* access modifiers changed from: private */
    public void a(final wa waVar, final JsAdapter jsAdapter) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(a().mPageContext, AccountType.Alipay, (anq) new anq() {
                public final void onComplete(boolean z) {
                    if (z) {
                        bme.this.c(waVar, jsAdapter);
                        return;
                    }
                    AMapLog.i(bme.a, "bindAlipay--->".concat(String.valueOf(z)));
                    bme.b(waVar, jsAdapter, "", "-1");
                }

                public final void loginOrBindCancel() {
                    bme.b(waVar, jsAdapter, "", "-1");
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void b(final wa waVar, final JsAdapter jsAdapter) {
        final IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(AccountType.Alipay, (anq) new anq() {
                public final void onComplete(boolean z) {
                    boolean a2 = iAccountService.a(AccountType.Alipay);
                    if (z && a2) {
                        bme.this.c(waVar, jsAdapter);
                    } else if (!z || a2) {
                        if (!z) {
                            AMapLog.i(bme.a, "loginAlipay--->".concat(String.valueOf(z)));
                            bme.b(waVar, jsAdapter, "", "-1");
                        }
                    } else {
                        bme.this.a(waVar, jsAdapter);
                    }
                }

                public final void loginOrBindCancel() {
                    AMapLog.i(bme.a, "loginAlipay--->false");
                    bme.b(waVar, jsAdapter, "", "-1");
                }
            });
        }
    }

    static /* synthetic */ void a(bme bme, JsAdapter jsAdapter) {
        if (!(jsAdapter == null || jsAdapter.mPageContext == null || bme.b == null)) {
            bme.b.dismiss();
        }
    }

    static /* synthetic */ void a(wa waVar, JsAdapter jsAdapter, String str) {
        if (!TextUtils.isEmpty(str)) {
            AMapLog.i(a, "token is sus ".concat(String.valueOf(str)));
            b(waVar, jsAdapter, str, "1");
            return;
        }
        AMapLog.i(a, "autoToken is empty.");
        b(waVar, jsAdapter, "", "0");
    }
}
