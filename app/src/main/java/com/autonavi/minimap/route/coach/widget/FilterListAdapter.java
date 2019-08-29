package com.autonavi.minimap.route.coach.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.autonavi.minimap.R;

public class FilterListAdapter extends FilterBaseAdapter {
    public static final int TYPE_PRIMARY = 0;
    public static final int TYPE_SECONDARY = 1;
    public static final int TYPE_TIME_BUCKET = 2;
    private String mFilterNone;
    private int mNormalColorId;
    private int mSelectedColorId;
    private int mType;

    public FilterListAdapter(Context context, int i) {
        super(context);
        this.mType = i;
        this.mFilterNone = context.getString(R.string.filter_by_none);
        this.mSelectedColorId = context.getResources().getColor(R.color.f_c_6);
        this.mNormalColorId = context.getResources().getColor(R.color.f_c_2);
    }

    /* access modifiers changed from: protected */
    public int getResourceId(int i) {
        if (this.mType == 0) {
            return R.layout.coach_filter_primary_item;
        }
        if (this.mType == 1) {
            return R.layout.coach_filter_secondary_item;
        }
        return R.layout.coach_filter_time_bucket_item;
    }

    /* access modifiers changed from: protected */
    public void bindViewData(int i, View view) {
        TextView textView = (TextView) getCacheView(view, R.id.item_name);
        FilterDataItem filterDataItem = (FilterDataItem) getItem(i);
        if (filterDataItem != null) {
            textView.setText(filterDataItem.toString());
        }
        if (this.mType != 0) {
            CheckBox checkBox = (CheckBox) getCacheView(view, R.id.item_checkbox);
            if (filterDataItem != null) {
                if (this.mFilterNone.equals(filterDataItem.toString())) {
                    if (filterDataItem.equals(this.mSelectedItem)) {
                        setSelected(textView, checkBox, true);
                        filterDataItem.setChecked(true);
                        return;
                    }
                    boolean isSpecialFilterChecked = isSpecialFilterChecked();
                    setSelected(textView, checkBox, !isSpecialFilterChecked);
                    filterDataItem.setChecked(!isSpecialFilterChecked);
                } else if (this.mSelectedItem == null || !this.mFilterNone.equals(this.mSelectedItem.toString())) {
                    setSelected(textView, checkBox, filterDataItem.isChecked());
                } else {
                    setSelected(textView, checkBox, false);
                    filterDataItem.setChecked(false);
                }
            }
        } else if (this.mSelectedItem == null || !this.mSelectedItem.equals(getItem(i))) {
            view.setBackgroundColor(this.mContext.getResources().getColor(R.color.c_1));
            if (filterDataItem != null) {
                filterDataItem.setChecked(false);
            }
        } else {
            view.setBackgroundColor(this.mContext.getResources().getColor(R.color.c_3));
            if (filterDataItem != null) {
                filterDataItem.setChecked(true);
            }
        }
    }

    private void setSelected(TextView textView, CheckBox checkBox, boolean z) {
        if (textView != null) {
            textView.setTextColor(z ? this.mSelectedColorId : this.mNormalColorId);
        }
        if (checkBox != null) {
            checkBox.setChecked(z);
        }
    }

    private boolean isSpecialFilterChecked() {
        if (this.mListData != null && this.mListData.size() > 1) {
            for (int i = 1; i < this.mListData.size(); i++) {
                if (((FilterDataItem) this.mListData.get(i)).isChecked()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T extends View> T getCacheView(View view, int i) {
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
