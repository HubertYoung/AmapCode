package android.support.v7.graphics;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.graphics.Palette.Filter;
import android.support.v7.graphics.Palette.Swatch;
import android.util.TimingLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

final class ColorCutQuantizer {
    private static final Comparator<Vbox> b = new Comparator<Vbox>() {
        public final int compare(Vbox vbox, Vbox vbox2) {
            return vbox2.getVolume() - vbox.getVolume();
        }
    };
    private final float[] a = new float[3];
    final int[] mColors;
    final Filter[] mFilters;
    final int[] mHistogram;
    final List<Swatch> mQuantizedColors;
    final TimingLogger mTimingLogger = null;

    class Vbox {
        private int a;
        private int b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;

        Vbox(int i2, int i3) {
            this.a = i2;
            this.b = i3;
            fitBox();
        }

        /* access modifiers changed from: 0000 */
        public final boolean canSplit() {
            return getColorCount() > 1;
        }

        /* access modifiers changed from: 0000 */
        public final int findSplitPoint() {
            int longestColorDimension = getLongestColorDimension();
            int[] iArr = ColorCutQuantizer.this.mColors;
            int[] iArr2 = ColorCutQuantizer.this.mHistogram;
            ColorCutQuantizer.access$300(iArr, longestColorDimension, this.a, this.b);
            Arrays.sort(iArr, this.a, this.b + 1);
            ColorCutQuantizer.access$300(iArr, longestColorDimension, this.a, this.b);
            int i2 = this.c / 2;
            int i3 = 0;
            for (int i4 = this.a; i4 <= this.b; i4++) {
                i3 += iArr2[iArr[i4]];
                if (i3 >= i2) {
                    return i4;
                }
            }
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public final void fitBox() {
            int[] iArr = ColorCutQuantizer.this.mColors;
            int[] iArr2 = ColorCutQuantizer.this.mHistogram;
            int i2 = Integer.MIN_VALUE;
            int i3 = 0;
            int i4 = Integer.MIN_VALUE;
            int i5 = Integer.MAX_VALUE;
            int i6 = Integer.MAX_VALUE;
            int i7 = Integer.MAX_VALUE;
            int i8 = Integer.MIN_VALUE;
            for (int i9 = this.a; i9 <= this.b; i9++) {
                int i10 = iArr[i9];
                i3 += iArr2[i10];
                int access$000 = ColorCutQuantizer.access$000(i10);
                int access$100 = ColorCutQuantizer.access$100(i10);
                int access$200 = ColorCutQuantizer.access$200(i10);
                if (access$000 > i4) {
                    i4 = access$000;
                }
                if (access$000 < i7) {
                    i7 = access$000;
                }
                if (access$100 > i8) {
                    i8 = access$100;
                }
                if (access$100 < i6) {
                    i6 = access$100;
                }
                if (access$200 > i2) {
                    i2 = access$200;
                }
                if (access$200 < i5) {
                    i5 = access$200;
                }
            }
            this.d = i7;
            this.e = i4;
            this.f = i6;
            this.g = i8;
            this.h = i5;
            this.i = i2;
            this.c = i3;
        }

        /* access modifiers changed from: 0000 */
        public final Swatch getAverageColor() {
            int i2 = 0;
            int[] iArr = ColorCutQuantizer.this.mColors;
            int[] iArr2 = ColorCutQuantizer.this.mHistogram;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            for (int i6 = this.a; i6 <= this.b; i6++) {
                int i7 = iArr[i6];
                int i8 = iArr2[i7];
                i2 += i8;
                i5 += ColorCutQuantizer.access$000(i7) * i8;
                i4 += ColorCutQuantizer.access$100(i7) * i8;
                i3 += ColorCutQuantizer.access$200(i7) * i8;
            }
            return new Swatch(ColorCutQuantizer.a(Math.round(((float) i5) / ((float) i2)), Math.round(((float) i4) / ((float) i2)), Math.round(((float) i3) / ((float) i2))), i2);
        }

        /* access modifiers changed from: 0000 */
        public final int getColorCount() {
            return (this.b + 1) - this.a;
        }

        /* access modifiers changed from: 0000 */
        public final int getLongestColorDimension() {
            int i2 = this.e - this.d;
            int i3 = this.g - this.f;
            int i4 = this.i - this.h;
            if (i2 < i3 || i2 < i4) {
                return (i3 < i2 || i3 < i4) ? -1 : -2;
            }
            return -3;
        }

        /* access modifiers changed from: 0000 */
        public final int getVolume() {
            return ((this.e - this.d) + 1) * ((this.g - this.f) + 1) * ((this.i - this.h) + 1);
        }

        /* access modifiers changed from: 0000 */
        public final Vbox splitBox() {
            if (!canSplit()) {
                throw new IllegalStateException("Can not split a box with only 1 color");
            }
            int findSplitPoint = findSplitPoint();
            Vbox vbox = new Vbox(findSplitPoint + 1, this.b);
            this.b = findSplitPoint;
            fitBox();
            return vbox;
        }
    }

