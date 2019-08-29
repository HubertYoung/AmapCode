package com.autonavi.widget.ui.filter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.filter.adapter.FilterA1MainListAdapter;
import com.autonavi.widget.ui.filter.adapter.FilterA1SubListAdapter;
import com.autonavi.widget.ui.filter.adapter.FilterA2ListAdapter;
import com.autonavi.widget.ui.filter.adapter.FilterA3ListAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FilterLayout<T> extends LinearLayout {
    public static final Integer INVALID_VALUE_CHECK = Integer.valueOf(9999);
    private final int DEFAULT_ITEM_HEIGHT;
    private final int DEFAULT_MAX_POPUP_HEIGHT;
    private LinearLayout innerLinearLayout;
    /* access modifiers changed from: private */
    public ArrayList<b> innerTabs;
    private a mCallBack;
    /* access modifiers changed from: private */
    public ers<T> mFilterAdapter;
    ers<T> mFilterAdapterProxy;
    public b mLastSelectTab;
    private final ArrayList<b> mTabs;
    /* access modifiers changed from: private */
    public PopupWindow popUpFilterWindow;
    private FrameLayout popUpRootView;

    public interface a {
    }

    final class b {
        final FilterType a;
        View b;
        View c;
        final String d;
        T e;
        T f;
        ArrayList<T> g = new ArrayList<>();

        b(FilterType filterType, String str) {
            this.a = filterType;
            this.d = str;
        }
    }

    public static class c {
        public static <T extends View> T a(View view, int i) {
            SparseArray sparseArray = (SparseArray) view.getTag();
            if (sparseArray == null) {
                sparseArray = new SparseArray();
                view.setTag(sparseArray);
            }
            T t = (View) sparseArray.get(i);
            if (t != null) {
                return t;
            }
            T findViewById = view.findViewById(i);
            sparseArray.put(i, findViewById);
            return findViewById;
        }
    }

    public FilterLayout(Context context) {
        this(context, null);
    }

    public FilterLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FilterLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTabs = new ArrayList<>();
        this.innerTabs = new ArrayList<>();
        this.popUpFilterWindow = null;
        this.innerLinearLayout = null;
        this.DEFAULT_ITEM_HEIGHT = dip2px(40.0f);
        this.DEFAULT_MAX_POPUP_HEIGHT = dip2px(318.0f);
        this.mFilterAdapterProxy = new ers<T>() {
            public final String a(T t) {
                return t == null ? "" : FilterLayout.this.mFilterAdapter.a(t);
            }

            public final boolean a(@Nullable T t, @Nullable T t2) {
                return (t == null || t2 == null || !FilterLayout.this.mFilterAdapter.a(t, t2)) ? false : true;
            }

            public final List<T> a(String str) {
                return FilterLayout.this.getNonNullList(FilterLayout.this.mFilterAdapter.a(str));
            }

            @NonNull
            public final T b(String str) {
                return FilterLayout.this.mFilterAdapter.b(str);
            }

            @NonNull
            public final List<T> a(T t, String str) {
                return FilterLayout.this.getNonNullList(FilterLayout.this.mFilterAdapter.a(t, str));
            }

            @Nullable
            public final T c(String str) {
                return FilterLayout.this.mFilterAdapter.c(str);
            }
        };
        setOrientation(1);
        setBackgroundColor(getResources().getColor(R.color.c_4));
        this.innerLinearLayout = new LinearLayout(context);
        addView(this.innerLinearLayout, new LayoutParams(-1, dip2px(40.0f)));
        this.innerLinearLayout.setOrientation(0);
        this.innerLinearLayout.setDividerPadding(dip2px(10.0f));
        this.innerLinearLayout.setDividerDrawable(getResources().getDrawable(R.drawable.ui_filter_btn_sep));
        this.innerLinearLayout.setShowDividers(2);
        View view = new View(context);
        view.setBackgroundColor(getResources().getColor(R.color.c_13));
        addView(view, new LayoutParams(-1, 1));
    }

    public void addTabA1(String str) {
        this.mTabs.add(new b(FilterType.FILTER_TYPE_A1, str));
    }

    public void addTabA2(String str) {
        this.mTabs.add(new b(FilterType.FILTER_TYPE_A2, str));
    }

    public void addTabA3(String str) {
        this.mTabs.add(new b(FilterType.FILTER_TYPE_A3, str));
    }

    public T getMainValue(String str) {
        return getTabInfoByTag(str).e;
    }

    @Nullable
    public T getSubValue(String str) {
        return getTabInfoByTag(str).f;
    }

    public ArrayList<T> getCheckedListValue(String str) {
        return getTabInfoByTag(str).g;
    }

    private b getTabInfoByTag(String str) {
        Iterator<b> it = this.innerTabs.iterator();
        while (it.hasNext()) {
            b next = it.next();
            if (next.d.equals(str)) {
                return next;
            }
        }
        return null;
    }

    public void setDataAndCallBack(ers<T> ers, a aVar) {
        this.mFilterAdapter = ers;
        this.mCallBack = aVar;
    }

    public void notifyTabsChanged() {
        if (this.mFilterAdapter == null) {
            throw new RuntimeException("filterAdapter is null");
        } else if (this.mCallBack == null) {
            throw new RuntimeException("callBack is null");
        } else {
            this.innerLinearLayout.removeAllViews();
            this.innerTabs.clear();
            LayoutParams layoutParams = new LayoutParams(0, dip2px(40.0f), 1.0f);
            for (int i = 0; i < this.mTabs.size(); i++) {
                final b bVar = this.mTabs.get(i);
                this.innerTabs.add(bVar);
                switch (bVar.a) {
                    case FILTER_TYPE_A1:
                        bVar.e = this.mFilterAdapterProxy.b(bVar.d);
                        bVar.f = this.mFilterAdapterProxy.c(bVar.d);
                        break;
                    case FILTER_TYPE_A2:
                        bVar.e = this.mFilterAdapterProxy.b(bVar.d);
                        break;
                    case FILTER_TYPE_A3:
                        bVar.g.add(this.mFilterAdapterProxy.b(bVar.d));
                        break;
                }
                final View inflate = inflate(getContext(), R.layout.ui_filter_item, null);
                ((TextView) c.a(inflate, R.id.filter_text)).setText(this.mFilterAdapterProxy.a(this.mFilterAdapterProxy.b(bVar.d)));
                inflate.setLayoutParams(layoutParams);
                this.innerLinearLayout.addView(inflate);
                inflate.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        if (bVar.c == null) {
                            FilterLayout.this.initFilterRootView(bVar);
                        }
                        if (FilterLayout.this.mLastSelectTab != bVar) {
                            if (FilterLayout.this.mLastSelectTab != null) {
                                FilterLayout.this.mLastSelectTab.b.setSelected(false);
                            }
                            inflate.setSelected(true);
                            FilterLayout.this.mLastSelectTab = bVar;
                            FilterLayout.this.updatePopUpFilterWindow(bVar);
                            return;
                        }
                        inflate.setSelected(false);
                        FilterLayout.this.mLastSelectTab = null;
                        FilterLayout.this.dismissNoCallBack();
                    }
                });
                bVar.b = inflate;
            }
            this.mTabs.clear();
        }
    }

    /* access modifiers changed from: private */
    public void updatePopUpFilterWindow(b bVar) {
        if (this.popUpFilterWindow == null) {
            this.popUpRootView = new FrameLayout(getContext());
            this.popUpRootView.addView(bVar.c, -1, measurePopUpHeight(bVar));
            this.popUpFilterWindow = new PopupWindow(this.popUpRootView, -1, -2);
            this.popUpFilterWindow.setBackgroundDrawable(getResources().getDrawable(R.color.transparent));
            this.popUpFilterWindow.setAnimationStyle(0);
            this.popUpFilterWindow.setOutsideTouchable(true);
            this.popUpFilterWindow.setFocusable(true);
            this.popUpFilterWindow.setOnDismissListener(new OnDismissListener() {
                public void onDismiss() {
                    if (FilterLayout.this.mLastSelectTab != null) {
                        FilterLayout.this.mLastSelectTab.b.setSelected(false);
                        FilterLayout.this.mLastSelectTab = null;
                    }
                }
            });
            this.popUpFilterWindow.setTouchInterceptor(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 0) {
                        int[] iArr = new int[2];
                        FilterLayout.this.getLocationOnScreen(iArr);
                        int rawX = (int) motionEvent.getRawX();
                        int rawY = (int) motionEvent.getRawY();
                        if (iArr[0] < rawX && iArr[1] < rawY && rawX < iArr[0] + FilterLayout.this.getWidth() && rawY < iArr[1] + FilterLayout.this.getHeight()) {
                            int i = 0;
                            while (i < FilterLayout.this.innerTabs.size()) {
                                int i2 = i + 1;
                                if (rawX < (FilterLayout.this.getWidth() * i2) / FilterLayout.this.innerTabs.size()) {
                                    ((b) FilterLayout.this.innerTabs.get(i)).b.performClick();
                                    return true;
                                }
                                i = i2;
                            }
                        }
                    }
                    return false;
                }
            });
            resetFilterValue(bVar);
        } else {
            this.popUpRootView.removeAllViews();
            this.popUpRootView.addView(bVar.c, -1, measurePopUpHeight(bVar));
            this.popUpFilterWindow.update();
            resetFilterValue(bVar);
        }
        this.popUpFilterWindow.showAsDropDown(this);
    }

    /* access modifiers changed from: private */
    public int measurePopUpHeight(b bVar) {
        switch (bVar.a) {
            case FILTER_TYPE_A1:
                int max = Math.max(((ListView) c.a(bVar.c, R.id.lv_main)).getAdapter().getCount(), ((ListView) c.a(bVar.c, R.id.lv_sub)).getAdapter().getCount());
                return Math.min(this.DEFAULT_MAX_POPUP_HEIGHT, (this.DEFAULT_ITEM_HEIGHT * max) + ((max - 1) * 1));
            case FILTER_TYPE_A2:
                int count = ((ListView) c.a(bVar.c, R.id.lv_main)).getAdapter().getCount();
                return Math.min(this.DEFAULT_MAX_POPUP_HEIGHT, (this.DEFAULT_ITEM_HEIGHT * count) + ((count - 1) * 1));
            case FILTER_TYPE_A3:
                int count2 = ((GridView) c.a(bVar.c, R.id.lv_main)).getAdapter().getCount();
                int i = (count2 / 3) + (count2 % 3 == 0 ? 0 : 1);
                return Math.min(this.DEFAULT_MAX_POPUP_HEIGHT, dip2px((float) ((i * 30) + ((i - 1) * 10) + 15 + 15 + 44)));
            default:
                return -2;
        }
    }

    private void resetFilterValue(b bVar) {
        if (bVar != null) {
            int i = AnonymousClass8.a[bVar.a.ordinal()];
            if (i != 1) {
                if (i == 3 && bVar.c != null) {
                    FilterA3ListAdapter filterA3ListAdapter = (FilterA3ListAdapter) ((GridView) c.a(bVar.c, R.id.lv_main)).getAdapter();
                    filterA3ListAdapter.getTempCheckedItemPositions().clear();
                    filterA3ListAdapter.getTempCheckedItemPositions().addAll(filterA3ListAdapter.getCheckedItemPositions());
                    filterA3ListAdapter.notifyDataSetChanged();
                }
            } else if (bVar.c != null) {
                FilterA1MainListAdapter filterA1MainListAdapter = (FilterA1MainListAdapter) ((ListView) c.a(bVar.c, R.id.lv_main)).getAdapter();
                filterA1MainListAdapter.setTempSelectItem(filterA1MainListAdapter.getSelectItem());
                filterA1MainListAdapter.notifyDataSetChanged();
                FilterA1SubListAdapter filterA1SubListAdapter = (FilterA1SubListAdapter) ((ListView) c.a(bVar.c, R.id.lv_sub)).getAdapter();
                filterA1SubListAdapter.clear();
                filterA1SubListAdapter.addAll(this.mFilterAdapterProxy.a(filterA1MainListAdapter.getSelectItem(), bVar.d));
                filterA1SubListAdapter.notifyDataSetChanged();
            }
        }
    }

    private int dip2px(float f) {
        return (int) ((f * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* access modifiers changed from: private */
    public void initFilterRootView(final b bVar) {
        View view = null;
        switch (bVar.a) {
            case FILTER_TYPE_A1:
                view = inflate(getContext(), R.layout.ui_filter_a1, null);
                ListView listView = (ListView) c.a(view, R.id.lv_main);
                ListView listView2 = (ListView) c.a(view, R.id.lv_sub);
                final FilterA1MainListAdapter filterA1MainListAdapter = new FilterA1MainListAdapter(getContext(), this.mFilterAdapterProxy, bVar.d);
                filterA1MainListAdapter.setSelectItem(this.mFilterAdapterProxy.b(bVar.d));
                listView.setAdapter(filterA1MainListAdapter);
                final FilterA1SubListAdapter filterA1SubListAdapter = new FilterA1SubListAdapter(getContext(), this.mFilterAdapterProxy, bVar.d, this.mFilterAdapterProxy.b(bVar.d));
                filterA1SubListAdapter.setSelectItem(this.mFilterAdapterProxy.c(bVar.d));
                listView2.setAdapter(filterA1SubListAdapter);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        if (FilterLayout.this.mFilterAdapterProxy.a(filterA1MainListAdapter.getItem(i), bVar.d) == null || FilterLayout.this.mFilterAdapterProxy.a(filterA1MainListAdapter.getItem(i), bVar.d).size() == 0) {
                            if (FilterLayout.this.mFilterAdapterProxy.a(filterA1MainListAdapter.getItem(i), filterA1MainListAdapter.getTempSelectItem())) {
                                FilterLayout.this.dismissNoCallBack();
                                return;
                            }
                            filterA1MainListAdapter.setSelectItem(filterA1MainListAdapter.getItem(i));
                            filterA1MainListAdapter.setTempSelectItem(null);
                            filterA1SubListAdapter.setSelectItem(null);
                            filterA1SubListAdapter.clear();
                            filterA1MainListAdapter.notifyDataSetChanged();
                            ((TextView) c.a(bVar.b, R.id.filter_text)).setText(FilterLayout.this.mFilterAdapterProxy.a(filterA1MainListAdapter.getSelectItem()));
                            bVar.e = filterA1MainListAdapter.getSelectItem();
                            bVar.f = null;
                            FilterLayout.this.dismiss();
                        } else if (!FilterLayout.this.mFilterAdapterProxy.a(filterA1MainListAdapter.getItem(i), filterA1MainListAdapter.getTempSelectItem())) {
                            filterA1MainListAdapter.setTempSelectItem(filterA1MainListAdapter.getItem(i));
                            filterA1MainListAdapter.notifyDataSetChanged();
                            filterA1SubListAdapter.setNotifyOnChange(false);
                            filterA1SubListAdapter.clear();
                            filterA1SubListAdapter.addAll(FilterLayout.this.mFilterAdapterProxy.a(filterA1MainListAdapter.getItem(i), bVar.d));
                            filterA1SubListAdapter.notifyDataSetChanged();
                            if (FilterLayout.this.popUpFilterWindow != null) {
                                bVar.c.getLayoutParams().height = FilterLayout.this.measurePopUpHeight(bVar);
                            }
                        }
                    }
                });
                listView2.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        if (FilterLayout.this.mFilterAdapterProxy.a(filterA1SubListAdapter.getItem(i), filterA1SubListAdapter.getSelectItem())) {
                            FilterLayout.this.dismissNoCallBack();
                            return;
                        }
                        filterA1MainListAdapter.setSelectItem(filterA1MainListAdapter.getTempSelectItem());
                        filterA1SubListAdapter.setSelectItem(filterA1SubListAdapter.getItem(i));
                        filterA1SubListAdapter.notifyDataSetChanged();
                        ((TextView) c.a(bVar.b, R.id.filter_text)).setText(FilterLayout.this.mFilterAdapterProxy.a(filterA1SubListAdapter.getSelectItem()));
                        bVar.e = filterA1MainListAdapter.getSelectItem();
                        bVar.f = filterA1SubListAdapter.getSelectItem();
                        FilterLayout.this.dismiss();
                    }
                });
                break;
            case FILTER_TYPE_A2:
                view = inflate(getContext(), R.layout.ui_filter_a2, null);
                ListView listView3 = (ListView) c.a(view, R.id.lv_main);
                final FilterA2ListAdapter filterA2ListAdapter = new FilterA2ListAdapter(getContext(), this.mFilterAdapterProxy, bVar.d);
                filterA2ListAdapter.setSelectItem(this.mFilterAdapterProxy.b(bVar.d));
                listView3.setAdapter(filterA2ListAdapter);
                listView3.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        if (FilterLayout.this.mFilterAdapterProxy.a(filterA2ListAdapter.getItem(i), filterA2ListAdapter.getSelectItem())) {
                            FilterLayout.this.dismissNoCallBack();
                            return;
                        }
                        filterA2ListAdapter.setSelectItem(filterA2ListAdapter.getItem(i));
                        filterA2ListAdapter.notifyDataSetChanged();
                        ((TextView) c.a(bVar.b, R.id.filter_text)).setText(FilterLayout.this.mFilterAdapterProxy.a(filterA2ListAdapter.getSelectItem()));
                        bVar.e = filterA2ListAdapter.getSelectItem();
                        FilterLayout.this.dismiss();
                    }
                });
                break;
            case FILTER_TYPE_A3:
                view = inflate(getContext(), R.layout.ui_filter_a3, null);
                GridView gridView = (GridView) c.a(view, R.id.lv_main);
                final FilterA3ListAdapter filterA3ListAdapter = new FilterA3ListAdapter(getContext(), this.mFilterAdapterProxy, bVar.d);
                filterA3ListAdapter.setSelectItem(this.mFilterAdapterProxy.b(bVar.d));
                gridView.setAdapter(filterA3ListAdapter);
                gridView.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        if (filterA3ListAdapter.getTempCheckedItemPositions().contains(Integer.valueOf(i))) {
                            filterA3ListAdapter.getTempCheckedItemPositions().remove(Integer.valueOf(i));
                            if (filterA3ListAdapter.getTempCheckedItemPositions().size() == 0) {
                                filterA3ListAdapter.getTempCheckedItemPositions().add(FilterLayout.INVALID_VALUE_CHECK);
                            }
                        } else {
                            filterA3ListAdapter.getTempCheckedItemPositions().add(Integer.valueOf(i));
                        }
                        filterA3ListAdapter.notifyDataSetChanged();
                    }
                });
                ((TextView) c.a(view, R.id.reset)).setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        filterA3ListAdapter.getTempCheckedItemPositions().clear();
                        filterA3ListAdapter.getTempCheckedItemPositions().add(FilterLayout.INVALID_VALUE_CHECK);
                        filterA3ListAdapter.notifyDataSetChanged();
                    }
                });
                ((TextView) c.a(view, R.id.confirm)).setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        filterA3ListAdapter.getTempCheckedItemPositions().remove(FilterLayout.INVALID_VALUE_CHECK);
                        ArrayList arrayList = new ArrayList(filterA3ListAdapter.getTempCheckedItemPositions());
                        Collections.sort(arrayList);
                        ArrayList arrayList2 = new ArrayList(filterA3ListAdapter.getCheckedItemPositions());
                        Collections.sort(arrayList2);
                        if (!arrayList.toString().equals(arrayList2.toString())) {
                            filterA3ListAdapter.getCheckedItemPositions().clear();
                            filterA3ListAdapter.getCheckedItemPositions().addAll(filterA3ListAdapter.getTempCheckedItemPositions());
                            if (filterA3ListAdapter.getCheckedItemPositions().size() == 0) {
                                bVar.g.add(FilterLayout.this.mFilterAdapterProxy.b(bVar.d));
                            } else {
                                Integer[] numArr = (Integer[]) filterA3ListAdapter.getCheckedItemPositions().toArray(new Integer[filterA3ListAdapter.getCheckedItemPositions().size()]);
                                Arrays.sort(numArr);
                                bVar.g.clear();
                                for (Integer intValue : numArr) {
                                    bVar.g.add(filterA3ListAdapter.getItem(intValue.intValue()));
                                }
                            }
                            ((TextView) c.a(bVar.b, R.id.filter_text)).setText(FilterLayout.this.mFilterAdapterProxy.a(bVar.g.get(0)));
                            filterA3ListAdapter.notifyDataSetChanged();
                            FilterLayout.this.dismiss();
                            return;
                        }
                        FilterLayout.this.dismissNoCallBack();
                    }
                });
                break;
        }
        bVar.c = view;
    }

    /* access modifiers changed from: private */
    public void dismiss() {
        if (this.popUpFilterWindow != null) {
            this.popUpFilterWindow.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void dismissNoCallBack() {
        if (this.popUpFilterWindow != null) {
            this.popUpFilterWindow.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissNoCallBack();
        this.popUpFilterWindow = null;
    }

    /* access modifiers changed from: private */
    public List<T> getNonNullList(List<T> list) {
        return list == null ? new ArrayList() : list;
    }
}
