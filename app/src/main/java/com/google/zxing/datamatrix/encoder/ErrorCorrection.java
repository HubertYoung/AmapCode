package com.google.zxing.datamatrix.encoder;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilesdk.socketcraft.api.WSContextConstant;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.jni.ae.pos.SpeedState;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.route.sharebike.model.EndBill;
import com.autonavi.minimap.route.sharebike.model.ScanQrcode;
import com.taobao.accs.common.Constants;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;

public final class ErrorCorrection {
    private static final int[] ALOG = new int[255];
    private static final int[][] FACTORS = {new int[]{228, 48, 15, 111, 62}, new int[]{23, 68, 144, 134, 240, 92, DumpSegment.ANDROID_HEAP_DUMP_INFO}, new int[]{28, 24, WSContextConstant.HANDSHAKE_RECEIVE_SIZE, 166, 223, 248, 116, 255, 110, 61}, new int[]{175, DumpSegment.ANDROID_ROOT_FINALIZING, 205, 12, 194, 168, 39, FavoritesPointFragment.REQUEST_TAG_SELECT, 60, 97, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_}, new int[]{41, 153, 158, 91, 61, 42, DumpSegment.ANDROID_ROOT_JNI_MONITOR, MessageCode.MSG_SERVER_ERROR, 97, 178, 100, FavoritesPointFragment.REQUEST_COMPNAY}, new int[]{156, 97, 192, 252, 95, 9, 157, 119, DumpSegment.ANDROID_ROOT_FINALIZING, 45, 18, 186, 83, WSContextConstant.HANDSHAKE_RECEIVE_SIZE}, new int[]{83, DumpSegment.ANDROID_PRIMITIVE_ARRAY_NODATA_DUMP, 100, 39, 188, 75, 66, 61, FavoritesPointFragment.REQUEST_HOME, MessageCode.MSG_SERVER_ERROR, 109, 129, 94, DumpSegment.ANDROID_HEAP_DUMP_INFO, 225, 48, 90, 188}, new int[]{15, DumpSegment.ANDROID_PRIMITIVE_ARRAY_NODATA_DUMP, 244, 9, 233, 71, 168, 2, 188, 160, 153, 145, 253, 79, 108, 82, 27, 174, 186, 172}, new int[]{52, 190, 88, 205, 109, 39, SpeedState.ENO_DEFINE, 21, 155, 197, 251, 223, 155, 21, 5, 172, DumpSegment.ANDROID_HEAP_DUMP_INFO, SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA, 12, 181, 184, 96, 50, 193}, new int[]{211, 231, 43, 97, 71, 96, 103, 174, 37, 151, 170, 53, 75, 34, 249, 121, 17, DumpSegment.ANDROID_ROOT_FINALIZING, 110, MessageCode.MSG_SERVER_ERROR, DumpSegment.ANDROID_ROOT_VM_INTERNAL, 136, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 151, 233, 168, 93, 255}, new int[]{FavoritesPointFragment.REQUEST_TAG_SELECT, 127, FavoritesPointFragment.REQUEST_COMPNAY, 218, 130, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, EndBill.UNKNOWN_END_ORDER_FAILED, 181, 102, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 84, 179, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 251, 80, 182, 229, 18, 2, 4, 68, 33, 101, DumpSegment.ANDROID_ROOT_INTERNED_STRING, 95, 119, 115, 44, 175, 184, 59, 25, 225, 98, 81, 112}, new int[]{77, 193, DumpSegment.ANDROID_ROOT_INTERNED_STRING, 31, 19, 38, 22, 153, 247, 105, 122, 2, FavoritesPointFragment.REQUEST_TAG_SELECT, 133, FavoritesPointFragment.REQUEST_COMPNAY, 8, 175, 95, 100, 9, 167, 105, MessageCode.MSG_NLP_RESPONSED, 111, 57, 121, 21, 1, 253, 57, 54, 101, 248, 202, 69, 50, 150, 177, 226, 5, 9, 5}, new int[]{FavoritesPointFragment.REQUEST_TAG_SELECT, 132, 172, 223, 96, 32, 117, 22, 238, 133, 238, 231, 205, 188, 237, 87, 191, 106, 16, 147, 118, 23, 37, 90, 170, 205, 131, 88, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 100, 66, DumpSegment.ANDROID_ROOT_FINALIZING, 186, 240, 82, 44, SpeedState.ENO_DEFINE, 87, 187, 147, 160, 175, 69, MessageCode.MSG_SERVER_ERROR, 92, 253, 225, 19}, new int[]{175, 9, 223, 238, 12, 17, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 208, 100, 29, 175, 170, 230, 192, MessageCode.MSG_ONLINE_BUILDING_LOCATED, 235, 150, 159, 36, 223, 38, 200, 132, 54, 228, 146, 218, 234, 117, 203, 29, 232, 144, 238, 22, 150, 201, 117, 62, 207, EndBill.ORDER_PAYMENT_FAILED, 13, DumpSegment.ANDROID_ROOT_INTERNED_STRING, FavoritesPointFragment.REQUEST_TAG_SELECT, 127, 67, 247, 28, 155, 43, 203, 107, 233, 53, 143, 46}, new int[]{FavoritesPointFragment.REQUEST_COMPNAY, 93, 169, 50, 144, 210, 39, 118, 202, 188, 201, WSContextConstant.HANDSHAKE_SEND_SIZE, 143, 108, 196, 37, WSContextConstant.HANDSHAKE_RECEIVE_SIZE, 112, 134, 230, FavoritesPointFragment.REQUEST_TAG_SELECT, 63, 197, 190, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 106, WSContextConstant.HANDSHAKE_RECEIVE_SIZE, Constants.SDK_VERSION_CODE, 175, 64, 114, 71, ScanQrcode.NOT_CP_RESPONSE_CODE, 44, 147, 6, 27, 218, 51, 63, 87, 10, 40, 130, 188, 17, EndBill.END_ORDER_FAILED, 31, SpeedState.ENO_DEFINE, 170, 4, 107, 232, 7, 94, 166, 224, SecExceptionCode.SEC_ERROR_INIT_LOW_VERSION_DATA, 86, 47, 11, 204}, new int[]{AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 228, 173, 89, 251, 149, 159, 56, 89, 33, 147, 244, ScanQrcode.SAME_BIKE_USING_RESPONSE_CODE, 36, 73, 127, MessageCode.MSG_SERVER_ERROR, 136, 248, 180, 234, 197, 158, 177, 68, 122, 93, MessageCode.MSG_SERVER_ERROR, 15, 160, 227, 236, 66, DumpSegment.ANDROID_ROOT_DEBUGGER, 153, WSContextConstant.HANDSHAKE_RECEIVE_SIZE, 202, 167, 179, 25, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 232, 96, 210, 231, 136, 223, 239, 181, FavoritesPointFragment.REQUEST_HOME, 59, 52, 172, 25, 49, 232, 211, WSContextConstant.HANDSHAKE_SEND_SIZE, 64, 54, 108, 153, 132, 63, 96, 103, 82, 186}};
    private static final int[] FACTOR_SETS = {5, 7, 10, 11, 12, 14, 18, 20, 24, 28, 36, 42, 48, 56, 62, 68};
    private static final int[] LOG = new int[256];
    private static final int MODULO_VALUE = 301;

