package com.alipay.mobile.nebulacore.dev.provider;

import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebulacore.dev.bugme.H5BugmeConsole;
import java.util.HashMap;

public class H5DebugConsolePool {
    private static H5DebugConsolePool b = null;
    private HashMap<String, H5BugmeConsole> a = new HashMap<>();

    private H5DebugConsolePool() {
    }

    public static H5DebugConsolePool getInstance() {
        if (b == null) {
            b = new H5DebugConsolePool();
        }
        return b;
    }

    public H5BugmeConsole addOrGetConsole(H5Page h5Page) {
        String viewId = H5BugmeIdGenerator.getBugmeViewId(h5Page);
        H5BugmeConsole console = this.a.get(viewId);
        if (console != null) {
            return console;
        }
        H5BugmeConsole console2 = new H5BugmeConsole(h5Page.getWebView(), h5Page.getContext().getContext());
        this.a.put(viewId, console2);
        return console2;
    }

    public H5BugmeConsole getConsole(String viewId) {
        return this.a.get(viewId);
    }

    public void release(String viewId) {
        H5BugmeConsole console = this.a.remove(viewId);
        if (console != null) {
            console.release();
        }
    }
}
