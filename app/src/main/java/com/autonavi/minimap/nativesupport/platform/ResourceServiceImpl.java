package com.autonavi.minimap.nativesupport.platform;

import android.content.res.AssetManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ackor.ackorplatform.IResourceService;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class ResourceServiceImpl implements IResourceService {
    ResourceServiceImpl() {
    }

    public boolean copyAssetResource(String str, String str2) {
        InputStream inputStream;
        AssetManager assets = AMapAppGlobal.getApplication().getApplicationContext().getAssets();
        File file = new File(str2, str);
        if (file.exists()) {
            file.delete();
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        boolean z = false;
        try {
            inputStream = assets.open(str);
            if (inputStream != null) {
                try {
                    byte[] bArr = new byte[1024];
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    while (true) {
                        try {
                            int read = inputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream2.write(bArr, 0, read);
                        } catch (IOException unused) {
                            fileOutputStream = fileOutputStream2;
                        } catch (Throwable th) {
                            th = th;
                            fileOutputStream = fileOutputStream2;
                            ahe.a((Closeable) fileOutputStream);
                            ahe.a((Closeable) inputStream);
                            throw th;
                        }
                    }
                    fileOutputStream2.flush();
                    fileOutputStream = fileOutputStream2;
                    z = true;
                } catch (IOException unused2) {
                } catch (Throwable th2) {
                    th = th2;
                    ahe.a((Closeable) fileOutputStream);
                    ahe.a((Closeable) inputStream);
                    throw th;
                }
            }
        } catch (IOException unused3) {
            inputStream = null;
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            ahe.a((Closeable) fileOutputStream);
            ahe.a((Closeable) inputStream);
            throw th;
        }
        ahe.a((Closeable) fileOutputStream);
        ahe.a((Closeable) inputStream);
        return z;
    }
}
