package com.autonavi.minimap.route.coach.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import java.util.Locale;

public class CoachOrderAdapter extends AbstractViewHolderBaseAdapter<eit, a> {
    private Context mContext;

    public static class a extends com.autonavi.map.template.AbstractViewHolderAdapter.a {
        public TextView a;
        public TextView b;
        public TextView c;
        public TextView d;
        public TextView e;

        public a(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.coach_depart_station);
            this.b = (TextView) view.findViewById(R.id.coach_order_status);
            this.c = (TextView) view.findViewById(R.id.coach_depart_date);
            this.d = (TextView) view.findViewById(R.id.coach_depart_tips);
            this.e = (TextView) view.findViewById(R.id.coach_order_ticket_count);
        }
    }

    public CoachOrderAdapter(Context context) {
        this.mContext = context;
    }

    public void onBindViewHolderWithData(a aVar, eit eit, int i, int i2) {
        int i3;
        String str;
        if (!(aVar == null || eit == null || !(eit instanceof dzc))) {
            dzc dzc = (dzc) eit;
            TextView textView = aVar.a;
            StringBuilder sb = new StringBuilder();
            if (TextUtils.isEmpty(dzc.f) || dzc.f.toLowerCase(Locale.getDefault()).contains("null")) {
                sb.append(eay.a(R.string.life_order_train_unkown));
            } else {
                sb.append(dzc.f);
            }
            sb.append('-');
            if (TextUtils.isEmpty(dzc.g) || dzc.g.toLowerCase(Locale.getDefault()).contains("null")) {
                sb.append(eay.a(R.string.life_order_train_unkown));
            } else {
                sb.append(dzc.g);
            }
            textView.setText(sb.toString());
            TextView textView2 = aVar.b;
            if (TextUtils.isEmpty(dzc.i) || dzc.i.toLowerCase(Locale.getDefault()).contains("null")) {
                switch (dzc.l) {
                    case -6:
                        dzc.i = eay.a(R.string.order_status_refund_fail);
                        break;
                    case -5:
                        dzc.i = eay.a(R.string.order_status_order_close);
                        break;
                    case -4:
                        dzc.i = eay.a(R.string.order_status_order_cancel);
                        break;
                    case -3:
                        dzc.i = eay.a(R.string.order_status_ticket_print_out_of_time);
                        break;
                    case -2:
                        dzc.i = eay.a(R.string.order_status_pay_out_of_time);
                        break;
                    case -1:
                        dzc.i = eay.a(R.string.order_status_ticket_print_fail);
                        break;
                    case 0:
                        dzc.i = eay.a(R.string.order_status_querying);
                        break;
                    case 1:
                        dzc.i = eay.a(R.string.order_status_unpaid);
                        break;
                    case 2:
                        dzc.i = eay.a(R.string.order_status_paid);
                        break;
                    case 3:
                        dzc.i = eay.a(R.string.order_status_processing);
                        break;
                    case 4:
                        dzc.i = eay.a(R.string.order_status_ticket_print);
                        break;
                    case 5:
                        dzc.i = eay.a(R.string.order_status_ticket_refund);
                        break;
                    default:
                        dzc.i = eay.a(R.string.life_order_train_unkown);
                        break;
                }
            }
            textView2.setText(dzc.i);
            TextView textView3 = aVar.b;
            switch (dzc.l) {
                case 0:
                case 1:
                case 2:
                case 3:
                    i3 = R.drawable.shape_round_corner_background_orange;
                    break;
                case 4:
                    i3 = R.drawable.shape_round_corner_background_green;
                    break;
                default:
                    i3 = R.drawable.shape_round_corner_background_gray;
                    break;
            }
            textView3.setBackgroundResource(i3);
            TextView textView4 = aVar.c;
            if (TextUtils.isEmpty(dzc.c) || dzc.c.toLowerCase(Locale.getDefault()).contains("null")) {
                str = eay.a(R.string.life_order_train_unkown);
            } else {
                str = dzc.c;
            }
            textView4.setText(str);
            aVar.d.setText(R.string.life_order_train_start);
            if (TextUtils.isEmpty(dzc.j) || dzc.j.toLowerCase(Locale.getDefault()).contains("null")) {
                aVar.e.setText(R.string.life_order_train_unkown);
            } else {
                aVar.e.setText(this.mContext.getString(R.string.life_order_train_ticket_num, new Object[]{dzc.j}));
            }
        }
    }

    public View onCreateView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(this.mContext).inflate(R.layout.coach_order_list_item, viewGroup, false);
    }

    public a onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
        return new a(view);
    }
}
