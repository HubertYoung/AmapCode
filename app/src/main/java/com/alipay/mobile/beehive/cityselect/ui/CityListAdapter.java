package com.alipay.mobile.beehive.cityselect.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import java.util.Arrays;
import java.util.List;

public class CityListAdapter extends BaseAdapter implements SectionIndexer {
    public static final String TAG = "cityselect_CityListAdapter";
    private List<CityVO> mCityList;
    private int mHeaderItemsCount;
    private LayoutInflater mInflater;
    private boolean mIsSearchResult = false;
    private List<Integer> mPositions;
    private List<String> mSections;

    private static class a {
        AULinearLayout a;
        AUTextView b;
        AUTextView c;
        AUTextView d;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public CityListAdapter(Context context, List<CityVO> cityList, List<String> sections, List<Integer> positions, int offset) {
        this.mInflater = LayoutInflater.from(context);
        this.mCityList = cityList;
        this.mSections = sections;
        this.mPositions = positions;
        this.mHeaderItemsCount = offset;
    }

    public int getCount() {
        return this.mCityList.size();
    }

    public Object getItem(int position) {
        return this.mCityList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        int positionWithHeaderItems = position + this.mHeaderItemsCount;
        int section = getSectionForPosition(positionWithHeaderItems);
        if (convertView == null) {
            convertView = this.mInflater.inflate(getLayoutId(), null);
            holder = new a(0);
            holder.a = (AULinearLayout) convertView.findViewById(R.id.section_header_layout);
            holder.b = (AUTextView) convertView.findViewById(R.id.section_header_title);
            holder.c = (AUTextView) convertView.findViewById(R.id.city_item);
            holder.d = (AUTextView) convertView.findViewById(R.id.city_item_desc);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        if (getPositionForSection(section) != positionWithHeaderItems || this.mIsSearchResult || section < 0 || section >= this.mSections.size()) {
            holder.a.setVisibility(8);
        } else {
            holder.a.setVisibility(0);
            holder.b.setText(this.mSections.get(section));
        }
        CityVO cityVO = this.mCityList.get(position);
        holder.c.setText(cityVO.city);
        if (holder.d != null) {
            holder.d.setText(cityVO.enName);
        }
        convertView.setTag(R.layout.activity_area_select, cityVO);
        return convertView;
    }

    public Object[] getSections() {
        return this.mSections.toArray();
    }

    public int getPositionForSection(int section) {
        if (section < 0 || section >= this.mPositions.size()) {
            return -1;
        }
        return this.mPositions.get(section).intValue();
    }

    public int getSectionForPosition(int position) {
        if (position < 0 || position >= getCount() + this.mHeaderItemsCount) {
            return -1;
        }
        int index = Arrays.binarySearch(this.mPositions.toArray(), Integer.valueOf(position));
        return index < 0 ? (-index) - 2 : index;
    }

    public void updateData(List<CityVO> cityList) {
        if (cityList != null) {
            this.mCityList = cityList;
            notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.list_item_city_list;
    }

    public void setIsSearchResult(boolean isSearchResult) {
        this.mIsSearchResult = isSearchResult;
    }

    public boolean getIsSearchResult() {
        return this.mIsSearchResult;
    }
}
