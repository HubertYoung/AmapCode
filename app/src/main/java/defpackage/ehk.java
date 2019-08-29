package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfo;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType;
import com.autonavi.jni.eyrie.amap.tracker.TrackPoster;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.IHealthFrameBikeSharing;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.jni.route.health.TraceStatus;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.logs.track.TrackPostUtils;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;

/* renamed from: ehk reason: default package */
/* compiled from: TrackFrameOfBikeSharing */
public final class ehk implements IHealthFrameBikeSharing {
    private ecy a;
    /* access modifiers changed from: private */
    public long b;
    /* access modifiers changed from: private */
    public boolean c;
    /* access modifiers changed from: private */
    public boolean d;

    public final void OnLengthSpeedTime(int i, double d2, long j) {
    }

    public final void OnLocationChanged(HealthPoint healthPoint) {
    }

    public final void OnMileStoneUpdated(HealthPoint healthPoint, int i) {
    }

    public final void OnPlaySound(String str) {
    }

    public final void OnStatusChanged(TraceStatus traceStatus) {
    }

    private ehk() {
    }

    public ehk(ecy ecy, long j, boolean z, boolean z2) {
        this.a = ecy;
        this.b = j;
        this.c = z;
        this.d = z2;
    }

    public final void OnStatisticsUpdated(TraceStatistics traceStatistics) {
        final long currentTimeMillis = System.currentTimeMillis();
        ehs.a((String) "share_bike_end_time_id", String.valueOf(currentTimeMillis));
        final ecy ecy = this.a;
        if (this.c || this.d) {
            final TraceStatistics traceStatistics2 = traceStatistics;
            AnonymousClass1 r0 = new Runnable() {
                public final void run() {
                    TraceStatistics traceStatistics = traceStatistics2;
                    String b2 = ehs.b("share_bike_order_id");
                    RideTraceHistory rideTraceHistory = null;
                    if (traceStatistics2 != null) {
                        eab b3 = eaa.a().b(b2);
                        if (b3 != null) {
                            AMapPageUtil.getAppContext();
                            rideTraceHistory = ees.a(bsp.a().a(b3.c));
                        }
                        if (rideTraceHistory == null) {
                            rideTraceHistory = ees.a(traceStatistics, ehk.this.b, currentTimeMillis);
                        }
                        if (ehk.this.c) {
                            ehs.a((String) "share_bike_riding_status_id", (String) "false");
                            ehl.a(AMapPageUtil.getAppContext()).b(ehs.b("share_bike_order_id"));
                            egj.a().a(false);
                            ees.a(rideTraceHistory);
                            egx.a().a(ehs.b("share_bike_order_id"));
                        }
                    }
                    if (ehk.this.d) {
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext != null && pageContext.isAlive()) {
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putObject("data", rideTraceHistory);
                            pageBundle.putBoolean("isfromRidePage", true);
                            pageBundle.putInt("where", 2);
                            pageBundle.putBoolean("bundle_is_request_coin", true);
                            eao.f("finishpage", "TrackFrameOfBikeSharing OnStatisticsUpdated callbØack");
                            if (traceStatistics2 != null) {
                                TrackInfo createTrackInfo = TrackInfo.createTrackInfo(TrackType.SHAREBIKE);
                                if (createTrackInfo != null) {
                                    createTrackInfo.set(TrackInfoKeyType.DrivenTime, (int) traceStatistics2.trace_time);
                                    createTrackInfo.set(TrackInfoKeyType.DrivenDist, traceStatistics2.trace_length);
                                    createTrackInfo.set(TrackInfoKeyType.ShareBikeDistance, traceStatistics2.trace_length);
                                    StringBuilder sb = new StringBuilder("OnStatisticsUpdated start upload sharebike distance:");
                                    sb.append(traceStatistics2.trace_length);
                                    TrackPostUtils.a(sb.toString());
                                    TrackPoster.upload(TrackType.SHAREBIKE);
                                }
                            }
                            pageContext.startPage(ShareRidingFinishPage.class, pageBundle);
                        }
                    }
                    if (ecy != null) {
                        ecy.a(traceStatistics2, rideTraceHistory);
                    }
                }
            };
            aho.a(r0);
            return;
        }
        if (ecy != null) {
            ecy.a(traceStatistics, null);
        }
    }
}
