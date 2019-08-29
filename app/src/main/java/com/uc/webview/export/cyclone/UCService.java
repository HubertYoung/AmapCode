package com.uc.webview.export.cyclone;

import android.content.Context;
import android.util.Pair;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.uc.webview.export.cyclone.service.UCDex;
import com.uc.webview.export.cyclone.service.UCServiceInterface;
import com.uc.webview.export.cyclone.service.UCUnSevenZip;
import com.uc.webview.export.cyclone.service.UCVmsize;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Constant
/* compiled from: ProGuard */
public class UCService {
    private static final int DEBUG_TOKEN = UCLogger.createToken("d", LOG_TAG);
    private static final String LOG_TAG = "UCService";
    private static final int MAX_CONFIG_CONTENT_LENGTH = 4096;
    private static final int VERBOSE_TOKEN = UCLogger.createToken("v", LOG_TAG);
    private static final int WARNN_TOKEN = UCLogger.createToken("w", LOG_TAG);
    private static Context s_ctx;
    private static final UCSingleton<ConcurrentHashMap<Class<? extends UCServiceInterface>, ConcurrentLinkedQueue<Pair<String, String>>>> s_serviceImplQueues = new UCSingleton<>(ConcurrentHashMap.class, new Class[0]);
    private static final UCSingleton<ConcurrentHashMap<Class<? extends UCServiceInterface>, UCServiceInterface>> s_serviceImpls = new UCSingleton<>(ConcurrentHashMap.class, new Class[0]);
    private static final UCSingleton<ConcurrentHashMap<String, Class<? extends UCServiceInterface>>> s_services = new UCSingleton<>(ConcurrentHashMap.class, new Class[0]);

    static {
        registerDefaultImpl(UCUnSevenZip.class, "com.uc.webview.export.cyclone.service.UCUnSevenZipMultiThreadImpl");
        registerDefaultImpl(UCVmsize.class, "com.uc.webview.export.cyclone.service.UCVmsizeImpl");
    }

    public static void registerService(String str, Class<? extends UCServiceInterface> cls) throws Exception {
        int i = DEBUG_TOKEN;
        StringBuilder sb = new StringBuilder("registerService ");
        sb.append(str);
        sb.append(",");
        sb.append(cls);
        UCLogger.print(i, sb.toString(), new Throwable[0]);
        Class<? extends UCServiceInterface> cls2 = (Class) ((ConcurrentHashMap) s_services.initInst(new Object[0])).get(str);
        if (cls2 == null || cls2 == cls) {
            ((ConcurrentHashMap) s_services.initInst(new Object[0])).put(str, cls);
            return;
        }
        StringBuilder sb2 = new StringBuilder("registerService service name '");
        sb2.append(str);
        sb2.append("' is registered by '");
        sb2.append(cls2);
        sb2.append("' but now '");
        sb2.append(cls);
        sb2.append("' requested.");
        throw new UCKnownException(2017, sb2.toString());
    }

    public static void registerImpl(Class<? extends UCServiceInterface> cls, String str, String str2) throws Exception {
        int i = DEBUG_TOKEN;
        StringBuilder sb = new StringBuilder("registerImpl ");
        sb.append(cls);
        sb.append(",");
        sb.append(str);
        sb.append(",");
        sb.append(str2);
        UCLogger.print(i, sb.toString(), new Throwable[0]);
        if (cls == null) {
            throw new UCKnownException(2016, (String) "registerImpl param null.");
        } else if (str == null || str.length() <= 0) {
            throw new UCKnownException(2016, (String) "registerImpl param null.");
        } else {
            ConcurrentLinkedQueue concurrentLinkedQueue = (ConcurrentLinkedQueue) ((ConcurrentHashMap) s_serviceImplQueues.initInst(new Object[0])).get(cls);
            if (concurrentLinkedQueue == null) {
                synchronized (cls) {
                    concurrentLinkedQueue = (ConcurrentLinkedQueue) ((ConcurrentHashMap) s_serviceImplQueues.initInst(new Object[0])).get(cls);
                    if (concurrentLinkedQueue == null) {
                        concurrentLinkedQueue = new ConcurrentLinkedQueue();
                        ((ConcurrentHashMap) s_serviceImplQueues.initInst(new Object[0])).put(cls, concurrentLinkedQueue);
                    }
                }
            }
            if (getImpl(cls) != null) {
                throw new UCKnownException(2018, (String) "registerImpl: the service has instanced. Please registers service impl before use it");
            }
            concurrentLinkedQueue.add(new Pair(str, str2));
        }
    }

