package com.autonavi.bundle.uitemplate.ajx;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup.MarginLayoutParams;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.MapWidgetFactory;
import com.autonavi.bundle.uitemplate.mapwidget.inter.AJXWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.AlignTypeAdapter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.combine.CombineMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.third.ThirdLocalMapWidget;
import com.autonavi.map.fragmentcontainer.page.TopStackPageRecorder;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("mapWidget")
public class ModuleMapWidget extends AbstractModule {
    private static final String AJX_PRE_IMG_PATH = "path://";
    public static final String AJX_PRE_TAG = "ajx_";
    private static final String LOCAL_CUSTOM_PRE_TAG = "local_custom";
    static final String MODULE_NAME = "mapWidget";
    private String mCurrentPageToken = TopStackPageRecorder.getTopStackPageToken();
    private Map<String, JsFunctionCallback> mJsFunctionCallbackMap = new HashMap();

    @AjxMethod(invokeMode = "sync", value = "require")
    public void require() {
    }

    public ModuleMapWidget(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod
    @Deprecated
    public void addAJXWidget(String str, String str2, int i, String str3, int i2, int i3, int i4, int i5, int i6, int i7, JsFunctionCallback jsFunctionCallback) {
        String str4 = str;
        String str5 = str2;
        JsFunctionCallback jsFunctionCallback2 = jsFunctionCallback;
        if (str4 == null || !str4.toLowerCase().startsWith(AJX_PRE_TAG) || str5 == null || !str5.startsWith("path://")) {
            return;
        }
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(0, 0);
        marginLayoutParams.leftMargin = DimensionUtils.standardUnitToPixel((float) i4);
        marginLayoutParams.topMargin = DimensionUtils.standardUnitToPixel((float) i5);
        marginLayoutParams.rightMargin = DimensionUtils.standardUnitToPixel((float) i6);
        marginLayoutParams.bottomMargin = DimensionUtils.standardUnitToPixel((float) i7);
        bel bel = new bel(str4, PictureParams.make(getContext(), str5, false), "", i != 0, 0);
        AJXWidgetProperty aJXWidgetProperty = new AJXWidgetProperty(AlignTypeAdapter.getAlignTypeForDesc(str3), i3, str4, i2, marginLayoutParams, bel);
        aJXWidgetProperty.setJsFunctionCallback(jsFunctionCallback2);
        cacheJSFunctionCallback(str4, jsFunctionCallback2);
        Stub.getMapWidgetManager().setContainerVisible(0);
        Stub.getMapWidgetManager().addWidget(aJXWidgetProperty);
    }

    @AjxMethod
    @Deprecated
    public void updateAJXWidget(String str, String str2, int i, String str3, int i2, int i3, int i4, int i5, int i6, int i7) {
        addAJXWidget(str, str2, i, str3, i2, i3, i4, i5, i6, i7, getJsFunctionCallback(str));
    }

    @AjxMethod("addAJXWidgetForConfig")
    public void addAJXWidgetForConfig(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken()) && str != null && str.toLowerCase().startsWith(AJX_PRE_TAG) && jsFunctionCallback != null) {
            try {
                JSONObject jSONObject = new JSONObject(str2);
                AJXWidgetProperty ajxWidgetObject2Property = ajxWidgetObject2Property(str, jSONObject, ajxItemObject2Model(jSONObject));
                ajxWidgetObject2Property.setJsFunctionCallback(jsFunctionCallback);
                cacheJSFunctionCallback(str, jsFunctionCallback);
                Stub.getMapWidgetManager().setContainerVisible(0);
                Stub.getMapWidgetManager().addWidget(ajxWidgetObject2Property);
            } catch (JSONException unused) {
            }
        }
    }

    @AjxMethod("updateAJXWidgetForConfig")
    public void updateAJXWidgetForConfig(String str, String str2) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken())) {
            addAJXWidgetForConfig(str, str2, getJsFunctionCallback(str));
        }
    }

    @AjxMethod("addNativeWidget")
    public void addNativeWidget(String str, String str2, int i, int i2, int i3, int i4, int i5, int i6, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken())) {
            addNativeWidgetInternal(str, str2, i, i2, i3, i4, i5, i6, jsFunctionCallback, false);
        }
    }

    @AjxMethod("updateNativeWidget")
    public void updateNativeWidget(String str, String str2, int i, int i2, int i3, int i4, int i5, int i6) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken())) {
            addNativeWidget(str, str2, i, i2, i3, i4, i5, i6, getJsFunctionCallback(str));
        }
    }

    @AjxMethod("addNativeWidgetForConfig")
    public void addNativeWidgetForConfig(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken()) && !TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str2);
                addNativeWidgetInternal(str, jSONObject.optString("alignType", null), jSONObject.optInt("index", 0), jSONObject.optInt("priority", 0), jSONObject.optInt("margin_left", 0), jSONObject.optInt("margin_top", 0), jSONObject.optInt("margin_right", 0), jSONObject.optInt("margin_bottom", 0), jsFunctionCallback, jSONObject.optString("custom_event_ajx", "").equals("1"));
            } catch (JSONException unused) {
            }
        }
    }

    @AjxMethod("updateNativeWidgetForConfig")
    public void updateNativeWidgetForConfig(String str, String str2) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken())) {
            addNativeWidgetForConfig(str, str2, getJsFunctionCallback(str));
        }
    }

    @AjxMethod("addAJXCombinedWidget")
    public void addAJXCombinedWidget(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken()) && str != null && str.toLowerCase().startsWith(AJX_PRE_TAG)) {
            try {
                JSONObject jSONObject = new JSONObject(str2);
                JSONArray optJSONArray = jSONObject.optJSONArray("items");
                if (optJSONArray != null) {
                    if (optJSONArray.length() != 0) {
                        bel[] ajxItemArray2Models = ajxItemArray2Models(optJSONArray);
                        if (ajxItemArray2Models != null) {
                            AJXWidgetProperty ajxWidgetObject2Property = ajxWidgetObject2Property(str, jSONObject, ajxItemArray2Models);
                            ajxWidgetObject2Property.setJsFunctionCallback(jsFunctionCallback);
                            cacheJSFunctionCallback(str, jsFunctionCallback);
                            Stub.getMapWidgetManager().setContainerVisible(0);
                            Stub.getMapWidgetManager().addWidget(ajxWidgetObject2Property);
                        }
                    }
                }
            } catch (JSONException unused) {
            }
        }
    }

    @AjxMethod("updateAJXCombinedWidget")
    public void updateAJXCombinedWidget(String str, String str2) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken())) {
            addAJXCombinedWidget(str, str2, getJsFunctionCallback(str));
        }
    }

    @AjxMethod("setWidgets")
    public void setWidgets(String str, JsFunctionCallback jsFunctionCallback) {
        WidgetProperty widgetProperty;
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken()) && !TextUtils.isEmpty(str) && jsFunctionCallback != null) {
            ArrayList arrayList = new ArrayList();
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                if (length != 0) {
                    for (int i = 0; i < length; i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        String string = jSONObject.getString("tag");
                        if (!TextUtils.isEmpty(string)) {
                            if (string.startsWith(AJX_PRE_TAG)) {
                                JSONArray optJSONArray = jSONObject.optJSONArray("items");
                                if (!(optJSONArray == null || optJSONArray.length() == 0)) {
                                    bel[] ajxItemArray2Models = ajxItemArray2Models(optJSONArray);
                                    if (ajxItemArray2Models != null) {
                                        widgetProperty = ajxWidgetObject2Property(string, jSONObject, ajxItemArray2Models);
                                    }
                                }
                            } else {
                                int alignTypeForDesc = AlignTypeAdapter.getAlignTypeForDesc(jSONObject.optString("alignType", null));
                                int optInt = jSONObject.optInt("index", 0);
                                int optInt2 = jSONObject.optInt("priority", 0);
                                MarginLayoutParams marginLayoutParams = new MarginLayoutParams(0, 0);
                                marginLayoutParams.leftMargin = DimensionUtils.standardUnitToPixel((float) jSONObject.optInt("margin_left", 0));
                                marginLayoutParams.topMargin = DimensionUtils.standardUnitToPixel((float) jSONObject.optInt("margin_top", 0));
                                marginLayoutParams.rightMargin = DimensionUtils.standardUnitToPixel((float) jSONObject.optInt("margin_right", 0));
                                marginLayoutParams.bottomMargin = DimensionUtils.standardUnitToPixel((float) jSONObject.optInt("margin_bottom", 0));
                                widgetProperty = new WidgetProperty(alignTypeForDesc, optInt2, string, optInt, marginLayoutParams);
                            }
                            widgetProperty.setJsFunctionCallback(jsFunctionCallback);
                            cacheJSFunctionCallback(string, jsFunctionCallback);
                            arrayList.add(widgetProperty);
                        }
                    }
                    Stub.getMapWidgetManager().setContainerVisible(0);
                    Stub.getMapWidgetManager().setWidget((IWidgetProperty[]) (WidgetProperty[]) arrayList.toArray());
                }
            } catch (JSONException unused) {
            }
        }
    }

    @AjxMethod("removeWidget")
    public void removeWidget(@NonNull String str) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken())) {
            Stub.getMapWidgetManager().removeWidget(str);
        }
    }

    @AjxMethod("setWidgetVisible")
    public void setWidgetVisible(@NonNull String str, @NonNull String str2) {
        int i;
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken())) {
            if ("visible".equalsIgnoreCase(str2)) {
                i = 0;
            } else if ("invisible".equalsIgnoreCase(str2)) {
                i = 4;
            } else if ("gone".equalsIgnoreCase(str2)) {
                i = 8;
            } else {
                return;
            }
            Stub.getMapWidgetManager().setWidgetVisibleForType(str, i);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getWidgetVisible")
    public String getWidgetVisible(@NonNull String str) {
        int widgetVisibleForType = Stub.getMapWidgetManager().getWidgetVisibleForType(str);
        if (widgetVisibleForType == 0) {
            return "visible";
        }
        return widgetVisibleForType == 4 ? "invisible" : "gone";
    }

    @AjxMethod(invokeMode = "sync", value = "getMapVisibleArea")
    public String getMapVisibleArea() {
        Rect containerArea = Stub.getMapWidgetManager().getContainerArea(true);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("left", 0);
            jSONObject.put("top", containerArea.top);
            jSONObject.put("right", 0);
            jSONObject.put("bottom", ags.a(getContext().getNativeContext()).height() - containerArea.bottom);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    @AjxMethod("setMapWidgetContainerMargin")
    public void setMapWidgetContainerMargin(int i, int i2, int i3, int i4) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken())) {
            Stub.getMapWidgetManager().setContainerMargin(DimensionUtils.standardUnitToPixel((float) i3), DimensionUtils.standardUnitToPixel((float) i), DimensionUtils.standardUnitToPixel((float) i4), DimensionUtils.standardUnitToPixel((float) i2));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getMapWidgetContainerMargin")
    public String getMapWidgetContainerMargin() {
        MarginLayoutParams containerMargin = Stub.getMapWidgetManager().getContainerMargin();
        JSONObject jSONObject = new JSONObject();
        if (containerMargin != null) {
            try {
                jSONObject.put("top", (double) DimensionUtils.pixelToStandardUnit((float) containerMargin.topMargin));
                jSONObject.put("bottom", (double) DimensionUtils.pixelToStandardUnit((float) containerMargin.bottomMargin));
                jSONObject.put("left", (double) DimensionUtils.pixelToStandardUnit((float) containerMargin.leftMargin));
                jSONObject.put("right", (double) DimensionUtils.pixelToStandardUnit((float) containerMargin.rightMargin));
            } catch (JSONException unused) {
            }
        }
        return jSONObject.toString();
    }

    @AjxMethod("setContainerAlpha")
    public void setContainerAlpha(float f) {
        if (TextUtils.equals(this.mCurrentPageToken, TopStackPageRecorder.getTopStackPageToken()) && f >= 0.0f && f <= 1.0f) {
            if (Stub.getMapWidgetManager().getContainerVisible() != 0) {
                Stub.getMapWidgetManager().setContainerVisible(0);
            }
            Stub.getMapWidgetManager().setContainerAlpha(f);
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        this.mJsFunctionCallbackMap.clear();
        this.mJsFunctionCallbackMap = null;
    }

    private void addNativeWidgetInternal(String str, String str2, int i, int i2, int i3, int i4, int i5, int i6, JsFunctionCallback jsFunctionCallback, boolean z) {
        String str3 = str;
        JsFunctionCallback jsFunctionCallback2 = jsFunctionCallback;
        boolean z2 = z;
        if (!TextUtils.isEmpty(str)) {
            MarginLayoutParams marginLayoutParams = new MarginLayoutParams(0, 0);
            marginLayoutParams.leftMargin = DimensionUtils.standardUnitToPixel((float) i3);
            marginLayoutParams.topMargin = DimensionUtils.standardUnitToPixel((float) i4);
            marginLayoutParams.rightMargin = DimensionUtils.standardUnitToPixel((float) i5);
            marginLayoutParams.bottomMargin = DimensionUtils.standardUnitToPixel((float) i6);
            if (str3.contains(".")) {
                String[] split = str3.split("\\.");
                IMapWidget[] iMapWidgetArr = new IMapWidget[split.length];
                int i7 = 0;
                while (i7 < split.length) {
                    String str4 = split[i7];
                    IMapWidget iMapWidget = null;
                    if (!str4.startsWith(LOCAL_CUSTOM_PRE_TAG)) {
                        iMapWidget = MapWidgetFactory.createInstance(getNativeContext(), new WidgetProperty(2, 0, str4));
                    } else if (str4.equals("local_custom_drive_preference")) {
                        iMapWidget = new ThirdLocalMapWidget(getNativeContext(), new WidgetProperty(2, 0, str4));
                    }
                    if (iMapWidget != null) {
                        iMapWidgetArr[i7] = iMapWidget;
                        i7++;
                    } else {
                        return;
                    }
                }
                CombineMapWidget combineMapWidget = new CombineMapWidget(getNativeContext());
                combineMapWidget.combineWidgets(iMapWidgetArr);
                ((WidgetProperty) combineMapWidget.getWidgetProperty()).setWidgetType(WidgetType.COMBINE);
                ((WidgetProperty) combineMapWidget.getWidgetProperty()).setAlignType(AlignTypeAdapter.getAlignTypeForDesc(str2));
                ((WidgetProperty) combineMapWidget.getWidgetProperty()).setPriority(i2);
                ((WidgetProperty) combineMapWidget.getWidgetProperty()).setIndex(i);
                ((WidgetProperty) combineMapWidget.getWidgetProperty()).setLayoutParams(marginLayoutParams);
                ((WidgetProperty) combineMapWidget.getWidgetProperty()).setJsFunctionCallback(jsFunctionCallback2);
                ((WidgetProperty) combineMapWidget.getWidgetProperty()).setInterceptEventByAjx(z2);
                cacheJSFunctionCallback(str3, jsFunctionCallback2);
                Stub.getMapWidgetManager().setContainerVisible(0);
                Stub.getMapWidgetManager().addWidget(combineMapWidget);
                return;
            }
            int alignTypeForDesc = AlignTypeAdapter.getAlignTypeForDesc(str2);
            WidgetProperty widgetProperty = new WidgetProperty(alignTypeForDesc, i2, str3, i, marginLayoutParams);
            widgetProperty.setJsFunctionCallback(jsFunctionCallback2);
            widgetProperty.setInterceptEventByAjx(z2);
            cacheJSFunctionCallback(str3, jsFunctionCallback2);
            Stub.getMapWidgetManager().setContainerVisible(0);
            Stub.getMapWidgetManager().addWidget(widgetProperty);
        }
    }

    private void cacheJSFunctionCallback(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            this.mJsFunctionCallbackMap.put(str, jsFunctionCallback);
        }
    }

    private JsFunctionCallback getJsFunctionCallback(String str) {
        return this.mJsFunctionCallbackMap.get(str);
    }

    @Nullable
    private bel[] ajxItemArray2Models(JSONArray jSONArray) {
        int length = jSONArray.length();
        bel[] belArr = new bel[length];
        int i = 0;
        while (i < length) {
            try {
                bel ajxItemObject2Model = ajxItemObject2Model(jSONArray.getJSONObject(i));
                if (ajxItemObject2Model == null) {
                    return null;
                }
                belArr[i] = ajxItemObject2Model;
                i++;
            } catch (JSONException unused) {
                return null;
            }
        }
        return belArr;
    }

    @Nullable
    private bel ajxItemObject2Model(JSONObject jSONObject) {
        String optString = jSONObject.optString("tag", "");
        String optString2 = jSONObject.optString("imgPath", "");
        String optString3 = jSONObject.optString("text", "");
        String optString4 = jSONObject.optString("textColor", "#8A000000");
        boolean z = true;
        boolean z2 = jSONObject.optInt("boldFont", 0) == 1;
        int optInt = jSONObject.optInt("red", 0);
        int optInt2 = jSONObject.optInt("priority", 0);
        if (optString.equals("") || !optString2.startsWith("path://") || (!optString4.equals("") && !optString4.startsWith(MetaRecord.LOG_SEPARATOR))) {
            return null;
        }
        PictureParams make = PictureParams.make(getContext(), optString2, false);
        if (optInt == 0) {
            z = false;
        }
        bel bel = new bel(optString, make, optString3, z, optInt2);
        bel.d = optString4;
        bel.e = z2;
        return bel;
    }

    private AJXWidgetProperty ajxWidgetObject2Property(String str, JSONObject jSONObject, bel... belArr) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        JSONObject jSONObject2 = jSONObject;
        int optInt = jSONObject2.optInt("itemTextImageMargin", -1);
        int standardUnitToPixel = optInt >= 0 ? DimensionUtils.standardUnitToPixel((float) optInt) : 0;
        int optInt2 = jSONObject2.optInt("itemImageSlideLength", -1);
        if (optInt2 >= 0) {
            i = DimensionUtils.standardUnitToPixel((float) optInt2);
        } else {
            i = DimensionUtils.dipToPixel(24.0f);
        }
        int optInt3 = jSONObject2.optInt("itemTopBottomPadding", -1);
        if (optInt3 >= 0) {
            i2 = DimensionUtils.standardUnitToPixel((float) optInt3);
        } else {
            i2 = DimensionUtils.dipToPixel(7.0f);
        }
        int optInt4 = jSONObject2.optInt("itemLeftRightPadding", -1);
        if (optInt4 >= 0) {
            i3 = DimensionUtils.standardUnitToPixel((float) optInt4);
        } else {
            i3 = DimensionUtils.dipToPixel(7.0f);
        }
        int optInt5 = jSONObject2.optInt("radius", -1);
        if (optInt5 >= 0) {
            i4 = DimensionUtils.standardUnitToPixel((float) optInt5);
        } else {
            i4 = DimensionUtils.dipToPixel(38.0f);
        }
        int optInt6 = jSONObject2.optInt("topBottomPadding", -1);
        if (optInt6 >= 0) {
            optInt6 = DimensionUtils.standardUnitToPixel((float) optInt6);
        }
        int optInt7 = jSONObject2.optInt("itemTextSize", 0);
        if (optInt7 <= 0) {
            i5 = DimensionUtils.dipToPixel(8.0f);
        } else {
            i5 = DimensionUtils.standardUnitToPixel((float) optInt7);
        }
        int optInt8 = jSONObject2.optInt("minItemCount", 0);
        String optString = jSONObject2.optString("alignType", null);
        int optInt9 = jSONObject2.optInt("index", 0);
        int optInt10 = jSONObject2.optInt("priority", 0);
        int optInt11 = jSONObject2.optInt("margin_left", 0);
        int optInt12 = jSONObject2.optInt("margin_top", 0);
        int optInt13 = jSONObject2.optInt("margin_right", 0);
        int optInt14 = jSONObject2.optInt("margin_bottom", 0);
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(0, 0);
        marginLayoutParams.leftMargin = DimensionUtils.standardUnitToPixel((float) optInt11);
        marginLayoutParams.topMargin = DimensionUtils.standardUnitToPixel((float) optInt12);
        marginLayoutParams.rightMargin = DimensionUtils.standardUnitToPixel((float) optInt13);
        marginLayoutParams.bottomMargin = DimensionUtils.standardUnitToPixel((float) optInt14);
        AJXWidgetProperty aJXWidgetProperty = new AJXWidgetProperty(AlignTypeAdapter.getAlignTypeForDesc(optString), optInt10, str, optInt9, marginLayoutParams, belArr);
        aJXWidgetProperty.setMinSubWidgetCount(optInt8);
        aJXWidgetProperty.setItemTextImageMargin(standardUnitToPixel);
        aJXWidgetProperty.setItemImageSlideLength(i);
        aJXWidgetProperty.setItemTopBottomPadding(i2);
        aJXWidgetProperty.setItemLeftRightPadding(i3);
        aJXWidgetProperty.setContainerRadius(i4);
        aJXWidgetProperty.setContainerTopBottomPadding(optInt6);
        aJXWidgetProperty.setItemTextSize(i5);
        return aJXWidgetProperty;
    }
}
