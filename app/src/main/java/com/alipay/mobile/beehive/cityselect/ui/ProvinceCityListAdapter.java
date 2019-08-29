package com.alipay.mobile.beehive.cityselect.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.tablelist.AUSingleTitleListItem;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityInfo;
import com.alipay.mobile.beehive.cityselect.model.CountryInfo;
import com.alipay.mobile.beehive.cityselect.model.ProvinceCityInfo;
import com.alipay.mobile.beehive.cityselect.model.ProvinceInfo;
import java.util.ArrayList;
import java.util.List;

public class ProvinceCityListAdapter extends BaseAdapter {
    private Context mContext;
    private List mData = new ArrayList();

    static class a {
        AUSingleTitleListItem a;
        View b;

        a() {
        }
    }

    public ProvinceCityListAdapter(Context context) {
        this.mContext = context;
    }

    public int getCount() {
        if (this.mData != null) {
            return this.mData.size();
        }
        return 0;
    }

    public Object getItem(int position) {
        if (this.mData != null) {
            return this.mData.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_provincecitylist, null);
            holder = new a();
            holder.a = (AUSingleTitleListItem) convertView.findViewById(R.id.provincecity_name);
            holder.b = convertView.findViewById(R.id.provincecity_divider);
            holder.a.setClickable(false);
            holder.a.getRightTextView().setTextColor(this.mContext.getResources().getColor(R.color.regionlist_selected_region));
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        holder.a.setBackgroundResource(0);
        String name = null;
        boolean isShowArrow = false;
        ProvinceCityInfo item = (ProvinceCityInfo) this.mData.get(position);
        if (item != null) {
            if (item.isSelected()) {
                holder.a.setRightText(this.mContext.getResources().getString(R.string.regionselect_selected_area));
            } else {
                holder.a.setRightText("");
            }
            if (item instanceof CityInfo) {
                name = ((CityInfo) item).getCityName();
            } else if (item instanceof ProvinceInfo) {
                ProvinceInfo province = (ProvinceInfo) item;
                name = ((ProvinceInfo) item).getProvinceName();
                if (province.getCitys() != null && !province.getCitys().isEmpty()) {
                    isShowArrow = true;
                }
            } else if (item instanceof CountryInfo) {
                CountryInfo country = (CountryInfo) item;
                name = country.getCountryName();
                if (country.getProvinces() != null && !country.getProvinces().isEmpty()) {
                    isShowArrow = true;
                }
            }
        }
        if (isShowArrow) {
            holder.a.setArrowImageVisibility(0);
        } else {
            holder.a.setArrowImageVisibility(8);
        }
        if (position == getCount() - 1) {
            holder.b.setVisibility(4);
        } else {
            holder.b.setVisibility(0);
        }
        holder.a.setLeftText(name);
        return convertView;
    }

    public void setData(List data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }
}
