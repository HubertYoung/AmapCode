package com.autonavi.miniapp.plugin.lbs;

import android.graphics.Bitmap;
import java.io.Serializable;

public class LatLonPointEx extends LatLonPoint implements Serializable {
    private static final long serialVersionUID = 5744163698640426769L;
    private Bitmap clickIcon;
    private boolean draggable = false;
    private Object ext;
    private Bitmap icon;
    private boolean showInfoWindow = true;
    private String snippet;
    private String title;

    public LatLonPointEx() {
    }

    public LatLonPointEx(double d, double d2) {
        super(d, d2);
    }

    public Bitmap getIcon() {
        return this.icon;
    }

    public void setIcon(Bitmap bitmap) {
        this.icon = bitmap;
    }

    public Bitmap getClickIcon() {
        return this.clickIcon;
    }

    public void setClickIcon(Bitmap bitmap) {
        this.clickIcon = bitmap;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getSnippet() {
        return this.snippet;
    }

    public void setSnippet(String str) {
        this.snippet = str;
    }

    public Object getExt() {
        return this.ext;
    }

    public void setExt(Object obj) {
        this.ext = obj;
    }

    public boolean isShowInfoWindow() {
        return this.showInfoWindow;
    }

    @Deprecated
    public void setShowInfoWindow(boolean z) {
        this.showInfoWindow = z;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setDraggable(boolean z) {
        this.draggable = z;
    }
}
