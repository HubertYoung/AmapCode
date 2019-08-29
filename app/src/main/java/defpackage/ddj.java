package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.util.ShareFinishCallback;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: ddj reason: default package */
/* compiled from: ShareDataHandler */
public final class ddj {
    private static long i;
    private bid a;
    /* access modifiers changed from: private */
    public Context b = DoNotUseTool.getContext();
    private PageBundle c;
    private ArrayList<dcr> d;
    private dco e;
    private ddh f;
    /* access modifiers changed from: private */
    public dcd g;
    private ArrayList<dcr> h;
    private boolean j = false;

    static /* synthetic */ boolean b(int i2) {
        return i2 == 8 || i2 == 9 || i2 == 5;
    }

    static /* synthetic */ boolean c(int i2) {
        return i2 == 8 || i2 == 9 || i2 == 5 || i2 == 3 || i2 == 4;
    }

    public ddj(PageBundle pageBundle) {
        this.c = pageBundle;
        if (this.c == null) {
            throw new IllegalArgumentException("empty data!");
        }
        ShareFinishCallback shareFinishCallback = (ShareFinishCallback) this.c.get("shareFinishCallback");
        ddh ddh = (ddh) this.c.get("shareCallback");
        dcd dcd = (dcd) this.c.get("shareStatusCallback");
        this.e = (dco) this.c.get("shareData");
        ddi.a().b();
        ddi a2 = ddi.a();
        if (shareFinishCallback != null && !a2.a.contains(shareFinishCallback)) {
            a2.a.add(shareFinishCallback);
        }
        ddi.a().a(new dcd() {
            public final ShareParam getShareDataByType(int i) {
                return null;
            }

            public final void onFinish(int i, int i2) {
                if (i2 == 0) {
                    String a2 = ddj.d(i);
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("type", a2);
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                    }
                    LogManager.actionLogV2(LogConstant.PAGE_ID_SHARE_PLUGIN, "B009", jSONObject);
                    if (ddj.b(i)) {
                        ToastHelper.showToast(ddj.this.b.getString(R.string.pubok));
                    }
                } else if (i2 == -1 && ddj.c(i)) {
                    ToastHelper.showToast(ddj.this.b.getString(R.string.puberr));
                }
                if (ddj.this.g != null) {
                    ddj.this.g.onFinish(i, i2);
                }
            }
        });
        ddi.a().a(dcd);
        this.f = ddh;
        if (this.d == null || this.d.size() <= 0) {
            this.d = new ArrayList<>();
            this.d.add(new dcr(this.b.getString(R.string.share_dialog_car), R.drawable.bg_btn_share_car2, 2));
            this.d.add(new dcr(this.b.getString(R.string.share_dialog_weibo), R.drawable.bg_btn_share_sina, 5));
            this.d.add(new dcr(this.b.getString(R.string.share_dialog_wx), R.drawable.bg_btn_share_wx2, 3));
            this.d.add(new dcr(this.b.getString(R.string.share_dialog_wx_circle), R.drawable.bg_btn_share_wx_circle2, 4));
            this.d.add(new dcr(this.b.getString(R.string.share_dialog_qq), R.drawable.bg_btn_share_qq, 8));
            this.d.add(new dcr(this.b.getString(R.string.share_dialog_lianjie), R.drawable.bg_btn_share_lianjie2, 6));
            this.d.add(new dcr(this.b.getString(R.string.share_dialog_dingding), R.drawable.bg_btn_share_dingding2, 11));
            this.d.add(new dcr(this.b.getString(R.string.share_dialog_car_other), R.drawable.bg_btn_share_car_other2, 10));
            this.d.add(new dcr(this.b.getString(R.string.share_dialog_gengduo), R.drawable.bg_btn_share_gengduo2, 7));
        }
        a(this.e.a.a);
        this.g = dcd;
    }

    /* access modifiers changed from: private */
    public static String d(int i2) {
        switch (i2) {
            case 0:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_sms);
            case 1:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_email);
            case 2:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_car);
            case 3:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_wx);
            case 4:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_wx_circle);
            case 5:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_weibo);
            case 6:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_lianjie);
            case 7:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_gengduo);
            case 8:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_qq);
            case 9:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_qq_zone);
            case 10:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_car_other);
            case 11:
                return AMapPageUtil.getAppContext().getString(R.string.share_dialog_dingding);
            default:
                return "";
        }
    }

    private void a(int... iArr) {
        Iterator<dcr> it = this.d.iterator();
        while (it.hasNext()) {
            it.next().d = false;
        }
        Iterator<dcr> it2 = this.d.iterator();
        while (it2.hasNext()) {
            dcr next = it2.next();
            for (int i2 : iArr) {
                if (next.e == i2) {
                    next.d = true;
                }
            }
        }
    }

    public final ArrayList<dcr> a() {
        ArrayList<dcr> arrayList = new ArrayList<>();
        Iterator<dcr> it = this.d.iterator();
        while (it.hasNext()) {
            dcr next = it.next();
            if (next.d) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public final void b() {
        if (!this.j) {
            ddi.a().b();
        }
        if (this.g != null) {
            this.g.onDismiss();
        }
    }

    public final void c() {
        if (this.g != null) {
            this.g.onShow();
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_SHARE_PLUGIN, "B008");
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str) || str.contains("src=app_share") || !str.contains("amap.com")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(b(str));
        sb.append("&src=app_");
        sb.append(NetworkParam.getDic());
        return sb.toString();
    }

    private static String b(String str) {
        if (str.contains("?")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("?");
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(com.autonavi.map.fragmentcontainer.IViewLayer r19, defpackage.dcr r20) {
        /*
            r18 = this;
            r1 = r18
            r2 = r20
            long r3 = java.lang.System.currentTimeMillis()
            long r5 = i
            long r5 = r3 - r5
            r7 = 0
            int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            r8 = 0
            r9 = 1
            if (r7 >= 0) goto L_0x0017
            i = r3
            goto L_0x001f
        L_0x0017:
            r3 = 1000(0x3e8, double:4.94E-321)
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x001f
            r3 = 1
            goto L_0x0020
        L_0x001f:
            r3 = 0
        L_0x0020:
            if (r3 == 0) goto L_0x0023
            goto L_0x004b
        L_0x0023:
            dco r3 = r1.e
            if (r3 != 0) goto L_0x0028
            goto L_0x004b
        L_0x0028:
            long r3 = java.lang.System.currentTimeMillis()
            i = r3
            java.util.ArrayList<dcr> r3 = r1.h
            if (r3 != 0) goto L_0x0038
            java.util.ArrayList r3 = r18.a()
            r1.h = r3
        L_0x0038:
            boolean r3 = com.amap.bundle.network.util.NetworkReachability.b()
            if (r3 != 0) goto L_0x004a
            android.content.Context r3 = r1.b
            int r4 = com.autonavi.minimap.R.string.share_network_error_tips
            java.lang.String r3 = r3.getString(r4)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r3)
            goto L_0x004b
        L_0x004a:
            r8 = 1
        L_0x004b:
            if (r8 == 0) goto L_0x0706
            bid r3 = r1.a
            if (r3 == 0) goto L_0x0058
            bid r3 = r1.a
            r4 = r19
            r3.dismissViewLayer(r4)
        L_0x0058:
            dcd r3 = r1.g
            if (r3 == 0) goto L_0x0706
            int r3 = r2.e
            r4 = 6
            r5 = 3
            r6 = 5
            r7 = 0
            if (r3 != r4) goto L_0x00b8
            dcj r3 = new dcj
            r3.<init>()
            dcd r4 = r1.g
            if (r4 == 0) goto L_0x009a
            dcd r4 = r1.g
            com.autonavi.minimap.bundle.share.entity.ShareParam r4 = r4.getShareDataByType(r6)
            com.autonavi.minimap.bundle.share.entity.ShareParam$f r4 = (com.autonavi.minimap.bundle.share.entity.ShareParam.f) r4
            if (r4 == 0) goto L_0x0084
            java.lang.String r5 = r4.b
            r3.a = r5
            boolean r5 = r4.c
            r3.b = r5
            java.lang.String r4 = r4.d
            r3.c = r4
            goto L_0x009a
        L_0x0084:
            dcd r4 = r1.g
            com.autonavi.minimap.bundle.share.entity.ShareParam r4 = r4.getShareDataByType(r5)
            com.autonavi.minimap.bundle.share.entity.ShareParam$e r4 = (com.autonavi.minimap.bundle.share.entity.ShareParam.e) r4
            if (r4 == 0) goto L_0x009a
            java.lang.String r5 = r4.b
            r3.a = r5
            boolean r5 = r4.c
            r3.b = r5
            java.lang.String r4 = r4.d
            r3.c = r4
        L_0x009a:
            java.lang.String r4 = r3.a
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x00b1
            java.lang.String r4 = r3.a
            java.lang.String r4 = a(r4)
            r3.a = r4
            com.amap.bundle.statistics.HttpUrlCollector$SCENE r4 = com.amap.bundle.statistics.HttpUrlCollector.SCENE.SHARE
            java.lang.String r5 = r3.a
            com.amap.bundle.statistics.HttpUrlCollector.a(r4, r5)
        L_0x00b1:
            r17 = r7
            r7 = r3
            r3 = r17
            goto L_0x02e6
        L_0x00b8:
            r4 = 7
            if (r3 != r4) goto L_0x0159
            dch r3 = new dch
            r3.<init>()
            dcd r4 = r1.g
            if (r4 == 0) goto L_0x0140
            dcd r4 = r1.g
            com.autonavi.minimap.bundle.share.entity.ShareParam r4 = r4.getShareDataByType(r6)
            com.autonavi.minimap.bundle.share.entity.ShareParam$f r4 = (com.autonavi.minimap.bundle.share.entity.ShareParam.f) r4
            r6 = 2
            if (r4 == 0) goto L_0x0115
            java.lang.String r5 = r4.h
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 == 0) goto L_0x00ea
            r3.f = r6
            java.lang.String r5 = r4.b
            r3.b = r5
            java.lang.String r5 = r4.a
            r3.c = r5
            boolean r5 = r4.c
            r3.d = r5
            java.lang.String r4 = r4.d
            r3.e = r4
            goto L_0x0140
        L_0x00ea:
            java.io.File r5 = new java.io.File
            java.lang.String r8 = r4.h
            r5.<init>(r8)
            boolean r5 = r5.exists()
            if (r5 != 0) goto L_0x010a
            r3.f = r6
            java.lang.String r5 = r4.b
            r3.b = r5
            java.lang.String r5 = r4.a
            r3.c = r5
            boolean r5 = r4.c
            r3.d = r5
            java.lang.String r4 = r4.d
            r3.e = r4
            goto L_0x0140
        L_0x010a:
            r3.f = r9
            java.lang.String r5 = r4.a
            r3.c = r5
            java.lang.String r4 = r4.h
            r3.a = r4
            goto L_0x0140
        L_0x0115:
            dcd r4 = r1.g
            com.autonavi.minimap.bundle.share.entity.ShareParam r4 = r4.getShareDataByType(r5)
            com.autonavi.minimap.bundle.share.entity.ShareParam$e r4 = (com.autonavi.minimap.bundle.share.entity.ShareParam.e) r4
            if (r4 == 0) goto L_0x0140
            java.lang.String r5 = r4.h
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 == 0) goto L_0x013a
            r3.f = r6
            java.lang.String r5 = r4.b
            r3.b = r5
            java.lang.String r5 = r4.a
            r3.c = r5
            boolean r5 = r4.c
            r3.d = r5
            java.lang.String r4 = r4.d
            r3.e = r4
            goto L_0x0140
        L_0x013a:
            r3.f = r9
            java.lang.String r4 = r4.h
            r3.a = r4
        L_0x0140:
            java.lang.String r4 = r3.b
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x02e6
            java.lang.String r4 = r3.b
            java.lang.String r4 = a(r4)
            r3.b = r4
            com.amap.bundle.statistics.HttpUrlCollector$SCENE r4 = com.amap.bundle.statistics.HttpUrlCollector.SCENE.SHARE
            java.lang.String r5 = r3.b
            com.amap.bundle.statistics.HttpUrlCollector.a(r4, r5)
            goto L_0x02e6
        L_0x0159:
            dcd r4 = r1.g
            com.autonavi.minimap.bundle.share.entity.ShareParam r4 = r4.getShareDataByType(r3)
            if (r4 != 0) goto L_0x0169
            ddi r2 = defpackage.ddi.a()
            r2.b()
            return
        L_0x0169:
            if (r4 == 0) goto L_0x0182
            java.lang.String r5 = r4.b
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x0182
            java.lang.String r5 = r4.b
            java.lang.String r5 = a(r5)
            r4.b = r5
            com.amap.bundle.statistics.HttpUrlCollector$SCENE r5 = com.amap.bundle.statistics.HttpUrlCollector.SCENE.SHARE
            java.lang.String r6 = r4.b
            com.amap.bundle.statistics.HttpUrlCollector.a(r5, r6)
        L_0x0182:
            dco r5 = r1.e
            switch(r3) {
                case 0: goto L_0x02c0;
                case 1: goto L_0x02a1;
                case 2: goto L_0x0282;
                case 3: goto L_0x024c;
                case 4: goto L_0x0215;
                case 5: goto L_0x01e1;
                case 6: goto L_0x0187;
                case 7: goto L_0x0187;
                case 8: goto L_0x01ba;
                case 9: goto L_0x0193;
                case 10: goto L_0x0282;
                case 11: goto L_0x0189;
                default: goto L_0x0187;
            }
        L_0x0187:
            goto L_0x02e5
        L_0x0189:
            com.autonavi.minimap.bundle.share.entity.ShareParam$DingDingParam r4 = (com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam) r4     // Catch:{ ClassCastException -> 0x02d8 }
            dco$a r3 = defpackage.dcf.a(r4)     // Catch:{ ClassCastException -> 0x02d8 }
            r5.h = r3     // Catch:{ ClassCastException -> 0x02d8 }
            goto L_0x02e5
        L_0x0193:
            com.autonavi.minimap.bundle.share.entity.ShareParam$b r4 = (com.autonavi.minimap.bundle.share.entity.ShareParam.b) r4     // Catch:{ ClassCastException -> 0x02d8 }
            dco$e r3 = new dco$e     // Catch:{ ClassCastException -> 0x02d8 }
            r3.<init>()     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r6 = r4.c     // Catch:{ ClassCastException -> 0x02d8 }
            r3.h = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.d     // Catch:{ ClassCastException -> 0x02d8 }
            r3.i = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.a     // Catch:{ ClassCastException -> 0x02d8 }
            r3.f = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.b     // Catch:{ ClassCastException -> 0x02d8 }
            r3.g = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.e     // Catch:{ ClassCastException -> 0x02d8 }
            r3.a = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.f     // Catch:{ ClassCastException -> 0x02d8 }
            r3.b = r6     // Catch:{ ClassCastException -> 0x02d8 }
            android.graphics.Bitmap r4 = r4.g     // Catch:{ ClassCastException -> 0x02d8 }
            r3.c = r4     // Catch:{ ClassCastException -> 0x02d8 }
            r5.k = r3     // Catch:{ ClassCastException -> 0x02d8 }
            goto L_0x02e5
        L_0x01ba:
            com.autonavi.minimap.bundle.share.entity.ShareParam$b r4 = (com.autonavi.minimap.bundle.share.entity.ShareParam.b) r4     // Catch:{ ClassCastException -> 0x02d8 }
            dco$d r3 = new dco$d     // Catch:{ ClassCastException -> 0x02d8 }
            r3.<init>()     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r6 = r4.c     // Catch:{ ClassCastException -> 0x02d8 }
            r3.h = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.d     // Catch:{ ClassCastException -> 0x02d8 }
            r3.i = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.a     // Catch:{ ClassCastException -> 0x02d8 }
            r3.f = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.b     // Catch:{ ClassCastException -> 0x02d8 }
            r3.g = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.e     // Catch:{ ClassCastException -> 0x02d8 }
            r3.a = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.f     // Catch:{ ClassCastException -> 0x02d8 }
            r3.b = r6     // Catch:{ ClassCastException -> 0x02d8 }
            android.graphics.Bitmap r4 = r4.g     // Catch:{ ClassCastException -> 0x02d8 }
            r3.c = r4     // Catch:{ ClassCastException -> 0x02d8 }
            r5.j = r3     // Catch:{ ClassCastException -> 0x02d8 }
            goto L_0x02e5
        L_0x01e1:
            r3 = r4
            com.autonavi.minimap.bundle.share.entity.ShareParam$f r3 = (com.autonavi.minimap.bundle.share.entity.ShareParam.f) r3     // Catch:{ ClassCastException -> 0x02d8 }
            dco$k r6 = new dco$k     // Catch:{ ClassCastException -> 0x02d8 }
            r6.<init>()     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r8 = r4.c     // Catch:{ ClassCastException -> 0x02d8 }
            r6.h = r8     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r4.d     // Catch:{ ClassCastException -> 0x02d8 }
            r6.i = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r3.b     // Catch:{ ClassCastException -> 0x02d8 }
            r6.g = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r3.g     // Catch:{ ClassCastException -> 0x02d8 }
            r6.d = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r3.a     // Catch:{ ClassCastException -> 0x02d8 }
            r6.f = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r3.h     // Catch:{ ClassCastException -> 0x02d8 }
            r6.c = r4     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r4 = r3.j     // Catch:{ ClassCastException -> 0x02d8 }
            r6.j = r4     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r4 = r3.i     // Catch:{ ClassCastException -> 0x02d8 }
            r6.e = r4     // Catch:{ ClassCastException -> 0x02d8 }
            int r4 = r3.e     // Catch:{ ClassCastException -> 0x02d8 }
            r6.a = r4     // Catch:{ ClassCastException -> 0x02d8 }
            int r3 = r3.f     // Catch:{ ClassCastException -> 0x02d8 }
            r6.b = r3     // Catch:{ ClassCastException -> 0x02d8 }
            r5.e = r6     // Catch:{ ClassCastException -> 0x02d8 }
            goto L_0x02e5
        L_0x0215:
            com.autonavi.minimap.bundle.share.entity.ShareParam$e r4 = (com.autonavi.minimap.bundle.share.entity.ShareParam.e) r4     // Catch:{ ClassCastException -> 0x02d8 }
            dco$h r3 = new dco$h     // Catch:{ ClassCastException -> 0x02d8 }
            r3.<init>()     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r6 = r4.c     // Catch:{ ClassCastException -> 0x02d8 }
            r3.h = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.d     // Catch:{ ClassCastException -> 0x02d8 }
            r3.i = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.a     // Catch:{ ClassCastException -> 0x02d8 }
            r3.f = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.b     // Catch:{ ClassCastException -> 0x02d8 }
            r3.g = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.f     // Catch:{ ClassCastException -> 0x02d8 }
            r3.b = r6     // Catch:{ ClassCastException -> 0x02d8 }
            android.graphics.Bitmap r6 = r4.g     // Catch:{ ClassCastException -> 0x02d8 }
            r3.c = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.h     // Catch:{ ClassCastException -> 0x02d8 }
            r3.d = r6     // Catch:{ ClassCastException -> 0x02d8 }
            int r6 = r4.e     // Catch:{ ClassCastException -> 0x02d8 }
            r3.a = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.h     // Catch:{ ClassCastException -> 0x02d8 }
            r3.d = r6     // Catch:{ ClassCastException -> 0x02d8 }
            dcs r6 = r4.j     // Catch:{ ClassCastException -> 0x02d8 }
            r3.j = r6     // Catch:{ ClassCastException -> 0x02d8 }
            int r4 = r4.k     // Catch:{ ClassCastException -> 0x02d8 }
            r3.k = r4     // Catch:{ ClassCastException -> 0x02d8 }
            r5.g = r3     // Catch:{ ClassCastException -> 0x02d8 }
            goto L_0x02e5
        L_0x024c:
            com.autonavi.minimap.bundle.share.entity.ShareParam$e r4 = (com.autonavi.minimap.bundle.share.entity.ShareParam.e) r4     // Catch:{ ClassCastException -> 0x02d8 }
            dco$i r3 = new dco$i     // Catch:{ ClassCastException -> 0x02d8 }
            r3.<init>()     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r6 = r4.c     // Catch:{ ClassCastException -> 0x02d8 }
            r3.h = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.d     // Catch:{ ClassCastException -> 0x02d8 }
            r3.i = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.a     // Catch:{ ClassCastException -> 0x02d8 }
            r3.f = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.b     // Catch:{ ClassCastException -> 0x02d8 }
            r3.g = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.f     // Catch:{ ClassCastException -> 0x02d8 }
            r3.b = r6     // Catch:{ ClassCastException -> 0x02d8 }
            android.graphics.Bitmap r6 = r4.g     // Catch:{ ClassCastException -> 0x02d8 }
            r3.c = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.h     // Catch:{ ClassCastException -> 0x02d8 }
            r3.d = r6     // Catch:{ ClassCastException -> 0x02d8 }
            int r6 = r4.e     // Catch:{ ClassCastException -> 0x02d8 }
            r3.a = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.h     // Catch:{ ClassCastException -> 0x02d8 }
            r3.d = r6     // Catch:{ ClassCastException -> 0x02d8 }
            int r6 = r4.k     // Catch:{ ClassCastException -> 0x02d8 }
            r3.k = r6     // Catch:{ ClassCastException -> 0x02d8 }
            dcs r4 = r4.j     // Catch:{ ClassCastException -> 0x02d8 }
            r3.j = r4     // Catch:{ ClassCastException -> 0x02d8 }
            r5.f = r3     // Catch:{ ClassCastException -> 0x02d8 }
            goto L_0x02e5
        L_0x0282:
            r3 = r4
            com.autonavi.minimap.bundle.share.entity.ShareParam$c r3 = (com.autonavi.minimap.bundle.share.entity.ShareParam.c) r3     // Catch:{ ClassCastException -> 0x02d8 }
            dco$f r6 = new dco$f     // Catch:{ ClassCastException -> 0x02d8 }
            r6.<init>()     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r8 = r4.c     // Catch:{ ClassCastException -> 0x02d8 }
            r6.h = r8     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r4.d     // Catch:{ ClassCastException -> 0x02d8 }
            r6.i = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r3.e     // Catch:{ ClassCastException -> 0x02d8 }
            r6.a = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r3.b     // Catch:{ ClassCastException -> 0x02d8 }
            r6.g = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r3 = r3.a     // Catch:{ ClassCastException -> 0x02d8 }
            r6.f = r3     // Catch:{ ClassCastException -> 0x02d8 }
            r5.i = r6     // Catch:{ ClassCastException -> 0x02d8 }
            goto L_0x02e5
        L_0x02a1:
            r3 = r4
            com.autonavi.minimap.bundle.share.entity.ShareParam$a r3 = (com.autonavi.minimap.bundle.share.entity.ShareParam.a) r3     // Catch:{ ClassCastException -> 0x02d8 }
            dco$b r6 = new dco$b     // Catch:{ ClassCastException -> 0x02d8 }
            r6.<init>()     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r8 = r4.c     // Catch:{ ClassCastException -> 0x02d8 }
            r6.h = r8     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r4.d     // Catch:{ ClassCastException -> 0x02d8 }
            r6.i = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r3.a     // Catch:{ ClassCastException -> 0x02d8 }
            r6.f = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r3.b     // Catch:{ ClassCastException -> 0x02d8 }
            r6.g = r4     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r3 = r3.e     // Catch:{ ClassCastException -> 0x02d8 }
            r6.a = r3     // Catch:{ ClassCastException -> 0x02d8 }
            r5.d = r6     // Catch:{ ClassCastException -> 0x02d8 }
            goto L_0x02e5
        L_0x02c0:
            dco$g r3 = new dco$g     // Catch:{ ClassCastException -> 0x02d8 }
            r3.<init>()     // Catch:{ ClassCastException -> 0x02d8 }
            boolean r6 = r4.c     // Catch:{ ClassCastException -> 0x02d8 }
            r3.h = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.d     // Catch:{ ClassCastException -> 0x02d8 }
            r3.i = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r6 = r4.a     // Catch:{ ClassCastException -> 0x02d8 }
            r3.f = r6     // Catch:{ ClassCastException -> 0x02d8 }
            java.lang.String r4 = r4.b     // Catch:{ ClassCastException -> 0x02d8 }
            r3.g = r4     // Catch:{ ClassCastException -> 0x02d8 }
            r5.c = r3     // Catch:{ ClassCastException -> 0x02d8 }
            goto L_0x02e5
        L_0x02d8:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            java.lang.ClassCastException r2 = new java.lang.ClassCastException
            java.lang.String r3 = "please specify a valid type by sharetype!!!"
            r2.<init>(r3)
            throw r2
        L_0x02e5:
            r3 = r7
        L_0x02e6:
            int r4 = r2.e
            switch(r4) {
                case 0: goto L_0x06ab;
                case 1: goto L_0x0673;
                case 2: goto L_0x0622;
                case 3: goto L_0x05be;
                case 4: goto L_0x055a;
                case 5: goto L_0x04fb;
                case 6: goto L_0x049e;
                case 7: goto L_0x0438;
                case 8: goto L_0x03e7;
                case 9: goto L_0x0396;
                case 10: goto L_0x0345;
                case 11: goto L_0x02f4;
                default: goto L_0x02eb;
            }
        L_0x02eb:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x02f4:
            dco r3 = r1.e
            dco$a r3 = r3.h
            if (r3 == 0) goto L_0x033c
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x0322 }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x0322 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0322 }
            dco r4 = r1.e     // Catch:{ Exception -> 0x0322 }
            dco$a r4 = r4.h     // Catch:{ Exception -> 0x0322 }
            java.lang.String r4 = r4.g     // Catch:{ Exception -> 0x0322 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0322 }
            if (r4 != 0) goto L_0x0327
            java.lang.String r4 = "url"
            dco r5 = r1.e     // Catch:{ Exception -> 0x0322 }
            dco$a r5 = r5.h     // Catch:{ Exception -> 0x0322 }
            java.lang.String r5 = r5.g     // Catch:{ Exception -> 0x0322 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0322 }
            goto L_0x0327
        L_0x0322:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x0327:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            dcf r3 = new dcf
            dco r4 = r1.e
            dco$a r4 = r4.h
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x033c:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x0345:
            dco r3 = r1.e
            dco$f r3 = r3.i
            if (r3 == 0) goto L_0x038d
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x0373 }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x0373 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0373 }
            dco r4 = r1.e     // Catch:{ Exception -> 0x0373 }
            dco$f r4 = r4.i     // Catch:{ Exception -> 0x0373 }
            java.lang.String r4 = r4.g     // Catch:{ Exception -> 0x0373 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0373 }
            if (r4 != 0) goto L_0x0378
            java.lang.String r4 = "url"
            dco r5 = r1.e     // Catch:{ Exception -> 0x0373 }
            dco$f r5 = r5.i     // Catch:{ Exception -> 0x0373 }
            java.lang.String r5 = r5.g     // Catch:{ Exception -> 0x0373 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0373 }
            goto L_0x0378
        L_0x0373:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x0378:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            dcn r3 = new dcn
            dco r4 = r1.e
            dco$f r4 = r4.i
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x038d:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x0396:
            dco r3 = r1.e
            dco$e r3 = r3.k
            if (r3 == 0) goto L_0x03de
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x03c4 }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x03c4 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x03c4 }
            dco r4 = r1.e     // Catch:{ Exception -> 0x03c4 }
            dco$e r4 = r4.k     // Catch:{ Exception -> 0x03c4 }
            java.lang.String r4 = r4.g     // Catch:{ Exception -> 0x03c4 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x03c4 }
            if (r4 != 0) goto L_0x03c9
            java.lang.String r4 = "url"
            dco r5 = r1.e     // Catch:{ Exception -> 0x03c4 }
            dco$e r5 = r5.k     // Catch:{ Exception -> 0x03c4 }
            java.lang.String r5 = r5.g     // Catch:{ Exception -> 0x03c4 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x03c4 }
            goto L_0x03c9
        L_0x03c4:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x03c9:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            dcm r3 = new dcm
            dco r4 = r1.e
            dco$e r4 = r4.k
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x03de:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x03e7:
            dco r3 = r1.e
            dco$d r3 = r3.j
            if (r3 == 0) goto L_0x042f
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x0415 }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x0415 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0415 }
            dco r4 = r1.e     // Catch:{ Exception -> 0x0415 }
            dco$d r4 = r4.j     // Catch:{ Exception -> 0x0415 }
            java.lang.String r4 = r4.g     // Catch:{ Exception -> 0x0415 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0415 }
            if (r4 != 0) goto L_0x041a
            java.lang.String r4 = "url"
            dco r5 = r1.e     // Catch:{ Exception -> 0x0415 }
            dco$d r5 = r5.j     // Catch:{ Exception -> 0x0415 }
            java.lang.String r5 = r5.g     // Catch:{ Exception -> 0x0415 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0415 }
            goto L_0x041a
        L_0x0415:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x041a:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            dcl r3 = new dcl
            dco r4 = r1.e
            dco$d r4 = r4.j
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x042f:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x0438:
            if (r3 == 0) goto L_0x0495
            int r4 = r3.f
            if (r4 != 0) goto L_0x043f
            goto L_0x0495
        L_0x043f:
            java.lang.String r4 = r3.b
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x0450
            java.lang.String r4 = r3.b
            java.lang.String r5 = "&gd_from=weibo"
            java.lang.String r6 = ""
            r4.replace(r5, r6)
        L_0x0450:
            dck r4 = new dck
            java.lang.String r11 = r3.a
            java.lang.String r12 = r3.c
            java.lang.String r13 = r3.b
            boolean r14 = r3.d
            java.lang.String r15 = r3.e
            int r5 = r3.f
            r10 = r4
            r16 = r5
            r10.<init>(r11, r12, r13, r14, r15, r16)
            r4.startShare()
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            java.lang.String r5 = "type"
            int r6 = r2.e     // Catch:{ Exception -> 0x0487 }
            java.lang.String r6 = d(r6)     // Catch:{ Exception -> 0x0487 }
            r4.put(r5, r6)     // Catch:{ Exception -> 0x0487 }
            java.lang.String r5 = r3.b     // Catch:{ Exception -> 0x0487 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0487 }
            if (r5 != 0) goto L_0x048c
            java.lang.String r5 = "url"
            java.lang.String r3 = r3.b     // Catch:{ Exception -> 0x0487 }
            r4.put(r5, r3)     // Catch:{ Exception -> 0x0487 }
            goto L_0x048c
        L_0x0487:
            r0 = move-exception
            r3 = r0
            defpackage.kf.a(r3)
        L_0x048c:
            java.lang.String r3 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r3, r5, r4)
            goto L_0x06f9
        L_0x0495:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x049e:
            if (r7 == 0) goto L_0x04e5
            java.lang.String r3 = r7.a
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 == 0) goto L_0x04a9
            goto L_0x04e5
        L_0x04a9:
            dce r3 = new dce
            java.lang.String r4 = r7.a
            boolean r5 = r7.b
            java.lang.String r6 = r7.c
            r3.<init>(r4, r5, r6)
            r3.startShare()
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x04d7 }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x04d7 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x04d7 }
            java.lang.String r4 = r7.a     // Catch:{ Exception -> 0x04d7 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x04d7 }
            if (r4 != 0) goto L_0x04dc
            java.lang.String r4 = "url"
            java.lang.String r5 = r7.a     // Catch:{ Exception -> 0x04d7 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x04d7 }
            goto L_0x04dc
        L_0x04d7:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x04dc:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            goto L_0x06f9
        L_0x04e5:
            android.content.Context r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            int r4 = com.autonavi.minimap.R.string.copy_link_share_get_content_failed
            java.lang.String r3 = r3.getString(r4)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r3)
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x04fb:
            dco r3 = r1.e
            dco$k r3 = r3.e
            if (r3 == 0) goto L_0x054a
            java.lang.String r3 = "ShareDatahandler"
            java.lang.String r4 = "sina will be shared"
            defpackage.ddl.a(r3, r4)
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x0530 }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x0530 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0530 }
            dco r4 = r1.e     // Catch:{ Exception -> 0x0530 }
            dco$k r4 = r4.e     // Catch:{ Exception -> 0x0530 }
            java.lang.String r4 = r4.g     // Catch:{ Exception -> 0x0530 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0530 }
            if (r4 != 0) goto L_0x0535
            java.lang.String r4 = "url"
            dco r5 = r1.e     // Catch:{ Exception -> 0x0530 }
            dco$k r5 = r5.e     // Catch:{ Exception -> 0x0530 }
            java.lang.String r5 = r5.g     // Catch:{ Exception -> 0x0530 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0530 }
            goto L_0x0535
        L_0x0530:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x0535:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            com.autonavi.minimap.bundle.share.entity.WeiboShare r3 = new com.autonavi.minimap.bundle.share.entity.WeiboShare
            dco r4 = r1.e
            dco$k r4 = r4.e
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x054a:
            java.lang.String r3 = "ShareDatahandler"
            java.lang.String r4 = "sina remove"
            defpackage.ddl.a(r3, r4)
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x055a:
            dco r3 = r1.e
            dco$h r3 = r3.g
            if (r3 == 0) goto L_0x05b5
            dco r3 = r1.e
            dco$h r3 = r3.g
            int r3 = r3.k
            if (r3 != r9) goto L_0x0573
            ddc r3 = defpackage.ddc.a.a
            dco r4 = r1.e
            dco$h r4 = r4.g
            r3.a(r4)
            goto L_0x06f9
        L_0x0573:
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x059b }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x059b }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x059b }
            dco r4 = r1.e     // Catch:{ Exception -> 0x059b }
            dco$h r4 = r4.g     // Catch:{ Exception -> 0x059b }
            java.lang.String r4 = r4.g     // Catch:{ Exception -> 0x059b }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x059b }
            if (r4 != 0) goto L_0x05a0
            java.lang.String r4 = "url"
            dco r5 = r1.e     // Catch:{ Exception -> 0x059b }
            dco$h r5 = r5.g     // Catch:{ Exception -> 0x059b }
            java.lang.String r5 = r5.g     // Catch:{ Exception -> 0x059b }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x059b }
            goto L_0x05a0
        L_0x059b:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x05a0:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            dcv r3 = new dcv
            dco r4 = r1.e
            dco$h r4 = r4.g
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x05b5:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x05be:
            dco r3 = r1.e
            dco$i r3 = r3.f
            if (r3 == 0) goto L_0x0619
            dco r3 = r1.e
            dco$i r3 = r3.f
            int r3 = r3.k
            if (r3 != r9) goto L_0x05d7
            ddc r3 = defpackage.ddc.a.a
            dco r4 = r1.e
            dco$i r4 = r4.f
            r3.a(r4)
            goto L_0x06f9
        L_0x05d7:
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x05ff }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x05ff }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x05ff }
            dco r4 = r1.e     // Catch:{ Exception -> 0x05ff }
            dco$i r4 = r4.f     // Catch:{ Exception -> 0x05ff }
            java.lang.String r4 = r4.g     // Catch:{ Exception -> 0x05ff }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x05ff }
            if (r4 != 0) goto L_0x0604
            java.lang.String r4 = "url"
            dco r5 = r1.e     // Catch:{ Exception -> 0x05ff }
            dco$i r5 = r5.f     // Catch:{ Exception -> 0x05ff }
            java.lang.String r5 = r5.g     // Catch:{ Exception -> 0x05ff }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x05ff }
            goto L_0x0604
        L_0x05ff:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x0604:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            dcv r3 = new dcv
            dco r4 = r1.e
            dco$i r4 = r4.f
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x0619:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x0622:
            dco r3 = r1.e
            dco$f r3 = r3.i
            if (r3 == 0) goto L_0x066a
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x0650 }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x0650 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0650 }
            dco r4 = r1.e     // Catch:{ Exception -> 0x0650 }
            dco$f r4 = r4.i     // Catch:{ Exception -> 0x0650 }
            java.lang.String r4 = r4.g     // Catch:{ Exception -> 0x0650 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x0650 }
            if (r4 != 0) goto L_0x0655
            java.lang.String r4 = "url"
            dco r5 = r1.e     // Catch:{ Exception -> 0x0650 }
            dco$f r5 = r5.i     // Catch:{ Exception -> 0x0650 }
            java.lang.String r5 = r5.g     // Catch:{ Exception -> 0x0650 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x0650 }
            goto L_0x0655
        L_0x0650:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x0655:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            com.autonavi.minimap.bundle.share.entity.SendToCarShare r3 = new com.autonavi.minimap.bundle.share.entity.SendToCarShare
            dco r4 = r1.e
            dco$f r4 = r4.i
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x066a:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x0673:
            dco r3 = r1.e
            dco$b r3 = r3.d
            if (r3 == 0) goto L_0x06a3
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x068a }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x068a }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x068a }
            goto L_0x068f
        L_0x068a:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x068f:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            dcg r3 = new dcg
            dco r4 = r1.e
            dco$b r4 = r4.d
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x06a3:
            ddi r3 = defpackage.ddi.a()
            r3.b()
            goto L_0x06f9
        L_0x06ab:
            dco r3 = r1.e
            dco$g r3 = r3.c
            if (r3 == 0) goto L_0x06f2
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            java.lang.String r4 = "type"
            int r5 = r2.e     // Catch:{ Exception -> 0x06d9 }
            java.lang.String r5 = d(r5)     // Catch:{ Exception -> 0x06d9 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x06d9 }
            dco r4 = r1.e     // Catch:{ Exception -> 0x06d9 }
            dco$g r4 = r4.c     // Catch:{ Exception -> 0x06d9 }
            java.lang.String r4 = r4.g     // Catch:{ Exception -> 0x06d9 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x06d9 }
            if (r4 != 0) goto L_0x06de
            java.lang.String r4 = "url"
            dco r5 = r1.e     // Catch:{ Exception -> 0x06d9 }
            dco$g r5 = r5.c     // Catch:{ Exception -> 0x06d9 }
            java.lang.String r5 = r5.g     // Catch:{ Exception -> 0x06d9 }
            r3.put(r4, r5)     // Catch:{ Exception -> 0x06d9 }
            goto L_0x06de
        L_0x06d9:
            r0 = move-exception
            r4 = r0
            defpackage.kf.a(r4)
        L_0x06de:
            java.lang.String r4 = "P00106"
            java.lang.String r5 = "B007"
            com.amap.bundle.statistics.LogManager.actionLogV2(r4, r5, r3)
            dcu r3 = new dcu
            dco r4 = r1.e
            dco$g r4 = r4.c
            r3.<init>(r4)
            r3.startShare()
            goto L_0x06f9
        L_0x06f2:
            ddi r3 = defpackage.ddi.a()
            r3.b()
        L_0x06f9:
            dcd r3 = r1.g
            if (r3 == 0) goto L_0x0704
            dcd r3 = r1.g
            int r2 = r2.e
            r3.onEntrySelected(r2)
        L_0x0704:
            r1.j = r9
        L_0x0706:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ddj.a(com.autonavi.map.fragmentcontainer.IViewLayer, dcr):void");
    }
}
