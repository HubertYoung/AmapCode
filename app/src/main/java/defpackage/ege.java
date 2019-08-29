package defpackage;

import android.location.Location;
import android.text.TextUtils;
import android.text.format.Time;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfo;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType;
import com.autonavi.jni.eyrie.amap.tracker.TrackPoster;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.HealthPointStatus;
import com.autonavi.jni.route.health.IHealthBike;
import com.autonavi.jni.route.health.IHealthBikeSharing;
import com.autonavi.jni.route.health.IHealthFrameBikeSharing;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.jni.route.health.TraceStatus;
import com.autonavi.minimap.route.foot.footnavi.FootNaviLocation;
import com.autonavi.minimap.route.foot.footnavi.FootNaviLocation.a;
import com.autonavi.minimap.route.logs.track.TrackPostUtils;

/* renamed from: ege reason: default package */
/* compiled from: ShareRidingEngine */
public final class ege implements IHealthFrameBikeSharing, a {
    public float a = -1.0f;
    public IHealthBikeSharing b = null;
    public egd c = null;
    public FootNaviLocation d = null;
    public boolean e = false;
    public boolean f = false;
    public boolean g = false;
    public TrackInfo h;
    private boolean i = false;
    private int j = 0;
    private boolean k = false;

    public final void a() {
        if (this.b != null) {
            a.a("CALL_ENGINE", "PauseTrace");
            this.b.PauseTrace();
            this.e = true;
        }
    }

    public final synchronized void b() {
        if (this.b != null && !this.f) {
            a.a("CALL_ENGINE", "StopTrace");
            this.b.StopTrace();
            this.e = true;
            this.f = true;
        }
    }

    public final void a(boolean z) {
        this.k = z;
        if (this.d != null) {
            this.d.stopLocation();
        }
    }

    public final TraceStatistics c() {
        HealthPoint[] healthPointArr;
        if (this.b == null) {
            return null;
        }
        TraceStatistics GetTraceStatistics = this.b.GetTraceStatistics();
        a.a("CALL_ENGINE", "HelRideGetAllMembers", GetTraceStatistics);
        if (GetTraceStatistics == null) {
            return GetTraceStatistics;
        }
        a.a("CALL_ENGINE", "HelRideAllMembers", "nTraceLength=", Integer.valueOf(GetTraceStatistics.trace_length), "nTraceTime=", Long.valueOf(GetTraceStatistics.trace_time), "nCalorie=", Integer.valueOf(GetTraceStatistics.calorie), "nStep=", Integer.valueOf(GetTraceStatistics.steps), "nAverageSpeed=", Double.valueOf(GetTraceStatistics.average_speed), "nMaxSpeed=", Double.valueOf(GetTraceStatistics.max_speed));
        if (GetTraceStatistics.gps_array == null) {
            return GetTraceStatistics;
        }
        StringBuilder sb = new StringBuilder();
        for (HealthPoint healthPoint : GetTraceStatistics.gps_array) {
            sb.append(healthPoint.longitude);
            sb.append(", ");
            sb.append(healthPoint.latitude);
            sb.append(", ");
            sb.append(healthPoint.speed);
            sb.append("\n");
        }
        a.a("ENGINE_OUT", "getAllMembers", sb.toString());
        return GetTraceStatistics;
    }

    public final void OnLocationChanged(HealthPoint healthPoint) {
        if (healthPoint != null) {
            if (healthPoint.status != HealthPointStatus.HPS_IN_VALID) {
                double d2 = healthPoint.longitude;
                double d3 = healthPoint.latitude;
                StringBuilder sb = new StringBuilder("speed: ");
                sb.append(healthPoint.speed);
                StringBuilder sb2 = new StringBuilder("PointStatus: ");
                sb2.append(healthPoint.status);
                a.a("HelRideLocationChange", d2, d3, sb.toString(), sb2.toString());
                if (this.c != null) {
                    this.c.a(healthPoint);
                }
                return;
            }
        }
        StringBuilder sb3 = new StringBuilder("OnLocationChanged location = ");
        sb3.append(healthPoint);
        sb3.append(", status = ");
        sb3.append(healthPoint != null ? healthPoint.status : "null");
        a.a(sb3.toString());
    }

    public final void OnMileStoneUpdated(HealthPoint healthPoint, int i2) {
        if (healthPoint != null) {
            a.a("HelRideMileStone", healthPoint.longitude, healthPoint.latitude, "nMileStoneDis = ".concat(String.valueOf(i2)));
            if (this.c != null) {
                this.c.a(healthPoint, i2);
            }
        }
    }

    public final void OnLengthSpeedTime(int i2, double d2, long j2) {
        a.a("ENGINE_OUT", "HelRideLengthSpeedTime", "nLength = ", Integer.valueOf(i2), "nSpeed = ", Double.valueOf(d2), "lTime = ", Long.valueOf(j2));
        if (this.c != null) {
            this.c.a(i2, d2, j2);
        }
    }

