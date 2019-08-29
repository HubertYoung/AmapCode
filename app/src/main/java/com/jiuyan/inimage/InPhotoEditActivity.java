package com.jiuyan.inimage;

import android.animation.Animator.AnimatorListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.jiuyan.inimage.bean.BeanPaster;
import com.jiuyan.inimage.bean.BeanPublishSticker;
import com.jiuyan.inimage.callback.ComponentLister;
import com.jiuyan.inimage.callback.DefaultAnimatorListener;
import com.jiuyan.inimage.callback.OnMagicWandClickListener;
import com.jiuyan.inimage.f.e;
import com.jiuyan.inimage.f.f;
import com.jiuyan.inimage.paster.ViewOperation;
import com.jiuyan.inimage.paster.d;
import com.jiuyan.inimage.paster.h;
import com.jiuyan.inimage.paster.j;
import com.jiuyan.inimage.util.DisplayUtil;
import com.jiuyan.inimage.util.a;
import com.jiuyan.inimage.util.b;
import com.jiuyan.inimage.util.g;
import com.jiuyan.inimage.util.i;
import com.jiuyan.inimage.util.n;
import com.jiuyan.inimage.util.q;
import com.jiuyan.inimage.widget.CropperView;
import com.jiuyan.inimage.widget.MagicWandView;
import com.jiuyan.inimage.widget.RelationFrameLayout;
import com.jiuyan.inimage.widget.RotateView;
import com.jiuyan.inimage.widget.TextWaterMarkView;
import com.jiuyan.inimage.widget.y;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class InPhotoEditActivity extends BaseActivity implements OnClickListener, ComponentLister, OnMagicWandClickListener, y {
    /* access modifiers changed from: private */
    public RotateView A;
    /* access modifiers changed from: private */
    public TextWaterMarkView B;
    private H5Receiver C;
    private boolean D;
    private boolean E;
    private boolean F;
    private List<BeanPublishSticker> G;
    private RectF H = new RectF();
    private RectF I = new RectF();
    /* access modifiers changed from: private */
    public volatile boolean J = false;
    private h K;
    /* access modifiers changed from: private */
    public boolean L = false;
    private boolean M = true;
    private int[] N = new int[5];
    private int O = -1;
    /* access modifiers changed from: private */
    public boolean P = false;
    private j Q = new a(this);
    /* access modifiers changed from: private */
    public boolean R = false;
    /* access modifiers changed from: private */
    public boolean S = true;
    private OnPreDrawListener T = new v(this);
    boolean a = false;
    boolean b = false;
    private com.jiuyan.inimage.util.h d;
    private FrameLayout e;
    /* access modifiers changed from: private */
    public e f;
    private ViewOperation g;
    private ImageView h;
    private View i;
    /* access modifiers changed from: private */
    public ImageView j;
    private MagicWandView k;
    /* access modifiers changed from: private */
    public RelationFrameLayout l;
    private Bitmap m;
    /* access modifiers changed from: private */
    public int n;
    /* access modifiers changed from: private */
    public int o;
    /* access modifiers changed from: private */
    public int p;
    /* access modifiers changed from: private */
    public int q;
    private int r;
    /* access modifiers changed from: private */
    public int s;
    /* access modifiers changed from: private */
    public int t;
    /* access modifiers changed from: private */
    public float u;
    private View v;
    private View w;
    private View x;
    private View y;
    /* access modifiers changed from: private */
    public CropperView z;

    public class H5Receiver extends BroadcastReceiver {
        public H5Receiver() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            q.a("H5Receiver", "action: " + action);
            String h5Action = InSDKEntrance.getH5Action();
            if (TextUtils.isEmpty(h5Action) || !h5Action.equals(action)) {
                q.a("H5Receiver", "action is null");
                return;
            }
            String stringExtra = intent.getStringExtra("id");
            String stringExtra2 = intent.getStringExtra("url");
            q.a("H5Receiver", "id: " + stringExtra + " url: " + stringExtra2);
            BeanPaster beanPaster = new BeanPaster();
            beanPaster.id = stringExtra;
            beanPaster.url = stringExtra2;
            InPhotoEditActivity.this.a(beanPaster);
        }
    }

    public InPhotoEditActivity() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.in_alipay_layout_photo_edit_activity);
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("insdk_param_1", false)) {
            getWindow().addFlags(8192);
        }
        q.a((String) "on InPhotoEditActivity create");
        g.a(this);
        c();
        q.a((String) "on view inited");
        i();
        s();
        q.a((String) "on broadcast registered");
    }

    public void a() {
        this.l.setVisibility(8);
        this.b = false;
        b();
    }

    /* access modifiers changed from: private */
    public void f() {
        boolean z2 = true;
        if (this.a) {
            this.b = false;
        } else {
            this.b = true;
        }
        if (this.a) {
            z2 = false;
        }
        a(z2);
    }

    public void b() {
        if (this.a) {
            a(!this.a);
        }
    }

    public void a(boolean z2) {
        boolean z3;
        int i2;
        if ((!this.b || z2) && this.a != z2) {
            int height = findViewById(R.id.layout_hide_wrapper).getHeight();
            if (!this.a) {
                z3 = true;
            } else {
                z3 = false;
            }
            this.a = z3;
            View view = this.i;
            if (this.a) {
                i2 = height;
            } else {
                i2 = 0;
            }
            a.a(view, i2);
            if (this.a) {
                a.b(this.h, 180);
            } else {
                a.b(this.h, 0);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        e();
        t();
    }

    /* access modifiers changed from: protected */
    public void c() {
        this.d = new com.jiuyan.inimage.util.h(this);
        int[] screenSize = DisplayUtil.getScreenSize(getApplicationContext());
        this.r = screenSize[0];
        this.s = screenSize[1];
        this.t = this.s;
        this.e = (FrameLayout) findViewById(R.id.fl_publish_core);
        this.k = (MagicWandView) findViewById(R.id.iv_publish_toolbar_random);
        this.j = (ImageView) findViewById(R.id.gpuiv_publish);
        this.g = (ViewOperation) findViewById(R.id.v_publish_sticker_layer);
        this.l = (RelationFrameLayout) findViewById(R.id.ll_paster_relation);
        this.h = (ImageView) findViewById(R.id.iv_publish_toolbar_hide);
        this.i = findViewById(R.id.ll_hide_wrapper_container);
        this.f = new e(this, new n(this), this.g);
        this.v = findViewById(R.id.ll_crop);
        this.y = findViewById(R.id.ll_text);
        this.w = findViewById(R.id.ll_rotate);
        this.x = findViewById(R.id.ll_mall);
        this.z = (CropperView) findViewById(R.id.inalipay_cropper_view);
        this.z.setComponentListener(this);
        this.A = (RotateView) findViewById(R.id.inalipay_rotate_view);
        this.A.setComponentListener(this);
        this.B = (TextWaterMarkView) findViewById(R.id.inalipay_watermark_view);
        this.B.setComponentListener(this);
        Intent intent = getIntent();
        int intExtra = intent.getIntExtra("insdk_param", 0);
        if (InSDKConfig.isEnabled(intExtra, -1)) {
            this.k.setVisibility(0);
            findViewById(R.id.ll_crop).setVisibility(0);
            findViewById(R.id.ll_rotate).setVisibility(0);
            findViewById(R.id.ll_mall).setVisibility(0);
            this.L = false;
            this.M = true;
            b(intExtra);
        } else {
            a(this.v, InSDKConfig.isEnabled(intExtra, 1));
            a(this.y, InSDKConfig.isEnabled(intExtra, 16));
            a(this.x, InSDKConfig.isEnabled(intExtra, 8));
            a(this.w, InSDKConfig.isEnabled(intExtra, 2));
            a((View) this.k, InSDKConfig.isEnabled(intExtra, 4));
            this.L = InSDKConfig.isEnabled(intExtra, 1) && !InSDKConfig.isEnabled(intExtra, 16) && InSDKConfig.isEnabled(intExtra, 8) && InSDKConfig.isEnabled(intExtra, 2) && InSDKConfig.isEnabled(intExtra, 4);
            this.M = InSDKConfig.isEnabled(intExtra, 1);
            b(intExtra);
            if (h()) {
                q.a("is one func");
                this.P = true;
                int g2 = g();
                if (g2 != -1) {
                    this.O = g2;
                }
                q.a("is one func " + this.O);
                if (this.O == 0) {
                    if (this.z != null) {
                        this.z.setBitmapPreviously(b.a);
                        this.z.setShowWithAnimationEnabled(false);
                    }
                } else if (this.O == 1) {
                    if (this.A != null) {
                        this.A.setBitmapPreviously(b.a);
                        this.A.setShowWithAnimationEnabled(false);
                    }
                } else if (this.O == 3 && this.B != null) {
                    this.B.setBitmapPreviously(b.a);
                    this.B.setShowWithAnimationEnabled(false);
                }
            }
        }
        a(findViewById(R.id.tv_edit_cancel), intent.getStringExtra(InSDKConfig.KEY_CONFIG_TEXT_LEFT));
        a(findViewById(R.id.tv_edit_finish), intent.getStringExtra(InSDKConfig.KEY_CONFIG_TEXT_RIGHT));
        a(findViewById(R.id.tv_crop), intent.getStringExtra(InSDKConfig.KEY_CONFIG_TEXT_FUNC_CROPPER));
        a(findViewById(R.id.tv_rotate), intent.getStringExtra(InSDKConfig.KEY_CONFIG_TEXT_FUNC_ROTATION));
        a(findViewById(R.id.tv_mall), intent.getStringExtra(InSDKConfig.KEY_CONFIG_TEXT_FUNC_PASTERMALL));
        a(findViewById(R.id.tv_text), intent.getStringExtra(InSDKConfig.KEY_CONFIG_TEXT_FUNC_TEXT));
    }

    private void b(int i2) {
        int i3;
        int i4;
        int i5;
        int i6 = 1;
        this.N[0] = InSDKConfig.isEnabled(i2, 1) ? 1 : 0;
        int[] iArr = this.N;
        if (InSDKConfig.isEnabled(i2, 2)) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        iArr[1] = i3;
        int[] iArr2 = this.N;
        if (InSDKConfig.isEnabled(i2, 8)) {
            i4 = 1;
        } else {
            i4 = 0;
        }
        iArr2[2] = i4;
        int[] iArr3 = this.N;
        if (InSDKConfig.isEnabled(i2, 16)) {
            i5 = 1;
        } else {
            i5 = 0;
        }
        iArr3[3] = i5;
        int[] iArr4 = this.N;
        if (!InSDKConfig.isEnabled(i2, 4)) {
            i6 = 0;
        }
        iArr4[4] = i6;
    }

    private int g() {
        for (int i2 = 0; i2 < this.N.length; i2++) {
            q.a("func" + i2);
            if (this.N[i2] == 1) {
                return i2;
            }
        }
        return -1;
    }

    private boolean h() {
        int i2 = 0;
        for (int i3 : this.N) {
            i2 += i3;
        }
        q.a("func only one ? " + i2);
        if (i2 == 1) {
            return true;
        }
        return false;
    }

    private void a(View view, boolean z2) {
        view.setVisibility(z2 ? 0 : 8);
    }

    private void a(View view, String str) {
        if (!TextUtils.isEmpty(str) && (view instanceof TextView)) {
            ((TextView) view).setText(str);
        }
    }

    private void i() {
        this.g.setOnCustomEventListener(this.Q);
        this.f.a((f) new p(this));
        this.e.getViewTreeObserver().addOnPreDrawListener(this.T);
        this.l.setOnPasterSelectedListener(this);
        this.k.setOnMagicWandClickListener(this);
        findViewById(R.id.ll_crop).setOnClickListener(this);
        findViewById(R.id.ll_rotate).setOnClickListener(this);
        findViewById(R.id.ll_mall).setOnClickListener(this);
        findViewById(R.id.ll_text).setOnClickListener(this);
        findViewById(R.id.tv_edit_cancel).setOnClickListener(this);
        findViewById(R.id.tv_edit_finish).setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        view.setClickable(false);
        view.postDelayed(new q(this, view), 500);
        if (id == R.id.tv_edit_cancel) {
            n();
        } else if (id == R.id.tv_edit_finish) {
            r();
        } else if (id == R.id.iv_publish_toolbar_hide) {
            f();
        } else if (id == R.id.ll_crop) {
            j();
            a(false, (AnimatorListener) new DefaultAnimatorListener(this, 0));
        } else if (id == R.id.ll_rotate) {
            j();
            a(false, (AnimatorListener) new DefaultAnimatorListener(this, 1));
        } else if (id == R.id.ll_text) {
            j();
            a(false, (AnimatorListener) new DefaultAnimatorListener(this, 2));
        } else if (id == R.id.ll_mall) {
            m();
        }
    }

    private void j() {
        if (this.f.e() > 0) {
            this.G = this.f.d();
            this.f.a(this.H);
        }
    }

    private void k() {
        if (this.G != null && this.G.size() > 0) {
            this.f.a(this.I);
            this.f.a(this.G, this.H, this.I);
        }
        if (this.g.getObjects().size() > 0) {
            for (d next : this.g.getObjects()) {
                if (next instanceof h) {
                    this.K = (h) next;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void l() {
        if (this.G != null) {
            this.G.clear();
        }
        this.G = null;
    }

    private void m() {
        Bundle bundle = new Bundle();
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(null, InSDKEntrance.getH5PasterAppId(), bundle);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.J) {
            return true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private void n() {
        if (q()) {
            if (InSDKEntrance.sEditCallback != null) {
                InSDKEntrance.sEditCallback.onEditCancel();
            }
            finish();
            return;
        }
        a(getString(R.string.in_sdk_cancle_edit), (Runnable) new r(this));
    }

    private void a(String str, Runnable runnable) {
        alert(null, str, getString(R.string.in_sdk_dialog_confirm), new s(this, runnable), getString(R.string.in_sdk_dialog_cancel), new t(this), Boolean.valueOf(false), Boolean.valueOf(true));
    }

    public void a(boolean z2, AnimatorListener animatorListener) {
        if (!z2) {
            a.a(this.i, true, animatorListener);
        } else {
            a.a(this.i, false, animatorListener);
        }
    }

    private void c(boolean z2) {
        int i2 = this.r;
        int i3 = this.t;
        float f2 = (float) this.n;
        float f3 = (float) this.o;
        float f4 = f3 / f2;
        this.u = ((float) i2) / f3;
        this.p = (int) (this.u * f2);
        this.q = (int) (this.u * f3);
        if (this.p > i3) {
            this.p = i3;
            this.q = (int) (((float) this.p) * f4);
            this.u = ((float) this.p) / f2;
        }
        if (z2 && this.q > this.o) {
            Matrix matrix = new Matrix();
            matrix.postScale(this.u, this.u);
            Bitmap createBitmap = Bitmap.createBitmap(this.m, 0, 0, this.o, this.n, matrix, false);
            this.m = createBitmap;
            this.n = createBitmap.getHeight();
            this.o = createBitmap.getWidth();
            this.u = 1.0f;
        }
        LayoutParams layoutParams = this.e.getLayoutParams();
        layoutParams.width = this.q;
        layoutParams.height = this.p;
        this.e.setLayoutParams(layoutParams);
        this.j.setImageBitmap(this.m);
        d();
        if (InSDKConfig.isEnabled(getIntent().getIntExtra("insdk_param", 0), -1)) {
            this.k.a(this.m);
        }
        if (a(this.o, this.n)) {
            if ((this.L && SharedPreUtils.getIntData(this, "key_guide") != 1) || h()) {
                return;
            }
            if (this.M) {
                this.j.postDelayed(new u(this), 200);
            }
        }
        q.a("setBitmapAndView " + z2 + ", index" + this.O);
    }

    public void d() {
        this.f.f();
    }

    public void e() {
        this.f.g();
    }

    /* access modifiers changed from: private */
    public boolean a(int i2, int i3) {
        float f2 = ((float) i2) / ((float) i3);
        if (((float) i3) / ((float) i2) > 1.79f && i3 > i2) {
            return true;
        }
        if (f2 <= 1.79f || i2 <= i3) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void o() {
        if (SharedPreUtils.getIntData(this, "key_guide") != 1) {
            ((ViewStub) findViewById(R.id.inalipay_guide_view_stub)).inflate();
            findViewById(R.id.inalipay_guide_view).setOnClickListener(new b(this));
            SharedPreUtils.putData((Context) this, (String) "key_guide", 1);
        }
    }

    /* access modifiers changed from: private */
    public void p() {
        int i2;
        int i3;
        int i4 = 0;
        if (this.P) {
            q.a("startOneFuncComponent " + this.O);
            if (this.O == 0) {
                q.a("go to crop func");
                onClick(findViewById(R.id.ll_crop));
            } else if (this.O == 1) {
                q.a("go to rotate func");
                onClick(findViewById(R.id.ll_rotate));
            } else if (this.O != 2) {
                if (this.O == 3) {
                    q.a("go to text func");
                    onClick(findViewById(R.id.ll_text));
                } else if (this.O == 4) {
                }
            }
            CropperView cropperView = this.z;
            if (this.O == 0) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            cropperView.setVisibility(i2);
            RotateView rotateView = this.A;
            if (this.O == 1) {
                i3 = 0;
            } else {
                i3 = 8;
            }
            rotateView.setVisibility(i3);
            TextWaterMarkView textWaterMarkView = this.B;
            if (this.O != 3) {
                i4 = 8;
            }
            textWaterMarkView.setVisibility(i4);
        }
    }

    public void a(Bitmap bitmap) {
        if (bitmap == null) {
            q.a((String) "bitmap is null");
        } else {
            q.a("bitmap is " + bitmap.hashCode());
        }
        if (bitmap != null) {
            this.m = bitmap;
            this.n = this.m.getHeight();
            this.o = this.m.getWidth();
            c(true);
        }
    }

    public void a(String str) {
        b(str);
    }

    private void b(String str) {
        c(str);
        c(false);
    }

    private void c(String str) {
        i a2 = this.d.a(Uri.parse("file://" + str));
        if (2 == a2.b) {
            this.m = a2.a;
            this.n = a2.a.getHeight();
            this.o = a2.a.getWidth();
        }
    }

    public void b(boolean z2) {
        if (z2) {
            showProgressDialog(null);
        } else {
            dismissProgressDialog();
        }
    }

    private boolean q() {
        return !this.D && !this.E && !this.f.a();
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00f8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void r() {
        /*
            r13 = this;
            r11 = 2047148045(0x7a05000d, float:1.7264413E35)
            r10 = 1
            r1 = 0
            r9 = 0
            com.jiuyan.inimage.f.e r0 = r13.f
            r0.c()
            com.jiuyan.inimage.f.e r0 = r13.f
            boolean r0 = r0.a()
            boolean r2 = r13.D
            if (r2 != 0) goto L_0x0039
            boolean r2 = r13.E
            if (r2 != 0) goto L_0x0039
            if (r0 != 0) goto L_0x0039
            boolean r0 = r13.F
            if (r0 != 0) goto L_0x0039
            com.jiuyan.inimage.callback.IEditCallback r0 = com.jiuyan.inimage.InSDKEntrance.sEditCallback
            if (r0 == 0) goto L_0x0033
            com.jiuyan.inimage.callback.IEditCallback r0 = com.jiuyan.inimage.InSDKEntrance.sEditCallback
            android.graphics.Bitmap r1 = r13.m
            r0.onEditNothing(r1)
            java.lang.String r0 = "sEditCallback getresult succ do nothing"
            com.jiuyan.inimage.util.q.a(r0)
        L_0x002f:
            r13.finish()
        L_0x0032:
            return
        L_0x0033:
            java.lang.String r0 = "sEditCallback is null do nothing"
            com.jiuyan.inimage.util.q.a(r0)
            goto L_0x002f
        L_0x0039:
            com.jiuyan.inimage.f.e r0 = r13.f
            boolean r0 = r0.a()
            if (r0 == 0) goto L_0x00cd
            com.jiuyan.inimage.f.e r0 = r13.f
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r0 = r0.a(r2)
        L_0x0049:
            int r2 = r13.o
            float r2 = (float) r2
            int r3 = r13.q
            float r3 = (float) r3
            float r3 = r2 / r3
            int r2 = r13.o     // Catch:{ Exception -> 0x00d0, OutOfMemoryError -> 0x00e4 }
            int r4 = r13.n     // Catch:{ Exception -> 0x00d0, OutOfMemoryError -> 0x00e4 }
            android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.ARGB_8888     // Catch:{ Exception -> 0x00d0, OutOfMemoryError -> 0x00e4 }
            android.graphics.Bitmap r2 = android.graphics.Bitmap.createBitmap(r2, r4, r5)     // Catch:{ Exception -> 0x00d0, OutOfMemoryError -> 0x00e4 }
            android.graphics.PaintFlagsDrawFilter r4 = new android.graphics.PaintFlagsDrawFilter     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            r5 = 0
            r6 = 3
            r4.<init>(r5, r6)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            android.graphics.Canvas r5 = new android.graphics.Canvas     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            r5.<init>(r2)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            r5.setDrawFilter(r4)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            android.graphics.Bitmap r4 = r13.m     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            r6 = 0
            r7 = 0
            r8 = 0
            r5.drawBitmap(r4, r6, r7, r8)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            r5.save()     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            r5.scale(r3, r3)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            boolean r3 = com.jiuyan.inimage.util.c.a(r0)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            if (r3 == 0) goto L_0x0084
            r3 = 0
            r4 = 0
            r6 = 0
            r5.drawBitmap(r0, r3, r4, r6)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
        L_0x0084:
            r5.restore()     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            boolean r0 = r13.F     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            if (r0 != 0) goto L_0x00ba
            android.content.res.Resources r0 = r13.getResources()     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            r3 = 2046951462(0x7a020026, float:1.687504E35)
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeResource(r0, r3)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            boolean r3 = com.jiuyan.inimage.util.c.a(r0)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            if (r3 == 0) goto L_0x00ba
            r3 = 1091567616(0x41100000, float:9.0)
            int r3 = com.jiuyan.inimage.util.DisplayUtil.dip2px(r13, r3)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            android.graphics.Bitmap r4 = r13.m     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            int r4 = r4.getHeight()     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            int r6 = r0.getHeight()     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            int r4 = r4 - r6
            r6 = 1093664768(0x41300000, float:11.0)
            int r6 = com.jiuyan.inimage.util.DisplayUtil.dip2px(r13, r6)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            int r4 = r4 - r6
            float r3 = (float) r3     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            float r4 = (float) r4     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
            r6 = 0
            r5.drawBitmap(r0, r3, r4, r6)     // Catch:{ Exception -> 0x013a, OutOfMemoryError -> 0x0135 }
        L_0x00ba:
            r0 = r2
        L_0x00bb:
            boolean r2 = com.jiuyan.inimage.util.c.a(r0)
            if (r2 != 0) goto L_0x00f8
            java.lang.String r0 = r13.getString(r11)
            r13.toast(r0, r9)
            java.lang.System.gc()
            goto L_0x0032
        L_0x00cd:
            r0 = r1
            goto L_0x0049
        L_0x00d0:
            r0 = move-exception
            r2 = r0
            r0 = r1
        L_0x00d3:
            java.lang.String r3 = r13.getString(r11)
            r13.toast(r3, r9)
            java.lang.String r3 = "InPhotoEditActivity"
            java.lang.String r2 = r2.toString()
            com.jiuyan.inimage.util.q.a(r3, r2)
            goto L_0x00bb
        L_0x00e4:
            r0 = move-exception
            r2 = r0
            r0 = r1
        L_0x00e7:
            java.lang.String r3 = r13.getString(r11)
            r13.toast(r3, r9)
            java.lang.String r3 = "InPhotoEditActivity"
            java.lang.String r2 = r2.toString()
            com.jiuyan.inimage.util.q.a(r3, r2)
            goto L_0x00bb
        L_0x00f8:
            com.jiuyan.inimage.callback.IPhotoSaveDelegate r2 = com.jiuyan.inimage.InSDKEntrance.sPhotoSaveDelegate
            if (r2 == 0) goto L_0x011b
            r1 = 2
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.String r2 = "mingtian "
            r1[r9] = r2
            java.lang.String r2 = "save photo begin"
            r1[r10] = r2
            com.jiuyan.inimage.util.q.a(r1)
            java.lang.String r1 = ""
            r13.showProgressDialog(r1)
            r13.J = r10
            com.jiuyan.inimage.d r1 = new com.jiuyan.inimage.d
            r1.<init>(r13, r0)
            r13.a(r1)
            goto L_0x0032
        L_0x011b:
            r2 = 2
            java.lang.String[] r2 = new java.lang.String[r2]
            java.lang.String r3 = "mingtian "
            r2[r9] = r3
            java.lang.String r3 = "save photo null"
            r2[r10] = r3
            com.jiuyan.inimage.util.q.a(r2)
            java.lang.String r2 = "mingtian "
            java.lang.String r3 = "save photo null"
            com.jiuyan.inimage.util.q.a(r2, r3)
            r13.a(r10, r0, r1)
            goto L_0x0032
        L_0x0135:
            r0 = move-exception
            r12 = r0
            r0 = r2
            r2 = r12
            goto L_0x00e7
        L_0x013a:
            r0 = move-exception
            r12 = r0
            r0 = r2
            r2 = r12
            goto L_0x00d3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jiuyan.inimage.InPhotoEditActivity.r():void");
    }

    private void a(Runnable runnable) {
        ThreadPoolExecutor acquireExecutor = ((TaskScheduleService) n.a(TaskScheduleService.class)).acquireExecutor(ScheduleType.IO);
        if (acquireExecutor != null) {
            acquireExecutor.execute(runnable);
        } else {
            LoggerFactory.getTraceLogger().warn((String) SpmUtils.SPM_BIZTYPE, (String) "获取线程池失败");
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z2, Bitmap bitmap, String str) {
        runOnUiThread(new f(this, z2, bitmap, str));
    }

    public void a(com.jiuyan.inimage.d.b bVar) {
        this.f.a(bVar);
    }

    public void a(BeanPaster beanPaster) {
        String a2 = com.jiuyan.inimage.util.f.a(beanPaster.url);
        com.jiuyan.inimage.b.q.a(this, beanPaster.id, beanPaster.url, g.a, a2, new g(this, beanPaster));
    }

    /* renamed from: a */
    public Void onComponentBack(Integer num, Integer num2, Object obj) {
        if (num.intValue() != 1 || this.z == null) {
            if (num.intValue() != 2 || this.A == null) {
                if (num.intValue() == 3 && this.B != null) {
                    if (this.B.c()) {
                        a(getString(R.string.in_sdk_cancle_edit), (Runnable) new j(this));
                    } else {
                        if (this.B != null && !this.P) {
                            this.B.e();
                        }
                        if (this.P) {
                            onClick(findViewById(R.id.tv_edit_cancel));
                        }
                    }
                }
            } else if (this.A.g()) {
                a(getString(R.string.in_sdk_cancle_edit), (Runnable) new i(this));
            } else {
                if (this.A != null && !this.P) {
                    this.A.d();
                }
                if (this.P) {
                    onClick(findViewById(R.id.tv_edit_cancel));
                }
            }
        } else if (this.z.g()) {
            a(getString(R.string.in_sdk_cancle_edit), (Runnable) new h(this));
        } else {
            if (this.z != null && !this.P) {
                this.z.d();
            }
            if (this.P) {
                onClick(findViewById(R.id.tv_edit_cancel));
            }
        }
        l();
        return null;
    }

    /* renamed from: b */
    public Void onComponentDone(Integer num, Integer num2, Object obj) {
        if (num.intValue() == 1) {
            this.l.b();
            this.m = b.a;
            a(this.m);
            if (!this.P && this.z != null) {
                this.z.d();
            }
            this.D = true;
            if (this.P) {
                onClick(findViewById(R.id.tv_edit_finish));
            }
        } else if (num.intValue() == 2) {
            this.l.b();
            this.m = b.a;
            a(this.m);
            if (!this.P && this.A != null) {
                this.A.d();
            }
            this.E = true;
            if (this.P) {
                onClick(findViewById(R.id.tv_edit_finish));
            }
        } else if (num.intValue() == 3) {
            this.l.b();
            this.m = b.a;
            a(this.m);
            if (this.B.getTextObject() != null) {
                if (this.K != null) {
                    this.g.a((d) this.K);
                }
                this.K = this.B.getTextObject().k();
                this.K.b(false);
                this.g.a((d) this.K, false);
                this.F = true;
            } else {
                this.K = null;
                this.F = false;
            }
            if (!this.P && this.B != null) {
                this.B.e();
            }
            if (this.P) {
                onClick(findViewById(R.id.tv_edit_finish));
            }
        }
        if (num.intValue() == 1 || num.intValue() == 2) {
            k();
        }
        return null;
    }

    /* renamed from: c */
    public Void onComponentEvent(Integer num, Integer num2, Object obj) {
        if (num2.intValue() == 101) {
            a(true, (AnimatorListener) null);
        }
        return null;
    }

    public void onBackPressed() {
        if (this.A == null || !this.A.isShown()) {
            if (this.z == null || !this.z.isShown()) {
                if (this.B == null || !this.B.isShown()) {
                    if (q()) {
                        if (InSDKEntrance.sEditCallback != null) {
                            InSDKEntrance.sEditCallback.onEditCancel();
                        }
                        finish();
                        return;
                    }
                    a(getString(R.string.in_sdk_cancle_edit), (Runnable) new o(this));
                } else if (this.B.c()) {
                    a(getString(R.string.in_sdk_cancle_edit), (Runnable) new m(this));
                } else {
                    if (this.B != null && !this.P) {
                        this.B.e();
                    }
                    l();
                    if (this.P) {
                        onClick(findViewById(R.id.tv_edit_cancel));
                    }
                }
            } else if (this.z.g()) {
                a(getString(R.string.in_sdk_cancle_edit), (Runnable) new l(this));
            } else {
                if (this.z != null && !this.P) {
                    this.z.d();
                }
                l();
                if (this.P) {
                    onClick(findViewById(R.id.tv_edit_cancel));
                }
            }
        } else if (this.A.g()) {
            a(getString(R.string.in_sdk_cancle_edit), (Runnable) new k(this));
        } else {
            if (this.A != null && !this.P) {
                this.A.d();
            }
            l();
            if (this.P) {
                onClick(findViewById(R.id.tv_edit_cancel));
            }
        }
    }

    public void a(int i2) {
        q.a("launch func " + this.m);
        if (i2 == 0) {
            b.a = this.m;
            if (this.z == null) {
                this.z = (CropperView) findViewById(R.id.inalipay_cropper_view);
                this.z.setComponentListener(this);
                this.z.c();
                return;
            }
            this.z.c();
        } else if (i2 == 1) {
            b.a = this.m;
            if (this.A == null) {
                this.A = (RotateView) findViewById(R.id.inalipay_rotate_view);
                this.A.setComponentListener(this);
                this.A.c();
                return;
            }
            this.A.c();
        } else if (i2 == 2) {
            b.a = this.m;
            if (this.B == null) {
                this.B = (TextWaterMarkView) findViewById(R.id.inalipay_watermark_view);
                this.B.setComponentListener(this);
                this.B.d();
                return;
            }
            if (this.K != null) {
                h k2 = this.K.k();
                k2.b(true);
                this.B.a(k2);
            }
            this.B.d();
        }
    }

    private void s() {
        this.C = new H5Receiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(InSDKEntrance.getH5Action());
        LocalBroadcastManager.getInstance(this).registerReceiver(this.C, intentFilter);
    }

    private void t() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.C);
    }

    public void onMagicWandClick(com.jiuyan.inimage.d.a aVar) {
        this.f.a(aVar);
    }
}
