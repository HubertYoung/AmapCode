package com.taobao.tao.remotebusiness;

import mtopsdk.mtop.domain.MtopResponse;

public interface IRemoteParserListener extends few {
    void parseResponse(MtopResponse mtopResponse);
}
