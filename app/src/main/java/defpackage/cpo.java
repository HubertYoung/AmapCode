package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* renamed from: cpo reason: default package */
/* compiled from: OperationIntentDispatcherImpl */
public class cpo extends BaseIntentDispatcher implements cpn {
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r1v1, types: [int] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r5v2, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v4, types: [boolean] */
    /* JADX WARNING: type inference failed for: r5v3 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r11v39, types: [com.amap.bundle.datamodel.FavoritePOI] */
    /* JADX WARNING: type inference failed for: r5v4 */
    /* JADX WARNING: type inference failed for: r11v44, types: [com.amap.bundle.datamodel.FavoritePOI] */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r5v10, types: [com.autonavi.map.db.model.Car] */
    /* JADX WARNING: type inference failed for: r5v13, types: [com.autonavi.map.db.model.Car] */
    /* JADX WARNING: type inference failed for: r1v87 */
    /* JADX WARNING: type inference failed for: r1v88 */
    /* JADX WARNING: type inference failed for: r1v89 */
    /* JADX WARNING: type inference failed for: r5v29 */
    /* JADX WARNING: type inference failed for: r1v90 */
    /* JADX WARNING: type inference failed for: r5v30 */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x029a, code lost:
        if (r11 == false) goto L_0x011b;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v3
      assigns: []
      uses: []
      mth insns count: 756
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x03fb A[RETURN] */
    /* JADX WARNING: Unknown variable types count: 9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(@android.support.annotation.NonNull android.content.Intent r11) {
        /*
            r10 = this;
            android.net.Uri r0 = r11.getData()
            r1 = 0
            if (r0 != 0) goto L_0x0011
            java.lang.String r11 = "basemap.operation"
            java.lang.String r0 = "OperationIntentDispatcherImpl"
            java.lang.String r2 = "dispatch，uri is null!"
            com.amap.bundle.logs.AMapLog.info(r11, r0, r2)
            return r1
        L_0x0011:
            java.lang.String r2 = r0.getHost()
            r3 = 1
            if (r0 == 0) goto L_0x00ba
            boolean r4 = r0.isHierarchical()
            if (r4 == 0) goto L_0x00ba
            java.lang.String r4 = r0.getScheme()
            if (r4 == 0) goto L_0x00ba
            java.lang.String r4 = "scme2017122201358023c49cf2"
            java.lang.String r5 = r0.getScheme()
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x003c
            java.lang.String r4 = "scme20171218009637581187f3"
            java.lang.String r5 = r0.getScheme()
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x00ba
        L_0x003c:
            java.lang.String r0 = "auth"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0ad1
            java.lang.String r0 = r11.getAction()
            android.net.Uri r11 = r11.getData()
            java.lang.String r2 = "OperationIntentDispatcherImpl. initZhiMaAuthResult. uri: %s."
            java.lang.Object[] r4 = new java.lang.Object[r3]
            if (r11 == 0) goto L_0x0057
            java.lang.String r5 = r11.toString()
            goto L_0x0059
        L_0x0057:
            java.lang.String r5 = ""
        L_0x0059:
            r4[r1] = r5
            operation.pay.AliSignTools.a(r2, r4)
            java.lang.String r1 = "android.intent.action.VIEW"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00b9
            if (r11 == 0) goto L_0x00b9
            java.lang.String r0 = r11.getAuthority()
            java.lang.String r1 = "auth"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00b9
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r1 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r0.<init>(r1)
            java.lang.String r1 = "zhima_sign_tranid"
            java.lang.String r2 = ""
            r0.getStringValue(r1, r2)
            java.lang.String r1 = "zhima_sign_role"
            java.lang.String r2 = ""
            java.lang.String r0 = r0.getStringValue(r1, r2)
            java.lang.String r1 = "auth_code"
            java.lang.String r1 = r11.getQueryParameter(r1)
            java.lang.String r2 = "state"
            java.lang.String r11 = r11.getQueryParameter(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x00b0
            boolean r2 = android.text.TextUtils.isEmpty(r11)
            if (r2 != 0) goto L_0x00b0
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x00b0
            fhg r2 = defpackage.fhg.a()
            r2.a(r0, r1, r11)
            goto L_0x00b9
        L_0x00b0:
            fhg r11 = defpackage.fhg.a()
            java.lang.String r0 = "102"
            r11.a(r0)
        L_0x00b9:
            return r3
        L_0x00ba:
            if (r0 == 0) goto L_0x0ad1
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x0ad1
            java.lang.String r4 = r0.getHost()
            java.util.List r5 = r0.getPathSegments()
            java.lang.String r6 = "reportTrafficEvent"
            boolean r4 = android.text.TextUtils.equals(r4, r6)
            if (r4 == 0) goto L_0x00fe
            if (r5 == 0) goto L_0x00fe
            boolean r4 = r5.isEmpty()
            if (r4 == 0) goto L_0x00db
            goto L_0x00fe
        L_0x00db:
            java.lang.Object r4 = r5.get(r1)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r5 = "main"
            boolean r4 = android.text.TextUtils.equals(r4, r5)
            if (r4 == 0) goto L_0x00fe
            com.autonavi.common.PageBundle r4 = new com.autonavi.common.PageBundle
            r4.<init>()
            java.lang.String r5 = "path://amap_bundle_basemap_feedback/src/feedbackhome/FeedbackHomePage.page.js"
            java.lang.String r6 = "url"
            r4.putString(r6, r5)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r5 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r6 = 99
            r10.startPageForResult(r5, r4, r6)
            r4 = 1
            goto L_0x00ff
        L_0x00fe:
            r4 = 0
        L_0x00ff:
            if (r4 == 0) goto L_0x0102
            return r3
        L_0x0102:
            java.lang.String r4 = "openFeature"
            boolean r4 = r2.equalsIgnoreCase(r4)
            r5 = 0
            r6 = 2
            if (r4 == 0) goto L_0x03fc
            android.net.Uri r11 = r11.getData()
            if (r11 != 0) goto L_0x011e
            java.lang.String r11 = "basemap.operation"
            java.lang.String r0 = "OperationIntentDispatcherImpl"
            java.lang.String r2 = "doOpenFeature，uri is null!"
            com.amap.bundle.logs.AMapLog.info(r11, r0, r2)
        L_0x011b:
            r11 = 0
            goto L_0x03f9
        L_0x011e:
            java.lang.String r0 = "featureName"
            java.lang.String r0 = r11.getQueryParameter(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L_0x0139
            android.app.Application r11 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = com.autonavi.minimap.R.string.intent_open_fail_param_error
            java.lang.String r11 = r11.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r11)
            goto L_0x03f8
        L_0x0139:
            java.lang.String r2 = "Feedback"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x017b
            java.lang.String r0 = "type"
            java.lang.String r0 = r11.getQueryParameter(r0)
            java.lang.String r2 = "recordId"
            java.lang.String r11 = r11.getQueryParameter(r2)
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0166
            java.lang.Class<com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter> r11 = com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter.class
            java.lang.Object r11 = defpackage.ank.a(r11)
            com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter r11 = (com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter) r11
            if (r11 == 0) goto L_0x03f8
            bid r0 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r11.startFeedback(r0)
            goto L_0x03f8
        L_0x0166:
            boolean r0 = b()
            if (r0 == 0) goto L_0x0171
            d(r11)
            goto L_0x03f8
        L_0x0171:
            cpo$6 r0 = new cpo$6
            r0.<init>(r11)
            a(r0)
            goto L_0x03f8
        L_0x017b:
            java.lang.String r2 = "UsefulAddress"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x0188
            e()
            goto L_0x03f8
        L_0x0188:
            java.lang.String r2 = "NaviPay"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x01c6
            java.lang.String r0 = "type"
            java.lang.String r11 = r11.getQueryParameter(r0)
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            boolean r2 = android.text.TextUtils.isEmpty(r11)
            if (r2 != 0) goto L_0x01bb
            java.lang.String r2 = "NotApply"
            boolean r2 = r11.contentEquals(r2)
            if (r2 == 0) goto L_0x01ae
            java.lang.String r2 = "showPage"
            r0.putInt(r2, r3)
        L_0x01ae:
            java.lang.String r2 = "Applied"
            boolean r11 = r11.contentEquals(r2)
            if (r11 == 0) goto L_0x01bb
            java.lang.String r11 = "showPage"
            r0.putInt(r11, r6)
        L_0x01bb:
            bid r11 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.String r2 = "apply_pay_main_page"
            r11.startPage(r2, r0)
            goto L_0x03f8
        L_0x01c6:
            java.lang.String r2 = "Mine"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x029e
            java.lang.String r0 = "page"
            java.lang.String r0 = r11.getQueryParameter(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x024a
            java.lang.String r2 = "ToolBox"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x024a
            java.lang.String r0 = "item"
            java.lang.String r11 = r11.getQueryParameter(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x0248
            java.lang.String r0 = "Address"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x01fa
            e()
            goto L_0x0248
        L_0x01fa:
            java.lang.String r0 = "Favorite"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x0219
            com.autonavi.common.PageBundle r11 = new com.autonavi.common.PageBundle
            r11.<init>()
            esb r0 = defpackage.esb.a.a
            java.lang.Class<auz> r2 = defpackage.auz.class
            esc r0 = r0.a(r2)
            auz r0 = (defpackage.auz) r0
            if (r0 == 0) goto L_0x0248
            r0.a(r11)
            goto L_0x0248
        L_0x0219:
            java.lang.String r0 = "TrafficJam"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x022d
            bid r11 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r11 == 0) goto L_0x0248
            java.lang.String r0 = "amap.basemap.action.traffic_board"
            r11.startPage(r0, r5)
            goto L_0x0248
        L_0x022d:
            java.lang.String r0 = "MainCompensate"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0264
            com.autonavi.common.PageBundle r11 = new com.autonavi.common.PageBundle
            r11.<init>()
            java.lang.String r0 = "showPage"
            r11.putInt(r0, r6)
            bid r0 = r10.getPageContext()
            java.lang.String r2 = "apply_pay_main_page"
            r0.startPage(r2, r11)
        L_0x0248:
            r11 = 1
            goto L_0x029a
        L_0x024a:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x0274
            java.lang.String r2 = "Fortune"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0274
            java.lang.String r0 = "item"
            java.lang.String r11 = r11.getQueryParameter(r0)
            boolean r11 = android.text.TextUtils.isEmpty(r11)
            if (r11 != 0) goto L_0x0266
        L_0x0264:
            r11 = 0
            goto L_0x029a
        L_0x0266:
            android.app.Application r11 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = com.autonavi.minimap.R.string.intent_open_fail
            java.lang.String r11 = r11.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r11)
            goto L_0x0248
        L_0x0274:
            bid r11 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r11 == 0) goto L_0x0248
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r2 = "url"
            java.lang.String r4 = "path://amap_bundle_mine/src/pages/MinePage.page.js"
            r0.putString(r2, r4)
            java.lang.String r2 = "jsData"
            bnv r4 = new bnv
            r4.<init>()
            java.lang.String r4 = defpackage.bnv.b()
            r0.putString(r2, r4)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r2 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r11.startPage(r2, r0)
            goto L_0x0248
        L_0x029a:
            if (r11 != 0) goto L_0x03f8
            goto L_0x011b
        L_0x029e:
            java.lang.String r2 = "carservice"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x02ab
            r10.c()
            goto L_0x03f8
        L_0x02ab:
            java.lang.String r2 = "OpenURL"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x03d2
            java.lang.String r0 = "url"
            java.lang.String r0 = r11.getQueryParameter(r0)
            if (r0 == 0) goto L_0x039c
            java.lang.String r2 = "activity/wzcx/index.html"
            boolean r2 = r0.contains(r2)
            if (r2 != 0) goto L_0x02c5
            goto L_0x039c
        L_0x02c5:
            boolean r0 = b()
            if (r0 == 0) goto L_0x036a
            java.lang.String r0 = "url"
            java.lang.String r11 = r11.getQueryParameter(r0)
            java.lang.String r0 = "ISO-8859-1"
            java.util.Map r11 = defpackage.agt.a(r11, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "trafficViolations/index.html?type=queryNative&userId="
            r0.<init>(r2)
            java.lang.String r2 = "u"
            java.lang.Object r2 = r11.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            r0.append(r2)
            java.lang.String r2 = "&carNumber="
            r0.append(r2)
            java.lang.String r2 = "n"
            java.lang.Object r2 = r11.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            r0.append(r2)
            java.lang.String r2 = "&carDriver="
            r0.append(r2)
            java.lang.String r2 = "f"
            java.lang.Object r2 = r11.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            r0.append(r2)
            java.lang.String r2 = "&carCode="
            r0.append(r2)
            java.lang.String r2 = "m"
            java.lang.Object r2 = r11.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            r0.append(r2)
            java.lang.String r2 = "&telephone="
            r0.append(r2)
            java.lang.String r2 = "t"
            java.lang.Object r2 = r11.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            r0.append(r2)
            java.lang.String r2 = "&dataSource="
            r0.append(r2)
            java.lang.String r2 = "dataSource"
            java.lang.Object r11 = r11.get(r2)
            java.lang.String r11 = (java.lang.String) r11
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "amapuri://webview/local?hide_title=1&url="
            r0.<init>(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r11)
            if (r2 == 0) goto L_0x034d
            java.lang.String r11 = ""
            goto L_0x0353
        L_0x034d:
            java.lang.String r2 = ""
            java.lang.String r11 = defpackage.ahj.a(r11, r2)
        L_0x0353:
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            android.net.Uri r11 = android.net.Uri.parse(r11)
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r2 = "android.intent.action.VIEW"
            r0.<init>(r2, r11)
            com.autonavi.map.fragmentcontainer.page.DoNotUseTool.startScheme(r0)
            goto L_0x03f8
        L_0x036a:
            com.autonavi.minimap.ajx3.CarInfo r0 = new com.autonavi.minimap.ajx3.CarInfo
            r0.<init>()
            java.lang.String r2 = "Violation"
            r0.entryType = r2
            java.lang.String r2 = "1"
            r0.needOpenUri = r2
            java.lang.String r11 = r11.toString()
            r0.outUri = r11
            com.autonavi.common.PageBundle r11 = new com.autonavi.common.PageBundle
            r11.<init>()
            java.lang.String r2 = "url"
            java.lang.String r4 = "path://amap_bundle_carowner/src/car_owner/CarAddViewController.page.js"
            r11.putString(r2, r4)
            java.lang.String r2 = "jsData"
            org.json.JSONObject r0 = com.autonavi.minimap.ajx3.CarInfo.CarInfoToJson(r0)
            java.lang.String r0 = r0.toString()
            r11.putString(r2, r0)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r0 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r10.startPage(r0, r11)
            goto L_0x03f8
        L_0x039c:
            if (r0 == 0) goto L_0x011b
            java.lang.String r11 = "isInAmap=1&gd_from=openlayer"
            boolean r11 = r0.contains(r11)
            if (r11 == 0) goto L_0x011b
            boolean r11 = defpackage.ajn.a(r0)
            if (r11 != 0) goto L_0x03b0
            java.lang.String r0 = defpackage.ajn.b(r0)
        L_0x03b0:
            aja r11 = new aja
            r11.<init>(r0)
            cpo$5 r0 = new cpo$5
            r0.<init>()
            r11.b = r0
            esb r0 = defpackage.esb.a.a
            java.lang.Class<aix> r2 = defpackage.aix.class
            esc r0 = r0.a(r2)
            aix r0 = (defpackage.aix) r0
            if (r0 == 0) goto L_0x03f8
            bid r2 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r0.a(r2, r11)
            goto L_0x03f8
        L_0x03d2:
            java.lang.String r11 = "DriveAchievement"
            boolean r11 = r0.equalsIgnoreCase(r11)
            if (r11 == 0) goto L_0x011b
            bid r11 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r11 == 0) goto L_0x03f8
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r2 = "url"
            java.lang.String r4 = "path://amap_bundle_drive_achievement/src/pages/BizDriveAchievementIndex.page.js"
            r0.putString(r2, r4)
            java.lang.String r2 = "pageId"
            java.lang.String r4 = "BizDriveAchievementIndex"
            r0.putString(r2, r4)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r2 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r11.startPage(r2, r0)
        L_0x03f8:
            r11 = 1
        L_0x03f9:
            if (r11 != 0) goto L_0x0779
            return r1
        L_0x03fc:
            java.lang.String r4 = "carownerservice"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x06be
            java.util.List r11 = r0.getPathSegments()
            if (r11 == 0) goto L_0x06bd
            boolean r2 = r11.isEmpty()
            if (r2 == 0) goto L_0x0412
            goto L_0x06bd
        L_0x0412:
            java.lang.Object r11 = r11.get(r1)
            java.lang.String r11 = (java.lang.String) r11
            java.lang.String r1 = "homepage"
            boolean r1 = android.text.TextUtils.equals(r11, r1)
            if (r1 == 0) goto L_0x0463
            com.autonavi.common.PageBundle r11 = new com.autonavi.common.PageBundle
            r11.<init>()
            java.lang.String r1 = "plateNum"
            java.lang.String r0 = r0.getQueryParameter(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x0433
            java.lang.String r0 = ""
        L_0x0433:
            com.autonavi.minimap.ajx3.CarInfo r1 = new com.autonavi.minimap.ajx3.CarInfo
            r1.<init>()
            r1.plateNum = r0
            bsl r2 = defpackage.bsl.a()
            com.autonavi.map.db.model.Car r0 = r2.a(r0)
            r1.car = r0
            java.lang.String r0 = "HomePage"
            r1.entryType = r0
            java.lang.String r0 = "url"
            java.lang.String r2 = "path://amap_bundle_carowner/src/car_owner/CarHomeViewController.page.js"
            r11.putString(r0, r2)
            java.lang.String r0 = "jsData"
            org.json.JSONObject r1 = com.autonavi.minimap.ajx3.CarInfo.CarInfoToJson(r1)
            java.lang.String r1 = r1.toString()
            r11.putString(r0, r1)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r0 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r10.startPage(r0, r11)
            goto L_0x06bd
        L_0x0463:
            java.lang.String r1 = "addcar"
            boolean r1 = android.text.TextUtils.equals(r11, r1)
            r2 = 1002(0x3ea, float:1.404E-42)
            if (r1 == 0) goto L_0x04f2
            com.autonavi.minimap.ajx3.CarInfo r11 = new com.autonavi.minimap.ajx3.CarInfo
            r11.<init>()
            java.lang.String r1 = "selectCarPage"
            java.lang.String r4 = "sourcePage"
            java.lang.String r4 = r0.getQueryParameter(r4)
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0485
            java.lang.String r1 = "SelectCar"
            r11.entryType = r1
            goto L_0x0497
        L_0x0485:
            java.lang.String r1 = "0"
            java.lang.String r4 = "from"
            java.lang.String r4 = r0.getQueryParameter(r4)
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0497
            java.lang.String r1 = "Violation"
            r11.entryType = r1
        L_0x0497:
            java.lang.String r1 = "0"
            java.lang.String r4 = "carsCount"
            java.lang.String r4 = r0.getQueryParameter(r4)
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x04a9
            java.lang.String r1 = "1"
            r11.needOpenUri = r1
        L_0x04a9:
            java.lang.String r1 = "0"
            java.lang.String r4 = "isLogin"
            java.lang.String r4 = r0.getQueryParameter(r4)
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x04bb
            java.lang.String r1 = "1"
            r11.needOpenUri = r1
        L_0x04bb:
            java.lang.String r1 = "addType"
            java.lang.String r1 = r0.getQueryParameter(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x04ca
            java.lang.String r0 = "1"
            goto L_0x04d0
        L_0x04ca:
            java.lang.String r1 = "addType"
            java.lang.String r0 = r0.getQueryParameter(r1)
        L_0x04d0:
            r11.addType = r0
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "url"
            java.lang.String r4 = "path://amap_bundle_carowner/src/car_owner/CarAddViewController.page.js"
            r0.putString(r1, r4)
            java.lang.String r1 = "jsData"
            org.json.JSONObject r11 = com.autonavi.minimap.ajx3.CarInfo.CarInfoToJson(r11)
            java.lang.String r11 = r11.toString()
            r0.putString(r1, r11)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r11 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r10.startPageForResult(r11, r0, r2)
            goto L_0x06bd
        L_0x04f2:
            java.lang.String r1 = "editcar"
            boolean r1 = android.text.TextUtils.equals(r11, r1)
            if (r1 == 0) goto L_0x0559
            com.autonavi.minimap.ajx3.CarInfo r11 = new com.autonavi.minimap.ajx3.CarInfo
            r11.<init>()
            java.lang.String r1 = "carNumber"
            java.lang.String r1 = r0.getQueryParameter(r1)
            r11.plateNum = r1
            esb r4 = defpackage.esb.a.a
            java.lang.Class<ato> r5 = defpackage.ato.class
            esc r4 = r4.a(r5)
            ato r4 = (defpackage.ato) r4
            if (r4 == 0) goto L_0x051f
            atm r4 = r4.a()
            com.autonavi.map.db.model.Car r1 = r4.a(r1)
            r11.car = r1
        L_0x051f:
            java.lang.String r1 = "perfectTruck"
            java.lang.String r1 = r0.getQueryParameter(r1)
            r11.perfectTruck = r1
            java.lang.String r1 = "0"
            java.lang.String r4 = "from"
            java.lang.String r0 = r0.getQueryParameter(r4)
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0539
            java.lang.String r0 = "Violation"
            r11.entryType = r0
        L_0x0539:
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "url"
            java.lang.String r4 = "path://amap_bundle_carowner/src/car_owner/CarEditViewController.page.js"
            r0.putString(r1, r4)
            java.lang.String r1 = "jsData"
            org.json.JSONObject r11 = com.autonavi.minimap.ajx3.CarInfo.CarInfoToJson(r11)
            java.lang.String r11 = r11.toString()
            r0.putString(r1, r11)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r11 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r10.startPageForResult(r11, r0, r2)
            goto L_0x06bd
        L_0x0559:
            java.lang.String r1 = "scan"
            boolean r1 = android.text.TextUtils.equals(r11, r1)
            if (r1 == 0) goto L_0x05bd
            com.autonavi.common.PageBundle r11 = new com.autonavi.common.PageBundle
            r11.<init>()
            com.autonavi.minimap.ajx3.CarInfo r1 = new com.autonavi.minimap.ajx3.CarInfo
            r1.<init>()
            java.lang.String r2 = "carNumber"
            java.lang.String r2 = r0.getQueryParameter(r2)
            r1.plateNum = r2
            esb r4 = defpackage.esb.a.a
            java.lang.Class<ato> r5 = defpackage.ato.class
            esc r4 = r4.a(r5)
            ato r4 = (defpackage.ato) r4
            if (r4 == 0) goto L_0x058b
            atm r4 = r4.a()
            com.autonavi.map.db.model.Car r2 = r4.a(r2)
            r1.car = r2
        L_0x058b:
            java.lang.String r2 = "opType"
            java.lang.String r2 = r0.getQueryParameter(r2)
            r1.opType = r2
            java.lang.String r2 = "0"
            java.lang.String r4 = "from"
            java.lang.String r0 = r0.getQueryParameter(r4)
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x05a9
            java.lang.String r0 = "Violation"
            r1.entryType = r0
            java.lang.String r0 = "update"
            r1.opType = r0
        L_0x05a9:
            cpo$4 r0 = new cpo$4
            r0.<init>(r11, r1)
            android.app.Activity r11 = com.autonavi.amap.app.AMapAppGlobal.getTopActivity()
            java.lang.String r1 = "android.permission.CAMERA"
            java.lang.String[] r1 = new java.lang.String[]{r1}
            defpackage.kj.a(r11, r1, r0)
            goto L_0x06bd
        L_0x05bd:
            java.lang.String r1 = "scannotice"
            boolean r1 = android.text.TextUtils.equals(r11, r1)
            if (r1 == 0) goto L_0x061c
            com.autonavi.common.PageBundle r11 = new com.autonavi.common.PageBundle
            r11.<init>()
            com.autonavi.minimap.ajx3.CarInfo r1 = new com.autonavi.minimap.ajx3.CarInfo
            r1.<init>()
            java.lang.String r4 = "carNumber"
            java.lang.String r4 = r0.getQueryParameter(r4)
            r1.plateNum = r4
            esb r5 = defpackage.esb.a.a
            java.lang.Class<ato> r6 = defpackage.ato.class
            esc r5 = r5.a(r6)
            ato r5 = (defpackage.ato) r5
            if (r5 == 0) goto L_0x05ef
            atm r5 = r5.a()
            com.autonavi.map.db.model.Car r4 = r5.a(r4)
            r1.car = r4
        L_0x05ef:
            java.lang.String r4 = "0"
            java.lang.String r5 = "from"
            java.lang.String r0 = r0.getQueryParameter(r5)
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0601
            java.lang.String r0 = "Violation"
            r1.entryType = r0
        L_0x0601:
            java.lang.String r0 = "url"
            java.lang.String r4 = "path://amap_bundle_carowner/src/car_owner/CarScanGuideViewController.page.js"
            r11.putString(r0, r4)
            java.lang.String r0 = "jsData"
            org.json.JSONObject r1 = com.autonavi.minimap.ajx3.CarInfo.CarInfoToJson(r1)
            java.lang.String r1 = r1.toString()
            r11.putString(r0, r1)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r0 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r10.startPageForResult(r0, r11, r2)
            goto L_0x06bd
        L_0x061c:
            java.lang.String r1 = "get_brand"
            boolean r1 = android.text.TextUtils.equals(r11, r1)
            if (r1 == 0) goto L_0x06aa
            com.autonavi.common.PageBundle r11 = new com.autonavi.common.PageBundle
            r11.<init>()
            com.autonavi.minimap.ajx3.CarInfo r1 = new com.autonavi.minimap.ajx3.CarInfo
            r1.<init>()
            java.lang.String r4 = "carNumber"
            java.lang.String r4 = r0.getQueryParameter(r4)
            r1.plateNum = r4
            esb r7 = defpackage.esb.a.a
            java.lang.Class<ato> r8 = defpackage.ato.class
            esc r7 = r7.a(r8)
            ato r7 = (defpackage.ato) r7
            if (r7 == 0) goto L_0x064c
            atm r5 = r7.a()
            com.autonavi.map.db.model.Car r5 = r5.a(r4)
        L_0x064c:
            if (r5 == 0) goto L_0x0673
            int r4 = r5.vehicleType
            if (r4 != r3) goto L_0x0657
            java.lang.String r4 = "0"
            r1.brandType = r4
            goto L_0x0673
        L_0x0657:
            int r4 = r5.vehicleType
            if (r4 != r6) goto L_0x0673
            int r4 = r5.truckType
            if (r4 <= 0) goto L_0x0673
            java.lang.String r4 = "1"
            r1.brandType = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            int r6 = r5.truckType
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            r1.brandLevel = r4
        L_0x0673:
            r1.car = r5
            java.lang.String r4 = "0"
            java.lang.String r5 = "from"
            java.lang.String r0 = r0.getQueryParameter(r5)
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x068c
            java.lang.String r0 = "Violation"
            r1.entryType = r0
            java.lang.String r0 = "update"
            r1.opType = r0
            goto L_0x0690
        L_0x068c:
            java.lang.String r0 = "back"
            r1.opType = r0
        L_0x0690:
            java.lang.String r0 = "url"
            java.lang.String r4 = "path://amap_bundle_carowner/src/car_owner/CarBrandSelectController.page.js"
            r11.putString(r0, r4)
            java.lang.String r0 = "jsData"
            org.json.JSONObject r1 = com.autonavi.minimap.ajx3.CarInfo.CarInfoToJson(r1)
            java.lang.String r1 = r1.toString()
            r11.putString(r0, r1)
            java.lang.Class<com.autonavi.minimap.ajx3.Ajx3Page> r0 = com.autonavi.minimap.ajx3.Ajx3Page.class
            r10.startPageForResult(r0, r11, r2)
            goto L_0x06bd
        L_0x06aa:
            java.lang.String r0 = "carservice"
            boolean r0 = android.text.TextUtils.equals(r11, r0)
            if (r0 != 0) goto L_0x06ba
            java.lang.String r0 = "carlist"
            boolean r11 = android.text.TextUtils.equals(r11, r0)
            if (r11 == 0) goto L_0x06bd
        L_0x06ba:
            r10.c()
        L_0x06bd:
            return r3
        L_0x06be:
            java.lang.String r4 = "settings"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x070d
            java.util.List r11 = r0.getPathSegments()
            if (r11 == 0) goto L_0x070c
            boolean r0 = r11.isEmpty()
            if (r0 == 0) goto L_0x06d3
            goto L_0x070c
        L_0x06d3:
            java.lang.Object r11 = r11.get(r1)
            java.lang.String r11 = (java.lang.String) r11
            java.lang.String r0 = "fontsizesetting"
            boolean r0 = android.text.TextUtils.equals(r11, r0)
            if (r0 == 0) goto L_0x06f3
            esb r11 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.minimap.bundle.setting.api.ISettingService> r0 = com.autonavi.minimap.bundle.setting.api.ISettingService.class
            esc r11 = r11.a(r0)
            com.autonavi.minimap.bundle.setting.api.ISettingService r11 = (com.autonavi.minimap.bundle.setting.api.ISettingService) r11
            if (r11 == 0) goto L_0x070c
            r11.b()
            goto L_0x070c
        L_0x06f3:
            java.lang.String r0 = "mapsettings"
            boolean r11 = android.text.TextUtils.equals(r11, r0)
            if (r11 == 0) goto L_0x070c
            esb r11 = defpackage.esb.a.a
            java.lang.Class<com.autonavi.minimap.bundle.setting.api.ISettingService> r0 = com.autonavi.minimap.bundle.setting.api.ISettingService.class
            esc r11 = r11.a(r0)
            com.autonavi.minimap.bundle.setting.api.ISettingService r11 = (com.autonavi.minimap.bundle.setting.api.ISettingService) r11
            if (r11 == 0) goto L_0x070c
            r11.c()
        L_0x070c:
            return r3
        L_0x070d:
            java.lang.String r4 = "carinsurance"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x076e
            java.util.List r11 = r0.getPathSegments()
            if (r11 == 0) goto L_0x076d
            boolean r2 = r11.isEmpty()
            if (r2 == 0) goto L_0x0722
            goto L_0x076d
        L_0x0722:
            java.lang.Object r11 = r11.get(r1)
            java.lang.String r11 = (java.lang.String) r11
            java.lang.String r2 = "main"
            boolean r11 = android.text.TextUtils.equals(r11, r2)
            if (r11 == 0) goto L_0x076c
            java.lang.String r11 = "url"
            java.lang.String r11 = r0.getQueryParameter(r11)
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x076b
            boolean r0 = defpackage.ajn.a(r11)
            if (r0 != 0) goto L_0x0746
            java.lang.String r11 = defpackage.ajn.b(r11)
        L_0x0746:
            aja r0 = new aja
            r0.<init>(r11)
            cpo$3 r11 = new cpo$3
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            r11.<init>(r1)
            r0.b = r11
            esb r11 = defpackage.esb.a.a
            java.lang.Class<aix> r1 = defpackage.aix.class
            esc r11 = r11.a(r1)
            aix r11 = (defpackage.aix) r11
            if (r11 == 0) goto L_0x076b
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r11.a(r1, r0)
        L_0x076b:
            return r3
        L_0x076c:
            return r1
        L_0x076d:
            return r1
        L_0x076e:
            java.lang.String r4 = "multiPointShow"
            boolean r4 = r2.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x077a
            r10.a(r0)
        L_0x0779:
            return r3
        L_0x077a:
            java.lang.String r4 = "webview"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x0804
            java.util.List r11 = r0.getPathSegments()
            if (r11 == 0) goto L_0x0803
            boolean r2 = r11.isEmpty()
            if (r2 == 0) goto L_0x0790
            goto L_0x0803
        L_0x0790:
            java.lang.Object r11 = r11.get(r1)
            java.lang.String r11 = (java.lang.String) r11
            java.lang.String r2 = "transparent"
            boolean r2 = android.text.TextUtils.equals(r11, r2)
            if (r2 == 0) goto L_0x07ce
            java.lang.String r11 = "url"
            java.lang.String r11 = r0.getQueryParameter(r11)
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 == 0) goto L_0x07b8
            android.app.Application r11 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = com.autonavi.minimap.R.string.intent_open_web_fail
            java.lang.String r11 = r11.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r11)
            goto L_0x0802
        L_0x07b8:
            esb r0 = defpackage.esb.a.a
            java.lang.Class<aix> r1 = defpackage.aix.class
            esc r0 = r0.a(r1)
            aix r0 = (defpackage.aix) r0
            if (r0 == 0) goto L_0x07cd
            bid r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            r0.a(r1, r11)
        L_0x07cd:
            return r3
        L_0x07ce:
            java.lang.String r2 = "amaponline"
            boolean r11 = android.text.TextUtils.equals(r11, r2)
            if (r11 == 0) goto L_0x0802
            java.lang.String r11 = "login_check"
            java.lang.String r11 = r0.getQueryParameter(r11)
            java.lang.String r2 = "url"
            java.lang.String r0 = r0.getQueryParameter(r2)
            java.lang.String r2 = "1"
            boolean r11 = r2.equals(r11)
            if (r11 == 0) goto L_0x0802
            boolean r11 = android.text.TextUtils.isEmpty(r0)
            if (r11 != 0) goto L_0x0802
            boolean r11 = b()
            if (r11 == 0) goto L_0x07fa
            c(r0)
            goto L_0x0802
        L_0x07fa:
            cpo$2 r11 = new cpo$2
            r11.<init>(r0)
            a(r11)
        L_0x0802:
            return r1
        L_0x0803:
            return r1
        L_0x0804:
            java.lang.String r4 = "alipayauthorize"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x0871
            java.lang.String r0 = r11.getAction()
            android.net.Uri r11 = r11.getData()
            java.lang.String r1 = "android.intent.action.VIEW"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0870
            if (r11 == 0) goto L_0x0870
            java.lang.String r0 = r11.getAuthority()
            java.lang.String r1 = "alipayauthorize"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0870
            java.lang.String r0 = "external_user_id"
            java.lang.String r0 = r11.getQueryParameter(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x084a
            java.lang.String r1 = a()
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x084a
            fhg r11 = defpackage.fhg.a()
            java.lang.String r0 = "104"
            r9 = r0
            r0 = r11
            r11 = r9
            goto L_0x0867
        L_0x084a:
            java.lang.String r0 = "is_success"
            java.lang.String r11 = r11.getQueryParameter(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x0870
            fhg r0 = defpackage.fhg.a()
            java.lang.String r1 = "T"
            boolean r11 = r11.equals(r1)
            if (r11 == 0) goto L_0x0865
            java.lang.String r11 = "1"
            goto L_0x0867
        L_0x0865:
            java.lang.String r11 = "102"
        L_0x0867:
            operation.pay.AliSignTools r1 = r0.a
            if (r1 == 0) goto L_0x0870
            operation.pay.AliSignTools r0 = r0.a
            r0.a(r11)
        L_0x0870:
            return r3
        L_0x0871:
            java.lang.String r4 = "alipay"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x0958
            java.lang.String r0 = r11.getAction()
            android.net.Uri r11 = r11.getData()
            java.lang.String r2 = "OperationIntentDispatcherImpl. initAlipayResult. uri: %s."
            java.lang.Object[] r4 = new java.lang.Object[r3]
            if (r11 == 0) goto L_0x088c
            java.lang.String r5 = r11.toString()
            goto L_0x088e
        L_0x088c:
            java.lang.String r5 = ""
        L_0x088e:
            r4[r1] = r5
            operation.pay.AliSignTools.a(r2, r4)
            java.lang.String r2 = "android.intent.action.VIEW"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0957
            if (r11 == 0) goto L_0x0957
            java.lang.String r0 = r11.getAuthority()
            java.lang.String r2 = "alipay"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0957
            java.util.List r0 = r11.getPathSegments()
            if (r0 == 0) goto L_0x0957
            boolean r2 = r0.isEmpty()
            if (r2 == 0) goto L_0x08b7
            goto L_0x0957
        L_0x08b7:
            java.lang.Object r0 = r0.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r2 = "signzhimacallback"
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 == 0) goto L_0x0957
            java.lang.String r0 = "params"
            java.lang.String r0 = r11.getQueryParameter(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x0957
            java.lang.String r2 = ","
            java.lang.String[] r0 = r0.split(r2)
            int r2 = r0.length
            r4 = 3
            if (r2 != r4) goto L_0x0957
            r1 = r0[r1]
            r2 = r0[r3]
            r0 = r0[r6]
            boolean r4 = android.text.TextUtils.isEmpty(r1)
            if (r4 != 0) goto L_0x094e
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x094e
            boolean r4 = android.text.TextUtils.isEmpty(r0)
            if (r4 == 0) goto L_0x08f4
            goto L_0x094e
        L_0x08f4:
            java.lang.String r4 = a()
            boolean r1 = r1.equals(r4)
            if (r1 != 0) goto L_0x0908
            fhg r11 = defpackage.fhg.a()
            java.lang.String r0 = "104"
            r11.a(r0)
            goto L_0x0957
        L_0x0908:
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r4 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r1.<init>(r4)
            java.lang.String r4 = "zhima_sign_tranid"
            java.lang.String r5 = ""
            java.lang.String r1 = r1.getStringValue(r4, r5)
            boolean r4 = android.text.TextUtils.isEmpty(r1)
            if (r4 != 0) goto L_0x0944
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0924
            goto L_0x0944
        L_0x0924:
            java.lang.String r1 = "orderNo"
            java.lang.String r11 = r11.getQueryParameter(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r11)
            if (r1 != 0) goto L_0x093a
            fhg r1 = defpackage.fhg.a()
            java.lang.String r2 = ""
            r1.a(r0, r11, r2)
            goto L_0x0957
        L_0x093a:
            fhg r11 = defpackage.fhg.a()
            java.lang.String r0 = "102"
            r11.a(r0)
            goto L_0x0957
        L_0x0944:
            fhg r11 = defpackage.fhg.a()
            java.lang.String r0 = "102"
            r11.a(r0)
            goto L_0x0957
        L_0x094e:
            fhg r11 = defpackage.fhg.a()
            java.lang.String r0 = "102"
            r11.a(r0)
        L_0x0957:
            return r3
        L_0x0958:
            java.lang.String r11 = "carlogoservice"
            boolean r11 = r2.equals(r11)
            if (r11 == 0) goto L_0x0973
            boolean r11 = b()
            if (r11 == 0) goto L_0x096a
            r10.b(r0)
            goto L_0x0972
        L_0x096a:
            cpo$1 r11 = new cpo$1
            r11.<init>(r0)
            a(r11)
        L_0x0972:
            return r3
        L_0x0973:
            java.lang.String r11 = "commute"
            boolean r11 = r2.equals(r11)
            if (r11 == 0) goto L_0x0a1c
            java.lang.String r11 = "shortcutLabel"
            java.lang.String r11 = r0.getQueryParameter(r11)
            boolean r2 = android.text.TextUtils.isEmpty(r11)
            if (r2 != 0) goto L_0x099d
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r4 = "status"
            r2.put(r4, r11)     // Catch:{ JSONException -> 0x0992 }
            goto L_0x0996
        L_0x0992:
            r11 = move-exception
            r11.printStackTrace()
        L_0x0996:
            java.lang.String r11 = "P00001"
            java.lang.String r4 = "B238"
            com.amap.bundle.statistics.LogManager.actionLogV2(r11, r4, r2)
        L_0x099d:
            java.lang.String r11 = "dest"
            java.lang.String r11 = r0.getQueryParameter(r11)
            if (r11 == 0) goto L_0x0a1b
            java.lang.String r2 = "home"
            boolean r2 = r11.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x09c7
            java.lang.Class<com> r11 = defpackage.com.class
            java.lang.Object r11 = defpackage.ank.a(r11)
            com r11 = (defpackage.com) r11
            if (r11 == 0) goto L_0x09e9
            java.lang.String r2 = r11.a()
            cop r11 = r11.b(r2)
            if (r11 == 0) goto L_0x09e9
            com.amap.bundle.datamodel.FavoritePOI r11 = r11.c()
            r5 = r11
            goto L_0x09e9
        L_0x09c7:
            java.lang.String r2 = "corp"
            boolean r11 = r11.equalsIgnoreCase(r2)
            if (r11 == 0) goto L_0x09e9
            java.lang.Class<com> r11 = defpackage.com.class
            java.lang.Object r11 = defpackage.ank.a(r11)
            com r11 = (defpackage.com) r11
            if (r11 == 0) goto L_0x09e8
            java.lang.String r1 = r11.a()
            cop r11 = r11.b(r1)
            if (r11 == 0) goto L_0x09e8
            com.amap.bundle.datamodel.FavoritePOI r11 = r11.d()
            r5 = r11
        L_0x09e8:
            r1 = 1
        L_0x09e9:
            if (r5 == 0) goto L_0x0a0e
            esb r11 = defpackage.esb.a.a
            java.lang.Class<bax> r0 = defpackage.bax.class
            esc r11 = r11.a(r0)
            bax r11 = (defpackage.bax) r11
            if (r11 == 0) goto L_0x0a1b
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "bundle_key_poi_end"
            r0.putObject(r1, r5)
            java.lang.String r1 = "bundle_key_auto_route"
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r0.putObject(r1, r2)
            r11.a(r0)
            goto L_0x0a1b
        L_0x0a0e:
            java.lang.Class<ava> r11 = defpackage.ava.class
            java.lang.Object r11 = defpackage.ank.a(r11)
            ava r11 = (defpackage.ava) r11
            if (r11 == 0) goto L_0x0a1b
            r11.a(r0, r1)
        L_0x0a1b:
            return r3
        L_0x0a1c:
            java.lang.String r11 = "performance"
            boolean r11 = r2.equals(r11)
            if (r11 == 0) goto L_0x0ad0
            java.util.List r11 = r0.getPathSegments()
            if (r11 == 0) goto L_0x0acf
            boolean r2 = r11.isEmpty()
            if (r2 == 0) goto L_0x0a32
            goto L_0x0acf
        L_0x0a32:
            java.lang.Object r2 = r11.get(r1)
            java.lang.String r2 = (java.lang.String) r2
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x0ace
            java.lang.String r4 = "autosec"
            boolean r2 = r2.equalsIgnoreCase(r4)
            if (r2 == 0) goto L_0x0ace
            int r2 = r11.size()
            if (r2 <= r3) goto L_0x0ace
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.Object r11 = r11.get(r3)
            java.lang.String r11 = (java.lang.String) r11
            java.lang.String r4 = "get"
            boolean r4 = r4.equalsIgnoreCase(r11)
            if (r4 == 0) goto L_0x0a80
            int r11 = com.autonavi.minimap.util.PerformanceSchemeConfig.a()
            if (r11 != r3) goto L_0x0a6b
            java.lang.String r11 = "评测SDK已打开"
            r2.append(r11)
            goto L_0x0a78
        L_0x0a6b:
            if (r11 != r6) goto L_0x0a73
            java.lang.String r11 = "评测SDK已关闭"
            r2.append(r11)
            goto L_0x0a78
        L_0x0a73:
            java.lang.String r11 = "评测SDK是否打开由云控控制"
            r2.append(r11)
        L_0x0a78:
            java.lang.String r11 = r2.toString()
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r11)
            return r3
        L_0x0a80:
            java.lang.String r4 = "set"
            boolean r11 = r4.equalsIgnoreCase(r11)
            if (r11 == 0) goto L_0x0ace
            java.lang.String r11 = "value"
            java.lang.String r11 = r0.getQueryParameter(r11)
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x0ace
            java.lang.String r0 = "1"
            boolean r0 = r11.equals(r0)
            if (r0 == 0) goto L_0x0aa3
            java.lang.String r11 = "评测SDK已打开,重启客户端生效"
            r2.append(r11)
            r1 = 1
            goto L_0x0ab7
        L_0x0aa3:
            java.lang.String r0 = "2"
            boolean r11 = r11.equals(r0)
            if (r11 == 0) goto L_0x0ab2
            java.lang.String r11 = "评测SDK已关闭,重启客户端生效"
            r2.append(r11)
            r1 = 2
            goto L_0x0ab7
        L_0x0ab2:
            java.lang.String r11 = "评测SDK设置清空,重启客户端生效"
            r2.append(r11)
        L_0x0ab7:
            defpackage.cmk.b.a()
            java.lang.String r11 = r2.toString()
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r11)
            com.amap.bundle.mapstorage.MapSharePreference r11 = new com.amap.bundle.mapstorage.MapSharePreference
            java.lang.String r0 = "PerformanceConfig"
            r11.<init>(r0)
            java.lang.String r0 = "performance_autosec_mode"
            r11.putIntValue(r0, r1)
            return r3
        L_0x0ace:
            return r1
        L_0x0acf:
            return r1
        L_0x0ad0:
            return r1
        L_0x0ad1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cpo.a(android.content.Intent):boolean");
    }

    private void a(Uri uri) {
        String queryParameter = uri.getQueryParameter("title");
        String queryParameter2 = uri.getQueryParameter("q");
        String queryParameter3 = uri.getQueryParameter("dev");
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(queryParameter2)) {
            StringTokenizer stringTokenizer = new StringTokenizer(queryParameter2, MergeUtil.SEPARATOR_KV);
            while (stringTokenizer.hasMoreTokens()) {
                String[] split = stringTokenizer.nextToken().split(",");
                if (split != null && split.length >= 3) {
                    try {
                        double parseDouble = Double.parseDouble(split[0]);
                        double parseDouble2 = Double.parseDouble(split[1]);
                        int parseInt = Integer.parseInt(queryParameter3);
                        GeoPoint geoPoint = new GeoPoint(parseDouble2, parseDouble);
                        if (parseInt == 1) {
                            geoPoint = cff.a(geoPoint.x, geoPoint.y);
                        }
                        POI createPOI = POIFactory.createPOI(split[2], geoPoint);
                        if (split.length >= 4) {
                            createPOI.setAddr(split[3]);
                        }
                        if (split.length >= 5) {
                            createPOI.setId(split[4]);
                        }
                        if (split.length >= 6) {
                            createPOI.setPhone(split[5]);
                        }
                        arrayList.add(createPOI);
                        if (arrayList.size() >= 10) {
                            break;
                        }
                    } catch (NumberFormatException unused) {
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.multi_points_params_error));
                        return;
                    }
                }
            }
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("key_title", queryParameter);
        pageBundle.putObject("key_multi_points", arrayList);
        startPage((String) "amap.basemap.action.multpoint_map_page", pageBundle);
    }

    /* access modifiers changed from: private */
    public static void c(String str) {
        new ajn();
        if (!ajn.a(str)) {
            str = ajn.b(str);
        }
        aja aja = new aja(str);
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null) {
            aix.a(AMapPageUtil.getPageContext(), aja);
        }
    }

    /* access modifiers changed from: private */
    public boolean b(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.isEmpty()) {
            return false;
        }
        if (!TextUtils.equals(pathSegments.get(0), "carlogo")) {
            return true;
        }
        d();
        return true;
    }

    private void c() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_carowner/src/car_owner/CarListViewController.page.js");
        startPage(Ajx3Page.class, pageBundle);
    }

    private void d() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_carowner/src/car_logo/CarLogoViewController.page.js");
        startPage(Ajx3Page.class, pageBundle);
    }

    private static void e() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean("openMinePage", true);
        auz auz = (auz) a.a.a(auz.class);
        if (auz != null) {
            auz.a(pageBundle);
        }
    }

    /* access modifiers changed from: private */
    public static void d(String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "amap_bundle_basemap_feedback/src/user_center_old/detail.jsx.js");
            pageBundle.putString("jsData", str);
            pageContext.startPage(Ajx3Page.class, pageBundle);
        }
    }

    private static String a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
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
            iAccountService.a(AMapPageUtil.getPageContext(), anq);
        }
    }
}
