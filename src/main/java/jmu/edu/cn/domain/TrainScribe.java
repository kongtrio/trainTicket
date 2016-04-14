package jmu.edu.cn.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by Administrator on 2016/3/29.
 */
public class TrainScribe {
    private String siteName;
    private String time;
    private int price;
    private int siteIndex;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSiteIndex() {
        return siteIndex;
    }

    public void setSiteIndex(int siteIndex) {
        this.siteIndex = siteIndex;
    }
}
