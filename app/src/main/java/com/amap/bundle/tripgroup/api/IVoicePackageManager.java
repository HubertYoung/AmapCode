package com.amap.bundle.tripgroup.api;

import android.content.Context;
import android.content.Intent;
import com.autonavi.common.Callback;
import java.util.List;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IVoicePackageManager {
    public static final String ENTRANCE_RECORD_CUSTOMIZED_VOICES = "entrance_record_customized_voices";
    public static final String ENTRANCE_VOICE_SQUARE = "entrance_voice_square";
    public static final String NAVITTS_ENTER_TYPE_GLOBAL = "全局页";
    public static final String NAVITTS_ENTER_TYPE_GUIDE = "切换导游";
    public static final String NAVITTS_ENTER_TYPE_MINE = "我的页";
    public static final String NAVITTS_ENTER_TYPE_NAVI = "导航中";
    public static final String NAVITTS_ENTER_TYPE_OTHER = "其他";
    public static final String SHOW_TTS_FROM_KEY = "show_tts_from_key";
    public static final String SHOW_TTS_FROM_KEY_SCHEME_AUDIO_GUIDE = "AudioGuide";
    public static final String SHOW_TTS_FROM_KEY_SCHEME_TRIP = "Trip";
    public static final int SHOW_TTS_FROM_VALUE_MINE = 0;
    public static final int SHOW_TTS_FROM_VALUE_NAVI_BUS = 1002;
    public static final int SHOW_TTS_FROM_VALUE_NAVI_CAR = 1001;
    public static final int SHOW_TTS_FROM_VALUE_NAVI_FOOT = 1003;
    public static final int SHOW_TTS_FROM_VALUE_NAVI_SETTING = 1;
    public static final int SHOW_TTS_FROM_VALUE_SCHEME_AUDIO_GUIDE = 101;
    public static final int SHOW_TTS_FROM_VALUE_SCHEME_TRIP = 102;
    public static final int SHOW_TTS_FROM_VALUE_UNKNOWN = -1;
    public static final String SHOW_TTS_ORIENTATION = "show_tts_orientation";
    public static final String VOICE_PACKAGE_BBHX = "百变欢笑语音";
    public static final String VOICE_PACKAGE_BZNZY = "标准男中音";
    public static final String VOICE_PACKAGE_CLH = "超俪嗨语音";
    public static final String VOICE_PACKAGE_DGGDH = "动感广东话";
    public static final String VOICE_PACKAGE_GDG = "郭德纲语音";
    public static final String VOICE_PACKAGE_GXS = "高晓松语音";
    public static final String VOICE_PACKAGE_HJX = "黄健翔语音";
    public static final String VOICE_PACKAGE_HSDBH = "豪爽东北话";
    public static final String VOICE_PACKAGE_HXM_GZ = "黄晓明耿直语音";
    public static final String VOICE_PACKAGE_HXM_NX = "黄晓明暖心语音";
    public static final String VOICE_PACKAGE_KLHNH = "快乐湖南话";
    public static final String VOICE_PACKAGE_LYH = "罗永浩语音";
    public static final String VOICE_PACKAGE_LZL_COMMON = "林志玲语音";
    public static final String VOICE_PACKAGE_LZL_SEXY = "林志玲性感语音";
    public static final String VOICE_PACKAGE_MLSCH = "麻辣四川话";
    public static final String VOICE_PACKAGE_PSHNH = "朴实河南话";
    public static final String VOICE_PACKAGE_TXTWH = "贴心台湾话";
    public static final String VOICE_PACKAGE_WJK = "王俊凯语音";
    public static final String VOICE_PACKAGE_WY = "王源语音";
    public static final String VOICE_PACKAGE_XTX = "小甜心语音";
    public static final String VOICE_PACKAGE_YSCW = "央视春晚语音";
    public static final String VOICE_PACKAGE_YYP = "岳云鹏搞笑也专业语音";
    public static final String VOICE_PACKAGE_YYQX = "易烊千玺语音";
    public static final String VOICE_PACKAGE_ZGHSY = "中国好声音";
    public static final String VOICE_PACKAGE_ZXX = "周星星经典语音";

    void deal(bid bid, Intent intent);

    void destroy();

    String getCurrentCustomizedVoice();

    String getCurrentTtsFilePath();

    String getCurrentTtsImage();

    String getCurrentTtsName();

    String getCurrentTtsName2();

    String getCurrentTtsSubName();

    boolean getCustomVoiceState();

    List<String[]> getCustomizedVoices(Context context);

    List<String[]> getDownloadedVoiceList();

    List<String> getDownloadedVoiceNameList();

    String getNaviTtsUpdateVer();

    String getPlayType(String str);

    boolean hasNaviTTS();

    void initialization();

    boolean isNaviTtsNewFeature();

    boolean isNaviTtsNewVersion();

    boolean isVoiceInDownloading();

    void restoreDefaultTTS();

    void setCurrentCustomVoice(String str);

    void setCurrentTtsFileByName(String str, Callback callback);

    boolean setCurrentTtsFileByName(String str);

    void setCustomVoiceState(boolean z);

    boolean setDefaultTts(boolean z);

    void setIsUpgradeAe8TTSVersion(boolean z);
}
