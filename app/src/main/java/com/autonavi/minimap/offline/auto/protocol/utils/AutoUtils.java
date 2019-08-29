package com.autonavi.minimap.offline.auto.protocol.utils;

import com.amap.bundle.blutils.PathManager;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.auto.model.nativeModel.AutoJsCity;
import com.autonavi.minimap.offline.auto.model.nativeModel.AutoJsCity.DataBean.CityBean;
import com.autonavi.minimap.offline.auto.model.nativeModel.NativeCity;
import com.autonavi.minimap.offline.auto.model.nativeModel.NativeCity.DataBean;
import com.autonavi.minimap.offline.auto.model.nativeModel.NativeCity.DataBean.CityBean.FilesBean;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATCityDataFile;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATCityDataItem;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATCityDataListResponse;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadCityDataFile;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadCityDataItem;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadCityDataItemRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoCityInfoRequest.CityInfoResponse;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offline.utils.log.Logger;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AutoUtils {
    public static final String CITY_GANGAO_GROUP_ADCOD = "998";
    public static final String CITY_GANGAO_GROUP_JIANPIN = "ga";
    public static final String CITY_GANGAO_GROUP_NAME = "港澳地区";
    public static final String CITY_GANGAO_GROUP_PINYIN = "tebiexingzhengqu";
    public static final int CITY_QGGYT_ADCODE = 0;
    public static final String CITY_QGGYT_CITY_NAME_AUTO = "基础功能包";
    public static final String CITY_QGGYT_CITY_NAME_MOBILE = "全国概要图";
    public static final String CITY_QGGYT_FILE_PINYIN_AUTO = "jichugongnengbao";
    public static final String CITY_QGGYT_FILE_PINYIN_MOBILE = "quanguo";
    public static final String CITY_QGGYT_GROUP_ADCODE = "999";
    public static final String CITY_QGGYT_GROUP_JIANPIN = "zxs";
    public static final String CITY_QGGYT_GROUP_NAME = "全国概要图+直辖市";
    public static final String CITY_QGGYT_GROUP_PINYIN = "zhixiashi";
    private static final Logger logger = Logger.getLogger("AutoUtils");

    public static String getNativeDownloadCity() {
        IDownloadManager downloadManager = OfflineNativeSdk.getInstance().getDownloadManager();
        return downloadManager != null ? downloadManager.getAlinkSyncDataStr() : "";
    }

    public static AutoJsCity buildJsAllCitys(ATCityDataListResponse aTCityDataListResponse, NativeCity nativeCity) {
        CityBean cityBean;
        AutoJsCity autoJsCity = new AutoJsCity();
        if (nativeCity == null || nativeCity.getData() == null) {
            return autoJsCity;
        }
        long currentCityAdcode = (long) OfflineUtil.getCurrentCityAdcode();
        ArrayList arrayList = new ArrayList();
        List<DataBean> data = nativeCity.getData();
        Map autoCityMap = aTCityDataListResponse != null ? getAutoCityMap(aTCityDataListResponse) : null;
        for (DataBean next : data) {
            AutoJsCity.DataBean dataBean = new AutoJsCity.DataBean();
            int i = 0;
            Map map = autoCityMap != null ? (Map) autoCityMap.get(next.getAdcode()) : null;
            if (map == null) {
                dataBean.setAdcode(next.getAdcode());
                dataBean.setCityname(next.getName());
                dataBean.setPinyin(next.getPinyin());
                List<DataBean.CityBean> city = next.getCity();
                ArrayList arrayList2 = new ArrayList();
                for (DataBean.CityBean buildJsCity : city) {
                    CityBean buildJsCity2 = buildJsCity(buildJsCity, currentCityAdcode, null);
                    arrayList2.add(buildJsCity2);
                    if (buildJsCity2.getIsUpdate() == 1) {
                        i = 1;
                    }
                }
                dataBean.setCity(arrayList2);
                dataBean.setIsUpdate(i);
            } else {
                dataBean.setAdcode(next.getAdcode());
                dataBean.setCityname(next.getName());
                dataBean.setPinyin(next.getPinyin());
                ArrayList arrayList3 = new ArrayList();
                for (DataBean.CityBean next2 : next.getCity()) {
                    new CityBean();
                    ATCityDataItem aTCityDataItem = (ATCityDataItem) map.get(next2.getAdcode());
                    if (aTCityDataItem == null) {
                        cityBean = buildJsCity(next2, currentCityAdcode, null);
                        arrayList3.add(cityBean);
                    } else {
                        cityBean = buildJsCity(next2, currentCityAdcode, aTCityDataItem);
                        arrayList3.add(cityBean);
                    }
                    if (cityBean.getIsUpdate() == 1) {
                        i = 1;
                    }
                }
                dataBean.setCity(arrayList3);
                dataBean.setIsUpdate(i);
            }
            arrayList.add(dataBean);
        }
        autoJsCity.setData(arrayList);
        return autoJsCity;
    }

    private static CityBean buildJsCity(DataBean.CityBean cityBean, long j, ATCityDataItem aTCityDataItem) {
        List files = aTCityDataItem != null ? aTCityDataItem.getFiles() : null;
        int autoSendStatus = getAutoSendStatus(cityBean, files);
        CityBean cityBean2 = new CityBean();
        int i = 0;
        cityBean2.setIsUpdate(cityBean.getStatus() == 64 ? 1 : 0);
        if (j == ((long) Integer.parseInt(cityBean.getAdcode()))) {
            i = 1;
        }
        cityBean2.setIsCurrentCity(String.valueOf(i));
        cityBean2.setCitysize(String.valueOf(cityBean.getMap_size() + cityBean.getRoute_size()));
        cityBean2.setAutoStatus(String.valueOf(autoSendStatus));
        cityBean2.setPinyin(cityBean.getPinyin());
        cityBean2.setCityname(cityBean.getName());
        cityBean2.setAdcode(cityBean.getAdcode());
        logger.e(cityBean.getName());
        if (autoSendStatus == 1) {
            cityBean2.setAlreadyDownloadSize(0.0d);
        } else {
            cityBean2.setAlreadyDownloadSize((double) buildJsAlreadyDownloadSize(cityBean.getFiles(), files));
        }
        List<FilesBean> files2 = cityBean.getFiles();
        if (files2 != null) {
            ArrayList arrayList = new ArrayList();
            if (aTCityDataItem == null) {
                for (FilesBean buildJsFile : files2) {
                    arrayList.add(buildJsFile(autoSendStatus, buildJsFile, null));
                }
            } else {
                List<ATCityDataFile> files3 = aTCityDataItem.getFiles();
                for (FilesBean buildJsFile2 : files2) {
                    arrayList.add(buildJsFile(autoSendStatus, buildJsFile2, files3));
                }
            }
            cityBean2.setFiles(arrayList);
        }
        return cityBean2;
    }

    private static CityBean.FilesBean buildJsFile(int i, FilesBean filesBean, List<ATCityDataFile> list) {
        CityBean.FilesBean filesBean2 = new CityBean.FilesBean();
        filesBean2.setType(filesBean.getType());
        filesBean2.setSize(filesBean.getSize());
        filesBean2.setPath(filesBean.getPath());
        filesBean2.setVersion(String.valueOf(filesBean.getVersion()));
        filesBean2.setMd5(filesBean.getMd5());
        if (list != null) {
            Iterator<ATCityDataFile> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ATCityDataFile next = it.next();
                if (i != 1) {
                    if (filesBean.getType().equals(next.getFileType())) {
                        filesBean2.setOffset(next.getDownloadingSize());
                        break;
                    }
                } else {
                    filesBean2.setOffset(0);
                    break;
                }
            }
        } else {
            filesBean2.setOffset(0);
        }
        return filesBean2;
    }

    private static long buildJsAlreadyDownloadSize(List<FilesBean> list, List<ATCityDataFile> list2) {
        long j = 0;
        if (list2 == null || list == null) {
            return 0;
        }
        for (FilesBean next : list) {
            for (ATCityDataFile next2 : list2) {
                if (next.getType().equals(next2.getFileType())) {
                    Logger logger2 = logger;
                    StringBuilder sb = new StringBuilder("downloadingSize:");
                    sb.append(next2.getDownloadingSize());
                    sb.append(",nativeFileVersion:");
                    sb.append(next.getVersion());
                    sb.append(",autoDownloadingVersion:");
                    sb.append(next2.getDownloadingVersion());
                    sb.append(",fileType:");
                    sb.append(next2.getFileType());
                    logger2.e(sb.toString());
                    if (next.getVersion() == next2.getDownloadingVersion()) {
                        j += next2.getDownloadingSize();
                    }
                }
            }
        }
        logger.e("已下载的总大小：".concat(String.valueOf(j)));
        return j;
    }

    public static int getAutoSendStatus(DataBean.CityBean cityBean, List<ATCityDataFile> list) {
        List<FilesBean> files = cityBean.getFiles();
        if (files == null) {
            return 4;
        }
        boolean z = false;
        Iterator<FilesBean> it = files.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String type = it.next().getType();
            if (cityBean.getPinyin().equals("quanguo")) {
                if (type.equals(AutoConstants.AUTO_FILE_CROSS)) {
                    break;
                }
            } else if (type.equals(AutoConstants.AUTO_FILE_ROUTE)) {
                break;
            }
        }
        z = true;
        if (!z) {
            return 4;
        }
        if (list == null) {
            return 1;
        }
        for (FilesBean next : files) {
            String type2 = next.getType();
            Iterator<ATCityDataFile> it2 = list.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                ATCityDataFile next2 = it2.next();
                if (type2.equals(next2.getFileType())) {
                    long version = next.getVersion();
                    long currentVersion = next2.getCurrentVersion();
                    long downloadingSize = next2.getDownloadingSize();
                    long downloadingVersion = next2.getDownloadingVersion();
                    if (downloadingSize > 0 || downloadingVersion > 0) {
                        int i = (version > downloadingVersion ? 1 : (version == downloadingVersion ? 0 : -1));
                        if (i == 0) {
                            return 2;
                        }
                        if (i > 0) {
                            return 1;
                        }
                    } else if (version > currentVersion) {
                        return 1;
                    }
                }
            }
        }
        return 3;
    }

    public static Map<String, Map<String, ATCityDataItem>> getAutoCityMap(ATCityDataListResponse aTCityDataListResponse) {
        HashMap hashMap = new HashMap();
        if (aTCityDataListResponse == null) {
            return null;
        }
        List<ATCityDataItem> cities = aTCityDataListResponse.getCities();
        if (cities == null) {
            return null;
        }
        for (ATCityDataItem next : cities) {
            String adCode = next.getAdCode();
            List<ATCityDataItem> subCities = next.getSubCities();
            if (subCities != null) {
                HashMap hashMap2 = new HashMap();
                for (ATCityDataItem next2 : subCities) {
                    hashMap2.put(next2.getAdCode(), next2);
                }
                hashMap.put(adCode, hashMap2);
            }
        }
        return hashMap;
    }

    public static ATUploadCityDataItem buildSyncTaskCity(CityBean cityBean) {
        ATUploadCityDataItem aTUploadCityDataItem = new ATUploadCityDataItem();
        aTUploadCityDataItem.setName(cityBean.getCityname());
        aTUploadCityDataItem.setAdCode(cityBean.getAdcode());
        aTUploadCityDataItem.setState(0);
        List<CityBean.FilesBean> files = cityBean.getFiles();
        if (files == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (CityBean.FilesBean next : files) {
            ATUploadCityDataFile aTUploadCityDataFile = new ATUploadCityDataFile();
            aTUploadCityDataFile.setFileType(next.getType());
            aTUploadCityDataFile.setLength(next.getSize());
            aTUploadCityDataFile.setOffset(next.getOffset());
            aTUploadCityDataFile.setVersion(next.getVersion());
            aTUploadCityDataFile.setMd5(next.getMd5());
            String trim = next.getPath().trim();
            aTUploadCityDataFile.setFileName(trim.substring(trim.lastIndexOf("/") + 1));
            arrayList.add(aTUploadCityDataFile);
        }
        aTUploadCityDataItem.setUploadFiles(arrayList);
        return aTUploadCityDataItem;
    }

    public static long buildUploadOffset(List<ATCityDataFile> list, CityBean.FilesBean filesBean) {
        if (list == null) {
            return 0;
        }
        for (ATCityDataFile next : list) {
            if (next.getFileType().equals(filesBean.getType())) {
                long parseLong = Long.parseLong(filesBean.getVersion());
                long downloadingVersion = next.getDownloadingVersion();
                long currentVersion = next.getCurrentVersion();
                long downloadingSize = next.getDownloadingSize();
                if (downloadingSize <= 0) {
                    if (parseLong <= currentVersion) {
                        return -1;
                    }
                    return 0;
                } else if (parseLong > downloadingVersion) {
                    return 0;
                } else {
                    return downloadingSize;
                }
            }
        }
        return 0;
    }

    public static ATUploadCityDataItemRequest buildUploadDataInfo(CityBean cityBean, CityInfoResponse cityInfoResponse) throws Exception {
        long j;
        ATCityDataItem city = cityInfoResponse.getCityInfo().getCity();
        ATUploadCityDataItemRequest aTUploadCityDataItemRequest = new ATUploadCityDataItemRequest();
        ATUploadCityDataItem aTUploadCityDataItem = new ATUploadCityDataItem();
        String cityname = cityBean.getCityname();
        String pinyin = cityBean.getPinyin();
        int parseInt = Integer.parseInt(cityBean.getAutoStatus());
        if (Integer.parseInt(cityBean.getAdcode()) == 0) {
            cityname = CITY_QGGYT_CITY_NAME_AUTO;
            pinyin = "jichugongnengbao";
        }
        aTUploadCityDataItem.setName(cityname);
        aTUploadCityDataItem.setPinyin(pinyin);
        aTUploadCityDataItem.setAdCode(cityBean.getAdcode());
        aTUploadCityDataItem.setState(0);
        ArrayList arrayList = new ArrayList();
        for (CityBean.FilesBean next : cityBean.getFiles()) {
            ATUploadCityDataFile aTUploadCityDataFile = new ATUploadCityDataFile();
            aTUploadCityDataFile.setVersion(next.getVersion());
            aTUploadCityDataFile.setMd5(next.getMd5());
            aTUploadCityDataFile.setLength(next.getSize());
            String trim = next.getPath().trim();
            aTUploadCityDataFile.setFileName(trim.substring(trim.lastIndexOf("/") + 1));
            aTUploadCityDataFile.setFileType(next.getType());
            if (parseInt == 1) {
                j = 0;
            } else {
                j = buildUploadOffset(city.getFiles(), next);
            }
            aTUploadCityDataFile.setOffset(j);
            if (j != -1 && ((double) j) < aTUploadCityDataFile.getLength()) {
                aTUploadCityDataFile.setMd5(OfflineUtil.getFileMD5(new File(trim)));
                arrayList.add(aTUploadCityDataFile);
            }
        }
        aTUploadCityDataItem.setUploadFiles(arrayList);
        aTUploadCityDataItemRequest.setUploadCity(aTUploadCityDataItem);
        return aTUploadCityDataItemRequest;
    }

    public static File getUploadCrashDir() {
        File file = new File(PathManager.a().b(), "autonavi");
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(file, "uploadcrash");
        if (!file2.exists()) {
            file2.mkdir();
        }
        return file2;
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0088 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList extract(java.lang.String r8, java.lang.String r9) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x009d }
            r1.<init>(r8)     // Catch:{ Exception -> 0x009d }
            r8 = 256(0x100, float:3.59E-43)
            byte[] r8 = new byte[r8]     // Catch:{ Exception -> 0x009d }
            java.util.zip.ZipInputStream r2 = new java.util.zip.ZipInputStream     // Catch:{ Exception -> 0x009d }
            r2.<init>(r1)     // Catch:{ Exception -> 0x009d }
        L_0x0013:
            java.util.zip.ZipEntry r3 = r2.getNextEntry()     // Catch:{ Exception -> 0x009d }
            if (r3 == 0) goto L_0x0096
            java.lang.String r4 = r3.getName()     // Catch:{ Exception -> 0x009d }
            java.lang.String r5 = "../"
            boolean r5 = r4.contains(r5)     // Catch:{ Exception -> 0x009d }
            if (r5 == 0) goto L_0x002e
            java.lang.Exception r8 = new java.lang.Exception     // Catch:{ Exception -> 0x009d }
            java.lang.String r9 = "unsecurity zipfile!"
            r8.<init>(r9)     // Catch:{ Exception -> 0x009d }
            throw r8     // Catch:{ Exception -> 0x009d }
        L_0x002e:
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x009d }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009d }
            r6.<init>()     // Catch:{ Exception -> 0x009d }
            r6.append(r9)     // Catch:{ Exception -> 0x009d }
            java.lang.String r7 = java.io.File.separator     // Catch:{ Exception -> 0x009d }
            r6.append(r7)     // Catch:{ Exception -> 0x009d }
            r6.append(r4)     // Catch:{ Exception -> 0x009d }
            java.lang.String r4 = r6.toString()     // Catch:{ Exception -> 0x009d }
            r5.<init>(r4)     // Catch:{ Exception -> 0x009d }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x009d }
            java.io.File r6 = r5.getParentFile()     // Catch:{ Exception -> 0x009d }
            java.lang.String r6 = r6.getPath()     // Catch:{ Exception -> 0x009d }
            r4.<init>(r6)     // Catch:{ Exception -> 0x009d }
            boolean r3 = r3.isDirectory()     // Catch:{ Exception -> 0x009d }
            if (r3 == 0) goto L_0x0067
            boolean r3 = r5.exists()     // Catch:{ Exception -> 0x009d }
            if (r3 != 0) goto L_0x0063
            r5.mkdirs()     // Catch:{ Exception -> 0x009d }
        L_0x0063:
            r2.closeEntry()     // Catch:{ Exception -> 0x009d }
            goto L_0x0013
        L_0x0067:
            boolean r3 = r4.exists()     // Catch:{ Exception -> 0x009d }
            if (r3 != 0) goto L_0x0070
            r4.mkdirs()     // Catch:{ Exception -> 0x009d }
        L_0x0070:
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x009d }
            r3.<init>(r5)     // Catch:{ Exception -> 0x009d }
            java.lang.String r4 = r5.getAbsolutePath()     // Catch:{ Exception -> 0x0088, all -> 0x008c }
            r0.add(r4)     // Catch:{ Exception -> 0x0088, all -> 0x008c }
        L_0x007c:
            int r4 = r2.read(r8)     // Catch:{ Exception -> 0x0088, all -> 0x008c }
            r5 = -1
            if (r4 == r5) goto L_0x0088
            r5 = 0
            r3.write(r8, r5, r4)     // Catch:{ Exception -> 0x0088, all -> 0x008c }
            goto L_0x007c
        L_0x0088:
            r3.close()     // Catch:{ Exception -> 0x009d }
            goto L_0x0091
        L_0x008c:
            r8 = move-exception
            r3.close()     // Catch:{ Exception -> 0x009d }
            throw r8     // Catch:{ Exception -> 0x009d }
        L_0x0091:
            r2.closeEntry()     // Catch:{ Exception -> 0x009d }
            goto L_0x0013
        L_0x0096:
            r1.close()     // Catch:{ Exception -> 0x009d }
            r2.close()     // Catch:{ Exception -> 0x009d }
            goto L_0x00ae
        L_0x009d:
            r8 = move-exception
            java.io.PrintStream r9 = java.lang.System.err
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r1 = "Extract error:"
            r9.<init>(r1)
            java.lang.String r8 = r8.getMessage()
            r9.append(r8)
        L_0x00ae:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils.extract(java.lang.String, java.lang.String):java.util.ArrayList");
    }

    private String validateFilename(String str, String str2) throws IOException {
        String canonicalPath = new File(str).getCanonicalPath();
        if (canonicalPath.startsWith(new File(str2).getCanonicalPath())) {
            return canonicalPath;
        }
        throw new IllegalStateException("File is outside extraction target directory");
    }
}
