package defpackage;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts.fragment.NavigationVoiceRecordFragment;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;
import com.autonavi.minimap.drive.navi.navitts.widget.NavigationTtsMicView;
import com.autonavi.widget.ui.TitleBar;
import java.io.File;

/* renamed from: dgh reason: default package */
/* compiled from: NavigationVoiceRecordPresenter */
public final class dgh extends sw<NavigationVoiceRecordFragment, dgg> {
    public dgh(NavigationVoiceRecordFragment navigationVoiceRecordFragment) {
        super(navigationVoiceRecordFragment);
    }

    public final void onPageCreated() {
        VUIStateManager.f().C();
        NavigationVoiceRecordFragment navigationVoiceRecordFragment = (NavigationVoiceRecordFragment) this.mPage;
        navigationVoiceRecordFragment.requestScreenOrientation(1);
        navigationVoiceRecordFragment.a();
        View contentView = navigationVoiceRecordFragment.getContentView();
        navigationVoiceRecordFragment.f = (TitleBar) contentView.findViewById(R.id.title);
        navigationVoiceRecordFragment.f.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                NavigationVoiceRecordFragment.this.e();
            }
        });
        navigationVoiceRecordFragment.f.setOnActionClickListener(new OnClickListener() {
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
        });
        navigationVoiceRecordFragment.i = (TextView) contentView.findViewById(R.id.sentence_sequence);
        navigationVoiceRecordFragment.j = (TextView) contentView.findViewById(R.id.sentence_content);
        navigationVoiceRecordFragment.k = contentView.findViewById(R.id.recorder_guild_info);
        navigationVoiceRecordFragment.l = (TextView) contentView.findViewById(R.id.example_info_line_1);
        navigationVoiceRecordFragment.m = (TextView) contentView.findViewById(R.id.example_info_line_2);
        navigationVoiceRecordFragment.n = (TextView) contentView.findViewById(R.id.random_change);
        navigationVoiceRecordFragment.o = (TextView) contentView.findViewById(R.id.recorder_description);
        navigationVoiceRecordFragment.p = (NavigationTtsMicView) contentView.findViewById(R.id.navigation_tts_mic_view);
        navigationVoiceRecordFragment.q = contentView.findViewById(R.id.recording_completed);
        navigationVoiceRecordFragment.r = (TextView) contentView.findViewById(R.id.record_again);
        navigationVoiceRecordFragment.s = (TextView) contentView.findViewById(R.id.one_more);
        navigationVoiceRecordFragment.t = (TextView) contentView.findViewById(R.id.next_sentence);
        navigationVoiceRecordFragment.u = (ImageView) contentView.findViewById(R.id.voice_replay);
        navigationVoiceRecordFragment.x = (ImageView) contentView.findViewById(R.id.voice_replay_outer_circle);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setRepeatCount(-1);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setRepeatCount(-1);
        navigationVoiceRecordFragment.y = new AnimationSet(true);
        navigationVoiceRecordFragment.y.addAnimation(scaleAnimation);
        navigationVoiceRecordFragment.y.addAnimation(alphaAnimation);
        navigationVoiceRecordFragment.y.setInterpolator(new DecelerateInterpolator());
        navigationVoiceRecordFragment.b();
    }

    public final void onStart() {
        NavigationVoiceRecordFragment navigationVoiceRecordFragment = (NavigationVoiceRecordFragment) this.mPage;
        if (navigationVoiceRecordFragment.b == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(navigationVoiceRecordFragment.c);
            sb.append(dkc.a(navigationVoiceRecordFragment.d, navigationVoiceRecordFragment.a));
            navigationVoiceRecordFragment.b = new File(sb.toString());
        }
    }

    public final void onStop() {
        NavigationVoiceRecordFragment navigationVoiceRecordFragment = (NavigationVoiceRecordFragment) this.mPage;
        navigationVoiceRecordFragment.requestScreenOrientation(-1);
        navigationVoiceRecordFragment.d();
        navigationVoiceRecordFragment.c();
        navigationVoiceRecordFragment.f();
        navigationVoiceRecordFragment.p.stopAnimations();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        NavigationVoiceRecordFragment navigationVoiceRecordFragment = (NavigationVoiceRecordFragment) this.mPage;
        if (i == 100 && resultType == ResultType.OK && navigationVoiceRecordFragment.e == 0) {
            navigationVoiceRecordFragment.startPageForResult(OfflineNaviTtsFragment.class, pageBundle, 100);
        }
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getRepeatCount() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        ((NavigationVoiceRecordFragment) this.mPage).e();
        return true;
    }

    public final void onNewIntent(PageBundle pageBundle) {
        NavigationVoiceRecordFragment navigationVoiceRecordFragment = (NavigationVoiceRecordFragment) this.mPage;
        navigationVoiceRecordFragment.a();
        navigationVoiceRecordFragment.b();
    }

    public final void onDestroy() {
        super.onDestroy();
        VUIStateManager.f().D();
    }

    public final /* synthetic */ su a() {
        return new dgg(this);
    }
}
