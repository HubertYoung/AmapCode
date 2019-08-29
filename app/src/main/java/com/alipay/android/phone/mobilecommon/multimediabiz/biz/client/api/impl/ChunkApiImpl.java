package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.ChunkApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.BaseApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.ChunkApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.exception.DjangoClientException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ChunksDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FileChunksInfoReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.GetChunksMetaReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ChunksDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileChunksInfoResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.GetChunksMetaResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.LiteStringUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

public class ChunkApiImpl extends BaseApiImpl implements ChunkApi {
    public ChunkApiImpl(DjangoClient djangoClient, ConnectionManager<HttpClient> connectionManager) {
        super(djangoClient, connectionManager);
    }

    public FileChunksInfoResp getFileChunksInfo(FileChunksInfoReq fileChunksInfoReq) {
        FileChunksInfoResp response;
        HttpGet method = null;
        HttpResponse resp = null;
        try {
            method = createHttpGet((BaseApiInfo) ChunkApiInfo.GET_FILE_CHUNKS_INFO, a("fileId", fileChunksInfoReq.getFileId()));
            fileChunksInfoReq.setHttpRequestBase(method);
            resp = ((HttpClient) this.connectionManager.getConnection(false)).execute(method);
            response = (FileChunksInfoResp) parseDjangoFileInfoResp(FileChunksInfoResp.class, resp);
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new FileChunksInfoResp();
            response.setCode(DjangoConstant.DJANGO_400);
            response.setMsg(e.getMessage());
        } finally {
            DjangoUtils.releaseConnection(method, resp);
        }
        return response;
    }

    public GetChunksMetaResp getChunksMeta(GetChunksMetaReq getChunksMetaReq) {
        GetChunksMetaResp response;
        HttpGet method = null;
        HttpResponse resp = null;
        try {
            method = createHttpGet((BaseApiInfo) ChunkApiInfo.GET_CHUNKS_META, a("chunkIds", getChunksMetaReq.getChunkIds()));
            getChunksMetaReq.setHttpRequestBase(method);
            resp = ((HttpClient) this.connectionManager.getConnection(false)).execute(method);
            response = (GetChunksMetaResp) parseDjangoFileInfoResp(GetChunksMetaResp.class, resp);
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new GetChunksMetaResp();
            response.setCode(DjangoConstant.DJANGO_400);
            response.setMsg(e.getMessage());
        } finally {
            DjangoUtils.releaseConnection(method, resp);
        }
        return response;
    }

    public ChunksDownResp downloadChunks(ChunksDownReq chunksDownReq) {
        try {
            if (LiteStringUtils.isBlank(chunksDownReq.getChunkIds())) {
                throw new DjangoClientException((String) "field[chunkIds] is null");
            }
            HttpGet method = createHttpGet((BaseApiInfo) ChunkApiInfo.DOWNLOAD_CHUNKS, a("chunkIds", chunksDownReq.getChunkIds()));
            chunksDownReq.setHttpRequestBase(method);
            HttpResponse resp = ((HttpClient) this.connectionManager.getConnection(false)).execute(method);
            ChunksDownResp response = (ChunksDownResp) parseDjangoFileInfoResp(ChunksDownResp.class, resp);
            response.setResp(resp);
            return response;
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            ChunksDownResp response2 = new ChunksDownResp();
            response2.setCode(DjangoConstant.DJANGO_400);
            response2.setMsg(e.getMessage());
            return response2;
        }
    }

    private List<NameValuePair> a(String idKey, String id) {
        List params = new ArrayList();
        String timestampStr = String.valueOf(System.currentTimeMillis());
        String acl = genAclString(id, timestampStr);
        params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
        params.add(new BasicNameValuePair(idKey, id));
        params.add(new BasicNameValuePair("timestamp", timestampStr));
        params.add(new BasicNameValuePair("acl", acl));
        params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, getTraceId()));
        return params;
    }
}
