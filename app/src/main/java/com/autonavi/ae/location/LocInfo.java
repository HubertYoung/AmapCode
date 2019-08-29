package com.autonavi.ae.location;

import com.autonavi.jni.ae.pos.LocMapPoint;
import com.autonavi.jni.ae.pos.LocMatchInfo;

public class LocInfo extends com.autonavi.jni.ae.pos.LocInfo {
    public double course;
    public double course3D;
    public double elevation;
    public byte fromWay;
    public int is3DValid;
    public byte isOnGuideRoad;
    public int linkCur;
    public byte linkType;
    public long pathId;
    public int postCur;
    public int segmCur;
    public LocMapPoint st3DPos;
    public LocMapPoint stPos;

    public LocInfo buildLocInfo(com.autonavi.jni.ae.pos.LocInfo locInfo) {
        if (locInfo == null || locInfo.MatchInfoCnt <= 0 || locInfo.MatchInfos.length <= 0) {
            return null;
        }
        setAttrInMatchInfo(locInfo.MatchInfos[0]);
        setCommonAttr(locInfo);
        return this;
    }

    public LocInfo buildLocInfo(com.autonavi.jni.ae.pos.LocInfo locInfo, long j) {
        LocMatchInfo locMatchInfo = null;
        if (locInfo == null || locInfo.MatchInfoCnt <= 0 || locInfo.MatchInfos.length <= 0) {
            return null;
        }
        LocMatchInfo[] locMatchInfoArr = locInfo.MatchInfos;
        int length = locMatchInfoArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            LocMatchInfo locMatchInfo2 = locMatchInfoArr[i];
            if (locMatchInfo2.pathId == j) {
                locMatchInfo = locMatchInfo2;
                break;
            }
            i++;
        }
        if (locMatchInfo == null) {
            locMatchInfo = locInfo.MatchInfos[0];
        }
        setAttrInMatchInfo(locMatchInfo);
        setCommonAttr(locInfo);
        return this;
    }

    private void setAttrInMatchInfo(LocMatchInfo locMatchInfo) {
        this.stPos = locMatchInfo.stPos;
        this.course = locMatchInfo.course;
        this.fromWay = locMatchInfo.formWay;
        this.linkType = locMatchInfo.linkType;
        this.isOnGuideRoad = locMatchInfo.isOnGuideRoad;
        this.segmCur = locMatchInfo.segmCur;
        this.linkCur = locMatchInfo.linkCur;
        this.postCur = locMatchInfo.posCur;
        this.pathId = locMatchInfo.pathId;
        this.st3DPos = locMatchInfo.st3DPos;
        this.elevation = locMatchInfo.elevation;
        this.course3D = locMatchInfo.course3D;
        this.is3DValid = locMatchInfo.is3DValid;
    }

    private void setCommonAttr(com.autonavi.jni.ae.pos.LocInfo locInfo) {
        this.isUse = locInfo.isUse;
        this.isSimulate = locInfo.isSimulate;
        this.sourType = locInfo.sourType;
        this.alt = locInfo.alt;
        this.speed = locInfo.speed;
        this.posAcc = locInfo.posAcc;
        this.showPosAcc = locInfo.showPosAcc;
        this.courseAcc = locInfo.courseAcc;
        this.altAcc = locInfo.altAcc;
        this.roadDir = locInfo.roadDir;
        this.roadId = locInfo.roadId;
        this.nearRoadId = locInfo.nearRoadId;
        this.segIdx = locInfo.segIdx;
        this.bindFlag = locInfo.bindFlag;
        this.distFromHeadLine = locInfo.distFromHeadLine;
        this.isHLocData = locInfo.isHLocData;
        this.ticktime = locInfo.ticktime;
        this.strPoiid = locInfo.strPoiid;
        this.strFloor = locInfo.strFloor;
        this.stDoorInPos = locInfo.stDoorInPos;
        this.year = locInfo.year;
        this.mouth = locInfo.mouth;
        this.day = locInfo.day;
        this.hour = locInfo.hour;
        this.minute = locInfo.minute;
        this.second = locInfo.second;
        this.uOverhead = locInfo.uOverhead;
        this.MatchRoadPos = locInfo.MatchRoadPos;
        this.MatchRoadCourse = locInfo.MatchRoadCourse;
        this.CourseType = locInfo.CourseType;
        this.CompassCourse = locInfo.CompassCourse;
        this.GpsCourse = locInfo.GpsCourse;
        this.errorDist = locInfo.errorDist;
        this.matchPosType = locInfo.matchPosType;
        this.gpsCoureAcc = locInfo.gpsCoureAcc;
        this.fittingCourse = locInfo.fittingCourse;
        this.fittingCourseAcc = locInfo.fittingCourseAcc;
        this.roadCourse = locInfo.roadCourse;
    }
}
