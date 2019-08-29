package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.amaphome.components.luban.LuBanDAL$1;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.transfer.TransferRequestHolder;
import com.autonavi.minimap.transfer.param.SearchWordinBoxRequest;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/* renamed from: aqu reason: default package */
/* compiled from: LuBanHotWordService */
public final class aqu {
    String a;
    TextView b;
    public final List<WeakReference<aps>> c;
    private String d;
    private Context e;
    private final aqr f;

    /* renamed from: aqu$a */
    /* compiled from: LuBanHotWordService */
    public static class a {
        public static aqu a = new aqu(0);
    }

    /* synthetic */ aqu(byte b2) {
        this();
    }

    private aqu() {
        this.f = new aqr();
        this.c = new ArrayList();
    }

    public final void a(Context context, TextView textView) {
        this.e = context;
        this.b = textView;
    }

    public final Resources a() {
        if (this.e != null) {
            return this.e.getResources();
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public final void a(CharSequence charSequence, int i) {
        for (WeakReference<aps> weakReference : this.c) {
            aps aps = (aps) weakReference.get();
            if (aps != null) {
                aps.callback(charSequence, i);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean b() {
        String str = this.f.a().get("hot_word_cache_time");
        aqr aqr = this.f;
        aqr.b = Calendar.getInstance();
        if ((Math.abs(aqr.b.get(6) - aqr.a.getIntValue("lu_ban_hot_word_time_cache", 0)) > 0) || Integer.parseInt(str) <= 0) {
            return false;
        }
        return true;
    }

    public final void c() {
        String str;
        MapSharePreference mapSharePreference = new MapSharePreference((String) "search_history");
        mapSharePreference.putIntValue(SearchHistoryList.SP_KEY_max_display_history_count, 20);
        mapSharePreference.putIntValue(SearchHistoryList.SP_KEY_current_display_history_count, 20);
        if (b()) {
            Map<String, String> a2 = this.f.a();
            this.d = a2.get("hot_word_txt");
            this.a = a2.get("hot_word_txt_color");
            Resources a3 = a();
            if (a3 != null) {
                if (!TextUtils.isEmpty(this.d)) {
                    int color = a3.getColor(R.color.f_c_3);
                    if (!TextUtils.isEmpty(this.a)) {
                        try {
                            color = Color.parseColor(this.a);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    String string = TextUtils.isEmpty(this.d) ? a3.getString(R.string.title_search_hint) : this.d;
                    this.b.setTextColor(color);
                    this.b.setText(string);
                    a((CharSequence) string, color);
                    return;
                }
                int color2 = a3.getColor(R.color.f_c_3);
                String string2 = a3.getString(R.string.title_search_hint);
                this.b.setText(string2);
                this.b.setTextColor(color2);
                a((CharSequence) string2, color2);
            }
            return;
        }
        AnonymousClass1 r0 = new defpackage.aqr.a() {
            public final void a(String str) {
                if (aqu.this.a() != null) {
                    int color = aqu.this.a().getColor(R.color.f_c_3);
                    try {
                        color = Color.parseColor(aqu.this.a);
                    } catch (Exception unused) {
                    }
                    if (TextUtils.isEmpty(str)) {
                        aqu.this.b();
                        return;
                    }
                    aqu.this.b.setTextColor(color);
                    aqu.this.b.setText(str);
                    aqu.this.a((CharSequence) str, color);
                }
            }
        };
        aqr aqr = this.f;
        aqr.c = new aqs();
        SearchWordinBoxRequest searchWordinBoxRequest = new SearchWordinBoxRequest();
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            DPoint a4 = cfg.a((long) latestPosition.x, (long) latestPosition.y);
            StringBuilder sb = new StringBuilder();
            sb.append(a4.x);
            sb.append(",");
            sb.append(a4.y);
            str = sb.toString();
        } else {
            bty mapView = DoNotUseTool.getMapManager().getMapView();
            if (mapView != null) {
                GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
                if (glGeoPoint2GeoPoint != null) {
                    DPoint a5 = cfg.a((long) glGeoPoint2GeoPoint.x, (long) glGeoPoint2GeoPoint.y);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(a5.x);
                    sb2.append(",");
                    sb2.append(a5.y);
                    str = sb2.toString();
                }
            }
            str = "";
        }
        searchWordinBoxRequest.b = str;
        if (!TextUtils.isEmpty(searchWordinBoxRequest.b)) {
            searchWordinBoxRequest.addCustomCommonParam("version", "2.13");
            TransferRequestHolder.getInstance().sendSearchWordinBox(searchWordinBoxRequest, new LuBanDAL$1(aqr, r0));
        }
    }
}
