package com.taobao.accs.client;

import android.content.Context;
import com.taobao.accs.ChannelService;
import com.taobao.accs.IProcessName;
import com.taobao.accs.client.AccsConfig.ACCS_GROUP;
import com.taobao.accs.data.Message;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.OrangeAdapter;

public class GlobalConfig {
    public static boolean enableCookie = true;
    public static ACCS_GROUP mGroup = ACCS_GROUP.TAOBAO;

    public static void setControlFrameMaxRetry(int i) {
        Message.CONTROL_MAX_RETRY_TIMES = i;
    }

    public static void setMainProcessName(String str) {
        AdapterGlobalClientInfo.mMainProcessName = str;
    }

    public static void setChannelProcessName(String str) {
        AdapterGlobalClientInfo.mChannelProcessName = str;
    }

    public static void setCurrProcessNameImpl(IProcessName iProcessName) {
        AdapterGlobalClientInfo.mProcessNameImpl = iProcessName;
    }

    public static void setChannelReuse(boolean z, ACCS_GROUP accs_group) {
        GlobalClientInfo.mSupprotElection = z;
        mGroup = accs_group;
    }

    public static void setEnableForground(Context context, boolean z) {
        int i = 0;
        ALog.i("GlobalConfig", "setEnableForground", "enable", Boolean.valueOf(z));
        if (z) {
            i = 24;
        }
        OrangeAdapter.saveConfigToSP(context, (String) ChannelService.SUPPORT_FOREGROUND_VERSION_KEY, i);
    }
}
