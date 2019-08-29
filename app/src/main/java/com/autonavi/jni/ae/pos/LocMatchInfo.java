package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocMatchInfo implements Serializable {
    public double course;
    public double course3D;
    public double elevation;
    public byte formWay;
    public int is3DValid;
    public byte isOnGuideRoad;
    public int linkCur;
    public byte linkType;
    public long pathId;
    public int posCur;
    public int posCur3D;
    public byte roadClass;
    public int segmCur;
    public LocMapPoint st3DPos;
    public LocMapPoint stPos;
    public LocMapPoint stPosProj;
    public int weight;

    public LocMatchInfo(LocMapPoint locMapPoint, LocMapPoint locMapPoint2, LocMapPoint locMapPoint3, double d, double d2, double d3, int i, byte b, byte b2, byte b3, int i2, int i3, int i4, int i5, byte b4, long j, int i6) {
        this.stPos = locMapPoint;
        this.course = d;
        this.st3DPos = locMapPoint2;
        this.stPosProj = locMapPoint3;
        this.course3D = d2;
        this.elevation = d3;
        this.is3DValid = i;
        this.formWay = b;
        this.linkType = b2;
        this.roadClass = b3;
        this.segmCur = i2;
        this.linkCur = i3;
        this.posCur = i4;
        this.posCur3D = i5;
        this.isOnGuideRoad = b4;
        this.pathId = j;
        this.weight = i6;
    }

    public LocMatchInfo() {
    }
}
