package com.autonavi.indooroutdoordetectorsdk;

import android.graphics.RectF;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.autonavi.indoor.constant.Configuration;
import com.autonavi.indoor.util.L;
import com.autonavi.indoor.util.MapUtils;
import com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.TimeStatus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

class GeoFenceDetector {
    int N1 = 10;
    int N2 = 30;
    ArrayList<Building> mBuildings = new ArrayList<>();
    Configuration mConfiguration;
    int mDetectTimes = 0;
    double mDistance = 0.0d;
    TimeStatus mDownloadStatus = new TimeStatus();
    File mFile = null;
    String mFilename = "";
    int mGlobalSearchTimes = 0;
    ArrayList<Building> mNearBuildings = new ArrayList<>();
    Coord mNearCoord = null;
    TimeStatus mNearest = new TimeStatus();
    long mUpdateTime = 0;
    int mVersion = 0;

    static class Building {
        String buildingId = "";
        Coord center = new Coord();
        double distance = 0.0d;
        RectF envelope = new RectF(1000.0f, 1000.0f, -1000.0f, -1000.0f);
        boolean isContained;
        boolean isNearby;
        boolean isReachable = false;
        ArrayList<Coord> points = new ArrayList<>();
        int support = 0;

        Building() {
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Building: ");
            sb.append(this.buildingId);
            sb.append(", support=");
            sb.append(this.support);
            sb.append(", center=(");
            sb.append(this.center.longitude);
            sb.append(",");
            sb.append(this.center.latitude);
            sb.append("), count=");
            sb.append(this.points.size());
            sb.append(", distance=");
            sb.append(this.distance);
            sb.append(", Near:");
            sb.append(this.isNearby);
            sb.append(", isContained：");
            sb.append(this.isContained);
            return sb.toString();
        }

        /* access modifiers changed from: 0000 */
        public boolean detect(Coord coord) {
            this.distance = this.center.distance(coord);
            boolean z = false;
            this.isNearby = this.distance < 2000.0d;
            if (this.distance < 10000.0d) {
                z = true;
            }
            this.isReachable = z;
            this.isContained = this.isNearby;
            if (this.isNearby) {
                this.isContained = contains(coord);
            }
            return this.isContained;
        }

        /* access modifiers changed from: 0000 */
        public boolean contains(Coord coord) {
            if (this.envelope.contains(coord.longitude, coord.latitude)) {
                return isPointInRing(coord, this.points);
            }
            return false;
        }

