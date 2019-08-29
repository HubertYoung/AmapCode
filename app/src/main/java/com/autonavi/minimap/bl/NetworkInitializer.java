package com.autonavi.minimap.bl;

import android.content.Context;
import com.autonavi.minimap.bl.net.INetworkProvider;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class NetworkInitializer {
    static a config;
    static Context context;

    public static class a {
        iq a;
        ctg b;
        is c;
        INetworkProvider d;
        boolean e = true;

        a() {
        }
    }

    public static class b {
        private a a = new a();

        public final b a(iq iqVar) {
            this.a.a = iqVar;
            return this;
        }

        public final b a(INetworkProvider iNetworkProvider) {
            this.a.d = iNetworkProvider;
            return this;
        }

        public final b a(is isVar) {
            this.a.c = isVar;
            return this;
        }

        public final b a(ctg ctg) {
            this.a.b = ctg;
            return this;
        }

        public final a a() {
            if (this.a.b == null) {
                this.a.b = new ctg() {
                    public final String a() {
                        return "";
                    }
                };
            }
            return this.a;
        }
    }

    public static void init(Context context2, a aVar) {
        config = aVar;
        context = context2.getApplicationContext();
        initAosService();
    }

    public static void destroy() {
        NetworkService.destroy();
        config = null;
        context = null;
    }

    private static void initAosService() {
        in.a((ir) new ir() {
            public final is a() {
                return NetworkInitializer.config.c;
            }

            public final iq b() {
                return NetworkInitializer.config.a;
            }
        });
    }
}
