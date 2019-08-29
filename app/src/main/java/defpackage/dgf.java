package defpackage;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts.fragment.NavigationVoiceListFragment;
import com.autonavi.minimap.drive.navi.navitts.fragment.NavigationVoiceListFragment.a;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;
import com.autonavi.widget.ui.TitleBar;
import java.io.File;

/* renamed from: dgf reason: default package */
/* compiled from: NavigationVoiceListPresenter */
public final class dgf extends sw<NavigationVoiceListFragment, dge> {
    public dgf(NavigationVoiceListFragment navigationVoiceListFragment) {
        super(navigationVoiceListFragment);
    }

    public final void onPageCreated() {
        NavigationVoiceListFragment navigationVoiceListFragment = (NavigationVoiceListFragment) this.mPage;
        PageBundle arguments = navigationVoiceListFragment.getArguments();
        if (arguments == null) {
            ToastHelper.showLongToast("语音包找不到了，请重新录制");
            navigationVoiceListFragment.finish();
            return;
        }
        navigationVoiceListFragment.b = arguments.getString("bundle_key_voice_package_name");
        navigationVoiceListFragment.a = (File) arguments.getObject("bundle_key_voice_package_obj");
        navigationVoiceListFragment.c = arguments.getInt("bundle_key_work_mode", 0);
        if (TextUtils.isEmpty(navigationVoiceListFragment.b) || navigationVoiceListFragment.a == null || !navigationVoiceListFragment.a.exists()) {
            ToastHelper.showLongToast("语音包找不到了，请重新录制");
            navigationVoiceListFragment.finish();
            return;
        }
        if (navigationVoiceListFragment.a != null && navigationVoiceListFragment.a.exists()) {
            String[] list = navigationVoiceListFragment.a.list();
            if (list != null && list.length <= 0) {
                ToastHelper.showLongToast("语音包找不到了，请重新录制");
                navigationVoiceListFragment.finish();
                return;
            }
        }
        View contentView = navigationVoiceListFragment.getContentView();
        navigationVoiceListFragment.e = (TitleBar) contentView.findViewById(R.id.title);
        navigationVoiceListFragment.e.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                NavigationVoiceListFragment.this.a();
            }
        });
        navigationVoiceListFragment.e.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("bundle_key_voice_package_name", NavigationVoiceListFragment.this.b);
                pageBundle.putObject("bundle_key_voice_package_obj", NavigationVoiceListFragment.this.a);
                pageBundle.putInt("bundle_key_work_mode", NavigationVoiceListFragment.this.c);
                pageBundle.putInt("bundle_key_dialog_mode", 2);
                pageBundle.putObject("bundle_key_backup_voice_package_obj", NavigationVoiceListFragment.this.k);
                NavigationVoiceListFragment.this.startPageForResult(NVPackageSavingDlgFragment.class, pageBundle, 100);
            }
        });
        navigationVoiceListFragment.f = (LinearLayout) contentView.findViewById(R.id.scroll_view_content);
        if (navigationVoiceListFragment.c == 1) {
            ahl.a(new a(navigationVoiceListFragment, 0));
        }
    }

    public final void onStart() {
        NavigationVoiceListFragment navigationVoiceListFragment = (NavigationVoiceListFragment) this.mPage;
        File[] listFiles = navigationVoiceListFragment.a.listFiles();
        if (!(listFiles == null || listFiles.length == 0)) {
            for (File name : listFiles) {
                String name2 = name.getName();
                if (name2.length() == 26) {
                    String substring = name2.substring(11, 18);
                    if (!TextUtils.isEmpty(substring) && dkc.a.containsKey(substring)) {
                        navigationVoiceListFragment.d[dkc.a.get(substring).intValue()] = true;
                    }
                }
            }
        }
        if (dgu.b(navigationVoiceListFragment.b)) {
            navigationVoiceListFragment.e.setActionTextEnable(true);
        } else {
            navigationVoiceListFragment.e.setActionTextEnable(false);
        }
        navigationVoiceListFragment.f.removeAllViews();
        navigationVoiceListFragment.c();
        int length = navigationVoiceListFragment.d.length;
        for (int i = 0; i < length; i++) {
            if (navigationVoiceListFragment.d[i]) {
                View inflate = LayoutInflater.from(navigationVoiceListFragment.getContext()).inflate(R.layout.voice_list_item_one_voice, null);
                ((ImageView) inflate.findViewById(R.id.play_status)).setOnClickListener(new OnClickListener(i) {
                    final /* synthetic */ int a;

                    {
                        this.a = r2;
                    }

                    public final void onClick(View view) {
                        if (view instanceof ImageView) {
                            ImageView imageView = (ImageView) view;
                            StringBuilder sb = new StringBuilder();
                            sb.append(NavigationVoiceListFragment.this.j);
                            sb.append(dkc.a(NavigationVoiceListFragment.this.b, this.a));
                            String sb2 = sb.toString();
                            if (NavigationVoiceListFragment.this.h == null) {
                                NavigationVoiceListFragment.this.n = this.a;
                                NavigationVoiceListFragment.a(NavigationVoiceListFragment.this, imageView, sb2);
                            } else if (NavigationVoiceListFragment.this.n == NavigationVoiceListFragment.m || NavigationVoiceListFragment.this.n == this.a) {
                                NavigationVoiceListFragment.this.b();
                                imageView.setBackgroundResource(R.drawable.navitts_play);
                            } else {
                                NavigationVoiceListFragment.this.b();
                                if (NavigationVoiceListFragment.this.o != null) {
                                    NavigationVoiceListFragment.this.o.setBackgroundResource(R.drawable.navitts_play);
                                }
                                NavigationVoiceListFragment.this.n = this.a;
                                NavigationVoiceListFragment.a(NavigationVoiceListFragment.this, imageView, sb2);
                            }
                        }
                    }
                });
                ((TextView) inflate.findViewById(R.id.sentence_name)).setText(dkc.b[i].a);
                inflate.findViewById(R.id.nvl_item_one_voice).setOnClickListener(new OnClickListener(i) {
                    final /* synthetic */ int a;

                    {
                        this.a = r2;
                    }

                    public final void onClick(View view) {
                        kj.a(NavigationVoiceListFragment.this.getActivity(), new String[]{"android.permission.RECORD_AUDIO"}, (b) new b() {
                            public final void run() {
                                PageBundle pageBundle = new PageBundle();
                                pageBundle.putInt("bundle_key_sequence_number", AnonymousClass6.this.a);
                                pageBundle.putString("bundle_key_voice_package_name", NavigationVoiceListFragment.this.b);
                                pageBundle.putInt("bundle_key_work_mode", NavigationVoiceListFragment.this.c);
                                NavigationVoiceListFragment.this.startPage(NavigationVoiceRecordFragment.class, pageBundle);
                            }
                        });
                    }
                });
                navigationVoiceListFragment.f.addView(inflate);
            } else {
                View inflate2 = LayoutInflater.from(navigationVoiceListFragment.getContext()).inflate(R.layout.voice_list_item_no_voice, null);
                ((TextView) inflate2.findViewById(R.id.sentence_name)).setText(dkc.b[i].a);
                inflate2.findViewById(R.id.nvl_item_no_voice).setOnClickListener(new OnClickListener(i) {
                    final /* synthetic */ int a;

                    {
                        this.a = r2;
                    }

                    public final void onClick(View view) {
                        kj.a(NavigationVoiceListFragment.this.getActivity(), new String[]{"android.permission.RECORD_AUDIO"}, (b) new b() {
                            public final void run() {
                                PageBundle pageBundle = new PageBundle();
                                pageBundle.putInt("bundle_key_sequence_number", AnonymousClass4.this.a);
                                pageBundle.putString("bundle_key_voice_package_name", NavigationVoiceListFragment.this.b);
                                pageBundle.putInt("bundle_key_work_mode", NavigationVoiceListFragment.this.c);
                                NavigationVoiceListFragment.this.startPage(NavigationVoiceRecordFragment.class, pageBundle);
                            }
                        });
                    }
                });
                navigationVoiceListFragment.f.addView(inflate2);
            }
        }
        navigationVoiceListFragment.c();
    }

    public final void onStop() {
        ((NavigationVoiceListFragment) this.mPage).b();
    }

    public final void onDestroy() {
        NavigationVoiceListFragment navigationVoiceListFragment = (NavigationVoiceListFragment) this.mPage;
        navigationVoiceListFragment.setResult(ResultType.OK, navigationVoiceListFragment.g);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getRepeatCount() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        ((NavigationVoiceListFragment) this.mPage).a();
        return true;
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        NavigationVoiceListFragment navigationVoiceListFragment = (NavigationVoiceListFragment) this.mPage;
        navigationVoiceListFragment.g = pageBundle;
        if (i == 100 && resultType == ResultType.OK) {
            if (navigationVoiceListFragment.c == 0) {
                navigationVoiceListFragment.startPage(OfflineNaviTtsFragment.class, pageBundle);
            } else if (navigationVoiceListFragment.c == 1) {
                switch (pageBundle.getInt("bundle_key_user_action", 0)) {
                    case 0:
                        ahl.a(new a() {
                            public final Object doBackground() throws Exception {
                                if (NavigationVoiceListFragment.this.k == null || !NavigationVoiceListFragment.this.k.exists()) {
                                    return null;
                                }
                                ahd.a(NavigationVoiceListFragment.this.k);
                                return !NavigationVoiceListFragment.this.k.delete() ? null : null;
                            }
                        });
                        break;
                    case 1:
                        break;
                }
                navigationVoiceListFragment.finish();
            }
        }
    }

    public final /* synthetic */ su a() {
        return new dge(this);
    }
}
