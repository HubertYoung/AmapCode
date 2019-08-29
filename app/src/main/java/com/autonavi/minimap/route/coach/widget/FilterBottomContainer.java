package com.autonavi.minimap.route.coach.widget;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.coach.util.CoachActionLogUtil;
import com.autonavi.minimap.route.coach.util.CoachActionLogUtil.FilterType;
import java.util.ArrayList;
import java.util.List;

public class FilterBottomContainer extends RelativeLayout implements OnClickListener, com.autonavi.minimap.route.coach.widget.FilterButton.a {
    private static final int KEY_FILTER_STATION_ARRIVE = 4097;
    private static final int KEY_FILTER_STATION_DEPART = 4096;
    private FilterDataItem mArriveStationItem;
    private ClickButtonType mClickType;
    private Context mContext;
    /* access modifiers changed from: private */
    public FilterDataItem mDepartStationItem;
    private FilterButton mFilterByStationButton;
    private FilterButton mFilterByTimeBucketButton;
    private FilterButton mFilterByTimeButton;
    /* access modifiers changed from: private */
    public dzu mFilterResult;
    /* access modifiers changed from: private */
    public a mFilterResultCallback;
    private View mFilterStationContentView;
    private View mFilterTimeBucketContentView;
    public FilterDataItem mLastSelectedArriveStationItem;
    public FilterDataItem mLastSelectedDepartStationItem;

    enum ClickButtonType {
        CLICK_STATION,
        CLICK_TIME_BUCKET
    }

    public interface a {
        void a(dzu dzu);
    }

    public FilterBottomContainer(Context context) {
        this(context, null);
    }

