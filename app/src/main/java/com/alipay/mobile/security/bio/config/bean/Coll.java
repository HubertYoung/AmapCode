package com.alipay.mobile.security.bio.config.bean;

import java.util.Arrays;

public class Coll {
    private boolean A = true;
    private boolean B = false;
    private boolean C = true;
    private boolean D = false;
    private String[] E = {"7"};
    private int a = 3;
    private float b = -0.6f;
    private float c = 0.6f;
    private float d = 0.5f;
    private float e = 0.28f;
    private int f = 50;
    private int g = 20;
    private int h = 178;
    private int i = 1;
    private int j = 1;
    private int k = 5;
    private String l = "";
    private String m = "";
    private String n = "";
    private String o = "";
    private String p = "";
    private String q = "";
    private String r = "";
    private String s = "";
    private String t = "";
    private String u = "";
    private String v = "";
    private String w = "";
    private int x = 0;
    private boolean y = false;
    private boolean z = false;

    public void setRetry(int i2) {
        this.a = i2;
    }

    public int getRetry() {
        return this.a;
    }

    public void setMinangle(float f2) {
        this.b = f2;
    }

    public float getMinangle() {
        return this.b;
    }

    public void setMaxangle(float f2) {
        this.c = f2;
    }

    public float getMaxangle() {
        return this.c;
    }

    public void setNear(float f2) {
        this.d = f2;
    }

    public float getNear() {
        return this.d;
    }

    public void setFar(float f2) {
        this.e = f2;
    }

    public float getFar() {
        return this.e;
    }

    public void setMinlight(int i2) {
        this.f = i2;
    }

    public int getMinlight() {
        return this.f;
    }

    public void setTime(int i2) {
        this.g = i2;
    }

    public int getTime() {
        return this.g;
    }

    public void setLight(int i2) {
        this.h = i2;
    }

    public int getLight() {
        return this.h;
    }

    public void setImageIndex(int i2) {
        this.i = i2;
    }

    public int getImageIndex() {
        return this.i;
    }

    public void setMineDscore(int i2) {
        this.j = i2;
    }

    public int getMineDscore() {
        return this.j;
    }

    public void setMineVideo(int i2) {
        this.k = i2;
    }

    public int getMineVideo() {
        return this.k;
    }

    public void setTopText(String str) {
        this.l = str;
    }

    public String getTopText() {
        return this.l;
    }

    public void setBottomText(String str) {
        this.m = str;
    }

    public String getBottomText() {
        return this.m;
    }

    public void setUploadMonitorPic(int i2) {
        this.x = i2;
    }

    public int getUploadMonitorPic() {
        return this.x;
    }

    public void setUploadLivePic(boolean z2) {
        this.y = z2;
    }

    public boolean isUploadLivePic() {
        return this.y;
    }

    public void setProgressbar(boolean z2) {
        this.z = z2;
    }

    public boolean isProgressbar() {
        return this.z;
    }

    public void setUploadBestPic(boolean z2) {
        this.A = z2;
    }

    public boolean isUploadBestPic() {
        return this.A;
    }

    public void setUploadPoseOkPic(boolean z2) {
        this.B = z2;
    }

    public boolean isUploadPoseOkPic() {
        return this.B;
    }

    public void setUploadBigPic(boolean z2) {
        this.C = z2;
    }

    public boolean isUploadBigPic() {
        return this.C;
    }

    public boolean isUploadDepthData() {
        return this.D;
    }

    public void setUploadDepthData(boolean z2) {
        this.D = z2;
    }

    public void setActionMode(String[] strArr) {
        this.E = strArr;
    }

    public String[] getActionMode() {
        return this.E;
    }

    public String getTopText_noface() {
        return this.n;
    }

    public void setTopText_noface(String str) {
        this.n = str;
    }

    public String getTopText_light() {
        return this.o;
    }

    public void setTopText_light(String str) {
        this.o = str;
    }

    public String getTopText_rectwidth() {
        return this.p;
    }

    public void setTopText_rectwidth(String str) {
        this.p = str;
    }

    public String getTopText_integrity() {
        return this.q;
    }

    public void setTopText_integrity(String str) {
        this.q = str;
    }

    public String getTopText_angle() {
        return this.r;
    }

    public void setTopText_angle(String str) {
        this.r = str;
    }

    public String getTopText_blur() {
        return this.s;
    }

    public void setTopText_blur(String str) {
        this.s = str;
    }

    public String getTopText_quality() {
        return this.t;
    }

    public void setTopText_quality(String str) {
        this.t = str;
    }

    public String getTopText_blink() {
        return this.u;
    }

    public void setTopText_blink(String str) {
        this.u = str;
    }

    public String getTopText_stay() {
        return this.v;
    }

    public void setTopText_stay(String str) {
        this.v = str;
    }

    public String getTopText_max_rectwidth() {
        return this.w;
    }

    public void setTopText_max_rectwidth(String str) {
        this.w = str;
    }

    public String toString() {
        return "Coll{retry=" + this.a + ", minangle=" + this.b + ", maxangle=" + this.c + ", near=" + this.d + ", far=" + this.e + ", minlight=" + this.f + ", time=" + this.g + ", light=" + this.h + ", imageIndex=" + this.i + ", mineDscore=" + this.j + ", mineVideo=" + this.k + ", topText='" + this.l + '\'' + ", bottomText='" + this.m + '\'' + ", topText_noface='" + this.n + '\'' + ", topText_light='" + this.o + '\'' + ", topText_rectwidth='" + this.p + '\'' + ", topText_integrity='" + this.q + '\'' + ", topText_angle='" + this.r + '\'' + ", topText_blur='" + this.s + '\'' + ", topText_quality='" + this.t + '\'' + ", topText_blink='" + this.u + '\'' + ", topText_stay='" + this.v + '\'' + ", topText_max_rectwidth='" + this.w + '\'' + ", uploadMonitorPic=" + this.x + ", uploadLivePic=" + this.y + ", progressbar=" + this.z + ", uploadBestPic=" + this.A + ", uploadPoseOkPic=" + this.B + ", uploadBigPic=" + this.C + ", uploadDepthData=" + this.D + ", actionMode=" + Arrays.toString(this.E) + '}';
    }
}
