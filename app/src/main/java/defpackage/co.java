package defpackage;

import android.util.SparseArray;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileRsp;
import com.alipay.mobile.mrtc.api.constants.APCallCode;

/* renamed from: co reason: default package */
/* compiled from: ErrorConstant */
public final class co {
    private static SparseArray<String> a;

    static {
        SparseArray<String> sparseArray = new SparseArray<>();
        a = sparseArray;
        sparseArray.put(200, "请求成功");
        a.put(-100, "未知错误");
        a.put(-101, "发生异常");
        a.put(-102, "非法参数");
        a.put(-103, "远程调用失败");
        a.put(-105, "ACCS自定义帧回调为空");
        a.put(APCallCode.CALL_ERROR_TIMEOUT, "获取Process失败");
        a.put(-200, "无网络");
        a.put(-203, "无策略");
        a.put(-202, "请求超时");
        a.put(-204, "请求被取消");
        a.put(-205, "请求后台被禁止");
        a.put(-206, "请求收到的数据长度与Content-Length不匹配");
        a.put(-300, "Tnet层抛出异常");
        a.put(-301, "Session不可用");
        a.put(-302, "鉴权异常");
        a.put(-303, "自定义帧数据过大");
        a.put(-304, "Tnet请求失败");
        a.put(-400, "连接超时");
        a.put(-401, "Socket超时");
        a.put(-402, "SSL失败");
        a.put(APFileRsp.CODE_ERR_AUTH_FAIL, "域名未认证");
        a.put(-404, "IO异常");
        a.put(-405, "域名不能解析");
        a.put(-406, "连接异常");
    }

    public static String a(int i) {
        return cz.a(a.get(i));
    }

    public static String a(int i, String str) {
        return cz.a(a(i), ":", str);
    }
}
