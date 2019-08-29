package com.alipay.mobile.common.transport;

import java.util.concurrent.Future;

public interface Transport {
    Future<Response> execute(Request request);
}
