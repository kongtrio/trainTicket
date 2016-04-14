package jmu.edu.cn.domain;

import jmu.edu.cn.util.DateUtil;
import jmu.edu.cn.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
@Entity
@Table(name = "train")
@DynamicInsert
public class Train extends IdEntity {
    private String trainSerial;
    private String trainSiteDetail;
    private int status;
    private List<TrainSite> trainSites;
    private List<TrainDetail> trainDetail;
    private List<TrainScribe> trainScribeList;

    @Column(name = "train_serial")
    public String getTrainSerial() {
        return trainSerial;
    }

    public void setTrainSerial(String trainSerial) {
        this.trainSerial = trainSerial;
    }

    @Column(name = "train_site_detail")
    public String getTrainSiteDetail() {
        return trainSiteDetail;
    }

    public void setTrainSiteDetail(String trainSiteDetail) {
        this.trainSiteDetail = trainSiteDetail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @OneToMany(targetEntity = TrainSite.class, mappedBy = "train")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TrainSite> getTrainSites() {
        return trainSites;
    }

    public void setTrainSites(List<TrainSite> trainSites) {
        this.trainSites = trainSites;
    }

    @OneToMany(targetEntity = TrainDetail.class, mappedBy = "train")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TrainDetail> getTrainDetail() {
        return trainDetail;
    }

    public void setTrainDetail(List<TrainDetail> trainDetail) {
        this.trainDetail = trainDetail;
    }

    @Transient
    public List<TrainScribe> getTrainScribeList() {
        if (StringUtils.isNotBlank(this.trainSiteDetail)) {
            List<TrainScribe> list = JsonMapper.nonEmptyMapper().fromJson(trainSiteDetail, JsonMapper.nonEmptyMapper().contructCollectionType(List.class, TrainScribe.class));
            return list;
        }
        return trainScribeList;
    }

    public void setTrainScribeList(List<TrainScribe> trainScribeList) {
        this.trainScribeList = trainScribeList;
    }

    public int getBeginSiteTime(String siteName) {
        List<TrainScribe> trainScribeList = this.getTrainScribeList();
        for (TrainScribe trainScribe : trainScribeList) {
            if (trainScribe.getSiteName().equals(siteName)) {
                return DateUtil.getDayTimes(trainScribe.getTime());
            }
        }
        return 0;
    }
}
