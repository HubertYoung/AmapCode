package com.autonavi.minimap.ajx3.layout;

import android.graphics.Paint.FontMetricsInt;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.text.style.LineHeightSpan;
import android.view.View;

public class TextLayoutBuilder {
    private static final int DEFAULT_MAX_LINES = Integer.MAX_VALUE;
    public static final int MEASURE_MODE_AT_MOST = 2;
    public static final int MEASURE_MODE_EXACTLY = 1;
    public static final int MEASURE_MODE_UNSPECIFIED = 0;
    private Params mParams = new Params();

    static class CssLineHeightSpan implements LineHeightSpan {
        private final boolean isMultLineHeight;
        private final float lineHeight;
        private int targetBottom = Integer.MIN_VALUE;
        private int targetTop = Integer.MIN_VALUE;

        CssLineHeightSpan(boolean z, float f) {
            this.isMultLineHeight = z;
            this.lineHeight = f;
        }

        public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, FontMetricsInt fontMetricsInt) {
            float f;
            if (!isSetLineHeight(fontMetricsInt.top, fontMetricsInt.bottom)) {
                if (this.targetTop == Integer.MIN_VALUE) {
                    float f2 = (float) (fontMetricsInt.descent - fontMetricsInt.ascent);
                    float f3 = (f2 / 2.0f) - ((float) fontMetricsInt.descent);
                    if (this.isMultLineHeight) {
                        f = this.lineHeight * f2;
                    } else {
                        f = this.lineHeight;
                    }
                    this.targetBottom = (int) Math.ceil((double) ((f / 2.0f) - f3));
                    this.targetTop = (int) Math.ceil((double) (((float) this.targetBottom) - f));
                }
                int i5 = this.targetBottom;
                fontMetricsInt.descent = i5;
                fontMetricsInt.bottom = i5;
                int i6 = this.targetTop;
                fontMetricsInt.ascent = i6;
                fontMetricsInt.top = i6;
            }
        }