    public static boolean registerImpl(Class<? extends UCServiceInterface> cls, UCServiceInterface uCServiceInterface) throws Exception {
        int i = DEBUG_TOKEN;
        StringBuilder sb = new StringBuilder("registerImpl ");
        sb.append(cls);
        sb.append("=>");
        sb.append(uCServiceInterface);
        UCLogger.print(i, sb.toString(), new Throwable[0]);
        if (cls == null) {
            throw new UCKnownException(2013, (String) "registerImpl: serviceInterface is null.");
        } else if (uCServiceInterface == null) {
            ((ConcurrentHashMap) s_serviceImpls.initInst(new Object[0])).remove(cls);
            return true;
        } else {
            UCServiceInterface uCServiceInterface2 = (UCServiceInterface) ((ConcurrentHashMap) s_serviceImpls.initInst(new Object[0])).get(cls);
            if (uCServiceInterface2 != null && uCServiceInterface2.getServiceVersion() >= uCServiceInterface.getServiceVersion()) {
                return false;
            }
            if (cls.isInstance(uCServiceInterface)) {
                ((ConcurrentHashMap) s_serviceImpls.initInst(new Object[0])).put(cls, uCServiceInterface);
                return true;
            }
            StringBuilder sb2 = new StringBuilder("registerImpl: impl");
            sb2.append(uCServiceInterface);
            sb2.append(" is not compatible with interface ");
            sb2.append(cls);
            sb2.append(".");
            throw new UCKnownException(2014, sb2.toString());
        }
    }

    private static Class<? extends UCServiceInterface> getService(String str) {
        if (s_services.getInst() == null) {
            return null;
        }
        return (Class) ((ConcurrentHashMap) s_services.getInst()).get(str);
    }

    private static UCServiceInterface getImpl(Class<? extends UCServiceInterface> cls) {
        if (s_serviceImpls.getInst() == null) {
            return null;
        }
        return (UCServiceInterface) ((ConcurrentHashMap) s_serviceImpls.getInst()).get(cls);
    }

    private static ConcurrentLinkedQueue<Pair<String, String>> getImplQueue(Class<? extends UCServiceInterface> cls) {
        if (s_serviceImplQueues.getInst() == null) {
            return null;
        }
        return (ConcurrentLinkedQueue) ((ConcurrentHashMap) s_serviceImplQueues.getInst()).get(cls);
    }

    public static <T extends UCServiceInterface> T initImpl(Class<T> cls) {
        T impl = getImpl(cls);
        if (impl == null) {
            ConcurrentLinkedQueue<Pair<String, String>> implQueue = getImplQueue(cls);
            if (implQueue != null) {
                synchronized (cls) {
                    while (!implQueue.isEmpty()) {
                        Pair poll = implQueue.poll();
                        ClassLoader classLoader = null;
                        try {
                            if (poll.second == null) {
                                classLoader = cls.getClassLoader();
                            } else if (s_ctx != null) {
                                String parent = new File((String) poll.second).getParent();
                                UCDex uCDex = (UCDex) initImpl(UCDex.class);
                                if (uCDex == null) {
                                    classLoader = new DexClassLoader((String) poll.second, parent, parent, cls.getClassLoader());
                                } else {
                                    classLoader = uCDex.createDexClassLoader(s_ctx, null, (String) poll.second, parent, parent, cls.getClassLoader());
                                }
                            }
                            Class<?> cls2 = Class.forName((String) poll.first, true, classLoader);
                            int i = DEBUG_TOKEN;
                            StringBuilder sb = new StringBuilder("initImpl ");
                            sb.append(cls);
                            sb.append("=>");
                            sb.append(cls2);
                            sb.append(" with ");
                            sb.append((String) poll.first);
                            sb.append(",");
                            sb.append((String) poll.second);
                            UCLogger.print(i, sb.toString(), new Throwable[0]);
                        } catch (Throwable th) {
                            UCLogger.print(WARNN_TOKEN, "initImpl exception", th);
                        }
                    }
                    impl = getImpl(cls);
                }
            }
        }
        return impl;
    }

