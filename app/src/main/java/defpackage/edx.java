package defpackage;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import android.text.format.Time;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfo;
import com.autonavi.jni.eyrie.amap.tracker.TrackInfoKeyType;
import com.autonavi.jni.eyrie.amap.tracker.TrackPoster;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.jni.route.health.HealthParamKey;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.HealthPointStatus;
import com.autonavi.jni.route.health.IHealthBike;
import com.autonavi.jni.route.health.IHealthFrameBike;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.jni.route.health.TraceStatus;
import com.autonavi.minimap.route.foot.footnavi.FootNaviLocation;
import com.autonavi.minimap.route.foot.footnavi.FootNaviLocation.a;

/* renamed from: edx reason: default package */
/* compiled from: RideEngine */
public final class edx implements IHealthFrameBike, a {
    public float a = -1.0f;
    public IHealthBike b = null;
    public edw c = null;
    public FootNaviLocation d = null;
    public boolean e = false;
    public boolean f = false;
    public boolean g = false;
    public TrackInfo h;
    private Context i;
    private boolean j = false;
    private int k = 0;

    public edx(Context context) {
        this.i = context;
    }

    public final void a() {
        if (this.b != null) {
            IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
            if (iVoicePackageManager != null) {
                String playType = iVoicePackageManager.getPlayType(iVoicePackageManager.getCurrentTtsName());
                if (!TextUtils.isEmpty(playType)) {
                    this.b.SetParam(HealthParamKey.HPK_STAR_CODE, playType);
                    eer.a("CALL_ENGINE", "setEngineVoiceType code = ".concat(String.valueOf(playType)));
                }
            }
        }
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
                eer.a("HelRideLocationChange", d2, d3, sb.toString(), sb2.toString());
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
        eer.a(sb3.toString());
    }

    public final void OnMileStoneUpdated(HealthPoint healthPoint, int i2) {
        if (healthPoint != null) {
            eer.a("HelRideMileStone", healthPoint.longitude, healthPoint.latitude, "nMileStoneDis = ".concat(String.valueOf(i2)));
            if (this.c != null) {
                this.c.a(healthPoint, i2);
            }
        }
    }

    public final void OnLengthSpeedTime(int i2, double d2, long j2) {
        StringBuilder sb = new StringBuilder("HelRideLengthSpeedTime nLength = ");
        sb.append(i2);
        sb.append(" nSpeed = ");
        sb.append(d2);
        sb.append(" lTime = ");
        sb.append(j2);
        eer.a("ENGINE_OUT", sb.toString());
        if (this.c != null) {
            this.c.a(i2, d2, j2);
        }
    }

    public final void OnStatusChanged(TraceStatus traceStatus) {
        StringBuilder sb = new StringBuilder("HelRideTraceStatus");
        sb.append(traceStatus.getValue() == 1 ? " 自动暂停" : " 自动继续");
        eer.a("ENGINE_OUT", sb.toString());
        eer.a("HelRunTraceStatus eTraceStatus: ".concat(String.valueOf(traceStatus)));
        if (traceStatus == TraceStatus.TS_STOP) {
            if (this.f && this.b != null) {
                eer.a("CALL_ENGINE", "Release");
                IHealthBike.Release(this.b);
                this.b = null;
            }
            StringBuilder sb2 = new StringBuilder("**************start upload bike ");
            sb2.append(this.h);
            eer.a("Tracker", sb2.toString());
            TrackPoster.upload(TrackType.HEALTH_RIDE);
            this.h = null;
            return;
        }
        if (this.c != null) {
            this.c.a(traceStatus);
        }
    }

    public final void OnStatisticsUpdated(TraceStatistics traceStatistics) {
        HealthPoint[] healthPointArr;
        if (traceStatistics != null) {
            StringBuilder sb = new StringBuilder("HelRideAllMembersUpdatenTraceLength=");
            sb.append(traceStatistics.trace_length);
            sb.append("nTraceTime=");
            sb.append(traceStatistics.trace_time);
            sb.append("nCalorie=");
            sb.append(traceStatistics.calorie);
            sb.append("nStep=");
            sb.append(traceStatistics.steps);
            sb.append("nAverageSpeed=");
            sb.append(traceStatistics.average_speed);
            eer.a("ENGINE_OUT", sb.toString());
            if (this.h != null) {
                int i2 = traceStatistics.trace_length;
                this.h.set(TrackInfoKeyType.DrivenTime, (int) traceStatistics.trace_time);
                this.h.set(TrackInfoKeyType.DrivenDist, i2);
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
                StringBuilder sb3 = new StringBuilder("HelRideAllMembersUpdate");
                sb3.append(sb2.toString());
                eer.a("ENGINE_OUT", sb3.toString());
            }
            if (this.c != null) {
                this.c.a(traceStatistics);
            }
        }
    }

    public final void OnPlaySound(String str) {
        if (!TextUtils.isEmpty(str)) {
            eer.a("ENGINE_OUT", "HelRidePlaySound".concat(String.valueOf(str)));
            if (this.c != null) {
                this.c.a(str);
            }
        }
    }

    public final void a(Location location) {
        int accuracy = (int) location.getAccuracy();
        double speed = ((double) location.getSpeed()) * 3.6d;
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        long time = location.getTime() / 1000;
        if (location.hasBearing()) {
            this.k = (int) location.getBearing();
        }
        if (!location.hasAccuracy() || accuracy <= 100) {
            Time time2 = new Time();
            time2.set(location.getTime());
            int i2 = time2.year;
            int i3 = time2.month + 1;
            int i4 = time2.monthDay;
            int i5 = time2.hour;
            int i6 = time2.minute;
            int i7 = time2.second;
            if (!this.j) {
                int i8 = i6;
                this.b.SetGPSInfo((double) accuracy, longitude, latitude, speed, this.k, time);
                int i9 = i4;
                int i10 = i2;
                eer.a("onLocationChange", accuracy, this.a, longitude, latitude, speed, (double) this.k, i10, i3, i9, i5, i8, i7);
                return;
            }
            int i11 = i2;
            int i12 = i6;
            int i13 = i4;
            double d2 = longitude;
            double d3 = latitude;
            double d4 = speed;
            eer.a("sim_onLocationChange", accuracy, this.a, d2, d3, d4, (double) this.k, i11, i3, i13, i5, i12, i7);
        }
    }

    public final void a(int i2) {
        eer.a("onSatNumberChanged  nSatNum: ".concat(String.valueOf(i2)));
        if (this.c != null) {
            this.c.a(i2);
        }
    }

    public static void a(String str, String str2) {
        eer.a("updateBackStageInfo title: ".concat(String.valueOf(str)));
        eac a2 = eac.a();
        ead a3 = ead.a(5);
        a3.c = str;
        a3.d = str2;
        a2.a(a3);
    }
}
