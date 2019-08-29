package com.autonavi.minimap.route.run.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import com.uc.webview.export.extension.UCCore;

public class PinnedSectionListView extends ListView {
    private boolean isAnimation = false;
    private final DataSetObserver mDataSetObserver = new DataSetObserver() {
        public final void onChanged() {
            PinnedSectionListView.this.recreatePinnedShadow();
        }

        public final void onInvalidated() {
            PinnedSectionListView.this.recreatePinnedShadow();
        }
    };
    /* access modifiers changed from: private */
    public OnScrollListener mDelegateOnScrollListener;
    private MotionEvent mDownEvent;
    private final OnScrollListener mOnScrollListener = new OnScrollListener() {
        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (PinnedSectionListView.this.mDelegateOnScrollListener != null) {
                PinnedSectionListView.this.mDelegateOnScrollListener.onScrollStateChanged(absListView, i);
            }
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            if (PinnedSectionListView.this.mDelegateOnScrollListener != null) {
                PinnedSectionListView.this.mDelegateOnScrollListener.onScroll(absListView, i, i2, i3);
            }
            ListAdapter adapter = PinnedSectionListView.this.getAdapter();
            if (adapter != null && i2 != 0) {
                if (!PinnedSectionListView.this.isItemViewTypePinned(adapter, adapter.getItemViewType(i))) {
                    int access$400 = PinnedSectionListView.this.findCurrentSectionPosition(i);
                    if (access$400 >= 0) {
                        PinnedSectionListView.this.ensureShadowForPosition(access$400, i, i2);
                        return;
                    }
                } else if (PinnedSectionListView.this.getChildAt(0).getTop() != PinnedSectionListView.this.getPaddingTop()) {
                    PinnedSectionListView.this.ensureShadowForPosition(i, i, i2);
                    return;
                }
                PinnedSectionListView.this.destroyPinnedShadow();
            }
        }
    };
    private a mPinnedSection;
    private a mRecycleSection;
    private int mSectionsDistanceY;
    private GradientDrawable mShadowDrawable;
    private int mShadowHeight;
    private final PointF mTouchPoint = new PointF();
    private final Rect mTouchRect = new Rect();
    private int mTouchSlop;
    private View mTouchTarget;
    private int mTranslateY;

    public interface PinnedSectionListAdapter extends ListAdapter {
        boolean isItemViewTypePinned(int i);
    }

    static class a {
        public View a;
        public int b;
        public long c;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public PinnedSectionListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public PinnedSectionListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void initView() {
        setOnScrollListener(this.mOnScrollListener);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initShadow(true);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            this.isAnimation = false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void initShadow(boolean z) {
        if (z) {
            if (this.mShadowDrawable == null) {
                this.mShadowDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#e2e2e2"), Color.parseColor("#e2e2e2"), Color.parseColor("#e2e2e2")});
                this.mShadowHeight = 1;
            }
        } else if (this.mShadowDrawable != null) {
            this.mShadowDrawable = null;
            this.mShadowHeight = 0;
        }
    }

    private void createPinnedShadow(int i) {
        a aVar = this.mRecycleSection;
        this.mRecycleSection = null;
        if (aVar == null) {
            aVar = new a(0);
        }
        View view = getAdapter().getView(i, aVar.a, this);
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
            view.setLayoutParams(layoutParams);
        }
        int mode = MeasureSpec.getMode(layoutParams.height);
        int size = MeasureSpec.getSize(layoutParams.height);
        if (mode == 0) {
            mode = UCCore.VERIFY_POLICY_QUICK;
        }
        int height = (getHeight() - getListPaddingTop()) - getListPaddingBottom();
        if (size > height) {
            size = height;
        }
        view.measure(MeasureSpec.makeMeasureSpec((getWidth() - getListPaddingLeft()) - getListPaddingRight(), UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(size, mode));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        this.mTranslateY = 0;
        aVar.a = view;
        aVar.b = i;
        aVar.c = getAdapter().getItemId(i);
        this.mPinnedSection = aVar;
    }

    /* access modifiers changed from: private */
    public void destroyPinnedShadow() {
        if (this.mPinnedSection != null) {
            this.mRecycleSection = this.mPinnedSection;
            this.mPinnedSection = null;
        }
    }

    /* access modifiers changed from: private */
    public void ensureShadowForPosition(int i, int i2, int i3) {
        if (i3 < 2) {
            destroyPinnedShadow();
            return;
        }
        if (!(this.mPinnedSection == null || this.mPinnedSection.b == i)) {
            destroyPinnedShadow();
        }
        if (this.mPinnedSection == null) {
            createPinnedShadow(i);
        }
        int i4 = i + 1;
        if (i4 < getCount()) {
            int findFirstVisibleSectionPosition = findFirstVisibleSectionPosition(i4, i3 - (i4 - i2));
            if (findFirstVisibleSectionPosition >= 0) {
                this.mSectionsDistanceY = getChildAt(findFirstVisibleSectionPosition - i2).getTop() - (this.mPinnedSection.a.getBottom() + getPaddingTop());
                if (this.mSectionsDistanceY < 0) {
                    this.mTranslateY = this.mSectionsDistanceY;
                } else {
                    this.mTranslateY = 0;
                }
            } else {
                this.mTranslateY = 0;
                this.mSectionsDistanceY = Integer.MAX_VALUE;
            }
        }
    }

    private int findFirstVisibleSectionPosition(int i, int i2) {
        ListAdapter adapter = getAdapter();
        int count = adapter.getCount();
        if (getLastVisiblePosition() >= count) {
            return -1;
        }
        if (i + i2 >= count) {
            i2 = count - i;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = i + i3;
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(i4))) {
                return i4;
            }
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public int findCurrentSectionPosition(int i) {
        ListAdapter adapter = getAdapter();
        if (i >= adapter.getCount()) {
            return -1;
        }
        if (adapter instanceof SectionIndexer) {
            SectionIndexer sectionIndexer = (SectionIndexer) adapter;
            int positionForSection = sectionIndexer.getPositionForSection(sectionIndexer.getSectionForPosition(i));
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(positionForSection))) {
                return positionForSection;
            }
        }
        while (i >= 0) {
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(i))) {
                return i;
            }
            i--;
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public void recreatePinnedShadow() {
        destroyPinnedShadow();
        ListAdapter adapter = getAdapter();
        if (adapter != null && adapter.getCount() > 0) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int findCurrentSectionPosition = findCurrentSectionPosition(firstVisiblePosition);
            if (findCurrentSectionPosition != -1) {
                ensureShadowForPosition(findCurrentSectionPosition, firstVisiblePosition, getLastVisiblePosition() - firstVisiblePosition);
            }
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        if (onScrollListener == this.mOnScrollListener) {
            super.setOnScrollListener(onScrollListener);
        } else {
            this.mDelegateOnScrollListener = onScrollListener;
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(parcelable);
        post(new Runnable() {
            public final void run() {
                PinnedSectionListView.this.recreatePinnedShadow();
            }
        });
    }

    private String getStack(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("\n");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace != null) {
            int i = 0;
            while (i < stackTrace.length && i < 6) {
                sb.append("    ");
                sb.append(stackTrace[i].toString());
                sb.append("\n");
                i++;
            }
        }
        return sb.toString();
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (listAdapter == null || ((listAdapter instanceof PinnedSectionListAdapter) && listAdapter.getViewTypeCount() >= 2)) {
            ListAdapter adapter = getAdapter();
            if (adapter != null) {
                adapter.unregisterDataSetObserver(this.mDataSetObserver);
            }
            if (listAdapter != null) {
                listAdapter.registerDataSetObserver(this.mDataSetObserver);
            }
            if (adapter != listAdapter) {
                destroyPinnedShadow();
            }
            super.setAdapter(listAdapter);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mPinnedSection != null && ((i3 - i) - getPaddingLeft()) - getPaddingRight() != this.mPinnedSection.a.getWidth()) {
            recreatePinnedShadow();
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        int i;
        super.dispatchDraw(canvas);
        if (this.mPinnedSection != null) {
            int listPaddingLeft = getListPaddingLeft();
            int listPaddingTop = getListPaddingTop();
            View view = this.mPinnedSection.a;
            canvas.save();
            int height = view.getHeight();
            if (this.mShadowDrawable == null) {
                i = 0;
            } else {
                i = Math.min(this.mShadowHeight, this.mSectionsDistanceY);
            }
            canvas.clipRect(listPaddingLeft, listPaddingTop, view.getWidth() + listPaddingLeft, height + i + listPaddingTop);
            canvas.translate((float) listPaddingLeft, (float) (listPaddingTop + this.mTranslateY));
            drawChild(canvas, this.mPinnedSection.a, getDrawingTime());
            if (this.mShadowDrawable != null && this.mSectionsDistanceY > 0) {
                this.mShadowDrawable.setBounds(this.mPinnedSection.a.getLeft(), this.mPinnedSection.a.getBottom(), this.mPinnedSection.a.getRight(), this.mPinnedSection.a.getBottom() + this.mShadowHeight);
                this.mShadowDrawable.draw(canvas);
            }
            canvas.restore();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0 && this.mTouchTarget == null && this.mPinnedSection != null && isPinnedViewTouched(this.mPinnedSection.a, x, y)) {
            this.mTouchTarget = this.mPinnedSection.a;
            this.mTouchPoint.x = x;
            this.mTouchPoint.y = y;
            this.mDownEvent = MotionEvent.obtain(motionEvent);
        }
        if (this.mTouchTarget == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (isPinnedViewTouched(this.mTouchTarget, x, y)) {
            this.mTouchTarget.dispatchTouchEvent(motionEvent);
        }
        if (action == 1) {
            super.dispatchTouchEvent(motionEvent);
            performPinnedItemClick();
            clearTouchTarget();
        } else if (action == 3) {
            clearTouchTarget();
        } else if (action == 2 && !this.isAnimation && Math.abs(y - this.mTouchPoint.y) > ((float) this.mTouchSlop)) {
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            obtain.setAction(3);
            this.mTouchTarget.dispatchTouchEvent(obtain);
            obtain.recycle();
            super.dispatchTouchEvent(this.mDownEvent);
            super.dispatchTouchEvent(motionEvent);
            clearTouchTarget();
        }
        return true;
    }

    private boolean isPinnedViewTouched(View view, float f, float f2) {
        view.getHitRect(this.mTouchRect);
        this.mTouchRect.top += this.mTranslateY;
        this.mTouchRect.bottom += this.mTranslateY + getPaddingTop();
        this.mTouchRect.left += getPaddingLeft();
        this.mTouchRect.right -= getPaddingRight();
        return this.mTouchRect.contains((int) f, (int) f2);
    }

    private void clearTouchTarget() {
        this.mTouchTarget = null;
        if (this.mDownEvent != null) {
            this.mDownEvent.recycle();
            this.mDownEvent = null;
        }
    }

    private boolean performPinnedItemClick() {
        if (this.mPinnedSection == null) {
            return false;
        }
        OnItemClickListener onItemClickListener = getOnItemClickListener();
        if (onItemClickListener == null || !getAdapter().isEnabled(this.mPinnedSection.b)) {
            return false;
        }
        View view = this.mPinnedSection.a;
        playSoundEffect(0);
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        onItemClickListener.onItemClick(this, view, this.mPinnedSection.b, this.mPinnedSection.c);
        return true;
    }

    /* access modifiers changed from: private */
    public boolean isItemViewTypePinned(ListAdapter listAdapter, int i) {
        if (listAdapter instanceof HeaderViewListAdapter) {
            listAdapter = ((HeaderViewListAdapter) listAdapter).getWrappedAdapter();
        }
        return ((PinnedSectionListAdapter) listAdapter).isItemViewTypePinned(i);
    }
}
