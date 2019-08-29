package com.airbnb.lottie.model.layer;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.Mask;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class Layer {
    private static final String v = "Layer";
    public final List<hn> a;
    public final ev b;
    public final String c;
    public final long d;
    public final LayerType e;
    public final long f;
    @Nullable
    public final String g;
    public final List<Mask> h;
    public final hi i;
    public final int j;
    public final int k;
    public final int l;
    public final float m;
    public final float n;
    public final int o;
    public final int p;
    @Nullable
    public final hg q;
    @Nullable
    public final hh r;
    @Nullable
    public final gy s;
    public final List<fc<Float>> t;
    public final MatteType u;

    public enum LayerType {
        PreComp,
        Solid,
        Image,
        Null,
        Shape,
        Text,
        Unknown
    }

    public enum MatteType {
        None,
        Add,
        Invert,
        Unknown
    }

    public static class a {
        public static Layer a(JSONObject jSONObject, ev evVar) {
            LayerType layerType;
            int i;
            int i2;
            int i3;
            hh hhVar;
            hg hgVar;
            int i4;
            int i5;
            String str;
            ArrayList arrayList;
            ArrayList arrayList2;
            float f;
            float f2;
            ArrayList arrayList3;
            JSONObject jSONObject2 = jSONObject;
            ev evVar2 = evVar;
            String optString = jSONObject2.optString(LogItem.MM_C18_K4_NM);
            String optString2 = jSONObject2.optString("refId");
            if (optString.endsWith(".ai") || jSONObject2.optString("cl", "").equals("ai")) {
                evVar2.a((String) "Convert your Illustrator layers to shape layers.");
            }
            long optLong = jSONObject2.optLong("ind");
            int optInt = jSONObject2.optInt(com.alipay.sdk.sys.a.g, -1);
            if (optInt < LayerType.Unknown.ordinal()) {
                layerType = LayerType.values()[optInt];
            } else {
                layerType = LayerType.Unknown;
            }
            if (layerType == LayerType.Text && !ij.a(evVar2, 8)) {
                layerType = LayerType.Unknown;
                evVar2.a((String) "Text is only supported on bodymovin >= 4.8.0");
            }
            LayerType layerType2 = layerType;
            long optLong2 = jSONObject2.optLong("parent", -1);
            if (layerType2 == LayerType.Solid) {
                i3 = (int) (((float) jSONObject2.optInt("sw")) * evVar2.k);
                i2 = (int) (((float) jSONObject2.optInt("sh")) * evVar2.k);
                i = Color.parseColor(jSONObject2.optString(H5Param.SAFEPAY_CONTEXT));
            } else {
                i3 = 0;
                i2 = 0;
                i = 0;
            }
            hi a = defpackage.hi.a.a(jSONObject2.optJSONObject("ks"), evVar2);
            MatteType matteType = MatteType.values()[jSONObject2.optInt(H5Param.TRANSPARENT)];
            ArrayList arrayList4 = new ArrayList();
            JSONArray optJSONArray = jSONObject2.optJSONArray("masksProperties");
            if (optJSONArray != null) {
                for (int i6 = 0; i6 < optJSONArray.length(); i6++) {
                    arrayList4.add(com.airbnb.lottie.model.content.Mask.a.a(optJSONArray.optJSONObject(i6), evVar2));
                }
            }
            ArrayList arrayList5 = new ArrayList();
            JSONArray optJSONArray2 = jSONObject2.optJSONArray("shapes");
            if (optJSONArray2 != null) {
                for (int i7 = 0; i7 < optJSONArray2.length(); i7++) {
                    hn a2 = hv.a(optJSONArray2.optJSONObject(i7), evVar2);
                    if (a2 != null) {
                        arrayList5.add(a2);
                    }
                }
            }
            JSONObject optJSONObject = jSONObject2.optJSONObject(LogItem.MM_C15_K4_TIME);
            if (optJSONObject != null) {
                hg a3 = defpackage.hg.a.a(optJSONObject.optJSONObject("d"), evVar2);
                hhVar = defpackage.hh.a.a(optJSONObject.optJSONArray("a").optJSONObject(0), evVar2);
                hgVar = a3;
            } else {
                hgVar = null;
                hhVar = null;
            }
            if (jSONObject2.has("ef")) {
                JSONArray optJSONArray3 = jSONObject2.optJSONArray("ef");
                String[] strArr = new String[optJSONArray3.length()];
                for (int i8 = 0; i8 < optJSONArray3.length(); i8++) {
                    strArr[i8] = optJSONArray3.optJSONObject(i8).optString(LogItem.MM_C18_K4_NM);
                }
                StringBuilder sb = new StringBuilder("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: ");
                sb.append(Arrays.toString(strArr));
                evVar2.a(sb.toString());
            }
            float optDouble = (float) jSONObject2.optDouble(H5Param.SHOW_REPORT_BTN, 1.0d);
            float optDouble2 = ((float) jSONObject2.optDouble("st")) / evVar.b();
            if (layerType2 == LayerType.PreComp) {
                i5 = (int) (((float) jSONObject2.optInt("w")) * evVar2.k);
                i4 = (int) (((float) jSONObject2.optInt("h")) * evVar2.k);
            } else {
                i5 = 0;
                i4 = 0;
            }
            float optLong3 = ((float) jSONObject2.optLong(OnNativeInvokeListener.ARG_IP)) / optDouble;
            float optLong4 = ((float) jSONObject2.optLong("op")) / optDouble;
            ArrayList arrayList6 = new ArrayList();
            if (optLong3 > 0.0f) {
                Float valueOf = Float.valueOf(0.0f);
                Float valueOf2 = Float.valueOf(0.0f);
                Float valueOf3 = Float.valueOf(optLong3);
                fc fcVar = r1;
                ArrayList arrayList7 = arrayList6;
                Float f3 = valueOf;
                f = optLong3;
                Float f4 = valueOf2;
                arrayList2 = arrayList5;
                arrayList = arrayList4;
                f2 = optDouble;
                str = optString2;
                fc fcVar2 = new fc(evVar2, f3, f4, null, 0.0f, valueOf3);
                arrayList3 = arrayList7;
                arrayList3.add(fcVar2);
            } else {
                f = optLong3;
                arrayList2 = arrayList5;
                arrayList = arrayList4;
                f2 = optDouble;
                str = optString2;
                arrayList3 = arrayList6;
            }
            if (optLong4 <= 0.0f) {
                optLong4 = (float) evVar2.j;
            }
            float f5 = optLong4 + 1.0f;
            ev evVar3 = evVar2;
            fc fcVar3 = r1;
            long j = optLong2;
            ArrayList arrayList8 = arrayList3;
            fc fcVar4 = new fc(evVar3, Float.valueOf(1.0f), Float.valueOf(1.0f), null, f, Float.valueOf(f5));
            arrayList8.add(fcVar3);
            fc fcVar5 = new fc(evVar3, Float.valueOf(0.0f), Float.valueOf(0.0f), null, f5, Float.valueOf(Float.MAX_VALUE));
            arrayList8.add(fcVar5);
            Layer layer = new Layer(arrayList2, evVar2, optString, optLong, layerType2, j, str, arrayList, a, i3, i2, i, f2, optDouble2, i5, i4, hgVar, hhVar, arrayList8, matteType, jSONObject2.has("tm") ? defpackage.gy.a.a(jSONObject2.optJSONObject("tm"), evVar2, false) : null, 0);
            return layer;
        }

        public static Layer a(ev evVar) {
            ev evVar2 = evVar;
            Rect rect = evVar2.h;
            Layer layer = new Layer(Collections.emptyList(), evVar2, DictionaryKeys.ENV_ROOT, -1, LayerType.PreComp, -1, null, Collections.emptyList(), defpackage.hi.a.a(), 0, 0, 0, 0.0f, 0.0f, rect.width(), rect.height(), null, null, Collections.emptyList(), MatteType.None, null, 0);
            return layer;
        }
    }

    /* synthetic */ Layer(List list, ev evVar, String str, long j2, LayerType layerType, long j3, String str2, List list2, hi hiVar, int i2, int i3, int i4, float f2, float f3, int i5, int i6, hg hgVar, hh hhVar, List list3, MatteType matteType, gy gyVar, byte b2) {
        this(list, evVar, str, j2, layerType, j3, str2, list2, hiVar, i2, i3, i4, f2, f3, i5, i6, hgVar, hhVar, list3, matteType, gyVar);
    }

    private Layer(List<hn> list, ev evVar, String str, long j2, LayerType layerType, long j3, @Nullable String str2, List<Mask> list2, hi hiVar, int i2, int i3, int i4, float f2, float f3, int i5, int i6, @Nullable hg hgVar, @Nullable hh hhVar, List<fc<Float>> list3, MatteType matteType, @Nullable gy gyVar) {
        this.a = list;
        this.b = evVar;
        this.c = str;
        this.d = j2;
        this.e = layerType;
        this.f = j3;
        this.g = str2;
        this.h = list2;
        this.i = hiVar;
        this.j = i2;
        this.k = i3;
        this.l = i4;
        this.m = f2;
        this.n = f3;
        this.o = i5;
        this.p = i6;
        this.q = hgVar;
        this.r = hhVar;
        this.t = list3;
        this.u = matteType;
        this.s = gyVar;
    }

    public String toString() {
        return a("");
    }

    public final String a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(this.c);
        sb.append("\n");
        Layer a2 = this.b.a(this.f);
        if (a2 != null) {
            sb.append("\t\tParents: ");
            sb.append(a2.c);
            Layer a3 = this.b.a(a2.f);
            while (a3 != null) {
                sb.append("->");
                sb.append(a3.c);
                a3 = this.b.a(a3.f);
            }
            sb.append(str);
            sb.append("\n");
        }
        if (!this.h.isEmpty()) {
            sb.append(str);
            sb.append("\tMasks: ");
            sb.append(this.h.size());
            sb.append("\n");
        }
        if (!(this.j == 0 || this.k == 0)) {
            sb.append(str);
            sb.append("\tBackground: ");
            sb.append(String.format(Locale.US, "%dx%d %X\n", new Object[]{Integer.valueOf(this.j), Integer.valueOf(this.k), Integer.valueOf(this.l)}));
        }
        if (!this.a.isEmpty()) {
            sb.append(str);
            sb.append("\tShapes:\n");
            for (hn next : this.a) {
                sb.append(str);
                sb.append("\t\t");
                sb.append(next);
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
