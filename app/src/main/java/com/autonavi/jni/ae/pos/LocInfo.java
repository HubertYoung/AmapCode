package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocInfo implements Serializable {
    public double CompassCourse;
    public int CourseType;
    public double GpsCourse;
    public int MatchInfoCnt;
    public LocMatchInfo[] MatchInfos;
    public double MatchRoadCourse;
    public LocMapPoint MatchRoadPos;
    public double alt;
    public double altAcc;
    public byte bindFlag;
    public double courseAcc;
    public int day;
    public double distFromHeadLine;
    public float errorDist;
    public float fittingCourse;
    public float fittingCourseAcc;
    public float gpsCoureAcc;
    public int hour;
    public int isHLocData;
    public int isSimulate;
    public int isUse;
    public boolean locInfoChange;
    public int matchPosType;
    public int minute;
    public int mouth;
    public long nearRoadId;
    public double posAcc;
    public float roadCourse;
    public int roadDir;
    public long roadId;
    public int second;
    public int segIdx;
    public double showPosAcc;
    public int sourType;
    public double speed;
    public LocMapPoint stDoorInPos;
    public String strFloor;
    public String strPoiid;
    public long ticktime;
    public int uOverhead;
    public int year;

    public LocInfo() {
    }

    public LocInfo(int i, int i2, int i3, double d, double d2, double d3, double d4, double d5, double d6, int i4, int i5, byte b, double d7, int i6, long j, int i7, int i8, int i9, int i10, int i11, int i12, int i13, double d8, int i14, double d9, double d10, float f, int i15, float f2, float f3, float f4, float f5, int i16, LocMatchInfo[] locMatchInfoArr, boolean z) {
        this.isUse = i;
        this.isSimulate = i2;
        this.sourType = i3;
        this.alt = d;
        this.speed = d2;
        this.posAcc = d3;
        this.showPosAcc = d4;
        this.courseAcc = d5;
        this.altAcc = d6;
        this.roadDir = i4;
        this.roadId = this.roadId;
        this.nearRoadId = this.nearRoadId;
        this.segIdx = i5;
        this.bindFlag = b;
        this.distFromHeadLine = d7;
        this.isHLocData = i6;
        this.ticktime = j;
        this.year = i7;
        this.mouth = i8;
        this.day = i9;
        this.hour = i10;
        this.minute = i11;
        this.second = i12;
        this.uOverhead = i13;
        this.MatchRoadPos = this.MatchRoadPos;
        this.MatchRoadCourse = d8;
        this.CourseType = i14;
        this.CompassCourse = d9;
        this.GpsCourse = d10;
        this.errorDist = f;
        this.matchPosType = i15;
        this.gpsCoureAcc = f2;
        this.fittingCourse = f3;
        this.fittingCourseAcc = f4;
        this.roadCourse = f5;
        this.MatchInfoCnt = i16;
        this.MatchInfos = locMatchInfoArr;
        this.locInfoChange = z;
    }

    public void setStringValues(String str, String str2) {
        this.strPoiid = str;
        this.strFloor = str2;
    }

    public void setObjectValue(long j, long j2, LocMapPoint locMapPoint) {
        this.roadId = j;
        this.nearRoadId = j2;
        this.MatchRoadPos = locMapPoint;
    }

    public void setStDoorInPos(LocMapPoint locMapPoint) {
        this.stDoorInPos = locMapPoint;
    }
}
