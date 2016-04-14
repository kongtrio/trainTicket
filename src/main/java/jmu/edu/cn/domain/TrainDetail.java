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
@Table(name = "train_detail")
@DynamicInsert
public class TrainDetail extends IdEntity {
    private Date time;
    private int seatNumber;
    private int status;
    private List<Orders> orders;
    private Train train;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "seat_number")
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @OneToMany(targetEntity = Orders.class, mappedBy = "trainDetail")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
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
