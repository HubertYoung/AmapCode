package defpackage;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.Locale;

/* renamed from: ewo reason: default package */
/* compiled from: ParamTemp */
public class ewo {
    private static ewo K = new ewo();
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String H;
    private String I;
    private String J;
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public boolean j = false;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;
    private String t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;
    private String z;

    public final String a(Context context) {
        if (TextUtils.isEmpty(this.q)) {
            this.q = euw.p(context);
        }
        return this.q;
    }

    public final String b(Context context) {
        if (TextUtils.isEmpty(this.n)) {
            this.n = euw.w(context);
        }
        return this.n;
    }

    public final String c(Context context) {
        if (TextUtils.isEmpty(this.o)) {
            this.o = euw.q(context);
        }
        return this.o;
    }

    public final String d(Context context) {
        if (TextUtils.isEmpty(this.l)) {
            this.l = euw.d(context);
        }
        return this.l;
    }

    public final String a() {
        if (TextUtils.isEmpty(this.v)) {
            this.v = euw.j();
        }
        return this.v;
    }

    public final String e(Context context) {
        if (TextUtils.isEmpty(this.p)) {
            this.p = euw.C(context);
        }
        return this.p;
    }

    public final String f(Context context) {
        if (TextUtils.isEmpty(this.u)) {
            this.u = euw.t(context);
        }
        return this.u;
    }

    public final String g(Context context) {
        if (TextUtils.isEmpty(this.A)) {
            this.A = euw.u(context);
        }
        return this.A;
    }

    public final String h(Context context) {
        if (TextUtils.isEmpty(this.D)) {
            this.D = euw.n(context);
        }
        return this.D;
    }

    public final String i(Context context) {
        if (TextUtils.isEmpty(this.C)) {
            this.C = euw.e(context);
        }
        return this.C;
    }

    public final String j(Context context) {
        if (TextUtils.isEmpty(this.B)) {
            this.B = euw.g(context);
        }
        return this.B;
    }

    public final String b() {
        if (TextUtils.isEmpty(this.t)) {
            this.t = euw.d();
        }
        return this.t;
    }

    public final String k(Context context) {
        if (TextUtils.isEmpty(this.m)) {
            this.m = euw.B(context);
        }
        return this.m;
    }

    public final String c() {
        if (TextUtils.isEmpty(this.x)) {
            this.x = Build.MANUFACTURER;
        }
        return this.x;
    }

    public final String l(Context context) {
        if (TextUtils.isEmpty(this.J)) {
            this.J = euw.k(context);
        }
        return this.J;
    }

    public final String d() {
        if (TextUtils.isEmpty(this.y)) {
            this.y = Build.MODEL;
        }
        return this.y;
    }

    public final String e() {
        if (TextUtils.isEmpty(this.k)) {
            this.k = "0";
        }
        return this.k;
    }

    public final String f() {
        if (TextUtils.isEmpty(this.r)) {
            this.r = euw.b();
        }
        return this.r;
    }

    public final String m(Context context) {
        if (TextUtils.isEmpty(this.z)) {
            this.z = context.getPackageName();
        }
        return this.z;
    }

    public final String g() {
        if (TextUtils.isEmpty(this.w)) {
            this.w = Build.PRODUCT;
        }
        return this.w;
    }

    public final String n(Context context) {
        if (TextUtils.isEmpty(this.F)) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
            switch (euw.K(context)) {
                case 0:
                    StringBuilder sb = new StringBuilder();
                    sb.append(displayMetrics.widthPixels);
                    sb.append(DictionaryKeys.CTRLXY_X);
                    sb.append(displayMetrics.heightPixels);
                    this.F = sb.toString();
                    break;
                case 1:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(displayMetrics.heightPixels);
                    sb2.append(DictionaryKeys.CTRLXY_X);
                    sb2.append(displayMetrics.widthPixels);
                    this.F = sb2.toString();
                    break;
                case 2:
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(displayMetrics.widthPixels);
                    sb3.append(DictionaryKeys.CTRLXY_X);
                    sb3.append(displayMetrics.heightPixels);
                    this.F = sb3.toString();
                    break;
                case 3:
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(displayMetrics.heightPixels);
                    sb4.append(DictionaryKeys.CTRLXY_X);
                    sb4.append(displayMetrics.widthPixels);
                    this.F = sb4.toString();
                    break;
            }
        }
        return this.F;
    }

    private ewo() {
    }

    public static synchronized void o(Context context) {
        synchronized (ewo.class) {
            boolean z2 = true;
            K.j = true;
            K.k = "0";
            K.l = euw.d(context);
            K.B = euw.g(context);
            K.C = euw.e(context);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
            ewo ewo = K;
            StringBuilder sb = new StringBuilder();
            sb.append(displayMetrics.widthPixels);
            sb.append(DictionaryKeys.CTRLXY_X);
            sb.append(displayMetrics.heightPixels);
            ewo.F = sb.toString();
            K.o = euw.q(context);
            K.n = euw.w(context);
            K.q = euw.p(context);
            K.m = euw.B(context);
            K.p = euw.C(context);
            K.b = euw.p(context);
            K.v = euw.j();
            K.r = euw.b();
            K.A = euw.u(context);
            K.D = euw.n(context);
            K.u = euw.t(context);
            K.s = euw.s(context);
            K.t = euw.d();
            K.c = Locale.getDefault().getLanguage();
            K.w = Build.PRODUCT;
            K.x = Build.MANUFACTURER;
            K.y = Build.MODEL;
            K.z = context.getPackageName();
            K.J = euw.k(context);
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            ewo ewo2 = K;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(defaultAdapter != null);
            ewo2.d = sb2.toString();
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            ewo ewo3 = K;
            StringBuilder sb3 = new StringBuilder();
            if (locationManager == null) {
                z2 = false;
            }
            sb3.append(z2);
            ewo3.e = sb3.toString();
            ewo ewo4 = K;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(euw.l(context));
            ewo4.f = sb4.toString();
            K.g = euw.A(context);
            K.h = "true";
            ewo ewo5 = K;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(euw.e());
            ewo5.i = sb5.toString();
            K.H = euw.h();
            K.G = euw.g();
            K.I = euw.i();
            K.E = euw.f();
        }
    }

    public static synchronized ewo h() {
        ewo ewo;
        synchronized (ewo.class) {
            try {
                if (!K.j) {
                    ewo.class.getSimpleName();
                    euw.a((String) "not initialized, call initializeInstance(..) method first.");
                }
                ewo = K;
            }
        }
        return ewo;
    }
}
