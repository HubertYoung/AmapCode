package com.alipay.inside.android.phone.mrpc.core;

import java.util.concurrent.Future;

public interface Transport {
    Future<Response> execute(Request request);
}
