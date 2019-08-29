package com.amap.bundle.drive.result.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.minimap.R;

public class FlowWidget extends LinearLayout implements OnPageChangeListener {
    private static final int CONTENT_HEIGHT = 400;
    private static final int INDICATOR_CONTAINER_MARGIN_TOP = 0;
    /* access modifiers changed from: private */
    public a adapter;
    private ViewPager flowContentContainer;
    private LinearLayout indicatorsContainer;

    public static abstract class a {
        public abstract int a();

        public abstract View b();

        public abstract int c();
    }

    class b extends PagerAdapter {
        public final boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        private b() {
        }

        /* synthetic */ b(FlowWidget flowWidget, byte b) {
            this();
        }

        public final int getCount() {
            if (FlowWidget.this.adapter == null) {
                return 0;
            }
            return FlowWidget.this.adapter.a();
        }

        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            if (FlowWidget.this.adapter == null) {
                return null;
            }
            View b = FlowWidget.this.adapter.b();
            viewGroup.addView(b);
            return b;
        }

        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
    }

    public FlowWidget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(1);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FlowWidget);
        this.flowContentContainer = new ViewPager(context);
        this.flowContentContainer.setOnPageChangeListener(this);
        addView(this.flowContentContainer, new LayoutParams(-1, (int) obtainStyledAttributes.getDimension(R.styleable.FlowWidget_content_height, 400.0f)));
        this.indicatorsContainer = new LinearLayout(context);
        this.indicatorsContainer.setOrientation(0);
        this.indicatorsContainer.setGravity(17);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.setMargins(0, 0, 0, 0);
        addView(this.indicatorsContainer, layoutParams);
        obtainStyledAttributes.recycle();
    }

    public void setAdapter(a aVar) {
        this.adapter = aVar;
        if (aVar != null) {
            this.indicatorsContainer.removeAllViews();
            this.flowContentContainer.setAdapter(new b(this, 0));
            if (aVar.a() > 1) {
                for (int i = 0; i < aVar.a(); i++) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageResource(aVar.c());
                    LayoutParams layoutParams = new LayoutParams(-2, -2);
                    layoutParams.setMargins(15, 0, 15, 0);
                    this.indicatorsContainer.addView(imageView, layoutParams);
                }
                ImageView imageView2 = (ImageView) this.indicatorsContainer.getChildAt(0);
                if (imageView2 != null) {
                    imageView2.setImageResource(aVar.c());
                }
            }
        }
    }

    public void setCurrentItem(int i) {
        this.flowContentContainer.setCurrentItem(i, false);
    }

    public void onPageSelected(int i) {
        if (this.adapter != null) {
            setIndactorIn(i);
        }
    }

    private void setIndactorIn(int i) {
        if (this.indicatorsContainer.getChildCount() > 0) {
            for (int i2 = 0; i2 < this.indicatorsContainer.getChildCount(); i2++) {
                ((ImageView) this.indicatorsContainer.getChildAt(i2)).setImageResource(this.adapter.c());
            }
            ImageView imageView = (ImageView) this.indicatorsContainer.getChildAt(i);
            if (imageView != null) {
                imageView.setImageResource(this.adapter.c());
            }
        }
    }
}
