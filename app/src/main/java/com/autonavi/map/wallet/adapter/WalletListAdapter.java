package com.autonavi.map.wallet.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.util.ArrayList;

@SuppressLint({"ResourceAsColor"})
public class WalletListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<cfp> mList;

    public static final class a {
        public TextView a;
        public TextView b;
        public TextView c;
        public TextView d;
        public TextView e;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public WalletListAdapter(Context context, ArrayList<cfp> arrayList) {
        this.mContext = context;
        this.mList = arrayList;
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
        View view2;
        a aVar;
        String str;
        if (view == null) {
            aVar = new a();
            view2 = LayoutInflater.from(this.mContext).inflate(R.layout.wallet_list_item, null);
            aVar.a = (TextView) view2.findViewById(R.id.title);
            aVar.b = (TextView) view2.findViewById(R.id.detail);
            aVar.c = (TextView) view2.findViewById(R.id.money);
            aVar.d = (TextView) view2.findViewById(R.id.time);
            aVar.e = (TextView) view2.findViewById(R.id.alert);
            view2.setTag(aVar);
        } else {
            view2 = view;
            aVar = (a) view.getTag();
        }
        cfp cfp = this.mList.get(i);
        String str2 = cfp.b;
        if (!TextUtils.isEmpty(str2)) {
            aVar.a.setText(str2);
        }
        String str3 = cfp.d;
        if (!TextUtils.isEmpty(str3)) {
            TextView textView = aVar.b;
            StringBuilder sb = new StringBuilder();
            sb.append(AMapAppGlobal.getApplication().getString(R.string.withdrawal_amount));
            sb.append(str3);
            textView.setText(sb.toString());
        }
        String str4 = cfp.c;
        if (!TextUtils.isEmpty(str4)) {
            if (str4.contains("-")) {
                str = str4.replace("-", "- ");
                aVar.c.setTextColor(Color.rgb(0, 122, 255));
            } else {
                str = "+ ".concat(String.valueOf(str4));
                aVar.c.setTextColor(Color.rgb(255, 77, 0));
            }
            aVar.c.setText(str);
        }
        String str5 = cfp.e;
        if (!TextUtils.isEmpty(str5)) {
            aVar.d.setText(str5);
        }
        if ("2".equals(cfp.a)) {
            String str6 = cfp.f;
            if (!TextUtils.isEmpty(str6)) {
                aVar.e.setVisibility(0);
                aVar.e.setText(Token.SEPARATOR.concat(String.valueOf(str6)));
            } else {
                aVar.e.setVisibility(8);
            }
        } else {
            aVar.e.setVisibility(8);
        }
        return view2;
    }

    public void setDataList(ArrayList<cfp> arrayList) {
        this.mList = arrayList;
    }
}
