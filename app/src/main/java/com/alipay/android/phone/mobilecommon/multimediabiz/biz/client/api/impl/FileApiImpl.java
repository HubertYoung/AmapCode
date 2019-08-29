package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.FileApi;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.BaseApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.FileApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.exception.DjangoClientException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.ProgressChunkByteArrayBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.ProgressChunkFileBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.ProgressFileBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.ProgressInputStreamBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.SliceProgressFileBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.SliceProgressInputStreamBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.BaseUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ChunkUpTxnCommitReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ChunkUpTxnOpenReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ChunkUpTxnProcessReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FileOfflineUploadReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FileRapidUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FileUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FilesDelReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FilesDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.GetFilesMetaReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.InputStreamUpReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.SetExtReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ChunkUpTxnCommitResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ChunkUpTxnOpenResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ChunkUpTxnProcessResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.DjangoFileInfoResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileOfflineUploadResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileParallelUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FilesDelResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FilesDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.GetFilesMetaResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.SetExtResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.LiteStringUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.Consts;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.HttpMultipartMode;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.MultipartEntityBuilder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.ContentBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DiskExpUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class FileApiImpl extends BaseApiImpl implements FileApi {
    public FileApiImpl(DjangoClient djangoClient, ConnectionManager<HttpClient> connectionManager) {
        super(djangoClient, connectionManager);
    }

    public FileUpResp uploadDirectRapid(FileRapidUpReq fileRapidUpReq) {
        FileUpResp response;
        try {
            List params = new ArrayList();
            params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
            if (LiteStringUtils.isNotBlank(fileRapidUpReq.getMd5())) {
                params.add(new BasicNameValuePair("md5", fileRapidUpReq.getMd5()));
            }
            if (LiteStringUtils.isNotBlank(fileRapidUpReq.getGcid())) {
                params.add(new BasicNameValuePair("gcid", fileRapidUpReq.getGcid()));
            }
            if (LiteStringUtils.isNotBlank(fileRapidUpReq.getExt())) {
                params.add(new BasicNameValuePair(ProcessInfo.ALIAS_EXT, fileRapidUpReq.getExt()));
            }
            String traceId = getTraceId();
            if (LiteStringUtils.isNotBlank(traceId)) {
                params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
            }
            if (fileRapidUpReq.getPublic() != null) {
                params.add(new BasicNameValuePair("setpublic", String.valueOf(fileRapidUpReq.getPublic())));
            }
            HttpGet method = createHttpGet(FileApiInfo.UPLOAD_DIRECT_RAPID, params, false);
            fileRapidUpReq.setHttpRequestBase(method);
            addParams(method, "uploadType", "2");
            if (fileRapidUpReq.mTimeout > 0) {
                addIntParams(method, Consts.KEY_TIME_OUT, fileRapidUpReq.mTimeout);
            }
            HttpResponse resp = a().execute(method);
            response = (FileUpResp) parseDjangoFileInfoResp(FileUpResp.class, resp);
            if (response != null && response.isSuccess()) {
                response.setRapid(true);
            }
            if (response != null && !TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
            DjangoUtils.releaseConnection(method, resp);
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new FileUpResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
            if (!TextUtils.isEmpty(null)) {
                response.setTraceId(null);
            }
            DjangoUtils.releaseConnection(null, null);
        } catch (Throwable th) {
            th = th;
        }
        return response;
        if (response != null && !TextUtils.isEmpty(null)) {
            response.setTraceId(null);
        }
        DjangoUtils.releaseConnection(null, null);
        throw th;
    }

    public FileUpResp uploadDirect(FileUpReq fileUpReq) {
        FileUpResp response;
        FileUpResp response2 = null;
        HttpRequestBase method = null;
        HttpResponse resp = null;
        String traceId = null;
        try {
            if (fileUpReq.getFile() == null) {
                throw new DjangoClientException((String) "Field[file] is null!");
            }
            boolean rapidSuccess = false;
            boolean isNotBlankMd5 = LiteStringUtils.isNotBlank(LiteStringUtils.trimToEmpty(fileUpReq.getMd5()));
            if (!fileUpReq.isSkipRapid() && (isNotBlankMd5 || LiteStringUtils.isNotBlank(fileUpReq.getGcid()))) {
                FileRapidUpReq rapidUpReq = new FileRapidUpReq(fileUpReq.getMd5(), fileUpReq.getGcid());
                rapidUpReq.setPublic(fileUpReq.getPublic());
                rapidUpReq.setExt(fileUpReq.getExt());
                response2 = uploadDirectRapid(rapidUpReq);
                rapidSuccess = response2 != null && response2.isSuccess();
            }
            if (!rapidSuccess) {
                traceId = getTraceId();
                method = a(FileApiInfo.UPLOAD_DIRECT, fileUpReq, a(fileUpReq, new ProgressFileBody(fileUpReq.getFile(), a(fileUpReq), fileUpReq.getTransferedListener())), traceId);
                fileUpReq.setHttpRequestBase(method);
                addParams(method, "uploadType", "-1");
                resp = a().execute(method);
                response2 = (FileUpResp) parseDjangoFileInfoResp(FileUpResp.class, resp);
            } else {
                response2.setRapid(true);
            }
            if (!TextUtils.isEmpty(traceId) && response2 != null && TextUtils.isEmpty(response2.getTraceId())) {
                response2.setTraceId(traceId);
            }
            DjangoUtils.releaseConnection(method, resp);
            return response2;
        } catch (Exception e) {
            response = response2;
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response2 = new FileUpResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response2.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response2.setCode(DjangoConstant.DJANGO_400);
            }
            String msg = e.getMessage();
            if (TextUtils.isEmpty(msg)) {
                msg = e.getClass().getSimpleName();
            }
            response2.setMsg(msg);
            if (!TextUtils.isEmpty(null) && TextUtils.isEmpty(response2.getTraceId())) {
                response2.setTraceId(null);
            }
            DjangoUtils.releaseConnection(null, null);
        } catch (Throwable th) {
            th = th;
        }
        if (!TextUtils.isEmpty(null) && response2 != null && TextUtils.isEmpty(response2.getTraceId())) {
            response2.setTraceId(null);
        }
        DjangoUtils.releaseConnection(null, null);
        throw th;
    }

    private static String a(FileUpReq fileUpReq) {
        String aliasFileName = null;
        if (fileUpReq != null && !TextUtils.isEmpty(fileUpReq.getExt())) {
            String ext = fileUpReq.getExt();
            if (ext.indexOf(46) < 0) {
                ext = "." + ext;
            }
            aliasFileName = fileUpReq.getFileName() + ext;
        }
        Logger.D("FileApi", "getAliasFileName aliasFileName: " + aliasFileName, new Object[0]);
        return aliasFileName;
    }

    public FileUpResp uploadDirect(InputStreamUpReq upReq) {
        FileUpResp response = null;
        HttpRequestBase method = null;
        HttpResponse resp = null;
        String traceId = null;
        try {
            if (upReq.getInputStream() == null || LiteStringUtils.isBlank(upReq.getFileName())) {
                throw new DjangoClientException((String) "Field[inputStream] or [fileName] is null!");
            }
            boolean rapidSuccess = false;
            String md5 = LiteStringUtils.trimToEmpty(upReq.getMd5());
            boolean isNotBlankMd5 = LiteStringUtils.isNotBlank(md5);
            if (!upReq.isSkipRapid() && (isNotBlankMd5 || LiteStringUtils.isNotBlank(upReq.getGcid()))) {
                FileRapidUpReq fileRapidUpReq = new FileRapidUpReq(md5, upReq.getGcid());
                fileRapidUpReq.setPublic(upReq.getPublic());
                fileRapidUpReq.setExt(upReq.getExt());
                response = uploadDirectRapid(fileRapidUpReq);
                rapidSuccess = response != null && response.isSuccess();
            }
            if (!rapidSuccess) {
                traceId = getTraceId();
                method = a(FileApiInfo.UPLOAD_DIRECT, upReq, a(upReq, new ProgressInputStreamBody(upReq.getInputStream(), upReq.getFileName(), upReq.getTotalLength(), upReq.getTransferedListener())), traceId);
                upReq.setHttpRequestBase(method);
                addParams(method, "uploadType", "-1");
                resp = ((HttpClient) this.connectionManager.getConnection()).execute(method);
                response = (FileUpResp) parseDjangoFileInfoResp(FileUpResp.class, resp);
            }
            if (!TextUtils.isEmpty(traceId) && response != null && TextUtils.isEmpty(response.getTraceId())) {
                response.setTraceId(traceId);
            }
            DjangoUtils.releaseConnection(method, resp);
            return response;
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new FileUpResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            String msg = e.getMessage();
            if (TextUtils.isEmpty(msg)) {
                msg = e.getClass().getSimpleName();
            }
            response.setMsg(msg);
            if (!TextUtils.isEmpty(null) && TextUtils.isEmpty(response.getTraceId())) {
                response.setTraceId(null);
            }
            DjangoUtils.releaseConnection(null, null);
        } catch (Throwable th) {
            th = th;
        }
        if (!TextUtils.isEmpty(null) && response != null && TextUtils.isEmpty(response.getTraceId())) {
            response.setTraceId(null);
        }
        DjangoUtils.releaseConnection(null, null);
        throw th;
    }

    public FileUpResp uploadRange(FileUpReq fileUpReq) {
        HttpRequestBase method = null;
        HttpResponse resp = null;
        FileUpResp response = null;
        if (fileUpReq == null || fileUpReq.getFile() == null) {
            throw new DjangoClientException((String) "Field[file] is null!");
        }
        try {
            HttpEntity entity = a(fileUpReq, new SliceProgressFileBody(fileUpReq.getFile(), fileUpReq.getFileName(), fileUpReq.getStartPos(), fileUpReq.getEndPos(), fileUpReq.getTransferedListener()));
            String traceId = getTraceId();
            method = a(FileApiInfo.UPLOAD_FILE_RANGE, fileUpReq, entity, traceId);
            fileUpReq.setHttpRequestBase(method);
            addParams(method, "uploadType", "-1");
            resp = a().execute(method);
            if (!(resp == null || resp.getStatusLine() == null || resp.getStatusLine().getStatusCode() == 200)) {
                Logger.D(DjangoClient.LOG_TAG, "uploadRange req:" + fileUpReq + " sc: " + resp.getStatusLine().getStatusCode() + " ti:" + traceId, new Object[0]);
            }
            response = a(resp);
            if (!TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, "uploadRange req: " + fileUpReq + " ti:" + "", new Object[0]);
        } finally {
            DjangoUtils.releaseConnection(method, resp);
        }
        return response;
    }

    public FileUpResp uploadRange(InputStreamUpReq upReq) {
        HttpRequestBase method = null;
        HttpResponse resp = null;
        FileUpResp response = null;
        if (upReq == null || upReq.getInputStream() == null) {
            throw new DjangoClientException((String) "Field[file] is null!");
        }
        try {
            HttpEntity entity = a(upReq, new SliceProgressInputStreamBody(upReq.getInputStream(), upReq.getFileName(), upReq.getStartPos(), upReq.getEndPos(), upReq.getTransferedListener()));
            String traceId = getTraceId();
            method = a(FileApiInfo.UPLOAD_FILE_RANGE, upReq, entity, traceId);
            upReq.setHttpRequestBase(method);
            addParams(method, "uploadType", "-1");
            resp = a().execute(method);
            if (!(resp == null || resp.getStatusLine() == null || resp.getStatusLine().getStatusCode() == 200)) {
                Logger.D(DjangoClient.LOG_TAG, "uploadRange upReq:" + upReq + " sc: " + resp.getStatusLine().getStatusCode() + " ti:" + traceId, new Object[0]);
            }
            response = a(resp);
            if (!TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, "uploadRange req: " + upReq + " ti:" + "", new Object[0]);
        } finally {
            DjangoUtils.releaseConnection(method, resp);
        }
        return response;
    }

    private static FileUpResp a(HttpResponse response) {
        FileUpResp resp;
        if (response == null || response.getStatusLine().getStatusCode() != 200) {
            resp = new FileUpResp();
            resp.setCode(response == null ? DjangoConstant.DJANGO_400 : response.getStatusLine().getStatusCode());
            resp.setMsg("Http invoker error: " + resp.getCode());
        } else {
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (DjangoClient.DEBUG) {
                Logger.D(DjangoClient.LOG_TAG, "parseRangeResponse() :" + content, new Object[0]);
            }
            resp = new FileUpResp();
            JSONObject js = new JSONObject(content);
            int code = js.getInt("code");
            if (code == DjangoConstant.DJANGO_OK) {
                resp.setFileInfo((DjangoFileInfoResp) JSON.parseObject(js.getString("data"), DjangoFileInfoResp.class));
                resp.setRapid(true);
                resp.setCode(DjangoConstant.DJANGO_OK);
            } else if (code == 206) {
                resp.setCode(code);
                resp.setRapid(false);
                resp.setRange(js.getInt("data"));
            } else {
                resp.setCode(code);
                resp.setRapid(false);
            }
        }
        Logger.D(DjangoClient.LOG_TAG, "uploadRsp: " + response, new Object[0]);
        return resp;
    }

    private HttpEntity a(BaseUpReq req, ContentBody filePartBody) {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(DjangoConstant.DEFAULT_CHARSET);
        multipartEntityBuilder.addTextBody("token", this.tokenApi.getTokenString());
        if (!TextUtils.isEmpty(req.getMd5())) {
            multipartEntityBuilder.addTextBody("md5", req.getMd5());
        }
        multipartEntityBuilder.addPart("file", filePartBody);
        return multipartEntityBuilder.build();
    }

    private HttpRequestBase a(FileApiInfo apiInfo, BaseUpReq upReq, HttpEntity entity, String traceId) {
        long offset = (long) upReq.getStartPos();
        String md5 = upReq.getMd5();
        boolean isNotBlankMd5 = LiteStringUtils.isNotBlank(md5);
        List urlParams = new ArrayList();
        if (LiteStringUtils.isNotBlank(traceId)) {
            urlParams.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
        }
        if (isNotBlankMd5) {
            urlParams.add(new BasicNameValuePair("md5", md5));
        }
        if (upReq.getPublic() != null) {
            urlParams.add(new BasicNameValuePair("setpublic", String.valueOf(upReq.getPublic())));
        }
        String rangeInfo = null;
        if (isNotBlankMd5 && (offset > 0 || upReq.getEndPos() > 0)) {
            long length = upReq.getTotalLength();
            StringBuilder rangeBuilder = new StringBuilder("bytes=").append(offset).append("-");
            if (upReq.getEndPos() > 0 && ((long) upReq.getEndPos()) > offset) {
                rangeBuilder.append(upReq.getEndPos());
            }
            rangeBuilder.append("/").append(length);
            rangeInfo = rangeBuilder.toString();
            urlParams.add(new BasicNameValuePair("Range", rangeInfo));
        }
        HttpPost method = createHttpPost(apiInfo, urlParams);
        method.expectContinue();
        method.setEntity(entity);
        if (!TextUtils.isEmpty(rangeInfo)) {
            method.addHeader("Range", rangeInfo);
        }
        if (upReq.mTimeout > 0) {
            addIntParams(method, Consts.KEY_TIME_OUT, upReq.mTimeout);
        }
        if (DjangoClient.DEBUG) {
            Logger.D(DjangoClient.LOG_TAG, "createUploadHttpRequest: " + Arrays.toString(method.getAllHeaders()), new Object[0]);
        }
        return method;
    }

    public ChunkUpTxnOpenResp uploadChunkOpen(ChunkUpTxnOpenReq openReq) {
        ChunkUpTxnOpenResp response;
        ChunkUpTxnOpenResp response2 = null;
        try {
            long fileSize = openReq.getSize();
            if (fileSize <= 0) {
                throw new DjangoClientException((String) "file is empty");
            }
            long chunkSize = openReq.getChunkSize();
            long chunkNumber = (long) openReq.getNumber();
            if (chunkSize > 0 || chunkNumber > 0) {
                if (chunkSize > 0) {
                    chunkNumber = fileSize / chunkSize;
                    if (fileSize % chunkSize != 0) {
                        chunkNumber++;
                    }
                }
                List params = new ArrayList();
                params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
                params.add(new BasicNameValuePair("md5", LiteStringUtils.trimToEmpty(openReq.getMd5())));
                params.add(new BasicNameValuePair("size", String.valueOf(fileSize)));
                params.add(new BasicNameValuePair("number", String.valueOf(chunkNumber)));
                params.add(new BasicNameValuePair(ProcessInfo.ALIAS_EXT, LiteStringUtils.trimToEmpty(openReq.getExtension())));
                String traceId = getTraceId();
                if (LiteStringUtils.isNotBlank(traceId)) {
                    params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
                }
                if (openReq.getPublic() != null) {
                    params.add(new BasicNameValuePair("setpublic", String.valueOf(openReq.getPublic())));
                }
                HttpGet method = createHttpGet(FileApiInfo.UPLOAD_CHUNK_OPEN, params, false);
                openReq.setHttpRequestBase(method);
                addParams(method, "uploadType", "-1");
                if (openReq.mTimeout > 0) {
                    addIntParams(method, Consts.KEY_TIME_OUT, openReq.mTimeout);
                }
                HttpResponse resp = a().execute(method);
                ChunkUpTxnOpenResp response3 = (ChunkUpTxnOpenResp) parseDjangoFileInfoResp(ChunkUpTxnOpenResp.class, resp);
                if (response3 != null) {
                    response3.setTraceId(traceId);
                }
                DjangoUtils.releaseConnection(method, resp);
                return response3;
            }
            throw new DjangoClientException((String) "Must give chunk size or chunk number");
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new ChunkUpTxnOpenResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
            response.setTraceId("");
            DjangoUtils.releaseConnection(null, null);
            return response;
        } catch (Throwable th) {
            th = th;
            response2 = response;
        }
        if (response2 != null) {
            response2.setTraceId("");
        }
        DjangoUtils.releaseConnection(null, null);
        throw th;
    }

    public ChunkUpTxnProcessResp uploadChunkProcessRapid(ChunkUpTxnProcessReq processReq) {
        ChunkUpTxnProcessResp response;
        try {
            if (!LiteStringUtils.isBlank(processReq.getMd5()) || !LiteStringUtils.isBlank(processReq.getGcid())) {
                List params = new ArrayList();
                params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
                params.add(new BasicNameValuePair("fileId", processReq.getFileId()));
                params.add(new BasicNameValuePair("sequence", String.valueOf(processReq.getSequence())));
                if (LiteStringUtils.isNotBlank(processReq.getMd5())) {
                    params.add(new BasicNameValuePair("md5", processReq.getMd5()));
                }
                if (LiteStringUtils.isNotBlank(processReq.getGcid())) {
                    params.add(new BasicNameValuePair("gcid", processReq.getGcid()));
                }
                String traceId = getTraceId();
                if (LiteStringUtils.isNotBlank(traceId)) {
                    params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
                }
                HttpGet method = createHttpGet(FileApiInfo.UPLOAD_CHUNK_PROCESS_RAPID, params, false);
                processReq.setHttpRequestBase(method);
                addParams(method, "uploadType", "2");
                if (processReq.mTimeout > 0) {
                    addIntParams(method, Consts.KEY_TIME_OUT, processReq.mTimeout);
                }
                HttpResponse resp = a().execute(method);
                response = (ChunkUpTxnProcessResp) parseDjangoFileInfoResp(ChunkUpTxnProcessResp.class, resp);
                if (response != null && response.isSuccess()) {
                    response.setRapid(true);
                }
                if (response != null) {
                    response.setTraceId(traceId);
                }
                DjangoUtils.releaseConnection(method, resp);
                return response;
            }
            throw new IllegalArgumentException("Parameter processReq.getMd5() or processReq.getGcid() can not be null !");
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new ChunkUpTxnProcessResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
            response.setTraceId("");
            DjangoUtils.releaseConnection(null, null);
        } catch (Throwable th) {
            th = th;
        }
        if (response != null) {
            response.setTraceId("");
        }
        DjangoUtils.releaseConnection(null, null);
        throw th;
    }

    public ChunkUpTxnProcessResp uploadChunkProcess(ChunkUpTxnProcessReq processReq) {
        ChunkUpTxnProcessResp response;
        ContentBody fileBody;
        ChunkUpTxnProcessResp response2 = null;
        HttpPost method = null;
        HttpResponse resp = null;
        String traceId = "";
        try {
            int sequence = processReq.getSequence();
            if (LiteStringUtils.isBlank(processReq.getFileId())) {
                throw new DjangoClientException((String) "field[fileId] is null");
            } else if (sequence < 0) {
                throw new DjangoClientException((String) "field[sequence] must greater than 0");
            } else if (processReq.getFile() == null && processReq.getData() == null) {
                throw new DjangoClientException((String) "field[file] is null");
            } else if (processReq.getChunkSize() < 1) {
                throw new DjangoClientException((String) "field[chunkSize] must greater than 0");
            } else {
                boolean isRapid = false;
                if (LiteStringUtils.isNotBlank(processReq.getMd5()) || LiteStringUtils.isNotBlank(processReq.getGcid())) {
                    response2 = uploadChunkProcessRapid(processReq);
                    isRapid = response2 != null && response2.isSuccess();
                }
                if (!isRapid) {
                    String token = this.tokenApi.getTokenString();
                    ArrayList arrayList = new ArrayList();
                    traceId = getTraceId();
                    if (LiteStringUtils.isNotBlank(traceId)) {
                        arrayList.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
                    }
                    method = createHttpPost(FileApiInfo.UPLOAD_CHUNK_PROCESS, arrayList, false);
                    processReq.setHttpRequestBase(method);
                    addParams(method, "uploadType", "-1");
                    if (processReq.mTimeout > 0) {
                        addIntParams(method, Consts.KEY_TIME_OUT, processReq.mTimeout);
                    }
                    MultipartEntityBuilder multipartEntityBuilder = genMultipartEntityBuilder();
                    multipartEntityBuilder.addTextBody("token", token);
                    multipartEntityBuilder.addTextBody("md5", LiteStringUtils.trimToEmpty(processReq.getMd5()));
                    multipartEntityBuilder.addTextBody("fileId", processReq.getFileId());
                    multipartEntityBuilder.addTextBody("sequence", String.valueOf(sequence));
                    long chunkSize = processReq.getChunkSize();
                    if (processReq.getData() != null) {
                        fileBody = new ProgressChunkByteArrayBody(processReq.getData(), sequence, (int) chunkSize, processReq.getMd5(), processReq.getChunkTransListener());
                    } else {
                        fileBody = new ProgressChunkFileBody(processReq.getFile(), sequence, chunkSize, processReq.getChunkTransListener());
                    }
                    multipartEntityBuilder.addPart("file", fileBody);
                    method.setEntity(multipartEntityBuilder.build());
                    resp = a().execute(method);
                    response2 = (ChunkUpTxnProcessResp) parseDjangoFileInfoResp(ChunkUpTxnProcessResp.class, resp);
                } else if (processReq.getChunkTransListener() != null) {
                    processReq.getChunkTransListener().onChunkTransferred(processReq.getSequence(), processReq.getRealChunkSize());
                }
                if (response2 != null) {
                    response2.setTraceId(traceId);
                }
                DjangoUtils.releaseConnection(method, resp);
                return response2;
            }
        } catch (Exception e) {
            response = response2;
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response2 = new ChunkUpTxnProcessResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response2.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response2.setCode(DjangoConstant.DJANGO_400);
            }
            response2.setMsg(e.getMessage());
            response2.setTraceId(traceId);
            DjangoUtils.releaseConnection(method, null);
        } catch (Throwable th) {
            th = th;
        }
        if (response2 != null) {
            response2.setTraceId(traceId);
        }
        DjangoUtils.releaseConnection(method, null);
        throw th;
    }

    public ChunkUpTxnCommitResp uploadChunkCommit(ChunkUpTxnCommitReq commitReq) {
        ChunkUpTxnCommitResp response;
        ChunkUpTxnCommitResp response2 = null;
        try {
            if (LiteStringUtils.isBlank(commitReq.getFileId())) {
                throw new DjangoClientException((String) "field[fileId] is null");
            }
            String timestampStr = String.valueOf(System.currentTimeMillis());
            String acl = genAclString(commitReq.getFileId(), timestampStr);
            List params = new ArrayList();
            params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
            params.add(new BasicNameValuePair("fileId", commitReq.getFileId()));
            params.add(new BasicNameValuePair("timestamp", timestampStr));
            params.add(new BasicNameValuePair("acl", acl));
            List urlParams = new ArrayList();
            String traceId = getTraceId();
            if (LiteStringUtils.isNotBlank(traceId)) {
                urlParams.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
            }
            HttpPost method = createHttpPost(FileApiInfo.UPLOAD_CHUNK_COMMIT, urlParams);
            commitReq.setHttpRequestBase(method);
            method.setEntity(new UrlEncodedFormEntity(params));
            if (commitReq.mTimeout > 0) {
                addIntParams(method, Consts.KEY_TIME_OUT, commitReq.mTimeout);
            }
            HttpResponse resp = a().execute(method);
            ChunkUpTxnCommitResp response3 = (ChunkUpTxnCommitResp) parseDjangoFileInfoResp(ChunkUpTxnCommitResp.class, resp);
            if (response3 != null) {
                response3.setTraceId(traceId);
            }
            DjangoUtils.releaseConnection(method, resp);
            return response3;
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new ChunkUpTxnCommitResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
            response.setTraceId("");
            DjangoUtils.releaseConnection(null, null);
            return response;
        } catch (Throwable th) {
            th = th;
            response2 = response;
        }
        if (response2 != null) {
            response2.setTraceId("");
        }
        DjangoUtils.releaseConnection(null, null);
        throw th;
    }

    private HttpClient a() {
        return (HttpClient) this.connectionManager.getConnection();
    }

    public GetFilesMetaResp getFilesMeta(GetFilesMetaReq req) {
        GetFilesMetaResp response;
        HttpGet method = null;
        HttpResponse resp = null;
        try {
            if (LiteStringUtils.isBlank(req.getFileIds())) {
                throw new DjangoClientException((String) "field[fileIds] is null");
            }
            String timestampStr = String.valueOf(System.currentTimeMillis());
            String acl = genAclString(req.getFileIds(), timestampStr);
            List params = new ArrayList();
            params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
            params.add(new BasicNameValuePair("fileIds", req.getFileIds()));
            params.add(new BasicNameValuePair("timestamp", timestampStr));
            params.add(new BasicNameValuePair("acl", acl));
            String traceId = getTraceId();
            if (LiteStringUtils.isNotBlank(traceId)) {
                params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
            }
            method = createHttpGet((BaseApiInfo) FileApiInfo.GET_FILES_META, params);
            if (req.mTimeout > 0) {
                addIntParams(method, Consts.KEY_TIME_OUT, req.mTimeout);
            }
            req.setHttpRequestBase(method);
            resp = ((HttpClient) this.connectionManager.getConnection(false)).execute(method);
            response = (GetFilesMetaResp) parseDjangoFileInfoResp(GetFilesMetaResp.class, resp);
            return response;
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new GetFilesMetaResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
        } finally {
            DjangoUtils.releaseConnection(method, resp);
        }
    }

    public FilesDownResp downloadBatch(FilesDownReq req) {
        HttpGet method;
        FilesDownResp response = new FilesDownResp();
        String traceId = null;
        try {
            if (LiteStringUtils.isBlank(req.getFileIds())) {
                throw new DjangoClientException((String) "field[fileIds] is null");
            }
            if (req.getForceUrl()) {
                method = createHttpGet(req.getSource(), req.getFileIds());
            } else {
                String timestampStr = String.valueOf(System.currentTimeMillis());
                String acl = genAclString(req.getFileIds(), timestampStr);
                List params = new ArrayList();
                params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
                params.add(new BasicNameValuePair("fileIds", req.getFileIds()));
                params.add(new BasicNameValuePair("timestamp", timestampStr));
                params.add(new BasicNameValuePair("acl", acl));
                if (LiteStringUtils.isNotBlank(req.getSource())) {
                    params.add(new BasicNameValuePair("source", req.getSource()));
                }
                traceId = getTraceId();
                if (LiteStringUtils.isNotBlank(traceId)) {
                    params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
                }
                method = createHttpGet((BaseApiInfo) FileApiInfo.DOWNLOAD_BATCH, params);
            }
            req.setHttpRequestBase(method);
            if (LiteStringUtils.isNotBlank(req.getRange())) {
                method.addHeader("Range", req.getRange());
            }
            if (req.mTimeout > 0) {
                addIntParams(method, Consts.KEY_TIME_OUT, req.mTimeout);
            }
            if (DjangoClient.DEBUG) {
                Logger.D(DjangoClient.LOG_TAG, Arrays.toString(method.getAllHeaders()), new Object[0]);
            }
            HttpResponse resp = ((HttpClient) this.connectionManager.getConnection(req.isbHttps())).execute(method);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode == 200 || statusCode == 206) {
                response.setResp(resp);
                response.setCode(DjangoConstant.DJANGO_OK);
            } else {
                response.setCode(statusCode);
                response.setMsg("http invoker error!");
            }
            response.setMethod(method);
            if (!TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
            return response;
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
            DjangoUtils.releaseConnection(null, null);
            if (!TextUtils.isEmpty(null)) {
                response.setTraceId(null);
            }
        } catch (Throwable th) {
            if (!TextUtils.isEmpty(null)) {
                response.setTraceId(null);
            }
            throw th;
        }
    }

    public FilesDelResp deleteBatch(FilesDelReq req) {
        FilesDelResp response;
        HttpDelete method = null;
        HttpResponse resp = null;
        try {
            if (LiteStringUtils.isBlank(req.getFileIds())) {
                throw new DjangoClientException((String) "field[fileIds] is null");
            }
            String timestampStr = String.valueOf(System.currentTimeMillis());
            String acl = genAclString(req.getFileIds(), timestampStr);
            List params = new ArrayList();
            params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
            params.add(new BasicNameValuePair("fileIds", req.getFileIds()));
            params.add(new BasicNameValuePair("timestamp", timestampStr));
            params.add(new BasicNameValuePair("acl", acl));
            String traceId = getTraceId();
            if (LiteStringUtils.isNotBlank(traceId)) {
                params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
            }
            method = createHttpDelete(FileApiInfo.DELETE_BATCH, params);
            req.setHttpRequestBase(method);
            if (req.mTimeout > 0) {
                addIntParams(method, Consts.KEY_TIME_OUT, req.mTimeout);
            }
            resp = ((HttpClient) this.connectionManager.getConnection(false)).execute(method);
            response = (FilesDelResp) parseDjangoFileInfoResp(FilesDelResp.class, resp);
            return response;
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new FilesDelResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
        } finally {
            DjangoUtils.releaseConnection(method, resp);
        }
    }

    public SetExtResp setExt(SetExtReq req) {
        SetExtResp response;
        HttpPost method = null;
        HttpResponse resp = null;
        try {
            if (LiteStringUtils.isBlank(req.getFileId())) {
                throw new DjangoClientException((String) "field[fileId] is null");
            } else if (req.getExt() == null || req.getExt().isEmpty()) {
                throw new DjangoClientException((String) "field[ext] is null");
            } else {
                String timestampStr = String.valueOf(System.currentTimeMillis());
                String acl = genAclString(req.getFileId(), timestampStr);
                String extJson = JSON.toJSONString(req.getExt());
                List params = new ArrayList();
                params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
                params.add(new BasicNameValuePair("timestamp", timestampStr));
                params.add(new BasicNameValuePair("acl", acl));
                params.add(new BasicNameValuePair("fileId", req.getFileId()));
                params.add(new BasicNameValuePair(ProcessInfo.ALIAS_EXT, extJson));
                List urlParams = new ArrayList();
                String traceId = getTraceId();
                if (LiteStringUtils.isNotBlank(traceId)) {
                    urlParams.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
                }
                method = createHttpPost(FileApiInfo.SET_EXT, urlParams);
                req.setHttpRequestBase(method);
                method.setEntity(new UrlEncodedFormEntity(params));
                if (req.mTimeout > 0) {
                    addIntParams(method, Consts.KEY_TIME_OUT, req.mTimeout);
                }
                resp = ((HttpClient) this.connectionManager.getConnection(false)).execute(method);
                response = (SetExtResp) parseDjangoFileInfoResp(SetExtResp.class, resp);
                return response;
            }
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new SetExtResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
        } finally {
            DjangoUtils.releaseConnection(method, resp);
        }
    }

    public FileOfflineUploadResp fileOfflineUpload(FileOfflineUploadReq req) {
        FileOfflineUploadResp response;
        HttpPost method = null;
        HttpResponse resp = null;
        if (req != null) {
            try {
                if (!LiteStringUtils.isBlank(req.downloadUrl)) {
                    List params = new ArrayList();
                    params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
                    params.add(new BasicNameValuePair("download_url", req.downloadUrl));
                    params.add(new BasicNameValuePair("synchronous", String.valueOf(req.synchoronous)));
                    if (req.size > 0) {
                        params.add(new BasicNameValuePair("size", String.valueOf(req.size)));
                    }
                    if (!TextUtils.isEmpty(req.md5)) {
                        params.add(new BasicNameValuePair("md5", req.md5));
                    }
                    if (!TextUtils.isEmpty(req.notifyUrl)) {
                        params.add(new BasicNameValuePair("notify_url", req.notifyUrl));
                    }
                    if (!TextUtils.isEmpty(req.type)) {
                        params.add(new BasicNameValuePair("type", req.type));
                    }
                    List urlParams = new ArrayList();
                    String traceId = getTraceId();
                    if (LiteStringUtils.isNotBlank(traceId)) {
                        urlParams.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
                    }
                    method = createHttpPost(FileApiInfo.UPLOAD_OFFLINE, urlParams);
                    req.setHttpRequestBase(method);
                    method.setEntity(new UrlEncodedFormEntity(params));
                    if (req.mTimeout > 0) {
                        addIntParams(method, Consts.KEY_TIME_OUT, req.mTimeout);
                    }
                    resp = ((HttpClient) this.connectionManager.getConnection(false)).execute(method);
                    response = (FileOfflineUploadResp) parseDjangoFileInfoResp(FileOfflineUploadResp.class, resp);
                    return response;
                }
            } catch (Exception e) {
                Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
                response = new FileOfflineUploadResp();
                if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                    response.setCode(DjangoConstant.DJANGO_TIMEOUT);
                } else {
                    response.setCode(DjangoConstant.DJANGO_400);
                }
                response.setMsg(e.getMessage());
            } finally {
                DjangoUtils.releaseConnection(method, resp);
            }
        }
        throw new DjangoClientException("Invalid args!!! req: " + req);
    }

    public FileUpResp uploadRapidRange(FileRapidUpReq fileRapidUpReq) {
        FileUpResp response;
        try {
            List params = new ArrayList();
            params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
            if (LiteStringUtils.isNotBlank(fileRapidUpReq.getMd5())) {
                params.add(new BasicNameValuePair("md5", fileRapidUpReq.getMd5()));
            }
            if (LiteStringUtils.isNotBlank(fileRapidUpReq.getGcid())) {
                params.add(new BasicNameValuePair("gcid", fileRapidUpReq.getGcid()));
            }
            if (LiteStringUtils.isNotBlank(fileRapidUpReq.getExt())) {
                params.add(new BasicNameValuePair(ProcessInfo.ALIAS_EXT, fileRapidUpReq.getExt()));
            }
            String traceId = getTraceId();
            if (LiteStringUtils.isNotBlank(traceId)) {
                params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
            }
            HttpGet method = createHttpGet(FileApiInfo.UPLOAD_CHECK_RAPID_RANGE, params, false);
            fileRapidUpReq.setHttpRequestBase(method);
            addParams(method, "uploadType", "2");
            if (fileRapidUpReq.mTimeout > 0) {
                addIntParams(method, Consts.KEY_TIME_OUT, fileRapidUpReq.mTimeout);
            }
            HttpResponse resp = a().execute(method);
            response = a(resp);
            if (response.isSuccess()) {
                response.setRapid(true);
            }
            if (!TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
            DjangoUtils.releaseConnection(method, resp);
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new FileUpResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
            if (!TextUtils.isEmpty(null)) {
                response.setTraceId(null);
            }
            DjangoUtils.releaseConnection(null, null);
        } catch (Throwable th) {
            th = th;
        }
        return response;
        if (response != null && !TextUtils.isEmpty(null)) {
            response.setTraceId(null);
        }
        DjangoUtils.releaseConnection(null, null);
        throw th;
    }

    public FileParallelUpResp uploadParallelRapidRange(FileRapidUpReq fileRapidUpReq) {
        FileParallelUpResp response;
        try {
            List params = new ArrayList();
            params.add(new BasicNameValuePair("token", this.tokenApi.getTokenString()));
            if (LiteStringUtils.isNotBlank(fileRapidUpReq.getMd5())) {
                params.add(new BasicNameValuePair("md5", fileRapidUpReq.getMd5()));
            }
            if (LiteStringUtils.isNotBlank(fileRapidUpReq.getGcid())) {
                params.add(new BasicNameValuePair("gcid", fileRapidUpReq.getGcid()));
            }
            if (LiteStringUtils.isNotBlank(fileRapidUpReq.getExt())) {
                params.add(new BasicNameValuePair(ProcessInfo.ALIAS_EXT, fileRapidUpReq.getExt()));
            }
            String traceId = getTraceId();
            if (LiteStringUtils.isNotBlank(traceId)) {
                params.add(new BasicNameValuePair(DjangoConstant.TRACE_ID, traceId));
            }
            HttpGet method = createHttpGet(FileApiInfo.UPLOAD_CHECK_RAPID_PARALLEL_RANGE, params, false, true);
            fileRapidUpReq.setHttpRequestBase(method);
            addParams(method, "uploadType", "2");
            if (fileRapidUpReq.mTimeout > 0) {
                addIntParams(method, Consts.KEY_TIME_OUT, fileRapidUpReq.mTimeout);
            }
            HttpResponse resp = a().execute(method);
            response = b(resp);
            if (response.isSuccess()) {
                response.setRapid(true);
            }
            if (!TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
            DjangoUtils.releaseConnection(method, resp);
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, e.getMessage(), new Object[0]);
            response = new FileParallelUpResp();
            if (DiskExpUtils.parseException(e) == DjangoConstant.DJANGO_TIMEOUT) {
                response.setCode(DjangoConstant.DJANGO_TIMEOUT);
            } else {
                response.setCode(DjangoConstant.DJANGO_400);
            }
            response.setMsg(e.getMessage());
            if (!TextUtils.isEmpty(null)) {
                response.setTraceId(null);
            }
            DjangoUtils.releaseConnection(null, null);
        } catch (Throwable th) {
            th = th;
        }
        return response;
        if (response != null && !TextUtils.isEmpty(null)) {
            response.setTraceId(null);
        }
        DjangoUtils.releaseConnection(null, null);
        throw th;
    }

    public FileParallelUpResp uploadParallelRange(FileUpReq fileUpReq) {
        HttpRequestBase method = null;
        HttpResponse resp = null;
        FileParallelUpResp response = null;
        if (fileUpReq == null || fileUpReq.getFile() == null) {
            throw new DjangoClientException((String) "Field[file] is null!");
        }
        try {
            HttpEntity entity = a(fileUpReq, new SliceProgressFileBody(fileUpReq.getFile(), fileUpReq.getFileName(), fileUpReq.getStartPos(), fileUpReq.getEndPos(), fileUpReq.getTransferedListener()));
            String traceId = getTraceId();
            method = a(FileApiInfo.UPLOAD_FILE_PARALLEL_RANGE, fileUpReq, entity, traceId);
            fileUpReq.setHttpRequestBase(method);
            addParams(method, "uploadType", "-1");
            resp = a().execute(method);
            response = b(resp);
            if (!TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, "uploadParallelRange req: " + fileUpReq, new Object[0]);
        } finally {
            DjangoUtils.releaseConnection(method, resp);
        }
        return response;
    }

    public FileParallelUpResp uploadParallelRange(InputStreamUpReq upReq) {
        HttpRequestBase method = null;
        HttpResponse resp = null;
        FileParallelUpResp response = null;
        if (upReq == null || upReq.getInputStream() == null) {
            throw new DjangoClientException((String) "Field[file] is null!");
        }
        try {
            HttpEntity entity = a(upReq, new SliceProgressInputStreamBody(upReq.getInputStream(), upReq.getFileName(), upReq.getStartPos(), upReq.getEndPos(), upReq.getTransferedListener()));
            String traceId = getTraceId();
            method = a(FileApiInfo.UPLOAD_FILE_PARALLEL_RANGE, upReq, entity, traceId);
            upReq.setHttpRequestBase(method);
            addParams(method, "uploadType", "-1");
            resp = a().execute(method);
            response = b(resp);
            if (!TextUtils.isEmpty(traceId)) {
                response.setTraceId(traceId);
            }
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, "uploadParallelRange req: " + upReq, new Object[0]);
        } finally {
            DjangoUtils.releaseConnection(method, resp);
        }
        return response;
    }

    private static FileParallelUpResp b(HttpResponse response) {
        FileParallelUpResp resp;
        if (response == null || response.getStatusLine().getStatusCode() != 200) {
            resp = new FileParallelUpResp();
            resp.setCode(response == null ? DjangoConstant.DJANGO_400 : response.getStatusLine().getStatusCode());
            resp.setMsg("Http invoker error: " + resp.getCode());
        } else {
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (DjangoClient.DEBUG) {
                Logger.D(DjangoClient.LOG_TAG, "parseParallelRangeResponse() :" + content, new Object[0]);
            }
            resp = new FileParallelUpResp();
            JSONObject js = new JSONObject(content);
            Logger.P(DjangoClient.LOG_TAG, "parseParallelRangeResponse parseJson ok!", new Object[0]);
            int code = js.getInt("code");
            Logger.P(DjangoClient.LOG_TAG, "parseParallelRangeResponse code = " + code, new Object[0]);
            if (code == DjangoConstant.DJANGO_OK) {
                resp.setFileInfo((DjangoFileInfoResp) JSON.parseObject(js.getString("data"), DjangoFileInfoResp.class));
                resp.setRapid(true);
                resp.setCode(DjangoConstant.DJANGO_OK);
            } else if (code == 206) {
                resp.setCode(code);
                resp.setRapid(false);
                String data = js.getString("data");
                Logger.P(DjangoClient.LOG_TAG, "parseParallelRangeResponse data = " + data, new Object[0]);
                resp.parseLeftRanges(data);
            } else {
                resp.setCode(code);
                resp.setRapid(false);
                resp.setHasLeftRange(false);
            }
        }
        Logger.D(DjangoClient.LOG_TAG, "uploadRsp: " + resp, new Object[0]);
        return resp;
    }
}
