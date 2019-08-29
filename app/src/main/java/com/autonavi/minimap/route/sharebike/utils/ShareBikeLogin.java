package com.autonavi.minimap.route.sharebike.utils;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.sharebike.page.ShareBikePage;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareBikeLogin {
    private Object[] a;

    public enum OpenPageType {
        PAGE_QRCODE_SCAN,
        PAGE_HISTORY,
        PAGE_WALLET_LIST,
        PAGE_WALLET_DETAIL
    }

    public ShareBikeLogin(Object... objArr) {
        this.a = objArr;
    }

    public final void a(bid bid, OpenPageType openPageType, boolean z) {
        final PageBundle pageBundle;
        boolean z2;
        Object obj;
        boolean z3;
        Object obj2;
        boolean z4;
        JSONObject jSONObject;
        boolean b = b();
        boolean a2 = a(AccountType.Mobile);
        if (eht.a(bid)) {
            final JSONObject jSONObject2 = new JSONObject();
            int i = 2;
            PageBundle pageBundle2 = null;
            switch (openPageType) {
                case PAGE_QRCODE_SCAN:
                    if (this.a == null || this.a.length <= 0) {
                        obj2 = null;
                        obj = null;
                        z3 = true;
                    } else {
                        if (this.a.length == 2) {
                            obj2 = (String) this.a[0];
                            obj = (String) this.a[1];
                            z4 = false;
                        } else {
                            obj2 = null;
                            obj = null;
                            z4 = true;
                        }
                        Object obj3 = this.a[0];
                        if (obj3 instanceof dzw) {
                            dzw dzw = (dzw) obj3;
                            pageBundle2 = dzw.a;
                            a(jSONObject2, dzw.b);
                            z3 = jSONObject2.optBoolean("launchBikePage", true);
                        } else {
                            z3 = z4;
                        }
                    }
                    try {
                        jSONObject2.put("cpSources", ehs.b("share_bike_cpsource_list"));
                        jSONObject2.put("cpSource", ehs.b("share_bike_check_cpsource"));
                        jSONObject2.put("cpNames", obj);
                        if (!jSONObject2.has("firepage")) {
                            jSONObject2.put("firepage", obj2);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    z2 = z3;
                    pageBundle = pageBundle2;
                    break;
                case PAGE_HISTORY:
                case PAGE_WALLET_LIST:
                case PAGE_WALLET_DETAIL:
                    if (this.a != null && this.a.length > 0) {
                        Object obj4 = this.a[0];
                        if (obj4 instanceof JSONObject) {
                            jSONObject = (JSONObject) obj4;
                            a(jSONObject2, jSONObject);
                            break;
                        }
                    }
                    jSONObject = null;
                    a(jSONObject2, jSONObject);
            }
            pageBundle = null;
            z2 = true;
            int i2 = 3;
            if (!z || (b && a2)) {
                switch (openPageType) {
                    case PAGE_QRCODE_SCAN:
                        final bid bid2 = bid;
                        final boolean z5 = z2;
                        AnonymousClass1 r3 = new b() {
                            public final void reject() {
                                super.reject();
                                ToastHelper.showToast(bid2.getContext().getString(R.string.sharebike_camera_init_fail));
                            }

                            public final void run() {
                                if (!ShareBikeLogin.a()) {
                                    ToastHelper.showToast(bid2.getContext().getString(R.string.sharebike_camera_init_fail));
                                    return;
                                }
                                if (z5) {
                                    bid2.startPage(ShareBikePage.class, pageBundle);
                                }
                                ehc.a(6, jSONObject2.toString());
                            }
                        };
                        kj.a(bid.getActivity(), new String[]{"android.permission.CAMERA"}, (b) r3);
                        return;
                    case PAGE_HISTORY:
                    case PAGE_WALLET_LIST:
                    case PAGE_WALLET_DETAIL:
                        if (openPageType != OpenPageType.PAGE_WALLET_LIST) {
                            i = openPageType == OpenPageType.PAGE_WALLET_DETAIL ? 3 : 1;
                        }
                        ehc.a(i, jSONObject2.toString());
                        return;
                    default:
                        return;
                }
            } else {
                try {
                    jSONObject2.put("targetPage", openPageType);
                    if (!b) {
                        i2 = 1;
                    }
                    jSONObject2.put("dialogType", i2);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                ehc.a(7, jSONObject2.toString());
            }
        }
    }

    private static void a(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject2 != null) {
            Iterator<String> keys = jSONObject2.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                try {
                    jSONObject.put(next, jSONObject2.opt(next));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x001d  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean a() {
        /*
            r0 = 0
            android.hardware.Camera r1 = android.hardware.Camera.open()     // Catch:{ Exception -> 0x0021, all -> 0x0017 }
            android.hardware.Camera$Parameters r0 = r1.getParameters()     // Catch:{ Exception -> 0x0015, all -> 0x0013 }
            r1.setParameters(r0)     // Catch:{ Exception -> 0x0015, all -> 0x0013 }
            if (r1 == 0) goto L_0x0011
            r1.release()
        L_0x0011:
            r0 = 1
            goto L_0x0028
        L_0x0013:
            r0 = move-exception
            goto L_0x001b
        L_0x0015:
            r0 = r1
            goto L_0x0021
        L_0x0017:
            r1 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x001b:
            if (r1 == 0) goto L_0x0020
            r1.release()
        L_0x0020:
            throw r0
        L_0x0021:
            r1 = 0
            if (r0 == 0) goto L_0x0027
            r0.release()
        L_0x0027:
            r0 = 0
        L_0x0028:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.sharebike.utils.ShareBikeLogin.a():boolean");
    }

    private static boolean b() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    private static boolean a(AccountType accountType) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a(accountType);
    }
}
