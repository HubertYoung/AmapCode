package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SparseArrayCompat;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.model.layer.Layer.LayerType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.antui.iconfont.constants.IconfontConstants;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.common.SuperId;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

/* renamed from: ev reason: default package */
/* compiled from: LottieComposition */
public final class ev {
    public final Map<String, List<Layer>> a;
    final Map<String, ex> b;
    public final Map<String, gr> c;
    public final SparseArrayCompat<gs> d;
    final LongSparseArray<Layer> e;
    public final List<Layer> f;
    public final ez g;
    public final Rect h;
    public final long i;
    public final long j;
    public final float k;
    public final int l;
    public final int m;
    public final int n;
    private final HashSet<String> o;
    private final float p;

    /* renamed from: ev$a */
    /* compiled from: LottieComposition */
    public static class a {
        public static er a(Context context, String str, ey eyVar) {
            try {
                return a(context, context.getAssets().open(str), eyVar);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to find file ".concat(String.valueOf(str)), e);
            }
        }

        public static er a(Context context, InputStream inputStream, ey eyVar) {
            gq gqVar = new gq(context.getResources(), eyVar);
            gqVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new InputStream[]{inputStream});
            return gqVar;
        }

        public static ev a(Context context, String str) {
            try {
                return a(context.getResources(), context.getAssets().open(str));
            } catch (IOException e) {
                throw new IllegalStateException("Unable to find file ".concat(String.valueOf(str)), e);
            }
        }

