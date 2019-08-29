package com.alipay.mobile.common.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.aspect.AspectJProcessorManager;
import com.alipay.mobile.aspect.CommonAspect;
import com.alipay.mobile.aspect.processor.IAspectJProcessor;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.utils.LogCatUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.reflect.Factory;

public class ReadSettingServerUrl {
    private static ReadSettingServerUrl a;
    private static String b = "https://mclient.alipay.com/gateway.do";
    private static final /* synthetic */ StaticPart e = null;
    private String c = "https://mobilegw.alipay.com/mgw.htm";
    private String d = "http://amdc.alipay.com/query";

    private static /* synthetic */ void a() {
        Factory factory = new Factory("ReadSettingServerUrl.java", ReadSettingServerUrl.class);
        e = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "11", (String) "query", (String) "android.content.ContentResolver", (String) "android.net.Uri:[Ljava.lang.String;:java.lang.String:[Ljava.lang.String;:java.lang.String", (String) "uri:projection:selection:selectionArgs:sortOrder", (String) "", (String) "android.database.Cursor"), 100);
    }

    public ReadSettingServerUrl() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    static {
        a();
    }

    private static final /* synthetic */ Object a(ContentResolver target, Uri uri, JoinPoint thisJoinPoint) {
        Cursor result;
        IAspectJProcessor processor = AspectJProcessorManager.get().obtainProcessor(thisJoinPoint);
        if (processor == null) {
            return target.query(uri, null, null, null, null);
        }
        if ((processor.getFlags() & 4) > 0) {
            try {
                return processor.whenIntercepted(thisJoinPoint, null);
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().error((String) "CommonAspect", tr);
                return target.query(uri, null, null, null, null);
            }
        } else {
            try {
                processor.beforeMethod(thisJoinPoint, null);
            } catch (Throwable tr2) {
                LoggerFactory.getTraceLogger().error((String) "CommonAspect", tr2);
            }
            if ((processor.getFlags() & 1) > 0) {
                try {
                    result = target.query(uri, null, null, null, null);
                } catch (Throwable tr3) {
                    return processor.whenThrown(thisJoinPoint, tr3, null);
                }
            } else {
                result = target.query(uri, null, null, null, null);
            }
            try {
                if ((processor.getFlags() & 2) > 0) {
                    return processor.afterMethodWithReturn(thisJoinPoint, result, null);
                }
                processor.afterMethodWithReturn(thisJoinPoint, result, null);
                return result;
            } catch (Throwable tr4) {
                LoggerFactory.getTraceLogger().error((String) "CommonAspect", tr4);
                return result;
            }
        }
    }

    public static synchronized ReadSettingServerUrl getInstance() {
        ReadSettingServerUrl readSettingServerUrl;
        synchronized (ReadSettingServerUrl.class) {
            try {
                if (a == null) {
                    a = new ReadSettingServerUrl();
                }
                readSettingServerUrl = a;
            }
        }
        return readSettingServerUrl;
    }

    public String getmUrl() {
        return this.c;
    }

    public void setmUrl(String mUrl) {
        this.c = mUrl;
    }

    public static boolean isDebug(Context context) {
        try {
            if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 16384).flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "isDebug exception.  " + e2.toString());
            return false;
        }
    }

    public final String getGWFURL(Context context) {
        try {
            String mobilegwUrl = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("mobilegw.url");
            if (!TextUtils.isEmpty(mobilegwUrl)) {
                return mobilegwUrl;
            }
        } catch (Exception e2) {
            LogCatUtil.warn((String) "getGWFURL", (Throwable) e2);
        }
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/GWFServerUrl", this.c);
        }
        return this.c;
    }

    public final String getHttpdnsServerUrl(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/httpdns_url", this.d);
        }
        return this.d;
    }

    public static String getValue(Context context, String uri, String defaultVal) {
        LogCatUtil.printInfo("ReadSettingServerUrl", "getValue start.");
        ContentResolver contentResolver = context.getContentResolver();
        Uri parse = Uri.parse(uri);
        JoinPoint makeJP = Factory.makeJP(e, (Object) null, (Object) contentResolver, new Object[]{parse, null, null, null, null});
        CommonAspect.aspectOf();
        Cursor cursor = (Cursor) a(contentResolver, parse, makeJP);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            return defaultVal;
        }
        cursor.moveToFirst();
        String ret = cursor.getString(0);
        cursor.close();
        LogCatUtil.printInfo("ReadSettingServerUrl", "getValue.  cursor exist.  uri=[" + uri + "]  ret=[" + ret + "]");
        return ret;
    }

    public static final String getSafePayServerUrl(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/SafePayServerUrl", b);
        }
        return "https://mclient.alipay.com/gateway.do";
    }

    public static final void setSafePayUrl(String url) {
        b = url;
    }

    public static final String getCcdcURL(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/CcrCcdcUrl", "https://ccdcapi.alipay.com/cacheWapCardInfo.json");
        }
        return "https://ccdcapi.alipay.com/cacheWapCardInfo.json";
    }

    public static final String getStatisticsUrl(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/StatisticsServerUrl", "http://mdap.alipay.com/loggw/log.do");
        }
        return "http://mdap.alipay.com/loggw/log.do";
    }

    public static final String getCmsHost(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/CmsUrl", "https://d.alipay.com");
        }
        return "https://d.alipay.com";
    }

    public static final String getCmsUrl(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/CmsUrl", "https://d.alipay.com/mbresultyy/prc.htm");
        }
        return "https://d.alipay.com/mbresultyy/prc.htm";
    }

    public static final String getPublicUrl(Context context) {
        return "https://d.alipay.com/mbresultyy/public.htm";
    }

    public static final String getNonsupportCmsUrl(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/NonsupportUrl", "http://d.alipay.net/cpbSign/nonsupport.htm");
        }
        return "https://d.alipay.com/cpbSign/nonsupport.htm";
    }

    public static final String getCpbSignAddCmsUrl(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/CpbSignAddUrl", "http://d.alipay.net/cpbSign/add.htm");
        }
        return "https://d.alipay.com/cpbSign/add.htm";
    }

    public static final String getRobotUrl(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/ContainerServerUrl", "https://cschannel.alipay.com/mobile/csrouter.htm?platform=android");
        }
        return "https://cschannel.alipay.com/mobile/csrouter.htm?platform=android";
    }

    public static final String getPoliceCenterUrl(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/SafePoliceCenterUrl", "https://clientsc.alipay.com/account/gateway.htm");
        }
        return "https://clientsc.alipay.com/account/gateway.htm";
    }

    public static final String getForgetPayPWD(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/ForgetPayPWDUrl", "https://wapcashier.alipay.com/home/resetPayPwd.htm?src=alipayclient&awid=");
        }
        return "https://wapcashier.alipay.com/home/resetPayPwd.htm?src=alipayclient&awid=";
    }

    public static final String getOuterPayPrefix(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/outer_pay_prefix", "https://wappaygw.alipay.com/service/rest.htm");
        }
        return "https://wappaygw.alipay.com/service/rest.htm";
    }

    public static final String getInnerSinglePayPrefix1(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/inner_single_pay_prefix1", "http://maliprod.alipay.com/w/trade_pay.do");
        }
        return "http://maliprod.alipay.com/w/trade_pay.do";
    }

    public static final String getInnerSinglePayPrefix2(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/inner_single_pay_prefix2", "http://mali.alipay.com/w/trade_pay.do");
        }
        return "http://mali.alipay.com/w/trade_pay.do";
    }

    public static final String getInnerBatchPayPrefix1(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/inner_batch_pay_prefix1", "http://maliprod.alipay.com/batch_payment.do");
        }
        return "http://maliprod.alipay.com/batch_payment.do";
    }

    public static final String getInnerBatchPayPrefix2(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/inner_batch_pay_prefix2", "http://mali.alipay.com/batch_payment.do");
        }
        return "http://mali.alipay.com/batch_payment.do";
    }

    public static final String getTaobaoMobileDomain(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/taobao_mobile_domain", "m.taobao.com");
        }
        return "m.taobao.com";
    }

    public static final String getTmallMobileDomain(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/tmall_mobile_domain", "m.tmall.com");
        }
        return "m.tmall.com";
    }

    public final boolean isOnline(Context context) {
        if (!isDebug(context) || getGWFURL(context).indexOf(".alipay.net") <= 0) {
            return true;
        }
        return false;
    }

    public final Boolean isEnableAmnetSetting(Context context) {
        if (isDebug(context)) {
            LogCatUtil.info("ReadSettingServerUrl", "isEnableAmnetSetting.  debug is true");
            String val = getValue(context, "content://com.alipay.setting/XmppUseMmtp", null);
            if ("1".equals(val)) {
                return Boolean.TRUE;
            }
            if ("0".equals(val)) {
                return Boolean.FALSE;
            }
            return null;
        }
        LogCatUtil.info("ReadSettingServerUrl", "isEnableAmnetSetting.  debug is false");
        return null;
    }

    public final Boolean isEnableSpdySetting(Context context) {
        if (isDebug(context)) {
            LogCatUtil.info("ReadSettingServerUrl", "isEnableSpdySetting.  debug is true");
            String val = getValue(context, "content://com.alipay.setting/XmppUseSpdy", null);
            if ("1".equals(val)) {
                return Boolean.TRUE;
            }
            if ("0".equals(val)) {
                return Boolean.FALSE;
            }
            return null;
        }
        LogCatUtil.info("ReadSettingServerUrl", "isEnableSpdySetting.  debug is false");
        return null;
    }

    public final String getAmnetDnsSetting(Context context) {
        String str;
        try {
            if (isDebug(context)) {
                LogCatUtil.info("ReadSettingServerUrl", "getAmnetDnsSetting. debug is true");
                String amnetDns = getValue(context, "content://com.alipay.setting/amnet_dns_conf", null);
                StringBuilder sb = new StringBuilder("getAmnetDnsSetting.  amnetDns=[");
                if (TextUtils.isEmpty(amnetDns)) {
                    str = " is null ";
                } else {
                    str = amnetDns;
                }
                LogCatUtil.info("ReadSettingServerUrl", sb.append(str).append("]").toString());
                return amnetDns;
            }
            LogCatUtil.info("ReadSettingServerUrl", "getAmnetDnsSetting.  debug is false");
            return "";
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "getAmnetDnsSetting exception. " + e2.toString());
            return "";
        }
    }
}
