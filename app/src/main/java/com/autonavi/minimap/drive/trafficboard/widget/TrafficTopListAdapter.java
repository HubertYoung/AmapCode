package com.autonavi.minimap.drive.trafficboard.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class TrafficTopListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<c> mList;

    static final class a {
        TextView a;
        View b;
        TextView c;
        TextView d;
        TextView e;
        TextView f;
        TextView g;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public TrafficTopListAdapter(Context context, ArrayList<c> arrayList) {
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
        return this.mList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        a aVar;
        if (view == null) {
            aVar = new a(0);
            view2 = LayoutInflater.from(this.mContext).inflate(R.layout.traffic_top_list, null);
            aVar.a = (TextView) view2.findViewById(R.id.traffic_top_list_number);
            aVar.b = view2.findViewById(R.id.traffic_top_list_number_layout);
            aVar.c = (TextView) view2.findViewById(R.id.traffic_top_list_name);
            aVar.f = (TextView) view2.findViewById(R.id.traffic_top_list_jam);
            aVar.g = (TextView) view2.findViewById(R.id.traffic_top_list_speed);
            aVar.d = (TextView) view2.findViewById(R.id.traffic_top_list_status);
            aVar.e = (TextView) view2.findViewById(R.id.traffic_top_list_description);
            view2.setTag(aVar);
        } else {
            view2 = view;
            aVar = (a) view.getTag();
        }
        c cVar = this.mList.get(i);
        aVar.e.setText(cVar.c);
        setName(view2, aVar.c, aVar.d, cVar.b);
        setNumberTv(aVar.a, aVar.b, i);
        setStatus(aVar.d, cVar);
        setAbout(aVar.f, aVar.g, cVar);
        return view2;
    }

    private void setName(View view, TextView textView, TextView textView2, String str) {
        textView.setText(str);
        textView.setTextColor(textView.getResources().getColor(R.color.f_c_2));
    }

    private void setAbout(TextView textView, TextView textView2, c cVar) {
        StringBuffer stringBuffer = new StringBuffer(AMapPageUtil.getAppContext().getString(R.string.traffic_board_congestion));
        stringBuffer.append(cVar.f);
        stringBuffer.append(Token.SEPARATOR);
        stringBuffer.append(AMapPageUtil.getAppContext().getString(R.string.traffic_board_kilometres));
        StringBuffer stringBuffer2 = new StringBuffer(AMapPageUtil.getAppContext().getString(R.string.traffic_board_speed));
        stringBuffer2.append(cVar.i);
        stringBuffer2.append(Token.SEPARATOR);
        stringBuffer2.append(AMapPageUtil.getAppContext().getString(R.string.traffic_board_speed_unit));
        textView.setText(stringBuffer.toString());
        textView2.setText(stringBuffer2.toString());
    }

    private void setNumberTv(TextView textView, View view, int i) {
        if (textView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(i + 1);
            textView.setText(sb.toString());
            if (i < 3) {
                view.setBackgroundResource(R.drawable.red);
                return;
            }
            view.setBackgroundResource(R.drawable.gary);
        }
    }

    private void setStatus(TextView textView, c cVar) {
        if (textView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(cVar.d);
            String sb2 = sb.toString();
            if (AMapPageUtil.getAppContext().getString(R.string.traffic_board_unblocked).equals(cVar.e)) {
                textView.setTextColor(this.mContext.getResources().getColor(R.color.traffic_list_status_green));
                textView.setBackgroundResource(R.drawable.traffic_changtong);
            } else if (AMapPageUtil.getAppContext().getString(R.string.traffic_board_slow).equals(cVar.e)) {
                textView.setTextColor(this.mContext.getResources().getColor(R.color.v6_orange));
                textView.setBackgroundResource(R.drawable.traffic_huanxing);
            } else if (AMapPageUtil.getAppContext().getString(R.string.traffic_board_jam).equals(cVar.e)) {
                textView.setTextColor(this.mContext.getResources().getColor(R.color.traffic_list_status_pink));
                textView.setBackgroundResource(R.drawable.traffic_yongdu);
            } else {
                if (10.0d < cVar.d) {
                    sb2 = String.valueOf((int) cVar.d);
                }
                textView.setTextColor(this.mContext.getResources().getColor(R.color.traffic_list_status_red));
                textView.setBackgroundResource(R.drawable.traffic_yanzhong);
            }
            textView.setText(String.valueOf(sb2));
        }
    }
}
