package android.support.v7.graphics;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.os.AsyncTaskCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Palette {
    /* access modifiers changed from: private */
    public static final Filter c = new Filter() {
        public final boolean isAllowed(int i, float[] fArr) {
            if (!(fArr[2] >= 0.95f)) {
                if (!(fArr[2] <= 0.05f)) {
                    if (!(fArr[0] >= 10.0f && fArr[0] <= 37.0f && fArr[1] <= 0.82f)) {
                        return true;
                    }
                }
            }
            return false;
        }
    };
    private final List<Swatch> a;
    private final Generator b;

    public final class Builder {
        private List<Swatch> a;
        private Bitmap b;
        private int c;
        private int d;
        private final List<Filter> e;
        private Generator f;

        private Builder() {
            this.c = 16;
            this.d = 192;
            this.e = new ArrayList();
            this.e.add(Palette.c);
        }

        public Builder(Bitmap bitmap) {
            this();
            if (bitmap == null || bitmap.isRecycled()) {
                throw new IllegalArgumentException("Bitmap is not valid");
            }
            this.b = bitmap;
        }

        public Builder(List<Swatch> list) {
            this();
            if (list == null || list.isEmpty()) {
                throw new IllegalArgumentException("List of Swatches is not valid");
            }
            this.a = list;
        }

        public final Builder addFilter(Filter filter) {
            if (filter != null) {
                this.e.add(filter);
            }
            return this;
        }

        public final Builder clearFilters() {
            this.e.clear();
            return this;
        }

        public final AsyncTask<Bitmap, Void, Palette> generate(final PaletteAsyncListener paletteAsyncListener) {
            if (paletteAsyncListener == null) {
                throw new IllegalArgumentException("listener can not be null");
            }
            return AsyncTaskCompat.executeParallel(new AsyncTask<Bitmap, Void, Palette>() {
                /* access modifiers changed from: protected */
                public Palette doInBackground(Bitmap... bitmapArr) {
                    return Builder.this.generate();
                }

                /* access modifiers changed from: protected */
                public void onPostExecute(Palette palette) {
                    paletteAsyncListener.onGenerated(palette);
                }
            }, this.b);
        }

        public final Palette generate() {
            List<Swatch> list;
            if (this.b == null) {
                list = this.a;
            } else if (this.d <= 0) {
                throw new IllegalArgumentException("Minimum dimension size for resizing should should be >= 1");
            } else {
                Bitmap access$100 = Palette.access$100(this.b, this.d);
                int width = access$100.getWidth();
                int height = access$100.getHeight();
                int[] iArr = new int[(width * height)];
                access$100.getPixels(iArr, 0, width, 0, 0, width, height);
                ColorCutQuantizer colorCutQuantizer = new ColorCutQuantizer(iArr, this.c, this.e.isEmpty() ? null : (Filter[]) this.e.toArray(new Filter[this.e.size()]));
                if (access$100 != this.b) {
                    access$100.recycle();
                }
                list = colorCutQuantizer.getQuantizedColors();
            }
            if (this.f == null) {
                this.f = new DefaultGenerator();
            }
            this.f.generate(list);
            return new Palette(list, this.f);
        }

        /* access modifiers changed from: 0000 */
        public final Builder generator(Generator generator) {
            this.f = generator;
            return this;
        }

        public final Builder maximumColorCount(int i) {
            this.c = i;
            return this;
        }

        public final Builder resizeBitmapSize(int i) {
            this.d = i;
            return this;
        }
    }

    public interface Filter {
        boolean isAllowed(int i, float[] fArr);
    }

    abstract class Generator {
        Generator() {
        }

        public abstract void generate(List<Swatch> list);

        public Swatch getDarkMutedSwatch() {
            return null;
        }

        public Swatch getDarkVibrantSwatch() {
            return null;
        }

        public Swatch getLightMutedSwatch() {
            return null;
        }

        public Swatch getLightVibrantSwatch() {
            return null;
        }

        public Swatch getMutedSwatch() {
            return null;
        }

        public Swatch getVibrantSwatch() {
            return null;
        }
    }

    public interface PaletteAsyncListener {
        void onGenerated(Palette palette);
    }

    public final class Swatch {
        private final int a;
        private final int b;
        private final int c;
        private final int d;
        private final int e;
        private boolean f;
        private int g;
        private int h;
        private float[] i;

        public Swatch(@ColorInt int i2, int i3) {
            this.a = Color.red(i2);
            this.b = Color.green(i2);
            this.c = Color.blue(i2);
            this.d = i2;
            this.e = i3;
        }

        Swatch(int i2, int i3, int i4, int i5) {
            this.a = i2;
            this.b = i3;
            this.c = i4;
            this.d = Color.rgb(i2, i3, i4);
            this.e = i5;
        }

        private void a() {
            if (!this.f) {
                int calculateMinimumAlpha = ColorUtils.calculateMinimumAlpha(-1, this.d, 4.5f);
                int calculateMinimumAlpha2 = ColorUtils.calculateMinimumAlpha(-1, this.d, 3.0f);
                if (calculateMinimumAlpha == -1 || calculateMinimumAlpha2 == -1) {
                    int calculateMinimumAlpha3 = ColorUtils.calculateMinimumAlpha(-16777216, this.d, 4.5f);
                    int calculateMinimumAlpha4 = ColorUtils.calculateMinimumAlpha(-16777216, this.d, 3.0f);
                    if (calculateMinimumAlpha3 == -1 || calculateMinimumAlpha3 == -1) {
                        this.h = calculateMinimumAlpha != -1 ? ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha) : ColorUtils.setAlphaComponent(-16777216, calculateMinimumAlpha3);
                        this.g = calculateMinimumAlpha2 != -1 ? ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha2) : ColorUtils.setAlphaComponent(-16777216, calculateMinimumAlpha4);
                        this.f = true;
                        return;
                    }
                    this.h = ColorUtils.setAlphaComponent(-16777216, calculateMinimumAlpha3);
                    this.g = ColorUtils.setAlphaComponent(-16777216, calculateMinimumAlpha4);
                    this.f = true;
                    return;
                }
                this.h = ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha);
                this.g = ColorUtils.setAlphaComponent(-1, calculateMinimumAlpha2);
                this.f = true;
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Swatch swatch = (Swatch) obj;
            return this.e == swatch.e && this.d == swatch.d;
        }

        @ColorInt
        public final int getBodyTextColor() {
            a();
            return this.h;
        }

        public final float[] getHsl() {
            if (this.i == null) {
                this.i = new float[3];
                ColorUtils.RGBToHSL(this.a, this.b, this.c, this.i);
            }
            return this.i;
        }

        public final int getPopulation() {
            return this.e;
        }

        @ColorInt
        public final int getRgb() {
            return this.d;
        }

        @ColorInt
        public final int getTitleTextColor() {
            a();
            return this.g;
        }

        public final int hashCode() {
            return (this.d * 31) + this.e;
        }

        public final String toString() {
            return new StringBuilder(getClass().getSimpleName()).append(" [RGB: #").append(Integer.toHexString(getRgb())).append(']').append(" [HSL: ").append(Arrays.toString(getHsl())).append(']').append(" [Population: ").append(this.e).append(']').append(" [Title Text: #").append(Integer.toHexString(getTitleTextColor())).append(']').append(" [Body Text: #").append(Integer.toHexString(getBodyTextColor())).append(']').toString();
        }
    }

    private Palette(List<Swatch> list, Generator generator) {
        this.a = list;
        this.b = generator;
    }

    static /* synthetic */ Bitmap access$100(Bitmap bitmap, int i) {
        int max = Math.max(bitmap.getWidth(), bitmap.getHeight());
        if (max <= i) {
            return bitmap;
        }
        float f = ((float) i) / ((float) max);
        return Bitmap.createScaledBitmap(bitmap, Math.round(((float) bitmap.getWidth()) * f), Math.round(f * ((float) bitmap.getHeight())), false);
    }

    public static Builder from(Bitmap bitmap) {
        return new Builder(bitmap);
    }

    public static Palette from(List<Swatch> list) {
        return new Builder(list).generate();
    }

    @Deprecated
    public static Palette generate(Bitmap bitmap) {
        return from(bitmap).generate();
    }

    @Deprecated
    public static Palette generate(Bitmap bitmap, int i) {
        return from(bitmap).maximumColorCount(i).generate();
    }

    @Deprecated
    public static AsyncTask<Bitmap, Void, Palette> generateAsync(Bitmap bitmap, int i, PaletteAsyncListener paletteAsyncListener) {
        return from(bitmap).maximumColorCount(i).generate(paletteAsyncListener);
    }

    @Deprecated
    public static AsyncTask<Bitmap, Void, Palette> generateAsync(Bitmap bitmap, PaletteAsyncListener paletteAsyncListener) {
        return from(bitmap).generate(paletteAsyncListener);
    }

    @ColorInt
    public final int getDarkMutedColor(@ColorInt int i) {
        Swatch darkMutedSwatch = getDarkMutedSwatch();
        return darkMutedSwatch != null ? darkMutedSwatch.getRgb() : i;
    }

    @Nullable
    public final Swatch getDarkMutedSwatch() {
        return this.b.getDarkMutedSwatch();
    }

    @ColorInt
    public final int getDarkVibrantColor(@ColorInt int i) {
        Swatch darkVibrantSwatch = getDarkVibrantSwatch();
        return darkVibrantSwatch != null ? darkVibrantSwatch.getRgb() : i;
    }

    @Nullable
    public final Swatch getDarkVibrantSwatch() {
        return this.b.getDarkVibrantSwatch();
    }

    @ColorInt
    public final int getLightMutedColor(@ColorInt int i) {
        Swatch lightMutedSwatch = getLightMutedSwatch();
        return lightMutedSwatch != null ? lightMutedSwatch.getRgb() : i;
    }

    @Nullable
    public final Swatch getLightMutedSwatch() {
        return this.b.getLightMutedSwatch();
    }

    @ColorInt
    public final int getLightVibrantColor(@ColorInt int i) {
        Swatch lightVibrantSwatch = getLightVibrantSwatch();
        return lightVibrantSwatch != null ? lightVibrantSwatch.getRgb() : i;
    }

    @Nullable
    public final Swatch getLightVibrantSwatch() {
        return this.b.getLightVibrantSwatch();
    }

    @ColorInt
    public final int getMutedColor(@ColorInt int i) {
        Swatch mutedSwatch = getMutedSwatch();
        return mutedSwatch != null ? mutedSwatch.getRgb() : i;
    }

    @Nullable
    public final Swatch getMutedSwatch() {
        return this.b.getMutedSwatch();
    }

    public final List<Swatch> getSwatches() {
        return Collections.unmodifiableList(this.a);
    }

    @ColorInt
    public final int getVibrantColor(@ColorInt int i) {
        Swatch vibrantSwatch = getVibrantSwatch();
        return vibrantSwatch != null ? vibrantSwatch.getRgb() : i;
    }

    @Nullable
    public final Swatch getVibrantSwatch() {
        return this.b.getVibrantSwatch();
    }
}
