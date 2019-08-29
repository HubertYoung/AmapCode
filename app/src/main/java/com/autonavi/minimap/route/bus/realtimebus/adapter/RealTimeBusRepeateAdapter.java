package com.autonavi.minimap.route.bus.realtimebus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.IPage;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class RealTimeBusRepeateAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mDayList = new ArrayList<>();
    private ArrayList<Integer> mPositionList = new ArrayList<>();

    static class a {
        TextView a;
        ImageView b;
        View c;
        View d;

        a() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public RealTimeBusRepeateAdapter(IPage iPage, ArrayList<String> arrayList) {
        this.mContext = iPage.getContext();
        this.mDayList = arrayList;
    }

    public int getCount() {
        return this.mDayList.size();
    }

    public Object getItem(int i) {
        return this.mDayList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        a aVar;
        if (view == null) {
            aVar = new a();
            view2 = LayoutInflater.from(this.mContext).inflate(R.layout.busline_attention_setting_repeate_item, viewGroup, false);
            aVar.b = (ImageView) view2.findViewById(R.id.iv_checkoutyes);
            aVar.a = (TextView) view2.findViewById(R.id.tv_day);
            aVar.c = view2.findViewById(R.id.view_line_parent);
            aVar.d = view2.findViewById(R.id.view_line);
            view2.setTag(aVar);
        } else {
            view2 = view;
            aVar = (a) view.getTag();
        }
        aVar.a.setText(this.mDayList.get(i));
        if (this.mPositionList.toString().contains(String.valueOf(i))) {
            aVar.b.setVisibility(0);
        } else {
            aVar.b.setVisibility(4);
        }
        if (i == this.mDayList.size() - 1) {
            aVar.c.setVisibility(0);
            aVar.d.setVisibility(8);
        }
        return view2;
    }

    public void setPositionList(ArrayList<Integer> arrayList) {
        this.mPositionList = arrayList;
    }
}
