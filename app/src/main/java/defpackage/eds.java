package defpackage;

import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: eds reason: default package */
/* compiled from: UtilDestNavi */
public final class eds {
    public static CharSequence a(int i) {
        if (i >= 1000) {
            String valueOf = String.valueOf(((float) Math.round((((float) i) / 1000.0f) * 10.0f)) / 10.0f);
            if (valueOf.endsWith(".0")) {
                valueOf = valueOf.replace(".0", "");
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(valueOf);
            spannableStringBuilder.setSpan(new StyleSpan(1), 0, valueOf.length(), 33);
            spannableStringBuilder.append(AMapAppGlobal.getApplication().getString(R.string.km));
            return spannableStringBuilder;
        }
        String valueOf2 = String.valueOf(i);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
        spannableStringBuilder2.append(valueOf2);
        spannableStringBuilder2.setSpan(new StyleSpan(1), 0, valueOf2.length(), 33);
        spannableStringBuilder2.append(AMapAppGlobal.getApplication().getString(R.string.route_meter));
        return spannableStringBuilder2;
    }
}
