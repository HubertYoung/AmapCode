package com.alipay.inside.android.phone.mrpc.core;

public interface TransportCallback {
    void onCancelled(Request request);

    void onFailed(Request request, int i, String str);

    void onPostExecute(Request request, Response response);

    void onPreExecute(Request request);

    void onProgressUpdate(Request request, double d);
}
