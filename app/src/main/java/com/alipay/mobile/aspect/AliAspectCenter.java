package com.alipay.mobile.aspect;

import com.alipay.mobile.aspect.processor.IAspectJProcessor;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import org.aspectj.lang.NoAspectBoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AliAspectCenter {
    public static final String TAG = "AliAspectCenter";
    private static Throwable a;
    public static final AliAspectCenter ajc$perSingletonInstance = null;
    public static boolean useAspectProcessor = false;

    private static void a() {
        ajc$perSingletonInstance = new AliAspectCenter();
    }

    public static AliAspectCenter aspectOf() {
        if (ajc$perSingletonInstance != null) {
            return ajc$perSingletonInstance;
        }
        throw new NoAspectBoundException("com.alipay.mobile.aspect.AliAspectCenter", a);
    }

    public static boolean hasAspect() {
        return ajc$perSingletonInstance != null;
    }

    static {
        try {
            a();
        } catch (Throwable th) {
            a = th;
        }
    }

    @Around("call(* android.content.ContentResolver.query(..))|| call(* android.content.ContentResolver.insert(..))|| call(* android.content.ContentResolver.update(..))|| call(* android.content.ContentResolver.delete(..))|| call(* android.content.ContentResolver.applyBatch(..))|| call(* android.content.ContentResolver.registerContentObserver(..))|| call(* android.telephony.SmsManager+.sendTextMessage(..))|| call(* android.content.Context+.startService(..))|| call(* android.content.Context+.bindService(..))|| call(* android.content.Context+.startActivity(..))|| execution(* android.content.BroadcastReceiver+.onReceive(..))|| call(* android.hardware.camera2.CameraManager+.openCamera(..))|| call(* android.media.AudioRecord+.startRecording(..))|| call(* android.telephony.TelephonyManager+.getCellLocation(..))|| call(* android.telephony.TelephonyManager+.getNeighboringCellInfo(..))|| call(* android.telephony.TelephonyManager+.getDeviceId(..))|| call(* android.telephony.TelephonyManager+.getImei(..))|| call(* android.telephony.TelephonyManager+.getSimSerialNumber(..))|| call(* android.telephony.TelephonyManager+.getSubscriberId(..))|| call(* android.location.LocationManager+.getLastKnownLocation(..))|| call(* android.location.LocationManager+.requestLocationUpdates(..))|| call(* android.support.v4.app.ActivityCompat+.requestPermissions(..))|| call(* android.support.v13.app.FragmentCompat+.requestPermissions(..))|| call(* android.app.Activity+.requestPermissions(..))|| call(* android.app.Fragment+.requestPermissions(..))|| call(* android.hardware.Camera+.open(..))|| call(* android.hardware.SensorManager+.registerListener(..))|| call(* android.hardware.SensorManager+.unregisterListener(..))|| call(* java.io.File+.delete(..))|| call(* java.io.File+.deleteOnExit(..))|| call(* android.net.wifi.WifiInfo+.getBSSID(..))|| call(* android.net.wifi.WifiInfo+.getMacAddress(..))|| call(* android.net.wifi.WifiInfo+.getSSID(..))|| call(* android.net.wifi.WifiInfo+.getIpAddress(..))|| call(* android.net.wifi.WifiInfo+.getNetworkId(..))|| call(* android.net.wifi.WifiManager+.getScanResults(..))|| call(* android.net.wifi.WifiManager+.startScan(..))|| call(* android.bluetooth.BluetoothAdapter+.getAddress(..))|| call(* java.net.NetworkInterface+.getHardwareAddress(..))|| call(* android.telephony.gsm.GsmCellLocation+.getCid(..))|| call(* android.telephony.cdma.CdmaCellLocation+.getBaseStationId(..))|| call(* android.content.pm.PackageManager+.getInstalledPackages(..))|| call(* java.net.InetAddress+.getHostAddress(..))|| call(* android.telephony.TelephonyManager+.getLine1Number(..))|| call(* android.telephony.TelephonyManager+.getSimCountryIso(..))|| call(* android.telephony.TelephonyManager+.getSimOperator(..))|| call(* android.telephony.TelephonyManager+.getSimOperatorName(..))|| call(* android.telephony.TelephonyManager+.getNetworkOperator(..))|| call(* android.telephony.TelephonyManager+.getNetworkOperatorName(..))|| call(* android.telephony.TelephonyManager+.getNetworkType(..))|| call(* android.app.AlarmManager+.set(..))|| call(* android.app.AlarmManager+.setExact(..))|| call(* android.app.AlarmManager+.setRepeating(..))|| call(* android.app.AlarmManager+.setInexactRepeating(..))|| call(* android.app.AlarmManager+.cancel(..))|| call(* android.os.PowerManager.WakeLock+.acquire(..))|| call(* android.os.PowerManager.WakeLock+.release())")
    public Object doAspect(ProceedingJoinPoint joinPoint) {
        return doAspectInner(joinPoint);
    }

    public static Object doAspectInner(ProceedingJoinPoint joinPoint) {
        Object proceed;
        if (!useAspectProcessor) {
            return joinPoint.proceed();
        }
        IAspectJProcessor processor = AspectJProcessorManager.get().obtainProcessor(joinPoint);
        if (processor == null) {
            return joinPoint.proceed();
        }
        if ((processor.getFlags() & 4) > 0) {
            try {
                return processor.whenIntercepted(joinPoint, null);
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().error((String) TAG, tr);
                return joinPoint.proceed();
            }
        } else {
            try {
                processor.beforeMethod(joinPoint, null);
            } catch (Throwable tr2) {
                LoggerFactory.getTraceLogger().error((String) TAG, tr2);
            }
            if ((processor.getFlags() & 1) > 0) {
                try {
                    proceed = joinPoint.proceed();
                } catch (Throwable tr3) {
                    return processor.whenThrown(joinPoint, tr3, null);
                }
            } else {
                proceed = joinPoint.proceed();
            }
            try {
                if ((processor.getFlags() & 2) > 0) {
                    return processor.afterMethodWithReturn(joinPoint, proceed, null);
                }
                processor.afterMethodWithReturn(joinPoint, proceed, null);
                return proceed;
            } catch (Throwable tr4) {
                LoggerFactory.getTraceLogger().error((String) TAG, tr4);
                return proceed;
            }
        }
    }
}
