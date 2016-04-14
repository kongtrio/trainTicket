package jmu.edu.cn.domain;

import jmu.edu.cn.util.DateUtil;

/**
 * Created by Administrator on 2016/4/9.
 */
public class TrainReport {
    private String beginSite;
    private String endSite;
    private String beginTime;
    private String endTime;
    private String useTime;
    private TrainDetail trainDetail;

    public TrainReport(String beginSite, String endSite) {
        this.beginSite = beginSite;
        this.endSite = endSite;
    }

    public String getBeginSite() {
        return beginSite;
    }

    public void setBeginSite(String beginSite) {
        this.beginSite = beginSite;
    }

    public String getEndSite() {
        return endSite;
    }

    public void setEndSite(String endSite) {
        this.endSite = endSite;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUseTime() {
        if (this.beginTime == null || this.endTime == null) {
            return null;
        }
        int trainBeginTime = DateUtil.getDayTimes(this.beginTime);
        int trainEndTime = DateUtil.getDayTimes(this.endTime);
        int trainUseTime = trainEndTime - trainBeginTime;
        return DateUtil.getDayTimeStr(trainUseTime);
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public TrainDetail getTrainDetail() {
        return trainDetail;
    }

    public void setTrainDetail(TrainDetail trainDetail) {
        this.trainDetail = trainDetail;
    }
}
