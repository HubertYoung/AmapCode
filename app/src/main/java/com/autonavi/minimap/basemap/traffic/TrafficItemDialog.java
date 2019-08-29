package com.autonavi.minimap.basemap.traffic;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback.a;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.dialog.BaseCompatDialog;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.archive.ArchiveRequestHolder;
import com.autonavi.minimap.archive.param.TrafficeventDetailMoreRequest;
import com.autonavi.minimap.basemap.traffic.TrafficTopic.Type;
import com.autonavi.minimap.basemap.traffic.inter.impl.TrafficRequestManagerImpl;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.server.TrafficAosUICallback;
import com.autonavi.widget.ui.BalloonLayout;
import java.lang.ref.WeakReference;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public final class TrafficItemDialog extends BaseCompatDialog implements OnClickListener, a {
    private int a = -1;
    /* access modifiers changed from: private */
    public final Activity b;
    /* access modifiers changed from: private */
    public TrafficTopic c;
    /* access modifiers changed from: private */
    public WeakReference<bjd> d;
    private ImageView e;
    /* access modifiers changed from: private */
    public ListView f;
    /* access modifiers changed from: private */
    public ImageView g;
    /* access modifiers changed from: private */
    public TextView h;
    /* access modifiers changed from: private */
    public TextView i;
    /* access modifiers changed from: private */
    public ViewGroup j;
    /* access modifiers changed from: private */
    public MapManager k;
    private TrafficRequestManagerImpl l;
    /* access modifiers changed from: private */
    public a m;
    /* access modifiers changed from: private */
    public lg n;
    /* access modifiers changed from: private */
    public PageBundle o;
    /* access modifiers changed from: private */
    public boolean p;
    private OnDismissListener q;
    private View r;
    private View s;
    private View t;
    /* access modifiers changed from: private */
    public View u;
    /* access modifiers changed from: private */
    public View v;
    /* access modifiers changed from: private */
    public TextView w;

    class TrafficDetailCallBack extends FalconAosPrepareResponseCallback<csf> {
        private TrafficDetailCallBack() {
        }

        /* synthetic */ TrafficDetailCallBack(TrafficItemDialog trafficItemDialog, byte b) {
            this();
        }

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            return b(aosByteResponse);
        }

        public final /* synthetic */ void a(Object obj) {
            int i;
            CharSequence charSequence;
            csf csf = (csf) obj;
            bty mapView = TrafficItemDialog.this.k.getMapView();
            if (mapView != null) {
                i = mapView.o(false);
            } else {
                i = 0;
            }
            if (csf == null || csf.b == null) {
                TrafficItemDialog.this.a(3);
                TrafficUtil.logAction(Integer.valueOf(0), (String) null, i);
                if (TrafficItemDialog.this.p && TrafficItemDialog.this.o.containsKey(IOverlayManager.EVENT_LAYERTAG_FROM_ROUTE_RESULT)) {
                    TrafficUtil.logActionFromRouteResult(TrafficItemDialog.this.o.getInt(IOverlayManager.EVENT_LAYERTAG_FROM_ROUTE_RESULT), "B062");
                }
                if (TrafficItemDialog.this.n == null) {
                    TrafficItemDialog.this.n = new lg(BalloonLayout.DEFAULT_DISPLAY_DURATION);
                    TrafficItemDialog.this.n.a(TrafficItemDialog.this);
                }
                TrafficItemDialog.this.n.b();
            } else {
                TrafficItemDialog.this.c = csf.b;
                TrafficItemDialog.this.h.setText(TrafficItemDialog.this.c.getExTopicLayer(TrafficItemDialog.this.p));
                if (!TextUtils.isEmpty(TrafficItemDialog.this.o.getCharSequence(IOverlayManager.EVENT_HEAD_KEY))) {
                    charSequence = TrafficItemDialog.this.o.getCharSequence(IOverlayManager.EVENT_HEAD_KEY);
                } else {
                    charSequence = TrafficItemDialog.this.c.getTopicHead();
                }
                if (TextUtils.isEmpty(charSequence)) {
                    TrafficItemDialog.this.i.setVisibility(8);
                } else {
                    TrafficItemDialog.this.i.setVisibility(0);
                    TrafficItemDialog.this.i.setText(charSequence);
                }
                if (TextUtils.isEmpty(TrafficItemDialog.this.c.getCardinfoUrl()) || TrafficItemDialog.this.p) {
                    TrafficItemDialog.this.v.setVisibility(8);
                } else {
                    LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B001");
                    if (!TextUtils.isEmpty(TrafficItemDialog.this.c.getCardinfoUrlName())) {
                        TrafficItemDialog.this.w.setText(TrafficItemDialog.this.c.getCardinfoUrlName());
                    } else {
                        TrafficItemDialog.this.w.setText("查看详情");
                    }
                    TrafficItemDialog.this.v.setVisibility(0);
                }
                TrafficItemDialog.this.g.setImageResource(TrafficItemDialog.this.c.getHeadImgRes(TrafficItemDialog.this.p));
                TrafficItemDialog.this.f.setAdapter(new TrafficPolyAdapter(TrafficItemDialog.this.c.getAllDetailsTopics(), TrafficItemDialog.this.b));
                if (TrafficItemDialog.this.c.isMultiDetailsReports() && TrafficItemDialog.this.c.getSubDetailTopicCount() < TrafficItemDialog.this.c.getSubTopicCount()) {
                    TrafficItemDialog.this.f.addFooterView(TrafficItemDialog.this.j);
                    ((TextView) TrafficItemDialog.this.j.findViewById(R.id.tv_others)).setText(TrafficItemDialog.this.c.getOtherReports());
                }
                TrafficUtil.logAction(Integer.valueOf(csf.a()), TrafficTopic.LayerTag2Title.get(Integer.valueOf(TrafficItemDialog.this.c.getLayerTag())), i);
                TrafficUtil.logAction(TrafficItemDialog.this.c.getLayerTag(), TrafficItemDialog.this.c.getId());
                int i2 = TrafficItemDialog.this.c.isMultiDetailsReports() ? -1 : -2;
                LayoutParams layoutParams = TrafficItemDialog.this.u.getLayoutParams();
                layoutParams.height = i2;
                TrafficItemDialog.this.u.setLayoutParams(layoutParams);
                TrafficItemDialog.this.a(1);
            }
            TrafficItemDialog.this.m = null;
        }

        private static csf b(AosByteResponse aosByteResponse) {
            csf csf = new csf();
            try {
                csf.a(new JSONObject(aosByteResponse.getResponseBodyString()));
                if (csf.a) {
                    return csf;
                }
                return null;
            } catch (JSONException unused) {
                return null;
            }
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            bty mapView = TrafficItemDialog.this.k.getMapView();
            int o = mapView != null ? mapView.o(false) : 0;
            TrafficItemDialog.this.a(2);
            TrafficItemDialog.this.m = null;
            TrafficUtil.logAction(Integer.valueOf(0), (String) null, o);
        }
    }

    class TrafficPolyAdapter extends BaseAdapter {
        Context mContext;
        Resources mResources;
        List<TrafficTopic> mTopics;

        public class a {
            public TextView a;
            public TextView b;
            public TextView c;
            public ImageView d;
            public TextView e;
            public TextView f;
            public TextView g;
            public TextView h;
            LinearLayout i;

            public a() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public TrafficPolyAdapter(List<TrafficTopic> list, Context context) {
            this.mTopics = list;
            this.mContext = context;
            this.mResources = context.getResources();
        }

        public int getCount() {
            return this.mTopics.size();
        }

        public Object getItem(int i) {
            return this.mTopics.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = LayoutInflater.from(this.mContext).inflate(R.layout.traffic_poly_new_item, null);
                aVar = new a();
                aVar.a = (TextView) view.findViewById(R.id.tv_specific_time);
                aVar.b = (TextView) view.findViewById(R.id.tv_start_time);
                aVar.c = (TextView) view.findViewById(R.id.tv_end_time);
                aVar.e = (TextView) view.findViewById(R.id.content);
                aVar.f = (TextView) view.findViewById(R.id.nick);
                aVar.d = (ImageView) view.findViewById(R.id.img_item_photo_tips);
                aVar.h = (TextView) view.findViewById(R.id.tv_others);
                aVar.i = (LinearLayout) view.findViewById(R.id.traffic_button_layout);
                aVar.g = (TextView) view.findViewById(R.id.tv_report_time);
                view.setTag(aVar);
            } else {
                aVar = (a) view.getTag();
            }
            setItemData(aVar, i);
            return view;
        }

        private void setItemData(a aVar, int i) {
            String str;
            final TrafficTopic trafficTopic = this.mTopics.get(i);
            if (!TextUtils.isEmpty(trafficTopic.getSpecificTime())) {
                aVar.a.setVisibility(0);
                TextView textView = aVar.a;
                StringBuilder sb = new StringBuilder("具体时段：");
                sb.append(trafficTopic.getSpecificTime());
                textView.setText(sb.toString());
            } else {
                aVar.a.setVisibility(8);
            }
            if (!TextUtils.isEmpty(trafficTopic.getStartTime())) {
                aVar.b.setVisibility(0);
                TextView textView2 = aVar.b;
                StringBuilder sb2 = new StringBuilder("开始时间：");
                sb2.append(trafficTopic.getStartTime());
                textView2.setText(sb2.toString());
            } else {
                aVar.b.setVisibility(8);
            }
            if (!TextUtils.isEmpty(trafficTopic.getEndTime())) {
                aVar.c.setVisibility(0);
                TextView textView3 = aVar.c;
                StringBuilder sb3 = new StringBuilder("结束时间：");
                sb3.append(trafficTopic.getEndTime());
                textView3.setText(sb3.toString());
            } else {
                aVar.c.setVisibility(8);
            }
            CharSequence content = trafficTopic.getContent();
            if (TextUtils.isEmpty(content)) {
                aVar.e.setVisibility(8);
            } else {
                aVar.e.setVisibility(0);
                aVar.e.setText(content);
            }
            if (trafficTopic.getNickName() == null || trafficTopic.getNickName().toCharArray().length <= 12) {
                str = trafficTopic.getNickName();
            } else {
                String str2 = new String(trafficTopic.getNickName().toCharArray(), 0, 12);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str2);
                sb4.append("...");
                str = sb4.toString();
            }
            aVar.f.setText(str);
            if ((trafficTopic.getType() == Type.AUTHORITY.ordinal() || trafficTopic.getType() == Type.OFFICCIAL.ordinal()) && !TextUtils.isEmpty(trafficTopic.getNickName())) {
                if ("11".equals(trafficTopic.getSource())) {
                    aVar.f.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.traffic_shenzhen_police, 0);
                } else if (TrafficTopic.SOURCE_TYPE_CHONGQING_EVENT.equals(trafficTopic.getSource())) {
                    aVar.f.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.traffic_chongqing_highway, 0);
                } else {
                    aVar.f.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_official_v, 0);
                }
            }
            aVar.f.setTextColor(this.mResources.getColor(!TextUtils.isEmpty(trafficTopic.getShowUrl()) ? R.color.f_c_6 : R.color.f_c_3));
            aVar.g.setText(getUpdateTime(trafficTopic));
            if (TextUtils.isEmpty(TrafficItemDialog.this.c.getOtherReports()) || TrafficItemDialog.this.c.isMultiDetailsReports()) {
                aVar.h.setVisibility(8);
            } else {
                aVar.h.setText(TrafficItemDialog.this.c.getOtherReports());
                aVar.h.setVisibility(0);
            }
            if (!TextUtils.isEmpty(trafficTopic.getPicUrl())) {
                aVar.d.setVisibility(0);
                int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.traffic_thumbnail_width);
                kt ktVar = new kt();
                ktVar.a(agn.a(this.mContext, 0.5f));
                int i2 = dimensionPixelSize * 2;
                ImageLoader.a(this.mContext.getApplicationContext()).a(TrafficUtil.processImageUrl(trafficTopic.getPicUrl())).a((bjo) ktVar).a(R.drawable.img_item_photo_tips).b(R.drawable.img_item_photo_tips).a(i2, i2).a(aVar.d, (bjl) null);
                aVar.d.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        if (!(TrafficItemDialog.this.d == null || TrafficItemDialog.this.d.get() == null || !((bjd) TrafficItemDialog.this.d.get()).isShowing())) {
                            ((bjd) TrafficItemDialog.this.d.get()).dismiss();
                        }
                        bjd bjd = new bjd(AMapAppGlobal.getTopActivity());
                        bjd.a();
                        bjd.a(trafficTopic.getPicUrl());
                        bjd.show();
                        LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B007");
                        TrafficItemDialog.this.d = new WeakReference(bjd);
                    }
                });
            } else {
                aVar.d.setVisibility(8);
            }
            processButtons(aVar.i, csu.a(this.mContext, trafficTopic.getButtonFlag(), false), trafficTopic);
            csr.a(aVar.f, trafficTopic);
        }

        private void processButtons(LinearLayout linearLayout, SparseArray<View> sparseArray, final TrafficTopic trafficTopic) {
            linearLayout.removeAllViews();
            final int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                View view = sparseArray.get(keyAt);
                boolean z = true;
                if (keyAt == 1) {
                    final TextView textView = (TextView) view.findViewById(R.id.item_view_traffic_text);
                    textView.setText(csr.a(TrafficItemDialog.this.getContext(), trafficTopic.getPraise(), keyAt, size));
                    view.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            TrafficItemDialog.this.a("1", TrafficItemDialog.this.a, textView, trafficTopic, size);
                        }
                    });
                } else if (keyAt == 2) {
                    final TextView textView2 = (TextView) view.findViewById(R.id.item_view_traffic_text);
                    textView2.setText(csr.a(TrafficItemDialog.this.getContext(), trafficTopic.getCriticism(), keyAt, size));
                    view.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            TrafficItemDialog.this.a("0", TrafficItemDialog.this.a, textView2, trafficTopic, size);
                        }
                    });
                } else {
                    if (TrafficItemDialog.this.o != null && TrafficItemDialog.this.o.containsKey("key_open_traffic_later")) {
                        z = TrafficItemDialog.this.o.getBoolean("key_open_traffic_later");
                    }
                    csr.a(view, keyAt, trafficTopic, z);
                }
                linearLayout.addView(view, new LinearLayout.LayoutParams(0, -2, 1.0f));
            }
        }

        private CharSequence getUpdateTime(TrafficTopic trafficTopic) {
            if (trafficTopic == null || TextUtils.isEmpty(trafficTopic.getNickName())) {
                return "";
            }
            if (trafficTopic.getType() == Type.AUTHORITY.ordinal() || trafficTopic.getType() == Type.OFFICCIAL.ordinal()) {
                return ahy.a(" • ", new ForegroundColorSpan(-1907998)).b("最近一次更新").b(lf.a(System.currentTimeMillis(), trafficTopic.getLastUpdateTime() * 1000));
            }
            return ahy.a(" • ", new ForegroundColorSpan(-1907998)).b(lf.a(System.currentTimeMillis(), trafficTopic.getCreateTime() * 1000));
        }
    }

    public final void onTimeReset() {
    }

    public TrafficItemDialog(Activity activity, PageBundle pageBundle, MapManager mapManager) {
        super(activity, R.style.half_transparent_dialog);
        this.b = activity;
        this.o = pageBundle == null ? PageBundle.EMPTY : pageBundle;
        this.a = this.o.getInt(IOverlayManager.FROM_SOURCE_PAGE_KEY, -1);
        this.p = this.o.getBoolean(IOverlayManager.EVENT_IS_FROM_ROUTE_RESULT, false);
        Object object = this.o.getObject(IOverlayManager.TRAFFIC_ITEM_LISTENER);
        if (object instanceof OnDismissListener) {
            this.q = (OnDismissListener) object;
        }
        this.k = mapManager;
        this.l = new TrafficRequestManagerImpl();
        requestWindowFeature(1);
        setContentView(R.layout.traffic_item_dialog);
        getWindow().setLayout(-1, "vivo Z1".equals(Build.MODEL) ? -2 : -1);
        getWindow().setGravity(80);
        getWindow().setWindowAnimations(R.style.traffic_dlg_animation);
        setCanceledOnTouchOutside(true);
        this.e = (ImageView) findViewById(R.id.img_item_close);
        this.e.setVisibility(0);
        this.e.setOnClickListener(this);
        findViewById(R.id.img_item_close_not_success).setOnClickListener(this);
        findViewById(R.id.traffic_item_dialog_layout).setOnClickListener(this);
        findViewById(R.id.traffic_event_status_container).setOnClickListener(this);
        this.f = (ListView) findViewById(R.id.lv_traffic_ploy);
        this.g = (ImageView) findViewById(R.id.layerImage);
        this.h = (TextView) findViewById(R.id.layer_id);
        this.i = (TextView) findViewById(R.id.tv_head);
        this.j = (ViewGroup) LayoutInflater.from(this.b).inflate(R.layout.layout_traffic_item_footer, null);
        this.r = findViewById(R.id.traffic_event_loading);
        this.s = findViewById(R.id.traffic_event_error);
        this.s.setOnClickListener(this);
        this.t = findViewById(R.id.traffic_event_expired);
        this.u = findViewById(R.id.traffic_event_content);
        this.u.setOnClickListener(this);
        this.v = findViewById(R.id.item_url_link_ll);
        this.w = (TextView) findViewById(R.id.item_url_link_tv);
        this.v.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (TrafficItemDialog.this.c != null && !TextUtils.isEmpty(TrafficItemDialog.this.c.getCardinfoUrl())) {
                    LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B002");
                    TrafficItemDialog.a(TrafficItemDialog.this, TrafficItemDialog.this.c.getCardinfoUrl());
                }
            }
        });
        a();
    }

    private void a() {
        int i2 = this.o.getInt(IOverlayManager.EVENT_ID_KEY);
        TrafficeventDetailMoreRequest trafficeventDetailMoreRequest = new TrafficeventDetailMoreRequest();
        trafficeventDetailMoreRequest.b = String.valueOf(i2);
        ArchiveRequestHolder.getInstance().sendTrafficeventDetailMore(trafficeventDetailMoreRequest, new TrafficDetailCallBack(this, 0));
        a(0);
    }

    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_item_close || id == R.id.img_item_close_not_success || id == R.id.traffic_item_dialog_layout) {
            dismiss();
        } else if (view == this.s) {
            if (this.m != null) {
                this.m.cancel();
            }
            a();
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        if (i2 == 0) {
            this.r.setVisibility(0);
            this.s.setVisibility(8);
            this.t.setVisibility(8);
            this.u.setVisibility(8);
        } else if (i2 == 2) {
            this.r.setVisibility(8);
            this.s.setVisibility(0);
            this.t.setVisibility(8);
            this.u.setVisibility(8);
        } else if (i2 == 3) {
            this.r.setVisibility(8);
            this.s.setVisibility(8);
            this.t.setVisibility(0);
            this.u.setVisibility(8);
        } else {
            this.r.setVisibility(8);
            this.s.setVisibility(8);
            this.t.setVisibility(8);
            this.u.setVisibility(0);
            findViewById(R.id.img_item_close_not_success).setVisibility(8);
            findViewById(R.id.img_item_divider_not_success).setVisibility(8);
        }
    }

    public final void onTimeOut() {
        dismiss();
    }

    public final void dismiss() {
        if (this.q != null) {
            this.q.onDismiss(this);
        }
        if (!(this.d == null || this.d.get() == null || !((bjd) this.d.get()).isShowing())) {
            ((bjd) this.d.get()).dismiss();
        }
        if (this.m != null) {
            this.m.cancel();
            this.m = null;
        }
        if (this.n != null && !this.n.c) {
            this.n.a();
        }
        super.dismiss();
    }

    /* access modifiers changed from: private */
    public void a(String str, int i2, TextView textView, TrafficTopic trafficTopic, int i3) {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition == null) {
            ToastHelper.showToast(getContext().getString(R.string.locate_before_traffic_critic));
            return;
        }
        String str2 = str;
        TrafficUtil.logAction(str2, trafficTopic.getLayerTag(), trafficTopic.getType());
        StringBuilder sb = new StringBuilder();
        sb.append(trafficTopic.getId());
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(latestPosition.getLongitude());
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(latestPosition.getLatitude());
        String sb6 = sb5.toString();
        final String str3 = str2;
        final TextView textView2 = textView;
        final TrafficTopic trafficTopic2 = trafficTopic;
        final int i4 = i3;
        AnonymousClass2 r3 = new TrafficAosUICallback() {
            public final void a(int i, String str) {
                if (i == 113) {
                    ToastHelper.showToast(str);
                    return;
                }
                if (i == 0 || i == 2 || i == 3 || i == 4 || i == 7) {
                    ToastHelper.showToast(TrafficItemDialog.this.getContext().getString(R.string.evaluate_fail));
                }
            }

            public final void a(JSONObject jSONObject) {
                String str = str3;
                TextView textView = textView2;
                TrafficTopic trafficTopic = trafficTopic2;
                csn.a().a(trafficTopic);
                if ("1".equals(str)) {
                    int praise = trafficTopic.getPraise() + 1;
                    trafficTopic.setPraise(praise);
                    textView.setText(csr.a(TrafficItemDialog.this.getContext(), praise, 1, i4));
                    return;
                }
                int criticism = trafficTopic.getCriticism() + 1;
                trafficTopic.setCriticism(criticism);
                textView.setText(csr.a(TrafficItemDialog.this.getContext(), criticism, 2, i4));
            }
        };
        TrafficRequestManagerImpl.a(str2, i2, sb2, sb4, sb6, r3);
    }

    static /* synthetic */ void a(TrafficItemDialog trafficItemDialog, String str) {
        aja aja = new aja(str);
        aja.b = new ajf() {
            public final boolean g() {
                return true;
            }

            public final b c() {
                return new b() {
                    public final boolean a() {
                        return true;
                    }

                    public final String b() {
                        return null;
                    }

                    public final long c() {
                        return 1000;
                    }
                };
            }
        };
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null) {
            aix.a(AMapPageUtil.getPageContext(), aja);
        }
    }
}
