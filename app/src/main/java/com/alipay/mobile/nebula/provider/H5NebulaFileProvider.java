package com.alipay.mobile.nebula.provider;

import android.net.Uri;
import java.io.File;

public interface H5NebulaFileProvider {
    Uri getUriForFile(File file);
}
