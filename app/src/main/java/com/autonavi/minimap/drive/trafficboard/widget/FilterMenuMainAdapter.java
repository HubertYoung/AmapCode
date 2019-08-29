package com.autonavi.minimap.drive.trafficboard.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.bundle.entity.infolite.internal.Condition;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class FilterMenuMainAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Condition> mList;
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

    public int getViewTypeCount() {
        return 2;
    }

    public FilterMenuMainAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public void setData(ArrayList<Condition> arrayList) {
        this.mList = arrayList;
        this.nSelectIndex = -1;
    }

    public void addItem(Condition condition) {
        this.mList.add(condition);
    }

    public int getCount() {
        if (this.mList != null) {
            return this.mList.size();
        }
        return 0;
    }

    public Condition getItem(int i) {
        if (i < 0 || i >= this.mList.size()) {
            return null;
        }
        return this.mList.get(i);
    }

    public int getItemViewType(int i) {
        Condition condition = this.mList.get(i);
        return (condition.subConditions == null || condition.subConditions.size() == 0) ? 0 : 1;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.operation_filter_main, null);
            aVar = new a();
            aVar.a = (TextView) view.findViewById(R.id.name);
            aVar.b = (LinearLayout) view.findViewById(R.id.secondary);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        Condition item = getItem(i);
        if (item != null) {
            aVar.a.setText(!TextUtils.isEmpty(item.displayName) ? item.displayName : item.name);
        }
        switch (getItemViewType(i)) {
            case 0:
                aVar.b.setVisibility(8);
                break;
            case 1:
                aVar.b.setVisibility(0);
                break;
        }
        if (this.nSelectIndex == i) {
            view.setBackgroundResource(R.drawable.tag_filter_main_itembg_s);
            view.setPadding(0, 0, 0, 0);
        } else {
            view.setBackgroundResource(R.drawable.tag_filter_main_itembg_selector);
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
