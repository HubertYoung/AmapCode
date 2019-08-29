package defpackage;

import android.graphics.Color;
import com.autonavi.common.SuperId;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: gz reason: default package */
/* compiled from: AnimatableGradientColorValue */
public final class gz extends hl<ho, ho> {

    /* renamed from: gz$a */
    /* compiled from: AnimatableGradientColorValue */
    public static final class a {
        public static gz a(JSONObject jSONObject, ev evVar) {
            a a = hk.a(jSONObject, 1.0f, evVar, new b(jSONObject.optInt(SuperId.BIT_1_MAIN_VOICE_ASSISTANT, jSONObject.optJSONArray(SuperId.BIT_1_REALTIMEBUS_BUSSTATION).length() / 4), 0)).a();
            return new gz(a.a, (ho) a.b, 0);
        }
    }

    /* renamed from: gz$b */
    /* compiled from: AnimatableGradientColorValue */
    static class b implements defpackage.hj.a<ho> {
        private final int a;

        /* synthetic */ b(int i, byte b) {
            this(i);
        }

        private b(int i) {
            this.a = i;
        }

        private void a(ho hoVar, JSONArray jSONArray) {
            int i;
            ho hoVar2 = hoVar;
            JSONArray jSONArray2 = jSONArray;
            int i2 = this.a * 4;
            if (jSONArray.length() > i2) {
                int length = (jSONArray.length() - i2) / 2;
                double[] dArr = new double[length];
                double[] dArr2 = new double[length];
                int i3 = 0;
                while (i2 < jSONArray.length()) {
                    if (i2 % 2 == 0) {
                        dArr[i3] = jSONArray2.optDouble(i2);
                    } else {
                        dArr2[i3] = jSONArray2.optDouble(i2);
                        i3++;
                    }
                    i2++;
                }
                for (int i4 = 0; i4 < hoVar2.b.length; i4++) {
                    int i5 = hoVar2.b[i4];
                    double d = (double) hoVar2.a[i4];
                    int i6 = 1;
                    while (true) {
                        if (i6 >= length) {
                            i = (int) (dArr2[length - 1] * 255.0d);
                            break;
                        }
                        int i7 = i6 - 1;
                        double d2 = dArr[i7];
                        double d3 = dArr[i6];
                        if (dArr[i6] >= d) {
                            double d4 = dArr2[i7];
                            i = (int) ((d4 + (((d - d2) / (d3 - d2)) * (dArr2[i6] - d4))) * 255.0d);
                            break;
                        }
                        i6++;
                    }
                    hoVar2.b[i4] = Color.argb(i, Color.red(i5), Color.green(i5), Color.blue(i5));
                }
            }
        }

        public final /* synthetic */ Object a(Object obj, float f) {
            JSONArray jSONArray = (JSONArray) obj;
            float[] fArr = new float[this.a];
            int[] iArr = new int[this.a];
            ho hoVar = new ho(fArr, iArr);
            if (jSONArray.length() != this.a * 4) {
                StringBuilder sb = new StringBuilder("Unexpected gradient length: ");
                sb.append(jSONArray.length());
                sb.append(". Expected ");
                sb.append(this.a * 4);
                sb.append(". This may affect the appearance of the gradient. Make sure to save your After Effects file before exporting an animation with gradients.");
            }
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < this.a * 4; i3++) {
                int i4 = i3 / 4;
                double optDouble = jSONArray.optDouble(i3);
                switch (i3 % 4) {
                    case 0:
                        fArr[i4] = (float) optDouble;
                        break;
                    case 1:
                        i = (int) (optDouble * 255.0d);
                        break;
                    case 2:
                        i2 = (int) (optDouble * 255.0d);
                        break;
                    case 3:
                        iArr[i4] = Color.argb(255, i, i2, (int) (optDouble * 255.0d));
                        break;
                }
            }
            a(hoVar, jSONArray);
            return hoVar;
        }
    }

    /* synthetic */ gz(List list, ho hoVar, byte b2) {
        this(list, hoVar);
    }

    private gz(List<fc<ho>> list, ho hoVar) {
        super(list, hoVar);
    }

    public final fu<ho, ho> a() {
        if (!d()) {
            return new gh(this.b);
        }
        return new fx(this.a);
    }
}
