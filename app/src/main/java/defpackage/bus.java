package defpackage;

import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/* renamed from: bus reason: default package */
/* compiled from: TransitionAnimationLoader */
public final class bus {
    public static akb a(Class cls, Class cls2) {
        if (cls == null || cls2 == null) {
            return null;
        }
        if (c.class.isAssignableFrom(cls2)) {
            return new akb() {
                public final Animation a(ViewGroup viewGroup) {
                    return new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                }

                public final Animation b(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.b((float) viewGroup.getHeight(), 0.0f)});
                }

                public final Animation c(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.b(0.0f, (float) viewGroup.getHeight())});
                }

                public final Animation d(ViewGroup viewGroup) {
                    return new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                }
            };
        }
        if (a.class.isAssignableFrom(cls) && a.class.isAssignableFrom(cls2)) {
            return new akb() {
                public final Animation a(ViewGroup viewGroup) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    alphaAnimation.setDuration(375);
                    return bus.a(new Animation[]{bus.b(0.0f, (float) viewGroup.getHeight()), alphaAnimation});
                }

                public final Animation b(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.b((float) viewGroup.getHeight(), 0.0f)});
                }

                public final Animation c(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.b(0.0f, (float) viewGroup.getHeight())});
                }

                public final Animation d(ViewGroup viewGroup) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration(375);
                    return bus.a(new Animation[]{bus.b((float) viewGroup.getHeight(), 0.0f), alphaAnimation});
                }
            };
        }
        if (a.class.isAssignableFrom(cls) && b.class.isAssignableFrom(cls2)) {
            return new akb() {
                public final Animation a(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.a(0.0f, -200.0f)});
                }

                public final Animation b(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.a((float) viewGroup.getWidth(), 0.0f)});
                }

                public final Animation c(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.a(0.0f, (float) viewGroup.getWidth())});
                }

                public final Animation d(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.a(-200.0f, 0.0f)});
                }
            };
        }
        if (b.class.isAssignableFrom(cls) && a.class.isAssignableFrom(cls2)) {
            return new akb() {
                public final Animation a(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.b(0.0f, (float) viewGroup.getHeight())});
                }

                public final Animation b(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.b(-200.0f, 0.0f)});
                }

                public final Animation c(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.b(0.0f, (float) viewGroup.getHeight())});
                }

                public final Animation d(ViewGroup viewGroup) {
                    return bus.a(new Animation[]{bus.b(-200.0f, 0.0f)});
                }
            };
        }
        if (!b.class.isAssignableFrom(cls) || !b.class.isAssignableFrom(cls2)) {
            return null;
        }
        return new akb() {
            public final Animation a(ViewGroup viewGroup) {
                return bus.a(new Animation[]{bus.a(0.0f, -200.0f)});
            }

            public final Animation b(ViewGroup viewGroup) {
                return bus.a(new Animation[]{bus.a((float) viewGroup.getWidth(), 0.0f)});
            }

            public final Animation c(ViewGroup viewGroup) {
                return bus.a(new Animation[]{bus.a(0.0f, (float) viewGroup.getWidth())});
            }

            public final Animation d(ViewGroup viewGroup) {
                return bus.a(new Animation[]{bus.a(-200.0f, 0.0f)});
            }
        };
    }

    static /* synthetic */ Animation a(float f, float f2) {
        TranslateAnimation translateAnimation = new TranslateAnimation(f, f2, 0.0f, 0.0f);
        translateAnimation.setDuration(375);
        return translateAnimation;
    }

    static /* synthetic */ Animation a(Animation[] animationArr) {
        AnimationSet animationSet = new AnimationSet(true);
        for (Animation addAnimation : animationArr) {
            animationSet.addAnimation(addAnimation);
        }
        animationSet.setDuration(375);
        animationSet.setInterpolator(new buq());
        return animationSet;
    }

    static /* synthetic */ Animation b(float f, float f2) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, f, f2);
        translateAnimation.setDuration(375);
        return translateAnimation;
    }
}