        private boolean isSetLineHeight(int i, int i2) {
            return i == this.targetTop && i2 == this.targetBottom;
        }
    }

    static class Params {
        Alignment alignment;
        TruncateAt ellipsize;
        boolean includePadding;
        boolean isMultLineHeight;
        boolean isRichText;
        float lineHeight;
        int maxLines;
        int measureMode;
        TextPaint paint;
        boolean singleLine;
        float spacingAdd;
        float spacingMult;
        String text;
        TextDirectionHeuristic textDirection;
        View view;
        int width;

        private Params() {
            this.paint = new TextPaint(1);
            this.spacingMult = 1.0f;
            this.spacingAdd = 0.0f;
            this.includePadding = true;
            this.ellipsize = null;
            this.singleLine = false;
            this.maxLines = Integer.MAX_VALUE;
            this.alignment = Alignment.ALIGN_NORMAL;
            this.textDirection = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            this.isRichText = false;
            this.lineHeight = 1.0f;
            this.isMultLineHeight = true;
        }

        /* access modifiers changed from: 0000 */
        public int getLineHeight() {
            return Math.round((((float) this.paint.getFontMetricsInt(null)) * this.spacingMult) + this.spacingAdd);
        }
    }

    public TextLayoutBuilder setText(String str) {
        this.mParams.text = str;
        return this;
    }

    public TextLayoutBuilder setView(View view) {
        this.mParams.view = view;
        return this;
    }

    public TextLayoutBuilder setWidth(int i) {
        return setWidth(i, i <= 0 ? 0 : 1);
    }

    public TextLayoutBuilder setWidth(int i, int i2) {
        this.mParams.width = i;
        this.mParams.measureMode = i2;
        return this;
    }

    public TextLayoutBuilder setMaxLines(int i) {
        this.mParams.maxLines = i;
        return this;
    }

    public float getTextSpacingAdd() {
        return this.mParams.spacingAdd;
    }

    public TextLayoutBuilder setTextSpacingAdd(float f) {
        this.mParams.spacingAdd = f;
        return this;
    }

    public float getTextSpacingMultiplier() {
        return this.mParams.spacingMult;
    }

    public TextLayoutBuilder setTextSpacingMultiplier(float f) {
        this.mParams.spacingMult = f;
        return this;
    }

    public boolean getIncludeFontPadding() {
        return this.mParams.includePadding;
    }

    public TextLayoutBuilder setLayoutAlignment(Alignment alignment) {
        this.mParams.alignment = alignment;
        return this;
    }

    public TextLayoutBuilder setIncludeFontPadding(boolean z) {
        this.mParams.includePadding = z;
        return this;
    }

    public TextLayoutBuilder setTextSize(int i) {
        this.mParams.paint.setTextSize((float) i);
        return this;
    }

    public TextLayoutBuilder setTypeface(int i) {
        setTypeface(Typeface.defaultFromStyle(i));
        return this;
    }

    public TextLayoutBuilder setTypeface(Typeface typeface) {
        this.mParams.paint.setTypeface(typeface);
        return this;
    }

    public TextLayoutBuilder setIsRichText(boolean z) {
        this.mParams.isRichText = z;
        return this;
    }

    public TextLayoutBuilder setLineHeight(float f, boolean z) {
        this.mParams.lineHeight = f;
        this.mParams.isMultLineHeight = z;
        return this;
    }

    public TextLayoutBuilder setEllipsize(TruncateAt truncateAt) {
        this.mParams.ellipsize = truncateAt;
        return this;
    }

    /* JADX WARNING: type inference failed for: r8v0, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r3v3, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r4v14, types: [java.lang.CharSequence] */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r3v15 */
    /* JADX WARNING: type inference failed for: r8v2, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r8v3, types: [android.text.SpannableStringBuilder] */
    /* JADX WARNING: type inference failed for: r8v9, types: [com.autonavi.minimap.ajx3.htmcompat.AjxSpannableStringBuilder] */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r8v11 */
    /* JADX WARNING: type inference failed for: r8v12 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v1
      assigns: []
      uses: []
      mth insns count: 161
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.text.Layout build() {
        /*
            r19 = this;
            r1 = r19
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            java.lang.String r2 = r2.text
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x000e
            r2 = 0
            return r2
        L_0x000e:
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            boolean r2 = r2.singleLine
            r3 = 1
            if (r2 == 0) goto L_0x0017
            r2 = 1
            goto L_0x001b
        L_0x0017:
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            int r2 = r2.maxLines
        L_0x001b:
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams
            boolean r4 = r4.isMultLineHeight
            r5 = 0
            if (r4 == 0) goto L_0x0030
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams
            float r4 = r4.lineHeight
            r6 = 1065353216(0x3f800000, float:1.0)
            boolean r4 = com.autonavi.minimap.ajx3.util.ComputeUtils.floatsEqual(r4, r6)
            if (r4 == 0) goto L_0x0030
            r4 = 1
            goto L_0x0031
        L_0x0030:
            r4 = 0
        L_0x0031:
            if (r2 != r3) goto L_0x003d
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r6 = r1.mParams
            boolean r6 = r6.isRichText
            if (r6 != 0) goto L_0x003d
            if (r4 == 0) goto L_0x003d
            r6 = 1
            goto L_0x003e
        L_0x003d:
            r6 = 0
        L_0x003e:
            if (r6 == 0) goto L_0x0079
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r6 = r1.mParams
            java.lang.String r6 = r6.text
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r7 = r1.mParams
            android.text.TextPaint r7 = r7.paint
            android.text.BoringLayout$Metrics r14 = android.text.BoringLayout.isBoring(r6, r7)
            if (r14 == 0) goto L_0x0079
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            java.lang.String r8 = r2.text
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            int r17 = r1.getDesiredWidth(r8, r2)
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            android.text.TextPaint r9 = r2.paint
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            android.text.Layout$Alignment r11 = r2.alignment
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            float r12 = r2.spacingMult
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            float r13 = r2.spacingAdd
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            boolean r15 = r2.includePadding
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r2 = r1.mParams
            android.text.TextUtils$TruncateAt r2 = r2.ellipsize
            r10 = r17
            r16 = r2
            android.text.BoringLayout r2 = android.text.BoringLayout.make(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            return r2
        L_0x0079:
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r6 = r1.mParams
            boolean r6 = r6.isRichText
            r7 = 33
            if (r6 == 0) goto L_0x00b4
            com.autonavi.minimap.ajx3.htmcompat.AjxImageGetter r6 = new com.autonavi.minimap.ajx3.htmcompat.AjxImageGetter
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r8 = r1.mParams
            android.view.View r8 = r8.view
            r6.<init>(r8)
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r8 = r1.mParams
            java.lang.String r8 = r8.text
            android.text.Spanned r8 = com.autonavi.minimap.ajx3.htmcompat.HtmlParser.fromHtml(r8, r6)
            com.autonavi.minimap.ajx3.htmcompat.AjxSpannableStringBuilder r8 = (com.autonavi.minimap.ajx3.htmcompat.AjxSpannableStringBuilder) r8
            boolean r6 = r6.isDirty()
            if (r6 == 0) goto L_0x009d
            r8.setWithImgSpan(r3)
        L_0x009d:
            if (r4 != 0) goto L_0x00d8
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$CssLineHeightSpan r4 = new com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$CssLineHeightSpan
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r6 = r1.mParams
            boolean r6 = r6.isMultLineHeight
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r9 = r1.mParams
            float r9 = r9.lineHeight
            r4.<init>(r6, r9)
            int r6 = r8.length()
            r8.setSpan(r4, r5, r6, r7)
            goto L_0x00d8
        L_0x00b4:
            if (r4 != 0) goto L_0x00d4
            android.text.SpannableStringBuilder r8 = new android.text.SpannableStringBuilder
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams
            java.lang.String r4 = r4.text
            r8.<init>(r4)
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$CssLineHeightSpan r4 = new com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$CssLineHeightSpan
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r6 = r1.mParams
            boolean r6 = r6.isMultLineHeight
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r9 = r1.mParams
            float r9 = r9.lineHeight
            r4.<init>(r6, r9)
            int r6 = r8.length()
            r8.setSpan(r4, r5, r6, r7)
            goto L_0x00d8
        L_0x00d4:
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams
            java.lang.String r8 = r4.text
        L_0x00d8:
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams
            int r17 = r1.getDesiredWidth(r8, r4)
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams
            int r4 = r4.maxLines
            if (r4 != r3) goto L_0x0130
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r3 = r1.mParams
            android.text.TextUtils$TruncateAt r3 = r3.ellipsize
            if (r3 == 0) goto L_0x0130
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r3 = r1.mParams
            android.text.TextPaint r3 = r3.paint
            float r3 = android.text.Layout.getDesiredWidth(r8, r3)
            double r3 = (double) r3
            double r3 = java.lang.Math.ceil(r3)
            int r3 = (int) r3
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams
            int r4 = r4.width
            if (r4 > r3) goto L_0x010d
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams
            android.text.Layout$Alignment r4 = r4.alignment
            android.text.Layout$Alignment r5 = android.text.Layout.Alignment.ALIGN_CENTER
            if (r4 != r5) goto L_0x010f
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams
            android.text.Layout$Alignment r5 = android.text.Layout.Alignment.ALIGN_NORMAL
            r4.alignment = r5
            goto L_0x010f
        L_0x010d:
            r3 = r17
        L_0x010f:
            java.lang.String r4 = "…"
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r5 = r1.mParams
            android.text.TextPaint r5 = r5.paint
            float r4 = android.text.Layout.getDesiredWidth(r4, r5)
            double r4 = (double) r4
            double r4 = java.lang.Math.ceil(r4)
            int r4 = (int) r4
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r5 = r1.mParams
            int r5 = r5.width
            if (r4 <= r5) goto L_0x012c
            java.lang.String r4 = ""
            r18 = r3
            r3 = r4
            goto L_0x0133
        L_0x012c:
            r18 = r3
            r3 = r8
            goto L_0x0133
        L_0x0130:
            r3 = r8
            r18 = r17
        L_0x0133:
            r5 = 0
            int r6 = r3.length()     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            android.text.TextPaint r7 = r4.paint     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            android.text.Layout$Alignment r9 = r4.alignment     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            android.text.TextDirectionHeuristic r10 = r4.textDirection     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            float r11 = r4.spacingMult     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            float r12 = r4.spacingAdd     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            boolean r13 = r4.includePadding     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            com.autonavi.minimap.ajx3.layout.TextLayoutBuilder$Params r4 = r1.mParams     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            android.text.TextUtils$TruncateAt r14 = r4.ellipsize     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            r4 = r3
            r8 = r18
            r15 = r17
            r16 = r2
            android.text.StaticLayout r4 = com.autonavi.minimap.ajx3.layout.AjxStaticLayout.create(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch:{ IndexOutOfBoundsException -> 0x0160 }
            return r4
        L_0x0160:
            r0 = move-exception
            r4 = r0
            boolean r5 = r3 instanceof java.lang.String
            if (r5 != 0) goto L_0x016b
            java.lang.String r3 = r3.toString()
            goto L_0x0133
        L_0x016b:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.layout.TextLayoutBuilder.build():android.text.Layout");
    }

    private int getDesiredWidth(CharSequence charSequence, Params params) {
        switch (params.measureMode) {
            case 0:
                return (int) Math.ceil((double) Layout.getDesiredWidth(charSequence, params.paint));
            case 1:
                return params.width;
            case 2:
                return Math.min((int) Math.ceil((double) Layout.getDesiredWidth(charSequence, params.paint)), params.width);
            default:
                StringBuilder sb = new StringBuilder("Unexpected measure mode ");
                sb.append(params.measureMode);
                throw new IllegalStateException(sb.toString());
        }
    }
}
