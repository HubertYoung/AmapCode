package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ChunksDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.FileChunksInfoReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.GetChunksMetaReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.ChunksDownResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.FileChunksInfoResp;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp.GetChunksMetaResp;

public interface ChunkApi {
    ChunksDownResp downloadChunks(ChunksDownReq chunksDownReq);

    GetChunksMetaResp getChunksMeta(GetChunksMetaReq getChunksMetaReq);

    FileChunksInfoResp getFileChunksInfo(FileChunksInfoReq fileChunksInfoReq);
}
