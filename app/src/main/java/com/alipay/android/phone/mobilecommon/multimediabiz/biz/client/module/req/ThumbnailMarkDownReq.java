package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

public class ThumbnailMarkDownReq extends ThumbnailsDownReq {
    private int markHeight = 100;
    private String markId;
    private int markWidth = 100;
    private Integer paddingX;
    private Integer paddingY;
    private Integer percent;
    private int position = 5;
    private int transparency = 80;

    public ThumbnailMarkDownReq(String fileIds, String zoom) {
        super(fileIds, zoom);
    }

    public String getMarkId() {
        return this.markId;
    }

    public void setMarkId(String markId2) {
        this.markId = markId2;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position2) {
        this.position = position2;
    }

    public int getTransparency() {
        return this.transparency;
    }

    public void setTransparency(int transparency2) {
        this.transparency = transparency2;
    }

    public int getMarkWidth() {
        return this.markWidth;
    }

    public void setMarkWidth(int markWidth2) {
        this.markWidth = markWidth2;
    }

    public int getMarkHeight() {
        return this.markHeight;
    }

    public void setMarkHeight(int markHeight2) {
        this.markHeight = markHeight2;
    }

    public Integer getPaddingX() {
        return this.paddingX;
    }

    public void setPaddingX(Integer paddingX2) {
        this.paddingX = paddingX2;
    }

    public Integer getPaddingY() {
        return this.paddingY;
    }

    public void setPaddingY(Integer paddingY2) {
        this.paddingY = paddingY2;
    }

    public Integer getPercent() {
        return this.percent;
    }

    public void setPercent(Integer percent2) {
        this.percent = percent2;
    }
}
