package com.autonavi.jni.route.health;

import com.autonavi.common.model.GeoPoint;
import java.util.ArrayList;

public class PathLineParser {
    private static final int COLOR_STYLE = 1;
    private static final int SPEED_STYLE = 0;
    public static final int STYLE_RIDE = 1;
    public static final int STYLE_RUN = 0;
    private static int[][][] STYPE_TABLE = {new int[][]{new int[]{0, 10, 20}, new int[]{-16729567, -17920, -1624040}}, new int[][]{new int[]{0, 15, 30}, new int[]{-15815612, -12541, -2019328}}};
    private ArrayList<PathLineSegment> allPath = new ArrayList<>();
    private int[] mBaseColor;
    private int[] mBaseSpeed;
    private PathLineSegment mLastPathLineSegment;

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0010, code lost:
        r4 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PathLineParser(int r4) {
        /*
            r3 = this;
            r3.<init>()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3.allPath = r0
            r0 = 0
            r1 = 1
            if (r4 == 0) goto L_0x0011
            if (r4 == r1) goto L_0x0011
            r4 = 0
        L_0x0011:
            int[][][] r2 = STYPE_TABLE
            r2 = r2[r4]
            r0 = r2[r0]
            r3.mBaseSpeed = r0
            int[][][] r0 = STYPE_TABLE
            r4 = r0[r4]
            r4 = r4[r1]
            r3.mBaseColor = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.jni.route.health.PathLineParser.<init>(int):void");
    }

    public void addPoint(GeoPoint geoPoint, boolean z, int i) {
        if (this.mLastPathLineSegment == null) {
            this.mLastPathLineSegment = new PathLineSegment();
            this.allPath.add(this.mLastPathLineSegment);
        }
        this.mLastPathLineSegment.addPoint(geoPoint, i, this.mBaseSpeed, this.mBaseColor);
        if (z) {
            this.mLastPathLineSegment = null;
        }
    }

    public PathLineSegment[] getSegments() {
        return (PathLineSegment[]) this.allPath.toArray(new PathLineSegment[this.allPath.size()]);
    }
}
