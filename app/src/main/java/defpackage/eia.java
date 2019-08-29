package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

/* renamed from: eia reason: default package */
/* compiled from: ShareBikeFinishCoinAnim */
public final class eia implements OnClickListener {
    private static int a = 375;
    private static int b = 1300;
    private View c;
    /* access modifiers changed from: private */
    public TextView d;
    /* access modifiers changed from: private */
    public ImageView e;
    private LinearLayout f;
    /* access modifiers changed from: private */
    public ImageView g;
    private Context h;
    private int i;
    /* access modifiers changed from: private */
    public int j;

    public eia(View view) {
        if (view != null) {
            this.h = AMapPageUtil.getAppContext();
            this.c = view;
            if (this.c != null) {
                this.d = (TextView) this.c.findViewById(R.id.sharebike_end_coin_count);
                this.f = (LinearLayout) this.c.findViewById(R.id.sharebike_end_coin_lin);
                this.e = (ImageView) this.c.findViewById(R.id.sharebike_end_coin_anim);
                this.g = (ImageView) this.c.findViewById(R.id.sharebike_end_coin_icon);
                NoDBClickUtil.a((View) this.f, (OnClickListener) this);
            }
        }
    }

    public final void a(int i2, final int i3) {
        if (i3 == 0) {
            eao.b("sharebikefinish:", "show anim bottom h = 0");
            return;
        }
        TextView textView = this.d;
        StringBuilder sb = new StringBuilder("+");
        sb.append(String.valueOf(i2));
        textView.setText(sb.toString());
        final LayoutParams layoutParams = (LayoutParams) this.e.getLayoutParams();
        layoutParams.bottomMargin = i3 - this.e.getHeight();
        this.e.requestLayout();
        this.i = i3;
        this.j = ags.a(this.h).hashCode() - ags.d(this.h);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-(((this.j - i3) / 2) + (this.e.getHeight() / 2))));
        translateAnimation.setDuration((long) a);
        translateAnimation.setFillAfter(true);
        this.e.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                eia.this.e.clearAnimation();
                eia.this.e.setVisibility(8);
                eia.this.e.setVisibility(0);
                layoutParams.bottomMargin = (i3 + ((eia.this.j - i3) / 2)) - (eia.this.e.getHeight() / 2);
                eia.c(eia.this);
            }
        });
    }

    public final void onClick(View view) {
        if (view.getId() == R.id.sharebike_end_coin_lin) {
            LogManager.actionLogV2("P00301", "B004");
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("amapuri://openFeature?featureName=GoldCoin"));
            AMapPageUtil.getPageContext().startScheme(intent);
            this.f.setVisibility(4);
            this.e.clearAnimation();
            this.e.setVisibility(8);
        }
    }

    static /* synthetic */ void c(eia eia) {
        AnimationDrawable animationDrawable = (AnimationDrawable) eia.h.getResources().getDrawable(R.drawable.shareriding_finish_coin);
        eia.e.setImageDrawable(animationDrawable);
        animationDrawable.start();
        ebr.a(true).postDelayed(new Runnable() {
            public final void run() {
                eia.this.e.clearAnimation();
                eia.this.e.setVisibility(8);
                eia.this.e.setVisibility(0);
                eia.this.e.setImageResource(R.drawable.sharebike_end_coin);
                eia.d(eia.this);
            }
        }, (long) b);
    }

    static /* synthetic */ void d(eia eia) {
        eia.f.setVisibility(0);
        eia.f.setAlpha(0.0f);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{1.0f, 0.4f});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{1.0f, 0.4f});
        PropertyValuesHolder ofFloat3 = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, new float[]{0.0f, (float) (-((((ags.a(eia.h).width() / 2) - eia.f.getLeft()) - eia.g.getLeft()) - (eia.g.getMeasuredWidth() / 2)))});
        PropertyValuesHolder ofFloat4 = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, new float[]{0.0f, (float) ((((eia.j - eia.i) / 2) - agn.a(eia.h, 25.0f)) - (eia.f.getMeasuredHeight() / 2))});
        ObjectAnimator duration = ObjectAnimator.ofPropertyValuesHolder(eia.e, new PropertyValuesHolder[]{ofFloat4, ofFloat3, ofFloat, ofFloat2}).setDuration((long) a);
        duration.setInterpolator(linearInterpolator);
        PropertyValuesHolder ofFloat5 = PropertyValuesHolder.ofFloat(View.ALPHA, new float[]{0.0f, 1.0f});
        ObjectAnimator duration2 = ObjectAnimator.ofPropertyValuesHolder(eia.f, new PropertyValuesHolder[]{ofFloat5}).setDuration((long) a);
        duration2.setInterpolator(linearInterpolator);
        PropertyValuesHolder ofFloat6 = PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{0.0f, 1.2f, 1.0f});
        PropertyValuesHolder ofFloat7 = PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{0.0f, 1.2f, 1.0f});
        ObjectAnimator duration3 = ObjectAnimator.ofPropertyValuesHolder(eia.d, new PropertyValuesHolder[]{ofFloat6, ofFloat7}).setDuration(200);
        duration3.setInterpolator(linearInterpolator);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(duration).with(duration2).before(duration3);
        animatorSet.start();
        duration.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                eia.this.d.setVisibility(0);
                eia.this.e.clearAnimation();
                eia.this.e.setVisibility(8);
                eia.this.g.setVisibility(0);
            }
        });
    }
}
