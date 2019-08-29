package com.alipay.mobile.accountopenauth.api.rpc.model.res;

import java.util.List;

public class UserInputData {
    public String inputKey;
    public String inputName;
    public String inputType;
    public List<OptionItem> optionList;
    public String placeHolder;

    public static class OptionItem {
        private String key;
        private String value;

        public String getKey() {
            return this.key;
        }

        public void setKey(String str) {
            this.key = str;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String str) {
            this.value = str;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("OptionItem{key='");
            sb.append(this.key);
            sb.append('\'');
            sb.append(", value='");
            sb.append(this.value);
            sb.append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
