package com.alibaba.wireless.security.open.initialize;

import android.content.Context;
import com.alibaba.wireless.security.framework.ISGPluginManager;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.initialize.IInitializeComponent.IInitFinishListener;

public class a implements IInitializeComponent {
    private b a;

    public a() {
        this.a = new b();
    }

    public a(String str) {
        this.a = new b(str);
    }

    public int a(Context context, String str, boolean z) throws SecException {
        return this.a.a(context, str, z, true);
    }

    public ISGPluginManager a() {
        return this.a.a();
    }

    public int initialize(Context context) throws SecException {
        return loadLibrarySync(context);
    }

    public void initializeAsync(Context context) {
        try {
            loadLibraryAsync(context);
        } catch (SecException e) {
            e.printStackTrace();
        }
    }

    public boolean isSoValid(Context context) throws SecException {
        return this.a.a(context);
    }

    public void loadLibraryAsync(Context context) throws SecException {
        loadLibraryAsync(context, null);
    }

    public void loadLibraryAsync(Context context, String str) throws SecException {
        this.a.b(context, str, true, true);
    }

    public int loadLibrarySync(Context context) throws SecException {
        return loadLibrarySync(context, null);
    }

    public int loadLibrarySync(Context context, String str) throws SecException {
        return this.a.a(context, str, true, true);
    }

    public void registerInitFinishListener(IInitFinishListener iInitFinishListener) throws SecException {
        this.a.a(iInitFinishListener);
    }

    public void unregisterInitFinishListener(IInitFinishListener iInitFinishListener) throws SecException {
        this.a.a(iInitFinishListener);
    }
}
