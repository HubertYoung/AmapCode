package com.alipay.mobile.nebulacore.wallet;

import java.util.List;

public class H5LoggerSwitchModel {
    private RulesBean a;
    private List<RulesBean> b;

    public static class RangeBean {
        private String a;
        private String b;
        private String c;

        public RangeBean() {
        }

        public RangeBean(String url, String appId, String publicId) {
            this.a = url;
            this.b = appId;
            this.c = publicId;
        }

        public String getUrl() {
            return this.a;
        }

        public void setUrl(String url) {
            this.a = url;
        }

        public String getAppId() {
            return this.b;
        }

        public void setAppId(String appId) {
            this.b = appId;
        }

        public String getPublicId() {
            return this.c;
        }

        public void setPublicId(String publicId) {
            this.c = publicId;
        }
    }

    public static class Rules {
        private RangeBean a;
        private String b;
        private int c;

        public Rules() {
        }

        public Rules(RangeBean range, String output, int rate) {
            this.a = range;
            this.b = output;
            this.c = rate;
        }

        public int getRate() {
            return this.c;
        }

        public void setRate(int rate) {
            this.c = rate;
        }

        public RangeBean getRange() {
            return this.a;
        }

        public void setRange(RangeBean range) {
            this.a = range;
        }

        public String getOutput() {
            return this.b;
        }

        public void setOutput(String output) {
            this.b = output;
        }
    }

    public static class RulesBean {
        private String a;
        private List<Rules> b;

        public RulesBean() {
        }

        public RulesBean(String sid, List<Rules> rules) {
            this.a = sid;
            this.b = rules;
        }

        public String getSid() {
            return this.a;
        }

        public void setSid(String sid) {
            this.a = sid;
        }

        public List<Rules> getRules() {
            return this.b;
        }

        public void setRules(List<Rules> rules) {
            this.b = rules;
        }
    }

    public RulesBean getDefaultRule() {
        return this.a;
    }

    public void setDefaultRule(RulesBean defaultRule) {
        this.a = defaultRule;
    }

    public List<RulesBean> getSingleRules() {
        return this.b;
    }

    public void setSingleRules(List<RulesBean> singleRules) {
        this.b = singleRules;
    }
}
