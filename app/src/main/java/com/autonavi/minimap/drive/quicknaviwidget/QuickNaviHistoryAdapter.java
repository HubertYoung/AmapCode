package com.autonavi.minimap.drive.quicknaviwidget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.drivecommon.view.ListViewWithHeaderAdapter;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class QuickNaviHistoryAdapter extends ListViewWithHeaderAdapter {
    private LayoutInflater mLayoutInflater;
    private List<sj> mQuickNaviHistoryArray = new ArrayList();

    static class a {
        TextView a;
        TextView b;
        View c;
        View d;

        a() {
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public QuickNaviHistoryAdapter(Context context) {
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public void setHistoryQuickNaviList(List<sj> list) {
        if (list != null) {
            this.mQuickNaviHistoryArray = list;
        } else {
            this.mQuickNaviHistoryArray = new ArrayList();
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mQuickNaviHistoryArray.size();
    }

    public sj getItem(int i) {
        if (this.mQuickNaviHistoryArray == null || this.mQuickNaviHistoryArray.size() == 0 || i >= this.mQuickNaviHistoryArray.size()) {
            return null;
        }
        return this.mQuickNaviHistoryArray.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        String str;
        if (view == null) {
            view = this.mLayoutInflater.inflate(R.layout.drive_v4_fromto_history_lisview_item, null);
            aVar = new a();
            aVar.b = (TextView) view.findViewById(R.id.addr);
            aVar.a = (TextView) view.findViewById(R.id.poiName);
            aVar.c = view.findViewById(R.id.his_diver);
            aVar.d = view.findViewById(R.id.his_line);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        if (i < this.mQuickNaviHistoryArray.size() - 1) {
            aVar.c.setVisibility(8);
            aVar.d.setVisibility(0);
        } else {
            aVar.c.setVisibility(0);
            aVar.d.setVisibility(8);
        }
        sj item = getItem(i);
        if (item != null) {
            TextView textView = aVar.a;
            StringBuilder sb = new StringBuilder();
            POI a2 = item.a();
            if (a2 == null) {
                str = "";
            } else if (!TextUtils.isEmpty(a2.getName())) {
                str = a2.getName();
            } else {
                str = ((FavoritePOI) a2.as(FavoritePOI.class)).getCustomName();
            }
            sb.append(str);
            textView.setText(sb.toString());
            if (!TextUtils.isEmpty(item.b())) {
                aVar.b.setText(item.b());
                aVar.b.setVisibility(0);
            } else {
                aVar.b.setVisibility(8);
            }
        }
        return view;
    }
}
