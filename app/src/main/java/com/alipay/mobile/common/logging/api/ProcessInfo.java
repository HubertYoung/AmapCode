package com.alipay.mobile.common.logging.api;

import android.net.Uri;
import android.os.Bundle;
import java.util.Map;
import java.util.Set;

public interface ProcessInfo {
    public static final String ALIAS_EXT = "ext";
    public static final String ALIAS_LITE = "lite";
    public static final String ALIAS_MAIN = "main";
    public static final String ALIAS_PUSH = "push";
    public static final String ALIAS_TOOLS = "tools";
    public static final String ALIAS_UNKNOWN = "unknown";
    public static final String RECORD_ACTIVITY = "ActivityClientRecord";
    public static final String RECORD_APP_BIND = "AppBindData";
    public static final String RECORD_BACKUP_AGENT = "CreateBackupAgentData";
    public static final String RECORD_NEW_INTENT = "NewIntentData";
    public static final String RECORD_PROVIDER = "ProviderClientRecord";
    public static final String RECORD_RECEIVER = "ReceiverData";
    public static final String RECORD_SERVICE_ARGS = "ServiceArgsData";
    public static final String RECORD_SERVICE_BIND = "BindServiceData";
    public static final String RECORD_SERVICE_CREATE = "CreateServiceData";
    public static final String SR_ACTION_NAME = "ActionName";
    public static final String SR_APP_ID = "TARGETAPPID";
    public static final String SR_COMPONENT_NAME = "ComponentName";
    public static final String SR_RECORD_TYPE = "RecordType";
    public static final String SR_TO_STRING = "toString";

    int getExtProcessId();

    String getExtProcessName();

    String getExtProcessTag();

    int getMainProcessId();

    String getMainProcessName();

    String getMainProcessTag();

    String getPackageName();

    String getProcessAlias();

    int getProcessId();

    int getProcessIdByName(String str);

    Set<Integer> getProcessIdsByName(String str);

    String getProcessName();

    String getProcessNameById(int i);

    String getProcessTag();

    int getPushProcessId();

    String getPushProcessName();

    String getPushProcessTag();

    Bundle getStartupBundle();

    Uri getStartupData();

    Map<String, String> getStartupReason();

    int getThreadId();

    int getToolsProcessId();

    String getToolsProcessName();

    String getToolsProcessTag();

    int getUserId();

    boolean isExtProcess();

    boolean isExtProcessExist();

    boolean isLiteProcess();

    boolean isMainProcess();

    boolean isMainProcessExist();

    boolean isPushProcess();

    boolean isPushProcessExist();

    boolean isToolsProcess();

    boolean isToolsProcessExist();
}
