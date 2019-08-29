package com.autonavi.bundle.searchservice.utils;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoiArrayTemplate extends PoiLayoutTemplate {
    private static final int CHILD_ICON_AIRPORT = 34;
    private static final int CHILD_ICON_ARRIVE = 44;
    private static final int CHILD_ICON_BUS = 200;
    private static final int CHILD_ICON_DOOR = 31;
    private static final int CHILD_ICON_EXIST = 46;
    private static final int CHILD_ICON_FLY = 43;
    private static final int CHILD_ICON_IN = 45;
    private static final int CHILD_ICON_PARK = 41;
    private static final int CHILD_ICON_TICKET = 42;
    private static final int CHILD_SUBWAY_ENTRANCE = 107;
    private static final long serialVersionUID = 216038044127935183L;
    private String action;
    private String address;
    private String anchor;
    private String busAlias;
    private String childTypes;
    private String deepinfo;
    private String distence;
    private String[] gas_prices = null;
    private String[] gas_types = null;
    private String[] gas_utils = null;
    private String label;
    private String labelNew;
    private List<auh> mCharges;
    private auw mChildConfig;
    private String[] mShoppingMallDatas;
    private String mShoppingMallUrl;
    private int mShowChild = 1;
    private String mXEntr;
    private String mYEntr;
    private String miniZoom;
    private String poiName;
    private String poiids;
    private String pxs;
    private String pys;
    private String renderRank;
    private String renderStyleMain;
    private String renderStyleSub;
    private String shortName;
    private String src;
    private String tagColors;
    private String tags;
    private String value;

    public int isShown() {
        return (this.poiids == null || "".equals(this.poiids)) ? 8 : 0;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public void setPoiids(String str) {
        this.poiids = str;
    }

    public String getPoiids() {
        return this.poiids;
    }

    public void setChildType(String str) {
        this.childTypes = str;
    }

    public String getChildType() {
        return this.childTypes;
    }

    public void setShortName(String str) {
        this.shortName = str;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setPoiName(String str) {
        this.poiName = str;
    }

    public String getPoiName() {
        return this.poiName;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public void setDeepinfo(String str) {
        this.deepinfo = str;
    }

    public void setDistence(String str) {
        this.distence = str;
    }

    public void setPxs(String str) {
        this.pxs = str;
    }

    public void setPys(String str) {
        this.pys = str;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String str) {
        this.src = str;
    }

    public void setShowChild(int i) {
        this.mShowChild = i;
    }

    public String[] getShoppingMallDatas() {
        if (this.mShoppingMallDatas == null) {
            return null;
        }
        return (String[]) Arrays.copyOf(this.mShoppingMallDatas, this.mShoppingMallDatas.length);
    }

    public String getShoppingMallUrl() {
        return this.mShoppingMallUrl;
    }

    public String[] getGas_prices() {
        if (this.gas_prices == null) {
            return null;
        }
        return (String[]) Arrays.copyOf(this.gas_prices, this.gas_prices.length);
    }

    public String[] getGas_types() {
        if (this.gas_types == null) {
            return null;
        }
        return (String[]) Arrays.copyOf(this.gas_types, this.gas_types.length);
    }

    public String[] getGas_utils() {
        if (this.gas_utils == null) {
            return null;
        }
        return (String[]) Arrays.copyOf(this.gas_utils, this.gas_utils.length);
    }

    public int getShowChild() {
        return this.mShowChild;
    }

    public void setGas_utils(String[] strArr) {
        if (strArr != null) {
            this.gas_utils = (String[]) Arrays.copyOf(strArr, strArr.length);
        }
    }

    public void setGas_types(String[] strArr) {
        if (strArr != null) {
            this.gas_types = (String[]) Arrays.copyOf(strArr, strArr.length);
        }
    }

    public void setGas_prices(String[] strArr) {
        if (strArr != null) {
            this.gas_prices = (String[]) Arrays.copyOf(strArr, strArr.length);
        }
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public String getLabelNew() {
        return this.labelNew;
    }

    public void setLabelNew(String str) {
        this.labelNew = str;
    }

    public void setShoppingMallDatas(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (!str.contains(PoiLayoutTemplate.SHOPPING_SPLITER)) {
                this.mShoppingMallDatas = new String[1];
                this.mShoppingMallDatas[0] = str;
                return;
            }
            this.mShoppingMallDatas = str.split(PoiLayoutTemplate.SHOPPING_SPLITER_REG);
        }
    }

    public void setShoppingMallUrl(String str) {
        this.mShoppingMallUrl = str;
    }

    public String[] splitData(String str) {
        if (str == null) {
            return null;
        }
        if (str.contains(PoiLayoutTemplate.SPLITER)) {
            return str.split(PoiLayoutTemplate.SPLITER_REG);
        }
        return new String[]{str};
    }

    public List<ChildStationPoiData> getChildStation() {
        ArrayList arrayList = new ArrayList();
        String[] splitData = splitData(this.value);
        String[] splitData2 = splitData(this.pxs);
        String[] splitData3 = splitData(this.pys);
        String[] splitData4 = splitData(this.poiids);
        String[] splitData5 = splitData(this.busAlias);
        for (int i = 0; i < splitData4.length; i++) {
            try {
                ChildStationPoiData childStationPoiData = (ChildStationPoiData) POIFactory.createPOI(ChildStationPoiData.class);
                childStationPoiData.setPoiId(splitData4[i]);
                childStationPoiData.setId(splitData4[i]);
                try {
                    if (!TextUtils.isEmpty(splitData2[i]) && !TextUtils.isEmpty(splitData3[i])) {
                        childStationPoiData.setPoint(new GeoPoint(Double.parseDouble(splitData2[i]), Double.parseDouble(splitData3[i])));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                childStationPoiData.setAddr(splitData[i]);
                childStationPoiData.setBusinfoAlias(splitData5[i]);
                childStationPoiData.getPoiExtra().put("bus_alias", splitData5[i]);
                childStationPoiData.getPoiExtra().put("lineKey", splitData[i]);
                arrayList.add(childStationPoiData);
            } catch (Exception unused) {
            }
        }
        return arrayList;
    }

    public String getMarkBGId() {
        if (TextUtils.isEmpty(this.src)) {
            return "";
        }
        if (!this.src.contains(PoiLayoutTemplate.SPLITER)) {
            String[] strArr = new String[1];
            int indexOf = this.src.indexOf(58);
            if (indexOf < 0) {
                return "";
            }
            strArr[0] = this.src.substring(indexOf + 1);
            return strArr[0];
        }
        String[] split = this.src.split(PoiLayoutTemplate.SPLITER_REG);
        for (int i = 0; i < split.length; i++) {
            int indexOf2 = split[i].indexOf(58);
            if (indexOf2 > 0) {
                split[i] = split[i].substring(indexOf2 + 1);
            }
        }
        if (split.length <= 0) {
            return "";
        }
        return split[0];
    }

    public boolean isOnlineBg(String str) {
        return !TextUtils.isEmpty(str) && !str.contains("localhost");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:211:0x04a6, code lost:
        if (r10.startsWith("0X") != false) goto L_0x04b6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0387 A[SYNTHETIC, Splitter:B:118:0x0387] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x03d5 A[SYNTHETIC, Splitter:B:137:0x03d5] */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x0426 A[SYNTHETIC, Splitter:B:167:0x0426] */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x0472 A[Catch:{ Exception -> 0x04f7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:259:0x054b  */
    /* JADX WARNING: Removed duplicated region for block: B:302:0x05b8  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x015e A[SYNTHETIC, Splitter:B:32:0x015e] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x02ba A[Catch:{ Exception -> 0x05dd }] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x02d1  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x030b A[Catch:{ Exception -> 0x02f3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0319 A[SYNTHETIC, Splitter:B:89:0x0319] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.autonavi.common.model.POI> getChildPois(com.autonavi.bundle.entity.common.searchpoi.SearchPoi r43) {
        /*
            r42 = this;
            r1 = r42
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.String r3 = r1.shortName
            java.lang.String[] r3 = r1.splitData(r3)
            java.lang.String r4 = r1.pxs
            java.lang.String[] r4 = r1.splitData(r4)
            java.lang.String r5 = r1.pys
            java.lang.String[] r5 = r1.splitData(r5)
            java.lang.String r6 = r1.poiids
            java.lang.String[] r6 = r1.splitData(r6)
            java.lang.String r7 = r1.childTypes
            java.lang.String[] r7 = r1.splitData(r7)
            java.lang.String r8 = r1.address
            java.lang.String[] r8 = r1.splitData(r8)
            java.lang.String r9 = r1.poiName
            java.lang.String[] r9 = r1.splitData(r9)
            java.lang.String r10 = r1.deepinfo
            java.lang.String[] r10 = r1.splitData(r10)
            java.lang.String r11 = r1.distence
            java.lang.String[] r11 = r1.splitData(r11)
            java.lang.String r12 = r1.renderStyleMain
            java.lang.String[] r12 = r1.splitData(r12)
            java.lang.String r13 = r1.renderStyleSub
            java.lang.String[] r13 = r1.splitData(r13)
            java.lang.String r14 = r1.renderRank
            java.lang.String[] r14 = r1.splitData(r14)
            java.lang.String r15 = r1.miniZoom
            java.lang.String[] r15 = r1.splitData(r15)
            r16 = r15
            java.lang.String r15 = r1.anchor
            java.lang.String[] r15 = r1.splitData(r15)
            r17 = r15
            java.lang.String r15 = r1.mXEntr
            java.lang.String[] r15 = r1.splitData(r15)
            r18 = r15
            java.lang.String r15 = r1.mYEntr
            java.lang.String[] r15 = r1.splitData(r15)
            r19 = r15
            java.lang.String r15 = r1.label
            java.lang.String[] r15 = r1.splitData(r15)
            r20 = r14
            java.lang.String r14 = r1.labelNew
            java.lang.String[] r14 = r1.splitData(r14)
            r21 = r13
            java.lang.String r13 = r1.tags
            java.lang.String[] r13 = r1.splitData(r13)
            r22 = r12
            java.lang.String r12 = r1.tagColors
            java.lang.String[] r12 = r1.splitData(r12)
            if (r6 != 0) goto L_0x0090
            return r2
        L_0x0090:
            r23 = r2
            r24 = r14
            r2 = 0
        L_0x0095:
            int r14 = r6.length
            if (r2 >= r14) goto L_0x060d
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r14 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r14 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r14)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r14 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r14
            r25 = r15
            java.util.ArrayList r15 = new java.util.ArrayList
            r15.<init>()
            r26 = r5
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            if (r10 == 0) goto L_0x00ff
            r27 = r4
            int r4 = r10.length     // Catch:{ Exception -> 0x00df }
            if (r4 <= r2) goto L_0x0101
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r4 = new com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate     // Catch:{ Exception -> 0x00df }
            r4.<init>()     // Catch:{ Exception -> 0x00df }
            r28 = r7
            r7 = r10[r2]     // Catch:{ Exception -> 0x00e1 }
            r4.setValue(r7)     // Catch:{ Exception -> 0x00e1 }
            r7 = 2010(0x7da, float:2.817E-42)
            r4.setId(r7)     // Catch:{ Exception -> 0x00e1 }
            java.lang.String r7 = "html"
            r4.setType(r7)     // Catch:{ Exception -> 0x00e1 }
            java.lang.String r7 = "deepinfo"
            r4.setName(r7)     // Catch:{ Exception -> 0x00e1 }
            r15.add(r4)     // Catch:{ Exception -> 0x00e1 }
            int r7 = r4.getId()     // Catch:{ Exception -> 0x00e1 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x00e1 }
            r5.put(r7, r4)     // Catch:{ Exception -> 0x00e1 }
            goto L_0x0103
        L_0x00df:
            r28 = r7
        L_0x00e1:
            r35 = r3
            r30 = r6
            r31 = r8
            r29 = r10
        L_0x00e9:
            r33 = r16
            r34 = r17
            r37 = r18
            r39 = r19
            r32 = r20
            r36 = r21
            r8 = r22
            r1 = r23
            r40 = r24
            r38 = r25
            goto L_0x05e5
        L_0x00ff:
            r27 = r4
        L_0x0101:
            r28 = r7
        L_0x0103:
            if (r11 == 0) goto L_0x012f
            int r4 = r11.length     // Catch:{ Exception -> 0x00e1 }
            if (r4 <= r2) goto L_0x012f
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r4 = new com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate     // Catch:{ Exception -> 0x00e1 }
            r4.<init>()     // Catch:{ Exception -> 0x00e1 }
            r7 = 1014(0x3f6, float:1.421E-42)
            r4.setId(r7)     // Catch:{ Exception -> 0x00e1 }
            java.lang.String r7 = "html"
            r4.setType(r7)     // Catch:{ Exception -> 0x00e1 }
            r7 = r11[r2]     // Catch:{ Exception -> 0x00e1 }
            r4.setValue(r7)     // Catch:{ Exception -> 0x00e1 }
            java.lang.String r7 = "distance"
            r4.setName(r7)     // Catch:{ Exception -> 0x00e1 }
            r15.add(r4)     // Catch:{ Exception -> 0x00e1 }
            int r7 = r4.getId()     // Catch:{ Exception -> 0x00e1 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x00e1 }
            r5.put(r7, r4)     // Catch:{ Exception -> 0x00e1 }
        L_0x012f:
            if (r8 == 0) goto L_0x015c
            int r4 = r8.length     // Catch:{ Exception -> 0x00e1 }
            if (r4 <= r2) goto L_0x015c
            com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate r4 = new com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate     // Catch:{ Exception -> 0x00e1 }
            r4.<init>()     // Catch:{ Exception -> 0x00e1 }
            r7 = 1010(0x3f2, float:1.415E-42)
            r4.setId(r7)     // Catch:{ Exception -> 0x00e1 }
            r7 = r8[r2]     // Catch:{ Exception -> 0x00e1 }
            r4.setValue(r7)     // Catch:{ Exception -> 0x00e1 }
            java.lang.String r7 = "text"
            r4.setType(r7)     // Catch:{ Exception -> 0x00e1 }
            java.lang.String r7 = "address"
            r4.setName(r7)     // Catch:{ Exception -> 0x00e1 }
            r15.add(r4)     // Catch:{ Exception -> 0x00e1 }
            int r7 = r4.getId()     // Catch:{ Exception -> 0x00e1 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x00e1 }
            r5.put(r7, r4)     // Catch:{ Exception -> 0x00e1 }
        L_0x015c:
            if (r13 == 0) goto L_0x01a6
            int r4 = r13.length     // Catch:{ Exception -> 0x019c }
            if (r4 <= r2) goto L_0x01a6
            if (r12 == 0) goto L_0x01a6
            int r4 = r12.length     // Catch:{ Exception -> 0x019c }
            if (r4 <= r2) goto L_0x01a6
            r4 = r13[r2]     // Catch:{ Exception -> 0x019c }
            r7 = r12[r2]     // Catch:{ Exception -> 0x019c }
            java.lang.String r4 = r1.getFakeTemplate2038Value(r4, r7)     // Catch:{ Exception -> 0x019c }
            boolean r7 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x019c }
            if (r7 != 0) goto L_0x01a6
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r7 = new com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate     // Catch:{ Exception -> 0x019c }
            r7.<init>()     // Catch:{ Exception -> 0x019c }
            r29 = r10
            r10 = 2038(0x7f6, float:2.856E-42)
            r7.setId(r10)     // Catch:{ Exception -> 0x019e }
            java.lang.String r10 = "label"
            r7.setName(r10)     // Catch:{ Exception -> 0x019e }
            java.lang.String r10 = "html"
            r7.setType(r10)     // Catch:{ Exception -> 0x019e }
            r7.setValue(r4)     // Catch:{ Exception -> 0x019e }
            r15.add(r7)     // Catch:{ Exception -> 0x019e }
            int r4 = r7.getId()     // Catch:{ Exception -> 0x019e }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x019e }
            r5.put(r4, r7)     // Catch:{ Exception -> 0x019e }
            goto L_0x01a8
        L_0x019c:
            r29 = r10
        L_0x019e:
            r35 = r3
            r30 = r6
        L_0x01a2:
            r31 = r8
            goto L_0x00e9
        L_0x01a6:
            r29 = r10
        L_0x01a8:
            com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate r4 = new com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate     // Catch:{ Exception -> 0x019e }
            r4.<init>()     // Catch:{ Exception -> 0x019e }
            r7 = 2001(0x7d1, float:2.804E-42)
            r4.setId(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "text"
            r4.setType(r7)     // Catch:{ Exception -> 0x019e }
            r7 = r9[r2]     // Catch:{ Exception -> 0x019e }
            r4.setValue(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "name"
            r4.setName(r7)     // Catch:{ Exception -> 0x019e }
            r15.add(r4)     // Catch:{ Exception -> 0x019e }
            int r7 = r4.getId()     // Catch:{ Exception -> 0x019e }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x019e }
            r5.put(r7, r4)     // Catch:{ Exception -> 0x019e }
            com.autonavi.bundle.entity.infolite.internal.template.PoiLinkTemplate r4 = new com.autonavi.bundle.entity.infolite.internal.template.PoiLinkTemplate     // Catch:{ Exception -> 0x019e }
            r4.<init>()     // Catch:{ Exception -> 0x019e }
            r7 = 1002(0x3ea, float:1.404E-42)
            r4.setId(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "link"
            r4.setType(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "detail"
            r4.setName(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "detail"
            r4.setAction(r7)     // Catch:{ Exception -> 0x019e }
            r15.add(r4)     // Catch:{ Exception -> 0x019e }
            int r7 = r4.getId()     // Catch:{ Exception -> 0x019e }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x019e }
            r5.put(r7, r4)     // Catch:{ Exception -> 0x019e }
            com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate r4 = new com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate     // Catch:{ Exception -> 0x019e }
            r4.<init>()     // Catch:{ Exception -> 0x019e }
            r7 = 1003(0x3eb, float:1.406E-42)
            r4.setId(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "button"
            r4.setType(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "sebxy"
            r4.setName(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "sebxy"
            r4.setAction(r7)     // Catch:{ Exception -> 0x019e }
            r15.add(r4)     // Catch:{ Exception -> 0x019e }
            int r7 = r4.getId()     // Catch:{ Exception -> 0x019e }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x019e }
            r5.put(r7, r4)     // Catch:{ Exception -> 0x019e }
            com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate r4 = new com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate     // Catch:{ Exception -> 0x019e }
            r4.<init>()     // Catch:{ Exception -> 0x019e }
            r7 = 2003(0x7d3, float:2.807E-42)
            r4.setId(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "button"
            r4.setType(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "route"
            r4.setName(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "route"
            r4.setAction(r7)     // Catch:{ Exception -> 0x019e }
            r15.add(r4)     // Catch:{ Exception -> 0x019e }
            int r7 = r4.getId()     // Catch:{ Exception -> 0x019e }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x019e }
            r5.put(r7, r4)     // Catch:{ Exception -> 0x019e }
            com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate r4 = new com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate     // Catch:{ Exception -> 0x019e }
            r4.<init>()     // Catch:{ Exception -> 0x019e }
            r7 = 1005(0x3ed, float:1.408E-42)
            r4.setId(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "button"
            r4.setType(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "nav"
            r4.setName(r7)     // Catch:{ Exception -> 0x019e }
            java.lang.String r7 = "nav"
            r4.setAction(r7)     // Catch:{ Exception -> 0x019e }
            r15.add(r4)     // Catch:{ Exception -> 0x019e }
            int r7 = r4.getId()     // Catch:{ Exception -> 0x019e }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x019e }
            r5.put(r7, r4)     // Catch:{ Exception -> 0x019e }
            r14.setTemplateData(r15)     // Catch:{ Exception -> 0x019e }
            r14.setTemplateDataMap(r5)     // Catch:{ Exception -> 0x019e }
            r4 = r9[r2]     // Catch:{ Exception -> 0x019e }
            r14.setName(r4)     // Catch:{ Exception -> 0x019e }
            r4 = r3[r2]     // Catch:{ Exception -> 0x019e }
            r14.setShortName(r4)     // Catch:{ Exception -> 0x019e }
            r4 = r6[r2]     // Catch:{ Exception -> 0x019e }
            r14.setId(r4)     // Catch:{ Exception -> 0x019e }
            r4 = r28[r2]     // Catch:{ Exception -> 0x019e }
            r14.setSubType(r4)     // Catch:{ Exception -> 0x019e }
            java.lang.String r4 = r1.address     // Catch:{ Exception -> 0x019e }
            if (r4 == 0) goto L_0x0296
            int r4 = r8.length     // Catch:{ Exception -> 0x019e }
            if (r4 <= r2) goto L_0x0296
            r4 = r8[r2]     // Catch:{ Exception -> 0x019e }
            r14.setAddr(r4)     // Catch:{ Exception -> 0x019e }
        L_0x0296:
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x019e }
            r5 = r27[r2]     // Catch:{ Exception -> 0x019e }
            r30 = r6
            double r5 = java.lang.Double.parseDouble(r5)     // Catch:{ Exception -> 0x05e1 }
            r7 = r26[r2]     // Catch:{ Exception -> 0x05e1 }
            r31 = r8
            double r7 = java.lang.Double.parseDouble(r7)     // Catch:{ Exception -> 0x05dd }
            r4.<init>(r5, r7)     // Catch:{ Exception -> 0x05dd }
            r14.setPoint(r4)     // Catch:{ Exception -> 0x05dd }
            r4 = r28[r2]     // Catch:{ Exception -> 0x05dd }
            int r4 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x05dd }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r5 = r14.getPoiChildrenInfo()     // Catch:{ Exception -> 0x05dd }
            if (r5 != 0) goto L_0x02c2
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r5 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData     // Catch:{ Exception -> 0x05dd }
            r5.<init>()     // Catch:{ Exception -> 0x05dd }
            r14.setPoiChildrenInfo(r5)     // Catch:{ Exception -> 0x05dd }
        L_0x02c2:
            r5 = r25
            int r6 = r5.length     // Catch:{ Exception -> 0x05c6 }
            if (r6 <= 0) goto L_0x02cf
            int r6 = r5.length     // Catch:{ Exception -> 0x05c6 }
            if (r2 >= r6) goto L_0x02cf
            r6 = r5[r2]     // Catch:{ Exception -> 0x05c6 }
            r14.setLabel(r6)     // Catch:{ Exception -> 0x05c6 }
        L_0x02cf:
            if (r24 == 0) goto L_0x030b
            r6 = r24
            int r7 = r6.length     // Catch:{ Exception -> 0x02f3 }
            if (r7 <= 0) goto L_0x030d
            int r7 = r6.length     // Catch:{ Exception -> 0x02f3 }
            if (r2 >= r7) goto L_0x030d
            r7 = r6[r2]     // Catch:{ Exception -> 0x02f3 }
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x02f3 }
            if (r8 != 0) goto L_0x030d
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ Exception -> 0x02f3 }
            r8.<init>()     // Catch:{ Exception -> 0x02f3 }
            r8.add(r7)     // Catch:{ Exception -> 0x02f3 }
            java.util.HashMap r7 = r14.getPoiExtra()     // Catch:{ Exception -> 0x02f3 }
            java.lang.String r10 = "poi_deep_info"
            r7.put(r10, r8)     // Catch:{ Exception -> 0x02f3 }
            goto L_0x030d
        L_0x02f3:
            r35 = r3
            r38 = r5
            r40 = r6
            r33 = r16
            r34 = r17
            r37 = r18
            r39 = r19
            r32 = r20
            r36 = r21
            r8 = r22
        L_0x0307:
            r1 = r23
            goto L_0x05e5
        L_0x030b:
            r6 = r24
        L_0x030d:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r7 = r14.getPoiChildrenInfo()     // Catch:{ Exception -> 0x02f3 }
            r7.childType = r4     // Catch:{ Exception -> 0x02f3 }
            r1.parseMarkerIcon(r4, r14)     // Catch:{ Exception -> 0x02f3 }
            r4 = 1
            if (r43 == 0) goto L_0x053c
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r8 = r43.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0526 }
            if (r8 == 0) goto L_0x053c
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r8 = r43.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0526 }
            boolean r8 = r8.bFlag     // Catch:{ Exception -> 0x0526 }
            if (r8 == 0) goto L_0x053c
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r8 = new com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData     // Catch:{ Exception -> 0x0526 }
            r8.<init>()     // Catch:{ Exception -> 0x0526 }
            r14.setIDynamicRenderInfo(r8)     // Catch:{ Exception -> 0x0526 }
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r8 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0526 }
            r8.bFlag = r4     // Catch:{ Exception -> 0x0526 }
            r8 = r22
            int r10 = r8.length     // Catch:{ Exception -> 0x051b }
            if (r2 >= r10) goto L_0x037b
            r10 = r8[r2]     // Catch:{ Exception -> 0x0368 }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0368 }
            if (r10 != 0) goto L_0x037b
            r10 = r8[r2]     // Catch:{ Exception -> 0x0368 }
            boolean r15 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0368 }
            if (r15 == 0) goto L_0x0351
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r15 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0368 }
            r4 = 0
            r15.bFlag = r4     // Catch:{ Exception -> 0x0368 }
        L_0x0351:
            boolean r4 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0368 }
            if (r4 != 0) goto L_0x0382
            boolean r4 = android.text.TextUtils.isDigitsOnly(r10)     // Catch:{ Exception -> 0x0368 }
            if (r4 == 0) goto L_0x0382
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r4 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0368 }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ Exception -> 0x0368 }
            r4.mainStyle = r10     // Catch:{ Exception -> 0x0368 }
            goto L_0x0382
        L_0x0368:
            r35 = r3
            r38 = r5
            r40 = r6
            r33 = r16
            r34 = r17
            r37 = r18
            r39 = r19
            r32 = r20
            r36 = r21
            goto L_0x0307
        L_0x037b:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r4 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x051b }
            r10 = 0
            r4.bFlag = r10     // Catch:{ Exception -> 0x051b }
        L_0x0382:
            r4 = r21
            int r10 = r4.length     // Catch:{ Exception -> 0x0510 }
            if (r2 >= r10) goto L_0x03c9
            r10 = r4[r2]     // Catch:{ Exception -> 0x03b5 }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x03b5 }
            if (r10 != 0) goto L_0x03c9
            r10 = r4[r2]     // Catch:{ Exception -> 0x03b5 }
            boolean r15 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x03b5 }
            if (r15 == 0) goto L_0x039e
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r15 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x03b5 }
            r1 = 0
            r15.bFlag = r1     // Catch:{ Exception -> 0x03b5 }
        L_0x039e:
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x03b5 }
            if (r1 != 0) goto L_0x03d0
            boolean r1 = android.text.TextUtils.isDigitsOnly(r10)     // Catch:{ Exception -> 0x03b5 }
            if (r1 == 0) goto L_0x03d0
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r1 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x03b5 }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ Exception -> 0x03b5 }
            r1.subStyle = r10     // Catch:{ Exception -> 0x03b5 }
            goto L_0x03d0
        L_0x03b5:
            r35 = r3
            r36 = r4
            r38 = r5
            r40 = r6
            r33 = r16
            r34 = r17
            r37 = r18
            r39 = r19
            r32 = r20
            goto L_0x0307
        L_0x03c9:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r1 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0510 }
            r10 = 0
            r1.bFlag = r10     // Catch:{ Exception -> 0x0510 }
        L_0x03d0:
            r1 = r20
            int r10 = r1.length     // Catch:{ Exception -> 0x0505 }
            if (r2 >= r10) goto L_0x0418
            r10 = r1[r2]     // Catch:{ Exception -> 0x0408 }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0408 }
            if (r10 != 0) goto L_0x0418
            r10 = r1[r2]     // Catch:{ Exception -> 0x0408 }
            boolean r15 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0408 }
            if (r15 == 0) goto L_0x03ef
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r15 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0408 }
            r32 = r1
            r1 = 0
            r15.bFlag = r1     // Catch:{ Exception -> 0x040a }
            goto L_0x03f1
        L_0x03ef:
            r32 = r1
        L_0x03f1:
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x040a }
            if (r1 != 0) goto L_0x0421
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r1 = r14.getIDynamicRenderInfo()     // Catch:{ NumberFormatException -> 0x0402 }
            float r10 = java.lang.Float.parseFloat(r10)     // Catch:{ NumberFormatException -> 0x0402 }
            r1.fRank = r10     // Catch:{ NumberFormatException -> 0x0402 }
            goto L_0x0421
        L_0x0402:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ Exception -> 0x040a }
            goto L_0x0421
        L_0x0408:
            r32 = r1
        L_0x040a:
            r35 = r3
            r36 = r4
            r38 = r5
            r40 = r6
            r33 = r16
        L_0x0414:
            r34 = r17
            goto L_0x0536
        L_0x0418:
            r32 = r1
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r1 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0507 }
            r10 = 0
            r1.bFlag = r10     // Catch:{ Exception -> 0x0507 }
        L_0x0421:
            r1 = r16
            int r10 = r1.length     // Catch:{ Exception -> 0x04fe }
            if (r2 >= r10) goto L_0x0464
            r10 = r1[r2]     // Catch:{ Exception -> 0x0459 }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0459 }
            if (r10 != 0) goto L_0x0464
            r10 = r1[r2]     // Catch:{ Exception -> 0x0459 }
            boolean r15 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0459 }
            if (r15 == 0) goto L_0x0440
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r15 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0459 }
            r33 = r1
            r1 = 0
            r15.bFlag = r1     // Catch:{ Exception -> 0x045b }
            goto L_0x0442
        L_0x0440:
            r33 = r1
        L_0x0442:
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x045b }
            if (r1 != 0) goto L_0x046d
            boolean r1 = android.text.TextUtils.isDigitsOnly(r10)     // Catch:{ Exception -> 0x045b }
            if (r1 == 0) goto L_0x046d
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r1 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x045b }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ Exception -> 0x045b }
            r1.minizoom = r10     // Catch:{ Exception -> 0x045b }
            goto L_0x046d
        L_0x0459:
            r33 = r1
        L_0x045b:
            r35 = r3
            r36 = r4
            r38 = r5
            r40 = r6
            goto L_0x0414
        L_0x0464:
            r33 = r1
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r1 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0500 }
            r10 = 0
            r1.bFlag = r10     // Catch:{ Exception -> 0x0500 }
        L_0x046d:
            r1 = r17
            int r10 = r1.length     // Catch:{ Exception -> 0x04f7 }
            if (r2 >= r10) goto L_0x04e8
            r10 = r1[r2]     // Catch:{ Exception -> 0x04f7 }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x04f7 }
            if (r10 != 0) goto L_0x04e8
            r10 = r1[r2]     // Catch:{ Exception -> 0x04f7 }
            java.lang.String r10 = r10.trim()     // Catch:{ Exception -> 0x04f7 }
            boolean r15 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x04f7 }
            if (r15 == 0) goto L_0x0490
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r15 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x04f7 }
            r34 = r1
            r1 = 0
            r15.bFlag = r1     // Catch:{ Exception -> 0x04f9 }
            goto L_0x0492
        L_0x0490:
            r34 = r1
        L_0x0492:
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x04f9 }
            if (r1 != 0) goto L_0x04a9
            java.lang.String r1 = "0x"
            boolean r1 = r10.startsWith(r1)     // Catch:{ NumberFormatException -> 0x04dd }
            if (r1 != 0) goto L_0x04b6
            java.lang.String r1 = "0X"
            boolean r1 = r10.startsWith(r1)     // Catch:{ NumberFormatException -> 0x04af }
            if (r1 == 0) goto L_0x04a9
            goto L_0x04b6
        L_0x04a9:
            r35 = r3
            r36 = r4
            goto L_0x0548
        L_0x04af:
            r0 = move-exception
            r1 = r0
            r35 = r3
            r36 = r4
            goto L_0x04e3
        L_0x04b6:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r1 = r14.getIDynamicRenderInfo()     // Catch:{ NumberFormatException -> 0x04d0 }
            r15 = 2
            java.lang.String r10 = r10.substring(r15)     // Catch:{ NumberFormatException -> 0x04d0 }
            r15 = 16
            r35 = r3
            r36 = r4
            long r3 = java.lang.Long.parseLong(r10, r15)     // Catch:{ NumberFormatException -> 0x04ce }
            int r3 = (int) r3     // Catch:{ NumberFormatException -> 0x04ce }
            r1.anchor = r3     // Catch:{ NumberFormatException -> 0x04ce }
            goto L_0x0548
        L_0x04ce:
            r0 = move-exception
            goto L_0x04d5
        L_0x04d0:
            r0 = move-exception
            r35 = r3
            r36 = r4
        L_0x04d5:
            r1 = r0
            r1.printStackTrace()     // Catch:{ NumberFormatException -> 0x04db }
            goto L_0x0548
        L_0x04db:
            r0 = move-exception
            goto L_0x04e2
        L_0x04dd:
            r0 = move-exception
            r35 = r3
            r36 = r4
        L_0x04e2:
            r1 = r0
        L_0x04e3:
            r1.printStackTrace()     // Catch:{ Exception -> 0x0532 }
            goto L_0x0548
        L_0x04e8:
            r34 = r1
            r35 = r3
            r36 = r4
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r1 = r14.getIDynamicRenderInfo()     // Catch:{ Exception -> 0x0532 }
            r3 = 0
            r1.bFlag = r3     // Catch:{ Exception -> 0x0532 }
            goto L_0x0549
        L_0x04f7:
            r34 = r1
        L_0x04f9:
            r35 = r3
            r36 = r4
            goto L_0x0532
        L_0x04fe:
            r33 = r1
        L_0x0500:
            r35 = r3
            r36 = r4
            goto L_0x050d
        L_0x0505:
            r32 = r1
        L_0x0507:
            r35 = r3
            r36 = r4
            r33 = r16
        L_0x050d:
            r34 = r17
            goto L_0x0532
        L_0x0510:
            r35 = r3
            r36 = r4
            r33 = r16
            r34 = r17
            r32 = r20
            goto L_0x0532
        L_0x051b:
            r35 = r3
            r33 = r16
            r34 = r17
            r32 = r20
            r36 = r21
            goto L_0x0532
        L_0x0526:
            r35 = r3
            r33 = r16
            r34 = r17
            r32 = r20
            r36 = r21
            r8 = r22
        L_0x0532:
            r38 = r5
            r40 = r6
        L_0x0536:
            r37 = r18
        L_0x0538:
            r39 = r19
            goto L_0x0307
        L_0x053c:
            r35 = r3
            r33 = r16
            r34 = r17
            r32 = r20
            r36 = r21
            r8 = r22
        L_0x0548:
            r3 = 0
        L_0x0549:
            if (r18 == 0) goto L_0x05b8
            r1 = r18
            int r4 = r1.length     // Catch:{ Exception -> 0x05b1 }
            if (r2 >= r4) goto L_0x05aa
            r4 = r19
            int r10 = r4.length     // Catch:{ Exception -> 0x05a0 }
            if (r2 >= r10) goto L_0x0597
            r10 = r1[r2]     // Catch:{ Exception -> 0x05a0 }
            r15 = r4[r2]     // Catch:{ Exception -> 0x05a0 }
            boolean r16 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x05a0 }
            if (r16 != 0) goto L_0x0597
            boolean r16 = android.text.TextUtils.isEmpty(r15)     // Catch:{ Exception -> 0x05a0 }
            if (r16 != 0) goto L_0x0597
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x05a0 }
            r37 = r1
            r1 = 1
            r3.<init>(r1)     // Catch:{ Exception -> 0x05a2 }
            com.autonavi.common.model.GeoPoint r1 = new com.autonavi.common.model.GeoPoint     // Catch:{ NumberFormatException -> 0x0588 }
            r39 = r4
            r38 = r5
            double r4 = java.lang.Double.parseDouble(r10)     // Catch:{ NumberFormatException -> 0x0586, Exception -> 0x05a6 }
            r40 = r6
            double r6 = java.lang.Double.parseDouble(r15)     // Catch:{ NumberFormatException -> 0x0584 }
            r1.<init>(r4, r6)     // Catch:{ NumberFormatException -> 0x0584 }
            r3.add(r1)     // Catch:{ NumberFormatException -> 0x0584 }
            goto L_0x0593
        L_0x0584:
            r0 = move-exception
            goto L_0x058f
        L_0x0586:
            r0 = move-exception
            goto L_0x058d
        L_0x0588:
            r0 = move-exception
            r39 = r4
            r38 = r5
        L_0x058d:
            r40 = r6
        L_0x058f:
            r1 = r0
            r1.printStackTrace()     // Catch:{ Exception -> 0x0307 }
        L_0x0593:
            r14.setEntranceList(r3)     // Catch:{ Exception -> 0x0307 }
            goto L_0x05c0
        L_0x0597:
            r37 = r1
            r39 = r4
            r38 = r5
            r40 = r6
            goto L_0x05c0
        L_0x05a0:
            r37 = r1
        L_0x05a2:
            r39 = r4
            r38 = r5
        L_0x05a6:
            r40 = r6
            goto L_0x0307
        L_0x05aa:
            r37 = r1
            r38 = r5
            r40 = r6
            goto L_0x05be
        L_0x05b1:
            r37 = r1
            r38 = r5
            r40 = r6
            goto L_0x0538
        L_0x05b8:
            r38 = r5
            r40 = r6
            r37 = r18
        L_0x05be:
            r39 = r19
        L_0x05c0:
            r1 = r23
            r1.add(r14)
            goto L_0x05e5
        L_0x05c6:
            r35 = r3
            r38 = r5
            r33 = r16
            r34 = r17
            r37 = r18
            r39 = r19
            r32 = r20
            r36 = r21
            r8 = r22
            r1 = r23
            r40 = r24
            goto L_0x05e5
        L_0x05dd:
            r35 = r3
            goto L_0x00e9
        L_0x05e1:
            r35 = r3
            goto L_0x01a2
        L_0x05e5:
            int r2 = r2 + 1
            r23 = r1
            r22 = r8
            r5 = r26
            r4 = r27
            r7 = r28
            r10 = r29
            r6 = r30
            r8 = r31
            r20 = r32
            r16 = r33
            r17 = r34
            r3 = r35
            r21 = r36
            r18 = r37
            r15 = r38
            r19 = r39
            r24 = r40
            r1 = r42
            goto L_0x0095
        L_0x060d:
            r1 = r23
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.searchservice.utils.PoiArrayTemplate.getChildPois(com.autonavi.bundle.entity.common.searchpoi.SearchPoi):java.util.List");
    }

    public String getBusAlias() {
        return this.busAlias;
    }

    public void setBusAlias(String str) {
        this.busAlias = str;
    }

    public void setAnchor(String str) {
        this.anchor = str;
    }

    public void setRenderStyleMain(String str) {
        this.renderStyleMain = str;
    }

    public void setRenderStyleSub(String str) {
        this.renderStyleSub = str;
    }

    public void setMiniZoom(String str) {
        this.miniZoom = str;
    }

    public void setRenderRank(String str) {
        this.renderRank = str;
    }

    public void setXEntr(String str) {
        this.mXEntr = str;
    }

    public void setYEntr(String str) {
        this.mYEntr = str;
    }

    public void setTags(String str) {
        this.tags = str;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTagColors(String str) {
        this.tagColors = str;
    }

    public String getTagColors() {
        return this.tagColors;
    }

    private String getFakeTemplate2038Value(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        String[] split = str.split(";");
        String[] split2 = str2.split(";");
        for (int i = 0; i < split.length; i++) {
            if (!TextUtils.isEmpty(split[i])) {
                sb.append(String.format("<font color='%s'>%s</font>", new Object[]{split2[i], split[i]}));
                if (i != split.length - 1) {
                    sb.append(PoiLayoutTemplate.SPLITER);
                }
            }
        }
        return sb.toString();
    }

    @SuppressFBWarnings({"SF_SWITCH_FALLTHROUGH"})
    private void parseMarkerIcon(int i, SearchPoi searchPoi) {
        if (i != 31) {
            if (i == 34) {
                searchPoi.setIconId(R.drawable.child_airport);
                searchPoi.setMarkerBGRes(R.drawable.child_airport_bg);
            } else if (i != 107) {
                switch (i) {
                    case 41:
                        searchPoi.setIconId(R.drawable.child_park);
                        searchPoi.setMarkerBGRes(R.drawable.child_park_bg);
                        return;
                    case 42:
                        searchPoi.setIconId(R.drawable.child_ticket);
                        searchPoi.setMarkerBGRes(R.drawable.child_ticket_bg);
                        return;
                    case 43:
                        searchPoi.setIconId(R.drawable.child_fly);
                        searchPoi.setMarkerBGRes(R.drawable.child_fly_bg);
                        return;
                    case 44:
                        searchPoi.setIconId(R.drawable.child_arrive);
                        searchPoi.setMarkerBGRes(R.drawable.child_arrive_bg);
                        return;
                    case 45:
                        searchPoi.setIconId(R.drawable.child_in);
                        searchPoi.setMarkerBGRes(R.drawable.child_in_bg);
                        return;
                    case 46:
                        searchPoi.setIconId(R.drawable.child_exist);
                        searchPoi.setMarkerBGRes(R.drawable.child_exist_bg);
                        return;
                    default:
                        searchPoi.setIconId(R.drawable.child_more);
                        searchPoi.setMarkerBGRes(R.drawable.child_more_bg);
                        return;
                }
            }
            searchPoi.setIconId(R.drawable.child_subway_enterance);
            searchPoi.setMarkerBGRes(R.drawable.child_subway_enterance_bg);
            return;
        }
        searchPoi.setIconId(R.drawable.child_door);
        searchPoi.setMarkerBGRes(R.drawable.child_door_bg);
    }

    public List<auh> getCharge() {
        return this.mCharges;
    }

    public void setCharge(List<auh> list) {
        this.mCharges = list;
    }

    public auw getChildConfig() {
        return this.mChildConfig;
    }

    public void setChildConfig(auw auw) {
        this.mChildConfig = auw;
    }
}
