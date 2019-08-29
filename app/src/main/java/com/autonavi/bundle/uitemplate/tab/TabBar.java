package com.autonavi.bundle.uitemplate.tab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.List;

public class TabBar extends LinearLayout {
    private static final int TABBAR_HEIGHT_IN_DP = 50;
    /* access modifiers changed from: private */
    public boolean isTabClickable = true;
    private final Handler mH;
    private a mOnTabClickListener;
    private b mOnTabLongClickListener;
    private c mOnTabTouchListener;
    private int mTabBarHeight;
    private int mTabBarWidth;
    private int mTabWidth;
    private final List<bep> mTabs;

    public interface a {
        void b(bep bep);
    }

    public interface b {
        boolean c(bep bep);
    }

    public interface c {
        boolean a(bep bep, MotionEvent motionEvent);
    }

    public TabBar(Handler handler, Context context, List<bep> list) {
        super(context);
        setWillNotDraw(false);
        this.mH = handler;
        setGravity(16);
        setBackgroundResource(R.drawable.main_tab_bg);
        setClickable(true);
        this.mTabs = list;
        init(this.mTabs);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        cnu.a();
        float a2 = cnu.a((float) configuration.screenWidthDp);
        if (configuration.orientation == 1 && bev.a(a2, (float) this.mTabBarWidth)) {
            int size = this.mTabs.size();
            this.mTabBarWidth = (int) a2;
            int i = (this.mTabBarWidth - (this.mTabWidth * size)) / ((size + 1) * 2);
            setPadding(i, 0, i, 0);
            for (bep bep : this.mTabs) {
                LayoutParams layoutParams = new LayoutParams(this.mTabWidth, this.mTabBarHeight);
                layoutParams.leftMargin = i;
                layoutParams.rightMargin = i;
                bep.f.setLayoutParams(layoutParams);
            }
        }
    }

    public void setTabClickable(boolean z) {
        this.isTabClickable = z;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(ags.a(getContext()).width(), bet.a(getContext(), 50));
    }

    public void setOnTabClickListener(a aVar) {
        this.mOnTabClickListener = aVar;
    }

    public void setOnTabLongClickListener(b bVar) {
        this.mOnTabLongClickListener = bVar;
    }

    public void setOnTabTouchListener(c cVar) {
        this.mOnTabTouchListener = cVar;
    }

    private void init(List<bep> list) {
        int size = list.size();
        this.mTabWidth = bet.a(getContext(), 108);
        this.mTabBarWidth = ags.a(getContext()).width();
        this.mTabBarHeight = bet.a(getContext(), 50);
        int i = (this.mTabBarWidth - (this.mTabWidth * size)) / ((size + 1) * 2);
        setPadding(i, 0, i, 0);
        for (final bep next : list) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.tab_bar_item, null);
            View findViewById = inflate.findViewById(R.id.tab_icon);
            TextView textView = (TextView) inflate.findViewById(R.id.tab_name);
            next.f = inflate;
            findViewById.setBackgroundResource(next.a);
            textView.setText(next.c);
            textView.setTextColor(ContextCompat.getColorStateList(getContext(), next.b));
            inflate.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (TabBar.this.isTabClickable) {
                        new StringBuilder("\n1\n2\n3\n4\nTabBar#Click: ").append(next.d);
                        TabBar.this.setSelectItem(next);
                        TabBar.this.dispatchTabClick(next);
                    }
                }
            });
            inflate.setOnTouchListener(new OnTouchListener() {
                @SuppressLint({"ClickableViewAccessibility"})
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return TabBar.this.dispatchTabTouchEvent(next, motionEvent);
                }
            });
            LayoutParams layoutParams = new LayoutParams(this.mTabWidth, this.mTabBarHeight);
            layoutParams.leftMargin = i;
            layoutParams.rightMargin = i;
            addView(inflate, layoutParams);
        }
    }

    public void setSelectItem(bep bep) {
        int indexOf = this.mTabs.indexOf(bep);
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            View findViewById = childAt.findViewById(R.id.tab_icon);
            View findViewById2 = childAt.findViewById(R.id.tab_name);
            findViewById.setSelected(false);
            findViewById2.setSelected(false);
            if (childCount == indexOf) {
                StringBuilder sb = new StringBuilder("TabBar#Select: ");
                sb.append(bep.d);
                sb.append(" -  ");
                sb.append(((TextView) findViewById2).getText());
                findViewById.setSelected(true);
                findViewById2.setSelected(true);
            }
        }
    }

    public void performTabClick(bep bep) {
        int indexOf = this.mTabs.indexOf(bep);
        if (indexOf != -1) {
            getChildAt(indexOf).performClick();
        }
    }

    /* access modifiers changed from: private */
    public void dispatchTabClick(bep bep) {
        if (this.mOnTabClickListener != null) {
            this.mOnTabClickListener.b(bep);
        }
    }

    private boolean dispatchTabLongClick(bep bep) {
        if (this.mOnTabLongClickListener != null) {
            return this.mOnTabLongClickListener.c(bep);
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean dispatchTabTouchEvent(bep bep, MotionEvent motionEvent) {
        if (this.mOnTabTouchListener != null) {
            return this.mOnTabTouchListener.a(bep, motionEvent);
        }
        return false;
    }
}
