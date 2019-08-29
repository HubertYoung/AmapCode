package com.autonavi.minimap.basemap.traffic;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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
import com.autonavi.jni.ae.gmap.scenic.Label3rd;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ITrafficViewForFeed;
import com.autonavi.map.widget.ITrafficViewForFeed.OnDismissListener;
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
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class TrafficGroupViewForFeed extends FrameLayout implements OnClickListener, ITrafficViewForFeed, a {
    private static final String CRITICISM = "0";
    private static final int LOADING_FAILED = 2;
    private static final int LOADING_NO_DATA = 3;
    private static final int LOADING_ON_LOADING = 0;
    private static final int LOADING_SUCCESS = 1;
    private static final String PRAISE = "1";
    private static final String TAG = "TrafficItemCard";
    /* access modifiers changed from: private */
    public int centerMargin = 0;
    private int commentFrom = -1;
    /* access modifiers changed from: private */
    public boolean dismiss = false;
    /* access modifiers changed from: private */
    public TextView displayBtn;
    /* access modifiers changed from: private */
    public boolean isClean = false;
    private boolean isReal3DShowing = false;
    /* access modifiers changed from: private */
    public ArrayList<Label3rd> labelArray = new ArrayList<>();
    /* access modifiers changed from: private */
    public View mContentLayout;
    /* access modifiers changed from: private */
    public final Activity mContext;
    private TrafficeventDetailMoreRequest mCurRequest;
    /* access modifiers changed from: private */
    public View mDisplayTrafficGroup;
    private View mErrorLayout;
    private ImageView mImgClose;
    /* access modifiers changed from: private */
    public WeakReference<bjd> mImgDialogRef;
    /* access modifiers changed from: private */
    public ImageView mImgHeadImage;
    private LayoutInflater mInflater;
    /* access modifiers changed from: private */
    public boolean mIsFromRouteResult = false;
    /* access modifiers changed from: private */
    public View mItemUrlLinkLL;
    /* access modifiers changed from: private */
    public TextView mItemUrlLinkTv;
    /* access modifiers changed from: private */
    public ListView mListView;
    private View mLoadingLayout;
    /* access modifiers changed from: private */
    public MapManager mMapManager;
    private View mNoDataLayout;
    /* access modifiers changed from: private */
    public ViewGroup mOtherReporters;
    /* access modifiers changed from: private */
    public IOverlayManager mOverlayManager;
    /* access modifiers changed from: private */
    public lg mTimeOutWatcher;
    /* access modifiers changed from: private */
    public int mTopMargin = 0;
    /* access modifiers changed from: private */
    public TrafficTopic mTopic;
    /* access modifiers changed from: private */
    public a mTrafficEventCancelable;
    /* access modifiers changed from: private */
    public a mTrafficEventHandler;
    /* access modifiers changed from: private */
    public TextView mTvHead;
    /* access modifiers changed from: private */
    public TextView mTvLayer;
    /* access modifiers changed from: private */
    public float rawSizeWithDip = 0.0f;
    private TrafficRequestManagerImpl reqManager;
    private View rootView;
    /* access modifiers changed from: private */
    public int trafficCardHeight = 0;
    /* access modifiers changed from: private */
    public PageBundle viewData;
    private g zoomListener = new g() {
        public final void updateZoomViewVisibility() {
        }

        public final void updateZoomButtonState(bty bty) {
            if (!TrafficGroupViewForFeed.this.dismiss && bty != null) {
                int p = bty.p(true);
                boolean b = Real3DManager.a().b(false);
                if (p == 0 && b) {
                    int j = bty.e().j();
                    if (!TrafficGroupViewForFeed.this.mIsFromRouteResult) {
                        bty.a(j, false);
                    }
                }
            }
        }
    };

    class TrafficDetailCallBack extends FalconAosPrepareResponseCallback<csf> {
        private TrafficDetailCallBack() {
        }

        /* synthetic */ TrafficDetailCallBack(TrafficGroupViewForFeed trafficGroupViewForFeed, byte b) {
            this();
        }

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            return b(aosByteResponse);
        }

        public final /* synthetic */ void a(Object obj) {
            int i;
            CharSequence charSequence;
            csf csf = (csf) obj;
            bty mapView = TrafficGroupViewForFeed.this.mMapManager.getMapView();
            if (mapView != null) {
                i = mapView.o(false);
            } else {
                i = 0;
            }
            if (csf == null || csf.b == null) {
                TrafficGroupViewForFeed.this.loadingSwitch(3);
                TrafficUtil.logAction(Integer.valueOf(0), (String) null, i);
                if (TrafficGroupViewForFeed.this.mIsFromRouteResult && TrafficGroupViewForFeed.this.viewData.containsKey(IOverlayManager.EVENT_LAYERTAG_FROM_ROUTE_RESULT)) {
                    TrafficGroupViewForFeed.this.viewData.getInt(IOverlayManager.EVENT_LAYERTAG_FROM_ROUTE_RESULT);
                }
                if (TrafficGroupViewForFeed.this.mTimeOutWatcher == null) {
                    TrafficGroupViewForFeed.this.mTimeOutWatcher = new lg(BalloonLayout.DEFAULT_DISPLAY_DURATION);
                    TrafficGroupViewForFeed.this.mTimeOutWatcher.a(TrafficGroupViewForFeed.this);
                }
                TrafficGroupViewForFeed.this.mTimeOutWatcher.b();
            } else {
                TrafficGroupViewForFeed.this.mTopic = csf.b;
                TrafficGroupViewForFeed.this.mTvLayer.setText(TrafficGroupViewForFeed.this.mTopic.getExTopicLayer(TrafficGroupViewForFeed.this.mIsFromRouteResult));
                if (!TextUtils.isEmpty(TrafficGroupViewForFeed.this.viewData.getCharSequence(IOverlayManager.EVENT_HEAD_KEY))) {
                    charSequence = TrafficGroupViewForFeed.this.viewData.getCharSequence(IOverlayManager.EVENT_HEAD_KEY);
                } else {
                    charSequence = TrafficGroupViewForFeed.this.mTopic.getTopicHead();
                }
                if (TextUtils.isEmpty(charSequence)) {
                    TrafficGroupViewForFeed.this.mTvHead.setVisibility(8);
                } else {
                    TrafficGroupViewForFeed.this.mTvHead.setVisibility(0);
                    TrafficGroupViewForFeed.this.mTvHead.setText(charSequence);
                }
                if (!TextUtils.isEmpty(TrafficGroupViewForFeed.this.mTopic.getCardinfoUrl())) {
                    LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B001");
                    if (!TextUtils.isEmpty(TrafficGroupViewForFeed.this.mTopic.getCardinfoUrlName())) {
                        TrafficGroupViewForFeed.this.mItemUrlLinkTv.setText(TrafficGroupViewForFeed.this.mTopic.getCardinfoUrlName());
                    } else {
                        TrafficGroupViewForFeed.this.mItemUrlLinkTv.setText("查看详情");
                    }
                    TrafficGroupViewForFeed.this.mItemUrlLinkLL.setVisibility(0);
                } else {
                    TrafficGroupViewForFeed.this.mItemUrlLinkLL.setVisibility(8);
                }
                TrafficGroupViewForFeed.this.mImgHeadImage.setImageResource(TrafficGroupViewForFeed.this.mTopic.getHeadImgRes(TrafficGroupViewForFeed.this.mIsFromRouteResult));
                new StringBuilder("Count of related traffic event is ").append(TrafficGroupViewForFeed.this.mTopic.getTrafficGroup().size());
                if (TrafficGroupViewForFeed.this.mTopic.getTrafficGroup().size() > 1 && !TrafficGroupViewForFeed.this.mMapManager.getMapView().k(TrafficGroupViewForFeed.this.mMapManager.getMapView().e().j()) && !TrafficGroupViewForFeed.this.mIsFromRouteResult) {
                    TrafficGroupViewForFeed.this.mDisplayTrafficGroup.setVisibility(0);
                    boolean z = TrafficGroupViewForFeed.this.viewData.getBoolean(IOverlayManager.TRAFFIC_GROUP_FLAG_KEY, false);
                    if (!z) {
                        LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B005");
                    }
                    TrafficGroupViewForFeed.this.viewData.getInt(IOverlayManager.EVENT_ID_KEY);
                    if (z) {
                        new Thread() {
                            public final void run() {
                                for (int i = 0; i < TrafficGroupViewForFeed.this.mTopic.getTrafficGroup().size(); i++) {
                                    csa csa = TrafficGroupViewForFeed.this.mTopic.getTrafficGroup().get(i);
                                    Label3rd label3rd = new Label3rd();
                                    label3rd.mMainkey = Integer.parseInt(csa.b);
                                    label3rd.mLabelName = csa.a;
                                    label3rd.mSubkey = Integer.parseInt(csa.c);
                                    label3rd.mMinzoom = csa.e;
                                    label3rd.mRank = (float) csa.d;
                                    label3rd.mPoiId = csa.a;
                                    GeoPoint geoPoint = new GeoPoint(csa.f, csa.g);
                                    label3rd.mP20X = geoPoint.x;
                                    label3rd.mP20Y = geoPoint.y;
                                    TrafficGroupViewForFeed.this.labelArray.add(label3rd);
                                }
                                TrafficGroupViewForFeed.this.mMapManager.getMapView().a(16777216, (Label3rd[]) TrafficGroupViewForFeed.this.labelArray.toArray(new Label3rd[TrafficGroupViewForFeed.this.labelArray.size()]), false);
                            }
                        }.start();
                    }
                }
                ArrayList arrayList = new ArrayList();
                ArrayList<ArrayList<GeoPoint>> arrayList2 = TrafficGroupViewForFeed.this.mTopic.getAffectOverlayData().b;
                ArrayList<GeoPoint> arrayList3 = TrafficGroupViewForFeed.this.mTopic.getAffectOverlayData().d;
                ArrayList arrayList4 = new ArrayList();
                int i2 = -1;
                if (TrafficGroupViewForFeed.this.mOverlayManager != null && arrayList3 != null && arrayList3.size() > 0 && !TrafficGroupViewForFeed.this.mIsFromRouteResult) {
                    arrayList4.add(arrayList3);
                    arrayList.add(TrafficGroupViewForFeed.this.mTopic.getAffectOverlayData().a);
                    bsa bsa = new bsa();
                    bsa.a = new a(arrayList);
                    if (arrayList2 != null && arrayList2.size() > 0) {
                        int i3 = TrafficGroupViewForFeed.this.mTopic.getAffectOverlayData().c;
                        if (i3 != -1) {
                            bsa.b = new b(arrayList2, 0, i3);
                        } else {
                            bsa.b = new b(arrayList2);
                        }
                    }
                    TrafficGroupViewForFeed.this.mOverlayManager.getAffectAreaOverlayManager().a(bsa);
                }
                TrafficGroupViewForFeed.this.mListView.setAdapter(new TrafficPolyAdapter(TrafficGroupViewForFeed.this.mTopic.getAllDetailsTopics(), TrafficGroupViewForFeed.this.mContext));
                if (TrafficGroupViewForFeed.this.mTopic.isMultiDetailsReports() && TrafficGroupViewForFeed.this.mTopic.getSubDetailTopicCount() < TrafficGroupViewForFeed.this.mTopic.getSubTopicCount()) {
                    TrafficGroupViewForFeed.this.mListView.addFooterView(TrafficGroupViewForFeed.this.mOtherReporters);
                    ((TextView) TrafficGroupViewForFeed.this.mOtherReporters.findViewById(R.id.tv_others)).setText(TrafficGroupViewForFeed.this.mTopic.getOtherReports());
                }
                TrafficUtil.logAction(Integer.valueOf(csf.a()), TrafficTopic.LayerTag2Title.get(Integer.valueOf(TrafficGroupViewForFeed.this.mTopic.getLayerTag())), i);
                TrafficUtil.logAction(TrafficGroupViewForFeed.this.mTopic.getLayerTag(), TrafficGroupViewForFeed.this.mTopic.getId());
                if (!TrafficGroupViewForFeed.this.mTopic.isMultiDetailsReports()) {
                    i2 = -2;
                }
                LayoutParams layoutParams = TrafficGroupViewForFeed.this.mContentLayout.getLayoutParams();
                layoutParams.height = i2;
                TrafficGroupViewForFeed.this.mContentLayout.setLayoutParams(layoutParams);
                TrafficGroupViewForFeed.this.loadingSwitch(1);
                if (arrayList3.size() > 0 && arrayList4.size() > 0) {
                    DisplayMetrics displayMetrics = TrafficGroupViewForFeed.this.getResources().getDisplayMetrics();
                    int access$1000 = TrafficGroupViewForFeed.this.mTopMargin;
                    int access$1100 = TrafficGroupViewForFeed.this.centerMargin;
                    int access$11002 = TrafficGroupViewForFeed.this.centerMargin;
                    int access$900 = TrafficGroupViewForFeed.this.trafficCardHeight;
                    int i4 = displayMetrics.widthPixels;
                    int i5 = displayMetrics.heightPixels;
                    ArrayList arrayList5 = (ArrayList) arrayList4.get(0);
                    GeoPoint[] geoPointArr = new GeoPoint[arrayList5.size()];
                    arrayList5.toArray(geoPointArr);
                    Rect bound = TrafficGroupViewForFeed.this.getBound(geoPointArr);
                    a a2 = new a().a(bound, access$1100 + 50, access$1000 + 50, access$11002 + 50, access$900 + 100);
                    bty mapView2 = TrafficGroupViewForFeed.this.mMapManager.getMapView();
                    a a3 = a2.a(mapView2, i4, i5, i4 / 2, (i5 - TrafficGroupViewForFeed.this.trafficCardHeight) / 2);
                    a3.j = 0;
                    a3.a().a();
                }
            }
            TrafficGroupViewForFeed.this.mTrafficEventCancelable = null;
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
            bty mapView = TrafficGroupViewForFeed.this.mMapManager.getMapView();
            int o = mapView != null ? mapView.o(false) : 0;
            TrafficGroupViewForFeed.this.loadingSwitch(2);
            TrafficGroupViewForFeed.this.mTrafficEventCancelable = null;
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

        TrafficPolyAdapter(List<TrafficTopic> list, Context context) {
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
                if (!trafficTopic.isJamReport()) {
                    TextView textView = aVar.a;
                    StringBuilder sb = new StringBuilder("具体时段：");
                    sb.append(trafficTopic.getSpecificTime());
                    textView.setText(sb.toString());
                } else {
                    TextView textView2 = aVar.a;
                    StringBuilder sb2 = new StringBuilder("预计拥堵时段：");
                    sb2.append(trafficTopic.getSpecificTime());
                    textView2.setText(sb2.toString());
                }
            } else {
                aVar.a.setVisibility(8);
            }
            if (!TextUtils.isEmpty(trafficTopic.getStartTime())) {
                aVar.b.setVisibility(0);
                TextView textView3 = aVar.b;
                StringBuilder sb3 = new StringBuilder("开始时间：");
                sb3.append(trafficTopic.getStartTime());
                textView3.setText(sb3.toString());
            } else {
                aVar.b.setVisibility(8);
            }
            if (!TextUtils.isEmpty(trafficTopic.getEndTime())) {
                aVar.c.setVisibility(0);
                TextView textView4 = aVar.c;
                StringBuilder sb4 = new StringBuilder("结束时间：");
                sb4.append(trafficTopic.getEndTime());
                textView4.setText(sb4.toString());
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
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str2);
                sb5.append("...");
                str = sb5.toString();
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
            if (!TrafficGroupViewForFeed.this.mIsFromRouteResult) {
                aVar.f.setTextColor(this.mResources.getColor(!TextUtils.isEmpty(trafficTopic.getShowUrl()) ? R.color.f_c_6 : R.color.f_c_3));
                csr.a(aVar.f, trafficTopic);
            } else {
                aVar.f.setTextColor(this.mResources.getColor(R.color.f_c_3));
            }
            aVar.g.setText(getUpdateTime(trafficTopic));
            if (TextUtils.isEmpty(TrafficGroupViewForFeed.this.mTopic.getOtherReports()) || TrafficGroupViewForFeed.this.mTopic.isMultiDetailsReports()) {
                aVar.h.setVisibility(8);
            } else {
                aVar.h.setText(TrafficGroupViewForFeed.this.mTopic.getOtherReports());
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
                        if (!(TrafficGroupViewForFeed.this.mImgDialogRef == null || TrafficGroupViewForFeed.this.mImgDialogRef.get() == null || !((bjd) TrafficGroupViewForFeed.this.mImgDialogRef.get()).isShowing())) {
                            ((bjd) TrafficGroupViewForFeed.this.mImgDialogRef.get()).dismiss();
                        }
                        bjd bjd = new bjd(AMapAppGlobal.getTopActivity());
                        bjd.a();
                        bjd.a(trafficTopic.getPicUrl());
                        bjd.show();
                        LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B007");
                        TrafficGroupViewForFeed.this.mImgDialogRef = new WeakReference(bjd);
                    }
                });
            } else {
                aVar.d.setVisibility(8);
            }
            processButtons(aVar.i, csu.a(this.mContext, trafficTopic.getButtonFlag(), TrafficGroupViewForFeed.this.mIsFromRouteResult), trafficTopic);
        }

        private void processButtons(LinearLayout linearLayout, SparseArray<View> sparseArray, final TrafficTopic trafficTopic) {
            linearLayout.removeAllViews();
            final int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                View view = sparseArray.get(keyAt);
                if (keyAt == 1) {
                    final TextView textView = (TextView) view.findViewById(R.id.item_view_traffic_text);
                    textView.setText(csr.a(TrafficGroupViewForFeed.this.getContext(), trafficTopic.getPraise(), keyAt, size));
                    view.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            TrafficGroupViewForFeed.this.clickPraise(textView, trafficTopic, size);
                        }
                    });
                } else if (keyAt == 2) {
                    final TextView textView2 = (TextView) view.findViewById(R.id.item_view_traffic_text);
                    textView2.setText(csr.a(TrafficGroupViewForFeed.this.getContext(), trafficTopic.getCriticism(), keyAt, size));
                    view.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            TrafficGroupViewForFeed.this.clickCriticism(textView2, trafficTopic, size);
                        }
                    });
                } else {
                    csr.a(view, keyAt, trafficTopic, true);
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

    public void onTimeReset() {
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
    }

    public void showOn(MapBasePage mapBasePage) {
    }

    public TrafficGroupViewForFeed(Context context, PageBundle pageBundle, MapManager mapManager, a aVar) {
        super(context);
        this.mContext = (Activity) context;
        this.mTrafficEventHandler = aVar;
        this.mMapManager = mapManager;
        this.viewData = pageBundle == null ? PageBundle.EMPTY : pageBundle;
        this.commentFrom = this.viewData.getInt(IOverlayManager.FROM_SOURCE_PAGE_KEY, -1);
        this.mIsFromRouteResult = this.viewData.getBoolean(IOverlayManager.EVENT_IS_FROM_ROUTE_RESULT, false);
        this.isReal3DShowing = this.mMapManager.getMapView().k(this.mMapManager.getMapView().e().j());
        this.mOverlayManager = this.mMapManager.getOverlayManager();
        if (!this.isReal3DShowing) {
            cdd.n().a(this.zoomListener);
            if (this.mMapManager.getMapView().p(true) == 0 && Real3DManager.a().b(false)) {
                int j = this.mMapManager.getMapView().e().j();
                if (!this.mIsFromRouteResult) {
                    this.mMapManager.getMapView().a(j, false);
                }
            }
        }
        this.reqManager = new TrafficRequestManagerImpl();
        initLayout();
        loadingData();
    }

    public void onMapSurfaceChanged() {
        if (this.mDisplayTrafficGroup.getVisibility() == 0 && this.trafficCardHeight > 0) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            bty e = this.mMapManager.getMapView().e();
            e.b(displayMetrics.widthPixels / 2, (displayMetrics.heightPixels - this.trafficCardHeight) / 2);
        }
    }

    public boolean updateTrafficEvent(PageBundle pageBundle) {
        if (pageBundle == null) {
            return false;
        }
        this.viewData = pageBundle;
        this.labelArray.clear();
        clearTrafficItem();
        loadingData();
        return true;
    }

    public void clearTrafficItem() {
        if (this.mMapManager != null) {
            this.mMapManager.getOverlayManager().getTrafficPointOverlay().clear();
        }
    }

    public void dismiss(boolean z) {
        if (this.mCurRequest != null) {
            this.mCurRequest.cancel();
            this.mCurRequest = null;
        }
        this.dismiss = true;
        this.zoomListener = null;
        if (!(this.mImgDialogRef == null || this.mImgDialogRef.get() == null || !((bjd) this.mImgDialogRef.get()).isShowing())) {
            ((bjd) this.mImgDialogRef.get()).dismiss();
        }
        if (this.mTrafficEventCancelable != null) {
            this.mTrafficEventCancelable.cancel();
            this.mTrafficEventCancelable = null;
        }
        if (this.mTimeOutWatcher != null && !this.mTimeOutWatcher.c) {
            this.mTimeOutWatcher.a();
        }
        boolean z2 = (this.mMapManager == null || this.mMapManager.getOverlayManager().getTrafficPointOverlay() == null || this.mMapManager.getOverlayManager().getTrafficPointOverlay().getSize() <= 0) ? false : true;
        StringBuilder sb = new StringBuilder("Real3d is ");
        sb.append(this.isReal3DShowing ? "showing" : "not showed");
        sb.append(";Real 3d is ");
        sb.append(Real3DManager.a().b(false) ? "enabled " : " disabled");
        if (this.mMapManager != null) {
            bty mapView = this.mMapManager.getMapView();
            int p = mapView.p(true);
            if (!z2 && p == 0 && Real3DManager.a().b(false)) {
                int j = mapView.e().j();
                if (!this.mIsFromRouteResult) {
                    mapView.a(j, true);
                }
            }
        }
        if (this.isClean) {
            this.mMapManager.getMapView().b(16777216, false);
            this.isClean = false;
            DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
            this.mMapManager.getMapView().b(displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2);
            this.displayBtn.setText(R.string.show_traffic_event_group);
            this.displayBtn.setTextColor(this.mContext.getResources().getColor(R.color.f_c_2));
            this.displayBtn.setTextSize(1, this.rawSizeWithDip);
            this.labelArray.clear();
        }
        if (this.mTrafficEventHandler != null && !z) {
            this.mTrafficEventHandler.j();
        }
        if (this.mOverlayManager != null && this.mOverlayManager.getAffectAreaOverlayManager() != null) {
            this.mOverlayManager.getAffectAreaOverlayManager().a();
            if (this.mTopic == null || this.mTopic.getAffectOverlayData().d == null || this.mTopic.getAffectOverlayData().d.size() <= 0) {
                DisplayMetrics displayMetrics2 = this.mContext.getResources().getDisplayMetrics();
                this.mMapManager.getMapView().b(displayMetrics2.widthPixels / 2, displayMetrics2.heightPixels / 2);
            }
        }
    }

    public void refreshByScreenState() {
        if (this.rootView.getLayoutParams() == null) {
            this.rootView.setLayoutParams(new LayoutParams(-1, -2));
        }
    }

    private void initLayout() {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(this.mContext);
        }
        this.rootView = this.mInflater.inflate(R.layout.traffic_item_dialog2, this);
        this.rootView.setLayoutParams(new LayoutParams(-1, -1));
        initView();
    }

    public void initView() {
        this.mImgClose = (ImageView) this.rootView.findViewById(R.id.img_item_close);
        this.mImgClose.setVisibility(0);
        this.mImgClose.setOnClickListener(this);
        this.rootView.findViewById(R.id.img_item_close_not_success).setOnClickListener(this);
        this.rootView.findViewById(R.id.traffic_event_status_container).setOnClickListener(this);
        this.mListView = (ListView) this.rootView.findViewById(R.id.lv_traffic_ploy);
        this.mImgHeadImage = (ImageView) this.rootView.findViewById(R.id.layerImage);
        this.mTvLayer = (TextView) this.rootView.findViewById(R.id.layer_id);
        this.mTvHead = (TextView) this.rootView.findViewById(R.id.tv_head);
        this.mDisplayTrafficGroup = this.rootView.findViewById(R.id.displayallbtn);
        this.displayBtn = (TextView) this.rootView.findViewById(R.id.displayallbtn1);
        this.mOtherReporters = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.layout_traffic_item_footer, null);
        this.mLoadingLayout = this.rootView.findViewById(R.id.traffic_event_loading);
        this.mErrorLayout = this.rootView.findViewById(R.id.traffic_event_error);
        this.mErrorLayout.setOnClickListener(this);
        this.mNoDataLayout = this.rootView.findViewById(R.id.traffic_event_expired);
        this.mContentLayout = this.rootView.findViewById(R.id.traffic_event_content);
        TypedValue typedValue = new TypedValue();
        this.mContext.getResources().getValue(R.dimen.f_s_12, typedValue, true);
        this.rawSizeWithDip = TypedValue.complexToFloat(typedValue.data);
        new StringBuilder("There is the font that size is ").append(this.rawSizeWithDip);
        this.mItemUrlLinkLL = this.rootView.findViewById(R.id.item_url_link_ll);
        this.mItemUrlLinkTv = (TextView) this.rootView.findViewById(R.id.item_url_link_tv);
        this.mItemUrlLinkLL.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (TrafficGroupViewForFeed.this.mTopic != null && !TextUtils.isEmpty(TrafficGroupViewForFeed.this.mTopic.getCardinfoUrl())) {
                    LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B002");
                    TrafficGroupViewForFeed.this.jumpUrlLink(TrafficGroupViewForFeed.this.mTopic.getCardinfoUrl());
                }
            }
        });
        this.displayBtn.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                final int size = TrafficGroupViewForFeed.this.mTopic.getTrafficGroup().size();
                TrafficGroupViewForFeed.this.viewData.getInt(IOverlayManager.EVENT_ID_KEY);
                if (TrafficGroupViewForFeed.this.isClean || TrafficGroupViewForFeed.this.mTopic == null || size <= 0) {
                    if (TrafficGroupViewForFeed.this.isClean) {
                        TrafficGroupViewForFeed.this.mMapManager.getMapView().b(16777216, false);
                        TrafficGroupViewForFeed.this.isClean = false;
                        TrafficGroupViewForFeed.this.displayBtn.setText(R.string.show_traffic_event_group);
                        TrafficGroupViewForFeed.this.displayBtn.setTextColor(TrafficGroupViewForFeed.this.mContext.getResources().getColor(R.color.f_c_2));
                        TrafficGroupViewForFeed.this.displayBtn.setTextSize(1, TrafficGroupViewForFeed.this.rawSizeWithDip);
                        TrafficGroupViewForFeed.this.labelArray.clear();
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("type", "隐藏");
                            LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B003", jSONObject);
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                }
                new Thread() {
                    public final void run() {
                        for (int i = 0; i < size; i++) {
                            csa csa = TrafficGroupViewForFeed.this.mTopic.getTrafficGroup().get(i);
                            Label3rd label3rd = new Label3rd();
                            label3rd.mMainkey = Integer.parseInt(csa.b);
                            label3rd.mLabelName = csa.a;
                            label3rd.mSubkey = Integer.parseInt(csa.c);
                            label3rd.mMinzoom = csa.e;
                            label3rd.mRank = (float) csa.d;
                            label3rd.mPoiId = csa.a;
                            GeoPoint geoPoint = new GeoPoint(csa.f, csa.g);
                            label3rd.mP20X = geoPoint.x;
                            label3rd.mP20Y = geoPoint.y;
                            TrafficGroupViewForFeed.this.labelArray.add(label3rd);
                        }
                        TrafficGroupViewForFeed.this.mMapManager.getMapView().a(16777216, (Label3rd[]) TrafficGroupViewForFeed.this.labelArray.toArray(new Label3rd[TrafficGroupViewForFeed.this.labelArray.size()]), false);
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("type", "查看");
                            LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B003", jSONObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                if (TrafficGroupViewForFeed.this.mTrafficEventHandler != null && size > 0 && TrafficGroupViewForFeed.this.trafficCardHeight > 0) {
                    new Thread() {
                        public final void run() {
                            GeoPoint[] geoPointArr = new GeoPoint[size];
                            for (int i = 0; i < size; i++) {
                                csa csa = TrafficGroupViewForFeed.this.mTopic.getTrafficGroup().get(i);
                                geoPointArr[i] = new GeoPoint(csa.f, csa.g);
                            }
                            if (geoPointArr.length > 1) {
                                DisplayMetrics displayMetrics = TrafficGroupViewForFeed.this.getResources().getDisplayMetrics();
                                int applyDimension = (int) TypedValue.applyDimension(1, 44.0f, displayMetrics);
                                int i2 = displayMetrics.widthPixels;
                                int i3 = displayMetrics.heightPixels;
                                Rect bound = TrafficGroupViewForFeed.this.getBound(geoPointArr);
                                a aVar = new a();
                                a a2 = aVar.a(bound, TrafficGroupViewForFeed.this.centerMargin + applyDimension, TrafficGroupViewForFeed.this.mTopMargin + applyDimension, TrafficGroupViewForFeed.this.centerMargin + applyDimension, TrafficGroupViewForFeed.this.trafficCardHeight + applyDimension);
                                bty mapView = TrafficGroupViewForFeed.this.mMapManager.getMapView();
                                a a3 = a2.a(mapView, i2, i3, i2 / 2, (i3 - TrafficGroupViewForFeed.this.trafficCardHeight) / 2);
                                a3.j = 0;
                                a3.a().a();
                            }
                        }
                    }.start();
                }
                TrafficGroupViewForFeed.this.isClean = true;
                TrafficGroupViewForFeed.this.displayBtn.setText(R.string.hide_traffic_event_group);
                TrafficGroupViewForFeed.this.displayBtn.setTextColor(TrafficGroupViewForFeed.this.mContext.getResources().getColor(R.color.f_c_6));
                TrafficGroupViewForFeed.this.displayBtn.setTextSize(1, TrafficGroupViewForFeed.this.rawSizeWithDip);
            }
        });
    }

    public Rect getBound(GeoPoint[] geoPointArr) {
        if (geoPointArr.length == 0) {
            return null;
        }
        int length = geoPointArr.length;
        int i = length > 1000 ? 5 : (length <= 500 || length > 1000) ? (length <= 200 || length > 500) ? (length <= 20 || length > 200) ? 1 : 2 : 3 : 4;
        int i2 = 999999999;
        int i3 = 999999999;
        int i4 = -999999999;
        int i5 = -999999999;
        for (int i6 = 0; i6 < length; i6 += i) {
            i2 = Math.min(i2, geoPointArr[i6].x);
            i3 = Math.min(i3, geoPointArr[i6].y);
            i4 = Math.max(i4, geoPointArr[i6].x);
            i5 = Math.max(i5, geoPointArr[i6].y);
        }
        Rect rect = new Rect();
        rect.set(i2, i3, i4, i5);
        return rect;
    }

    private void loadingData() {
        int i = this.viewData.getInt(IOverlayManager.EVENT_ID_KEY);
        if (this.mTrafficEventCancelable != null) {
            this.mTrafficEventCancelable.cancel();
        }
        this.mCurRequest = new TrafficeventDetailMoreRequest();
        this.mCurRequest.b = String.valueOf(i);
        ArchiveRequestHolder.getInstance().sendTrafficeventDetailMore(this.mCurRequest, new TrafficDetailCallBack(this, 0));
        loadingSwitch(0);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_item_close || id == R.id.img_item_close_not_success) {
            dismiss(false);
        } else if (view == this.mErrorLayout) {
            if (this.mTrafficEventCancelable != null) {
                this.mTrafficEventCancelable.cancel();
            }
            loadingData();
        }
    }

    /* access modifiers changed from: private */
    public void loadingSwitch(int i) {
        boolean z = false;
        if (i == 0) {
            this.mLoadingLayout.setVisibility(0);
            this.mErrorLayout.setVisibility(8);
            this.mNoDataLayout.setVisibility(8);
            this.mContentLayout.setVisibility(8);
            int tipCollapseHeight = getTipCollapseHeight(this.mLoadingLayout);
            if (this.mTrafficEventHandler != null) {
                this.mTrafficEventHandler.a(tipCollapseHeight, 0, false);
            }
        } else if (i == 2) {
            this.mLoadingLayout.setVisibility(8);
            this.mErrorLayout.setVisibility(0);
            this.mNoDataLayout.setVisibility(8);
            this.mContentLayout.setVisibility(8);
            int tipCollapseHeight2 = getTipCollapseHeight(this.mErrorLayout);
            if (this.mTrafficEventHandler != null) {
                this.mTrafficEventHandler.a(tipCollapseHeight2, 0, false);
            }
        } else if (i == 3) {
            this.mLoadingLayout.setVisibility(8);
            this.mErrorLayout.setVisibility(8);
            this.mNoDataLayout.setVisibility(0);
            this.mContentLayout.setVisibility(8);
            int tipCollapseHeight3 = getTipCollapseHeight(this.mNoDataLayout);
            if (this.mTrafficEventHandler != null) {
                this.mTrafficEventHandler.a(tipCollapseHeight3, 0, false);
            }
        } else {
            this.mLoadingLayout.setVisibility(8);
            this.mErrorLayout.setVisibility(8);
            this.mNoDataLayout.setVisibility(8);
            this.mContentLayout.setVisibility(0);
            int tipCollapseHeight4 = getTipCollapseHeight(this.mContentLayout);
            this.trafficCardHeight = tipCollapseHeight4;
            if (!(this.mTrafficEventHandler == null || this.mTrafficEventHandler.e() == null)) {
                this.mTopMargin = this.mTrafficEventHandler.e().getHeight();
                this.centerMargin = this.mTrafficEventHandler.c();
            }
            int tipCollapseHeight5 = this.mDisplayTrafficGroup.getVisibility() == 0 ? getTipCollapseHeight(this.mDisplayTrafficGroup) : 0;
            findViewById(R.id.img_item_close_not_success).setVisibility(8);
            findViewById(R.id.img_item_divider_not_success).setVisibility(8);
            bty mapView = this.mMapManager.getMapView();
            if (mapView != null && !this.isReal3DShowing && mapView.p(true) == 0 && Real3DManager.a().b(false)) {
                int j = mapView.e().j();
                if (mapView.j(j) && !this.mIsFromRouteResult) {
                    mapView.a(j, false);
                }
            }
            StringBuilder sb = new StringBuilder("Height of traffic card is ");
            sb.append(tipCollapseHeight4);
            sb.append(", overlay height of card is ");
            sb.append(tipCollapseHeight5);
            if (this.mTopic != null && this.mTopic.isMultiDetailsReports()) {
                z = true;
            }
            if (this.mTrafficEventHandler != null) {
                this.mTrafficEventHandler.a(tipCollapseHeight4, tipCollapseHeight5, z);
            }
        }
    }

    /* access modifiers changed from: private */
    public void clickPraise(TextView textView, TrafficTopic trafficTopic, int i) {
        clickPraiseAndCriticism("1", this.commentFrom, textView, trafficTopic, i);
    }

    /* access modifiers changed from: private */
    public void clickCriticism(TextView textView, TrafficTopic trafficTopic, int i) {
        clickPraiseAndCriticism("0", this.commentFrom, textView, trafficTopic, i);
    }

    public void onTimeOut() {
        dismiss(false);
    }

    /* access modifiers changed from: private */
    public void drawBounds(int i, int i2, int i3, int i4, int i5, int i6) {
        bty mapView = this.mMapManager.getMapView();
        GeoPoint geoPoint = new GeoPoint(mapView.c(i2, i));
        int i7 = i5 - i3;
        GeoPoint geoPoint2 = new GeoPoint(mapView.c(i7, i));
        int i8 = i6 - i4;
        GeoPoint geoPoint3 = new GeoPoint(mapView.c(i2, i8));
        GeoPoint geoPoint4 = new GeoPoint(mapView.c(i7, i8));
        ArrayList arrayList = new ArrayList();
        arrayList.add(geoPoint);
        arrayList.add(geoPoint3);
        arrayList.add(geoPoint4);
        arrayList.add(geoPoint2);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(arrayList);
        bsa bsa = new bsa();
        bsa.a = new a(arrayList2, 0);
        this.mOverlayManager.getAffectAreaOverlayManager().a(bsa);
    }

    private int getTipCollapseHeight(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(view.getResources().getDisplayMetrics().widthPixels, Integer.MIN_VALUE), 0);
        return view.getMeasuredHeight();
    }

    private int getTipCollapseWidth(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(view.getResources().getDisplayMetrics().widthPixels, Integer.MIN_VALUE), 0);
        return view.getMeasuredWidth();
    }

    /* access modifiers changed from: private */
    public void jumpUrlLink(String str) {
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

    private void clickPraiseAndCriticism(String str, int i, TextView textView, TrafficTopic trafficTopic, int i2) {
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
        final int i3 = i2;
        AnonymousClass4 r3 = new TrafficAosUICallback() {
            public final void a(int i, String str) {
                if (i == 113) {
                    ToastHelper.showToast(str);
                    return;
                }
                if (i == 0 || i == 2 || i == 3 || i == 4 || i == 7) {
                    ToastHelper.showToast(TrafficGroupViewForFeed.this.getContext().getString(R.string.evaluate_fail));
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
                    textView.setText(csr.a(TrafficGroupViewForFeed.this.getContext(), praise, 1, i3));
                    return;
                }
                int criticism = trafficTopic.getCriticism() + 1;
                trafficTopic.setCriticism(criticism);
                textView.setText(csr.a(TrafficGroupViewForFeed.this.getContext(), criticism, 2, i3));
            }
        };
        TrafficRequestManagerImpl.a(str2, i, sb2, sb4, sb6, r3);
    }
}
