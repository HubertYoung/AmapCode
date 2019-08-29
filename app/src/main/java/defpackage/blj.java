package defpackage;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import org.json.JSONObject;

/* renamed from: blj reason: default package */
/* compiled from: OpenAppUrlAction */
public class blj extends vz {
    private boolean a = false;

    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a2 = a();
        if (a2 != null) {
            try {
                String optString = jSONObject.optString("package");
                jSONObject.optString("version");
                String optString2 = jSONObject.optString("andh");
                String optString3 = jSONObject.optString("wapUrl");
                boolean z = false;
                final boolean optBoolean = jSONObject.optBoolean("isShowClose", false);
                final String optString4 = jSONObject.optString("appName");
                final int optInt = jSONObject.optInt("loadingTime");
                if (optInt != 0) {
                    z = true;
                }
                final Boolean valueOf = Boolean.valueOf(z);
                String optString5 = jSONObject.optString("isout", "0");
                Intent intent = new Intent();
                if (optString != null && !"".equals(optString)) {
                    intent.setPackage(optString);
                }
                try {
                    if (TextUtils.isEmpty(optString2)) {
                        throw new ActivityNotFoundException();
                    }
                    if (optString2.startsWith(AjxHttpLoader.DOMAIN_HTTP)) {
                        intent.setAction("android.intent.action.VIEW");
                    }
                    intent.setData(Uri.parse(optString2));
                    intent.addFlags(268435456);
                    if (a2.mPageContext.getActivity() != null) {
                        a2.mPageContext.getActivity().startActivity(intent);
                    }
                } catch (ActivityNotFoundException unused) {
                    if (optString5.equals("1")) {
                        a2.mPageContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(optString3)));
                        return;
                    }
                    JSONObject optJSONObject = jSONObject.optJSONObject("showButton");
                    aja aja = new aja(optString3);
                    final JSONObject jSONObject2 = optJSONObject;
                    final JsAdapter jsAdapter = a2;
                    AnonymousClass1 r0 = new ajf() {
                        public final a l_() {
                            return new a() {
                                public final String a() {
                                    if (jSONObject2 != null) {
                                        String optString = jSONObject2.optString("buttonText");
                                        if (!TextUtils.isEmpty(optString)) {
                                            return optString;
                                        }
                                    }
                                    return null;
                                }

                                public final boolean b() {
                                    if (jSONObject2 == null) {
                                        return false;
                                    }
                                    String optString = jSONObject2.optString("localFile");
                                    if (!TextUtils.isEmpty(optString)) {
                                        PageBundle pageBundle = new PageBundle();
                                        pageBundle.putString("localFile", optString);
                                        jsAdapter.mPageContext.startPage((String) "amap.life.action.HotelReserveMorePage", pageBundle);
                                    }
                                    return true;
                                }
                            };
                        }

                        public final b c() {
                            return new b() {
                                public final boolean a() {
                                    return !valueOf.booleanValue();
                                }

                                public final String b() {
                                    return optString4;
                                }

                                public final long c() {
                                    return (long) optInt;
                                }
                            };
                        }

                        public final boolean h() {
                            return optBoolean;
                        }
                    };
                    aja.b = r0;
                    if (a2.mPageContext != null) {
                        aix aix = (aix) a.a.a(aix.class);
                        if (aix != null) {
                            aix.a(a2.mPageContext, aja);
                        }
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
