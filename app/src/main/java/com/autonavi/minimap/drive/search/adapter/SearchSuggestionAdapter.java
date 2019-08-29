package com.autonavi.minimap.drive.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class SearchSuggestionAdapter extends BaseAdapter {
    private ArrayList<CitySuggestion> mCitySugList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<POI> mPois;
    private int mSuggestionType;

    static class a {
        TextView a;
        TextView b;

        a() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public SearchSuggestionAdapter(Context context, ArrayList<CitySuggestion> arrayList) {
        this.mCitySugList = arrayList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void setCityData(ArrayList<CitySuggestion> arrayList) {
        this.mCitySugList = arrayList;
        this.mSuggestionType = 1;
        notifyDataSetChanged();
    }

    public void setAreaData(ArrayList<POI> arrayList) {
        this.mPois = arrayList;
        this.mSuggestionType = 0;
        notifyDataSetChanged();
    }

    public int getCount() {
        switch (this.mSuggestionType) {
            case 0:
                if (this.mPois != null) {
                    return this.mPois.size();
                }
                return 0;
            case 1:
                if (this.mCitySugList != null) {
                    return this.mCitySugList.size();
                }
                return 0;
            default:
                return 0;
        }
    }

    public Object getItem(int i) {
        switch (this.mSuggestionType) {
            case 0:
                if (this.mPois == null || i < 0 || i >= this.mPois.size()) {
                    return null;
                }
                return this.mPois.get(i);
            case 1:
                if (this.mCitySugList == null || i < 0 || i >= this.mCitySugList.size()) {
                    return null;
                }
                return this.mCitySugList.get(i);
            default:
                return null;
        }
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar = new a();
        if (view == null) {
            view = this.mLayoutInflater.inflate(R.layout.drive_search_city_suggestion_item, null);
            aVar.a = (TextView) view.findViewById(R.id.name);
            aVar.b = (TextView) view.findViewById(R.id.number);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        if (this.mSuggestionType == 1) {
            CitySuggestion citySuggestion = (CitySuggestion) getItem(i);
            if (citySuggestion != null) {
                aVar.a.setText(citySuggestion.name);
                String string = this.mContext.getString(R.string.city_suggestion_nums);
                aVar.b.setText(String.format(string, new Object[]{Integer.valueOf(citySuggestion.resultnum)}));
            }
        } else {
            POI poi = (POI) getItem(i);
            if (poi != null) {
                aVar.a.setText(poi.getName());
                aVar.b.setVisibility(8);
            }
        }
        return view;
    }
}
