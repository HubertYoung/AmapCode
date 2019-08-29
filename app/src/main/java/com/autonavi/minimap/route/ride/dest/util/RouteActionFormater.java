package com.autonavi.minimap.route.ride.dest.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

public enum RouteActionFormater {
    DEFAULT {
        public final CharSequence formatSegmentFirstAction(int i, String str, int i2) {
            return null;
        }

        public final CharSequence formatSegmentSecondAction(int i, String str, int i2) {
            return null;
        }
    },
    GO_STRAIGHT {
        public final CharSequence formatSegmentFirstAction(int i, String str, int i2) {
            if (TextUtils.isEmpty(str) || str.contains(this.mContext.getString(R.string.route_foot_navi_no_name_road))) {
                return null;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilderAppendGrayer(spannableStringBuilder, this.mContext.getString(R.string.route_foot_navi_along));
            spannableStringBuilderAppendDark(spannableStringBuilder, Token.SEPARATOR.concat(String.valueOf(str)));
            return spannableStringBuilder;
        }

        public final CharSequence formatSegmentSecondAction(int i, String str, int i2) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilderAppendDark(spannableStringBuilder, this.mContext.getString(R.string.route_foot_navi_straight));
            StringBuilder sb = new StringBuilder(Token.SEPARATOR);
            sb.append(eds.a(i2));
            spannableStringBuilderAppendGrayer(spannableStringBuilder, sb.toString());
            return spannableStringBuilder;
        }
    },
    GO_FORWARD {
        public final CharSequence formatSegmentFirstAction(int i, String str, int i2) {
            if (TextUtils.isEmpty(str) || str.contains(this.mContext.getString(R.string.route_foot_navi_no_name_road))) {
                return null;
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilderAppendGrayer(spannableStringBuilder, this.mContext.getString(R.string.route_foot_navi_along));
            spannableStringBuilderAppendDark(spannableStringBuilder, Token.SEPARATOR.concat(String.valueOf(str)));
            return spannableStringBuilder;
        }

        public final CharSequence formatSegmentSecondAction(int i, String str, int i2) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilderAppendDark(spannableStringBuilder, this.mContext.getString(R.string.route_foot_navi_forward));
            StringBuilder sb = new StringBuilder(Token.SEPARATOR);
            sb.append(eds.a(i2));
            spannableStringBuilderAppendGrayer(spannableStringBuilder, sb.toString());
            return spannableStringBuilder;
        }
    },
    WALKING_FACILITY {
        public final CharSequence formatSegmentFirstAction(int i, String str, int i2) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            StringBuilder sb = new StringBuilder();
            sb.append(eds.a(i2));
            sb.append(this.mContext.getString(R.string.route_after));
            spannableStringBuilderAppendGrayer(spannableStringBuilder, sb.toString());
            return spannableStringBuilder;
        }

