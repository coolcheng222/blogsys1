package com.sealll.application.fav.bean;

/**
 * @author sealll
 * @time 2021/5/12 18:49
 */
public class Faving {
    private String pid;
    private Boolean res;
    @Override
    public String toString() {
        return "Faving{" +
                "pid='" + pid + '\'' +
                ", res=" + res +
                '}';
    }



    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Boolean getRes() {
        return res;
    }

    public void setRes(Boolean res) {
        this.res = res;
    }
}
