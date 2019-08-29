package com.autonavi.minimap.drive.navi.navitts.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts.widget.NavigationTtsMicView;
import com.autonavi.minimap.drive.navi.navitts.widget.NavigationTtsMicView.c;
import com.autonavi.minimap.drive.navi.navitts.widget.NavigationTtsMicView.d;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.widget.ui.TitleBar;
import com.iflytek.tts.TtsService.Tts;
import java.io.File;
import java.util.Random;

public class NavigationVoiceRecordFragment extends DriveBasePage<dgh> implements launchModeSingleTask {
    /* access modifiers changed from: private */
    public final c A = new a(this, 0);
    private String[] B = new String[3];
    private int C;
    /* access modifiers changed from: private */
    public eun D = null;
    private eum E = null;
    /* access modifiers changed from: private */
    public boolean F = false;
    /* access modifiers changed from: private */
    public boolean G = false;
    /* access modifiers changed from: private */
    public int H = 8;
    private File I;
    private boolean J = true;
    private Context K;
    /* access modifiers changed from: private */
    public boolean L = true;
    private int M = -1;
    /* access modifiers changed from: private */
    public long N;
    private final d O = new d() {
        public final boolean a(MotionEvent motionEvent) {
            try {
                NavigationVoiceRecordFragment.this.p.setListener(NavigationVoiceRecordFragment.this.A);
                switch (motionEvent.getAction()) {
                    case 0:
                        if (NavigationVoiceRecordFragment.this.L) {
                            NavigationVoiceRecordFragment.this.F = false;
                            NavigationVoiceRecordFragment.this.N = System.currentTimeMillis();
                            break;
                        } else {
                            return false;
                        }
                    case 1:
                        if (NavigationVoiceRecordFragment.this.L) {
                            if (NavigationVoiceRecordFragment.this.G) {
                                NavigationVoiceRecordFragment.this.G = false;
                                NavigationVoiceRecordFragment.this.p.stopAnimations();
                                long currentTimeMillis = System.currentTimeMillis();
                                if (!NavigationVoiceRecordFragment.this.F) {
                                    if (currentTimeMillis - NavigationVoiceRecordFragment.this.N < 2000) {
                                        ToastHelper.showLongToast(NavigationVoiceRecordFragment.this.getString(R.string.nr_too_short_to_record));
                                        if (NavigationVoiceRecordFragment.this.b != null && NavigationVoiceRecordFragment.this.b.exists()) {
                                            NavigationVoiceRecordFragment.this.b.delete();
                                        }
                                    } else {
                                        NavigationVoiceRecordFragment.this.i();
                                    }
                                }
                                NavigationVoiceRecordFragment.this.d();
                                break;
                            }
                        }
                        return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    };
    public int a = 0;
    public File b = null;
    public String c;
    public String d;
    public int e = 0;
    public TitleBar f;
    OnClickListener g = new OnClickListener() {
        public final void onClick(View view) {
            switch (NavigationVoiceRecordFragment.this.e) {
                case 0:
                    NavigationVoiceRecordFragment.b(NavigationVoiceRecordFragment.this);
                    return;
                case 1:
                    NavigationVoiceRecordFragment.this.finish();
                    break;
            }
        }
    };
    OnClickListener h = new OnClickListener() {
        public final void onClick(View view) {
            switch (NavigationVoiceRecordFragment.this.e) {
                case 0:
                    NavigationVoiceRecordFragment.b(NavigationVoiceRecordFragment.this);
                    return;
                case 1:
                    NavigationVoiceRecordFragment.this.finish();
                    break;
            }
        }
    };
    public TextView i;
    public TextView j;
    public View k;
    public TextView l;
    public TextView m;
    public TextView n;
    public TextView o;
    public NavigationTtsMicView p;
    public View q;
    public TextView r;
    public TextView s;
    public TextView t;
    public ImageView u;
    Runnable v = new Runnable() {
        public final void run() {
            NavigationVoiceRecordFragment.this.p.stopAnimations();
            NavigationVoiceRecordFragment.this.d();
            NavigationVoiceRecordFragment.this.i();
        }
    };
    OnClickListener w = new OnClickListener() {
        public final void onClick(View view) {
            if (NavigationVoiceRecordFragment.this.a < NavigationVoiceRecordFragment.this.H - 1) {
                NavigationVoiceRecordFragment.this.c();
                NavigationVoiceRecordFragment.i(NavigationVoiceRecordFragment.this);
                NavigationVoiceRecordFragment navigationVoiceRecordFragment = NavigationVoiceRecordFragment.this;
                StringBuilder sb = new StringBuilder();
                sb.append(NavigationVoiceRecordFragment.this.c);
                sb.append(dkc.a(NavigationVoiceRecordFragment.this.d, NavigationVoiceRecordFragment.this.a));
                navigationVoiceRecordFragment.b = new File(sb.toString());
                NavigationVoiceRecordFragment.this.g();
                if (NavigationVoiceRecordFragment.this.b == null || !NavigationVoiceRecordFragment.this.b.exists()) {
                    NavigationVoiceRecordFragment.this.h();
                } else {
                    NavigationVoiceRecordFragment.this.i();
                }
                NavigationVoiceRecordFragment.this.p.stopAnimations();
            }
        }
    };
    public ImageView x;
    public AnimationSet y;
    /* access modifiers changed from: private */
    public final Handler z = new Handler();

    class a implements c {
        private a() {
        }

        /* synthetic */ a(NavigationVoiceRecordFragment navigationVoiceRecordFragment, byte b) {
            this();
        }

        public final void a() {
            Activity activity = NavigationVoiceRecordFragment.this.getActivity();
            String[] strArr = {"android.permission.RECORD_AUDIO"};
            AnonymousClass1 r2 = new b() {
                public final void run() {
                    NavigationVoiceRecordFragment.this.L = true;
                    NavigationVoiceRecordFragment.this.p.startAnimations();
                    NavigationVoiceRecordFragment.this.G = true;
                    NavigationVoiceRecordFragment.y(NavigationVoiceRecordFragment.this);
                }

                public final void reject() {
                    NavigationVoiceRecordFragment.this.L = false;
                    ToastHelper.showLongToast("您可能没有权限打开录音机，请到系统设置中打开录音权限");
                }
            };
            if (VERSION.SDK_INT >= 23) {
                kj.a(activity, strArr, (b) r2);
                return;
            }
            boolean z = true;
            for (int i = 0; i <= 0; i++) {
                if ("android.permission.RECORD_AUDIO".equals(strArr[0])) {
                    z = djj.a();
                }
            }
            r2.callback(z);
        }

        public final void b() {
            if (NavigationVoiceRecordFragment.this.L) {
                NavigationVoiceRecordFragment.this.p.stopAnimations();
                NavigationVoiceRecordFragment.this.d();
                boolean b = dgu.b(NavigationVoiceRecordFragment.this.d);
                if (b && NavigationVoiceRecordFragment.this.q != null && NavigationVoiceRecordFragment.this.q.getVisibility() == 8 && NavigationVoiceRecordFragment.this.b != null && NavigationVoiceRecordFragment.this.b.exists()) {
                    NavigationVoiceRecordFragment.this.b.delete();
                    b = false;
                }
                NavigationVoiceRecordFragment.this.f.setActionTextEnable(b);
            }
        }
    }

    static /* synthetic */ int i(NavigationVoiceRecordFragment navigationVoiceRecordFragment) {
        int i2 = navigationVoiceRecordFragment.a + 1;
        navigationVoiceRecordFragment.a = i2;
        return i2;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navigation_voice_record_fragment);
        this.K = getContext();
        this.c = PathManager.a().b(DirType.DRIVE_VOICE);
    }

    public final void a() {
        PageBundle arguments = getArguments();
        this.a = arguments.getInt("bundle_key_sequence_number");
        this.d = arguments.getString("bundle_key_voice_package_name");
        this.e = arguments.getInt("bundle_key_work_mode", 0);
        if (this.a == -1 || TextUtils.isEmpty(this.d)) {
            this.a = 0;
            this.d = FilePathHelper.DEFAULT_VOICE_PACKAGE_NAME;
            this.J = true;
        } else {
            this.J = false;
        }
        if (dgu.b(this.d)) {
            this.J = false;
        } else {
            this.J = true;
        }
    }

    public final void b() {
        if (dgu.b(this.d)) {
            this.f.setActionTextEnable(true);
        } else {
            this.f.setActionTextEnable(false);
        }
        if (!this.J) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.c);
            sb.append(dkc.a(this.d, this.a));
            this.b = new File(sb.toString());
            if (this.b.exists()) {
                i();
                g();
                this.n.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        NavigationVoiceRecordFragment.t(NavigationVoiceRecordFragment.this);
                    }
                });
                this.p.setListener(this.A);
                this.p.setTouchListener(this.O);
                this.r.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        NavigationVoiceRecordFragment.this.c();
                        NavigationVoiceRecordFragment.this.f();
                        NavigationVoiceRecordFragment.this.f.setActionTextEnable(false);
                        if (NavigationVoiceRecordFragment.this.b != null && NavigationVoiceRecordFragment.this.b.exists()) {
                            NavigationVoiceRecordFragment.this.b.delete();
                        }
                        NavigationVoiceRecordFragment.this.h();
                        NavigationVoiceRecordFragment.this.p.stopAnimations();
                    }
                });
                this.s.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        NavigationVoiceRecordFragment.this.h();
                        NavigationVoiceRecordFragment.this.p.stopAnimations();
                    }
                });
                this.u.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        if (NavigationVoiceRecordFragment.this.b != null && NavigationVoiceRecordFragment.this.b.exists()) {
                            NavigationVoiceRecordFragment.a(NavigationVoiceRecordFragment.this, NavigationVoiceRecordFragment.this.b.getPath());
                        }
                    }
                });
                this.I = new File(dgu.a(this.d));
            }
        }
        h();
        g();
        this.n.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                NavigationVoiceRecordFragment.t(NavigationVoiceRecordFragment.this);
            }
        });
        this.p.setListener(this.A);
        this.p.setTouchListener(this.O);
        this.r.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                NavigationVoiceRecordFragment.this.c();
                NavigationVoiceRecordFragment.this.f();
                NavigationVoiceRecordFragment.this.f.setActionTextEnable(false);
                if (NavigationVoiceRecordFragment.this.b != null && NavigationVoiceRecordFragment.this.b.exists()) {
                    NavigationVoiceRecordFragment.this.b.delete();
                }
                NavigationVoiceRecordFragment.this.h();
                NavigationVoiceRecordFragment.this.p.stopAnimations();
            }
        });
        this.s.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                NavigationVoiceRecordFragment.this.h();
                NavigationVoiceRecordFragment.this.p.stopAnimations();
            }
        });
        this.u.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (NavigationVoiceRecordFragment.this.b != null && NavigationVoiceRecordFragment.this.b.exists()) {
                    NavigationVoiceRecordFragment.a(NavigationVoiceRecordFragment.this, NavigationVoiceRecordFragment.this.b.getPath());
                }
            }
        });
        this.I = new File(dgu.a(this.d));
    }

    public final void c() {
        if (this.E != null) {
            this.E.b();
            this.E = null;
        }
        if (this.x != null && this.x.getVisibility() == 0) {
            f();
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        Drawable drawable;
        if (this.a < this.H) {
            TextView textView = this.i;
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(this.a + 1));
            sb.append("/");
            sb.append(String.valueOf(this.H));
            textView.setText(sb.toString());
            this.j.setText(dkc.b[this.a].a);
            this.B = dkc.b[this.a].c.split(";");
            this.C = new Random().nextInt(3);
            String[] split = this.B[this.C].split(AUScreenAdaptTool.PREFIX_ID);
            this.l.setText(split[0]);
            this.m.setText(split[1]);
            if (this.a + 1 == this.H) {
                this.t.setText("完成");
                drawable = getResources().getDrawable(R.drawable.navitts_complete_selector);
                this.t.setOnClickListener(this.h);
            } else {
                this.t.setText("下一句");
                drawable = getResources().getDrawable(R.drawable.navitts_next_sentence_selector);
                this.t.setOnClickListener(this.w);
            }
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            this.t.setCompoundDrawables(null, drawable, null, null);
        }
    }

    /* access modifiers changed from: private */
    public void h() {
        k();
        this.q.setVisibility(8);
        this.u.setVisibility(8);
        c();
    }

    /* access modifiers changed from: private */
    public void i() {
        j();
        this.q.setVisibility(0);
        this.u.setVisibility(0);
    }

    private void j() {
        this.k.setVisibility(8);
        this.o.setVisibility(8);
        this.p.setVisibility(8);
    }

    private void k() {
        this.k.setVisibility(0);
        this.o.setVisibility(0);
        this.p.setVisibility(0);
    }

    public final void d() {
        this.z.postDelayed(new Runnable() {
            public final void run() {
                if (NavigationVoiceRecordFragment.this.D != null) {
                    NavigationVoiceRecordFragment.this.D.a(false);
                    NavigationVoiceRecordFragment.this.D = null;
                }
            }
        }, 500);
        this.z.removeCallbacks(this.v);
        if (Tts.getInstance().JniIsPlaying() == 2) {
            bnz.b(getContext());
        }
    }

    public final void e() {
        switch (this.e) {
            case 0:
                if (dgu.b(this.d)) {
                    j();
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putString("bundle_key_voice_package_name", this.d);
                    pageBundle.putObject("bundle_key_voice_package_obj", this.I);
                    pageBundle.putInt("bundle_key_work_mode", 0);
                    pageBundle.putInt("bundle_key_dialog_mode", 0);
                    startPageForResult(NVPackageSavingDlgFragment.class, pageBundle, 100);
                    return;
                }
                finish();
                return;
            case 1:
                finish();
                break;
        }
    }

    public final void f() {
        if (this.x != null && this.y != null) {
            this.y.cancel();
            this.x.clearAnimation();
            this.x.setVisibility(8);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dgh(this);
    }

    static /* synthetic */ void b(NavigationVoiceRecordFragment navigationVoiceRecordFragment) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("bundle_key_voice_package_name", navigationVoiceRecordFragment.d);
        pageBundle.putObject("bundle_key_voice_package_obj", navigationVoiceRecordFragment.I);
        pageBundle.putInt("bundle_key_work_mode", navigationVoiceRecordFragment.e);
        navigationVoiceRecordFragment.startPage(NavigationVoiceListFragment.class, pageBundle);
    }

    static /* synthetic */ void t(NavigationVoiceRecordFragment navigationVoiceRecordFragment) {
        int nextInt = ((navigationVoiceRecordFragment.C + new Random().nextInt(2)) + 1) % 3;
        String[] split = navigationVoiceRecordFragment.B[nextInt].split(AUScreenAdaptTool.PREFIX_ID);
        navigationVoiceRecordFragment.l.setText(split[0]);
        navigationVoiceRecordFragment.m.setText(split[1]);
        navigationVoiceRecordFragment.C = nextInt;
    }

    static /* synthetic */ void a(NavigationVoiceRecordFragment navigationVoiceRecordFragment, String str) {
        if (navigationVoiceRecordFragment.E != null) {
            navigationVoiceRecordFragment.E.b();
        }
        navigationVoiceRecordFragment.E = new eum(str);
        navigationVoiceRecordFragment.E.a(new defpackage.eum.a() {
            public final void onStart() {
                NavigationVoiceRecordFragment.this.getActivity().runOnUiThread(new Runnable() {
                    public final void run() {
                        NavigationVoiceRecordFragment navigationVoiceRecordFragment = NavigationVoiceRecordFragment.this;
                        if (navigationVoiceRecordFragment.x != null && navigationVoiceRecordFragment.y != null) {
                            navigationVoiceRecordFragment.y.reset();
                            navigationVoiceRecordFragment.x.setVisibility(0);
                            navigationVoiceRecordFragment.x.startAnimation(navigationVoiceRecordFragment.y);
                        }
                    }
                });
            }

            public final void onFinish() {
                NavigationVoiceRecordFragment.this.getActivity().runOnUiThread(new Runnable() {
                    public final void run() {
                        NavigationVoiceRecordFragment.this.f();
                    }
                });
            }
        });
        navigationVoiceRecordFragment.E.a();
    }

    static /* synthetic */ void y(NavigationVoiceRecordFragment navigationVoiceRecordFragment) {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            ToastHelper.showLongToast(navigationVoiceRecordFragment.getResources().getString(R.string.publish_sd_notexist));
        } else if (dhd.h() < 50) {
            ToastHelper.showLongToast("SD卡空间过小，请删除一些无用文件再试。");
        } else {
            bnz.a(navigationVoiceRecordFragment.getContext());
            if (navigationVoiceRecordFragment.b == null) {
                StringBuilder sb = new StringBuilder();
                sb.append(navigationVoiceRecordFragment.c);
                sb.append(dkc.a(navigationVoiceRecordFragment.d, navigationVoiceRecordFragment.a));
                navigationVoiceRecordFragment.b = new File(sb.toString());
            }
            if (navigationVoiceRecordFragment.b.exists()) {
                boolean delete = navigationVoiceRecordFragment.b.delete();
            } else if (!navigationVoiceRecordFragment.b.getParentFile().exists()) {
                navigationVoiceRecordFragment.b.getParentFile().mkdirs();
            }
            if (navigationVoiceRecordFragment.D == null) {
                try {
                    navigationVoiceRecordFragment.D = new eun(navigationVoiceRecordFragment.b.getPath());
                    new Thread(navigationVoiceRecordFragment.D, "SpeexRecorder").start();
                } catch (IllegalStateException unused) {
                    navigationVoiceRecordFragment.p.stopAnimations();
                    navigationVoiceRecordFragment.d();
                    ToastHelper.showLongToast("请到系统设置中打开录音权限");
                    return;
                }
            }
            navigationVoiceRecordFragment.D.a = new defpackage.eun.a() {
                public final void a(final double d) {
                    NavigationVoiceRecordFragment.this.z.post(new Runnable() {
                        public final void run() {
                            NavigationVoiceRecordFragment.this.p.setVolume((int) d);
                        }
                    });
                }

                public final void a(final int i) {
                    NavigationVoiceRecordFragment.this.F = true;
                    if (NavigationVoiceRecordFragment.this.p != null) {
                        NavigationVoiceRecordFragment.this.p.post(new Runnable() {
                            public final void run() {
                                NavigationVoiceRecordFragment.this.p.stopAnimations();
                                NavigationVoiceRecordFragment.this.d();
                                if (i == -3) {
                                    ToastHelper.showLongToast("请到系统设置中打开录音权限");
                                }
                            }
                        });
                    }
                }
            };
            navigationVoiceRecordFragment.D.a(true);
            navigationVoiceRecordFragment.z.postDelayed(navigationVoiceRecordFragment.v, 5000);
        }
    }
}
