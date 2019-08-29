package com.autonavi.map.search.view;

import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.TextView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.widget.DrawableCenterTextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.minimap.widget.SearchListColorBlockView;
import java.util.ArrayList;

public class PoiTipView extends AbstractPoiTipView {
    public static final int TIP_ADDR = 1024;
    public static final int TIP_ADDRESS = 1010;
    public static final int TIP_BACKGROUND = 1016;
    public static final int TIP_BTN1 = 1003;
    public static final int TIP_BTN2 = 2003;
    public static final int TIP_BTN3 = 1005;
    public static final int TIP_BTN4 = 1012;
    public static final int TIP_DEEP_INFO = 1011;
    public static final int TIP_DISTANT = 1014;
    public static final int TIP_EXTICON = 1013;
    public static final int TIP_ICON = 2005;
    public static final int TIP_POIDETAIL = 1002;
    public static final int TIP_POINAME = 2001;
    public static final int TIP_POI_CLOSE = 2022;
    public static final int TIP_PRICE = 1009;
    public static final int TIP_RATE = 2006;
    public static final int TIP_TAG = 1008;
    public TextView address;
    public TextView avgPrice;
    private SearchListColorBlockView blockView;
    public View btn1;
    public View btn2;
    public View btn3;
    public DrawableCenterTextView btn4;
    public TextView cmsInfo;
    public ImageView cmsInfoDivider;
    public ImageView cprIcon;
    public TextView delete_addr;
    public TextView detail;
    public TextView distance1;
    public TextView distance2;
    public View distance_divider1;
    public View distance_divider2;
    private boolean hasCmsInfo;
    private String[] images = null;
    /* access modifiers changed from: private */
    public boolean isFromFav = false;
    private boolean isLand = false;
    /* access modifiers changed from: private */
    public long lastClickTime;
    public View layout;
    /* access modifiers changed from: private */
    public bid mContext;
    LayoutParams mLayoutParams = new LayoutParams(-2, -2);
    /* access modifiers changed from: private */
    public a mListener;
    public TextView more_station_tv;
    public TextView poiClose;
    public ImageView[] poiIvs = new ImageView[3];
    public TextView poiName;
    public RatingBar ratingBar;
    public ImageView referResultView;
    public TextView roadStat;
    public LinearLayout super_address_tip;
    public TextView tag;
    public OnClickListener tapListener = new OnClickListener() {
        public final void onClick(View view) {
            AMapLog.d("CLICKEVENT", "PoiTipView");
            long currentTimeMillis = System.currentTimeMillis();
            long access$000 = currentTimeMillis - PoiTipView.this.lastClickTime;
            if (access$000 <= 0 || access$000 >= 1000) {
                PoiTipView.this.lastClickTime = currentTimeMillis;
                if (view == PoiTipView.this.layout) {
                    if (PoiTipView.this.mListener != null) {
                        PoiTipView.this.mListener.b(PoiTipView.this.poi);
                    }
                    String str = "poitip";
                    if (PoiTipView.this.mFromSource != null) {
                        str = PoiTipView.this.mFromSource;
                    }
                    PoiTipView.this.goToDetail(PoiTipView.this.poi, str);
                } else if (view == PoiTipView.this.detail) {
                    SearchPoi searchPoi = (SearchPoi) PoiTipView.this.poi.as(SearchPoi.class);
                    if (PoiTipView.this.mListener != null) {
                        PoiTipView.this.mListener.a((POI) searchPoi);
                    }
                    String str2 = "poitip";
                    if (PoiTipView.this.mFromSource != null) {
                        str2 = PoiTipView.this.mFromSource;
                    }
                    PoiTipView.this.goToDetail(PoiTipView.this.poi, str2);
                } else if (view == PoiTipView.this.btn1) {
                    if (PoiTipView.this.mListener != null) {
                        PoiTipView.this.mListener.c(PoiTipView.this.poi);
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("POI", PoiTipView.this.poi);
                    PoiTipView.this.mContext.startPage((String) "amap.search.action.category", pageBundle);
                } else if (view == PoiTipView.this.btn2) {
                    if (PoiTipView.this.mListener != null) {
                        PoiTipView.this.mListener.d(PoiTipView.this.poi);
                    }
                    bax bax = (bax) a.a.a(bax.class);
                    if (bax != null) {
                        PageBundle pageBundle2 = new PageBundle();
                        pageBundle2.putObject("bundle_key_poi_end", PoiTipView.this.poi.clone());
                        pageBundle2.putObject("bundle_key_auto_route", Boolean.TRUE);
                        if (PoiTipView.this.isFromFav) {
                            pageBundle2.putString("bundle_key_from_page", "collect_go");
                        }
                        bax.a(pageBundle2);
                    }
                } else if (view == PoiTipView.this.btn3) {
                    if (PoiTipView.this.mListener != null) {
                        PoiTipView.this.mListener.e(PoiTipView.this.poi);
                    }
                    dfm dfm = (dfm) ank.a(dfm.class);
                    if (dfm != null) {
                        dfm.a(PoiTipView.this.poi);
                    }
                } else {
                    if (view == PoiTipView.this.btn4) {
                        if (PoiTipView.this.mListener != null) {
                            PoiTipView.this.mListener.a(view);
                        }
                        String obj = PoiTipView.this.btn4.getTag().toString();
                        if ("share".equals(obj)) {
                            dct dct = new dct();
                            dct.a = true;
                            dct.b = false;
                            dct.f = true;
                            dct.d = true;
                            dct.e = true;
                            dct.j = true;
                            dct.c = false;
                            dct.k = false;
                            dct.i = true;
                            dct.l = true;
                            dcb dcb = (dcb) a.a.a(dcb.class);
                            if (dcb != null) {
                                dcb.a(DoNotUseTool.getContext(), dct, PoiTipView.this.poi.clone(), ConfigerHelper.getInstance().getShareMsgUrl());
                            }
                        } else if (!"scenic_route".equals(obj)) {
                            if ("tel".equals(obj)) {
                                PoiTipView.this.showTelPanel(PoiTipView.this.poi, 11101, PoiTipView.this.btn4.getText().toString());
                            } else if (WidgetType.INDOOR_GUIDE.equals(obj)) {
                                if (!aaw.c(DoNotUseTool.getContext())) {
                                    ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_message));
                                    return;
                                }
                                SearchPoi searchPoi2 = (SearchPoi) PoiTipView.this.poi.as(SearchPoi.class);
                                if (searchPoi2.getTemplateDataMap() != null) {
                                    String value = ((PoiLayoutTemplate) searchPoi2.getTemplateDataMap().get(Integer.valueOf(1012))).getValue();
                                    if (!TextUtils.isEmpty(value)) {
                                        Uri parse = Uri.parse(value);
                                        Intent intent = new Intent();
                                        intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
                                        intent.setData(parse);
                                        DoNotUseTool.startScheme(intent);
                                    }
                                }
                            } else if (AMapAppGlobal.getApplication().getString(R.string.add_poi).equals(obj)) {
                                IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                                if (iErrorReportStarter != null) {
                                    iErrorReportStarter.startAddPoi(PoiTipView.this.poi);
                                }
                            } else {
                                String str3 = "poitip";
                                if (PoiTipView.this.mFromSource != null) {
                                    str3 = PoiTipView.this.mFromSource;
                                }
                                PoiTipView.this.goToDetail(PoiTipView.this.poi, str3);
                            }
                        }
                    }
                }
            }
        }
    };

    public View getView() {
        return this;
    }

    public PoiTipView(ViewGroup viewGroup, bid bid) {
        super(viewGroup, bid.getContext());
        this.parent = viewGroup;
        this.mContext = bid;
        init();
    }

    private void init() {
        this.mRootView = this.mLayoutInflater.inflate(R.layout.poi_layout_tip_template, null);
        this.poiName = (TextView) this.mRootView.findViewById(R.id.poi_name);
        this.roadStat = (TextView) this.mRootView.findViewById(R.id.road_stat);
        this.poiIvs[0] = (ImageView) this.mRootView.findViewById(R.id.poi_iv_1);
        this.poiIvs[1] = (ImageView) this.mRootView.findViewById(R.id.poi_iv_2);
        this.poiIvs[2] = (ImageView) this.mRootView.findViewById(R.id.poi_iv_3);
        this.cprIcon = (ImageView) this.mRootView.findViewById(R.id.cpr_icon);
        this.detail = (TextView) this.mRootView.findViewById(R.id.detail);
        this.detail.setOnClickListener(this.tapListener);
        this.distance1 = (TextView) this.mRootView.findViewById(R.id.distance1);
        this.distance2 = (TextView) this.mRootView.findViewById(R.id.distance2);
        this.distance_divider1 = this.mRootView.findViewById(R.id.divider1);
        this.distance_divider2 = this.mRootView.findViewById(R.id.divider2);
        this.ratingBar = (RatingBar) this.mRootView.findViewById(R.id.rating_bar);
        this.avgPrice = (TextView) this.mRootView.findViewById(R.id.avgprice);
        this.tag = (TextView) this.mRootView.findViewById(R.id.tag);
        this.poiClose = (TextView) this.mRootView.findViewById(R.id.close);
        this.address = (TextView) this.mRootView.findViewById(R.id.address);
        this.delete_addr = (TextView) this.mRootView.findViewById(R.id.delete_addr);
        this.more_station_tv = (TextView) this.mRootView.findViewById(R.id.more_station_tv);
        this.super_address_tip = (LinearLayout) this.mRootView.findViewById(R.id.super_address_tip);
        this.cmsInfo = (TextView) this.mRootView.findViewById(R.id.cms_info);
        this.cmsInfoDivider = (ImageView) this.mRootView.findViewById(R.id.cms_info_divider);
        this.btn1 = this.mRootView.findViewById(R.id.btn1);
        this.btn1.setOnClickListener(this.tapListener);
        this.btn2 = this.mRootView.findViewById(R.id.btn2);
        this.btn2.setOnClickListener(this.tapListener);
        this.btn3 = this.mRootView.findViewById(R.id.btn3);
        this.btn3.setOnClickListener(this.tapListener);
        this.btn4 = (DrawableCenterTextView) this.mRootView.findViewById(R.id.btn4);
        this.btn4.setOnClickListener(this.tapListener);
        this.layout = this.mRootView.findViewById(R.id.main_layout);
        this.blockView = (SearchListColorBlockView) this.mRootView.findViewById(R.id.super_addr_color_block);
        this.referResultView = (ImageView) this.mRootView.findViewById(R.id.refer_result);
        this.layout.setOnClickListener(this.tapListener);
        addView(this.mRootView, new FrameLayout.LayoutParams(-1, ags.b(DoNotUseTool.getActivity()) ? AbstractPoiDetailView.TIPSHEIGHTINLAND : AbstractPoiDetailView.TIPSHEIGHTINPORT));
    }

    private void setAddressWithCostTime(String str) {
        if (!TextUtils.isEmpty(str)) {
            String format = String.format(getResources().getString(R.string.cost_time_start), new Object[]{str});
            SpannableString spannableString = new SpannableString(format);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.f_c_3)), 0, 5, 17);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.cost_time_text_red_color)), 5, format.length(), 17);
            this.address.setText(spannableString);
        }
    }

    public void setTipItemEvent(a aVar) {
        this.mListener = aVar;
    }

    private void setIsFromFav() {
        if (this.poi != null && this.poi.getPoiExtra().containsKey(IOverlayManager.POI_EXTRA_FROM_FAV)) {
            this.isFromFav = true;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:39|(2:41|42)|43|44) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x0181 */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x02ea A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0319 A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x0348 A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0372 A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x03a6 A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x03be A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x03dc A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x03fa A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x0424 A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x04fe A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x051c A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x0531 A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x057c A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x05c1 A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0272 A[Catch:{ ClassCastException -> 0x0685 }] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x029c A[Catch:{ ClassCastException -> 0x0685 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initData(com.autonavi.bundle.entity.infolite.internal.InfoliteResult r20, com.autonavi.common.model.POI r21, int r22) {
        /*
            r19 = this;
            r0 = r19
            r1 = r21
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            r0.poi = r1
            r19.setIsFromFav()
            r2 = r20
            r0.dataCenter = r2
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r2 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r2 = r1.as(r2)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2
            java.util.Map r2 = r2.getTemplateDataMap()
            r3 = 8
            r4 = 0
            if (r2 == 0) goto L_0x0685
            int r5 = r2.size()
            if (r5 <= 0) goto L_0x0685
            r5 = 2001(0x7d1, float:2.804E-42)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r6 = r2.get(r6)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r6 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r6     // Catch:{ ClassCastException -> 0x0685 }
            r7 = -2
            r8 = 1
            if (r6 == 0) goto L_0x018a
            int r9 = r6.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 != 0) goto L_0x018a
            android.widget.TextView r9 = r0.poiName     // Catch:{ ClassCastException -> 0x0685 }
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 == 0) goto L_0x004d
            android.widget.TextView r9 = r0.poiName     // Catch:{ ClassCastException -> 0x0685 }
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()     // Catch:{ ClassCastException -> 0x0685 }
            r9.width = r7     // Catch:{ ClassCastException -> 0x0685 }
        L_0x004d:
            android.widget.TextView r9 = r0.poiName     // Catch:{ ClassCastException -> 0x0685 }
            r9.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r9 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r9 = r1.as(r9)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r9 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r9     // Catch:{ ClassCastException -> 0x0685 }
            int r9 = r9.getReferenceRltFlag()     // Catch:{ ClassCastException -> 0x0685 }
            r10 = 2
            if (r9 != r8) goto L_0x010b
            java.lang.String r9 = r6.getValue()     // Catch:{ ClassCastException -> 0x0685 }
            java.util.HashMap r11 = r21.getPoiExtra()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r12 = "titleName"
            java.lang.Object r11 = r11.get(r12)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ ClassCastException -> 0x0685 }
            boolean r11 = android.text.TextUtils.isEmpty(r11)     // Catch:{ ClassCastException -> 0x0685 }
            if (r11 != 0) goto L_0x0089
            java.util.HashMap r9 = r21.getPoiExtra()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r11 = "titleName"
            java.lang.Object r9 = r9.get(r11)     // Catch:{ ClassCastException -> 0x0685 }
            java.io.Serializable r9 = (java.io.Serializable) r9     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r9 = r9.toString()     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0089:
            android.widget.TextView r11 = r0.poiName     // Catch:{ ClassCastException -> 0x0685 }
            r11.setText(r9)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r9 = r0.poiName     // Catch:{ ClassCastException -> 0x0685 }
            r9.measure(r4, r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r9 = r0.poiName     // Catch:{ ClassCastException -> 0x0685 }
            int r9 = r9.getMeasuredWidth()     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r11 = r0.detail     // Catch:{ ClassCastException -> 0x0685 }
            r11.measure(r4, r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r11 = r0.detail     // Catch:{ ClassCastException -> 0x0685 }
            int r11 = r11.getMeasuredWidth()     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.ImageView r12 = r0.referResultView     // Catch:{ ClassCastException -> 0x0685 }
            r12.measure(r4, r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.ImageView r12 = r0.referResultView     // Catch:{ ClassCastException -> 0x0685 }
            int r12 = r12.getMeasuredWidth()     // Catch:{ ClassCastException -> 0x0685 }
            android.app.Activity r13 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getActivity()     // Catch:{ ClassCastException -> 0x0685 }
            android.content.res.Resources r13 = r13.getResources()     // Catch:{ ClassCastException -> 0x0685 }
            int r14 = com.autonavi.minimap.R.dimen.default_margin_6A     // Catch:{ ClassCastException -> 0x0685 }
            int r13 = r13.getDimensionPixelOffset(r14)     // Catch:{ ClassCastException -> 0x0685 }
            android.content.Context r14 = r19.getContext()     // Catch:{ ClassCastException -> 0x0685 }
            android.graphics.Rect r14 = defpackage.ags.a(r14)     // Catch:{ ClassCastException -> 0x0685 }
            int r14 = r14.width()     // Catch:{ ClassCastException -> 0x0685 }
            double r14 = (double) r14     // Catch:{ ClassCastException -> 0x0685 }
            r16 = 4606281698874543309(0x3feccccccccccccd, double:0.9)
            double r14 = r14 * r16
            int r14 = (int) r14     // Catch:{ ClassCastException -> 0x0685 }
            r15 = 1073741824(0x40000000, float:2.0)
            int r14 = android.view.View.MeasureSpec.makeMeasureSpec(r14, r15)     // Catch:{ ClassCastException -> 0x0685 }
            int r14 = android.view.View.MeasureSpec.getSize(r14)     // Catch:{ ClassCastException -> 0x0685 }
            android.graphics.Rect r15 = new android.graphics.Rect     // Catch:{ ClassCastException -> 0x0685 }
            r15.<init>()     // Catch:{ ClassCastException -> 0x0685 }
            android.view.View r7 = r0.mRootView     // Catch:{ ClassCastException -> 0x0685 }
            android.graphics.drawable.Drawable r7 = r7.getBackground()     // Catch:{ ClassCastException -> 0x0685 }
            r7.getPadding(r15)     // Catch:{ ClassCastException -> 0x0685 }
            int r7 = r15.right     // Catch:{ ClassCastException -> 0x0685 }
            int r7 = java.lang.Math.abs(r7)     // Catch:{ ClassCastException -> 0x0685 }
            int r15 = r15.left     // Catch:{ ClassCastException -> 0x0685 }
            int r15 = java.lang.Math.abs(r15)     // Catch:{ ClassCastException -> 0x0685 }
            int r7 = r7 + r15
            int r14 = r14 - r12
            int r14 = r14 - r11
            int r13 = r13 * 2
            int r14 = r14 - r13
            int r14 = r14 - r7
            if (r9 <= r14) goto L_0x0105
            android.widget.ImageView r7 = r0.referResultView     // Catch:{ ClassCastException -> 0x0685 }
            r7.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x0167
        L_0x0105:
            android.widget.ImageView r7 = r0.referResultView     // Catch:{ ClassCastException -> 0x0685 }
            r7.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x0167
        L_0x010b:
            android.widget.ImageView r7 = r0.referResultView     // Catch:{ ClassCastException -> 0x0685 }
            r7.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            java.util.HashMap r7 = r21.getPoiExtra()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r9 = "pointType"
            boolean r7 = r7.containsKey(r9)     // Catch:{ ClassCastException -> 0x0685 }
            if (r7 == 0) goto L_0x012f
            java.util.HashMap r7 = r21.getPoiExtra()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r9 = "pointType"
            java.lang.Object r7 = r7.get(r9)     // Catch:{ ClassCastException -> 0x0685 }
            java.io.Serializable r7 = (java.io.Serializable) r7     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Integer r7 = (java.lang.Integer) r7     // Catch:{ ClassCastException -> 0x0685 }
            int r7 = r7.intValue()     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x0130
        L_0x012f:
            r7 = 0
        L_0x0130:
            if (r7 == r8) goto L_0x015e
            if (r7 != r10) goto L_0x0135
            goto L_0x015e
        L_0x0135:
            java.lang.String r7 = r6.getValue()     // Catch:{ ClassCastException -> 0x0685 }
            java.util.HashMap r9 = r21.getPoiExtra()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r10 = "titleName"
            java.lang.Object r9 = r9.get(r10)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ ClassCastException -> 0x0685 }
            boolean r9 = android.text.TextUtils.isEmpty(r9)     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 != 0) goto L_0x0162
            java.util.HashMap r7 = r21.getPoiExtra()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r9 = "titleName"
            java.lang.Object r7 = r7.get(r9)     // Catch:{ ClassCastException -> 0x0685 }
            java.io.Serializable r7 = (java.io.Serializable) r7     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r7 = r7.toString()     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x0162
        L_0x015e:
            java.lang.String r7 = r6.getValue()     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0162:
            android.widget.TextView r9 = r0.poiName     // Catch:{ ClassCastException -> 0x0685 }
            r9.setText(r7)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0167:
            boolean r7 = r6 instanceof com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate     // Catch:{ ClassCastException -> 0x0685 }
            if (r7 == 0) goto L_0x018f
            com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate r6 = (com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate) r6     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r6 = r6.getColor()     // Catch:{ ClassCastException -> 0x0685 }
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch:{ ClassCastException -> 0x0685 }
            if (r7 != 0) goto L_0x0181
            android.widget.TextView r7 = r0.poiName     // Catch:{ IllegalArgumentException -> 0x0181 }
            int r6 = android.graphics.Color.parseColor(r6)     // Catch:{ IllegalArgumentException -> 0x0181 }
            r7.setTextColor(r6)     // Catch:{ IllegalArgumentException -> 0x0181 }
            goto L_0x018f
        L_0x0181:
            android.widget.TextView r6 = r0.poiName     // Catch:{ ClassCastException -> 0x0685 }
            r7 = -14606047(0xffffffffff212121, float:-2.1417772E38)
            r6.setTextColor(r7)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x018f
        L_0x018a:
            android.widget.TextView r6 = r0.poiName     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x018f:
            r6 = 1024(0x400, float:1.435E-42)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r6 = r2.get(r6)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r6 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r6     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 == 0) goto L_0x01b8
            int r7 = r6.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r7 != 0) goto L_0x01b8
            boolean r7 = r6 instanceof com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate     // Catch:{ ClassCastException -> 0x0685 }
            if (r7 == 0) goto L_0x01bd
            android.widget.TextView r7 = r0.delete_addr     // Catch:{ ClassCastException -> 0x0685 }
            r7.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r6 = (com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate) r6     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r7 = r0.delete_addr     // Catch:{ ClassCastException -> 0x0685 }
            android.text.Spanned r6 = r6.getSpanned()     // Catch:{ ClassCastException -> 0x0685 }
            r7.setText(r6)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x01bd
        L_0x01b8:
            android.widget.TextView r6 = r0.delete_addr     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x01bd:
            r6 = 1010(0x3f2, float:1.415E-42)
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r6 = r2.get(r6)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r6 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r6     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r7 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r7 = r1.as(r7)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r7 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r7     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r7 = r7.getCostTime()     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 == 0) goto L_0x0250
            int r9 = r6.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 != 0) goto L_0x0250
            android.widget.TextView r9 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r10 = ""
            r9.setText(r10)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r9 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            r9.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            boolean r9 = android.text.TextUtils.isEmpty(r7)     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 == 0) goto L_0x023c
            boolean r7 = r0.isUseNameAsAddress(r6)     // Catch:{ ClassCastException -> 0x0685 }
            if (r7 == 0) goto L_0x01ff
            android.widget.TextView r5 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r7 = r21.getName()     // Catch:{ ClassCastException -> 0x0685 }
            r5.setText(r7)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x022c
        L_0x01ff:
            java.lang.String r7 = r6.getValue()     // Catch:{ ClassCastException -> 0x0685 }
            if (r7 == 0) goto L_0x0227
            java.lang.String r9 = r7.trim()     // Catch:{ ClassCastException -> 0x0685 }
            boolean r9 = android.text.TextUtils.isEmpty(r9)     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 == 0) goto L_0x0227
            java.lang.Integer r9 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r9 = r2.get(r9)     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 == 0) goto L_0x0227
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r7 = r5.getValue()     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0227:
            android.widget.TextView r5 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            r5.setText(r7)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x022c:
            android.widget.TextView r5 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            android.content.res.Resources r7 = r19.getResources()     // Catch:{ ClassCastException -> 0x0685 }
            int r9 = com.autonavi.minimap.R.color.f_c_3     // Catch:{ ClassCastException -> 0x0685 }
            int r7 = r7.getColor(r9)     // Catch:{ ClassCastException -> 0x0685 }
            r5.setTextColor(r7)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x023f
        L_0x023c:
            r0.setAddressWithCostTime(r7)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x023f:
            java.lang.String r5 = r6.getValue()     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x0264
            java.lang.String r5 = r6.getValue()     // Catch:{ ClassCastException -> 0x0685 }
            int r5 = r5.length()     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 != 0) goto L_0x0264
            goto L_0x025f
        L_0x0250:
            boolean r5 = android.text.TextUtils.isEmpty(r7)     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 != 0) goto L_0x025f
            r0.setAddressWithCostTime(r7)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r5 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x0264
        L_0x025f:
            android.widget.TextView r5 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0264:
            r5 = 2023(0x7e7, float:2.835E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x0289
            int r6 = r5.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 != 0) goto L_0x0289
            android.widget.TextView r6 = r0.roadStat     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r6 = r0.roadStat     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            android.text.Spanned r5 = r5.getSpanned()     // Catch:{ ClassCastException -> 0x0685 }
            r6.setText(r5)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x028e
        L_0x0289:
            android.widget.TextView r5 = r0.roadStat     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x028e:
            r5 = 2006(0x7d6, float:2.811E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x02d7
            int r6 = r5.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 != 0) goto L_0x02d7
            r6 = -1
            java.lang.String r7 = r5.getValue()     // Catch:{ Exception -> 0x02bf }
            boolean r7 = com.autonavi.minimap.search.utils.SearchUtils.isNumeric(r7)     // Catch:{ Exception -> 0x02bf }
            if (r7 == 0) goto L_0x02bf
            java.lang.String r5 = r5.getValue()     // Catch:{ Exception -> 0x02bf }
            java.lang.Float r5 = java.lang.Float.valueOf(r5)     // Catch:{ Exception -> 0x02bf }
            float r5 = r5.floatValue()     // Catch:{ Exception -> 0x02bf }
            r6 = 1092616192(0x41200000, float:10.0)
            float r5 = r5 * r6
            int r5 = (int) r5
            r6 = r5
        L_0x02bf:
            if (r6 <= 0) goto L_0x02d1
            android.widget.RatingBar r5 = r0.ratingBar     // Catch:{ ClassCastException -> 0x0685 }
            r5.setProgress(r6)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r5 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.RatingBar r5 = r0.ratingBar     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x02dc
        L_0x02d1:
            android.widget.RatingBar r5 = r0.ratingBar     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x02dc
        L_0x02d7:
            android.widget.RatingBar r5 = r0.ratingBar     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x02dc:
            r5 = 1008(0x3f0, float:1.413E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x0306
            int r6 = r5.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 != 0) goto L_0x0306
            android.widget.TextView r6 = r0.tag     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r6 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r6 = r0.tag     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            android.text.Spanned r5 = r5.getSpanned()     // Catch:{ ClassCastException -> 0x0685 }
            r6.setText(r5)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x030b
        L_0x0306:
            android.widget.TextView r5 = r0.tag     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x030b:
            r5 = 1009(0x3f1, float:1.414E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x0335
            int r6 = r5.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 != 0) goto L_0x0335
            android.widget.TextView r6 = r0.avgPrice     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r6 = r0.address     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r6 = r0.avgPrice     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            android.text.Spanned r5 = r5.getSpanned()     // Catch:{ ClassCastException -> 0x0685 }
            r6.setText(r5)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x033a
        L_0x0335:
            android.widget.TextView r5 = r0.avgPrice     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x033a:
            r5 = 2022(0x7e6, float:2.833E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x035f
            int r6 = r5.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 != 0) goto L_0x035f
            android.widget.TextView r6 = r0.poiClose     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r6 = r0.poiClose     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            android.text.Spanned r5 = r5.getSpanned()     // Catch:{ ClassCastException -> 0x0685 }
            r6.setText(r5)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x0364
        L_0x035f:
            android.widget.TextView r5 = r0.poiClose     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0364:
            r5 = 1011(0x3f3, float:1.417E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x0390
            int r6 = r5.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 != 0) goto L_0x0390
            android.widget.ImageView r6 = r0.cmsInfoDivider     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r6 = r0.cmsInfo     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            android.text.Spanned r5 = r5.getSpanned()     // Catch:{ ClassCastException -> 0x0685 }
            r6.setText(r5)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r5 = r0.cmsInfo     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            r0.hasCmsInfo = r8     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x039c
        L_0x0390:
            r0.hasCmsInfo = r4     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r5 = r0.cmsInfo     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.ImageView r5 = r0.cmsInfoDivider     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x039c:
            android.app.Activity r5 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getActivity()     // Catch:{ ClassCastException -> 0x0685 }
            boolean r5 = defpackage.ags.b(r5)     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x03b0
            android.widget.TextView r5 = r0.cmsInfo     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.ImageView r5 = r0.cmsInfoDivider     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x03b0:
            r5 = 1003(0x3eb, float:1.406E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x03ce
            android.view.View r6 = r0.btn1     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            android.view.View r6 = r0.btn1     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r5 = r5.getAction()     // Catch:{ ClassCastException -> 0x0685 }
            r6.setTag(r5)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x03ce:
            r5 = 2003(0x7d3, float:2.807E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x03ec
            android.view.View r6 = r0.btn2     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            android.view.View r6 = r0.btn2     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r5 = r5.getAction()     // Catch:{ ClassCastException -> 0x0685 }
            r6.setTag(r5)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x03ec:
            r5 = 1005(0x3ed, float:1.408E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x0411
            int r6 = r5.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 != 0) goto L_0x0411
            android.view.View r6 = r0.btn3     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            android.view.View r6 = r0.btn3     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r5 = r5.getAction()     // Catch:{ ClassCastException -> 0x0685 }
            r6.setTag(r5)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x0416
        L_0x0411:
            android.view.View r5 = r0.btn3     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0416:
            r5 = 1012(0x3f4, float:1.418E-42)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r5 = r2.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r5 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x04a7
            int r6 = r5.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 != 0) goto L_0x04a7
            com.autonavi.map.widget.DrawableCenterTextView r6 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            r6.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            r6 = r5
            com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate r6 = (com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate) r6     // Catch:{ ClassCastException -> 0x0685 }
            java.util.HashMap r7 = EXT_BTN_TEXT     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r9 = r6.getAction()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r7 = r7.get(r9)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ ClassCastException -> 0x0685 }
            boolean r9 = android.text.TextUtils.isEmpty(r7)     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 != 0) goto L_0x0449
            com.autonavi.map.widget.DrawableCenterTextView r9 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            r9.setText(r7)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0449:
            java.util.HashMap r9 = EXT_BTN_DRAWABLE     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r10 = r6.getAction()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r9 = r9.get(r10)     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 == 0) goto L_0x0466
            java.util.HashMap r9 = EXT_BTN_DRAWABLE     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r10 = r6.getAction()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r9 = r9.get(r10)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ ClassCastException -> 0x0685 }
            int r9 = r9.intValue()     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x0467
        L_0x0466:
            r9 = 0
        L_0x0467:
            java.lang.String r10 = "tel"
            r11 = r5
            com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate r11 = (com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate) r11     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r11 = r11.getAction()     // Catch:{ ClassCastException -> 0x0685 }
            boolean r10 = r10.equals(r11)     // Catch:{ ClassCastException -> 0x0685 }
            if (r10 == 0) goto L_0x0480
            com.autonavi.map.widget.DrawableCenterTextView r10 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            boolean r5 = r5.isEnable()     // Catch:{ ClassCastException -> 0x0685 }
            r10.setEnabled(r5)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0480:
            com.autonavi.map.widget.DrawableCenterTextView r5 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            r5.setCompoundDrawablesWithIntrinsicBounds(r9, r4, r4, r4)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.map.widget.DrawableCenterTextView r5 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r9 = r6.getAction()     // Catch:{ ClassCastException -> 0x0685 }
            r5.setTag(r9)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.map.widget.DrawableCenterTextView r5 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            android.view.View$OnClickListener r9 = r0.tapListener     // Catch:{ ClassCastException -> 0x0685 }
            r5.setOnClickListener(r9)     // Catch:{ ClassCastException -> 0x0685 }
            boolean r5 = android.text.TextUtils.isEmpty(r7)     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 != 0) goto L_0x04ac
            java.lang.String r5 = r6.getAction()     // Catch:{ ClassCastException -> 0x0685 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 != 0) goto L_0x04ac
            r5 = 1
            goto L_0x04ad
        L_0x04a7:
            com.autonavi.map.widget.DrawableCenterTextView r5 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x04ac:
            r5 = 0
        L_0x04ad:
            if (r5 != 0) goto L_0x04e7
            java.lang.String r1 = r21.getId()     // Catch:{ ClassCastException -> 0x0685 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ ClassCastException -> 0x0685 }
            if (r1 == 0) goto L_0x04e7
            java.util.HashMap r1 = EXT_BTN_DRAWABLE     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r5 = "add_poi"
            java.lang.Object r1 = r1.get(r5)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ ClassCastException -> 0x0685 }
            int r1 = r1.intValue()     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.map.widget.DrawableCenterTextView r5 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            r5.setCompoundDrawablesWithIntrinsicBounds(r1, r4, r4, r4)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.map.widget.DrawableCenterTextView r1 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            int r5 = com.autonavi.minimap.R.string.add_poi     // Catch:{ ClassCastException -> 0x0685 }
            r1.setText(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.map.widget.DrawableCenterTextView r1 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            android.app.Application r5 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ ClassCastException -> 0x0685 }
            int r6 = com.autonavi.minimap.R.string.add_poi     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r5 = r5.getString(r6)     // Catch:{ ClassCastException -> 0x0685 }
            r1.setTag(r5)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.map.widget.DrawableCenterTextView r1 = r0.btn4     // Catch:{ ClassCastException -> 0x0685 }
            r1.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x04e7:
            android.widget.TextView r1 = r0.detail     // Catch:{ ClassCastException -> 0x0685 }
            r1.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            r1 = 2005(0x7d5, float:2.81E-42)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r1 = r2.get(r1)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r1 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r1     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.ImageView[] r5 = r0.poiIvs     // Catch:{ ClassCastException -> 0x0685 }
            int r6 = r5.length     // Catch:{ ClassCastException -> 0x0685 }
            r7 = 0
        L_0x04fc:
            if (r7 >= r6) goto L_0x0508
            r9 = r5[r7]     // Catch:{ ClassCastException -> 0x0685 }
            if (r9 == 0) goto L_0x0505
            r9.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0505:
            int r7 = r7 + 1
            goto L_0x04fc
        L_0x0508:
            if (r1 == 0) goto L_0x0523
            int r5 = r1.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 != 0) goto L_0x0523
            com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate r1 = (com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate) r1     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String[] r1 = r1.getSrc()     // Catch:{ ClassCastException -> 0x0685 }
            r0.images = r1     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String[] r1 = r0.images     // Catch:{ ClassCastException -> 0x0685 }
            if (r1 == 0) goto L_0x0523
            android.widget.ImageView[] r1 = r0.poiIvs     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String[] r5 = r0.images     // Catch:{ ClassCastException -> 0x0685 }
            r0.resetPoiIvs(r1, r5)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0523:
            r1 = 1013(0x3f5, float:1.42E-42)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r1 = r2.get(r1)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r1 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r1     // Catch:{ ClassCastException -> 0x0685 }
            if (r1 == 0) goto L_0x0569
            int r5 = r1.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 != 0) goto L_0x0569
            r5 = r1
            com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String[] r5 = r5.getSrc()     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 == 0) goto L_0x0563
            r5 = r1
            com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate r5 = (com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate) r5     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String[] r5 = r5.getSrc()     // Catch:{ ClassCastException -> 0x0685 }
            int r5 = r5.length     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 <= 0) goto L_0x0563
            android.widget.ImageView r5 = r0.cprIcon     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.ImageView r5 = r0.cprIcon     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate r1 = (com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate) r1     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String[] r1 = r1.getSrc()     // Catch:{ ClassCastException -> 0x0685 }
            r1 = r1[r4]     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r6 = "drawable"
            int r1 = r0.getResourceId(r1, r6)     // Catch:{ ClassCastException -> 0x0685 }
            r5.setBackgroundResource(r1)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x056e
        L_0x0563:
            android.widget.ImageView r1 = r0.cprIcon     // Catch:{ ClassCastException -> 0x0685 }
            r1.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x056e
        L_0x0569:
            android.widget.ImageView r1 = r0.cprIcon     // Catch:{ ClassCastException -> 0x0685 }
            r1.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x056e:
            r1 = 1014(0x3f6, float:1.421E-42)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r1 = r2.get(r1)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r1 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r1     // Catch:{ ClassCastException -> 0x0685 }
            if (r1 == 0) goto L_0x05a9
            int r5 = r1.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r5 != 0) goto L_0x05a9
            android.widget.TextView r5 = r0.distance1     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.view.View r5 = r0.distance_divider1     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.view.View r5 = r0.distance_divider2     // Catch:{ ClassCastException -> 0x0685 }
            r5.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r5 = r0.distance1     // Catch:{ ClassCastException -> 0x0685 }
            r6 = r1
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r6 = (com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate) r6     // Catch:{ ClassCastException -> 0x0685 }
            android.text.Spanned r6 = r6.getSpanned()     // Catch:{ ClassCastException -> 0x0685 }
            r5.setText(r6)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r5 = r0.distance2     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r1 = (com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate) r1     // Catch:{ ClassCastException -> 0x0685 }
            android.text.Spanned r1 = r1.getSpanned()     // Catch:{ ClassCastException -> 0x0685 }
            r5.setText(r1)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x05b3
        L_0x05a9:
            android.widget.TextView r1 = r0.distance1     // Catch:{ ClassCastException -> 0x0685 }
            r1.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
            android.view.View r1 = r0.distance_divider1     // Catch:{ ClassCastException -> 0x0685 }
            r1.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x05b3:
            r1 = 1016(0x3f8, float:1.424E-42)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.Object r1 = r2.get(r1)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.search.templete.type.PoiLayoutTemplate r1 = (com.autonavi.minimap.search.templete.type.PoiLayoutTemplate) r1     // Catch:{ ClassCastException -> 0x0685 }
            if (r1 == 0) goto L_0x0680
            int r2 = r1.isShown()     // Catch:{ ClassCastException -> 0x0685 }
            if (r2 != 0) goto L_0x0680
            android.widget.LinearLayout r2 = r0.super_address_tip     // Catch:{ ClassCastException -> 0x0685 }
            r2.setVisibility(r4)     // Catch:{ ClassCastException -> 0x0685 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ ClassCastException -> 0x0685 }
            r2.<init>()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r1 = r1.getValue()     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r2 = ";"
            java.lang.String[] r1 = r1.split(r2)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.LinearLayout$LayoutParams r2 = new android.widget.LinearLayout$LayoutParams     // Catch:{ ClassCastException -> 0x0685 }
            r5 = -2
            r2.<init>(r5, r5)     // Catch:{ ClassCastException -> 0x0685 }
            r5 = 10
            r2.setMargins(r4, r4, r5, r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r2 = r0.detail     // Catch:{ ClassCastException -> 0x0685 }
            r2.measure(r4, r4)     // Catch:{ ClassCastException -> 0x0685 }
            android.widget.TextView r2 = r0.distance2     // Catch:{ ClassCastException -> 0x0685 }
            r2.measure(r4, r4)     // Catch:{ ClassCastException -> 0x0685 }
            int r2 = r1.length     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock[] r2 = new com.autonavi.minimap.widget.SearchListColorBlockView.ColorBlock[r2]     // Catch:{ ClassCastException -> 0x0685 }
            r5 = -9079435(0xffffffffff757575, float:-3.2627073E38)
            r6 = 0
            r7 = -9079435(0xffffffffff757575, float:-3.2627073E38)
        L_0x05fa:
            int r9 = r1.length     // Catch:{ ClassCastException -> 0x0685 }
            if (r6 >= r9) goto L_0x066d
            r9 = r1[r6]     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r10 = "\\|"
            java.lang.String[] r9 = r9.split(r10)     // Catch:{ ClassCastException -> 0x0685 }
            int r10 = r9.length     // Catch:{ ClassCastException -> 0x0685 }
            if (r10 == 0) goto L_0x066a
            com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock r10 = new com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock     // Catch:{ ClassCastException -> 0x0685 }
            r10.<init>()     // Catch:{ ClassCastException -> 0x0685 }
            int r11 = r9.length     // Catch:{ ClassCastException -> 0x0685 }
            if (r11 <= r8) goto L_0x065a
            r11 = r9[r4]     // Catch:{ ClassCastException -> 0x0685 }
            boolean r11 = android.text.TextUtils.isEmpty(r11)     // Catch:{ ClassCastException -> 0x0685 }
            if (r11 != 0) goto L_0x0668
            r11 = r9[r8]     // Catch:{ ClassCastException -> 0x0685 }
            boolean r11 = android.text.TextUtils.isEmpty(r11)     // Catch:{ ClassCastException -> 0x0685 }
            if (r11 != 0) goto L_0x0668
            r11 = r9[r4]     // Catch:{ ClassCastException -> 0x0685 }
            r10.mText = r11     // Catch:{ ClassCastException -> 0x0685 }
            int r11 = r1.length     // Catch:{ ClassCastException -> 0x0685 }
            int r11 = r11 - r8
            if (r6 != r11) goto L_0x0643
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r11 = "#"
            r7.<init>(r11)     // Catch:{ ClassCastException -> 0x0685 }
            r11 = r9[r8]     // Catch:{ ClassCastException -> 0x0685 }
            r7.append(r11)     // Catch:{ ClassCastException -> 0x0685 }
            java.lang.String r7 = r7.toString()     // Catch:{ ClassCastException -> 0x0685 }
            int r7 = android.graphics.Color.parseColor(r7)     // Catch:{ IllegalArgumentException -> 0x063d }
            goto L_0x0643
        L_0x063d:
            java.lang.String r7 = "#999999"
            int r7 = android.graphics.Color.parseColor(r7)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0643:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0666 }
            java.lang.String r12 = "#"
            r11.<init>(r12)     // Catch:{ IllegalArgumentException -> 0x0666 }
            r9 = r9[r8]     // Catch:{ IllegalArgumentException -> 0x0666 }
            r11.append(r9)     // Catch:{ IllegalArgumentException -> 0x0666 }
            java.lang.String r9 = r11.toString()     // Catch:{ IllegalArgumentException -> 0x0666 }
            int r9 = android.graphics.Color.parseColor(r9)     // Catch:{ IllegalArgumentException -> 0x0666 }
            r10.mColor = r9     // Catch:{ IllegalArgumentException -> 0x0666 }
            goto L_0x0668
        L_0x065a:
            r11 = r9[r4]     // Catch:{ ClassCastException -> 0x0685 }
            boolean r11 = android.text.TextUtils.isEmpty(r11)     // Catch:{ ClassCastException -> 0x0685 }
            if (r11 != 0) goto L_0x0666
            r9 = r9[r4]     // Catch:{ ClassCastException -> 0x0685 }
            r10.mText = r9     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0666:
            r10.mColor = r5     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0668:
            r2[r6] = r10     // Catch:{ ClassCastException -> 0x0685 }
        L_0x066a:
            int r6 = r6 + 1
            goto L_0x05fa
        L_0x066d:
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r1 = new com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo     // Catch:{ ClassCastException -> 0x0685 }
            r1.<init>()     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.widget.SearchListColorBlockView r5 = r0.blockView     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r1 = r1.setBlocks(r2)     // Catch:{ ClassCastException -> 0x0685 }
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r1 = r1.setBlockColor(r7)     // Catch:{ ClassCastException -> 0x0685 }
            r5.setItemInfo(r1)     // Catch:{ ClassCastException -> 0x0685 }
            goto L_0x0685
        L_0x0680:
            android.widget.LinearLayout r1 = r0.super_address_tip     // Catch:{ ClassCastException -> 0x0685 }
            r1.setVisibility(r3)     // Catch:{ ClassCastException -> 0x0685 }
        L_0x0685:
            android.widget.TextView r1 = r0.distance1
            int r1 = r1.getVisibility()
            if (r1 != 0) goto L_0x06dd
            android.widget.RatingBar r1 = r0.ratingBar
            int r1 = r1.getVisibility()
            if (r1 != r3) goto L_0x06dd
            android.widget.TextView r1 = r0.tag
            int r1 = r1.getVisibility()
            if (r1 != r3) goto L_0x06dd
            android.widget.TextView r1 = r0.avgPrice
            int r1 = r1.getVisibility()
            if (r1 != r3) goto L_0x06dd
            android.widget.TextView r1 = r0.distance1
            r1.setVisibility(r3)
            android.view.View r1 = r0.distance_divider1
            r1.setVisibility(r3)
            android.widget.TextView r1 = r0.distance2
            r1.setVisibility(r4)
            android.widget.LinearLayout r1 = r0.super_address_tip
            int r1 = r1.getVisibility()
            if (r1 == 0) goto L_0x06d7
            android.widget.TextView r1 = r0.address
            int r1 = r1.getVisibility()
            if (r1 != 0) goto L_0x06d1
            android.widget.TextView r1 = r0.address
            java.lang.CharSequence r1 = r1.getText()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x06d1
            goto L_0x06d7
        L_0x06d1:
            android.view.View r1 = r0.distance_divider2
            r1.setVisibility(r3)
            goto L_0x06e7
        L_0x06d7:
            android.view.View r1 = r0.distance_divider2
            r1.setVisibility(r4)
            goto L_0x06e7
        L_0x06dd:
            android.widget.TextView r1 = r0.distance2
            r1.setVisibility(r3)
            android.view.View r1 = r0.distance_divider2
            r1.setVisibility(r3)
        L_0x06e7:
            android.widget.RatingBar r1 = r0.ratingBar
            int r1 = r1.getVisibility()
            if (r1 == 0) goto L_0x06ff
            android.widget.TextView r1 = r0.tag
            int r1 = r1.getVisibility()
            if (r1 == 0) goto L_0x06ff
            android.widget.TextView r1 = r0.avgPrice
            int r1 = r1.getVisibility()
            if (r1 != 0) goto L_0x070c
        L_0x06ff:
            android.widget.TextView r1 = r0.cmsInfo
            int r1 = r1.getVisibility()
            if (r1 != 0) goto L_0x070c
            android.widget.TextView r1 = r0.address
            r1.setVisibility(r3)
        L_0x070c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.search.view.PoiTipView.initData(com.autonavi.bundle.entity.infolite.internal.InfoliteResult, com.autonavi.common.model.POI, int):void");
    }

    /* access modifiers changed from: protected */
    public void goToDetail(POI poi, String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("POI", poi);
        pageBundle.putString("fromSource", str);
        pageBundle.putObject("poi_search_result", this.dataCenter);
        if (this.superId != null) {
            pageBundle.putSerializable("SUPER_ID", this.superId);
        }
        pageBundle.putInt("poi_detail_page_type", 5);
        this.mContext.startPageForResult((String) "amap.search.action.poidetail", pageBundle, 1111);
    }

    /* access modifiers changed from: protected */
    public void showTelPanel(POI poi, int i, String str) {
        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
        if (searchPoi.getTemplateDataMap() != null) {
            String value = ((PoiLayoutTemplate) searchPoi.getTemplateDataMap().get(Integer.valueOf(1012))).getValue();
            String type = poi.getType();
            if (type.length() >= 4) {
                type = type.substring(0, 4);
            }
            if ("1001".equals(type) || "1002".equals(type)) {
                ArrayList arrayList = new ArrayList();
                if (!TextUtils.isEmpty(value)) {
                    if (value.indexOf(59) >= 0) {
                        String[] split = value.split(";");
                        for (int i2 = 0; i2 < split.length; i2++) {
                            if (split[i2].substring(0, 3).equals("400")) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(String.format(AMapAppGlobal.getApplication().getString(R.string.book_by_phone), new Object[]{split[i2]}));
                                sb.append("$");
                                sb.append(split[i2]);
                                arrayList.add(sb.toString());
                            } else {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(String.format(AMapAppGlobal.getApplication().getString(R.string.reception_phone), new Object[]{split[i2]}));
                                sb2.append("$");
                                sb2.append(split[i2]);
                                arrayList.add(sb2.toString());
                            }
                        }
                    } else if (value.substring(0, 3).equals("400")) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(String.format(AMapAppGlobal.getApplication().getString(R.string.book_by_phone), new Object[]{value}));
                        sb3.append("$");
                        sb3.append(value);
                        arrayList.add(sb3.toString());
                    } else {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(String.format(AMapAppGlobal.getApplication().getString(R.string.reception_phone), new Object[]{value}));
                        sb4.append("$");
                        sb4.append(value);
                        arrayList.add(sb4.toString());
                    }
                }
                if (arrayList.size() > 0) {
                    bnz.a(arrayList, DoNotUseTool.getActivity());
                }
            } else if (!TextUtils.isEmpty(value)) {
                if (value.indexOf(";") > 0) {
                    ArrayList arrayList2 = new ArrayList();
                    String[] split2 = value.split(";");
                    for (int i3 = 0; i3 < split2.length; i3++) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(split2[i3]);
                        sb5.append("$");
                        sb5.append(split2[i3]);
                        arrayList2.add(sb5.toString());
                    }
                    if (arrayList2.size() > 0) {
                        bnz.a(arrayList2, DoNotUseTool.getActivity());
                    }
                    return;
                }
                getContext();
                bnz.a(value);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.images == null || this.isLand) {
            this.poiName.setMaxWidth(Integer.MAX_VALUE);
            this.poiName.setLayoutParams(this.mLayoutParams);
        } else {
            setTextViewMaxWidth(this.images.length, this.parent, this.poiIvs, this.detail, this.poiName);
        }
        super.onMeasure(i, i2);
    }

    public void adjustMargin() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mRootView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.setMargins(0, 0, 0, 0);
            layoutParams.gravity = 1;
            this.mRootView.setLayoutParams(layoutParams);
        }
    }

    public void refreshByScreenState(boolean z) {
        this.isLand = z;
        if (this.mRootView.getLayoutParams() == null) {
            this.mRootView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        }
        if (this.mRootView.getLayoutParams() != null) {
            this.mRootView.getLayoutParams().height = z ? AbstractPoiDetailView.TIPSHEIGHTINLAND : AbstractPoiDetailView.TIPSHEIGHTINPORT;
            int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.portrait_tip_min_height);
            int dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.landscape_tip_min_height);
            View findViewById = this.mRootView.findViewById(R.id.poi_info_ll);
            if (z) {
                dimensionPixelSize = dimensionPixelSize2;
            }
            findViewById.setMinimumHeight(dimensionPixelSize);
        }
        if (!this.hasCmsInfo || z) {
            this.cmsInfo.setVisibility(8);
            this.cmsInfoDivider.setVisibility(0);
            return;
        }
        this.cmsInfoDivider.setVisibility(8);
        this.cmsInfo.setVisibility(0);
    }

    public POI getPoi() {
        return this.poi;
    }

    public void setAddressTip(String str) {
        this.address.setVisibility(0);
        this.address.setText(str);
    }

    private boolean isUseNameAsAddress(PoiLayoutTemplate poiLayoutTemplate) {
        if (this.isFromFav && this.poi.getPoiExtra().containsKey("pointType")) {
            int intValue = ((Integer) this.poi.getPoiExtra().get("pointType")).intValue();
            if ((intValue == 1 || intValue == 2) && ((poiLayoutTemplate == null || "".equals(poiLayoutTemplate.getValue().trim())) && !TextUtils.isEmpty(this.poi.getName()))) {
                this.poi.setAddr(this.poi.getName());
                return true;
            }
        }
        return false;
    }
}
