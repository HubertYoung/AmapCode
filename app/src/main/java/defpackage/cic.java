package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.Router;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.agroup.ajx.ModuleAgroup;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.widget.ConfirmDlgLifeCircle;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"AGroup"})
/* renamed from: cic reason: default package */
/* compiled from: AgroupRouter */
public class cic extends esk {
    private static String a(Uri uri) {
        try {
            return uri.getQueryParameter("clearStack");
        } catch (Exception unused) {
            r2 = "";
            return "";
        }
    }

    private static String b(Uri uri) {
        try {
            return uri.getQueryParameter("loginCheck");
        } catch (Exception unused) {
            r2 = "";
            return "";
        }
    }

    private static String c(Uri uri) {
        try {
            return uri.getQueryParameter("nickname");
        } catch (Exception e) {
            e.printStackTrace();
            r2 = "";
            return "";
        }
    }

    private static String d(Uri uri) {
        try {
            return uri.getQueryParameter("teamNumber");
        } catch (Exception e) {
            e.printStackTrace();
            r2 = "";
            return "";
        }
    }

    /* access modifiers changed from: private */
    public void a(Intent intent) {
        if (!cjq.a() || intent == null) {
            a((String) "");
        } else {
            b(intent.getStringExtra("page_data_key"));
        }
    }

