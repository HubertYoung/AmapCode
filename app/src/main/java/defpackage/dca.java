package defpackage;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IThirdAuth.a;
import com.autonavi.bundle.account.api.IThirdAuth.b;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.sina.weibo.BuildConfig;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@BundleInterface(dcb.class)
/* renamed from: dca reason: default package */
/* compiled from: ShareService */
public class dca extends esi implements dcb {
    private static final String a;
    private static dca b;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(File.separator);
        sb.append("data");
        sb.append(File.separator);
        sb.append("data");
        sb.append(File.separator);
        a = sb.toString();
    }

    public static dca a() {
        if (b == null) {
            dca dca = (dca) a.a.a(dcb.class);
            b = dca;
            dca.b();
        }
        return b;
    }

    public final void b() {
        a((b) new b() {
            public final void triggerWxShare(BaseResp baseResp) {
                ddn a2 = ddn.a();
                for (int i = 0; i < a2.a.size(); i++) {
                    a2.a.get(i);
                }
                a2.a.clear();
                AtomicReference atomicReference = new AtomicReference(Boolean.FALSE);
                String str = baseResp.transaction;
                if (!TextUtils.isEmpty(str)) {
                    String[] split = str.split("_");
                    if (split.length == 3) {
                        atomicReference.set(Boolean.valueOf("1".equals(split[2])));
                    }
                }
                int i2 = 4;
                if (baseResp.errCode == 0) {
                    ddi a3 = ddi.a();
                    if (!((Boolean) atomicReference.get()).booleanValue()) {
                        i2 = 3;
                    }
                    a3.a(i2, 0);
                } else if (baseResp.errCode != -2) {
                    ddi a4 = ddi.a();
                    if (!((Boolean) atomicReference.get()).booleanValue()) {
                        i2 = 3;
                    }
                    a4.a(i2, -1);
                }
                ddi.a().b();
            }
        });
    }

    public final void a(dct dct, dcd dcd) {
        if (dct == null || dcd == null) {
            throw new IllegalArgumentException("types or shareCallback can not be null!");
        }
        dco a2 = a(dct);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext == null) {
            throw new IllegalArgumentException("share must called by pagecontext object");
        }
        a(pageContext, a2, dcd);
    }

    public final void a(bid bid, dct dct, dcd dcd) {
        a(bid, a(dct), dcd);
    }

    public final String a(dcq dcq) {
        return dcw.a(dcq);
    }

    public final void a(Context context, dct dct, POI poi, String str) {
        dcw.a(context, dct, poi, str);
    }

    public final dcc c() {
        return ddk.a();
    }

    public final void a(Context context, dct dct, dcd dcd) {
        if (dct == null) {
            throw new IllegalArgumentException("types or shareCallback can not be null!");
        }
        dco a2 = a(dct);
        if (a2.a != null && a2.a.a != null && a2.a.a.length != 0) {
            if (a2.a.a.length > 1 || (a2.a.a.length == 1 && !a2.b)) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("shareData", a2);
                pageBundle.putObject("shareStatusCallback", dcd);
                if (context != null) {
                    new eoo(context).a(pageBundle);
                }
                return;
            }
            new dcp(a2, dcd).a();
        }
    }

    private static void a(bid bid, dco dco, dcd dcd) {
        if (dco != null && dco.a != null && dco.a.a != null && dco.a.a.length != 0) {
            if (dco.a.a.length > 1 || (dco.a.a.length == 1 && !dco.b)) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("shareData", dco);
                pageBundle.putObject("shareStatusCallback", dcd);
                if (bid != null) {
                    new eop(bid).a(pageBundle);
                }
                return;
            }
            new dcp(dco, dcd).a();
        }
    }

    private static dco a(dct dct) {
        dci b2 = b(dct);
        dco dco = new dco();
        dco.b = dct.m;
        dco.a = b2;
        return dco;
    }

    private static dci b(dct dct) {
        dci dci = new dci();
        ArrayList arrayList = new ArrayList();
        boolean z = bno.b;
        boolean z2 = !z || ddf.a(AMapPageUtil.getAppContext(), "com.tencent.mm");
        boolean z3 = !z || ddf.a(AMapPageUtil.getAppContext(), BuildConfig.APPLICATION_ID);
        boolean z4 = !z || ddf.a(AMapPageUtil.getAppContext(), "com.tencent.mobileqq");
        boolean z5 = !z || dcf.a();
        if (dct.a) {
            arrayList.add(Integer.valueOf(0));
        }
        if (dct.b) {
            arrayList.add(Integer.valueOf(1));
        }
        if (dct.c) {
            arrayList.add(Integer.valueOf(2));
        }
        if (dct.k) {
            arrayList.add(Integer.valueOf(10));
        }
        if (dct.d && z2) {
            arrayList.add(Integer.valueOf(3));
        }
        if (dct.e && z2) {
            arrayList.add(Integer.valueOf(4));
        }
        if (dct.f && z3) {
            arrayList.add(Integer.valueOf(5));
        }
        if (dct.g) {
            arrayList.add(Integer.valueOf(6));
        }
        if (dct.h) {
            arrayList.add(Integer.valueOf(7));
        }
        if (dct.i && z4) {
            arrayList.add(Integer.valueOf(8));
        }
        if (dct.j && z4) {
            arrayList.add(Integer.valueOf(9));
        }
        if (dct.l && z5) {
            arrayList.add(Integer.valueOf(11));
        }
        dci.a = a((List<Integer>) arrayList);
        return dci;
    }

    private static int[] a(List<Integer> list) {
        if (list.size() <= 0) {
            return null;
        }
        int[] iArr = new int[list.size()];
        int i = 0;
        for (Integer intValue : list) {
            iArr[i] = intValue.intValue();
            i++;
        }
        return iArr;
    }

    public final boolean a(String str, String str2) {
        boolean z;
        boolean z2 = false;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        Activity topActivity = AMapAppGlobal.getTopActivity();
        if (topActivity == null) {
            return false;
        }
        if (TextUtils.equals(str2, "sms")) {
            Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:"));
            intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            intent.putExtra("sms_body", str);
            topActivity.startActivity(intent);
            z2 = true;
        } else {
            String a2 = a(str2);
            if (a2 == null) {
                z = false;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(a);
                sb.append(a2);
                z = new File(sb.toString()).exists();
            }
            if (z) {
                ((ClipboardManager) topActivity.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(null, str));
                z2 = a((Context) topActivity, a(str2));
            } else {
                ToastHelper.showLongToast(DoNotUseTool.getContext().getString(R.string.app_is_not_installed));
            }
        }
        return z2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x003e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.lang.String r2) {
        /*
            int r0 = r2.hashCode()
            r1 = -791770330(0xffffffffd0ce8b26, float:-2.77218058E10)
            if (r0 == r1) goto L_0x0027
            r1 = 3616(0xe20, float:5.067E-42)
            if (r0 == r1) goto L_0x001d
            r1 = 95904528(0x5b76310, float:1.7245627E-35)
            if (r0 == r1) goto L_0x0013
            goto L_0x0032
        L_0x0013:
            java.lang.String r0 = "dtalk"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0032
            r2 = 2
            goto L_0x0033
        L_0x001d:
            java.lang.String r0 = "qq"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0032
            r2 = 0
            goto L_0x0033
        L_0x0027:
            java.lang.String r0 = "wechat"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0032
            r2 = 1
            goto L_0x0033
        L_0x0032:
            r2 = -1
        L_0x0033:
            switch(r2) {
                case 0: goto L_0x003e;
                case 1: goto L_0x003b;
                case 2: goto L_0x0038;
                default: goto L_0x0036;
            }
        L_0x0036:
            r2 = 0
            goto L_0x0040
        L_0x0038:
            java.lang.String r2 = "com.alibaba.android.rimet"
            goto L_0x0040
        L_0x003b:
            java.lang.String r2 = "com.tencent.mm"
            goto L_0x0040
        L_0x003e:
            java.lang.String r2 = "com.tencent.mobileqq"
        L_0x0040:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dca.a(java.lang.String):java.lang.String");
    }

    private static boolean a(Context context, String str) {
        try {
            context.startActivity(context.getPackageManager().getLaunchIntentForPackage(str));
            return true;
        } catch (Exception unused) {
            ToastHelper.showLongToast(DoNotUseTool.getContext().getString(R.string.app_is_not_installed));
            return false;
        }
    }

    public static void a(b bVar) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            a a2 = iAccountService.c().a();
            if (a2 != null) {
                a2.a(bVar);
            }
        }
    }
}
