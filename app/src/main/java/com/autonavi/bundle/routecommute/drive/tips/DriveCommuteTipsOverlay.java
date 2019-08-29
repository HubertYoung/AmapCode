package com.autonavi.bundle.routecommute.drive.tips;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.bundle.routecommute.common.bean.CommuteControlBean;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.map.overlayholder.OverlayUtil;
import java.util.ArrayList;

public class DriveCommuteTipsOverlay extends PointOverlay {
    private static final String TAG = "DriveCommuteTipsOverlay";
    private baj mCPointItem;
    private bak mNormalItem;

    public void setOnDriveCommuteTipListener(a aVar) {
        this.mNormalItem.l = aVar;
    }

    public void setOnDriveCommuteTipListener(a aVar) {
        this.mCPointItem.i = aVar;
    }

    public DriveCommuteTipsOverlay(bty bty) {
        super(bty);
        setMoveToFocus(false);
        this.mNormalItem = new bak(bty, this);
        this.mCPointItem = new baj(bty, this);
    }

    public void updateView(int i, GeoPoint geoPoint, azu azu) {
        String str;
        String str2;
        String str3;
        bak bak = this.mNormalItem;
        if (azu != null && azu.a != null && azu.a.size() > 0) {
            CommuteControlBean commuteControlBean = a.a.a;
            if (commuteControlBean != null) {
                bak.o = commuteControlBean.isOperateEventEnable("drive");
                bak.p = commuteControlBean.getDriveOperationOptions();
            }
            azt azt = azu.a.get(0);
            String str4 = azu.c;
            if (TextUtils.isEmpty(str4)) {
                str4 = "点击查看详细路线";
            }
            boolean z = true;
            if (azu.b != 1) {
                z = false;
            }
            if (i == 4 || i == 2) {
                if (z) {
                    bak.a = LayoutInflater.from(bak.m.d()).inflate(R.layout.drive_commute_tips_restrict_layout, null);
                } else {
                    bak.a = LayoutInflater.from(bak.m.d()).inflate(R.layout.drive_commute_tips_layout, null);
                }
            } else if (i == 8 || i == 6) {
                bak.a = LayoutInflater.from(bak.m.d()).inflate(R.layout.drive_commute_tips_mine_layout, null);
            }
            bak.a.setLayoutParams(new LayoutParams(-2, -2));
            bak.b = bak.a.findViewById(R.id.commute_tips_close);
            bak.d = (TextView) bak.a.findViewById(R.id.commute_tips_spend_time);
            bak.e = (TextView) bak.a.findViewById(R.id.commute_tips_destination);
            bak.f = (TextView) bak.a.findViewById(R.id.commute_tips_etd_info);
            bak.g = (RainbowBarView) bak.a.findViewById(R.id.commute_rainbow_bar);
            bak.c = (ImageView) bak.a.findViewById(R.id.commute_tips_icon);
            bak.h = (LinearLayout) bak.a.findViewById(R.id.commute_info_layout);
            bak.i = (LinearLayout) bak.a.findViewById(R.id.comute_tips_restrict);
            bak.j = (ImageView) bak.a.findViewById(R.id.commute_tips_arr);
            bak.k = bak.a.findViewById(R.id.commute_tips_arr_restrict);
            if (i == 4 || i == 2) {
                if (i == 4) {
                    if (z || !bak.o) {
                        bak.c.setImageResource(R.drawable.drive_commute_tips_work);
                    } else if (bak.p == null || TextUtils.isEmpty(bak.p.d)) {
                        bak.c.setImageResource(R.drawable.drive_commute_tips_operate);
                    } else {
                        ko.a(bak.c, bak.p.d, null, R.drawable.drive_commute_tips_operate);
                    }
                    str = "去公司";
                    StringBuilder sb = new StringBuilder("约");
                    sb.append(bag.a(azt.b));
                    sb.append("到达");
                    str2 = sb.toString();
                } else {
                    if (z || !bak.o) {
                        bak.c.setImageResource(R.drawable.drive_commute_tips_home);
                    } else if (bak.p == null || TextUtils.isEmpty(bak.p.c)) {
                        bak.c.setImageResource(R.drawable.drive_commute_tips_operate);
                    } else {
                        ko.a(bak.c, bak.p.c, null, R.drawable.drive_commute_tips_operate);
                    }
                    str = "回家";
                    StringBuilder sb2 = new StringBuilder("约");
                    sb2.append(bag.b(azt.b));
                    str2 = sb2.toString();
                }
                bak.a(z);
                if (!z) {
                    TextView textView = bak.e;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append(Token.SEPARATOR);
                    sb3.append(str2);
                    textView.setText(sb3.toString());
                    bak.f.setVisibility(0);
                    bak.f.setText(str4);
                    bak.d.setVisibility(8);
                } else {
                    bak.e.setText(str);
                    bak.d.setVisibility(0);
                    bak.d.setText(str2);
                    bak.f.setVisibility(8);
                }
            } else if (i == 8 || i == 6) {
                NaviAddress b = azf.b();
                if (b != null) {
                    if (i == 8) {
                        if (b.company != null && b.company.getCompany() != null) {
                            StringBuilder sb4 = new StringBuilder("去");
                            sb4.append(b.company.getCompany().getName());
                            str3 = sb4.toString();
                        } else {
                            return;
                        }
                    } else if (b.home != null && b.home.getHome() != null) {
                        StringBuilder sb5 = new StringBuilder("去");
                        sb5.append(b.home.getHome().getName());
                        str3 = sb5.toString();
                    } else {
                        return;
                    }
                    StringBuilder sb6 = new StringBuilder("约");
                    sb6.append(bag.b(azt.b));
                    String sb7 = sb6.toString();
                    bak.e.setText(str3);
                    bak.d.setText(sb7);
                    bak.d.setVisibility(0);
                    bak.f.setVisibility(8);
                    bak.a(z);
                    if (z || !bak.o || bak.p == null || TextUtils.isEmpty(bak.p.c)) {
                        bak.c.setVisibility(8);
                    } else {
                        bak.c.setVisibility(0);
                        ko.a(bak.c, bak.p.c, null, R.drawable.drive_commute_tips_operate);
                        bak.e.setMaxEms(6);
                    }
                }
            }
            bak.g.setRainbowData(azt.c);
            ArrayList arrayList = new ArrayList();
            bak.h.measure(0, 0);
            int measuredWidth = bak.h.getMeasuredWidth();
            int a = agn.a(AMapPageUtil.getAppContext(), 20.0f);
            int a2 = agn.a(AMapPageUtil.getAppContext(), 20.0f);
            alz alz = new alz(measuredWidth, bak.h.getMeasuredHeight() + agn.a(AMapPageUtil.getAppContext(), 33.0f), a, a2);
            alz.e = new a(i) {
                final /* synthetic */ int a;

                {
                    this.a = r2;
                }

                public final void a() {
                    if (bak.this.l != null) {
                        bak.this.l.a();
                        bag.b((String) "0", this.a);
                    }
                }
            };
            arrayList.add(alz);
            if (z) {
                bak.i.measure(0, 0);
                alz alz2 = new alz(bak.i.getMeasuredWidth(), bak.i.getMeasuredHeight() + agn.a(AMapPageUtil.getAppContext(), 33.0f), a + agn.a(AMapPageUtil.getAppContext(), 16.0f) + measuredWidth, a2);
                alz2.e = new a(i) {
                    final /* synthetic */ int a;

                    {
                        this.a = r2;
                    }

                    public final void a() {
                        if (bak.this.l != null) {
                            bak.this.l.c();
                            bag.b((String) "1", this.a);
                        }
                    }
                };
                arrayList.add(alz2);
            }
            bak.b.measure(0, 0);
            int measuredWidth2 = bak.b.getMeasuredWidth();
            int measuredHeight = bak.b.getMeasuredHeight();
            bak.a.measure(0, 0);
            alz alz3 = new alz(measuredWidth2, measuredHeight, bak.a.getMeasuredWidth() - measuredWidth2, 0);
            alz3.e = new a() {
                public final void a() {
                    if (bak.this.l != null) {
                        bak.this.l.b();
                    }
                }
            };
            arrayList.add(alz3);
            bak.n.setClickList(arrayList);
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            View view = bak.a;
            MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 81);
            mapViewLayoutParams.mode = 0;
            bak.m.a(view, (FrameLayout.LayoutParams) mapViewLayoutParams);
            Bitmap convertViewToBitmap = OverlayUtil.convertViewToBitmap(view);
            Marker createMarker = bak.n.createMarker(0, convertViewToBitmap, 5, false);
            bak.m.a(view);
            if (convertViewToBitmap != null && !convertViewToBitmap.isRecycled()) {
                convertViewToBitmap.recycle();
            }
            pointOverlayItem.mDefaultMarker = createMarker;
            bak.n.clear();
            bak.n.addItem(pointOverlayItem);
        }
    }

    public void updateView(int i, GeoPoint geoPoint, azs azs) {
        baj baj = this.mCPointItem;
        if (azs != null && azs.a != null && azs.b != null) {
            azt azt = azs.a;
            azt azt2 = azs.b;
            StringBuilder sb = new StringBuilder("约");
            sb.append(bag.a(azt2.b));
            sb.append("到达");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder("约");
            sb3.append(bag.b(azt.b));
            String sb4 = sb3.toString();
            TextView textView = baj.c;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("回家");
            sb5.append(Token.SEPARATOR);
            sb5.append(sb4);
            textView.setText(sb5.toString());
            TextView textView2 = baj.d;
            StringBuilder sb6 = new StringBuilder();
            sb6.append("去公司");
            sb6.append(Token.SEPARATOR);
            sb6.append(sb2);
            textView2.setText(sb6.toString());
            baj.e.setRainbowData(azt.c);
            baj.f.setRainbowData(azt2.c);
            ArrayList arrayList = new ArrayList();
            baj.g.measure(0, 0);
            int measuredWidth = baj.g.getMeasuredWidth();
            int measuredHeight = baj.g.getMeasuredHeight();
            int a = agn.a(AMapPageUtil.getAppContext(), 20.0f);
            int a2 = agn.a(AMapPageUtil.getAppContext(), 20.0f);
            alz alz = new alz(measuredWidth, measuredHeight, a, a2);
            alz.e = new a() {
                public final void a() {
                    if (baj.this.i != null) {
                        baj.this.i.a();
                    }
                }
            };
            arrayList.add(alz);
            baj.a.measure(0, 0);
            int measuredHeight2 = baj.a.getMeasuredHeight();
            int measuredWidth2 = baj.a.getMeasuredWidth();
            baj.h.measure(0, 0);
            alz alz2 = new alz(baj.h.getMeasuredWidth(), baj.h.getMeasuredHeight() + agn.a(AMapPageUtil.getAppContext(), 20.0f), a, (measuredHeight2 - measuredHeight) - a2);
            alz2.e = new a() {
                public final void a() {
                    if (baj.this.i != null) {
                        baj.this.i.b();
                    }
                }
            };
            arrayList.add(alz2);
            baj.b.measure(0, 0);
            int measuredWidth3 = baj.b.getMeasuredWidth();
            alz alz3 = new alz(measuredWidth3, baj.b.getMeasuredHeight(), measuredWidth2 - measuredWidth3, 0);
            alz3.e = new a() {
                public final void a() {
                    if (baj.this.i != null) {
                        baj.this.i.c();
                    }
                }
            };
            arrayList.add(alz3);
            baj.k.setClickList(arrayList);
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            View view = baj.a;
            MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 81);
            mapViewLayoutParams.mode = 0;
            baj.j.a(view, (FrameLayout.LayoutParams) mapViewLayoutParams);
            Bitmap convertViewToBitmap = OverlayUtil.convertViewToBitmap(view);
            Marker createMarker = baj.k.createMarker(0, convertViewToBitmap, 5, false);
            baj.j.a(view);
            if (convertViewToBitmap != null && !convertViewToBitmap.isRecycled()) {
                convertViewToBitmap.recycle();
            }
            pointOverlayItem.mDefaultMarker = createMarker;
            baj.k.clear();
            baj.k.addItem(pointOverlayItem);
        }
    }
}
