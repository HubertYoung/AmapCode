package com.alibaba.baichuan.android.auth;

import java.util.List;

public interface AlibcAuthRemote {
    List getHintList(String str);

    void postHintList(String str, List list);
}
