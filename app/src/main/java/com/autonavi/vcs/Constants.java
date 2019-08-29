package com.autonavi.vcs;

import com.autonavi.minimap.R;

public final class Constants {
    public static final int a = R.raw.vui_wakeup_ring;

    public enum AmapNuiLogLevel {
        LOG_LEVEL_VERBOSE,
        LOG_LEVEL_DEBUG,
        LOG_LEVEL_INFO,
        LOG_LEVEL_WARNING,
        LOG_LEVEL_ERROR,
        LOG_LEVEL_NONE
    }

    public enum AmapNuiNetworkState {
        STATE_2G,
        STATE_3G,
        STATE_4G,
        STATE_MOBILE_FURTHER,
        STATE_WIFI
    }

    public enum AmapPhoneType {
        TYPE_DEFAULT,
        TYPE_XIAOMI_MX2,
        TYPE_MAX
    }

    public enum AudioState {
        STATE_OPEN,
        STATE_PAUSE,
        STATE_CLOSE
    }

    public enum NuiEvent {
        EVENT_VAD_START,
        EVENT_VAD_TIMEOUT,
        EVENT_VAD_END,
        EVENT_WUW,
        EVENT_ASR_RESULT,
        EVENT_ASR_ERROR
    }

    public enum VUIStatus {
        VUIStatus_Silent,
        VUIStatus_Listening,
        VUIStatus_Wakeup,
        VUIStatus_SpeechRecognizing,
        VUIStatus_SpeechRecognition,
        VUIStatus_SpeechRecognizeFail,
        VUIStatus_SpeechTranslating,
        VUIStatus_SpeechTranslated,
        VUIStatus_SpeechTranslateFail,
        VUIStatus_ExecuteCommand,
        VUIStatus_CommandSuccess,
        VUIStatus_CommandFail,
        VUIStatus_RecognizingWaiting,
        VUIStatus_AutoListenSilent,
        VUIStatus_Error,
        VUIStatus_AudioPreparing,
        VUIStatus_VoiceUnsupport
    }

    public enum WuwCredibility {
        REJECT(-1),
        TRUSTED(0),
        NEED_DOUBLE_CHECK(1);
        
        private int code;

        public final int getCode() {
            return this.code;
        }

        private WuwCredibility(int i) {
            this.code = i;
        }

        public static WuwCredibility fromInt(int i) {
            switch (i) {
                case -1:
                    return REJECT;
                case 0:
                    return TRUSTED;
                case 1:
                    return NEED_DOUBLE_CHECK;
                default:
                    return REJECT;
            }
        }
    }

    public enum WuwType {
        TYPE_UNKNOWN(-1),
        TYPE_MAIN(0),
        TYPE_PREFIX(1),
        TYPE_ACTION(2);
        
        private int code;

        public final int getCode() {
            return this.code;
        }

        private WuwType(int i) {
            this.code = i;
        }

        public static WuwType fromInt(int i) {
            switch (i) {
                case -1:
                    return TYPE_UNKNOWN;
                case 0:
                    return TYPE_MAIN;
                case 1:
                    return TYPE_PREFIX;
                case 2:
                    return TYPE_ACTION;
                default:
                    return TYPE_UNKNOWN;
            }
        }
    }
}
