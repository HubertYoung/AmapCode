package com.autonavi.minimap.route.sharebike.model;

import java.util.List;

public class IconConf extends BaseNetResult {
    private String code;
    private a data;
    private String message;
    private String resultX;
    private String timestamp;
    private String version;

    public static class a {
        public List<C0053a> a;
        public List<b> b;
        public c c;

        /* renamed from: com.autonavi.minimap.route.sharebike.model.IconConf$a$a reason: collision with other inner class name */
        public static class C0053a {
            public C0054a a;
            public String b;

            /* renamed from: com.autonavi.minimap.route.sharebike.model.IconConf$a$a$a reason: collision with other inner class name */
            public static class C0054a {
                public String a;
                public String b;
            }
        }

        public static class b {
            public String a;
            public String b;
            public String c;
        }

        public static class c {
            public String a;
            public String b;
        }
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getResultX() {
        return this.resultX;
    }

    public void setResultX(String str) {
        this.resultX = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public a getData() {
        return this.data;
    }

    public void setData(a aVar) {
        this.data = aVar;
    }
}
