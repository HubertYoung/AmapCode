package com.autonavi.minimap.ajx3.scheme;

import android.net.Uri;
import com.autonavi.minimap.ajx3.Ajx3Page;

public interface IRedesignPageLoader {
    Class<? extends Ajx3Page> loadPage(Uri uri);
}
