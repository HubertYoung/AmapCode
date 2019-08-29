package com.google.zxing.pdf417.encoder;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.InputDeviceCompat;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.alibaba.baichuan.android.trade.constants.MessageConstants;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilesdk.socketcraft.api.WSContextConstant;
import com.alipay.mobile.antui.clickspan.BaseClickableSpan;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.jni.ae.pos.SpeedState;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.minimap.route.sharebike.model.EndBill;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.google.zxing.WriterException;
import com.google.zxing.pdf417.PDF417Common;
import com.iflytek.tts.TtsService.Tts;
import com.standardar.common.Util;
import com.taobao.accs.common.Constants;
import com.taobao.accs.flowcontrol.FlowControl;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;
import tv.danmaku.ijk.media.encode.VideoRecordParameters;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

final class PDF417ErrorCorrection {
    private static final int[][] EC_COEFFICIENTS = {new int[]{27, 917}, new int[]{522, 568, 723, MessageConstants.PAY_SDK_FAILED}, new int[]{237, SecExceptionCode.SEC_ERROR_STA_NO_MEMORY, 436, 284, 646, 653, 428, 379}, new int[]{274, 562, 232, 755, SecExceptionCode.SEC_ERROR_DYN_STORE_UNKNOWN_ERROR, 524, 801, 132, 295, 116, 442, 428, 295, 42, SpeedState.ENO_DEFINE, 65}, new int[]{361, 575, 922, 525, SpeedState.ENO_DEFINE, 586, 640, 321, 536, 742, 677, 742, 687, 284, 193, 517, LZMA_Base.kMatchMaxLen, 494, 263, 147, 593, 800, 571, 320, SecExceptionCode.SEC_ERROR_PKG_VALID_NO_MEMORY, 133, 231, 390, 685, 330, 63, H5ErrorCode.HTTP_GONE}, new int[]{539, 422, 6, 93, 862, 771, 453, 106, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE_DATA, 287, 107, 505, 733, 877, 381, SecExceptionCode.SEC_ERROR_SIGNATURE_ILLEGEL_KEY, 723, 476, 462, 172, 430, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE, 858, 822, 543, 376, MessageCode.MSG_BLE_NOT_SUPPORT, 400, 672, 762, 283, 184, 440, 35, 519, 31, 460, 594, 225, 535, 517, 352, SecExceptionCode.SEC_ERROR_SIGNATURE_CONFUSE_FAILED, 158, 651, 201, 488, 502, 648, 733, MessageCode.MSG_LBS_INSUFFICIENT_PRIVILEGES, 83, 404, 97, 280, 771, 840, 629, 4, 381, 843, 623, 264, 543}, new int[]{521, SecExceptionCode.SEC_ERROR_STA_INVALID_ENCRYPTED_DATA, 864, 547, 858, 580, 296, 379, 53, 779, 897, 444, 400, 925, 749, 415, 822, 93, 217, 208, PDF417Common.MAX_CODEWORDS_IN_BARCODE, 244, 583, 620, 246, 148, 447, 631, 292, SecExceptionCode.SEC_ERROR_UMID_TIME_OUT, 490, SecExceptionCode.SEC_ERROR_STA_KEY_ENC_INVALID_ENCRYPTED_DATA, 516, Tts.TTS_STATE_INVALID_DATA, 457, SecExceptionCode.SEC_ERROR_UMID_NO_NETWORK_INIT, 594, 723, 674, 292, LZMA_Base.kNumLenSymbols, 96, 684, 432, 686, SecExceptionCode.SEC_ERROR_SIGNATURE_NO_SEEDSECRET, 860, 569, 193, 219, 129, 186, 236, 287, 192, 775, 278, 173, 40, 379, 712, 463, 646, 776, 171, 491, 297, 763, 156, 732, 95, 270, 447, 90, MessageCode.MSG_SENSOR_MISSING, 48, 228, 821, MessageConstants.PAY_COMMON_ERROR, 898, 784, 663, 627, 378, 382, 262, 380, 602, 754, 336, 89, SecExceptionCode.SEC_ERROR_SIGNATURE_BLOWFISH_FAILED, 87, 432, 670, 616, 157, 374, FavoritesPointFragment.REQUEST_COMPNAY, 726, 600, 269, 375, 898, 845, 454, 354, 130, 814, 587, 804, 34, 211, 330, 539, 297, 827, 865, 37, 517, 834, 315, 550, 86, 801, 4, 108, 539}, new int[]{524, 894, 75, 766, 882, 857, 74, 204, 82, 586, 708, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, SecExceptionCode.SEC_ERROR_UMID_SERVER_RESP_INVALID, 786, DumpSegment.ANDROID_ROOT_FINALIZING, 720, 858, 194, SecExceptionCode.SEC_ERROR_STA_DECRYPT_MISMATCH_KEY_DATA, 913, 275, 190, 375, 850, 438, 733, 194, 280, 201, 280, 828, 757, 710, 814, 919, 89, 68, 569, 11, 204, 796, SecExceptionCode.SEC_ERROR_SIGNATURE_CONFUSE_FAILED, VideoRecordParameters.HD_HEIGHT_16_9, 913, 801, 700, SecExceptionCode.SEC_ERROR_STA_KEY_ENC_UNKNOWN_ERROR, DumpSegment.ANDROID_ROOT_INTERNED_STRING, 439, Util.TOF_TAG, 592, 668, 353, 859, 370, 694, PullToRefreshBase.SMOOTH_SCROLL_LONG_DURATION_MS, 240, MessageCode.MSG_ONLINE_BUILDING_CHANGED, 257, 284, 549, 209, 884, 315, 70, 329, 793, 490, 274, 877, EndBill.UNKNOWN_END_ORDER_FAILED, 749, 812, 684, 461, 334, 376, 849, 521, SecExceptionCode.SEC_ERROR_STA_ILLEGEL_KEY, 291, SecExceptionCode.SEC_ERROR_PKG_VALID_NO_MEMORY, 712, 19, 358, SecExceptionCode.SEC_ERROR_STA_UNKNOWN_ERROR, SecExceptionCode.SEC_ERROR_UMID_TIME_OUT, 103, MessageCode.MSG_BLE_NOT_SUPPORT, 51, 8, 517, 225, 289, 470, 637, 731, 66, 255, 917, 269, 463, 830, 730, 433, 848, 585, 136, 538, SecExceptionCode.SEC_ERROR_UMID_ENVIRONMENT_CHANGED, 90, 2, 290, 743, SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR, 655, 903, 329, 49, 802, 580, 355, 588, 188, 462, 10, 134, 628, 320, 479, 130, 739, 71, 263, 318, 374, 601, 192, SecExceptionCode.SEC_ERROR_SIGNATURE_CONFUSE_FAILED, DumpSegment.ANDROID_ROOT_JNI_MONITOR, 673, 687, 234, 722, 384, 177, 752, SecExceptionCode.SEC_ERROR_SIGNATURE_DATA_FILE_MISMATCH, 640, 455, 193, 689, 707, 805, 641, 48, 60, 732, 621, 895, FFmpegSessionConfig.VIDEO_SOFTENCODE_W, 261, 852, 655, SecExceptionCode.SEC_ERROR_STA_NO_SUCH_INDEX, 697, 755, 756, 60, 231, 773, 434, FlowControl.STATUS_FLOW_CTRL_CUR, 726, 528, 503, 118, 49, 795, 32, 144, 500, 238, 836, 394, 280, 566, 319, 9, 647, 550, 73, 914, 342, TransportMediator.KEYCODE_MEDIA_PLAY, 32, 681, 331, 792, 620, 60, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE, 441, 180, 791, 893, 754, SecExceptionCode.SEC_ERROR_SIGNATURE_CONFUSE_FAILED, 383, 228, 749, 760, MessageCode.MSG_SERVER_ERROR, 54, 297, 134, 54, 834, SecExceptionCode.SEC_ERROR_STA_STORE_UNKNOWN_ERROR, 922, 191, 910, 532, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE, 829, WSContextConstant.HANDSHAKE_SEND_SIZE, 20, 167, 29, 872, 449, 83, 402, 41, 656, 505, 579, 481, 173, 404, 251, 688, 95, 497, 555, 642, 543, SecExceptionCode.SEC_ERROR_STA_ILLEGEL_KEY, 159, 924, 558, 648, 55, 497, 10}, new int[]{352, 77, 373, 504, 35, SecExceptionCode.SEC_ERROR_DYN_STORE_UNKNOWN_ERROR, 428, 207, H5ErrorCode.HTTP_CONFLICT, 574, 118, 498, 285, 380, BaseClickableSpan.CLICK_ENABLE_TIME, 492, 197, 265, 920, 155, 914, SecExceptionCode.SEC_ERROR_STA_STORE_UNKNOWN_ERROR, 229, 643, 294, 871, SecExceptionCode.SEC_ERROR_STA_KEY_NOT_EXISTED, 88, 87, 193, 352, 781, 846, 75, 327, 520, 435, 543, 203, 666, 249, 346, 781, 621, 640, 268, 794, 534, 539, 781, 408, 390, 644, 102, 476, SecExceptionCode.SEC_ERROR_DYN_ENC_UNKNOWN_ERROR, 290, 632, 545, 37, 858, 916, 552, 41, 542, 289, 122, LZMA_Base.kNumLenSymbols, 383, 800, 485, 98, 752, 472, 761, 107, 784, 860, 658, 741, 290, 204, 681, 407, 855, 85, 99, 62, 482, 180, 20, 297, 451, 593, 913, DumpSegment.ANDROID_ROOT_JNI_MONITOR, MessageConstants.PAY_COMMON_ERROR, 684, 287, 536, 561, 76, 653, SecExceptionCode.SEC_ERROR_PKG_VALID_UNKNOWN_ERR, 729, 567, 744, 390, InputDeviceCompat.SOURCE_DPAD, 192, 516, Tts.TTS_STATE_INVALID_DATA, 240, 518, 794, 395, 768, 848, 51, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE_DATA, 384, 168, 190, 826, 328, 596, 786, 303, 570, 381, 415, 641, 156, 237, 151, 429, 531, 207, 676, 710, 89, 168, 304, 402, 40, 708, 575, EndBill.UNKNOWN_END_ORDER_FAILED, 864, 229, 65, 861, 841, 512, EndBill.ORDER_PAYMENT_FAILED, 477, Constants.SDK_VERSION_CODE, 92, 358, 785, 288, 357, 850, 836, 827, 736, 707, 94, 8, 494, 114, 521, 2, SecExceptionCode.SEC_ERROR_DYN_ENC_UNKNOWN_ERROR, 851, 543, 152, 729, 771, 95, 248, 361, IjkMediaMeta.FF_PROFILE_H264_CONSTRAINED_BASELINE, 323, 856, 797, 289, 51, 684, 466, 533, 820, 669, 45, 902, 452, 167, 342, 244, 173, 35, 463, 651, 51, SecExceptionCode.SEC_ERROR_SIGNATRUE_UNKNOWN, 591, 452, IjkMediaMeta.FF_PROFILE_H264_CONSTRAINED_BASELINE, 37, SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA, 298, 332, 552, 43, 427, 119, 662, 777, 475, 850, 764, 364, IjkMediaMeta.FF_PROFILE_H264_CONSTRAINED_BASELINE, 911, 283, 711, 472, FlowControl.STATUS_FLOW_CTRL_ALL, FavoritesPointFragment.REQUEST_TAG_SELECT, 288, 594, 394, MessageCode.MSG_BLE_NOT_SUPPORT, 327, 589, 777, SecExceptionCode.SEC_ERROR_SIGNATRUE_UNKNOWN, 688, 43, 408, 842, 383, MessageCode.MSG_LBS_ERROR, 521, 560, 644, MessageCode.MSG_LBS_INVALID_USER_KEY, 559, 62, 145, 873, 663, 713, 159, 672, 729, 624, 59, 193, Util.SLAM_TAG, 158, 209, 563, 564, 343, 693, 109, SecExceptionCode.SEC_ERROR_SIGNATURE_NO_DATA_FILE, 563, 365, 181, 772, 677, SecExceptionCode.SEC_ERROR_STA_INVALID_ENCRYPTED_DATA, 248, 353, 708, H5ErrorCode.HTTP_GONE, 579, 870, 617, 841, 632, 860, 289, 536, 35, 777, 618, 586, 424, 833, 77, 597, 346, 269, 757, 632, 695, 751, 331, 247, 184, 45, 787, 680, 18, 66, 407, 369, 54, 492, 228, SecExceptionCode.SEC_ERROR_SIGNATURE_ATLAS_KEY_NOT_EXSITED, 830, 922, 437, 519, 644, SecExceptionCode.SEC_ERROR_UMID_SERVER_RESP_INVALID, 789, FlowControl.STATUS_FLOW_CTRL_ALL, SecExceptionCode.SEC_ERROR_STA_INCORRECT_DATA_FILE_DATA, 441, 207, 300, 892, 827, DumpSegment.ANDROID_ROOT_VM_INTERNAL, 537, 381, 662, InputDeviceCompat.SOURCE_DPAD, 56, 252, 341, FavoritesPointFragment.REQUEST_COMPNAY, 797, 838, 837, 720, 224, SecExceptionCode.SEC_ERROR_STA_ILLEGEL_KEY, 631, 61, 87, 560, SecExceptionCode.SEC_ERROR_STA_INVALID_ENCRYPTED_DATA, 756, 665, 397, MessageConstants.PAY_COMMON_ERROR, 851, SecExceptionCode.SEC_ERROR_STA_NO_SUCH_INDEX, 473, 795, 378, 31, 647, 915, 459, 806, 590, 731, 425, MessageCode.MSG_ONLINE_BUILDING_CHANGED, 548, 249, 321, 881, SecExceptionCode.SEC_ERROR_SIGNATRUE_UNKNOWN, 535, 673, 782, 210, 815, SecExceptionCode.SEC_ERROR_UMID_SERVER_RESP_INVALID, 303, 843, 922, 281, 73, 469, 791, Configuration.INTERVAL_MAX, EndBill.UNKNOWN_END_ORDER_FAILED, 498, SecExceptionCode.SEC_ERROR_STA_NO_MEMORY, 155, 422, SecExceptionCode.SEC_ERROR_UMID_NO_NETWORK_INIT, 817, 187, 62, 16, 425, 535, 336, 286, 437, 375, LZMA_Base.kMatchMaxLen, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE_DATA, 296, 183, 923, 116, 667, 751, 353, 62, 366, 691, 379, 687, 842, 37, 357, 720, 742, 330, 5, 39, 923, SecExceptionCode.SEC_ERROR_STA_DECRYPT_MISMATCH_KEY_DATA, 424, FavoritesPointFragment.REQUEST_COMPNAY, 749, 321, 54, 669, 316, 342, SecExceptionCode.SEC_ERROR_STA_STORE_UNKNOWN_ERROR, 534, 105, 667, 488, 640, 672, 576, VideoRecordParameters.HD_HEIGHT_16_9, 316, 486, MessageCode.MSG_LBS_ERROR, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE_DATA, 46, 656, 447, 171, 616, 464, 190, 531, 297, 321, 762, 752, 533, 175, 134, 14, 381, 433, MessageCode.MSG_LBS_INSUFFICIENT_PRIVILEGES, 45, 111, 20, 596, 284, 736, DumpSegment.ANDROID_ROOT_FINALIZING, 646, H5ErrorCode.HTTP_LENGTH_REQUIRED, 877, 669, DumpSegment.ANDROID_ROOT_VM_INTERNAL, 919, 45, 780, 407, EndBill.ORDER_PAYMENT_FAILED, 332, SecExceptionCode.SEC_ERROR_PKG_VALID_UNKNOWN_ERR, 165, 726, 600, PullToRefreshBase.SMOOTH_SCROLL_LONG_DURATION_MS, 498, 655, 357, 752, 768, 223, 849, 647, 63, SecExceptionCode.SEC_ERROR_STA_INVALID_ENCRYPTED_DATA, 863, 251, 366, 304, 282, 738, 675, H5ErrorCode.HTTP_GONE, 389, 244, 31, 121, 303, 263}};

