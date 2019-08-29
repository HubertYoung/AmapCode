package com.alipay.mobile.tinyappcommon.utils.io;

import android.text.TextUtils;
import java.lang.reflect.Field;

public final class SafeStructStat {
    public final long st_atime;
    public final long st_blksize;
    public final long st_blocks;
    public final long st_ctime;
    public final long st_dev;
    public final int st_gid;
    public final long st_ino;
    public final int st_mode;
    public final long st_mtime;
    public final long st_nlink;
    public final long st_rdev;
    public final long st_size;
    public final int st_uid;

    public SafeStructStat(long st_dev2, long st_ino2, int st_mode2, long st_nlink2, int st_uid2, int st_gid2, long st_rdev2, long st_size2, long st_atime2, long st_mtime2, long st_ctime2, long st_blksize2, long st_blocks2) {
        this.st_dev = st_dev2;
        this.st_ino = st_ino2;
        this.st_mode = st_mode2;
        this.st_nlink = st_nlink2;
        this.st_uid = st_uid2;
        this.st_gid = st_gid2;
        this.st_rdev = st_rdev2;
        this.st_size = st_size2;
        this.st_atime = st_atime2;
        this.st_mtime = st_mtime2;
        this.st_ctime = st_ctime2;
        this.st_blksize = st_blksize2;
        this.st_blocks = st_blocks2;
    }

    public SafeStructStat() {
        this.st_dev = 0;
        this.st_ino = 0;
        this.st_mode = 0;
        this.st_nlink = 0;
        this.st_uid = 0;
        this.st_gid = 0;
        this.st_rdev = 0;
        this.st_size = 0;
        this.st_atime = 0;
        this.st_mtime = 0;
        this.st_ctime = 0;
        this.st_blksize = 0;
        this.st_blocks = 0;
    }

    public SafeStructStat(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        Long st_dev2 = null;
        Long st_ino2 = null;
        Integer st_mode2 = null;
        Long st_nlink2 = null;
        Integer st_uid2 = null;
        Integer st_gid2 = null;
        Long st_rdev2 = null;
        Long st_size2 = null;
        Long st_atime2 = null;
        Long st_mtime2 = null;
        Long st_ctime2 = null;
        Long st_blksize2 = null;
        Long st_blocks2 = null;
        try {
            Field[] fields = object.getClass().getFields();
            if (!(fields == null || fields.length == 0)) {
                int length = fields.length;
                for (int i = 0; i < length; i++) {
                    Field field = fields[i];
                    String name = field.getName();
                    if (TextUtils.equals(name, "st_dev")) {
                        st_dev2 = (Long) field.get(object);
                    } else if (TextUtils.equals(name, "st_ino")) {
                        st_ino2 = (Long) field.get(object);
                    } else if (TextUtils.equals(name, "st_mode")) {
                        st_mode2 = (Integer) field.get(object);
                    } else if (TextUtils.equals(name, "st_nlink")) {
                        st_nlink2 = (Long) field.get(object);
                    } else if (TextUtils.equals(name, "st_uid")) {
                        st_uid2 = (Integer) field.get(object);
                    } else if (TextUtils.equals(name, "st_gid")) {
                        st_gid2 = (Integer) field.get(object);
                    } else if (TextUtils.equals(name, "st_rdev")) {
                        st_rdev2 = (Long) field.get(object);
                    } else if (TextUtils.equals(name, "st_size")) {
                        st_size2 = (Long) field.get(object);
                    } else if (TextUtils.equals(name, "st_atime")) {
                        st_atime2 = (Long) field.get(object);
                    } else if (TextUtils.equals(name, "st_mtime")) {
                        st_mtime2 = (Long) field.get(object);
                    } else if (TextUtils.equals(name, "st_ctime")) {
                        st_ctime2 = (Long) field.get(object);
                    } else if (TextUtils.equals(name, "st_blksize")) {
                        st_blksize2 = (Long) field.get(object);
                    } else if (TextUtils.equals(name, "st_blocks")) {
                        st_blocks2 = (Long) field.get(object);
                    }
                }
            }
            this.st_dev = st_dev2 != null ? st_dev2.longValue() : 0;
            this.st_ino = st_ino2 != null ? st_ino2.longValue() : 0;
            this.st_mode = st_mode2 != null ? st_mode2.intValue() : 0;
            this.st_nlink = st_nlink2 != null ? st_nlink2.longValue() : 0;
            this.st_uid = st_uid2 != null ? st_uid2.intValue() : 0;
            this.st_gid = st_gid2 != null ? st_gid2.intValue() : 0;
            this.st_rdev = st_rdev2 != null ? st_rdev2.longValue() : 0;
            this.st_size = st_size2 != null ? st_size2.longValue() : 0;
            this.st_atime = st_atime2 != null ? st_atime2.longValue() : 0;
            this.st_mtime = st_mtime2 != null ? st_mtime2.longValue() : 0;
            this.st_ctime = st_ctime2 != null ? st_ctime2.longValue() : 0;
            this.st_blksize = st_blksize2 != null ? st_blksize2.longValue() : 0;
            this.st_blocks = st_blocks2 != null ? st_blocks2.longValue() : 0;
        } catch (Exception e) {
            RuntimeException runtimeException = new RuntimeException(e);
            throw runtimeException;
        }
    }

    public SafeStructStat(SafeStructStat object) {
        if (object == null) {
            throw new NullPointerException();
        }
        this.st_dev = object.st_dev;
        this.st_ino = object.st_ino;
        this.st_mode = object.st_mode;
        this.st_nlink = object.st_nlink;
        this.st_uid = object.st_uid;
        this.st_gid = object.st_gid;
        this.st_rdev = object.st_rdev;
        this.st_size = object.st_size;
        this.st_atime = object.st_atime;
        this.st_mtime = object.st_mtime;
        this.st_ctime = object.st_ctime;
        this.st_blksize = object.st_blksize;
        this.st_blocks = object.st_blocks;
    }
}