        static boolean isPointInRing(Coord coord, ArrayList<Coord> arrayList) {
            Coord coord2 = coord;
            ArrayList<Coord> arrayList2 = arrayList;
            int size = arrayList.size();
            int i = 0;
            for (int i2 = 1; i2 < size; i2++) {
                Coord coord3 = arrayList2.get(i2);
                Coord coord4 = arrayList2.get(i2 - 1);
                double d = (double) (coord3.longitude - coord2.longitude);
                double d2 = (double) (coord3.latitude - coord2.latitude);
                double d3 = (double) (coord4.longitude - coord2.longitude);
                double d4 = (double) (coord4.latitude - coord2.latitude);
                if ((d2 > 0.0d && d4 <= 0.0d) || (d4 > 0.0d && d2 <= 0.0d)) {
                    if (0.0d < ((double) signOfDet2x2(d, d2, d3, d4)) / (d4 - d2)) {
                        i++;
                    }
                }
            }
            return i % 2 == 1;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x002b, code lost:
            if (r2 > r6) goto L_0x002d;
         */
        /* JADX WARNING: Removed duplicated region for block: B:121:0x0089 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x0062  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x006c  */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x008b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static int signOfDet2x2(double r21, double r23, double r25, double r27) {
            /*
                r0 = r21
                r2 = r23
                r4 = r25
                r6 = r27
                r8 = 0
                int r10 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
                r11 = 0
                r12 = 1
                r13 = -1
                if (r10 == 0) goto L_0x010e
                int r14 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r14 != 0) goto L_0x0017
                goto L_0x010e
            L_0x0017:
                int r15 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
                if (r15 == 0) goto L_0x0104
                int r15 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r15 != 0) goto L_0x0021
                goto L_0x0104
            L_0x0021:
                int r10 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
                if (r10 >= 0) goto L_0x0043
                int r10 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r10 >= 0) goto L_0x0039
                int r10 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r10 <= 0) goto L_0x005e
            L_0x002d:
                r12 = -1
            L_0x002e:
                r17 = r2
                r2 = r6
                r6 = r17
                r19 = r0
                r0 = r4
                r4 = r19
                goto L_0x005e
            L_0x0039:
                double r6 = -r6
                int r10 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r10 > 0) goto L_0x0041
                double r4 = -r4
            L_0x003f:
                r12 = -1
                goto L_0x005e
            L_0x0041:
                double r4 = -r4
                goto L_0x002e
            L_0x0043:
                int r10 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r10 >= 0) goto L_0x0050
                double r2 = -r2
                int r10 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r10 > 0) goto L_0x004e
                double r0 = -r0
                goto L_0x003f
            L_0x004e:
                double r0 = -r0
                goto L_0x002e
            L_0x0050:
                int r10 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r10 < 0) goto L_0x0059
                double r0 = -r0
                double r2 = -r2
                double r4 = -r4
                double r6 = -r6
                goto L_0x005e
            L_0x0059:
                double r0 = -r0
                double r4 = -r4
                double r2 = -r2
                double r6 = -r6
                goto L_0x002d
            L_0x005e:
                int r10 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
                if (r10 >= 0) goto L_0x006c
                int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r10 >= 0) goto L_0x006b
                int r10 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r10 <= 0) goto L_0x0079
                return r12
            L_0x006b:
                return r12
            L_0x006c:
                int r10 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r10 >= 0) goto L_0x0072
                int r0 = -r12
                return r0
            L_0x0072:
                int r10 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r10 < 0) goto L_0x0102
                int r12 = -r12
                double r0 = -r0
                double r4 = -r4
            L_0x0079:
                double r13 = r4 / r0
                double r13 = java.lang.Math.floor(r13)
                double r15 = r13 * r0
                double r4 = r4 - r15
                double r13 = r13 * r2
                double r6 = r6 - r13
                int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r10 >= 0) goto L_0x008b
                int r0 = -r12
                return r0
            L_0x008b:
                int r10 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
                if (r10 <= 0) goto L_0x0090
                return r12
            L_0x0090:
                r10 = 0
                double r13 = r4 + r4
                int r10 = (r0 > r13 ? 1 : (r0 == r13 ? 0 : -1))
                if (r10 <= 0) goto L_0x009e
                double r13 = r6 + r6
                int r10 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
                if (r10 >= 0) goto L_0x00ad
                return r12
            L_0x009e:
                r10 = 0
                double r13 = r6 + r6
                int r10 = (r2 > r13 ? 1 : (r2 == r13 ? 0 : -1))
                if (r10 <= 0) goto L_0x00a7
                int r0 = -r12
                return r0
            L_0x00a7:
                r10 = 0
                double r4 = r0 - r4
                double r6 = r2 - r6
                int r12 = -r12
            L_0x00ad:
                int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r10 != 0) goto L_0x00b8
                int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r0 != 0) goto L_0x00b6
                return r11
            L_0x00b6:
                int r0 = -r12
                return r0
            L_0x00b8:
                int r10 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r10 != 0) goto L_0x00bd
                return r12
            L_0x00bd:
                double r13 = r0 / r4
                double r13 = java.lang.Math.floor(r13)
                double r15 = r13 * r4
                double r0 = r0 - r15
                double r13 = r13 * r6
                double r2 = r2 - r13
                int r10 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
                if (r10 >= 0) goto L_0x00ce
                return r12
            L_0x00ce:
                int r10 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                if (r10 <= 0) goto L_0x00d4
                int r0 = -r12
                return r0
            L_0x00d4:
                r10 = 0
                double r13 = r0 + r0
                int r10 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
                if (r10 <= 0) goto L_0x00e3
                double r13 = r2 + r2
                int r10 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
                if (r10 >= 0) goto L_0x00f2
                int r0 = -r12
                return r0
            L_0x00e3:
                r10 = 0
                double r13 = r2 + r2
                int r10 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
                if (r10 <= 0) goto L_0x00eb
                return r12
            L_0x00eb:
                r10 = 0
                double r0 = r4 - r0
                double r2 = r6 - r2
                int r10 = -r12
                r12 = r10
            L_0x00f2:
                int r10 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
                if (r10 != 0) goto L_0x00fc
                int r0 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
                if (r0 != 0) goto L_0x00fb
                return r11
            L_0x00fb:
                return r12
            L_0x00fc:
                int r10 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
                if (r10 != 0) goto L_0x0079
                int r0 = -r12
                return r0
            L_0x0102:
                int r0 = -r12
                return r0
            L_0x0104:
                if (r14 <= 0) goto L_0x010a
                if (r10 <= 0) goto L_0x0109
                return r12
            L_0x0109:
                return r13
            L_0x010a:
                if (r10 <= 0) goto L_0x010d
                return r13
            L_0x010d:
                return r12
            L_0x010e:
                int r0 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
                if (r0 == 0) goto L_0x0121
                int r1 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
                if (r1 != 0) goto L_0x0117
                goto L_0x0121
            L_0x0117:
                if (r0 <= 0) goto L_0x011d
                if (r1 <= 0) goto L_0x011c
                return r13
            L_0x011c:
                return r12
            L_0x011d:
                if (r1 <= 0) goto L_0x0120
                return r12
            L_0x0120:
                return r13
            L_0x0121:
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector.Building.signOfDet2x2(double, double, double, double):int");
        }
    }

    public void initDetect(Configuration configuration) {
        this.mConfiguration = configuration;
        String str = this.mConfiguration.mSqlitePath;
        String substring = str.substring(0, str.lastIndexOf("/"));
        this.mFile = new File(substring);
        try {
            this.mFile.mkdirs();
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append("/Buildings");
        this.mFilename = sb.toString();
        this.mFile = new File(this.mFilename);
        if (this.mFile.exists()) {
            readBuildings();
        }
        downBuildings();
    }

    /* access modifiers changed from: 0000 */
    public int startDetect() {
        this.mDistance = 0.0d;
        this.mNearBuildings.clear();
        this.mNearest.reset();
        this.mNearCoord = null;
        this.mGlobalSearchTimes = 0;
        this.mDetectTimes = 0;
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int stopDetect() {
        this.mDistance = 0.0d;
        this.mNearBuildings.clear();
        this.mNearest.reset();
        this.mNearCoord = null;
        this.mGlobalSearchTimes = 0;
        this.mDetectTimes = 0;
        return 0;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0107, code lost:
        return r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector.Building detect(float r10, float r11) {
        /*
            r9 = this;
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r0 = r9.mDownloadStatus
            boolean r0 = r0.status
            if (r0 != 0) goto L_0x0013
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r0 = r9.mDownloadStatus
            r1 = 20000(0x4e20, double:9.8813E-320)
            boolean r0 = r0.isTimeout(r1)
            if (r0 == 0) goto L_0x0013
            r9.downBuildings()
        L_0x0013:
            java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r0 = r9.mBuildings
            monitor-enter(r0)
            java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r1 = r9.mBuildings     // Catch:{ all -> 0x0108 }
            boolean r1 = com.autonavi.indoor.util.MapUtils.isEmpty(r1)     // Catch:{ all -> 0x0108 }
            r2 = 0
            if (r1 == 0) goto L_0x0021
            monitor-exit(r0)     // Catch:{ all -> 0x0108 }
            return r2
        L_0x0021:
            int r1 = r9.mDetectTimes     // Catch:{ all -> 0x0108 }
            int r1 = r1 + 1
            r9.mDetectTimes = r1     // Catch:{ all -> 0x0108 }
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$Coord r1 = new com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$Coord     // Catch:{ all -> 0x0108 }
            r1.<init>(r10, r11)     // Catch:{ all -> 0x0108 }
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$Coord r10 = r9.mNearCoord     // Catch:{ all -> 0x0108 }
            if (r10 == 0) goto L_0x0041
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$Coord r10 = r9.mNearCoord     // Catch:{ all -> 0x0108 }
            double r10 = r10.distance(r1)     // Catch:{ all -> 0x0108 }
            r3 = 4652007308841189376(0x408f400000000000, double:1000.0)
            int r10 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r10 <= 0) goto L_0x0041
            r9.mNearCoord = r2     // Catch:{ all -> 0x0108 }
        L_0x0041:
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r10 = r9.mNearest     // Catch:{ all -> 0x0108 }
            java.lang.Object r10 = r10.object     // Catch:{ all -> 0x0108 }
            com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building r10 = (com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector.Building) r10     // Catch:{ all -> 0x0108 }
            java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r11 = r9.mNearBuildings     // Catch:{ all -> 0x0108 }
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$Coord r3 = r9.mNearCoord     // Catch:{ all -> 0x0108 }
            if (r3 != 0) goto L_0x006a
            boolean r11 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0108 }
            if (r11 == 0) goto L_0x0056
            java.lang.String r11 = "global search"
            com.autonavi.indoor.util.L.d(r11)     // Catch:{ all -> 0x0108 }
        L_0x0056:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0108 }
            r2.<init>()     // Catch:{ all -> 0x0108 }
            java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r11 = r9.mBuildings     // Catch:{ all -> 0x0108 }
            int r3 = r9.mGlobalSearchTimes     // Catch:{ all -> 0x0108 }
            int r3 = r3 + 1
            r9.mGlobalSearchTimes = r3     // Catch:{ all -> 0x0108 }
            r9.mNearCoord = r1     // Catch:{ all -> 0x0108 }
            java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r3 = r9.mNearBuildings     // Catch:{ all -> 0x0108 }
            r3.clear()     // Catch:{ all -> 0x0108 }
        L_0x006a:
            java.util.Iterator r3 = r11.iterator()     // Catch:{ all -> 0x0108 }
        L_0x006e:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x0108 }
            if (r4 == 0) goto L_0x009f
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x0108 }
            com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building r4 = (com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector.Building) r4     // Catch:{ all -> 0x0108 }
            r4.detect(r1)     // Catch:{ all -> 0x0108 }
            if (r10 == 0) goto L_0x0087
            double r5 = r4.distance     // Catch:{ all -> 0x0108 }
            double r7 = r10.distance     // Catch:{ all -> 0x0108 }
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x0088
        L_0x0087:
            r10 = r4
        L_0x0088:
            boolean r5 = r4.isNearby     // Catch:{ all -> 0x0108 }
            if (r5 == 0) goto L_0x0095
            java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r5 = r9.mNearBuildings     // Catch:{ all -> 0x0108 }
            if (r11 == r5) goto L_0x0095
            java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r5 = r9.mNearBuildings     // Catch:{ all -> 0x0108 }
            r5.add(r4)     // Catch:{ all -> 0x0108 }
        L_0x0095:
            if (r2 == 0) goto L_0x006e
            boolean r5 = r4.isReachable     // Catch:{ all -> 0x0108 }
            if (r5 == 0) goto L_0x006e
            r2.add(r4)     // Catch:{ all -> 0x0108 }
            goto L_0x006e
        L_0x009f:
            boolean r11 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0108 }
            if (r11 == 0) goto L_0x00b0
            java.lang.String r11 = "Nearest "
            java.lang.String r1 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x0108 }
            java.lang.String r11 = r11.concat(r1)     // Catch:{ all -> 0x0108 }
            com.autonavi.indoor.util.L.d(r11)     // Catch:{ all -> 0x0108 }
        L_0x00b0:
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r11 = r9.mNearest     // Catch:{ all -> 0x0108 }
            r11.object = r10     // Catch:{ all -> 0x0108 }
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r11 = r9.mNearest     // Catch:{ all -> 0x0108 }
            boolean r1 = r10.isContained     // Catch:{ all -> 0x0108 }
            r11.status = r1     // Catch:{ all -> 0x0108 }
            double r3 = r10.distance     // Catch:{ all -> 0x0108 }
            r9.mDistance = r3     // Catch:{ all -> 0x0108 }
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r11 = r9.mNearest     // Catch:{ all -> 0x0108 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0108 }
            r11.time = r3     // Catch:{ all -> 0x0108 }
            if (r2 == 0) goto L_0x0106
            boolean r11 = r2.isEmpty()     // Catch:{ all -> 0x0108 }
            if (r11 != 0) goto L_0x0106
            boolean r11 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x0108 }
            if (r11 == 0) goto L_0x0104
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0108 }
            java.lang.String r1 = "shrink memory. total="
            r11.<init>(r1)     // Catch:{ all -> 0x0108 }
            java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r1 = r9.mBuildings     // Catch:{ all -> 0x0108 }
            int r1 = r1.size()     // Catch:{ all -> 0x0108 }
            r11.append(r1)     // Catch:{ all -> 0x0108 }
            java.lang.String r1 = ", ReachableBuildings(cache)="
            r11.append(r1)     // Catch:{ all -> 0x0108 }
            int r1 = r2.size()     // Catch:{ all -> 0x0108 }
            r11.append(r1)     // Catch:{ all -> 0x0108 }
            java.lang.String r1 = ", NearBuildings="
            r11.append(r1)     // Catch:{ all -> 0x0108 }
            java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r1 = r9.mNearBuildings     // Catch:{ all -> 0x0108 }
            int r1 = r1.size()     // Catch:{ all -> 0x0108 }
            r11.append(r1)     // Catch:{ all -> 0x0108 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x0108 }
            com.autonavi.indoor.util.L.d(r11)     // Catch:{ all -> 0x0108 }
        L_0x0104:
            r9.mBuildings = r2     // Catch:{ all -> 0x0108 }
        L_0x0106:
            monitor-exit(r0)     // Catch:{ all -> 0x0108 }
            return r10
        L_0x0108:
            r10 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0108 }
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector.detect(float, float):com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building");
    }

    /* access modifiers changed from: 0000 */
    public void readBuildings() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.mFile);
            byte[] bArr = new byte[((int) this.mFile.length())];
            fileInputStream.read(bArr);
            fileInputStream.close();
            decodeBuildings(bArr);
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void writeBuildings(byte[] bArr) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.mFile);
            fileOutputStream.write(bArr);
            fileOutputStream.close();
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        decodeBuildings(bArr);
    }

    /* access modifiers changed from: 0000 */
    public void decodeBuildings(byte[] bArr) {
        try {
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            this.mUpdateTime = wrap.getLong();
            this.mVersion = wrap.getInt();
            this.N1 = wrap.get();
            this.N2 = wrap.get();
            wrap.getShort();
            wrap.getInt();
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("decodeBuildings: N1=");
                sb.append(this.N1);
                sb.append(", N2=");
                sb.append(this.N2);
                sb.append(", bufsize=");
                sb.append(bArr.length);
                L.d(sb.toString());
            }
            int i = wrap.getInt();
            int i2 = wrap.getInt();
            synchronized (this.mBuildings) {
                if (i2 >= 0) {
                    this.mBuildings.clear();
                }
                if (L.isLogging) {
                    StringBuilder sb2 = new StringBuilder("\tmVersion=");
                    sb2.append(this.mVersion);
                    sb2.append(", dataVersion=");
                    sb2.append(i);
                    sb2.append(", count=");
                    sb2.append(i2);
                    L.d(sb2.toString());
                }
                for (int i3 = 0; i3 < i2; i3++) {
                    Building building = new Building();
                    building.support = wrap.get();
                    byte[] bArr2 = new byte[16];
                    wrap.get(bArr2);
                    building.buildingId = new String(bArr2, 0, 10);
                    building.center.longitude = wrap.getFloat();
                    building.center.latitude = wrap.getFloat();
                    int i4 = wrap.getInt();
                    for (int i5 = 0; i5 < i4; i5++) {
                        Coord coord = new Coord();
                        coord.longitude = (float) ((((double) wrap.getShort()) * 1.0E-6d) + ((double) building.center.longitude));
                        coord.latitude = (float) ((((double) wrap.getShort()) * 1.0E-6d) + ((double) building.center.latitude));
                        building.points.add(coord);
                        building.envelope.union(coord.longitude, coord.latitude);
                    }
                    this.mBuildings.add(building);
                }
            }
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void downBuildings() {
        Configuration configuration = this.mConfiguration;
        if (configuration != null) {
            try {
                this.mDownloadStatus.changeStatus(true);
                String url = configuration.getUrl();
                ByteBuffer allocate = ByteBuffer.allocate(1024);
                allocate.putShort(2);
                allocate.putInt(1);
                allocate.putShort(0);
                MapUtils.put(allocate, MapUtils.getImei());
                MapUtils.put(allocate, MapUtils.getImsi());
                allocate.putInt(this.mVersion);
                allocate.putInt(0);
                allocate.putInt(0);
                allocate.putShort(6, (short) ((allocate.position() - 8) + 1));
                allocate.put(GeoFenceHelper.getCheckData(allocate));
                GeoFenceHelper.postRequest(url, System.currentTimeMillis(), MapUtils.copyOf(allocate.array(), allocate.position()), new Handler(new Callback() {
                    public boolean handleMessage(Message message) {
                        try {
                            if (message.what == 1209) {
                                byte[] bArr = (byte[]) message.obj;
                                if (L.isLogging) {
                                    StringBuilder sb = new StringBuilder("buf.length=");
                                    sb.append(bArr.length);
                                    L.d(sb.toString());
                                }
                                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                                if (wrap.getShort() != 2) {
                                    return false;
                                }
                                int i = wrap.getInt();
                                if (i == 1 || i == 2 || i == 3) {
                                    int i2 = wrap.getInt();
                                    if (i2 < 0) {
                                        if (L.isLogging) {
                                            L.d(" invalid status ".concat(String.valueOf(i2)));
                                        }
                                        return false;
                                    }
                                    int i3 = wrap.getInt();
                                    if (i3 <= 0) {
                                        if (L.isLogging) {
                                            L.d(" invalid length ".concat(String.valueOf(i3)));
                                        }
                                        return false;
                                    }
                                    int i4 = wrap.getInt();
                                    if (i4 <= 0) {
                                        if (L.isLogging) {
                                            L.d(" invalid dataVersion ".concat(String.valueOf(i4)));
                                        }
                                        return false;
                                    }
                                    int i5 = wrap.getInt();
                                    if (L.isLogging) {
                                        StringBuilder sb2 = new StringBuilder("downloaded geofence dataVersion=");
                                        sb2.append(i4);
                                        sb2.append(", dataLength=");
                                        sb2.append(i5);
                                        L.d(sb2.toString());
                                    }
                                    if (i5 > 0) {
                                        byte[] bArr2 = new byte[i5];
                                        wrap.get(bArr2);
                                        ByteBuffer allocate = ByteBuffer.allocate(i5 + 8 + 4);
                                        allocate.putLong(System.currentTimeMillis());
                                        allocate.putInt(i4);
                                        allocate.put(bArr2);
                                        GeoFenceDetector.this.writeBuildings(allocate.array());
                                    }
                                } else {
                                    if (L.isLogging) {
                                        L.d(" invalid actionVersion ".concat(String.valueOf(i)));
                                    }
                                    return false;
                                }
                            } else {
                                GeoFenceDetector.this.mDownloadStatus.changeStatus(false);
                            }
                        } catch (Throwable th) {
                            if (L.isLogging) {
                                L.d(th);
                            }
                            GeoFenceDetector.this.mDownloadStatus.changeStatus(false);
                        }
                        return false;
                    }
                }));
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
                this.mDownloadStatus.changeStatus(false);
            }
        }
    }

    public String toString() {
        String sb;
        synchronized (this.mBuildings) {
            StringBuilder sb2 = new StringBuilder("围栏(");
            sb2.append(this.mBuildings.size());
            sb2.append("栋建筑, 附近");
            sb2.append(this.mNearBuildings.size());
            sb2.append("栋, 版本");
            sb2.append(this.mVersion);
            sb2.append(", 参数");
            sb2.append(this.N1);
            sb2.append(",");
            sb2.append(this.N2);
            sb2.append("), DownloadStatus:");
            sb2.append(this.mDownloadStatus.status);
            sb2.append(", 次数(全局搜索/总数):");
            sb2.append(this.mGlobalSearchTimes);
            sb2.append("/");
            sb2.append(this.mDetectTimes);
            sb = sb2.toString();
            if (this.mNearest.object != null) {
                Building building = (Building) this.mNearest.object;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb);
                sb3.append(" 离");
                sb3.append(building.buildingId);
                sb3.append("约");
                sb3.append(GeoFenceHelper.round(this.mDistance, 2));
                sb3.append("米  ");
                String sb4 = sb3.toString();
                if (building.isContained) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(sb4);
                    sb5.append("进入围栏了 :)))))");
                    sb = sb5.toString();
                } else {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(sb4);
                    sb6.append("围栏之外");
                    sb = sb6.toString();
                }
            }
        }
        return sb;
    }
}
