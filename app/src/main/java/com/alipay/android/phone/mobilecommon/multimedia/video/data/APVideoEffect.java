package com.alipay.android.phone.mobilecommon.multimedia.video.data;

public class APVideoEffect {
    private static final int HASHCODE = 1;
    public static final String TPYE_ORIGINAL = "original";
    public static final String TPYE_WATERMARK = "watermark";
    public static final String TYPE_FILTER = "filter";
    String djangoId;
    String id;
    String name;
    String path;
    String text;
    String type;

    public void setName(String n) {
        this.name = n;
    }

    public void setType(String t) {
        this.type = t;
    }

    public void setDjangoId(String id2) {
        this.djangoId = id2;
    }

    public void setText(String t) {
        this.text = t;
    }

    public void setId(String i) {
        this.id = i;
    }

    public void setPath(String p) {
        this.path = p;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getDjangoId() {
        return this.djangoId;
    }

    public String getText() {
        return this.text;
    }

    public String getPath() {
        return this.path;
    }

    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object o) {
        APVideoEffect effect = (APVideoEffect) o;
        if (!this.type.equals(effect.getType())) {
            return false;
        }
        if (this.type.equals(TPYE_WATERMARK)) {
            return this.djangoId.equals(effect.getDjangoId());
        }
        if (this.type.equals(TYPE_FILTER)) {
            return this.id.equals(effect.getId());
        }
        return true;
    }

    public String toString() {
        return "type:" + this.type + ",djangoId:" + this.djangoId + ",text:" + this.text + ",id:" + this.id + "path:" + this.path + "name:" + this.name;
    }
}
