package com.autonavi.minimap.basemap.save.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class SaveSearchCitySuggestionAdapter extends BaseAdapter {
    private ArrayList<CitySuggestion> cs;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    static class a {
        TextView a;
        TextView b;

        a() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public SaveSearchCitySuggestionAdapter(Context context, ArrayList<CitySuggestion> arrayList) {
        this.cs = arrayList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void setData(ArrayList<CitySuggestion> arrayList) {
        this.cs = arrayList;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (this.cs != null) {
            return this.cs.size();
        }
        return 0;
    }

    public Object getItem(int i) {
        if (this.cs == null || i < 0 || i >= this.cs.size()) {
            return null;
        }
        return this.cs.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar = new a();
        CitySuggestion citySuggestion = (CitySuggestion) getItem(i);
        if (view == null) {
            view = this.mLayoutInflater.inflate(R.layout.save_search_city_suggestion_item, null);
            aVar.a = (TextView) view.findViewById(R.id.name);
            aVar.b = (TextView) view.findViewById(R.id.number);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        if (citySuggestion != null) {
            aVar.a.setText(citySuggestion.name);
            String string = this.mContext.getString(R.string.city_suggestion_nums);
            aVar.b.setText(String.format(string, new Object[]{Integer.valueOf(citySuggestion.resultnum)}));
        }
        return view;
    }
}