    public final void OnStatusChanged(TraceStatus traceStatus) {
        Object[] objArr = new Object[2];
        objArr[0] = "HelRideTraceStatus";
        objArr[1] = traceStatus.getValue() == 1 ? "自动暂停" : "自动继续";
        a.a("ENGINE_OUT", objArr);
        a.a("HelRunTraceStatus eTraceStatus: ".concat(String.valueOf(traceStatus)));
        if (traceStatus == TraceStatus.TS_STOP) {
            if (this.b != null) {
                a.a("CALL_ENGINE", "Release");
                IHealthBike.Release(this.b);
                this.b = null;
            }
            if (this.k) {
                TrackPostUtils.a("**************start upload sharebike");
                TraceStatistics c2 = c();
                if (!(c2 == null || this.h == null)) {
                    int i2 = (int) c2.trace_time;
                    int i3 = c2.trace_length;
                    if (i2 > 0) {
                        this.h.set(TrackInfoKeyType.DrivenTime, i2);
                    }
                    if (i3 > 0) {
                        this.h.set(TrackInfoKeyType.DrivenDist, i3);
                    }
                    String b2 = ehs.b("share_bike_order_id");
                    String b3 = ehs.b("share_bike_cp_source");
                    StringBuilder sb = new StringBuilder("OnStatusChanged trackTime:");
                    sb.append(i2);
                    sb.append(", trackLength:");
                    sb.append(i3);
                    sb.append(", cpSource:");
                    sb.append(b3);
                    sb.append(", orderId:");
                    sb.append(b2);
                    TrackPostUtils.a(sb.toString());
                    this.h.set(TrackInfoKeyType.ShareBikeSource, b3);
                    this.h.set(TrackInfoKeyType.ShareBikeOrderId, b2);
                    this.h.set(TrackInfoKeyType.ShareBikeDistance, i3);
                }
                TrackPoster.upload(TrackType.SHAREBIKE);
                this.h = null;
            }
            return;
        }
        if (this.c != null) {
            this.c.a(traceStatus);
        }
    }

    public final void OnStatisticsUpdated(TraceStatistics traceStatistics) {
        HealthPoint[] healthPointArr;
        if (traceStatistics != null) {
            a.a("ENGINE_OUT", "HelRideAllMembersUpdate", "nTraceLength=", Integer.valueOf(traceStatistics.trace_length), "nTraceTime=", Long.valueOf(traceStatistics.trace_time), "nCalorie=", Integer.valueOf(traceStatistics.calorie), "nStep=", Integer.valueOf(traceStatistics.steps), "nAverageSpeed=", Double.valueOf(traceStatistics.average_speed));
            if (this.h != null) {
                int i2 = (int) traceStatistics.trace_time;
                int i3 = traceStatistics.trace_length;
                if (i2 > 0) {
                    this.h.set(TrackInfoKeyType.DrivenTime, i2);
                }
                if (i3 > 0) {
                    this.h.set(TrackInfoKeyType.DrivenDist, i3);
                }
                String b2 = ehs.b("share_bike_order_id");
                String b3 = ehs.b("share_bike_cp_source");
                StringBuilder sb = new StringBuilder("OnStatisticsUpdated trackTime:");
                sb.append(i2);
                sb.append(", trackLength:");
                sb.append(i3);
                sb.append(", cpSource:");
                sb.append(b3);
                sb.append(", orderId:");
                sb.append(b2);
                TrackPostUtils.a(sb.toString());
                this.h.set(TrackInfoKeyType.ShareBikeSource, b3);
                this.h.set(TrackInfoKeyType.ShareBikeOrderId, b2);
                this.h.set(TrackInfoKeyType.ShareBikeDistance, i3);
            }
            if (traceStatistics.gps_array != null) {
                StringBuilder sb2 = new StringBuilder();
                for (HealthPoint healthPoint : traceStatistics.gps_array) {
                    sb2.append(healthPoint.latitude);
                    sb2.append(", ");
                    sb2.append(healthPoint.longitude);
                    sb2.append(", ");
                    sb2.append(healthPoint.speed);
                    sb2.append("\n");
                }
                a.a("ENGINE_OUT", "HelRideAllMembersUpdate", sb2.toString());
            }
            if (this.c != null) {
                this.c.a(traceStatistics);
            }
        }
    }

    public final void OnPlaySound(String str) {
        if (!TextUtils.isEmpty(str)) {
            a.a("ENGINE_OUT", "HelRidePlaySound", str);
        }
    }

    public final void a(int i2) {
        a.a("onSatNumberChanged  nSatNum: ".concat(String.valueOf(i2)));
        if (this.c != null) {
            this.c.a();
        }
    }

    public final void a(Location location) {
        int i2;
        int accuracy = (int) location.getAccuracy();
        double speed = ((double) location.getSpeed()) * 3.6d;
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        long time = location.getTime() / 1000;
        if (location.hasBearing()) {
            this.j = (int) location.getBearing();
        }
        if (!location.hasAccuracy() || accuracy <= 100) {
            Time time2 = new Time();
            time2.set(location.getTime());
            int i3 = time2.year;
            int i4 = time2.month + 1;
            int i5 = time2.monthDay;
            int i6 = time2.hour;
            int i7 = time2.minute;
            int i8 = time2.second;
            if (!this.i) {
                if (this.b != null) {
                    i2 = i7;
                    this.b.SetGPSInfo((double) accuracy, longitude, latitude, speed, this.j, time);
                } else {
                    i2 = i7;
                }
                int i9 = i5;
                int i10 = i3;
                a.a("onLocationChange", accuracy, this.a, longitude, latitude, speed, (double) this.j, i10, i4, i9, i6, i2, i8);
                return;
            }
            int i11 = i3;
            int i12 = i7;
            int i13 = i5;
            double d2 = longitude;
            double d3 = latitude;
            double d4 = speed;
            a.a("sim_onLocationChange", accuracy, this.a, d2, d3, d4, (double) this.j, i11, i4, i13, i6, i12, i8);
        }
    }
}
