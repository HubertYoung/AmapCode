package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatManager;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatRequest;
import com.autonavi.minimap.route.bus.realtimebus.data.POISearchManager;
import com.autonavi.minimap.route.bus.realtimebus.model.RTBConfigData;
import com.autonavi.minimap.route.bus.realtimebus.model.RTBZipFileData;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBus;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBusTrip;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationBuses;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStation;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStationData;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dwe reason: default package */
/* compiled from: RecomStationViewManager */
public final class dwe {
    private TextView A;
    private TextView B;
    Context a;
    public String b = "";
    HeartBeatRequest c;
    RTBConfigData d;
    boolean e = true;
    boolean f = true;
    int g = -1;
    int h = -1;
    String i = null;
    String j = null;
    Bitmap k = null;
    NinePatchDrawable l = null;
    Bitmap m = null;
    Bitmap n = null;
    TextView o;
    TextView p;
    ImageView q;
    LinearLayout r;
    ImageView s;
    ImageView t;
    int u = -1;
    a v = null;
    b w = null;
    int x;
    ImageView y;
    private RecommenStationLines z;

    /* renamed from: dwe$a */
    /* compiled from: RecomStationViewManager */
    public interface a {
        void a();
    }

    /* renamed from: dwe$b */
    /* compiled from: RecomStationViewManager */
    public interface b {
        void a();
    }

    /* renamed from: dwe$c */
    /* compiled from: RecomStationViewManager */
    public interface c {
        void a(RealtimeBusTrip realtimeBusTrip);
    }

    public dwe(Context context) {
        this.a = context;
    }

    public final void a() {
        if (this.w != null) {
            this.w.a();
        }
    }

