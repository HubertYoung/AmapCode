package com.alipay.multimedia.network;

import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alipay.multimedia.apxmmusic.MusicFile;
import com.alipay.multimedia.common.logging.MLog;
import com.alipay.multimedia.common.logging.MusicDownloadBehavior;
import com.alipay.multimedia.utils.MusicUtils;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.request.Method;
import org.nanohttpd.protocols.http.response.IStatus;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

public class HttpServer extends NanoHTTPD {
    private static final String TAG = "HttpServer";
    private WeakReference<MusicFile> mCurrentFile = null;

    public HttpServer(String hostname, int port) {
        super(hostname, port);
    }

    /* access modifiers changed from: protected */
    public Response serve(IHTTPSession session) {
        String value;
        String sign;
        Response response;
        String value2;
        MLog.i(TAG, "serve start.url=" + session.getUri() + ",queryParameterString=" + session.getQueryParameterString() + ",header=" + session.getHeaders() + ",method=" + session.getMethod());
        MusicDownloadBehavior behavior = new MusicDownloadBehavior();
        if (Method.GET.equals(session.getMethod())) {
            String queryParameterString = session.getQueryParameterString();
            Map header = session.getHeaders();
            MLog.i(TAG, "serve.queryParameterString=" + queryParameterString + ",header=" + header);
            int realUrlStartIndex = queryParameterString.indexOf("realurl=");
            if (realUrlStartIndex < 0) {
                behavior.result = 2;
                behavior.submit();
                MLog.e(TAG, "serve.realurl not found.");
                return Response.newFixedLengthResponse((IStatus) Status.BAD_REQUEST, (String) "text/plain", (String) "real url not found.");
            }
            int realUrlEndIndex = queryParameterString.indexOf("&", realUrlStartIndex);
            if (realUrlEndIndex > 0) {
                value = queryParameterString.substring(realUrlStartIndex + 8, realUrlEndIndex);
            } else {
                value = queryParameterString.substring(realUrlStartIndex + 8);
            }
            int signStartIndex = queryParameterString.indexOf("sign=");
            if (signStartIndex < 0) {
                behavior.result = 3;
                behavior.submit();
                MLog.e(TAG, "serve.signStartIndex=" + signStartIndex);
                return Response.newFixedLengthResponse((IStatus) Status.BAD_REQUEST, (String) "text/plain", (String) "verify sign failed.");
            }
            int signEndIndex = queryParameterString.indexOf("&", signStartIndex);
            if (signEndIndex > 0) {
                sign = queryParameterString.substring(signStartIndex + 5, signEndIndex);
            } else {
                sign = queryParameterString.substring(signStartIndex + 5);
            }
            if (TextUtils.isEmpty(sign) || MusicUtils.verify(value, sign)) {
                behavior.result = 3;
                behavior.submit();
                MLog.e(TAG, "serve.verify failed.sign=" + sign);
                return Response.newFixedLengthResponse((IStatus) Status.BAD_REQUEST, (String) "text/plain", (String) "verify sign failed.");
            }
            String realUrl = new String(Base64.decode(value, 0));
            behavior.url = realUrl;
            int jsonExtraStartIndex = queryParameterString.indexOf("jsonextra=");
            String jsonExtra = null;
            if (jsonExtraStartIndex >= 0) {
                int jsonExtraEndIndex = queryParameterString.indexOf("&", jsonExtraStartIndex);
                if (jsonExtraEndIndex > 0) {
                    value2 = queryParameterString.substring(jsonExtraStartIndex + 10, jsonExtraEndIndex);
                } else {
                    value2 = queryParameterString.substring(jsonExtraStartIndex);
                }
                jsonExtra = new String(Base64.decode(value2, 0));
            }
            String fileId = null;
            if (!TextUtils.isEmpty(jsonExtra)) {
                fileId = JSON.parseObject(jsonExtra).getString("fid");
                behavior.fileId = fileId;
            }
            long rangeStart = 0;
            String rangeStr = null;
            if (header.get("range") != null) {
                rangeStr = header.get("range");
            } else if (header.get("Range") != null) {
                rangeStr = header.get("Range");
            }
            if (!TextUtils.isEmpty(rangeStr)) {
                rangeStart = Long.valueOf(rangeStr.substring(rangeStr.indexOf("=") + 1).split("-")[0]).longValue();
            }
            MLog.i(TAG, "header end.queryParameterString=" + queryParameterString + ",realUrl=" + realUrl + ", jsonExtra=" + jsonExtra + ",headers=" + header + ",rangeStart=" + rangeStart);
            try {
                stopMusicFile(false);
                MusicFile file = new MusicFile(realUrl, fileId, rangeStart, behavior);
                WeakReference weakReference = new WeakReference(file);
                this.mCurrentFile = weakReference;
                long contentLength = file.getContentLength();
                String contentType = file.getContentType();
                int status = file.getStatus();
                behavior.status = status;
                behavior.contentLength = contentLength;
                MLog.i(TAG, "serve.status=" + status + ",contentType=" + contentType + ",contentLength=" + contentLength);
                if (status != 200 && status != 206) {
                    behavior.result = 4;
                    behavior.submit();
                    stopMusicFile(false);
                    if (status == 403) {
                        return Response.newFixedLengthResponse((IStatus) Status.FORBIDDEN, (String) "text/plain", "status is " + status);
                    }
                    if (status == 401) {
                        return Response.newFixedLengthResponse((IStatus) Status.UNAUTHORIZED, (String) "text/plain", "status is " + status);
                    }
                    return Response.newFixedLengthResponse((IStatus) Status.INTERNAL_ERROR, (String) "text/plain", "status is " + status);
                } else if (contentLength <= 0) {
                    behavior.result = 5;
                    behavior.submit();
                    stopMusicFile(false);
                    return Response.newFixedLengthResponse((IStatus) Status.INTERNAL_ERROR, (String) "text/plain", (String) "content length get fail");
                } else {
                    if (rangeStart == 0) {
                        response = Response.newChunkedResponse(Status.OK, contentType, file);
                        response.addHeader("Content-Length", String.valueOf(contentLength));
                    } else {
                        response = Response.newChunkedResponse(Status.PARTIAL_CONTENT, contentType, file);
                        response.addHeader("Content-Range", "bytes " + rangeStart + "-" + (contentLength - 1) + "/" + contentLength);
                    }
                    response.addHeader("Content-Type", contentType);
                    response.addHeader("Accept-Ranges", "bytes");
                    return response;
                }
            } catch (Throwable e) {
                behavior.result = 10;
                behavior.submit();
                MLog.e(TAG, "serve exception.e=" + e);
                return Response.newFixedLengthResponse((IStatus) Status.INTERNAL_ERROR, (String) "text/plain", e.getMessage());
            }
        } else {
            behavior.result = 1;
            behavior.submit();
            MLog.e(TAG, "Method not GET.method=" + session.getMethod());
            return Response.newFixedLengthResponse((IStatus) Status.INTERNAL_ERROR, (String) "text/plain", (String) "method not supported");
        }
    }

    public int getErrorCode() {
        if (this.mCurrentFile != null) {
            MusicFile musicFile = (MusicFile) this.mCurrentFile.get();
            if (musicFile != null) {
                return musicFile.getErrorCode();
            }
        }
        return 0;
    }

    public void stopMusicFile(boolean isError) {
        if (this.mCurrentFile != null) {
            MusicFile musicFile = (MusicFile) this.mCurrentFile.get();
            if (musicFile != null) {
                musicFile.finish();
                if (isError) {
                    musicFile.onError();
                }
            }
        }
    }
}
