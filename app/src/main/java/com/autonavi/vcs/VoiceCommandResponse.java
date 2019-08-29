package com.autonavi.vcs;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMemberNames;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepClassMemberNames
@Keep
@KeepName
public class VoiceCommandResponse {
    public int autoListen;
    public String errText;
    public String keywords;
    public String operate;
    public String paramStr;
    public String tipText;
    public String type;
}
