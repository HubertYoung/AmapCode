package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.webview.MultiTabWebView;

/* renamed from: dnj reason: default package */
/* compiled from: LandingPageHander */
public final class dnj implements OnClickListener {
    private static volatile dnj l;
    public View a = null;
    public RelativeLayout b = null;
    public MultiTabWebView c = null;
    public TitleBar d;
    public View e;
    public ImageButton f;
    public ImageButton g;
    public ImageButton h;
    public a i = null;
    public OnClickListener j = new OnClickListener() {
        public final void onClick(View view) {
            dnj.a(view);
            if (dnj.this.c.canGoBack()) {
                dnj.this.c.goBack();
                dnj.a(dnj.this.c, (View) dnj.this.f, (View) dnj.this.g);
                return;
            }
            dnj.d(dnj.this);
        }
    };
    public OnClickListener k = new OnClickListener() {
        public final void onClick(View view) {
            dnj.a(view);
            dnj.d(dnj.this);
        }
    };
    /* access modifiers changed from: private */
    public String m;

    /* renamed from: dnj$a */
    /* compiled from: LandingPageHander */
    public interface a {
        void a();
    }

    private dnj() {
    }

    public static dnj a() {
        if (l == null) {
            synchronized (dea.class) {
                if (l == null) {
                    l = new dnj();
                }
            }
        }
        return l;
    }

    public final void onClick(View view) {
        if (view == this.f) {
            this.c.goBack();
            a(this.c, (View) this.f, (View) this.g);
        } else if (view == this.g) {
            this.c.goForward();
            a(this.c, (View) this.f, (View) this.g);
        } else {
            if (view == this.h) {
                this.c.reload();
            }
        }
    }

    public static void a(MultiTabWebView multiTabWebView, View view, View view2) {
        if (multiTabWebView != null && view != null && view2 != null) {
            view.setEnabled(multiTabWebView.canGoBack());
            view2.setEnabled(multiTabWebView.canGoForward());
        }
    }

    public final void b() {
        this.i = null;
        if (this.a != null) {
            this.a.setOnKeyListener(null);
        }
    }

    static /* synthetic */ void a(View view) {
        if (view != null) {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    static /* synthetic */ void d(dnj dnj) {
        if (dnj.i != null) {
            dnj.i.a();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005b, code lost:
        if (r0.find() == false) goto L_0x005f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0067  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ void a(defpackage.dnj r7, java.lang.String r8, java.lang.String r9) {
        /*
            if (r9 == 0) goto L_0x0089
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x005e
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L_0x0011
            goto L_0x005e
        L_0x0011:
            java.lang.String r0 = r9.substring(r2, r1)
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 == 0) goto L_0x001d
        L_0x001b:
            r0 = 0
            goto L_0x0044
        L_0x001d:
            char[] r0 = r0.toCharArray()
            int r3 = r0.length
            r4 = 0
        L_0x0023:
            if (r4 >= r3) goto L_0x001b
            char r5 = r0[r4]
            java.lang.Character$UnicodeBlock r5 = java.lang.Character.UnicodeBlock.of(r5)
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            if (r5 == r6) goto L_0x0043
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            if (r5 == r6) goto L_0x0043
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            if (r5 == r6) goto L_0x0043
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
            if (r5 == r6) goto L_0x0043
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            if (r5 != r6) goto L_0x0040
            goto L_0x0043
        L_0x0040:
            int r4 = r4 + 1
            goto L_0x0023
        L_0x0043:
            r0 = 1
        L_0x0044:
            if (r0 == 0) goto L_0x0047
            goto L_0x005f
        L_0x0047:
            java.lang.String r0 = "((http|https)://)?[A-Za-z0-9_]+([.][A-Za-z0-9_]+)*(/[A-Za-z0-9_]+)*([?][A-Za-z0-9_]+=[A-Za-z0-9_]+)?([&][A-Za-z0-9_]+=[A-Za-z0-9_]+)*([.](html|htm))*$"
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)
            java.util.regex.Matcher r0 = r0.matcher(r9)
            boolean r8 = r8.equals(r9)
            if (r8 != 0) goto L_0x005e
            boolean r8 = r0.find()
            if (r8 != 0) goto L_0x005e
            goto L_0x005f
        L_0x005e:
            r1 = 0
        L_0x005f:
            if (r1 == 0) goto L_0x0067
            com.autonavi.widget.ui.TitleBar r7 = r7.d
            r7.setTitle(r9)
            return
        L_0x0067:
            java.lang.String r8 = "file:///android_asset/connect_error.html"
            boolean r8 = r8.contains(r9)
            if (r8 != 0) goto L_0x0080
            java.lang.String r8 = "file:///android_asset/not_found_error.html"
            boolean r8 = r8.contains(r9)
            if (r8 == 0) goto L_0x0078
            goto L_0x0080
        L_0x0078:
            com.autonavi.widget.ui.TitleBar r7 = r7.d
            java.lang.String r8 = ""
            r7.setTitle(r8)
            goto L_0x0089
        L_0x0080:
            com.autonavi.widget.ui.TitleBar r7 = r7.d
            java.lang.String r8 = "出错了"
            r7.setTitle(r8)
            return
        L_0x0089:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dnj.a(dnj, java.lang.String, java.lang.String):void");
    }
}
