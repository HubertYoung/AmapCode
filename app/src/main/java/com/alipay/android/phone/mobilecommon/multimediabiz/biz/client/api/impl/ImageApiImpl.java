package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.ImageApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.BaseApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.ImageApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.exception.DjangoClientException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailMarkAddReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailMarkDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailsDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.AddMarkResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ThumbnailsDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.LiteStringUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.Consts;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.scansdk.constant.Constants;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class ImageApiImpl extends BaseApiImpl implements ImageApi {
    public ImageApiImpl(DjangoClient djangoClient, ConnectionManager<HttpClient> connectionManager) {
        super(djangoClient, connectionManager);
    }

    public ThumbnailsDownResp downloadThumbnails(ThumbnailsDownReq thumbnailsDownReq) {
        ThumbnailsDownResp response = new ThumbnailsDownResp();
        HttpGet method = null;
        HttpResponse httpResponse = null;
        String traceId = null;
        try {
            if (LiteStringUtils.isBlank(thumbnailsDownReq.getFileIds())) {
                throw new DjangoClientException((String) "field[fileIds] can not be null");
            } else if (LiteStringUtils.isBlank(thumbnailsDownReq.getZoom())) {
                throw new DjangoClientException((String) "field[zoom] can not be null");
            } else {
                List params = new ArrayList();
                traceId = a(params, thumbnailsDownReq.getFileIds());
                a(params, thumbnailsDownReq);
                ImageApiInfo imageApiInfo = ImageApiInfo.DOWNLOAD_THUMBNAILS;
                if (thumbnailsDownReq instanceof ThumbnailMarkDownReq) {
                    imageApiInfo = ImageApiInfo.DOWNLOAD_THUMBNAILS_WARTERMARK;
                }
                method = createHttpGet((BaseApiInfo) imageApiInfo, params);
                thumbnailsDownReq.setHttpRequestBase(method);
                if (thumbnailsDownReq.getRange() > 0) {
                    method.setHeader("Range", String.format("bytes=%d-", new Object[]{Long.valueOf(thumbnailsDownReq.getRange())}));
                }
                method.getParams().setParameter("bizId", thumbnailsDownReq.getBizId());
                if (thumbnailsDownReq.mTimeout > 0) {
                    addIntParams(method, Consts.KEY_TIME_OUT, thumbnailsDownReq.mTimeout);
                }
                httpResponse = ((HttpClient) this.connectionManager.getConnection(thumbnailsDownReq.isbHttps())).execute(method);
                if (httpResponse.getStatusLine().getStatusCode() == 200 || httpResponse.getStatusLine().getStatusCode() == 206) {
                    response.setResp(httpResponse);
                    response.setCode(DjangoConstant.DJANGO_OK);
                } else {
                    response.setCode(httpResponse.getStatusLine().getStatusCode());
                    response.setMsg("Http invoker error :" + response.getCode());
                }
                response.setMethod(method);
                if (!TextUtils.isEmpty(traceId)) {
                    response.setTraceId(traceId);
                }
                if (!response.isSuccess()) {
                    DjangoUtils.releaseConnection(method, httpResponse);
                }
                return response;
            }
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            String msg = e.getMessage();
            if (com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils.isEmpty(msg)) {
                msg = e.getClass().getSimpleName();
            }
            response.setMsg(msg);
            DjangoUtils.releaseConnection(method, httpResponse);
            if (!TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
            if (!response.isSuccess()) {
                DjangoUtils.releaseConnection(method, httpResponse);
            }
        } catch (Throwable th) {
            if (!TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
            if (!response.isSuccess()) {
                DjangoUtils.releaseConnection(method, httpResponse);
            }
            throw th;
        }
    }

    private static void a(List<NameValuePair> params, ThumbnailsDownReq thumbnailsDownReq) {
        params.add(new BasicNameValuePair("fileIds", thumbnailsDownReq.getFileIds()));
        params.add(new BasicNameValuePair("zoom", thumbnailsDownReq.getZoom()));
        if (!TextUtils.isEmpty(thumbnailsDownReq.getZoom2())) {
            params.add(new BasicNameValuePair("zoom2", thumbnailsDownReq.getZoom2()));
        }
        if (LiteStringUtils.isNotBlank(thumbnailsDownReq.getSource())) {
            params.add(new BasicNameValuePair("source", thumbnailsDownReq.getSource()));
        }
        if (thumbnailsDownReq instanceof ThumbnailMarkDownReq) {
            ThumbnailMarkDownReq req = (ThumbnailMarkDownReq) thumbnailsDownReq;
            params.add(new BasicNameValuePair(Constants.SERVICE_SOURCE_ID, req.getMarkId()));
            params.add(new BasicNameValuePair("position", String.valueOf(req.getPosition())));
            params.add(new BasicNameValuePair("transparency", String.valueOf(req.getTransparency())));
            params.add(new BasicNameValuePair("width", String.valueOf(req.getMarkWidth())));
            params.add(new BasicNameValuePair("height", String.valueOf(req.getMarkHeight())));
            params.add(new BasicNameValuePair(DictionaryKeys.CTRLXY_X, String.valueOf(req.getPaddingX())));
            params.add(new BasicNameValuePair(DictionaryKeys.CTRLXY_Y, String.valueOf(req.getPaddingY())));
            if (req.getPercent() != null) {
                params.add(new BasicNameValuePair("P", String.valueOf(req.getPercent())));
            }
        }
    }

    private String a(List<NameValuePair> params, String id) {
        String traceId = getTraceId();
        String timestampStr = String.valueOf(System.currentTimeMillis());
        String acl = genAclString(id, timestampStr);
        params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
        params.add(new BasicNameValuePair("timestamp", timestampStr));
        params.add(new BasicNameValuePair("acl", acl));
        if (LiteStringUtils.isNotBlank(traceId)) {
            params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
        }
        return traceId;
    }

    public AddMarkResp addWaterMark(ThumbnailMarkAddReq thumbnailMarkAddReq) {
        AddMarkResp response = new AddMarkResp();
        try {
            List params = new ArrayList();
            String traceId = a(params, thumbnailMarkAddReq.getFileIds());
            a(params, (ThumbnailsDownReq) thumbnailMarkAddReq);
            HttpPost method = createHttpPost(ImageApiInfo.ADD_THUMBNAILS_WARTERMARK, params);
            if (DjangoClient.DEBUG) {
                Logger.D(DjangoClient.LOG_TAG, "addWaterMark: " + Arrays.toString(method.getAllHeaders()), new Object[0]);
            }
            thumbnailMarkAddReq.setHttpRequestBase(method);
            if (thumbnailMarkAddReq.mTimeout > 0) {
                addIntParams(method, Consts.KEY_TIME_OUT, thumbnailMarkAddReq.mTimeout);
            }
            HttpResponse httpResponse = ((HttpClient) this.connectionManager.getConnection(false)).execute(method);
            response = (AddMarkResp) parseDjangoFileInfoResp(AddMarkResp.class, httpResponse);
            if (response != null && !TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
            DjangoUtils.releaseConnection(method, httpResponse);
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            String msg = e.getMessage();
            if (com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils.isEmpty(msg)) {
                msg = e.getClass().getSimpleName();
            }
            response.setMsg(msg);
            if (!TextUtils.isEmpty(null)) {
                response.setTraceId(null);
            }
            DjangoUtils.releaseConnection(null, null);
        } catch (Throwable th) {
            if (!TextUtils.isEmpty(null)) {
                response.setTraceId(null);
            }
            DjangoUtils.releaseConnection(null, null);
            throw th;
        }
        return response;
    }
}
