package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.R;

/* renamed from: pw reason: default package */
/* compiled from: TrafficEventUtil */
public final class pw {
    public static CharSequence a(@NonNull Context context, int i, int i2) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int a = agn.a(context, 13.0f);
        spannableStringBuilder.append("拥堵");
        spannableStringBuilder.append(a(context, i, a, a));
        spannableStringBuilder.append("，");
        SpannableString b = b(context, i2, a, a);
        spannableStringBuilder.append("约");
        spannableStringBuilder.append(b);
        spannableStringBuilder.append("通过");
        return spannableStringBuilder.toString();
    }

    private static SpannableString a(Context context, int i, int i2, int i3) {
        if (i >= 1000) {
            float round = ((float) Math.round(((float) i) / 100.0f)) / 10.0f;
            if ((round * 10.0f) % 10.0f == 0.0f) {
                StringBuilder sb = new StringBuilder();
                sb.append((int) round);
                return a(sb.toString(), context.getString(R.string.km), i2, i3);
            }
            String valueOf = String.valueOf(round);
            String[] split = valueOf.split("\\.");
            if (split.length > 1) {
                String str = split[1];
                if (str.length() > 1) {
                    str = str.substring(0, 1);
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append(split[0]);
                sb2.append(".");
                sb2.append(str);
                valueOf = sb2.toString();
            }
            return a(String.valueOf(valueOf), context.getString(R.string.km), i2, i3);
        } else if (i > 10) {
            return a(String.valueOf(i), context.getString(R.string.route_meter), i2, i3);
        } else {
            return a(context.getString(R.string.autonavi_page_now_string), (String) Token.SEPARATOR, i2, i3);
        }
    }

    private static SpannableString b(Context context, int i, int i2, int i3) {
        SpannableString spannableString;
        if (i < 60) {
            return a((String) "<1", context.getString(R.string.route_minutes_simple), i2, i3);
        }
        int i4 = (i + 30) / 60;
        if (i4 < 60) {
            spannableString = a(String.valueOf(i4), context.getString(R.string.route_minutes), i2, i3);
        } else if (i4 < 1440) {
            int i5 = i4 / 60;
            int i6 = i4 % 60;
            if (i6 > 0) {
                spannableString = a(String.valueOf(i5), context.getString(R.string.route_hour), String.valueOf(i6), context.getString(R.string.route_minutes_simple), i2, i3, i2, i3);
            } else {
                spannableString = a(String.valueOf(i5), context.getString(R.string.route_hour), i2, i3);
            }
        } else {
            int i7 = i4 / 60;
            int i8 = i4 % 60;
            int i9 = i7 % 24;
            int i10 = i7 / 24;
            String string = context.getString(R.string.navi_display_day);
            if (i9 == 0) {
                if (i8 == 0) {
                    spannableString = a(String.valueOf(i10), string, i2, i3);
                } else {
                    spannableString = a(String.valueOf(i10), string, String.valueOf(i8), context.getString(R.string.route_minutes_simple), i2, i3, i2, i3);
                }
            } else if (i8 == 0) {
                spannableString = a(String.valueOf(i10), string, String.valueOf(i9), context.getString(R.string.route_hour), i2, i3, i2, i3);
            } else {
                spannableString = a(String.valueOf(i10), string, String.valueOf(i9), context.getString(R.string.route_hour), String.valueOf(i8), context.getString(R.string.route_minutes_simple), i2, i3, i2, i3, i2, i3);
            }
        }
        return spannableString;
    }

    private static SpannableString a(String str, String str2, int i, int i2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        SpannableString spannableString = new SpannableString(sb.toString());
        int length = str.length();
        spannableString.setSpan(new AbsoluteSizeSpan(i), 0, length, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), 0, length, 33);
        spannableString.setSpan(new StyleSpan(1), 0, length, 33);
        int length2 = str2.length() + length;
        spannableString.setSpan(new AbsoluteSizeSpan(i2), length, length2, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), length, length2, 33);
        return spannableString;
    }

    private static SpannableString a(String str, String str2, String str3, String str4, String str5, String str6, int i, int i2, int i3, int i4, int i5, int i6) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4) || TextUtils.isEmpty(str5) || TextUtils.isEmpty(str6)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String str7 = str;
        sb.append(str7);
        String str8 = str2;
        sb.append(str8);
        String str9 = str3;
        sb.append(str9);
        String str10 = str4;
        sb.append(str10);
        sb.append(str5);
        sb.append(str6);
        SpannableString spannableString = new SpannableString(sb.toString());
        int length = str7.length();
        spannableString.setSpan(new AbsoluteSizeSpan(i), 0, length, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), 0, length, 33);
        spannableString.setSpan(new StyleSpan(1), 0, length, 33);
        int length2 = str8.length() + length;
        spannableString.setSpan(new AbsoluteSizeSpan(i2), length, length2, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), length, length2, 33);
        int length3 = str9.length() + length2;
        spannableString.setSpan(new AbsoluteSizeSpan(i3), length2, length3, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), length2, length3, 33);
        spannableString.setSpan(new StyleSpan(1), length2, length3, 33);
        int length4 = str10.length() + length3;
        spannableString.setSpan(new AbsoluteSizeSpan(i4), length3, length4, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), length3, length4, 33);
        int length5 = str5.length() + length4;
        spannableString.setSpan(new AbsoluteSizeSpan(i5), length4, length5, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), length4, length5, 33);
        spannableString.setSpan(new StyleSpan(1), length4, length5, 33);
        int length6 = str6.length() + length5;
        spannableString.setSpan(new AbsoluteSizeSpan(i6), length5, length6, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), length5, length6, 33);
        return spannableString;
    }

    private static SpannableString a(String str, String str2, String str3, String str4, int i, int i2, int i3, int i4) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        sb.append(str3);
        sb.append(str4);
        SpannableString spannableString = new SpannableString(sb.toString());
        int length = str.length();
        spannableString.setSpan(new AbsoluteSizeSpan(i), 0, length, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), 0, length, 33);
        spannableString.setSpan(new StyleSpan(1), 0, length, 33);
        int length2 = str2.length() + length;
        spannableString.setSpan(new AbsoluteSizeSpan(i2), length, length2, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), length, length2, 33);
        int length3 = str3.length() + length2;
        spannableString.setSpan(new AbsoluteSizeSpan(i3), length2, length3, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), length2, length3, 33);
        spannableString.setSpan(new StyleSpan(1), length2, length3, 33);
        int length4 = str4.length() + length3;
        spannableString.setSpan(new AbsoluteSizeSpan(i4), length3, length4, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), length3, length4, 33);
        return spannableString;
    }
}
