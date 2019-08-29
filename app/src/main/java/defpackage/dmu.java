package defpackage;

import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import org.json.JSONObject;

/* renamed from: dmu reason: default package */
/* compiled from: LicenseConfirmAtion */
public class dmu extends vz {
    public bkm a;

    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a2 = a();
        if (a2 != null) {
            int optInt = jSONObject.optInt("clickType");
            if (optInt != 0) {
                if (optInt == 1 && this.a != null) {
                    if (a2.mViewLayer != null) {
                        a2.mViewLayer.a();
                    } else {
                        a2.mPageContext.finish();
                    }
                    Editor edit = DoNotUseTool.getContext().getSharedPreferences("category_save_v2", 0).edit();
                    edit.putBoolean(this.a.a, true);
                    edit.commit();
                    String str = "";
                    ddq ddq = (ddq) a.a.a(ddq.class);
                    if (ddq != null) {
                        str = ddq.e();
                    }
                    if (TextUtils.isEmpty(this.a.f) || !this.a.f.equals(str)) {
                        final String str2 = this.a.d;
                        final boolean z = this.a.e;
                        aja aja = new aja(this.a.c);
                        aja.b = new ajf() {
                            public final String b() {
                                return str2;
                            }

                            public final b c() {
                                return new b() {
                                    public final long c() {
                                        return 1000;
                                    }

                                    public final boolean a() {
                                        return !z;
                                    }

                                    public final String b() {
                                        if (!z) {
                                            return str2;
                                        }
                                        return null;
                                    }
                                };
                            }
                        };
                        aix aix = (aix) a.a.a(aix.class);
                        if (aix != null) {
                            aix.a(AMapPageUtil.getPageContext(), aja);
                        }
                    } else {
                        final String str3 = this.a.d;
                        String str4 = this.a.c;
                        final String str5 = this.a.d;
                        aja aja2 = new aja(str4);
                        aja2.b = new ajf() {
                            final /* synthetic */ boolean b = true;
                            final /* synthetic */ boolean d;

                            {
                                this.d = false;
                            }

                            public final String b() {
                                if (this.b) {
                                    return null;
                                }
                                return str3;
                            }

                            public final boolean d() {
                                return this.d;
                            }

                            public final b c() {
                                return new b() {
                                    public final boolean a() {
                                        return true;
                                    }

                                    public final long c() {
                                        return 1000;
                                    }

                                    public final String b() {
                                        return str5;
                                    }
                                };
                            }
                        };
                        aix aix2 = (aix) a.a.a(aix.class);
                        if (aix2 != null) {
                            aix2.a(AMapPageUtil.getPageContext(), aja2);
                        }
                    }
                }
            } else if (a2.mViewLayer != null) {
                a2.mViewLayer.a();
            } else {
                a2.mPageContext.finish();
            }
        }
    }
}
