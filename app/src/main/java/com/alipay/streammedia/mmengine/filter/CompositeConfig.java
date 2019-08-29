package com.alipay.streammedia.mmengine.filter;

import android.graphics.Bitmap;

public class CompositeConfig {
    private Bitmap src;
    private Bitmap superimosed;
    private int superimosed_h;
    private int superimosed_w;
    private int x;
    private int y;

    public int getX() {
        return this.x;
    }

    public void setX(int x2) {
        this.x = x2;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y2) {
        this.y = y2;
    }

    public Bitmap getSrc() {
        return this.src;
    }

    public Bitmap getSuperimosed() {
        return this.superimosed;
    }

    public int getSuperimosed_w() {
        return this.superimosed_w;
    }

    public int getSuperimosed_h() {
        return this.superimosed_h;
    }

    public void setSrc(Bitmap src2) {
        this.src = src2;
    }

    public void setSuperimosed(Bitmap superimosed2) {
        this.superimosed = superimosed2;
    }

    public void setSuperimosed_w(int superimosed_w2) {
        this.superimosed_w = superimosed_w2;
    }

    public void setSuperimosed_h(int superimosed_h2) {
        this.superimosed_h = superimosed_h2;
    }
}