    static {
        int i = 1;
        for (int i2 = 0; i2 < 255; i2++) {
            ALOG[i2] = i;
            LOG[i] = i2;
            i *= 2;
            if (i >= 256) {
                i ^= 301;
            }
        }
    }

    private ErrorCorrection() {
    }

    public static String encodeECC200(String str, SymbolInfo symbolInfo) {
        if (str.length() != symbolInfo.getDataCapacity()) {
            throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        }
        StringBuilder sb = new StringBuilder(symbolInfo.getDataCapacity() + symbolInfo.getErrorCodewords());
        sb.append(str);
        int interleavedBlockCount = symbolInfo.getInterleavedBlockCount();
        if (interleavedBlockCount == 1) {
            sb.append(createECCBlock(str, symbolInfo.getErrorCodewords()));
        } else {
            sb.setLength(sb.capacity());
            int[] iArr = new int[interleavedBlockCount];
            int[] iArr2 = new int[interleavedBlockCount];
            int[] iArr3 = new int[interleavedBlockCount];
            int i = 0;
            while (i < interleavedBlockCount) {
                int i2 = i + 1;
                iArr[i] = symbolInfo.getDataLengthForInterleavedBlock(i2);
                iArr2[i] = symbolInfo.getErrorLengthForInterleavedBlock(i2);
                iArr3[i] = 0;
                if (i > 0) {
                    iArr3[i] = iArr3[i - 1] + iArr[i];
                }
                i = i2;
            }
            for (int i3 = 0; i3 < interleavedBlockCount; i3++) {
                StringBuilder sb2 = new StringBuilder(iArr[i3]);
                for (int i4 = i3; i4 < symbolInfo.getDataCapacity(); i4 += interleavedBlockCount) {
                    sb2.append(str.charAt(i4));
                }
                String createECCBlock = createECCBlock(sb2.toString(), iArr2[i3]);
                int i5 = i3;
                int i6 = 0;
                while (i5 < iArr2[i3] * interleavedBlockCount) {
                    sb.setCharAt(symbolInfo.getDataCapacity() + i5, createECCBlock.charAt(i6));
                    i5 += interleavedBlockCount;
                    i6++;
                }
            }
        }
        return sb.toString();
    }

    private static String createECCBlock(CharSequence charSequence, int i) {
        return createECCBlock(charSequence, 0, charSequence.length(), i);
    }

    private static String createECCBlock(CharSequence charSequence, int i, int i2, int i3) {
        int i4 = 0;
        while (true) {
            if (i4 >= FACTOR_SETS.length) {
                i4 = -1;
                break;
            } else if (FACTOR_SETS[i4] == i3) {
                break;
            } else {
                i4++;
            }
        }
        if (i4 < 0) {
            throw new IllegalArgumentException("Illegal number of error correction codewords specified: ".concat(String.valueOf(i3)));
        }
        int[] iArr = FACTORS[i4];
        char[] cArr = new char[i3];
        for (int i5 = 0; i5 < i3; i5++) {
            cArr[i5] = 0;
        }
        for (int i6 = i; i6 < i + i2; i6++) {
            int i7 = i3 - 1;
            char charAt = cArr[i7] ^ charSequence.charAt(i6);
            while (i7 > 0) {
                if (charAt == 0 || iArr[i7] == 0) {
                    cArr[i7] = cArr[i7 - 1];
                } else {
                    cArr[i7] = (char) (cArr[i7 - 1] ^ ALOG[(LOG[charAt] + LOG[iArr[i7]]) % 255]);
                }
                i7--;
            }
            if (charAt == 0 || iArr[0] == 0) {
                cArr[0] = 0;
            } else {
                cArr[0] = (char) ALOG[(LOG[charAt] + LOG[iArr[0]]) % 255];
            }
        }
        char[] cArr2 = new char[i3];
        for (int i8 = 0; i8 < i3; i8++) {
            cArr2[i8] = cArr[(i3 - i8) - 1];
        }
        return String.valueOf(cArr2);
    }
}
