package com.alipay.mobile.common.transport.http;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class AndroidH2DnsHandler implements InvocationHandler {
    private static AndroidH2DnsHandler a;
    private static final String[] b = {"resolveInetAddresses", "lookup"};
    private static final Class<?>[] c = {InetAddress[].class, List.class};
    private Object d;

    class MethodDesc {
        String dnsMethodName = null;
        Class<?> dnsMethodReturnType = null;

        MethodDesc() {
        }
    }

    public static final AndroidH2DnsHandler getInstance() {
        if (a != null) {
            return a;
        }
        synchronized (AndroidH2DnsHandler.class) {
            if (a != null) {
                AndroidH2DnsHandler androidH2DnsHandler = a;
                return androidH2DnsHandler;
            }
            a = new AndroidH2DnsHandler();
            return a;
        }
    }

    private AndroidH2DnsHandler() {
    }

    /* JADX INFO: finally extract failed */
    public Object invoke(Object proxy, Method method, Object[] args) {
        MethodDesc methodDesc = new MethodDesc();
        if (!a(method, args, methodDesc)) {
            LogCatUtil.warn((String) "AndroidH2DnsHandler", (String) "invoke. preCheck is false， use raw dns.");
            return a(method, args);
        }
        Object addresses = null;
        String hostName = "";
        String dnsFrom = "";
        try {
            hostName = (String) args[0];
            if (!TextUtils.equals(methodDesc.dnsMethodName, method.getName())) {
                LogCatUtil.warn((String) "AndroidH2DnsHandler", "Invoke method name it's " + method.getName() + ", method name not " + methodDesc.dnsMethodName);
                Object addresses2 = a(method, args);
                a(addresses2, hostName, (String) "rawdns");
                return addresses2;
            } else if (DnsUtil.isLogicIP(hostName)) {
                Object addresses3 = a(new InetAddress[]{InetAddress.getByAddress(DnsUtil.ipToBytesByReg(hostName))}, methodDesc);
                a(addresses3, hostName, dnsFrom);
                return addresses3;
            } else {
                AlipayHttpDnsClient dnsClient = AlipayHttpDnsClient.getDnsClient();
                if (dnsClient != null) {
                    InetAddress[] addressArray = dnsClient.getAllByName(hostName);
                    if (addressArray != null && addressArray.length > 0) {
                        Object addresses4 = a(addressArray, methodDesc);
                        a(addresses4, hostName, (String) "dnsClient");
                        return addresses4;
                    }
                }
                addresses = a(DnsUtil.getAllByName(hostName), methodDesc);
                a(addresses, hostName, (String) RPCDataItems.VALUE_DT_LOCALDNS);
                return addresses;
            }
        } catch (Throwable th) {
            a(addresses, hostName, dnsFrom);
            throw th;
        }
    }

    public void setRawAndroidH2DnsHandler(Object rawAndroidH2DnsHandler) {
        this.d = rawAndroidH2DnsHandler;
    }

    private static Object a(InetAddress[] returnValue, MethodDesc methodDesc) {
        if (returnValue == null) {
            throw new IllegalArgumentException("adapteReturnValue.  returnValue maybe null.");
        } else if (methodDesc.dnsMethodReturnType == returnValue.getClass()) {
            return returnValue;
        } else {
            List inetAddrlist = Arrays.asList(returnValue);
            LogCatUtil.info("AndroidH2DnsHandler", "adapteReturnValue. returnValue=[" + inetAddrlist.toString() + "]");
            return inetAddrlist;
        }
    }

    private static void a(Object addresses, String hostName, String dnsFrom) {
        if (addresses == null) {
            try {
                LogCatUtil.info("AndroidH2DnsHandler", "printLog. DNS resolution failed. hostName = " + hostName);
            } catch (Throwable e) {
                LogCatUtil.warn("AndroidH2DnsHandler", "printLog. finally process exception ", e);
            }
        } else {
            int count = 0;
            String ips = "";
            if (addresses.getClass() == InetAddress[].class) {
                InetAddress[] tmpInetAddress = (InetAddress[]) addresses;
                ips = Arrays.toString(tmpInetAddress);
                count = tmpInetAddress.length;
            } else {
                if (List.class.isAssignableFrom(addresses.getClass())) {
                    List tmpInetAddressList = (List) addresses;
                    ips = tmpInetAddressList.toString();
                    count = tmpInetAddressList.size();
                } else {
                    LogCatUtil.warn((String) "AndroidH2DnsHandler", "printLog. Unknown address object type it's = " + addresses.getClass().getName());
                }
            }
            LogCatUtil.info("AndroidH2DnsHandler", "printLog. DNS resolution is complete. dnsFrom = " + dnsFrom + ", hostName = " + hostName + ", addresses len = " + count + ",ips = [" + ips + "]");
        }
    }

    private Object a(Method method, Object[] args) {
        if (this.d == null) {
            throw new IllegalArgumentException("rawAndroidH2DnsHandler field is null, you must first call this 'setRawAndroidH2DnsHandler' to set the field");
        }
        if (TextUtils.equals(method.getName(), b[0]) || TextUtils.equals(method.getName(), b[1])) {
            TransportContextThreadLocalUtils.addDnsType(RPCDataItems.VALUE_DT_LOCALDNS);
        }
        Object object = method.invoke(this.d, args);
        StringBuilder stringBuilder = new StringBuilder("invokeFromRawObj execution finish. raw class = [" + this.d.getClass().getName() + "], methodName=[" + method.getName() + "] ");
        if (object != null) {
            stringBuilder.append(", value = [" + JSON.toJSONString(object) + "]");
        }
        LogCatUtil.info("AndroidH2DnsHandler", stringBuilder.toString());
        return object;
    }

    private static boolean a(Method method, Object[] args, MethodDesc methodDesc) {
        if (args == null || args.length <= 0) {
            LogCatUtil.info("AndroidH2DnsHandler", "preCheck. args count < 1 ");
            return false;
        }
        Class currentReturnType = method.getReturnType();
        Class[] clsArr = c;
        int length = clsArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (currentReturnType == clsArr[i]) {
                methodDesc.dnsMethodReturnType = currentReturnType;
                LogCatUtil.warn((String) "AndroidH2DnsHandler", "preCheck. dnsMethodReturnType is " + currentReturnType.getName());
                break;
            } else {
                i++;
            }
        }
        if (methodDesc.dnsMethodReturnType == null) {
            return false;
        }
        String currentMethodName = method.getName();
        String[] strArr = b;
        int length2 = strArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length2) {
                break;
            } else if (strArr[i2].equals(currentMethodName)) {
                LogCatUtil.warn((String) "AndroidH2DnsHandler", "preCheck. dnsMethodName is " + currentMethodName);
                methodDesc.dnsMethodName = currentMethodName;
                break;
            } else {
                i2++;
            }
        }
        if (!TextUtils.isEmpty(methodDesc.dnsMethodName)) {
            return true;
        }
        return false;
    }
}
