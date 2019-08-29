package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api;

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
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileOfflineUploadResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileParallelUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileUpResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FilesDelResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FilesDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.GetFilesMetaResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.SetExtResp;

public interface FileApi {
    public static final long DEFAULT_TRUNK_SIZE = 4194304;

    FilesDelResp deleteBatch(FilesDelReq filesDelReq);

    FilesDownResp downloadBatch(FilesDownReq filesDownReq);

    FileOfflineUploadResp fileOfflineUpload(FileOfflineUploadReq fileOfflineUploadReq);

    GetFilesMetaResp getFilesMeta(GetFilesMetaReq getFilesMetaReq);

    SetExtResp setExt(SetExtReq setExtReq);

    ChunkUpTxnCommitResp uploadChunkCommit(ChunkUpTxnCommitReq chunkUpTxnCommitReq);

    ChunkUpTxnOpenResp uploadChunkOpen(ChunkUpTxnOpenReq chunkUpTxnOpenReq);

    ChunkUpTxnProcessResp uploadChunkProcess(ChunkUpTxnProcessReq chunkUpTxnProcessReq);

    ChunkUpTxnProcessResp uploadChunkProcessRapid(ChunkUpTxnProcessReq chunkUpTxnProcessReq);

    FileUpResp uploadDirect(FileUpReq fileUpReq);

    FileUpResp uploadDirect(InputStreamUpReq inputStreamUpReq);

    FileUpResp uploadDirectRapid(FileRapidUpReq fileRapidUpReq);

    FileParallelUpResp uploadParallelRange(FileUpReq fileUpReq);

    FileParallelUpResp uploadParallelRange(InputStreamUpReq inputStreamUpReq);

    FileParallelUpResp uploadParallelRapidRange(FileRapidUpReq fileRapidUpReq);

    FileUpResp uploadRange(FileUpReq fileUpReq);

    FileUpResp uploadRange(InputStreamUpReq inputStreamUpReq);

    FileUpResp uploadRapidRange(FileRapidUpReq fileRapidUpReq);
}
