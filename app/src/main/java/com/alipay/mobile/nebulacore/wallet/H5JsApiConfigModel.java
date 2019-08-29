package com.alipay.mobile.nebulacore.wallet;

import com.alibaba.fastjson.JSONObject;

public class H5JsApiConfigModel {
    private boolean a;
    private AllBean b;
    private JSONObject c;

    public static class AllBean {
        private boolean a;
        private boolean b;
        private int c;

        public boolean isIn() {
            return this.a;
        }

        public void setIn(boolean in) {
            this.a = in;
        }

        public boolean isOut() {
            return this.b;
        }

        public void setOut(boolean out) {
            this.b = out;
        }

        public int getMaxLength() {
            return this.c;
        }

        public void setMaxLength(int maxLength) {
            this.c = maxLength;
        }
    }

    public boolean isEnable() {
        return this.a;
    }

    public void setEnable(boolean enable) {
        this.a = enable;
    }

    public AllBean getAll() {
        return this.b;
    }

    public void setAll(AllBean all) {
        this.b = all;
    }

    public JSONObject getEvery() {
        return this.c;
    }

    public void setEvery(JSONObject every) {
        this.c = every;
    }
}