    ColorCutQuantizer(int[] iArr, int i, Filter[] filterArr) {
        int i2;
        this.mFilters = filterArr;
        int[] iArr2 = new int[32768];
        this.mHistogram = iArr2;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            int b2 = b(Color.blue(i4), 8, 5) | (b(Color.red(i4), 8, 5) << 10) | (b(Color.green(i4), 8, 5) << 5);
            iArr[i3] = b2;
            iArr2[b2] = iArr2[b2] + 1;
        }
        int i5 = 0;
        for (int i6 = 0; i6 < 32768; i6++) {
            if (iArr2[i6] > 0) {
                int a2 = a(i6);
                ColorUtils.colorToHSL(a2, this.a);
                if (a(a2, this.a)) {
                    iArr2[i6] = 0;
                }
            }
            if (iArr2[i6] > 0) {
                i5++;
            }
        }
        int[] iArr3 = new int[i5];
        this.mColors = iArr3;
        int i7 = 0;
        int i8 = 0;
        while (i7 < 32768) {
            if (iArr2[i7] > 0) {
                i2 = i8 + 1;
                iArr3[i8] = i7;
            } else {
                i2 = i8;
            }
            i7++;
            i8 = i2;
        }
        if (i5 <= i) {
            this.mQuantizedColors = new ArrayList();
            for (int i9 : iArr3) {
                this.mQuantizedColors.add(new Swatch(a(i9), iArr2[i9]));
            }
            return;
        }
        PriorityQueue priorityQueue = new PriorityQueue(i, b);
        priorityQueue.offer(new Vbox(0, this.mColors.length - 1));
        a(priorityQueue, i);
        this.mQuantizedColors = a((Collection<Vbox>) priorityQueue);
    }

    private static int a(int i) {
        return a((i >> 10) & 31, (i >> 5) & 31, i & 31);
    }

    /* access modifiers changed from: private */
    public static int a(int i, int i2, int i3) {
        return Color.rgb(b(i, 5, 8), b(i2, 5, 8), b(i3, 5, 8));
    }

    private List<Swatch> a(Collection<Vbox> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (Vbox averageColor : collection) {
            Swatch averageColor2 = averageColor.getAverageColor();
            if (!a(averageColor2.getRgb(), averageColor2.getHsl())) {
                arrayList.add(averageColor2);
            }
        }
        return arrayList;
    }

    private static void a(PriorityQueue<Vbox> priorityQueue, int i) {
        while (priorityQueue.size() < i) {
            Vbox poll = priorityQueue.poll();
            if (poll != null && poll.canSplit()) {
                priorityQueue.offer(poll.splitBox());
                priorityQueue.offer(poll);
            } else {
                return;
            }
        }
    }

    private boolean a(int i, float[] fArr) {
        if (this.mFilters == null || this.mFilters.length <= 0) {
            return false;
        }
        for (Filter isAllowed : this.mFilters) {
            if (!isAllowed.isAllowed(i, fArr)) {
                return true;
            }
        }
        return false;
    }

    static /* synthetic */ int access$000(int i) {
        return (i >> 10) & 31;
    }

    static /* synthetic */ int access$100(int i) {
        return (i >> 5) & 31;
    }

    static /* synthetic */ int access$200(int i) {
        return i & 31;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0004, code lost:
        if (r5 > r6) goto L_0x0003;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r3[r5];
        r3[r5] = (r0 & 31) | ((((r0 >> 5) & 31) << 10) | (((r0 >> 10) & 31) << 5));
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        if (r5 > r6) goto L_0x0003;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x001f, code lost:
        r0 = r3[r5];
        r3[r5] = ((r0 >> 10) & 31) | (((r0 & 31) << 10) | (((r0 >> 5) & 31) << 5));
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void access$300(int[] r3, int r4, int r5, int r6) {
        /*
            switch(r4) {
                case -3: goto L_0x0003;
                case -2: goto L_0x0004;
                case -1: goto L_0x001d;
                default: goto L_0x0003;
            }
        L_0x0003:
            return
        L_0x0004:
            if (r5 > r6) goto L_0x0003
            r0 = r3[r5]
            int r1 = r0 >> 5
            r1 = r1 & 31
            int r1 = r1 << 10
            int r2 = r0 >> 10
            r2 = r2 & 31
            int r2 = r2 << 5
            r1 = r1 | r2
            r0 = r0 & 31
            r0 = r0 | r1
            r3[r5] = r0
            int r5 = r5 + 1
            goto L_0x0004
        L_0x001d:
            if (r5 > r6) goto L_0x0003
            r0 = r3[r5]
            r1 = r0 & 31
            int r1 = r1 << 10
            int r2 = r0 >> 5
            r2 = r2 & 31
            int r2 = r2 << 5
            r1 = r1 | r2
            int r0 = r0 >> 10
            r0 = r0 & 31
            r0 = r0 | r1
            r3[r5] = r0
            int r5 = r5 + 1
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.graphics.ColorCutQuantizer.access$300(int[], int, int, int):void");
    }

    private static int b(int i, int i2, int i3) {
        return (i3 > i2 ? i << (i3 - i2) : i >> (i2 - i3)) & ((1 << i3) - 1);
    }

    /* access modifiers changed from: 0000 */
    public final List<Swatch> getQuantizedColors() {
        return this.mQuantizedColors;
    }
}
