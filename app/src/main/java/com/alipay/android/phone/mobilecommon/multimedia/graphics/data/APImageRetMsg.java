package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APImageRetMsg {
    private RETCODE code;
    private String msg;

    public enum RETCODE {
        SUC(0),
        CONNTIMEOUT(1),
        DNSTIMEOUT(2),
        STREAMERROR(3),
        COMPRESS_ERROR(4),
        PARAM_ERROR(5),
        INVALID_ACL(6),
        INVALID_TOKEN(7),
        GET_TOKEN_FAILED(8),
        NO_PRIVILEGE(9),
        INVALID_CODE(10),
        UPLOAD_ERROR(11),
        MD5_FAILED(12),
        STORE_FAILED(13),
        INVALID_DJANGO(14),
        INCONSISTENT_CHUNK_NUM(15),
        INCONSISTENT_SIZE(16),
        INVALID_APPKEY(17),
        FILE_IS_EXISTED(18),
        FILE_NOT_EXIST(19),
        DB_FAILED(20),
        CACHE_FAILED(21),
        TFS_READ_FAILED(22),
        TAIR_READ_FAILED(23),
        DOWNLOAD_FAILED(24),
        UNKNOWN_ERROR(25),
        CANCEL(26),
        REUSE(27),
        INVALID_NETWORK(28),
        SPACE_NOT_ENOUGH(29),
        ENCRYPT_FAILED(30),
        DECRYPT_FAILED(31),
        TIME_OUT(32),
        CURRENT_LIMIT(2000);
        
        int code;

        private RETCODE(int code2) {
            this.code = code2;
        }

        public final int value() {
            return this.code;
        }
    }

    public RETCODE getCode() {
        return this.code;
    }

    public void setCode(RETCODE code2) {
        this.code = code2;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }

    public String toString() {
        return "APImageRetMsg{code=" + this.code + ", msg='" + this.msg + '\'' + '}';
    }
}
