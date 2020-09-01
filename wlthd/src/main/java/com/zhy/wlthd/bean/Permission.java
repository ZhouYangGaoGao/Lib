package com.zhy.wlthd.bean;

public class Permission {


    /**
     * code : self-registration
     * name : 自查上图
     * type : menu
     * component : selft-registration/selft-registration.vue
     * ico : https://dhome-baike.oss-cn-hangzhou.aliyuncs.com/20200827/661019de071c4565a68c9457139589d8.png
     */

    private String code;
    private String name;
    private String type;
    private String component;
    private String ico;
    private String remarks;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }
}
