package com.alipay.streammedia.mmengine.filter;

import android.graphics.Bitmap;

public class WatermarkConfig {
    private int alpha;
    private Bitmap src;
    private Bitmap watermark;
    private int watermarkIntervalHeight;
    private int watermarkIntervalWidth;
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

    public int getWatermarkIntervalWidth() {
        return this.watermarkIntervalWidth;
    }

    public void setWatermarkIntervalWidth(int watermarkIntervalWidth2) {
        this.watermarkIntervalWidth = watermarkIntervalWidth2;
    }

    public int getWatermarkIntervalHeight() {
        return this.watermarkIntervalHeight;
    }

    public void setWatermarkIntervalHeight(int watermarkIntervalHeight2) {
        this.watermarkIntervalHeight = watermarkIntervalHeight2;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setAlpha(int alpha2) {
        this.alpha = alpha2;
    }

    public Bitmap getSrc() {
        return this.src;
    }

    public void setSrc(Bitmap src2) {
        this.src = src2;
    }

    public Bitmap getWatermark() {
        return this.watermark;
    }

    public void setWatermark(Bitmap watermark2) {
        this.watermark = watermark2;
    }
}
