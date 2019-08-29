package test.tinyapp.alipay.com.testlibrary.core;

import android.text.TextUtils;

public interface DataProvider<T> {

    public enum UserAction {
        ACTION_SWITCH_TAB("switch_tab"),
        ACTION_SWITCH_PAGE("switch_page"),
        ACTION_NORMAL("normal");
        
        private String mUserAction;

        private UserAction(String action) {
            this.mUserAction = action;
        }

        public final String toString() {
            return TextUtils.isEmpty(this.mUserAction) ? "no action" : this.mUserAction;
        }
    }
}