        public static er a(Resources resources, JSONObject jSONObject, ey eyVar) {
            gt gtVar = new gt(resources, eyVar);
            gtVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new JSONObject[]{jSONObject});
            return gtVar;
        }

        @Nullable
        public static ev a(Resources resources, InputStream inputStream) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb.append(readLine);
                    } else {
                        ev a = a(resources, new JSONObject(sb.toString()));
                        ij.a((Closeable) inputStream);
                        return a;
                    }
                }
            } catch (IOException e) {
                new IllegalStateException("Unable to find file.", e);
            } catch (JSONException e2) {
                new IllegalStateException("Unable to load JSON.", e2);
            } catch (Throwable th) {
                ij.a((Closeable) inputStream);
                throw th;
            }
            ij.a((Closeable) inputStream);
            return null;
        }

        public static ev a(Resources resources, JSONObject jSONObject) {
            JSONObject jSONObject2 = jSONObject;
            float f = resources.getDisplayMetrics().density;
            int optInt = jSONObject2.optInt("w", -1);
            int optInt2 = jSONObject2.optInt("h", -1);
            Rect rect = (optInt == -1 || optInt2 == -1) ? null : new Rect(0, 0, (int) (((float) optInt) * f), (int) (((float) optInt2) * f));
            long optLong = jSONObject2.optLong(OnNativeInvokeListener.ARG_IP, 0);
            long optLong2 = jSONObject2.optLong("op", 0);
            String[] split = jSONObject2.optString("v").split("[.]");
            ev evVar = new ev(rect, optLong, optLong2, (float) jSONObject2.optDouble("fr", 0.0d), f, Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), 0);
            JSONArray optJSONArray = jSONObject2.optJSONArray("assets");
            b(optJSONArray, evVar);
            a(optJSONArray, evVar);
            b(jSONObject2.optJSONObject(IconfontConstants.KEY_ICON_FONTS), evVar);
            c(jSONObject2.optJSONArray("chars"), evVar);
            a(jSONObject2, evVar);
            return evVar;
        }

        private static void a(JSONObject jSONObject, ev evVar) {
            JSONArray optJSONArray = jSONObject.optJSONArray("layers");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                int i = 0;
                for (int i2 = 0; i2 < length; i2++) {
                    Layer a = com.airbnb.lottie.model.layer.Layer.a.a(optJSONArray.optJSONObject(i2), evVar);
                    if (a.e == LayerType.Image) {
                        i++;
                    }
                    List<Layer> list = evVar.f;
                    LongSparseArray<Layer> longSparseArray = evVar.e;
                    list.add(a);
                    longSparseArray.put(a.d, a);
                }
                if (i > 4) {
                    StringBuilder sb = new StringBuilder("You have ");
                    sb.append(i);
                    sb.append(" images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
                    evVar.a(sb.toString());
                }
            }
        }

        private static void a(@Nullable JSONArray jSONArray, ev evVar) {
            if (jSONArray != null) {
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    JSONArray optJSONArray = optJSONObject.optJSONArray("layers");
                    if (optJSONArray != null) {
                        ArrayList arrayList = new ArrayList(optJSONArray.length());
                        LongSparseArray longSparseArray = new LongSparseArray();
                        for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                            Layer a = com.airbnb.lottie.model.layer.Layer.a.a(optJSONArray.optJSONObject(i2), evVar);
                            longSparseArray.put(a.d, a);
                            arrayList.add(a);
                        }
                        evVar.a.put(optJSONObject.optString("id"), arrayList);
                    }
                }
            }
        }

        private static void b(@Nullable JSONArray jSONArray, ev evVar) {
            if (jSONArray != null) {
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    if (optJSONObject.has(SuperId.BIT_1_MAIN_VOICE_ASSISTANT)) {
                        ex exVar = new ex(optJSONObject.optInt("w"), optJSONObject.optInt("h"), optJSONObject.optString("id"), optJSONObject.optString(SuperId.BIT_1_MAIN_VOICE_ASSISTANT), optJSONObject.optString(H5Param.URL), 0);
                        evVar.b.put(exVar.a, exVar);
                    }
                }
            }
        }

        private static void b(@Nullable JSONObject jSONObject, ev evVar) {
            if (jSONObject != null) {
                JSONArray optJSONArray = jSONObject.optJSONArray("list");
                if (optJSONArray != null) {
                    int length = optJSONArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        gr grVar = new gr(optJSONObject.optString("fFamily"), optJSONObject.optString("fName"), optJSONObject.optString("fStyle"), (float) optJSONObject.optDouble("ascent"));
                        evVar.c.put(grVar.b, grVar);
                    }
                }
            }
        }

        private static void c(@Nullable JSONArray jSONArray, ev evVar) {
            if (jSONArray != null) {
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    char charAt = optJSONObject.optString(LogItem.MM_C15_K4_C_HEIGHT).charAt(0);
                    int optInt = optJSONObject.optInt("size");
                    double optDouble = optJSONObject.optDouble("w");
                    String optString = optJSONObject.optString(ResUtils.STYLE);
                    String optString2 = optJSONObject.optString("fFamily");
                    JSONObject optJSONObject2 = optJSONObject.optJSONObject("data");
                    List emptyList = Collections.emptyList();
                    if (optJSONObject2 != null) {
                        JSONArray optJSONArray = optJSONObject2.optJSONArray("shapes");
                        if (optJSONArray != null) {
                            emptyList = new ArrayList(optJSONArray.length());
                            for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                                emptyList.add((hv) hv.a(optJSONArray.optJSONObject(i2), evVar));
                            }
                        }
                    }
                    gs gsVar = new gs(emptyList, charAt, optInt, optDouble, optString, optString2);
                    evVar.d.put(gsVar.hashCode(), gsVar);
                }
            }
        }
    }

    /* synthetic */ ev(Rect rect, long j2, long j3, float f2, float f3, int i2, int i3, int i4, byte b2) {
        this(rect, j2, j3, f2, f3, i2, i3, i4);
    }

    private ev(Rect rect, long j2, long j3, float f2, float f3, int i2, int i3, int i4) {
        this.a = new HashMap();
        this.b = new HashMap();
        this.c = new HashMap();
        this.d = new SparseArrayCompat<>();
        this.e = new LongSparseArray<>();
        this.f = new ArrayList();
        this.o = new HashSet<>();
        this.g = new ez();
        this.h = rect;
        this.i = j2;
        this.j = j3;
        this.p = f2;
        this.k = f3;
        this.l = i2;
        this.m = i3;
        this.n = i4;
        if (!ij.a(this, 5)) {
            a((String) "Lottie only supports bodymovin >= 4.5.0");
        }
    }

    public final void a(String str) {
        this.o.add(str);
    }

    public final void a(boolean z) {
        this.g.a = z;
    }

    public final Layer a(long j2) {
        return (Layer) this.e.get(j2);
    }

    public final long a() {
        return (long) ((((float) (this.j - this.i)) / this.p) * 1000.0f);
    }

    public final float b() {
        return (((float) a()) * this.p) / 1000.0f;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LottieComposition:\n");
        for (Layer a2 : this.f) {
            sb.append(a2.a("\t"));
        }
        return sb.toString();
    }
}
