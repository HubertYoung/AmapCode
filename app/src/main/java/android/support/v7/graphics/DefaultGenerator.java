package android.support.v7.graphics;

import android.support.v4.graphics.ColorUtils;
import android.support.v7.graphics.Palette.Swatch;
import java.util.Iterator;
import java.util.List;

class DefaultGenerator extends Generator {
    private List<Swatch> a;
    private int b;
    private Swatch c;
    private Swatch d;
    private Swatch e;
    private Swatch f;
    private Swatch g;
    private Swatch h;

    DefaultGenerator() {
    }

    private static float a(float f2, float f3) {
        return 1.0f - Math.abs(f2 - f3);
    }

    private Swatch a(float f2, float f3, float f4, float f5, float f6, float f7) {
        float f8;
        Swatch swatch;
        Swatch swatch2 = null;
        float f9 = 0.0f;
        for (Swatch next : this.a) {
            float f10 = next.getHsl()[1];
            float f11 = next.getHsl()[2];
            if (f10 >= f6 && f10 <= f7 && f11 >= f3 && f11 <= f4) {
                if (!(this.c == next || this.e == next || this.g == next || this.d == next || this.f == next || this.h == next)) {
                    float[] fArr = {a(f10, f5), 3.0f, a(f11, f2), 6.0f, ((float) next.getPopulation()) / ((float) this.b), 1.0f};
                    float f12 = 0.0f;
                    float f13 = 0.0f;
                    for (int i = 0; i < 6; i += 2) {
                        float f14 = fArr[i];
                        float f15 = fArr[i + 1];
                        f12 += f14 * f15;
                        f13 += f15;
                    }
                    float f16 = f12 / f13;
                    if (swatch2 == null || f16 > f9) {
                        swatch = next;
                        f8 = f16;
                        swatch2 = swatch;
                        f9 = f8;
                    }
                }
            }
            f8 = f9;
            swatch = swatch2;
            swatch2 = swatch;
            f9 = f8;
        }
        return swatch2;
    }

    private static float[] a(Swatch swatch) {
        float[] fArr = new float[3];
        System.arraycopy(swatch.getHsl(), 0, fArr, 0, 3);
        return fArr;
    }

    public void generate(List<Swatch> list) {
        int i;
        this.a = list;
        int i2 = 0;
        Iterator<Swatch> it = this.a.iterator();
        while (true) {
            i = i2;
            if (!it.hasNext()) {
                break;
            }
            i2 = Math.max(i, it.next().getPopulation());
        }
        this.b = i;
        this.c = a(0.5f, 0.3f, 0.7f, 1.0f, 0.35f, 1.0f);
        this.g = a(0.74f, 0.55f, 1.0f, 1.0f, 0.35f, 1.0f);
        this.e = a(0.26f, 0.0f, 0.45f, 1.0f, 0.35f, 1.0f);
        this.d = a(0.5f, 0.3f, 0.7f, 0.3f, 0.0f, 0.4f);
        this.h = a(0.74f, 0.55f, 1.0f, 0.3f, 0.0f, 0.4f);
        this.f = a(0.26f, 0.0f, 0.45f, 0.3f, 0.0f, 0.4f);
        if (this.c == null && this.e != null) {
            float[] a2 = a(this.e);
            a2[2] = 0.5f;
            this.c = new Swatch(ColorUtils.HSLToColor(a2), 0);
        }
        if (this.e == null && this.c != null) {
            float[] a3 = a(this.c);
            a3[2] = 0.26f;
            this.e = new Swatch(ColorUtils.HSLToColor(a3), 0);
        }
    }

    public Swatch getDarkMutedSwatch() {
        return this.f;
    }

    public Swatch getDarkVibrantSwatch() {
        return this.e;
    }

    public Swatch getLightMutedSwatch() {
        return this.h;
    }

    public Swatch getLightVibrantSwatch() {
        return this.g;
    }

    public Swatch getMutedSwatch() {
        return this.d;
    }

    public Swatch getVibrantSwatch() {
        return this.c;
    }
}
