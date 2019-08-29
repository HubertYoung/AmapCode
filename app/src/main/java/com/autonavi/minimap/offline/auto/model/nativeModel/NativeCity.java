package com.autonavi.minimap.offline.auto.model.nativeModel;

import java.util.List;

public class NativeCity {
    private List<DataBean> data;

    public static class DataBean {
        private String adcode;
        private List<CityBean> city;
        private String jianpin;
        private String name;
        private String pinyin;

        public static class CityBean {
            private String adcode;
            private List<FilesBean> files;
            private String id;
            private String jianpin;
            private double map_size;
            private String name;
            private String pinyin;
            private double route_size;
            private int status;

            public static class FilesBean {
                private String md5;
                private String path;
                private double size;
                private String type;
                private long version;

                public String getPath() {
                    return this.path;
                }

                public void setPath(String str) {
                    this.path = str;
                }

                public long getVersion() {
                    return this.version;
                }

                public void setVersion(long j) {
                    this.version = j;
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

                public String getMd5() {
                    return this.md5;
                }

                public void setMd5(String str) {
                    this.md5 = str;
                }
            }

            public String getId() {
                return this.id;
            }

            public void setId(String str) {
                this.id = str;
            }

            public String getJianpin() {
                return this.jianpin;
            }

            public void setJianpin(String str) {
                this.jianpin = str;
            }

            public String getPinyin() {
                return this.pinyin;
            }

            public void setPinyin(String str) {
                this.pinyin = str;
            }

            public String getName() {
                return this.name;
            }

            public void setName(String str) {
                this.name = str;
            }

            public String getAdcode() {
                return this.adcode;
            }

            public void setAdcode(String str) {
                this.adcode = str;
            }

            public int getStatus() {
                return this.status;
            }

            public void setStatus(int i) {
                this.status = i;
            }

            public double getMap_size() {
                return this.map_size;
            }

            public void setMap_size(double d) {
                this.map_size = d;
            }

            public double getRoute_size() {
                return this.route_size;
            }

            public void setRoute_size(double d) {
                this.route_size = d;
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

        public String getPinyin() {
            return this.pinyin;
        }

        public void setPinyin(String str) {
            this.pinyin = str;
        }

        public String getJianpin() {
            return this.jianpin;
        }

        public void setJianpin(String str) {
            this.jianpin = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
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
}
