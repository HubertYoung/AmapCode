package defpackage;

import mtopsdk.mtop.domain.MtopResponse;

/* renamed from: fdk reason: default package */
/* compiled from: BusinessErrorAfterFilter */
public final class fdk implements fdg {
    public final String a() {
        return "mtopsdk.BusinessErrorAfterFilter";
    }

    public final String a(fdf fdf) {
        MtopResponse mtopResponse = fdf.c;
        if (304 == mtopResponse.getResponseCode() && fdf.j != null) {
            MtopResponse mtopResponse2 = fdf.j.cacheResponse;
            if (mtopResponse2 != null) {
                fdf.c = mtopResponse2;
                fed.a(fdf);
                return "STOP";
            }
        }
        if (mtopResponse.getBytedata() == null) {
            mtopResponse.setRetCode("ANDROID_SYS_JSONDATA_BLANK");
            mtopResponse.setRetMsg("返回JSONDATA为空");
            fed.a(fdf);
            return "STOP";
        }
        fed.a(mtopResponse);
        return "CONTINUE";
    }
}
