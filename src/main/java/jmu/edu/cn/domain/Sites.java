package jmu.edu.cn.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
@Entity
@Table(name = "sites")
@DynamicInsert
public class Sites extends IdEntity {
    private Date insertTime;
    private String site;
    private String province;
    private String remark;
    private List<TrainSite> trainSites;

    @Column(name = "insert_time")
    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @OneToMany(targetEntity = TrainSite.class, mappedBy = "site")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<TrainSite> getTrainSites() {
        return trainSites;
    }

    public void setTrainSites(List<TrainSite> trainSites) {
        this.trainSites = trainSites;
    }


}
