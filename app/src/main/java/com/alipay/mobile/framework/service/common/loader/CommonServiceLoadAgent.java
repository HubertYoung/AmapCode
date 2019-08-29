package com.alipay.mobile.framework.service.common.loader;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.MicroService;
import com.alipay.mobile.framework.service.ServicesLoader;
import com.alipay.mobile.framework.service.common.DiskCacheService;
import com.alipay.mobile.framework.service.common.DownloadService;
import com.alipay.mobile.framework.service.common.FilePatcherService;
import com.alipay.mobile.framework.service.common.GenericMemCacheService;
import com.alipay.mobile.framework.service.common.HttpTransportSevice;
import com.alipay.mobile.framework.service.common.ImageLoaderService;
import com.alipay.mobile.framework.service.common.ImageMemCacheService;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.framework.service.common.SilentDownloadService;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.ThirdpartyRpcService;
import com.alipay.mobile.framework.service.common.TimerTaskService;
import com.alipay.mobile.framework.service.common.impl.DiskCacheServiceImpl;
import com.alipay.mobile.framework.service.common.impl.DownloadServiceImpl;
import com.alipay.mobile.framework.service.common.impl.FilePatcherServiceImpl;
import com.alipay.mobile.framework.service.common.impl.GenericMemCacheServiceImpl;
import com.alipay.mobile.framework.service.common.impl.HttpTransportSeviceImpl;
import com.alipay.mobile.framework.service.common.impl.ImageLoaderServiceImpl;
import com.alipay.mobile.framework.service.common.impl.ImageMemCacheServiceImpl;
import com.alipay.mobile.framework.service.common.impl.RpcServiceImpl;
import com.alipay.mobile.framework.service.common.impl.SilentDownloadServiceImpl;
import com.alipay.mobile.framework.service.common.impl.TaskScheduleServiceImpl;
import com.alipay.mobile.framework.service.common.impl.ThirdpartyRpcServiceImpl;
import com.alipay.mobile.framework.service.common.impl.TimerTaskServiceImpl;
import com.alipay.mobile.quinox.utils.LiteProcessInfo;

public class CommonServiceLoadAgent implements ServicesLoader {
    protected MicroApplicationContext mMicroAppContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();

    public CommonServiceLoadAgent() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void preLoad() {
    }

    public void postLoad() {
    }

    public String load(String serviceName) {
        return null;
    }

    public final void load() {
        preLoad();
        registerLazyService(TaskScheduleService.class.getName(), TaskScheduleServiceImpl.class.getName());
        registerService(DiskCacheService.class.getName(), new DiskCacheServiceImpl());
        registerService(GenericMemCacheService.class.getName(), new GenericMemCacheServiceImpl());
        registerLazyService(ImageMemCacheService.class.getName(), ImageMemCacheServiceImpl.class.getName());
        registerLazyService(ImageLoaderService.class.getName(), ImageLoaderServiceImpl.class.getName());
        registerLazyService(HttpTransportSevice.class.getName(), HttpTransportSeviceImpl.class.getName());
        if (LiteProcessInfo.g(this.mMicroAppContext.getApplicationContext()).isCurrentProcessALiteProcess()) {
            registerLazyService(RpcService.class.getName(), "com.alipay.mobile.liteprocess.rpc.LiteRpcServiceImpl");
        } else {
            registerLazyService(RpcService.class.getName(), RpcServiceImpl.class.getName());
        }
        registerLazyService(ThirdpartyRpcService.class.getName(), ThirdpartyRpcServiceImpl.class.getName());
        registerLazyService(DownloadService.class.getName(), DownloadServiceImpl.class.getName());
        registerLazyService(TimerTaskService.class.getName(), TimerTaskServiceImpl.class.getName());
        registerLazyService(FilePatcherService.class.getName(), FilePatcherServiceImpl.class.getName());
        registerLazyService(SilentDownloadService.class.getName(), SilentDownloadServiceImpl.class.getName());
        postLoad();
    }

    public void afterBootLoad() {
        this.mMicroAppContext.findServiceByInterface(TaskScheduleService.class.getName());
        this.mMicroAppContext.findServiceByInterface(RpcService.class.getName());
        this.mMicroAppContext.findServiceByInterface(TimerTaskService.class.getName());
        this.mMicroAppContext.findServiceByInterface(FilePatcherService.class.getName());
    }

    public final void registerService(String serviceName, MicroService service) {
        service.attachContext(this.mMicroAppContext);
        this.mMicroAppContext.registerService(serviceName, service);
    }

    public final void registerLazyService(String serviceName, String className) {
        this.mMicroAppContext.registerService(serviceName, className);
    }
}
