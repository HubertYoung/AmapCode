package com.autonavi.minimap.drive.navi.navitts.fragment;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import java.io.File;

public class NavigationVoiceListFragment extends DriveBasePage<dgf> implements launchModeSingleTask {
    /* access modifiers changed from: private */
    public static int m = -1;
    public File a;
    public String b;
    public int c = 0;
    public boolean[] d = {false, false, false, false, false, false, false, false};
    public TitleBar e;
    public LinearLayout f;
    public PageBundle g;
    /* access modifiers changed from: private */
    public eum h = null;
    /* access modifiers changed from: private */
    public Handler i = new Handler();
    /* access modifiers changed from: private */
    public String j;
    /* access modifiers changed from: private */
    public File k;
    /* access modifiers changed from: private */
    public String l;
    /* access modifiers changed from: private */
    public int n = m;
    /* access modifiers changed from: private */
    public ImageView o;

    public class a extends defpackage.dgw.a {
        private a() {
        }

        public /* synthetic */ a(NavigationVoiceListFragment navigationVoiceListFragment, byte b) {
            this();
        }

        public final Object doBackground() {
            File b = NavigationVoiceListFragment.this.a;
            NavigationVoiceListFragment navigationVoiceListFragment = NavigationVoiceListFragment.this;
            StringBuilder sb = new StringBuilder("__backup_");
            sb.append(NavigationVoiceListFragment.this.b);
            navigationVoiceListFragment.l = sb.toString();
            NavigationVoiceListFragment.this.k = new File(dgu.a(NavigationVoiceListFragment.this.l));
            if (!NavigationVoiceListFragment.this.k.exists() && !NavigationVoiceListFragment.this.k.mkdir()) {
                NavigationVoiceListFragment.this.getActivity().runOnUiThread(new Runnable() {
                    public final void run() {
                        NavigationVoiceListFragment.this.finish();
                    }
                });
            }
            ahd.a(b, NavigationVoiceListFragment.this.k);
            return null;
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navigation_voice_list_fragment);
        this.j = PathManager.a().b(DirType.DRIVE_VOICE);
    }

    public final void a() {
        switch (this.c) {
            case 0:
            case 1:
                finish();
                return;
            default:
                return;
        }
    }

    public final void b() {
        if (this.h != null) {
            this.h.b();
            this.h = null;
            this.n = m;
        }
    }

    public final void c() {
        this.f.addView(LayoutInflater.from(getContext()).inflate(R.layout.voice_list_item_separated_section, null));
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dgf(this);
    }

    static /* synthetic */ void a(NavigationVoiceListFragment navigationVoiceListFragment, final ImageView imageView, String str) {
        if (navigationVoiceListFragment.h != null) {
            navigationVoiceListFragment.h.b();
        }
        navigationVoiceListFragment.h = new eum(str);
        imageView.setBackgroundResource(R.drawable.navitts_pause);
        navigationVoiceListFragment.h.a(new defpackage.eum.a() {
            public final void onStart() {
            }

            public final void onFinish() {
                NavigationVoiceListFragment.this.i.post(new Runnable() {
                    public final void run() {
                        imageView.setBackgroundResource(R.drawable.navitts_play);
                        NavigationVoiceListFragment.this.b();
                    }
                });
            }
        });
        navigationVoiceListFragment.h.a();
        navigationVoiceListFragment.o = imageView;
    }
}
