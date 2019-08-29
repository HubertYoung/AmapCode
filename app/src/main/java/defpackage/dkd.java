package defpackage;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.drive.view.BarPicker;
import com.autonavi.minimap.drive.view.BarPicker.c;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: dkd reason: default package */
/* compiled from: Ajx3BarPickerProperty */
public final class dkd extends BaseProperty<BarPicker> {
    public dkd(@NonNull BarPicker barPicker, @NonNull IAjxContext iAjxContext) {
        super(barPicker, iAjxContext);
    }

    public final void updateAttribute(String str, Object obj) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        String str2 = str;
        if (obj != null) {
            if (TextUtils.equals("init_params", str2)) {
                try {
                    JSONObject jSONObject = new JSONObject((String) obj);
                    int a = agn.a((Context) AMapAppGlobal.getApplication(), (float) jSONObject.optInt("title_height"));
                    int a2 = agn.a((Context) AMapAppGlobal.getApplication(), (float) jSONObject.optInt("middle_height"));
                    int a3 = agn.a((Context) AMapAppGlobal.getApplication(), (float) jSONObject.optInt("bar_max_height"));
                    int a4 = agn.a((Context) AMapAppGlobal.getApplication(), (float) jSONObject.optInt("bar_min_height"));
                    int a5 = agn.a((Context) AMapAppGlobal.getApplication(), (float) jSONObject.optInt("bar_width"));
                    int a6 = agn.a((Context) AMapAppGlobal.getApplication(), (float) jSONObject.optInt("divider_width"));
                    int a7 = agn.a((Context) AMapAppGlobal.getApplication(), (float) jSONObject.optInt("divider_height"));
                    int a8 = agn.a((Context) AMapAppGlobal.getApplication(), (float) jSONObject.optInt("bar_max_width", 24));
                    int a9 = agn.a((Context) AMapAppGlobal.getApplication(), (float) jSONObject.optInt("bar_min_width", 16));
                    int optInt = jSONObject.optInt("bar_count_per_screen", 7);
                    String optString = jSONObject.optString("title_focus_text_size");
                    if (!TextUtils.isEmpty(optString)) {
                        if (optString.endsWith(Params.UNIT_PX)) {
                            i8 = Integer.parseInt(optString.substring(0, optString.length() - 2));
                        } else {
                            i8 = Integer.parseInt(optString);
                        }
                        i = DimensionUtils.standardUnitToPixel((float) i8);
                    } else {
                        i = agn.a((Context) AMapAppGlobal.getApplication(), 11.0f);
                    }
                    int i9 = i;
                    String optString2 = jSONObject.optString("title_nomal_text_size");
                    if (!TextUtils.isEmpty(optString2)) {
                        if (optString2.endsWith(Params.UNIT_PX)) {
                            i7 = Integer.parseInt(optString2.substring(0, optString2.length() - 2));
                        } else {
                            i7 = Integer.parseInt(optString2);
                        }
                        i2 = DimensionUtils.standardUnitToPixel((float) i7);
                    } else {
                        i2 = agn.a((Context) AMapAppGlobal.getApplication(), 8.0f);
                    }
                    int i10 = i2;
                    String optString3 = jSONObject.optString("bar_top_focus_text_size");
                    if (!TextUtils.isEmpty(optString3)) {
                        if (optString3.endsWith(Params.UNIT_PX)) {
                            i6 = Integer.parseInt(optString3.substring(0, optString3.length() - 2));
                        } else {
                            i6 = Integer.parseInt(optString3);
                        }
                        i3 = DimensionUtils.standardUnitToPixel((float) i6);
                    } else {
                        i3 = agn.a((Context) AMapAppGlobal.getApplication(), 11.0f);
                    }
                    int i11 = i3;
                    String optString4 = jSONObject.optString("bar_top_nomal_text_size");
                    if (!TextUtils.isEmpty(optString4)) {
                        if (optString4.endsWith(Params.UNIT_PX)) {
                            i5 = Integer.parseInt(optString4.substring(0, optString4.length() - 2));
                        } else {
                            i5 = Integer.parseInt(optString4);
                        }
                        i4 = DimensionUtils.standardUnitToPixel((float) i5);
                    } else {
                        i4 = agn.a((Context) AMapAppGlobal.getApplication(), 8.0f);
                    }
                    int i12 = i4;
                    ((BarPicker) this.mView).initView(a, a2, a3, a4, a5, a6, a7, i9, i10, Color.parseColor(jSONObject.optString("title_focus_text_color")), Color.parseColor(jSONObject.optString("title_nomal_text_color")), i11, i12, Color.parseColor(jSONObject.optString("bar_top_focus_text_color")), Color.parseColor(jSONObject.optString("bar_top_nomal_text_color")), Color.parseColor(jSONObject.optString("title_unreachable_text_color")), a8, a9, optInt);
                    ((BarPicker) this.mView).initParamsAfterMeasure();
                } catch (Exception e) {
                    Exception exc = e;
                    AMapLog.w("BarPicker", "init param error");
                    if (bno.a) {
                        throw new IllegalArgumentException("init param error !", exc);
                    }
                    return;
                }
            } else if (TextUtils.equals("picker_data", str2)) {
                ArrayList arrayList = new ArrayList();
                try {
                    JSONObject jSONObject2 = new JSONObject((String) obj);
                    int optInt2 = jSONObject2.optInt("left_bound");
                    int optInt3 = jSONObject2.optInt("right_bound");
                    int optInt4 = jSONObject2.optInt("focus_index", -1);
                    JSONArray optJSONArray = jSONObject2.optJSONArray("points");
                    if (optJSONArray != null) {
                        if (optJSONArray.length() > optInt2 + optInt3) {
                            for (int i13 = 0; i13 < optJSONArray.length(); i13++) {
                                JSONObject jSONObject3 = optJSONArray.getJSONObject(i13);
                                arrayList.add(new c(jSONObject3.optInt("type", 2), (float) jSONObject3.optInt("eta_time"), jSONObject3.optString("title")));
                            }
                            ((BarPicker) this.mView).feedWithAnim(arrayList, optInt4, optInt2, optInt3);
                        }
                    }
                    AMapLog.w("BarPicker", "show data error");
                    return;
                } catch (Exception unused) {
                    AMapLog.w("BarPicker", "show data error");
                    return;
                }
            }
            if (!TextUtils.isEmpty(str)) {
                super.updateAttribute(str, obj);
            }
        }
    }
}
