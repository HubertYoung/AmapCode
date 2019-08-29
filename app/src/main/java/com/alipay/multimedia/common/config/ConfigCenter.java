package com.alipay.multimedia.common.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.mobile.aspect.AliAspectCenter;
import com.alipay.multimedia.adapter.AdapterFactory.Executor;
import com.alipay.multimedia.common.adapter.AdapterInitial;
import com.alipay.multimedia.common.logging.MLog;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.internal.AroundClosure;
import org.aspectj.runtime.reflect.Factory;

public class ConfigCenter {
    public static final String ACTION_UPDATE_CONFIG = "com.alipay.mobile.client.CONFIG_CHANGE";
    private static final String TAG = "ConfigCenter";
    private static ConfigCenter sInstance = new ConfigCenter();
    private Map<String, IConfig> mConfigCache = new HashMap();
    private Map<String, IParser> mConfigParserCache = new HashMap();
    private IConfigProvider mConfigProvider;
    private Context mContext;
    private Executor mExecutor;

    private class ConfigUpdateBroadcastReceiver extends BroadcastReceiver {
        private static final /* synthetic */ StaticPart ajc$tjp_0 = null;

        public class AjcClosure1 extends AroundClosure {
            public AjcClosure1(Object[] objArr) {
                super(objArr);
            }

            public Object run(Object[] objArr) {
                Object[] objArr2 = this.state;
                ConfigUpdateBroadcastReceiver.onReceive_aroundBody0((ConfigUpdateBroadcastReceiver) objArr2[0], (Context) objArr2[1], (Intent) objArr2[2], (JoinPoint) objArr2[3]);
                return null;
            }
        }

        static {
            ajc$preClinit();
        }

        private static /* synthetic */ void ajc$preClinit() {
            Factory factory = new Factory("ConfigCenter.java", ConfigUpdateBroadcastReceiver.class);
            ajc$tjp_0 = factory.makeSJP((String) JoinPoint.METHOD_EXECUTION, (Signature) factory.makeMethodSig((String) "1", (String) "onReceive", (String) "com.alipay.multimedia.common.config.ConfigCenter$ConfigUpdateBroadcastReceiver", (String) "android.content.Context:android.content.Intent", (String) "context:intent", (String) "", (String) "void"), 156);
        }

        private ConfigUpdateBroadcastReceiver() {
        }

        static final /* synthetic */ void onReceive_aroundBody0(ConfigUpdateBroadcastReceiver ajc$this, Context context, Intent intent, JoinPoint joinPoint) {
            if ("com.alipay.mobile.client.CONFIG_CHANGE".equals(intent.getAction())) {
                ConfigCenter.this.sync(false);
            }
        }

        public void onReceive(Context context, Intent intent) {
            JoinPoint makeJP = Factory.makeJP(ajc$tjp_0, this, this, context, intent);
            AliAspectCenter.aspectOf().doAspect(new AjcClosure1(new Object[]{this, context, intent, makeJP}).linkClosureAndJoinPoint(69648));
        }
    }

    public interface IConfigProvider {
        String getConfigValue(String str);
    }

    private ConfigCenter() {
        this.mConfigCache.putAll(Defaults.getDefaultConfigs());
        this.mConfigParserCache.putAll(Defaults.getDefaultParsers());
    }

    public static ConfigCenter get() {
        return sInstance;
    }

    public void initConfigCenter(Context context, IConfigProvider configProvider) {
        MLog.d(TAG, "initConfigCenter context: " + context + ", configProvider: " + configProvider);
        this.mContext = context;
        this.mConfigProvider = configProvider;
        this.mExecutor = AdapterInitial.getAdapterFactory().Executor();
        registerBroadcastReceiver();
    }

    public void sync(boolean background) {
        if (background) {
            this.mExecutor.execute((Runnable) new Runnable() {
                public void run() {
                    ConfigCenter.this.updateInner();
                }
            });
        } else {
            updateInner();
        }
    }

    public <T extends IConfig> T getConfig(String configName) {
        return (IConfig) this.mConfigCache.get(configName);
    }

    public void registerConfigParsers(Map<String, IParser> configParsers) {
        this.mConfigParserCache.putAll(configParsers);
        sync(true);
    }

    public void registerConfigParser(String configName, IParser parser) {
        this.mConfigParserCache.put(configName, parser);
        sync(true);
    }

    public void unregisterConfigParser(String... configNames) {
        for (String configName : configNames) {
            this.mConfigParserCache.remove(configName);
        }
    }

    /* access modifiers changed from: private */
    public void updateInner() {
        synchronized (this) {
            for (Entry entry : this.mConfigParserCache.entrySet()) {
                String value = this.mConfigProvider.getConfigValue((String) entry.getKey());
                if (value != null) {
                    IConfig config = ((IParser) entry.getValue()).parse(value);
                    if (config != null) {
                        IConfig pre = this.mConfigCache.get(entry.getKey());
                        if (pre != null) {
                            pre.update(config);
                        } else {
                            this.mConfigCache.put(entry.getKey(), config);
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    private void registerBroadcastReceiver() {
        if (this.mContext != null) {
            LocalBroadcastManager.getInstance(this.mContext).registerReceiver(new ConfigUpdateBroadcastReceiver(), new IntentFilter("com.alipay.mobile.client.CONFIG_CHANGE"));
        }
    }
}
