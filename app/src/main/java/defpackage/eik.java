package defpackage;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.autonavi.map.template.AbstractViewHolderAdapter.a;
import com.autonavi.minimap.R;

/* renamed from: eik reason: default package */
/* compiled from: TrainTicketListViewHolder */
public final class eik extends a {
    public TextView a;
    public TextView b;
    public TextView c;
    public TextView d;
    public TextView e;
    public TextView f;
    public TextView g;
    public TextView h;
    public TextView i;
    public Button j;

    public eik(View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.train_name);
        this.b = (TextView) view.findViewById(R.id.train_seat);
        this.c = (TextView) view.findViewById(R.id.train_departure_time);
        this.d = (TextView) view.findViewById(R.id.train_departure_station_tv);
        this.e = (TextView) view.findViewById(R.id.train_arrival_time);
        this.f = (TextView) view.findViewById(R.id.train_arrival_station_tv);
        this.g = (TextView) view.findViewById(R.id.train_running_time);
        this.h = (TextView) view.findViewById(R.id.train_shortest_time_logo);
        this.i = (TextView) view.findViewById(R.id.train_buy_ticket_tv);
        this.j = (Button) view.findViewById(R.id.train_buy_ticket_btn);
    }
}
