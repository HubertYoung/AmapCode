package defpackage;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.autonavi.bundle.amaphome.widget.guideview.Configuration;
import com.autonavi.bundle.amaphome.widget.guideview.GuideBuilder.SlideState;
import com.autonavi.bundle.amaphome.widget.guideview.GuideBuilder.a;
import com.autonavi.bundle.amaphome.widget.guideview.GuideBuilder.b;
import com.autonavi.bundle.amaphome.widget.guideview.MaskView;

/* renamed from: asg reason: default package */
/* compiled from: Guide */
public class asg implements OnKeyListener, OnTouchListener {
    static final /* synthetic */ boolean g = true;
    public Configuration a;
    public MaskView b;
    public asf[] c;
    public b d;
    public a e;
    float f = -1.0f;
    private boolean h = true;

    public final void a(Activity activity, ViewGroup viewGroup) {
        this.b = b(activity, viewGroup);
        if (viewGroup == null) {
            viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        }
        if (this.b.getParent() == null && this.a.a != null) {
            viewGroup.addView(this.b);
            if (this.a.q != -1) {
                Animation loadAnimation = AnimationUtils.loadAnimation(activity, this.a.q);
                if (g || loadAnimation != null) {
                    loadAnimation.setAnimationListener(new AnimationListener() {
                        public final void onAnimationRepeat(Animation animation) {
                        }

                        public final void onAnimationStart(Animation animation) {
                        }

                        public final void onAnimationEnd(Animation animation) {
                            if (asg.this.d != null) {
                                asg.this.d.a();
                            }
                        }
                    });
                    this.b.startAnimation(loadAnimation);
                    return;
                }
                throw new AssertionError();
            } else if (this.d != null) {
                this.d.a();
            }
        }
    }

    private void b() {
        if (this.b != null) {
            final ViewGroup viewGroup = (ViewGroup) this.b.getParent();
            if (viewGroup != null) {
                if (this.a.r != -1) {
                    Context context = this.b.getContext();
                    if (g || context != null) {
                        Animation loadAnimation = AnimationUtils.loadAnimation(context, this.a.r);
                        if (g || loadAnimation != null) {
                            loadAnimation.setAnimationListener(new AnimationListener() {
                                public final void onAnimationRepeat(Animation animation) {
                                }

                                public final void onAnimationStart(Animation animation) {
                                }

                                public final void onAnimationEnd(Animation animation) {
                                    viewGroup.removeView(asg.this.b);
                                    if (asg.this.d != null) {
                                        asg.this.d.b();
                                    }
                                    asg.this.a();
                                }
                            });
                            this.b.startAnimation(loadAnimation);
                            return;
                        }
                        throw new AssertionError();
                    }
                    throw new AssertionError();
                }
                viewGroup.removeView(this.b);
                if (this.d != null) {
                    this.d.b();
                }
                a();
            }
        }
    }

    private MaskView b(Activity activity, ViewGroup viewGroup) {
        int i;
        int i2;
        if (viewGroup == null) {
            viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        }
        MaskView maskView = new MaskView(activity);
        maskView.setFullingColor(activity.getResources().getColor(this.a.m));
        maskView.setFullingAlpha(this.a.h);
        maskView.setHighTargetCorner(this.a.k);
        maskView.setPadding(this.a.b);
        maskView.setPaddingLeft(this.a.c);
        maskView.setPaddingTop(this.a.d);
        maskView.setPaddingRight(this.a.e);
        maskView.setPaddingBottom(this.a.f);
        maskView.setHighTargetGraphStyle(this.a.l);
        maskView.setOverlayTarget(this.a.o);
        maskView.setOnKeyListener(this);
        if (viewGroup != null) {
            int[] iArr = new int[2];
            viewGroup.getLocationInWindow(iArr);
            i = iArr[0];
            i2 = iArr[1];
        } else {
            i = 0;
            i2 = 0;
        }
        if (this.a.a != null) {
            maskView.setTargetRect(ase.a(this.a.a, i, i2));
        } else {
            View findViewById = activity.findViewById(this.a.j);
            if (findViewById != null) {
                maskView.setTargetRect(ase.a(findViewById, i, i2));
            }
        }
        View findViewById2 = activity.findViewById(this.a.i);
        if (findViewById2 != null) {
            maskView.setFullingRect(ase.a(findViewById2, i, i2));
        }
        if (this.a.g) {
            maskView.setClickable(false);
        } else {
            maskView.setOnTouchListener(this);
        }
        for (asf a2 : this.c) {
            maskView.addView(ase.a(activity.getLayoutInflater(), a2));
        }
        return maskView;
    }

    public final void a() {
        this.a = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.b.removeAllViews();
        this.b = null;
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 1 || this.a == null || !this.a.n) {
            return false;
        }
        b();
        return true;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.f = motionEvent.getY();
        } else if (motionEvent.getAction() == 1) {
            if (this.f - motionEvent.getY() > ((float) agn.a(view.getContext(), 30.0f))) {
                if (this.e != null) {
                    SlideState slideState = SlideState.UP;
                }
            } else if (motionEvent.getY() - this.f > ((float) agn.a(view.getContext(), 30.0f)) && this.e != null) {
                SlideState slideState2 = SlideState.DOWN;
            }
            if (this.a != null && this.a.n) {
                b();
            }
        }
        return true;
    }
}