    private void a(String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_agroup/src/BizBasemapAgroupJoinTeamPage.page.js");
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("jsData", str);
        }
        startPage(Ajx3Page.class, pageBundle);
    }

    /* access modifiers changed from: private */
    public void a() {
        String str;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", ModuleAgroup.GROUP_ANNOUNCEMENT_PATH);
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("forceUpdate", true);
            str = jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str = null;
        }
        pageBundle.putString("jsData", str);
        startPage(Ajx3Page.class, pageBundle);
    }

    /* JADX WARNING: type inference failed for: r4v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r4v4, types: [org.json.JSONObject] */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.lang.String r4) {
        /*
            r3 = this;
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "path://amap_bundle_agroup/src/BizBasemapAgroupMyGroup.page.js"
            java.lang.String r2 = "url"
            r0.putString(r2, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 != 0) goto L_0x0013
            goto L_0x0017
        L_0x0013:
            org.json.JSONObject r4 = defpackage.cju.a()
        L_0x0017:
            if (r4 == 0) goto L_0x0022
            java.lang.String r1 = "jsData"
            java.lang.String r4 = r4.toString()
            r0.putObject(r1, r4)
        L_0x0022:
            java.lang.Class<com.autonavi.minimap.agroup.page.MyGroupMapPage> r4 = com.autonavi.minimap.agroup.page.MyGroupMapPage.class
            r3.startPage(r4, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cic.b(java.lang.String):void");
    }

    private static boolean c() {
        if (cih.a().c()) {
            return true;
        }
        ToastHelper.showToast("功能暂时下线");
        return false;
    }

    private static void d() {
        try {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
            }
            ConfirmDlgLifeCircle.removeAll();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            AMapLog.e("BaseIntentDispatcher", sb.toString());
        }
    }

    private static boolean e() {
        dfm dfm = (dfm) ank.a(dfm.class);
        return dfm != null && dfm.b();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00c4, code lost:
        if (r3.equals(r1) != false) goto L_0x01aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00e2, code lost:
        if (r1.equals(r3.nickname) != false) goto L_0x01aa;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean start(defpackage.ese r7) {
        /*
            r6 = this;
            r0 = 0
            if (r7 != 0) goto L_0x0005
            r1 = r0
            goto L_0x0007
        L_0x0005:
            android.net.Uri r1 = r7.a
        L_0x0007:
            r2 = 0
            if (r1 != 0) goto L_0x000b
            return r2
        L_0x000b:
            java.util.List r3 = r1.getPathSegments()
            if (r3 == 0) goto L_0x01ac
            int r4 = r3.size()
            if (r4 <= 0) goto L_0x01ac
            android.content.Intent r7 = r7.b
            java.lang.Object r3 = r3.get(r2)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 != 0) goto L_0x0022
            return r2
        L_0x0022:
            r4 = 1
            java.lang.String r5 = "joinGroup"
            boolean r5 = android.text.TextUtils.equals(r5, r3)
            if (r5 != 0) goto L_0x0129
            java.lang.String r5 = "myGroup"
            boolean r5 = android.text.TextUtils.equals(r5, r3)
            if (r5 == 0) goto L_0x0035
            goto L_0x0129
        L_0x0035:
            java.lang.String r7 = "announcement"
            boolean r7 = android.text.TextUtils.equals(r7, r3)
            if (r7 == 0) goto L_0x0079
            boolean r7 = c()
            if (r7 == 0) goto L_0x01aa
            java.lang.String r7 = "1"
            java.lang.String r0 = a(r1)
            boolean r7 = r7.equalsIgnoreCase(r0)
            if (r7 == 0) goto L_0x0059
            boolean r7 = e()
            if (r7 != 0) goto L_0x0059
            d()
            goto L_0x0064
        L_0x0059:
            java.lang.String r7 = "path://amap_bundle_agroup/src/BizBasemapAGroupNoticePage.page.js"
            bid r7 = defpackage.cjp.a(r7)
            if (r7 == 0) goto L_0x0064
            r7.finish()
        L_0x0064:
            boolean r7 = b()
            if (r7 == 0) goto L_0x006f
            r6.a()
            goto L_0x01aa
        L_0x006f:
            cic$2 r7 = new cic$2
            r7.<init>()
            a(r7)
            goto L_0x01aa
        L_0x0079:
            java.lang.String r7 = "dealWithPassphrase"
            boolean r7 = android.text.TextUtils.equals(r7, r3)
            if (r7 == 0) goto L_0x01ab
            cih r7 = defpackage.cih.a()
            boolean r7 = r7.c()
            if (r7 == 0) goto L_0x01aa
            cii r7 = defpackage.cii.a.a
            java.lang.String r2 = d(r1)
            java.lang.String r1 = c(r1)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x01aa
            esb r3 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.bundle.account.api.IAccountService> r5 = com.autonavi.bundle.account.api.IAccountService.class
            esc r3 = r3.a(r5)
            com.autonavi.bundle.account.api.IAccountService r3 = (com.autonavi.bundle.account.api.IAccountService) r3
            if (r3 == 0) goto L_0x01aa
            ant r5 = r3.e()
            boolean r3 = r3.a()
            if (r3 == 0) goto L_0x00c6
            if (r5 != 0) goto L_0x00b8
            java.lang.String r3 = ""
            goto L_0x00ba
        L_0x00b8:
            java.lang.String r3 = r5.e
        L_0x00ba:
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            if (r5 != 0) goto L_0x00c6
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L_0x01aa
        L_0x00c6:
            cjt r3 = defpackage.cjt.a()
            if (r3 == 0) goto L_0x00e4
            com.autonavi.minimap.agroup.entity.MemberInfo r3 = r3.g()
            if (r3 == 0) goto L_0x00e4
            com.autonavi.minimap.agroup.entity.MemberInfo r3 = r3.clone()
            boolean r5 = android.text.TextUtils.isEmpty(r1)
            if (r5 != 0) goto L_0x00e4
            java.lang.String r3 = r3.nickname
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x01aa
        L_0x00e4:
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = r1.trim()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0100
            java.lang.String r3 = r1.toLowerCase()
            java.lang.String r5 = "amap_"
            boolean r3 = r3.startsWith(r5)
            if (r3 == 0) goto L_0x0102
        L_0x0100:
            java.lang.String r1 = "您的好友"
        L_0x0102:
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x01aa
            android.content.Context r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            if (r3 == 0) goto L_0x011f
            boolean r5 = defpackage.aaw.c(r3)
            if (r5 != 0) goto L_0x011f
            int r7 = com.autonavi.minimap.R.string.network_error
            java.lang.String r7 = r3.getString(r7)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r7)
            goto L_0x01aa
        L_0x011f:
            cii$1 r5 = new cii$1
            r5.<init>(r3, r2, r1)
            defpackage.cin.a(r2, r5, r0)
            goto L_0x01aa
        L_0x0129:
            boolean r2 = c()
            if (r2 == 0) goto L_0x01aa
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            boolean r3 = r2 instanceof com.autonavi.map.fragmentcontainer.page.AbstractBasePage
            if (r3 == 0) goto L_0x0142
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r2 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r2
            com.autonavi.map.fragmentcontainer.page.PageContainer r2 = r2.getPageContainer()
            if (r2 == 0) goto L_0x0142
            r2.getCureentRecordPage()
        L_0x0142:
            java.lang.String r2 = "1"
            java.lang.String r3 = a(r1)
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0158
            boolean r2 = e()
            if (r2 != 0) goto L_0x0158
            d()
            goto L_0x0188
        L_0x0158:
            java.lang.String r2 = "path://amap_bundle_agroup/src/BizBasemapAgroupJoinTeamPage.page.js"
            bid r2 = defpackage.cjp.a(r2)
            if (r2 == 0) goto L_0x0163
            r2.finish()
        L_0x0163:
            java.lang.String r2 = "path://amap_bundle_agroup/src/BizBasemapAgroupMyGroup.page.js"
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x0183
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r3 == 0) goto L_0x0183
            boolean r5 = r3 instanceof com.autonavi.minimap.ajx3.Ajx3Page
            if (r5 == 0) goto L_0x0183
            r5 = r3
            com.autonavi.minimap.ajx3.Ajx3Page r5 = (com.autonavi.minimap.ajx3.Ajx3Page) r5
            java.lang.String r5 = r5.getAjx3Url()
            boolean r2 = android.text.TextUtils.equals(r5, r2)
            if (r2 == 0) goto L_0x0183
            r0 = r3
        L_0x0183:
            if (r0 == 0) goto L_0x0188
            r0.finish()
        L_0x0188:
            java.lang.String r0 = "1"
            java.lang.String r1 = b(r1)
            boolean r0 = r0.equalsIgnoreCase(r1)
            if (r0 == 0) goto L_0x01a7
            boolean r0 = b()
            if (r0 == 0) goto L_0x019e
            r6.a(r7)
            goto L_0x01aa
        L_0x019e:
            cic$1 r0 = new cic$1
            r0.<init>(r7)
            a(r0)
            goto L_0x01aa
        L_0x01a7:
            r6.a(r7)
        L_0x01aa:
            r2 = 1
        L_0x01ab:
            return r2
        L_0x01ac:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cic.start(ese):boolean");
    }

    private static boolean b() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    private static void a(anq anq) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                iAccountService.a(pageContext, anq);
            }
        }
    }
}
