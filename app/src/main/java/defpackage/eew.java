package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

/* renamed from: eew reason: default package */
/* compiled from: RouteEleGuideDlg */
public final class eew extends CompatDialog implements OnClickListener {
    /* access modifiers changed from: private */
    public Context a;
    private a b;
    private LinearLayout c;
    /* access modifiers changed from: private */
    public ImageView d;
    private LinearLayout e;

    /* renamed from: eew$a */
    /* compiled from: RouteEleGuideDlg */
    public interface a {
        void a();
    }

    public eew(Activity activity, a aVar) {
        super(activity, R.style.route_ele_guide_dialog);
        requestWindowFeature(1);
        Window window = getWindow();
        if (window != null) {
            window.setLayout(-1, -1);
            window.setWindowAnimations(R.style.route_ele_guide_dialog_window_anim);
        }
        setContentView(R.layout.electric_bike_guide);
        this.b = aVar;
        this.a = activity;
        this.c = (LinearLayout) findViewById(R.id.ele_bike_guide_root);
        this.d = (ImageView) findViewById(R.id.ele_guide_icon);
        this.e = (LinearLayout) findViewById(R.id.ele_bike_choose_root);
        NoDBClickUtil.a((View) (ImageView) findViewById(R.id.ele_guide_close), (OnClickListener) this);
        NoDBClickUtil.a((View) (LinearLayout) findViewById(R.id.bicycle_choose), (OnClickListener) this);
        NoDBClickUtil.a((View) (LinearLayout) findViewById(R.id.ele_choose), (OnClickListener) this);
    }

    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ele_guide_close) {
            a();
        } else if (id == R.id.bicycle_choose) {
            a();
        } else {
            if (id == R.id.ele_choose) {
                a();
                if (this.b != null) {
                    this.b.a();
                }
            }
        }
    }

    public final void show() {
        super.show();
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(225);
        this.c.setAnimation(alphaAnimation);
        ebr.a(true).postDelayed(new Runnable() {
            public final void run() {
                eew.this.d.setVisibility(0);
                TranslateAnimation translateAnimation = new TranslateAnimation((float) (-agn.a(eew.this.getContext(), 20.0f)), (float) agn.a(eew.this.a, 114.0f), 0.0f, 0.0f);
                translateAnimation.setDuration(300);
                translateAnimation.setFillAfter(true);
                eew.this.d.startAnimation(translateAnimation);
            }
        }, 300);
        if (euk.a()) {
            euk.a((Dialog) this, 0);
        }
    }

    public final void dismiss() {
        super.dismiss();
    }

    public final boolean onKeyDown(int i, @NonNull KeyEvent keyEvent) {
        if (i == 4) {
            a();
        }
        return false;
    }

    private void a() {
        AnimatorSet animatorSet = new AnimatorSet();
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        float measuredHeight = (float) (this.e.getMeasuredHeight() / 2);
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, new float[]{0.0f, (measuredHeight * 0.7f) + ((((float) (ags.a(getContext()).height() / 2)) - measuredHeight) - ((float) agn.a(getContext(), 155.0f)))});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, new float[]{0.0f, (float) (-((this.e.getMeasuredHeight() / 2) + agn.a(AMapPageUtil.getAppContext(), 20.0f)))});
        PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{1.0f, 0.0f});
        PropertyValuesHolder ofFloat4 = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{1.0f, 0.3f});
        PropertyValuesHolder ofFloat5 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{1.0f, 0.3f});
        ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(this.e, new PropertyValuesHolder[]{ofFloat, ofFloat2, ofFloat3, ofFloat4, ofFloat5}).setDuration(300);
        duration.setInterpolator(linearInterpolator);
        LinearInterpolator linearInterpolator2 = new LinearInterpolator();
        PropertyValuesHolder ofFloat6 = PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{1.0f, 0.0f});
        ObjectAnimator duration2 = ObjectAnimator.ofPropertyValuesHolder(this.c, new PropertyValuesHolder[]{ofFloat6}).setDuration(300);
        duration2.setInterpolator(linearInterpolator2);
        animatorSet.play(duration).with(duration2);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                eew.this.dismiss();
            }
        });
        animatorSet.start();
    }
}
