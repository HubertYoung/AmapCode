package com.autonavi.minimap.route.train.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.map.template.AbstractViewHolderBaseAdapter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.model.TrainTicketStationInfoItem;

public class TrainInfoListAdapter extends AbstractViewHolderBaseAdapter<TrainTicketStationInfoItem, eij> {
    private Context context;

    public TrainInfoListAdapter(Context context2) {
        this.context = context2;
    }

    public View onCreateView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(this.context).inflate(R.layout.train_info_list_item, viewGroup, false);
    }

    public eij onCreateViewHolder(View view, ViewGroup viewGroup, int i) {
        return new eij(view);
    }

    public void onBindViewHolderWithData(eij eij, TrainTicketStationInfoItem trainTicketStationInfoItem, int i, int i2) {
        int i3;
        eij.b.setText(trainTicketStationInfoItem.name.trim());
        if (i == 0) {
            eij.a.setText(R.string.train_info_start);
            eij.a.setBackgroundResource(R.drawable.train_station_start_logo_background);
            eij.a.setGravity(17);
            eij.e.setVisibility(8);
            eij.f.setVisibility(0);
            eij.c.setText(R.string.train_info_start_station);
            eij.d.setText(trainTicketStationInfoItem.departureTime);
        } else if (i == getCount() - 1) {
            eij.a.setText(R.string.train_info_end);
            eij.a.setBackgroundResource(R.drawable.train_station_end_logo_background);
            eij.a.setGravity(17);
            eij.e.setVisibility(8);
            eij.f.setVisibility(0);
            eij.c.setText(trainTicketStationInfoItem.arrivalTime);
            eij.d.setText(R.string.train_info_end_station);
        } else {
            eij.a.setText(String.valueOf(i + 1));
            eij.a.setBackgroundResource(R.drawable.train_station_middle_logo_background);
            eij.a.setGravity(17);
            String str = trainTicketStationInfoItem.arrivalTime;
            String str2 = trainTicketStationInfoItem.departureTime;
            String substring = str.substring(0, 2);
            try {
                i3 = Integer.parseInt(str2.substring(3, 5)) - Integer.parseInt(str.substring(3, 5));
            } catch (NumberFormatException unused) {
                i3 = 0;
            }
            if (!substring.equals(str2.substring(0, 2))) {
                i3 += 60;
            }
            if (i3 > 0) {
                eij.e.setVisibility(0);
                eij.f.setVisibility(8);
                StringBuilder sb = new StringBuilder();
                sb.append(i3);
                sb.append(this.context.getString(R.string.train_info_minite));
                eij.e.setText(sb.toString());
            } else {
                eij.e.setVisibility(8);
                eij.f.setVisibility(0);
            }
            eij.c.setText(trainTicketStationInfoItem.arrivalTime);
            eij.d.setText(trainTicketStationInfoItem.departureTime);
        }
    }
}
