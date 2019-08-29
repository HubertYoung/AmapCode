package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.IViewLayerExt;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.diy.DIYMainMapEntry;
import com.autonavi.widget.ui.BalloonImg;
import java.util.Iterator;
import java.util.List;

public class SchoolBusTipView implements OnLayoutChangeListener, OnGlobalLayoutListener, IViewLayer, IViewLayerExt {
    public static final String SP_KEY_DIY_SCHOOL_BUS_ENTRY_SHOW = "diy_school_bus_entry_show";
    private static final String TIPS_BOTTOM_POSITION = "bottom";
    private static final String TIPS_MIDDLE_POSITION = "middle";
    private static final String TIPS_TOP_POSITION = "top";
    private View mAnchorView;
    private AutoDismissTipsRunnable mAutoDismissTipsRunnable;
    private ViewGroup mContainer;
    private View mContentView;
    private Context mContext;
    private boolean mIsShowing = false;
    private OnDismissListener mOnDismissListener;
    /* access modifiers changed from: private */
    public bid mPageContext;
    private int mTipsDelayTime = 5000;
    private ViewTreeObserver mViewTreeObserver;
    private int mXOffsetPx = 15;
    private int mXPosition;
    private int mYOffsetPx = -40;
    private int mYPosition;
    private String tipsPosition = "";

    class AutoDismissTipsRunnable implements Runnable {
        private AutoDismissTipsRunnable() {
        }

        public void run() {
            if (SchoolBusTipView.this.mPageContext.isAlive()) {
                SchoolBusTipView.this.dismiss();
            }
        }
    }

    public interface OnDismissListener {
        void onDismiss();
    }

