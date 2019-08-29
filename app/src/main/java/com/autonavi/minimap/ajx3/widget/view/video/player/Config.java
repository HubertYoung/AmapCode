package com.autonavi.minimap.ajx3.widget.view.video.player;

import android.content.Context;
import com.autonavi.minimap.ajx3.widget.view.video.util.Utils;
import java.io.File;

public class Config {
    private boolean mCacheEnable;
    private etm mCacheProxy;
    private Context mContext;
    private boolean mDebugEnable;
    private IPlayerFactory mPlayerFactory;
    private boolean mSmallWindowPlayEnable;

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean cacheEnable = false;
        /* access modifiers changed from: private */
        public Context context;
        /* access modifiers changed from: private */
        public boolean debugEnable;
        /* access modifiers changed from: private */
        public IPlayerFactory playerFactory;
        /* access modifiers changed from: private */
        public etm proxy;
        /* access modifiers changed from: private */
        public boolean smallWindowPlayEnable = false;

        public Builder(Context context2) {
            this.context = context2;
        }

        protected Builder() {
        }

        public final Builder buildPlayerFactory(IPlayerFactory iPlayerFactory) {
            this.playerFactory = iPlayerFactory;
            return this;
        }

        public final Builder enableSmallWindowPlay() {
            this.smallWindowPlayEnable = true;
            return this;
        }

        public final Builder cache(boolean z) {
            this.cacheEnable = z;
            return this;
        }

        public final Builder debug(boolean z) {
            this.debugEnable = z;
            return this;
        }

        public final Builder cacheProxy(etm etm) {
            this.proxy = etm;
            return this;
        }

        public final Config build() {
            if (this.playerFactory == null) {
                this.playerFactory = new MediaPlayerFactory();
            }
            this.cacheEnable = Utils.isSDCardAvailable() && this.cacheEnable;
            if (this.cacheEnable && this.proxy == null) {
                try {
                    this.proxy = buildCacheProxy();
                } catch (Throwable unused) {
                }
            }
            return new Config(this);
        }

        private etm buildCacheProxy() {
            a aVar = new a(this.context.getApplicationContext());
            aVar.a = (File) etr.a(new File(Utils.getCacheDir()));
            aVar.c = new eud(1073741824);
            aVar.b = (etz) etr.a(new etz() {
                public String generate(String str) {
                    return ett.c(str);
                }
            });
            etj etj = new etj(aVar.a, aVar.b, aVar.c, aVar.d, aVar.e);
            return new etm(etj, 0);
        }
    }

    private Config(Builder builder) {
        this.mPlayerFactory = builder.playerFactory;
        this.mSmallWindowPlayEnable = builder.smallWindowPlayEnable;
        this.mCacheEnable = builder.cacheEnable;
        this.mCacheProxy = builder.proxy;
        this.mDebugEnable = builder.debugEnable;
        this.mContext = builder.context;
    }

    public boolean isDebugEnable() {
        return this.mDebugEnable;
    }

    public IPlayerFactory getPlayerFactory() {
        return this.mPlayerFactory;
    }

    public boolean isSmallWindowPlayEnable() {
        return this.mSmallWindowPlayEnable;
    }

    public boolean isCacheEnable() {
        return this.mCacheEnable && this.mCacheProxy != null;
    }

    public etm getCacheProxy() {
        return this.mCacheProxy;
    }

    public Context getContext() {
        return this.mContext;
    }
}
