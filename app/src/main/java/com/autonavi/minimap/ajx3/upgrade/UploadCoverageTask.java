package com.autonavi.minimap.ajx3.upgrade;

import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.network.util.NetworkReachability;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UploadCoverageTask extends AsyncTask<JSONObject, Integer, Integer> {
    private static final String FILE_NAME = "coverage.info";
    private static final String FILE_PATH;
    private static final long MAX_SIZE = 3145728;
    private static final String TAG = "coverage";
    private static final String URL_PATH = "http://coverage.alibaba.net/api/coverage/v1/file/upload";
    TaskCallBack mCallBack;

    static class ComparatorByLastModified implements Comparator<File> {
        ComparatorByLastModified() {
        }

        public int compare(File file, File file2) {
            int i = ((file.lastModified() - file2.lastModified()) > 0 ? 1 : ((file.lastModified() - file2.lastModified()) == 0 ? 0 : -1));
            if (i > 0) {
                return -1;
            }
            return i == 0 ? 0 : 1;
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().toString());
        sb.append("/Android/data/com.autonavi.minimap/files/coverage_info/");
        FILE_PATH = sb.toString();
    }

    UploadCoverageTask(TaskCallBack taskCallBack) {
        this.mCallBack = taskCallBack;
    }

    /* access modifiers changed from: protected */
    public Integer doInBackground(JSONObject... jSONObjectArr) {
        saveInfoToFile(jSONObjectArr[0]);
        File file = new File(FILE_PATH);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                Arrays.sort(listFiles, new ComparatorByLastModified());
                int i = 0;
                while (i < listFiles.length) {
                    if (i >= 40) {
                        listFiles[i].delete();
                    } else if (isWifi()) {
                        uploadFile(listFiles[i]);
                    } else {
                        i = 40;
                    }
                    i++;
                }
            }
        }
        return Integer.valueOf(0);
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Integer num) {
        this.mCallBack.onFinished();
    }

    /* access modifiers changed from: protected */
    public void onCancelled() {
        super.onCancelled();
    }

    private static byte[] compressForGzip(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(str.getBytes());
            gZIPOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private void saveInfoToFile(JSONObject jSONObject) {
        if (checkDir()) {
            String str = FILE_PATH;
            StringBuilder sb = new StringBuilder("coverage.info.");
            sb.append(System.currentTimeMillis());
            File file = new File(str, sb.toString());
            JSONArray jSONArray = new JSONArray();
            if (file.exists()) {
                long length = file.length();
                if (length > 0 && length < MAX_SIZE) {
                    try {
                        jSONArray = new JSONArray(FileUtil.readData(file.getAbsolutePath()));
                    } catch (JSONException unused) {
                    }
                }
            }
            jSONArray.put(jSONObject);
            FileUtil.writeDatasToFile(file.getAbsolutePath(), jSONArray.toString().getBytes());
        }
    }

    private boolean isWifi() {
        return NetworkReachability.a();
    }

    private void uploadFile(File file) {
        byte[] compressForGzip = compressForGzip(FileUtil.readData(file.getAbsolutePath()));
        if (compressForGzip != null && compressForGzip.length > 0) {
            String submitPostData = submitPostData(compressForGzip);
            if (!TextUtils.isEmpty(submitPostData)) {
                try {
                    JSONObject jSONObject = new JSONObject(submitPostData);
                    if (jSONObject.has("errorCode") && jSONObject.getInt("errorCode") == 0) {
                        file.delete();
                    }
                } catch (JSONException unused) {
                }
            }
        }
    }

    private boolean checkDir() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    private static String submitPostData(byte[] bArr) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(URL_PATH).openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("content-encoding", "gzip");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.getOutputStream().write(bArr);
            if (httpURLConnection.getResponseCode() == 200) {
                return dealResponseResult(httpURLConnection.getInputStream());
            }
        } catch (IOException unused) {
        }
        return "-1";
    }

    private static String dealResponseResult(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            } catch (IOException unused) {
            }
        }
        return new String(byteArrayOutputStream.toByteArray());
    }
}