    public static int search(Context context, File file) {
        File[] listFiles;
        if (file == null || !file.isDirectory()) {
            return 0;
        }
        s_ctx = context.getApplicationContext();
        LinkedList linkedList = new LinkedList();
        do {
            UCLogger.print(DEBUG_TOKEN, "searching ".concat(String.valueOf(file)), new Throwable[0]);
            for (File file2 : file.listFiles()) {
                if (!(file2 == null || !file2.exists() || file2.getName().replace(".", "").replace("/", "").replace(Token.SEPARATOR, "").length() == 0)) {
                    if (file2.isDirectory()) {
                        linkedList.add(file2);
                    } else if (file2.isFile() && file2.getName().startsWith("cyclone.UCService.") && file2.length() < 4096) {
                        String[] split = file2.getName().split("\\.", 4);
                        if (split.length >= 4) {
                            String str = split[2];
                            String str2 = split[3];
                            int i = DEBUG_TOKEN;
                            StringBuilder sb = new StringBuilder("search config file:");
                            sb.append(str);
                            sb.append("=>");
                            sb.append(str2);
                            UCLogger.print(i, sb.toString(), new Throwable[0]);
                            File file3 = new File(file, str2);
                            if (file3.isFile()) {
                                try {
                                    Class<? extends UCServiceInterface> service = getService(str);
                                    if (service == null) {
                                        int i2 = WARNN_TOKEN;
                                        StringBuilder sb2 = new StringBuilder("search service:");
                                        sb2.append(str);
                                        sb2.append(" not registered.");
                                        UCLogger.print(i2, sb2.toString(), new Throwable[0]);
                                    } else {
                                        String asciiContents = getAsciiContents(file2);
                                        if (asciiContents != null) {
                                            if (asciiContents.length() != 0) {
                                                String[] split2 = asciiContents.split(",");
                                                if (split2.length == 0) {
                                                    UCLogger.print(WARNN_TOKEN, "search no impl class defined in config.", new Throwable[0]);
                                                } else {
                                                    UCLogger.print(DEBUG_TOKEN, "search config contents:".concat(String.valueOf(asciiContents)), new Throwable[0]);
                                                    for (String trim : split2) {
                                                        String trim2 = trim.trim();
                                                        if (trim2.length() != 0) {
                                                            try {
                                                                registerImpl(service, trim2, file3.getAbsolutePath());
                                                            } catch (Exception e) {
                                                                UCLogger.print(WARNN_TOKEN, "search exception", e);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        UCLogger.print(WARNN_TOKEN, "search config contents is null.", new Throwable[0]);
                                    }
                                } catch (Exception e2) {
                                    UCLogger.print(WARNN_TOKEN, "search exception", e2);
                                }
                            }
                        }
                    }
                }
            }
            file = (File) linkedList.poll();
        } while (!linkedList.isEmpty());
        return 0;
    }

    private static void registerDefaultImpl(Class<? extends UCServiceInterface> cls, String str) {
        int i = VERBOSE_TOKEN;
        StringBuilder sb = new StringBuilder("registerDefaultImpl ");
        sb.append(cls);
        sb.append(",");
        sb.append(str);
        UCLogger.print(i, sb.toString(), new Throwable[0]);
        try {
            registerService(cls.getSimpleName(), cls);
            registerImpl(cls, str, null);
        } catch (Throwable th) {
            UCLogger create = UCLogger.create("w", LOG_TAG);
            if (create != null) {
                create.print("registerDefaultImpl register exception:".concat(String.valueOf(th)), new Throwable[0]);
            }
        }
    }

    private static String getAsciiContents(File file) throws Exception {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[4096];
                fileInputStream.read(bArr);
                String str = new String(bArr, "US-ASCII");
                UCCyclone.close(fileInputStream);
                return str;
            } catch (Throwable th) {
                th = th;
                UCCyclone.close(fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            UCCyclone.close(fileInputStream);
            throw th;
        }
    }
}
