package defpackage;

import com.alibaba.fastjson.JSON;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.MtopResponse;

/* renamed from: ffx reason: default package */
/* compiled from: MtopConvert */
public final class ffx {
    @Deprecated
    public static BaseOutDo a(byte[] bArr, Class<?> cls) {
        BaseOutDo baseOutDo;
        if (cls == null || bArr == null || bArr.length == 0) {
            TBSdkLog.d("mtopsdk.MtopConvert", "[jsonToOutputDO]outClass is null or jsonData is blank");
            return null;
        }
        try {
            baseOutDo = (BaseOutDo) JSON.parseObject(new String(bArr, "UTF-8"), cls);
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("[jsonToOutputDO]invoke JSON.parseObject error ---Class=");
            sb.append(cls.getName());
            TBSdkLog.b((String) "mtopsdk.MtopConvert", sb.toString(), th);
            baseOutDo = null;
        }
        return baseOutDo;
    }

    @Deprecated
    public static BaseOutDo a(MtopResponse mtopResponse, Class<?> cls) {
        if (cls != null && mtopResponse != null) {
            return a(mtopResponse.getBytedata(), cls);
        }
        TBSdkLog.d("mtopsdk.MtopConvert", "outClass is null or response is null");
        return null;
    }

    public static <T> T b(byte[] bArr, Class<T> cls) {
        T t;
        if (bArr == null || bArr.length == 0) {
            TBSdkLog.d("mtopsdk.MtopConvert", "[jsonToOutputDO]outputClass is null or jsonData is blank");
            return null;
        }
        try {
            t = JSON.parseObject(new String(bArr, "UTF-8"), cls);
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("[jsonToOutputDO]invoke JSON.parseObject error ---Class=");
            sb.append(cls.getName());
            TBSdkLog.b((String) "mtopsdk.MtopConvert", sb.toString(), th);
            t = null;
        }
        return t;
    }
}
