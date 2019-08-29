package com.alipay.mobile.antui.basic.banner;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.antui.api.AdapterCount;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BannerView extends FrameLayout implements OnPageChangeListener {
    private static final int SCALE_FACTOR = 10000;
    private static final String TAG = "BannerView";
    private BannerPageSelectedListener bannerPageSelectedListener;
    private Context context;
    private int disToBottom;
    private boolean isDark;
    /* access modifiers changed from: private */
    public boolean isDetached = false;
    private boolean isRotating;
    private long loopTime;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public BannerViewPager mPager;
    private RectanglePageIndicator mRectanglePageIndicator;
    private int selectColor;
    private int unSelectColor;

    public class BannerItem {
        public String actionUrl;
        public String contentDesc;
        public String id;
        public String imageUrl;
        public Object param;
        public Map<String, String> params;

        public String toString() {
            return "BannerItem{id='" + this.id + '\'' + ", imageUrl='" + this.imageUrl + '\'' + ", actionUrl='" + this.actionUrl + '\'' + ", contentDesc='" + this.contentDesc + '\'' + ", param=" + this.param + ", params=" + this.params + '}';
        }
    }

    public interface BannerItemClickListener {
        void onBannerAdClick(View view, int i);
    }

    public interface BannerPageSelectedListener {
        void onPageSelected(int i);
    }

    public class BannerViewPager extends ViewPager {
        boolean isTouching = false;

        public BannerViewPager(Context context) {
            super(context);
        }

        public BannerViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public boolean onTouchEvent(MotionEvent ev) {
            if (ev.getAction() == 3) {
                ev.setAction(1);
            }
            if (ev.getAction() == 1) {
                this.isTouching = false;
            }
            try {
                if (!enable() || !super.onTouchEvent(ev)) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                AuiLogger.error(BannerView.TAG, e.getMessage());
                return false;
            }
        }

        public boolean onInterceptTouchEvent(MotionEvent ev) {
            if (ev.getAction() == 0) {
                this.isTouching = true;
            } else if (ev.getAction() == 1 || ev.getAction() == 3) {
                this.isTouching = false;
            }
            try {
                if (!enable() || !super.onInterceptTouchEvent(ev)) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                AuiLogger.error(BannerView.TAG, e.getMessage());
                return false;
            }
        }

        private boolean enable() {
            try {
                if (getAdapter() == null || getAdapter().getCount() / 10000 != 1) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                return true;
            }
        }
    }

    public abstract class BaseBannerPagerAdapter extends PagerAdapter implements AdapterCount {
        protected BannerItemClickListener bannerClickListener;
        protected BannerView bannerView;
        protected List<BannerItem> items;

        public abstract View getView(ViewGroup viewGroup, int i);

        public BaseBannerPagerAdapter(BannerView bannerView2, List<BannerItem> items2) {
            this.bannerView = bannerView2;
            setItems(items2);
        }

        public List<BannerItem> getItems() {
            return this.items;
        }

        public void setItems(List<BannerItem> items2) {
            if (items2 != null) {
                if (this.items == null) {
                    this.items = new ArrayList();
                } else {
                    this.items.clear();
                }
                this.items.addAll(items2);
                notifyDataSetChanged();
            }
        }

        public int getItemPosition(Object object) {
            if (this.items == null || this.items.isEmpty()) {
                return -2;
            }
            return this.items.indexOf(object);
        }

        public int getCount() {
            if (this.items == null) {
                return 0;
            }
            return this.items.size() * 10000;
        }

        public int getRealCount() {
            if (this.items == null) {
                return 0;
            }
            return this.items.size();
        }

        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        public Object instantiateItem(ViewGroup container, int pos) {
            int realPos = pos % getRealCount();
            View itemView = getView(container, realPos);
            if (itemView == null) {
                Log.w(BannerView.TAG, "create null item " + this.items.get(realPos));
                return null;
            }
            itemView.setOnClickListener(new a(this, realPos));
            Log.d(BannerView.TAG, "create item " + this.items.get(realPos));
            return itemView;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        public BannerItemClickListener getItemClickListener() {
            return this.bannerClickListener;
        }

        public void setItemClickListener(BannerItemClickListener bannerClickListener2) {
            this.bannerClickListener = bannerClickListener2;
        }
    }

    public BannerView(Context context2, long loopTime2) {
        super(context2);
        this.context = context2;
        this.loopTime = loopTime2;
        this.disToBottom = dip2px(context2, 8.0d);
    }

    private static int dip2px(Context context2, double d) {
        return (int) ((((double) context2.getResources().getDisplayMetrics().density) * d) + 0.5d);
    }

    private static void setAccessibilityDisable(View view) {
        if (view != null && VERSION.SDK_INT >= 16) {
            view.setImportantForAccessibility(2);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.w(TAG, "onDetachedFromWindow");
        stopRotation();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
        startRotation();
    }

    public void startRotation() {
        Log.d(TAG, "startRotation");
        this.isRotating = true;
        doRotation();
    }

    /* access modifiers changed from: private */
    public void doRotation() {
        if (!this.isRotating) {
            Log.d(TAG, "doRotation cancel");
            return;
        }
        if (this.mHandler == null) {
            this.mHandler = new b(this);
        }
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessageDelayed(0, this.loopTime);
    }

    public void stopRotation() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(0);
        }
        this.isRotating = false;
        Log.d(TAG, "stopRotation");
    }

    public boolean isRotating() {
        Log.d(TAG, "isRotating " + this.isRotating);
        return this.isRotating;
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float v, int i2) {
    }

    public void onPageSelected(int i) {
        doRotation();
        callBannerPageSelectedListener(i);
    }

    public void setAdapter(BaseBannerPagerAdapter adapter) {
        if (adapter != null) {
            refresh(adapter);
        }
    }

    private void refresh(BaseBannerPagerAdapter adapter) {
        removeAllViews();
        this.mPager = new BannerViewPager(this.context);
        this.mPager.setOnPageChangeListener(this);
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.gravity = 16;
        this.mPager.setAdapter(adapter);
        addView(this.mPager, layoutParams);
        this.mRectanglePageIndicator = new RectanglePageIndicator(getContext());
        this.mRectanglePageIndicator.setIndicatorStyleBrightOrDark(getContext(), this.isDark);
        if (!(this.selectColor == 0 || this.unSelectColor == 0)) {
            this.mRectanglePageIndicator.setIndicatorColor(this.selectColor, this.unSelectColor);
        }
        this.mRectanglePageIndicator.setViewPager(this.mPager);
        this.mRectanglePageIndicator.setOnPageChangeListener(this);
        LayoutParams layoutParams2 = new LayoutParams(-1, -2);
        layoutParams2.gravity = 81;
        this.mRectanglePageIndicator.setPadding(0, 0, 0, this.disToBottom);
        addView(this.mRectanglePageIndicator, layoutParams2);
        startRotation();
        setAccessibilityDisable(this.mPager);
    }

    public void setIndicatorColor(int selectColor2, int unSelectColor2) {
        this.selectColor = selectColor2;
        this.unSelectColor = unSelectColor2;
    }

    public void setIndicatorPositionFromBottom(int disToBottom2) {
        this.disToBottom = disToBottom2;
    }

    public void setIndicatorStyleDark(boolean isDark2) {
        this.isDark = isDark2;
    }

    private void callBannerPageSelectedListener(int pos) {
        try {
            if (this.bannerPageSelectedListener != null) {
                this.bannerPageSelectedListener.onPageSelected(pos % ((BaseBannerPagerAdapter) this.mPager.getAdapter()).getRealCount());
            }
        } catch (Exception e) {
            AuiLogger.error(TAG, e.getMessage());
        }
    }

    public void setBannerPageSelectedListener(BannerPageSelectedListener listener) {
        this.bannerPageSelectedListener = listener;
    }

    public long getLoopTime() {
        return this.loopTime;
    }

    public void setLoopTime(long loopTime2) {
        this.loopTime = loopTime2;
    }

    public BannerViewPager getPager() {
        return this.mPager;
    }

    public RectanglePageIndicator getRectanglePageIndicator() {
        return this.mRectanglePageIndicator;
    }
}
