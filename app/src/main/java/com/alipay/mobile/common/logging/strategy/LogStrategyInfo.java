package com.alipay.mobile.common.logging.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogStrategyInfo {
    public static int SIMPLING_STATE_HIT = 1;
    public static int SIMPLING_STATE_INIT = -1;
    public static int SIMPLING_STATE_UNHIT = 0;
    boolean hasSendCondition = false;
    boolean isEncrypt;
    boolean isWrite;
    int level = -1;
    Map<String, Integer> levelHits = new ConcurrentHashMap();
    int levelRate1 = -1;
    int levelRate2 = -1;
    int levelRate3 = -1;
    boolean realtime;
    List<String> sendCondition = new ArrayList();
    int threshold;
    List<String> uploadEvents = new ArrayList();
    int uploadInterval = -1;
    int uploadRate = -1;

    public boolean isWrite() {
        return this.isWrite;
    }

    public void setWrite(boolean isWrite2) {
        this.isWrite = isWrite2;
    }

    public boolean isEncrypt() {
        return this.isEncrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.isEncrypt = encrypt;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public void setThreshold(int threshold2) {
        this.threshold = threshold2;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level2) {
        this.level = level2;
    }

    public List<String> getSendCondition() {
        return this.sendCondition;
    }

    public void setSendCondition(List<String> sendCondition2) {
        this.sendCondition = sendCondition2;
    }

    public List<String> getUploadEvents() {
        return this.uploadEvents;
    }

    public void setUploadEvents(List<String> uploadEvents2) {
        this.uploadEvents = uploadEvents2;
    }

    public boolean isRealtime() {
        return this.realtime;
    }

    public void setRealtime(boolean realtime2) {
        this.realtime = realtime2;
    }

    public int getUploadRate() {
        return this.uploadRate;
    }

    public void setUploadRate(int uploadRate2) {
        this.uploadRate = uploadRate2;
    }

    public int getUploadInterval() {
        return this.uploadInterval;
    }

    public void setUploadInterval(int uploadInterval2) {
        this.uploadInterval = uploadInterval2;
    }

    public int getLevelRate1() {
        return this.levelRate1;
    }

    public void setLevelRate1(int levelRate12) {
        this.levelRate1 = levelRate12;
    }

    public int getLevelRate2() {
        return this.levelRate2;
    }

    public void setLevelRate2(int levelRate22) {
        this.levelRate2 = levelRate22;
    }

    public int getLevelRate3() {
        return this.levelRate3;
    }

    public void setLevelRate3(int levelRate32) {
        this.levelRate3 = levelRate32;
    }

    public boolean isHasSendCondition() {
        return this.hasSendCondition;
    }

    public void setHasSendCondition(boolean hasSendCondition2) {
        this.hasSendCondition = hasSendCondition2;
    }
}