    public FilterBottomContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FilterBottomContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.coach_filter_layout, this, true);
        this.mFilterByTimeButton = (FilterButton) findViewById(R.id.btn_filter_by_time);
        this.mFilterByTimeButton.setTextColorSelected(true);
        this.mFilterByTimeButton.setBgColorSelected(false);
        this.mFilterByStationButton = (FilterButton) findViewById(R.id.btn_filter_by_station);
        this.mFilterByTimeBucketButton = (FilterButton) findViewById(R.id.btn_filter_by_time_bucket);
        this.mFilterStationContentView = findViewById(R.id.filter_station_layout);
        this.mFilterTimeBucketContentView = findViewById(R.id.filter_time_bucket_layout);
        this.mFilterByTimeButton.setOnFilterButtonClickListener(this);
        this.mFilterByStationButton.setOnFilterButtonClickListener(this);
        this.mFilterByTimeBucketButton.setOnFilterButtonClickListener(this);
        this.mFilterResult = new dzu(context);
    }

    public void onFilterButtonClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_filter_by_time) {
            clickFilterByTime();
            CoachActionLogUtil.a(FilterType.FILTER_TIME);
        } else if (id == R.id.btn_filter_by_station) {
            showFilterPopupView(ClickButtonType.CLICK_STATION);
            CoachActionLogUtil.a(FilterType.FILTER_STATION);
        } else {
            if (id == R.id.btn_filter_by_time_bucket) {
                showFilterPopupView(ClickButtonType.CLICK_TIME_BUCKET);
                CoachActionLogUtil.a(FilterType.FILTER_TIME_BUCKET);
            }
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.filter_cancel || id == R.id.filter_mask) {
            onBackPressed();
        }
    }

    private void clickFilterByTime() {
        if (this.mFilterByTimeButton != null) {
            String text = this.mFilterByTimeButton.getText();
            String string = getContext().getString(R.string.filter_by_time_asc);
            String string2 = getContext().getString(R.string.filter_by_time_desc);
            if (TextUtils.isEmpty(text) || !text.equals(string)) {
                this.mFilterByTimeButton.setText(string);
                this.mFilterByTimeButton.setBgColorSelected(false);
                this.mFilterResult.b = true;
            } else {
                this.mFilterByTimeButton.setText(string2);
                this.mFilterByTimeButton.setBgColorSelected(true);
                this.mFilterResult.b = false;
            }
        }
        if (this.mFilterResultCallback != null) {
            this.mFilterResultCallback.a(this.mFilterResult);
        }
    }

    /* access modifiers changed from: private */
    public void updateFilterButtons(dzu dzu) {
        if (this.mFilterByTimeButton != null) {
            this.mFilterByTimeButton.setText(dzu.b ? R.string.filter_by_time_asc : R.string.filter_by_time_desc);
            this.mFilterByTimeButton.setBgColorSelected(!dzu.b);
        }
        if (this.mFilterByTimeBucketButton != null) {
            this.mFilterByTimeBucketButton.setSelected(dzu.i());
        }
        if (this.mFilterByStationButton != null) {
            this.mFilterByStationButton.setSelected(dzu.j());
        }
    }

    /* access modifiers changed from: private */
    public void dismissFilterPopupView() {
        if (this.mClickType == ClickButtonType.CLICK_STATION) {
            if (this.mFilterStationContentView != null) {
                endFilterPopupViewAnimation(this.mFilterStationContentView);
            }
        } else if (this.mFilterTimeBucketContentView != null) {
            endFilterPopupViewAnimation(this.mFilterTimeBucketContentView);
        }
    }

    private void showFilterPopupView(ClickButtonType clickButtonType) {
        View view;
        View view2;
        this.mClickType = clickButtonType;
        switch (clickButtonType) {
            case CLICK_STATION:
                if (this.mFilterStationContentView != null) {
                    view = this.mFilterStationContentView.findViewById(R.id.filter_mask);
                    view2 = this.mFilterStationContentView.findViewById(R.id.filter_content);
                    ListView listView = (ListView) this.mFilterStationContentView.findViewById(R.id.filter_primary_list);
                    ListView listView2 = (ListView) this.mFilterStationContentView.findViewById(R.id.filter_secondary_list);
                    final FilterListAdapter filterListAdapter = new FilterListAdapter(getContext(), 0);
                    final FilterListAdapter filterListAdapter2 = new FilterListAdapter(getContext(), 1);
                    filterListAdapter.setListData(getPrimaryList());
                    listView.setAdapter(filterListAdapter);
                    filterListAdapter.setSelectedItem(this.mDepartStationItem);
                    filterListAdapter2.setListData(this.mFilterResult.b());
                    listView2.setAdapter(filterListAdapter2);
                    view.setOnClickListener(this);
                    ((TextView) this.mFilterStationContentView.findViewById(R.id.filter_cancel)).setOnClickListener(this);
                    ((TextView) this.mFilterStationContentView.findViewById(R.id.filter_ok)).setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            FilterBottomContainer.this.mFilterResult.e();
                            if (FilterBottomContainer.this.mFilterResultCallback != null) {
                                FilterBottomContainer.this.mFilterResultCallback.a(FilterBottomContainer.this.mFilterResult);
                            }
                            FilterBottomContainer.this.updateFilterButtons(FilterBottomContainer.this.mFilterResult);
                            FilterBottomContainer.this.dismissFilterPopupView();
                        }
                    });
                    listView.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                            FilterDataItem filterDataItem = (FilterDataItem) filterListAdapter.getItem(i);
                            FilterDataItem filterDataItem2 = (FilterDataItem) filterListAdapter.getSelectedItem();
                            if (filterDataItem != null && !filterDataItem.equals(filterDataItem2)) {
                                filterListAdapter.setSelectedItem(filterDataItem);
                                filterListAdapter.notifyDataSetChanged();
                                if (filterDataItem.equals(FilterBottomContainer.this.mDepartStationItem)) {
                                    FilterBottomContainer.this.mFilterResult.b(filterListAdapter2.getListData());
                                    if (FilterBottomContainer.this.mLastSelectedDepartStationItem != null) {
                                        filterListAdapter2.setSelectedItem(FilterBottomContainer.this.mLastSelectedDepartStationItem);
                                    }
                                    filterListAdapter2.setListData(FilterBottomContainer.this.mFilterResult.b());
                                    return;
                                }
                                FilterBottomContainer.this.mFilterResult.a(filterListAdapter2.getListData());
                                if (FilterBottomContainer.this.mLastSelectedArriveStationItem != null) {
                                    filterListAdapter2.setSelectedItem(FilterBottomContainer.this.mLastSelectedArriveStationItem);
                                }
                                filterListAdapter2.setListData(FilterBottomContainer.this.mFilterResult.c());
                            }
                        }
                    });
                    listView2.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                            FilterDataItem filterDataItem = (FilterDataItem) filterListAdapter2.getItem(i);
                            if (filterDataItem != null) {
                                filterDataItem.setChecked(!filterDataItem.isChecked());
                            }
                            FilterDataItem filterDataItem2 = (FilterDataItem) filterListAdapter.getSelectedItem();
                            if (filterDataItem2 == null || !filterDataItem2.equals(FilterBottomContainer.this.mDepartStationItem)) {
                                FilterBottomContainer.this.mLastSelectedArriveStationItem = filterDataItem;
                            } else {
                                FilterBottomContainer.this.mLastSelectedDepartStationItem = filterDataItem;
                            }
                            filterListAdapter2.setSelectedItem(filterDataItem);
                            filterListAdapter2.notifyDataSetChanged();
                        }
                    });
                    this.mFilterStationContentView.setVisibility(0);
                    if (this.mFilterTimeBucketContentView != null) {
                        this.mFilterTimeBucketContentView.setVisibility(8);
                        break;
                    }
                } else {
                    return;
                }
                break;
            case CLICK_TIME_BUCKET:
                if (this.mFilterTimeBucketContentView != null) {
                    view = this.mFilterTimeBucketContentView.findViewById(R.id.filter_mask);
                    view2 = this.mFilterTimeBucketContentView.findViewById(R.id.filter_content);
                    ListView listView3 = (ListView) this.mFilterTimeBucketContentView.findViewById(R.id.filter_list);
                    final FilterListAdapter filterListAdapter3 = new FilterListAdapter(getContext(), 2);
                    filterListAdapter3.setListData(this.mFilterResult.d());
                    listView3.setAdapter(filterListAdapter3);
                    view.setOnClickListener(this);
                    ((TextView) this.mFilterTimeBucketContentView.findViewById(R.id.filter_cancel)).setOnClickListener(this);
                    ((TextView) this.mFilterTimeBucketContentView.findViewById(R.id.filter_ok)).setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            FilterBottomContainer.this.mFilterResult.g();
                            if (FilterBottomContainer.this.mFilterResultCallback != null) {
                                FilterBottomContainer.this.mFilterResultCallback.a(FilterBottomContainer.this.mFilterResult);
                            }
                            FilterBottomContainer.this.updateFilterButtons(FilterBottomContainer.this.mFilterResult);
                            FilterBottomContainer.this.dismissFilterPopupView();
                        }
                    });
                    listView3.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                            FilterDataItem filterDataItem = (FilterDataItem) filterListAdapter3.getItem(i);
                            if (filterDataItem != null) {
                                filterDataItem.setChecked(!filterDataItem.isChecked());
                            }
                            filterListAdapter3.setSelectedItem(filterDataItem);
                            filterListAdapter3.notifyDataSetChanged();
                        }
                    });
                    this.mFilterTimeBucketContentView.setVisibility(0);
                    if (this.mFilterStationContentView != null) {
                        this.mFilterStationContentView.setVisibility(8);
                        break;
                    }
                } else {
                    return;
                }
                break;
            default:
                return;
        }
        startFilterPopupViewAnimation(view, view2);
    }

    private void startFilterPopupViewAnimation(final View view, final View view2) {
        final int measuredHeight = view2.getMeasuredHeight();
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 200});
        ofInt.setDuration(200);
        ofInt.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                view.setAlpha(((float) intValue) / 200.0f);
                view2.setTranslationY((float) ((measuredHeight / 200) * (200 - intValue)));
            }
        });
        ofInt.start();
    }

    private void endFilterPopupViewAnimation(View view) {
        final View findViewById = view.findViewById(R.id.filter_mask);
        final View findViewById2 = view.findViewById(R.id.filter_content);
        final int measuredHeight = findViewById2.getMeasuredHeight();
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 200});
        ofInt.setDuration(200);
        final View view2 = view;
        AnonymousClass7 r1 = new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                findViewById.setAlpha(((float) (200 - intValue)) / 200.0f);
                findViewById2.setTranslationY((float) ((measuredHeight / 200) * intValue));
                if (intValue == 200) {
                    view2.setVisibility(8);
                }
            }
        };
        ofInt.addUpdateListener(r1);
        ofInt.start();
    }

    private List<FilterDataItem> getPrimaryList() {
        ArrayList arrayList = new ArrayList();
        if (this.mDepartStationItem == null) {
            this.mDepartStationItem = new FilterDataItem(4096, this.mContext.getString(R.string.filter_by_station_start));
            this.mDepartStationItem.setChecked(true);
        }
        if (this.mArriveStationItem == null) {
            this.mArriveStationItem = new FilterDataItem(4097, this.mContext.getString(R.string.filter_by_station_end));
        }
        arrayList.add(this.mDepartStationItem);
        arrayList.add(this.mArriveStationItem);
        return arrayList;
    }

    public void setStationList(List<String> list, List<String> list2) {
        this.mFilterResult.a(list, list2);
        if (this.mFilterResultCallback != null) {
            this.mFilterResultCallback.a(this.mFilterResult);
        }
    }

    public void setFilterCallback(a aVar) {
        this.mFilterResultCallback = aVar;
    }

    public void resetFilterUI() {
        this.mFilterResult.a();
        updateFilterButtons(this.mFilterResult);
    }

    public boolean onBackPressed() {
        if (this.mClickType == ClickButtonType.CLICK_STATION) {
            if (this.mFilterStationContentView != null && this.mFilterStationContentView.isShown()) {
                endFilterPopupViewAnimation(this.mFilterStationContentView);
                this.mFilterResult.f();
                return true;
            }
        } else if (this.mFilterTimeBucketContentView != null && this.mFilterTimeBucketContentView.isShown()) {
            endFilterPopupViewAnimation(this.mFilterTimeBucketContentView);
            this.mFilterResult.h();
            return true;
        }
        return false;
    }
}
