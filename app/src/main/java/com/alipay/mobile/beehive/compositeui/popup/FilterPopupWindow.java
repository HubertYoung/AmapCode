package com.alipay.mobile.beehive.compositeui.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.compositeui.popup.model.FilterGridModel;
import java.util.List;

public class FilterPopupWindow extends PopupWindow {
    /* access modifiers changed from: private */
    public List<View> exceptionViews;
    /* access modifiers changed from: private */
    public LinearLayout filtersContainer;
    private List<FilterGridModel> gridModels;
    /* access modifiers changed from: private */
    public OnFilterChangedListener listener;
    private Context mContext;
    private String selectedFilter;

    public FilterPopupWindow(Context mContext2, View view, List<FilterGridModel> gridModels2, OnFilterChangedListener listener2, String selectedFilter2) {
        super(view, -1, -1);
        this.mContext = mContext2;
        this.gridModels = gridModels2;
        this.listener = listener2;
        this.filtersContainer = (LinearLayout) ((ViewGroup) view).getChildAt(0);
        this.selectedFilter = selectedFilter2;
        initView();
    }

    private void initView() {
        setAnimationStyle(R.style.popup_popupAnimation);
        for (FilterGridModel filterGridModel : this.gridModels) {
            if (TextUtils.equals(filterGridModel.styleType, FilterGridModel.STYLE_DIVIDER)) {
                this.filtersContainer.addView(LayoutInflater.from(this.mContext).inflate(R.layout.popup_seperator_line, null));
            }
            this.filtersContainer.addView(getFilterGrid(filterGridModel));
        }
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchInterceptor(new OnTouchListener() {
            public final boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                if (FilterPopupWindow.this.exceptionViews != null) {
                    for (View view : FilterPopupWindow.this.exceptionViews) {
                        int[] location = new int[2];
                        view.getLocationOnScreen(location);
                        if (a(view, x, y, location) && event.getAction() == 0) {
                            FilterPopupWindow.this.listener.onOtherClick();
                            FilterPopupWindow.this.dismiss();
                            view.dispatchTouchEvent(MotionEvent.obtain(event.getDownTime(), event.getEventTime(), 0, (float) (x - location[0]), (float) (y - location[1]), 0));
                            final View view2 = view;
                            final MotionEvent motionEvent = event;
                            final int i = x;
                            final int[] iArr = location;
                            final int i2 = y;
                            view.postDelayed(new Runnable() {
                                public final void run() {
                                    view2.dispatchTouchEvent(MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(), 1, (float) (i - iArr[0]), (float) (i2 - iArr[1]), 0));
                                }
                            }, 50);
                            return true;
                        }
                    }
                }
                if (event.getAction() != 0) {
                    return false;
                }
                int[] location2 = new int[2];
                FilterPopupWindow.this.filtersContainer.getLocationOnScreen(location2);
                if (a(FilterPopupWindow.this.filtersContainer, x, y, location2)) {
                    return false;
                }
                FilterPopupWindow.this.dismiss();
                return false;
            }

            private static boolean a(View view, int x, int y, int[] location) {
                if (x <= location[0] || x >= location[0] + view.getWidth() || y <= location[1] || y >= location[1] + view.getHeight()) {
                    return false;
                }
                return true;
            }
        });
    }

    private View getFilterGrid(FilterGridModel filterGridModel) {
        View v = LayoutInflater.from(this.mContext).inflate(R.layout.popup_filter_grid_layout, null);
        GridView gridView = (GridView) v.findViewById(R.id.filter_grid);
        gridView.setNumColumns(filterGridModel.numColumns);
        GridAdapter gridAdapter = new GridAdapter(this.mContext, this, this.listener, this.selectedFilter);
        gridAdapter.setFilters(filterGridModel.filters);
        gridView.setAdapter(gridAdapter);
        gridView.setFocusable(true);
        gridView.setFocusableInTouchMode(true);
        if (VERSION.SDK_INT > 8) {
            gridView.setOverScrollMode(2);
        }
        return v;
    }

    public List<View> getExceptionViews() {
        return this.exceptionViews;
    }

    public void setExceptionViews(List<View> exceptionViews2) {
        this.exceptionViews = exceptionViews2;
    }
}
