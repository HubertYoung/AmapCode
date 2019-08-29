package defpackage;

import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.searchservice.api.ISearchService;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.drive.search.controller.ISearchCompleteListener;
import com.autonavi.minimap.drive.search.fragment.SearchCallbackResultFragment;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* renamed from: dih reason: default package */
/* compiled from: SearchCallbackUIController */
public final class dih implements aeb<aud> {
    aen a;
    boolean b = false;
    private WeakReference<AbstractBasePage> c;
    private ISearchCompleteListener d;
    private String e;

    public dih(AbstractBasePage abstractBasePage, aen aen, boolean z, String str, ISearchCompleteListener iSearchCompleteListener) {
        this.c = new WeakReference<>(abstractBasePage);
        this.a = aen;
        this.d = iSearchCompleteListener;
        this.b = z;
        this.e = str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0078, code lost:
        if (r3.a(r8) != false) goto L_0x007c;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void callback(final defpackage.aud r8) {
        /*
            r7 = this;
            java.lang.ref.WeakReference<com.autonavi.map.fragmentcontainer.page.AbstractBasePage> r0 = r7.c
            java.lang.Object r0 = r0.get()
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r0 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r0
            if (r0 != 0) goto L_0x000b
            return
        L_0x000b:
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            android.os.Looper r2 = android.os.Looper.myLooper()
            if (r1 == r2) goto L_0x001e
            dih$1 r0 = new dih$1
            r0.<init>(r8)
            defpackage.aho.a(r0)
            return
        L_0x001e:
            defpackage.dii.a()
            r1 = 0
            r2 = 1
            if (r8 == 0) goto L_0x00ed
            com.autonavi.bundle.entity.infolite.external.ResponseHeaderModule r3 = r8.c
            if (r3 == 0) goto L_0x00ed
            com.autonavi.bundle.entity.infolite.external.ResponseHeaderModule r3 = r8.c
            boolean r3 = r3.isOnLine
            if (r3 != 0) goto L_0x00ed
            boolean r3 = r7.b
            if (r3 == 0) goto L_0x00cf
            auc r3 = r8.b
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d
            if (r3 == 0) goto L_0x0043
            auc r3 = r8.b
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d
            int r3 = r3.size()
            if (r3 != 0) goto L_0x00fb
        L_0x0043:
            auc r3 = r8.b
            aub r3 = r3.a
            java.lang.String r3 = r3.c
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x007b
            r3 = -1
            auc r8 = r8.b     // Catch:{ NumberFormatException -> 0x005b }
            aub r8 = r8.a     // Catch:{ NumberFormatException -> 0x005b }
            java.lang.String r8 = r8.c     // Catch:{ NumberFormatException -> 0x005b }
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ NumberFormatException -> 0x005b }
            goto L_0x0060
        L_0x005b:
            r8 = move-exception
            defpackage.kf.a(r8)
            r8 = -1
        L_0x0060:
            esb r3 = defpackage.esb.a.a
            java.lang.Class<adz> r4 = defpackage.adz.class
            esc r3 = r3.a(r4)
            adz r3 = (defpackage.adz) r3
            if (r3 == 0) goto L_0x007b
            ady r3 = r3.b()
            if (r3 == 0) goto L_0x007b
            boolean r8 = r3.a(r8)
            if (r8 == 0) goto L_0x007b
            goto L_0x007c
        L_0x007b:
            r1 = 1
        L_0x007c:
            android.content.Context r8 = r0.getContext()
            int r3 = com.autonavi.minimap.R.string.drive_no_offline_result_switch_to_net
            java.lang.String r8 = r8.getString(r3)
            if (r1 != 0) goto L_0x0092
            android.content.Context r8 = r0.getContext()
            int r1 = com.autonavi.minimap.R.string.drive_have_not_finish_download_offline_data
            java.lang.String r8 = r8.getString(r1)
        L_0x0092:
            com.autonavi.widget.ui.AlertView$a r1 = new com.autonavi.widget.ui.AlertView$a
            android.app.Application r3 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            r1.<init>(r3)
            com.autonavi.widget.ui.AlertView$a r8 = r1.a(r8)
            android.content.Context r3 = r0.getContext()
            int r4 = com.autonavi.minimap.R.string.drive_user_g_net
            java.lang.String r3 = r3.getString(r4)
            dih$3 r4 = new dih$3
            r4.<init>(r0)
            com.autonavi.widget.ui.AlertView$a r8 = r8.a(r3, r4)
            android.content.Context r3 = r0.getContext()
            int r4 = com.autonavi.minimap.R.string.ignore
            java.lang.String r3 = r3.getString(r4)
            dih$2 r4 = new dih$2
            r4.<init>(r0)
            r8.b(r3, r4)
            r1.a(r2)
            com.autonavi.widget.ui.AlertView r8 = r1.a()
            r0.showViewLayer(r8)
            return
        L_0x00cf:
            auc r0 = r8.b
            java.util.ArrayList<com.autonavi.common.model.POI> r0 = r0.d
            if (r0 == 0) goto L_0x00df
            auc r0 = r8.b
            java.util.ArrayList<com.autonavi.common.model.POI> r0 = r0.d
            int r0 = r0.size()
            if (r0 != 0) goto L_0x00fb
        L_0x00df:
            android.app.Application r8 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = com.autonavi.minimap.R.string.autonavi_route_net_error
            java.lang.String r8 = r8.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r8)
            return
        L_0x00ed:
            if (r8 == 0) goto L_0x02a2
            com.autonavi.bundle.entity.infolite.external.ResponseHeaderModule r3 = r8.c
            if (r3 == 0) goto L_0x02a2
            com.autonavi.bundle.entity.infolite.external.ResponseHeaderModule r3 = r8.c
            int r3 = r3.errorCode
            if (r3 == r2) goto L_0x00fb
            goto L_0x02a2
        L_0x00fb:
            aen r0 = r7.a
            int r0 = r0.e
            if (r0 <= r2) goto L_0x0104
            r7.b(r8)
        L_0x0104:
            auc r0 = r8.b
            java.util.ArrayList<java.lang.String> r0 = r0.b
            auc r3 = r8.b
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d
            if (r0 == 0) goto L_0x01aa
            int r4 = r0.size()
            if (r4 <= 0) goto L_0x01aa
            int r3 = r3.size()
            if (r3 != 0) goto L_0x01aa
            auc r2 = r8.b
            java.util.ArrayList<java.lang.String> r2 = r2.b
            if (r2 == 0) goto L_0x015c
            auc r2 = r8.b
            java.util.ArrayList<java.lang.String> r2 = r2.b
            int r2 = r2.size()
            if (r2 != 0) goto L_0x012b
            goto L_0x015c
        L_0x012b:
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "bundle_key_result"
            r0.putObject(r1, r8)
            java.lang.ref.WeakReference<com.autonavi.map.fragmentcontainer.page.AbstractBasePage> r8 = r7.c
            java.lang.Object r8 = r8.get()
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r8 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r8
            if (r8 == 0) goto L_0x015b
            java.lang.Class<com.autonavi.minimap.drive.search.fragment.SearchErrorCityFragment> r1 = com.autonavi.minimap.drive.search.fragment.SearchErrorCityFragment.class
            r8.startPage(r1, r0)
            java.lang.ref.WeakReference<com.autonavi.map.fragmentcontainer.page.AbstractBasePage> r8 = r7.c
            java.lang.Object r8 = r8.get()
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r8 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r8
            if (r8 == 0) goto L_0x015b
            boolean r0 = r8 instanceof com.autonavi.minimap.drive.search.fragment.SearchErrorCityFragment
            if (r0 == 0) goto L_0x015b
            com.autonavi.minimap.drive.search.fragment.SearchErrorCityFragment r8 = (com.autonavi.minimap.drive.search.fragment.SearchErrorCityFragment) r8
            dih$5 r0 = new dih$5
            r0.<init>()
            r8.f = r0
        L_0x015b:
            return
        L_0x015c:
            int r8 = r0.size()
            java.lang.String[] r2 = new java.lang.String[r8]
        L_0x0162:
            if (r1 >= r8) goto L_0x016f
            java.lang.Object r3 = r0.get(r1)
            java.lang.String r3 = (java.lang.String) r3
            r2[r1] = r3
            int r1 = r1 + 1
            goto L_0x0162
        L_0x016f:
            com.autonavi.common.PageBundle r8 = new com.autonavi.common.PageBundle
            r8.<init>()
            java.lang.String r0 = "bundle_key_keyword"
            aen r1 = r7.a
            java.lang.String r1 = r1.a
            r8.putString(r0, r1)
            java.lang.String r0 = "bunde_key_selected"
            r8.putObject(r0, r2)
            java.lang.ref.WeakReference<com.autonavi.map.fragmentcontainer.page.AbstractBasePage> r0 = r7.c
            java.lang.Object r0 = r0.get()
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r0 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r0
            if (r0 == 0) goto L_0x01a9
            java.lang.Class<com.autonavi.minimap.drive.search.fragment.SearchErrorSuggestionFragment> r1 = com.autonavi.minimap.drive.search.fragment.SearchErrorSuggestionFragment.class
            r0.startPage(r1, r8)
            java.lang.ref.WeakReference<com.autonavi.map.fragmentcontainer.page.AbstractBasePage> r8 = r7.c
            java.lang.Object r8 = r8.get()
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r8 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r8
            if (r8 == 0) goto L_0x01a9
            boolean r0 = r8 instanceof com.autonavi.minimap.drive.search.fragment.SearchErrorSuggestionFragment
            if (r0 == 0) goto L_0x01a9
            com.autonavi.minimap.drive.search.fragment.SearchErrorSuggestionFragment r8 = (com.autonavi.minimap.drive.search.fragment.SearchErrorSuggestionFragment) r8
            com.autonavi.minimap.drive.search.controller.SearchCallbackUIController$5 r0 = new com.autonavi.minimap.drive.search.controller.SearchCallbackUIController$5
            r0.<init>(r7, r8, r2)
            r8.setOnItemClickListener(r0)
        L_0x01a9:
            return
        L_0x01aa:
            auc r0 = r8.b
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r0 = r0.c
            if (r0 == 0) goto L_0x01e8
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x01e8
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "keyword"
            aen r3 = r7.a
            java.lang.String r3 = r3.a
            r0.putString(r1, r3)
            java.lang.String r1 = "bundle_key_result"
            r0.putObject(r1, r8)
            java.lang.String r8 = "bundle_key_listener"
            dih$6 r1 = new dih$6
            r1.<init>()
            r0.putObject(r8, r1)
            java.lang.String r8 = "is_from_search_call_back"
            r0.putBoolean(r8, r2)
            java.lang.ref.WeakReference<com.autonavi.map.fragmentcontainer.page.AbstractBasePage> r8 = r7.c
            java.lang.Object r8 = r8.get()
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r8 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r8
            if (r8 == 0) goto L_0x01e7
            java.lang.Class<com.autonavi.minimap.drive.search.fragment.SearchCitySuggestionPage> r1 = com.autonavi.minimap.drive.search.fragment.SearchCitySuggestionPage.class
            r8.startPage(r1, r0)
        L_0x01e7:
            return
        L_0x01e8:
            java.lang.ref.WeakReference<com.autonavi.map.fragmentcontainer.page.AbstractBasePage> r0 = r7.c
            java.lang.Object r0 = r0.get()
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r0 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r0
            if (r0 == 0) goto L_0x02a1
            auc r3 = r8.b
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d
            auc r4 = r8.b
            java.util.ArrayList<java.lang.String> r4 = r4.b
            auc r5 = r8.b
            int r5 = r5.e
            if (r3 == 0) goto L_0x0226
            int r6 = r3.size()
            if (r6 == 0) goto L_0x0226
            if (r5 > 0) goto L_0x0209
            goto L_0x0226
        L_0x0209:
            if (r3 == 0) goto L_0x0217
            int r1 = r3.size()
            if (r1 <= 0) goto L_0x0217
            if (r5 <= 0) goto L_0x0217
            r7.b(r8)
            return
        L_0x0217:
            android.content.res.Resources r8 = r0.getResources()
            int r0 = com.autonavi.minimap.R.string.ic_net_error_noresult
            java.lang.String r8 = r8.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r8)
            goto L_0x02a1
        L_0x0226:
            if (r4 == 0) goto L_0x0232
            int r3 = r4.size()
            if (r3 <= 0) goto L_0x0232
            r7.b(r8)
            return
        L_0x0232:
            r3 = 0
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r4 = r8.a
            if (r4 == 0) goto L_0x023b
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r3 = r8.a
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.POIList
        L_0x023b:
            if (r3 == 0) goto L_0x025b
            int r4 = r3.size()
            if (r4 != r2) goto L_0x025b
            auc r4 = r8.b
            aub r4 = r4.a
            if (r4 == 0) goto L_0x025b
            auc r4 = r8.b
            aub r4 = r4.a
            int r4 = r4.e
            if (r4 != r2) goto L_0x025b
            java.lang.Object r8 = r3.get(r1)
            com.autonavi.common.model.POI r8 = (com.autonavi.common.model.POI) r8
            r7.a(r8)
            return
        L_0x025b:
            if (r3 == 0) goto L_0x0293
            int r1 = r3.size()
            if (r1 <= 0) goto L_0x0293
            java.lang.ref.WeakReference<com.autonavi.map.fragmentcontainer.page.AbstractBasePage> r0 = r7.c
            java.lang.Object r0 = r0.get()
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r0 = (com.autonavi.map.fragmentcontainer.page.AbstractBasePage) r0
            if (r0 == 0) goto L_0x0292
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            java.lang.String r2 = "search_parser"
            r1.putObject(r2, r8)
            java.lang.String r8 = "search_url"
            aen r2 = r7.a
            r1.putObject(r8, r2)
            java.lang.String r8 = "dialog_title"
            java.lang.String r2 = r7.e
            r1.putString(r8, r2)
            java.lang.String r8 = "OFFLINE_FIRST"
            boolean r2 = r7.b
            r1.putBoolean(r8, r2)
            java.lang.Class<com.autonavi.minimap.drive.search.fragment.SearchCallbackResultFragment> r8 = com.autonavi.minimap.drive.search.fragment.SearchCallbackResultFragment.class
            r2 = 2
            r0.startPageForResult(r8, r1, r2)
        L_0x0292:
            return
        L_0x0293:
            android.content.res.Resources r8 = r0.getResources()
            int r0 = com.autonavi.minimap.R.string.ic_net_error_noresult
            java.lang.String r8 = r8.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r8)
            return
        L_0x02a1:
            return
        L_0x02a2:
            int r8 = com.autonavi.minimap.R.string.ic_net_error_noresult
            java.lang.String r8 = r0.getString(r8)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dih.callback(aud):void");
    }

    public final void error(int i) {
        aho.a(new Runnable() {
            public final void run() {
                dii.a();
            }
        }, 100);
    }

    private void a(POI poi) {
        if (this.d != null) {
            POI createPOI = POIFactory.createPOI(poi.getName(), poi.getPoint());
            createPOI.setId(poi.getId());
            createPOI.setAdCode(poi.getAdCode());
            createPOI.setAddr(poi.getAddr());
            createPOI.setType(poi.getType());
            createPOI.setEntranceList(poi.getEntranceList());
            createPOI.setExitList(poi.getExitList());
            createPOI.setIndustry(poi.getIndustry());
            createPOI.setEndPoiExtension(poi.getEndPoiExtension());
            createPOI.setTransparent(poi.getTransparent());
            ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
            ISearchPoiData iSearchPoiData2 = (ISearchPoiData) createPOI.as(ISearchPoiData.class);
            iSearchPoiData2.setFnona(iSearchPoiData.getFnona());
            iSearchPoiData2.setTowardsAngle(iSearchPoiData.getTowardsAngle());
            iSearchPoiData2.setParent(iSearchPoiData.getParent());
            iSearchPoiData2.setChildType(iSearchPoiData.getChildType());
            this.d.a((POI) iSearchPoiData2);
        }
    }

    private void b(aud aud) {
        AbstractBasePage abstractBasePage = (AbstractBasePage) this.c.get();
        if (abstractBasePage != null) {
            if (this.a.e <= 0) {
                this.a.e = 1;
            }
            ArrayList<POI> a2 = a(this.a.e, aud);
            if (a2 != null) {
                if (aud.b.a.e == 1) {
                    if (a2.size() > 0) {
                        a(a2.get(0));
                    }
                    return;
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("search_parser", aud);
                pageBundle.putObject("search_url", this.a);
                pageBundle.putString("dialog_title", this.e);
                pageBundle.putBoolean("OFFLINE_FIRST", this.b);
                abstractBasePage.startPageForResult(SearchCallbackResultFragment.class, pageBundle, 2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00b8, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00bd, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.util.ArrayList<com.autonavi.common.model.POI> a(int r6, defpackage.aud r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            if (r7 == 0) goto L_0x00bc
            auc r1 = r7.b     // Catch:{ all -> 0x00b9 }
            if (r1 == 0) goto L_0x00bc
            auc r1 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r1 = r1.d     // Catch:{ all -> 0x00b9 }
            if (r1 == 0) goto L_0x00bc
            auc r1 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r1 = r1.d     // Catch:{ all -> 0x00b9 }
            int r1 = r1.size()     // Catch:{ all -> 0x00b9 }
            if (r1 != 0) goto L_0x001a
            goto L_0x00bc
        L_0x001a:
            r1 = 0
            if (r7 == 0) goto L_0x005b
            auc r2 = r7.b     // Catch:{ all -> 0x00b9 }
            if (r2 == 0) goto L_0x005b
            auc r2 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.d     // Catch:{ all -> 0x00b9 }
            if (r2 != 0) goto L_0x0028
            goto L_0x005b
        L_0x0028:
            r2 = 0
        L_0x0029:
            auc r3 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x00b9 }
            int r3 = r3.size()     // Catch:{ all -> 0x00b9 }
            if (r2 >= r3) goto L_0x005b
            auc r3 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x00b9 }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ all -> 0x00b9 }
            com.autonavi.common.model.POI r3 = (com.autonavi.common.model.POI) r3     // Catch:{ all -> 0x00b9 }
            if (r3 == 0) goto L_0x0058
            java.lang.String r3 = r3.getId()     // Catch:{ all -> 0x00b9 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x00b9 }
            if (r3 == 0) goto L_0x0058
            auc r3 = r7.b     // Catch:{ all -> 0x00b9 }
            auc r4 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00b9 }
            java.lang.Object r2 = r4.remove(r2)     // Catch:{ all -> 0x00b9 }
            com.autonavi.common.model.POI r2 = (com.autonavi.common.model.POI) r2     // Catch:{ all -> 0x00b9 }
            r3.f = r2     // Catch:{ all -> 0x00b9 }
            goto L_0x005b
        L_0x0058:
            int r2 = r2 + 1
            goto L_0x0029
        L_0x005b:
            auc r2 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.d     // Catch:{ all -> 0x00b9 }
            int r2 = r2.size()     // Catch:{ all -> 0x00b9 }
            if (r6 <= 0) goto L_0x00b7
            r3 = 1
            if (r7 == 0) goto L_0x008a
            auc r4 = r7.b     // Catch:{ all -> 0x00b9 }
            if (r4 == 0) goto L_0x008a
            auc r4 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00b9 }
            if (r4 == 0) goto L_0x008a
            auc r4 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00b9 }
            int r4 = r4.size()     // Catch:{ all -> 0x00b9 }
            if (r4 <= 0) goto L_0x008a
            auc r4 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r4 = r4.d     // Catch:{ all -> 0x00b9 }
            int r4 = r4.size()     // Catch:{ all -> 0x00b9 }
            int r4 = r4 + 10
            int r4 = r4 - r3
            int r4 = r4 / 10
            goto L_0x008b
        L_0x008a:
            r4 = 1
        L_0x008b:
            if (r6 <= r4) goto L_0x008e
            goto L_0x00b7
        L_0x008e:
            int r6 = r6 - r3
            int r6 = r6 * 10
            if (r6 < r2) goto L_0x0095
            monitor-exit(r5)
            return r0
        L_0x0095:
            int r0 = r6 + 10
            int r0 = r0 - r3
            int r2 = r2 - r3
            if (r0 <= r2) goto L_0x009c
            r0 = r2
        L_0x009c:
            int r0 = r0 - r6
            int r0 = r0 + r3
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x00b9 }
            r2.<init>()     // Catch:{ all -> 0x00b9 }
        L_0x00a3:
            if (r1 >= r0) goto L_0x00b5
            auc r3 = r7.b     // Catch:{ all -> 0x00b9 }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.d     // Catch:{ all -> 0x00b9 }
            int r4 = r6 + r1
            java.lang.Object r3 = r3.get(r4)     // Catch:{ all -> 0x00b9 }
            r2.add(r3)     // Catch:{ all -> 0x00b9 }
            int r1 = r1 + 1
            goto L_0x00a3
        L_0x00b5:
            monitor-exit(r5)
            return r2
        L_0x00b7:
            monitor-exit(r5)
            return r0
        L_0x00b9:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        L_0x00bc:
            monitor-exit(r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dih.a(int, aud):java.util.ArrayList");
    }

    public static /* synthetic */ void a(dih dih, String str) {
        AbstractBasePage abstractBasePage = (AbstractBasePage) dih.c.get();
        if (abstractBasePage != null) {
            if (dih.d != null) {
                dih.d.a(str);
            }
            dih.a.a = str;
            ISearchService iSearchService = (ISearchService) a.a.a(ISearchService.class);
            if (iSearchService != null) {
                dii.a(str, iSearchService.b(aew.a((aem) dih.a), dih.b ? 1 : 0, dih), abstractBasePage.getContext());
            }
        }
    }
}
