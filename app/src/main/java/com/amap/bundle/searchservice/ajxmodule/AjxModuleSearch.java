package com.amap.bundle.searchservice.ajxmodule;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.searchservice.service.search.param.SuggestionParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleSearch;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxModuleSearch extends AbstractModuleSearch {
    public final String OFFLINE_SUGG_ERROR_RESPONSE = "{\"responseType\":\"param parse error\"}";
    private aet mShareInfo;
    private dct mShareType;

    public AjxModuleSearch(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void poiSelectSearch(String str, final JsFunctionCallback jsFunctionCallback) {
        SuperId.getInstance().reset();
        SuperId.getInstance().setBit1("f");
        SuperId.getInstance().setBit2(SuperId.BIT_2_TAG_SEARCH_POLYGON);
        ael ael = new ael(str);
        ISearchService iSearchService = (ISearchService) a.a.a(ISearchService.class);
        if (iSearchService != null) {
            iSearchService.b(aew.a((aem) ael), 2, new aeb<aud>() {
                public final /* synthetic */ void callback(Object obj) {
                    aud aud = (aud) obj;
                    if (aud == null || aud.b == null || aud.b.d == null) {
                        jsFunctionCallback.callback("");
                        return;
                    }
                    ArrayList<POI> arrayList = aud.b.d;
                    JSONObject jSONObject = new JSONObject();
                    try {
                        JSONArray jSONArray = new JSONArray();
                        Iterator<POI> it = arrayList.iterator();
                        while (it.hasNext()) {
                            jSONArray.put(bnx.b(it.next()));
                        }
                        jSONObject.put("poi_list", jSONArray);
                    } catch (Exception unused) {
                    }
                    jsFunctionCallback.callback(jSONObject.toString());
                }

                public final void error(int i) {
                    jsFunctionCallback.callback("");
                }
            });
            return;
        }
        jsFunctionCallback.callback("");
    }

    public void share(String str, JsFunctionCallback jsFunctionCallback) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback("");
            }
            return;
        }
        if (this.mShareInfo == null) {
            this.mShareInfo = new aet();
        }
        if (this.mShareType == null) {
            this.mShareType = new dct(0);
            this.mShareType.e = true;
            this.mShareType.d = true;
            this.mShareType.l = true;
            this.mShareType.g = true;
            this.mShareType.h = true;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.mShareInfo.f = jSONObject.optString("url");
            this.mShareInfo.c = jSONObject.optString(ActionConstant.IMG_URL);
            this.mShareInfo.d = jSONObject.optString("title");
            this.mShareInfo.e = jSONObject.optString("desc");
            str2 = jSONObject.optString(BioDetector.EXT_KEY_SCENE_ID_BUNDLE);
        } catch (JSONException unused) {
            str2 = "";
        }
        this.mShareInfo.a = this.mShareType;
        if (jsFunctionCallback != null) {
            this.mShareInfo.g = new aes(str2, jsFunctionCallback);
        }
        Context appContext = AMapPageUtil.getAppContext();
        aet aet = this.mShareInfo;
        if (aet.a != null && appContext != null) {
            dcb dcb = (dcb) a.a.a(dcb.class);
            aet.b = BitmapFactory.decodeResource(appContext.getResources(), R.drawable.v3_icon);
            aeu.a = new b(aet);
            if (dcb != null) {
                dcb.a(aet.a, (dcd) aeu.a);
            }
        }
    }

    public void searchPoiByKeyword(String str, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            bty mapView = DoNotUseTool.getMapView();
            final bid pageContext = AMapPageUtil.getPageContext();
            if (mapView == null || pageContext == null) {
                jsFunctionCallback.callback("");
                return;
            }
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            ISearchService iSearchService = (ISearchService) a.a.a(ISearchService.class);
            if (iSearchService != null) {
                SuggestionParam suggestionParam = new SuggestionParam();
                suggestionParam.adcode = (long) latestPosition.getAdCode();
                suggestionParam.keyWord = str;
                suggestionParam.category = null;
                suggestionParam.suggestType = "poi";
                suggestionParam.mCenter = latestPosition;
                iSearchService.a(suggestionParam, 2, (aeb<aux>) new aeb<aux>() {
                    public final /* synthetic */ void callback(Object obj) {
                        aux aux = (aux) obj;
                        if (pageContext.isAlive()) {
                            List<TipItem> list = aux.b;
                            if (list == null || list.isEmpty()) {
                                jsFunctionCallback.callback("");
                                return;
                            }
                            JSONArray jSONArray = new JSONArray();
                            for (TipItem next : list) {
                                if (next != null) {
                                    JSONObject b2 = bnx.b(AjxModuleSearch.this.covertPoi(next));
                                    if (b2 != null) {
                                        jSONArray.put(b2);
                                    }
                                }
                            }
                            jsFunctionCallback.callback(jSONArray.toString());
                        }
                    }

                    public final void error(int i) {
                        if (pageContext.isAlive()) {
                            jsFunctionCallback.callback("");
                        }
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public POI covertPoi(@NonNull TipItem tipItem) {
        POI createPOI = POIFactory.createPOI(tipItem.name, new GeoPoint(tipItem.x, tipItem.y));
        createPOI.setId(tipItem.poiid);
        createPOI.setAdCode(tipItem.adcode);
        StringBuilder sb = new StringBuilder();
        sb.append(tipItem.district);
        sb.append(tipItem.addr);
        createPOI.setAddr(sb.toString().trim());
        createPOI.setType(tipItem.newType);
        createPOI.getPoiExtra().put("icon_info", Integer.valueOf(tipItem.iconinfo));
        createPOI.setEndPoiExtension(tipItem.endPoiExtension);
        createPOI.setTransparent(tipItem.transparent);
        if (tipItem.x_entr > 0.0d && tipItem.y_entr > 0.0d) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new GeoPoint(tipItem.x_entr, tipItem.y_entr));
            createPOI.setEntranceList(arrayList);
        }
        return createPOI;
    }

    public void offlineSugg(String str, JsFunctionCallback jsFunctionCallback) {
        JsFunctionCallback jsFunctionCallback2 = jsFunctionCallback;
        if (jsFunctionCallback2 != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                int i = jSONObject.getInt("searchType");
                int i2 = jSONObject.getInt("totalSize");
                String string = jSONObject.getString(TrafficUtil.KEYWORD);
                int i3 = jSONObject.getInt(AutoJsonUtils.JSON_ADCODE);
                double d = jSONObject.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON);
                double d2 = jSONObject.getDouble("lat");
                aee.a().d();
                aee.a();
                final double d3 = d;
                final double d4 = d2;
                String str2 = string;
                double d5 = d2;
                final int i4 = i;
                final JsFunctionCallback jsFunctionCallback3 = jsFunctionCallback2;
                AnonymousClass3 r0 = new OnSearchResultListener() {
                    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ba A[Catch:{ JSONException -> 0x0118 }] */
                    /* JADX WARNING: Removed duplicated region for block: B:36:0x00da A[Catch:{ JSONException -> 0x0118 }] */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void onGetSearchResult(int r10, com.autonavi.ae.search.model.GPoiResult r11) {
                        /*
                            r9 = this;
                            r10 = 0
                            r0 = 1
                            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0118 }
                            r1.<init>()     // Catch:{ JSONException -> 0x0118 }
                            if (r11 != 0) goto L_0x000b
                            goto L_0x0102
                        L_0x000b:
                            java.util.List r11 = r11.getPoiList()     // Catch:{ JSONException -> 0x0118 }
                            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ JSONException -> 0x0118 }
                            r2.<init>()     // Catch:{ JSONException -> 0x0118 }
                            java.util.Iterator r11 = r11.iterator()     // Catch:{ JSONException -> 0x0118 }
                        L_0x0018:
                            boolean r3 = r11.hasNext()     // Catch:{ JSONException -> 0x0118 }
                            if (r3 == 0) goto L_0x00fc
                            java.lang.Object r3 = r11.next()     // Catch:{ JSONException -> 0x0118 }
                            com.autonavi.ae.search.model.GPoiBase r3 = (com.autonavi.ae.search.model.GPoiBase) r3     // Catch:{ JSONException -> 0x0118 }
                            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0118 }
                            r4.<init>()     // Catch:{ JSONException -> 0x0118 }
                            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0118 }
                            r5.<init>()     // Catch:{ JSONException -> 0x0118 }
                            if (r3 != 0) goto L_0x0033
                            r4 = r5
                            goto L_0x00f7
                        L_0x0033:
                            boolean r6 = r3 instanceof com.autonavi.ae.search.model.GPoiBean     // Catch:{ JSONException -> 0x0118 }
                            if (r6 == 0) goto L_0x00e4
                            com.autonavi.ae.search.model.GPoiBean r3 = (com.autonavi.ae.search.model.GPoiBean) r3     // Catch:{ JSONException -> 0x0118 }
                            java.lang.String r6 = "name"
                            java.lang.String r7 = r3.getName()     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0118 }
                            java.lang.String r6 = "address"
                            java.lang.String r7 = r3.getAddr()     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0118 }
                            int r6 = r3.getAdcode()     // Catch:{ JSONException -> 0x0118 }
                            if (r6 <= 0) goto L_0x0056
                            java.lang.String r7 = "adcode"
                            r5.put(r7, r6)     // Catch:{ JSONException -> 0x0118 }
                        L_0x0056:
                            java.lang.String r6 = r3.getCatName()     // Catch:{ JSONException -> 0x0118 }
                            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ JSONException -> 0x0118 }
                            if (r6 != 0) goto L_0x0069
                            java.lang.String r6 = "poi_tag"
                            java.lang.String r7 = r3.getCatName()     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0118 }
                        L_0x0069:
                            java.lang.String r6 = r3.getPoiID()     // Catch:{ JSONException -> 0x0118 }
                            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ JSONException -> 0x0118 }
                            if (r6 != 0) goto L_0x007c
                            java.lang.String r6 = "poiid"
                            java.lang.String r7 = r3.getPoiID()     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0118 }
                        L_0x007c:
                            java.lang.String r6 = "datatype_spec"
                            int r7 = r3.getCatCode()     // Catch:{ JSONException -> 0x0118 }
                            r8 = 150600(0x24c48, float:2.11036E-40)
                            if (r7 == r8) goto L_0x0096
                            r8 = 150800(0x24d10, float:2.11316E-40)
                            if (r7 == r8) goto L_0x0094
                            switch(r7) {
                                case 150500: goto L_0x0096;
                                case 150501: goto L_0x0096;
                                default: goto L_0x008f;
                            }     // Catch:{ JSONException -> 0x0118 }
                        L_0x008f:
                            switch(r7) {
                                case 150700: goto L_0x0094;
                                case 150701: goto L_0x0094;
                                case 150702: goto L_0x0094;
                                case 150703: goto L_0x0094;
                                default: goto L_0x0092;
                            }     // Catch:{ JSONException -> 0x0118 }
                        L_0x0092:
                            r7 = -1
                            goto L_0x0097
                        L_0x0094:
                            r7 = 2
                            goto L_0x0097
                        L_0x0096:
                            r7 = 3
                        L_0x0097:
                            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0118 }
                            java.lang.String r6 = "x"
                            android.graphics.PointF r7 = r3.getLocalPoint()     // Catch:{ JSONException -> 0x0118 }
                            float r7 = r7.x     // Catch:{ JSONException -> 0x0118 }
                            double r7 = (double) r7     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0118 }
                            java.lang.String r6 = "y"
                            android.graphics.PointF r7 = r3.getLocalPoint()     // Catch:{ JSONException -> 0x0118 }
                            float r7 = r7.y     // Catch:{ JSONException -> 0x0118 }
                            double r7 = (double) r7     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0118 }
                            android.graphics.PointF r6 = r3.getNaviPoint()     // Catch:{ JSONException -> 0x0118 }
                            if (r6 == 0) goto L_0x00d4
                            java.lang.String r6 = "x_entr"
                            android.graphics.PointF r7 = r3.getNaviPoint()     // Catch:{ JSONException -> 0x0118 }
                            float r7 = r7.x     // Catch:{ JSONException -> 0x0118 }
                            double r7 = (double) r7     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0118 }
                            java.lang.String r6 = "y_entr"
                            android.graphics.PointF r7 = r3.getNaviPoint()     // Catch:{ JSONException -> 0x0118 }
                            float r7 = r7.y     // Catch:{ JSONException -> 0x0118 }
                            double r7 = (double) r7     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0118 }
                        L_0x00d4:
                            int r3 = r3.getCatCode()     // Catch:{ JSONException -> 0x0118 }
                            if (r3 <= 0) goto L_0x00f1
                            java.lang.String r6 = "category"
                            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r3)     // Catch:{ JSONException -> 0x0118 }
                            goto L_0x00f1
                        L_0x00e4:
                            boolean r6 = r3 instanceof com.autonavi.ae.search.model.GPoiGroup     // Catch:{ JSONException -> 0x0118 }
                            if (r6 == 0) goto L_0x00f1
                            java.lang.String r6 = "name"
                            java.lang.String r3 = r3.getName()     // Catch:{ JSONException -> 0x0118 }
                            r5.put(r6, r3)     // Catch:{ JSONException -> 0x0118 }
                        L_0x00f1:
                            java.lang.String r3 = "tip"
                            r4.put(r3, r5)     // Catch:{ JSONException -> 0x0118 }
                        L_0x00f7:
                            r2.put(r4)     // Catch:{ JSONException -> 0x0118 }
                            goto L_0x0018
                        L_0x00fc:
                            java.lang.String r11 = "suggArr"
                            r1.put(r11, r2)     // Catch:{ JSONException -> 0x0118 }
                        L_0x0102:
                            java.lang.String r11 = "responseType"
                            int r2 = r6     // Catch:{ JSONException -> 0x0118 }
                            r1.put(r11, r2)     // Catch:{ JSONException -> 0x0118 }
                            com.autonavi.minimap.ajx3.core.JsFunctionCallback r11 = r7     // Catch:{ JSONException -> 0x0118 }
                            java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ JSONException -> 0x0118 }
                            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x0118 }
                            r2[r10] = r1     // Catch:{ JSONException -> 0x0118 }
                            r11.callback(r2)     // Catch:{ JSONException -> 0x0118 }
                            return
                        L_0x0118:
                            com.autonavi.minimap.ajx3.core.JsFunctionCallback r11 = r7
                            java.lang.Object[] r0 = new java.lang.Object[r0]
                            java.lang.String r1 = "{\"responseType\":\"param parse error\"}"
                            r0[r10] = r1
                            r11.callback(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.searchservice.ajxmodule.AjxModuleSearch.AnonymousClass3.onGetSearchResult(int, com.autonavi.ae.search.model.GPoiResult):void");
                    }
                };
                aee.c();
                float f = (float) d5;
                aee.a.preSearch(i, str2, i3, (float) d, f, i2, r0);
            } catch (JSONException unused) {
                jsFunctionCallback2.callback("{\"responseType\":\"param parse error\"}");
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void searchOfflineNearestPoi(java.lang.String r4, final com.autonavi.minimap.ajx3.core.JsFunctionCallback r5) {
        /*
            r3 = this;
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x001d }
            r1.<init>(r4)     // Catch:{ Exception -> 0x001d }
            java.lang.String r4 = "lon"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ Exception -> 0x001d }
            float r4 = java.lang.Float.parseFloat(r4)     // Catch:{ Exception -> 0x001d }
            java.lang.String r2 = "lat"
            java.lang.String r1 = r1.optString(r2)     // Catch:{ Exception -> 0x001b }
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ Exception -> 0x001b }
            goto L_0x0023
        L_0x001b:
            r1 = move-exception
            goto L_0x001f
        L_0x001d:
            r1 = move-exception
            r4 = 0
        L_0x001f:
            r1.printStackTrace()
            r1 = 0
        L_0x0023:
            int r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r2 == 0) goto L_0x004b
            int r0 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x002c
            goto L_0x004b
        L_0x002c:
            esb r0 = defpackage.esb.a.a
            java.lang.Class<adz> r2 = defpackage.adz.class
            esc r0 = r0.a(r2)
            adz r0 = (defpackage.adz) r0
            r2 = 0
            if (r0 == 0) goto L_0x003f
            ady r2 = r0.b()
        L_0x003f:
            if (r2 != 0) goto L_0x0042
            return
        L_0x0042:
            com.amap.bundle.searchservice.ajxmodule.AjxModuleSearch$4 r0 = new com.amap.bundle.searchservice.ajxmodule.AjxModuleSearch$4
            r0.<init>(r5)
            r2.a(r4, r1, r0)
            return
        L_0x004b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.searchservice.ajxmodule.AjxModuleSearch.searchOfflineNearestPoi(java.lang.String, com.autonavi.minimap.ajx3.core.JsFunctionCallback):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0034  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getOfflinePoiDetail(java.lang.String r9, final com.autonavi.minimap.ajx3.core.JsFunctionCallback r10) {
        /*
            r8 = this;
            java.lang.String r0 = ""
            r1 = 0
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0029 }
            r3.<init>(r9)     // Catch:{ Exception -> 0x0029 }
            java.lang.String r9 = "lon"
            java.lang.String r9 = r3.optString(r9)     // Catch:{ Exception -> 0x0029 }
            double r4 = java.lang.Double.parseDouble(r9)     // Catch:{ Exception -> 0x0029 }
            java.lang.String r9 = "lat"
            java.lang.String r9 = r3.optString(r9)     // Catch:{ Exception -> 0x0026 }
            double r6 = java.lang.Double.parseDouble(r9)     // Catch:{ Exception -> 0x0026 }
            java.lang.String r9 = "id"
            java.lang.String r9 = r3.optString(r9)     // Catch:{ Exception -> 0x0024 }
            goto L_0x0030
        L_0x0024:
            r9 = move-exception
            goto L_0x002c
        L_0x0026:
            r9 = move-exception
            r6 = r1
            goto L_0x002c
        L_0x0029:
            r9 = move-exception
            r4 = r1
            r6 = r4
        L_0x002c:
            r9.printStackTrace()
            r9 = r0
        L_0x0030:
            int r0 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x0062
            int r0 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x0062
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 == 0) goto L_0x003f
            goto L_0x0062
        L_0x003f:
            esb r0 = defpackage.esb.a.a
            java.lang.Class<adz> r1 = defpackage.adz.class
            esc r0 = r0.a(r1)
            adz r0 = (defpackage.adz) r0
            if (r0 == 0) goto L_0x0061
            ady r0 = r0.b()
            if (r0 != 0) goto L_0x0054
            return
        L_0x0054:
            com.autonavi.common.model.GeoPoint r1 = new com.autonavi.common.model.GeoPoint
            r1.<init>(r4, r6)
            com.amap.bundle.searchservice.ajxmodule.AjxModuleSearch$5 r2 = new com.amap.bundle.searchservice.ajxmodule.AjxModuleSearch$5
            r2.<init>(r0, r10)
            r0.a(r1, r9, r2)
        L_0x0061:
            return
        L_0x0062:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.searchservice.ajxmodule.AjxModuleSearch.getOfflinePoiDetail(java.lang.String, com.autonavi.minimap.ajx3.core.JsFunctionCallback):void");
    }
}
