package defpackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.List;

/* renamed from: dpk reason: default package */
/* compiled from: NearbyTypeAdapter */
public final class dpk extends Adapter<a> {
    public List<String> a;
    public OnItemClickListener b;
    public int c = 47;
    private Context d;
    private int e = 0;
    private int f = 0;

    /* renamed from: dpk$a */
    /* compiled from: NearbyTypeAdapter */
    public static class a extends ViewHolder implements OnClickListener {
        /* access modifiers changed from: 0000 */
        public TextView a;
        /* access modifiers changed from: 0000 */
        public View b;
        private dpk c;

        public a(Context context, View view, dpk dpk, int i) {
            super(view);
            view.setOnClickListener(this);
            this.c = dpk;
            this.a = (TextView) view.findViewById(R.id.around_type_display_text);
            this.b = view.findViewById(R.id.around_type_display_horizontalspacing);
            view.getLayoutParams().height = agn.a(context, (float) i);
        }

        public final void onClick(View view) {
            dpk.a(this.c, this);
        }
    }

    public final /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, int i) {
        a aVar = (a) viewHolder;
        if (this.a != null) {
            String str = this.a.get(i);
            if (str != null) {
                if (i == this.f) {
                    aVar.a.setTextColor(-16739841);
                    aVar.b.setVisibility(0);
                } else {
                    aVar.a.setTextColor(-13421773);
                    aVar.b.setVisibility(4);
                }
                aVar.a.setText(str);
            }
        }
    }

    public dpk(Context context) {
        this.d = context;
    }

    public final int getItemCount() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public final void a(int i) {
        if (getItemCount() > i) {
            this.f = i;
            notifyItemChanged(this.f);
            notifyItemChanged(this.e);
            this.e = this.f;
        }
    }

    public final /* synthetic */ ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new a(this.d, LayoutInflater.from(this.d).inflate(R.layout.around_type_item, viewGroup, false), this, this.c);
    }

    static /* synthetic */ void a(dpk dpk, a aVar) {
        dpk.f = aVar.getPosition();
        aVar.a.setTextColor(-16739841);
        aVar.b.setVisibility(0);
        dpk.notifyItemChanged(dpk.e);
        dpk.e = dpk.f;
        if (dpk.b != null) {
            dpk.b.onItemClick(null, aVar.itemView, aVar.getPosition(), aVar.getItemId());
        }
    }
}
