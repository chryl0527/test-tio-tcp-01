package com.sdsoon.test.tio.bean;

import java.io.Serializable;

/**
 * Created By Chr on 2019/4/17.
 */
public class TestBean implements Serializable {

    private static final long serialVersionUID = 425589149527593254L;


    private Integer xh;
    private Byte dl;
    private Integer qd;
    private String imei;
    private String imsi;
    private Long timel;

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Byte getDl() {
        return dl;
    }

    public void setDl(Byte dl) {
        this.dl = dl;
    }

    public Integer getQd() {
        return qd;
    }

    public void setQd(Integer qd) {
        this.qd = qd;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public Long getTimel() {
        return timel;
    }

    public void setTimel(Long timel) {
        this.timel = timel;
    }
}

