package com.autonavi.minimap.offline.auto.model.nativeModel;

import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import java.util.ArrayList;
import java.util.List;

public class AutoJsCity implements ResponseValue {
    private String code;
    private List<DataBean> data = new ArrayList();

    public static class DataBean {
        private String adcode;
        private List<CityBean> city;
        private String cityname;
        private String citysize;
        private int isUpdate;
        private String pinyin;

        public static class CityBean {
            private String adcode;
            private double alreadyDownloadSize;
            private String autoStatus;
            private String cityname;
            private String citysize;
            private List<FilesBean> files;
            private String isCurrentCity;
            private int isUpdate;
            private String pinyin;

            public static class FilesBean {
                private String md5;
                private long offset;
                private String path;
                private double size;
                private String type;
                private String version;

                public String getPath() {
                    return this.path;
                }

                public void setPath(String str) {
                    this.path = str;
                }

                public String getVersion() {
                    return this.version;
                }

                public void setVersion(String str) {
                    this.version = str;
                }

                public double getSize() {
                    return this.size;
                }

                public void setSize(double d) {
                    this.size = d;
                }

                public String getType() {
                    return this.type;
                }

                public void setType(String str) {
                    this.type = str;
                }

                public long getOffset() {
                    return this.offset;
                }

                public void setOffset(long j) {
                    this.offset = j;
                }

                public String getMd5() {
                    return this.md5;
                }

                public void setMd5(String str) {
                    this.md5 = str;
                }
            }

            public double getAlreadyDownloadSize() {
                return this.alreadyDownloadSize;
            }

            public void setAlreadyDownloadSize(double d) {
                this.alreadyDownloadSize = d;
            }

            public String getAdcode() {
                return this.adcode;
            }

            public void setAdcode(String str) {
                this.adcode = str;
            }

            public String getCityname() {
                return this.cityname;
            }

            public void setCityname(String str) {
                this.cityname = str;
            }

            public String getPinyin() {
                return this.pinyin;
            }

            public void setPinyin(String str) {
                this.pinyin = str;
            }

            public String getCitysize() {
                return this.citysize;
            }

            public void setCitysize(String str) {
                this.citysize = str;
            }

            public int getIsUpdate() {
                return this.isUpdate;
            }

            public void setIsUpdate(int i) {
                this.isUpdate = i;
            }

            public String getAutoStatus() {
                return this.autoStatus;
            }

            public void setAutoStatus(String str) {
                this.autoStatus = str;
            }

            public String getIsCurrentCity() {
                return this.isCurrentCity;
            }

            public void setIsCurrentCity(String str) {
                this.isCurrentCity = str;
            }

            public List<FilesBean> getFiles() {
                return this.files;
            }

            public void setFiles(List<FilesBean> list) {
                this.files = list;
            }
        }

        public String getAdcode() {
            return this.adcode;
        }

        public void setAdcode(String str) {
            this.adcode = str;
        }

        public String getCityname() {
            return this.cityname;
        }

        public void setCityname(String str) {
            this.cityname = str;
        }

        public String getPinyin() {
            return this.pinyin;
        }

        public void setPinyin(String str) {
            this.pinyin = str;
        }

        public String getCitysize() {
            return this.citysize;
        }

        public void setCitysize(String str) {
            this.citysize = str;
        }

        public int getIsUpdate() {
            return this.isUpdate;
        }

        public void setIsUpdate(int i) {
            this.isUpdate = i;
        }

        public List<CityBean> getCity() {
            return this.city;
        }

        public void setCity(List<CityBean> list) {
            this.city = list;
        }
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }
}
