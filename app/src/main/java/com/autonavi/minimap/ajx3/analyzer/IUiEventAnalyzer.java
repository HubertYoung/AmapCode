package com.autonavi.minimap.ajx3.analyzer;

import com.autonavi.minimap.ajx3.context.AjxContextHandlerCallback;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.dom.JsDomEvent;

public interface IUiEventAnalyzer extends AjxContextHandlerCallback {
    void onUiEventFinish(IAjxContext iAjxContext, int i);

    void transferUiEvent(IAjxContext iAjxContext, JsDomEvent jsDomEvent);
}
