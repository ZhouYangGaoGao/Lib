package com.zhy.wlthd;


import java.util.List;

/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class WLTLoginModel {


    /**
     * avatar : string
     * email : string
     * enable : false
     * gender : string
     * id : 0
     * loginTime : 2020-08-20T02:18:06.267Z
     * orgId : 0
     * orgName : string
     * phone : string
     * realName : string
     * regionCode : string
     * regionLevel : 0
     * regionList : [{"area":0,"createTime":"2020-08-20T02:18:06.267Z","creator":0,"fullName":"string","geometry":"string","id":0,"interiorPoint":"string","labelText":"string","lastName":"string","level":0,"modifier":0,"modifyTime":"2020-08-20T02:18:06.267Z","zqCode":"string"}]
     * regionName : string
     */

    private String token;
    private String avatar;
    private String email;
    private boolean enable;
    private String gender;
    private int id;
    private String loginTime;
    private int orgId;
    private String orgName;
    private String phone;
    private String realName;
    private String regionCode;
    private int regionLevel;
    private String regionName;
    private List<RegionListBean> regionList;

    public String getToken() {
        return token;
    }

    public static class RegionListBean {
        /**
         * area : 0
         * createTime : 2020-08-20T02:18:06.267Z
         * creator : 0
         * fullName : string
         * geometry : string
         * id : 0
         * interiorPoint : string
         * labelText : string
         * lastName : string
         * level : 0
         * modifier : 0
         * modifyTime : 2020-08-20T02:18:06.267Z
         * zqCode : string
         */

        private int area;
        private String createTime;
        private int creator;
        private String fullName;
        private String geometry;
        private int id;
        private String interiorPoint;
        private String labelText;
        private String lastName;
        private int level;
        private int modifier;
        private String modifyTime;
        private String zqCode;
    }
}

