package defpackage;

import android.view.View;
import android.widget.TextView;
import com.autonavi.map.template.AbstractViewHolderAdapter.a;
import com.autonavi.minimap.R;

/* renamed from: eij reason: default package */
/* compiled from: TrainInfoListViewHolder */
public final class eij extends a {
    public TextView a;
    public TextView b;
    public TextView c;
    public TextView d;
    public TextView e;
    public View f;

    public eij(View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.station_no);
        this.b = (TextView) view.findViewById(R.id.station_name);
        this.c = (TextView) view.findViewById(R.id.arrival_time);
        this.d = (TextView) view.findViewById(R.id.departure_time);
        this.e = (TextView) view.findViewById(R.id.saty_time);
        this.f = view.findViewById(R.id.saty_time_line);
        this.f.setLayerType(1, null);
    }
}
