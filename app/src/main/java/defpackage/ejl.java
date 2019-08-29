package defpackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.adapter.TrainInfoListAdapter;
import com.autonavi.minimap.route.train.page.TrainInfoPage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: ejl reason: default package */
/* compiled from: TrainInfoPresenter */
public final class ejl extends eaf<TrainInfoPage> implements OnClickListener {
    public ejl(TrainInfoPage trainInfoPage) {
        super(trainInfoPage);
    }

    public final void onPageCreated() {
        int i;
        int i2;
        int i3;
        int i4;
        super.onPageCreated();
        TrainInfoPage trainInfoPage = (TrainInfoPage) this.mPage;
        View contentView = trainInfoPage.getContentView();
        trainInfoPage.d = (ImageButton) contentView.findViewById(R.id.title_btn_left);
        trainInfoPage.d.setOnClickListener((OnClickListener) trainInfoPage.mPresenter);
        trainInfoPage.c = (TextView) contentView.findViewById(R.id.title_text_name);
        trainInfoPage.c.setTextColor(trainInfoPage.getContext().getResources().getColor(R.color.blue));
        trainInfoPage.e = LayoutInflater.from(trainInfoPage.getActivity()).inflate(R.layout.train_info_list_header, null);
        trainInfoPage.f = (ListView) contentView.findViewById(R.id.list);
        trainInfoPage.f.addHeaderView(trainInfoPage.e, null, false);
        PageBundle arguments = trainInfoPage.getArguments();
        if (arguments != null) {
            trainInfoPage.a = (ejd) arguments.getObject("trainResponser");
            trainInfoPage.c.setText(trainInfoPage.a.a);
            ((TextView) trainInfoPage.e.findViewById(R.id.start_station)).setText(trainInfoPage.a.f);
            ((TextView) trainInfoPage.e.findViewById(R.id.start_time)).setText(trainInfoPage.a.d);
            ((TextView) trainInfoPage.e.findViewById(R.id.end_station)).setText(trainInfoPage.a.g);
            ((TextView) trainInfoPage.e.findViewById(R.id.end_time)).setText(trainInfoPage.a.e);
            ((TextView) trainInfoPage.e.findViewById(R.id.distance)).setText(trainInfoPage.a.b);
            TextView textView = (TextView) trainInfoPage.e.findViewById(R.id.running_time);
            String str = trainInfoPage.a.c;
            int indexOf = str.indexOf(":");
            String str2 = "";
            String str3 = "";
            if (indexOf > 0) {
                str2 = str.substring(0, indexOf);
                str3 = str.substring(indexOf + 1);
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append("时");
                sb.append(str3);
                sb.append("分");
                str = sb.toString();
            }
            textView.setText(str);
            TextView textView2 = (TextView) trainInfoPage.e.findViewById(R.id.start_date);
            TextView textView3 = (TextView) trainInfoPage.e.findViewById(R.id.end_date);
            String[] strArr = {"", "日", "一", "二", "三", "四", "五", "六"};
            Calendar instance = Calendar.getInstance();
            try {
                i = Integer.parseInt(trainInfoPage.a.d.substring(0, 2));
            } catch (NumberFormatException unused) {
                i = 0;
            }
            instance.set(11, i);
            try {
                i2 = Integer.parseInt(trainInfoPage.a.d.substring(3));
            } catch (NumberFormatException unused2) {
                i2 = 0;
            }
            instance.set(12, i2);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(instance.get(2) + 1);
            sb2.append("-");
            sb2.append(instance.get(5));
            sb2.append(" 周");
            sb2.append(strArr[instance.get(7)]);
            textView2.setText(sb2.toString());
            try {
                i3 = Integer.parseInt(str2);
            } catch (NumberFormatException unused3) {
                i3 = 0;
            }
            instance.add(10, i3);
            try {
                i4 = Integer.parseInt(str3);
            } catch (NumberFormatException unused4) {
                i4 = 0;
            }
            instance.add(12, i4);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(instance.get(2) + 1);
            sb3.append("-");
            sb3.append(instance.get(5));
            sb3.append(" 周");
            sb3.append(strArr[instance.get(7)]);
            textView3.setText(sb3.toString());
            Map<String, String> map = trainInfoPage.b;
            if (map != null) {
                map.clear();
                map.put("seat_rwgj_s", eay.a(R.string.tt_seat_type_super_soft_couch_up));
                map.put("seat_rwgj_x", eay.a(R.string.tt_seat_type_super_soft_couch_down));
                map.put("seat_rw_s", eay.a(R.string.tt_seat_type_soft_couch_up));
                map.put("seat_rw_x", eay.a(R.string.tt_seat_type_soft_couch_down));
                map.put("seat_yw_s", eay.a(R.string.tt_seat_type_hard_couch_up));
                map.put("seat_yw_z", eay.a(R.string.tt_seat_type_hard_couch_middle));
                map.put("seat_yw_x", eay.a(R.string.tt_seat_type_hard_couch_down));
                map.put("seat_rz_t", eay.a(R.string.tt_seat_type_special_soft_seat));
                map.put("seat_rz_1", eay.a(R.string.tt_seat_type_soft_seat_level_1));
                map.put("seat_rz_2", eay.a(R.string.tt_seat_type_soft_seat_level_2));
                map.put("seat_sw", eay.a(R.string.tt_seat_type_business_seat));
                map.put("seat_1", eay.a(R.string.tt_seat_type_common_seat_level_1));
                map.put("seat_2", eay.a(R.string.tt_seat_type_common_seat_level_2));
                map.put("seat_yz", eay.a(R.string.tt_seat_type_hard_seat));
                map.put("seat_rz", eay.a(R.string.tt_seat_type_soft_seat));
                map.put("seat_t", eay.a(R.string.tt_seat_type_special_seat));
                map.put("seat_gg", eay.a(R.string.tt_seat_type_tourism_seat));
                map.put("seat_rb", eay.a(R.string.tt_seat_type_for_one_person_only));
                map.put("seat_dw", eay.a(R.string.tt_seat_type_D_train_couch));
            }
            LinearLayout linearLayout = (LinearLayout) trainInfoPage.e.findViewById(R.id.seat_1);
            TextView textView4 = (TextView) trainInfoPage.e.findViewById(R.id.seat_price_1);
            TextView textView5 = (TextView) trainInfoPage.e.findViewById(R.id.seat_type_1);
            LinearLayout linearLayout2 = (LinearLayout) trainInfoPage.e.findViewById(R.id.seat_2);
            TextView textView6 = (TextView) trainInfoPage.e.findViewById(R.id.seat_price_2);
            TextView textView7 = (TextView) trainInfoPage.e.findViewById(R.id.seat_type_2);
            LinearLayout linearLayout3 = (LinearLayout) trainInfoPage.e.findViewById(R.id.seat_3);
            TextView textView8 = (TextView) trainInfoPage.e.findViewById(R.id.seat_price_3);
            LinearLayout linearLayout4 = (LinearLayout) trainInfoPage.e.findViewById(R.id.seat_4);
            View findViewById = trainInfoPage.e.findViewById(R.id.seat_divider_1);
            View findViewById2 = trainInfoPage.e.findViewById(R.id.seat_divider_2);
            TextView textView9 = (TextView) trainInfoPage.e.findViewById(R.id.seat_type_4);
            View findViewById3 = trainInfoPage.e.findViewById(R.id.seat_divider_3);
            TextView textView10 = (TextView) trainInfoPage.e.findViewById(R.id.seat_price_4);
            Map map2 = trainInfoPage.a.h.get(trainInfoPage.a.h.size() - 1).map;
            int size = map2.size();
            TextView textView11 = (TextView) trainInfoPage.e.findViewById(R.id.seat_type_3);
            ArrayList arrayList = new ArrayList(map2.entrySet());
            Collections.sort(arrayList, trainInfoPage.h);
            if (size == 0) {
                linearLayout.setVisibility(8);
                linearLayout2.setVisibility(8);
                linearLayout3.setVisibility(8);
                linearLayout4.setVisibility(8);
                findViewById.setVisibility(8);
                findViewById2.setVisibility(8);
                findViewById3.setVisibility(8);
            } else if (size == 1) {
                linearLayout.setVisibility(0);
                linearLayout2.setVisibility(8);
                linearLayout3.setVisibility(8);
                linearLayout4.setVisibility(8);
                findViewById.setVisibility(8);
                findViewById2.setVisibility(8);
                findViewById3.setVisibility(8);
                String[] split = ((Entry) arrayList.get(0)).toString().split("=");
                textView4.setText(TrainInfoPage.a(split[1]));
                textView5.setText(trainInfoPage.b.get(split[0]));
            } else {
                TextView textView12 = textView8;
                if (size == 2) {
                    linearLayout.setVisibility(0);
                    linearLayout2.setVisibility(0);
                    linearLayout3.setVisibility(8);
                    linearLayout4.setVisibility(8);
                    findViewById.setVisibility(0);
                    findViewById2.setVisibility(8);
                    findViewById3.setVisibility(8);
                    String[] split2 = ((Entry) arrayList.get(0)).toString().split("=");
                    textView4.setText(TrainInfoPage.a(split2[1]));
                    textView5.setText(trainInfoPage.b.get(split2[0]));
                    String[] split3 = ((Entry) arrayList.get(1)).toString().split("=");
                    textView6.setText(TrainInfoPage.a(split3[1]));
                    textView7.setText(trainInfoPage.b.get(split3[0]));
                } else if (size == 3) {
                    linearLayout.setVisibility(0);
                    linearLayout2.setVisibility(0);
                    linearLayout3.setVisibility(0);
                    linearLayout4.setVisibility(8);
                    findViewById.setVisibility(0);
                    findViewById2.setVisibility(0);
                    findViewById3.setVisibility(8);
                    String[] split4 = ((Entry) arrayList.get(0)).toString().split("=");
                    textView4.setText(TrainInfoPage.a(split4[1]));
                    textView5.setText(trainInfoPage.b.get(split4[0]));
                    String[] split5 = ((Entry) arrayList.get(1)).toString().split("=");
                    textView6.setText(TrainInfoPage.a(split5[1]));
                    textView7.setText(trainInfoPage.b.get(split5[0]));
                    String[] split6 = ((Entry) arrayList.get(2)).toString().split("=");
                    textView12.setText(TrainInfoPage.a(split6[1]));
                    textView11.setText(trainInfoPage.b.get(split6[0]));
                } else {
                    TextView textView13 = textView11;
                    TextView textView14 = textView12;
                    if (size >= 4) {
                        linearLayout.setVisibility(0);
                        linearLayout2.setVisibility(0);
                        linearLayout3.setVisibility(0);
                        linearLayout4.setVisibility(0);
                        findViewById.setVisibility(0);
                        findViewById2.setVisibility(0);
                        findViewById3.setVisibility(0);
                        String[] split7 = ((Entry) arrayList.get(0)).toString().split("=");
                        textView4.setText(TrainInfoPage.a(split7[1]));
                        textView5.setText(trainInfoPage.b.get(split7[0]));
                        String[] split8 = ((Entry) arrayList.get(1)).toString().split("=");
                        textView6.setText(TrainInfoPage.a(split8[1]));
                        textView7.setText(trainInfoPage.b.get(split8[0]));
                        String[] split9 = ((Entry) arrayList.get(2)).toString().split("=");
                        textView14.setText(TrainInfoPage.a(split9[1]));
                        textView13.setText(trainInfoPage.b.get(split9[0]));
                        String[] split10 = ((Entry) arrayList.get(3)).toString().split("=");
                        textView10.setText(TrainInfoPage.a(split10[1]));
                        textView9.setText(trainInfoPage.b.get(split10[0]));
                    }
                }
            }
            trainInfoPage.g = new TrainInfoListAdapter(trainInfoPage.getContext());
            trainInfoPage.f.setAdapter(trainInfoPage.g);
            trainInfoPage.g.setDataAndChangeDataSet(trainInfoPage.a.h);
        }
    }

    public final void onClick(View view) {
        if (view.getId() == R.id.title_btn_left) {
            ((TrainInfoPage) this.mPage).finish();
        }
    }

    public final void onStart() {
        super.onStart();
    }
}
