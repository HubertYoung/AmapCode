package com.autonavi.minimap.ajx3.views;

import android.support.annotation.NonNull;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.util.AjxImageThemeUtil;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;

public class Ajx3RatingBarProperty extends BaseProperty<Ajx3RatingBar> {
    private static final String ATTRIBUTE_ICON_HEIGHT = "iconHeight";
    private static final String ATTRIBUTE_ICON_PADDING = "iconPadding";
    private static final String ATTRIBUTE_ICON_WIDTH = "iconWidth";
    private static final String ATTRIBUTE_IMAGES = "icons";
    private static final String ATTRIBUTE_MAX = "max";
    private static final String ATTRIBUTE_RATING = "value";
    private static final boolean DEBUG = LogHelper.IS_DEBUG;
    private static final int DEFAULT_MAX = 5;
    private static final int DEFAULT_STAR_FULL = R.drawable.star_full;
    private static final int DEFAULT_STAR_HALF = R.drawable.star_half;
    private static final int DEFAULT_STAR_HEIGHT = 0;
    private static final int DEFAULT_STAR_NULL = R.drawable.star_null;
    private static final int DEFUALT_PADDING = 0;
    private static final float DEFUALT_RATING = 3.5f;
    private static final int DEFUALT_STAR_WIDTH = 0;
    private static final String TAG = "Ajx3RatingBar";

    public Ajx3RatingBarProperty(@NonNull Ajx3RatingBar ajx3RatingBar, @NonNull IAjxContext iAjxContext) {
        super(ajx3RatingBar, iAjxContext);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0023, code lost:
        if (r6.equals("value") != false) goto L_0x0059;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r6, java.lang.Object r7) {
        /*
            r5 = this;
            boolean r0 = DEBUG
            r1 = 1
            r2 = 0
            r3 = 2
            if (r0 == 0) goto L_0x0013
            java.lang.String r0 = "update attribute key = %s, value = %s"
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r4[r2] = r6
            r4[r1] = r7
            java.lang.String.format(r0, r4)
        L_0x0013:
            r0 = -1
            int r4 = r6.hashCode()
            switch(r4) {
                case -1446359720: goto L_0x004e;
                case -1398151987: goto L_0x0044;
                case -826033408: goto L_0x003a;
                case 107876: goto L_0x0030;
                case 100029210: goto L_0x0026;
                case 111972721: goto L_0x001c;
                default: goto L_0x001b;
            }
        L_0x001b:
            goto L_0x0058
        L_0x001c:
            java.lang.String r2 = "value"
            boolean r2 = r6.equals(r2)
            if (r2 == 0) goto L_0x0058
            goto L_0x0059
        L_0x0026:
            java.lang.String r1 = "icons"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0058
            r1 = 5
            goto L_0x0059
        L_0x0030:
            java.lang.String r1 = "max"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0058
            r1 = 0
            goto L_0x0059
        L_0x003a:
            java.lang.String r1 = "iconHeight"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0058
            r1 = 2
            goto L_0x0059
        L_0x0044:
            java.lang.String r1 = "iconWidth"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0058
            r1 = 3
            goto L_0x0059
        L_0x004e:
            java.lang.String r1 = "iconPadding"
            boolean r1 = r6.equals(r1)
            if (r1 == 0) goto L_0x0058
            r1 = 4
            goto L_0x0059
        L_0x0058:
            r1 = -1
        L_0x0059:
            switch(r1) {
                case 0: goto L_0x0074;
                case 1: goto L_0x0070;
                case 2: goto L_0x006c;
                case 3: goto L_0x0068;
                case 4: goto L_0x0064;
                case 5: goto L_0x0060;
                default: goto L_0x005c;
            }
        L_0x005c:
            super.updateAttribute(r6, r7)
            return
        L_0x0060:
            r5.updateStarImages(r7)
            return
        L_0x0064:
            r5.updateIconPadding(r7)
            return
        L_0x0068:
            r5.updateIconWidth(r7)
            return
        L_0x006c:
            r5.updateIconHeight(r7)
            return
        L_0x0070:
            r5.updateRating(r7)
            return
        L_0x0074:
            r5.updateMax(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.views.Ajx3RatingBarProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateMax(Object obj) {
        if (obj == null) {
            ((Ajx3RatingBar) this.mView).setMax(5);
            return;
        }
        if (obj instanceof String) {
            ((Ajx3RatingBar) this.mView).setMax(StringUtils.parseInt((String) obj, 5));
        }
    }

    private void updateRating(Object obj) {
        if (obj == null) {
            ((Ajx3RatingBar) this.mView).setRating(DEFUALT_RATING);
            return;
        }
        if (obj instanceof String) {
            ((Ajx3RatingBar) this.mView).setRating(StringUtils.parseFloat((String) obj));
        }
    }

    private void updateIconWidth(Object obj) {
        if (obj == null) {
            ((Ajx3RatingBar) this.mView).setImageWidth(0);
            return;
        }
        if (obj instanceof String) {
            ((Ajx3RatingBar) this.mView).setImageWidth(StringUtils.parseStandUnit2Px(((Ajx3RatingBar) this.mView).getContext(), (String) obj, 0));
        }
    }

    private void updateIconHeight(Object obj) {
        if (obj == null) {
            ((Ajx3RatingBar) this.mView).setImageHeight(0);
            return;
        }
        if (obj instanceof String) {
            ((Ajx3RatingBar) this.mView).setImageHeight(StringUtils.parseStandUnit2Px(((Ajx3RatingBar) this.mView).getContext(), (String) obj, 0));
        }
    }

    private void updateIconPadding(Object obj) {
        if (obj == null) {
            ((Ajx3RatingBar) this.mView).setImagePadding(0);
            return;
        }
        if (obj instanceof String) {
            ((Ajx3RatingBar) this.mView).setImagePadding(StringUtils.parseStandUnit2Px(((Ajx3RatingBar) this.mView).getContext(), (String) obj, 0));
        }
    }

    private void updateStarImages(Object obj) {
        if (obj == null) {
            ((Ajx3RatingBar) this.mView).setRatingImages(DEFAULT_STAR_FULL, DEFAULT_STAR_NULL, DEFAULT_STAR_HALF);
            return;
        }
        if (obj instanceof String) {
            String[] split = ((String) obj).split(";");
            if (split.length >= 2) {
                int i = 0;
                if (!(split[0] == null || split[1] == null)) {
                    int loadAjxImageResourceId = AjxImageThemeUtil.loadAjxImageResourceId(this.mAjxContext, split[0]);
                    int loadAjxImageResourceId2 = AjxImageThemeUtil.loadAjxImageResourceId(this.mAjxContext, split[1]);
                    if (split.length >= 3 && split[2] != null) {
                        i = AjxImageThemeUtil.loadAjxImageResourceId(this.mAjxContext, split[2]);
                    }
                    if (loadAjxImageResourceId > 0 && loadAjxImageResourceId2 > 0) {
                        ((Ajx3RatingBar) this.mView).setRatingImages(loadAjxImageResourceId, loadAjxImageResourceId2, i);
                    }
                    return;
                }
            }
            ((Ajx3RatingBar) this.mView).setRatingImages(DEFAULT_STAR_FULL, DEFAULT_STAR_NULL, DEFAULT_STAR_HALF);
        }
    }
}
