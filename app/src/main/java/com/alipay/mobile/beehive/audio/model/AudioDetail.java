package com.alipay.mobile.beehive.audio.model;

import android.os.Bundle;
import java.io.Serializable;

public class AudioDetail implements Serializable {
    public static final String DEFAULT_BIZ_IDENTIFIER = "DEFAULT_BIZ_ID";
    private static final String FORMAT_PATTERN = "url=%s,name=%s,author=%s,duration=%d,playedTime=%d,extraDesc=%s,coverImage=%s,bufferedPercent=%d,extraInfo = %s";
    public String audioLogoUrl;
    public String author;
    public String bizIdentifier = DEFAULT_BIZ_IDENTIFIER;
    public int bufferedPercent;
    public String coverImg;
    public long duration;
    public String epname;
    public String extraDesc;
    public Bundle extraInfo;
    public String name;
    public long playedTime;
    public String url;
    public String webUrl;

    public AudioDetail(String url2, String name2, String author2, long duration2, String extraDesc2, String coverImg2) {
        this.url = url2;
        this.name = name2;
        this.author = author2;
        this.duration = duration2;
        this.extraDesc = extraDesc2;
        this.coverImg = coverImg2;
    }

    public AudioDetail() {
    }

    public String toString() {
        String bundle;
        Object[] objArr = new Object[9];
        objArr[0] = this.url;
        objArr[1] = this.name;
        objArr[2] = this.author;
        objArr[3] = Long.valueOf(this.duration);
        objArr[4] = Long.valueOf(this.playedTime);
        objArr[5] = this.extraDesc;
        objArr[6] = this.coverImg;
        objArr[7] = Integer.valueOf(this.bufferedPercent);
        if (this.extraInfo == null) {
            bundle = "Null";
        } else {
            bundle = this.extraInfo.toString();
        }
        objArr[8] = bundle;
        return String.format(FORMAT_PATTERN, objArr);
    }
}
