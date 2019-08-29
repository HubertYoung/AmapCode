package com.alipay.mobile.quinox.utils;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

public class ProcessInfo {
    private static final String ALIAS_EXT = "ext";
    private static final String ALIAS_ISOLATE = "isolate";
    private static final String ALIAS_MAIN = "main";
    private static final String ALIAS_PUSH = "push";
    private static final String ALIAS_SSS = "sss";
    private static final String ALIAS_TOOLS = "tools";
    private static final String ALIAS_UNKNOWN = "unknown";
    private static final String TAG = "quinox.ProcessInfo";
    private boolean mIsExtProcess = false;
    private boolean mIsIsolatedProcess = false;
    private boolean mIsLiteProcess = false;
    private boolean mIsMainProcess = false;
    private boolean mIsPushProcess = false;
    private boolean mIsSSSProcess = false;
    private boolean mIsToolsProcess = false;
    private String mProcessAlias = "";
    private String mProcessName = "";

    public ProcessInfo(Context context, String str) {
        String packageName = context.getPackageName();
        this.mProcessName = str;
        this.mIsMainProcess = packageName.equalsIgnoreCase(this.mProcessName);
        if (this.mIsMainProcess) {
            this.mProcessAlias = "main";
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(packageName);
        sb.append(':');
        sb.append("push");
        this.mIsPushProcess = sb.toString().equalsIgnoreCase(this.mProcessName);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(packageName);
        sb2.append(':');
        sb2.append("tools");
        this.mIsToolsProcess = sb2.toString().equalsIgnoreCase(this.mProcessName);
        StringBuilder sb3 = new StringBuilder();
        sb3.append(packageName);
        sb3.append(':');
        sb3.append("ext");
        this.mIsExtProcess = sb3.toString().equals(this.mProcessName);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(packageName);
        sb4.append(':');
        sb4.append(ALIAS_SSS);
        this.mIsSSSProcess = sb4.toString().equals(this.mProcessName);
        this.mIsLiteProcess = LiteProcessInfo.g(context).isLiteProcess(this.mProcessName);
        this.mIsIsolatedProcess = isCurrentProcessIsolated();
        if (this.mIsMainProcess) {
            this.mProcessAlias = "main";
        } else if (this.mIsPushProcess) {
            this.mProcessAlias = "push";
        } else if (this.mIsToolsProcess) {
            this.mProcessAlias = "tools";
        } else if (this.mIsExtProcess) {
            this.mProcessAlias = "ext";
        } else if (this.mIsSSSProcess) {
            this.mProcessAlias = ALIAS_SSS;
        } else if (this.mIsIsolatedProcess) {
            this.mProcessAlias = ALIAS_ISOLATE;
        } else if (this.mIsLiteProcess) {
            this.mProcessAlias = LiteProcessInfo.g(context).getProcessAlias();
        } else {
            new StringBuilder("unknown process: ").append(this.mProcessName);
            if (TextUtils.isEmpty(this.mProcessName)) {
                this.mProcessAlias = "unknown";
                return;
            }
            String str2 = this.mProcessName;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(packageName);
            sb5.append(':');
            this.mProcessAlias = str2.replace(sb5.toString(), "");
        }
    }

    public static boolean isCurrentProcessIsolated() {
        int myUid = Process.myUid() % 100000;
        return myUid >= 99000 && myUid <= 99999;
    }

    public String getProcessName() {
        return this.mProcessName;
    }

    public String getProcessAlias() {
        return this.mProcessAlias;
    }

    public boolean isMainProcess() {
        return this.mIsMainProcess;
    }

    public boolean isPushProcess() {
        return this.mIsPushProcess;
    }

    public boolean isToolsProcess() {
        return this.mIsToolsProcess;
    }

    public boolean isLiteProcess() {
        return this.mIsLiteProcess;
    }

    public boolean isExtProcess() {
        return this.mIsExtProcess;
    }

    public boolean isSSSProcess() {
        return this.mIsSSSProcess;
    }

    public boolean isIsolatedProcess() {
        return this.mIsIsolatedProcess;
    }
}
