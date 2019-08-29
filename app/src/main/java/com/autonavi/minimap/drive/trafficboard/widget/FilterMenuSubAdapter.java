package com.autonavi.minimap.drive.trafficboard.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.bundle.entity.infolite.internal.Condition;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class FilterMenuSubAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Condition> mList;
    private int nSelectIndex = -1;

    static class a {
        public TextView a;
        public TextView b;
        public ImageView c;

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

    public FilterMenuSubAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void setData(ArrayList<Condition> arrayList) {
        this.mList = arrayList;
        this.nSelectIndex = -1;
    }

    public void addItem(Condition condition) {
        if (this.mList == null) {
            this.mList = new ArrayList<>();
        }
        this.mList.add(condition);
    }

    public int getCount() {
        if (this.mList == null) {
            return 0;
        }
        return this.mList.size();
    }

    public Condition getItem(int i) {
        if (this.mList != null && i >= 0 && i < this.mList.size()) {
            return this.mList.get(i);
        }
        return null;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.operation_filter_sub, null);
            aVar = new a();
            aVar.a = (TextView) view.findViewById(R.id.name);
            aVar.b = (TextView) view.findViewById(R.id.count);
            aVar.c = (ImageView) view.findViewById(R.id.ivSelectedIcon);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        Condition item = getItem(i);
        if (item == null) {
            return view;
        }
        aVar.a.setText(TextUtils.isEmpty(item.displayName) ? item.name : item.displayName);
        aVar.b.setVisibility(8);
        if (this.nSelectIndex == i) {
            aVar.c.setVisibility(0);
            view.setPadding(0, 0, 0, 0);
        } else {
            aVar.c.setVisibility(8);
            view.setBackgroundResource(R.drawable.groupbuy_filter_sub_itembg_selector);
            view.setPadding(0, 0, 0, 0);
        }
        return view;
    }

    public void setSelection(int i) {
        this.nSelectIndex = i;
    }

    public int getSelection() {
        return this.nSelectIndex;
    }
}
