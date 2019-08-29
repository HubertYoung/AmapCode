package defpackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.model.BusStationData;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBusTrip;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationBuses;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStationData;
import com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.FrameGifView;
import com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.ZoomBoardViewBg;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* renamed from: dyy reason: default package */
/* compiled from: RealtimeInfoItemAdapter */
public final class dyy extends Adapter<ViewHolder> {
    public b a;
    public RecommendStationData b;
    public RealtimeBuses c;
    public dyg d;
    public boolean e;
    public BusStationData f;
    public int g;
    private int h = 0;
    private int i = 1;
    private LayoutInflater j;
    private RealtimeBuses k;
    /* access modifiers changed from: private */
    public dyg l;
    /* access modifiers changed from: private */
    public List<RecommenStationLines> m;
    private List<a> n = new ArrayList();
    private int o;
    private int p;
    private int q;
    /* access modifiers changed from: private */
    public String r = "";
    private int s = 0;
    private Context t;

    /* renamed from: dyy$a */
    /* compiled from: RealtimeInfoItemAdapter */
    static class a {
        CharSequence a;
        CharSequence b;
        CharSequence c;
        CharSequence d;
        CharSequence e;
        CharSequence f;
        int g;
        int h;
        int i;

        private a() {
            this.g = 8;
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    /* renamed from: dyy$b */
    /* compiled from: RealtimeInfoItemAdapter */
    public interface b {
        void a(View view, int i);
    }

    /* renamed from: dyy$c */
    /* compiled from: RealtimeInfoItemAdapter */
    public class c extends ViewHolder {
        View a;

        public c(View view) {
            super(view);
            this.a = view.findViewById(R.id.realtime_info_list_header_place_holder);
        }
    }

    /* renamed from: dyy$d */
    /* compiled from: RealtimeInfoItemAdapter */
    public class d extends ViewHolder {
        ZoomBoardViewBg a;
        View b;
        TextView c;
        TextView d;
        TextView e;
        TextView f;
        View g;
        FrameGifView h;

        public d(View view) {
            super(view);
            this.b = view;
            this.c = (TextView) view.findViewById(R.id.realtime_info_item_first_tip);
            this.a = (ZoomBoardViewBg) view.findViewById(R.id.realtime_info_item_summary_tip);
            this.d = (TextView) view.findViewById(R.id.realtime_info_item_second_tip);
            this.e = (TextView) view.findViewById(R.id.realtime_info_item_summary_tip_text);
            this.f = (TextView) view.findViewById(R.id.realtime_info_item_summary_tip_subtext);
            this.g = view.findViewById(R.id.realtime_info_item_summary_icon_tip);
            this.h = (FrameGifView) view.findViewById(R.id.realtime_bus_detail_gif);
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int a(int r3, int r4, int r5) {
        /*
            r0 = 8
            r1 = 60
            if (r4 <= 0) goto L_0x000b
            if (r4 >= r1) goto L_0x000b
            if (r5 <= 0) goto L_0x000b
            return r0
        L_0x000b:
            r2 = 16
            switch(r3) {
                case 0: goto L_0x0022;
                case 1: goto L_0x0013;
                case 2: goto L_0x0022;
                case 3: goto L_0x0022;
                default: goto L_0x0010;
            }
        L_0x0010:
            r0 = 16
            goto L_0x0024
        L_0x0013:
            if (r4 != 0) goto L_0x0018
            if (r5 != 0) goto L_0x0018
            goto L_0x0024
        L_0x0018:
            if (r4 <= 0) goto L_0x001f
            if (r4 >= r1) goto L_0x001f
            if (r5 != 0) goto L_0x001f
            goto L_0x0024
        L_0x001f:
            r3 = 3600(0xe10, float:5.045E-42)
            goto L_0x0010
        L_0x0022:
            r0 = 32
        L_0x0024:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dyy.a(int, int, int):int");
    }

    public dyy(Context context) {
        this.j = LayoutInflater.from(context);
        this.t = context;
        this.o = this.t.getResources().getColor(R.color.f_c_2);
        this.p = this.t.getResources().getColor(R.color.f_c_3);
        this.q = this.t.getResources().getColor(R.color.f_c_16);
        this.s = ags.a(context).height();
    }

    public final int a() {
        return (int) ((((float) this.s) / 9.0f) * 2.0f);
    }

    public final void a(dyg dyg) {
        List<RecommenStationLines> list;
        if (this.e) {
            this.d = dyg;
        } else if (this.l == null || !dxx.a((Collection<T>) this.l.a) || !dyg.a.get(0).getStationid().equals(this.l.a.get(0).getStationid())) {
            this.n.clear();
            this.g = 0;
            this.l = dyg;
            List<RealtimeBus> list2 = null;
            if (dyg == null) {
                list = null;
            } else {
                list = dyg.a;
            }
            this.m = list;
            if (dyg != null) {
                list2 = dyg.c;
            }
            if (!dxx.a((Collection<T>) this.m)) {
                this.g = dyg.b;
                if (!dxx.a((Collection<T>) this.m)) {
                    this.n.addAll(a(this.m));
                    a(this.k);
                    a(this.n, this.m, list2);
                    notifyDataSetChanged();
                }
            }
        }
    }

    public final void b() {
        for (int i2 = 0; i2 < this.n.size(); i2++) {
            a aVar = this.n.get(i2);
            aVar.c = aVar.e;
            aVar.d = aVar.f;
            if (aVar.i == 1) {
                aVar.i = 2;
            }
            aVar.g = 32;
        }
        notifyDataSetChanged();
    }

    public final void a(RealtimeBuses realtimeBuses) {
        if (this.e) {
            this.c = realtimeBuses;
            return;
        }
        this.k = realtimeBuses;
        if (getItemCount() > 1) {
            for (int i2 = 0; i2 < this.m.size(); i2++) {
                RecommenStationLines recommenStationLines = this.m.get(i2);
                if (recommenStationLines != null && "1".equals(recommenStationLines.getIs_realtime())) {
                    RealtimeBus a2 = a(realtimeBuses, recommenStationLines.getLineid());
                    if (a2 != null) {
                        a aVar = this.n.get(i2);
                        a(aVar, a2.status, a2.trip);
                        aVar.b = dxx.a(a2.status);
                        aVar.i = a2.status == 1 ? 1 : 2;
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    private static RealtimeBus a(RealtimeBuses realtimeBuses, String str) {
        RealtimeBus realtimeBus = null;
        if (realtimeBuses == null || realtimeBuses.buses == null || realtimeBuses.buses.size() == 0) {
            return null;
        }
        List<RealtimeBus> list = realtimeBuses.buses;
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                break;
            }
            RealtimeBus realtimeBus2 = list.get(i2);
            if (TextUtils.equals(str, realtimeBus2.line)) {
                realtimeBus = realtimeBus2;
                break;
            }
            i2++;
        }
        return realtimeBus;
    }

    private List<a> a(List<RecommenStationLines> list) {
        ArrayList arrayList = new ArrayList();
        if (dxx.a((Collection<T>) list)) {
            return arrayList;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            RecommenStationLines recommenStationLines = list.get(i2);
            if (recommenStationLines != null) {
                a b2 = b(recommenStationLines);
                if (b2 != null) {
                    arrayList.add(b2);
                }
            }
        }
        return arrayList;
    }

    private static List<a> a(List<a> list, List<RecommenStationLines> list2, List<RealtimeBus> list3) {
        if (dxx.a((Collection<T>) list2) || dxx.a((Collection<T>) list3) || list.size() != list2.size()) {
            return list;
        }
        for (int i2 = 0; i2 < list2.size(); i2++) {
            RecommenStationLines recommenStationLines = list2.get(i2);
            a aVar = list.get(i2);
            if (recommenStationLines != null && !dxx.a((Collection<T>) list3)) {
                String lineid = recommenStationLines.getLineid();
                int i3 = 0;
                while (true) {
                    if (i3 >= list3.size()) {
                        break;
                    }
                    RealtimeBus realtimeBus = list3.get(i3);
                    if (realtimeBus != null && TextUtils.equals(lineid, realtimeBus.line)) {
                        a(aVar, realtimeBus.status, realtimeBus.trip);
                        break;
                    }
                    i3++;
                }
            }
        }
        return list;
    }

    private a b(RecommenStationLines recommenStationLines) {
        if (recommenStationLines == null) {
            return null;
        }
        int i2 = 0;
        a aVar = new a(0);
        boolean equals = "1".equals(recommenStationLines.getIs_realtime());
        aVar.a = recommenStationLines.getKey_name();
        aVar.b = equals ? dxx.a(b(recommenStationLines.getStatus())) : "";
        a(recommenStationLines, aVar);
        aVar.h = 0;
        String a2 = a(recommenStationLines.getLineid());
        if ("3".equals(a2)) {
            aVar.h = 2;
        } else if ("4".equals(a2)) {
            aVar.h = 4;
        }
        if (a(recommenStationLines)) {
            aVar.h = 1;
        }
        if (equals) {
            i2 = b(recommenStationLines.getStatus()) == 1 ? 1 : 2;
        }
        aVar.i = i2;
        a(aVar, recommenStationLines);
        return aVar;
    }

    private void a(RecommenStationLines recommenStationLines, a aVar) {
        StringBuilder sb = new StringBuilder(recommenStationLines.getStart_time());
        if (!TextUtils.isEmpty(sb)) {
            StringBuilder insert = sb.insert(2, ":");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.t.getString(R.string.route_bus_detail_bus_firsttime));
            sb2.append(Token.SEPARATOR);
            insert.insert(0, sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder(recommenStationLines.getEnd_time());
        if (!TextUtils.isEmpty(sb3)) {
            StringBuilder insert2 = sb3.insert(2, ":");
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this.t.getString(R.string.route_bus_detail_bus_endtime));
            sb4.append(Token.SEPARATOR);
            insert2.insert(0, sb4.toString());
        }
        aVar.e = sb;
        aVar.f = sb3;
    }

    public final ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        if (i2 == this.h) {
            return new c(this.j.inflate(R.layout.realtime_info_list_header, viewGroup, false));
        }
        if (i2 == this.i) {
            return new d(this.j.inflate(R.layout.realtime_info_list_item, viewGroup, false));
        }
        return new d(this.j.inflate(R.layout.realtime_info_list_item, viewGroup, false));
    }

    public final int getItemViewType(int i2) {
        return i2 == 0 ? this.h : this.i;
    }

    public final int getItemCount() {
        if (this.n != null) {
            return this.n.size() + 1;
        }
        return 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:52:0x014b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0175, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder r7, int r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = r7 instanceof defpackage.dyy.d     // Catch:{ all -> 0x0176 }
            if (r0 == 0) goto L_0x014c
            dyy$d r7 = (defpackage.dyy.d) r7     // Catch:{ all -> 0x0176 }
            r0 = 1
            int r8 = r8 - r0
            com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.ZoomBoardViewBg r1 = r7.a     // Catch:{ all -> 0x0176 }
            dyy$2 r2 = new dyy$2     // Catch:{ all -> 0x0176 }
            r2.<init>(r7, r8)     // Catch:{ all -> 0x0176 }
            r1.setOnClickListener(r2)     // Catch:{ all -> 0x0176 }
            android.view.View r1 = r7.itemView     // Catch:{ all -> 0x0176 }
            dyy$3 r2 = new dyy$3     // Catch:{ all -> 0x0176 }
            r2.<init>()     // Catch:{ all -> 0x0176 }
            r1.setOnClickListener(r2)     // Catch:{ all -> 0x0176 }
            java.util.List<dyy$a> r1 = r6.n     // Catch:{ all -> 0x0176 }
            int r1 = r1.size()     // Catch:{ all -> 0x0176 }
            if (r8 >= r1) goto L_0x014a
            java.util.List<dyy$a> r1 = r6.n     // Catch:{ all -> 0x0176 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0176 }
            if (r1 != 0) goto L_0x014a
            int r1 = r6.g     // Catch:{ all -> 0x0176 }
            r2 = 0
            if (r8 != r1) goto L_0x0034
            r1 = 1
            goto L_0x0035
        L_0x0034:
            r1 = 0
        L_0x0035:
            com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.ZoomBoardViewBg r3 = r7.a     // Catch:{ all -> 0x0176 }
            r3.setSelectItem(r1)     // Catch:{ all -> 0x0176 }
            java.util.List<dyy$a> r1 = r6.n     // Catch:{ all -> 0x0176 }
            java.lang.Object r8 = r1.get(r8)     // Catch:{ all -> 0x0176 }
            dyy$a r8 = (defpackage.dyy.a) r8     // Catch:{ all -> 0x0176 }
            r1 = 8
            if (r8 != 0) goto L_0x004d
            android.view.View r7 = r7.b     // Catch:{ all -> 0x0176 }
            r7.setVisibility(r1)     // Catch:{ all -> 0x0176 }
            monitor-exit(r6)
            return
        L_0x004d:
            android.widget.TextView r3 = r7.e     // Catch:{ all -> 0x0176 }
            java.lang.CharSequence r4 = r8.a     // Catch:{ all -> 0x0176 }
            r3.setText(r4)     // Catch:{ all -> 0x0176 }
            java.lang.CharSequence r3 = r8.b     // Catch:{ all -> 0x0176 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x0176 }
            if (r3 == 0) goto L_0x0069
            android.widget.TextView r3 = r7.f     // Catch:{ all -> 0x0176 }
            r3.setVisibility(r1)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r3 = r7.e     // Catch:{ all -> 0x0176 }
            int r4 = r6.o     // Catch:{ all -> 0x0176 }
            r3.setTextColor(r4)     // Catch:{ all -> 0x0176 }
            goto L_0x007c
        L_0x0069:
            android.widget.TextView r3 = r7.f     // Catch:{ all -> 0x0176 }
            java.lang.CharSequence r4 = r8.b     // Catch:{ all -> 0x0176 }
            r3.setText(r4)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r3 = r7.e     // Catch:{ all -> 0x0176 }
            int r4 = r6.p     // Catch:{ all -> 0x0176 }
            r3.setTextColor(r4)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r3 = r7.f     // Catch:{ all -> 0x0176 }
            r3.setVisibility(r2)     // Catch:{ all -> 0x0176 }
        L_0x007c:
            int r3 = r8.h     // Catch:{ all -> 0x0176 }
            r4 = 4
            if (r3 != r0) goto L_0x008e
            android.view.View r3 = r7.g     // Catch:{ all -> 0x0176 }
            r3.setVisibility(r2)     // Catch:{ all -> 0x0176 }
            android.view.View r3 = r7.g     // Catch:{ all -> 0x0176 }
            int r5 = com.autonavi.minimap.R.drawable.realtime_info_item_summary_icon_favorite     // Catch:{ all -> 0x0176 }
            r3.setBackgroundResource(r5)     // Catch:{ all -> 0x0176 }
            goto L_0x00b6
        L_0x008e:
            int r3 = r8.h     // Catch:{ all -> 0x0176 }
            r5 = 2
            if (r3 != r5) goto L_0x00a0
            android.view.View r3 = r7.g     // Catch:{ all -> 0x0176 }
            r3.setVisibility(r2)     // Catch:{ all -> 0x0176 }
            android.view.View r3 = r7.g     // Catch:{ all -> 0x0176 }
            int r5 = com.autonavi.minimap.R.drawable.realtime_info_item_summary_icon_home     // Catch:{ all -> 0x0176 }
            r3.setBackgroundResource(r5)     // Catch:{ all -> 0x0176 }
            goto L_0x00b6
        L_0x00a0:
            int r3 = r8.h     // Catch:{ all -> 0x0176 }
            if (r3 != r4) goto L_0x00b1
            android.view.View r3 = r7.g     // Catch:{ all -> 0x0176 }
            r3.setVisibility(r2)     // Catch:{ all -> 0x0176 }
            android.view.View r3 = r7.g     // Catch:{ all -> 0x0176 }
            int r5 = com.autonavi.minimap.R.drawable.realtime_info_item_summary_icon_company     // Catch:{ all -> 0x0176 }
            r3.setBackgroundResource(r5)     // Catch:{ all -> 0x0176 }
            goto L_0x00b6
        L_0x00b1:
            android.view.View r3 = r7.g     // Catch:{ all -> 0x0176 }
            r3.setVisibility(r1)     // Catch:{ all -> 0x0176 }
        L_0x00b6:
            int r3 = r8.g     // Catch:{ all -> 0x0176 }
            r5 = 1098907648(0x41800000, float:16.0)
            if (r3 == r1) goto L_0x00fa
            r1 = 16
            if (r3 == r1) goto L_0x00dd
            r1 = 32
            if (r3 == r1) goto L_0x00c5
            goto L_0x010f
        L_0x00c5:
            android.widget.TextView r1 = r7.c     // Catch:{ all -> 0x0176 }
            r3 = 1095761920(0x41500000, float:13.0)
            r1.setTextSize(r0, r3)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r0 = r7.c     // Catch:{ all -> 0x0176 }
            int r1 = r6.o     // Catch:{ all -> 0x0176 }
            r0.setTextColor(r1)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r0 = r7.c     // Catch:{ all -> 0x0176 }
            android.graphics.Typeface r1 = android.graphics.Typeface.defaultFromStyle(r2)     // Catch:{ all -> 0x0176 }
            r0.setTypeface(r1)     // Catch:{ all -> 0x0176 }
            goto L_0x010f
        L_0x00dd:
            android.widget.TextView r1 = r7.c     // Catch:{ all -> 0x0176 }
            r1.setTextSize(r0, r5)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r1 = r7.c     // Catch:{ all -> 0x0176 }
            int r3 = r6.o     // Catch:{ all -> 0x0176 }
            r1.setTextColor(r3)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r1 = r7.c     // Catch:{ all -> 0x0176 }
            int r3 = r6.o     // Catch:{ all -> 0x0176 }
            r1.setTextColor(r3)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r1 = r7.c     // Catch:{ all -> 0x0176 }
            android.graphics.Typeface r0 = android.graphics.Typeface.defaultFromStyle(r0)     // Catch:{ all -> 0x0176 }
            r1.setTypeface(r0)     // Catch:{ all -> 0x0176 }
            goto L_0x010f
        L_0x00fa:
            android.widget.TextView r1 = r7.c     // Catch:{ all -> 0x0176 }
            r1.setTextSize(r0, r5)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r1 = r7.c     // Catch:{ all -> 0x0176 }
            int r3 = r6.q     // Catch:{ all -> 0x0176 }
            r1.setTextColor(r3)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r1 = r7.c     // Catch:{ all -> 0x0176 }
            android.graphics.Typeface r0 = android.graphics.Typeface.defaultFromStyle(r0)     // Catch:{ all -> 0x0176 }
            r1.setTypeface(r0)     // Catch:{ all -> 0x0176 }
        L_0x010f:
            java.lang.CharSequence r0 = r8.c     // Catch:{ all -> 0x0176 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0176 }
            if (r0 == 0) goto L_0x011d
            android.widget.TextView r0 = r7.c     // Catch:{ all -> 0x0176 }
            r0.setVisibility(r4)     // Catch:{ all -> 0x0176 }
            goto L_0x0129
        L_0x011d:
            android.widget.TextView r0 = r7.c     // Catch:{ all -> 0x0176 }
            java.lang.CharSequence r1 = r8.c     // Catch:{ all -> 0x0176 }
            r0.setText(r1)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r0 = r7.c     // Catch:{ all -> 0x0176 }
            r0.setVisibility(r2)     // Catch:{ all -> 0x0176 }
        L_0x0129:
            java.lang.CharSequence r0 = r8.d     // Catch:{ all -> 0x0176 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0176 }
            if (r0 == 0) goto L_0x0137
            android.widget.TextView r0 = r7.d     // Catch:{ all -> 0x0176 }
            r0.setVisibility(r4)     // Catch:{ all -> 0x0176 }
            goto L_0x0143
        L_0x0137:
            android.widget.TextView r0 = r7.d     // Catch:{ all -> 0x0176 }
            java.lang.CharSequence r1 = r8.d     // Catch:{ all -> 0x0176 }
            r0.setText(r1)     // Catch:{ all -> 0x0176 }
            android.widget.TextView r0 = r7.d     // Catch:{ all -> 0x0176 }
            r0.setVisibility(r2)     // Catch:{ all -> 0x0176 }
        L_0x0143:
            com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.FrameGifView r7 = r7.h     // Catch:{ all -> 0x0176 }
            int r8 = r8.i     // Catch:{ all -> 0x0176 }
            r7.setStatus(r8)     // Catch:{ all -> 0x0176 }
        L_0x014a:
            monitor-exit(r6)
            return
        L_0x014c:
            boolean r8 = r7 instanceof defpackage.dyy.c     // Catch:{ all -> 0x0176 }
            if (r8 == 0) goto L_0x0174
            dyy$c r7 = (defpackage.dyy.c) r7     // Catch:{ all -> 0x0176 }
            android.view.View r8 = r7.a     // Catch:{ all -> 0x0176 }
            android.view.ViewGroup$LayoutParams r8 = r8.getLayoutParams()     // Catch:{ all -> 0x0176 }
            android.widget.LinearLayout$LayoutParams r8 = (android.widget.LinearLayout.LayoutParams) r8     // Catch:{ all -> 0x0176 }
            int r0 = r6.a()     // Catch:{ all -> 0x0176 }
            r8.height = r0     // Catch:{ all -> 0x0176 }
            android.view.View r0 = r7.a     // Catch:{ all -> 0x0176 }
            r0.setLayoutParams(r8)     // Catch:{ all -> 0x0176 }
            android.view.View r8 = r7.a     // Catch:{ all -> 0x0176 }
            r8.requestLayout()     // Catch:{ all -> 0x0176 }
            android.view.View r7 = r7.a     // Catch:{ all -> 0x0176 }
            dyy$1 r8 = new dyy$1     // Catch:{ all -> 0x0176 }
            r8.<init>()     // Catch:{ all -> 0x0176 }
            r7.setOnClickListener(r8)     // Catch:{ all -> 0x0176 }
        L_0x0174:
            monitor-exit(r6)
            return
        L_0x0176:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dyy.onBindViewHolder(android.support.v7.widget.RecyclerView$ViewHolder, int):void");
    }

    private static void a(a aVar, RecommenStationLines recommenStationLines) {
        List<RecommenStationBuses> buses = recommenStationLines.getBuses();
        if (!"1".equals(recommenStationLines.getIs_realtime()) || "0".equals(recommenStationLines.getStatus())) {
            aVar.c = aVar.e;
            aVar.d = aVar.f;
            aVar.g = 32;
        } else if (buses != null && buses.size() > 0) {
            int b2 = b(recommenStationLines.getStatus());
            RecommenStationBuses recommenStationBuses = buses.get(0);
            int b3 = b(recommenStationBuses.getArrival());
            int b4 = b(recommenStationBuses.getStation_left());
            aVar.c = b(b2, b3, b4);
            aVar.g = a(b2, b3, b4);
            if (buses.size() > 1) {
                buses.get(1);
                int b5 = b(recommenStationBuses.getArrival());
                int b6 = b(recommenStationBuses.getStation_left());
                StringBuilder sb = new StringBuilder("下辆 ");
                sb.append(b(b2, b5, b6));
                aVar.d = sb.toString();
                return;
            }
            aVar.d = "下辆 暂无数据";
        }
    }

    private static String b(int i2, int i3, int i4) {
        String str = "";
        if (i3 > 0 && i3 < 60 && i4 > 0) {
            return "即将到站";
        }
        if (i2 == 1) {
            if (i3 == 0 && i4 == 0) {
                str = "车已到达";
            } else if (i3 > 0 && i3 < 60 && i4 == 0) {
                str = "即将到站";
            } else if (i3 >= 60 && i3 < 3600) {
                StringBuilder sb = new StringBuilder("约");
                sb.append(Math.round(((double) i3) / 60.0d));
                sb.append("分钟");
                str = sb.toString();
            } else if (i3 >= 3600) {
                StringBuilder sb2 = new StringBuilder("约");
                sb2.append(Math.round(((double) i3) / 3600.0d));
                sb2.append("小时");
                sb2.append(Math.round(((double) (i3 % 60)) / 60.0d));
                sb2.append("分钟");
                str = sb2.toString();
            }
        }
        return str;
    }

    private static void a(a aVar, int i2, List<RealtimeBusTrip> list) {
        if (i2 != 1) {
            aVar.c = aVar.e;
            aVar.d = aVar.f;
            aVar.g = 32;
        } else if (list == null || list.size() <= 0) {
            aVar.c = aVar.e;
            aVar.d = aVar.f;
            aVar.g = 32;
        } else {
            RealtimeBusTrip realtimeBusTrip = list.get(0);
            aVar.c = b(i2, realtimeBusTrip.arrival, realtimeBusTrip.station_left);
            aVar.g = a(i2, realtimeBusTrip.arrival, realtimeBusTrip.station_left);
            if (list.size() > 1) {
                RealtimeBusTrip realtimeBusTrip2 = list.get(1);
                StringBuilder sb = new StringBuilder("下辆 ");
                sb.append(b(i2, realtimeBusTrip2.arrival, realtimeBusTrip2.station_left));
                aVar.d = sb.toString();
                return;
            }
            aVar.d = "下辆 暂无数据";
        }
    }

    private static int b(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public final String a(String str) {
        if (this.b == null) {
            return "0";
        }
        List<RecommenStationLines> lines = this.b.getLines();
        if (dxx.a((Collection<T>) lines)) {
            return "0";
        }
        for (int i2 = 0; i2 < lines.size(); i2++) {
            RecommenStationLines recommenStationLines = lines.get(i2);
            if (recommenStationLines != null && TextUtils.equals(str, recommenStationLines.getLineid())) {
                return recommenStationLines.getTactics();
            }
        }
        return "0";
    }

    private boolean a(RecommenStationLines recommenStationLines) {
        RealTimeBusStation realTimeBusStation = null;
        if (recommenStationLines != null) {
            String lineid = recommenStationLines.getLineid();
            recommenStationLines.getStationid();
            if (this.f != null) {
                List<RealTimeBusStation> list = this.f.stations;
                if (!dxx.a((Collection<T>) list)) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= list.size()) {
                            break;
                        }
                        RealTimeBusStation realTimeBusStation2 = list.get(i2);
                        if (TextUtils.equals(realTimeBusStation2.bus_id, lineid)) {
                            realTimeBusStation = realTimeBusStation2;
                            break;
                        }
                        i2++;
                    }
                }
            }
        }
        if (realTimeBusStation == null) {
            return false;
        }
        return realTimeBusStation.isFollow;
    }
}
