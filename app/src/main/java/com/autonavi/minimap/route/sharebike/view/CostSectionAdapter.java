package com.autonavi.minimap.route.sharebike.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.text.DecimalFormat;
import java.util.List;

public class CostSectionAdapter extends BaseAdapter {
    private List<egq> mCostSectionList;
    private LayoutInflater mInflater;
    private String mRmb = "元";

    static class a {
        public TextView a;
        public TextView b;
        public TextView c;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public CostSectionAdapter(Context context, List<egq> list) {
        this.mInflater = LayoutInflater.from(context);
        this.mCostSectionList = list;
    }

    public int getCount() {
        if (this.mCostSectionList != null) {
            return this.mCostSectionList.size();
        }
        return 0;
    }

    public egq getItem(int i) {
        if (this.mCostSectionList == null || i >= this.mCostSectionList.size() || i < 0) {
            return null;
        }
        return this.mCostSectionList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        a aVar;
        if (view == null) {
            aVar = new a(0);
            view2 = this.mInflater.inflate(R.layout.share_bike_pop_tip_cost_item, null);
            aVar.a = (TextView) view2.findViewById(R.id.share_bike_fee_bucket);
            aVar.b = (TextView) view2.findViewById(R.id.share_bike_fee_detail);
            aVar.c = (TextView) view2.findViewById(R.id.share_bike_fee_detail_amount);
            view2.setTag(aVar);
        } else {
            view2 = view;
            aVar = (a) view.getTag();
        }
        setData(i, aVar);
        return view2;
    }

    private void setData(int i, a aVar) {
        egq item = getItem(i);
        if (item != null) {
            if (!TextUtils.isEmpty(item.a)) {
                aVar.a.setText(item.a);
            }
            if (!TextUtils.isEmpty(item.b)) {
                String format = new DecimalFormat("0.00").format((double) (Float.parseFloat(item.b) / 100.0f));
                TextView textView = aVar.c;
                StringBuilder sb = new StringBuilder();
                sb.append(format);
                sb.append(this.mRmb);
                textView.setText(sb.toString());
            }
            if (!TextUtils.isEmpty(item.c)) {
                aVar.b.setText(item.c);
            } else {
                aVar.b.setVisibility(8);
                aVar.b.requestLayout();
            }
        }
    }
}
