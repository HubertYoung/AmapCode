package com.autonavi.minimap.auidebugger.boommenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheInfo;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.auidebugger.boommenu.Eases.EaseType;
import com.autonavi.minimap.auidebugger.boommenu.Types.BoomType;
import com.autonavi.minimap.auidebugger.boommenu.Types.ButtonType;
import com.autonavi.minimap.auidebugger.boommenu.Types.ClickEffectType;
import com.autonavi.minimap.auidebugger.boommenu.Types.DimType;
import com.autonavi.minimap.auidebugger.boommenu.Types.OrderType;
import com.autonavi.minimap.auidebugger.boommenu.Types.PlaceType;
import com.autonavi.minimap.auidebugger.boommenu.Types.StateType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class BoomMenuButton extends FrameLayout implements com.autonavi.minimap.auidebugger.boommenu.CircleButton.a, com.autonavi.minimap.auidebugger.boommenu.HamButton.a {
    public static final int MAX_CIRCLE_BUTTON_NUMBER = 9;
    public static final int MAX_HAM_BUTTON_NUMBER = 4;
    public static final boolean MEMORY_OPTIMIZATION = true;
    public static final int MIN_CIRCLE_BUTTON_NUMBER = 1;
    public static final int MIN_HAM_BUTTON_NUMBER = 1;
    static Activity sActivity;
    /* access modifiers changed from: private */
    public ViewGroup animationLayout;
    /* access modifiers changed from: private */
    public boolean animationPlaying;
    /* access modifiers changed from: private */
    public a animatorListener;
    private boolean autoDismiss;
    private int barHeight;
    private int barWidth;
    private Bar[] bars;
    private int boomButtonColor;
    private int boomButtonPressedColor;
    private int boomButtonRadius;
    private BoomType boomType;
    private int buttonNum;
    /* access modifiers changed from: private */
    public ButtonType buttonType;
    private int buttonWidth;
    /* access modifiers changed from: private */
    public boolean cancelable;
    public CircleButton[] circleButtons;
    private ClickEffectType clickEffectType;
    private int[][] colors;
    private int delay;
    private DimType dimType;
    private int dotHeight;
    private int dotWidth;
    private Dot[] dots;
    private Drawable[] drawables;
    private int duration;
    private int[][] endLocations;
    private FrameLayout frameLayout;
    private int frames;
    private int hamButtonHeight;
    private int hamButtonWidth;
    /* access modifiers changed from: private */
    public HamButton[] hamButtons;
    private EaseType hideMoveEaseType;
    private OrderType hideOrderType;
    private EaseType hideRotateEaseType;
    private EaseType hideScaleEaseType;
    private boolean isInActionBar;
    /* access modifiers changed from: private */
    public boolean isInList;
    private Context mContext;
    private OnTouchListener mOnTouchListener;
    public ArrayList<String> mStrings;
    private c onClickListener;
    /* access modifiers changed from: private */
    public d onStateChangeListener;
    private e onSubButtonClickListener;
    private int[][] originalLocations;
    private PlaceType placeType;
    private View ripple;
    private int rotateDegree;
    private ShadowLayout shadowLayout;
    private ShareLines shareLines;
    private EaseType showMoveEaseType;
    private OrderType showOrderType;
    private EaseType showRotateEaseType;
    private EaseType showScaleEaseType;
    private int[][] startLocations;
    /* access modifiers changed from: private */
    public StateType state;
    public String[] strings;
    private ScaleType subButtonImageScaleType;
    private int subButtonTextColor;
    private float subButtonsXOffsetOfShadow;
    private float subButtonsYOffsetOfShadow;

    public interface a {
    }

    public static class b {
        public int A = -1;
        public ScaleType B = ScaleType.CENTER;
        public c C = null;
        public a D = null;
        public e E = null;
        public float F = 3.0f;
        public int G = -1;
        public int H = -1;
        public ArrayList<Drawable> a = null;
        public ArrayList<int[]> b = null;
        public ArrayList<String> c = null;
        public int d = 80;
        public int e = 800;
        public int f = 100;
        public OrderType g = OrderType.DEFAULT;
        public OrderType h = OrderType.DEFAULT;
        public ButtonType i = ButtonType.CIRCLE;
        public BoomType j = BoomType.HORIZONTAL_THROW;
        public PlaceType k = null;
        public EaseType l = EaseType.EaseOutBack;
        public EaseType m = EaseType.EaseOutCirc;
        public EaseType n = EaseType.EaseOutBack;
        public EaseType o = EaseType.EaseOutCirc;
        public int p = 720;
        public EaseType q = EaseType.EaseOutBack;
        public EaseType r = EaseType.Linear;
        public boolean s = true;
        public boolean t = true;
        public DimType u = DimType.DIM_6;
        public ClickEffectType v = ClickEffectType.RIPPLE;
        public float w = 0.0f;
        public float x = 0.0f;
        public float y = 0.0f;
        public float z = 0.0f;

        public final b a(Drawable drawable, int[] iArr, String str) {
            if (this.a == null) {
                this.a = new ArrayList<>();
            }
            this.a.add(drawable);
            if (this.b == null) {
                this.b = new ArrayList<>();
            }
            this.b.add(iArr);
            if (this.c == null) {
                this.c = new ArrayList<>();
            }
            this.c.add(str);
            return this;
        }
    }

    public interface c {
    }

    public interface d {
        void stateChange(boolean z);
    }

    public interface e {
        void onClick(int i);
    }

    public BoomMenuButton(Context context) {
        this(context, null);
    }

    public BoomMenuButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.animationLayout = null;
        this.mStrings = new ArrayList<>();
        this.originalLocations = (int[][]) Array.newInstance(int.class, new int[]{9, 2});
        this.startLocations = (int[][]) Array.newInstance(int.class, new int[]{9, 2});
        this.endLocations = (int[][]) Array.newInstance(int.class, new int[]{9, 2});
        this.animationPlaying = false;
        this.state = StateType.CLOSED;
        this.buttonNum = 0;
        this.circleButtons = new CircleButton[9];
        this.hamButtons = new HamButton[4];
        this.dots = new Dot[9];
        this.bars = new Bar[4];
        this.shareLines = null;
        this.drawables = null;
        this.colors = null;
        this.strings = null;
        this.isInActionBar = false;
        this.isInList = false;
        this.boomButtonColor = 0;
        this.boomButtonPressedColor = 0;
        this.frames = 80;
        this.duration = 400;
        this.delay = 100;
        this.showOrderType = OrderType.DEFAULT;
        this.hideOrderType = OrderType.DEFAULT;
        this.buttonType = ButtonType.CIRCLE;
        this.boomType = BoomType.HORIZONTAL_THROW;
        this.placeType = null;
        cnu.a();
        this.dotWidth = (int) cnu.a(8.0f);
        cnu.a();
        this.dotHeight = (int) cnu.a(8.0f);
        cnu.a();
        this.buttonWidth = (int) cnu.a(88.0f);
        cnu.a();
        this.barWidth = (int) cnu.a(36.0f);
        cnu.a();
        this.barHeight = (int) cnu.a(6.0f);
        this.hamButtonWidth = 0;
        cnu.a();
        this.hamButtonHeight = (int) cnu.a(80.0f);
        cnu.a();
        this.boomButtonRadius = (int) cnu.a(56.0f);
        this.showMoveEaseType = EaseType.EaseOutBack;
        this.hideMoveEaseType = EaseType.EaseOutCirc;
        this.showScaleEaseType = EaseType.EaseOutBack;
        this.hideScaleEaseType = EaseType.EaseOutCirc;
        this.rotateDegree = 720;
        this.showRotateEaseType = EaseType.EaseOutBack;
        this.hideRotateEaseType = EaseType.Linear;
        this.autoDismiss = true;
        this.cancelable = true;
        this.dimType = DimType.DIM_6;
        this.clickEffectType = ClickEffectType.RIPPLE;
        this.subButtonsXOffsetOfShadow = 0.0f;
        this.subButtonsYOffsetOfShadow = 0.0f;
        this.subButtonTextColor = -1;
        this.subButtonImageScaleType = ScaleType.CENTER;
        this.onClickListener = null;
        this.animatorListener = null;
        this.onSubButtonClickListener = null;
        this.onStateChangeListener = null;
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BoomMenuButton, 0, 0);
        if (obtainStyledAttributes != null) {
            try {
                this.isInActionBar = obtainStyledAttributes.getBoolean(R.styleable.BoomMenuButton_boom_inActionBar, false);
                this.isInList = obtainStyledAttributes.getBoolean(R.styleable.BoomMenuButton_boom_inList, false);
                this.boomButtonColor = obtainStyledAttributes.getColor(R.styleable.BoomMenuButton_boom_button_color, ContextCompat.getColor(this.mContext, R.color.default_boom_button_color));
                this.boomButtonPressedColor = obtainStyledAttributes.getColor(R.styleable.BoomMenuButton_boom_button_pressed_color, ContextCompat.getColor(this.mContext, R.color.default_boom_button_color_pressed));
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        if (this.isInActionBar || this.isInList) {
            LayoutInflater.from(context).inflate(R.layout.boom_menu_button_in_action_bar, this, true);
            this.frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
            this.frameLayout.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    BoomMenuButton.this.shoot();
                }
            });
        } else {
            if (VERSION.SDK_INT >= 21) {
                LayoutInflater.from(context).inflate(R.layout.boom_menu_button, this, true);
            } else {
                LayoutInflater.from(context).inflate(R.layout.boom_menu_button_below_lollipop, this, true);
            }
            this.shadowLayout = (ShadowLayout) findViewById(R.id.shadow_layout);
            this.frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
            this.ripple = findViewById(R.id.ripple);
            setRipple(this.clickEffectType);
            setBoomButtonBackgroundColor(this.boomButtonPressedColor, this.boomButtonColor);
        }
        cnu.a();
        cnu.a();
        this.hamButtonWidth = (int) (((float) ((cnu.a(getContext()) * 8) / 9)) + cnu.a(4.0f));
        setWillNotDraw(false);
    }

    public void changeText(String str) {
        this.strings[4] = str;
    }

    public void changeText(int i, String str) {
        this.strings[i] = str;
    }

    public void init(Drawable[] drawableArr, String[] strArr, int[][] iArr, ButtonType buttonType2, BoomType boomType2, PlaceType placeType2, EaseType easeType, EaseType easeType2, EaseType easeType3, EaseType easeType4, EaseType easeType5, EaseType easeType6, Integer num) {
        errorJudge(drawableArr, strArr, iArr, buttonType2);
        this.buttonType = buttonType2;
        this.boomType = boomType2;
        if (placeType2 == null) {
            throw new RuntimeException("Place type is null!");
        }
        this.placeType = placeType2;
        if (easeType != null) {
            this.showMoveEaseType = easeType;
        }
        if (easeType2 != null) {
            this.showScaleEaseType = easeType2;
        }
        if (easeType3 != null) {
            this.showRotateEaseType = easeType3;
        }
        if (easeType4 != null) {
            this.hideMoveEaseType = easeType4;
        }
        if (easeType5 != null) {
            this.hideScaleEaseType = easeType5;
        }
        if (easeType6 != null) {
            this.hideRotateEaseType = easeType6;
        }
        if (num != null) {
            this.rotateDegree = num.intValue();
        }
        int i = 0;
        if (buttonType2.equals(ButtonType.CIRCLE)) {
            this.buttonNum = drawableArr.length;
            this.drawables = drawableArr;
            this.colors = iArr;
            this.strings = strArr;
            while (i < this.buttonNum) {
                this.dots[i] = new Dot(this.mContext);
                this.dots[i].setColor(iArr[i][1]);
                i++;
            }
            placeDots();
            return;
        }
        if (buttonType2.equals(ButtonType.HAM)) {
            cnu.a();
            this.hamButtonWidth = (cnu.a(getContext()) * 8) / 9;
            this.buttonNum = drawableArr.length;
            this.drawables = drawableArr;
            this.colors = iArr;
            this.strings = strArr;
            while (i < this.buttonNum) {
                this.bars[i] = new Bar(this.mContext);
                this.bars[i].setColor(iArr[i][1]);
                i++;
            }
            placeBars();
        }
    }

    private void errorJudge(Drawable[] drawableArr, String[] strArr, int[][] iArr, ButtonType buttonType2) {
        if (drawableArr == null) {
            throw new RuntimeException("The button's drawables are null!");
        } else if (iArr == null) {
            throw new RuntimeException("The button's colors are null!");
        } else if (buttonType2.equals(ButtonType.CIRCLE)) {
            if (1 > drawableArr.length || drawableArr.length > 9 || ((strArr != null && (1 > strArr.length || strArr.length > 9)) || 1 > iArr.length || iArr.length > 9)) {
                throw new RuntimeException("The circle type button's length must be in [1, 9]!");
            }
        } else if (!buttonType2.equals(ButtonType.HAM)) {
        } else {
            if (1 > drawableArr.length || drawableArr.length > 4 || ((strArr != null && (1 > strArr.length || strArr.length > 4)) || 1 > iArr.length || iArr.length > 4)) {
                throw new RuntimeException("The ham type button's length must be in [1, 4]!");
            }
        }
    }

    private void placeDots() {
        this.frameLayout.removeAllViews();
        LayoutParams[] a2 = cnt.a(this.placeType, this.frameLayout.getWidth(), this.frameLayout.getHeight(), this.dotWidth, this.dotHeight);
        if (PlaceType.SHARE_3_1.v <= this.placeType.v && this.placeType.v <= PlaceType.SHARE_9_2.v) {
            this.shareLines = new ShareLines(this.mContext);
            float[][] fArr = (float[][]) Array.newInstance(float.class, new int[]{3, 2});
            fArr[0][0] = (float) (a2[0].leftMargin + (this.dotWidth / 2));
            fArr[0][1] = (float) (a2[0].topMargin + (this.dotHeight / 2));
            fArr[1][0] = (float) (a2[1].leftMargin + (this.dotWidth / 2));
            fArr[1][1] = (float) (a2[1].topMargin + (this.dotHeight / 2));
            fArr[2][0] = (float) (a2[2].leftMargin + (this.dotWidth / 2));
            fArr[2][1] = (float) (a2[2].topMargin + (this.dotHeight / 2));
            this.shareLines.setLocations(fArr);
            this.shareLines.setOffset(1.0f);
            this.frameLayout.addView(this.shareLines, new LayoutParams(this.frameLayout.getWidth(), this.frameLayout.getHeight()));
        }
        for (int i = 0; i < this.buttonNum; i++) {
            this.frameLayout.addView(this.dots[i], a2[i]);
        }
    }

    private void placeBars() {
        LayoutParams[] layoutParamsArr;
        this.frameLayout.removeAllViews();
        PlaceType placeType2 = this.placeType;
        int width = this.frameLayout.getWidth();
        int height = this.frameLayout.getHeight();
        int i = this.barWidth;
        int i2 = this.barHeight;
        if (placeType2.equals(PlaceType.HAM_1_1)) {
            layoutParamsArr = new LayoutParams[]{new LayoutParams(i, i2)};
            layoutParamsArr[0].leftMargin = (width / 2) - (i / 2);
            layoutParamsArr[0].topMargin = (height / 2) - (i2 / 2);
        } else if (placeType2.equals(PlaceType.HAM_2_1)) {
            layoutParamsArr = new LayoutParams[2];
            layoutParamsArr[0] = new LayoutParams(i, i2);
            int i3 = (width / 2) - (i / 2);
            layoutParamsArr[0].leftMargin = i3;
            int i4 = height / 2;
            layoutParamsArr[0].topMargin = i4 - ((i2 * 3) / 2);
            layoutParamsArr[1] = new LayoutParams(i, i2);
            layoutParamsArr[1].leftMargin = i3;
            layoutParamsArr[1].topMargin = i4 + (i2 / 2);
        } else if (placeType2.equals(PlaceType.HAM_3_1)) {
            layoutParamsArr = new LayoutParams[3];
            layoutParamsArr[0] = new LayoutParams(i, i2);
            int i5 = (width / 2) - (i / 2);
            layoutParamsArr[0].leftMargin = i5;
            int i6 = height / 2;
            layoutParamsArr[0].topMargin = i6 - ((i2 * 13) / 6);
            layoutParamsArr[1] = new LayoutParams(i, i2);
            layoutParamsArr[1].leftMargin = i5;
            layoutParamsArr[1].topMargin = i6 - (i2 / 2);
            layoutParamsArr[2] = new LayoutParams(i, i2);
            layoutParamsArr[2].leftMargin = i5;
            layoutParamsArr[2].topMargin = i6 + ((i2 * 7) / 6);
        } else if (placeType2.equals(PlaceType.HAM_4_1)) {
            LayoutParams[] layoutParamsArr2 = new LayoutParams[4];
            layoutParamsArr2[0] = new LayoutParams(i, i2);
            int i7 = (width / 2) - (i / 2);
            layoutParamsArr2[0].leftMargin = i7;
            int i8 = height / 2;
            layoutParamsArr2[0].topMargin = i8 - ((i2 * 11) / 4);
            layoutParamsArr2[1] = new LayoutParams(i, i2);
            layoutParamsArr2[1].leftMargin = i7;
            layoutParamsArr2[1].topMargin = i8 - ((i2 * 5) / 4);
            layoutParamsArr2[2] = new LayoutParams(i, i2);
            layoutParamsArr2[2].leftMargin = i7;
            layoutParamsArr2[2].topMargin = (i2 / 4) + i8;
            layoutParamsArr2[3] = new LayoutParams(i, i2);
            layoutParamsArr2[3].leftMargin = i7;
            layoutParamsArr2[3].topMargin = i8 + ((i2 * 7) / 4);
            layoutParamsArr = layoutParamsArr2;
        } else {
            layoutParamsArr = null;
        }
        for (LayoutParams layoutParams : layoutParamsArr) {
            layoutParams.gravity = 51;
        }
        for (int i9 = 0; i9 < layoutParamsArr.length; i9++) {
            this.frameLayout.addView(this.bars[i9], layoutParamsArr[i9]);
        }
    }

    /* access modifiers changed from: private */
    public void shoot() {
        if (this.buttonType.equals(ButtonType.CIRCLE)) {
            for (int i = 0; i < this.buttonNum; i++) {
                this.circleButtons[i] = new CircleButton(this.mContext);
                this.circleButtons[i].setOnCircleButtonClickListener(this, i);
                this.circleButtons[i].setDrawable(this.drawables[i]);
                if (this.strings != null) {
                    this.circleButtons[i].setText(this.strings[i]);
                }
                this.circleButtons[i].setColor(this.colors[i][0], this.colors[i][1]);
                this.circleButtons[i].setShadowDx(this.subButtonsXOffsetOfShadow);
                this.circleButtons[i].setShadowDy(this.subButtonsYOffsetOfShadow);
                this.circleButtons[i].getTextView().setTextColor(this.subButtonTextColor);
                this.circleButtons[i].getImageView().setScaleType(this.subButtonImageScaleType);
                this.circleButtons[i].setRipple(this.clickEffectType);
            }
        } else if (this.buttonType.equals(ButtonType.HAM)) {
            for (int i2 = 0; i2 < this.buttonNum; i2++) {
                this.hamButtons[i2] = new HamButton(this.mContext);
                this.hamButtons[i2].setOnHamButtonClickListener(this, i2);
                this.hamButtons[i2].setDrawable(this.drawables[i2]);
                if (this.strings != null) {
                    this.hamButtons[i2].setText(this.strings[i2]);
                }
                this.hamButtons[i2].setColor(this.colors[i2][0], this.colors[i2][1]);
                this.hamButtons[i2].setShadowDx(this.subButtonsXOffsetOfShadow);
                this.hamButtons[i2].setShadowDy(this.subButtonsYOffsetOfShadow);
                this.hamButtons[i2].getTextView().setTextColor(this.subButtonTextColor);
                this.hamButtons[i2].getImageView().setScaleType(this.subButtonImageScaleType);
                this.hamButtons[i2].setRipple(this.clickEffectType);
            }
        }
        setRipple(this.clickEffectType);
        if (!this.animationPlaying) {
            this.animationPlaying = true;
            dimAnimationLayout();
            startShowAnimations();
        }
    }

    private void dimAnimationLayout() {
        if (this.animationLayout == null) {
            this.animationLayout = createAnimationLayout();
            this.animationLayout.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (!BoomMenuButton.this.animationPlaying && BoomMenuButton.this.cancelable) {
                        BoomMenuButton.this.startHideAnimations();
                    }
                }
            });
        }
        this.animationLayout.setVisibility(0);
        ObjectAnimator duration2 = ObjectAnimator.ofInt(this.animationLayout, "backgroundColor", new int[]{DimType.DIM_0.value, this.dimType.value}).setDuration((long) (this.duration + (this.delay * (this.buttonNum - 1))));
        duration2.setEvaluator(new ArgbEvaluator());
        duration2.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                if (BoomMenuButton.this.animatorListener != null) {
                    BoomMenuButton.this.animatorListener;
                }
                BoomMenuButton.this.state = StateType.OPENING;
            }

            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (BoomMenuButton.this.animatorListener != null) {
                    BoomMenuButton.this.animatorListener;
                }
                BoomMenuButton.this.state = StateType.OPEN;
                if (BoomMenuButton.this.onStateChangeListener != null) {
                    BoomMenuButton.this.onStateChangeListener.stateChange(true);
                }
            }
        });
        duration2.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (BoomMenuButton.this.animatorListener != null) {
                    BoomMenuButton.this.animatorListener;
                    valueAnimator.getAnimatedFraction();
                }
            }
        });
        duration2.start();
        if (PlaceType.SHARE_3_1.v <= this.placeType.v && this.placeType.v <= PlaceType.SHARE_9_2.v) {
            ObjectAnimator duration3 = ObjectAnimator.ofFloat(this.shareLines, "offset", new float[]{1.0f, 0.0f}).setDuration((long) (this.duration + (this.delay * (this.buttonNum - 1))));
            duration3.setStartDelay(0);
            duration3.start();
        }
    }

    private void startShowAnimations() {
        if (this.animationLayout != null) {
            this.animationLayout.removeAllViews();
        }
        if (this.buttonType.equals(ButtonType.CIRCLE)) {
            getEndLocations();
            if (this.showOrderType.equals(OrderType.DEFAULT)) {
                for (int i = 0; i < this.buttonNum; i++) {
                    this.dots[i].getLocationOnScreen(this.startLocations[i]);
                    this.originalLocations[i] = this.startLocations[i];
                    int[] iArr = this.startLocations[i];
                    iArr[0] = iArr[0] - ((this.buttonWidth - this.dots[i].getWidth()) / 2);
                    int[] iArr2 = this.startLocations[i];
                    iArr2[1] = iArr2[1] - ((this.buttonWidth - this.dots[i].getHeight()) / 2);
                    setShowAnimation(this.dots[i], this.circleButtons[i], this.originalLocations[i], this.startLocations[i], this.endLocations[i], i);
                }
            } else if (this.showOrderType.equals(OrderType.REVERSE)) {
                for (int i2 = 0; i2 < this.buttonNum; i2++) {
                    this.dots[i2].getLocationOnScreen(this.startLocations[i2]);
                    int[] iArr3 = this.startLocations[i2];
                    iArr3[0] = iArr3[0] - ((this.buttonWidth - this.dots[i2].getWidth()) / 2);
                    int[] iArr4 = this.startLocations[i2];
                    iArr4[1] = iArr4[1] - ((this.buttonWidth - this.dots[i2].getHeight()) / 2);
                    setShowAnimation(this.dots[i2], this.circleButtons[i2], this.originalLocations[i2], this.startLocations[i2], this.endLocations[i2], (this.buttonNum - i2) - 1);
                }
            } else if (this.showOrderType.equals(OrderType.RANDOM)) {
                Random random = new Random();
                boolean[] zArr = new boolean[this.buttonNum];
                for (int i3 = 0; i3 < this.buttonNum; i3++) {
                    zArr[i3] = false;
                }
                int i4 = 0;
                while (true) {
                    int nextInt = random.nextInt(this.buttonNum);
                    if (!zArr[nextInt]) {
                        zArr[nextInt] = true;
                        this.dots[i4].getLocationOnScreen(this.startLocations[i4]);
                        int[] iArr5 = this.startLocations[i4];
                        iArr5[0] = iArr5[0] - ((this.buttonWidth - this.dots[i4].getWidth()) / 2);
                        int[] iArr6 = this.startLocations[i4];
                        iArr6[1] = iArr6[1] - ((this.buttonWidth - this.dots[i4].getHeight()) / 2);
                        setShowAnimation(this.dots[i4], this.circleButtons[i4], this.originalLocations[i4], this.startLocations[i4], this.endLocations[i4], nextInt);
                        i4++;
                        if (i4 == this.buttonNum) {
                            return;
                        }
                    }
                }
            }
        } else if (this.buttonType.equals(ButtonType.HAM)) {
            getEndLocations();
            if (this.showOrderType.equals(OrderType.DEFAULT)) {
                for (int i5 = 0; i5 < this.buttonNum; i5++) {
                    this.bars[i5].getLocationOnScreen(this.startLocations[i5]);
                    int[] iArr7 = this.startLocations[i5];
                    iArr7[0] = iArr7[0] - ((this.hamButtonWidth - this.bars[i5].getWidth()) / 2);
                    int[] iArr8 = this.startLocations[i5];
                    iArr8[1] = iArr8[1] - ((this.hamButtonHeight - this.bars[i5].getHeight()) / 2);
                    setShowAnimation(this.bars[i5], this.hamButtons[i5], this.originalLocations[i5], this.startLocations[i5], this.endLocations[i5], i5);
                }
            } else if (this.showOrderType.equals(OrderType.REVERSE)) {
                for (int i6 = 0; i6 < this.buttonNum; i6++) {
                    this.bars[i6].getLocationOnScreen(this.startLocations[i6]);
                    int[] iArr9 = this.startLocations[i6];
                    iArr9[0] = iArr9[0] - ((this.hamButtonWidth - this.bars[i6].getWidth()) / 2);
                    int[] iArr10 = this.startLocations[i6];
                    iArr10[1] = iArr10[1] - ((this.hamButtonHeight - this.bars[i6].getHeight()) / 2);
                    setShowAnimation(this.bars[i6], this.hamButtons[i6], this.originalLocations[i6], this.startLocations[i6], this.endLocations[i6], (this.buttonNum - i6) - 1);
                }
            } else if (this.showOrderType.equals(OrderType.RANDOM)) {
                Random random2 = new Random();
                boolean[] zArr2 = new boolean[this.buttonNum];
                for (int i7 = 0; i7 < this.buttonNum; i7++) {
                    zArr2[i7] = false;
                }
                int i8 = 0;
                while (true) {
                    int nextInt2 = random2.nextInt(this.buttonNum);
                    if (!zArr2[nextInt2]) {
                        zArr2[nextInt2] = true;
                        this.bars[i8].getLocationOnScreen(this.startLocations[i8]);
                        int[] iArr11 = this.startLocations[i8];
                        iArr11[0] = iArr11[0] - ((this.hamButtonWidth - this.bars[i8].getWidth()) / 2);
                        int[] iArr12 = this.startLocations[i8];
                        iArr12[1] = iArr12[1] - ((this.hamButtonHeight - this.bars[i8].getHeight()) / 2);
                        setShowAnimation(this.bars[i8], this.hamButtons[i8], this.originalLocations[i8], this.startLocations[i8], this.endLocations[i8], nextInt2);
                        i8++;
                        if (i8 == this.buttonNum) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void getEndLocations() {
        cnu.a();
        int a2 = cnu.a(this.mContext);
        cnu.a();
        int b2 = cnu.b(this.mContext);
        if (this.buttonType.equals(ButtonType.CIRCLE)) {
            this.endLocations = cnr.a(this.placeType, a2, b2, this.buttonWidth, this.buttonWidth);
            return;
        }
        if (this.buttonType.equals(ButtonType.HAM)) {
            this.endLocations = cnr.a(this.placeType, a2, b2, this.hamButtonWidth, this.hamButtonHeight);
        }
    }

    private ViewGroup createAnimationLayout() {
        LinearLayout linearLayout = new LinearLayout(this.mContext);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.setBackgroundResource(17170445);
        ((ViewGroup) scanForActivity(this.mContext).getWindow().getDecorView()).addView(linearLayout);
        return linearLayout;
    }

    private View setViewLocationInAnimationLayout(View view, int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        LinearLayout.LayoutParams layoutParams = this.buttonType.equals(ButtonType.CIRCLE) ? new LinearLayout.LayoutParams(this.buttonWidth, this.buttonWidth) : this.buttonType.equals(ButtonType.HAM) ? new LinearLayout.LayoutParams(this.hamButtonWidth, this.hamButtonHeight) : null;
        layoutParams.leftMargin = i;
        layoutParams.topMargin = i2;
        view.setVisibility(4);
        this.animationLayout.addView(view, layoutParams);
        return view;
    }

    public void setShowAnimation(final View view, View view2, int[] iArr, int[] iArr2, int[] iArr3, int i) {
        float f;
        float f2;
        Object obj;
        view2.bringToFront();
        final View viewLocationInAnimationLayout = setViewLocationInAnimationLayout(view2, iArr);
        float[] fArr = {((float) iArr2[0]) * 1.0f, ((float) iArr2[1]) * 1.0f};
        float[] fArr2 = {((float) iArr3[0]) * 1.0f, ((float) iArr3[1]) * 1.0f};
        float[] fArr3 = new float[(this.frames + 1)];
        float[] fArr4 = new float[(this.frames + 1)];
        getShowXY(fArr, fArr2, fArr3, fArr4);
        this.duration = 1;
        this.delay = 1;
        if (viewLocationInAnimationLayout != null) {
            ObjectAnimator duration2 = ObjectAnimator.ofFloat(viewLocationInAnimationLayout, DictionaryKeys.CTRLXY_X, fArr3).setDuration((long) this.duration);
            duration2.setStartDelay((long) (this.delay * i));
            duration2.setInterpolator(cns.a(this.showMoveEaseType));
            duration2.start();
            ObjectAnimator duration3 = ObjectAnimator.ofFloat(viewLocationInAnimationLayout, DictionaryKeys.CTRLXY_Y, fArr4).setDuration((long) this.duration);
            duration3.setStartDelay((long) (this.delay * i));
            duration3.setInterpolator(cns.a(this.showMoveEaseType));
            duration3.start();
        }
        if (this.buttonType.equals(ButtonType.CIRCLE)) {
            f = (((float) this.dotWidth) * 1.0f) / ((float) this.buttonWidth);
            f2 = (((float) this.dotWidth) * 1.0f) / ((float) this.buttonWidth);
        } else if (this.buttonType.equals(ButtonType.HAM)) {
            f = (((float) this.barWidth) * 1.0f) / ((float) this.hamButtonWidth);
            f2 = (((float) this.barHeight) * 1.0f) / ((float) this.hamButtonHeight);
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        if (viewLocationInAnimationLayout != null) {
            viewLocationInAnimationLayout.setScaleX(f);
            ObjectAnimator duration4 = ObjectAnimator.ofFloat(viewLocationInAnimationLayout, "scaleX", new float[]{f, 1.0f}).setDuration((long) this.duration);
            duration4.setStartDelay((long) (this.delay * i));
            duration4.setInterpolator(cns.a(this.showScaleEaseType));
            duration4.start();
            viewLocationInAnimationLayout.setScaleY(f2);
            ObjectAnimator duration5 = ObjectAnimator.ofFloat(viewLocationInAnimationLayout, "scaleY", new float[]{f2, 1.0f}).setDuration((long) this.duration);
            duration5.setStartDelay((long) (this.delay * i));
            duration5.setInterpolator(cns.a(this.showScaleEaseType));
            duration5.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    view.setVisibility(4);
                    viewLocationInAnimationLayout.setVisibility(0);
                }

                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    BoomMenuButton.this.animationPlaying = false;
                }
            });
            duration5.start();
        }
        ImageView imageView = null;
        if (view2 != null && (view2 instanceof CircleButton)) {
            CircleButton circleButton = (CircleButton) view2;
            imageView = circleButton.getImageView();
            obj = circleButton.getTextView();
        } else if (view2 == null || !(view2 instanceof HamButton)) {
            obj = null;
        } else {
            HamButton hamButton = (HamButton) view2;
            imageView = hamButton.getImageView();
            obj = hamButton.getTextView();
        }
        if (imageView != null) {
            ObjectAnimator duration6 = ObjectAnimator.ofFloat(imageView, "alpha", new float[]{0.0f, 1.0f}).setDuration((long) this.duration);
            duration6.setStartDelay((long) (this.delay * i));
            duration6.setInterpolator(cns.a(this.showMoveEaseType));
            duration6.start();
        }
        if (obj != null) {
            ObjectAnimator duration7 = ObjectAnimator.ofFloat(obj, "alpha", new float[]{0.0f, 1.0f}).setDuration((long) this.duration);
            duration7.setStartDelay((long) (this.delay * i));
            duration7.setInterpolator(cns.a(this.showMoveEaseType));
            duration7.start();
        }
        if (viewLocationInAnimationLayout != null && (viewLocationInAnimationLayout instanceof CircleButton)) {
            ObjectAnimator duration8 = ObjectAnimator.ofFloat(((CircleButton) viewLocationInAnimationLayout).getFrameLayout(), APCacheInfo.EXTRA_ROTATION, new float[]{0.0f, (float) this.rotateDegree}).setDuration((long) this.duration);
            duration8.setStartDelay((long) (this.delay * i));
            duration8.setInterpolator(cns.a(this.showRotateEaseType));
            duration8.start();
        }
    }

    /* JADX WARNING: type inference failed for: r21v0, types: [float[]] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getShowXY(float[] r18, float[] r19, float[] r20, float[] r21) {
        /*
            r17 = this;
            r0 = r17
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r4 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.LINE
            boolean r3 = r3.equals(r4)
            r4 = 1065353216(0x3f800000, float:1.0)
            r5 = 1
            r6 = 0
            if (r3 == 0) goto L_0x0041
            r3 = r18[r6]
            r7 = r18[r5]
            r8 = r19[r6]
            r5 = r19[r5]
            float r5 = r5 - r7
            float r8 = r8 - r3
            float r5 = r5 / r8
            float r3 = r3 * r5
            float r7 = r7 - r3
            int r3 = r0.frames
            float r3 = (float) r3
            float r4 = r4 / r3
            r2 = r19[r6]
            r3 = r18[r6]
            float r2 = r2 - r3
            r3 = 0
        L_0x0028:
            int r8 = r0.frames
            if (r3 > r8) goto L_0x0040
            float r8 = (float) r3
            float r8 = r8 * r4
            r9 = r18[r6]
            float r8 = r8 * r2
            float r9 = r9 + r8
            r20[r3] = r9
            r8 = r20[r3]
            float r8 = r8 * r5
            float r8 = r8 + r7
            r21[r3] = r8
            int r3 = r3 + 1
            goto L_0x0028
        L_0x0040:
            return
        L_0x0041:
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r7 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.PARABOLA
            boolean r3 = r3.equals(r7)
            r7 = 1073741824(0x40000000, float:2.0)
            if (r3 == 0) goto L_0x00ba
            r3 = r18[r6]
            r8 = r18[r5]
            r9 = r19[r6]
            r10 = r19[r5]
            r11 = r18[r6]
            r12 = r19[r6]
            float r11 = r11 + r12
            float r11 = r11 / r7
            r12 = r18[r5]
            r5 = r19[r5]
            float r5 = java.lang.Math.min(r12, r5)
            float r5 = r5 / r7
            float r7 = r9 - r11
            float r12 = r8 * r7
            float r13 = r11 - r3
            float r14 = r10 * r13
            float r12 = r12 + r14
            float r14 = r3 - r9
            float r5 = r5 * r14
            float r12 = r12 + r5
            float r5 = r3 * r3
            float r7 = r7 * r5
            float r15 = r9 * r9
            float r15 = r15 * r13
            float r7 = r7 + r15
            float r11 = r11 * r11
            float r11 = r11 * r14
            float r7 = r7 + r11
            float r12 = r12 / r7
            float r7 = r8 - r10
            float r7 = r7 / r14
            float r9 = r9 + r3
            float r9 = r9 * r12
            float r7 = r7 - r9
            float r5 = r5 * r12
            float r8 = r8 - r5
            float r3 = r3 * r7
            float r8 = r8 - r3
            int r3 = r0.frames
            float r3 = (float) r3
            float r4 = r4 / r3
            r2 = r19[r6]
            r3 = r18[r6]
            float r2 = r2 - r3
            r3 = 0
        L_0x0098:
            int r5 = r0.frames
            if (r3 > r5) goto L_0x00b9
            float r5 = (float) r3
            float r5 = r5 * r4
            r9 = r18[r6]
            float r5 = r5 * r2
            float r9 = r9 + r5
            r20[r3] = r9
            r5 = r20[r3]
            float r5 = r5 * r12
            r9 = r20[r3]
            float r5 = r5 * r9
            r9 = r20[r3]
            float r9 = r9 * r7
            float r5 = r5 + r9
            float r5 = r5 + r8
            r21[r3] = r5
            int r3 = r3 + 1
            goto L_0x0098
        L_0x00b9:
            return
        L_0x00ba:
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r8 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.HORIZONTAL_THROW
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x011d
            r3 = r18[r6]
            r1 = r18[r5]
            r8 = r19[r6]
            r2 = r19[r5]
            float r7 = r7 * r8
            float r7 = r7 - r3
            float r5 = r7 - r8
            float r9 = r1 * r5
            float r10 = r8 - r3
            float r11 = r1 * r10
            float r9 = r9 + r11
            float r11 = r3 - r7
            float r2 = r2 * r11
            float r9 = r9 + r2
            float r2 = r3 * r3
            float r5 = r5 * r2
            float r12 = r7 * r7
            float r12 = r12 * r10
            float r5 = r5 + r12
            float r8 = r8 * r8
            float r8 = r8 * r11
            float r5 = r5 + r8
            float r9 = r9 / r5
            float r5 = r1 - r1
            float r5 = r5 / r11
            float r7 = r7 + r3
            float r7 = r7 * r9
            float r5 = r5 - r7
            float r2 = r2 * r9
            float r1 = r1 - r2
            float r2 = r3 * r5
            float r1 = r1 - r2
            int r2 = r0.frames
            float r2 = (float) r2
            float r4 = r4 / r2
        L_0x00fd:
            int r2 = r0.frames
            if (r6 > r2) goto L_0x011c
            float r2 = (float) r6
            float r2 = r2 * r4
            float r2 = r2 * r10
            float r2 = r2 + r3
            r20[r6] = r2
            r2 = r20[r6]
            float r2 = r2 * r9
            r7 = r20[r6]
            float r2 = r2 * r7
            r7 = r20[r6]
            float r7 = r7 * r5
            float r2 = r2 + r7
            float r2 = r2 + r1
            r21[r6] = r2
            int r6 = r6 + 1
            goto L_0x00fd
        L_0x011c:
            return
        L_0x011d:
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r8 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.PARABOLA_2
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x01a3
            r3 = r18[r6]
            r8 = r18[r5]
            r9 = r19[r6]
            r10 = r19[r5]
            r11 = r18[r6]
            r12 = r19[r6]
            float r11 = r11 + r12
            float r11 = r11 / r7
            defpackage.cnu.a()
            android.content.Context r12 = r0.mContext
            int r12 = defpackage.cnu.b(r12)
            float r12 = (float) r12
            r13 = r18[r5]
            r6 = r19[r5]
            float r6 = java.lang.Math.max(r13, r6)
            float r12 = r12 - r6
            float r12 = r12 / r7
            r1 = r18[r5]
            r2 = r19[r5]
            float r1 = java.lang.Math.max(r1, r2)
            float r12 = r12 + r1
            float r1 = r9 - r11
            float r2 = r8 * r1
            float r5 = r11 - r3
            float r6 = r10 * r5
            float r2 = r2 + r6
            float r6 = r3 - r9
            float r12 = r12 * r6
            float r2 = r2 + r12
            float r7 = r3 * r3
            float r1 = r1 * r7
            float r12 = r9 * r9
            float r12 = r12 * r5
            float r1 = r1 + r12
            float r11 = r11 * r11
            float r11 = r11 * r6
            float r1 = r1 + r11
            float r2 = r2 / r1
            float r1 = r8 - r10
            float r1 = r1 / r6
            float r5 = r3 + r9
            float r5 = r5 * r2
            float r1 = r1 - r5
            float r7 = r7 * r2
            float r8 = r8 - r7
            float r5 = r3 * r1
            float r8 = r8 - r5
            int r5 = r0.frames
            float r5 = (float) r5
            float r4 = r4 / r5
            float r9 = r9 - r3
            r5 = 0
        L_0x0183:
            int r6 = r0.frames
            if (r5 > r6) goto L_0x01a2
            float r6 = (float) r5
            float r6 = r6 * r4
            float r6 = r6 * r9
            float r6 = r6 + r3
            r20[r5] = r6
            r6 = r20[r5]
            float r6 = r6 * r2
            r7 = r20[r5]
            float r6 = r6 * r7
            r7 = r20[r5]
            float r7 = r7 * r1
            float r6 = r6 + r7
            float r6 = r6 + r8
            r21[r5] = r6
            int r5 = r5 + 1
            goto L_0x0183
        L_0x01a2:
            return
        L_0x01a3:
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r6 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.HORIZONTAL_THROW_2
            boolean r3 = r3.equals(r6)
            if (r3 == 0) goto L_0x020f
            r3 = 0
            r6 = r19[r3]
            r8 = r19[r5]
            r9 = r18[r3]
            r3 = r18[r5]
            float r7 = r7 * r9
            float r7 = r7 - r6
            float r5 = r7 - r9
            float r10 = r8 * r5
            float r11 = r9 - r6
            float r12 = r8 * r11
            float r10 = r10 + r12
            float r12 = r6 - r7
            float r3 = r3 * r12
            float r10 = r10 + r3
            float r3 = r6 * r6
            float r5 = r5 * r3
            float r13 = r7 * r7
            float r13 = r13 * r11
            float r5 = r5 + r13
            float r9 = r9 * r9
            float r9 = r9 * r12
            float r5 = r5 + r9
            float r10 = r10 / r5
            float r5 = r8 - r8
            float r5 = r5 / r12
            float r7 = r7 + r6
            float r7 = r7 * r10
            float r5 = r5 - r7
            float r3 = r3 * r10
            float r8 = r8 - r3
            float r6 = r6 * r5
            float r8 = r8 - r6
            int r3 = r0.frames
            float r3 = (float) r3
            float r4 = r4 / r3
            r3 = 0
            r2 = r19[r3]
            r6 = r18[r3]
            float r2 = r2 - r6
            r6 = 0
        L_0x01ee:
            int r7 = r0.frames
            if (r6 > r7) goto L_0x020f
            float r7 = (float) r6
            float r7 = r7 * r4
            r9 = r18[r3]
            float r7 = r7 * r2
            float r9 = r9 + r7
            r20[r6] = r9
            r7 = r20[r6]
            float r7 = r7 * r10
            r9 = r20[r6]
            float r7 = r7 * r9
            r9 = r20[r6]
            float r9 = r9 * r5
            float r7 = r7 + r9
            float r7 = r7 + r8
            r21[r6] = r7
            int r6 = r6 + 1
            goto L_0x01ee
        L_0x020f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.auidebugger.boommenu.BoomMenuButton.getShowXY(float[], float[], float[], float[]):void");
    }

    /* JADX WARNING: type inference failed for: r21v0, types: [float[]] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getHideXY(float[] r18, float[] r19, float[] r20, float[] r21) {
        /*
            r17 = this;
            r0 = r17
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r4 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.LINE
            boolean r3 = r3.equals(r4)
            r4 = 1065353216(0x3f800000, float:1.0)
            r5 = 1
            r6 = 0
            if (r3 == 0) goto L_0x0041
            r3 = r18[r6]
            r7 = r18[r5]
            r8 = r19[r6]
            r5 = r19[r5]
            float r5 = r5 - r7
            float r8 = r8 - r3
            float r5 = r5 / r8
            float r3 = r3 * r5
            float r7 = r7 - r3
            int r3 = r0.frames
            float r3 = (float) r3
            float r4 = r4 / r3
            r2 = r19[r6]
            r3 = r18[r6]
            float r2 = r2 - r3
            r3 = 0
        L_0x0028:
            int r8 = r0.frames
            if (r3 > r8) goto L_0x0040
            float r8 = (float) r3
            float r8 = r8 * r4
            r9 = r18[r6]
            float r8 = r8 * r2
            float r9 = r9 + r8
            r20[r3] = r9
            r8 = r20[r3]
            float r8 = r8 * r5
            float r8 = r8 + r7
            r21[r3] = r8
            int r3 = r3 + 1
            goto L_0x0028
        L_0x0040:
            return
        L_0x0041:
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r7 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.PARABOLA
            boolean r3 = r3.equals(r7)
            r7 = 1073741824(0x40000000, float:2.0)
            if (r3 == 0) goto L_0x00ba
            r3 = r18[r6]
            r8 = r18[r5]
            r9 = r19[r6]
            r10 = r19[r5]
            r11 = r18[r6]
            r12 = r19[r6]
            float r11 = r11 + r12
            float r11 = r11 / r7
            r12 = r18[r5]
            r5 = r19[r5]
            float r5 = java.lang.Math.min(r12, r5)
            float r5 = r5 / r7
            float r7 = r9 - r11
            float r12 = r8 * r7
            float r13 = r11 - r3
            float r14 = r10 * r13
            float r12 = r12 + r14
            float r14 = r3 - r9
            float r5 = r5 * r14
            float r12 = r12 + r5
            float r5 = r3 * r3
            float r7 = r7 * r5
            float r15 = r9 * r9
            float r15 = r15 * r13
            float r7 = r7 + r15
            float r11 = r11 * r11
            float r11 = r11 * r14
            float r7 = r7 + r11
            float r12 = r12 / r7
            float r7 = r8 - r10
            float r7 = r7 / r14
            float r9 = r9 + r3
            float r9 = r9 * r12
            float r7 = r7 - r9
            float r5 = r5 * r12
            float r8 = r8 - r5
            float r3 = r3 * r7
            float r8 = r8 - r3
            int r3 = r0.frames
            float r3 = (float) r3
            float r4 = r4 / r3
            r2 = r19[r6]
            r3 = r18[r6]
            float r2 = r2 - r3
            r3 = 0
        L_0x0098:
            int r5 = r0.frames
            if (r3 > r5) goto L_0x00b9
            float r5 = (float) r3
            float r5 = r5 * r4
            r9 = r18[r6]
            float r5 = r5 * r2
            float r9 = r9 + r5
            r20[r3] = r9
            r5 = r20[r3]
            float r5 = r5 * r12
            r9 = r20[r3]
            float r5 = r5 * r9
            r9 = r20[r3]
            float r9 = r9 * r7
            float r5 = r5 + r9
            float r5 = r5 + r8
            r21[r3] = r5
            int r3 = r3 + 1
            goto L_0x0098
        L_0x00b9:
            return
        L_0x00ba:
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r8 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.HORIZONTAL_THROW
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x0125
            r3 = r19[r6]
            r8 = r19[r5]
            r9 = r18[r6]
            r5 = r18[r5]
            float r7 = r7 * r9
            float r7 = r7 - r3
            float r10 = r7 - r9
            float r11 = r8 * r10
            float r12 = r9 - r3
            float r13 = r8 * r12
            float r11 = r11 + r13
            float r13 = r3 - r7
            float r5 = r5 * r13
            float r11 = r11 + r5
            float r5 = r3 * r3
            float r10 = r10 * r5
            float r14 = r7 * r7
            float r14 = r14 * r12
            float r10 = r10 + r14
            float r9 = r9 * r9
            float r9 = r9 * r13
            float r10 = r10 + r9
            float r11 = r11 / r10
            float r9 = r8 - r8
            float r9 = r9 / r13
            float r7 = r7 + r3
            float r7 = r7 * r11
            float r9 = r9 - r7
            float r5 = r5 * r11
            float r8 = r8 - r5
            float r3 = r3 * r9
            float r8 = r8 - r3
            int r3 = r0.frames
            float r3 = (float) r3
            float r4 = r4 / r3
            r2 = r19[r6]
            r3 = r18[r6]
            float r2 = r2 - r3
            r3 = 0
        L_0x0103:
            int r5 = r0.frames
            if (r3 > r5) goto L_0x0124
            float r5 = (float) r3
            float r5 = r5 * r4
            r7 = r18[r6]
            float r5 = r5 * r2
            float r7 = r7 + r5
            r20[r3] = r7
            r5 = r20[r3]
            float r5 = r5 * r11
            r7 = r20[r3]
            float r5 = r5 * r7
            r7 = r20[r3]
            float r7 = r7 * r9
            float r5 = r5 + r7
            float r5 = r5 + r8
            r21[r3] = r5
            int r3 = r3 + 1
            goto L_0x0103
        L_0x0124:
            return
        L_0x0125:
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r8 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.PARABOLA_2
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L_0x01ab
            r3 = r18[r6]
            r8 = r18[r5]
            r9 = r19[r6]
            r10 = r19[r5]
            r11 = r18[r6]
            r12 = r19[r6]
            float r11 = r11 + r12
            float r11 = r11 / r7
            defpackage.cnu.a()
            android.content.Context r12 = r0.mContext
            int r12 = defpackage.cnu.b(r12)
            float r12 = (float) r12
            r13 = r18[r5]
            r6 = r19[r5]
            float r6 = java.lang.Math.max(r13, r6)
            float r12 = r12 - r6
            float r12 = r12 / r7
            r1 = r18[r5]
            r2 = r19[r5]
            float r1 = java.lang.Math.max(r1, r2)
            float r12 = r12 + r1
            float r1 = r9 - r11
            float r2 = r8 * r1
            float r5 = r11 - r3
            float r6 = r10 * r5
            float r2 = r2 + r6
            float r6 = r3 - r9
            float r12 = r12 * r6
            float r2 = r2 + r12
            float r7 = r3 * r3
            float r1 = r1 * r7
            float r12 = r9 * r9
            float r12 = r12 * r5
            float r1 = r1 + r12
            float r11 = r11 * r11
            float r11 = r11 * r6
            float r1 = r1 + r11
            float r2 = r2 / r1
            float r1 = r8 - r10
            float r1 = r1 / r6
            float r5 = r3 + r9
            float r5 = r5 * r2
            float r1 = r1 - r5
            float r7 = r7 * r2
            float r8 = r8 - r7
            float r5 = r3 * r1
            float r8 = r8 - r5
            int r5 = r0.frames
            float r5 = (float) r5
            float r4 = r4 / r5
            float r9 = r9 - r3
            r5 = 0
        L_0x018b:
            int r6 = r0.frames
            if (r5 > r6) goto L_0x01aa
            float r6 = (float) r5
            float r6 = r6 * r4
            float r6 = r6 * r9
            float r6 = r6 + r3
            r20[r5] = r6
            r6 = r20[r5]
            float r6 = r6 * r2
            r7 = r20[r5]
            float r6 = r6 * r7
            r7 = r20[r5]
            float r7 = r7 * r1
            float r6 = r6 + r7
            float r6 = r6 + r8
            r21[r5] = r6
            int r5 = r5 + 1
            goto L_0x018b
        L_0x01aa:
            return
        L_0x01ab:
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r3 = r0.boomType
            com.autonavi.minimap.auidebugger.boommenu.Types.BoomType r6 = com.autonavi.minimap.auidebugger.boommenu.Types.BoomType.HORIZONTAL_THROW_2
            boolean r3 = r3.equals(r6)
            if (r3 == 0) goto L_0x0217
            r3 = 0
            r6 = r18[r3]
            r8 = r18[r5]
            r9 = r19[r3]
            r3 = r19[r5]
            float r7 = r7 * r9
            float r7 = r7 - r6
            float r5 = r7 - r9
            float r10 = r8 * r5
            float r11 = r9 - r6
            float r12 = r8 * r11
            float r10 = r10 + r12
            float r12 = r6 - r7
            float r3 = r3 * r12
            float r10 = r10 + r3
            float r3 = r6 * r6
            float r5 = r5 * r3
            float r13 = r7 * r7
            float r13 = r13 * r11
            float r5 = r5 + r13
            float r9 = r9 * r9
            float r9 = r9 * r12
            float r5 = r5 + r9
            float r10 = r10 / r5
            float r5 = r8 - r8
            float r5 = r5 / r12
            float r7 = r7 + r6
            float r7 = r7 * r10
            float r5 = r5 - r7
            float r3 = r3 * r10
            float r8 = r8 - r3
            float r6 = r6 * r5
            float r8 = r8 - r6
            int r3 = r0.frames
            float r3 = (float) r3
            float r4 = r4 / r3
            r3 = 0
            r2 = r19[r3]
            r6 = r18[r3]
            float r2 = r2 - r6
            r6 = 0
        L_0x01f6:
            int r7 = r0.frames
            if (r6 > r7) goto L_0x0217
            float r7 = (float) r6
            float r7 = r7 * r4
            r9 = r18[r3]
            float r7 = r7 * r2
            float r9 = r9 + r7
            r20[r6] = r9
            r7 = r20[r6]
            float r7 = r7 * r10
            r9 = r20[r6]
            float r7 = r7 * r9
            r9 = r20[r6]
            float r9 = r9 * r5
            float r7 = r7 + r9
            float r7 = r7 + r8
            r21[r6] = r7
            int r6 = r6 + 1
            goto L_0x01f6
        L_0x0217:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.auidebugger.boommenu.BoomMenuButton.getHideXY(float[], float[], float[], float[]):void");
    }

    /* access modifiers changed from: private */
    public void startHideAnimations() {
        this.animationPlaying = true;
        lightAnimationLayout();
        int i = 0;
        if (this.buttonType.equals(ButtonType.CIRCLE)) {
            if (this.hideOrderType.equals(OrderType.DEFAULT)) {
                while (i < this.buttonNum) {
                    setHideAnimation(this.dots[i], this.circleButtons[i], this.endLocations[i], this.startLocations[i], i);
                    i++;
                }
            } else if (this.hideOrderType.equals(OrderType.REVERSE)) {
                while (i < this.buttonNum) {
                    setHideAnimation(this.dots[i], this.circleButtons[i], this.endLocations[i], this.startLocations[i], (this.buttonNum - i) - 1);
                    i++;
                }
            } else if (this.hideOrderType.equals(OrderType.RANDOM)) {
                Random random = new Random();
                boolean[] zArr = new boolean[this.buttonNum];
                for (int i2 = 0; i2 < this.buttonNum; i2++) {
                    zArr[i2] = false;
                }
                while (true) {
                    int nextInt = random.nextInt(this.buttonNum);
                    if (!zArr[nextInt]) {
                        zArr[nextInt] = true;
                        setHideAnimation(this.dots[i], this.circleButtons[i], this.endLocations[i], this.startLocations[i], nextInt);
                        i++;
                        if (i == this.buttonNum) {
                            return;
                        }
                    }
                }
            }
        } else if (this.buttonType.equals(ButtonType.HAM)) {
            if (this.hideOrderType.equals(OrderType.DEFAULT)) {
                while (i < this.buttonNum) {
                    setHideAnimation(this.bars[i], this.hamButtons[i], this.endLocations[i], this.startLocations[i], i);
                    i++;
                }
            } else if (this.hideOrderType.equals(OrderType.REVERSE)) {
                while (i < this.buttonNum) {
                    setHideAnimation(this.bars[i], this.hamButtons[i], this.endLocations[i], this.startLocations[i], (this.buttonNum - i) - 1);
                    i++;
                }
            } else if (this.hideOrderType.equals(OrderType.RANDOM)) {
                Random random2 = new Random();
                boolean[] zArr2 = new boolean[this.buttonNum];
                for (int i3 = 0; i3 < this.buttonNum; i3++) {
                    zArr2[i3] = false;
                }
                while (true) {
                    int nextInt2 = random2.nextInt(this.buttonNum);
                    if (!zArr2[nextInt2]) {
                        zArr2[nextInt2] = true;
                        setHideAnimation(this.bars[i], this.hamButtons[i], this.endLocations[i], this.startLocations[i], nextInt2);
                        i++;
                        if (i == this.buttonNum) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public void setHideAnimation(final View view, View view2, int[] iArr, int[] iArr2, final int i) {
        float f;
        float f2;
        Object obj;
        float[] fArr = {((float) iArr[0]) * 1.0f, ((float) iArr[1]) * 1.0f};
        float[] fArr2 = {((float) iArr2[0]) * 1.0f, ((float) iArr2[1]) * 1.0f};
        float[] fArr3 = new float[(this.frames + 1)];
        float[] fArr4 = new float[(this.frames + 1)];
        getHideXY(fArr, fArr2, fArr3, fArr4);
        if (view2 != null) {
            ObjectAnimator duration2 = ObjectAnimator.ofFloat(view2, DictionaryKeys.CTRLXY_X, fArr3).setDuration((long) this.duration);
            duration2.setStartDelay((long) (this.delay * i));
            duration2.setInterpolator(cns.a(this.hideMoveEaseType));
            duration2.start();
            ObjectAnimator duration3 = ObjectAnimator.ofFloat(view2, DictionaryKeys.CTRLXY_Y, fArr4).setDuration((long) this.duration);
            duration3.setStartDelay((long) (this.delay * i));
            duration3.setInterpolator(cns.a(this.hideMoveEaseType));
            duration3.start();
        }
        if (this.buttonType.equals(ButtonType.CIRCLE)) {
            f = (((float) this.dotWidth) * 1.0f) / ((float) this.buttonWidth);
            f2 = (((float) this.dotWidth) * 1.0f) / ((float) this.buttonWidth);
        } else if (this.buttonType.equals(ButtonType.HAM)) {
            f = (((float) this.barWidth) * 1.0f) / ((float) this.hamButtonWidth);
            f2 = (((float) this.barHeight) * 1.0f) / ((float) this.hamButtonHeight);
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        if (view2 != null) {
            ObjectAnimator duration4 = ObjectAnimator.ofFloat(view2, "scaleX", new float[]{1.0f, f}).setDuration((long) this.duration);
            duration4.setStartDelay((long) (this.delay * i));
            duration4.setInterpolator(cns.a(this.hideScaleEaseType));
            duration4.start();
            ObjectAnimator duration5 = ObjectAnimator.ofFloat(view2, "scaleY", new float[]{1.0f, f2}).setDuration((long) this.duration);
            duration5.setStartDelay((long) (this.delay * i));
            duration5.setInterpolator(cns.a(this.hideScaleEaseType));
            duration5.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    view.setVisibility(0);
                    BoomMenuButton.this.isInList;
                    if (BoomMenuButton.this.buttonType.equals(ButtonType.CIRCLE)) {
                        BoomMenuButton.this.circleButtons[i] = null;
                        return;
                    }
                    if (BoomMenuButton.this.buttonType.equals(ButtonType.HAM)) {
                        BoomMenuButton.this.hamButtons[i] = null;
                    }
                }
            });
            duration5.start();
        }
        TextView textView = null;
        if (view2 != null && (view2 instanceof CircleButton)) {
            CircleButton circleButton = (CircleButton) view2;
            obj = circleButton.getImageView();
            textView = circleButton.getTextView();
        } else if (view2 == null || !(view2 instanceof HamButton)) {
            obj = null;
        } else {
            HamButton hamButton = (HamButton) view2;
            obj = hamButton.getImageView();
            textView = hamButton.getTextView();
        }
        if (obj != null) {
            ObjectAnimator duration6 = ObjectAnimator.ofFloat(obj, "alpha", new float[]{1.0f, 0.0f}).setDuration((long) this.duration);
            duration6.setStartDelay((long) (this.delay * i));
            duration6.setInterpolator(cns.a(this.hideMoveEaseType));
            duration6.start();
        }
        if (textView != null) {
            ObjectAnimator duration7 = ObjectAnimator.ofFloat(textView, "alpha", new float[]{1.0f, 0.0f}).setDuration((long) this.duration);
            duration7.setStartDelay((long) (this.delay * i));
            duration7.setInterpolator(cns.a(this.hideMoveEaseType));
            duration7.start();
        }
        if (view2 != null && (view2 instanceof CircleButton)) {
            ObjectAnimator duration8 = ObjectAnimator.ofFloat(((CircleButton) view2).getFrameLayout(), APCacheInfo.EXTRA_ROTATION, new float[]{0.0f, (float) (-this.rotateDegree)}).setDuration((long) this.duration);
            duration8.setStartDelay((long) (i * this.delay));
            duration8.setInterpolator(cns.a(this.hideRotateEaseType));
            duration8.start();
        }
    }

    public void lightAnimationLayout() {
        ObjectAnimator duration2 = ObjectAnimator.ofInt(this.animationLayout, "backgroundColor", new int[]{this.dimType.value, DimType.DIM_0.value}).setDuration((long) (this.duration + (this.delay * (this.buttonNum - 1))));
        duration2.setEvaluator(new ArgbEvaluator());
        duration2.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                if (BoomMenuButton.this.animatorListener != null) {
                    BoomMenuButton.this.animatorListener;
                }
                BoomMenuButton.this.state = StateType.CLOSING;
            }

            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                BoomMenuButton.this.animationLayout.removeAllViews();
                BoomMenuButton.this.animationLayout.setVisibility(8);
                BoomMenuButton.this.animationPlaying = false;
                if (BoomMenuButton.this.animatorListener != null) {
                    BoomMenuButton.this.animatorListener;
                }
                BoomMenuButton.this.state = StateType.CLOSED;
                if (BoomMenuButton.this.onStateChangeListener != null) {
                    BoomMenuButton.this.onStateChangeListener.stateChange(false);
                }
            }
        });
        duration2.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (BoomMenuButton.this.animatorListener != null) {
                    BoomMenuButton.this.animatorListener;
                    valueAnimator.getAnimatedFraction();
                }
            }
        });
        duration2.start();
        if (PlaceType.SHARE_3_1.v <= this.placeType.v && this.placeType.v <= PlaceType.SHARE_9_2.v) {
            ObjectAnimator duration3 = ObjectAnimator.ofFloat(this.shareLines, "offset", new float[]{0.0f, 1.0f}).setDuration((long) (this.duration + (this.delay * (this.buttonNum - 1))));
            duration3.setStartDelay(0);
            duration3.start();
        }
    }

    public void setAutoDismiss(boolean z) {
        this.autoDismiss = z;
    }

    public void setCancelable(boolean z) {
        this.cancelable = z;
    }

    public void setFrames(int i) {
        this.frames = i;
    }

    public void setDuration(int i) {
        this.duration = i;
    }

    public void setDelay(int i) {
        this.delay = i;
    }

    public void setRotateDegree(int i) {
        this.rotateDegree = i;
    }

    public void setShowOrderType(OrderType orderType) {
        this.showOrderType = orderType;
    }

    public void setHideOrderType(OrderType orderType) {
        this.hideOrderType = orderType;
    }

    public void setOnClickListener(c cVar) {
        this.onClickListener = cVar;
    }

    public void setOnStateChangeListener(d dVar) {
        this.onStateChangeListener = dVar;
    }

    public void setAnimatorListener(a aVar) {
        this.animatorListener = aVar;
    }

    public ImageButton[] getImageButtons() {
        return new ImageButton[this.buttonNum];
    }

    public ImageView[] getImageViews() {
        return new ImageView[this.buttonNum];
    }

    public TextView[] getTextViews() {
        return new TextView[this.buttonNum];
    }

    public void setOnSubButtonClickListener(e eVar) {
        this.onSubButtonClickListener = eVar;
    }

    public void setBoomButtonBackgroundColor(int i, int i2) {
        cnu.a();
        cnu.a(this.frameLayout, this.boomButtonRadius, i, i2);
    }

    public void setSubButtonShadowOffset(float f, float f2) {
        for (int i = 0; i < this.buttonNum; i++) {
            if (this.buttonType.equals(ButtonType.CIRCLE)) {
                if (this.circleButtons[i] != null) {
                    this.circleButtons[i].setShadowDx(f);
                    this.circleButtons[i].setShadowDy(f2);
                } else {
                    this.subButtonsXOffsetOfShadow = f;
                    this.subButtonsYOffsetOfShadow = f;
                }
            } else if (this.buttonType.equals(ButtonType.HAM)) {
                if (this.hamButtons[i] != null) {
                    this.hamButtons[i].setShadowDx(f);
                    this.hamButtons[i].setShadowDy(f2);
                } else {
                    this.subButtonsXOffsetOfShadow = f;
                    this.subButtonsYOffsetOfShadow = f;
                }
            }
        }
    }

    public void setBoomButtonShadowOffset(float f, float f2) {
        if (this.shadowLayout != null) {
            this.shadowLayout.setmDx(f);
            this.shadowLayout.setmDy(f2);
        }
    }

    public void setDimType(DimType dimType2) {
        this.dimType = dimType2;
    }

    public void setClickEffectType(ClickEffectType clickEffectType2) {
        setRipple(clickEffectType2);
        for (int i = 0; i < this.buttonNum; i++) {
            if (this.buttonType.equals(ButtonType.CIRCLE)) {
                if (this.circleButtons[i] != null) {
                    this.circleButtons[i].setRipple(clickEffectType2);
                } else {
                    this.clickEffectType = clickEffectType2;
                }
            } else if (this.buttonType.equals(ButtonType.HAM)) {
                if (this.hamButtons[i] != null) {
                    this.hamButtons[i].setRipple(clickEffectType2);
                } else {
                    this.clickEffectType = clickEffectType2;
                }
            }
        }
    }

    private void setRipple(ClickEffectType clickEffectType2) {
        this.clickEffectType = clickEffectType2;
        if (VERSION.SDK_INT < 21 || !clickEffectType2.equals(ClickEffectType.RIPPLE) || this.ripple == null) {
            if (this.ripple != null) {
                this.ripple.setVisibility(8);
            }
            this.frameLayout.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    BoomMenuButton.this.shoot();
                }
            });
            return;
        }
        this.ripple.setVisibility(0);
        this.ripple.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                BoomMenuButton.this.shoot();
            }
        });
    }

    public void setTextViewColor(int i) {
        for (int i2 = 0; i2 < this.buttonNum; i2++) {
            if (this.buttonType.equals(ButtonType.CIRCLE)) {
                if (this.circleButtons[i2] != null) {
                    this.circleButtons[i2].getTextView().setTextColor(i);
                } else {
                    this.subButtonTextColor = i;
                }
            } else if (this.buttonType.equals(ButtonType.HAM)) {
                if (this.hamButtons[i2] != null) {
                    this.hamButtons[i2].getTextView().setTextColor(i);
                } else {
                    this.subButtonTextColor = i;
                }
            }
        }
    }

    public void setTextViewColor(int[] iArr) {
        int min = Math.min(this.buttonNum, iArr.length);
        int i = 0;
        if (this.buttonType.equals(ButtonType.CIRCLE)) {
            while (i < min) {
                if (this.circleButtons[i] != null) {
                    this.circleButtons[i].getTextView().setTextColor(iArr[i]);
                }
                i++;
            }
            return;
        }
        if (this.buttonType.equals(ButtonType.HAM)) {
            while (i < min) {
                if (this.hamButtons[i] != null) {
                    this.hamButtons[i].getTextView().setTextColor(iArr[i]);
                }
                i++;
            }
        }
    }

    public void setImageViewScaleType(ScaleType scaleType) {
        for (int i = 0; i < this.buttonNum; i++) {
            if (this.buttonType.equals(ButtonType.CIRCLE)) {
                if (this.circleButtons[i] != null) {
                    this.circleButtons[i].getImageView().setScaleType(scaleType);
                } else {
                    this.subButtonImageScaleType = scaleType;
                }
            } else if (this.buttonType.equals(ButtonType.HAM)) {
                if (this.hamButtons[i] != null) {
                    this.hamButtons[i].getImageView().setScaleType(scaleType);
                } else {
                    this.subButtonImageScaleType = scaleType;
                }
            }
        }
    }

    public void setBoomType(BoomType boomType2) {
        this.boomType = boomType2;
    }

    public boolean isClosed() {
        return this.state.equals(StateType.CLOSED);
    }

    public boolean isClosing() {
        return this.state.equals(StateType.CLOSING);
    }

    public boolean isOpen() {
        return this.state.equals(StateType.OPEN);
    }

    public boolean isOpening() {
        return this.state.equals(StateType.OPENING);
    }

    public void setShowMoveEaseType(EaseType easeType) {
        this.showMoveEaseType = easeType;
    }

    public void setShowScaleEaseType(EaseType easeType) {
        this.showScaleEaseType = easeType;
    }

    public void setShowRotateEaseType(EaseType easeType) {
        this.showRotateEaseType = easeType;
    }

    public void setHideMoveEaseType(EaseType easeType) {
        this.hideMoveEaseType = easeType;
    }

    public void setHideScaleEaseType(EaseType easeType) {
        this.hideScaleEaseType = easeType;
    }

    public void setHideRotateEaseType(EaseType easeType) {
        this.hideRotateEaseType = easeType;
    }

    public void onClick(int i) {
        if (this.state.equals(StateType.OPEN)) {
            if (this.onSubButtonClickListener != null) {
                this.onSubButtonClickListener.onClick(i);
            }
            if (this.autoDismiss && !this.animationPlaying) {
                startHideAnimations();
            }
        }
    }

    public boolean dismiss() {
        if (this.state != StateType.OPEN) {
            return false;
        }
        startHideAnimations();
        return true;
    }

    public boolean boom() {
        if (this.state != StateType.CLOSED) {
            return false;
        }
        shoot();
        return true;
    }

    public void setShareLineWidth(float f) {
        if (this.shareLines != null) {
            this.shareLines.setLineWidth(f);
        }
    }

    public void setShareLine1Color(int i) {
        if (this.shareLines != null) {
            this.shareLines.setLine1Color(i);
        }
    }

    public void setShareLine2Color(int i) {
        if (this.shareLines != null) {
            this.shareLines.setLine2Color(i);
        }
    }

    private static Activity scanForActivity(Context context) {
        try {
            return AMapAppGlobal.getTopActivity();
        } catch (Throwable unused) {
            return sActivity;
        }
    }

    public static void setTopAct(Activity activity) {
        sActivity = activity;
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        super.setOnTouchListener(onTouchListener);
        this.mOnTouchListener = onTouchListener;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mOnTouchListener == null || !this.mOnTouchListener.onTouch(this, motionEvent)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }
}
