package com.autonavi.minimap.bundle.share.entity;

import android.graphics.Bitmap;

public abstract class ShareParam {
    public String a;
    public String b;
    public boolean c = true;
    public String d = null;

    public static class DingDingParam extends ShareParam {
        public SendType e = SendType.Unknown;
        public String f;
        public Bitmap g;
        public String h;
        public String i;

        public enum SendType {
            Unknown,
            Text,
            WebPage,
            ByteImage,
            OnlineImage,
            LocalImage,
            ZhiFuBao
        }
    }

    public static class a extends ShareParam {
        public String e;
    }

    public static class b extends ShareParam {
        public String e;
        public String f;
        public Bitmap g;
        protected int h;

        public b(int i) {
            this.h = i;
        }
    }

    public static class c extends ShareParam {
        public String e;
    }

    public static class d extends ShareParam {
    }

    public static class e extends ShareParam {
        public int e;
        public String f;
        public Bitmap g;
        public String h;
        protected int i;
        public dcs j;
        public int k;

        public e(int i2) {
            this.i = i2;
        }
    }

    public static class f extends ShareParam {
        public int e;
        public int f;
        public String g;
        public String h;
        public boolean i = false;
        public boolean j = false;
    }
}