    private PDF417ErrorCorrection() {
    }

    static int getErrorCorrectionCodewordCount(int i) {
        if (i >= 0 && i <= 8) {
            return 1 << (i + 1);
        }
        throw new IllegalArgumentException("Error correction level must be between 0 and 8!");
    }

    static int getRecommendedMinimumErrorCorrectionLevel(int i) throws WriterException {
        if (i <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        } else if (i <= 40) {
            return 2;
        } else {
            if (i <= 160) {
                return 3;
            }
            if (i <= 320) {
                return 4;
            }
            if (i <= 863) {
                return 5;
            }
            throw new WriterException((String) "No recommendation possible");
        }
    }

    static String generateErrorCorrection(CharSequence charSequence, int i) {
        int errorCorrectionCodewordCount = getErrorCorrectionCodewordCount(i);
        char[] cArr = new char[errorCorrectionCodewordCount];
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = errorCorrectionCodewordCount - 1;
            int charAt = (charSequence.charAt(i2) + cArr[i3]) % PDF417Common.NUMBER_OF_CODEWORDS;
            while (i3 > 0) {
                cArr[i3] = (char) ((cArr[i3 - 1] + (929 - ((EC_COEFFICIENTS[i][i3] * charAt) % PDF417Common.NUMBER_OF_CODEWORDS))) % PDF417Common.NUMBER_OF_CODEWORDS);
                i3--;
            }
            cArr[0] = (char) ((929 - ((charAt * EC_COEFFICIENTS[i][0]) % PDF417Common.NUMBER_OF_CODEWORDS)) % PDF417Common.NUMBER_OF_CODEWORDS);
        }
        StringBuilder sb = new StringBuilder(errorCorrectionCodewordCount);
        for (int i4 = errorCorrectionCodewordCount - 1; i4 >= 0; i4--) {
            if (cArr[i4] != 0) {
                cArr[i4] = (char) (929 - cArr[i4]);
            }
            sb.append(cArr[i4]);
        }
        return sb.toString();
    }
}
