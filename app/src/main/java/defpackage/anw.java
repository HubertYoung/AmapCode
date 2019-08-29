package defpackage;

import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.bundle.account.jsaction.GetAmapUserIdAction$3;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.feedback.FeedbackRequestHolder;
import com.autonavi.minimap.feedback.param.BindRequest;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: anw reason: default package */
/* compiled from: GetAmapUserIdAction */
public class anw extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            try {
                JSONObject optJSONObject = jSONObject.optJSONObject("extra");
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("extra", optJSONObject);
                jSONObject2.put("_action", waVar.b);
                String str = null;
                if (jSONObject.has("loginBackToPage")) {
                    str = jSONObject.optString("loginBackToPage");
                    if (!TextUtils.isEmpty(str) && !str.startsWith("http") && !str.startsWith("https")) {
                        bgx bgx = (bgx) a.a.a(bgx.class);
                        if (bgx != null) {
                            str = bgx.getUrl(str);
                        }
                    }
                }
                int optInt = jSONObject.optInt("onlyGetId");
                int optInt2 = jSONObject.optInt("needRelogin");
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    String b = iAccountService.b();
                    boolean a2 = iAccountService.a(AccountType.Taobao);
                    if (optInt2 != 0) {
                        iAccountService.d();
                        a(waVar, jSONObject, str);
                    } else if (optInt == 1) {
                        if (b == null) {
                            b = "";
                        }
                        jSONObject2.put("userid", b);
                        jSONObject2.put("isBindTaoBao", a2 ? "1" : "0");
                        a.callJs(waVar.a, jSONObject2.toString());
                    } else if (TextUtils.isEmpty(b)) {
                        a(waVar, jSONObject, str);
                    } else {
                        if (b == null) {
                            b = "";
                        }
                        jSONObject2.put("userid", b);
                        jSONObject2.put("isBindTaoBao", a2 ? "1" : "0");
                        a.callJs(waVar.a, jSONObject2.toString());
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    private void a(wa waVar, JSONObject jSONObject, String str) {
        final JSONObject optJSONObject = jSONObject.optJSONObject("extra");
        final wa waVar2 = waVar;
        final JSONObject jSONObject2 = jSONObject;
        final String str2 = str;
        AnonymousClass1 r1 = new anq() {
            public final void onComplete(boolean z) {
                anw.a(anw.this, optJSONObject, waVar2, jSONObject2, str2, z);
            }

            public final void loginOrBindCancel() {
                anw.a(anw.this, optJSONObject, waVar2, jSONObject2, str2, false);
            }
        };
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            JSONObject jSONObject3 = new JSONObject();
            try {
                jSONObject3.put("from", 4);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00400", "B001", jSONObject3);
            iAccountService.a(a().mPageContext, (anq) r1);
        }
    }

    /* access modifiers changed from: private */
    public void a(JSONObject jSONObject, wa waVar, JSONObject jSONObject2, String str, boolean z) {
        JsAdapter a = a();
        if (a != null) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                JSONObject jSONObject3 = new JSONObject();
                if (z) {
                    if (str != null) {
                        try {
                            a.mBaseWebView.a(str);
                        } catch (Exception e) {
                            kf.a((Throwable) e);
                        }
                    }
                    jSONObject3.put("extra", jSONObject);
                    jSONObject3.put("_action", waVar.b);
                    jSONObject3.put("userid", iAccountService.b());
                    a.callJs(waVar.a, jSONObject3.toString());
                } else {
                    jSONObject3.put("extra", jSONObject);
                    jSONObject3.put("_action", waVar.b);
                    jSONObject3.put("userid", "");
                    a.callJs(waVar.a, jSONObject3.toString());
                }
                if (a.mPageContext instanceof anr) {
                    ((anr) a.mPageContext).a(z);
                }
                if (z) {
                    String optString = jSONObject2.optString("pageId");
                    if (!TextUtils.isEmpty(optString) && optString.equals("feedback") && a.mPageContext != null) {
                        PageBundle arguments = a.mPageContext.getArguments();
                        String str2 = null;
                        boolean z2 = false;
                        if (arguments != null) {
                            str2 = arguments.getString("record_id");
                            z2 = arguments.getBoolean("native_feedback");
                        }
                        if (z2) {
                            if (!TextUtils.isEmpty(str2)) {
                                BindRequest bindRequest = new BindRequest();
                                bindRequest.b = str2;
                                FeedbackRequestHolder.getInstance().sendBind(bindRequest, new GetAmapUserIdAction$3(this, a));
                            } else if (a.mViewLayer != null) {
                                a.mViewLayer.a();
                            } else {
                                a.mPageContext.finish();
                            }
                        }
                    }
                }
            }
        }
    }

    static /* synthetic */ void a(anw anw, JSONObject jSONObject, wa waVar, JSONObject jSONObject2, String str, boolean z) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            anw.a(jSONObject, waVar, jSONObject2, str, z);
            return;
        }
        final JSONObject jSONObject3 = jSONObject;
        final wa waVar2 = waVar;
        final JSONObject jSONObject4 = jSONObject2;
        final String str2 = str;
        final boolean z2 = z;
        AnonymousClass2 r2 = new Runnable() {
            public final void run() {
                anw.this.a(jSONObject3, waVar2, jSONObject4, str2, z2);
            }
        };
        aho.a(r2);
    }
}
