package com.alipay.mobile.nebula.util.tar;

import java.io.File;

public class TarHeader {
    public static final int CHKSUMLEN = 8;
    public static final int GIDLEN = 8;
    public static final byte LF_BLK = 52;
    public static final byte LF_CHR = 51;
    public static final byte LF_CONTIG = 55;
    public static final byte LF_DIR = 53;
    public static final byte LF_FIFO = 54;
    public static final byte LF_LINK = 49;
    public static final byte LF_NORMAL = 48;
    public static final byte LF_OLDNORM = 0;
    public static final byte LF_SYMLINK = 50;
    public static final int MODELEN = 8;
    public static final int MODTIMELEN = 12;
    public static final int NAMELEN = 100;
    public static final int SIZELEN = 12;
    public static final int UIDLEN = 8;
    public static final int USTAR_DEVLEN = 8;
    public static final int USTAR_FILENAME_PREFIX = 155;
    public static final int USTAR_GROUP_NAMELEN = 32;
    public static final String USTAR_MAGIC = "ustar";
    public static final int USTAR_MAGICLEN = 8;
    public static final int USTAR_USER_NAMELEN = 32;
    public int checkSum;
    public int devMajor;
    public int devMinor;
    public int groupId;
    public StringBuffer groupName;
    public byte linkFlag;
    public StringBuffer linkName = new StringBuffer();
    public StringBuffer magic = new StringBuffer("ustar");
    public long modTime;
    public int mode;
    public StringBuffer name = new StringBuffer();
    public StringBuffer namePrefix;
    public long size;
    public int userId;
    public StringBuffer userName;

    public TarHeader() {
        String user = System.getProperty("user.name", "");
        user = user.length() > 31 ? user.substring(0, 31) : user;
        this.userId = 0;
        this.groupId = 0;
        this.userName = new StringBuffer(user);
        this.groupName = new StringBuffer("");
        this.namePrefix = new StringBuffer();
    }

    public static StringBuffer parseName(byte[] header, int offset, int length) {
        StringBuffer result = new StringBuffer(length);
        int end = offset + length;
        int i = offset;
        while (i < end && header[i] != 0) {
            result.append((char) header[i]);
            i++;
        }
        return result;
    }

    public static int getNameBytes(StringBuffer name2, byte[] buf, int offset, int length) {
        int i = 0;
        while (i < length && i < name2.length()) {
            buf[offset + i] = (byte) name2.charAt(i);
            i++;
        }
        while (i < length) {
            buf[offset + i] = 0;
            i++;
        }
        return offset + length;
    }

    public static TarHeader createHeader(String entryName, long size2, long modTime2, boolean dir) {
        String name2 = trim(entryName.replace(File.separatorChar, '/'), '/');
        TarHeader header = new TarHeader();
        header.linkName = new StringBuffer("");
        if (name2.length() > 100) {
            header.namePrefix = new StringBuffer(name2.substring(0, name2.lastIndexOf(47)));
            header.name = new StringBuffer(name2.substring(name2.lastIndexOf(47) + 1));
        } else {
            header.name = new StringBuffer(name2);
        }
        if (dir) {
            header.mode = 16877;
            header.linkFlag = 53;
            if (header.name.charAt(header.name.length() - 1) != '/') {
                header.name.append("/");
            }
            header.size = 0;
        } else {
            header.mode = 33188;
            header.linkFlag = 48;
            header.size = size2;
        }
        header.modTime = modTime2;
        header.checkSum = 0;
        header.devMajor = 0;
        header.devMinor = 0;
        return header;
    }

    public static String trim(String s, char c) {
        StringBuffer tmp = new StringBuffer(s);
        int i = 0;
        while (i < tmp.length() && tmp.charAt(i) == c) {
            tmp.deleteCharAt(i);
            i++;
        }
        int i2 = tmp.length() - 1;
        while (i2 >= 0 && tmp.charAt(i2) == c) {
            tmp.deleteCharAt(i2);
            i2--;
        }
        return tmp.toString();
    }
}
