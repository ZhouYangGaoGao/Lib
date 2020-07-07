package com.zhy.hd;


/**
 * @author ZhouYang
 * @describe
 * @date 2020/6/9  11:45 AM
 * - generate by MvpAutoCodePlus plugin.
 */

public class LoginModel {

    /**
     * clientType : cms
     * token : string
     * userInfo : {"appId":"string","avatar":"string","email":"string","enable":true,"gender":"string","lastLoginTime":"2020-06-09T06:53:28.317Z","orgId":0,"phone":"string","position":"string","realName":"string","regionCode":"string","regionLevel":0,"regionName":"string","regionObjid":"string","uid":0}
     */

    private String clientType;
    private String token;
    private UserInfoBean userInfo;

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        /**
         * appId : string
         * avatar : string
         * email : string
         * enable : true
         * gender : string
         * lastLoginTime : 2020-06-09T06:53:28.317Z
         * orgId : 0
         * phone : string
         * position : string
         * realName : string
         * regionCode : string
         * regionLevel : 0
         * regionName : string
         * regionObjid : string
         * uid : 0
         */

        private String appId;
        private String avatar;
        private String email;
        private boolean enable;
        private String gender;
        private String lastLoginTime;
        private int orgId;
        private String phone;
        private String position;
        private String realName;
        private String regionCode;
        private int regionLevel;
        private String regionName;
        private String regionObjid;
        private int uid;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public int getRegionLevel() {
            return regionLevel;
        }

        public void setRegionLevel(int regionLevel) {
            this.regionLevel = regionLevel;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public String getRegionObjid() {
            return regionObjid;
        }

        public void setRegionObjid(String regionObjid) {
            this.regionObjid = regionObjid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}

