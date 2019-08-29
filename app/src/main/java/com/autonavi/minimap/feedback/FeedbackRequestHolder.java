package com.autonavi.minimap.feedback;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.miniapp.plugin.carowner.CarOwnerHelper;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.feedback.param.BindRequest;
import com.autonavi.minimap.feedback.param.ReportBatchRequest;
import com.autonavi.minimap.feedback.param.ReportRequest;
import com.autonavi.minimap.feedback.param.ReportSatisfactionRequest;
import com.autonavi.minimap.feedback.param.ReportUrgeRequest;
import com.autonavi.minimap.feedback.param.TwiceCommentRuleRequest;
import com.autonavi.minimap.feedback.param.TwiceReportRequest;
import com.autonavi.minimap.feedback.param.UserPayReportRequest;
import com.autonavi.minimap.feedback.param.UserpayListRequest;
import com.autonavi.minimap.feedback.param.UserpayTypeRequest;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import com.taobao.accs.common.Constants;
import com.tencent.open.SocialConstants;
import java.io.File;
import java.util.Map.Entry;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class FeedbackRequestHolder {
    private static volatile FeedbackRequestHolder instance;

    private FeedbackRequestHolder() {
    }

    public static FeedbackRequestHolder getInstance() {
        if (instance == null) {
            synchronized (FeedbackRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new FeedbackRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendBind(BindRequest bindRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendBind(bindRequest, new dkn(), aosResponseCallback);
    }

    public void sendReport(ReportRequest reportRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendReport(reportRequest, new dkn(), aosResponseCallback);
    }

    public void sendReportBatch(ReportBatchRequest reportBatchRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendReportBatch(reportBatchRequest, new dkn(), aosResponseCallback);
    }

    public void sendReportSatisfaction(ReportSatisfactionRequest reportSatisfactionRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendReportSatisfaction(reportSatisfactionRequest, new dkn(), aosResponseCallback);
    }

    public void sendReportUrge(ReportUrgeRequest reportUrgeRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendReportUrge(reportUrgeRequest, new dkn(), aosResponseCallback);
    }

    public void sendTwiceReport(TwiceReportRequest twiceReportRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTwiceReport(twiceReportRequest, new dkn(), aosResponseCallback);
    }

    public void sendUserpayType(UserpayTypeRequest userpayTypeRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUserpayType(userpayTypeRequest, new dkn(), aosResponseCallback);
    }

    public void sendUserPayList(UserpayListRequest userpayListRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUserPayList(userpayListRequest, new dkn(), aosResponseCallback);
    }

    public void sendUserPayReport(UserPayReportRequest userPayReportRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendUserPayReport(userPayReportRequest, new dkn(), aosResponseCallback);
    }

    public void sendTwiceCommentRule(TwiceCommentRuleRequest twiceCommentRuleRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendTwiceCommentRule(twiceCommentRuleRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendBind(BindRequest bindRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            bindRequest.addHeaders(dkn.d);
            bindRequest.setTimeout(dkn.b);
            bindRequest.setRetryTimes(dkn.c);
        }
        bindRequest.setUrl(BindRequest.a);
        bindRequest.addSignParam("channel");
        bindRequest.addSignParam("record_id");
        bindRequest.addReqParam("record_id", bindRequest.b);
        bindRequest.addReqParam("tid", bindRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) bindRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) bindRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendReport(ReportRequest reportRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            reportRequest.addHeaders(dkn.d);
            reportRequest.setTimeout(dkn.b);
            reportRequest.setRetryTimes(dkn.c);
        }
        reportRequest.setUrl(ReportRequest.a);
        reportRequest.addSignParam("channel");
        reportRequest.addSignParam("type");
        reportRequest.addSignParam("description");
        reportRequest.addReqParam(LocationParams.PARA_COMMON_DIP, reportRequest.b);
        reportRequest.addReqParam("tid", reportRequest.c);
        reportRequest.addReqParam("type", reportRequest.d);
        reportRequest.addReqParam("title", reportRequest.e);
        reportRequest.addReqParam(H5ContactPlugin.CONTACT, reportRequest.f);
        reportRequest.addReqParam("description", reportRequest.g);
        reportRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, reportRequest.h);
        reportRequest.addReqParam("category", reportRequest.i);
        reportRequest.addReqParam("name", reportRequest.j);
        reportRequest.addReqParam("address", reportRequest.k);
        reportRequest.addReqParam("longitude", reportRequest.l);
        reportRequest.addReqParam("latitude", reportRequest.m);
        reportRequest.addReqParam("points", reportRequest.n);
        reportRequest.addReqParam("tel", reportRequest.o);
        reportRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, reportRequest.p);
        reportRequest.addReqParam("startpoint", reportRequest.q);
        reportRequest.addReqParam("endpoint", reportRequest.r);
        reportRequest.addReqParam(SocialConstants.PARAM_AVATAR_URI, reportRequest.s);
        reportRequest.addReqParam("offlinemap_log", reportRequest.t);
        reportRequest.addReqParam("attachment", reportRequest.u);
        reportRequest.addReqParam("picture_info", reportRequest.v);
        reportRequest.addReqParam("errortype", Integer.toString(reportRequest.w));
        reportRequest.addReqParam(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, reportRequest.x);
        reportRequest.addReqParam("sourcepage", reportRequest.y);
        reportRequest.addReqParam("promotion", Integer.toString(reportRequest.z));
        reportRequest.addReqParam(Constants.KEY_MODE, reportRequest.A);
        reportRequest.addReqParam("mapver", reportRequest.B);
        reportRequest.addReqParam("Ad1", reportRequest.C);
        reportRequest.addReqParam("Ad2", reportRequest.D);
        reportRequest.addReqParam("errorcode", reportRequest.E);
        reportRequest.addReqParam("token", reportRequest.F);
        reportRequest.addReqParam(ConfigerHelper.CONF_DIB_KEY, reportRequest.G);
        reportRequest.addReqParam(LocationParams.PARA_COMMON_DIBV, reportRequest.H);
        reportRequest.addReqParam("snowman_id", reportRequest.I);
        reportRequest.addReqParam("snowman_action", reportRequest.J);
        reportRequest.addReqParam("snowman_engine", reportRequest.K);
        reportRequest.addReqParam("content_options", Integer.toString(reportRequest.L));
        reportRequest.addReqParam("passing_points", reportRequest.M);
        reportRequest.addReqParam("plate", reportRequest.N);
        reportRequest.addReqParam("error_id", reportRequest.O);
        reportRequest.addReqParam("lineid", reportRequest.P);
        reportRequest.addReqParam("bus_lineids", reportRequest.Q);
        reportRequest.addReqParam("bus_poiids", reportRequest.R);
        reportRequest.addReqParam("scaleaccuracy", reportRequest.S);
        reportRequest.addReqParam("unique_id", reportRequest.T);
        reportRequest.addReqParam("extra_info", reportRequest.U);
        if (dkn != null) {
            in.a().a((AosRequest) reportRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) reportRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendReportBatch(ReportBatchRequest reportBatchRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            reportBatchRequest.addHeaders(dkn.d);
            reportBatchRequest.setTimeout(dkn.b);
            reportBatchRequest.setRetryTimes(dkn.c);
        }
        reportBatchRequest.setUrl(ReportBatchRequest.f);
        reportBatchRequest.addSignParam("channel");
        reportBatchRequest.addSignParam("tid");
        reportBatchRequest.addReqParam("tid", reportBatchRequest.g);
        reportBatchRequest.addReqParam("data", reportBatchRequest.h);
        reportBatchRequest.addReqParam("sourcepage", reportBatchRequest.i);
        reportBatchRequest.addReqParam("category", reportBatchRequest.j);
        reportBatchRequest.addReqParam("errortype", reportBatchRequest.k);
        reportBatchRequest.addReqParam("content_options", reportBatchRequest.l);
        reportBatchRequest.addReqParam("startpoint", reportBatchRequest.m);
        reportBatchRequest.addReqParam(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, reportBatchRequest.n);
        reportBatchRequest.addReqParam("endpoint", reportBatchRequest.o);
        reportBatchRequest.addReqParam(H5ContactPlugin.CONTACT, reportBatchRequest.r);
        reportBatchRequest.addReqParam("plate", reportBatchRequest.p);
        reportBatchRequest.addReqParam("extra_info", reportBatchRequest.q);
        reportBatchRequest.addReqParam("longitude", reportBatchRequest.s);
        reportBatchRequest.addReqParam("latitude", reportBatchRequest.t);
        reportBatchRequest.addReqParam(AutoJsonUtils.JSON_ADCODE, reportBatchRequest.u);
        reportBatchRequest.addReqParam("userid", reportBatchRequest.v);
        reportBatchRequest.addReqParam(Constants.KEY_MODE, reportBatchRequest.w);
        reportBatchRequest.addReqParam("mapver", reportBatchRequest.x);
        reportBatchRequest.addReqParam("client_network_class", reportBatchRequest.y);
        if (dkn != null) {
            in.a().a((AosRequest) reportBatchRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) reportBatchRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendReportSatisfaction(ReportSatisfactionRequest reportSatisfactionRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            reportSatisfactionRequest.addHeaders(dkn.d);
            reportSatisfactionRequest.setTimeout(dkn.b);
            reportSatisfactionRequest.setRetryTimes(dkn.c);
        }
        reportSatisfactionRequest.setUrl(ReportSatisfactionRequest.a);
        reportSatisfactionRequest.addSignParam("channel");
        reportSatisfactionRequest.addSignParam("record_id");
        reportSatisfactionRequest.addReqParam("record_id", reportSatisfactionRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) reportSatisfactionRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) reportSatisfactionRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendReportUrge(ReportUrgeRequest reportUrgeRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            reportUrgeRequest.addHeaders(dkn.d);
            reportUrgeRequest.setTimeout(dkn.b);
            reportUrgeRequest.setRetryTimes(dkn.c);
        }
        reportUrgeRequest.setUrl(ReportUrgeRequest.a);
        reportUrgeRequest.addSignParam("channel");
        reportUrgeRequest.addSignParam("record_id");
        reportUrgeRequest.addReqParam("record_id", reportUrgeRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) reportUrgeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) reportUrgeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTwiceReport(TwiceReportRequest twiceReportRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            twiceReportRequest.addHeaders(dkn.d);
            twiceReportRequest.setTimeout(dkn.b);
            twiceReportRequest.setRetryTimes(dkn.c);
        }
        twiceReportRequest.setUrl(TwiceReportRequest.f);
        twiceReportRequest.addSignParam("channel");
        twiceReportRequest.addSignParam("record_id");
        twiceReportRequest.addReqParam("record_id", twiceReportRequest.g);
        twiceReportRequest.addReqParam("description", twiceReportRequest.h);
        twiceReportRequest.addReqParam(H5ContactPlugin.CONTACT, twiceReportRequest.i);
        twiceReportRequest.addReqParam(SocialConstants.PARAM_AVATAR_URI, twiceReportRequest.j);
        for (Entry next : twiceReportRequest.k.entrySet()) {
            twiceReportRequest.a((String) next.getKey(), (File) next.getValue());
        }
        if (dkn != null) {
            in.a().a((AosRequest) twiceReportRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) twiceReportRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendUserpayType(UserpayTypeRequest userpayTypeRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            userpayTypeRequest.addHeaders(dkn.d);
            userpayTypeRequest.setTimeout(dkn.b);
            userpayTypeRequest.setRetryTimes(dkn.c);
        }
        userpayTypeRequest.setUrl(UserpayTypeRequest.a);
        userpayTypeRequest.addSignParam("channel");
        userpayTypeRequest.addSignParam("tid");
        userpayTypeRequest.addSignParam("promotion");
        userpayTypeRequest.addReqParam("tid", userpayTypeRequest.b);
        userpayTypeRequest.addReqParam("promotion", userpayTypeRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) userpayTypeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) userpayTypeRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendUserPayList(UserpayListRequest userpayListRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            userpayListRequest.addHeaders(dkn.d);
            userpayListRequest.setTimeout(dkn.b);
            userpayListRequest.setRetryTimes(dkn.c);
        }
        userpayListRequest.setUrl(UserpayListRequest.a);
        userpayListRequest.addSignParam("tid");
        userpayListRequest.addSignParam("promotion");
        userpayListRequest.addReqParam("page_size", userpayListRequest.c);
        userpayListRequest.addReqParam("page_num", userpayListRequest.d);
        userpayListRequest.addReqParam("promotion", userpayListRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) userpayListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) userpayListRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendUserPayReport(UserPayReportRequest userPayReportRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            userPayReportRequest.addHeaders(dkn.d);
            userPayReportRequest.setTimeout(dkn.b);
            userPayReportRequest.setRetryTimes(dkn.c);
        }
        userPayReportRequest.setUrl(UserPayReportRequest.a);
        userPayReportRequest.addSignParam("promotion");
        userPayReportRequest.addSignParam("type");
        userPayReportRequest.addSignParam("tid");
        userPayReportRequest.addReqParam("tid", userPayReportRequest.b);
        userPayReportRequest.addReqParam("points", userPayReportRequest.c);
        userPayReportRequest.addReqParam("st_point", userPayReportRequest.d);
        userPayReportRequest.addReqParam("end_point", userPayReportRequest.e);
        userPayReportRequest.addReqParam("start_name", userPayReportRequest.f);
        userPayReportRequest.addReqParam("end_name", userPayReportRequest.g);
        userPayReportRequest.addReqParam("desc", userPayReportRequest.h);
        userPayReportRequest.addReqParam("car_lic", userPayReportRequest.i);
        userPayReportRequest.addReqParam("engine_no", userPayReportRequest.j);
        userPayReportRequest.addReqParam(CarOwnerHelper.OPTIONAL_ITEM_VIN, userPayReportRequest.k);
        userPayReportRequest.addReqParam(H5ContactPlugin.CONTACT, userPayReportRequest.l);
        userPayReportRequest.addReqParam("type", userPayReportRequest.m);
        userPayReportRequest.addReqParam("distance", userPayReportRequest.n);
        userPayReportRequest.addReqParam("cost_time", userPayReportRequest.o);
        userPayReportRequest.addReqParam("speed", userPayReportRequest.p);
        userPayReportRequest.addReqParam("navi_timestamp", userPayReportRequest.q);
        userPayReportRequest.addReqParam("promotion", userPayReportRequest.r);
        if (dkn != null) {
            in.a().a((AosRequest) userPayReportRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) userPayReportRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }

    public void sendTwiceCommentRule(TwiceCommentRuleRequest twiceCommentRuleRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            twiceCommentRuleRequest.addHeaders(dkn.d);
            twiceCommentRuleRequest.setTimeout(dkn.b);
            twiceCommentRuleRequest.setRetryTimes(dkn.c);
        }
        twiceCommentRuleRequest.setUrl(TwiceCommentRuleRequest.a);
        twiceCommentRuleRequest.addSignParam("channel");
        twiceCommentRuleRequest.addSignParam("record_id");
        twiceCommentRuleRequest.addSignParam("type");
        twiceCommentRuleRequest.addReqParam("record_id", twiceCommentRuleRequest.b);
        twiceCommentRuleRequest.addReqParam("type", twiceCommentRuleRequest.c);
        if (dkn != null) {
            in.a().a((AosRequest) twiceCommentRuleRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) twiceCommentRuleRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
