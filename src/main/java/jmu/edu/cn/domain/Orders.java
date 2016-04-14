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
@Table(name = "orders")
@DynamicInsert
public class Orders extends IdEntity {
    private Date time;
    private Date beginTime;
    private String seatSerial;
    private int status;
    private Users users;
    private List<OrdersDetail> ordersDetails;
    private TrainDetail trainDetail;

    @Column(name = "begin_time")
    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @Column(name = "seat_serial")
    public String getSeatSerial() {
        return seatSerial;
    }

    public void setSeatSerial(String seatSerial) {
        this.seatSerial = seatSerial;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id", updatable = false)
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @OneToMany(targetEntity = OrdersDetail.class, mappedBy = "orders")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<OrdersDetail> getOrdersDetail() {
        return ordersDetails;
    }

    public void setOrdersDetail(List<OrdersDetail> ordersDetail) {
        this.ordersDetails = ordersDetail;
    }

    @ManyToOne(targetEntity = TrainDetail.class)
    @JoinColumn(name = "train_detail_id ", updatable = false)
    public TrainDetail getTrainDetail() {
        return trainDetail;
    }

    public void setTrainDetail(TrainDetail trainDetail) {
        this.trainDetail = trainDetail;
    }
}
