package com.autonavi.minimap.comment;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.comment.param.BusBsCreateRequest;
import com.autonavi.minimap.comment.param.CommentCreateRequest;
import com.autonavi.minimap.comment.param.CommentDeleteRequest;
import com.autonavi.minimap.comment.param.ImgUploadRequest;
import com.autonavi.minimap.comment.param.WalkCreateRequest;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.tencent.open.SocialConstants;
import org.json.JSONArray;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class CommentRequestHolder {
    private static volatile CommentRequestHolder instance;

    private CommentRequestHolder() {
    }

    public static CommentRequestHolder getInstance() {
        if (instance == null) {
            synchronized (CommentRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new CommentRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendImgUpload(ImgUploadRequest imgUploadRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendImgUpload(imgUploadRequest, new dkn(), aosResponseCallback);
    }

    public void sendBusBsCreate(BusBsCreateRequest busBsCreateRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBusBsCreate(busBsCreateRequest, new dkn(), aosResponseCallback);
    }

    public void sendWalkCreate(WalkCreateRequest walkCreateRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendWalkCreate(walkCreateRequest, new dkn(), aosResponseCallback);
    }

    public void sendCommentDelete(CommentDeleteRequest commentDeleteRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendCommentDelete(commentDeleteRequest, new dkn(), aosResponseCallback);
    }

    public void sendCommentCreate(CommentCreateRequest commentCreateRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendCommentCreate(commentCreateRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendImgUpload(ImgUploadRequest imgUploadRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            imgUploadRequest.addHeaders(dkn.d);
            imgUploadRequest.setTimeout(dkn.b);
            imgUploadRequest.setRetryTimes(dkn.c);
        }
        imgUploadRequest.setUrl(ImgUploadRequest.f);
        imgUploadRequest.addSignParam("channel");
        imgUploadRequest.addSignParam("poi_id");
        imgUploadRequest.addSignParam("tid");
        imgUploadRequest.addReqParam("poi_id", imgUploadRequest.g);
        imgUploadRequest.a((String) SocialConstants.PARAM_AVATAR_URI, imgUploadRequest.h);
        imgUploadRequest.addReqParam("checksum", imgUploadRequest.i);
        imgUploadRequest.addReqParam("poi_x", imgUploadRequest.j);
        imgUploadRequest.addReqParam("poi_y", imgUploadRequest.k);
        if (dkn != null) {
            in.a().a((AosRequest) imgUploadRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) imgUploadRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendBusBsCreate(BusBsCreateRequest busBsCreateRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            busBsCreateRequest.addHeaders(dkn.d);
            busBsCreateRequest.setTimeout(dkn.b);
            busBsCreateRequest.setRetryTimes(dkn.c);
        }
        busBsCreateRequest.setUrl(BusBsCreateRequest.a);
        busBsCreateRequest.addSignParam("channel");
        busBsCreateRequest.addSignParam("tid");
        busBsCreateRequest.addReqParam("tid", busBsCreateRequest.b);
        busBsCreateRequest.addReqParam("channel", busBsCreateRequest.c);
        busBsCreateRequest.addReqParam("data", busBsCreateRequest.d);
        if (dkn != null) {
            in.a().a((AosRequest) busBsCreateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) busBsCreateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendWalkCreate(WalkCreateRequest walkCreateRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            walkCreateRequest.addHeaders(dkn.d);
            walkCreateRequest.setTimeout(dkn.b);
            walkCreateRequest.setRetryTimes(dkn.c);
        }
        walkCreateRequest.setUrl(WalkCreateRequest.a);
        walkCreateRequest.addSignParam("channel");
        walkCreateRequest.addSignParam("tid");
        walkCreateRequest.addReqParam("tid", walkCreateRequest.b);
        walkCreateRequest.addReqParam("channel", walkCreateRequest.c);
        walkCreateRequest.addReqParam("data", walkCreateRequest.d);
        if (dkn != null) {
            in.a().a((AosRequest) walkCreateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) walkCreateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendCommentDelete(CommentDeleteRequest commentDeleteRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            commentDeleteRequest.addHeaders(dkn.d);
            commentDeleteRequest.setTimeout(dkn.b);
            commentDeleteRequest.setRetryTimes(dkn.c);
        }
        commentDeleteRequest.setUrl(CommentDeleteRequest.a);
        commentDeleteRequest.addSignParam("channel");
        commentDeleteRequest.addSignParam("comment_id");
        commentDeleteRequest.addSignParam("tid");
        commentDeleteRequest.addReqParam("tid", commentDeleteRequest.b);
        commentDeleteRequest.addReqParam("comment_id", commentDeleteRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) commentDeleteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) commentDeleteRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendCommentCreate(CommentCreateRequest commentCreateRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            commentCreateRequest.addHeaders(dkn.d);
            commentCreateRequest.setTimeout(dkn.b);
            commentCreateRequest.setRetryTimes(dkn.c);
        }
        commentCreateRequest.setUrl(CommentCreateRequest.f);
        commentCreateRequest.addSignParam("channel");
        commentCreateRequest.addSignParam("poi_id");
        commentCreateRequest.addSignParam("star");
        commentCreateRequest.addSignParam("tid");
        commentCreateRequest.addReqParam("tid", commentCreateRequest.g);
        commentCreateRequest.addReqParam("poi_id", commentCreateRequest.h);
        commentCreateRequest.addReqParam("star", commentCreateRequest.i);
        commentCreateRequest.addReqParam("content", commentCreateRequest.j);
        commentCreateRequest.a((String) SocialConstants.PARAM_AVATAR_URI, commentCreateRequest.k);
        commentCreateRequest.addReqParam("push_token", commentCreateRequest.l);
        commentCreateRequest.addReqParam("checksum", new JSONArray(commentCreateRequest.m).toString());
        if (dkn != null) {
            in.a().a((AosRequest) commentCreateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) commentCreateRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
