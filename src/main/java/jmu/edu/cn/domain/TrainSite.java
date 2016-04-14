package jmu.edu.cn.domain;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/17.
 */
@Entity
@Table(name = "train_site")
@DynamicInsert
public class TrainSite extends IdEntity {
    private Sites site;
    private Train train;

    public TrainSite() {
    }

    public TrainSite(Sites site, Train train) {
        this.site = site;
        this.train = train;
    }

    @ManyToOne(targetEntity = Sites.class)
    @JoinColumn(name = "site", updatable = false)
    public Sites getSite() {
        return site;
    }

    public void setSite(Sites site) {
        this.site = site;
    }

    @ManyToOne(targetEntity = Train.class)
    @JoinColumn(name = "train_id", updatable = false)
    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}
