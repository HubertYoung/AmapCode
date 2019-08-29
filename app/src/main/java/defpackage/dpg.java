package defpackage;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.autonavi.map.template.AbstractViewHolderAdapter.a;
import com.autonavi.minimap.R;

/* renamed from: dpg reason: default package */
/* compiled from: MarketDetailViewHolder */
public final class dpg extends a {
    public ImageView a;
    public TextView b;
    public ImageView c;
    public RatingBar d;
    public TextView e;
    public TextView f;
    public TextView g;

    public dpg(View view) {
        super(view);
        this.a = (ImageView) view.findViewById(R.id.image_view);
        this.b = (TextView) view.findViewById(R.id.poi_name);
        this.c = (ImageView) view.findViewById(R.id.groupbuy_tag);
        this.d = (RatingBar) view.findViewById(R.id.rating_bar);
        this.e = (TextView) view.findViewById(R.id.avgprice);
        this.g = (TextView) view.findViewById(R.id.tag);
        this.f = (TextView) view.findViewById(R.id.floor);
    }
}
