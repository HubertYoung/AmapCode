package com.google.zxing.maxicode.decoder;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.InputDeviceCompat;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.alibaba.baichuan.android.trade.constants.MessageConstants;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilesdk.socketcraft.api.WSContextConstant;
import com.alipay.mobile.antui.clickspan.BaseClickableSpan;
import com.alipay.mobile.common.nbnet.api.NBNetStatus;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.jni.ae.pos.SpeedState;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.sevenzip.LZMA_Base;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.route.sharebike.model.EndBill;
import com.autonavi.minimap.route.sharebike.model.ScanQrcode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.google.zxing.common.BitMatrix;
import com.iflytek.tts.TtsService.Tts;
import com.sina.weibo.sdk.constant.WBConstants;
import com.standardar.common.Util;
import com.taobao.accs.antibrush.AntiBrush;
import com.taobao.accs.common.Constants;
import com.taobao.accs.flowcontrol.FlowControl;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;
import tv.danmaku.ijk.media.encode.VideoRecordParameters;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

final class BitMatrixParser {
    private static final int[][] BITNR;
    private final BitMatrix bitMatrix;

    static {
        int[] iArr = new int[30];
        iArr[0] = 481;
        iArr[1] = 480;
        iArr[2] = 475;
        iArr[3] = 474;
        iArr[4] = 469;
        iArr[5] = 468;
        iArr[6] = 48;
        iArr[7] = -2;
        iArr[8] = 30;
        iArr[9] = -3;
        iArr[10] = -3;
        iArr[11] = -3;
        iArr[12] = -3;
        iArr[13] = -3;
        iArr[14] = -3;
        iArr[15] = -3;
        iArr[16] = -3;
        iArr[17] = -3;
        iArr[18] = -3;
        iArr[20] = 53;
        iArr[21] = 52;
        iArr[22] = 463;
        iArr[23] = 462;
        iArr[24] = 457;
        iArr[25] = 456;
        iArr[26] = 451;
        iArr[27] = 450;
        iArr[28] = 837;
        iArr[29] = -3;
        BITNR = new int[][]{new int[]{121, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 127, TransportMediator.KEYCODE_MEDIA_PLAY, 133, 132, DumpSegment.ANDROID_ROOT_DEBUGGER, DumpSegment.ANDROID_ROOT_FINALIZING, 145, 144, 151, 150, 157, 156, EndBill.END_ORDER_FAILED, EndBill.UNKNOWN_END_ORDER_FAILED, 169, 168, 175, 174, 181, 180, 187, 186, 193, 192, SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR, 198, -2, -2}, new int[]{123, 122, 129, 128, 135, 134, DumpSegment.ANDROID_ROOT_VM_INTERNAL, 140, 147, 146, 153, 152, 159, 158, 165, EndBill.ORDER_PAYMENT_FAILED, 171, 170, 177, SpeedState.ENO_DEFINE, 183, 182, WSContextConstant.HANDSHAKE_SEND_SIZE, 188, DumpSegment.ANDROID_PRIMITIVE_ARRAY_NODATA_DUMP, 194, 201, 200, 816, -3}, new int[]{125, SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA, 131, 130, DumpSegment.ANDROID_ROOT_INTERNED_STRING, 136, 143, DumpSegment.ANDROID_ROOT_JNI_MONITOR, 149, 148, 155, ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE, ScanQrcode.NOT_CP_RESPONSE_CODE, 160, 167, 166, 173, 172, 179, 178, WSContextConstant.HANDSHAKE_RECEIVE_SIZE, 184, 191, 190, 197, 196, 203, 202, 818, 817}, new int[]{283, 282, 277, 276, 271, 270, 265, 264, Tts.TTS_STATE_CREATED, Tts.TTS_STATE_INVALID_DATA, 253, 252, 247, 246, FavoritesPointFragment.REQUEST_HOME, 240, 235, 234, 229, 228, 223, 222, 217, MessageCode.MSG_ONLINE_BUILDING_CHANGED, 211, 210, 205, 204, 819, -3}, new int[]{285, 284, 279, 278, LZMA_Base.kMatchMaxLen, LZMA_Base.kNumLenSymbols, 267, 266, 261, Tts.TTS_STATE_DESTROY, 255, DumpSegment.ANDROID_HEAP_DUMP_INFO, 249, 248, FavoritesPointFragment.REQUEST_EDIT_POINT, FavoritesPointFragment.REQUEST_COMPNAY, 237, 236, 231, 230, 225, 224, 219, 218, MessageCode.MSG_SERVER_ERROR, 212, 207, 206, 821, 820}, new int[]{287, 286, 281, 280, 275, 274, 269, 268, 263, 262, 257, 256, 251, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, FavoritesPointFragment.REQUEST_TAG_SELECT, 244, 239, 238, 233, 232, 227, 226, Constants.SDK_VERSION_CODE, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, MessageCode.MSG_ONLINE_BUILDING_LOCATED, MessageCode.MSG_NLP_RESPONSED, 209, 208, 822, -3}, new int[]{289, 288, 295, 294, 301, 300, SecExceptionCode.SEC_ERROR_STA_ILLEGEL_KEY, SecExceptionCode.SEC_ERROR_STA_KEY_NOT_EXISTED, 313, SecExceptionCode.SEC_ERROR_STA_LOW_VERSION_DATA_FILE, 319, 318, PullToRefreshBase.SMOOTH_SCROLL_LONG_DURATION_MS, 324, 331, 330, 337, 336, 343, 342, 349, 348, 355, 354, 361, 360, 367, 366, 824, 823}, new int[]{291, 290, 297, 296, 303, 302, SecExceptionCode.SEC_ERROR_STA_NO_SUCH_INDEX, SecExceptionCode.SEC_ERROR_STA_NO_MEMORY, 315, 314, 321, 320, 327, 326, 333, 332, 339, 338, 345, 344, 351, BaseClickableSpan.CLICK_ENABLE_TIME, 357, 356, 363, 362, 369, 368, 825, -3}, new int[]{293, 292, SecExceptionCode.SEC_ERROR_STA_STORE_UNKNOWN_ERROR, 298, SecExceptionCode.SEC_ERROR_STA_INCORRECT_DATA_FILE_DATA, 304, SecExceptionCode.SEC_ERROR_STA_DECRYPT_MISMATCH_KEY_DATA, SecExceptionCode.SEC_ERROR_STA_INVALID_ENCRYPTED_DATA, 317, 316, 323, 322, 329, 328, 335, 334, 341, 340, 347, 346, 353, 352, 359, 358, 365, 364, 371, 370, 827, 826}, new int[]{H5ErrorCode.HTTP_CONFLICT, 408, 403, 402, 397, 396, 391, 390, 79, 78, -2, -2, 13, 12, 37, 36, 2, -1, 44, 43, 109, 108, 385, 384, 379, 378, 373, 372, 828, -3}, new int[]{H5ErrorCode.HTTP_LENGTH_REQUIRED, H5ErrorCode.HTTP_GONE, 405, 404, SecExceptionCode.SEC_ERROR_STA_UNKNOWN_ERROR, 398, 393, 392, 81, 80, 40, -2, 15, 14, 39, 38, 3, -1, -1, 45, 111, 110, 387, 386, 381, 380, 375, 374, 830, 829}, new int[]{413, H5ErrorCode.HTTP_PRECONDITION, 407, 406, 401, 400, 395, 394, 83, 82, 41, -3, -3, -3, -3, -3, 5, 4, 47, 46, 113, 112, 389, 388, 383, 382, 377, 376, 831, -3}, new int[]{415, 414, FlowControl.STATUS_FLOW_CTRL_CUR, FlowControl.STATUS_FLOW_CTRL_ALL, 427, 426, 103, 102, 55, 54, 16, -3, -3, -3, -3, -3, -3, -3, 20, 19, 85, 84, 433, 432, 439, 438, 445, 444, 833, 832}, new int[]{Util.SLAM_TAG, NBNetStatus.SC_HTTP_RANGE_NOT_SATISFIABLE, SecExceptionCode.SEC_ERROR_DYN_ENC_BASE64_DECODE_FAILED, 422, 429, 428, 105, 104, 57, 56, -3, -3, -3, -3, -3, -3, -3, -3, 22, 21, 87, 86, 435, 434, 441, 440, 447, 446, 834, -3}, new int[]{AntiBrush.STATUS_BRUSH, Util.TOF_TAG, 425, 424, 431, 430, 107, 106, 59, 58, -3, -3, -3, -3, -3, -3, -3, -3, -3, 23, 89, 88, 437, 436, 443, 442, 449, 448, 836, 835}, iArr, new int[]{483, 482, 477, 476, 471, 470, 49, -1, -2, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -2, -1, 465, 464, 459, 458, 453, 452, 839, 838}, new int[]{485, 484, 479, 478, 473, 472, 51, 50, 31, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, 1, -2, 42, 467, 466, 461, 460, 455, 454, 840, -3}, new int[]{487, 486, 493, 492, SecExceptionCode.SEC_ERROR_DYN_ENC_UNKNOWN_ERROR, 498, 97, 96, 61, 60, -3, -3, -3, -3, -3, -3, -3, -3, -3, 26, 91, 90, 505, 504, MessageCode.MSG_BLE_NOT_SUPPORT, MessageCode.MSG_NETWORK_NOT_SATISFY, 517, 516, 842, 841}, new int[]{489, 488, 495, 494, 501, 500, 99, 98, 63, 62, -3, -3, -3, -3, -3, -3, -3, -3, 28, 27, 93, 92, MessageCode.MSG_SENSOR_MISSING, MessageCode.MSG_BLE_NO_SCAN, InputDeviceCompat.SOURCE_DPAD, 512, 519, 518, 843, -3}, new int[]{491, 490, 497, 496, 503, 502, 101, 100, 65, 64, 17, -3, -3, -3, -3, -3, -3, -3, 18, 29, 95, 94, MessageCode.MSG_BUILDING_NOT_SUPPORTED, 508, 515, 514, 521, 520, 845, 844}, new int[]{559, 558, 553, 552, 547, 546, 541, VideoRecordParameters.HD_HEIGHT_16_9, 73, 72, 32, -3, -3, -3, -3, -3, -3, 10, 67, 66, 115, 114, 535, 534, 529, 528, 523, 522, 846, -3}, new int[]{561, 560, 555, 554, 549, 548, 543, 542, 75, 74, -2, -1, 7, 6, 35, 34, 11, -2, 69, 68, 117, 116, 537, 536, 531, 530, 525, 524, 848, 847}, new int[]{563, 562, 557, 556, 551, 550, 545, FFmpegSessionConfig.VIDEO_SOFTENCODE_W, 77, 76, -2, 33, 9, 8, 25, 24, -1, -2, 71, 70, 119, 118, 539, 538, 533, 532, 527, 526, 849, -3}, new int[]{565, 564, 571, 570, 577, 576, 583, 582, 589, 588, 595, 594, 601, 600, SecExceptionCode.SEC_ERROR_SIGNATURE_DATA_FILE_MISMATCH, SecExceptionCode.SEC_ERROR_SIGNATURE_NO_SEEDSECRET, SecExceptionCode.SEC_ERROR_SIGNATURE_ATLAS_KEY_NOT_EXSITED, SecExceptionCode.SEC_ERROR_SIGNATURE_ILLEGEL_KEY, 619, 618, 625, 624, 631, 630, 637, 636, 643, 642, 851, 850}, new int[]{567, 566, 573, 572, 579, IjkMediaMeta.FF_PROFILE_H264_CONSTRAINED_BASELINE, 585, 584, 591, 590, 597, 596, 603, 602, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE, SecExceptionCode.SEC_ERROR_SIGNATURE_NO_DATA_FILE, SecExceptionCode.SEC_ERROR_SIGNATURE_LOW_VERSION_DATA_FILE, SecExceptionCode.SEC_ERROR_SIGNATURE_BLOWFISH_FAILED, 621, 620, 627, 626, 633, 632, 639, 638, 645, 644, 852, -3}, new int[]{569, 568, 575, 574, 581, 580, 587, 586, 593, 592, SecExceptionCode.SEC_ERROR_DYN_STORE_UNKNOWN_ERROR, 598, SecExceptionCode.SEC_ERROR_SIGNATURE_CONFUSE_FAILED, SecExceptionCode.SEC_ERROR_SIGNATURE_BASE64_FAILED, 611, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE_DATA, 617, 616, 623, 622, 629, 628, 635, 634, 641, 640, 647, 646, 854, 853}, new int[]{727, 726, MessageCode.MSG_LBS_ERROR, 720, MessageCode.MSG_LBS_SERVICE_NOT_AVAILBALE, MessageCode.MSG_LBS_INVALID_USER_KEY, 709, 708, 703, 702, 697, 696, 691, 690, 685, 684, 679, 678, 673, 672, 667, 666, 661, Configuration.INTERVAL_MAX, 655, 654, 649, 648, 855, -3}, new int[]{729, 728, 723, 722, MessageCode.MSG_LBS_INSUFFICIENT_PRIVILEGES, MessageCode.MSG_LBS_SERVICE_RESPONSE_ERROR, 711, 710, SecExceptionCode.SEC_ERROR_STA_KEY_ENC_MISMATCH_KEY_DATA, SecExceptionCode.SEC_ERROR_STA_KEY_ENC_INVALID_ENCRYPTED_DATA, SecExceptionCode.SEC_ERROR_SIGNATRUE_UNKNOWN, SecExceptionCode.SEC_ERROR_SIGNATURE_NONSUPPORTED_SIGN_TYPE, 693, 692, 687, 686, 681, 680, 675, 674, 669, 668, 663, 662, 657, 656, 651, 650, 857, 856}, new int[]{731, 730, 725, 724, MessageCode.MSG_LBS_INVALID_PARAMS, MessageCode.MSG_LBS_OVER_QUOTA, 713, 712, 707, 706, 701, 700, 695, 694, 689, 688, 683, 682, 677, 676, 671, 670, 665, 664, 659, 658, 653, 652, 858, -3}, new int[]{733, 732, 739, 738, 745, 744, 751, 750, 757, 756, 763, 762, 769, 768, 775, 774, 781, 780, 787, 786, 793, 792, SecExceptionCode.SEC_ERROR_STA_KEY_ENC_UNKNOWN_ERROR, 798, 805, 804, 811, 810, 860, 859}, new int[]{735, 734, 741, 740, 747, 746, 753, 752, 759, 758, WBConstants.SDK_ACTIVITY_FOR_RESULT_CODE, 764, 771, 770, 777, 776, 783, 782, 789, 788, 795, 794, 801, 800, MessageConstants.PAY_NETWORK_FAILED, 806, 813, 812, 861, -3}, new int[]{737, 736, 743, 742, 749, 748, 755, 754, 761, 760, 767, 766, 773, 772, 779, 778, 785, 784, 791, 790, 797, 796, SecExceptionCode.SEC_ERROR_PKG_VALID_NO_MEMORY, 802, MessageConstants.PAY_SDK_FAILED, MessageConstants.PAY_COMMON_ERROR, 815, 814, 863, 862}};
    }

    BitMatrixParser(BitMatrix bitMatrix2) {
        this.bitMatrix = bitMatrix2;
    }

    /* access modifiers changed from: 0000 */
    public final byte[] readCodewords() {
        byte[] bArr = new byte[144];
        int height = this.bitMatrix.getHeight();
        int width = this.bitMatrix.getWidth();
        for (int i = 0; i < height; i++) {
            int[] iArr = BITNR[i];
            for (int i2 = 0; i2 < width; i2++) {
                int i3 = iArr[i2];
                if (i3 >= 0 && this.bitMatrix.get(i2, i)) {
                    int i4 = i3 / 6;
                    bArr[i4] = (byte) (((byte) (1 << (5 - (i3 % 6)))) | bArr[i4]);
                }
            }
        }
        return bArr;
    }
}
