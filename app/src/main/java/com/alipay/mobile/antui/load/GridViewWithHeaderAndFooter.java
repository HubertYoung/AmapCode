package com.alipay.mobile.antui.load;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import com.uc.webview.export.extension.UCCore;
import java.util.ArrayList;

public class GridViewWithHeaderAndFooter extends GridView {
    public static boolean DEBUG = false;
    private ArrayList<i> mFooterViewInfos = new ArrayList<>();
    private ArrayList<i> mHeaderViewInfos = new ArrayList<>();
    private int mRowHeight = -1;
    private OnTouchInvalidPositionListener mTouchInvalidPosListener;
    private View mViewForMeasureRowHeight = null;

    public interface OnTouchInvalidPositionListener {
        boolean onTouchInvalidPosition(int i);
    }

    private void initHeaderGridView() {
    }

    public GridViewWithHeaderAndFooter(Context context) {
        super(context);
        initHeaderGridView();
    }

    public GridViewWithHeaderAndFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderGridView();
    }

    public GridViewWithHeaderAndFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHeaderGridView();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ListAdapter adapter = getAdapter();
        if (adapter != null && (adapter instanceof k)) {
            ((k) adapter).a(getNumColumns());
            ((k) adapter).b(getRowHeight());
        }
    }

    public void setClipChildren(boolean clipChildren) {
    }

    public void setClipChildrenSupper(boolean clipChildren) {
        super.setClipChildren(false);
    }

    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    public void addHeaderView(View v, Object data, boolean isSelectable) {
        ListAdapter adapter = getAdapter();
        if (adapter == null || (adapter instanceof k)) {
            LayoutParams lyp = v.getLayoutParams();
            i info = new i(0);
            FrameLayout fl = new j(this, getContext());
            if (lyp != null) {
                v.setLayoutParams(new FrameLayout.LayoutParams(lyp.width, lyp.height));
                fl.setLayoutParams(new AbsListView.LayoutParams(lyp.width, lyp.height));
            }
            fl.addView(v);
            info.a = v;
            info.b = fl;
            info.c = data;
            info.d = isSelectable;
            this.mHeaderViewInfos.add(info);
            if (adapter != null) {
                ((k) adapter).a();
                return;
            }
            return;
        }
        throw new IllegalStateException("Cannot add header view to grid -- setAdapter has already been called.");
    }

    public void addFooterView(View v) {
        addFooterView(v, null, true);
    }

    public void addFooterView(View v, Object data, boolean isSelectable) {
        ListAdapter mAdapter = getAdapter();
        if (mAdapter == null || (mAdapter instanceof k)) {
            LayoutParams lyp = v.getLayoutParams();
            i info = new i(0);
            FrameLayout fl = new j(this, getContext());
            if (lyp != null) {
                v.setLayoutParams(new FrameLayout.LayoutParams(lyp.width, lyp.height));
                fl.setLayoutParams(new AbsListView.LayoutParams(lyp.width, lyp.height));
            }
            fl.addView(v);
            info.a = v;
            info.b = fl;
            info.c = data;
            info.d = isSelectable;
            this.mFooterViewInfos.add(info);
            if (mAdapter != null) {
                ((k) mAdapter).a();
                return;
            }
            return;
        }
        throw new IllegalStateException("Cannot add header view to grid -- setAdapter has already been called.");
    }

    public int getHeaderViewCount() {
        return this.mHeaderViewInfos.size();
    }

    public int getFooterViewCount() {
        return this.mFooterViewInfos.size();
    }

    public boolean removeHeaderView(View v) {
        if (this.mHeaderViewInfos.size() <= 0) {
            return false;
        }
        boolean result = false;
        ListAdapter adapter = getAdapter();
        if (adapter != null && ((k) adapter).a(v)) {
            result = true;
        }
        removeFixedViewInfo(v, this.mHeaderViewInfos);
        return result;
    }

    public boolean removeFooterView(View v) {
        if (this.mFooterViewInfos.size() <= 0) {
            return false;
        }
        boolean result = false;
        ListAdapter adapter = getAdapter();
        if (adapter != null && ((k) adapter).b(v)) {
            result = true;
        }
        removeFixedViewInfo(v, this.mFooterViewInfos);
        return result;
    }

    private void removeFixedViewInfo(View v, ArrayList<i> where) {
        int len = where.size();
        for (int i = 0; i < len; i++) {
            if (where.get(i).a == v) {
                where.remove(i);
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mViewForMeasureRowHeight = null;
    }

    public void invalidateRowHeight() {
        this.mRowHeight = -1;
    }

    public int getHeaderHeight(int row) {
        if (row >= 0) {
            return this.mHeaderViewInfos.get(row).a.getMeasuredHeight();
        }
        return 0;
    }

    public int getRowHeight() {
        if (this.mRowHeight > 0) {
            return this.mRowHeight;
        }
        ListAdapter adapter = getAdapter();
        int numColumns = getNumColumns();
        if (adapter == null || adapter.getCount() <= (this.mHeaderViewInfos.size() + this.mFooterViewInfos.size()) * numColumns) {
            return -1;
        }
        int mColumnWidth = getColumnWidth();
        View view = getAdapter().getView(this.mHeaderViewInfos.size() * numColumns, this.mViewForMeasureRowHeight, this);
        AbsListView.LayoutParams p = (AbsListView.LayoutParams) view.getLayoutParams();
        if (p == null) {
            p = new AbsListView.LayoutParams(-1, -2, 0);
            view.setLayoutParams(p);
        }
        view.measure(getChildMeasureSpec(MeasureSpec.makeMeasureSpec(mColumnWidth, UCCore.VERIFY_POLICY_QUICK), 0, p.width), getChildMeasureSpec(MeasureSpec.makeMeasureSpec(0, 0), 0, p.height));
        this.mViewForMeasureRowHeight = view;
        this.mRowHeight = view.getMeasuredHeight();
        return this.mRowHeight;
    }

    @TargetApi(11)
    public void tryToScrollToBottomSmoothly() {
        int lastPos = getAdapter().getCount() - 1;
        if (VERSION.SDK_INT >= 11) {
            smoothScrollToPositionFromTop(lastPos, 0);
        } else {
            setSelection(lastPos);
        }
    }

    @TargetApi(11)
    public void tryToScrollToBottomSmoothly(int duration) {
        int lastPos = getAdapter().getCount() - 1;
        if (VERSION.SDK_INT >= 11) {
            smoothScrollToPositionFromTop(lastPos, 0, duration);
        } else {
            setSelection(lastPos);
        }
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            k headerViewGridAdapter = new k(this.mHeaderViewInfos, this.mFooterViewInfos, adapter);
            int numColumns = getNumColumns();
            if (numColumns > 1) {
                headerViewGridAdapter.a(numColumns);
            }
            headerViewGridAdapter.b(getRowHeight());
            super.setAdapter(headerViewGridAdapter);
            return;
        }
        super.setAdapter(adapter);
    }

    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
        ListAdapter adapter = getAdapter();
        if (adapter != null && (adapter instanceof k)) {
            ((k) adapter).a(numColumns);
        }
    }

    public void setOnTouchInvalidPositionListener(OnTouchInvalidPositionListener listener) {
        this.mTouchInvalidPosListener = listener;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mTouchInvalidPosListener == null) {
            return super.onTouchEvent(event);
        }
        if (!isEnabled()) {
            return isClickable() || isLongClickable();
        }
        if (pointToPosition((int) event.getX(), (int) event.getY()) != -1) {
            return super.onTouchEvent(event);
        }
        super.onTouchEvent(event);
        return this.mTouchInvalidPosListener.onTouchInvalidPosition(event.getActionMasked());
    }
}
