package com.autonavi.map.wallet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class WalletBillListAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<cfn> mList;
    private final List<cfm> mSourceList;

    public static final class a {
        public TextView a;
        public TextView b;
        public TextView c;
        public TextView d;
        public ImageView e;
        public TextView f;
        public View g;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public WalletBillListAdapter(Context context, ArrayList<cfn> arrayList, List<cfm> list) {
        this.mContext = context;
        this.mList = arrayList;
        this.mSourceList = list;
    }

    public int getCount() {
        if (this.mList != null) {
            return this.mList.size();
        }
        return 0;
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        View view2;
        if (view == null) {
            aVar = new a();
            view2 = LayoutInflater.from(this.mContext).inflate(R.layout.wallet_bill_list_item, null);
            aVar.a = (TextView) view2.findViewById(R.id.source);
            aVar.b = (TextView) view2.findViewById(R.id.money);
            aVar.c = (TextView) view2.findViewById(R.id.time);
            aVar.d = (TextView) view2.findViewById(R.id.status);
            aVar.e = (ImageView) view2.findViewById(R.id.icon);
            aVar.f = (TextView) view2.findViewById(R.id.month);
            aVar.g = view2.findViewById(R.id.month_line);
            view2.setTag(aVar);
        } else {
            view2 = view;
            aVar = (a) view.getTag();
        }
        cfn cfn = this.mList.get(i);
        aVar.a.setText(cfn.d);
        aVar.b.setText(Double.valueOf(cfn.e).toString());
        aVar.c.setText(cfn.b.toString());
        aVar.d.setText(cfn.g);
        aVar.c.setText(cfn.b);
        if (TextUtils.isEmpty(cfn.h)) {
            aVar.f.setVisibility(8);
            aVar.g.setVisibility(8);
        } else {
            aVar.f.setVisibility(0);
            aVar.g.setVisibility(0);
            aVar.f.setText(cfn.h);
        }
        if (cfn.f == 4 || cfn.f == 6) {
            aVar.d.setTextColor(Color.rgb(211, 78, 59));
        } else if (cfn.f == 5) {
            aVar.d.setTextColor(Color.rgb(232, 232, 232));
        } else if (cfn.f == 1 || cfn.f == 2 || cfn.f == 3) {
            aVar.d.setTextColor(Color.rgb(0, 145, 255));
        } else {
            aVar.d.setTextColor(-9079435);
        }
        if (cfn.c < this.mSourceList.size()) {
            ko.a(aVar.e, this.mSourceList.get(cfn.c).c, null, R.drawable.wallet_bill_icon);
        }
        return view2;
    }
}
