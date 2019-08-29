package com.alipay.mobile.common.transportext.biz.spdy;

import com.alipay.mobile.common.transportext.biz.spdy.Failure.Builder;
import com.alipay.mobile.common.transportext.biz.spdy.Request.Body;
import com.alipay.mobile.common.transportext.biz.spdy.Response.Receiver;
import java.io.IOException;
import java.net.HttpURLConnection;

final class Job implements Runnable {
    final HttpURLConnection connection;
    final Dispatcher dispatcher;
    final Request request;
    final Receiver responseReceiver;

    public Job(Dispatcher dispatcher2, HttpURLConnection connection2, Request request2, Receiver responseReceiver2) {
        this.dispatcher = dispatcher2;
        this.connection = connection2;
        this.request = request2;
        this.responseReceiver = responseReceiver2;
    }

    public final void run() {
        try {
            sendRequest();
            this.responseReceiver.onResponse(readResponse());
        } catch (IOException e) {
            this.responseReceiver.onFailure(new Builder().request(this.request).exception(e).build());
        } finally {
            this.connection.disconnect();
            this.dispatcher.finished(this);
        }
    }

    private HttpURLConnection sendRequest() {
        for (int i = 0; i < this.request.headerCount(); i++) {
            this.connection.addRequestProperty(this.request.headerName(i), this.request.headerValue(i));
        }
        Body body = this.request.body();
        if (body != null) {
            this.connection.setDoOutput(true);
            long contentLength = body.contentLength();
            if (contentLength == -1 || contentLength > 2147483647L) {
                this.connection.setChunkedStreamingMode(0);
            } else {
                this.connection.setFixedLengthStreamingMode((int) contentLength);
            }
            body.writeTo(this.connection.getOutputStream());
        }
        return this.connection;
    }

    private Response readResponse() {
        Response.Builder responseBuilder = new Response.Builder(this.request, this.connection.getResponseCode());
        int i = 0;
        while (true) {
            String name = this.connection.getHeaderFieldKey(i);
            if (name != null) {
                responseBuilder.addHeader(name, this.connection.getHeaderField(i));
                i++;
            } else {
                responseBuilder.body(new RealResponseBody(this.connection, this.connection.getInputStream()));
                return responseBuilder.build();
            }
        }
    }
}
