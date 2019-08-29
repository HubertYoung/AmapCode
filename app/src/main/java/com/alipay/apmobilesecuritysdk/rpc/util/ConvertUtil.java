package com.alipay.apmobilesecuritysdk.rpc.util;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.protobuf.okio.ByteString;
import com.alipay.apmobilesecuritysdk.rpc.gen.BizData;
import com.alipay.apmobilesecuritysdk.rpc.gen.DeviceData;
import com.alipay.apmobilesecuritysdk.rpc.gen.ReportRequest;
import com.alipay.apmobilesecuritysdk.rpc.gen.ReportResult;
import com.alipay.apmobilesecuritysdk.rpc.gen.ResultData;
import com.alipay.apmobilesecuritysdk.type.DevType;
import com.alipay.rdssecuritysdk.constant.CONST;
import com.alipay.security.mobile.module.commonutils.CommonUtils;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class ConvertUtil {
    private static TraceLogger a = LoggerFactory.f();

    public static ReportRequest a(DeviceDataRequestModel deviceDataRequestModel) throws Exception {
        Field field;
        ReportRequest reportRequest = new ReportRequest();
        if (deviceDataRequestModel == null) {
            return null;
        }
        Map<String, DevType<?>> f = deviceDataRequestModel.f();
        if (f == null) {
            return null;
        }
        BizData bizData = new BizData();
        bizData.apdid = deviceDataRequestModel.b();
        bizData.apdidToken = deviceDataRequestModel.c();
        bizData.umidToken = deviceDataRequestModel.d();
        bizData.lastTime = deviceDataRequestModel.e();
        bizData.dynamicKey = deviceDataRequestModel.a();
        DeviceData deviceData = new DeviceData();
        TraceLogger traceLogger = a;
        StringBuilder sb = new StringBuilder("data map as follows(");
        sb.append(f.size());
        sb.append(") :");
        traceLogger.b((String) CONST.LOG_TAG, sb.toString());
        Set<String> keySet = f.keySet();
        Class<?> cls = deviceData.getClass();
        if (keySet != null) {
            for (String next : keySet) {
                DevType devType = f.get(next);
                if (devType != null) {
                    try {
                        field = cls.getDeclaredField(next);
                    } catch (Throwable unused) {
                        field = null;
                    }
                    if (field != null) {
                        if (devType.c() == 4 && field.getType() == Integer.class) {
                            field.set(deviceData, devType.b());
                        } else if (devType.c() == 1 && field.getType() == Boolean.class) {
                            field.set(deviceData, devType.b());
                        } else if (devType.c() == 2 && field.getType() == ByteString.class) {
                            byte[] bArr = (byte[]) devType.b();
                            field.set(deviceData, ByteString.of(bArr, 0, bArr.length));
                        } else if (devType.c() == 5 && field.getType() == Long.class) {
                            field.set(deviceData, devType.b());
                        } else if (devType.c() == 6 && field.getType() == String.class) {
                            field.set(deviceData, devType.b());
                        } else {
                            TraceLogger traceLogger2 = a;
                            StringBuilder sb2 = new StringBuilder("error, key = ");
                            sb2.append(next);
                            sb2.append(" , request type is ");
                            sb2.append(field.getType().getCanonicalName());
                            sb2.append(" ,but real type is ");
                            sb2.append(devType.b());
                            traceLogger2.b((String) CONST.LOG_TAG, sb2.toString());
                        }
                    }
                }
            }
        }
        reportRequest.bizData = bizData;
        reportRequest.deviceData = deviceData;
        JSONObject jSONObject = new JSONObject();
        if (f != null) {
            try {
                jSONObject.put("AD43", f.get("AD43").b());
            } catch (Throwable unused2) {
            }
            try {
                jSONObject.put("AD48", f.get("AD48").b());
            } catch (Throwable unused3) {
            }
            try {
                jSONObject.put("AD49", f.get("AD49").b());
            } catch (Throwable unused4) {
            }
            try {
                DevType devType2 = f.get("AD100");
                if (devType2 != null) {
                    jSONObject.put("AD100", devType2.b());
                }
            } catch (Throwable unused5) {
            }
            try {
                DevType devType3 = f.get("AD101");
                if (devType3 != null) {
                    jSONObject.put("AD101", devType3.b());
                }
            } catch (Throwable unused6) {
            }
            try {
                DevType devType4 = f.get("AD50");
                if (devType4 != null) {
                    jSONObject.put("AD50", devType4.b());
                }
            } catch (Throwable unused7) {
            }
        }
        reportRequest.extDeviceData = jSONObject.toString();
        TraceLogger traceLogger3 = a;
        StringBuilder sb3 = new StringBuilder("extDeviceData ");
        sb3.append(reportRequest.extDeviceData);
        traceLogger3.b((String) CONST.LOG_TAG, sb3.toString());
        return reportRequest;
    }

    public static DeviceDataReponseModel a(ReportResult reportResult) {
        if (reportResult == null) {
            return null;
        }
        DeviceDataReponseModel deviceDataReponseModel = new DeviceDataReponseModel();
        deviceDataReponseModel.a = reportResult.success.booleanValue();
        deviceDataReponseModel.b = reportResult.resultCode;
        deviceDataReponseModel.l = reportResult.extResultData;
        deviceDataReponseModel.f = "0";
        deviceDataReponseModel.h = "0";
        deviceDataReponseModel.m = "0";
        ResultData resultData = reportResult.resultData;
        if (resultData != null) {
            deviceDataReponseModel.c = resultData.apdid;
            deviceDataReponseModel.d = resultData.apdidToken;
            deviceDataReponseModel.e = resultData.createTime;
            deviceDataReponseModel.g = resultData.appListCmdVer;
            deviceDataReponseModel.i = resultData.dynamicKey;
            deviceDataReponseModel.j = resultData.webrtcUrl;
            deviceDataReponseModel.k = resultData.timeInterval;
            String str = resultData.drmSwitch;
            if (CommonUtils.isNotBlank(str)) {
                if (str.length() > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str.charAt(0));
                    deviceDataReponseModel.f = sb.toString();
                }
                if (str.length() >= 3) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str.charAt(2));
                    deviceDataReponseModel.h = sb2.toString();
                }
                if (str.length() >= 5) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str.charAt(4));
                    deviceDataReponseModel.m = sb3.toString();
                }
            }
        }
        return deviceDataReponseModel;
    }
}
