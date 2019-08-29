package com.alibaba.sdk.trade.container.auth;

import com.alibaba.baichuan.android.auth.AlibcAuthRemote;
import java.util.List;

public class AlibcContainerAuthRemote implements AlibcAuthRemote {

    static class WantAuthRemoteHolder {
        public static AlibcContainerAuthRemote instance = new AlibcContainerAuthRemote();

        private WantAuthRemoteHolder() {
        }
    }

    public static AlibcContainerAuthRemote getInstance() {
        return WantAuthRemoteHolder.instance;
    }

    private AlibcContainerAuthRemote() {
    }

    public List<String> getHintList(String str) {
        return AlibcContainerHintManager.getHintList(str);
    }

    public void postHintList(String str, List list) {
        AlibcContainerHintManager.putComplementHintList(str, list);
    }
}
