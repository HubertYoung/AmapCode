package com.autonavi.minimap.route.train.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.model.TrainTypeItem;
import java.util.ArrayList;
import java.util.List;

public class TrainTicketFilterAdapter extends BaseAdapter {
    private Context mContext;
    private List<TrainTypeItem> mList;
    private int nSelectIndex = -1;

    static class a {
        public TextView a;
        public LinearLayout b;

        a() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return 0;
    }

    public int getViewTypeCount() {
        return 2;
    }

    public TrainTicketFilterAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList();
    }

    public void setData(List<TrainTypeItem> list) {
        this.mList = list;
    }

    public void addItem(TrainTypeItem trainTypeItem) {
        this.mList.add(trainTypeItem);
    }

    public int getCount() {
        if (this.mList != null) {
            return this.mList.size();
        }
        return 0;
    }

    public TrainTypeItem getItem(int i) {
        if (i < 0 || i >= this.mList.size()) {
            return null;
        }
        return this.mList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.widget_filter_main, null);
            aVar = new a();
            aVar.a = (TextView) view.findViewById(R.id.name);
            aVar.b = (LinearLayout) view.findViewById(R.id.secondary);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        TrainTypeItem item = getItem(i);
        aVar.a.setText(item == null ? "" : item.b);
        switch (getItemViewType(i)) {
            case 0:
                aVar.b.setVisibility(8);
                break;
            case 1:
                aVar.b.setVisibility(0);
                break;
        }
        if (this.nSelectIndex == i) {
            view.setBackgroundResource(R.drawable.widget_filter_main_itembg_s);
            view.setPadding(0, 0, 0, 0);
        } else {
            view.setBackgroundResource(R.drawable.widget_filter_main_itembg_selector);
            view.setPadding(0, 0, 0, 0);
        }
        return view;
    }

    public void setSelection(int i) {
        this.nSelectIndex = i;
    }

    public int getSelection() {
        if (this.nSelectIndex < this.mList.size()) {
            return this.nSelectIndex;
        }
        return 0;
    }
}
