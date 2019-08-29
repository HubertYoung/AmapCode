package com.alipay.mobile.beehive.cityselect.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.List;

public class CityGridAdapter extends BaseAdapter {
    private static final String TAG = "CityGridAdapter";
    private int bgResID;
    /* access modifiers changed from: private */
    public List<CityVO> cityList;
    private Context context;
    /* access modifiers changed from: private */
    public SelectCityFragment fragment;

    private static class a {
        public AUTextView a;

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    public CityGridAdapter(Context context2, List<CityVO> cityList2) {
        this.context = context2;
        this.cityList = cityList2;
    }

    public CityGridAdapter(Context context2, List<CityVO> cityList2, SelectCityFragment fragment2) {
        this.context = context2;
        this.cityList = cityList2;
        this.fragment = fragment2;
    }

    public int getCount() {
        return this.cityList.size();
    }

    public Object getItem(int position) {
        return this.cityList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.grid_item_city_list, parent, false);
        }
        a holder = (a) convertView.getTag();
        if (holder == null) {
            holder = new a(0);
            holder.a = (AUTextView) convertView.findViewById(R.id.city_item);
            holder.a.setClickable(true);
            if (this.fragment != null) {
                holder.a.setOnClickListener(new OnClickListener() {
                    public final void onClick(View v) {
                        H5Log.d(CityGridAdapter.TAG, "gridview click.");
                        CityGridAdapter.this.fragment.notifyCitySelectResult((CityVO) CityGridAdapter.this.cityList.get(position));
                    }
                });
            }
        }
        CityVO cityVO = (CityVO) getItem(position);
        holder.a.setText(cityVO.city);
        if (this.bgResID != 0) {
            convertView.setBackgroundResource(this.bgResID);
        }
        convertView.setTag(R.layout.activity_area_select, cityVO);
        return convertView;
    }

    public void setBgResID(int bgResID2) {
        this.bgResID = bgResID2;
    }
}
