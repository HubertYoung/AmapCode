package com.alipay.mobile.antui.basic;

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
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import com.uc.webview.export.extension.UCCore;

public class AUPinnedSectionListView extends ListView {
    private AUDragLoadingView dragLoadingView;
    /* access modifiers changed from: private */
    public boolean hasMoreItems;
    /* access modifiers changed from: private */
    public boolean isLoading;
    private final DataSetObserver mDataSetObserver;
    OnScrollListener mDelegateOnScrollListener;
    private MotionEvent mDownEvent;
    private final OnScrollListener mOnScrollListener;
    ae mPinnedSection;
    ae mRecycleSection;
    private int mSectionsDistanceY;
    private GradientDrawable mShadowDrawable;
    private int mShadowHeight;
    private final PointF mTouchPoint;
    private final Rect mTouchRect;
    private int mTouchSlop;
    private View mTouchTarget;
    int mTranslateY;
    /* access modifiers changed from: private */
    public OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMoreItems();
    }

    public interface PinnedSectionListAdapter extends ListAdapter {
        View getPinnedView(int i, View view, ViewGroup viewGroup);

        boolean isItemViewTypePinned(int i);
    }

    public AUPinnedSectionListView(Context context) {
        this(context, null);
    }

    public AUPinnedSectionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTouchRect = new Rect();
        this.mTouchPoint = new PointF();
        this.mOnScrollListener = new ab(this);
        this.mDataSetObserver = new ac(this);
        initView();
    }

    public AUPinnedSectionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mTouchRect = new Rect();
        this.mTouchPoint = new PointF();
        this.mOnScrollListener = new ab(this);
        this.mDataSetObserver = new ac(this);
        initView();
    }

    private void initView() {
        setOnScrollListener(this.mOnScrollListener);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initShadow(true);
        this.isLoading = false;
        this.dragLoadingView = new AUDragLoadingView(getContext());
    }

    public void setShadowVisible(boolean visible) {
        initShadow(visible);
        if (this.mPinnedSection != null) {
            View v = this.mPinnedSection.a;
            invalidate(v.getLeft(), v.getTop(), v.getRight(), v.getBottom() + this.mShadowHeight);
        }
    }

    public void initShadow(boolean visible) {
        if (visible) {
            if (this.mShadowDrawable == null) {
                this.mShadowDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#ffa0a0a0"), Color.parseColor("#50a0a0a0"), Color.parseColor("#00a0a0a0")});
                this.mShadowHeight = (int) (8.0f * getResources().getDisplayMetrics().density);
            }
        } else if (this.mShadowDrawable != null) {
            this.mShadowDrawable = null;
            this.mShadowHeight = 0;
        }
    }

    /* access modifiers changed from: 0000 */
    public View getPinnedViewInner(int position, View convertView) {
        ListAdapter adapter = getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            int numHeaders = ((HeaderViewListAdapter) adapter).getHeadersCount();
            if (position >= numHeaders) {
                int adjPosition = position - numHeaders;
                adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
                if ((adapter instanceof PinnedSectionListAdapter) && adjPosition < adapter.getCount()) {
                    return ((PinnedSectionListAdapter) adapter).getPinnedView(adjPosition, convertView, this);
                }
            }
        }
        if (adapter instanceof PinnedSectionListAdapter) {
            return ((PinnedSectionListAdapter) adapter).getPinnedView(position, convertView, this);
        }
        return getAdapter().getView(position, convertView, this);
    }

    /* access modifiers changed from: 0000 */
    public void createPinnedShadow(int position) {
        ae pinnedShadow = this.mRecycleSection;
        this.mRecycleSection = null;
        if (pinnedShadow == null) {
            pinnedShadow = new ae();
        }
        View pinnedView = getPinnedViewInner(position, pinnedShadow.a);
        LayoutParams layoutParams = pinnedView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
            pinnedView.setLayoutParams(layoutParams);
        }
        int heightMode = MeasureSpec.getMode(layoutParams.height);
        int heightSize = MeasureSpec.getSize(layoutParams.height);
        if (heightMode == 0) {
            heightMode = UCCore.VERIFY_POLICY_QUICK;
        }
        int maxHeight = (getHeight() - getListPaddingTop()) - getListPaddingBottom();
        if (heightSize > maxHeight) {
            heightSize = maxHeight;
        }
        pinnedView.measure(MeasureSpec.makeMeasureSpec((getWidth() - getListPaddingLeft()) - getListPaddingRight(), UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(heightSize, heightMode));
        pinnedView.layout(0, 0, pinnedView.getMeasuredWidth(), pinnedView.getMeasuredHeight());
        this.mTranslateY = 0;
        pinnedShadow.a = pinnedView;
        pinnedShadow.b = position;
        pinnedShadow.c = getAdapter().getItemId(position);
        this.mPinnedSection = pinnedShadow;
    }

    /* access modifiers changed from: 0000 */
    public void destroyPinnedShadow() {
        if (this.mPinnedSection != null) {
            this.mRecycleSection = this.mPinnedSection;
            this.mPinnedSection = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void ensureShadowForPosition(int sectionPosition, int firstVisibleItem, int visibleItemCount) {
        if (visibleItemCount < 2) {
            destroyPinnedShadow();
            return;
        }
        if (!(this.mPinnedSection == null || this.mPinnedSection.b == sectionPosition)) {
            destroyPinnedShadow();
        }
        if (this.mPinnedSection == null) {
            createPinnedShadow(sectionPosition);
        }
        int nextPosition = sectionPosition + 1;
        if (nextPosition < getCount()) {
            int nextSectionPosition = findFirstVisibleSectionPosition(nextPosition, visibleItemCount - (nextPosition - firstVisibleItem));
            if (nextSectionPosition >= 0) {
                int bottom = this.mPinnedSection.a.getBottom() + getPaddingTop();
                this.mSectionsDistanceY = getChildAt(nextSectionPosition - firstVisibleItem).getTop() - bottom;
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

    /* access modifiers changed from: 0000 */
    public int findFirstVisibleSectionPosition(int firstVisibleItem, int visibleItemCount) {
        ListAdapter adapter = getAdapter();
        int adapterDataCount = adapter.getCount();
        if (getLastVisiblePosition() >= adapterDataCount) {
            return -1;
        }
        if (firstVisibleItem + visibleItemCount >= adapterDataCount) {
            visibleItemCount = adapterDataCount - firstVisibleItem;
        }
        for (int childIndex = 0; childIndex < visibleItemCount; childIndex++) {
            int position = firstVisibleItem + childIndex;
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(position))) {
                return position;
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public int findCurrentSectionPosition(int fromPosition) {
        ListAdapter adapter = getAdapter();
        if (fromPosition >= adapter.getCount()) {
            return -1;
        }
        if (adapter instanceof SectionIndexer) {
            SectionIndexer indexer = (SectionIndexer) adapter;
            int itemPosition = indexer.getPositionForSection(indexer.getSectionForPosition(fromPosition));
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(itemPosition))) {
                return itemPosition;
            }
        }
        for (int position = fromPosition; position >= 0; position--) {
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(position))) {
                return position;
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    public void recreatePinnedShadow() {
        destroyPinnedShadow();
        ListAdapter adapter = getAdapter();
        if (adapter != null && adapter.getCount() > 0) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int sectionPosition = findCurrentSectionPosition(firstVisiblePosition);
            if (sectionPosition != -1) {
                ensureShadowForPosition(sectionPosition, firstVisiblePosition, getLastVisiblePosition() - firstVisiblePosition);
            }
        }
    }

    public void setOnScrollListener(OnScrollListener listener) {
        if (listener == this.mOnScrollListener) {
            super.setOnScrollListener(listener);
        } else {
            this.mDelegateOnScrollListener = listener;
        }
    }

    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        post(new ad(this));
    }

    public void setAdapter(ListAdapter adapter) {
        if (adapter != null) {
            if (!(adapter instanceof PinnedSectionListAdapter)) {
                throw new IllegalArgumentException("Does your adapter implement PinnedSectionListAdapter?");
            } else if (adapter.getViewTypeCount() < 2) {
                throw new IllegalArgumentException("Does your adapter handle at least two types of views in getViewTypeCount() method: items and sections?");
            }
        }
        ListAdapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        if (adapter != null) {
            adapter.registerDataSetObserver(this.mDataSetObserver);
        }
        if (oldAdapter != adapter) {
            destroyPinnedShadow();
        }
        super.setAdapter(adapter);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mPinnedSection != null && ((r - l) - getPaddingLeft()) - getPaddingRight() != this.mPinnedSection.a.getWidth()) {
            recreatePinnedShadow();
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        int min;
        super.dispatchDraw(canvas);
        if (this.mPinnedSection != null) {
            int pLeft = getListPaddingLeft();
            int pTop = getListPaddingTop();
            View view = this.mPinnedSection.a;
            canvas.save();
            int height = view.getHeight();
            if (this.mShadowDrawable == null) {
                min = 0;
            } else {
                min = Math.min(this.mShadowHeight, this.mSectionsDistanceY);
            }
            canvas.clipRect(pLeft, pTop, view.getWidth() + pLeft, pTop + height + min);
            canvas.translate((float) pLeft, (float) (this.mTranslateY + pTop));
            drawChild(canvas, this.mPinnedSection.a, getDrawingTime());
            if (this.mShadowDrawable != null && this.mSectionsDistanceY > 0) {
                this.mShadowDrawable.setBounds(this.mPinnedSection.a.getLeft(), this.mPinnedSection.a.getBottom(), this.mPinnedSection.a.getRight(), this.mPinnedSection.a.getBottom() + this.mShadowHeight);
                this.mShadowDrawable.draw(canvas);
            }
            canvas.restore();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        int action = ev.getAction();
        if (action == 0 && this.mTouchTarget == null && this.mPinnedSection != null && isPinnedViewTouched(this.mPinnedSection.a, x, y)) {
            this.mTouchTarget = this.mPinnedSection.a;
            this.mTouchPoint.x = x;
            this.mTouchPoint.y = y;
            this.mDownEvent = MotionEvent.obtain(ev);
        }
        if (this.mTouchTarget == null) {
            return super.dispatchTouchEvent(ev);
        }
        if (isPinnedViewTouched(this.mTouchTarget, x, y)) {
            ev.offsetLocation((float) (-getListPaddingLeft()), (float) (-getListPaddingTop()));
            this.mTouchTarget.dispatchTouchEvent(ev);
        }
        if (action == 1) {
            super.dispatchTouchEvent(ev);
            performPinnedItemClick();
            clearTouchTarget();
            return true;
        } else if (action == 3) {
            clearTouchTarget();
            return true;
        } else if (action != 2 || Math.abs(y - this.mTouchPoint.y) <= ((float) this.mTouchSlop)) {
            return true;
        } else {
            MotionEvent event = MotionEvent.obtain(ev);
            event.setAction(3);
            this.mTouchTarget.dispatchTouchEvent(event);
            event.recycle();
            super.dispatchTouchEvent(this.mDownEvent);
            super.dispatchTouchEvent(ev);
            clearTouchTarget();
            return true;
        }
    }

    private boolean isPinnedViewTouched(View view, float x, float y) {
        view.getHitRect(this.mTouchRect);
        this.mTouchRect.top += this.mTranslateY;
        this.mTouchRect.bottom += this.mTranslateY + getPaddingTop();
        this.mTouchRect.left += getPaddingLeft();
        this.mTouchRect.right -= getPaddingRight();
        return this.mTouchRect.contains((int) x, (int) y);
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
        OnItemClickListener listener = getOnItemClickListener();
        if (listener == null || !getAdapter().isEnabled(this.mPinnedSection.b)) {
            return false;
        }
        View view = this.mPinnedSection.a;
        playSoundEffect(0);
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        listener.onItemClick(this, view, this.mPinnedSection.b, this.mPinnedSection.c);
        return true;
    }

    public static boolean isItemViewTypePinned(ListAdapter adapter, int viewType) {
        if (adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        return ((PinnedSectionListAdapter) adapter).isItemViewTypePinned(viewType);
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void setHasMoreItems(boolean hasMoreItems2) {
        setIsLoading(false);
        this.hasMoreItems = hasMoreItems2;
        if (!this.hasMoreItems) {
            this.dragLoadingView.onShowNoMoreText();
            removeFooterView(this.dragLoadingView);
            return;
        }
        addFooterView(this.dragLoadingView);
    }

    public boolean hasMoreItems() {
        return this.hasMoreItems;
    }

    public void onFinishLoading(boolean hasMoreItems2) {
        setHasMoreItems(hasMoreItems2);
    }

    private void setIsLoading(boolean isLoading2) {
        this.isLoading = isLoading2;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }
}
