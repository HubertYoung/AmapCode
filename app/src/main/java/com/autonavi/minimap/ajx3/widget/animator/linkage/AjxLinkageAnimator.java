package com.autonavi.minimap.ajx3.widget.animator.linkage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.AjxDomTree;
import com.autonavi.minimap.ajx3.dom.KeyDefine;
import com.autonavi.minimap.ajx3.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class AjxLinkageAnimator {
    private static final String DIRECTION_BACKWARDS = "backwards";
    private static final String DIRECTION_FORWARDS = "forwards";
    private static final String DIRECTION_INSIDE = "inside";
    private static final String DIRECTION_NONE = "";
    private static final String DIRECTION_OUTSIDE = "outside";
    private static final String TYPE_END = "end";
    private static final String TYPE_START = "start";
    private IAjxContext mAjxContext;
    private AjxDomTree mAjxDomTree;
    private long mAnimatorId;
    private final Map<String, LongSparseArray<List<ViewProperty>>> mAutoDisRelationsMap = new HashMap();
    private final Map<String, ViewProperty> mPropertyMap = new HashMap();
    private final Map<String, LongSparseArray<List<ViewProperty>>> mRelationsMap = new HashMap();
    private final long mSourceViewNodeId;

    private int getA(int i) {
        return (i >> 24) & 255;
    }

    private int getB(int i) {
        return i & 255;
    }

    private int getG(int i) {
        return (i >> 8) & 255;
    }

    private int getR(int i) {
        return (i >> 16) & 255;
    }

    AjxLinkageAnimator(long j, @Nullable String str, long j2, @NonNull IAjxContext iAjxContext) {
        this.mAnimatorId = j2;
        this.mSourceViewNodeId = j;
        this.mAjxContext = iAjxContext;
        this.mAjxDomTree = iAjxContext.getDomTree();
        initPropertyData(str);
    }

    private void initPropertyData(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    ViewProperty createViewProperty = createViewProperty(next, jSONObject.optString(next));
                    if (createViewProperty != null) {
                        this.mPropertyMap.put(createViewProperty.getProperty(), createViewProperty);
                        initPropertyPercent(createViewProperty);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initPropertyPercent(@NonNull ViewProperty viewProperty) {
        AjxDomNode sourceNode = getSourceNode();
        if (sourceNode != null) {
            Float curSourceValue = getCurSourceValue(null, sourceNode, viewProperty);
            if (curSourceValue != null) {
                dealLastPercent(curSourceValue.floatValue(), viewProperty);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void addObserver(long j, @Nullable String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (z) {
                    addObserverValues(jSONObject, j, this.mAutoDisRelationsMap);
                } else {
                    addObserverValues(jSONObject, j, this.mRelationsMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addObserverValues(JSONObject jSONObject, long j, Map<String, LongSparseArray<List<ViewProperty>>> map) {
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            String optString = jSONObject.optString(next);
            if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(optString) && this.mPropertyMap.containsKey(next)) {
                try {
                    JSONObject jSONObject2 = new JSONObject(optString);
                    LongSparseArray longSparseArray = map.get(next);
                    ArrayList arrayList = new ArrayList();
                    Iterator<String> keys2 = jSONObject2.keys();
                    while (keys2.hasNext()) {
                        String next2 = keys2.next();
                        if (!"__native_value__".equals(next2)) {
                            ViewProperty createViewProperty = createViewProperty(next2, jSONObject2.optString(next2));
                            if (createViewProperty != null) {
                                arrayList.add(createViewProperty);
                            }
                        }
                    }
                    if (arrayList.size() > 0) {
                        if (longSparseArray == null) {
                            longSparseArray = new LongSparseArray();
                            map.put(next, longSparseArray);
                        }
                        longSparseArray.put(j, arrayList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clear() {
        this.mPropertyMap.clear();
        this.mRelationsMap.clear();
        this.mAutoDisRelationsMap.clear();
    }

    /* access modifiers changed from: 0000 */
    public void clearDisposableObserver() {
        this.mAutoDisRelationsMap.clear();
    }

    public void destroy() {
        clear();
    }

    @Nullable
    private AjxDomNode getSourceNode() {
        return this.mAjxDomTree.findNodeById(this.mSourceViewNodeId);
    }

    public void remove(long j) {
        for (Entry<String, LongSparseArray<List<ViewProperty>>> value : this.mRelationsMap.entrySet()) {
            LongSparseArray longSparseArray = (LongSparseArray) value.getValue();
            if (longSparseArray != null) {
                longSparseArray.remove(j);
            }
        }
        for (Entry<String, LongSparseArray<List<ViewProperty>>> value2 : this.mAutoDisRelationsMap.entrySet()) {
            LongSparseArray longSparseArray2 = (LongSparseArray) value2.getValue();
            if (longSparseArray2 != null) {
                longSparseArray2.remove(j);
            }
        }
    }

    @Nullable
    private ViewProperty createViewProperty(@Nullable String str, @Nullable String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(str2);
            if (jSONArray.length() < 2) {
                return null;
            }
            boolean z = false;
            Object opt = jSONArray.opt(0);
            Object opt2 = jSONArray.opt(1);
            boolean z2 = (opt instanceof Integer) || (opt instanceof Double);
            if ((opt2 instanceof Integer) || (opt2 instanceof Double)) {
                z = true;
            }
            if (z2 && z) {
                ViewProperty viewProperty = new ViewProperty(str, Float.parseFloat(String.valueOf(opt)), Float.parseFloat(String.valueOf(opt2)), 1, getPropertyTypeAccordingName(str));
                return viewProperty;
            } else if (!(opt instanceof String) || !(opt2 instanceof String)) {
                return null;
            } else {
                int parseColorByAnimator = StringUtils.parseColorByAnimator((String) opt);
                int parseColorByAnimator2 = StringUtils.parseColorByAnimator((String) opt2);
                if (parseColorByAnimator == -2 || parseColorByAnimator2 == -2) {
                    return null;
                }
                ViewProperty viewProperty2 = new ViewProperty(str, (float) parseColorByAnimator, (float) parseColorByAnimator2, 2, getPropertyTypeAccordingName(str));
                return viewProperty2;
            }
        } catch (JSONException unused) {
            if (FormulaHelper.checkExpression(str2)) {
                return new ViewProperty(str, 3, getPropertyTypeAccordingName(str), str2);
            }
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void propertyChange() {
        for (String propertyChange : this.mRelationsMap.keySet()) {
            propertyChange(propertyChange, null);
        }
        for (String propertyChange2 : this.mAutoDisRelationsMap.keySet()) {
            propertyChange(propertyChange2, null);
        }
    }

    /* access modifiers changed from: 0000 */
    public void propertyChange(String str, Object obj) {
        if (this.mPropertyMap.containsKey(str)) {
            AjxDomNode sourceNode = getSourceNode();
            if (sourceNode != null) {
                ViewProperty viewProperty = this.mPropertyMap.get(str);
                Float curSourceValue = getCurSourceValue(obj, sourceNode, viewProperty);
                if (curSourceValue != null) {
                    float percentAccordingValue = getPercentAccordingValue(curSourceValue.floatValue(), viewProperty);
                    if (viewProperty.getLastPercent() == null || viewProperty.getLastPercent().floatValue() != percentAccordingValue || percentAccordingValue == 1.0f || "translateScrollY".equals(str)) {
                        dealLastPercent(curSourceValue.floatValue(), viewProperty);
                        boolean triggerBoundaryCallback = triggerBoundaryCallback(viewProperty, percentAccordingValue, curSourceValue.floatValue());
                        viewProperty.setLastPercent(percentAccordingValue);
                        viewProperty.setLastValue(curSourceValue.floatValue());
                        if (triggerBoundaryCallback) {
                            if (percentAccordingValue < 0.0f) {
                                percentAccordingValue = 0.0f;
                            } else if (percentAccordingValue > 1.0f) {
                                percentAccordingValue = 1.0f;
                            }
                        }
                        if (percentAccordingValue <= 1.0f && percentAccordingValue >= 0.0f) {
                            applyObserverValueByRelationsMap(percentAccordingValue, curSourceValue.floatValue(), this.mRelationsMap.get(str));
                            applyObserverValueByRelationsMap(percentAccordingValue, curSourceValue.floatValue(), this.mAutoDisRelationsMap.get(str));
                        }
                    }
                }
            }
        }
    }

    @Nullable
    private Float getCurSourceValue(Object obj, AjxDomNode ajxDomNode, ViewProperty viewProperty) {
        if (obj == null) {
            obj = getProperty(ajxDomNode, viewProperty);
        }
        if (obj instanceof Integer) {
            return Float.valueOf((float) ((Integer) obj).intValue());
        }
        if (obj instanceof Float) {
            return (Float) obj;
        }
        return null;
    }

    private boolean triggerBoundaryCallback(ViewProperty viewProperty, float f, float f2) {
        Float lastPercent = viewProperty.getLastPercent();
        String property = viewProperty.getProperty();
        boolean z = false;
        if (viewProperty.getFrom() == viewProperty.getTo()) {
            invokeRelativeAnimation(property, TYPE_END, viewProperty, f, f2);
            return false;
        } else if (lastPercent == null) {
            return false;
        } else {
            if (f > lastPercent.floatValue()) {
                if (lastPercent.floatValue() <= 0.0f && f >= 0.0f) {
                    invokeRelativeAnimation(property, "start", viewProperty, f, f2);
                    z = true;
                }
                if (lastPercent.floatValue() > 1.0f || f < 1.0f) {
                    return z;
                }
                invokeRelativeAnimation(property, TYPE_END, viewProperty, f, f2);
            } else if (f >= lastPercent.floatValue()) {
                return false;
            } else {
                if (lastPercent.floatValue() >= 1.0f && f <= 1.0f) {
                    invokeRelativeAnimation(property, TYPE_END, viewProperty, f, f2);
                    z = true;
                }
                if (lastPercent.floatValue() < 0.0f || f > 0.0f) {
                    return z;
                }
                invokeRelativeAnimation(property, "start", viewProperty, f, f2);
            }
            return true;
        }
    }

    private void applyObserverValueByRelationsMap(float f, float f2, @Nullable LongSparseArray<List<ViewProperty>> longSparseArray) {
        if (longSparseArray != null) {
            for (int i = 0; i < longSparseArray.size(); i++) {
                long keyAt = longSparseArray.keyAt(i);
                for (ViewProperty viewProperty : (List) longSparseArray.get(keyAt)) {
                    float valueAccordingPercent = getValueAccordingPercent(f, viewProperty, f2);
                    if (valueAccordingPercent != Float.MAX_VALUE) {
                        AjxDomNode findNodeById = this.mAjxDomTree.findNodeById(keyAt);
                        if (findNodeById != null) {
                            setProperty(findNodeById, viewProperty, Float.valueOf(valueAccordingPercent));
                        }
                    }
                }
            }
        }
    }

    private void dealLastPercent(float f, @NonNull ViewProperty viewProperty) {
        if (viewProperty.getLastPercent() == null) {
            if ("scrollTop".equals(viewProperty.getProperty())) {
                viewProperty.setLastPercent(0.0f);
                return;
            }
            if (viewProperty.getFrom() != viewProperty.getTo()) {
                if (viewProperty.getType() == 1) {
                    try {
                        viewProperty.setLastPercent((f - viewProperty.getFrom()) / (viewProperty.getTo() - viewProperty.getFrom()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            } else if (viewProperty.getType() == 1) {
                try {
                    if (f <= viewProperty.getFrom()) {
                        viewProperty.setLastPercent(-1.0f);
                    } else {
                        viewProperty.setLastPercent(2.0f);
                    }
                    viewProperty.setLastValue(f);
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void savePropertyValue(String str, Object obj) {
        float f;
        if (this.mPropertyMap.containsKey(str)) {
            AjxDomNode sourceNode = getSourceNode();
            ViewProperty viewProperty = this.mPropertyMap.get(str);
            if (sourceNode != null && viewProperty != null) {
                if (obj == null) {
                    obj = viewProperty.getPropertyType() == 0 ? Float.valueOf(sourceNode.getSize(str)) : viewProperty.getPropertyType() == 1 ? sourceNode.getStyleValue(viewProperty.getStyleValue(), 0) : viewProperty.getPropertyType() == 2 ? sourceNode.getAttributeValue(str) : null;
                }
                if (obj instanceof Integer) {
                    f = (float) ((Integer) obj).intValue();
                } else if (obj instanceof Float) {
                    f = ((Float) obj).floatValue();
                } else {
                    return;
                }
                float percentAccordingValue = getPercentAccordingValue(f, viewProperty);
                viewProperty.setLastValue(f);
                viewProperty.setLastPercent(percentAccordingValue);
            }
        }
    }

    private void invokeRelativeAnimation(String str, String str2, ViewProperty viewProperty, float f, float f2) {
        String str3 = "";
        if (viewProperty.getFrom() != viewProperty.getTo()) {
            if (viewProperty.getLastPercent() == null || f - viewProperty.getLastPercent().floatValue() == 0.0f) {
                str3 = "";
            } else if (f - viewProperty.getLastPercent().floatValue() > 0.0f) {
                str3 = f <= 0.0f ? DIRECTION_INSIDE : viewProperty.getLastPercent().floatValue() >= 1.0f ? DIRECTION_OUTSIDE : DIRECTION_FORWARDS;
            } else if (f - viewProperty.getLastPercent().floatValue() < 0.0f) {
                str3 = viewProperty.getLastPercent().floatValue() <= 0.0f ? DIRECTION_OUTSIDE : f >= 1.0f ? DIRECTION_INSIDE : "backwards";
            }
        } else if (f2 - viewProperty.getLastValue() == 0.0f) {
            str3 = "";
        } else if (f2 - viewProperty.getLastValue() > 0.0f) {
            str3 = DIRECTION_FORWARDS;
        } else if (f2 - viewProperty.getLastValue() < 0.0f) {
            str3 = "backwards";
        }
        String str4 = str3;
        if ("backwards".equals(str4) || DIRECTION_FORWARDS.equals(str4)) {
            this.mAjxContext.getJsContext().invokeRelativeAnimation(this.mAnimatorId, str, str2, str4);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005d, code lost:
        if (r2.equals("left") != false) goto L_0x006c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void syncLinkageAnimator() {
        /*
            r12 = this;
            java.util.Map<java.lang.String, com.autonavi.minimap.ajx3.widget.animator.linkage.ViewProperty> r0 = r12.mPropertyMap
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0009
            return
        L_0x0009:
            java.util.HashMap r0 = new java.util.HashMap
            r1 = 16
            r0.<init>(r1)
            java.util.Map<java.lang.String, com.autonavi.minimap.ajx3.widget.animator.linkage.ViewProperty> r1 = r12.mPropertyMap
            java.util.Set r1 = r1.keySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x001a:
            boolean r2 = r1.hasNext()
            r3 = 2
            if (r2 == 0) goto L_0x00cb
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            com.autonavi.minimap.ajx3.dom.AjxDomNode r11 = r12.getSourceNode()
            if (r11 != 0) goto L_0x002e
            return
        L_0x002e:
            r4 = -1
            int r5 = r2.hashCode()
            switch(r5) {
                case 115029: goto L_0x0060;
                case 3317767: goto L_0x0057;
                case 66047092: goto L_0x004c;
                case 417780552: goto L_0x0041;
                case 1057926094: goto L_0x0037;
                default: goto L_0x0036;
            }
        L_0x0036:
            goto L_0x006b
        L_0x0037:
            java.lang.String r3 = "relativeScrollDistance"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x006b
            r3 = 4
            goto L_0x006c
        L_0x0041:
            java.lang.String r3 = "scrollTop"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x006b
            r3 = 0
            goto L_0x006c
        L_0x004c:
            java.lang.String r3 = "scrollLeft"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x006b
            r3 = 3
            goto L_0x006c
        L_0x0057:
            java.lang.String r5 = "left"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x006b
            goto L_0x006c
        L_0x0060:
            java.lang.String r3 = "top"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x006b
            r3 = 1
            goto L_0x006c
        L_0x006b:
            r3 = -1
        L_0x006c:
            switch(r3) {
                case 0: goto L_0x0098;
                case 1: goto L_0x0082;
                case 2: goto L_0x0082;
                case 3: goto L_0x0070;
                case 4: goto L_0x0070;
                default: goto L_0x006f;
            }
        L_0x006f:
            goto L_0x00b3
        L_0x0070:
            java.lang.Object r3 = r11.getAttributeValue(r2)
            r7 = 0
            r8 = 1
            r9 = 1
            r10 = 0
            r4 = r11
            r5 = r2
            r6 = r3
            r4.setAttribute(r5, r6, r7, r8, r9, r10)
            r0.put(r2, r3)
            goto L_0x00b3
        L_0x0082:
            float r3 = r11.getSize(r2)
            r7 = 0
            r8 = 1
            r9 = 1
            r10 = 0
            r4 = r11
            r5 = r2
            r6 = r3
            r4.setSize(r5, r6, r7, r8, r9, r10)
            java.lang.Float r3 = java.lang.Float.valueOf(r3)
            r0.put(r2, r3)
            goto L_0x00b3
        L_0x0098:
            java.lang.Object r3 = r11.getAttributeValue(r2)
            r7 = 0
            r8 = 1
            r9 = 1
            r10 = 0
            r4 = r11
            r5 = r2
            r6 = r3
            r4.setAttribute(r5, r6, r7, r8, r9, r10)
            java.lang.String r5 = "_SCROLL_TOP"
            r4.setAttribute(r5, r6, r7, r8, r9, r10)
            r0.put(r2, r3)
            java.lang.String r4 = "_SCROLL_TOP"
            r0.put(r4, r3)
        L_0x00b3:
            java.util.Map<java.lang.String, android.support.v4.util.LongSparseArray<java.util.List<com.autonavi.minimap.ajx3.widget.animator.linkage.ViewProperty>>> r3 = r12.mRelationsMap
            java.lang.Object r3 = r3.get(r2)
            android.support.v4.util.LongSparseArray r3 = (android.support.v4.util.LongSparseArray) r3
            r12.syncLinkageAnimatorByMap(r3)
            java.util.Map<java.lang.String, android.support.v4.util.LongSparseArray<java.util.List<com.autonavi.minimap.ajx3.widget.animator.linkage.ViewProperty>>> r3 = r12.mAutoDisRelationsMap
            java.lang.Object r2 = r3.get(r2)
            android.support.v4.util.LongSparseArray r2 = (android.support.v4.util.LongSparseArray) r2
            r12.syncLinkageAnimatorByMap(r2)
            goto L_0x001a
        L_0x00cb:
            int r1 = r0.size()
            if (r1 <= 0) goto L_0x0124
            com.autonavi.minimap.ajx3.platform.ackor.Parcel r1 = new com.autonavi.minimap.ajx3.platform.ackor.Parcel
            r1.<init>()
            int r2 = r0.size()
            int r2 = r2 * 2
            r1.writeInt(r2)
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x00e7:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x010c
            java.lang.Object r2 = r0.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getValue()
            java.lang.Object r2 = r2.getKey()
            java.lang.String r2 = (java.lang.String) r2
            r1.writeString(r2)
            if (r3 == 0) goto L_0x0107
            java.lang.String r2 = r3.toString()
            goto L_0x0108
        L_0x0107:
            r2 = 0
        L_0x0108:
            r1.writeString(r2)
            goto L_0x00e7
        L_0x010c:
            com.autonavi.minimap.ajx3.context.IAjxContext r0 = r12.mAjxContext
            com.autonavi.minimap.ajx3.core.EventInfo$Builder r2 = new com.autonavi.minimap.ajx3.core.EventInfo$Builder
            r2.<init>()
            long r3 = r12.mSourceViewNodeId
            com.autonavi.minimap.ajx3.core.EventInfo$Builder r2 = r2.setNodeId(r3)
            com.autonavi.minimap.ajx3.core.EventInfo$Builder r1 = r2.setAttribute(r1)
            com.autonavi.minimap.ajx3.core.EventInfo r1 = r1.build()
            r0.invokeJsEvent(r1)
        L_0x0124:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.animator.linkage.AjxLinkageAnimator.syncLinkageAnimator():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006c, code lost:
        if (r11.equals("left") != false) goto L_0x007b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void syncLinkageAnimatorByMap(android.support.v4.util.LongSparseArray<java.util.List<com.autonavi.minimap.ajx3.widget.animator.linkage.ViewProperty>> r19) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            r2 = 0
            r3 = 0
        L_0x0009:
            int r4 = r19.size()
            if (r3 >= r4) goto L_0x0147
            long r4 = r1.keyAt(r3)
            java.util.HashMap r6 = new java.util.HashMap
            r7 = 16
            r6.<init>(r7)
            java.lang.Object r7 = r1.get(r4)
            java.util.List r7 = (java.util.List) r7
            java.util.Iterator r7 = r7.iterator()
        L_0x0024:
            boolean r8 = r7.hasNext()
            r9 = 2
            if (r8 == 0) goto L_0x00ec
            java.lang.Object r8 = r7.next()
            com.autonavi.minimap.ajx3.widget.animator.linkage.ViewProperty r8 = (com.autonavi.minimap.ajx3.widget.animator.linkage.ViewProperty) r8
            com.autonavi.minimap.ajx3.dom.AjxDomTree r10 = r0.mAjxDomTree
            com.autonavi.minimap.ajx3.dom.AjxDomNode r10 = r10.findNodeById(r4)
            if (r10 == 0) goto L_0x0024
            java.lang.String r11 = r8.getProperty()
            r12 = -1
            int r13 = r11.hashCode()
            switch(r13) {
                case 115029: goto L_0x006f;
                case 3317767: goto L_0x0066;
                case 66047092: goto L_0x005b;
                case 417780552: goto L_0x0050;
                case 1057926094: goto L_0x0046;
                default: goto L_0x0045;
            }
        L_0x0045:
            goto L_0x007a
        L_0x0046:
            java.lang.String r9 = "relativeScrollDistance"
            boolean r9 = r11.equals(r9)
            if (r9 == 0) goto L_0x007a
            r9 = 4
            goto L_0x007b
        L_0x0050:
            java.lang.String r9 = "scrollTop"
            boolean r9 = r11.equals(r9)
            if (r9 == 0) goto L_0x007a
            r9 = 0
            goto L_0x007b
        L_0x005b:
            java.lang.String r9 = "scrollLeft"
            boolean r9 = r11.equals(r9)
            if (r9 == 0) goto L_0x007a
            r9 = 3
            goto L_0x007b
        L_0x0066:
            java.lang.String r13 = "left"
            boolean r11 = r11.equals(r13)
            if (r11 == 0) goto L_0x007a
            goto L_0x007b
        L_0x006f:
            java.lang.String r9 = "top"
            boolean r9 = r11.equals(r9)
            if (r9 == 0) goto L_0x007a
            r9 = 1
            goto L_0x007b
        L_0x007a:
            r9 = -1
        L_0x007b:
            switch(r9) {
                case 0: goto L_0x00c2;
                case 1: goto L_0x009e;
                case 2: goto L_0x009e;
                case 3: goto L_0x007f;
                case 4: goto L_0x007f;
                default: goto L_0x007e;
            }
        L_0x007e:
            goto L_0x0024
        L_0x007f:
            java.lang.String r9 = r8.getProperty()
            java.lang.Object r9 = r10.getAttributeValue(r9)
            java.lang.String r12 = r8.getProperty()
            r14 = 0
            r15 = 1
            r16 = 1
            r17 = 0
            r11 = r10
            r13 = r9
            r11.setAttribute(r12, r13, r14, r15, r16, r17)
            java.lang.String r8 = r8.getProperty()
            r6.put(r8, r9)
            goto L_0x0024
        L_0x009e:
            java.lang.String r9 = r8.getProperty()
            float r9 = r10.getSize(r9)
            java.lang.String r12 = r8.getProperty()
            r14 = 0
            r15 = 1
            r16 = 1
            r17 = 0
            r11 = r10
            r13 = r9
            r11.setSize(r12, r13, r14, r15, r16, r17)
            java.lang.String r8 = r8.getProperty()
            java.lang.Float r9 = java.lang.Float.valueOf(r9)
            r6.put(r8, r9)
            goto L_0x0024
        L_0x00c2:
            java.lang.String r9 = r8.getProperty()
            java.lang.Object r9 = r10.getAttributeValue(r9)
            java.lang.String r12 = r8.getProperty()
            r14 = 0
            r15 = 1
            r16 = 0
            r17 = 0
            r11 = r10
            r13 = r9
            r11.setAttribute(r12, r13, r14, r15, r16, r17)
            java.lang.String r12 = "_SCROLL_TOP"
            r11.setAttribute(r12, r13, r14, r15, r16, r17)
            java.lang.String r8 = r8.getProperty()
            r6.put(r8, r9)
            java.lang.String r8 = "_SCROLL_TOP"
            r6.put(r8, r9)
            goto L_0x0024
        L_0x00ec:
            int r7 = r6.size()
            if (r7 == 0) goto L_0x0143
            com.autonavi.minimap.ajx3.platform.ackor.Parcel r7 = new com.autonavi.minimap.ajx3.platform.ackor.Parcel
            r7.<init>()
            int r8 = r6.size()
            int r8 = r8 * 2
            r7.writeInt(r8)
            java.util.Set r6 = r6.entrySet()
            java.util.Iterator r6 = r6.iterator()
        L_0x0108:
            boolean r8 = r6.hasNext()
            if (r8 == 0) goto L_0x012d
            java.lang.Object r8 = r6.next()
            java.util.Map$Entry r8 = (java.util.Map.Entry) r8
            java.lang.Object r9 = r8.getValue()
            java.lang.Object r8 = r8.getKey()
            java.lang.String r8 = (java.lang.String) r8
            r7.writeString(r8)
            if (r9 == 0) goto L_0x0128
            java.lang.String r8 = r9.toString()
            goto L_0x0129
        L_0x0128:
            r8 = 0
        L_0x0129:
            r7.writeString(r8)
            goto L_0x0108
        L_0x012d:
            com.autonavi.minimap.ajx3.context.IAjxContext r6 = r0.mAjxContext
            com.autonavi.minimap.ajx3.core.EventInfo$Builder r8 = new com.autonavi.minimap.ajx3.core.EventInfo$Builder
            r8.<init>()
            com.autonavi.minimap.ajx3.core.EventInfo$Builder r4 = r8.setNodeId(r4)
            com.autonavi.minimap.ajx3.core.EventInfo$Builder r4 = r4.setAttribute(r7)
            com.autonavi.minimap.ajx3.core.EventInfo r4 = r4.build()
            r6.invokeJsEvent(r4)
        L_0x0143:
            int r3 = r3 + 1
            goto L_0x0009
        L_0x0147:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.animator.linkage.AjxLinkageAnimator.syncLinkageAnimatorByMap(android.support.v4.util.LongSparseArray):void");
    }

    @Nullable
    private Object getProperty(@NonNull AjxDomNode ajxDomNode, @NonNull ViewProperty viewProperty) {
        if (viewProperty.getPropertyType() == 0) {
            return Float.valueOf(ajxDomNode.getSize(viewProperty.getProperty()));
        }
        if (viewProperty.getPropertyType() == 1) {
            return ajxDomNode.getStyleValue(viewProperty.getStyleValue(), 0);
        }
        if (viewProperty.getPropertyType() == 2) {
            return ajxDomNode.getAttributeValue(viewProperty.getProperty());
        }
        return null;
    }

    private void setProperty(@NonNull AjxDomNode ajxDomNode, @NonNull ViewProperty viewProperty, Object obj) {
        if (viewProperty.getPropertyType() == 0 && (obj instanceof Float)) {
            ajxDomNode.setSize(viewProperty.getProperty(), ((Float) obj).floatValue(), true, true, false, true);
        } else if (viewProperty.getPropertyType() == 1) {
            ajxDomNode.setStyle(0, viewProperty.getStyleValue(), obj, true, true, false, true);
        } else {
            if (viewProperty.getPropertyType() == 2) {
                ajxDomNode.setAttribute(viewProperty.getProperty(), obj, true, true, false, true);
            }
        }
    }

    private int getPropertyTypeAccordingName(String str) {
        if ("left".equals(str) || "top".equals(str) || "width".equals(str) || "height".equals(str)) {
            return 0;
        }
        return KeyDefine.name2Type(str) == 1056964767 ? 2 : 1;
    }

    private float getPercentAccordingValue(float f, @NonNull ViewProperty viewProperty) {
        switch (viewProperty.getType()) {
            case 1:
                float from = viewProperty.getFrom();
                float to = viewProperty.getTo();
                if (from != to) {
                    return (f - from) / (to - from);
                }
                if (f < from) {
                    return -1.0f;
                }
                return f > from ? 2.0f : 0.0f;
            case 2:
                try {
                    return getColorDelta((float) ((int) f), viewProperty.getFrom(), viewProperty.getTo());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    break;
                }
        }
        return Float.MAX_VALUE;
    }

    private float getValueAccordingPercent(float f, ViewProperty viewProperty, float f2) {
        switch (viewProperty.getType()) {
            case 1:
                float from = viewProperty.getFrom();
                return from + (f * (viewProperty.getTo() - from));
            case 2:
                return (float) getColorValue(f, viewProperty.getFrom(), viewProperty.getTo());
            case 3:
                if (f == 0.0f) {
                    return viewProperty.getFrom();
                }
                if (f == 1.0f) {
                    return viewProperty.getTo();
                }
                return FormulaHelper.getResultFromFormula(viewProperty.getFormula(), f2);
            default:
                return -1.0f;
        }
    }

    private int getColorValue(float f, float f2, float f3) {
        int i = (int) f2;
        int a = getA(i);
        int r = getR(i);
        int g = getG(i);
        int b = getB(i);
        int i2 = (int) f3;
        return ((a + ((int) (((float) (getA(i2) - a)) * f))) << 24) | ((r + ((int) (((float) (getR(i2) - r)) * f))) << 16) | ((g + ((int) (((float) (getG(i2) - g)) * f))) << 8) | (b + ((int) (f * ((float) (getB(i2) - b)))));
    }

    private float getColorDelta(float f, float f2, float f3) {
        int i = (int) f2;
        int a = getA(i);
        int r = getR(i);
        int g = getG(i);
        int b = getB(i);
        int i2 = (int) f3;
        int a2 = getA(i2);
        int r2 = getR(i2);
        int g2 = getG(i2);
        int b2 = getB(i2);
        if (a != a2) {
            return (float) (getA((int) f) / (a2 - a));
        }
        if (b != b2) {
            return (float) (getB((int) f) / (b2 - b));
        }
        if (g != g2) {
            return (float) (getG((int) f) / (g2 - g));
        }
        if (r != r2) {
            return (float) (getR((int) f) / (r2 - r));
        }
        return 1.0f;
    }
}
