package com.amap.bundle.drive.guide;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.drive.api.ITruckGuideManager;
import com.amap.bundle.drive.api.ITruckGuideManager.a;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;

public class TruckGuideManagerImpl implements ITruckGuideManager {
    /* access modifiers changed from: private */
    public RouteType a = null;
    /* access modifiers changed from: private */
    public WeakReference<View> b;
    private WeakReference<a> c;

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001a, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean isGuideShowing() {
        /*
            r2 = this;
            monitor-enter(r2)
            java.lang.ref.WeakReference<android.view.View> r0 = r2.b     // Catch:{ all -> 0x001d }
            r1 = 0
            if (r0 == 0) goto L_0x001b
            java.lang.ref.WeakReference<android.view.View> r0 = r2.b     // Catch:{ all -> 0x001d }
            java.lang.Object r0 = r0.get()     // Catch:{ all -> 0x001d }
            android.view.View r0 = (android.view.View) r0     // Catch:{ all -> 0x001d }
            if (r0 == 0) goto L_0x0019
            int r0 = r0.getVisibility()     // Catch:{ all -> 0x001d }
            if (r0 != 0) goto L_0x0019
            r0 = 1
            monitor-exit(r2)
            return r0
        L_0x0019:
            monitor-exit(r2)
            return r1
        L_0x001b:
            monitor-exit(r2)
            return r1
        L_0x001d:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.guide.TruckGuideManagerImpl.isGuideShowing():boolean");
    }

    public synchronized void checkShowGuide(final IRouteUI iRouteUI) {
        if (iRouteUI != null) {
            if (((!DriveUtil.isNeedGuideTruck() || TextUtils.isEmpty(DriveUtil.getTruckCarPlateNumber())) ? (char) 0 : 2) != 0) {
                this.a = iRouteUI.g();
                iRouteUI.b(RouteType.TRUCK);
                if (iRouteUI != null) {
                    final RouteType routeType = RouteType.TRUCK;
                    View inflate = LayoutInflater.from(AMapAppGlobal.getTopActivity()).inflate(R.layout.old_user_truck_guide_view, null);
                    this.b = new WeakReference<>(inflate);
                    ImageView imageView = (ImageView) inflate.findViewById(R.id.icon_guide_truck);
                    ((TextView) inflate.findViewById(R.id.start_experience)).setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            iRouteUI.b(TruckGuideManagerImpl.this.a);
                            TruckGuideManagerImpl.this.a(false);
                            if (TruckGuideManagerImpl.this.b != null) {
                                View view2 = (View) TruckGuideManagerImpl.this.b.get();
                                if (view2 != null) {
                                    view2.setVisibility(8);
                                    iRouteUI.b(view2);
                                }
                                TruckGuideManagerImpl.this.b = null;
                            }
                        }
                    });
                    imageView.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            if (TruckGuideManagerImpl.this.b != null) {
                                View view2 = (View) TruckGuideManagerImpl.this.b.get();
                                if (view2 != null) {
                                    view2.setVisibility(8);
                                    iRouteUI.b(view2);
                                }
                                TruckGuideManagerImpl.this.b = null;
                            }
                            iRouteUI.a(routeType);
                            TruckGuideManagerImpl.this.a(false);
                        }
                    });
                    int m = iRouteUI.m();
                    int c2 = iRouteUI.c(routeType);
                    AMapLog.i("TruckGuideManager", "tabTruckViewX:".concat(String.valueOf(c2)));
                    int intrinsicWidth = AMapAppGlobal.getApplication().getResources().getDrawable(R.drawable.guide_truck_icon).getIntrinsicWidth();
                    LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
                    layoutParams.topMargin = m - AMapAppGlobal.getApplication().getResources().getDrawable(R.drawable.guide_truck_icon).getIntrinsicHeight();
                    layoutParams.leftMargin = c2 - (intrinsicWidth / 2);
                    iRouteUI.a(inflate);
                    a(true);
                }
                DriveUtil.setNeedGuideTruck(false);
                return;
            }
            DriveUtil.setNeedGuideTruck(false);
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        if (this.c != null) {
            a aVar = (a) this.c.get();
            if (aVar != null) {
                aVar.a(z);
            }
        }
    }

    public void setTruckGuideListener(a aVar) {
        if (aVar == null) {
            this.c = null;
        } else {
            this.c = new WeakReference<>(aVar);
        }
    }
}
