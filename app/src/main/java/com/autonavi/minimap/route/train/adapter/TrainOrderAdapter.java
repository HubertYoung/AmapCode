package com.autonavi.minimap.route.train.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import java.util.Locale;

public class TrainOrderAdapter extends AbstractViewHolderBaseAdapter<eit, a> {
    private Context mContext;

    public static class a extends com.autonavi.map.template.AbstractViewHolderAdapter.a {
        public TextView a;
        public TextView b;
        public TextView c;
        public TextView d;
        public TextView e;
        public TextView f;

        public a(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.train_date);
            this.b = (TextView) view.findViewById(R.id.train_number);
            this.c = (TextView) view.findViewById(R.id.train_city);
            this.d = (TextView) view.findViewById(R.id.train_rank);
            this.e = (TextView) view.findViewById(R.id.train_status);
            this.f = (TextView) view.findViewById(R.id.ticket_num);
        }
    }

    public TrainOrderAdapter(Context context) {
        this.mContext = context;
    }

    public void onBindViewHolderWithData(a aVar, eit eit, int i, int i2) {
        int i3;
        String str;
        if (!(aVar == null || eit == null || !(eit instanceof eiu))) {
            eiu eiu = (eiu) eit;
            if (TextUtils.isEmpty(eiu.e) || TextUtils.isEmpty(eiu.f) || eiu.e.toLowerCase(Locale.getDefault()).contains("null") || eiu.f.toLowerCase(Locale.getDefault()).contains("null")) {
                aVar.e.setText(R.string.life_order_train_unkown);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(eiu.e);
                sb.append("-");
                sb.append(eiu.f);
                aVar.c.setText(sb.toString());
            }
            if (TextUtils.isEmpty(eiu.a)) {
                aVar.a.setText(R.string.life_order_train_unkown);
            } else {
                aVar.a.setText(eiu.a);
            }
            if (TextUtils.isEmpty(eiu.b)) {
                aVar.b.setText(R.string.life_order_train_unkown);
            } else {
                aVar.b.setText(eiu.b);
            }
            if (TextUtils.isEmpty(eiu.b)) {
                aVar.f.setText(R.string.life_order_train_unkown);
            } else {
                aVar.f.setText(this.mContext.getString(R.string.life_order_train_ticket_num, new Object[]{eiu.c}));
            }
            TextView textView = aVar.d;
            switch (eiu.d) {
                case 1:
                    i3 = R.string.life_order_train_seat_1;
                    break;
                case 2:
                    i3 = R.string.life_order_train_seat_2;
                    break;
                case 3:
                    i3 = R.string.life_order_train_seat_3;
                    break;
                case 4:
                    i3 = R.string.life_order_train_seat_4;
                    break;
                case 5:
                    i3 = R.string.life_order_train_seat_5;
                    break;
                case 6:
                    i3 = R.string.life_order_train_seat_6;
                    break;
                case 7:
                    i3 = R.string.life_order_train_seat_7;
                    break;
                case 8:
                    i3 = R.string.life_order_train_seat_8;
                    break;
                case 9:
                    i3 = R.string.life_order_train_seat_9;
                    break;
                case 10:
                    i3 = R.string.life_order_train_seat_10;
                    break;
                case 11:
                    i3 = R.string.life_order_train_seat_11;
                    break;
                case 12:
                    i3 = R.string.life_order_train_seat_12;
                    break;
                case 13:
                    i3 = R.string.life_order_train_seat_13;
                    break;
                case 14:
                    i3 = R.string.life_order_train_seat_14;
                    break;
                default:
                    i3 = R.string.life_order_train_seat_0;
                    break;
            }
            textView.setText(i3);
            if (TextUtils.isEmpty(eiu.h)) {
                str = "";
            } else {
                str = eiu.h;
            }
            if (TextUtils.isEmpty(str)) {
                aVar.e.setVisibility(8);
                aVar.e.setText("");
            } else {
                aVar.e.setVisibility(0);
                aVar.e.setText(str);
            }
            if (eiu.g == 5) {
                aVar.e.setBackgroundResource(R.drawable.shape_round_corner_background_gray);
                return;
            }
            aVar.e.setBackgroundResource(R.drawable.shape_round_corner_background_orange);
        }
    }

    public View onCreateView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(this.mContext).inflate(R.layout.order_train_list_item, viewGroup, false);
    }

    public a onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
        return new a(view);
    }
}
