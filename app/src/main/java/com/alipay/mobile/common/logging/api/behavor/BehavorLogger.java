package com.alipay.mobile.common.logging.api.behavor;

public interface BehavorLogger {
    void autoClick(Behavor behavor);

    void autoEvent(Behavor behavor);

    void autoOpenPage(Behavor behavor);

    void click(Behavor behavor);

    void event(String str, Behavor behavor);

    void longClick(Behavor behavor);

    void openPage(Behavor behavor);

    void slide(Behavor behavor);

    void submit(Behavor behavor);
}