    public static MapViewLayoutParams a(GeoPoint geoPoint) {
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 81);
        mapViewLayoutParams.mode = 0;
        return mapViewLayoutParams;
    }

    private View c() {
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.realtimebus_without_busstop, null);
        this.b = "0";
        return inflate;
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str) || str.length() <= 5) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 5));
        sb.append("...");
        return sb.toString();
    }

    private String a(int i2, int i3) {
        return ebj.a(this.a, i2, i3);
    }

    private String a(RecommenStationLines recommenStationLines) {
        List<RecommenStationBuses> buses = recommenStationLines.getBuses();
        if (buses == null || buses.size() <= 0) {
            String status = recommenStationLines.getStatus();
            String str = "";
            if ("2".equals(status)) {
                str = this.a.getString(R.string.temporary_car);
            } else if ("0".equals(status) || "3".equals(status) || status == null) {
                if (TextUtils.isEmpty(recommenStationLines.getStart_time()) || TextUtils.isEmpty(recommenStationLines.getEnd_time())) {
                    str = "";
                } else {
                    StringBuilder sb = new StringBuilder(recommenStationLines.getStart_time());
                    sb.insert(2, ":");
                    StringBuilder insert = new StringBuilder(recommenStationLines.getEnd_time()).insert(2, ":");
                    StringBuilder sb2 = new StringBuilder("首班");
                    sb2.append(sb.toString());
                    sb2.append(" 末班");
                    sb2.append(insert.toString());
                    str = sb2.toString();
                }
            }
            return str;
        }
        return ebj.a(this.a, Integer.parseInt(TextUtils.isEmpty(buses.get(0).getArrival()) ? "0" : buses.get(0).getArrival()), Integer.parseInt(TextUtils.isEmpty(buses.get(0).getStation_left()) ? "0" : buses.get(0).getStation_left()));
    }

    private View b(RecommendStation recommendStation, RealtimeBusTrip realtimeBusTrip) {
        if (recommendStation == null || recommendStation.getData() == null) {
            return null;
        }
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.realtimebus_daily_recom_common, null);
        this.r = (LinearLayout) inflate.findViewById(R.id.real_bus_daily_recom_common_content_main);
        this.s = (ImageView) inflate.findViewById(R.id.real_bus_daily_recom_common_icon);
        this.o = (TextView) inflate.findViewById(R.id.real_bus_daily_recom_common_linename);
        this.p = (TextView) inflate.findViewById(R.id.real_bus_daily_recom_common_linenumtime);
        this.q = (ImageView) inflate.findViewById(R.id.real_bus_daily_recom_common_linename_icon);
        this.t = (ImageView) inflate.findViewById(R.id.real_bus_daily_recom_common_close);
        RecommendStationData data = recommendStation.getData();
        this.z = c(recommendStation);
        RecommenStationLines recommenStationLines = this.z;
        if (!(this.o == null || this.p == null || recommenStationLines == null)) {
            this.o.setText(a(recommenStationLines.getKey_name()));
            if (realtimeBusTrip == null) {
                this.p.setText(a(recommenStationLines));
            } else {
                this.p.setText(a(realtimeBusTrip.arrival, realtimeBusTrip.station_left));
            }
        }
        final String bubble_style = data.getBubble_style();
        if (this.d == null || !this.d.isDownLoadAll() || this.d.getRtbZipFileData() == null) {
            ahm.a(new Runnable() {
                public final void run() {
                    int i;
                    if (dwe.this.e) {
                        dwe dwe = dwe.this;
                        String str = bubble_style;
                        if (!TextUtils.isEmpty(str)) {
                            int i2 = -1;
                            if ("3".equals(str)) {
                                i2 = R.drawable.realtimebus_homepop_bg;
                                i = R.drawable.realtimebus_homeicon_big_pop;
                            } else if ("4".equals(str)) {
                                i2 = R.drawable.realtimebus_officepop_bg;
                                i = R.drawable.realtimebus_workicon_big_pop;
                            } else if ("6".equals(str)) {
                                i2 = R.drawable.realtimebus_nightpop_bg;
                                i = R.drawable.realtimebus_nighticon_big_pop;
                            } else {
                                i = -1;
                            }
                            dwe.b = str;
                            aho.a(new Runnable(i2, i) {
                                final /* synthetic */ int a;
                                final /* synthetic */ int b;

                                {
                                    this.a = r2;
                                    this.b = r3;
                                }

                                public final void run() {
                                    dwe.this.r.setBackgroundResource(this.a);
                                    dwe.this.s.setImageResource(this.b);
                                }
                            });
                        }
                        aho.a(new Runnable() {
                            public final void run() {
                                dwe.a(dwe.this);
                            }
                        });
                    }
                }
            });
        } else {
            ahm.a(new Runnable() {
                public final void run() {
                    if (dwe.this.e) {
                        dwe dwe = dwe.this;
                        String str = bubble_style;
                        RTBZipFileData rtbZipFileData = dwe.d.getRtbZipFileData();
                        if ("3".equals(str)) {
                            dwe.g = dwe.d.getHomeStatusFont();
                            dwe.h = dwe.d.getCompanyLineFont();
                            StringBuilder sb = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                            sb.append(dwe.d.getHome_statuscolor());
                            dwe.i = sb.toString();
                            StringBuilder sb2 = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                            sb2.append(dwe.d.getHome_linecolor());
                            dwe.j = sb2.toString();
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbhomeIcon2())) {
                                dwe.k = dyv.c(rtbZipFileData.getRtbhomeIcon2());
                            }
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbHomeBg())) {
                                dwe.l = dyv.a(dyv.c(rtbZipFileData.getRtbHomeBg()));
                            }
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbClose2())) {
                                dwe.m = dyv.c(rtbZipFileData.getRtbClose2());
                            }
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbSignalIcon())) {
                                dwe.n = dyv.c(rtbZipFileData.getRtbSignalIcon());
                            }
                        } else if ("4".equals(str)) {
                            dwe.g = dwe.d.getCompanyStatusFont();
                            dwe.h = dwe.d.getCompanyLineFont();
                            StringBuilder sb3 = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                            sb3.append(dwe.d.getCompany_statuscolor());
                            dwe.i = sb3.toString();
                            StringBuilder sb4 = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                            sb4.append(dwe.d.getCompany_linecolor());
                            dwe.j = sb4.toString();
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbCompanyIcon2())) {
                                dwe.k = dyv.c(rtbZipFileData.getRtbCompanyIcon2());
                            }
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbCompanyBg())) {
                                dwe.l = dyv.a(dyv.c(rtbZipFileData.getRtbCompanyBg()));
                            }
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbCompanyClose2())) {
                                dwe.m = dyv.c(rtbZipFileData.getRtbCompanyClose2());
                            }
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbCompanysignalIcon())) {
                                dwe.n = dyv.c(rtbZipFileData.getRtbCompanysignalIcon());
                            }
                        } else if ("6".equals(str)) {
                            dwe.g = dwe.d.getNightStatusFont();
                            dwe.h = dwe.d.getNightLineFont();
                            StringBuilder sb5 = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                            sb5.append(dwe.d.getNight_statuscolor());
                            dwe.i = sb5.toString();
                            StringBuilder sb6 = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                            sb6.append(dwe.d.getNight_linecolor());
                            dwe.j = sb6.toString();
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbNightIcon2())) {
                                dwe.k = dyv.c(rtbZipFileData.getRtbNightIcon2());
                            }
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbNightBg())) {
                                dwe.l = dyv.a(dyv.c(rtbZipFileData.getRtbNightBg()));
                            }
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbNightClose2())) {
                                dwe.m = dyv.c(rtbZipFileData.getRtbNightClose2());
                            }
                            if (!TextUtils.isEmpty(rtbZipFileData.getRtbNightSignalIcon())) {
                                dwe.n = dyv.c(rtbZipFileData.getRtbNightSignalIcon());
                            }
                        }
                        dwe.b = str;
                        aho.a(new Runnable() {
                            public final void run() {
                                dwe dwe = dwe.this;
                                if (dwe.h != -1) {
                                    int round = Math.round((float) (dwe.h / 2));
                                    dwe.o.setTextSize(1, (float) round);
                                    StringBuilder sb = new StringBuilder("stopLineColor--->");
                                    sb.append(round);
                                    sb.append(Params.UNIT_DP);
                                }
                                if (dwe.j != null) {
                                    new StringBuilder("stopLineColor--->").append(dwe.j);
                                    dwe.o.setTextColor(Color.parseColor(dwe.j));
                                }
                                if (dwe.g != -1) {
                                    int round2 = Math.round((float) (dwe.g / 2));
                                    dwe.p.setTextSize(1, (float) round2);
                                    StringBuilder sb2 = new StringBuilder("stopTimeColor--->");
                                    sb2.append(round2);
                                    sb2.append(Params.UNIT_DP);
                                }
                                if (dwe.i != null) {
                                    new StringBuilder("stopTimeColor--->").append(dwe.i);
                                    dwe.p.setTextColor(Color.parseColor(dwe.i));
                                }
                                if (dwe.l != null) {
                                    new StringBuilder("contentMainBg--->").append(dwe.l);
                                    dwe.r.setBackgroundResource(0);
                                    dwe.r.setBackgroundDrawable(dwe.l);
                                }
                                if (dwe.k != null) {
                                    dwe.s.setImageBitmap(dwe.k);
                                }
                                if (dwe.m != null) {
                                    dwe.t.setImageBitmap(dwe.m);
                                }
                                if (dwe.n != null) {
                                    dwe.q.setImageBitmap(dwe.n);
                                }
                            }
                        });
                        aho.a(new Runnable() {
                            public final void run() {
                                dwe.a(dwe.this);
                            }
                        });
                    }
                }
            });
        }
        return inflate;
    }

    private View b(RecommendStation recommendStation) {
        if (recommendStation == null || recommendStation.getData() == null) {
            return null;
        }
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.realtimebus_daily_recom_common_small, null);
        this.y = (ImageView) inflate.findViewById(R.id.real_bus_daily_recom_common_small_icon);
        final String bubble_style = recommendStation.getData().getBubble_style();
        this.u = 66;
        if (this.d == null || !this.d.isDownLoadAll() || this.d.getRtbZipFileData() == null) {
            ahm.a(new Runnable() {
                public final void run() {
                    if (dwe.this.f) {
                        dwe dwe = dwe.this;
                        String str = bubble_style;
                        if (!TextUtils.isEmpty(str)) {
                            int i = "3".equals(str) ? R.drawable.realtimebus_homeicon_small_pop : "4".equals(str) ? R.drawable.realtimebus_workicon_small_pop : "6".equals(str) ? R.drawable.realtimebus_nighticon_small_pop : -1;
                            dwe.b = str;
                            if (!(i == -1 || dwe.y == null)) {
                                dwe.y.setImageResource(0);
                                dwe.y.setImageResource(i);
                            }
                        }
                        aho.a(new Runnable() {
                            public final void run() {
                                dwe.this.a();
                            }
                        });
                    }
                }
            });
        } else {
            ahm.a(new Runnable() {
                public final void run() {
                    if (dwe.this.f) {
                        dwe dwe = dwe.this;
                        String str = bubble_style;
                        RTBZipFileData rtbZipFileData = dwe.d.getRtbZipFileData();
                        final Bitmap bitmap = null;
                        if (!TextUtils.isEmpty(str)) {
                            if ("3".equals(str)) {
                                if (!TextUtils.isEmpty(rtbZipFileData.getRtbIcon1())) {
                                    bitmap = dyv.c(rtbZipFileData.getRtbIcon1());
                                }
                            } else if ("4".equals(str)) {
                                if (!TextUtils.isEmpty(rtbZipFileData.getRtbCompanyIcon1())) {
                                    bitmap = dyv.c(rtbZipFileData.getRtbCompanyIcon1());
                                }
                            } else if ("6".equals(str) && !TextUtils.isEmpty(rtbZipFileData.getRtbNightIcon1())) {
                                bitmap = dyv.c(rtbZipFileData.getRtbNightIcon1());
                            }
                            dwe.b = str;
                        }
                        aho.a(new Runnable() {
                            public final void run() {
                                if (!(bitmap == null || dwe.this.y == null)) {
                                    dwe.this.y.setImageBitmap(bitmap);
                                }
                                dwe.this.a();
                            }
                        });
                    }
                }
            });
        }
        return inflate;
    }

    private View c(RecommendStation recommendStation, RealtimeBusTrip realtimeBusTrip) {
        int i2;
        CharSequence charSequence;
        if (recommendStation == null || recommendStation.getData() == null) {
            return null;
        }
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.realtimebus_recom_common, null);
        inflate.findViewById(R.id.real_bus_recom_common_main);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.real_bus_recom_common_flag_main);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.real_bus_recom_common_flag_icon);
        TextView textView = (TextView) inflate.findViewById(R.id.real_bus_recom_common_flag);
        this.A = (TextView) inflate.findViewById(R.id.real_bus_recom_common_linename);
        this.B = (TextView) inflate.findViewById(R.id.real_bus_recom_common_linenumtime);
        inflate.findViewById(R.id.real_bus_recom_common_close);
        RecommendStationData data = recommendStation.getData();
        this.z = c(recommendStation);
        RecommenStationLines recommenStationLines = this.z;
        if (!(this.A == null || this.B == null || recommenStationLines == null)) {
            this.A.setText(a(recommenStationLines.getKey_name()));
            if (realtimeBusTrip == null) {
                this.B.setText(a(recommenStationLines));
            } else if (this.B != null) {
                this.B.setText(a(realtimeBusTrip.arrival, realtimeBusTrip.station_left));
            }
        }
        String bubble_style = data.getBubble_style();
        if (!TextUtils.isEmpty(bubble_style)) {
            int i3 = -1;
            if ("1".equals(bubble_style)) {
                i3 = R.drawable.recommed_search;
                i2 = R.drawable.realtimebus_search_big_pop;
                charSequence = this.a.getString(R.string.recommed_search);
            } else if ("2".equals(bubble_style)) {
                i3 = R.drawable.recommed_like;
                i2 = R.drawable.realtimebus_like_big_pop;
                charSequence = this.a.getString(R.string.recommed_like);
            } else if ("5".equals(bubble_style)) {
                i3 = R.drawable.recommed_nearest;
                i2 = R.drawable.realtimebus_nearest_big_pop;
                charSequence = this.a.getString(R.string.recommed_nearest);
            } else {
                charSequence = "";
                i2 = -1;
            }
            this.b = bubble_style;
            linearLayout.setBackgroundResource(i3);
            imageView.setImageResource(i2);
            textView.setText(charSequence);
        }
        return inflate;
    }

    private static RecommenStationLines c(RecommendStation recommendStation) {
        if (!(recommendStation == null || recommendStation.getData() == null)) {
            String bubble_style = recommendStation.getData().getBubble_style();
            RecommendStationData data = recommendStation.getData();
            String recommend_station = data.getRecommend_station();
            boolean a2 = dyv.a(bubble_style);
            List<RecommenStationLines> lines = data.getLines();
            if (!a2) {
                return lines.get(0);
            }
            AMapPageUtil.getAppContext();
            List<btd> a3 = bso.a().a(recommend_station);
            ArrayList arrayList = new ArrayList();
            if (a3 != null && a3.size() > 0) {
                int size = lines.size();
                int size2 = a3.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String str = a3.get(i2).bus_id;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= size) {
                            break;
                        }
                        RecommenStationLines recommenStationLines = lines.get(i3);
                        if (str.equals(recommenStationLines.getLineid())) {
                            arrayList.add(recommenStationLines);
                            break;
                        }
                        i3++;
                    }
                }
                return a((List<RecommenStationLines>) arrayList, lines);
            } else if (lines != null && lines.size() > 0) {
                return lines.get(0);
            }
        }
        return null;
    }

    private static RecommenStationLines a(List<RecommenStationLines> list, List<RecommenStationLines> list2) {
        if (list.size() <= 0) {
            return list2.get(0);
        }
        int size = list.size();
        RecommenStationLines recommenStationLines = list.get(0);
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            RecommenStationLines recommenStationLines2 = list.get(i3);
            List<RecommenStationBuses> buses = recommenStationLines2.getBuses();
            if (buses != null && buses.size() > 0) {
                String arrival = buses.get(0).getArrival();
                if (!TextUtils.isEmpty(arrival)) {
                    int parseInt = Integer.parseInt(arrival);
                    if (i2 == 0 || parseInt < i2) {
                        recommenStationLines = recommenStationLines2;
                        i2 = parseInt;
                    }
                }
            }
        }
        return recommenStationLines;
    }

    private View d(RecommendStation recommendStation) {
        if (recommendStation == null || recommendStation.getData() == null) {
            return null;
        }
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.realtimebus_recom_common_small, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.recom_common_small_icon);
        TextView textView = (TextView) inflate.findViewById(R.id.recom_common_small_txt);
        int i2 = -1;
        String str = "";
        String bubble_style = recommendStation.getData().getBubble_style();
        this.u = 88;
        if (!TextUtils.isEmpty(bubble_style)) {
            if ("1".equals(bubble_style)) {
                i2 = R.drawable.realtimebus_search_small_pop;
                str = this.a.getString(R.string.recommed_search);
            } else if ("2".equals(bubble_style)) {
                i2 = R.drawable.realtimebus_like_small_pop;
                str = this.a.getString(R.string.recommed_like);
            } else if ("5".equals(bubble_style)) {
                i2 = R.drawable.realtimebus_nearest_small_pop;
                str = this.a.getString(R.string.recommed_nearest);
            }
            this.b = bubble_style;
            imageView.setImageResource(i2);
            textView.setText(str);
        }
        return inflate;
    }

    public final View a(RecommendStation recommendStation) {
        String bubble_style = recommendStation.getData().getBubble_style();
        if ("3".equals(bubble_style) || "4".equals(bubble_style) || "6".equals(bubble_style)) {
            return b(recommendStation);
        }
        if ("1".equals(bubble_style) || "2".equals(bubble_style) || "5".equals(bubble_style)) {
            return d(recommendStation);
        }
        return null;
    }

    public final View a(RecommendStation recommendStation, RealtimeBusTrip realtimeBusTrip) {
        String bubble_style = recommendStation.getData().getBubble_style();
        if ("3".equals(bubble_style) || "4".equals(bubble_style) || "6".equals(bubble_style)) {
            return b(recommendStation, realtimeBusTrip);
        }
        if ("1".equals(bubble_style) || "2".equals(bubble_style) || "5".equals(bubble_style)) {
            return c(recommendStation, realtimeBusTrip);
        }
        if ("0".equals(bubble_style)) {
            return c();
        }
        return null;
    }

    public final void b() {
        if (this.c != null) {
            HeartBeatManager.a().a(this.c);
        }
    }

    public final void a(final c cVar) {
        b();
        if (!"0".equals(this.b)) {
            RecommenStationLines recommenStationLines = this.z;
            if (recommenStationLines != null) {
                this.c = POISearchManager.a(String.valueOf(LocationInstrument.getInstance().getLatestPosition().getAdCode()), recommenStationLines.getLineid(), recommenStationLines.getStationid(), new dyc<RealtimeBuses>() {
                    public final /* synthetic */ void a(Object obj) {
                        RealtimeBuses realtimeBuses = (RealtimeBuses) obj;
                        dwe.this.x = 0;
                        a(dwe.this.c, realtimeBuses);
                        if (realtimeBuses != null && realtimeBuses.code == 1) {
                            new StringBuilder("code--->").append(realtimeBuses.code);
                            if (realtimeBuses.buses != null && realtimeBuses.buses.size() > 0) {
                                RealtimeBus realtimeBus = realtimeBuses.buses.get(0);
                                if (realtimeBus != null && realtimeBus.trip.size() > 0) {
                                    RealtimeBusTrip realtimeBusTrip = realtimeBus.trip.get(0);
                                    if (realtimeBusTrip != null) {
                                        StringBuilder sb = new StringBuilder("arrival--->");
                                        sb.append(realtimeBusTrip.arrival);
                                        sb.append("  station_left--->");
                                        sb.append(realtimeBusTrip.station_left);
                                        if (cVar != null) {
                                            cVar.a(realtimeBusTrip);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    public final void a() {
                        dwe.this.x++;
                        if (dwe.this.x == 5) {
                            String string = dwe.this.a == null ? "" : dwe.this.a.getResources().getString(R.string.toast_realtime_no_realtime_bus_data);
                            if (!TextUtils.isEmpty(string)) {
                                ToastHelper.showToast(string);
                            }
                        }
                    }
                });
            }
        }
    }

    static /* synthetic */ void a(dwe dwe) {
        if (dwe.v != null) {
            dwe.v.a();
        }
    }
}
