package com.zhy.wlthd.bean;

import java.util.List;

public class Check {
    /**
     * year : 2020
     * planList : [{"id":"11","stage":"spot_check","startTime":"2020-08-30","endTime":"2020-08-31","status":"doing","remarks":""},{"id":"9","stage":"accept_check","startTime":null,"endTime":null,"status":"todo","remarks":""},{"id":"10","stage":"self_examination","startTime":"2020-08-29","endTime":"2020-08-31","status":"doing","remarks":""}]
     */

    private String year;
    private List<PlanListBean> planList;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<PlanListBean> getPlanList() {
        return planList;
    }

    public void setPlanList(List<PlanListBean> planList) {
        this.planList = planList;
    }

    public static class PlanListBean {
        /**
         * id : 11
         * stage : spot_check
         * startTime : 2020-08-30
         * endTime : 2020-08-31
         * status : doing
         * remarks :
         */

        private String id;
        private String stage;
        private String startTime;
        private String endTime;
        private String status;
        private String remarks;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}
