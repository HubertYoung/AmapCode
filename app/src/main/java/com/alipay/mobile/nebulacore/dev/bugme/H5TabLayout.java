package com.alipay.mobile.nebulacore.dev.bugme;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;
import com.uc.webview.export.extension.UCCore;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class H5TabLayout extends HorizontalScrollView {
    /* access modifiers changed from: private */
    public static final Interpolator a = new FastOutSlowInInterpolator();
    private final ArrayList<Tab> b;
    private Tab c;
    private final SlidingTabStrip d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public final int f;
    /* access modifiers changed from: private */
    public int g;
    /* access modifiers changed from: private */
    public final int h;
    /* access modifiers changed from: private */
    public final int i;
    /* access modifiers changed from: private */
    public int j;
    private OnTabSelectedListener k;
    private OnClickListener l;

    public interface OnTabSelectedListener {
        void onTabReselected(Tab tab);

        void onTabSelected(Tab tab);

        void onTabUnselected(Tab tab);
    }

    private class SlidingTabStrip extends LinearLayout {
        private int b;
        private final Paint c;
        /* access modifiers changed from: private */
        public int d = -1;
        /* access modifiers changed from: private */
        public float e;
        private int f = -1;
        private int g = -1;
        private boolean h = true;
        private boolean i = false;

        SlidingTabStrip(Context context) {
            super(context);
            setWillNotDraw(false);
            this.c = new Paint();
        }

        /* access modifiers changed from: 0000 */
        public final void a(int color) {
            this.c.setColor(color);
            ViewCompat.postInvalidateOnAnimation(this);
        }

        /* access modifiers changed from: 0000 */
        public final void b(int height) {
            this.b = height;
            ViewCompat.postInvalidateOnAnimation(this);
        }

        /* access modifiers changed from: 0000 */
        public final void a(int position, float positionOffset) {
            if ((this.h || !this.i) && !H5TabLayout.b(getAnimation())) {
                this.d = position;
                this.e = positionOffset;
                a();
                this.i = true;
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(boolean isScrollable) {
            this.h = isScrollable;
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (MeasureSpec.getMode(widthMeasureSpec) != 1073741824) {
            }
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean changed, int l, int t, int r, int b2) {
            super.onLayout(changed, l, t, r, b2);
            if (!H5TabLayout.b(getAnimation())) {
                a();
            }
        }

        private void a() {
            int right;
            int left;
            View selectedTitle = getChildAt(this.d);
            if (selectedTitle == null || selectedTitle.getWidth() <= 0) {
                right = -1;
                left = -1;
            } else {
                left = selectedTitle.getLeft();
                right = selectedTitle.getRight();
                if (this.e > 0.0f && this.d < getChildCount() - 1) {
                    View nextTitle = getChildAt(this.d + 1);
                    left = (int) ((this.e * ((float) nextTitle.getLeft())) + ((1.0f - this.e) * ((float) left)));
                    right = (int) ((this.e * ((float) nextTitle.getRight())) + ((1.0f - this.e) * ((float) right)));
                }
            }
            a(left, right);
        }

        /* access modifiers changed from: private */
        public void a(int left, int right) {
            if (left != this.f || right != this.g) {
                this.f = left;
                this.g = right;
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }

        /* access modifiers changed from: 0000 */
        public final void c(final int position) {
            final int startRight;
            final int startLeft;
            boolean isRtl = ViewCompat.getLayoutDirection(this) == 1;
            View targetView = getChildAt(position);
            final int targetLeft = targetView.getLeft();
            final int targetRight = targetView.getRight();
            if (Math.abs(position - this.d) <= 1) {
                startLeft = this.f;
                startRight = this.g;
            } else {
                int offset = H5TabLayout.this.b(24);
                if (position < this.d) {
                    if (!isRtl) {
                        startRight = targetRight + offset;
                        startLeft = startRight;
                    }
                } else if (isRtl) {
                    startRight = targetRight + offset;
                    startLeft = startRight;
                }
                startRight = targetLeft - offset;
                startLeft = startRight;
            }
            if (startLeft != targetLeft || startRight != targetRight) {
                Animation anim = new Animation() {
                    /* access modifiers changed from: protected */
                    public void applyTransformation(float interpolatedTime, Transformation t) {
                        SlidingTabStrip.this.a((int) H5TabLayout.a((float) startLeft, (float) targetLeft, interpolatedTime), (int) H5TabLayout.a((float) startRight, (float) targetRight, interpolatedTime));
                    }
                };
                anim.setInterpolator(H5TabLayout.a);
                anim.setDuration(300);
                anim.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        SlidingTabStrip.this.d = position;
                        SlidingTabStrip.this.e = 0.0f;
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                startAnimation(anim);
            }
        }

        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (this.f >= 0 && this.g > this.f) {
                canvas.drawRect((float) this.f, (float) (getHeight() - this.b), (float) this.g, (float) getHeight(), this.c);
            }
        }
    }

    public static final class Tab {
        public static final int INVALID_POSITION = -1;
        private CharSequence a;
        private int b = -1;
        /* access modifiers changed from: private */
        public final H5TabLayout c;

        Tab(H5TabLayout parent) {
            this.c = parent;
        }

        public final int getPosition() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public final void a(int position) {
            this.b = position;
        }

        public final CharSequence getText() {
            return this.a;
        }

        public final Tab setText(CharSequence text) {
            this.a = text;
            if (this.b >= 0) {
                this.c.a(this.b);
            }
            return this;
        }

        public final void select() {
            this.c.a(this);
        }
    }

    public static class TabLayoutOnPageChangeListener implements OnPageChangeListener {
        private final WeakReference<H5TabLayout> a;

        public TabLayoutOnPageChangeListener(H5TabLayout tabLayout) {
            this.a = new WeakReference<>(tabLayout);
        }

        public void onPageScrollStateChanged(int state) {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            H5TabLayout tabLayout = (H5TabLayout) this.a.get();
            if (tabLayout != null) {
                tabLayout.setScrollPosition(position, positionOffset);
            }
        }

        public void onPageSelected(int position) {
            H5TabLayout tabLayout = (H5TabLayout) this.a.get();
            if (tabLayout != null) {
                tabLayout.a(tabLayout.getTabAt(position));
            }
        }
    }

    class TabView extends LinearLayout {
        private final Tab b;
        private TextView c;

        public TabView(Context context, Tab tab) {
            super(context);
            this.b = tab;
            if (H5TabLayout.this.h != 0) {
                setBackgroundDrawable(context.getResources().getDrawable(H5TabLayout.this.h));
            }
            ViewCompat.setPaddingRelative(this, H5TabLayout.this.e, H5TabLayout.this.e, H5TabLayout.this.e, H5TabLayout.this.e);
            setGravity(17);
            a();
        }

        public void setSelected(boolean selected) {
            boolean changed = isSelected() != selected;
            super.setSelected(selected);
            if (changed && selected) {
                sendAccessibilityEvent(4);
                if (this.c != null) {
                    this.c.setSelected(selected);
                }
            }
        }

        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (getMeasuredWidth() > H5TabLayout.this.j) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(H5TabLayout.this.j, UCCore.VERIFY_POLICY_QUICK), heightMeasureSpec);
            } else if (H5TabLayout.this.i > 0 && getMeasuredHeight() < H5TabLayout.this.i) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(H5TabLayout.this.i, UCCore.VERIFY_POLICY_QUICK), heightMeasureSpec);
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            CharSequence text = this.b.getText();
            if (!TextUtils.isEmpty(text)) {
                if (this.c == null) {
                    TextView textView = new TextView(getContext());
                    textView.setTextAppearance(getContext(), H5TabLayout.this.f);
                    textView.setEllipsize(TruncateAt.END);
                    textView.setGravity(17);
                    textView.setTextColor(a(textView.getCurrentTextColor(), H5TabLayout.this.g));
                    addView(textView, -2, -2);
                    this.c = textView;
                }
                this.c.setText(text);
                this.c.setVisibility(0);
            } else if (this.c != null) {
                this.c.setVisibility(8);
                this.c.setText(null);
            }
        }

        private static ColorStateList a(int defaultColor, int selectedColor) {
            return new ColorStateList(new int[][]{SELECTED_STATE_SET, EMPTY_STATE_SET}, new int[]{selectedColor, defaultColor});
        }

        public Tab getTab() {
            return this.b;
        }
    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager a;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.a = viewPager;
        }

        public void onTabSelected(Tab tab) {
            this.a.setCurrentItem(tab.getPosition());
        }

        public void onTabUnselected(Tab tab) {
        }

        public void onTabReselected(Tab tab) {
        }
    }

    public H5TabLayout(Context context) {
        this(context, null);
    }

    public H5TabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public H5TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.b = new ArrayList<>();
        setHorizontalScrollBarEnabled(false);
        setFillViewport(true);
        this.d = new SlidingTabStrip(context);
        addView(this.d, -2, -1);
        TypedArray a2 = context.obtainStyledAttributes(attrs, R.styleable.H5TabLayout, defStyleAttr, R.style.Widget_Design_TabLayout);
        this.d.b(a2.getDimensionPixelSize(R.styleable.H5TabLayout_tabIndicatorHeight, 0));
        this.d.a(a2.getColor(R.styleable.H5TabLayout_tabIndicatorColor, 0));
        this.d.a(a2.getBoolean(R.styleable.H5TabLayout_tabIndicatorScrollable, true));
        this.f = a2.getResourceId(R.styleable.H5TabLayout_tabTextAppearance, R.style.TextAppearance_Design_Tab);
        this.e = a2.getDimensionPixelSize(R.styleable.H5TabLayout_tabPadding, 0);
        if (a2.hasValue(R.styleable.H5TabLayout_tabSelectedTextColor)) {
            this.g = a2.getColor(R.styleable.H5TabLayout_tabSelectedTextColor, 0);
        }
        this.i = a2.getDimensionPixelSize(R.styleable.H5TabLayout_tabMinWidth, 0);
        this.h = a2.getResourceId(R.styleable.H5TabLayout_tabBackground, 0);
        a2.recycle();
        c();
    }

    public void setScrollPosition(int position, float positionOffset) {
        if (!b(getAnimation()) && position >= 0 && position < this.d.getChildCount()) {
            this.d.a(position, positionOffset);
            scrollTo(a(position, positionOffset), 0);
            setSelectedTabView(Math.round(((float) position) + positionOffset));
        }
    }

    public void addTabsFromPagerAdapter(PagerAdapter adapter) {
        int count = adapter.getCount();
        for (int i2 = 0; i2 < count; i2++) {
            addTab(newTab().setText(adapter.getPageTitle(i2)));
        }
    }

    public void setupWithViewPager(ViewPager viewPager) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        }
        addTabsFromPagerAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayoutOnPageChangeListener(this));
        setOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager));
        if (this.c == null || this.c.getPosition() != viewPager.getCurrentItem()) {
            getTabAt(viewPager.getCurrentItem()).select();
        }
    }

    public void addTab(Tab tab) {
        addTab(tab, this.b.isEmpty());
    }

    public void addTab(Tab tab, boolean setSelected) {
        if (tab.c != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        a(tab, setSelected);
        a(tab, this.b.size());
        if (setSelected) {
            tab.select();
        }
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.k = onTabSelectedListener;
    }

    public Tab newTab() {
        return new Tab(this);
    }

    public Tab getTabAt(int index) {
        return this.b.get(index);
    }

    private TabView b(Tab tab) {
        TabView tabView = new TabView(getContext(), tab);
        tabView.setFocusable(true);
        if (this.l == null) {
            this.l = new OnClickListener() {
                public void onClick(View view) {
                    ((TabView) view).getTab().select();
                }
            };
        }
        tabView.setOnClickListener(this.l);
        return tabView;
    }

    private void a(Tab tab, int position) {
        tab.a(position);
        this.b.add(position, tab);
        int count = this.b.size();
        for (int i2 = position + 1; i2 < count; i2++) {
            this.b.get(i2).a(i2);
        }
    }

    /* access modifiers changed from: private */
    public void a(int position) {
        TabView view = (TabView) this.d.getChildAt(position);
        if (view != null) {
            view.a();
        }
    }

    private void a(Tab tab, boolean setSelected) {
        TabView tabView = b(tab);
        this.d.addView(tabView, b());
        if (setSelected) {
            tabView.setSelected(true);
        }
    }

    private static LayoutParams b() {
        LayoutParams lp = new LayoutParams(-2, -1);
        a(lp);
        return lp;
    }

    private static void a(LayoutParams lp) {
        lp.width = -2;
        lp.weight = 0.0f;
    }

    /* access modifiers changed from: private */
    public int b(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * ((float) dps));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case Integer.MIN_VALUE:
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(b(45), MeasureSpec.getSize(heightMeasureSpec)), UCCore.VERIFY_POLICY_QUICK);
                break;
            case 0:
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(b(45), UCCore.VERIFY_POLICY_QUICK);
                break;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.j = getMeasuredWidth() - b(56);
    }

    private void c(int newPosition) {
        clearAnimation();
        if (newPosition != -1) {
            if (getWindowToken() == null || !ViewCompat.isLaidOut(this)) {
                setScrollPosition(newPosition, 0.0f);
                return;
            }
            final int startScrollX = getScrollX();
            final int targetScrollX = a(newPosition, 0.0f);
            if (startScrollX != targetScrollX) {
                Animation animation = new Animation() {
                    /* access modifiers changed from: protected */
                    public void applyTransformation(float interpolatedTime, Transformation t) {
                        H5TabLayout.this.scrollTo((int) H5TabLayout.a((float) startScrollX, (float) targetScrollX, interpolatedTime), 0);
                    }
                };
                animation.setInterpolator(a);
                animation.setDuration(300);
                startAnimation(animation);
            }
            this.d.c(newPosition);
        }
    }

    private void setSelectedTabView(int position) {
        int tabCount = this.d.getChildCount();
        int i2 = 0;
        while (i2 < tabCount) {
            this.d.getChildAt(i2).setSelected(i2 == position);
            i2++;
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    /* access modifiers changed from: 0000 */
    public final void a(Tab tab) {
        int newPosition;
        if (this.c != tab) {
            if (tab != null) {
                newPosition = tab.getPosition();
            } else {
                newPosition = -1;
            }
            setSelectedTabView(newPosition);
            if ((this.c == null || this.c.getPosition() == -1) && newPosition != -1) {
                setScrollPosition(newPosition, 0.0f);
            } else {
                c(newPosition);
            }
            if (!(this.c == null || this.k == null)) {
                this.k.onTabUnselected(this.c);
            }
            this.c = tab;
            if (this.c != null && this.k != null) {
                this.k.onTabSelected(this.c);
            }
        } else if (this.c != null) {
            if (this.k != null) {
                this.k.onTabReselected(this.c);
            }
            c(tab.getPosition());
        }
    }

    private int a(int position, float positionOffset) {
        int selectedWidth;
        int nextWidth = 0;
        View selectedChild = this.d.getChildAt(position);
        View nextChild = position + 1 < this.d.getChildCount() ? this.d.getChildAt(position + 1) : null;
        if (selectedChild != null) {
            selectedWidth = selectedChild.getWidth();
        } else {
            selectedWidth = 0;
        }
        if (nextChild != null) {
            nextWidth = nextChild.getWidth();
        }
        return (int) (((((float) selectedChild.getLeft()) + ((((float) (selectedWidth + nextWidth)) * positionOffset) * 0.5f)) + (((float) selectedChild.getWidth()) * 0.5f)) - (((float) getWidth()) * 0.5f));
    }

    private void c() {
        ViewCompat.setPaddingRelative(this.d, Math.max(0, 0 - this.e), 0, 0, 0);
        this.d.setGravity(GravityCompat.START);
        d();
    }

    private void d() {
        for (int i2 = 0; i2 < this.d.getChildCount(); i2++) {
            View child = this.d.getChildAt(i2);
            a((LayoutParams) child.getLayoutParams());
            child.requestLayout();
        }
    }

    static float a(float startValue, float endValue, float fraction) {
        return ((endValue - startValue) * fraction) + startValue;
    }
}
