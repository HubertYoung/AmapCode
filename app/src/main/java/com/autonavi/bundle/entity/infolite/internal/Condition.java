package com.autonavi.bundle.entity.infolite.internal;

import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.searchservice.utils.SearchUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class Condition implements Serializable, Cloneable {
    private static final String BUSINESS_AREA = "business_area";
    private static final String BUSINESS_AREA_FLAG = "business_area_flag";
    private static final String SUBWAY_AREA = "subway_area";
    private static final String SUBWAY_AREA_FILE_SUFFIX = ".json";
    private static final String SUBWAY_AREA_FLAG = "subway_station_flag";
    private static final String TYPE_RANGE = "range";
    private static final Map<String, Condition> mLocalConditionMap = new HashMap();
    private static final long serialVersionUID = 5103701436312008035L;
    private int areaSelectIndex = 0;
    public String checkedValue;
    public ArrayList<Condition> conditionsData;
    public String dValue;
    public String displayName;
    private ArrayList<String> displayNames;
    private boolean hasCheckMaxSubTotal = false;
    private boolean hasDistrictAndSubway = false;
    public boolean isCategoryBrandFilter;
    public boolean isLevelPriceFilter;
    private int mDisplayType = -1;
    private String mDistrictAdcode = "";
    private int mDistrictEnable = 0;
    private int mNearbyEnable = 1;
    private String mSubwayAdcode = "";
    private int mSubwayEnable = 0;
    private int maxSubTotal = 0;
    public String name;
    private StringBuilder nameBuilder;
    private int rangeIndex = -1;
    public ArrayList<Condition> subConditions = new ArrayList<>();
    private int tempIndex;
    public String type;
    public String value;

    public Condition clone() {
        try {
            Condition condition = (Condition) super.clone();
            if (this.subConditions != null) {
                condition.subConditions = new ArrayList<>();
                Iterator<Condition> it = this.subConditions.iterator();
                while (it.hasNext()) {
                    condition.subConditions.add(it.next().clone());
                }
            }
            if (this.displayNames != null) {
                condition.displayNames = new ArrayList<>(this.displayNames);
            }
            if (this.conditionsData != null) {
                condition.conditionsData = new ArrayList<>();
                Iterator<Condition> it2 = this.conditionsData.iterator();
                while (it2.hasNext()) {
                    condition.conditionsData.add(it2.next().clone());
                }
            }
            return condition;
        } catch (CloneNotSupportedException unused) {
            return new Condition();
        }
    }

    public int getMaxSubTotal() {
        if (this.hasCheckMaxSubTotal) {
            return this.maxSubTotal;
        }
        int i = 0;
        if (this.subConditions == null || this.subConditions.size() <= 0) {
            return 0;
        }
        int size = this.subConditions.get(0).subConditions != null ? this.subConditions.get(0).subConditions.size() : 0;
        while (true) {
            i++;
            if (i >= this.subConditions.size()) {
                this.hasCheckMaxSubTotal = true;
                this.maxSubTotal = size;
                return this.maxSubTotal;
            } else if (this.subConditions.get(i).subConditions != null) {
                size = Math.max(size, this.subConditions.get(i).subConditions.size());
            }
        }
    }

    private static void parseCondition(JSONObject jSONObject, Condition condition) {
        try {
            if (condition.subConditions == null) {
                condition.subConditions = new ArrayList<>();
            }
            if (jSONObject.has("name")) {
                condition.name = jSONObject.getString("name");
            }
            if (jSONObject.has("value")) {
                condition.value = jSONObject.getString("value");
            }
            if (jSONObject.has("default")) {
                condition.dValue = jSONObject.getString("default");
            }
            if (jSONObject.has("checkedvalue")) {
                condition.checkedValue = jSONObject.getString("checkedvalue");
            }
            if (jSONObject.has("ctype")) {
                condition.type = jSONObject.getString("ctype");
            }
            if (jSONObject.has("display")) {
                condition.mDisplayType = jSONObject.optInt("display");
            }
            if (jSONObject.has("category")) {
                JSONArray jSONArray = jSONObject.getJSONArray("category");
                condition.subConditions = new ArrayList<>(jSONArray.length());
                for (int i = 0; i < jSONArray.length(); i++) {
                    Condition condition2 = new Condition();
                    parseCondition(jSONArray.getJSONObject(i), condition2);
                    condition.subConditions.add(condition2);
                }
            }
            if (condition.type == null || !condition.type.equals("category") || condition.name == null || !condition.name.contains("星级价格")) {
                condition.isLevelPriceFilter = false;
            } else {
                condition.isLevelPriceFilter = true;
            }
            if (condition.type == null || !condition.type.equals("sub_category") || condition.name == null || !condition.name.contains("更多筛选")) {
                condition.isCategoryBrandFilter = false;
            } else {
                condition.isCategoryBrandFilter = true;
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    private void findConditionName(Condition condition, int i, String str, int i2) {
        ArrayList<Condition> arrayList = condition.subConditions;
        if (arrayList != null && arrayList.size() > 0) {
            int i3 = 0;
            String str2 = "";
            Iterator<Condition> it = arrayList.iterator();
            while (it.hasNext()) {
                Condition next = it.next();
                if (!TextUtils.isEmpty(next.value)) {
                    if (next.value.equals(str)) {
                        Condition condition2 = new Condition();
                        if ("全部".equals(next.name) || "全部分类".equals(next.name)) {
                            condition2.displayName = condition.name;
                        } else {
                            condition2.displayName = next.name;
                        }
                        this.displayNames.set(i, condition2.displayName);
                        return;
                    } else if (i2 >= 0 && str.contains(next.value)) {
                        if (i2 == 0) {
                            if (next.name == null) {
                                continue;
                            } else if (!next.name.equals("不限")) {
                                this.nameBuilder.append(next.name);
                                this.nameBuilder.append(",");
                                this.tempIndex = i;
                            } else {
                                return;
                            }
                        } else if (i2 == 1 && next.name != null) {
                            if (i3 == 1) {
                                if (!next.name.equals("不限")) {
                                    this.nameBuilder.append(str2);
                                    this.nameBuilder.append("-");
                                } else if (!str2.equals("￥0")) {
                                    this.nameBuilder.append(str2);
                                    this.nameBuilder.append("以上");
                                    this.nameBuilder.append(",");
                                    this.tempIndex = i;
                                    return;
                                } else {
                                    return;
                                }
                            }
                            str2 = next.name;
                            if (i3 == 1) {
                                this.nameBuilder.append(str2);
                                this.nameBuilder.append(",");
                                this.tempIndex = i;
                            }
                            i3++;
                        }
                    }
                } else if (next.subConditions != null && next.subConditions.size() > 0) {
                    findConditionName(next, i, str, next.mDisplayType);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:0x0294 A[Catch:{ Exception -> 0x02af }, LOOP:3: B:101:0x028c->B:103:0x0294, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0210 A[Catch:{ Exception -> 0x02af }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseConditions(org.json.JSONObject r10) {
        /*
            r9 = this;
            java.lang.String r0 = "classify"
            boolean r0 = r10.has(r0)     // Catch:{ Exception -> 0x02af }
            if (r0 == 0) goto L_0x02ae
            java.lang.String r0 = "classify"
            org.json.JSONArray r10 = r10.getJSONArray(r0)     // Catch:{ Exception -> 0x02af }
            int r0 = r10.length()     // Catch:{ Exception -> 0x02af }
            if (r0 > 0) goto L_0x0015
            return
        L_0x0015:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x02af }
            int r1 = r10.length()     // Catch:{ Exception -> 0x02af }
            r0.<init>(r1)     // Catch:{ Exception -> 0x02af }
            r9.conditionsData = r0     // Catch:{ Exception -> 0x02af }
            r0 = 0
            r1 = 0
        L_0x0022:
            int r2 = r10.length()     // Catch:{ Exception -> 0x02af }
            if (r1 >= r2) goto L_0x003c
            com.autonavi.bundle.entity.infolite.internal.Condition r2 = new com.autonavi.bundle.entity.infolite.internal.Condition     // Catch:{ Exception -> 0x02af }
            r2.<init>()     // Catch:{ Exception -> 0x02af }
            org.json.JSONObject r3 = r10.getJSONObject(r1)     // Catch:{ Exception -> 0x02af }
            parseCondition(r3, r2)     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r3 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            r3.add(r2)     // Catch:{ Exception -> 0x02af }
            int r1 = r1 + 1
            goto L_0x0022
        L_0x003c:
            r10 = -1
            r9.rangeIndex = r10     // Catch:{ Exception -> 0x02af }
            r1 = 0
        L_0x0040:
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r2 = r2.size()     // Catch:{ Exception -> 0x02af }
            if (r1 >= r2) goto L_0x0060
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r2 = (com.autonavi.bundle.entity.infolite.internal.Condition) r2     // Catch:{ Exception -> 0x02af }
            java.lang.String r2 = r2.type     // Catch:{ Exception -> 0x02af }
            java.lang.String r3 = "range"
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x02af }
            if (r2 == 0) goto L_0x005d
            r9.rangeIndex = r1     // Catch:{ Exception -> 0x02af }
            goto L_0x0060
        L_0x005d:
            int r1 = r1 + 1
            goto L_0x0040
        L_0x0060:
            int r1 = r9.mNearbyEnable     // Catch:{ Exception -> 0x02af }
            if (r1 != 0) goto L_0x00a1
            int r1 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            if (r1 == r10) goto L_0x00a1
            com.autonavi.bundle.entity.infolite.internal.Condition r1 = new com.autonavi.bundle.entity.infolite.internal.Condition     // Catch:{ Exception -> 0x02af }
            r1.<init>()     // Catch:{ Exception -> 0x02af }
            java.lang.String r2 = "全城"
            r1.name = r2     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r1.subConditions     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r3 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r4 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r3 = r3.get(r4)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r3 = (com.autonavi.bundle.entity.infolite.internal.Condition) r3     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r3 = r3.subConditions     // Catch:{ Exception -> 0x02af }
            r2.addAll(r3)     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r3 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r2 = (com.autonavi.bundle.entity.infolite.internal.Condition) r2     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r2.subConditions     // Catch:{ Exception -> 0x02af }
            r2.clear()     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r3 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r2 = (com.autonavi.bundle.entity.infolite.internal.Condition) r2     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r2.subConditions     // Catch:{ Exception -> 0x02af }
            r2.add(r1)     // Catch:{ Exception -> 0x02af }
        L_0x00a1:
            int r1 = r9.mDistrictEnable     // Catch:{ Exception -> 0x02af }
            r2 = 0
            r3 = 1
            if (r1 == r3) goto L_0x00d1
            int r1 = r9.mSubwayEnable     // Catch:{ Exception -> 0x02af }
            if (r1 != r3) goto L_0x00ac
            goto L_0x00d1
        L_0x00ac:
            int r1 = r9.mNearbyEnable     // Catch:{ Exception -> 0x02af }
            if (r1 != 0) goto L_0x01fa
            int r1 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            if (r1 == r10) goto L_0x01fa
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r1 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r4 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r1 = r1.get(r4)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r1 = (com.autonavi.bundle.entity.infolite.internal.Condition) r1     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r1 = r1.subConditions     // Catch:{ Exception -> 0x02af }
            r1.clear()     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r1 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r4 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r1 = r1.get(r4)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r1 = (com.autonavi.bundle.entity.infolite.internal.Condition) r1     // Catch:{ Exception -> 0x02af }
            r1.subConditions = r2     // Catch:{ Exception -> 0x02af }
            goto L_0x01fa
        L_0x00d1:
            int r1 = r9.mSubwayEnable     // Catch:{ Exception -> 0x02af }
            if (r1 != r3) goto L_0x010d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02af }
            java.lang.String r4 = "subway_area"
            r1.<init>(r4)     // Catch:{ Exception -> 0x02af }
            java.lang.String r4 = java.io.File.separator     // Catch:{ Exception -> 0x02af }
            r1.append(r4)     // Catch:{ Exception -> 0x02af }
            java.lang.String r4 = r9.mSubwayAdcode     // Catch:{ Exception -> 0x02af }
            r1.append(r4)     // Catch:{ Exception -> 0x02af }
            java.lang.String r4 = ".json"
            r1.append(r4)     // Catch:{ Exception -> 0x02af }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r1 = r9.parseLocalCondition(r1)     // Catch:{ Exception -> 0x02af }
            if (r1 != 0) goto L_0x00f9
            r9.mSubwayEnable = r0     // Catch:{ Exception -> 0x02af }
            goto L_0x010e
        L_0x00f9:
            java.lang.String r4 = "地铁"
            r1.name = r4     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r4 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r5 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r4 = r4.get(r5)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r4 = (com.autonavi.bundle.entity.infolite.internal.Condition) r4     // Catch:{ Exception -> 0x02af }
            java.lang.String r4 = r4.checkedValue     // Catch:{ Exception -> 0x02af }
            r1.checkedValue = r4     // Catch:{ Exception -> 0x02af }
            goto L_0x010e
        L_0x010d:
            r1 = r2
        L_0x010e:
            int r4 = r9.mDistrictEnable     // Catch:{ Exception -> 0x02af }
            if (r4 != r3) goto L_0x0144
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02af }
            java.lang.String r5 = "business_area"
            r4.<init>(r5)     // Catch:{ Exception -> 0x02af }
            java.lang.String r5 = java.io.File.separator     // Catch:{ Exception -> 0x02af }
            r4.append(r5)     // Catch:{ Exception -> 0x02af }
            java.lang.String r5 = r9.mDistrictAdcode     // Catch:{ Exception -> 0x02af }
            r4.append(r5)     // Catch:{ Exception -> 0x02af }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r4 = r9.parseLocalCondition(r4)     // Catch:{ Exception -> 0x02af }
            if (r4 != 0) goto L_0x0130
            r9.mDistrictEnable = r0     // Catch:{ Exception -> 0x02af }
            goto L_0x0145
        L_0x0130:
            java.lang.String r5 = "商圈"
            r4.name = r5     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r5 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r6 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r5 = r5.get(r6)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = (com.autonavi.bundle.entity.infolite.internal.Condition) r5     // Catch:{ Exception -> 0x02af }
            java.lang.String r5 = r5.checkedValue     // Catch:{ Exception -> 0x02af }
            r4.checkedValue = r5     // Catch:{ Exception -> 0x02af }
            goto L_0x0145
        L_0x0144:
            r4 = r2
        L_0x0145:
            int r5 = r9.mNearbyEnable     // Catch:{ Exception -> 0x02af }
            if (r5 != r3) goto L_0x017c
            int r5 = r9.mSubwayEnable     // Catch:{ Exception -> 0x02af }
            if (r5 == r3) goto L_0x0151
            int r5 = r9.mDistrictEnable     // Catch:{ Exception -> 0x02af }
            if (r5 != r3) goto L_0x017c
        L_0x0151:
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = new com.autonavi.bundle.entity.infolite.internal.Condition     // Catch:{ Exception -> 0x02af }
            r5.<init>()     // Catch:{ Exception -> 0x02af }
            java.lang.String r6 = "附近"
            r5.name = r6     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r6 = r5.subConditions     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r7 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r8 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r7 = r7.get(r8)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r7 = (com.autonavi.bundle.entity.infolite.internal.Condition) r7     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r7 = r7.subConditions     // Catch:{ Exception -> 0x02af }
            r6.addAll(r7)     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r6 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r7 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r6 = r6.get(r7)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r6 = (com.autonavi.bundle.entity.infolite.internal.Condition) r6     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r6 = r6.subConditions     // Catch:{ Exception -> 0x02af }
            r6.clear()     // Catch:{ Exception -> 0x02af }
            goto L_0x017d
        L_0x017c:
            r5 = r2
        L_0x017d:
            int r6 = r9.mSubwayEnable     // Catch:{ Exception -> 0x02af }
            if (r6 != r3) goto L_0x01bb
            int r6 = r9.mDistrictEnable     // Catch:{ Exception -> 0x02af }
            if (r6 != r3) goto L_0x01bb
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r6 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r7 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r6 = r6.get(r7)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r6 = (com.autonavi.bundle.entity.infolite.internal.Condition) r6     // Catch:{ Exception -> 0x02af }
            int r7 = r9.mNearbyEnable     // Catch:{ Exception -> 0x02af }
            if (r7 != r3) goto L_0x0198
            com.autonavi.bundle.entity.infolite.internal.Condition r2 = r9.insertConditionToHead(r4, r5, r2)     // Catch:{ Exception -> 0x02af }
            goto L_0x01a8
        L_0x0198:
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r5 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r7 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r5 = r5.get(r7)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = (com.autonavi.bundle.entity.infolite.internal.Condition) r5     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r5 = r5.subConditions     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r2 = r9.insertConditionToHead(r4, r2, r5)     // Catch:{ Exception -> 0x02af }
        L_0x01a8:
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r4 = r6.subConditions     // Catch:{ Exception -> 0x02af }
            r4.clear()     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r4 = r6.subConditions     // Catch:{ Exception -> 0x02af }
            r4.add(r2)     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r6.subConditions     // Catch:{ Exception -> 0x02af }
            r2.add(r1)     // Catch:{ Exception -> 0x02af }
            r6.setHasDistrictAndSubway(r3)     // Catch:{ Exception -> 0x02af }
            goto L_0x01fa
        L_0x01bb:
            int r6 = r9.mSubwayEnable     // Catch:{ Exception -> 0x02af }
            if (r6 != r3) goto L_0x01db
            int r4 = r9.mNearbyEnable     // Catch:{ Exception -> 0x02af }
            if (r4 != r3) goto L_0x01c7
            com.autonavi.bundle.entity.infolite.internal.Condition r1 = r9.insertConditionToHead(r1, r5, r2)     // Catch:{ Exception -> 0x02af }
        L_0x01c7:
            if (r1 == 0) goto L_0x01fa
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r4 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r2 = r2.get(r4)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r2 = (com.autonavi.bundle.entity.infolite.internal.Condition) r2     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r2.subConditions     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r1 = r1.subConditions     // Catch:{ Exception -> 0x02af }
            r2.addAll(r1)     // Catch:{ Exception -> 0x02af }
            goto L_0x01fa
        L_0x01db:
            int r1 = r9.mDistrictEnable     // Catch:{ Exception -> 0x02af }
            if (r1 != r3) goto L_0x01fa
            int r1 = r9.mNearbyEnable     // Catch:{ Exception -> 0x02af }
            if (r1 != r3) goto L_0x01e7
            com.autonavi.bundle.entity.infolite.internal.Condition r4 = r9.insertConditionToHead(r4, r5, r2)     // Catch:{ Exception -> 0x02af }
        L_0x01e7:
            if (r4 == 0) goto L_0x01fa
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r1 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r2 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r1 = (com.autonavi.bundle.entity.infolite.internal.Condition) r1     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r1 = r1.subConditions     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r4.subConditions     // Catch:{ Exception -> 0x02af }
            r1.addAll(r2)     // Catch:{ Exception -> 0x02af }
        L_0x01fa:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r2 = r2.size()     // Catch:{ Exception -> 0x02af }
            r1.<init>(r2)     // Catch:{ Exception -> 0x02af }
            r9.displayNames = r1     // Catch:{ Exception -> 0x02af }
            r1 = 0
        L_0x0208:
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            int r2 = r2.size()     // Catch:{ Exception -> 0x02af }
            if (r1 >= r2) goto L_0x028c
            java.util.ArrayList<java.lang.String> r2 = r9.displayNames     // Catch:{ Exception -> 0x02af }
            java.lang.String r4 = ""
            r2.add(r1, r4)     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r2 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r2 = (com.autonavi.bundle.entity.infolite.internal.Condition) r2     // Catch:{ Exception -> 0x02af }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02af }
            r4.<init>()     // Catch:{ Exception -> 0x02af }
            r9.nameBuilder = r4     // Catch:{ Exception -> 0x02af }
            r9.tempIndex = r10     // Catch:{ Exception -> 0x02af }
            java.lang.String r4 = r2.checkedValue     // Catch:{ Exception -> 0x02af }
            if (r4 == 0) goto L_0x023e
            java.lang.String r4 = r2.checkedValue     // Catch:{ Exception -> 0x02af }
            java.lang.String r5 = ""
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x02af }
            if (r4 != 0) goto L_0x023e
            java.lang.String r4 = r2.checkedValue     // Catch:{ Exception -> 0x02af }
            int r5 = r2.mDisplayType     // Catch:{ Exception -> 0x02af }
            r9.findConditionName(r2, r1, r4, r5)     // Catch:{ Exception -> 0x02af }
            goto L_0x0245
        L_0x023e:
            java.util.ArrayList<java.lang.String> r4 = r9.displayNames     // Catch:{ Exception -> 0x02af }
            java.lang.String r5 = r2.name     // Catch:{ Exception -> 0x02af }
            r4.set(r1, r5)     // Catch:{ Exception -> 0x02af }
        L_0x0245:
            int r4 = r9.tempIndex     // Catch:{ Exception -> 0x02af }
            if (r4 < 0) goto L_0x026a
            java.lang.StringBuilder r4 = r9.nameBuilder     // Catch:{ Exception -> 0x02af }
            int r4 = r4.length()     // Catch:{ Exception -> 0x02af }
            if (r4 == 0) goto L_0x026a
            java.lang.StringBuilder r4 = r9.nameBuilder     // Catch:{ Exception -> 0x02af }
            java.lang.StringBuilder r5 = r9.nameBuilder     // Catch:{ Exception -> 0x02af }
            int r5 = r5.length()     // Catch:{ Exception -> 0x02af }
            int r5 = r5 - r3
            r4.deleteCharAt(r5)     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<java.lang.String> r4 = r9.displayNames     // Catch:{ Exception -> 0x02af }
            int r5 = r9.tempIndex     // Catch:{ Exception -> 0x02af }
            java.lang.StringBuilder r6 = r9.nameBuilder     // Catch:{ Exception -> 0x02af }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x02af }
            r4.set(r5, r6)     // Catch:{ Exception -> 0x02af }
        L_0x026a:
            int r4 = r9.rangeIndex     // Catch:{ Exception -> 0x02af }
            if (r1 != r4) goto L_0x0288
            java.lang.String r4 = r2.checkedValue     // Catch:{ Exception -> 0x02af }
            java.lang.String r5 = "subway_station_flag"
            boolean r4 = r4.contains(r5)     // Catch:{ Exception -> 0x02af }
            if (r4 == 0) goto L_0x027c
            r9.areaSelectIndex = r3     // Catch:{ Exception -> 0x02af }
            goto L_0x0288
        L_0x027c:
            java.lang.String r2 = r2.checkedValue     // Catch:{ Exception -> 0x02af }
            java.lang.String r4 = "business_area_flag"
            boolean r2 = r2.contains(r4)     // Catch:{ Exception -> 0x02af }
            if (r2 == 0) goto L_0x0288
            r9.areaSelectIndex = r0     // Catch:{ Exception -> 0x02af }
        L_0x0288:
            int r1 = r1 + 1
            goto L_0x0208
        L_0x028c:
            java.util.ArrayList<java.lang.String> r10 = r9.displayNames     // Catch:{ Exception -> 0x02af }
            int r10 = r10.size()     // Catch:{ Exception -> 0x02af }
            if (r0 >= r10) goto L_0x02a9
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.internal.Condition> r10 = r9.conditionsData     // Catch:{ Exception -> 0x02af }
            java.lang.Object r10 = r10.get(r0)     // Catch:{ Exception -> 0x02af }
            com.autonavi.bundle.entity.infolite.internal.Condition r10 = (com.autonavi.bundle.entity.infolite.internal.Condition) r10     // Catch:{ Exception -> 0x02af }
            java.util.ArrayList<java.lang.String> r1 = r9.displayNames     // Catch:{ Exception -> 0x02af }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ Exception -> 0x02af }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x02af }
            r10.displayName = r1     // Catch:{ Exception -> 0x02af }
            int r0 = r0 + 1
            goto L_0x028c
        L_0x02a9:
            java.util.ArrayList<java.lang.String> r10 = r9.displayNames     // Catch:{ Exception -> 0x02af }
            r10.clear()     // Catch:{ Exception -> 0x02af }
        L_0x02ae:
            return
        L_0x02af:
            r10 = move-exception
            defpackage.kf.a(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.entity.infolite.internal.Condition.parseConditions(org.json.JSONObject):void");
    }

    private static Condition getLocalCondition(String str) {
        if (mLocalConditionMap.containsKey(str)) {
            return mLocalConditionMap.get(str);
        }
        byte[] decodeAssetResData = SearchUtils.decodeAssetResData(AMapAppGlobal.getApplication(), str);
        if (decodeAssetResData == null) {
            mLocalConditionMap.put(str, new Condition());
        } else {
            try {
                JSONArray jSONArray = new JSONArray(new String(decodeAssetResData, "UTF-8"));
                Condition condition = new Condition();
                for (int i = 0; i < jSONArray.length(); i++) {
                    Condition condition2 = new Condition();
                    parseCondition(jSONArray.getJSONObject(i), condition2);
                    condition.subConditions.add(condition2);
                }
                mLocalConditionMap.put(str, condition);
            } catch (Exception unused) {
                mLocalConditionMap.put(str, new Condition());
            }
        }
        return mLocalConditionMap.get(str);
    }

    public Condition parseLocalCondition(String str) {
        return getLocalCondition(str).clone();
    }

    public Condition insertConditionToHead(Condition condition, Condition condition2, List<Condition> list) {
        if (condition2 == null && list == null) {
            if (condition == null) {
                condition = new Condition();
                condition.name = "";
            }
            return condition;
        }
        ArrayList arrayList = new ArrayList();
        if (condition2 != null) {
            arrayList.add(condition2);
        }
        if (list != null && list.size() > 0) {
            arrayList.addAll(list);
        }
        Iterator<Condition> it = condition.subConditions.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        condition.subConditions.clear();
        condition.subConditions.addAll(arrayList);
        return condition;
    }

    public void setNearbyEnable(int i) {
        this.mNearbyEnable = i;
    }

    public void setDistrictAdcode(String str) {
        this.mDistrictAdcode = str;
    }

    public void setDistrictEnable(int i) {
        this.mDistrictEnable = i;
    }

    public String getSubwayAdcode() {
        return this.mSubwayAdcode;
    }

    public void setSubwayAdcode(String str) {
        this.mSubwayAdcode = str;
    }

    public int getSubwayEnable() {
        return this.mSubwayEnable;
    }

    public void setSubwayEnable(int i) {
        this.mSubwayEnable = i;
    }

    public String getDistrictAdcode() {
        return this.mDistrictAdcode;
    }

    public int getDistrictEnable() {
        return this.mDistrictEnable;
    }

    public int getRangeIndex() {
        return this.rangeIndex;
    }

    public int getAreaSelectIndex() {
        return this.areaSelectIndex;
    }

    public boolean isHasDistrictAndSubway() {
        return this.hasDistrictAndSubway;
    }

    public void setHasDistrictAndSubway(boolean z) {
        this.hasDistrictAndSubway = z;
    }

    public void clear() {
        if (this.subConditions != null) {
            this.subConditions.clear();
            this.subConditions = null;
        }
        if (this.conditionsData != null) {
            this.conditionsData.clear();
            this.conditionsData = null;
        }
        this.rangeIndex = -1;
        this.hasDistrictAndSubway = false;
        this.areaSelectIndex = 0;
        this.mDistrictEnable = 0;
        this.mDistrictAdcode = "";
        this.mSubwayAdcode = "";
        this.mSubwayEnable = 0;
        this.mNearbyEnable = 0;
    }
}
