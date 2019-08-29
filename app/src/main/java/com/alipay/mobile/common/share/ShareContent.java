package com.alipay.mobile.common.share;

import java.io.Serializable;
import java.util.HashMap;

public class ShareContent implements Serializable {
    private static final long serialVersionUID = 1;
    private String content;
    private String contentType;
    private String extData;
    private HashMap<String, Object> extraInfo;
    private String iconUrl;
    private byte[] image;
    private String imgUrl;
    private String localImageUrl;
    private String title;
    private String url;

    public String toString() {
        return "content=" + this.content + ",url=" + this.url + ",image=" + (this.image != null ? this.image.length : 0) + ",title=" + this.title + ",imgUrl=" + this.imgUrl;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content2) {
        this.content = content2;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image2) {
        this.image = image2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl2) {
        this.imgUrl = imgUrl2;
    }

    public String getExtData() {
        return this.extData;
    }

    public void setExtData(String extData2) {
        this.extData = extData2;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType2) {
        this.contentType = contentType2;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl2) {
        this.iconUrl = iconUrl2;
    }

    public HashMap<String, Object> getExtraInfo() {
        return this.extraInfo;
    }

    public void setExtraInfo(HashMap<String, Object> extraInfo2) {
        this.extraInfo = extraInfo2;
    }

    public String getLocalImageUrl() {
        return this.localImageUrl;
    }

    public void setLocalImageUrl(String localImageUrl2) {
        this.localImageUrl = localImageUrl2;
    }
}
