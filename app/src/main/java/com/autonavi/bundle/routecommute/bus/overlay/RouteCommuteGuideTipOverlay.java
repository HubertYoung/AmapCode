package com.autonavi.bundle.routecommute.bus.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.analytics.utils.Logger;
import com.autonavi.common.Callback.PrepareCallback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.core.network.inter.response.ByteResponse;
import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

public class RouteCommuteGuideTipOverlay extends PointOverlay {
    private static final String TAG = "RouteCommuteGuideTipOverlay";
    private TextView mActionTv;
    private ImageView mCloseIv;
    private View mContentView;
    private TextView mDescriptionTv;
    /* access modifiers changed from: private */
    public ImageView mIconIv;
    /* access modifiers changed from: private */
    public View mRootView;

    static class AbstractBitmapCallback implements PrepareCallback<byte[], Bitmap> {
        private AbstractBitmapCallback() {
        }

        public void callback(Bitmap bitmap) {
            if (bitmap == null) {
                throw new NullPointerException("Decoded bitmap is null");
            }
        }

        public void error(Throwable th, boolean z) {
            Logger.e((String) RouteCommuteGuideTipOverlay.TAG, "An error occurs while dealing with bitmap", th);
        }

        public Bitmap prepare(byte[] bArr) {
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
    }

    @Target({ElementType.PARAMETER, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IconType {
        public static final int COMPANY = 2;
        public static final int HOME = 1;
        public static final int NONE = 0;
        public static final int OTHER = 3;
    }

    public static final class a {
        public int a = 0;
        public String b;
        public String c;
        public final defpackage.aly.a d;
        public final defpackage.aly.a e;
        public GeoPoint f;
        public String g = null;

        public a(String str, String str2, GeoPoint geoPoint, defpackage.aly.a aVar, defpackage.aly.a aVar2) {
            this.b = str;
            this.c = str2;
            this.d = aVar;
            this.e = aVar2;
            this.f = geoPoint;
        }
    }

    public RouteCommuteGuideTipOverlay(bty bty) {
        super(bty);
    }

    public RouteCommuteGuideTipOverlay(int i, bty bty) {
        super(i, bty);
    }

    public void draw(final a aVar) {
        clear();
        if (aVar == null) {
            azb.a(TAG, "bean is null, unable to draw overlay.");
            return;
        }
        Context appContext = AMapPageUtil.getAppContext();
        if (this.mRootView == null) {
            this.mRootView = LayoutInflater.from(appContext).inflate(R.layout.bus_commute_guide_tip_overlay, null);
            this.mRootView.setLayoutParams(new LayoutParams(-2, -2));
            this.mContentView = this.mRootView.findViewById(R.id.cl_route_commute_guide_tip_bubble_content);
            this.mActionTv = (TextView) this.mRootView.findViewById(R.id.tv_route_commute_tip_action);
            this.mDescriptionTv = (TextView) this.mRootView.findViewById(R.id.tv_route_commute_tip_description);
            this.mIconIv = (ImageView) this.mRootView.findViewById(R.id.iv_route_commute_guide_tip_commute_icon);
            this.mCloseIv = (ImageView) this.mRootView.findViewById(R.id.iv_route_commute_guide_tip_close_btn);
        } else {
            azb.a(TAG, "mRootView is set, skip inflation.");
        }
        if (!TextUtils.isEmpty(aVar.b)) {
            this.mActionTv.setText(aVar.b);
        }
        if (!TextUtils.isEmpty(aVar.c)) {
            this.mDescriptionTv.setText(aVar.c);
        }
        if (!TextUtils.isEmpty(aVar.g)) {
            bpf bpf = new bpf();
            bpf.setUrl(aVar.g);
            yq.a();
            yq.a((bph) bpf, (bpl<T>) new bpm<ByteResponse>() {
                public final /* synthetic */ void onSuccess(bpk bpk) {
                    byte[] bArr = (byte[]) ((ByteResponse) bpk).getResult();
                    final Bitmap decodeByteArray = (bArr == null || bArr.length <= 0) ? null : BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
                    if (decodeByteArray == null) {
                        onFailure(null, null);
                    } else {
                        aho.a(new Runnable() {
                            public final void run() {
                                Logger.d((String) RouteCommuteGuideTipOverlay.TAG, "apply downloaded icon to tip view");
                                RouteCommuteGuideTipOverlay.this.mIconIv.setImageBitmap(decodeByteArray);
                                RouteCommuteGuideTipOverlay.this.setClickListeners(aVar);
                                RouteCommuteGuideTipOverlay.this.addOverlayItem(RouteCommuteGuideTipOverlay.this.mRootView, aVar.f);
                            }
                        });
                    }
                }

                public final void onFailure(bph bph, ResponseException responseException) {
                    aho.a(new Runnable() {
                        public final void run() {
                            RouteCommuteGuideTipOverlay.this.mIconIv.setImageDrawable(RouteCommuteGuideTipOverlay.this.mContext.getResources().getDrawable(R.drawable.route_commute_tip_ic_predict_point));
                            RouteCommuteGuideTipOverlay.this.setClickListeners(aVar);
                            RouteCommuteGuideTipOverlay.this.addOverlayItem(RouteCommuteGuideTipOverlay.this.mRootView, aVar.f);
                        }
                    });
                }
            });
            return;
        }
        this.mIconIv.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.route_commute_tip_ic_predict_point));
        setClickListeners(aVar);
        addOverlayItem(this.mRootView, aVar.f);
    }

    /* access modifiers changed from: 0000 */
    public void addOverlayItem(View view, GeoPoint geoPoint) {
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
        pointOverlayItem.mDefaultMarker = createMarker(0, view, 5, 0.0f, 0.0f, false);
        azb.a(TAG, "drawTip");
        addItem(pointOverlayItem);
    }

    /* access modifiers changed from: private */
    public void setClickListeners(a aVar) {
        if (aVar == null) {
            azb.a(TAG, "bean is null, unable to set click listener.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        this.mContentView.measure(0, 0);
        aly aly = new aly(this.mContentView.getMeasuredWidth() + DimensionUtils.dipToPixel(5.0f), this.mContentView.getMeasuredHeight());
        aly.d = true;
        aly.e = aVar.d;
        this.mCloseIv.measure(0, 0);
        aly aly2 = new aly(this.mCloseIv.getMeasuredWidth(), this.mCloseIv.getMeasuredHeight());
        aly2.d = true;
        aly2.e = aVar.e;
        arrayList.add(aly);
        arrayList.add(aly2);
        setClickList(arrayList);
    }
}
