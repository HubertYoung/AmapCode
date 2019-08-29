package com.autonavi.indooroutdoordetectorsdk;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import com.amap.location.g.b.a;
import com.amap.location.g.b.b;
import com.amap.location.g.b.c;
import com.amap.location.g.b.d;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.indoor.constant.Configuration;
import com.autonavi.indoor.util.L;
import java.util.List;

class GpsDetector {
    boolean isAvailable = true;
    boolean isStarted = false;
    private final LocationListener locationListener = new LocationListener() {
        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public void onLocationChanged(Location location) {
            try {
                if (GpsDetector.this.mGpsLastTime == 0) {
                    if (L.isLogging) {
                        L.d((String) "on onLocationChanged, GPS Enabled");
                    }
                    GpsDetector.this.mHandler.sendEmptyMessage(1006);
                }
                GpsDetector.this.mGpsLastTime = System.currentTimeMillis();
                if (L.isLogging) {
                    L.d((String) "onLocationChanged");
                }
                if (!GpsDetector.this.isStarted) {
                    if (L.isLogging) {
                        L.d((Object) Boolean.valueOf(GpsDetector.this.isStarted));
                    }
                    return;
                }
                GpsDetector.this.mHandler.sendEmptyMessage(802);
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }

        public void onProviderDisabled(String str) {
            if (WidgetType.GPS.equals(str)) {
                if (L.isLogging) {
                    L.d((String) "on GPS_PROVIDER Disabled");
                }
                GpsDetector.this.mHandler.sendEmptyMessage(1005);
            }
        }

        public void onProviderEnabled(String str) {
            if (WidgetType.GPS.equals(str)) {
                if (L.isLogging) {
                    L.d((String) "on GPS_PROVIDER Enabled");
                }
                GpsDetector.this.mHandler.sendEmptyMessage(1006);
            }
        }
    };
    a locationManager = null;
    Configuration mConfiguration;
    int mCountSatellite = 0;
    long mGpsLastTime = 0;
    Handler mHandler = null;
    int mLastCountSatellite = 0;
    private final b nmeaListener = new b() {
        public void onNmeaReceived(long j, String str) {
            try {
                if (GpsDetector.this.mGpsLastTime == 0) {
                    if (L.isLogging) {
                        L.d((String) "onNmeaReceived, GPS Enabled");
                    }
                    GpsDetector.this.mHandler.sendEmptyMessage(1006);
                }
                GpsDetector.this.mGpsLastTime = System.currentTimeMillis();
                if (!GpsDetector.this.isStarted) {
                    if (L.isLogging) {
                        L.d((Object) Boolean.valueOf(GpsDetector.this.isStarted));
                    }
                    return;
                }
                String trim = str.trim();
                if (trim.startsWith("$GPGSA") || trim.startsWith("$GPGSV")) {
                    GeoFenceHelper.logFile("nmea", trim);
                }
                JNIWrapper.jniSetNemaData(GpsDetector.this.mGpsLastTime, trim);
                GpsDetector.this.mHandler.sendEmptyMessage(802);
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }
    };
    private final d statusListener = new d() {
        public void onFirstFix(int i) {
        }

        public void onStarted() {
        }

        public void onStopped() {
        }

        public void onGpsStatusListener(int i, int i2, float f, List<c> list) {
            try {
                if (GpsDetector.this.mGpsLastTime == 0) {
                    if (L.isLogging) {
                        L.d((String) "onGpsStatusChanged, GPS Enabled");
                    }
                    GpsDetector.this.mHandler.sendEmptyMessage(1006);
                }
                GpsDetector.this.mGpsLastTime = System.currentTimeMillis();
                if (GpsDetector.this.locationManager != null) {
                    if (GpsDetector.this.isStarted) {
                        GpsDetector.this.mCountSatellite = i;
                        if (L.isLogging) {
                            StringBuilder sb = new StringBuilder("mCountSatellite=");
                            sb.append(GpsDetector.this.mCountSatellite);
                            L.d(sb.toString());
                        }
                        if (GpsDetector.this.mLastCountSatellite != GpsDetector.this.mCountSatellite) {
                            StringBuilder sb2 = new StringBuilder("Satellite:");
                            sb2.append(GpsDetector.this.mCountSatellite);
                            GeoFenceHelper.logFile(sb2.toString());
                            GpsDetector.this.mLastCountSatellite = GpsDetector.this.mCountSatellite;
                        }
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(GpsDetector.this.mCountSatellite);
                        GeoFenceHelper.logFile("gpsn", sb3.toString());
                        JNIWrapper.jniSetGPSState(GpsDetector.this.mGpsLastTime, GpsDetector.this.mCountSatellite, (double) (((float) i) * f));
                        GpsDetector.this.mHandler.sendEmptyMessage(802);
                        return;
                    }
                }
                if (L.isLogging) {
                    StringBuilder sb4 = new StringBuilder("locationManager == null, ");
                    sb4.append(GpsDetector.this.isStarted);
                    L.d(sb4.toString());
                }
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }
    };

    GpsDetector() {
    }

    public void initDetect(Configuration configuration) {
        this.mConfiguration = configuration;
        this.mGpsLastTime = 0;
        this.locationManager = a.a(this.mConfiguration.context);
    }

    public boolean startDetect() {
        if (L.isLogging) {
            L.d((String) "try to start gps");
        }
        this.isAvailable = this.locationManager.a((String) WidgetType.GPS);
        if (this.isStarted && this.mGpsLastTime != 0 && System.currentTimeMillis() - this.mGpsLastTime > 5000) {
            if (this.isAvailable) {
                if (L.isLogging) {
                    L.d((String) "GPS is no more useable, client should stop pdr.  MSG_GPS_DISABLED");
                }
                this.mHandler.sendEmptyMessage(1005);
            }
            this.isAvailable = false;
            if (L.isLogging) {
                L.d((String) "GPS is timeout!");
            }
        }
        if (this.isAvailable && !this.isStarted) {
            try {
                GeoFenceHelper.logFile("GpsStart");
                JNIWrapper.jniSetFlag(System.currentTimeMillis(), "GpsStart");
                this.locationManager.a(WidgetType.GPS, 1000, 0.0f, this.locationListener, null);
                try {
                    if (this.mConfiguration.context.checkPermission("android.permission.ACCESS_FINE_LOCATION", Process.myPid(), Process.myUid()) != 0) {
                        if (L.isLogging) {
                            L.d((String) "Permssion rejected by user");
                        }
                        return false;
                    }
                } catch (Exception e) {
                    if (L.isLogging) {
                        L.d((Throwable) e);
                    }
                }
                this.locationManager.a(this.statusListener, (Looper) null);
                this.locationManager.a(this.nmeaListener, (Looper) null);
                JNIWrapper.jniSetFlag(System.currentTimeMillis(), "GpsStarted");
                this.isStarted = true;
                GeoFenceHelper.logFile("GpsStarted");
                this.mGpsLastTime = 0;
            } catch (SecurityException unused) {
                if (L.isLogging) {
                    L.d((String) "Missing Permissions");
                }
            } catch (Throwable th) {
                this.isAvailable = false;
                this.isStarted = false;
                if (L.isLogging) {
                    L.d((String) "start GPS detector failed.");
                }
                if (L.isLogging) {
                    L.d(th);
                }
            }
        } else if (!this.isAvailable) {
            if (L.isLogging) {
                L.d((String) "GPS is not available");
            }
            return false;
        }
        return this.isStarted;
    }

    public void stopDetect() {
        try {
            if (this.isStarted) {
                GeoFenceHelper.logFile("GpsStop");
                JNIWrapper.jniSetFlag(System.currentTimeMillis(), "GpsStop");
                this.locationManager.a(this.statusListener);
                this.locationManager.a(this.nmeaListener);
                this.locationManager.a(this.locationListener);
            }
        } catch (SecurityException e) {
            if (L.isLogging) {
                L.d("Missing Permissions:".concat(String.valueOf(e)));
            }
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        this.isStarted = false;
    }

    public String toString() {
        String str;
        if (!this.isAvailable) {
            return "用户没有打开GPS";
        }
        StringBuilder sb = new StringBuilder("GPS");
        if (this.isStarted) {
            StringBuilder sb2 = new StringBuilder("Running , ");
            sb2.append(this.mCountSatellite);
            sb2.append("个卫星");
            str = sb2.toString();
        } else {
            str = "未工作";
        }
        sb.append(str);
        return sb.toString();
    }
}
