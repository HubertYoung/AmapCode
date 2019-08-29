package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.bundle.life.api.entity.ScenicPlayRouteItemEntity;
import com.autonavi.minimap.R;
import java.util.ArrayList;

/* renamed from: bbf reason: default package */
/* compiled from: ScenicPlayRouteAdapter */
public final class bbf extends Adapter<ViewHolder> {
    public a a;
    public int b;
    Context c;
    private Resources d;
    /* access modifiers changed from: private */
    public ArrayList<ScenicPlayRouteItemEntity> e = new ArrayList<>();

    /* renamed from: bbf$a */
    /* compiled from: ScenicPlayRouteAdapter */
    public interface a {
        void a(int i);
    }

    /* renamed from: bbf$b */
    /* compiled from: ScenicPlayRouteAdapter */
    class b extends ViewHolder {
        /* access modifiers changed from: private */
        public TextView b;
        /* access modifiers changed from: private */
        public View c;
        /* access modifiers changed from: private */
        public TextView d;
        /* access modifiers changed from: private */
        public RelativeLayout e;

        public b(View view) {
            super(view);
            this.b = (TextView) view.findViewById(R.id.scenic_play_route_item_name);
            this.c = view.findViewById(R.id.scenic_play_route_item_divider);
            this.d = (TextView) view.findViewById(R.id.scenic_play_route_item_desc);
            this.e = (RelativeLayout) view.findViewById(R.id.scenic_play_route_root_layout);
        }
    }

    public bbf(@NonNull Context context, @NonNull ArrayList<ScenicPlayRouteItemEntity> arrayList) {
        this.d = context.getResources();
        this.e = arrayList;
        this.c = context;
    }

    public final ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        b bVar = new b(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scenic_play_route_view_item, viewGroup, false));
        int size = this.e.size();
        b bVar2 = bVar;
        LayoutParams layoutParams = bVar2.e.getLayoutParams();
        layoutParams.width = ((ags.a(this.c).width() - (((int) this.c.getResources().getDimension(R.dimen.map_scenic_play_route_widget_margin)) * 2)) - (((int) this.c.getResources().getDimension(R.dimen.map_scenic_play_route_widget_margin_divider)) * (size - 1))) / size;
        bVar2.e.setLayoutParams(layoutParams);
        return bVar;
    }

    public final void onBindViewHolder(ViewHolder viewHolder, final int i) {
        ScenicPlayRouteItemEntity scenicPlayRouteItemEntity = this.e.get(i);
        b bVar = (b) viewHolder;
        String str = scenicPlayRouteItemEntity.c;
        String str2 = scenicPlayRouteItemEntity.e;
        bVar.b.setText(str);
        bVar.d.setText(str2);
        if (this.b == i) {
            bVar.b.setTextColor(this.d.getColor(R.color.map_widget_guide_name_selected));
            bVar.d.setTextColor(this.d.getColor(R.color.map_widget_guide_name_selected));
        } else {
            bVar.b.setTextColor(this.d.getColor(R.color.map_widget_guide_play_route));
            bVar.d.setTextColor(this.d.getColor(R.color.map_widget_guide_play_route_desc));
        }
        if (i == this.e.size() - 1) {
            bVar.c.setVisibility(8);
        } else {
            bVar.c.setVisibility(0);
        }
        bVar.itemView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (bbf.this.a != null) {
                    a a2 = bbf.this.a;
                    int i = i;
                    bbf.this.e;
                    a2.a(i);
                }
            }
        });
    }

    public final int getItemCount() {
        if (this.e == null) {
            return 0;
        }
        return this.e.size();
    }
}
