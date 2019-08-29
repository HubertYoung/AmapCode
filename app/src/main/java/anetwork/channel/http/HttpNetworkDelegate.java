package anetwork.channel.http;

import android.content.Context;
import anetwork.channel.unified.UnifiedNetworkDelegate;

public class HttpNetworkDelegate extends UnifiedNetworkDelegate {
    public HttpNetworkDelegate(Context context) {
        super(context);
        this.type = 0;
    }
}
