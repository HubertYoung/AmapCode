package defpackage;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.sdk.sys.a;
import com.autonavi.common.SuperId;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cxa reason: default package */
/* compiled from: HandleMessage */
public final class cxa {
    private static volatile boolean a = true;

    private static String a(View view) {
        int i;
        if (view == null) {
            return "";
        }
        ViewParent parent = view.getParent();
        if (parent == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (parent instanceof ViewGroup) {
            try {
                String simpleName = view.getClass().getSimpleName();
                if (parent instanceof AdapterView) {
                    i = ((AdapterView) parent).getPositionForView(view);
                } else if (parent instanceof ViewPager) {
                    i = ((ViewPager) parent).getCurrentItem();
                } else if (parent instanceof RecyclerView) {
                    i = ((RecyclerView) parent).getChildAdapterPosition(view);
                } else {
                    i = ((ViewGroup) parent).indexOfChild(view);
                }
                View view2 = (View) parent;
                String str = cxn.a.get(simpleName);
                if (str == null) {
                    sb.append("$");
                    sb.append(simpleName);
                    sb.append(":");
                    sb.append(i);
                } else {
                    sb.append("$");
                    sb.append(str);
                    sb.append(":");
                    sb.append(i);
                }
                parent = parent.getParent();
                if (parent == null) {
                    break;
                }
                view = view2;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String str2 = "";
        if (sb.length() > 1) {
            str2 = sb.toString().substring(1);
        }
        return str2;
    }

    private static void a(ViewGroup viewGroup, StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        if (viewGroup != null) {
            CharSequence contentDescription = viewGroup.getContentDescription();
            if (contentDescription != null) {
                stringBuffer2.append("$");
                stringBuffer2.append(contentDescription.toString());
            }
            b(viewGroup, stringBuffer, stringBuffer2);
        }
    }

    private static void b(ViewGroup viewGroup, StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt != null) {
                    CharSequence contentDescription = childAt.getContentDescription();
                    if (contentDescription != null) {
                        stringBuffer2.append("$");
                        stringBuffer2.append(contentDescription.toString());
                    }
                    if (childAt instanceof TextView) {
                        CharSequence text = ((TextView) childAt).getText();
                        if (text != null) {
                            String charSequence = text.toString();
                            if (charSequence.trim().length() != 0) {
                                stringBuffer.append("$");
                                stringBuffer.append(charSequence);
                            }
                        }
                    } else if (childAt instanceof ViewGroup) {
                        b((ViewGroup) childAt, stringBuffer, stringBuffer2);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0233, code lost:
        if ((r0 instanceof android.widget.TextView) == false) goto L_0x025b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0235, code lost:
        r1 = (android.widget.TextView) r0;
        r3 = r1.getInputType();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x023c, code lost:
        if (r3 == 129) goto L_0x0254;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x023e, code lost:
        if (r3 != 18) goto L_0x0241;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:?, code lost:
        defpackage.cxj.c.a(r5, com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem.MM_C15_K4_TIME, r1.getText().toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x024f, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:?, code lost:
        r1.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0254, code lost:
        defpackage.cxj.c.a(r5, com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem.MM_C15_K4_TIME, "passwd");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0288, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01d5, code lost:
        if (defpackage.cwz.a(r11.obj, android.widget.CompoundButton.OnCheckedChangeListener.class) == false) goto L_0x01d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01d7, code lost:
        r11 = r0.getParent();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01dd, code lost:
        if ((r11 instanceof android.widget.AdapterView) == false) goto L_0x01ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01df, code lost:
        r6.append("$p");
        r6.append(((android.widget.AdapterView) r11).getPositionForView(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01f0, code lost:
        if ((r0 instanceof android.view.ViewGroup) == false) goto L_0x0231;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01f2, code lost:
        r1 = new java.lang.StringBuffer();
        r3 = new java.lang.StringBuffer();
        a((android.view.ViewGroup) r0, r1, r3);
        defpackage.cxj.c.a(r5, com.autonavi.common.SuperId.BIT_1_MAIN_VOICE_ASSISTANT, r6.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x020e, code lost:
        if (r1.length() <= 1) goto L_0x021d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0210, code lost:
        defpackage.cxj.c.a(r5, com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem.MM_C15_K4_TIME, r1.toString().substring(1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0221, code lost:
        if (r3.length() <= 1) goto L_0x0276;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0223, code lost:
        defpackage.cxj.c.a(r5, "m", r3.toString().substring(1));
     */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x027f A[Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }] */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0283 A[Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(@android.support.annotation.NonNull android.os.Message r11) {
        /*
            java.lang.Object r0 = r11.obj     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            android.view.View r0 = (android.view.View) r0     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            android.os.Bundle r1 = r11.getData()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r1 == 0) goto L_0x028e
            java.lang.String r2 = "_view_name"
            r3 = 0
            java.lang.String r2 = r1.getString(r2, r3)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r4 = "_location_property"
            java.lang.String r1 = r1.getString(r4, r3)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r0 == 0) goto L_0x0289
            if (r2 != 0) goto L_0x001d
            goto L_0x0289
        L_0x001d:
            int r4 = r0.getId()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r5 = -1
            if (r4 == r5) goto L_0x0029
            java.lang.String r4 = java.lang.Integer.toHexString(r4)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            goto L_0x002b
        L_0x0029:
            java.lang.String r4 = ""
        L_0x002b:
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r5.<init>()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r6.<init>()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r6.append(r4)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r4 = "$"
            r6.append(r4)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r4 = a(r0)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r6.append(r4)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            int r4 = r11.arg1     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r7 = 18
            r8 = 129(0x81, float:1.81E-43)
            r9 = 8
            r10 = 1
            switch(r4) {
                case 257: goto L_0x01bd;
                case 258: goto L_0x01d7;
                case 259: goto L_0x0160;
                case 260: goto L_0x00df;
                case 261: goto L_0x0052;
                case 262: goto L_0x0052;
                case 263: goto L_0x0052;
                default: goto L_0x0050;
            }     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x0050:
            goto L_0x0288
        L_0x0052:
            boolean r11 = r0 instanceof android.view.ViewGroup     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r11 == 0) goto L_0x0095
            java.lang.StringBuffer r11 = new java.lang.StringBuffer     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r11.<init>()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r4.<init>()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            a(r0, r11, r4)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r0 = "p"
            java.lang.String r6 = r6.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r0, r6)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            int r0 = r11.length()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r0 <= r10) goto L_0x0081
            java.lang.String r0 = "t"
            java.lang.String r11 = r11.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r11 = r11.substring(r10)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r0, r11)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x0081:
            int r11 = r4.length()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r11 <= r10) goto L_0x00c9
            java.lang.String r11 = "m"
            java.lang.String r0 = r4.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r0 = r0.substring(r10)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r11, r0)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            goto L_0x00c9
        L_0x0095:
            java.lang.String r11 = "i"
            java.lang.String r4 = r6.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r11, r4)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            boolean r11 = r0 instanceof android.widget.TextView     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r11 == 0) goto L_0x00b7
            java.lang.String r11 = "t"
            r4 = r0
            android.widget.TextView r4 = (android.widget.TextView) r4     // Catch:{ NullPointerException -> 0x00b3 }
            java.lang.CharSequence r4 = r4.getText()     // Catch:{ NullPointerException -> 0x00b3 }
            java.lang.String r4 = r4.toString()     // Catch:{ NullPointerException -> 0x00b3 }
            defpackage.cxj.c.a(r5, r11, r4)     // Catch:{ NullPointerException -> 0x00b3 }
            goto L_0x00b7
        L_0x00b3:
            r11 = move-exception
            r11.printStackTrace()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x00b7:
            java.lang.String r11 = "m"
            java.lang.CharSequence r0 = r0.getContentDescription()     // Catch:{ NullPointerException -> 0x00c5 }
            java.lang.String r0 = r0.toString()     // Catch:{ NullPointerException -> 0x00c5 }
            defpackage.cxj.c.a(r5, r11, r0)     // Catch:{ NullPointerException -> 0x00c5 }
            goto L_0x00c9
        L_0x00c5:
            r11 = move-exception
            r11.printStackTrace()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x00c9:
            java.lang.String r11 = "c"
            defpackage.cxj.c.a(r5, r11, r2)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r1 == 0) goto L_0x00da
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r3.<init>()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r11 = "po"
            defpackage.cxj.c.a(r3, r11, r1)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x00da:
            defpackage.cxl.a(r9, r5, r3)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            goto L_0x0288
        L_0x00df:
            boolean r11 = r0 instanceof android.view.ViewGroup     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r11 == 0) goto L_0x0122
            java.lang.StringBuffer r11 = new java.lang.StringBuffer     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r11.<init>()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r1.<init>()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            a(r0, r11, r1)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r0 = "p"
            java.lang.String r3 = r6.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r0, r3)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            int r0 = r11.length()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r0 <= r10) goto L_0x010e
            java.lang.String r0 = "t"
            java.lang.String r11 = r11.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r11 = r11.substring(r10)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r0, r11)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x010e:
            int r11 = r1.length()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r11 <= r10) goto L_0x0156
            java.lang.String r11 = "m"
            java.lang.String r0 = r1.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r0 = r0.substring(r10)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r11, r0)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            goto L_0x0156
        L_0x0122:
            java.lang.String r11 = "i"
            java.lang.String r1 = r6.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r11, r1)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            boolean r11 = r0 instanceof android.widget.TextView     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r11 == 0) goto L_0x0144
            java.lang.String r11 = "t"
            r1 = r0
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ NullPointerException -> 0x0140 }
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ NullPointerException -> 0x0140 }
            java.lang.String r1 = r1.toString()     // Catch:{ NullPointerException -> 0x0140 }
            defpackage.cxj.c.a(r5, r11, r1)     // Catch:{ NullPointerException -> 0x0140 }
            goto L_0x0144
        L_0x0140:
            r11 = move-exception
            r11.printStackTrace()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x0144:
            java.lang.String r11 = "m"
            java.lang.CharSequence r0 = r0.getContentDescription()     // Catch:{ NullPointerException -> 0x0152 }
            java.lang.String r0 = r0.toString()     // Catch:{ NullPointerException -> 0x0152 }
            defpackage.cxj.c.a(r5, r11, r0)     // Catch:{ NullPointerException -> 0x0152 }
            goto L_0x0156
        L_0x0152:
            r11 = move-exception
            r11.printStackTrace()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x0156:
            java.lang.String r11 = "c"
            defpackage.cxj.c.a(r5, r11, r2)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r11 = 7
            defpackage.cxl.a(r11, r5)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            return
        L_0x0160:
            boolean r11 = r0 instanceof android.widget.EditText     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r11 == 0) goto L_0x0288
            r11 = r0
            android.widget.EditText r11 = (android.widget.EditText) r11     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            int r1 = r11.getInputType()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            boolean r3 = a     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r3 == 0) goto L_0x017e
            if (r1 == r8) goto L_0x0173
            if (r1 != r7) goto L_0x017e
        L_0x0173:
            java.lang.String r11 = "t"
            java.lang.String r1 = "passwd"
            defpackage.cxj.c.a(r5, r11, r1)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r11 = 0
            a = r11     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            goto L_0x0192
        L_0x017e:
            a = r10     // Catch:{ NullPointerException -> 0x018e }
            java.lang.String r1 = "t"
            android.text.Editable r11 = r11.getText()     // Catch:{ NullPointerException -> 0x018e }
            java.lang.String r11 = r11.toString()     // Catch:{ NullPointerException -> 0x018e }
            defpackage.cxj.c.a(r5, r1, r11)     // Catch:{ NullPointerException -> 0x018e }
            goto L_0x0192
        L_0x018e:
            r11 = move-exception
            r11.printStackTrace()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x0192:
            java.lang.CharSequence r11 = r0.getContentDescription()     // Catch:{ NullPointerException -> 0x01a6 }
            if (r11 == 0) goto L_0x01aa
            java.lang.String r11 = "m"
            java.lang.CharSequence r0 = r0.getContentDescription()     // Catch:{ NullPointerException -> 0x01a6 }
            java.lang.String r0 = r0.toString()     // Catch:{ NullPointerException -> 0x01a6 }
            defpackage.cxj.c.a(r5, r11, r0)     // Catch:{ NullPointerException -> 0x01a6 }
            goto L_0x01aa
        L_0x01a6:
            r11 = move-exception
            r11.printStackTrace()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x01aa:
            java.lang.String r11 = "i"
            java.lang.String r0 = r6.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r11, r0)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r11 = "c"
            defpackage.cxj.c.a(r5, r11, r2)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r11 = 3
            defpackage.cxl.a(r11, r5)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            return
        L_0x01bd:
            defpackage.cwz.a()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.Object r1 = r11.obj     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.Class<android.view.View$OnClickListener> r3 = android.view.View.OnClickListener.class
            boolean r1 = defpackage.cwz.a(r1, r3)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r1 == 0) goto L_0x01d7
            defpackage.cwz.a()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.Object r11 = r11.obj     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.Class<android.widget.CompoundButton$OnCheckedChangeListener> r1 = android.widget.CompoundButton.OnCheckedChangeListener.class
            boolean r11 = defpackage.cwz.a(r11, r1)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r11 != 0) goto L_0x0288
        L_0x01d7:
            android.view.ViewParent r11 = r0.getParent()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            boolean r1 = r11 instanceof android.widget.AdapterView     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r1 == 0) goto L_0x01ee
            java.lang.String r1 = "$p"
            r6.append(r1)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r1 = r11
            android.widget.AdapterView r1 = (android.widget.AdapterView) r1     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            int r1 = r1.getPositionForView(r0)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r6.append(r1)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x01ee:
            boolean r1 = r0 instanceof android.view.ViewGroup     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r1 == 0) goto L_0x0231
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r1.<init>()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            r3.<init>()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            a(r0, r1, r3)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r0 = "p"
            java.lang.String r4 = r6.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r0, r4)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            int r0 = r1.length()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r0 <= r10) goto L_0x021d
            java.lang.String r0 = "t"
            java.lang.String r1 = r1.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r1 = r1.substring(r10)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r0, r1)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x021d:
            int r0 = r3.length()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r0 <= r10) goto L_0x0276
            java.lang.String r0 = "m"
            java.lang.String r1 = r3.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r1 = r1.substring(r10)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r0, r1)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            goto L_0x0276
        L_0x0231:
            boolean r1 = r0 instanceof android.widget.TextView     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r1 == 0) goto L_0x025b
            r1 = r0
            android.widget.TextView r1 = (android.widget.TextView) r1     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            int r3 = r1.getInputType()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r3 == r8) goto L_0x0254
            if (r3 != r7) goto L_0x0241
            goto L_0x0254
        L_0x0241:
            java.lang.String r3 = "t"
            java.lang.CharSequence r1 = r1.getText()     // Catch:{ NullPointerException -> 0x024f }
            java.lang.String r1 = r1.toString()     // Catch:{ NullPointerException -> 0x024f }
            defpackage.cxj.c.a(r5, r3, r1)     // Catch:{ NullPointerException -> 0x024f }
            goto L_0x025b
        L_0x024f:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            goto L_0x025b
        L_0x0254:
            java.lang.String r1 = "t"
            java.lang.String r3 = "passwd"
            defpackage.cxj.c.a(r5, r1, r3)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x025b:
            java.lang.String r1 = "p"
            java.lang.String r3 = r6.toString()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            defpackage.cxj.c.a(r5, r1, r3)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            java.lang.String r1 = "m"
            java.lang.CharSequence r0 = r0.getContentDescription()     // Catch:{ NullPointerException -> 0x0272 }
            java.lang.String r0 = r0.toString()     // Catch:{ NullPointerException -> 0x0272 }
            defpackage.cxj.c.a(r5, r1, r0)     // Catch:{ NullPointerException -> 0x0272 }
            goto L_0x0276
        L_0x0272:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
        L_0x0276:
            java.lang.String r0 = "c"
            defpackage.cxj.c.a(r5, r0, r2)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            boolean r11 = r11 instanceof android.widget.AdapterView     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            if (r11 == 0) goto L_0x0283
            defpackage.cxl.a(r9, r5)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            return
        L_0x0283:
            r11 = 2
            defpackage.cxl.a(r11, r5)     // Catch:{ IllegalAccessException -> 0x028f, Exception -> 0x028a }
            return
        L_0x0288:
            return
        L_0x0289:
            return
        L_0x028a:
            r11 = move-exception
            r11.printStackTrace()
        L_0x028e:
            return
        L_0x028f:
            r11 = move-exception
            r11.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cxa.a(android.os.Message):void");
    }

    public static void b(@NonNull Message message) {
        try {
            Bundle data = message.getData();
            if (data != null) {
                String string = data.getString("_view_name", null);
                if (string != null) {
                    JSONObject jSONObject = new JSONObject();
                    c.a(jSONObject, SuperId.BIT_1_NEARBY_SEARCH, string);
                    switch (message.arg1) {
                        case InputDeviceCompat.SOURCE_DPAD /*513*/:
                            cxl.a(4, jSONObject);
                            break;
                        case 514:
                            cxl.a(5, jSONObject);
                            return;
                        case 515:
                            cxl.a(6, jSONObject);
                            return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void c(@NonNull Message message) {
        try {
            Bundle data = message.getData();
            if (data != null) {
                String string = data.getString("_view_name", null);
                if (string != null) {
                    JSONObject jSONObject = new JSONObject();
                    c.a(jSONObject, SuperId.BIT_1_NEARBY_SEARCH, string);
                    if (message.arg1 == 769) {
                        int i = data.getInt("_gesture_type");
                        int i2 = data.getInt("_gesture_sub_type");
                        StringBuilder sb = new StringBuilder();
                        sb.append(String.valueOf(i));
                        sb.append("$");
                        sb.append(String.valueOf(i2));
                        sb.append("$");
                        c.a(jSONObject, "i", sb.toString());
                        cxl.a(9, jSONObject);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(@NonNull Message message) {
        try {
            Bundle data = message.getData();
            if (data != null) {
                String string = data.getString("_ajx_property_", null);
                if (string != null) {
                    if (string.trim().length() > 0) {
                        JSONObject jSONObject = new JSONObject();
                        if (message.arg1 == 1025) {
                            JSONObject jSONObject2 = new JSONObject(string);
                            JSONObject jSONObject3 = new JSONObject();
                            if (jSONObject2.has("version")) {
                                c.a(jSONObject3, a.k, jSONObject2.getString("version"));
                            }
                            if (jSONObject2.has("path")) {
                                c.a(jSONObject, SuperId.BIT_1_NEARBY_SEARCH, jSONObject2.getString("path"));
                            }
                            if (jSONObject2.has("engineJson")) {
                                JSONObject jSONObject4 = new JSONObject(jSONObject2.getString("engineJson"));
                                if (jSONObject4.has("stack")) {
                                    c.a(jSONObject3, "st", jSONObject4.getString("stack"));
                                }
                                if (jSONObject4.has("content")) {
                                    c.a(jSONObject, "i", jSONObject4.getString("content"));
                                }
                            }
                            cxl.a(10, jSONObject, jSONObject3);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void e(@NonNull Message message) {
        try {
            Bundle data = message.getData();
            if (data != null) {
                String string = data.getString("_view_name", null);
                String string2 = data.getString("_page_name", null);
                if (string != null) {
                    if (string2 != null) {
                        JSONObject jSONObject = new JSONObject();
                        if (message.arg1 == 1281) {
                            c.a(jSONObject, LogItem.MM_C15_K4_TIME, string2);
                            c.a(jSONObject, SuperId.BIT_1_NEARBY_SEARCH, string);
                            cxl.a(11, jSONObject);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