    public boolean isDismiss() {
        return false;
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void showBackground(boolean z) {
    }

    public SchoolBusTipView(bid bid) {
        this.mPageContext = bid;
        this.mContext = this.mPageContext.getContext();
        initView();
    }

    private void initView() {
        this.mContainer = new FrameLayout(this.mContext);
        this.mContainer.setLayoutParams(new LayoutParams(-1, -1));
        this.mContentView = createContentView();
        this.mContainer.addView(this.mContentView);
        this.mContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SchoolBusTipView.this.dismiss();
                return false;
            }
        });
        this.mAutoDismissTipsRunnable = new AutoDismissTipsRunnable();
    }

    private View createContentView() {
        BalloonImg balloonImg = new BalloonImg(this.mContext);
        balloonImg.setImageResource(R.drawable.school_bus_tip_bg);
        balloonImg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SchoolBusTipView.this.dismiss();
            }
        });
        return balloonImg;
    }

    public View getView() {
        return this.mContainer;
    }

    public void show(ViewGroup viewGroup, View view, List<DIYMainMapEntry> list) {
        if (!isShowing()) {
            View findEntryAnchorView = findEntryAnchorView(viewGroup, view, list);
            if (findEntryAnchorView != null) {
                this.mIsShowing = true;
                new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(SP_KEY_DIY_SCHOOL_BUS_ENTRY_SHOW, false);
                this.mAnchorView = findEntryAnchorView;
                this.mViewTreeObserver = this.mAnchorView.getViewTreeObserver();
                this.mViewTreeObserver.addOnGlobalLayoutListener(this);
                getAnchorViewPosition(viewGroup, list);
                getAnchorLocation(this.mAnchorView);
                showWithAnchorPosition();
                aho.a(this.mAutoDismissTipsRunnable, (long) this.mTipsDelayTime);
            }
        }
    }

    private void getAnchorViewPosition(ViewGroup viewGroup, List<DIYMainMapEntry> list) {
        View findViewWithTag = viewGroup.findViewWithTag("schoolbus");
        if (findViewWithTag == null && list.size() > a.a) {
            this.tipsPosition = TIPS_BOTTOM_POSITION;
        } else if (findViewWithTag == null || !"schoolbus".equals(list.get(0).key)) {
            this.tipsPosition = TIPS_MIDDLE_POSITION;
        } else {
            this.tipsPosition = "top";
        }
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    public void dismiss() {
        if (isShowing()) {
            this.mIsShowing = false;
            if (this.mAnchorView != null) {
                this.mAnchorView.removeOnLayoutChangeListener(this);
            }
            if (this.mAutoDismissTipsRunnable != null) {
                aho.b(this.mAutoDismissTipsRunnable);
            }
            removeOnGlobalLayoutListener();
            if (this.mOnDismissListener != null) {
                this.mPageContext.dismissViewLayer(this);
            }
        }
    }

    @TargetApi(16)
    private void removeOnGlobalLayoutListener() {
        if (this.mViewTreeObserver != null && this.mViewTreeObserver.isAlive()) {
            this.mViewTreeObserver.removeOnGlobalLayoutListener(this);
        }
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public boolean onBackPressed() {
        dismiss();
        return false;
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        boolean z = !this.mAnchorView.isShown();
        boolean updatePosition = updatePosition(this.mAnchorView);
        if (z) {
            dismiss();
            return;
        }
        if (updatePosition) {
            update(this.mXPosition, this.mYPosition, this.mAnchorView);
        }
    }

    private void update(int i, int i2, View view) {
        getAnchorLocation(view);
        this.mContentView.setTranslationX((float) i);
        this.mContentView.setTranslationY((float) i2);
    }

    private boolean updatePosition(View view) {
        int i = this.mXPosition;
        int i2 = this.mYPosition;
        getAnchorLocation(view);
        return (i == this.mXPosition && i2 == this.mYPosition) ? false : true;
    }

    private void showWithAnchorPosition() {
        this.mAnchorView.removeOnLayoutChangeListener(this);
        this.mAnchorView.addOnLayoutChangeListener(this);
        this.mContentView.setTranslationX((float) this.mXPosition);
        this.mContentView.setTranslationY((float) this.mYPosition);
        if (this.mAnchorView.isShown()) {
            this.mPageContext.showViewLayer(this);
        }
    }

    private void getAnchorLocation(View view) {
        measureContentView();
        int[] iArr = new int[2];
        if (view != null) {
            view.getLocationInWindow(iArr);
            iArr[0] = iArr[0] - getContentViewWidth();
            if (this.tipsPosition.equals(TIPS_MIDDLE_POSITION)) {
                iArr[1] = iArr[1] - ((view.getHeight() * 4) / 5);
            } else if (this.tipsPosition.equals("top")) {
                iArr[1] = iArr[1] - ((view.getHeight() * 3) / 5);
            } else if (this.tipsPosition.equals(TIPS_BOTTOM_POSITION)) {
                iArr[1] = iArr[1] - view.getHeight();
            }
            this.mXPosition = iArr[0] + this.mXOffsetPx;
            this.mYPosition = iArr[1] + this.mYOffsetPx;
        }
    }

    private void measureContentView() {
        View rootView = this.mAnchorView.getRootView();
        int width = rootView.getWidth();
        int height = rootView.getHeight();
        this.mContentView.measure(MeasureSpec.makeMeasureSpec(width, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(height, Integer.MIN_VALUE));
    }

    private int getContentViewWidth() {
        int width = this.mContentView.getWidth();
        if (width > 0) {
            return width;
        }
        this.mContentView.measure(0, 0);
        return this.mContentView.getMeasuredWidth();
    }

    private View findEntryAnchorView(ViewGroup viewGroup, View view, List<DIYMainMapEntry> list) {
        View findViewWithTag = viewGroup.findViewWithTag("schoolbus");
        if (findViewWithTag != null) {
            return findViewWithTag;
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        Iterator<DIYMainMapEntry> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            DIYMainMapEntry next = it.next();
            if (next != null && TextUtils.equals(next.key, "schoolbus")) {
                findViewWithTag = view;
                break;
            }
        }
        return findViewWithTag;
    }

    public void onGlobalLayout() {
        if (this.mPageContext.isViewLayerShowing(this) && this.mAnchorView != null && this.mPageContext.isAlive()) {
            if (!this.mAnchorView.isShown()) {
                dismiss();
            }
            if (updatePosition(this.mAnchorView)) {
                update(this.mXPosition, this.mYPosition, this.mAnchorView);
            }
        }
    }
}