        public final CharSequence formatSegmentSecondAction(int i, String str, int i2) {
            String b = edq.b(i);
            String c = edq.c(i);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            sb.append(c);
            spannableStringBuilderAppendDark(spannableStringBuilder, sb.toString());
            return spannableStringBuilder;
        }
    },
    TURNING {
        public final CharSequence formatSegmentFirstAction(int i, String str, int i2) {
            StringBuilder sb;
            String str2;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(eds.a(i2));
            sb2.append(this.mContext.getString(R.string.route_after));
            spannableStringBuilderAppendGrayer(spannableStringBuilder, sb2.toString());
            if (i == 37) {
                sb = new StringBuilder(Token.SEPARATOR);
                str2 = edq.a((byte) i);
            } else if (i == 36) {
                spannableStringBuilderAppendDark(spannableStringBuilder, Token.SEPARATOR);
                return spannableStringBuilder;
            } else {
                sb = new StringBuilder(Token.SEPARATOR);
                byte b = (byte) i;
                if (b > 15 && b <= 40) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(edq.b(b));
                    sb3.append(edq.c(b));
                    str2 = sb3.toString();
                } else {
                    str2 = edq.a(b);
                }
            }
            sb.append(str2);
            spannableStringBuilderAppendDark(spannableStringBuilder, sb.toString());
            return spannableStringBuilder;
        }

        public final CharSequence formatSegmentSecondAction(int i, String str, int i2) {
            if (TextUtils.isEmpty(str) || str.equalsIgnoreCase(this.mContext.getString(R.string.route_foot_navi_no_name_road))) {
                return null;
            }
            String string = this.mContext.getString(R.string.route_foot_navi_enter);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilderAppendGrayer(spannableStringBuilder, string);
            spannableStringBuilderAppendDark(spannableStringBuilder, Token.SEPARATOR.concat(String.valueOf(str)));
            return spannableStringBuilder;
        }
    },
    ARRIVE {
        public final CharSequence formatSegmentFirstAction(int i, String str, int i2) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            StringBuilder sb = new StringBuilder();
            sb.append(eds.a(i2));
            sb.append(this.mContext.getString(R.string.route_after));
            spannableStringBuilderAppendDark(spannableStringBuilder, sb.toString());
            return spannableStringBuilder;
        }

        public final CharSequence formatSegmentSecondAction(int i, String str, int i2) {
            if (TextUtils.isEmpty(str)) {
                str = this.mContext.getString(R.string.route_navi_destination);
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilderAppendGrayer(spannableStringBuilder, this.mContext.getString(R.string.route_arrived));
            spannableStringBuilderAppendDark(spannableStringBuilder, Token.SEPARATOR.concat(String.valueOf(str)));
            return spannableStringBuilder;
        }
    },
    NAVI_STARTING_POINT {
        public final CharSequence formatSegmentSecondAction(int i, String str, int i2) {
            return null;
        }

        public final CharSequence formatSegmentFirstAction(int i, String str, int i2) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mContext.getString(R.string.route_from));
            sb.append(this.mContext.getString(R.string.autonavi_fragment_current_location));
            sb.append(this.mContext.getString(R.string.route_setout));
            SpannableString spannableString = new SpannableString(sb.toString());
            spannableString.setSpan(new ForegroundColorSpan(-13421773), 0, spannableString.length(), 33);
            return spannableString;
        }
    },
    BROWSER_STARTING_POINT {
        public final CharSequence formatSegmentFirstAction(int i, String str, int i2) {
            return null;
        }

        public final CharSequence formatSegmentSecondAction(int i, String str, int i2) {
            return null;
        }
    };
    
    protected Context mContext;
    private String mValue;

    public abstract CharSequence formatSegmentFirstAction(int i, String str, int i2);

    public abstract CharSequence formatSegmentSecondAction(int i, String str, int i2);

    private ForegroundColorSpan getGrayeColorSpan() {
        return new ForegroundColorSpan(-7829368);
    }

    private ForegroundColorSpan getDarkColorSpan() {
        return new ForegroundColorSpan(-13421773);
    }

    private StyleSpan getBoldStyleSpan() {
        return new StyleSpan(1);
    }

    private RouteActionFormater(String str) {
        this.mValue = "";
        this.mContext = AMapPageUtil.getAppContext();
        this.mValue = str;
    }

    /* access modifiers changed from: protected */
    public void spannableStringBuilderAppendGrayer(SpannableStringBuilder spannableStringBuilder, CharSequence charSequence) {
        if (spannableStringBuilder != null && charSequence != null) {
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.setSpan(getGrayeColorSpan(), length, spannableStringBuilder.length(), 33);
        }
    }

    /* access modifiers changed from: protected */
    public void spannableStringBuilderAppendDark(SpannableStringBuilder spannableStringBuilder, CharSequence charSequence) {
        if (spannableStringBuilder != null && charSequence != null) {
            int length = spannableStringBuilder.length();
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.setSpan(getDarkColorSpan(), length, spannableStringBuilder.length(), 33);
            spannableStringBuilder.setSpan(getBoldStyleSpan(), length, spannableStringBuilder.length(), 33);
        }
    }
}
