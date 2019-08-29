package com.taobao.accs.update;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import dalvik.system.DexClassLoader;
import java.io.File;

public class ACCSClassLoader {
    private static final String TAG = "ACCSClassLoader";
    private static ACCSClassLoader sInstance;
    /* access modifiers changed from: private */
    public boolean dexOpting = false;
    private ClassLoader mClassLoader = null;
    /* access modifiers changed from: private */
    public Context mContext;

    static class InnerClassLoader extends DexClassLoader {
        private ClassLoader contextClassLoader;

        public InnerClassLoader(String str, String str2, String str3, ClassLoader classLoader) {
            super(str, str2, str3, classLoader.getParent());
            this.contextClassLoader = classLoader;
        }

        /* access modifiers changed from: protected */
        public Class<?> findClass(String str) throws ClassNotFoundException {
            try {
                return super.findClass(str);
            } catch (Exception unused) {
                return this.contextClassLoader.loadClass(str);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ACCSClassLoader$InnerClassLoader$");
            sb.append(hashCode());
            return sb.toString();
        }
    }

    public static synchronized ACCSClassLoader getInstance() {
        ACCSClassLoader aCCSClassLoader;
        synchronized (ACCSClassLoader.class) {
            try {
                if (sInstance == null) {
                    sInstance = new ACCSClassLoader();
                }
                aCCSClassLoader = sInstance;
            }
        }
        return aCCSClassLoader;
    }

    public synchronized ClassLoader getClassLoader(Context context) {
        if (context != null) {
            try {
                this.mContext = context;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mClassLoader == null) {
            ALog.d(TAG, "create new class loader", new Object[0]);
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_FILE_NAME, 0);
            String string = sharedPreferences.getString(Constants.SP_KEY_UPDATE_FOLDER, null);
            ALog.d(TAG, "baseUpdateFolder:".concat(String.valueOf(string)), new Object[0]);
            if (string != null) {
                File dir = context.getDir(string, 0);
                if (dir.exists() && dir.isDirectory()) {
                    File file = new File(dir, Constants.UPDATE_DEX_FILE);
                    if (file.exists() && file.isFile() && sharedPreferences.getInt(Constants.SP_KEY_UPDATE_VERSION, Constants.SDK_VERSION_CODE) > 221) {
                        if (sharedPreferences.getBoolean(Constants.SP_KEY_UPDATE_DONE, false)) {
                            ALog.d(TAG, "dexopt already done", new Object[0]);
                            this.mClassLoader = new InnerClassLoader(file.getAbsolutePath(), dir.getAbsolutePath(), new File(dir.getParentFile(), "lib").getAbsolutePath(), ACCSClassLoader.class.getClassLoader());
                        } else {
                            ALog.d(TAG, "try dexopt", new Object[0]);
                            dexopt(file.getAbsolutePath(), dir.getAbsolutePath());
                        }
                    }
                }
            }
        }
        if (this.mClassLoader == null) {
            ALog.d(TAG, "get defalut class loader", new Object[0]);
            this.mClassLoader = ACCSClassLoader.class.getClassLoader();
        }
        return this.mClassLoader;
    }

    public synchronized ClassLoader getClassLoader() {
        try {
            if (this.mClassLoader == null) {
                ALog.d(TAG, "getClassLoader", new Object[0]);
                this.mClassLoader = ACCSClassLoader.class.getClassLoader();
            }
        }
        return this.mClassLoader;
    }

    private synchronized void dexopt(final String str, final String str2) {
        if (this.dexOpting) {
            ALog.d(TAG, "dexOpting, exit", new Object[0]);
            return;
        }
        this.dexOpting = true;
        new Thread() {
            public void run() {
                new InnerClassLoader(str, str2, null, ACCSClassLoader.class.getClassLoader());
                ALog.d(ACCSClassLoader.TAG, "dexOpt done", new Object[0]);
                ACCSClassLoader.this.dexOpting = false;
                Editor edit = ACCSClassLoader.this.mContext.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                edit.putBoolean(Constants.SP_KEY_UPDATE_DONE, true);
                edit.apply();
            }
        }.start();
    }
}
