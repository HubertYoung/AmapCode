package com.autonavi.minimap.offline.model;

import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.autonavi.minimap.offline.utils.OfflineLog;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FilePathHelper {
    public static final String APP_FOLDER = "/autonavi";
    private static final String AUTONAVI_400_400 = "autonavi/400_400";
    public static final String AUTONAVI_DATA = "autonavi/data";
    private static final String AUTONAVI_DATA_3D_CROSSS = "autonavi/data/3dcross";
    private static final String AUTONAVI_DATA_BUS = "autonavi/data/bus";
    private static final String AUTONAVI_DATA_CROSSS = "autonavi/data/cross";
    private static final String AUTONAVI_DATA_CUSTOM_TTS = "autonavi/data/navitts/custom";
    private static final String AUTONAVI_DATA_CUSTOM_TTS_OLD = "autonavi/900_960";
    private static final String AUTONAVI_DATA_MAP = "autonavi/data/map";
    private static final String AUTONAVI_DATA_MAP_MAPZIP = "autonavi/data/map/mapzip";
    private static final String AUTONAVI_DATA_POI = "autonavi/data/poi";
    private static final String AUTONAVI_DATA_POIV5 = "autonavi/data/poiv5";
    private static final String AUTONAVI_DATA_ROADENLARGE = "autonavi/data/roadenlarge";
    private static final String AUTONAVI_DATA_ROUTE = "autonavi/data/route";
    private static final String AUTONAVI_DATA_ROUTE_ZIP = "autonavi/data/route/routezip";
    private static final String AUTONAVI_DATA_TTS = "autonavi/data/voice";
    private static final String AUTONAVI_DATA_TTS_OLD1 = "autonavi/800_850";
    private static final String AUTONAVI_DATA_TTS_OLD2 = "autonavi/data/navitts";
    private static final String AUTONAVI_DATA_VMAP = "autonavi/data/vmap";
    private static final String AUTONAVI_DATA_VMAP_VMAPZIP = "autonavi/data/vmap/vmapzip";
    private static final String AUTONAVI_TEMP_DOWNLOAD = "autonavi/data/tempDownload";
    private static final String AUTONAVI_TEMP_UNZIP = "autonavi/data/tempUnzip";
    public static final String DATA_INDEX_POI = "poiidx";
    public static final String DATA_INDEX_ROUTE = "gridcity";
    public static final String DATA_QUANGUO_FOR_CROSS = "jichugongnengbao";
    public static final String DATA_QUANGUO_FOR_MAP = "quanguo";
    public static final String DEFAULT_BACKUP_VOICE_PACKAGE_NAME = "__backup";
    public static final String DEFAULT_SAVED_VOICE_PACKAGE_NAME = "我的好声音";
    public static final String DEFAULT_VOICE_PACKAGE_NAME = "__anc_voices";
    public static final String FILE_NAVI_RECORD_DEFAULT_FILE_PREFIX = "__";
    public static final int FILE_NAVI_RECORD_FEATURE_STRING_END = 18;
    public static final int FILE_NAVI_RECORD_FEATURE_STRING_START = 11;
    public static final int FILE_NAVI_RECORD_FILE_LENGTH = 26;
    public static final String FOLDER_1_NAVI_TTS = "/800_850";
    public static final String FOLDER_2_NAVI_TTS = "/data/navitts";
    public static final String FOLDER_COMPILE_BETA1 = "autonavi/data/navi/compile_v1";
    public static final String FOLDER_NAVI_RECORD = "/900_960";
    public static final String SUFFIX_DOT_A_FOR_OLD_POI = ".a";
    public static final String SUFFIX_DOT_DAT_FOR_MAP = ".dat";
    public static final String SUFFIX_DOT_INX = ".idx";
    public static final String SUFFIX_DOT_IRF_FOR_VOICE = ".irf";
    public static final String SUFFIX_DOT_JV_FOR_CROSS = ".jv";
    public static final String SUFFIX_DOT_OBD_FOR_BUS = ".obd";
    public static final String SUFFIX_DOT_OSD_FOR_POI = ".osd";
    public static final String SUFFIX_DOT_RJV_FOR_3D_CROSS = ".rjv";
    public static final String SUFFIX_DOT_RT_FOR_ROUTE = ".rt";
    public static final String SUFFIX_DOT_TMP = ".tmp";
    public static final String SUFFIX_DOT_UDAT_FOR_MAP = ".udat";
    public static final String SUFFIX_DOT_UJV_FOR_CROSS = ".ujv";
    public static final String SUFFIX_DOT_UOSD_FOR_POI = ".uosd";
    public static final String SUFFIX_DOT_URJV_FOR_3D_CROSS = ".urjv";
    public static final String SUFFIX_DOT_URT_FOR_ROUTE = ".urt";
    public static final String SUFFIX_DOT_ZIP = ".zip";
    public static final String SUFFIX_JOURNAL = "-journal";
    private static volatile FilePathHelper sInstance;
    private String mCurrentStoragePath = PathManager.a().b(DirType.OFFLINE);

    private FilePathHelper() {
        StringBuilder sb = new StringBuilder("FilePathHelper mCurrentStoragePath:");
        sb.append(this.mCurrentStoragePath);
        OfflineLog.d(sb.toString());
    }

    public static FilePathHelper getInstance() {
        if (sInstance == null) {
            synchronized (FilePathHelper.class) {
                if (sInstance == null) {
                    sInstance = new FilePathHelper();
                }
            }
        }
        return sInstance;
    }

    public static void destroy() {
        sInstance = null;
    }

    public static void deleteFolder(String str, boolean z) {
        deleteFolder(new File(str), z);
    }

    public static void deleteFolder(File file, boolean z) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                arrayList.add(file);
                while (arrayList.size() > 0) {
                    File file2 = (File) arrayList.remove(0);
                    if (file2 != null && file2.exists() && file2.isDirectory()) {
                        String[] list = file2.list();
                        if (list != null && list.length > 0) {
                            for (String str : list) {
                                if (str != null) {
                                    File file3 = new File(file2, str);
                                    if (file3.exists()) {
                                        if (file3.isDirectory()) {
                                            arrayList.add(file3);
                                        } else {
                                            deleteFileInSafely(file3);
                                        }
                                    }
                                }
                            }
                        }
                        arrayList2.add(file2);
                    }
                }
                int i = !z;
                for (int size = arrayList2.size() - 1; size >= i; size--) {
                    File file4 = (File) arrayList2.get(size);
                    if (file4 != null && file4.exists()) {
                        deleteFileInSafely(file4);
                    }
                }
                return;
            }
            deleteFileInSafely(file);
        }
    }

    public static boolean deleteFileInSafely(File file) {
        if (file == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(file.getAbsolutePath());
        sb.append(System.currentTimeMillis());
        File file2 = new File(sb.toString());
        file.renameTo(file2);
        return file2.delete();
    }

    public final String getVoiceTtsConfigDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_TTS_OLD1);
        return sb.toString();
    }

    public final List<String> getVoiceTtsOldPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_TTS_OLD1);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.mCurrentStoragePath);
        sb3.append(File.separator);
        sb3.append(AUTONAVI_DATA_TTS_OLD2);
        return Arrays.asList(new String[]{sb2, sb3.toString()});
    }

    public final String getMapFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_MAP);
        return sb.toString();
    }

    public final String getMapZipFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_MAP_MAPZIP);
        return sb.toString();
    }

    public final String getRouteZipFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_ROUTE_ZIP);
        return sb.toString();
    }

    public final String getPoiFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_POI);
        return sb.toString();
    }

    public final String getV5MapZipDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append("autonavi/data/vmapzip");
        return sb.toString();
    }

    public final String getV5MapFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_VMAP);
        return sb.toString();
    }

    public final String getV5PoiDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_POIV5);
        return sb.toString();
    }

    public final String getV5RoadEnglargeDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_ROADENLARGE);
        return sb.toString();
    }

    public final String getV5RouteFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_ROUTE);
        return sb.toString();
    }

    public final String getRouteFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_ROUTE);
        return sb.toString();
    }

    public final String getCrossFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_CROSSS);
        return sb.toString();
    }

    public final String getTempDownloadDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_TEMP_DOWNLOAD);
        return sb.toString();
    }

    public final String getTempUnzipDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_TEMP_UNZIP);
        return sb.toString();
    }

    public final String get3dCrossFileDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_3D_CROSSS);
        return sb.toString();
    }

    public final String getMapFilePath(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_MAP);
        sb.append(File.separator);
        sb.append(str);
        sb.append(z ? SUFFIX_DOT_DAT_FOR_MAP : "");
        return sb.toString();
    }

    public final String getPoiFilePath(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_POI);
        sb.append(File.separator);
        sb.append(str);
        sb.append(z ? SUFFIX_DOT_OSD_FOR_POI : "");
        return sb.toString();
    }

    public final String getRouteFilePath(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_ROUTE);
        sb.append(File.separator);
        sb.append(str);
        String str2 = z ? DATA_INDEX_ROUTE.equalsIgnoreCase(str) ? SUFFIX_DOT_INX : SUFFIX_DOT_RT_FOR_ROUTE : "";
        sb.append(str2);
        return sb.toString();
    }

    public final String getCrossFilePath(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_CROSSS);
        sb.append(File.separator);
        sb.append(str);
        sb.append(z ? SUFFIX_DOT_JV_FOR_CROSS : "");
        return sb.toString();
    }

    public final String get3dCrossFilePath(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(AUTONAVI_DATA_3D_CROSSS);
        sb.append(File.separator);
        sb.append(str);
        sb.append(z ? SUFFIX_DOT_RJV_FOR_3D_CROSS : "");
        return sb.toString();
    }

    public final String getCompileBeta1DataDir() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCurrentStoragePath);
        sb.append(File.separator);
        sb.append(FOLDER_COMPILE_BETA1);
        return sb.toString();
    }
}
