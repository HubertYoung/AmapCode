package com.jiuyan.inimage.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.jiuyan.inimage.paster.ViewOperation;
import com.jiuyan.inimage.paster.a;
import com.jiuyan.inimage.paster.d;
import com.jiuyan.inimage.paster.h;
import com.jiuyan.inimage.util.DisplayUtil;
import com.jiuyan.inimage.util.b;
import com.jiuyan.inimage.util.o;
import com.jiuyan.inimage.util.p;
import com.jiuyan.inimage.util.q;

public class TextWaterMarkView extends ComponentView implements OnClickListener, ac {
    /* access modifiers changed from: private */
    public final String c;
    /* access modifiers changed from: private */
    public TextWaterMarkBottomView d;
    private boolean e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private float m;
    private View n;
    private ImageView o;
    private ViewOperation p;
    /* access modifiers changed from: private */
    public h q;
    /* access modifiers changed from: private */
    public EditTextPreIme r;
    /* access modifiers changed from: private */
    public boolean s;
    /* access modifiers changed from: private */
    public String t;

    public boolean c() {
        return this.e;
    }

    public TextWaterMarkView(Context context) {
        this(context, null);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public TextWaterMarkView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextWaterMarkView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.c = TextWaterMarkView.class.getSimpleName();
        this.e = false;
        this.s = true;
    }

    public void a() {
        int[] screenSize = DisplayUtil.getScreenSize(getContext());
        this.j = screenSize[0];
        this.k = screenSize[1];
        this.l = this.k - DisplayUtil.getStatusBarHeight(getContext());
        this.n = findViewById(R.id.image_wrapper);
        this.o = (ImageView) findViewById(R.id.image);
        this.p = (ViewOperation) findViewById(R.id.view_operation);
        this.r = (EditTextPreIme) findViewById(R.id.et_content);
        this.t = getContext().getString(R.string.watermark_default_text);
        int a = a(this.t);
        if (a > 0) {
            this.r.setMinWidth(a);
        }
        setOnClickListener(this);
        g();
        h();
    }

    private int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        Paint paint = new Paint();
        paint.setTextSize(this.r.getTextSize());
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    private void g() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        this.d = (TextWaterMarkBottomView) findViewById(R.id.ll_bottom_bar);
        this.d.setOnTextColorChangedListener(this);
    }

    private void h() {
        this.r.setOnEditorActionListener(new ad(this));
        this.r.setOnBackPressListener(new ae(this));
        this.p.setOnCustomEventListener(new af(this));
        this.r.addTextChangedListener(new ag(this));
        new o(this).a((p) new ah(this));
    }

    public void a(int i2) {
        if (this.q != null) {
            this.q.a(i2);
        }
        if (this.q != null) {
            this.r.setHintTextColor(this.d.getSelectedColor());
        }
    }

    public void setBitmapPreviously(Bitmap bitmap) {
        if (this.o != null) {
            this.o.setImageBitmap(bitmap);
        }
    }

    public void d() {
        i();
        setVisibility(4);
        post(new ai(this));
    }

    private void i() {
        this.d.a();
        if (b.a != null) {
            this.g = b.a.getWidth();
            this.f = b.a.getHeight();
            int i2 = this.j;
            int i3 = this.l;
            float f2 = (float) this.f;
            float f3 = (float) this.g;
            float f4 = f3 / f2;
            this.m = ((float) i2) / f3;
            this.h = (int) (this.m * f2);
            this.i = (int) (this.m * f3);
            if (this.h > i3) {
                this.h = i3;
                this.i = (int) (((float) this.h) * f4);
                this.m = ((float) this.h) / f2;
            }
            LayoutParams layoutParams = this.n.getLayoutParams();
            layoutParams.width = this.i;
            layoutParams.height = this.h;
            this.n.setLayoutParams(layoutParams);
            this.o.setImageBitmap(b.a);
            if (this.q == null || this.t.equals(this.q.d())) {
                this.d.a();
                if (this.q != null) {
                    this.p.a((d) this.q);
                }
                this.q = new h(getContext());
                this.q.c(true);
                Matrix matrix = new Matrix();
                Rect a = this.q.a();
                matrix.postTranslate((float) ((this.i - a.width()) / 2), (float) ((this.h - a.height()) / 2));
                this.q.a(matrix);
                this.q.a(this.d.getSelectedColor());
                this.q.a((a) this.p);
                this.p.a((d) this.q, true);
                return;
            }
            this.q.c(true);
            this.d.setSelectedColor(this.q.e());
            this.p.a((d) this.q, true);
        }
    }

    public void a(h hVar) {
        this.q = hVar;
        if (this.q != null) {
            this.q.a((a) this.p);
        }
    }

    /* access modifiers changed from: private */
    public void j() {
        String str;
        setEditTextHintEnabled(false);
        String str2 = null;
        if (this.q != null) {
            str2 = this.q.d();
        }
        if (this.t.equals(str2)) {
            str2 = "";
        }
        if (str2 == null) {
            str = "";
        } else {
            str = str2;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.r.getLayoutParams();
        layoutParams.bottomMargin = this.k / 2;
        this.r.setLayoutParams(layoutParams);
        this.r.setText(str);
        this.r.setVisibility(0);
        this.r.setTextColor(this.d.getSelectedColor());
        this.r.setHintTextColor(this.d.getSelectedColor());
        this.r.setSelection(str.length());
        this.r.setFocusable(true);
        this.r.setFocusableInTouchMode(true);
        this.r.requestFocus();
        a(getContext(), this.r);
        if (this.p.getObjects().contains(this.q)) {
            this.p.a((d) this.q);
        }
        this.s = false;
    }

    /* access modifiers changed from: private */
    public void k() {
        this.r.clearFocus();
        a(getContext());
        this.r.setVisibility(8);
        if (this.q != null && !this.p.getObjects().contains(this.q)) {
            this.p.a((d) this.q, true);
        }
        this.s = true;
        setEditTextHintEnabled(true);
    }

    private void setEditTextHintEnabled(boolean z) {
        if (this.r == null) {
            return;
        }
        if (z) {
            this.r.setHint(R.string.watermark_default_text);
        } else {
            this.r.setHint("");
        }
    }

    /* access modifiers changed from: private */
    public void l() {
        a(this.d, true, null);
    }

    public void e() {
        if (b()) {
            a(this.d, false, new aj(this));
        }
    }

    public h getTextObject() {
        if (this.q == null || !this.t.equals(this.q.d())) {
            return this.q;
        }
        return null;
    }

    public void f() {
        this.p.b();
        this.q = null;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            q.a("watermardk", H5Param.DEFAULT_LONG_BACK_BEHAVIOR);
            if (this.b != null) {
                this.b.onComponentBack(Integer.valueOf(3), Integer.valueOf(0), null);
            }
            f();
        } else if (id == R.id.btn_next) {
            q.a("watermardk", AudioUtils.CMDNEXT);
            if (this.b != null) {
                this.b.onComponentDone(Integer.valueOf(3), Integer.valueOf(0), null);
            }
        } else if (view == this) {
            q.a("watermardk", "block area to back");
            k();
        }
    }

    public static void a(Context context) {
        Activity activity = (Activity) context;
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
            if (inputMethodManager.isActive() && activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static void a(Context context, EditText editText) {
        if (editText != null) {
            ((InputMethodManager) context.getSystemService("input_method")).showSoftInput(editText, 0);
        }
    }
}
