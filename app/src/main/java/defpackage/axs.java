package defpackage;

import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: axs reason: default package */
/* compiled from: StringUtil */
public final class axs {
    public static final Pattern a = Pattern.compile(Token.SEPARATOR);
    public static final Pattern b = Pattern.compile(" > ");

    /* renamed from: axs$a */
    /* compiled from: StringUtil */
    static class a implements OnPreDrawListener {
        TextView a;
        String b;
        Pattern c;
        int d;
        private int e = Integer.MAX_VALUE;

        public a(TextView textView, String str, Pattern pattern, int i) {
            if (textView == null || str == null || pattern == null || i <= 0) {
                throw new IllegalArgumentException();
            }
            String replace = str.replace("\n", "");
            if (TextUtils.isEmpty(replace)) {
                throw new IllegalArgumentException("text is empty.");
            }
            this.a = textView;
            this.b = replace;
            this.c = pattern;
            this.d = i;
            int a2 = a(textView);
            if (a2 > 0) {
                this.e = a2;
            }
        }

        private static int a(TextView textView) {
            if (VERSION.SDK_INT >= 16) {
                return textView.getMaxLines();
            }
            try {
                Class<?> cls = textView.getClass();
                Field declaredField = cls.getDeclaredField("mMaxMode");
                declaredField.setAccessible(true);
                if (((Integer) declaredField.get(textView)).intValue() == 1) {
                    Field declaredField2 = cls.getDeclaredField("mMaximum");
                    declaredField2.setAccessible(true);
                    return ((Integer) declaredField2.get(textView)).intValue();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return -1;
        }

        public final boolean onPreDraw() {
            if (this.a.getWidth() > 0) {
                this.a.getViewTreeObserver().removeOnPreDrawListener(this);
                this.a.setText(a(this.a, this.b, this.c, this.d));
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x0099  */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x00ad  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final android.text.SpannableStringBuilder a(android.widget.TextView r21, java.lang.String r22, java.util.regex.Pattern r23, int r24) {
            /*
                r20 = this;
                r0 = r20
                r1 = r22
                r2 = r24
                android.content.Context r3 = r21.getContext()
                android.text.TextPaint r4 = r21.getPaint()
                int r5 = r21.getWidth()
                int r6 = r21.getPaddingLeft()
                int r5 = r5 - r6
                int r6 = r21.getPaddingRight()
                int r5 = r5 - r6
                android.content.res.Resources r6 = r3.getResources()
                android.graphics.drawable.Drawable r6 = r6.getDrawable(r2)
                int r6 = r6.getIntrinsicWidth()
                java.lang.String r7 = "…"
                float r7 = r4.measureText(r7)
                android.text.SpannableStringBuilder r8 = new android.text.SpannableStringBuilder
                r8.<init>()
                r9 = r23
                java.util.regex.Matcher r10 = r9.matcher(r1)
                r11 = 1
                r14 = 0
                r15 = 0
            L_0x003c:
                boolean r16 = r10.find()
                if (r16 == 0) goto L_0x00e1
                int r12 = r10.start()
                java.lang.String r12 = r1.substring(r14, r12)
                float r16 = r4.measureText(r12)
                float r17 = r15 + r16
                float r13 = (float) r5
                int r17 = (r17 > r13 ? 1 : (r17 == r13 ? 0 : -1))
                if (r17 <= 0) goto L_0x008b
                r18 = r5
                int r5 = r0.e
                if (r11 >= r5) goto L_0x0078
                float r5 = r13 - r15
                r1 = 0
                r15 = 1
                int r5 = r4.breakText(r12, r15, r5, r1)
                r1 = 0
                r8.append(r12, r1, r5)
                java.lang.String r15 = "\n"
                r8.append(r15)
                java.lang.String r12 = r12.substring(r5)
                float r16 = r4.measureText(r12)
                int r11 = r11 + 1
                r15 = 0
                goto L_0x008d
            L_0x0078:
                r1 = 0
                r5 = 1
                float r13 = r13 - r15
                float r13 = r13 - r7
                r2 = 0
                int r3 = r4.breakText(r12, r5, r13, r2)
                r8.append(r12, r1, r3)
                java.lang.String r1 = "…"
                r8.append(r1)
            L_0x0089:
                r1 = 1
                goto L_0x00e4
            L_0x008b:
                r18 = r5
            L_0x008d:
                r8.append(r12)
                float r1 = r15 + r16
                float r5 = (float) r6
                float r12 = r1 + r5
                int r15 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
                if (r15 <= 0) goto L_0x00ad
                int r12 = r0.e
                if (r11 >= r12) goto L_0x00a6
                java.lang.String r1 = "\n"
                r8.append(r1)
                int r11 = r11 + 1
                r1 = 0
                goto L_0x00bc
            L_0x00a6:
                java.lang.String r2 = "…"
                r8.append(r2)
            L_0x00ab:
                r15 = r1
                goto L_0x0089
            L_0x00ad:
                float r12 = r12 + r7
                int r12 = (r12 > r13 ? 1 : (r12 == r13 ? 0 : -1))
                if (r12 <= 0) goto L_0x00bc
                int r12 = r0.e
                if (r11 != r12) goto L_0x00bc
                java.lang.String r2 = "…"
                r8.append(r2)
                goto L_0x00ab
            L_0x00bc:
                int r12 = r8.length()
                java.lang.String r13 = r23.pattern()
                r8.append(r13)
                axu r13 = new axu
                r13.<init>(r3, r2)
                int r14 = r8.length()
                r15 = 33
                r8.setSpan(r13, r12, r14, r15)
                float r15 = r1 + r5
                int r14 = r10.end()
                r5 = r18
                r1 = r22
                goto L_0x003c
            L_0x00e1:
                r18 = r5
                r1 = 0
            L_0x00e4:
                if (r1 != 0) goto L_0x0129
                r1 = r22
                java.lang.String r1 = r1.substring(r14)
                float r2 = r4.measureText(r1)
                float r2 = r2 + r15
                r5 = r18
                float r3 = (float) r5
                int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r2 <= 0) goto L_0x0126
                int r2 = r0.e
                if (r11 >= r2) goto L_0x0114
                float r3 = r3 - r15
                r2 = 1
                r5 = 0
                int r2 = r4.breakText(r1, r2, r3, r5)
                r6 = 0
                r8.append(r1, r6, r2)
                java.lang.String r3 = "\n"
                r8.append(r3)
                java.lang.String r1 = r1.substring(r2)
                r8.append(r1)
                goto L_0x0129
            L_0x0114:
                r2 = 1
                r5 = 0
                r6 = 0
                float r3 = r3 - r15
                float r3 = r3 - r7
                int r2 = r4.breakText(r1, r2, r3, r5)
                r8.append(r1, r6, r2)
                java.lang.String r1 = "…"
                r8.append(r1)
                goto L_0x0129
            L_0x0126:
                r8.append(r1)
            L_0x0129:
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.axs.a.a(android.widget.TextView, java.lang.String, java.util.regex.Pattern, int):android.text.SpannableStringBuilder");
        }
    }

    public static SpannableString a(String str) {
        if (str.indexOf(SimpleComparison.GREATER_THAN_OPERATION) > 0) {
            str = str.replace(SimpleComparison.GREATER_THAN_OPERATION, "-");
        }
        SpannableString spannableString = new SpannableString(str);
        try {
            if (str.contains("-")) {
                int indexOf = str.indexOf("-");
                int length = "-".length() + indexOf;
                if (indexOf >= 0 && length > 0 && indexOf < length) {
                    spannableString.setSpan(new ForegroundColorSpan(Color.argb(255, 153, 153, 153)), indexOf, length, 33);
                }
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return spannableString;
    }

    public static SpannableString a(Context context, String str, int i) {
        if (context == null || str == null || i <= 0) {
            throw new IllegalArgumentException();
        }
        SpannableString spannableString = new SpannableString(str);
        Matcher matcher = a.matcher(str);
        while (matcher.find()) {
            spannableString.setSpan(new axu(context, i), matcher.start(), matcher.end(), 33);
        }
        return spannableString;
    }

    public static void a(TextView textView, String str, int i) {
        a aVar = new a(textView, str, b, i);
        if (aVar.a.getWidth() <= 0) {
            aVar.a.setText(aVar.b);
            aVar.a.getViewTreeObserver().addOnPreDrawListener(aVar);
            return;
        }
        aVar.a.setText(aVar.a(aVar.a, aVar.b, aVar.c, aVar.d));
    }
}
