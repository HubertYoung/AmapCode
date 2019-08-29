package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.autonavi.bundle.life.api.entity.ScenicGuideListItemEntity;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bbe reason: default package */
/* compiled from: ScenicGuideAdapter */
public final class bbe extends Adapter<ViewHolder> {
    public a a;
    public int b = -1;
    private Resources c;
    /* access modifiers changed from: private */
    public List<ScenicGuideListItemEntity> d = new ArrayList();

    /* renamed from: bbe$a */
    /* compiled from: ScenicGuideAdapter */
    public interface a {
        void a(int i);
    }

    /* renamed from: bbe$b */
    /* compiled from: ScenicGuideAdapter */
    class b extends ViewHolder {
        /* access modifiers changed from: private */
        public TextView b;
        /* access modifiers changed from: private */
        public ImageView c;
        /* access modifiers changed from: private */
        public View d;

        public b(View view) {
            super(view);
            this.b = (TextView) view.findViewById(R.id.scenic_guide_item_name);
            this.c = (ImageView) view.findViewById(R.id.scenic_guide_item_icon);
            this.d = view.findViewById(R.id.scenic_guide_item_divider);
        }
    }

    public bbe(@NonNull Context context, @NonNull List<ScenicGuideListItemEntity> list) {
        this.c = context.getResources();
        this.d = list;
    }

    public final ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new b(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scenic_guide_view_item, viewGroup, false));
    }

    public final void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Drawable drawable;
        ScenicGuideListItemEntity scenicGuideListItemEntity = this.d.get(i);
        b bVar = (b) viewHolder;
        bVar.b.setText(scenicGuideListItemEntity.a);
        String str = scenicGuideListItemEntity.b;
        if (this.b != i) {
            a(bVar, str);
            this.d.get(i).f = false;
        } else if (scenicGuideListItemEntity.f) {
            char c2 = 65535;
            switch (str.hashCode()) {
                case -1560402639:
                    if (str.equals("aid_station")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case -873960692:
                    if (str.equals("ticket")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case -868239859:
                    if (str.equals("toilet")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -793201736:
                    if (str.equals("parking")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -575478802:
                    if (str.equals("scenic_spot")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case -189118908:
                    if (str.equals("gateway")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -151653955:
                    if (str.equals("lodgement")) {
                        c2 = 8;
                        break;
                    }
                    break;
                case 3148894:
                    if (str.equals("food")) {
                        c2 = 7;
                        break;
                    }
                    break;
                case 3529462:
                    if (str.equals(AlibcConstants.SHOP)) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 106642994:
                    if (str.equals("photo")) {
                        c2 = 9;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_scenic_spot_icon_selected);
                    break;
                case 1:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_toilet_icon_selected);
                    break;
                case 2:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_gateway_icon_selected);
                    break;
                case 3:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_parking_icon_selected);
                    break;
                case 4:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_ticket_icon_selected);
                    break;
                case 5:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_aid_station_icon_selected);
                    break;
                case 6:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_shop_icon_selected);
                    break;
                case 7:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_food_icon_selected);
                    break;
                case 8:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_lodgement_icon_selected);
                    break;
                case 9:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_photo_icon_selected);
                    break;
                default:
                    drawable = this.c.getDrawable(R.drawable.map_widget_guide_default_icon_selected);
                    break;
            }
            bVar.c.setImageDrawable(drawable);
            bVar.b.setTextColor(this.c.getColor(R.color.map_widget_guide_name_selected));
        } else {
            a(bVar, str);
        }
        if (i == this.d.size() - 1) {
            bVar.d.setVisibility(8);
        } else {
            bVar.d.setVisibility(0);
        }
        bVar.itemView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (bbe.this.a != null) {
                    a a2 = bbe.this.a;
                    int i = i;
                    bbe.this.d;
                    a2.a(i);
                }
            }
        });
    }

    private void a(b bVar, String str) {
        bVar.c.setImageDrawable(a(str));
        bVar.b.setTextColor(this.c.getColor(R.color.map_widget_guide_name_normal));
    }

    public final int getItemCount() {
        if (this.d == null) {
            return 0;
        }
        return this.d.size();
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable a(java.lang.String r2) {
        /*
            r1 = this;
            int r0 = r2.hashCode()
            switch(r0) {
                case -1560402639: goto L_0x0065;
                case -873960692: goto L_0x005b;
                case -868239859: goto L_0x0051;
                case -793201736: goto L_0x0047;
                case -575478802: goto L_0x003d;
                case -189118908: goto L_0x0033;
                case -151653955: goto L_0x0028;
                case 3148894: goto L_0x001e;
                case 3529462: goto L_0x0014;
                case 106642994: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x006f
        L_0x0009:
            java.lang.String r0 = "photo"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 9
            goto L_0x0070
        L_0x0014:
            java.lang.String r0 = "shop"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 6
            goto L_0x0070
        L_0x001e:
            java.lang.String r0 = "food"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 7
            goto L_0x0070
        L_0x0028:
            java.lang.String r0 = "lodgement"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 8
            goto L_0x0070
        L_0x0033:
            java.lang.String r0 = "gateway"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 2
            goto L_0x0070
        L_0x003d:
            java.lang.String r0 = "scenic_spot"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 0
            goto L_0x0070
        L_0x0047:
            java.lang.String r0 = "parking"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 3
            goto L_0x0070
        L_0x0051:
            java.lang.String r0 = "toilet"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 1
            goto L_0x0070
        L_0x005b:
            java.lang.String r0 = "ticket"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 4
            goto L_0x0070
        L_0x0065:
            java.lang.String r0 = "aid_station"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x006f
            r2 = 5
            goto L_0x0070
        L_0x006f:
            r2 = -1
        L_0x0070:
            switch(r2) {
                case 0: goto L_0x00cd;
                case 1: goto L_0x00c4;
                case 2: goto L_0x00bb;
                case 3: goto L_0x00b2;
                case 4: goto L_0x00a9;
                case 5: goto L_0x00a0;
                case 6: goto L_0x0097;
                case 7: goto L_0x008e;
                case 8: goto L_0x0085;
                case 9: goto L_0x007c;
                default: goto L_0x0073;
            }
        L_0x0073:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_default_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x007c:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_photo_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x0085:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_lodgement_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x008e:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_food_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x0097:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_shop_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x00a0:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_aid_station_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x00a9:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_ticket_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x00b2:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_parking_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x00bb:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_gateway_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x00c4:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_toilet_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
            goto L_0x00d5
        L_0x00cd:
            android.content.res.Resources r2 = r1.c
            int r0 = com.autonavi.minimap.R.drawable.map_widget_guide_scenic_spot_icon_normal
            android.graphics.drawable.Drawable r2 = r2.getDrawable(r0)
        L_0x00d5:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bbe.a(java.lang.String):android.graphics.drawable.Drawable");
    }
}
