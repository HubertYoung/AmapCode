package com.autonavi.minimap.ajx3.widget.property;

import android.support.annotation.NonNull;
import android.text.Layout.Alignment;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.view.Label;

public class LabelProperty extends BaseProperty<Label> {
    private boolean mHasAdditionalStroke = false;
    private int mStrokeColor = -1;
    private float mStrokeWidth = (DimensionUtils.getDensisty() * 1.0f);

    public LabelProperty(@NonNull Label label, @NonNull IAjxContext iAjxContext) {
        super(label, iAjxContext);
    }

    /* access modifiers changed from: protected */
    public void updateStyle(int i, Object obj, boolean z) {
        switch (i) {
            case Property.NODE_PROPERTY_FLEX_PADDING /*1056964650*/:
            case Property.NODE_PROPERTY_FLEX_PADDING_LEFT /*1056964651*/:
            case Property.NODE_PROPERTY_FLEX_PADDING_RIGHT /*1056964652*/:
            case Property.NODE_PROPERTY_FLEX_PADDING_TOP /*1056964653*/:
            case Property.NODE_PROPERTY_FLEX_PADDING_BOTTOM /*1056964654*/:
                updatePadding(obj);
                return;
            case Property.NODE_PROPERTY_LINE_CLAMP /*1056964656*/:
                updateLineClamp(obj);
                return;
            case Property.NODE_PROPERTY_LINE_HEIGHT /*1056964657*/:
                updateLineHeight(obj);
                return;
            case Property.NODE_PROPERTY_FONT_SIZE /*1056964659*/:
                updateFontSize(obj);
                return;
            case Property.NODE_PROPERTY_FONT_WEIGHT /*1056964660*/:
                updateFontWeight(obj);
                return;
            case Property.NODE_PROPERTY_FONT_STYLE /*1056964661*/:
                updateFontStyle(obj);
                return;
            case Property.NODE_PROPERTY_FONT_FAMILY /*1056964662*/:
                updateFontFamily(obj);
                return;
            case Property.NODE_PROPERTY_COLOR /*1056964667*/:
                updateColor(obj);
                return;
            case Property.NODE_PROPERTY_TEXT_ALIGN /*1056964668*/:
                updateTextAlign(obj);
                return;
            case Property.NODE_PROPERTY_TEXT_OVERFLOW /*1056964669*/:
                updateTextOverflow(obj);
                return;
            case Property.NODE_PROPERTY_TEXT_DECORATION /*1056964670*/:
                updateTextDecoration(obj);
                return;
            default:
                super.updateStyle(i, obj, z);
                return;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0066  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r5, java.lang.Object r6) {
        /*
            r4 = this;
            int r0 = r5.hashCode()
            r1 = -891980232(0xffffffffcad57638, float:-6994716.0)
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L_0x003c
            r1 = 3556653(0x36452d, float:4.983932E-39)
            if (r0 == r1) goto L_0x0031
            r1 = 1935334443(0x735adc2b, float:1.7339878E31)
            if (r0 == r1) goto L_0x0026
            r1 = 1953618574(0x7471da8e, float:7.6646506E31)
            if (r0 == r1) goto L_0x001b
            goto L_0x0047
        L_0x001b:
            java.lang.String r0 = "strokewidth"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0047
            r0 = 2
            goto L_0x0048
        L_0x0026:
            java.lang.String r0 = "strokecolor"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0047
            r0 = 3
            goto L_0x0048
        L_0x0031:
            java.lang.String r0 = "text"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0047
            r0 = 0
            goto L_0x0048
        L_0x003c:
            java.lang.String r0 = "stroke"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0047
            r0 = 1
            goto L_0x0048
        L_0x0047:
            r0 = -1
        L_0x0048:
            switch(r0) {
                case 0: goto L_0x0066;
                case 1: goto L_0x0054;
                case 2: goto L_0x0050;
                case 3: goto L_0x004c;
                default: goto L_0x004b;
            }
        L_0x004b:
            goto L_0x006a
        L_0x004c:
            r4.updateAdditionalStrokeColor(r6)
            goto L_0x006a
        L_0x0050:
            r4.updateAdditionalStrokeWidth(r6)
            goto L_0x006a
        L_0x0054:
            boolean r0 = r6 instanceof java.lang.String
            if (r0 == 0) goto L_0x0062
            r0 = r6
            java.lang.String r0 = (java.lang.String) r0
            boolean r0 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r0)
            if (r0 == 0) goto L_0x0062
            goto L_0x0063
        L_0x0062:
            r2 = 0
        L_0x0063:
            r4.mHasAdditionalStroke = r2
            goto L_0x006a
        L_0x0066:
            r4.updateText(r6)
            return
        L_0x006a:
            super.updateAttribute(r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.LabelProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateAdditionalStrokeWidth(Object obj) {
        if (obj instanceof Float) {
            this.mStrokeWidth = DimensionUtils.standardUnitToPixelPrecise(((Float) obj).floatValue());
            return;
        }
        if (obj instanceof String) {
            try {
                this.mStrokeWidth = DimensionUtils.standardUnitToPixelPrecise(Float.parseFloat((String) obj));
            } catch (Exception unused) {
                this.mStrokeWidth = DimensionUtils.getDensisty() * 1.0f;
            }
        }
    }

    private void updateAdditionalStrokeColor(Object obj) {
        if (obj instanceof String) {
            this.mStrokeColor = StringUtils.parseColorByAnimator((String) obj);
        }
    }

    public boolean hasAdditionalStroke() {
        return this.mHasAdditionalStroke;
    }

    public float getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public int getStrokeColor() {
        return this.mStrokeColor;
    }

    /* access modifiers changed from: protected */
    public void updateText(Object obj) {
        if (obj == null) {
            ((Label) this.mView).setText(null);
        } else {
            ((Label) this.mView).setText((String) obj);
        }
    }

    /* access modifiers changed from: protected */
    public void updateFontSize(Object obj) {
        if (obj == null) {
            ((Label) this.mView).setFontSize(Label.DEFAULT_FONT_SIZE);
            return;
        }
        ((Label) this.mView).setFontSize(DimensionUtils.standardUnitToPixel((float) ((Integer) obj).intValue()));
    }

    /* access modifiers changed from: protected */
    public void updateLineClamp(Object obj) {
        if (obj == null) {
            ((Label) this.mView).setLineClamp(Integer.MAX_VALUE);
            return;
        }
        int intValue = ((Integer) obj).intValue();
        if (intValue <= 0) {
            intValue = Integer.MAX_VALUE;
        }
        ((Label) this.mView).setLineClamp(intValue);
    }

    /* access modifiers changed from: protected */
    public void updateLineHeight(Object obj) {
        float f;
        boolean z;
        if (obj == null) {
            ((Label) this.mView).setLineHeight(1.0f, true);
        } else if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            if (objArr.length == 2) {
                z = ((Boolean) objArr[0]).booleanValue();
                f = ((Float) objArr[1]).floatValue();
            } else {
                f = 1.0f;
                z = true;
            }
            if (!z) {
                f = (float) DimensionUtils.standardUnitToPixel(f);
            }
            ((Label) this.mView).setLineHeight(f, z);
        }
    }

    /* access modifiers changed from: protected */
    public void updateTextOverflow(Object obj) {
        int i = Property.NODE_PROPERTY_TEXT_OVERFLOW_UNDEFINED;
        if (obj == null) {
            ((Label) this.mView).setTextOverflow(Property.NODE_PROPERTY_TEXT_OVERFLOW_UNDEFINED);
            return;
        }
        int intValue = ((Integer) obj).intValue();
        if (intValue == 1056964725) {
            i = Property.NODE_PROPERTY_TEXT_OVERFLOW_ELLIPSIS;
        } else if (intValue == 1056964726) {
            i = Property.NODE_PROPERTY_TEXT_OVERFLOW_MIDDLE;
        } else if (intValue == 1056964727) {
            i = Property.NODE_PROPERTY_TEXT_OVERFLOW_HEAD;
        }
        ((Label) this.mView).setTextOverflow(i);
    }

    /* access modifiers changed from: protected */
    public void updateFontWeight(Object obj) {
        if (obj == null) {
            ((Label) this.mView).setFontWeight(Property.NODE_PROPERTY_FONT_NORMAL);
            return;
        }
        ((Label) this.mView).setFontWeight(((Integer) obj).intValue());
    }

    /* access modifiers changed from: protected */
    public void updateFontStyle(Object obj) {
        if (obj == null) {
            ((Label) this.mView).setFontStyle(Property.NODE_PROPERTY_FONT_NORMAL);
            return;
        }
        ((Label) this.mView).setFontStyle(((Integer) obj).intValue());
    }

    private void updateTextDecoration(Object obj) {
        int paintFlags = ((Label) this.mView).getPaintFlags();
        if (obj == null) {
            ((Label) this.mView).setPaintFlags(paintFlags & -17 & -9);
            return;
        }
        String str = (String) obj;
        if (TextUtils.equals("underline", str)) {
            ((Label) this.mView).setPaintFlags(paintFlags | 8);
            return;
        }
        if (!TextUtils.equals("overline", str)) {
            if (TextUtils.equals("line-through", str)) {
                ((Label) this.mView).setPaintFlags(paintFlags | 16);
                return;
            }
            ((Label) this.mView).setPaintFlags(paintFlags & -17 & -9);
        }
    }

    private void updateTextAlign(Object obj) {
        if (obj == null) {
            ((Label) this.mView).setLayoutAlignment(Alignment.ALIGN_NORMAL);
            ((Label) this.mView).setGravity(51);
            return;
        }
        Alignment alignment = Alignment.ALIGN_NORMAL;
        int intValue = ((Integer) obj).intValue();
        if (intValue == 1056964614) {
            ((Label) this.mView).setGravity(51);
        } else if (intValue == 1056964613) {
            ((Label) this.mView).setGravity(49);
        } else if (intValue == 1056964620) {
            ((Label) this.mView).setGravity(53);
        } else if (intValue == 1056964611) {
            ((Label) this.mView).setGravity(19);
        } else if (intValue == 1056964609) {
            alignment = Alignment.ALIGN_CENTER;
            ((Label) this.mView).setGravity(17);
        } else if (intValue == 1056964617) {
            alignment = Alignment.ALIGN_OPPOSITE;
            ((Label) this.mView).setGravity(21);
        } else if (intValue == 1056964626) {
            ((Label) this.mView).setGravity(83);
        } else if (intValue == 1056964625) {
            ((Label) this.mView).setGravity(81);
        } else if (intValue == 1056964632) {
            ((Label) this.mView).setGravity(85);
        }
        ((Label) this.mView).setLayoutAlignment(alignment);
    }

    private void updateColor(Object obj) {
        if (obj == null) {
            ((Label) this.mView).setTextColor(-16777216);
        } else {
            ((Label) this.mView).setTextColor(((Integer) obj).intValue());
        }
    }

    private void updatePadding(Object obj) {
        if (obj == null) {
            ((Label) this.mView).setPadding(0, 0, 0, 0);
        } else if (obj instanceof float[]) {
            int[] iArr = new int[4];
            ((Label) this.mView).getContext();
            float[] fArr = (float[]) obj;
            for (int i = 0; i < fArr.length; i++) {
                iArr[i] = (int) DimensionUtils.standardUnitToPixelPrecise(fArr[i]);
            }
            ((Label) this.mView).setPadding(iArr[3], iArr[0], iArr[1], iArr[2]);
        }
    }

    private void updateFontFamily(Object obj) {
        if (obj == null) {
            ((Label) this.mView).setFontFamily(null);
            return;
        }
        ((Label) this.mView).setFontFamily((String) obj);
    }
}
